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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.descriptions.State2ActionDescriptionEntry;
import org.socialworld.datasource.parsing.JsonState2ActionDescription;
import org.socialworld.datasource.pool.GaussPoolState2ActionType;

/**
 * Generates the state-to-action rules for every state-to-action type from characters.csv
 * (state2action_konzept.md, section 7). Type t gets the rules of character t + 1.
 *
 * The chain of a type: sleep (from tiredBed, for sleepHours), eat (from hungerEat, 18 min),
 * then up to three own rules from the most pronounced levels of the character.
 * A character without a pronounced level gets the former curiosity rule.
 *
 * The order of the entries is their precedence: only the first entry whose condition holds becomes an action.
 */
public class State2ActionGenerator {

	public static final int COUNT_STATE2ACTION_TYPES = GaussPoolState2ActionType.CAPACITY_GPS2A_ARRAY;

	/** At most this many own rules per character, after sleeping and eating. */
	public static final int MAX_OWN_RULES = 3;

	static final int MILLISECONDS_PER_HOUR = 3600000;
	/** A meal takes 18 min for everybody (108 selfEat events every 10 s), until it follows the food. */
	static final int DURATION_EAT = 1080000;
	/** Length of the walking direction. */
	static final double DIRECTION_LENGTH = 5.0;

	/** The attributes that can give an own rule, in the order that decides a tie. */
	static final Attribute[] OWN_RULE_ATTRIBUTES = {Attribute.mood, Attribute.courage, Attribute.morals,
			Attribute.materialism, Attribute.curiosity, Attribute.spirituality, Attribute.power};

	/** What was written. */
	public static final class Result {
		private final int countRules;
		private final int countEntries;
		private final int countFallbacks;

		Result(int countRules, int countEntries, int countFallbacks) {
			this.countRules = countRules;
			this.countEntries = countEntries;
			this.countFallbacks = countFallbacks;
		}

		public int getCountRules() {
			return countRules;
		}

		public int getCountEntries() {
			return countEntries;
		}

		/** @return the number of types that got the former curiosity rule, because their character has no pronounced level */
		public int getCountFallbacks() {
			return countFallbacks;
		}

		@Override
		public String toString() {
			return countRules + " rules with " + countEntries + " entries (" + countFallbacks + " with the fallback rule)";
		}
	}

	/** The action of an own rule and its values, taken over from the former rules. */
	enum OwnAction {
		walk(ActionType.move, ActionMode.walk, 50, "8;0.015;23", 2000, true),
		sneak(ActionType.move, ActionMode.sneak, 50, "8;0.015;23", 2000, true),
		run(ActionType.move, ActionMode.run, 60, "8;0.01;0", 5000, true),
		sayNormal(ActionType.say, ActionMode.sayNormal, 70, "8;0.03;0", 1000, false),
		sayScream(ActionType.say, ActionMode.sayScream, 70, "8;0.03;0", 1000, false),
		sayWhisper(ActionType.say, ActionMode.sayWhisper, 70, "8;0.03;0", 1000, false);

		final ActionType type;
		final ActionMode mode;
		final int priority;
		final String intensity;
		final int duration;
		final boolean hasDirection;

		private OwnAction(ActionType type, ActionMode mode, int priority, String intensity, int duration, boolean hasDirection) {
			this.type = type;
			this.mode = mode;
			this.priority = priority;
			this.intensity = intensity;
			this.duration = duration;
			this.hasDirection = hasDirection;
		}
	}

	private final AttributeCharacterCatalog characters;

	public State2ActionGenerator(AttributeCharacterCatalog characters) {
		if (characters.size() < COUNT_STATE2ACTION_TYPES) {
			throw new IllegalArgumentException(AttributeCharacterCatalog.RESOURCE + " has " + characters.size()
					+ " characters, but there are " + COUNT_STATE2ACTION_TYPES + " state-to-action types");
		}
		this.characters = characters;
	}

	public static State2ActionGenerator fromResources() {
		return new State2ActionGenerator(AttributeCharacterCatalog.load());
	}

	/** @return the number of rules that generate() writes */
	public static int countRules() {
		return COUNT_STATE2ACTION_TYPES;
	}

	/** @return the character whose rules the type gets: character number = type + 1 */
	public AttributeCharacter characterOf(int state2ActionType) {
		return characters.getCharacters().get(state2ActionType);
	}

	public JsonState2ActionDescription buildRule(int state2ActionType) {
		AttributeCharacter character = characterOf(state2ActionType);

		JsonState2ActionDescription rule = new JsonState2ActionDescription();
		rule.state2ActionType = state2ActionType;
		rule.entrys = new ArrayList<State2ActionDescriptionEntry>();
		rule.entrys.add(sleep(character));
		rule.entrys.add(eat(character));

		List<Attribute> own = pronouncedAttributes(character);
		if (own.isEmpty()) {
			rule.entrys.add(fallback());
		}
		for (Attribute attribute : own) {
			rule.entrys.add(ownRule(character, attribute));
		}
		return rule;
	}

	/** Removes all rules of the store and writes one rule for every state-to-action type. */
	public Result generate(State2ActionStore store) {
		int countRules = 0;
		int countEntries = 0;
		int countFallbacks = 0;

		store.clear();
		for (int state2ActionType = 0; state2ActionType < COUNT_STATE2ACTION_TYPES; state2ActionType++) {
			JsonState2ActionDescription rule = buildRule(state2ActionType);
			store.insert(state2ActionType, rule.toString());

			countRules++;
			countEntries += rule.entrys.size();
			if (pronouncedAttributes(characterOf(state2ActionType)).isEmpty()) countFallbacks++;
		}
		return new Result(countRules, countEntries, countFallbacks);
	}

	// --- sleeping and eating (concept 7.1, 7.2) ---

	static State2ActionDescriptionEntry sleep(AttributeCharacter character) {
		int threshold = character.getIdealHighest(Attribute.tiredness);
		int duration = (int) Math.round(character.getSleepHours() * (double) MILLISECONDS_PER_HOUR);
		return entry(Attribute.tiredness + " >= " + threshold, ActionType.bodilyFunction, ActionMode.sleep,
				10000, 100000, 40, "4;0.015;0", duration, null);
	}

	static State2ActionDescriptionEntry eat(AttributeCharacter character) {
		int threshold = character.getIdealHighest(Attribute.hunger);
		return entry(Attribute.hunger + " >= " + threshold, ActionType.bodilyFunction, ActionMode.eat,
				10000, 100000, 40, "7;0.015;0", DURATION_EAT, null);
	}

	// --- own rules (concept 7.3) ---

	/** @return how far the level is from o: 3 for ---/+++, 2 for --/++, 1 for -/+, 0 for o and ~ */
	static int distanceFromAverage(AttributeLevel level) {
		switch (level) {
		case extremelyLow:
		case extremelyHigh:
			return 3;
		case veryLow:
		case veryHigh:
			return 2;
		case low:
		case high:
			return 1;
		default:
			return 0;
		}
	}

	static boolean isHigh(AttributeLevel level) {
		return level == AttributeLevel.high || level == AttributeLevel.veryHigh || level == AttributeLevel.extremelyHigh;
	}

	/** @return the middle of the band, rounded up: + 6650, ++ 8200, +++ 9500, - 3350, -- 1800, --- 500 */
	static int middleOfBand(AttributeLevel level) {
		return (level.getLowest() + level.getHighest() + 1) / 2;
	}

	/** @return up to three attributes, the most pronounced first; a tie keeps the order of OWN_RULE_ATTRIBUTES */
	static List<Attribute> pronouncedAttributes(AttributeCharacter character) {
		List<Attribute> result = new ArrayList<Attribute>();
		for (int distance = 3; distance >= 1 && result.size() < MAX_OWN_RULES; distance--) {
			for (Attribute attribute : OWN_RULE_ATTRIBUTES) {
				if (result.size() == MAX_OWN_RULES) break;
				if (distanceFromAverage(character.getLevel(attribute.getIndex())) == distance) result.add(attribute);
			}
		}
		return result;
	}

	/** The table of concept 7.3.3: attribute × high/low → action. */
	static OwnAction actionFor(Attribute attribute, boolean high) {
		switch (attribute) {
		case curiosity:
			return high ? OwnAction.walk : OwnAction.sneak;
		case courage:
			return high ? OwnAction.run : OwnAction.sneak;
		case mood:
			return high ? OwnAction.sayNormal : OwnAction.sayScream;
		case power:
			return high ? OwnAction.run : OwnAction.walk;
		case morals:
			return high ? OwnAction.sayNormal : OwnAction.sayWhisper;
		case materialism:
			return high ? OwnAction.walk : OwnAction.sayWhisper;
		case spirituality:
			return high ? OwnAction.sayWhisper : OwnAction.walk;
		default:
			throw new IllegalArgumentException(attribute + " gives no own rule");
		}
	}

	static State2ActionDescriptionEntry ownRule(AttributeCharacter character, Attribute attribute) {
		AttributeLevel level = character.getLevel(attribute.getIndex());
		boolean high = isHigh(level);
		String condition = attribute + (high ? " >= " : " < ") + middleOfBand(level);
		OwnAction action = actionFor(attribute, high);
		return entry(condition, action.type, action.mode, 1000, 5000, action.priority, action.intensity, action.duration,
				action.hasDirection ? direction(character.getNumber()) : null);
	}

	/** @return the walking direction of the character: angle (number - 1) × 3.6°, length 5, two decimals */
	static String direction(int characterNumber) {
		double angle = Math.toRadians((characterNumber - 1) * 360.0 / 100);
		// + 0.0 turns -0.0 into 0.0
		double x = Math.round(DIRECTION_LENGTH * Math.cos(angle) * 100) / 100.0 + 0.0;
		double y = Math.round(DIRECTION_LENGTH * Math.sin(angle) * 100) / 100.0 + 0.0;
		return String.format(Locale.ROOT, "(%.2f,%.2f,0)", x, y);
	}

	/** The former curiosity rule, for a character without a pronounced level (concept 7.3.7). */
	static State2ActionDescriptionEntry fallback() {
		return entry("curiosity >= 5500 & curiosity < 6500 & power >= 3000", ActionType.move, ActionMode.walk,
				1000, 5000, 50, "8;0.015;23", 2000, "(2,7,0)");
	}

	private static State2ActionDescriptionEntry entry(String conditions, ActionType actionType, ActionMode actionMode,
			int minTimeFromNow, int maxTimeFromNow, int priority, String intensityMXplusN, int duration, String direction) {
		State2ActionDescriptionEntry entry = new State2ActionDescriptionEntry();
		entry.conditions = conditions;
		entry.actionType = constant(Integer.toString(actionType.getIndex()));
		entry.actionMode = constant(Integer.toString(actionMode.getIndex()));
		entry.minTime = "<Now+N>" + minTimeFromNow + "</Now+N>";
		entry.maxTime = "<Now+N>" + maxTimeFromNow + "</Now+N>";
		entry.priority = constant(Integer.toString(priority));
		entry.intensity = "<MX+N>" + intensityMXplusN + "</MX+N>";
		entry.duration = constant(Integer.toString(duration));
		if (direction != null) entry.direction = constant(direction);
		return entry;
	}

	private static String constant(String value) {
		return "<Const>" + value + "</Const>";
	}
}
