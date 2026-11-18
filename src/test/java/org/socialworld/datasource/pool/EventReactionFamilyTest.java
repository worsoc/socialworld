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

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.core.EventType;

/**
 * The reaction types are drawn per event family, uniformly from 0 to 115, independent of the character
 * (erd_regeln_konzept.md, E4). Without database.
 */
class EventReactionFamilyTest {

  @Test
  void thereAre116ReactionTypes() {
    assertThat(GaussPoolReactionType.CAPACITY_GPRT_ARRAY).isEqualTo(116);
  }

  @Test
  void everyFamilyGetsADrawnType_theOtherEventTypesKeepType1() {
    int[] types = EventReactionFamily.drawReactionTypes(new Random(7));

    assertThat(types).hasSize(EventType.MAX_EVENT_TYPE);
    for (EventReactionFamily family : EventReactionFamily.values()) {
      assertThat(types[family.getEventTypes().get(0).getIndex()]).isBetween(0, 115);
    }
    assertThat(types[EventType.selfSleep.getIndex()]).isEqualTo(1);
    assertThat(types[EventType.candidatesMoveWalk.getIndex()]).isEqualTo(1);
  }

  @Test
  void theFamiliesAreDrawnSeparately_andAllTypesOccur() {
    Set<Integer> seen = new HashSet<>();
    boolean familiesDiffer = false;
    Random random = new Random(11);
    for (int human = 0; human < 2000; human++) {
      int[] types = EventReactionFamily.drawReactionTypes(random);
      int scream = types[EventType.candidatesSayScream.getIndex()];
      int lookedAt = types[EventType.targetExamineByLook.getIndex()];
      if (scream != lookedAt) familiesDiffer = true;
      seen.add(scream);
    }
    assertThat(familiesDiffer).isTrue();
    assertThat(seen).hasSize(116);
  }

  @Test
  void drawingDoesNotChangeTheGaussPool() {
    EventReactionFamily.drawReactionTypes(new Random(3));

    assertThat(GaussPoolReactionType.getInstance().getReactionTypes(0)[EventType.candidatesSayScream.getIndex()]).isEqualTo(1);
  }
}
