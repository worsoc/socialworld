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
import org.socialworld.calculation.Functions;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.tools.dct.AttributeDataReader.StoredMatrix;

/**
 * Calculates the target values of a stored matrix, which are not stored themselves.
 *
 * The targets are the fixed point of the matrix while hunger and tiredness are low. They are
 * calculated from the cells without rounding: the rounded steps of the running calculation
 * stop in a dead zone around the target, for the slow attributes up to 250 away from it.
 */
public final class AttributeMatrixTargets {

	/** Hunger and tiredness rise with time, they have no target. */
	public static final int NO_TARGET = -1;

	private static final int N = Attribute.NUMBER_OF_ATTRIBUTES;
	private static final int HUNGER = Attribute.hunger.getIndex();
	private static final int TIREDNESS = Attribute.tiredness.getIndex();

	/** Low enough that the couplings of hunger and tiredness do not act. */
	private static final int RESTED_VALUE = 3000;
	private static final int MAX_ROUNDS = 1000;
	private static final double TOLERANCE = 0.01;

	private AttributeMatrixTargets() {
	}

	private static boolean hasTarget(int attribute) {
		return attribute != HUNGER && attribute != TIREDNESS;
	}

	/**
	 * @return the target per attribute index, {@link #NO_TARGET} for hunger and tiredness
	 * @throws IllegalStateException if the matrix has no fixed point of this kind
	 */
	public static int[] compute(StoredMatrix matrix, AttributeCharacter character) {
		double[] values = startValues(character);

		for (int round = 0; round < MAX_ROUNDS; round++) {
			double[] next = values.clone();
			double change = 0;
			for (int column = 0; column < N; column++) {
				if (hasTarget(column)) {
					next[column] = targetOf(matrix, column, values);
					change = Math.max(change, Math.abs(next[column] - values[column]));
				}
			}
			values = next;
			if (change < TOLERANCE) {
				return rounded(values);
			}
		}
		throw new IllegalStateException("func_id " + matrix.getFuncID() + " does not settle");
	}

	private static double[] startValues(AttributeCharacter character) {
		double[] values = new double[N];
		for (int i = 0; i < N; i++) {
			if (hasTarget(i)) {
				values[i] = (character.getLevel(i).getLowest() + character.getLevel(i).getHighest()) / 2.0;
			} else {
				values[i] = RESTED_VALUE;
			}
		}
		return values;
	}

	private static double targetOf(StoredMatrix matrix, int column, double[] values) {
		double returnRate = 1.0 - matrix.getShare(column, column);
		if (returnRate <= 0) {
			throw new IllegalStateException("func_id " + matrix.getFuncID() + " does not return in column " + column);
		}
		double sum = 0;
		for (int row = 0; row < N; row++) {
			if (row != column && matrix.getShare(row, column) != 0f) {
				sum += matrix.getShare(row, column) * function(matrix.getFunction(row, column), values[row], matrix.getFuncID());
			}
		}
		return sum / returnRate;
	}

	private static int function(int functionIndex, double input, int funcID) {
		ValueArrayList arguments = new ValueArrayList();
		arguments.add(Value.getInteger((int) Math.round(input)));
		Value result = Functions.getInstance().get(functionIndex).calculate(arguments);
		if (result.isInvalidOrNothing()) {
			throw new IllegalStateException("func_id " + funcID + " uses the unknown function " + functionIndex);
		}
		return (int) result.getObject(Type.integer);
	}

	private static int[] rounded(double[] values) {
		int[] targets = new int[N];
		for (int i = 0; i < N; i++) {
			targets[i] = hasTarget(i) ? (int) Math.round(values[i]) : NO_TARGET;
		}
		return targets;
	}
}
