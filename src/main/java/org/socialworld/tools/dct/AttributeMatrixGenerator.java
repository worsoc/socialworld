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
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.functions.FunctionByMatrix_Matrix;
import org.socialworld.calculation.functions.FunctionByTable;
import org.socialworld.calculation.functions.FunctionByTable_Type;
import org.socialworld.datasource.pool.GaussPoolAttributeCalculatorMatrix;

/**
 * Generates attribute calculation matrices with a meaning for the characters.
 * See attribute_matrix_characters.md. One step of a matrix is one simulated minute.
 *
 * A matrix variant belongs to one character. Its target values are drawn from the bands
 * of the character and are the wanted fixed point of the matrix.
 */
public final class AttributeMatrixGenerator {

	public static final int FIRST_FUNC_ID = 200;
	private static final int N = Attribute.NUMBER_OF_ATTRIBUTES;
	private static final int MAX = AttributeArray.ATTRIBUTE_VALUE_MAX;

	/** About a standard deviation of the Gaussian index, see CreateSimulationObjects.mapGaussToIndex. */
	private static final double INDEX_PER_SIGMA = 25.0;

	static final Drive HUNGER_DRIVE = Drive.through(480, 5150, 720, 9150);
	static final Drive TIREDNESS_DRIVE = Drive.through(960, 5150, 1440, 9150);

	/** x -> x * growth + constant, through two points reached from 0. */
	static final class Drive {
		final float constant;
		final float growth;

		private Drive(float constant, float growth) {
			this.constant = constant;
			this.growth = growth;
		}

		static Drive through(int step1, double value1, int step2, double value2) {
			double ratio = value2 / value1;
			double low = 1e-7;
			double high = 0.1;
			for (int i = 0; i < 200; i++) {
				double g = (low + high) / 2;
				if ((Math.exp(g * step2) - 1) / (Math.exp(g * step1) - 1) < ratio) low = g;
				else high = g;
			}
			double g = (low + high) / 2;
			return new Drive((float) (value1 * g / (Math.exp(g * step1) - 1)), (float) (1 + g));
		}
	}

	public static final class GeneratedMatrix {
		private final AttributeCharacter character;
		private final int variant;
		private final int funcID;
		private final int gaussIndex;
		private final float[] shares;
		private final int[] functions;
		private final int[] targets;

		GeneratedMatrix(AttributeCharacter character, int variant, int funcID, int gaussIndex,
				float[] shares, int[] functions, int[] targets) {
			this.character = character;
			this.variant = variant;
			this.funcID = funcID;
			this.gaussIndex = gaussIndex;
			this.shares = shares;
			this.functions = functions;
			this.targets = targets;
		}

		public AttributeCharacter getCharacter() { return character; }
		public int getVariant() { return variant; }
		public int getFuncID() { return funcID; }
		public int getGaussIndex() { return gaussIndex; }
		public int[] getTargets() { return targets.clone(); }
		public float getShare(int row, int column) { return shares[row * N + column]; }
		public int getFunction(int row, int column) { return functions[row * N + column]; }

		public FunctionByMatrix_Matrix toMatrix() {
			Value[] shareValues = new Value[N * N];
			Value[] offsetValues = new Value[N * N];
			for (int i = 0; i < N * N; i++) {
				shareValues[i] = new Value(Type.floatingpoint, shares[i]);
				offsetValues[i] = new Value(Type.floatingpoint, 0f);
			}
			FunctionByMatrix_Matrix matrix = new FunctionByMatrix_Matrix(N);
			matrix.setFunctions(functions.clone());
			matrix.setShares(shareValues);
			matrix.setOffsets(offsetValues);
			return matrix;
		}
	}

	private static final Map<FunctionByTable_Type, FunctionByTable> TABLES = new EnumMap<>(FunctionByTable_Type.class);

	private AttributeMatrixGenerator() {
	}

	private static int f(FunctionByTable_Type type, int x) {
		return TABLES.computeIfAbsent(type, FunctionByTable::new).calculate(x);
	}

	private static boolean isState(Attribute attribute) {
		return attribute == Attribute.hunger || attribute == Attribute.tiredness;
	}

	private static float returnRate(Attribute attribute) {
		switch (attribute) {
		case mood: case power: return 0.02f;
		case courage: case curiosity: return 0.005f;
		case morals: case materialism: case spirituality: return 0.002f;
		default: return 0f;
		}
	}

	/**
	 * @return matrices per character, at least 1 each, by the frequency of the character
	 */
	static int[] variantsPerCharacter(int countCharacters, int places) {
		if (places < countCharacters) throw new IllegalArgumentException(places + " places for " + countCharacters + " characters");
		double[] weights = new double[countCharacters];
		double sum = 0;
		for (int i = 0; i < countCharacters; i++) {
			weights[i] = Math.exp(-0.5 * Math.pow(i / INDEX_PER_SIGMA, 2));
			sum += weights[i];
		}
		int extras = places - countCharacters;
		int[] result = new int[countCharacters];
		double[] remainders = new double[countCharacters];
		int given = 0;
		for (int i = 0; i < countCharacters; i++) {
			double exact = extras * weights[i] / sum;
			result[i] = 1 + (int) exact;
			remainders[i] = exact - (int) exact;
			given += (int) exact;
		}
		for (; given < extras; given++) {
			int best = 0;
			for (int i = 1; i < countCharacters; i++) {
				if (remainders[i] > remainders[best]) best = i;
			}
			result[best]++;
			remainders[best] = -1;
		}
		return result;
	}

	/** @return how many shares of the text die all matrices use */
	static int countShares(AttributeCharacterCatalog characters, AttributeCouplingCatalog couplings) {
		int places = 2 * GaussPoolAttributeCalculatorMatrix.CAPACITY_GPACM_ARRAY - 1;
		int[] variants = variantsPerCharacter(characters.size(), places);
		int count = 0;
		for (AttributeCharacter character : characters.getCharacters()) {
			int perVariant = 2 + couplings.strengthsFor(character, 5000).size();
			for (Attribute attribute : Attribute.values()) {
				if (attribute != Attribute.ignore && !isState(attribute)) {
					perVariant += character.getLevel(attribute.getIndex()).getCountLetters();
				}
			}
			count += perVariant * variants[character.getNumber() - 1];
		}
		return count;
	}

	public static List<GeneratedMatrix> generate(AttributeCharacterCatalog characters, AttributeCouplingCatalog couplings, TextDie die) {
		int places = 2 * GaussPoolAttributeCalculatorMatrix.CAPACITY_GPACM_ARRAY - 1;
		int needed = countShares(characters, couplings);
		if (die.countRemaining() < needed) {
			throw new IllegalArgumentException("the text gives " + die.countRemaining() + " letters of " + needed + " needed");
		}

		int[] variants = variantsPerCharacter(characters.size(), places);
		List<GeneratedMatrix> result = new ArrayList<>();
		int slot = 0;
		for (AttributeCharacter character : characters.getCharacters()) {
			for (int variant = 0; variant < variants[character.getNumber() - 1]; variant++) {
				int gaussIndex = slot - (GaussPoolAttributeCalculatorMatrix.CAPACITY_GPACM_ARRAY - 1);
				result.add(build(character, variant, FIRST_FUNC_ID + slot, gaussIndex, couplings, die));
				slot++;
			}
		}
		return result;
	}

	private static GeneratedMatrix build(AttributeCharacter character, int variant, int funcID, int gaussIndex,
			AttributeCouplingCatalog couplings, TextDie die) {

		int[] targets = new int[N];
		for (Attribute attribute : Attribute.values()) {
			if (attribute != Attribute.ignore && !isState(attribute)) {
				targets[attribute.getIndex()] = character.getLevel(attribute.getIndex()).draw(die);
			}
		}

		float[] shares = new float[N * N];
		int[] functions = new int[N * N];

		for (Attribute attribute : Attribute.values()) {
			if (attribute == Attribute.ignore) continue;
			int i = attribute.getIndex();
			functions[i * N + i] = FunctionByTable_Type.identical.getIndex();
			shares[i * N + i] = 1f - returnRate(attribute);
		}

		float[] anchors = new float[N];
		for (Attribute attribute : Attribute.values()) {
			if (attribute != Attribute.ignore && !isState(attribute)) {
				anchors[attribute.getIndex()] = returnRate(attribute) * targets[attribute.getIndex()];
			}
		}

		for (Map.Entry<AttributeCoupling, Float> entry : couplings.strengthsFor(character, targets[Attribute.power.getIndex()]).entrySet()) {
			AttributeCoupling coupling = entry.getKey();
			float share = entry.getValue() / 100f * (0.8f + 0.4f * (float) die.next());
			int row = coupling.source.getIndex();
			int column = coupling.target.getIndex();
			shares[row * N + column] = share;
			functions[row * N + column] = coupling.function.getIndex();
			if (!isState(coupling.source) && !isState(coupling.target)) {
				anchors[column] -= share * f(coupling.function, targets[row]);
			}
		}

		applyDrive(shares, functions, Attribute.hunger, HUNGER_DRIVE, die);
		applyDrive(shares, functions, Attribute.tiredness, TIREDNESS_DRIVE, die);

		for (Attribute attribute : Attribute.values()) {
			if (attribute != Attribute.ignore && !isState(attribute)) {
				putConstant(shares, functions, attribute.getIndex(), anchors[attribute.getIndex()]);
			}
		}

		return new GeneratedMatrix(character, variant, funcID, gaussIndex, shares, functions, targets);
	}

	private static void applyDrive(float[] shares, int[] functions, Attribute attribute, Drive drive, TextDie die) {
		int index = attribute.getIndex();
		shares[index * N + index] = drive.growth;
		putConstant(shares, functions, index, drive.constant * (0.8f + 0.4f * (float) die.next()));
	}

	/** A constant is a cell with horizontal_max in a row without a coupling into this column. */
	private static void putConstant(float[] shares, int[] functions, int column, float amount) {
		for (int row = 0; row < N; row++) {
			if (row != column && shares[row * N + column] == 0f) {
				shares[row * N + column] = amount / MAX;
				functions[row * N + column] = FunctionByTable_Type.horizontal_max.getIndex();
				return;
			}
		}
		throw new IllegalStateException("no free cell in column " + column);
	}
}
