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

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.FunctionArgType;
import org.socialworld.calculation.Type;
import org.socialworld.core.EventType;
import org.socialworld.datasource.parsing.JsonEventInfluenceDescription;
import org.socialworld.datasource.parsing.JsonEventInfluencesAttributeDescription;
import org.socialworld.datasource.parsing.JsonFunctionArg;
import org.socialworld.datasource.parsing.JsonTerm;
import org.socialworld.datasource.parsing.JsonValue;

/**
 * Builds the event influence rule of one event type for one influence type from the lines
 * of a character (concept section 6.3).
 *
 * Every line becomes up to four entries, one per tier of the character's band [low, high]
 * with the middle m: [0, low), [low, m), [m, high), [high, 9999]. Each entry has a strict
 * condition of two comparisons and the action attribute := attribute + step, the step
 * multiplied by the factor of the event type and rounded symmetrically.
 * If the attribute falls, the entries run from the lowest tier up, if it rises from the
 * highest tier down, so a value changed in one tier only reaches tiers already checked.
 * Steps of 0 and empty tiers give no entry. A rule without entries changes nothing.
 *
 * For hunger and tiredness the tiers sit at the ideal of the character instead of the band of its
 * level (concept section 14): tiredness [after sleep, at bedtime], hunger [after a meal, when going to eat].
 * Both have no target in the attribute matrix and swing every day further than a narrow band reaches.
 *
 * The need of the character (concept section 13.4) scales the steps of sleeping and eating:
 * tiredness while sleeping × 8 / sleepHours, hunger and tiredness while eating × 3 / mealsPerDay.
 * Who sleeps and eats as much as it needs then has the daily course of the reference (8 h, 3 meals).
 */
public final class EventInfluenceRuleBuilder {

	/** Situations of event_situations.csv whose steps follow the need of the character. */
	static final int SITUATION_SLEEP = 1;
	static final int SITUATION_EAT = 3;

	private EventInfluenceRuleBuilder() {
	}

	/** The factor from the need of sleep or food of the character, 1 for all other lines. */
	static float needFactor(EventInfluenceCatalog.Line line, AttributeCharacter character) {
		Attribute attribute = line.getAttribute();
		if (line.getSituation() == SITUATION_SLEEP && attribute == Attribute.tiredness) {
			return AttributeCharacter.DEFAULT_SLEEP_HOURS / character.getSleepHours();
		}
		if (line.getSituation() == SITUATION_EAT && (attribute == Attribute.hunger || attribute == Attribute.tiredness)) {
			return (float) AttributeCharacter.DEFAULT_MEALS_PER_DAY / character.getMealsPerDay();
		}
		return 1f;
	}

	/** Rounds half away from zero: 7.5 → 8, −7.5 → −8. */
	static int roundSymmetric(float value) {
		int rounded = Math.round(Math.abs(value));
		return value < 0 ? -rounded : rounded;
	}

	/**
	 * @param character the character whose bands (for hunger and tiredness its ideal) give the tiers
	 * @param lines the lines of the character in the situation of the event type, may be empty
	 * @param factor the factor of the event type in its situation
	 */
	public static JsonEventInfluenceDescription build(EventType eventType, int influenceType,
			AttributeCharacter character, List<EventInfluenceCatalog.Line> lines, float factor) {

		JsonEventInfluenceDescription root = new JsonEventInfluenceDescription();
		root.eventType = eventType.name();
		root.influenceType = influenceType;
		root.attributeChanges = new ArrayList<>();

		for (EventInfluenceCatalog.Line line : lines) {
			Attribute attribute = line.getAttribute();
			if (attribute == null) continue;

			float lineFactor = factor * needFactor(line, character);
			int low;
			int high;
			if (AttributeCharacter.hasIdeal(attribute)) {
				low = character.getIdealLowest(attribute);
				high = character.getIdealHighest(attribute);
			} else {
				AttributeLevel level = character.getLevel(attribute.getIndex());
				low = level.getLowest();
				high = level.getHighest();
			}
			int middle = (low + high) / 2;
			int[] lowerBounds = {0, low, middle, high};
			int[] upperBounds = {low, middle, high, AttributeArray.ATTRIBUTE_VALUE_MAX};

			boolean rises = false;
			for (int tier = 0; tier < EventInfluenceCatalog.COUNT_STEPS; tier++) {
				if (line.getStep(tier) > 0) rises = true;
			}

			for (int i = 0; i < EventInfluenceCatalog.COUNT_STEPS; i++) {
				int tier = rises ? EventInfluenceCatalog.COUNT_STEPS - 1 - i : i;
				int step = roundSymmetric(line.getStep(tier) * lineFactor);
				if (step == 0) continue;

				boolean top = tier == EventInfluenceCatalog.COUNT_STEPS - 1;
				if (top ? lowerBounds[tier] > upperBounds[tier] : lowerBounds[tier] >= upperBounds[tier]) continue;

				root.attributeChanges.add(entry(attribute, root.attributeChanges.size(),
						lowerBounds[tier], top ? "<=" : "<", upperBounds[tier], step));
			}
		}
		return root;
	}

	private static JsonEventInfluencesAttributeDescription entry(Attribute attribute, int orderNr,
			int lowerBound, String upperOperator, int upperBound, int step) {

		String name = attribute.toString();

		JsonTerm condition = new JsonTerm();
		condition.termNr = 1;
		condition.function = "condition";
		condition.functionArgs = new ArrayList<>();
		condition.functionArgs.add(arg(1, FunctionArgType.Attribute, Type.string, name));
		condition.functionArgs.add(arg(2, FunctionArgType.Const, Type.string, ">="));
		condition.functionArgs.add(arg(3, FunctionArgType.Const, Type.integer, Integer.toString(lowerBound)));
		condition.functionArgs.add(arg(4, FunctionArgType.Attribute, Type.string, name));
		condition.functionArgs.add(arg(5, FunctionArgType.Const, Type.string, upperOperator));
		condition.functionArgs.add(arg(6, FunctionArgType.Const, Type.integer, Integer.toString(upperBound)));

		JsonTerm action = new JsonTerm();
		action.termNr = 2;
		action.function = "function";
		action.functionArgs = new ArrayList<>();
		action.functionArgs.add(arg(1, FunctionArgType.Const, Type.string, "MX+N"));
		// Locale.ROOT: MX+N reads its numbers with a decimal point
		action.functionArgs.add(arg(2, FunctionArgType.Const, Type.string,
				String.format(Locale.ROOT, "%s;1.00;%d.00", name, step)));

		JsonEventInfluencesAttributeDescription entry = new JsonEventInfluencesAttributeDescription();
		entry.attribute = name;
		entry.orderNr = orderNr;
		entry.term = new ArrayList<>();
		entry.term.add(condition);
		entry.term.add(action);
		return entry;
	}

	private static JsonFunctionArg arg(int faNr, FunctionArgType argType, Type valueType, String value) {
		JsonFunctionArg arg = new JsonFunctionArg();
		arg.faNr = faNr;
		arg.type = argType.toString();
		arg.value = new JsonValue();
		arg.value.type = valueType == Type.integer ? "integer" : "string";
		arg.value.name = "";
		arg.value.value = value;
		return arg;
	}
}
