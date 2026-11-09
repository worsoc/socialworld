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

import java.util.List;
import java.util.Map;

import org.socialworld.attributes.Attribute;

/**
 * Where the DCT reads the stored attribute arrays and matrices from.
 * Nothing is changed. The tool knows only this interface, so the tests can run without the database.
 */
public interface AttributeDataReader {

	/**
	 * A stored matrix. Row and column are attribute indices, the row is the source, the column the target.
	 */
	final class StoredMatrix {

		private static final int N = Attribute.NUMBER_OF_ATTRIBUTES;

		private final int funcID;
		private final float[] shares;
		private final int[] functions;

		StoredMatrix(int funcID, float[] shares, int[] functions) {
			if (shares.length != N * N || functions.length != N * N) {
				throw new IllegalArgumentException("func_id " + funcID + " needs " + (N * N) + " cells");
			}
			this.funcID = funcID;
			this.shares = shares.clone();
			this.functions = functions.clone();
		}

		public int getFuncID() { return funcID; }
		public float getShare(int row, int column) { return shares[row * N + column]; }
		public int getFunction(int row, int column) { return functions[row * N + column]; }
	}

	/**
	 * One row of the mapping from the drawn indices to the matrix that is really used.
	 */
	final class MappingEntry {

		private final int gaussIndexAA;
		private final int gaussIndexACMDrawn;
		private final int gaussIndexACM;

		MappingEntry(int gaussIndexAA, int gaussIndexACMDrawn, int gaussIndexACM) {
			this.gaussIndexAA = gaussIndexAA;
			this.gaussIndexACMDrawn = gaussIndexACMDrawn;
			this.gaussIndexACM = gaussIndexACM;
		}

		public int getGaussIndexAA() { return gaussIndexAA; }
		public int getGaussIndexACMDrawn() { return gaussIndexACMDrawn; }
		public int getGaussIndexACM() { return gaussIndexACM; }
	}

	/**
	 * @return the attribute values by gauss_index, in the order of the attribute index
	 */
	Map<Integer, int[]> readArrays();

	/**
	 * @return the func_id of the matrix by gauss_index
	 */
	Map<Integer, Integer> readMatrixIndices();

	/**
	 * @return the matrices that have a gauss_index, by func_id
	 */
	Map<Integer, StoredMatrix> readMatrices();

	/**
	 * @return all rows of the mapping
	 */
	List<MappingEntry> readMapping();
}
