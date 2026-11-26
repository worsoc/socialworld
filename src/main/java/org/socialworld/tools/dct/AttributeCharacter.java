/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
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

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.BodyBuild;

/**
 * A sketched character: one level per attribute, in the order of the enum Attribute.
 */
public class AttributeCharacter {

	private final int number;
	private final String name;
	private final String sketch;
	private final AttributeLevel[] levels;
	private final float sleepHours;
	private final int mealsPerDay;
	private final int tiredWake;
	private final int tiredBed;
	private final int hungerFull;
	private final int hungerEat;
	private final BodyBuild build;

	/** The need of the reference character (farmer): 8 hours of sleep, 3 meals (EID concept 13.4). */
	public static final float DEFAULT_SLEEP_HOURS = 8f;
	public static final int DEFAULT_MEALS_PER_DAY = 3;
	public static final float MIN_SLEEP_HOURS = 6f;
	public static final float MAX_SLEEP_HOURS = 10f;
	public static final int MIN_MEALS_PER_DAY = 1;
	public static final int MAX_MEALS_PER_DAY = 5;

	/**
	 * The ideal of the reference character (farmer), displayed values 0..99 (EID concept 14):
	 * tiredness 20 after his sleep and 70 when he goes to bed, hunger 40 after a meal and 60 when he goes to eat.
	 */
	public static final int DEFAULT_TIRED_WAKE = 20;
	public static final int DEFAULT_TIRED_BED = 70;
	public static final int DEFAULT_HUNGER_FULL = 40;
	public static final int DEFAULT_HUNGER_EAT = 60;
	public static final int MAX_IDEAL = 99;
	/** Displayed value 0..99 times this is the attribute value 0..9999. */
	private static final int IDEAL_SCALE = 100;

	public AttributeCharacter(int number, String name, String sketch, AttributeLevel[] levels) {
		this(number, name, sketch, levels, DEFAULT_SLEEP_HOURS, DEFAULT_MEALS_PER_DAY);
	}

	/**
	 * @param sleepHours hours of sleep the character needs, 6.0 to 10.0 in half hours
	 * @param mealsPerDay meals the character needs, 1 to 5
	 */
	public AttributeCharacter(int number, String name, String sketch, AttributeLevel[] levels,
			float sleepHours, int mealsPerDay) {
		this(number, name, sketch, levels, sleepHours, mealsPerDay,
				DEFAULT_TIRED_WAKE, DEFAULT_TIRED_BED, DEFAULT_HUNGER_FULL, DEFAULT_HUNGER_EAT);
	}

	/**
	 * @param sleepHours hours of sleep the character needs, 6.0 to 10.0 in half hours
	 * @param mealsPerDay meals the character needs, 1 to 5
	 * @param tiredWake ideal tiredness after sleeping sleepHours, displayed 0..99
	 * @param tiredBed ideal tiredness when going to bed, displayed 0..99, above tiredWake
	 * @param hungerFull ideal hunger after a meal, displayed 0..99
	 * @param hungerEat ideal hunger when going to eat, displayed 0..99, above hungerFull
	 */
	public AttributeCharacter(int number, String name, String sketch, AttributeLevel[] levels,
			float sleepHours, int mealsPerDay, int tiredWake, int tiredBed, int hungerFull, int hungerEat) {
		this(number, name, sketch, levels, sleepHours, mealsPerDay, tiredWake, tiredBed, hungerFull, hungerEat,
				BodyBuild.DEFAULT);
	}

	/**
	 * @param build the body build, which chooses the composition of a human (body build concept B1)
	 * @see #AttributeCharacter(int, String, String, AttributeLevel[], float, int, int, int, int, int)
	 */
	public AttributeCharacter(int number, String name, String sketch, AttributeLevel[] levels,
			float sleepHours, int mealsPerDay, int tiredWake, int tiredBed, int hungerFull, int hungerEat,
			BodyBuild build) {
		if (build == null) throw new IllegalArgumentException("character " + number + " has no body build");
		if (levels.length != Attribute.NUMBER_OF_ATTRIBUTES) {
			throw new IllegalArgumentException("character " + number + " has " + levels.length
					+ " levels instead of " + Attribute.NUMBER_OF_ATTRIBUTES);
		}
		if (sleepHours < MIN_SLEEP_HOURS || sleepHours > MAX_SLEEP_HOURS || sleepHours * 2 != Math.round(sleepHours * 2)) {
			throw new IllegalArgumentException("character " + number + ": sleepHours " + sleepHours
					+ " is not between " + MIN_SLEEP_HOURS + " and " + MAX_SLEEP_HOURS + " in half hours");
		}
		if (mealsPerDay < MIN_MEALS_PER_DAY || mealsPerDay > MAX_MEALS_PER_DAY) {
			throw new IllegalArgumentException("character " + number + ": mealsPerDay " + mealsPerDay
					+ " is not between " + MIN_MEALS_PER_DAY + " and " + MAX_MEALS_PER_DAY);
		}
		checkIdeal(number, "tiredness", tiredWake, tiredBed);
		checkIdeal(number, "hunger", hungerFull, hungerEat);
		this.number = number;
		this.name = name;
		this.sketch = sketch;
		this.levels = levels.clone();
		this.sleepHours = sleepHours;
		this.mealsPerDay = mealsPerDay;
		this.tiredWake = tiredWake;
		this.tiredBed = tiredBed;
		this.hungerFull = hungerFull;
		this.hungerEat = hungerEat;
		this.build = build;
	}

	private static void checkIdeal(int number, String attribute, int low, int high) {
		if (low < 0 || high > MAX_IDEAL || low >= high) {
			throw new IllegalArgumentException("character " + number + ": ideal of " + attribute + " " + low + "/" + high
					+ " is not 0 <= low < high <= " + MAX_IDEAL);
		}
	}

	/**
	 * Hunger and tiredness have no target in the attribute matrix, they rise with time. For them the
	 * ideal of the character takes the place of the band of its level (EID concept 14.5, 14.8).
	 */
	public static boolean hasIdeal(Attribute attribute) {
		return attribute == Attribute.tiredness || attribute == Attribute.hunger;
	}

	/** @return the lower end of the ideal as attribute value 0..9999: tiredness after sleep, hunger after a meal */
	public int getIdealLowest(Attribute attribute) {
		if (attribute == Attribute.tiredness) return tiredWake * IDEAL_SCALE;
		if (attribute == Attribute.hunger) return hungerFull * IDEAL_SCALE;
		throw new IllegalArgumentException(attribute + " has no ideal");
	}

	/** @return the upper end of the ideal as attribute value 0..9999: tiredness at bedtime, hunger when going to eat */
	public int getIdealHighest(Attribute attribute) {
		if (attribute == Attribute.tiredness) return tiredBed * IDEAL_SCALE;
		if (attribute == Attribute.hunger) return hungerEat * IDEAL_SCALE;
		throw new IllegalArgumentException(attribute + " has no ideal");
	}

	/** Hours of sleep the character needs (the body's need, not how it lives). */
	public float getSleepHours() {
		return sleepHours;
	}

	/** Meals the character needs per day (the body's need, not how it lives). */
	public int getMealsPerDay() {
		return mealsPerDay;
	}

	/** The body build (column build of characters.csv); it chooses the composition of a human. */
	public BodyBuild getBuild() {
		return build;
	}

	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public String getSketch() {
		return sketch;
	}

	public AttributeLevel getLevel(int attributeIndex) {
		return levels[attributeIndex];
	}

	/**
	 * How many shares of the text die one array of this character uses.
	 */
	public int countShares() {
		int count = 0;
		for (AttributeLevel level : levels) {
			count += level.getCountLetters();
		}
		return count;
	}

	public int[] draw(TextDie die) {
		int[] values = new int[levels.length];
		for (int i = 0; i < levels.length; i++) {
			values[i] = levels[i].draw(die);
		}
		return values;
	}
}
