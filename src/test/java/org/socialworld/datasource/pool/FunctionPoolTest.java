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
import org.socialworld.calculation.FunctionBase;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.functions.FunctionByMatrix_Matrix;
import org.socialworld.calculation.functions.FunctionByTable;
import org.socialworld.calculation.functions.FunctionByTable_Type;
import org.socialworld.calculation.functions.FunctionMXplusN;
import org.socialworld.calculation.functions.FunctionNothing;
import org.socialworld.collections.ValueArrayList;

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

  private static int tableValue(FunctionBase function, int x) {
    ValueArrayList arguments = new ValueArrayList();
    arguments.add(new Value(Type.integer, x));
    return ((Number) function.calculate(arguments).getObject(Type.integer)).intValue();
  }

  @Test
  void tableFunctions_haveTheIndexOfTheirTypeAsFuncID() {
    FunctionPool pool = FunctionPool.getInstance();

    for (FunctionByTable_Type type : FunctionByTable_Type.values()) {
      FunctionBase function = pool.getFunction(type.getIndex());
      assertThat(function).as("func_id %d", type.getIndex()).isInstanceOf(FunctionByTable.class);

      FunctionByTable expected = new FunctionByTable(type);
      for (int x = 0; x <= 9999; x += 1111) {
        assertThat(tableValue(function, x)).as("%s at %d", type, x).isEqualTo(tableValue(expected, x));
      }
    }
  }

  @Test
  void register_givesEachFunctionObjectOneFuncIDFromTheRuntimeRange() {
    FunctionPool pool = FunctionPool.getInstance();
    FunctionBase first = FunctionMXplusN.getInstance(Type.integer, 1.0f, -12345.0f, 0f, 9999f);
    FunctionBase second = FunctionMXplusN.getInstance(Type.integer, 1.0f, -12346.0f, 0f, 9999f);

    int idFirst = pool.register(first);
    int idSecond = pool.register(second);

    assertThat(idFirst).isGreaterThanOrEqualTo(FunctionPool.FIRST_RUNTIME_FUNCTION_ID);
    assertThat(pool.register(first)).as("the same function again").isEqualTo(idFirst);
    assertThat(idSecond).as("another function").isNotEqualTo(idFirst);
    assertThat(pool.getFunction(idFirst)).isSameAs(first);
    assertThat(pool.getFunction(idSecond)).isSameAs(second);
  }

  @Test
  void getFunction_beyondTheRegisteredRuntimeFunctions_isNothing() {
    assertThat(FunctionPool.getInstance().getFunction(Integer.MAX_VALUE)).isInstanceOf(FunctionNothing.class);
  }
}
