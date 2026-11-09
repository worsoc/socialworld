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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.socialworld.attributes.Attribute;
import org.socialworld.datasource.pool.GaussPoolAttributeArray;
import org.socialworld.datasource.pool.GaussPoolAttributeCalculatorMatrix;
import org.socialworld.tools.dct.AttributeMatrixGenerator.GeneratedMatrix;

/**
 * The mapping from the two drawn Gaussian indices to the matrix that fits the attribute array.
 * Character no. n has the attribute arrays with the Gaussian indices +(n-1) and -(n-1).
 * Near 0 the drawn matrix index gives the most typical variant of the character,
 * at the edge the most unusual one.
 */
final class AttributeMatrixMapping {

	static final class Entry {
		final int gaussIndexAA;
		final int gaussIndexACMDrawn;
		final int gaussIndexACM;

		Entry(int gaussIndexAA, int gaussIndexACMDrawn, int gaussIndexACM) {
			this.gaussIndexAA = gaussIndexAA;
			this.gaussIndexACMDrawn = gaussIndexACMDrawn;
			this.gaussIndexACM = gaussIndexACM;
		}
	}

	private AttributeMatrixMapping() {
	}

	static List<Entry> generate(List<GeneratedMatrix> matrices) {
		Map<Integer, List<GeneratedMatrix>> variants = new TreeMap<>();
		for (GeneratedMatrix matrix : matrices) {
			variants.computeIfAbsent(matrix.getCharacter().getNumber(), key -> new ArrayList<>()).add(matrix);
		}
		for (List<GeneratedMatrix> list : variants.values()) {
			list.sort(Comparator.comparingLong(AttributeMatrixMapping::distanceFromBandCentres)
					.thenComparingInt(GeneratedMatrix::getVariant));
		}

		int capacityAA = GaussPoolAttributeArray.CAPACITY_GPAA_ARRAY;
		int capacityACM = GaussPoolAttributeCalculatorMatrix.CAPACITY_GPACM_ARRAY;

		List<Entry> entries = new ArrayList<>();
		for (int aa = -capacityAA + 1; aa < capacityAA; aa++) {
			List<GeneratedMatrix> list = variants.get(Math.abs(aa) + 1);
			if (list == null) {
				throw new IllegalArgumentException("no matrix for the character of gauss index " + aa);
			}
			for (int drawn = -capacityACM + 1; drawn < capacityACM; drawn++) {
				int position = Math.min(list.size() - 1, Math.abs(drawn) * list.size() / capacityACM);
				entries.add(new Entry(aa, drawn, list.get(position).getGaussIndex()));
			}
		}
		return entries;
	}

	private static long distanceFromBandCentres(GeneratedMatrix matrix) {
		long distance = 0;
		for (Attribute attribute : Attribute.values()) {
			if (attribute == Attribute.ignore || attribute == Attribute.hunger || attribute == Attribute.tiredness) continue;
			AttributeLevel level = matrix.getCharacter().getLevel(attribute.getIndex());
			distance += Math.abs(matrix.getTargets()[attribute.getIndex()] - (level.getLowest() + level.getHighest()) / 2);
		}
		return distance;
	}
}
