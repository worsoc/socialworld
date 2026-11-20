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
package org.socialworld.actions.move;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.ActionMode;

/**
 * The velocity of a move action from event to event (movement concept G2, step M5).
 */
public class ActionMoveVelocityTest {

  @Test
  void uniformMove_keepsItsVelocity() {
    float v = ActionMove.startVelocity(ActionMode.walk, 100f);
    for (int event = 0; event < 5; event++) {
      v = ActionMove.nextVelocity(v, 0f, ActionMode.walk.getEventIntervalSeconds());
    }
    assertThat(v).isEqualTo(1400f);
  }

  @Test
  void acceleratedMove_growsByAccelerationTimesInterval() {
    float v = 1400f;
    v = ActionMove.nextVelocity(v, 1400f, 1f);
    assertThat(v).isEqualTo(2800f);
    v = ActionMove.nextVelocity(v, 1400f, 1f);
    assertThat(v).isEqualTo(4200f);
  }

  @Test
  void braking_isANegativeAcceleration() {
    assertThat(ActionMove.nextVelocity(1000f, -250f, 2f)).isEqualTo(500f);
  }

  @Test
  void startVelocity_normalIntensity_isTheBaseSpeedOfTheMode() {
    assertThat(ActionMove.startVelocity(ActionMode.sneak, 100f)).isEqualTo(500f);
    assertThat(ActionMove.startVelocity(ActionMode.walk, 100f)).isEqualTo(1400f);
    assertThat(ActionMove.startVelocity(ActionMode.run, 100f)).isEqualTo(4000f);
    assertThat(ActionMove.startVelocity(ActionMode.swim, 100f)).isEqualTo(700f);
    assertThat(ActionMove.startVelocity(ActionMode.fly, 100f)).isEqualTo(3000f);
  }

  @Test
  void startVelocity_scalesWithIntensity() {
    assertThat(ActionMove.startVelocity(ActionMode.walk, 120f)).isCloseTo(1680f, within(0.01f));
    assertThat(ActionMove.startVelocity(ActionMode.run, 80f)).isCloseTo(3200f, within(0.01f));
  }

  @Test
  void startVelocity_tempoFactorIsLimited() {
    // intensity 23 (power 0) and 173 (power 9999) of the ERD rules
    assertThat(ActionMove.startVelocity(ActionMode.walk, 23f)).isEqualTo(700f);
    assertThat(ActionMove.startVelocity(ActionMode.walk, 173f)).isEqualTo(2100f);
    assertThat(ActionMove.startVelocity(ActionMode.run, 0f)).isEqualTo(2000f);
  }

  @Test
  void startVelocity_noIntensity_isNormalTempo() {
    assertThat(ActionMove.startVelocity(ActionMode.walk, Float.NaN)).isEqualTo(1400f);
  }

  @Test
  void startVelocity_nonMoveModes_isZero() {
    assertThat(ActionMove.startVelocity(ActionMode.jump, 100f)).isEqualTo(0f);
    assertThat(ActionMove.startVelocity(ActionMode.eat, 100f)).isEqualTo(0f);
  }
}
