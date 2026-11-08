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
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.Type;
import org.socialworld.collections.ValueArrayList;

public class ConstantTest {

  @Test
  void sigmoidK_isFiveTimesTheAttributeScale() {
    Object k = Constant.SIGMOID_K_ATTRIBUTE.evaluate(new ValueArrayList()).getObject(Type.floatingpoint);

    assertThat(((Number) k).floatValue()).isEqualTo(5.0f * AttributeArray.ATTRIBUTE_VALUE_SCALE);
  }
}
