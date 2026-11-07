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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.tools.dct.AttributeArrayGenerator.GeneratedArray;

public class AttributeArrayGeneratorTest {

  private static final String SENTENCE =
      "Der alte Fischer zog sein Boot auf den Strand und sah lange auf das Meer hinaus. ";

  private static String longText(int countLetters) {
    StringBuilder text = new StringBuilder();
    while (TextDie.onlyLetters(text.toString()).length() < countLetters) {
      text.append(SENTENCE);
    }
    return text.toString();
  }

  private static List<GeneratedArray> generateAll() {
    AttributeCharacterCatalog catalog = AttributeCharacterCatalog.load();
    String text = longText(AttributeArrayGenerator.countLettersNeeded(catalog));
    return AttributeArrayGenerator.generate(catalog, new TextDie(text), 1);
  }

  // ---------------------------------------------------------------------------
  // text die
  // ---------------------------------------------------------------------------

  @Test
  void onlyLetters_foldsUmlautsAndDropsTheRest() {
    assertThat(TextDie.onlyLetters("Äpfel, Öl & Füße!")).isEqualTo("apfelolfuse");
  }

  @Test
  void die_readsEveryThirdLetterAsShareOfTheAlphabet() {
    TextDie die = new TextDie("a..xx z yy");

    assertThat(die.countRemaining()).isEqualTo(2);
    assertThat(die.next()).isEqualTo(0.0);
    assertThat(die.next()).isEqualTo(1.0);
    assertThat(die.countRemaining()).isZero();
    assertThatThrownBy(die::next).isInstanceOf(IllegalStateException.class);
  }

  // ---------------------------------------------------------------------------
  // levels
  // ---------------------------------------------------------------------------

  @Test
  void level_placesShareInsideItsBand() {
    assertThat(AttributeLevel.average.valueFor(0.0)).isEqualTo(38);
    assertThat(AttributeLevel.average.valueFor(1.0)).isEqualTo(62);
    assertThat(AttributeLevel.extremelyHigh.valueFor(1.0)).isEqualTo(AttributeArray.ATTRIBUTE_VALUE_MAX);
    assertThat(AttributeLevel.extremelyLow.valueFor(0.0)).isZero();
  }

  @Test
  void level_isFoundByItsCode() {
    for (AttributeLevel level : AttributeLevel.values()) {
      assertThat(AttributeLevel.fromCode(level.getCode())).isSameAs(level);
    }
    assertThatThrownBy(() -> AttributeLevel.fromCode("++++")).isInstanceOf(IllegalArgumentException.class);
  }

  // ---------------------------------------------------------------------------
  // catalog
  // ---------------------------------------------------------------------------

  @Test
  void catalog_hasHundredCharactersInOrder() {
    AttributeCharacterCatalog catalog = AttributeCharacterCatalog.load();

    assertThat(catalog.size()).isEqualTo(100);
    for (int i = 0; i < catalog.size(); i++) {
      assertThat(catalog.getCharacters().get(i).getNumber()).isEqualTo(i + 1);
    }
    assertThat(catalog.getCharacters().get(30).getName()).isEqualTo("village smith");
    assertThat(catalog.getCharacters().get(30).getLevel(Attribute.power.getIndex()))
        .isEqualTo(AttributeLevel.veryHigh);
  }

  @Test
  void catalog_rejectsWrongNumberOfLevels() {
    assertThatThrownBy(() -> AttributeCharacterCatalog.parse("1;farmer;sketch;o;o;o"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  // ---------------------------------------------------------------------------
  // generator
  // ---------------------------------------------------------------------------

  @Test
  void generator_fillsEveryGaussIndexOnce() {
    List<GeneratedArray> arrays = generateAll();
    Set<Integer> gaussIndices = new HashSet<>();
    Set<Integer> aaIDs = new HashSet<>();

    for (GeneratedArray array : arrays) {
      gaussIndices.add(array.getGaussIndex());
      aaIDs.add(array.getAaID());
      assertThat(Math.abs(array.getGaussIndex())).isEqualTo(array.getCharacter().getNumber() - 1);
    }

    assertThat(arrays).hasSize(199);
    assertThat(gaussIndices).hasSize(199).allMatch(index -> index >= -99 && index <= 99);
    assertThat(aaIDs).hasSize(199).allMatch(id -> id >= 1 && id <= 199);
  }

  @Test
  void generator_keepsEveryValueInsideTheBandOfItsLevel() {
    for (GeneratedArray array : generateAll()) {
      int[] values = array.getValues();
      assertThat(values).hasSize(Attribute.NUMBER_OF_ATTRIBUTES);

      for (int i = 0; i < values.length; i++) {
        AttributeLevel level = array.getCharacter().getLevel(i);
        assertThat(values[i]).as("no. %d attribute %d", array.getCharacter().getNumber(), i)
            .isBetween(level.getLowest(), level.getHighest());
      }
    }
  }

  @Test
  void generator_givesTheSameValuesForTheSameText() {
    List<GeneratedArray> first = generateAll();
    List<GeneratedArray> second = generateAll();

    for (int i = 0; i < first.size(); i++) {
      assertThat(second.get(i).getValues()).isEqualTo(first.get(i).getValues());
    }
  }

  @Test
  void generator_rejectsTooShortText() {
    AttributeCharacterCatalog catalog = AttributeCharacterCatalog.load();
    int needed = AttributeArrayGenerator.countLettersNeeded(catalog);
    String text = TextDie.onlyLetters(longText(needed)).substring(0, needed - 1);

    assertThatThrownBy(() -> AttributeArrayGenerator.generate(catalog, new TextDie(text), 1))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void generator_needsExactlyTheCountedLetters() {
    AttributeCharacterCatalog catalog = AttributeCharacterCatalog.load();
    String letters = TextDie.onlyLetters(longText(AttributeArrayGenerator.countLettersNeeded(catalog)))
        .substring(0, AttributeArrayGenerator.countLettersNeeded(catalog));

    TextDie die = new TextDie(letters);
    AttributeArrayGenerator.generate(catalog, die, 1);

    assertThat(die.countRemaining()).isZero();
  }

  @Test
  void generator_rejectsAaIDsOutsideThePool() {
    AttributeCharacterCatalog catalog = AttributeCharacterCatalog.load();
    TextDie die = new TextDie(longText(AttributeArrayGenerator.countLettersNeeded(catalog)));

    assertThatThrownBy(() -> AttributeArrayGenerator.generate(catalog, die, 0))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> AttributeArrayGenerator.generate(catalog, die, 900))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void withValues_keepsCharacterGaussIndexAndAaID() {
    GeneratedArray array = generateAll().get(30);
    int[] adjusted = {1, 2, 3, 4, 5, 6, 7, 8, 99};

    GeneratedArray changed = array.withValues(adjusted);
    adjusted[0] = 50;

    assertThat(changed.getCharacter()).isSameAs(array.getCharacter());
    assertThat(changed.getGaussIndex()).isEqualTo(array.getGaussIndex());
    assertThat(changed.getAaID()).isEqualTo(array.getAaID());
    assertThat(changed.getValues()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 99);
    assertThat(changed.getTuple()).isEqualTo("(1,2,3,4,5,6,7,8,99)");
    assertThatThrownBy(() -> array.withValues(new int[] {1, 2})).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void tuple_isReadBackByAttributeArray() {
    GeneratedArray array = generateAll().get(5);
    AttributeArray read = new AttributeArray(array.getTuple(), Attribute.NUMBER_OF_ATTRIBUTES);

    for (int i = 0; i < Attribute.NUMBER_OF_ATTRIBUTES; i++) {
      assertThat(read.get(i)).isEqualTo(array.getValues()[i]);
    }
  }

  // ---------------------------------------------------------------------------
  // writer (in memory only, nothing is written to the database)
  // ---------------------------------------------------------------------------

  @Test
  void writer_keepsTheOrderOfTheForeignKey() {
    InMemoryAttributeArrayStore store = new InMemoryAttributeArrayStore();

    AttributeArrayWriter.write(generateAll(), store);

    List<String> calls = store.getCalls();
    int gauss = 199;
    int attributes = 199 * 9;
    assertThat(calls).hasSize(2 * gauss + 2 * attributes);
    assertThat(calls.subList(0, gauss)).allMatch(call -> call.startsWith("deleteGaussIndex "));
    assertThat(calls.subList(gauss, gauss + attributes)).allMatch(call -> call.startsWith("deleteAttribute "));
    assertThat(calls.subList(gauss + attributes, gauss + 2 * attributes))
        .allMatch(call -> call.startsWith("insertAttribute "));
    assertThat(calls.subList(gauss + 2 * attributes, calls.size()))
        .allMatch(call -> call.startsWith("insertGaussIndex "));
  }

  @Test
  void writer_storesAllValuesAndGaussIndices() {
    List<GeneratedArray> arrays = generateAll();
    InMemoryAttributeArrayStore store = new InMemoryAttributeArrayStore();

    AttributeArrayWriter.write(arrays, store);

    assertThat(store.countAttributes()).isEqualTo(199 * 9);
    assertThat(store.countGaussIndices()).isEqualTo(199);
    for (GeneratedArray array : arrays) {
      assertThat(store.getAaID(array.getGaussIndex())).isEqualTo(array.getAaID());
      for (int i = 0; i < 9; i++) {
        assertThat(store.getAttribute(array.getAaID(), i + 1)).isEqualTo(array.getValues()[i]);
      }
    }
  }

  @Test
  void writer_replacesExistingRecordsAndKeepsOthers() {
    InMemoryAttributeArrayStore store = new InMemoryAttributeArrayStore();
    store.insertAttribute(1, 1, 7);
    store.insertAttribute(2, 1, 8);
    store.insertAttribute(500, 1, 33);
    store.insertGaussIndex(0, 500);
    store.insertGaussIndex(5, 2);

    List<GeneratedArray> arrays = generateAll();
    AttributeArrayWriter.write(arrays, store);

    assertThat(store.getAttribute(1, 1)).isEqualTo(arrays.get(0).getValues()[0]);
    assertThat(store.getAaID(0)).isEqualTo(1);
    assertThat(store.getAttribute(500, 1)).isEqualTo(33);
  }

  @Test
  void inMemoryStore_knowsTheForeignKey() {
    InMemoryAttributeArrayStore store = new InMemoryAttributeArrayStore();

    assertThatThrownBy(() -> store.insertGaussIndex(0, 1)).isInstanceOf(IllegalStateException.class);
    store.insertAttribute(1, 1, 50);
    store.insertGaussIndex(0, 1);
    assertThatThrownBy(() -> store.deleteAttribute(1, 1)).isInstanceOf(IllegalStateException.class);
  }
}
