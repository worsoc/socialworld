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
package org.socialworld.calculation.functions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;

/**
 * The SIMPLE mode of the real FunctionByMatrix with floating-point shares:
 * new[target] = sum over sources share[source][target] * f(old[source]).
 */
public class FunctionByMatrixSimpleTest {

  private static final int N = Attribute.NUMBER_OF_ATTRIBUTES;
  private static final int MOOD = Attribute.mood.getIndex();
  private static final int MATERIALISM = Attribute.materialism.getIndex();
  private static final int TIREDNESS = Attribute.tiredness.getIndex();
  private static final int HUNGER = Attribute.hunger.getIndex();
  private static final int POWER = Attribute.power.getIndex();

  private final float[][] shares = new float[N][N];
  private final FunctionByTable_Type[][] functions = new FunctionByTable_Type[N][N];

  /** Starts as "every attribute keeps itself". */
  FunctionByMatrixSimpleTest() {
    for (int row = 0; row < N; row++) {
      Arrays.fill(functions[row], FunctionByTable_Type.horizontal_min);
      shares[row][row] = 1f;
      functions[row][row] = FunctionByTable_Type.identical;
    }
  }

  private void cell(int source, int target, float share, FunctionByTable_Type function) {
    shares[source][target] = share;
    functions[source][target] = function;
  }

  private void anchor(int source, int target, float amountPerStep) {
    cell(source, target, amountPerStep / AttributeArray.ATTRIBUTE_VALUE_MAX, FunctionByTable_Type.horizontal_max);
  }

  private int[] step(int... values) {
    Value[] shareValues = new Value[N * N];
    int[] functionIndices = new int[N * N];
    Value[] offsets = new Value[N * N];
    for (int row = 0; row < N; row++) {
      for (int column = 0; column < N; column++) {
        shareValues[row * N + column] = new Value(Type.floatingpoint, shares[row][column]);
        functionIndices[row * N + column] = functions[row][column].getIndex();
        offsets[row * N + column] = new Value(Type.floatingpoint, 0f);
      }
    }
    // the same way as FunctionPool.loadMatrixFromDB builds a matrix
    FunctionByMatrix_Matrix matrix = new FunctionByMatrix_Matrix(N);
    matrix.setFunctions(functionIndices);
    matrix.setShares(shareValues);
    matrix.setOffsets(offsets);
    FunctionByMatrix function = new FunctionByMatrix(matrix);

    ValueArrayList arguments = new ValueArrayList();
    arguments.add(new Value(Type.attributeArray, new AttributeArray(values)));
    arguments.add(new Value(Type.integer, FunctionByMatrix_Matrix.CALCULATION_MODE_MATRIX_X_VECTOR_SIMPLE));

    AttributeArray result = (AttributeArray) function.calculate(arguments).getObject(Type.attributeArray);
    int[] newValues = new int[N];
    for (int i = 0; i < N; i++) {
      newValues[i] = result.get(i);
    }
    return newValues;
  }

  private static int[] withValue(int index, int value) {
    int[] values = {5000, 4000, 5000, 5000, 3000, 5000, 5000, 3000, 6000};
    values[index] = value;
    return values;
  }

  @Test
  void identityMatrix_changesNothing() {
    int[] values = {1, 4000, 5000, 9999, 3000, 0, 5000, 8000, 6000};

    assertThat(step(values)).containsExactly(values);
  }

  @Test
  void anchor_makesTheTargetTheFixedPoint() {
    // target 6000, return rate 0.02: 0.98 * old + 120
    cell(POWER, POWER, 0.98f, FunctionByTable_Type.identical);
    anchor(MATERIALISM, POWER, 120f);

    assertThat(step(withValue(POWER, 3000))[POWER]).isEqualTo(3060);
    assertThat(step(withValue(POWER, 6000))[POWER]).isEqualTo(6000);
    assertThat(step(withValue(POWER, 8000))[POWER]).isEqualTo(7960);
  }

  @Test
  void anchor_ignoresItsSource() {
    cell(POWER, POWER, 0.98f, FunctionByTable_Type.identical);
    anchor(MATERIALISM, POWER, 120f);

    assertThat(step(withValue(MATERIALISM, 0))[POWER]).isEqualTo(step(withValue(MATERIALISM, 9999))[POWER]);
  }

  @Test
  void negativeShare_lowersTheTarget() {
    cell(HUNGER, POWER, -0.003f, FunctionByTable_Type.identical);

    // 6000 - 0.003 * 8000 = 5976
    assertThat(step(withValue(HUNGER, 8000))[POWER]).isEqualTo(5976);
  }

  @Test
  void diagonalAboveOneWithConstant_letsHungerRise() {
    cell(HUNGER, HUNGER, 1.00134f, FunctionByTable_Type.identical);
    anchor(MATERIALISM, HUNGER, 7.41f);

    // 5000 * 1.00134 + 7.41 = 5014.11; from 0 only the constant acts
    assertThat(step(withValue(HUNGER, 5000))[HUNGER]).isEqualTo(5014);
    assertThat(step(withValue(HUNGER, 0))[HUNGER]).isEqualTo(7);
  }

  @Test
  void aChangeBelowHalfAStep_isRoundedAway() {
    cell(HUNGER, HUNGER, 1.00001f, FunctionByTable_Type.identical);

    assertThat(step(withValue(HUNGER, 5000))[HUNGER]).isEqualTo(5000);
  }

  @Test
  void result_isLimitedToTheRange() {
    cell(HUNGER, HUNGER, 2f, FunctionByTable_Type.identical);
    cell(TIREDNESS, POWER, -3f, FunctionByTable_Type.identical);

    int[] result = step(withValue(HUNGER, 8000));

    assertThat(result[HUNGER]).isEqualTo(AttributeArray.ATTRIBUTE_VALUE_MAX);
    assertThat(result[POWER]).isZero();
  }

  @Test
  void tableFunction_isAppliedToTheSource() {
    // v(9999) = 9999, v(5000) = 2 * 5000 - 9999 = 1
    cell(TIREDNESS, MOOD, -0.01f, FunctionByTable_Type.v);

    assertThat(step(withValue(TIREDNESS, 9999))[MOOD]).isEqualTo(4900);
    assertThat(step(withValue(TIREDNESS, 5000))[MOOD]).isEqualTo(5000);
  }

  @Test
  void allCellsUseTheOldValues() {
    // power takes from hunger and hunger takes from power, both with the old value
    shares[HUNGER][HUNGER] = 0f;
    shares[POWER][POWER] = 0f;
    cell(HUNGER, POWER, 1f, FunctionByTable_Type.identical);
    cell(POWER, HUNGER, 1f, FunctionByTable_Type.identical);

    int[] result = step(withValue(HUNGER, 2000));

    assertThat(result[POWER]).isEqualTo(2000);
    assertThat(result[HUNGER]).isEqualTo(6000);
  }
}
