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
import static org.assertj.core.api.Assumptions.assumeThat;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.functions.FunctionByTable_Type;
import org.socialworld.datasource.pool.GaussPoolAttributeArray;
import org.socialworld.datasource.pool.GaussPoolAttributeCalculatorMatrix;
import org.socialworld.tools.dct.AttributeDataReader.MappingEntry;
import org.socialworld.tools.dct.AttributeDataReader.StoredMatrix;

/**
 * Reads the real tables. The database is only read. Without a database or without data the tests are skipped.
 */
public class TablePoolAttributeDataReaderTest {

  private static final int N = Attribute.NUMBER_OF_ATTRIBUTES;
  private static final int PLACES_AA = 2 * GaussPoolAttributeArray.CAPACITY_GPAA_ARRAY - 1;
  private static final int PLACES_ACM = 2 * GaussPoolAttributeCalculatorMatrix.CAPACITY_GPACM_ARRAY - 1;

  private static Map<Integer, int[]> arrays;
  private static Map<Integer, Integer> indices;
  private static Map<Integer, StoredMatrix> matrices;
  private static List<MappingEntry> mapping;

  private static void read() {
    if (arrays == null) {
      try {
        AttributeDataReader reader = new TablePoolAttributeDataReader();
        mapping = reader.readMapping();
        matrices = reader.readMatrices();
        indices = reader.readMatrixIndices();
        arrays = reader.readArrays();
      } catch (RuntimeException | LinkageError e) {
        assumeThat(false).as("no database available: " + e).isTrue();
      }
    }
    assumeThat(arrays).as("swgauss_aa").isNotEmpty();
    assumeThat(matrices).as("swpool_matrix_rowcol").isNotEmpty();
  }

  @Test
  void readArrays_givesAllValuesInRange() {
    read();
    assertThat(arrays).hasSize(PLACES_AA);
    for (Map.Entry<Integer, int[]> entry : arrays.entrySet()) {
      assertThat(Math.abs(entry.getKey())).isLessThan(GaussPoolAttributeArray.CAPACITY_GPAA_ARRAY);
      assertThat(entry.getValue()).hasSize(N);
      for (int value : entry.getValue()) {
        assertThat(value).isBetween(0, AttributeArray.ATTRIBUTE_VALUE_MAX);
      }
    }
  }

  @Test
  void readMatrices_givesEveryMatrixOfTheGaussIndices() {
    read();
    assertThat(indices).hasSize(PLACES_ACM);
    assertThat(matrices.keySet()).isEqualTo(new HashSet<>(indices.values()));
  }

  @Test
  void readMatrices_keepsTheDiagonalAsIdenticalOfEveryAttribute() {
    read();
    for (StoredMatrix matrix : matrices.values()) {
      for (int i = 0; i < N; i++) {
        assertThat(matrix.getFunction(i, i)).as("func_id %d attribute %d", matrix.getFuncID(), i)
            .isEqualTo(FunctionByTable_Type.identical.getIndex());
        assertThat(matrix.getShare(i, i)).as("func_id %d attribute %d", matrix.getFuncID(), i).isGreaterThan(0.9f);
      }
    }
  }

  @Test
  void readMatrices_rowIsTheSourceAndColumnTheTarget() {
    read();
    StoredMatrix matrix = matrices.values().iterator().next();
    int hunger = Attribute.hunger.getIndex();
    int power = Attribute.power.getIndex();

    assertThat(matrix.getFunction(hunger, power)).isEqualTo(FunctionByTable_Type.hinge_high.getIndex());
    assertThat(matrix.getShare(hunger, power)).isNegative();
    assertThat(matrix.getFunction(power, hunger)).isNotEqualTo(FunctionByTable_Type.hinge_high.getIndex());
  }

  @Test
  void readMapping_givesAllCombinationsAndOnlyKnownMatrices() {
    read();
    assertThat(mapping).hasSize(PLACES_AA * PLACES_ACM);
    for (MappingEntry entry : mapping) {
      assertThat(indices).containsKey(entry.getGaussIndexACM());
    }
  }
}
