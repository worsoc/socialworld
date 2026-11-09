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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.functions.FunctionByMatrix_Matrix;

public class FunctionPoolTest {

  private static float value(org.socialworld.calculation.Value value) {
    return ((Number) value.getObject(Type.floatingpoint)).floatValue();
  }

  @Test
  void createMatrix_putsEveryCellInItsPlace() {
    int floatingPoint = Type.floatingpoint.getIndex();
    float[] shares = {0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f};
    int[] functions = {1, 2, 3, 4, 5, 6};
    float[] offsets = {10f, 20f, 30f, 40f, 50f, 60f};

    FunctionByMatrix_Matrix matrix = FunctionPool.createMatrix(3, floatingPoint, floatingPoint, shares, functions, offsets);

    assertThat(value(matrix.getShare(0, 1))).isEqualTo(0.2f);
    assertThat(value(matrix.getShare(1, 2))).isEqualTo(0.6f);
    assertThat(matrix.getFunction(1, 0)).isEqualTo(4);
    assertThat(value(matrix.getOffset(0, 2))).isEqualTo(30f);
    assertThat(value(matrix.getOffset(1, 1))).isEqualTo(50f);
  }

  @Test
  void createMatrix_withFloatingPointShares_doesNotDivideBy100() {
    int floatingPoint = Type.floatingpoint.getIndex();

    FunctionByMatrix_Matrix matrix = FunctionPool.createMatrix(1, floatingPoint, floatingPoint,
        new float[] {1f}, new int[] {1}, new float[] {0f});

    assertThat(matrix.isWithDiv100()).isFalse();
  }
}
