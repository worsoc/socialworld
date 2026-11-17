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
package org.socialworld.calculation.expressions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.ValueInterpreteAs;
import org.socialworld.datasource.pool.FunctionPool;

/**
 * An MX+N expression gets the func_id of its function from the FunctionPool.
 * There is no limit of slots any more: many expressions with few different functions all become valid.
 */
public class MXPlusNTest {

  @Test
  void manyExpressions_areAllValid_andShareTheFuncIDOfTheSameFunction() {
    int[] firstFuncID = new int[10];

    for (int i = 0; i < 20000; i++) {
      int step = i % 10;
      MXPlusN expression = new MXPlusN("tiredness;1.00;" + (step - 50) + ".00", ValueInterpreteAs.attributeValue);

      assertThat(expression.isValid()).as("expression %d", i).isTrue();
      assertThat(expression.getFuncID()).isGreaterThanOrEqualTo(FunctionPool.FIRST_RUNTIME_FUNCTION_ID);

      if (i < 10) {
        firstFuncID[step] = expression.getFuncID();
      }
      else {
        assertThat(expression.getFuncID()).as("same function as expression %d", step).isEqualTo(firstFuncID[step]);
      }
    }

    for (int a = 0; a < 10; a++) {
      for (int b = a + 1; b < 10; b++) {
        assertThat(firstFuncID[a]).as("steps %d and %d", a, b).isNotEqualTo(firstFuncID[b]);
      }
    }
  }
}
