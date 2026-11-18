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
package org.socialworld.datasource.createObjects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.core.EventType;

/**
 * A7 of the EID concept: a human or an animal gets the influence type of the character of its attribute array,
 * the same for every event type. Without database.
 */
class CreateAnimalInfluenceTypeTest {

  @Test
  void gaussIndexZero_isTheFarmer_forEveryEventType() {
    int[] types = CreateAnimal.influenceTypesOfCharacter(0);

    assertThat(types).hasSize(EventType.MAX_EVENT_TYPE);
    assertThat(types).containsOnly(0);
  }

  @Test
  void bothSidesOfTheGaussPool_leadToTheSameCharacter() {
    // gauss index +-3 is character 4, the maid, influence type 3
    assertThat(CreateAnimal.influenceTypesOfCharacter(3)).containsOnly(3);
    assertThat(CreateAnimal.influenceTypesOfCharacter(-3)).containsOnly(3);
    // the rarest character, 100 child prodigy
    assertThat(CreateAnimal.influenceTypesOfCharacter(99)).containsOnly(99);
    assertThat(CreateAnimal.influenceTypesOfCharacter(-99)).containsOnly(99);
  }

  @Test
  void sleepAndPunch_getTheSameInfluenceType() {
    int[] types = CreateAnimal.influenceTypesOfCharacter(-68);

    assertThat(types[EventType.selfSleep.getIndex()]).isEqualTo(68);
    assertThat(types[EventType.candidatesPunchLeftFistStraight.getIndex()]).isEqualTo(68);
  }

  @Test
  void gaussIndexOutsideThePool_staysAtTheLastInfluenceType() {
    // mapGaussToIndex gives 100 only for a gauss value of exactly 4.0
    assertThat(CreateAnimal.influenceTypesOfCharacter(100)).containsOnly(99);
    assertThat(CreateAnimal.influenceTypesOfCharacter(-100)).containsOnly(99);
  }
}
