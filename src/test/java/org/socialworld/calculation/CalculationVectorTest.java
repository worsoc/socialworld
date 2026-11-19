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
package org.socialworld.calculation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.geometry.Vector;

/**
 * Vector operations in Calculation (concept ERD X2, step X2.1a).
 */
public class CalculationVectorTest {

  private static final float EPS = 0.0001f;

  private final Calculation calc = Calculation.getInstance();

  private static Value vec(float x, float y, float z) {
    return new Value(Type.vector, new Vector(x, y, z));
  }

  private static Vector vectorOf(Value value) {
    assertThat(value.isValid()).isTrue();
    assertThat(value.getType()).isEqualTo(Type.vector);
    return (Vector) value.getObject(Type.vector);
  }

  private static void assertVector(Value value, float x, float y, float z) {
    Vector v = vectorOf(value);
    assertThat(v.getX()).isCloseTo(x, within(EPS));
    assertThat(v.getY()).isCloseTo(y, within(EPS));
    assertThat(v.getZ()).isCloseTo(z, within(EPS));
  }

  @Test
  void addition_addsVectors() {
    assertVector(calc.addition(vec(1, 2, 3), vec(4, 5, 6)), 5, 7, 9);
  }

  @Test
  void subtraction_subtractsVectors() {
    assertVector(calc.subtraction(vec(5, 7, 9), vec(4, 5, 6)), 1, 2, 3);
  }

  @Test
  void negate_turnsVectorAround() {
    assertVector(calc.negate(vec(1, -2, 3)), -1, 2, -3);
  }

  @Test
  void multiplication_vectorTimesNumber_bothOrders() {
    assertVector(calc.multiplication(vec(1, 2, 3), new Value(Type.floatingpoint, 2.5f)), 2.5f, 5, 7.5f);
    assertVector(calc.multiplication(new Value(Type.integer, -2), vec(1, 2, 3)), -2, -4, -6);
    assertVector(calc.multiplication(new Value(Type.floatingpoint, 0.5f), vec(2, 4, 6)), 1, 2, 3);
    assertVector(calc.multiplication(vec(2, 4, 6), new Value(Type.integer, 3)), 6, 12, 18);
  }

  @Test
  void multiplyComponents_isComponentWise_notCrossProduct() {
    assertVector(calc.multiplyComponents(vec(2, 3, 4), vec(1, 1, 0)), 2, 3, 0);
    assertVector(calc.multiplyComponents(vec(2, 3, 4), vec(-1, 0.5f, 2)), -2, 1.5f, 8);
  }

  @Test
  void normalize_givesLengthOne() {
    assertVector(calc.normalize(vec(3, 4, 0)), 0.6f, 0.8f, 0);
    assertVector(calc.normalize(vec(0, 0, -7)), 0, 0, -1);
  }

  @Test
  void normalize_zeroVector_staysZero_withLengthZero() {
    Value normalized = calc.normalize(vec(0, 0, 0));

    assertVector(normalized, 0, 0, 0);
    assertThat((float) calc.length(normalized).getObject(Type.floatingpoint)).isEqualTo(0f);
  }

  @Test
  void length_ofVector() {
    assertThat((float) calc.length(vec(3, 4, 0)).getObject(Type.floatingpoint)).isCloseTo(5f, within(EPS));
    assertThat((float) calc.length(vec(2, 3, 6)).getObject(Type.floatingpoint)).isCloseTo(7f, within(EPS));
  }

  @Test
  void results_areUnchangeable_andOperandsStayUnchanged() {
    Vector original = new Vector(3, 4, 0);
    Value operand = new Value(Type.vector, original);

    Value scaled = calc.multiplication(operand, new Value(Type.integer, 2));
    Value normalized = calc.normalize(operand);

    assertThat(vectorOf(scaled).isMutable()).isFalse();
    assertThat(vectorOf(normalized).isMutable()).isFalse();
    assertThat(original.getX()).isEqualTo(3f);
    assertThat(original.getY()).isEqualTo(4f);
    assertThat(original.isNormalized()).isFalse();
  }

  @Test
  void mixedOrMissingOperands_giveNothing() {
    Value nothing = Value.getValueNothing();

    assertThat(calc.addition(vec(1, 2, 3), new Value(Type.integer, 1)).isValid()).isFalse();
    assertThat(calc.addition(new Value(Type.integer, 1), vec(1, 2, 3)).isValid()).isFalse();
    assertThat(calc.addition(vec(1, 2, 3), nothing).isValid()).isFalse();
    assertThat(calc.multiplication(vec(1, 2, 3), vec(1, 2, 3)).isValid()).isFalse();
    assertThat(calc.multiplyComponents(vec(1, 2, 3), new Value(Type.floatingpoint, 1f)).isValid()).isFalse();
    assertThat(calc.normalize(nothing).isValid()).isFalse();
    assertThat(calc.length(new Value(Type.integer, 3)).isValid()).isFalse();
  }

  @Test
  void compareEqual_worksForVectorsNow() {
    assertThat(calc.compareEqual(vec(1, 2, 3), vec(1, 2, 3)).isTrue()).isTrue();
    assertThat(calc.compareEqual(vec(1, 2, 3), vec(1, 2, 4)).isTrue()).isFalse();
    assertThat(calc.compareNotEqual(vec(1, 2, 3), vec(1, 2, 4)).isTrue()).isTrue();
  }
}
