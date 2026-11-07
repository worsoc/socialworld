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

import org.socialworld.attributes.AttributeArray;

/**
 * The level of an attribute in a character and the band of values it stands for.
 * The bands overlap on purpose. See documentation/attribute_charaktere.md.
 */
public enum AttributeLevel {

	// bands in attribute values 0..9999 (displayed 0..99)
	extremelyLow("---", 0, 1000, 1),
	veryLow("--", 800, 2800, 3),
	low("-", 2500, 4200, 3),
	average("o", 3800, 6200, 3),
	high("+", 5800, 7500, 3),
	veryHigh("++", 7200, 9200, 3),
	extremelyHigh("+++", 9000, AttributeArray.ATTRIBUTE_VALUE_MAX, 1),
	open("~", 2000, 8000, 3);

	private final String code;
	private final int lowest;
	private final int highest;
	private final int countLetters;

	private AttributeLevel(String code, int lowest, int highest, int countLetters) {
		this.code = code;
		this.lowest = lowest;
		this.highest = highest;
		this.countLetters = countLetters;
	}

	public static AttributeLevel fromCode(String code) {
		for (AttributeLevel level : values()) {
			if (level.code.equals(code)) return level;
		}
		throw new IllegalArgumentException("unknown attribute level '" + code + "'");
	}

	public String getCode() {
		return code;
	}

	public int getLowest() {
		return lowest;
	}

	public int getHighest() {
		return highest;
	}

	/**
	 * How many letters of the text are averaged for one value of this level.
	 */
	public int getCountLetters() {
		return countLetters;
	}

	/**
	 * Places a share between 0 and 1 inside the band.
	 */
	public int valueFor(double share) {
		return lowest + (int) Math.round((highest - lowest) * share);
	}

	/**
	 * Draws a value inside the band from the text die.
	 */
	public int draw(TextDie die) {
		double sum = 0;
		for (int i = 0; i < countLetters; i++) {
			sum += die.next();
		}
		return valueFor(sum / countLetters);
	}
}
