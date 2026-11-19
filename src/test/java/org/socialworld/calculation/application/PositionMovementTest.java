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
package org.socialworld.calculation.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.core.EventType;

/**
 * The vector arithmetic of the technical position movement (movement concept 2026-11-19, M2 and M3)
 * and the technical event types, without simulation.
 */
public class PositionMovementTest {

  private static final float EPS = 0.001f;

  private static void assertVector(Vector v, float x, float y, float z) {
    assertThat(v.getX()).isCloseTo(x, within(EPS));
    assertThat(v.getY()).isCloseTo(y, within(EPS));
    assertThat(v.getZ()).isCloseTo(z, within(EPS));
  }

  @Test
  void unit_hasLengthOne_andTheZeroVectorStaysZero() {
    assertVector(PositionCalculator.unit(new Vector(3, 4, 0)), 0.6f, 0.8f, 0);
    assertVector(PositionCalculator.unit(new Vector(0, 0, 0)), 0, 0, 0);
    assertThat(PositionCalculator.length(PositionCalculator.unit(new Vector(0, 0, 0)))).isEqualTo(0f);
  }

  @Test
  void impulse_isAddedOnce_toThePassiveVelocity() {
    Vector passive = new Vector(100, 0, 0);

    // direction (0, 2, 0) is used as unit direction, strength 200 mm/s
    Vector afterPush = PositionCalculator.addImpulse(passive, new Vector(0, 2, 0), 200);

    assertVector(afterPush, 100, 200, 0);
    // the passive velocity is the starting value, it is not changed in place
    assertVector(passive, 100, 0, 0);
  }

  @Test
  void impulseWithoutDirection_changesNothing() {
    assertVector(PositionCalculator.addImpulse(new Vector(5, 5, 0), new Vector(0, 0, 0), 200), 5, 5, 0);
  }

  @Test
  void friction_halvesThePassiveVelocityPerSecond_andEndsInRest() {
    assertThat(PositionCalculator.FRICTION_PER_SECOND).isEqualTo(0.5f);

    Vector passive = new Vector(400, 0, 0);
    passive = PositionCalculator.damped(passive);
    assertVector(passive, 200, 0, 0);

    for (int second = 0; second < 20; second++) {
      passive = PositionCalculator.damped(passive);
    }
    // below MIN_SPEED it is rest, not an endless creep
    assertThat(passive.is000()).isTrue();
  }

  @Test
  void stepMovesBySumTimesOneSecond() {
    Vector active = new Vector(100, 0, 0);
    Vector passive = new Vector(0, 50, 0);

    Vector step = PositionCalculator.scaled(PositionCalculator.sumOf(active, passive), PositionCalculator.STEP_SECONDS);

    assertVector(step, 100, 50, 0);
  }

  @Test
  void technicalEventTypes_lieOutsideAllCategories() {
    for (EventType type : new EventType[] { EventType.positionStep, EventType.activeMoveStop }) {
      assertThat(type.isTechnical()).as(type.name()).isTrue();
      assertThat(type.isEventToCauserItself()).as(type.name()).isFalse();
      assertThat(type.isEventToTarget()).as(type.name()).isFalse();
      assertThat(type.isEventToCandidates()).as(type.name()).isFalse();
      assertThat(type.isEventToPercipient()).as(type.name()).isFalse();
      // they never pass the gate of the EventMaster route, they use the bypass
      assertThat(type.isRelevantForChangingPosition()).as(type.name()).isFalse();
    }
    assertThat(EventType.positionStep.getIndex()).isEqualTo(128);
    assertThat(EventType.activeMoveStop.getIndex()).isEqualTo(256);
    assertThat(EventType.selfMoveWalk.isTechnical()).isFalse();
  }

  @Test
  void eventKinds_selfMove_candidatesMove_andSleepThroughTheGate() {
    assertThat(EventType.selfMoveWalk.isSelfMove()).isTrue();
    assertThat(EventType.selfMoveFly.isSelfMove()).isTrue();
    assertThat(EventType.selfSleep.isSelfMove()).isFalse();
    assertThat(EventType.candidatesMoveWalk.isCandidatesMove()).isTrue();
    assertThat(EventType.candidatesMoveFly.isCandidatesMove()).isTrue();
    assertThat(EventType.selfMoveWalk.isCandidatesMove()).isFalse();

    // sleeping sets both velocities to 0, so selfSleep must reach the PositionCalculator
    assertThat(EventType.selfSleep.isRelevantForChangingPosition()).isTrue();
  }

  @Test
  void ownCandidatesMove_isIgnored_bystandersAreStillPushed() {
    // the mover itself: only its selfMove counts
    assertThat(PositionCalculator.isOwnMoveSeenAsCandidate(EventType.candidatesMoveWalk, true)).isTrue();
    assertThat(PositionCalculator.isOwnMoveSeenAsCandidate(EventType.candidatesMoveFly, true)).isTrue();
    // a bystander is pushed
    assertThat(PositionCalculator.isOwnMoveSeenAsCandidate(EventType.candidatesMoveWalk, false)).isFalse();
    // other candidate events of the causer are not affected by this rule
    assertThat(PositionCalculator.isOwnMoveSeenAsCandidate(EventType.candidatesPunchRightFistStraight, true)).isFalse();
    assertThat(PositionCalculator.isOwnMoveSeenAsCandidate(EventType.selfMoveWalk, true)).isFalse();
  }
}
