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

/** Only the empty mapping, nothing is read from the database. */
public class GaussPoolAAACMMappingTest {

  @Test
  void withoutEntry_theDrawnIndexStays() {
    GaussPoolAAACMMapping mapping = new GaussPoolAAACMMapping();

    assertThat(mapping.getMatrixIndex(5, 17)).isEqualTo(17);
    assertThat(mapping.getMatrixIndex(-99, -42)).isEqualTo(-42);
    assertThat(mapping.getMatrixIndex(0, 0)).isZero();
  }

  @Test
  void entry_givesTheMatrixIndex_forExactlyThisPair() {
    GaussPoolAAACMMapping mapping = new GaussPoolAAACMMapping();

    mapping.setMatrixIndex(-3, 40, -77);

    assertThat(mapping.getMatrixIndex(-3, 40)).isEqualTo(-77);
    assertThat(mapping.getMatrixIndex(3, 40)).isEqualTo(40);
    assertThat(mapping.getMatrixIndex(-3, -40)).isEqualTo(-40);
  }

  @Test
  void allCornersAreReachable() {
    GaussPoolAAACMMapping mapping = new GaussPoolAAACMMapping();

    mapping.setMatrixIndex(-99, -99, 1);
    mapping.setMatrixIndex(99, 99, 2);

    assertThat(mapping.getMatrixIndex(-99, -99)).isEqualTo(1);
    assertThat(mapping.getMatrixIndex(99, 99)).isEqualTo(2);
  }

  @Test
  void indexOfExactlyTheCapacity_countsAsTheOutermostPlace() {
    GaussPoolAAACMMapping mapping = new GaussPoolAAACMMapping();
    mapping.setMatrixIndex(99, -99, 7);

    assertThat(mapping.getMatrixIndex(100, -100)).isEqualTo(7);
    assertThat(new GaussPoolAAACMMapping().getMatrixIndex(0, 100)).isEqualTo(99);
  }
}
