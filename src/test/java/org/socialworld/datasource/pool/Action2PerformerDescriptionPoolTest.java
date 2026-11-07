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
import org.socialworld.calculation.Value;
import org.socialworld.calculation.expressions.Calculate;
import org.socialworld.collections.ValueArrayList;

public class Action2PerformerDescriptionPoolTest {

  private static String constant(int value) {
    return "CONST(" + Type.integer.getIndex() + " " + value + ")";
  }

  @Test
  void scaleConstant_isTheAttributeScale() {
    assertThat(Action2PerformerDescriptionPool.CONST_ATTRIBUTE_VALUE_SCALE).isEqualTo("CONST(1 100)");
  }

  @Test
  void commaInsideTheFirstOperand_isNotTheSeparator() {
    // before the fix the comma inside MUL(...) was taken as the separator of the outer SUB
    Calculate expression = new Calculate("SUB(MUL(" + constant(6) + "," + constant(7) + ")," + constant(2) + ")", "x");

    assertThat(expression.isValid()).isTrue();
    assertThat(((Number) expression.evaluate(new ValueArrayList()).getObject(Type.integer)).intValue()).isEqualTo(40);
  }

  @Test
  void commaOnlyInTheSecondOperand_worksAsBefore() {
    Calculate expression = new Calculate("SUB(" + constant(50) + ",MUL(" + constant(6) + "," + constant(7) + "))", "x");

    assertThat(((Number) expression.evaluate(new ValueArrayList()).getObject(Type.integer)).intValue()).isEqualTo(8);
  }

  @Test
  void intensityTimesPower_isDividedByTheScale() {
    // action intensity 30.0 (a float), power 5000 (displayed 50): 30 * 50 = 1500 as before with 0..99
    String intensity = "CONST(" + Type.floatingpoint.getIndex() + " 30.0)";
    String term = "DIV(MUL(" + intensity + "," + constant(5000) + "),"
        + Action2PerformerDescriptionPool.CONST_ATTRIBUTE_VALUE_SCALE + ")";

    Calculate expression = new Calculate(term, "intensity");
    Value result = expression.evaluate(new ValueArrayList());

    assertThat(expression.isValid()).isTrue();
    assertThat(result.getType()).isEqualTo(Type.floatingpoint);
    assertThat(((Number) result.getObject(Type.floatingpoint)).floatValue()).isEqualTo(1500.0f);
  }
}
