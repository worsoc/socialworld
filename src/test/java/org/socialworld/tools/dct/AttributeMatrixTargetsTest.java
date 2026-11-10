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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.functions.FunctionByTable_Type;
import org.socialworld.datasource.mariaDB.DatabaseAssumptions;
import org.socialworld.tools.dct.AttributeCharacterOverview.Entry;
import org.socialworld.tools.dct.AttributeDataReader.StoredMatrix;
import org.socialworld.tools.dct.AttributeMatrixGenerator.GeneratedMatrix;

/** Only the last test reads the database, nothing is written. */
public class AttributeMatrixTargetsTest {

  private static final int N = Attribute.NUMBER_OF_ATTRIBUTES;
  private static final int MOOD = Attribute.mood.getIndex();
  private static final int COURAGE = Attribute.courage.getIndex();
  private static final int MORALS = Attribute.morals.getIndex();
  private static final int HUNGER = Attribute.hunger.getIndex();
  private static final int TIREDNESS = Attribute.tiredness.getIndex();
  private static final int POWER = Attribute.power.getIndex();
  private static final int IDENTICAL = FunctionByTable_Type.identical.getIndex();
  private static final int CONSTANT = FunctionByTable_Type.horizontal_max.getIndex();
  private static final int HINGE_HIGH = FunctionByTable_Type.hinge_high.getIndex();
  private static final float MAX = 9999f;

  private static final AttributeCharacter CHARACTER = AttributeCharacterCatalog.parse("1;a;b;o;o;o;o;o;o;o;o;o\n").getCharacters().get(0);

  private final float[] shares = new float[N * N];
  private final int[] functions = new int[N * N];

  private void cell(int row, int column, float share, int function) {
    shares[row * N + column] = share;
    functions[row * N + column] = function;
  }

  /** Every attribute returns with rate 0.02, hunger and tiredness grow. */
  private void diagonals() {
    for (int i = 0; i < N; i++) {
      cell(i, i, (i == HUNGER || i == TIREDNESS) ? 1.001f : 0.98f, IDENTICAL);
    }
  }

  /** A constant drive which is the target for the rate 0.02. */
  private void constant(int row, int column, int target) {
    cell(row, column, 0.02f * target / MAX, CONSTANT);
  }

  private void constantsForAll(int target) {
    for (int column = 0; column < N; column++) {
      if (column != HUNGER && column != TIREDNESS) {
        constant(column == MOOD ? COURAGE : MOOD, column, target);
      }
    }
  }

  private int[] compute() {
    return AttributeMatrixTargets.compute(new StoredMatrix(200, shares, functions), CHARACTER);
  }

  @Test
  void compute_givesTheConstantDividedByTheReturnRate() {
    diagonals();
    constantsForAll(6000);

    int[] targets = compute();

    assertThat(targets[MOOD]).isBetween(5999, 6001);
    assertThat(targets[POWER]).isBetween(5999, 6001);
  }

  @Test
  void compute_hasNoTargetForHungerAndTiredness() {
    diagonals();
    constantsForAll(6000);

    int[] targets = compute();

    assertThat(targets[HUNGER]).isEqualTo(AttributeMatrixTargets.NO_TARGET);
    assertThat(targets[TIREDNESS]).isEqualTo(AttributeMatrixTargets.NO_TARGET);
  }

  @Test
  void compute_includesACouplingBetweenAttributesWithTargets() {
    diagonals();
    constantsForAll(4000);
    // courage is 8000, the hinge gives about 5000 of it
    constant(MOOD, COURAGE, 8000);
    cell(COURAGE, MORALS, 0.002f, HINGE_HIGH);

    int[] targets = compute();

    int hinge = (8000 - 6000) * 9999 / (9999 - 6000);
    int expected = Math.round(4000f + 0.002f * hinge / 0.02f);
    assertThat(targets[COURAGE]).isBetween(7999, 8001);
    assertThat(targets[MORALS]).isBetween(expected - 2, expected + 2);
  }

  @Test
  void compute_ignoresACouplingOfRestedHunger() {
    diagonals();
    constantsForAll(6000);
    cell(HUNGER, POWER, -0.004f, HINGE_HIGH);

    assertThat(compute()[POWER]).isBetween(5999, 6001);
  }

  @Test
  void compute_failsWithoutAReturnRate() {
    diagonals();
    constantsForAll(6000);
    cell(MOOD, MOOD, 1.0f, IDENTICAL);

    assertThatThrownBy(this::compute).isInstanceOf(IllegalStateException.class).hasMessageContaining("does not return");
  }

  @Test
  void compute_failsForAnUnknownFunction() {
    diagonals();
    constantsForAll(6000);
    cell(COURAGE, MORALS, 0.002f, 150);

    assertThatThrownBy(this::compute).isInstanceOf(IllegalStateException.class).hasMessageContaining("unknown function 150");
  }

  @Test
  void compute_findsTheTargetsOfTheGenerator() {
    AttributeCharacterCatalog characters = AttributeCharacterCatalog.load();
    AttributeCouplingCatalog couplings = AttributeCouplingCatalog.load();
    StringBuilder text = new StringBuilder();
    while (TextDie.onlyLetters(text.toString()).length() < AttributeMatrixGenerator.countShares(characters, couplings) * 2 + 10) {
      text.append("Der alte Fischer zog sein Boot auf den Strand und sah lange auf das Meer hinaus, bevor es dunkel wurde. ");
    }
    List<GeneratedMatrix> generated = AttributeMatrixGenerator.generate(characters, couplings, new TextDie(text.toString(), 1, 2));

    for (GeneratedMatrix matrix : generated) {
      float[] matrixShares = new float[N * N];
      int[] matrixFunctions = new int[N * N];
      for (int row = 0; row < N; row++) {
        for (int column = 0; column < N; column++) {
          matrixShares[row * N + column] = matrix.getShare(row, column);
          matrixFunctions[row * N + column] = matrix.getFunction(row, column);
        }
      }
      int[] targets = AttributeMatrixTargets.compute(
          new StoredMatrix(matrix.getFuncID(), matrixShares, matrixFunctions), matrix.getCharacter());

      for (int i = 0; i < N; i++) {
        if (i != HUNGER && i != TIREDNESS) {
          assertThat(targets[i]).as("func_id %d attribute %d", matrix.getFuncID(), i)
              .isBetween(matrix.getTargets()[i] - 1, matrix.getTargets()[i] + 1);
        }
      }
    }
  }

  @Test
  void compute_staysInTheBandsForTheRealTables() {
    DatabaseAssumptions.assumeAttributeTablesAreFilled();
    List<Entry> entries = AttributeCharacterOverview.read(AttributeCharacterCatalog.load(), new TablePoolAttributeDataReader()).getEntries();

    for (Entry entry : entries) {
      AttributeCharacter character = entry.getCharacter();
      for (StoredMatrix matrix : entry.getMatrices()) {
        int[] targets = AttributeMatrixTargets.compute(matrix, character);
        for (int i = 0; i < N; i++) {
          if (i != HUNGER && i != TIREDNESS) {
            assertThat(targets[i]).as("func_id %d attribute %d", matrix.getFuncID(), i)
                .isBetween(character.getLevel(i).getLowest() - 2, character.getLevel(i).getHighest() + 2);
          }
        }
      }
    }
  }
}
