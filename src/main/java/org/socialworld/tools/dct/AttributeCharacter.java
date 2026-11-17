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

import org.socialworld.attributes.Attribute;

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

	/** The need of the reference character (farmer): 8 hours of sleep, 3 meals (EID concept 13.4). */
	public static final float DEFAULT_SLEEP_HOURS = 8f;
	public static final int DEFAULT_MEALS_PER_DAY = 3;
	public static final float MIN_SLEEP_HOURS = 6f;
	public static final float MAX_SLEEP_HOURS = 10f;
	public static final int MIN_MEALS_PER_DAY = 1;
	public static final int MAX_MEALS_PER_DAY = 5;

	public AttributeCharacter(int number, String name, String sketch, AttributeLevel[] levels) {
		this(number, name, sketch, levels, DEFAULT_SLEEP_HOURS, DEFAULT_MEALS_PER_DAY);
	}

	/**
	 * @param sleepHours hours of sleep the character needs, 6.0 to 10.0 in half hours
	 * @param mealsPerDay meals the character needs, 1 to 5
	 */
	public AttributeCharacter(int number, String name, String sketch, AttributeLevel[] levels,
			float sleepHours, int mealsPerDay) {
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
		this.number = number;
		this.name = name;
		this.sketch = sketch;
		this.levels = levels.clone();
		this.sleepHours = sleepHours;
		this.mealsPerDay = mealsPerDay;
	}

	/** Hours of sleep the character needs (the body's need, not how it lives). */
	public float getSleepHours() {
		return sleepHours;
	}

	/** Meals the character needs per day (the body's need, not how it lives). */
	public int getMealsPerDay() {
		return mealsPerDay;
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
