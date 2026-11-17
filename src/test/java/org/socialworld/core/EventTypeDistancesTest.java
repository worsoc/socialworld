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
package org.socialworld.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Distances of the event types: how far an event reaches, and how close a candidate must be
 * to be moved by it (concept EID 7.2 and code point C1).
 */
public class EventTypeDistancesTest {

  @Test
  void seeingSomeoneMove_reachesFartherThanBeingReallyClose() {
    assertThat(EventType.percipientMoveWalk.getEffectDistance()).isEqualTo(20000f);
    assertThat(EventType.percipientMoveRun.getEffectDistance()).isEqualTo(30000f);
    assertThat(EventType.percipientMoveSneak.getEffectDistance()).isEqualTo(10000f);
    assertThat(EventType.percipientMoveJump.getEffectDistance()).isEqualTo(20000f);
    assertThat(EventType.percipientMoveSwim.getEffectDistance()).isEqualTo(20000f);
    assertThat(EventType.percipientMoveFly.getEffectDistance()).isEqualTo(50000f);

    EventType[][] pairs = {
        {EventType.percipientMoveWalk, EventType.candidatesMoveWalk},
        {EventType.percipientMoveRun, EventType.candidatesMoveRun},
        {EventType.percipientMoveSneak, EventType.candidatesMoveSneak},
        {EventType.percipientMoveJump, EventType.candidatesMoveJump},
        {EventType.percipientMoveSwim, EventType.candidatesMoveSwim},
        {EventType.percipientMoveFly, EventType.candidatesMoveFly}};
    for (EventType[] pair : pairs) {
      assertThat(pair[0].getEffectDistance()).as("%s farther than %s", pair[0], pair[1])
          .isGreaterThan(pair[1].getEffectDistance());
    }
  }

  @Test
  void positionChangeDistance_onlyForTheCandidatesThatCanBeMoved() {
    assertThat(EventType.candidatesMoveWalk.getPositionChangeDistance()).isEqualTo(500f);
    assertThat(EventType.candidatesMoveRun.getPositionChangeDistance()).isEqualTo(500f);
    assertThat(EventType.candidatesWeaponClub.getPositionChangeDistance())
        .as("who is hit is pushed").isEqualTo(EventType.candidatesWeaponClub.getEffectDistance());
    assertThat(EventType.candidatesPunchLeftFistStraight.getPositionChangeDistance())
        .isEqualTo(EventType.candidatesPunchLeftFistStraight.getEffectDistance());
    assertThat(EventType.candidatesSayScream.getPositionChangeDistance()).isZero();
    assertThat(EventType.selfMoveWalk.getPositionChangeDistance()).as("self events are not checked").isZero();

    for (EventType type : EventType.values()) {
      if (type.getPositionChangeDistance() > 0) {
        assertThat(type.isEventToCandidates()).as("%s", type).isTrue();
        assertThat(type.isRelevantForChangingPosition()).as("%s", type).isTrue();
      }
    }
  }

  @Test
  void relevantForChangingPosition_onlyTheListedRanges() {
    assertThat(EventType.selfMoveWalk.isRelevantForChangingPosition()).isTrue();
    assertThat(EventType.candidatesMoveWalk.isRelevantForChangingPosition()).isTrue();
    assertThat(EventType.candidatesWeaponClub.isRelevantForChangingPosition()).isTrue();
    assertThat(EventType.selfSleep.isRelevantForChangingPosition()).isFalse();
    assertThat(EventType.candidatesSayScream.isRelevantForChangingPosition()).isFalse();
    assertThat(EventType.percipientExistsDistance5000.isRelevantForChangingPosition()).isFalse();
    assertThat(EventType.percipientMoveWalk.isRelevantForChangingPosition()).as("seeing moves nobody").isFalse();
  }
}
