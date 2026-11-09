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
package org.socialworld.datasource.pool;

import java.util.Arrays;

import org.socialworld.datasource.tablesPool.TableGaussAAACM;

/**
 * The mapping from the two drawn Gaussian indices (attribute array and matrix)
 * to the Gaussian index of the matrix that fits the attribute array.
 * It is loaded once from swgauss_aa_acm. Without an entry the drawn index stays as it is.
 */
public class GaussPoolAAACMMapping {

	private static final int CAPACITY_AA = GaussPoolAttributeArray.CAPACITY_GPAA_ARRAY;
	private static final int CAPACITY_ACM = GaussPoolAttributeCalculatorMatrix.CAPACITY_GPACM_ARRAY;
	private static final short NO_ENTRY = Short.MIN_VALUE;

	private static GaussPoolAAACMMapping instance;

	private final short[][] matrixIndices = new short[2 * CAPACITY_AA - 1][2 * CAPACITY_ACM - 1];

	/** An empty mapping. */
	GaussPoolAAACMMapping() {
		for (short[] row : matrixIndices) {
			Arrays.fill(row, NO_ENTRY);
		}
	}

	public static GaussPoolAAACMMapping getInstance() {
		if (instance == null) {
			instance = new GaussPoolAAACMMapping();
			instance.initialize();
		}
		return instance;
	}

	/**
	 * @return the Gaussian index of the matrix to use. mapGaussToIndex can give exactly
	 *         the capacity; such an index counts as the outermost place.
	 */
	public int getMatrixIndex(int gaussIndexAA, int gaussIndexACMDrawn) {
		int drawn = limit(gaussIndexACMDrawn, CAPACITY_ACM);
		short index = matrixIndices[limit(gaussIndexAA, CAPACITY_AA) + CAPACITY_AA - 1][drawn + CAPACITY_ACM - 1];
		return index == NO_ENTRY ? drawn : index;
	}

	void setMatrixIndex(int gaussIndexAA, int gaussIndexACMDrawn, int gaussIndexACM) {
		matrixIndices[limit(gaussIndexAA, CAPACITY_AA) + CAPACITY_AA - 1]
				[limit(gaussIndexACMDrawn, CAPACITY_ACM) + CAPACITY_ACM - 1] = (short) gaussIndexACM;
	}

	private static int limit(int index, int capacity) {
		return Math.max(-capacity + 1, Math.min(capacity - 1, index));
	}

	private void initialize() {
		int countLoaded = 0;

		try {
			countLoaded = loadFromDB();
		}
		catch (RuntimeException e) {
			e.printStackTrace();
		}

		if (countLoaded == 0) {
			System.out.println("GaussPoolAAACMMapping: no mapping from the database, the drawn matrix index stays as it is");
		}
	}

	private int loadFromDB() {
		TableGaussAAACM table = new TableGaussAAACM();

		table.select(table.SELECT_ALL_COLUMNS, "", "");
		int rowCount = table.rowCount();

		for (int row = 0; row < rowCount; row++) {
			setMatrixIndex(table.getGaussIndexAA(row), table.getGaussIndexACMDrawn(row), table.getGaussIndexACM(row));
		}
		return Math.max(0, rowCount);
	}
}
