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

/**
 * A human or an animal gets the state-to-action type of the character of its attribute array,
 * character number - 1, as the influence type (A7). Without database.
 */
class CreateAnimalState2ActionTypeTest {

  @Test
  void bothSidesOfTheGaussPool_leadToTheSameCharacter() {
    assertThat(CreateAnimal.state2ActionTypeOfCharacter(0)).isEqualTo(0);
    assertThat(CreateAnimal.state2ActionTypeOfCharacter(3)).isEqualTo(3);
    assertThat(CreateAnimal.state2ActionTypeOfCharacter(-3)).isEqualTo(3);
    assertThat(CreateAnimal.state2ActionTypeOfCharacter(-99)).isEqualTo(99);
  }

  @Test
  void theTypeIsTheInfluenceType() {
    for (int gaussIndex = -99; gaussIndex <= 99; gaussIndex++) {
      assertThat(CreateAnimal.state2ActionTypeOfCharacter(gaussIndex))
          .isEqualTo(CreateAnimal.influenceTypesOfCharacter(gaussIndex)[0]);
    }
  }

  @Test
  void gaussIndexOutsideThePool_staysAtTheLastType() {
    assertThat(CreateAnimal.state2ActionTypeOfCharacter(100)).isEqualTo(99);
    assertThat(CreateAnimal.state2ActionTypeOfCharacter(-100)).isEqualTo(99);
  }
}
