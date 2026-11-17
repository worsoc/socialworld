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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TickObjectCooldownTest {

  private static final int OBJECTS = 200;
  private static final int PERIOD = TickObjectCooldown.ATTRIBUTE_MATRIX_STEP_SECONDS;
  private static final int TYPE = TickObjectCooldown.TYPE_REFRESH_ATTRIBUTES;

  /** @return the ticks in which the object got a release, trying once in every tick */
  private static List<Integer> releases(TickObjectCooldown cooldown, int objectId, int ticks) {
    List<Integer> result = new ArrayList<>();
    for (int tick = 0; tick < ticks; tick++) {
      cooldown.setTick(tick);
      if (cooldown.checkAndApplyPeriod(objectId, TYPE, PERIOD)) {
        result.add(tick);
      }
    }
    return result;
  }

  @Test
  void period_isOneMinute() {
    assertThat(PERIOD).isEqualTo(60);
  }

  @Test
  void objectGetsOneReleasePerPeriod_atItsOffset() {
    assertThat(releases(new TickObjectCooldown(OBJECTS), 5, 190)).containsExactly(5, 65, 125, 185);
    assertThat(releases(new TickObjectCooldown(OBJECTS), 60, 130)).containsExactly(0, 60, 120);
  }

  @Test
  void objects_areSpreadOverThePeriod() {
    assertThat(releases(new TickObjectCooldown(OBJECTS), 1, 60)).containsExactly(1);
    assertThat(releases(new TickObjectCooldown(OBJECTS), 2, 60)).containsExactly(2);
    assertThat(releases(new TickObjectCooldown(OBJECTS), 59, 60)).containsExactly(59);
  }

  @Test
  void aMissedSecond_isMadeUpInTheSamePeriod() {
    TickObjectCooldown cooldown = new TickObjectCooldown(OBJECTS);

    cooldown.setTick(30);
    assertThat(cooldown.checkAndApplyPeriod(5, TYPE, PERIOD)).isTrue();
    assertThat(cooldown.checkAndApplyPeriod(5, TYPE, PERIOD)).isFalse();
    cooldown.setTick(64);
    assertThat(cooldown.checkAndApplyPeriod(5, TYPE, PERIOD)).isFalse();
    cooldown.setTick(65);
    assertThat(cooldown.checkAndApplyPeriod(5, TYPE, PERIOD)).isTrue();
  }

  @Test
  void objectOutsideOfTheRange_isLetThrough() {
    TickObjectCooldown cooldown = new TickObjectCooldown(OBJECTS);

    assertThat(cooldown.checkAndApplyPeriod(-1, TYPE, PERIOD)).isTrue();
    assertThat(cooldown.checkAndApplyPeriod(OBJECTS, TYPE, PERIOD)).isTrue();
  }

  @Test
  void otherCooldownTypes_areNotDisturbed() {
    TickObjectCooldown cooldown = new TickObjectCooldown(OBJECTS);

    cooldown.setTick(5);
    assertThat(cooldown.checkAndApplyPeriod(5, TYPE, PERIOD)).isTrue();
    assertThat(cooldown.checkAndApplyCooldown(5, TickObjectCooldown.TYPE_REFRESH, 1)).isTrue();
  }

  private static boolean attributes(TickObjectCooldown cooldown, int objectId, int level) {
    return cooldown.checkAndApplyCooldown(objectId, TickObjectCooldown.getAttributesCooldownType(level),
        TickObjectCooldown.getAttributesCooldownSeconds(level));
  }

  @Test
  void attributesCooldown_typesAndWindowsOfTheStages() {
    assertThat(TickObjectCooldown.getAttributesCooldownType(1)).isEqualTo(TickObjectCooldown.TYPE_ATTRIBUTES_HIGH);
    assertThat(TickObjectCooldown.getAttributesCooldownType(2)).isEqualTo(TickObjectCooldown.TYPE_ATTRIBUTES_MEDIUM);
    assertThat(TickObjectCooldown.getAttributesCooldownType(3)).isEqualTo(TickObjectCooldown.TYPE_ATTRIBUTES_LOW);
    assertThat(TickObjectCooldown.getAttributesCooldownType(4)).isEqualTo(TickObjectCooldown.TYPE_ATTRIBUTES_BACKGROUND);
    assertThat(TickObjectCooldown.getAttributesCooldownType(0)).as("stage 0 has no cooldown").isEqualTo(-1);

    assertThat(new int[] {
        TickObjectCooldown.getAttributesCooldownSeconds(1), TickObjectCooldown.getAttributesCooldownSeconds(2),
        TickObjectCooldown.getAttributesCooldownSeconds(3), TickObjectCooldown.getAttributesCooldownSeconds(4)})
        .containsExactly(5, 10, 30, 60);
  }

  @Test
  void attributesCooldown_letsOneEventThroughPerWindow() {
    TickObjectCooldown cooldown = new TickObjectCooldown(OBJECTS);

    cooldown.setTick(100);
    assertThat(attributes(cooldown, 7, 3)).as("first event").isTrue();
    assertThat(attributes(cooldown, 7, 3)).as("second event in the same second").isFalse();
    cooldown.setTick(129);
    assertThat(attributes(cooldown, 7, 3)).as("still in the window of 30 s").isFalse();
    cooldown.setTick(130);
    assertThat(attributes(cooldown, 7, 3)).as("the next window").isTrue();
    assertThat(attributes(cooldown, 8, 3)).as("another object").isTrue();
  }

  @Test
  void attributesCooldown_stagesDoNotBlockEachOther() {
    TickObjectCooldown cooldown = new TickObjectCooldown(OBJECTS);

    cooldown.setTick(0);
    assertThat(attributes(cooldown, 7, 3)).as("someone walks").isTrue();
    assertThat(attributes(cooldown, 7, 1)).as("now runs: another stage").isTrue();
    cooldown.setTick(5);
    assertThat(attributes(cooldown, 7, 1)).as("runs on after 5 s").isTrue();
    assertThat(attributes(cooldown, 7, 3)).as("walking is still in its window").isFalse();
  }

  @Test
  void attributeCooldownLevels_ofTheEventTypes() {
    assertThat(EventType.percipientMoveRun.getAttributeCooldownLevel()).isEqualTo(1);
    assertThat(EventType.percipientMoveJump.getAttributeCooldownLevel()).isEqualTo(2);
    assertThat(EventType.percipientExistsDistance1000.getAttributeCooldownLevel()).isEqualTo(3);
    assertThat(EventType.percipientExistsDistance5000.getAttributeCooldownLevel()).isEqualTo(4);
    assertThat(EventType.percipientWeaponClub.getAttributeCooldownLevel()).isZero();
    assertThat(EventType.selfMoveWalk.getAttributeCooldownLevel()).isEqualTo(3);
    assertThat(EventType.selfHandleItemPush.getAttributeCooldownLevel()).isEqualTo(3);
    assertThat(EventType.selfMoveJump.getAttributeCooldownLevel()).isZero();
    assertThat(EventType.selfSleep.getAttributeCooldownLevel()).isZero();
    assertThat(EventType.candidatesMoveWalk.getAttributeCooldownLevel()).as("really close: every second").isZero();

    for (EventType type : EventType.values()) {
      if (!type.isEventToPercipient()) continue;
      assertThat(type.getAttributeCooldownLevel() == 0)
          .as("%s: stage 0 exactly for the critical ones", type)
          .isEqualTo(type.getPriority() == EventPriority.CRITICAL);
    }
  }
}
