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
  void friction_restBelowFiftyMillimetersPerSecond() {
    assertThat(PositionCalculator.MIN_SPEED).isEqualTo(50f);
    // 120 halves to 60: still moving; 60 halves to 30: rest
    Vector passive = PositionCalculator.damped(new Vector(120, 0, 0));
    assertVector(passive, 60, 0, 0);
    assertThat(PositionCalculator.damped(passive).is000()).isTrue();
  }

  @Test
  void bystanderPush_isAFifthOfTheVelocityOfTheMover() {
    assertThat(PositionCalculator.bystanderPush(500f)).isEqualTo(100f);
    assertThat(PositionCalculator.bystanderPush(1400f)).isEqualTo(280f);
    assertThat(PositionCalculator.bystanderPush(4000f)).isEqualTo(800f);
    assertThat(PositionCalculator.bystanderPush(0f)).isEqualTo(0f);
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
    assertThat(EventType.positionStep.getIndex()).isEqualTo(512);
    assertThat(EventType.activeMoveStop.getIndex()).isEqualTo(513);
    assertThat(EventType.selfMoveWalk.isTechnical()).isFalse();
  }

  @Test
  void technicalEventTypes_haveTheirOwnBlockBehindAllCategories() {
    assertThat(EventType.TECHNICAL_FIRST).isEqualTo(EventType.MAX_EVENT_TYPE);
    assertThat(EventType.TECHNICAL_END).isEqualTo(528);
    for (EventType type : EventType.values()) {
      boolean inBlock = type.getIndex() >= EventType.TECHNICAL_FIRST && type.getIndex() < EventType.TECHNICAL_END;
      // technical exactly in the block, and every other type fits into the pools (index < MAX_EVENT_TYPE)
      assertThat(type.isTechnical()).as(type.name()).isEqualTo(inBlock);
      if (!type.isTechnical()) assertThat(type.getIndex()).as(type.name()).isLessThan(EventType.MAX_EVENT_TYPE);
    }
    // the first places of the blocks are empty again, as 0 (nothing) and 384
    for (int index : new int[] { 128, 256, 384 }) {
      assertThat(EventType.getEventType(index)).as("index " + index).isIn(EventType.nothing, null);
    }
    assertThat(EventType.getEventType(512)).isEqualTo(EventType.positionStep);
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
    assertThat(PositionCalculator.isOwnEventSeenAsCandidate(EventType.candidatesMoveWalk, true)).isTrue();
    assertThat(PositionCalculator.isOwnEventSeenAsCandidate(EventType.candidatesMoveFly, true)).isTrue();
    // a bystander is pushed
    assertThat(PositionCalculator.isOwnEventSeenAsCandidate(EventType.candidatesMoveWalk, false)).isFalse();
    // the own selfMove is not affected by this rule
    assertThat(PositionCalculator.isOwnEventSeenAsCandidate(EventType.selfMoveWalk, true)).isFalse();
  }

  @Test
  void ownCandidatesBlow_isIgnored_whoIsHitIsPushed() {
    // the hitter does not push itself (P3, B5)
    assertThat(PositionCalculator.isOwnEventSeenAsCandidate(EventType.candidatesPunchRightFistStraight, true)).isTrue();
    assertThat(PositionCalculator.isOwnEventSeenAsCandidate(EventType.candidatesWeaponClub, true)).isTrue();
    // who is hit is pushed
    assertThat(PositionCalculator.isOwnEventSeenAsCandidate(EventType.candidatesPunchLeftFistUpward, false)).isFalse();
    assertThat(PositionCalculator.isOwnEventSeenAsCandidate(EventType.candidatesWeaponLeftStab, false)).isFalse();
    // other candidate events of the causer, e.g. its scream, are not affected
    assertThat(PositionCalculator.isOwnEventSeenAsCandidate(EventType.candidatesSayScream, true)).isFalse();
  }

  @Test
  void blowEventKinds_candidatesBlowAndWeapon() {
    for (int index = 304; index <= 310; index++) {
      EventType type = EventType.getEventType(index);
      assertThat(type.isCandidatesBlow()).as(type.name()).isTrue();
      assertThat(type.isCandidatesWeapon()).as(type.name()).isTrue();
    }
    for (int index = 312; index <= 317; index++) {
      EventType type = EventType.getEventType(index);
      assertThat(type.isCandidatesBlow()).as(type.name()).isTrue();
      assertThat(type.isCandidatesWeapon()).as(type.name()).isFalse();
    }
    assertThat(EventType.candidatesMoveRun.isCandidatesBlow()).isFalse();
    assertThat(EventType.targetPunchRightFistStraight.isCandidatesBlow()).isFalse();
  }

  @Test
  void onlyTheCandidatesEventOfABlowPushes_notTheTargetEvent() {
    // B4: else a target in the cone would be pushed twice
    for (int index = 176; index <= 189; index++) {
      EventType type = EventType.getEventType(index);
      if (type == EventType.nothing) continue;
      assertThat(type.isRelevantForChangingPosition()).as(type.name()).isFalse();
    }
    assertThat(EventType.candidatesPunchRightFistStraight.isRelevantForChangingPosition()).isTrue();
    assertThat(EventType.candidatesWeaponClub.isRelevantForChangingPosition()).isTrue();
  }

  @Test
  void blowImpulse_isAShareOfTheEventIntensity_withAnUpperLimit() {
    // intensity 100, power 5000: event intensity 5000
    assertThat(PositionCalculator.blowImpulse(EventType.candidatesPunchRightFistStraight, 5000f)).isEqualTo(500f);
    assertThat(PositionCalculator.blowImpulse(EventType.candidatesWeaponClub, 5000f)).isEqualTo(750f);
    // at most 3000 mm/s, never negative, nothing for no intensity
    assertThat(PositionCalculator.blowImpulse(EventType.candidatesWeaponClub, 1000000f)).isEqualTo(3000f);
    assertThat(PositionCalculator.blowImpulse(EventType.candidatesPunchLeftFistUpward, -100f)).isEqualTo(0f);
    assertThat(PositionCalculator.blowImpulse(EventType.candidatesPunchLeftFistUpward, Float.NaN)).isEqualTo(0f);
  }

  @Test
  void blowPushesAwayFromTheHitter_notAlongItsView() {
    // the hitter looks east (1,0,0), the one hit stands north-east of it: pushed north-east
    Vector away = PositionCalculator.awayFromCauser(new Vector(300, 300, 0), new Vector(1, 0, 0));
    assertVector(away, 0.70710677f, 0.70710677f, 0);
    // standing where the hitter stood: along the blow
    assertVector(PositionCalculator.awayFromCauser(new Vector(0, 0, 0), new Vector(2, 0, 0)), 1, 0, 0);
  }
}
