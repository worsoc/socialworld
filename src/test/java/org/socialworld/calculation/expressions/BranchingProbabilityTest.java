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
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_ConditionOperator;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.TickRandomCache;

/**
 * A single comparison of an attribute with a constant is decided with a probability:
 * the more clearly the condition is met, the more likely the THEN branch is taken.
 */
public class BranchingProbabilityTest {

  private static final int THRESHOLD = 5000;
  private static final int OBJECTS = 20000;
  private static final double TOLERANCE = 0.02;

  /** @return the share of objects that take the THEN branch */
  private static double shareOfThen(Expression_ConditionOperator operator, int hunger) {
    Expression comparison = new Comparison(operator, GetAttributeValue.getInstance(Attribute.hunger.getIndex()),
        new Constant(new Value(Type.integer, THRESHOLD)));
    Expression branching = Branching.createBranchingSmart(comparison, new Constant(new Value(Type.integer, 1)),
        new Constant(new Value(Type.integer, 0)));

    AttributeArray attributes = new AttributeArray(Attribute.NUMBER_OF_ATTRIBUTES);
    attributes.set(Attribute.hunger, hunger);
    TickRandomCache.getInstance().setTick(5);

    int countThen = 0;
    for (int objectID = 1; objectID <= OBJECTS; objectID++) {
      ValueArrayList arguments = new ValueArrayList();
      arguments.add(new Value(Type.integer, Value.VALUE_BY_NAME_OBJECTID, objectID));
      arguments.add(new Value(Type.attributeArray, attributes));
      if ((int) branching.evaluate(arguments).getObject(Type.integer) == 1) {
        countThen++;
      }
    }
    return (double) countThen / OBJECTS;
  }

  @Test
  void greaterEqual_isLikelyWhenTheValueIsHigh() {
    assertThat(shareOfThen(Expression_ConditionOperator.greaterEqual, 3000)).isCloseTo(0.018, within(TOLERANCE));
    assertThat(shareOfThen(Expression_ConditionOperator.greaterEqual, 4500)).isCloseTo(0.269, within(TOLERANCE));
    assertThat(shareOfThen(Expression_ConditionOperator.greaterEqual, 5500)).isCloseTo(0.731, within(TOLERANCE));
    assertThat(shareOfThen(Expression_ConditionOperator.greaterEqual, 7000)).isCloseTo(0.982, within(TOLERANCE));
  }

  @Test
  void less_isLikelyWhenTheValueIsLow() {
    assertThat(shareOfThen(Expression_ConditionOperator.less, 3000)).isCloseTo(0.982, within(TOLERANCE));
    assertThat(shareOfThen(Expression_ConditionOperator.less, 4500)).isCloseTo(0.731, within(TOLERANCE));
    assertThat(shareOfThen(Expression_ConditionOperator.less, 5500)).isCloseTo(0.269, within(TOLERANCE));
    assertThat(shareOfThen(Expression_ConditionOperator.less, 7000)).isCloseTo(0.018, within(TOLERANCE));
  }

  @Test
  void atTheThreshold_bothBranchesAreEquallyLikely() {
    assertThat(shareOfThen(Expression_ConditionOperator.greaterEqual, THRESHOLD)).isCloseTo(0.5, within(TOLERANCE));
    assertThat(shareOfThen(Expression_ConditionOperator.less, THRESHOLD)).isCloseTo(0.5, within(TOLERANCE));
  }
}
