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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.functions.FunctionByMatrix;
import org.socialworld.calculation.functions.FunctionByMatrix_Matrix;
import org.socialworld.calculation.functions.FunctionByTable_Type;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.tools.dct.AttributeMatrixGenerator.Drive;
import org.socialworld.tools.dct.AttributeMatrixGenerator.GeneratedMatrix;

/** Nothing is written to the database. */
public class AttributeMatrixGeneratorTest {

  private static final int N = Attribute.NUMBER_OF_ATTRIBUTES;
  private static final int HUNGER = Attribute.hunger.getIndex();
  private static final int TIREDNESS = Attribute.tiredness.getIndex();
  private static final int POWER = Attribute.power.getIndex();
  private static final int MOOD = Attribute.mood.getIndex();

  private static final String SENTENCE =
      "Der alte Fischer zog sein Boot auf den Strand und sah lange auf das Meer hinaus, bevor es dunkel wurde. ";

  private static String longText(int countShares) {
    StringBuilder text = new StringBuilder();
    while (TextDie.onlyLetters(text.toString()).length() < countShares * 2 + 10) {
      text.append(SENTENCE);
    }
    return text.toString();
  }

  private static List<GeneratedMatrix> generateAll() {
    AttributeCharacterCatalog characters = AttributeCharacterCatalog.load();
    AttributeCouplingCatalog couplings = AttributeCouplingCatalog.load();
    String text = longText(AttributeMatrixGenerator.countShares(characters, couplings));
    return AttributeMatrixGenerator.generate(characters, couplings, new TextDie(text, 1, 2));
  }

  private static GeneratedMatrix ofCharacter(List<GeneratedMatrix> matrices, int number) {
    return matrices.stream().filter(m -> m.getCharacter().getNumber() == number).findFirst().get();
  }

  private static int[] step(FunctionByMatrix_Matrix matrix, int[] values) {
    ValueArrayList arguments = new ValueArrayList();
    arguments.add(new Value(Type.attributeArray, new AttributeArray(values)));
    arguments.add(new Value(Type.integer, FunctionByMatrix_Matrix.CALCULATION_MODE_MATRIX_X_VECTOR_SIMPLE));
    AttributeArray result = (AttributeArray) new FunctionByMatrix(matrix).calculate(arguments).getObject(Type.attributeArray);
    int[] newValues = new int[N];
    for (int i = 0; i < N; i++) {
      newValues[i] = result.get(i);
    }
    return newValues;
  }

  /** The target values, rested. */
  private static int[] atRest(GeneratedMatrix matrix) {
    int[] values = matrix.getTargets();
    values[HUNGER] = 3000;
    values[TIREDNESS] = 3000;
    return values;
  }

  // ---------------------------------------------------------------------------
  // slots
  // ---------------------------------------------------------------------------

  @Test
  void variants_fillAllPlaces_everyCharacterHasOne_commonOnesHaveMore() {
    int[] variants = AttributeMatrixGenerator.variantsPerCharacter(100, 199);

    assertThat(java.util.Arrays.stream(variants).sum()).isEqualTo(199);
    assertThat(java.util.Arrays.stream(variants).min().getAsInt()).isGreaterThanOrEqualTo(1);
    assertThat(variants[0]).isGreaterThan(variants[99]);
  }

  @Test
  void generator_fillsEveryGaussIndexOnce_withOwnFuncIDs() {
    List<GeneratedMatrix> matrices = generateAll();
    Set<Integer> gaussIndices = new HashSet<>();
    Set<Integer> funcIDs = new HashSet<>();

    for (GeneratedMatrix matrix : matrices) {
      gaussIndices.add(matrix.getGaussIndex());
      funcIDs.add(matrix.getFuncID());
    }

    assertThat(matrices).hasSize(199);
    assertThat(gaussIndices).hasSize(199).allMatch(index -> index >= -99 && index <= 99);
    assertThat(funcIDs).hasSize(199).allMatch(id -> id >= AttributeMatrixGenerator.FIRST_FUNC_ID && id < 1000);
  }

  @Test
  void generator_needsEnoughText() {
    AttributeCharacterCatalog characters = AttributeCharacterCatalog.load();
    AttributeCouplingCatalog couplings = AttributeCouplingCatalog.load();

    assertThatThrownBy(() -> AttributeMatrixGenerator.generate(characters, couplings, new TextDie("zu kurz", 1, 2)))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void generator_givesTheSameMatricesForTheSameText() {
    List<GeneratedMatrix> first = generateAll();
    List<GeneratedMatrix> second = generateAll();

    for (int i = 0; i < first.size(); i += 17) {
      assertThat(second.get(i).getTargets()).isEqualTo(first.get(i).getTargets());
      assertThat(second.get(i).getShare(HUNGER, POWER)).isEqualTo(first.get(i).getShare(HUNGER, POWER));
    }
  }

  @Test
  void variantsOfACharacter_differ() {
    List<GeneratedMatrix> matrices = generateAll();

    assertThat(matrices.get(0).getCharacter()).isSameAs(matrices.get(1).getCharacter());
    assertThat(matrices.get(0).getTargets()).isNotEqualTo(matrices.get(1).getTargets());
  }

  // ---------------------------------------------------------------------------
  // meaning
  // ---------------------------------------------------------------------------

  @Test
  void targets_lieInTheBandsOfTheCharacter() {
    for (GeneratedMatrix matrix : generateAll()) {
      for (Attribute attribute : Attribute.values()) {
        if (attribute == Attribute.ignore || attribute == Attribute.hunger || attribute == Attribute.tiredness) continue;
        AttributeLevel level = matrix.getCharacter().getLevel(attribute.getIndex());
        assertThat(matrix.getTargets()[attribute.getIndex()]).isBetween(level.getLowest(), level.getHighest());
      }
    }
  }

  @Test
  void atRest_theRealMatrixKeepsEveryPersonOnTheTargets() {
    List<GeneratedMatrix> matrices = generateAll();
    for (int i = 0; i < matrices.size(); i += 7) {
      GeneratedMatrix generated = matrices.get(i);
      FunctionByMatrix_Matrix matrix = generated.toMatrix();
      int[] values = atRest(generated);
      for (int minute = 0; minute < 60; minute++) {
        values = step(matrix, values);
        values[HUNGER] = 3000;
        values[TIREDNESS] = 3000;
      }
      for (Attribute attribute : Attribute.values()) {
        if (attribute == Attribute.ignore || attribute == Attribute.hunger || attribute == Attribute.tiredness) continue;
        assertThat(values[attribute.getIndex()]).as("no. %d %s", generated.getCharacter().getNumber(), attribute)
            .isBetween(generated.getTargets()[attribute.getIndex()] - 2, generated.getTargets()[attribute.getIndex()] + 2);
      }
    }
  }

  @Test
  void hungerAndTiredness_pressPowerAndMoodDown() {
    GeneratedMatrix generated = generateAll().get(0);
    int[] rested = step(generated.toMatrix(), atRest(generated));
    int[] strained = atRest(generated);
    strained[HUNGER] = 9000;
    strained[TIREDNESS] = 9000;

    strained = step(generated.toMatrix(), strained);

    assertThat(strained[POWER]).isLessThan(rested[POWER]);
    assertThat(strained[MOOD]).isLessThan(rested[MOOD]);
  }

  @Test
  void signature_ofTheAscetic_liftsTheMoodWhenHungry() {
    GeneratedMatrix ascetic = ofCharacter(generateAll(), 86);

    assertThat(ascetic.getShare(HUNGER, MOOD)).isPositive();
    assertThat(ascetic.getFunction(HUNGER, MOOD)).isEqualTo(FunctionByTable_Type.hinge_high.getIndex());
  }

  @Test
  void anchors_useTheConstantFunction_notTheDiagonal() {
    GeneratedMatrix generated = generateAll().get(0);

    for (int column = 0; column < N; column++) {
      int anchors = 0;
      for (int row = 0; row < N; row++) {
        if (generated.getFunction(row, column) == FunctionByTable_Type.horizontal_max.getIndex()) {
          assertThat(row).isNotEqualTo(column);
          anchors++;
        }
      }
      assertThat(anchors).as("column %d", column).isEqualTo(1);
    }
  }

  // ---------------------------------------------------------------------------
  // hunger and tiredness rise with time
  // ---------------------------------------------------------------------------

  private static int afterSteps(Drive drive, int steps) {
    int value = 0;
    for (int i = 0; i < steps; i++) {
      value = Math.max(0, Math.min(9999, (int) (drive.growth * value + drive.constant + 0.5f)));
    }
    return value;
  }

  @Test
  void hunger_reachesItsReferencePoints() {
    assertThat(afterSteps(AttributeMatrixGenerator.HUNGER_DRIVE, 8 * 60)).isGreaterThan(5000);
    assertThat(afterSteps(AttributeMatrixGenerator.HUNGER_DRIVE, 12 * 60)).isGreaterThanOrEqualTo(9000);
  }

  @Test
  void tiredness_reachesItsReferencePoints() {
    assertThat(afterSteps(AttributeMatrixGenerator.TIREDNESS_DRIVE, 16 * 60)).isGreaterThan(5000);
    assertThat(afterSteps(AttributeMatrixGenerator.TIREDNESS_DRIVE, 24 * 60)).isGreaterThanOrEqualTo(9000);
  }

  @Test
  void hungerRises_inTheRealMatrix() {
    GeneratedMatrix generated = generateAll().get(0);
    int[] values = atRest(generated);
    values[HUNGER] = 0;

    int[] after = step(generated.toMatrix(), values);

    assertThat(after[HUNGER]).isPositive();
  }

  // ---------------------------------------------------------------------------
  // signatures file
  // ---------------------------------------------------------------------------

  @Test
  void wrongSignatureLine_isRejected() {
    assertThatThrownBy(() -> AttributeCouplingCatalog.parse("86;K3;oops;1")).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> AttributeCouplingCatalog.parse("86;K3")).isInstanceOf(IllegalArgumentException.class);
  }

  // ---------------------------------------------------------------------------
  // writer (in memory only, nothing is written to the database)
  // ---------------------------------------------------------------------------

  private static final class RecordingStore implements AttributeMatrixStore {
    final List<String> calls = new ArrayList<>();
    @Override public void deleteMapping(int aa, int drawn) { calls.add("deleteMapping"); }
    @Override public void insertMapping(int aa, int drawn, int acm) { calls.add("insertMapping"); }
    @Override public void deleteGaussIndex(int gaussIndex) { calls.add("deleteGauss"); }
    @Override public void deleteCell(int funcID, int row, int column) { calls.add("deleteCell"); }
    @Override public void deleteMatrix(int funcID) { calls.add("deleteMatrix"); }
    @Override public void insertMatrix(int funcID, int rows, int columns, int typeShare, int typeOffset) { calls.add("insertMatrix"); }
    @Override public void insertCell(int funcID, int row, int column, float share, int function, float offset, int inputType) {
      assertThat(row).isBetween(1, N);
      assertThat(column).isBetween(1, N);
      calls.add("insertCell");
    }
    @Override public void insertGaussIndex(int gaussIndex, int funcID) { calls.add("insertGauss"); }
  }

  @Test
  void writer_keepsTheOrderOfTheForeignKeys() {
    RecordingStore store = new RecordingStore();
    List<GeneratedMatrix> generated = generateAll();
    List<AttributeMatrixMapping.Entry> mapping = AttributeMatrixMapping.generate(generated);

    AttributeMatrixWriter.write(generated, mapping, store);

    String[] order = {"deleteMapping", "deleteGauss", "deleteCell", "deleteMatrix", "insertMatrix", "insertCell", "insertGauss", "insertMapping"};
    int[] counts = {39601, 199, 199 * 81, 199, 199, 199 * 81, 199, 39601};
    int from = 0;
    for (int i = 0; i < order.length; i++) {
      String expected = order[i];
      assertThat(store.calls.subList(from, from + counts[i])).as(expected).allMatch(expected::equals);
      from += counts[i];
    }
    assertThat(store.calls).hasSize(from);
  }

  // ---------------------------------------------------------------------------
  // mapping from the drawn indices to the matrix
  // ---------------------------------------------------------------------------

  @Test
  void mapping_coversAllCombinationsOnce() {
    List<AttributeMatrixMapping.Entry> mapping = AttributeMatrixMapping.generate(generateAll());
    Set<Integer> keys = new HashSet<>();

    for (AttributeMatrixMapping.Entry entry : mapping) {
      keys.add((entry.gaussIndexAA + 99) * 199 + entry.gaussIndexACMDrawn + 99);
    }

    assertThat(mapping).hasSize(199 * 199);
    assertThat(keys).hasSize(199 * 199);
  }

  @Test
  void mapping_alwaysLeadsToAMatrixOfTheCharacterOfTheArray() {
    List<GeneratedMatrix> generated = generateAll();
    java.util.Map<Integer, GeneratedMatrix> byGauss = new java.util.HashMap<>();
    generated.forEach(matrix -> byGauss.put(matrix.getGaussIndex(), matrix));

    for (AttributeMatrixMapping.Entry entry : AttributeMatrixMapping.generate(generated)) {
      assertThat(byGauss.get(entry.gaussIndexACM).getCharacter().getNumber()).isEqualTo(Math.abs(entry.gaussIndexAA) + 1);
    }
  }

  private static Set<Integer> reachedBy(List<AttributeMatrixMapping.Entry> mapping, int aa) {
    Set<Integer> reached = new HashSet<>();
    for (AttributeMatrixMapping.Entry entry : mapping) {
      if (entry.gaussIndexAA == aa) reached.add(entry.gaussIndexACM);
    }
    return reached;
  }

  @Test
  void mapping_reachesAllVariantsOfACharacter() {
    List<GeneratedMatrix> generated = generateAll();
    List<AttributeMatrixMapping.Entry> mapping = AttributeMatrixMapping.generate(generated);
    long variantsOfFarmer = generated.stream().filter(m -> m.getCharacter().getNumber() == 1).count();
    long variantsOfLast = generated.stream().filter(m -> m.getCharacter().getNumber() == 100).count();

    assertThat(reachedBy(mapping, 0)).hasSize((int) variantsOfFarmer);
    assertThat(reachedBy(mapping, 99)).hasSize((int) variantsOfLast);
  }

  @Test
  void mapping_nearZeroGivesTheSameVariantForBothSigns() {
    List<AttributeMatrixMapping.Entry> mapping = AttributeMatrixMapping.generate(generateAll());
    int plus = -1000;
    int minus = -2000;

    for (AttributeMatrixMapping.Entry entry : mapping) {
      if (entry.gaussIndexAA == 0 && entry.gaussIndexACMDrawn == 1) plus = entry.gaussIndexACM;
      if (entry.gaussIndexAA == 0 && entry.gaussIndexACMDrawn == -1) minus = entry.gaussIndexACM;
    }

    assertThat(plus).isEqualTo(minus);
  }
}
