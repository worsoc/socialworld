/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.tools.dct;

import java.util.Collections;
import java.util.List;

import org.socialworld.core.EventType;
import org.socialworld.datasource.parsing.JsonEventInfluenceDescription;
import org.socialworld.datasource.pool.GaussPoolInfluenceType;

/**
 * Generates the event influence rules for every event type and every influence type
 * from event_situations.csv, event_influences.csv and characters.csv (concept sections 6 to 8).
 *
 * The influence type of a character is its number - 1. As long as event_influences.csv
 * has fewer characters than influence types, the characters are repeated:
 * influence type i gets the (i mod n)-th character of the file.
 * Event types without a situation, and situations without lines of a character, get a neutral
 * rule that changes nothing.
 */
public class EventInfluenceGenerator {

	public static final int COUNT_INFLUENCE_TYPES = GaussPoolInfluenceType.CAPACITY_GPIT_ARRAY;

	/** What was written. */
	public static final class Result {
		private final int countRules;
		private final int countNeutralRules;
		private final int countEntries;
		private final int countCharacters;

		Result(int countRules, int countNeutralRules, int countEntries, int countCharacters) {
			this.countRules = countRules;
			this.countNeutralRules = countNeutralRules;
			this.countEntries = countEntries;
			this.countCharacters = countCharacters;
		}

		public int getCountRules() {
			return countRules;
		}

		public int getCountNeutralRules() {
			return countNeutralRules;
		}

		public int getCountEntries() {
			return countEntries;
		}

		public int getCountCharacters() {
			return countCharacters;
		}

		public boolean isRepeated() {
			return countCharacters < COUNT_INFLUENCE_TYPES;
		}

		@Override
		public String toString() {
			return countRules + " rules (" + countNeutralRules + " neutral) with " + countEntries + " entries, "
					+ countCharacters + " characters"
					+ (isRepeated() ? ", repeated over " + COUNT_INFLUENCE_TYPES + " influence types" : "");
		}
	}

	private final EventInfluenceSituations situations;
	private final EventInfluenceCatalog catalog;
	private final AttributeCharacterCatalog characters;
	private final List<Integer> characterNumbers;

	public EventInfluenceGenerator(EventInfluenceSituations situations, EventInfluenceCatalog catalog,
			AttributeCharacterCatalog characters) {
		this.situations = situations;
		this.catalog = catalog;
		this.characters = characters;
		this.characterNumbers = catalog.getCharacters();
		if (characterNumbers.isEmpty()) {
			throw new IllegalArgumentException("event influences without characters");
		}
		for (int number : characterNumbers) {
			if (number < 1 || number > characters.size()) {
				throw new IllegalArgumentException("character " + number + " is not in " + AttributeCharacterCatalog.RESOURCE);
			}
		}
	}

	public static EventInfluenceGenerator fromResources() {
		return new EventInfluenceGenerator(EventInfluenceSituations.load(), EventInfluenceCatalog.load(),
				AttributeCharacterCatalog.load());
	}

	/** @return the number of the character whose rules the influence type gets */
	public int characterOf(int influenceType) {
		return characterNumbers.get(influenceType % characterNumbers.size());
	}

	/** @return the number of rules that generate() writes */
	public static int countRules() {
		return (EventType.values().length - 1) * COUNT_INFLUENCE_TYPES;
	}

	public JsonEventInfluenceDescription buildRule(EventType eventType, int influenceType) {
		int number = characterOf(influenceType);
		AttributeCharacter character = characters.getCharacters().get(number - 1);
		EventInfluenceSituations.Entry situation = situations.get(eventType);

		List<EventInfluenceCatalog.Line> lines = situation == null
				? Collections.emptyList()
				: catalog.getLines(number, situation.getSituation());
		float factor = situation == null ? 0f : situation.getFactor();

		return EventInfluenceRuleBuilder.build(eventType, influenceType, character, lines, factor);
	}

	/** Removes all rules of the store and writes one rule for every event type and influence type. */
	public Result generate(EventInfluenceStore store) {
		int countRules = 0;
		int countNeutral = 0;
		int countEntries = 0;

		store.clear();
		for (EventType eventType : EventType.values()) {
			if (eventType == EventType.nothing) continue;

			for (int influenceType = 0; influenceType < COUNT_INFLUENCE_TYPES; influenceType++) {
				JsonEventInfluenceDescription rule = buildRule(eventType, influenceType);
				store.insert(eventType.getIndex(), influenceType, rule.toString());

				countRules++;
				countEntries += rule.attributeChanges.size();
				if (rule.attributeChanges.isEmpty()) countNeutral++;
			}
		}
		return new Result(countRules, countNeutral, countEntries, characterNumbers.size());
	}
}
