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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.socialworld.tools.dct.AttributeDataReader.MappingEntry;
import org.socialworld.tools.dct.AttributeDataReader.StoredMatrix;

/**
 * The stored attribute arrays and matrices, assigned to the characters.
 *
 * The tables hold no character number. The character of an array or of a gauss index of the
 * mapping is abs(gauss_index) + 1. Data that does not fit together is reported by an exception,
 * so that nothing wrong is shown.
 */
public final class AttributeCharacterOverview {

	/** One stored attribute array. */
	public static final class StoredArray {

		private final int gaussIndex;
		private final int[] values;

		StoredArray(int gaussIndex, int[] values) {
			this.gaussIndex = gaussIndex;
			this.values = values.clone();
		}

		public int getGaussIndex() { return gaussIndex; }
		public int[] getValues() { return values.clone(); }
	}

	/** One character with its arrays and with its matrices from typical to unusual. */
	public static final class Entry {

		private final AttributeCharacter character;
		private final List<StoredArray> arrays;
		private final List<StoredMatrix> matrices;

		Entry(AttributeCharacter character, List<StoredArray> arrays, List<StoredMatrix> matrices) {
			this.character = character;
			this.arrays = Collections.unmodifiableList(arrays);
			this.matrices = Collections.unmodifiableList(matrices);
		}

		public AttributeCharacter getCharacter() { return character; }
		public List<StoredArray> getArrays() { return arrays; }
		public List<StoredMatrix> getMatrices() { return matrices; }
	}

	private final List<Entry> entries;

	private AttributeCharacterOverview(List<Entry> entries) {
		this.entries = Collections.unmodifiableList(entries);
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public static AttributeCharacterOverview read(AttributeCharacterCatalog catalog, AttributeDataReader reader) {
		Map<Integer, List<StoredArray>> arrays = arraysByCharacter(catalog, reader.readArrays());
		Map<Integer, List<StoredMatrix>> matrices = matricesByCharacter(catalog, reader);

		List<Entry> entries = new ArrayList<>();
		for (AttributeCharacter character : catalog.getCharacters()) {
			List<StoredArray> arraysOfCharacter = arrays.get(character.getNumber());
			List<StoredMatrix> matricesOfCharacter = matrices.get(character.getNumber());
			if (arraysOfCharacter == null) throw new IllegalStateException("no array for character " + character.getNumber());
			if (matricesOfCharacter == null) throw new IllegalStateException("no matrix for character " + character.getNumber());
			entries.add(new Entry(character, arraysOfCharacter, matricesOfCharacter));
		}
		return new AttributeCharacterOverview(entries);
	}

	private static int characterOf(AttributeCharacterCatalog catalog, int gaussIndex) {
		int number = Math.abs(gaussIndex) + 1;
		if (number > catalog.size()) {
			throw new IllegalStateException("no character for gauss index " + gaussIndex);
		}
		return number;
	}

	/** The array with the positive gauss index comes first. */
	private static Map<Integer, List<StoredArray>> arraysByCharacter(AttributeCharacterCatalog catalog, Map<Integer, int[]> stored) {
		Map<Integer, List<StoredArray>> arrays = new TreeMap<>();
		for (Map.Entry<Integer, int[]> entry : stored.entrySet()) {
			arrays.computeIfAbsent(characterOf(catalog, entry.getKey()), key -> new ArrayList<>())
					.add(new StoredArray(entry.getKey(), entry.getValue()));
		}
		for (List<StoredArray> list : arrays.values()) {
			list.sort(Comparator.comparingInt(StoredArray::getGaussIndex).reversed());
		}
		return arrays;
	}

	/**
	 * A small distance of the drawn index from 0 leads to a typical matrix, the edge to an unusual one,
	 * so the smallest distance that leads to a matrix gives its place in the list.
	 */
	private static Map<Integer, List<StoredMatrix>> matricesByCharacter(AttributeCharacterCatalog catalog, AttributeDataReader reader) {
		Map<Integer, Integer> indices = reader.readMatrixIndices();
		Map<Integer, StoredMatrix> stored = reader.readMatrices();

		Map<Integer, Integer> characterOfMatrix = new HashMap<>();
		Map<Integer, Integer> smallestDistance = new HashMap<>();

		for (MappingEntry entry : reader.readMapping()) {
			int character = characterOf(catalog, entry.getGaussIndexAA());
			Integer funcID = indices.get(entry.getGaussIndexACM());
			if (funcID == null) throw new IllegalStateException("no matrix at gauss index " + entry.getGaussIndexACM());
			if (!stored.containsKey(funcID)) throw new IllegalStateException("no cells for func_id " + funcID);

			Integer known = characterOfMatrix.putIfAbsent(funcID, character);
			if (known != null && known != character) {
				throw new IllegalStateException("func_id " + funcID + " belongs to the characters " + known + " and " + character);
			}
			smallestDistance.merge(funcID, Math.abs(entry.getGaussIndexACMDrawn()), Math::min);
		}

		Map<Integer, List<StoredMatrix>> matrices = new TreeMap<>();
		for (Map.Entry<Integer, Integer> entry : characterOfMatrix.entrySet()) {
			matrices.computeIfAbsent(entry.getValue(), key -> new ArrayList<>()).add(stored.get(entry.getKey()));
		}
		for (List<StoredMatrix> list : matrices.values()) {
			list.sort(Comparator.<StoredMatrix>comparingInt(matrix -> smallestDistance.get(matrix.getFuncID()))
					.thenComparingInt(StoredMatrix::getFuncID));
		}
		return matrices;
	}
}
