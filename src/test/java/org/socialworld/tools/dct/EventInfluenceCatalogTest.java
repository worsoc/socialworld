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
import org.socialworld.core.EventType;

/**
 * Reading event_situations.csv and event_influences.csv, the sources of the generated event influence rules.
 */
public class EventInfluenceCatalogTest {

  @Test
  void situations_fromTheResource_coverTheRelevantEventTypesOnce() {
    EventInfluenceSituations situations = EventInfluenceSituations.load();

    assertThat(situations.size()).isEqualTo(91);
    assertThat(situations.get(EventType.selfSleep).getSituation()).isEqualTo(1);
    assertThat(situations.get(EventType.candidatesWeaponClub).getSituation()).isEqualTo(20);
    assertThat(situations.get(EventType.candidatesWeaponClub).getFactor()).isEqualTo(1.5f);
    assertThat(situations.get(EventType.percipientMoveWalk).getFactor()).isZero();
    assertThat(situations.get(EventType.targetEat)).as("reaches only items").isNull();
  }

  @Test
  void situations_rejectAnUnknownEventTypeAndAnEventTypeTwice() {
    assertThatThrownBy(() -> EventInfluenceSituations.parse("1;noSuchEvent;1.0"))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> EventInfluenceSituations.parse("1;selfSleep;1.0\n2;selfSleep;1.0"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void catalog_fromTheResource_hasTheCharacters1To100WithAll25Situations() {
    EventInfluenceCatalog catalog = EventInfluenceCatalog.load();

    assertThat(catalog.getCharacters()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
        21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
        41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60,
        61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
        81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100);
    for (int character = 1; character <= 100; character++) {
      for (int situation = 1; situation <= 25; situation++) {
        assertThat(catalog.getLines(character, situation)).as("character %d, situation %d", character, situation).isNotEmpty();
      }
    }
  }

  @Test
  void catalog_lineWithoutAttribute_onlyRecordsTheSentence() {
    List<EventInfluenceCatalog.Line> lines = EventInfluenceCatalog.load().getLines(1, 9);

    assertThat(lines).hasSize(1);
    assertThat(lines.get(0).getAttribute()).isNull();
    assertThat(lines.get(0).getSentence()).isNotEmpty();
  }

  @Test
  void catalog_readsTheFourSteps() {
    EventInfluenceCatalog catalog = EventInfluenceCatalog.parse("1;21;A sentence.;mood;-10;-100;-200;-200");
    EventInfluenceCatalog.Line line = catalog.getLines(1, 21).get(0);

    assertThat(line.getAttribute()).isEqualTo(Attribute.mood);
    assertThat(new int[] {line.getStep(0), line.getStep(1), line.getStep(2), line.getStep(3)})
        .containsExactly(-10, -100, -200, -200);
  }

  @Test
  void catalog_rejectsStepsWithBothSignsAndUnknownAttributes() {
    assertThatThrownBy(() -> EventInfluenceCatalog.parse("1;21;A sentence.;mood;-10;0;5;0"))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> EventInfluenceCatalog.parse("1;21;A sentence.;anger;-10;0;0;0"))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
