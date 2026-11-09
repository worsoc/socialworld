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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.functions.FunctionByTable_Type;
import org.socialworld.tools.dct.AttributeDataReader.StoredMatrix;
import org.socialworld.tools.dct.AttributeMatrixCell.Kind;

public class AttributeMatrixCellTest {

  private static final int N = Attribute.NUMBER_OF_ATTRIBUTES;
  private static final int HUNGER = Attribute.hunger.getIndex();
  private static final int POWER = Attribute.power.getIndex();
  private static final int MOOD = Attribute.mood.getIndex();

  private static AttributeMatrixCell cell(int row, int column, float share, FunctionByTable_Type function) {
    float[] shares = new float[N * N];
    int[] functions = new int[N * N];
    shares[row * N + column] = share;
    functions[row * N + column] = function.getIndex();
    return AttributeMatrixCell.of(new StoredMatrix(200, shares, functions), row, column);
  }

  @Test
  void diagonal_showsTheReturnRate() {
    AttributeMatrixCell cell = cell(MOOD, MOOD, 0.98f, FunctionByTable_Type.identical);

    assertThat(cell.getKind()).isEqualTo(Kind.DIAGONAL);
    assertThat(cell.getValueText()).isEqualTo("0.020");
    assertThat(cell.getFunctionText()).isEmpty();
  }

  @Test
  void diagonalAboveOne_showsTheGrowth() {
    AttributeMatrixCell cell = cell(HUNGER, HUNGER, 1.00134f, FunctionByTable_Type.identical);

    assertThat(cell.getKind()).isEqualTo(Kind.DIAGONAL);
    assertThat(cell.getValueText()).isEqualTo("+0.0013");
    assertThat(cell.getToolTip()).contains("Wachstum");
  }

  @Test
  void coupling_showsPointsPerStepAndTheFunction() {
    AttributeMatrixCell cell = cell(HUNGER, POWER, -0.004f, FunctionByTable_Type.hinge_high);

    assertThat(cell.getKind()).isEqualTo(Kind.COUPLING);
    assertThat(cell.getValueText()).isEqualTo("-0.40");
    assertThat(cell.getFunctionText()).isEqualTo("hh");
    assertThat(cell.getToolTip()).contains("hunger", "power", "hinge_high");
  }

  @Test
  void horizontalMaxOutsideTheDiagonal_isAConstant() {
    AttributeMatrixCell cell = cell(MOOD, POWER, 0.012f, FunctionByTable_Type.horizontal_max);

    assertThat(cell.getKind()).isEqualTo(Kind.CONSTANT);
    assertThat(cell.getValueText()).isEqualTo("+1.20");
    assertThat(cell.getFunctionText()).isEqualTo("konst");
    assertThat(cell.getToolTip()).contains("Platzhalter");
  }

  @Test
  void shareZero_isEmpty() {
    AttributeMatrixCell cell = cell(MOOD, POWER, 0f, FunctionByTable_Type.horizontal_min);

    assertThat(cell.getKind()).isEqualTo(Kind.EMPTY);
    assertThat(cell.getValueText()).isEmpty();
    assertThat(cell.getFunctionText()).isEmpty();
  }

  @Test
  void anUnknownFunctionIsShownByItsNumber() {
    float[] shares = new float[N * N];
    int[] functions = new int[N * N];
    shares[MOOD * N + POWER] = 0.01f;
    functions[MOOD * N + POWER] = 150;

    assertThat(AttributeMatrixCell.of(new StoredMatrix(200, shares, functions), MOOD, POWER).getFunctionText()).isEqualTo("#150");
  }
}
