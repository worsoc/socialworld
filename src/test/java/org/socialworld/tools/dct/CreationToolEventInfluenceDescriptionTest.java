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

public class CreationToolEventInfluenceDescriptionTest {

  @Test
  void withDecimalPoint_turnsACommaIntoAPoint() {
    assertThat(CreationTool_EventInfluenceDescription.withDecimalPoint("1,5")).isEqualTo("1.5");
  }

  @Test
  void withDecimalPoint_keepsAPointAndAnInteger() {
    assertThat(CreationTool_EventInfluenceDescription.withDecimalPoint("1.5")).isEqualTo("1.5");
    assertThat(CreationTool_EventInfluenceDescription.withDecimalPoint("-20")).isEqualTo("-20");
  }

  @Test
  void withDecimalPoint_removesSurroundingSpaces() {
    assertThat(CreationTool_EventInfluenceDescription.withDecimalPoint(" 0,51 ")).isEqualTo("0.51");
  }
}
