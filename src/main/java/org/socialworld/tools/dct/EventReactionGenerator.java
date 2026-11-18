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
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.EventReactionDescriptionEntry;
import org.socialworld.core.EventType;
import org.socialworld.datasource.parsing.JsonEventReactionDescription;
import org.socialworld.datasource.pool.EventReactionFamily;
import org.socialworld.datasource.pool.GaussPoolReactionType;

/**
 * Generates the event reaction rules for every event family and every reaction type (erd_regeln_konzept.md, section 4).
 *
 * Type t = 0..111: style t / 16, variant t mod 16 = c + 4 e + 8 k with
 * c the condition (mood high, mood low, courage high, curiosity high),
 * e the fallback (nothing, or the action of the fallback style when the condition does not hold),
 * k the length (short, long = duration × 3).
 * Type 112..115: the former behaviour (look, else hand).
 *
 * The reaction type does not follow the character; it is drawn per family (EventReactionFamily).
 */
public class EventReactionGenerator {

	public static final int COUNT_REACTION_TYPES = GaussPoolReactionType.CAPACITY_GPRT_ARRAY;
	public static final int COUNT_VARIANTS = 16;
	/** The first type with the former behaviour. */
	public static final int FIRST_FORMER_TYPE = Style.values().length * COUNT_VARIANTS;

	static final String CAUSER = "<GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm>";
	static final String LOUDNESS = "3 " + Value.VALUE_BY_NAME_EVENT_SAY_LOUDNESS;
	static final int LONG_FACTOR = 3;
	static final double DIRECTION_LENGTH = 5.0;

	/** The seven styles, in the order of the types. */
	enum Style {
		observer, toucher, fearful, turningAway, echo, indifferent, brawler;

		/** The style whose action follows when the condition does not hold (e = 1). */
		Style fallback() {
			switch (this) {
			case observer: return toucher;
			case toucher: return fearful;
			case fearful: return turningAway;
			case turningAway: return echo;
			case echo: return brawler;
			case brawler: return observer;
			default: return observer; // indifferent
			}
		}
	}

	/** The four conditions of a variant (c) and their opposites. */
	static final String[] CONDITIONS = {"mood >= 5000", "mood < 5000", "courage >= 5000", "curiosity >= 5000"};
	static final String[] OPPOSITE_CONDITIONS = {"mood < 5000", "mood >= 5000", "courage < 5000", "curiosity < 5000"};

	/** What was written. */
	public static final class Result {
		private final int countRules;
		private final int countEntries;
		private final int countEmpty;

		Result(int countRules, int countEntries, int countEmpty) {
			this.countRules = countRules;
			this.countEntries = countEntries;
			this.countEmpty = countEmpty;
		}

		public int getCountRules() {
			return countRules;
		}

		public int getCountEntries() {
			return countEntries;
		}

		/** @return the pairs without a rule (indifferent without fallback) */
		public int getCountEmpty() {
			return countEmpty;
		}

		@Override
		public String toString() {
			return countRules + " rules with " + countEntries + " entries (" + countEmpty + " pairs without a reaction)";
		}
	}

	/** @return the number of rules that generate() writes */
	public static int countRules() {
		int empty = EventReactionFamily.values().length * (COUNT_VARIANTS / 2);
		return EventReactionFamily.values().length * COUNT_REACTION_TYPES - empty;
	}

	static Style styleOf(int reactionType) {
		return Style.values()[reactionType / COUNT_VARIANTS];
	}

	static int conditionOf(int reactionType) {
		return reactionType % COUNT_VARIANTS % 4;
	}

	static boolean hasFallback(int reactionType) {
		return reactionType % COUNT_VARIANTS / 4 % 2 == 1;
	}

	static boolean isLong(int reactionType) {
		return reactionType % COUNT_VARIANTS / 8 == 1;
	}

	/** Removes all rules of the store and writes one rule for every family and reaction type that reacts. */
	public Result generate(EventReactionStore store) {
		int countRules = 0;
		int countEntries = 0;
		int countEmpty = 0;

		store.clear();
		for (EventReactionFamily family : EventReactionFamily.values()) {
			for (EventType eventType : family.getEventTypes()) {
				for (int reactionType = 0; reactionType < COUNT_REACTION_TYPES; reactionType++) {
					JsonEventReactionDescription rule = buildRule(family, eventType, reactionType);
					if (rule == null) {
						countEmpty++;
						continue;
					}
					store.insert(eventType.getIndex(), reactionType, rule.toString());
					countRules++;
					countEntries += rule.entrys.size();
				}
			}
		}
		return new Result(countRules, countEntries, countEmpty);
	}

	/** @return the rule of the pair, null if this reaction type does not react to the family */
	public JsonEventReactionDescription buildRule(EventReactionFamily family, EventType eventType, int reactionType) {
		List<EventReactionDescriptionEntry> entries = entries(family, reactionType);
		if (entries.isEmpty()) return null;

		JsonEventReactionDescription rule = new JsonEventReactionDescription();
		rule.eventType = eventType.name();
		rule.reactionType = reactionType;
		rule.entrys = entries;
		return rule;
	}

	static List<EventReactionDescriptionEntry> entries(EventReactionFamily family, int reactionType) {
		List<EventReactionDescriptionEntry> entries = new ArrayList<EventReactionDescriptionEntry>();

		if (reactionType >= FIRST_FORMER_TYPE) {
			entries.add(formerLook(family));
			entries.add(formerHand(family));
			return entries;
		}

		Style style = styleOf(reactionType);
		int c = conditionOf(reactionType);
		boolean longer = isLong(reactionType);

		if (style == Style.indifferent) {
			// no own action; with fallback it reacts as an observer when its condition does not hold
			if (hasFallback(reactionType)) {
				entries.add(action(Style.observer, family, reactionType, longer, OPPOSITE_CONDITIONS[c]));
			}
			return entries;
		}

		entries.add(action(style, family, reactionType, longer, CONDITIONS[c]));
		if (hasFallback(reactionType)) {
			entries.add(action(style.fallback(), family, reactionType, longer, OPPOSITE_CONDITIONS[c]));
		}
		return entries;
	}

	/** The action of a style for a family (table 4.1), with the values of table 4.3. */
	static EventReactionDescriptionEntry action(Style style, EventReactionFamily family, int reactionType, boolean longer,
			String condition) {
		switch (style) {
		case observer:
			return entry(condition, ActionType.examine, ActionMode.look, 60, "<MX+N>8;0.015;0</MX+N>", 5000, longer, CAUSER, null);
		case toucher:
			return entry(condition, ActionType.touch, ActionMode.hand, 58, "<MX+N>8;0.013;23</MX+N>", 2000, longer, CAUSER, null);
		case fearful:
			return entry(condition, ActionType.move, ActionMode.sneak, 50, "<MX+N>8;0.015;23</MX+N>", 2000, longer, null,
					direction(reactionType));
		case turningAway:
			return entry(condition, ActionType.move, ActionMode.walk, 50, "<MX+N>8;0.015;23</MX+N>", 5000, longer, null,
					direction(reactionType));
		case echo:
			return entry(condition, ActionType.say, echoMode(family), 70, "<MX+N>8;0.03;0</MX+N>", 1000, longer, null, null);
		case brawler:
			if (longer) {
				// the club: one blow, not stretched (mehrere Schläge sind mehrere Aktionen)
				return entry(condition, ActionType.useWeapon, ActionMode.weaponClub, 62, "<MX+N>8;0.015;0</MX+N>", 1000, false,
						CAUSER, null);
			}
			return entry(condition, ActionType.punch, ActionMode.punchRightFistStraight, 62, "<MX+N>8;0.001;0</MX+N>", 2000, false,
					CAUSER, null);
		default:
			throw new IllegalArgumentException(style + " has no action");
		}
	}

	/** The echo answers in the manner of the family: scream to a scream, whisper to a whisper. */
	static ActionMode echoMode(EventReactionFamily family) {
		switch (family) {
		case screamHeard: return ActionMode.sayScream;
		case whisperHeard: return ActionMode.sayWhisper;
		case touched: return ActionMode.sayScream;
		default: return ActionMode.sayNormal; // sayHeard, lookedAt
		}
	}

	/** Look as before (mood 2000..5500); for the scream with the loudness as before. */
	static EventReactionDescriptionEntry formerLook(EventReactionFamily family) {
		String condition = (family == EventReactionFamily.screamHeard ? LOUDNESS + " > 50 & " : "") + "mood >= 2000 & mood < 5500";
		return entry(condition, ActionType.examine, ActionMode.look, 60, "<Const>1</Const>", 5000, false, CAUSER, null);
	}

	/** Else hand as before (mood 1000..6500), now with the causer as target. */
	static EventReactionDescriptionEntry formerHand(EventReactionFamily family) {
		String condition = (family == EventReactionFamily.screamHeard ? LOUDNESS + " > 30 & " : "") + "mood >= 1000 & mood < 6500";
		return entry(condition, ActionType.touch, ActionMode.hand, 58, "<MX+N>8;0.013;23</MX+N>", 2000, false, CAUSER, null);
	}

	/** @return the fixed direction of a reaction type: angle t × 360° / 116, length 5, two decimals */
	static String direction(int reactionType) {
		double angle = Math.toRadians(reactionType * 360.0 / COUNT_REACTION_TYPES);
		// + 0.0 turns -0.0 into 0.0
		double x = Math.round(DIRECTION_LENGTH * Math.cos(angle) * 100) / 100.0 + 0.0;
		double y = Math.round(DIRECTION_LENGTH * Math.sin(angle) * 100) / 100.0 + 0.0;
		return String.format(Locale.ROOT, "(%.2f,%.2f,0)", x, y);
	}

	private static EventReactionDescriptionEntry entry(String conditions, ActionType actionType, ActionMode actionMode,
			int priority, String intensity, int duration, boolean longer, String target, String direction) {
		EventReactionDescriptionEntry entry = new EventReactionDescriptionEntry();
		entry.conditions = conditions;
		entry.actionType = constant(Integer.toString(actionType.getIndex()));
		entry.actionMode = constant(Integer.toString(actionMode.getIndex()));
		entry.minTime = "<Now+N>1000</Now+N>";
		entry.maxTime = "<Now+N>10000</Now+N>";
		entry.priority = constant(Integer.toString(priority));
		entry.intensity = intensity;
		entry.duration = constant(Integer.toString(longer ? duration * LONG_FACTOR : duration));
		if (target != null) entry.target = target;
		if (direction != null) entry.direction = constant(direction);
		return entry;
	}

	private static String constant(String value) {
		return "<Const>" + value + "</Const>";
	}
}
