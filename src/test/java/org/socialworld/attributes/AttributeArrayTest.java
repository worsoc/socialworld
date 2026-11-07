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
package org.socialworld.attributes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AttributeArrayTest {

  @Test
  void range_isZeroTo9999_displayedAsZeroTo99() {
    assertThat(AttributeArray.ATTRIBUTE_VALUE_MAX).isEqualTo(9999);
    assertThat(AttributeArray.ATTRIBUTE_VALUE_MIDDLE).isEqualTo(5000);
    assertThat(AttributeArray.ATTRIBUTE_VALUE_SCALE).isEqualTo(100);
  }

  @Test
  void displayValue_isRoundedDown() {
    assertThat(AttributeArray.toDisplayValue(0)).isZero();
    assertThat(AttributeArray.toDisplayValue(99)).isZero();
    assertThat(AttributeArray.toDisplayValue(100)).isEqualTo(1);
    assertThat(AttributeArray.toDisplayValue(5049)).isEqualTo(50);
    assertThat(AttributeArray.toDisplayValue(9950)).isEqualTo(99);
    assertThat(AttributeArray.toDisplayValue(9999)).isEqualTo(99);
  }

  @Test
  void set_limitsToTheInternalRange() {
    AttributeArray attributes = new AttributeArray(Attribute.NUMBER_OF_ATTRIBUTES);

    attributes.set(Attribute.hunger, 12345);
    attributes.set(Attribute.power, -7);
    attributes.set(Attribute.mood, 4321);

    assertThat(attributes.get(Attribute.hunger)).isEqualTo(9999);
    assertThat(attributes.get(Attribute.power)).isZero();
    assertThat(attributes.get(Attribute.mood)).isEqualTo(4321);
    assertThat(attributes.getDisplayValue(Attribute.hunger.getIndex())).isEqualTo(99);
    assertThat(attributes.getDisplayValue(Attribute.mood.getIndex())).isEqualTo(43);
  }

  @Test
  void displayString_showsDisplayedValues_toStringKeepsInternalOnes() {
    AttributeArray attributes = new AttributeArray(new int[] {5299, 0, 99, 100, 9999, 4500, 1, 7777, 5000});

    assertThat(attributes.toDisplayString()).isEqualTo("(52, 0, 0, 1, 99, 45, 0, 77, 50)");
    assertThat(attributes.toString()).isEqualTo("(5299, 0, 99, 100, 9999, 4500, 1, 7777, 5000)");
  }

  @Test
  void displayValue_keepsMinusOneForAnInvalidIndex() {
    AttributeArray attributes = new AttributeArray(Attribute.NUMBER_OF_ATTRIBUTES);

    assertThat(attributes.getDisplayValue(42)).isEqualTo(-1);
  }
}
