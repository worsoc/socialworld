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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.Attribute;
import org.socialworld.datasource.mariaDB.DatabaseAssumptions;
import org.socialworld.datasource.pool.GaussPoolAttributeArray;
import org.socialworld.datasource.pool.GaussPoolAttributeCalculatorMatrix;
import org.socialworld.tools.dct.AttributeCharacterOverview.Entry;
import org.socialworld.tools.dct.AttributeCharacterOverview.StoredArray;
import org.socialworld.tools.dct.AttributeDataReader.MappingEntry;
import org.socialworld.tools.dct.AttributeDataReader.StoredMatrix;
import org.socialworld.tools.dct.AttributeMatrixGenerator.GeneratedMatrix;

/** Only the last test reads the database, nothing is written. */
public class AttributeCharacterOverviewTest {

  private static final int N = Attribute.NUMBER_OF_ATTRIBUTES;
  private static final int CAPACITY_AA = GaussPoolAttributeArray.CAPACITY_GPAA_ARRAY;
  private static final int PLACES_ACM = 2 * GaussPoolAttributeCalculatorMatrix.CAPACITY_GPACM_ARRAY - 1;
  private static final String TWO_CHARACTERS = "1;first;a;o;o;o;o;o;o;o;o;o\n2;second;b;o;o;o;o;o;o;o;o;o\n";

  private static final class FakeReader implements AttributeDataReader {
    final Map<Integer, int[]> arrays = new TreeMap<>();
    final Map<Integer, Integer> indices = new TreeMap<>();
    final Map<Integer, StoredMatrix> matrices = new TreeMap<>();
    final List<MappingEntry> mapping = new ArrayList<>();

    @Override public Map<Integer, int[]> readArrays() { return arrays; }
    @Override public Map<Integer, Integer> readMatrixIndices() { return indices; }
    @Override public Map<Integer, StoredMatrix> readMatrices() { return matrices; }
    @Override public List<MappingEntry> readMapping() { return mapping; }

    void matrix(int gaussIndex, int funcID) {
      indices.put(gaussIndex, funcID);
      matrices.put(funcID, new StoredMatrix(funcID, new float[N * N], new int[N * N]));
    }

    void map(int aa, int drawn, int acm) {
      mapping.add(new MappingEntry(aa, drawn, acm));
    }
  }

  /** Character 1 has the matrices 200 and 201, character 2 has 202. */
  private static FakeReader twoCharacters() {
    FakeReader reader = new FakeReader();
    reader.arrays.put(0, new int[N]);
    reader.arrays.put(1, new int[N]);
    reader.arrays.put(-1, new int[N]);
    reader.matrix(-1, 200);
    reader.matrix(0, 201);
    reader.matrix(1, 202);
    reader.map(0, 0, -1);
    reader.map(0, 50, 0);
    reader.map(1, 0, 1);
    reader.map(-1, 0, 1);
    return reader;
  }

  private static AttributeCharacterOverview read(AttributeDataReader reader) {
    return AttributeCharacterOverview.read(AttributeCharacterCatalog.parse(TWO_CHARACTERS), reader);
  }

  private static List<Integer> funcIDs(Entry entry) {
    return entry.getMatrices().stream().map(StoredMatrix::getFuncID).collect(Collectors.toList());
  }

  @Test
  void read_givesEveryCharacterItsArraysPositiveIndexFirst() {
    List<Entry> entries = read(twoCharacters()).getEntries();

    assertThat(entries).hasSize(2);
    assertThat(entries.get(0).getCharacter().getName()).isEqualTo("first");
    assertThat(entries.get(0).getArrays()).extracting(StoredArray::getGaussIndex).containsExactly(0);
    assertThat(entries.get(1).getArrays()).extracting(StoredArray::getGaussIndex).containsExactly(1, -1);
  }

  @Test
  void read_givesEveryCharacterItsMatrices() {
    List<Entry> entries = read(twoCharacters()).getEntries();

    assertThat(funcIDs(entries.get(0))).containsExactly(200, 201);
    assertThat(funcIDs(entries.get(1))).containsExactly(202);
  }

  @Test
  void read_sortsTheMatricesByTheSmallestDrawnDistance() {
    FakeReader reader = twoCharacters();
    reader.mapping.clear();
    reader.map(0, 7, -1);
    reader.map(0, -3, 0);
    reader.map(1, 0, 1);

    assertThat(funcIDs(read(reader).getEntries().get(0))).containsExactly(201, 200);
  }

  @Test
  void read_failsWithoutAnArrayForACharacter() {
    FakeReader reader = twoCharacters();
    reader.arrays.remove(1);
    reader.arrays.remove(-1);

    assertThatThrownBy(() -> read(reader)).isInstanceOf(IllegalStateException.class).hasMessageContaining("no array");
  }

  @Test
  void read_failsWithoutAMatrixForACharacter() {
    FakeReader reader = twoCharacters();
    reader.mapping.removeIf(entry -> Math.abs(entry.getGaussIndexAA()) == 1);

    assertThatThrownBy(() -> read(reader)).isInstanceOf(IllegalStateException.class).hasMessageContaining("no matrix");
  }

  @Test
  void read_failsWhenAMatrixBelongsToTwoCharacters() {
    FakeReader reader = twoCharacters();
    reader.map(1, 1, -1);

    assertThatThrownBy(() -> read(reader)).isInstanceOf(IllegalStateException.class).hasMessageContaining("belongs to");
  }

  @Test
  void read_failsForAMappingToAnUnknownGaussIndex() {
    FakeReader reader = twoCharacters();
    reader.map(0, 2, 77);

    assertThatThrownBy(() -> read(reader)).isInstanceOf(IllegalStateException.class).hasMessageContaining("77");
  }

  @Test
  void read_failsForAMatrixWithoutCells() {
    FakeReader reader = twoCharacters();
    reader.matrices.remove(201);

    assertThatThrownBy(() -> read(reader)).isInstanceOf(IllegalStateException.class).hasMessageContaining("201");
  }

  @Test
  void read_failsForAnArrayOfAnUnknownCharacter() {
    FakeReader reader = twoCharacters();
    reader.arrays.put(5, new int[N]);

    assertThatThrownBy(() -> read(reader)).isInstanceOf(IllegalStateException.class).hasMessageContaining("gauss index 5");
  }

  private static long distanceFromBandCentres(GeneratedMatrix matrix) {
    long distance = 0;
    for (Attribute attribute : Attribute.values()) {
      if (attribute == Attribute.ignore || attribute == Attribute.hunger || attribute == Attribute.tiredness) continue;
      AttributeLevel level = matrix.getCharacter().getLevel(attribute.getIndex());
      distance += Math.abs(matrix.getTargets()[attribute.getIndex()] - (level.getLowest() + level.getHighest()) / 2);
    }
    return distance;
  }

  /** What the generators hand over to the writer, as the reader would give it back. */
  private static FakeReader fromGenerator(List<GeneratedMatrix> generated) {
    FakeReader reader = new FakeReader();
    for (int aa = -CAPACITY_AA + 1; aa < CAPACITY_AA; aa++) {
      reader.arrays.put(aa, new int[N]);
    }
    for (GeneratedMatrix matrix : generated) {
      float[] shares = new float[N * N];
      int[] functions = new int[N * N];
      for (int row = 0; row < N; row++) {
        for (int column = 0; column < N; column++) {
          shares[row * N + column] = matrix.getShare(row, column);
          functions[row * N + column] = matrix.getFunction(row, column);
        }
      }
      reader.indices.put(matrix.getGaussIndex(), matrix.getFuncID());
      reader.matrices.put(matrix.getFuncID(), new StoredMatrix(matrix.getFuncID(), shares, functions));
    }
    for (AttributeMatrixMapping.Entry entry : AttributeMatrixMapping.generate(generated)) {
      reader.map(entry.gaussIndexAA, entry.gaussIndexACMDrawn, entry.gaussIndexACM);
    }
    return reader;
  }

  @Test
  void read_givesTheVariantsOfTheGeneratorFromTypicalToUnusual() {
    AttributeCharacterCatalog characters = AttributeCharacterCatalog.load();
    AttributeCouplingCatalog couplings = AttributeCouplingCatalog.load();
    StringBuilder text = new StringBuilder();
    while (TextDie.onlyLetters(text.toString()).length() < AttributeMatrixGenerator.countShares(characters, couplings) * 2 + 10) {
      text.append("Der alte Fischer zog sein Boot auf den Strand und sah lange auf das Meer hinaus, bevor es dunkel wurde. ");
    }
    List<GeneratedMatrix> generated = AttributeMatrixGenerator.generate(characters, couplings, new TextDie(text.toString(), 1, 2));

    List<Entry> entries = AttributeCharacterOverview.read(characters, fromGenerator(generated)).getEntries();

    for (Entry entry : entries) {
      List<Integer> expected = generated.stream()
          .filter(matrix -> matrix.getCharacter().getNumber() == entry.getCharacter().getNumber())
          .sorted(Comparator.comparingLong(AttributeCharacterOverviewTest::distanceFromBandCentres)
              .thenComparingInt(GeneratedMatrix::getVariant))
          .map(GeneratedMatrix::getFuncID)
          .collect(Collectors.toList());
      assertThat(funcIDs(entry)).as("character %d", entry.getCharacter().getNumber()).isEqualTo(expected);
    }
  }

  @Test
  void read_fitsTheRealTables() {
    DatabaseAssumptions.assumeAttributeTablesAreFilled();
    AttributeCharacterOverview overview = AttributeCharacterOverview.read(AttributeCharacterCatalog.load(), new TablePoolAttributeDataReader());

    List<Entry> entries = overview.getEntries();
    assertThat(entries).hasSize(100);
    int countMatrices = 0;
    for (Entry entry : entries) {
      assertThat(entry.getArrays()).hasSize(entry.getCharacter().getNumber() == 1 ? 1 : 2);
      assertThat(entry.getMatrices()).isNotEmpty();
      countMatrices += entry.getMatrices().size();
    }
    assertThat(countMatrices).isEqualTo(PLACES_ACM);
  }
}
