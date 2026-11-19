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
import static org.assertj.core.api.Assertions.within;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.collections.ValueArrayList;

/**
 * The tag ActionDir as expression (concept ERD X2, step X2.1b), only through the argument list, without simulation.
 */
public class DirectionToReferenceTest {

  private static final float EPS = 0.0001f;

  private static ValueArrayList positions(Vector reference, Vector actor) {
    ValueArrayList arguments = new ValueArrayList();
    if (reference != null) {
      arguments.add(new Value(Type.vector, Value.VALUE_BY_NAME_ACTION_REFERENCE_POSITION, reference));
    }
    if (actor != null) {
      arguments.add(new Value(Type.vector, Value.VALUE_BY_NAME_ACTION_ACTOR_POSITION, actor));
    }
    return arguments;
  }

  private static void assertDirection(String formula, ValueArrayList arguments, float x, float y, float z) {
    DirectionToReference expression = new DirectionToReference(formula);
    assertThat(expression.isValid()).as(formula).isTrue();

    Value result = expression.evaluate(arguments);

    assertThat(result.isValid()).as(formula).isTrue();
    assertThat(result.getType()).isEqualTo(Type.vector);
    Vector v = (Vector) result.getObject(Type.vector);
    assertThat(v.getX()).as("x of " + formula).isCloseTo(x, within(EPS));
    assertThat(v.getY()).as("y of " + formula).isCloseTo(y, within(EPS));
    assertThat(v.getZ()).as("z of " + formula).isCloseTo(z, within(EPS));
  }

  @Test
  void negativeLength_pointsAwayFromTheReference() {
    assertDirection("-5;0", positions(new Vector(0, 0, 0), new Vector(3000, 4000, 0)), 3, 4, 0);
  }

  @Test
  void positiveLength_pointsToTheReference() {
    assertDirection("5;0", positions(new Vector(0, 0, 0), new Vector(3000, 4000, 0)), -3, -4, 0);
  }

  @Test
  void kzZero_ignoresTheHeight_andKeepsTheLength() {
    assertDirection("5;0", positions(new Vector(0, 0, 1000), new Vector(3000, 4000, 0)), -3, -4, 0);
    assertDirection("-2;0", positions(new Vector(1000, 0, -5000), new Vector(0, 0, 0)), -2, 0, 0);
  }

  @Test
  void kzOne_isSpatial_andTheOffsetIsAddedAfterwards() {
    // positions in millimetres; away from (0,0,0): (0,3000,4000) normalized × 5 = (0,3,4), plus (0,0,2)
    assertDirection("-5;1;(0,0,2)", positions(new Vector(0, 0, 0), new Vector(0, 3000, 4000)), 0, 3, 6);
  }

  @Test
  void samePlace_givesTheFallbackPlusOffset() {
    assertDirection("-5;0;(0,0,1);(3.5,1.2,0)", positions(new Vector(1, 1, 0), new Vector(1, 1, 0)), 3.5f, 1.2f, 1);
  }

  @Test
  void belowMinimalDistance_givesTheFallback() {
    assertThat(org.socialworld.calculation.functions.FunctionDirectionToReference.MIN_DISTANCE).isEqualTo(100f);
    assertDirection("-5;0;(0,0,0);(0,5,0)", positions(new Vector(0, 0, 0), new Vector(50, 50, 0)), 0, 5, 0);
    // only the height differs and kz = 0: horizontally no direction
    assertDirection("-5;0;(0,0,0);(0,5,0)", positions(new Vector(0, 0, 9000), new Vector(0, 0, 0)), 0, 5, 0);
  }

  @Test
  void missingPosition_givesTheFallback() {
    assertDirection("-5;0;(0,0,0);(1,2,0)", positions(null, new Vector(3000, 4000, 0)), 1, 2, 0);
    assertDirection("-5;0;(0,0,0);(1,2,0)", positions(new Vector(0, 0, 0), null), 1, 2, 0);
    assertDirection("-5;0;(0,0,0);(1,2,0)", new ValueArrayList(), 1, 2, 0);
  }

  @Test
  void missingParts_meanNoOffsetAndNoFallback() {
    assertDirection("5;0", positions(new Vector(2, 2, 0), new Vector(2, 2, 0)), 0, 0, 0);
    assertDirection("5;0;(0,0,3)", positions(new Vector(2, 2, 0), new Vector(2, 2, 0)), 0, 0, 3);
  }

  @Test
  void wrongFormulas_giveAnInvalidExpression() {
    String[] wrong = { "", "5", "x;0", "5;y", "5;0;(1,2)", "5;0;1,2,3", "5;0;(0,0,0);(0,0,a)", "5;0;(0,0,0);(0,0,0);7" };

    for (String formula : wrong) {
      assertThat(new DirectionToReference(formula).isValid()).as("'" + formula + "'").isFalse();
    }
  }

  @Test
  void sameParameters_shareTheFuncID() {
    DirectionToReference a = new DirectionToReference("-5;0;(0,0,0);(3.5,1.2,0)");
    DirectionToReference b = new DirectionToReference(" -5 ; 0 ; ( 0 , 0 , 0 ) ; ( 3.5 , 1.2 , 0 ) ");
    DirectionToReference c = new DirectionToReference("-5;0;(0,0,0);(1.2,3.5,0)");

    assertThat(a.getFuncID()).isEqualTo(b.getFuncID());
    assertThat(a.getFuncID()).isNotEqualTo(c.getFuncID());
  }
}
