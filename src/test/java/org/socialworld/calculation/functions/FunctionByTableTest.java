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

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.AttributeArray;

public class FunctionByTableTest {

  private static final int MAX = AttributeArray.ATTRIBUTE_VALUE_MAX;

  /** The left edge of the former hand-written table u for 0..99. */
  private static final int[] FORMER_U_EDGE =
      {99, 89, 80, 71, 63, 55, 48, 41, 35, 29, 24, 19, 15, 11, 8, 5, 3, 1};

  private static int[] table(FunctionByTable_Type type, int maxValue) {
    FunctionByTable function = new FunctionByTable(type, maxValue);
    int[] values = new int[maxValue + 1];
    for (int x = 0; x <= maxValue; x++) {
      values[x] = function.calculate(x);
    }
    return values;
  }

  // ---------------------------------------------------------------------------
  // u and u_mirrored: formula instead of the hand-written table
  // ---------------------------------------------------------------------------

  @Test
  void u_at99_isTheFormerTableWithinOne() {
    int[] u = table(FunctionByTable_Type.u, 99);

    for (int x = 0; x < FORMER_U_EDGE.length; x++) {
      assertThat(u[x]).as("u(%d)", x).isBetween(FORMER_U_EDGE[x] - 1, FORMER_U_EDGE[x] + 1);
      assertThat(u[99 - x]).as("u(%d)", 99 - x).isEqualTo(u[x]);
    }
    for (int x = 18; x <= 81; x++) {
      assertThat(u[x]).as("u(%d)", x).isZero();
    }
  }

  @Test
  void u_at9999_keepsItsShape() {
    int[] u = table(FunctionByTable_Type.u, MAX);
    int edge = (int) Math.round(MAX * FunctionByTable.U_EDGE_SHARE);

    assertThat(u[0]).isEqualTo(MAX);
    assertThat(u[MAX]).isEqualTo(MAX);
    assertThat(u[MAX / 2]).isZero();
    for (int x = 1; x < edge; x++) {
      assertThat(u[x]).as("u falls at %d", x).isLessThanOrEqualTo(u[x - 1]);
      assertThat(u[MAX - x]).as("u is symmetric at %d", x).isEqualTo(u[x]);
    }
    for (int x = edge; x <= MAX - edge; x++) {
      assertThat(u[x]).as("u(%d)", x).isZero();
    }
    // the same curve as at 99, only finer: halfway down the edge u is 0.5^1.8 = 0.287 of MAX (u(9) = 29 at 99)
    assertThat(u[edge / 2]).isBetween((int) (0.27 * MAX), (int) (0.30 * MAX));
  }

  @Test
  void uMirrored_isMaxMinusU() {
    int[] u = table(FunctionByTable_Type.u, MAX);
    int[] mirrored = table(FunctionByTable_Type.u_mirrored, MAX);

    for (int x = 0; x <= MAX; x++) {
      assertThat(mirrored[x]).as("u_mirrored(%d)", x).isEqualTo(MAX - u[x]);
    }
  }

  // ---------------------------------------------------------------------------
  // v and v_mirrored: formulas, unchanged
  // ---------------------------------------------------------------------------

  @Test
  void v_isHighAtTheEdgesAndLowInTheMiddle() {
    int[] v = table(FunctionByTable_Type.v, MAX);

    assertThat(v[0]).isEqualTo(MAX);
    assertThat(v[MAX]).isEqualTo(MAX);
    assertThat(v[MAX / 2]).isLessThanOrEqualTo(1);
    assertThat(v[MAX / 4]).isBetween(MAX / 2 - 2, MAX / 2 + 2);
  }

  @Test
  void vMirrored_isLowAtTheEdgesAndHighInTheMiddle() {
    int[] mirrored = table(FunctionByTable_Type.v_mirrored, MAX);

    assertThat(mirrored[0]).isZero();
    assertThat(mirrored[MAX]).isZero();
    assertThat(mirrored[MAX / 2]).isGreaterThanOrEqualTo(MAX - 1);
  }

  // ---------------------------------------------------------------------------
  // raise: the last entry is filled too
  // ---------------------------------------------------------------------------

  @Test
  void positiveRaise_reachesMax() {
    FunctionByTable function = new FunctionByTable(FunctionByTable_Type.positive_raise);

    assertThat(function.calculate(0)).isZero();
    assertThat(function.calculate(1234)).isEqualTo(1234);
    assertThat(function.calculate(MAX)).isEqualTo(MAX);
  }

  @Test
  void negativeRaise_fallsToZero() {
    FunctionByTable function = new FunctionByTable(FunctionByTable_Type.negative_raise);

    assertThat(function.calculate(0)).isEqualTo(MAX);
    assertThat(function.calculate(1234)).isEqualTo(MAX - 1234);
    assertThat(function.calculate(MAX)).isZero();
  }

  // ---------------------------------------------------------------------------
  // hinge: acts only above or below a threshold
  // ---------------------------------------------------------------------------

  @Test
  void hingeHigh_isZeroUpTo6000AndRisesToMax() {
    FunctionByTable function = new FunctionByTable(FunctionByTable_Type.hinge_high);

    assertThat(function.calculate(0)).isZero();
    assertThat(function.calculate(6000)).isZero();
    assertThat(function.calculate(8000)).isBetween(5000, 5001);
    assertThat(function.calculate(MAX)).isEqualTo(MAX);
  }

  @Test
  void hingeLow_fallsFromMaxToZeroAt4000() {
    FunctionByTable function = new FunctionByTable(FunctionByTable_Type.hinge_low);

    assertThat(function.calculate(0)).isEqualTo(MAX);
    assertThat(function.calculate(2000)).isBetween(4999, 5000);
    assertThat(function.calculate(4000)).isZero();
    assertThat(function.calculate(MAX)).isZero();
  }

  @Test
  void hinges_areMonotone() {
    int[] high = table(FunctionByTable_Type.hinge_high, MAX);
    int[] low = table(FunctionByTable_Type.hinge_low, MAX);

    for (int x = 1; x <= MAX; x++) {
      assertThat(high[x]).as("hinge_high at %d", x).isGreaterThanOrEqualTo(high[x - 1]);
      assertThat(low[x]).as("hinge_low at %d", x).isLessThanOrEqualTo(low[x - 1]);
    }
  }

  @Test
  void input_isLimitedToTheRange() {
    FunctionByTable function = new FunctionByTable(FunctionByTable_Type.identical);

    assertThat(function.calculate(-5)).isZero();
    assertThat(function.calculate(MAX + 10)).isEqualTo(MAX);
    assertThat(new FunctionByTable(FunctionByTable_Type.horizontal_max).calculate(17)).isEqualTo(MAX);
  }
}
