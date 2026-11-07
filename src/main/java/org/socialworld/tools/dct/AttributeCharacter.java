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

	public AttributeCharacter(int number, String name, String sketch, AttributeLevel[] levels) {
		if (levels.length != Attribute.NUMBER_OF_ATTRIBUTES) {
			throw new IllegalArgumentException("character " + number + " has " + levels.length
					+ " levels instead of " + Attribute.NUMBER_OF_ATTRIBUTES);
		}
		this.number = number;
		this.name = name;
		this.sketch = sketch;
		this.levels = levels.clone();
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
