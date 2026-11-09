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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.socialworld.attributes.Attribute;
import org.socialworld.datasource.tablesPool.TableGaussAA;
import org.socialworld.datasource.tablesPool.TableGaussAAACM;
import org.socialworld.datasource.tablesPool.TableGaussACM;
import org.socialworld.datasource.tablesPool.TablePoolAttribute;
import org.socialworld.datasource.tablesPool.TablePoolMatrixRowCol;

/**
 * Reads the stored attribute arrays and matrices with the table classes. Only selects, nothing is changed.
 * Incomplete data is reported by an exception, so that nothing wrong is shown.
 */
public class TablePoolAttributeDataReader implements AttributeDataReader {

	private static final int N = Attribute.NUMBER_OF_ATTRIBUTES;
	private static final int NOT_READ = -1;

	@Override
	public Map<Integer, int[]> readArrays() {
		Map<Integer, int[]> valuesByID = readAttributeValues();

		TableGaussAA tableGauss = new TableGaussAA();
		tableGauss.select(tableGauss.SELECT_ALL_COLUMNS, "", "ORDER BY gauss_index");

		Map<Integer, int[]> arrays = new TreeMap<>();
		for (int i = 0; i < tableGauss.rowCount(); i++) {
			int[] values = valuesByID.get(tableGauss.getAttributeArrayID(i));
			if (values == null) {
				throw new IllegalStateException("no attributes for aa_id " + tableGauss.getAttributeArrayID(i));
			}
			arrays.put(tableGauss.getGaussIndex(i), values.clone());
		}
		return arrays;
	}

	private Map<Integer, int[]> readAttributeValues() {
		TablePoolAttribute table = new TablePoolAttribute();
		table.select(table.SELECT_ALL_COLUMNS, "WHERE aa_id IN (SELECT aa_id FROM swgauss_aa)", "");

		Map<Integer, int[]> valuesByID = new HashMap<>();
		for (int i = 0; i < table.rowCount(); i++) {
			int index = table.getAttribNr(i) - 1;
			if (index < 0 || index >= N) {
				throw new IllegalStateException("attrib_nr " + table.getAttribNr(i) + " of aa_id " + table.getAttributeArrayID(i));
			}
			valuesByID.computeIfAbsent(table.getAttributeArrayID(i), key -> newUnreadValues())[index] = table.getValue(i);
		}

		for (Map.Entry<Integer, int[]> entry : valuesByID.entrySet()) {
			if (Arrays.stream(entry.getValue()).anyMatch(value -> value == NOT_READ)) {
				throw new IllegalStateException("aa_id " + entry.getKey() + " has not all attributes");
			}
		}
		return valuesByID;
	}

	private static int[] newUnreadValues() {
		int[] values = new int[N];
		Arrays.fill(values, NOT_READ);
		return values;
	}

	@Override
	public Map<Integer, Integer> readMatrixIndices() {
		TableGaussACM table = new TableGaussACM();
		table.select(table.SELECT_ALL_COLUMNS, "", "ORDER BY gauss_index");

		Map<Integer, Integer> indices = new TreeMap<>();
		for (int i = 0; i < table.rowCount(); i++) {
			indices.put(table.getGaussIndex(i), table.getFunctionID(i));
		}
		return indices;
	}

	@Override
	public Map<Integer, StoredMatrix> readMatrices() {
		TablePoolMatrixRowCol table = new TablePoolMatrixRowCol();
		table.select(table.SELECT_ALL_COLUMNS, "WHERE func_id IN (SELECT func_id FROM swgauss_acm)", "ORDER BY func_id, `row`, col");

		Map<Integer, float[]> shares = new TreeMap<>();
		Map<Integer, int[]> functions = new HashMap<>();
		Map<Integer, Integer> countCells = new HashMap<>();

		for (int i = 0; i < table.rowCount(); i++) {
			int funcID = table.getFunctionID(i);
			int row = table.getRow(i) - 1;
			int column = table.getCol(i) - 1;
			if (row < 0 || row >= N || column < 0 || column >= N) {
				throw new IllegalStateException("cell " + table.getRow(i) + "/" + table.getCol(i) + " of func_id " + funcID);
			}
			shares.computeIfAbsent(funcID, key -> new float[N * N])[row * N + column] = table.getShare(i);
			functions.computeIfAbsent(funcID, key -> new int[N * N])[row * N + column] = table.getFunction(i);
			countCells.merge(funcID, 1, Integer::sum);
		}

		Map<Integer, StoredMatrix> matrices = new TreeMap<>();
		for (Map.Entry<Integer, float[]> entry : shares.entrySet()) {
			int funcID = entry.getKey();
			if (countCells.get(funcID) != N * N) {
				throw new IllegalStateException("func_id " + funcID + " has " + countCells.get(funcID) + " cells");
			}
			matrices.put(funcID, new StoredMatrix(funcID, entry.getValue(), functions.get(funcID)));
		}
		return matrices;
	}

	@Override
	public List<MappingEntry> readMapping() {
		TableGaussAAACM table = new TableGaussAAACM();
		table.select(table.SELECT_ALL_COLUMNS, "", "ORDER BY gauss_index_aa, gauss_index_acm_drawn");

		List<MappingEntry> mapping = new ArrayList<>(table.rowCount());
		for (int i = 0; i < table.rowCount(); i++) {
			mapping.add(new MappingEntry(table.getGaussIndexAA(i), table.getGaussIndexACMDrawn(i), table.getGaussIndexACM(i)));
		}
		return mapping;
	}
}
