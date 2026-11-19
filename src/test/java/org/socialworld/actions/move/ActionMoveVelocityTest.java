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

import org.junit.jupiter.api.Test;
import org.socialworld.actions.ActionMode;

/**
 * The velocity of a move action from event to event (movement concept G2, step M5).
 */
public class ActionMoveVelocityTest {

  @Test
  void uniformMove_keepsItsVelocity() {
    float v = ActionMove.INITIAL_SPEED;
    for (int event = 0; event < 5; event++) {
      v = ActionMove.nextVelocity(v, 0f, ActionMode.walk.getEventIntervalSeconds());
    }
    assertThat(v).isEqualTo(100f);
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
  void startSpeed_isThePlaceholder() {
    assertThat(ActionMove.INITIAL_SPEED).isEqualTo(100f);
  }
}
