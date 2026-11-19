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
package org.socialworld.calculation.geometry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

import org.junit.jupiter.api.Test;

public class VectorTest {

  private static final float EPS = 0.0001f;

  @Test
  void normalize_nonZero_isNormalizedWithLengthOne() {
    Vector v = Vector.getMutable(3, 4, 0);

    v.normalize();

    assertThat(v.isNormalized()).isTrue();
    assertThat(v.length()).isEqualTo(1f);
    assertThat(v.getX()).isCloseTo(0.6f, within(EPS));
    assertThat(v.getY()).isCloseTo(0.8f, within(EPS));
  }

  @Test
  void normalize_zeroVector_isNotNormalized_andHasLengthZero() {
    Vector v = Vector.getMutable(0, 0, 0);

    v.normalize();

    assertThat(v.isNormalized()).isFalse();
    assertThat(v.length()).isEqualTo(0f);
    assertThat(v.is000()).isTrue();
  }

  @Test
  void normalize_tinyVector_becomesZeroVector_withLengthZero() {
    Vector v = Vector.getMutable(0.000001f, 0, 0);

    v.normalize();

    assertThat(v.isNormalized()).isFalse();
    assertThat(v.length()).isEqualTo(0f);
  }

  @Test
  void mulComponents_multipliesInPlace_componentByComponent() {
    Vector v = Vector.getMutable(2, 3, 4);

    v.mulComponents(new Vector(-1, 0.5f, 0));

    assertThat(v.getX()).isEqualTo(-2f);
    assertThat(v.getY()).isEqualTo(1.5f);
    assertThat(v.getZ()).isEqualTo(0f);
  }

  @Test
  void mulComponents_resetsNormalizedFlag() {
    Vector v = Vector.getMutable(3, 4, 0);
    v.normalize();

    v.mulComponents(new Vector(5, 5, 5));

    assertThat(v.isNormalized()).isFalse();
    assertThat(v.length()).isCloseTo(5f, within(EPS));
  }

  @Test
  void mulComponents_onUnchangeableVector_throws() {
    Vector v = new Vector(1, 2, 3);

    assertThatThrownBy(() -> v.mulComponents(new Vector(1, 1, 1)))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void mulComponents_onNothing_staysNothing() {
    Vector nothing = Vector.getObjectNothing();

    nothing.mulComponents(new Vector(2, 2, 2));

    assertThat(nothing.checkIsObjectNothing()).isTrue();
    assertThat(nothing.is000()).isTrue();
  }
}
