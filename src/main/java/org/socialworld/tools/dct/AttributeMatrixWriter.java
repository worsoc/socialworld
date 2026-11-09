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

import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.Type;
import org.socialworld.tools.dct.AttributeMatrixGenerator.GeneratedMatrix;
import org.socialworld.tools.dct.AttributeMatrixMapping.Entry;

/**
 * Exchanges generated matrices and the mapping record by record. swgauss_aa_acm refers to
 * swgauss_acm and swgauss_acm to swpool_matrix, so the order is, each step for all records:
 * delete mapping, delete gauss rows, delete cells, delete matrices,
 * insert matrices, insert cells, insert gauss rows, insert mapping.
 * Rows and columns are counted from 1 in the table.
 */
final class AttributeMatrixWriter {

	private static final int N = Attribute.NUMBER_OF_ATTRIBUTES;

	private AttributeMatrixWriter() {
	}

	static void write(List<GeneratedMatrix> matrices, List<Entry> mapping, AttributeMatrixStore store) {
		for (Entry entry : mapping) {
			store.deleteMapping(entry.gaussIndexAA, entry.gaussIndexACMDrawn);
		}
		for (GeneratedMatrix matrix : matrices) {
			store.deleteGaussIndex(matrix.getGaussIndex());
		}
		for (GeneratedMatrix matrix : matrices) {
			for (int row = 1; row <= N; row++) {
				for (int column = 1; column <= N; column++) {
					store.deleteCell(matrix.getFuncID(), row, column);
				}
			}
		}
		for (GeneratedMatrix matrix : matrices) {
			store.deleteMatrix(matrix.getFuncID());
		}
		for (GeneratedMatrix matrix : matrices) {
			store.insertMatrix(matrix.getFuncID(), N, N, Type.floatingpoint.getIndex(), Type.floatingpoint.getIndex());
		}
		for (GeneratedMatrix matrix : matrices) {
			for (int row = 1; row <= N; row++) {
				for (int column = 1; column <= N; column++) {
					store.insertCell(matrix.getFuncID(), row, column, matrix.getShare(row - 1, column - 1),
							matrix.getFunction(row - 1, column - 1), 0f, 0);
				}
			}
		}
		for (GeneratedMatrix matrix : matrices) {
			store.insertGaussIndex(matrix.getGaussIndex(), matrix.getFuncID());
		}
		for (Entry entry : mapping) {
			store.insertMapping(entry.gaussIndexAA, entry.gaussIndexACMDrawn, entry.gaussIndexACM);
		}
	}
}
