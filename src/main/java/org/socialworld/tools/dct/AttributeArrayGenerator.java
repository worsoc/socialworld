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

import org.socialworld.datasource.pool.AttributeArrayPool;
import org.socialworld.datasource.pool.GaussPoolAttributeArray;

/**
 * Draws the attribute arrays for all characters of a catalog from a text die.
 * Character no. 1 gets gauss_index 0, character no. n gets +(n-1) and -(n-1),
 * each with its own draw. The aa_ids are counted up from a start id in this order.
 */
public class AttributeArrayGenerator {

	public static class GeneratedArray {
		private final AttributeCharacter character;
		private final int gaussIndex;
		private final int aaID;
		private final int[] values;

		GeneratedArray(AttributeCharacter character, int gaussIndex, int aaID, int[] values) {
			this.character = character;
			this.gaussIndex = gaussIndex;
			this.aaID = aaID;
			this.values = values;
		}

		public AttributeCharacter getCharacter() {
			return character;
		}

		public int getGaussIndex() {
			return gaussIndex;
		}

		public int getAaID() {
			return aaID;
		}

		public int[] getValues() {
			return values.clone();
		}

		/**
		 * The same character, gauss_index and aa_id with other values, e.g. adjusted by hand.
		 */
		GeneratedArray withValues(int[] newValues) {
			if (newValues.length != values.length) {
				throw new IllegalArgumentException(newValues.length + " values instead of " + values.length);
			}
			return new GeneratedArray(character, gaussIndex, aaID, newValues.clone());
		}

		/**
		 * The tuple as the DCT attribute list and AttributeArray(String, int) know it.
		 */
		public String getTuple() {
			StringBuilder tuple = new StringBuilder("(");
			for (int i = 0; i < values.length; i++) {
				if (i > 0) tuple.append(",");
				tuple.append(values[i]);
			}
			return tuple.append(")").toString();
		}
	}

	private AttributeArrayGenerator() {
	}

	/**
	 * How many arrays the catalog gives: one for the first character, two for each other.
	 */
	public static int countArrays(AttributeCharacterCatalog catalog) {
		return Math.max(0, 2 * catalog.size() - 1);
	}

	/**
	 * How many shares of the text die all arrays use together.
	 */
	public static int countShares(AttributeCharacterCatalog catalog) {
		int count = 0;
		for (AttributeCharacter character : catalog.getCharacters()) {
			count += character.countShares() * (character.getNumber() == 1 ? 1 : 2);
		}
		return count;
	}

	/**
	 * How many letters the text must have at least.
	 */
	public static int countLettersNeeded(AttributeCharacterCatalog catalog) {
		return (countShares(catalog) - 1) * TextDie.STEP + 1;
	}

	public static List<GeneratedArray> generate(AttributeCharacterCatalog catalog, TextDie die, int startAaID) {

		if (catalog.size() > GaussPoolAttributeArray.CAPACITY_GPAA_ARRAY) {
			throw new IllegalArgumentException("the gauss pool has room for "
					+ GaussPoolAttributeArray.CAPACITY_GPAA_ARRAY + " characters, not " + catalog.size());
		}
		if (startAaID < 1 || startAaID + countArrays(catalog) > AttributeArrayPool.CAPACITY_AAP_ARRAY) {
			throw new IllegalArgumentException("aa_ids from " + startAaID + " do not fit into 1.."
					+ (AttributeArrayPool.CAPACITY_AAP_ARRAY - 1));
		}
		if (die.countRemaining() < countShares(catalog)) {
			throw new IllegalArgumentException("the text gives " + die.countRemaining() + " letters of "
					+ countShares(catalog) + " needed (every " + TextDie.STEP + ". letter counts)");
		}

		List<GeneratedArray> result = new ArrayList<>();
		int aaID = startAaID;

		for (AttributeCharacter character : catalog.getCharacters()) {
			int distance = character.getNumber() - 1;

			result.add(new GeneratedArray(character, distance, aaID++, character.draw(die)));
			if (distance > 0) {
				result.add(new GeneratedArray(character, -distance, aaID++, character.draw(die)));
			}
		}
		return result;
	}
}
