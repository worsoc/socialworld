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
import org.socialworld.actions.ActionMode;

/**
 * The fixed intervals between the events of a running action (concept EID, section 7.1).
 * The loop below runs an action through the calculations the way ActionHandler does:
 * perform() at the start, then every interval, and at the end only the remaining time passes.
 */
public class ActionEventTimingTest {

  /** @return {number of perform() calls, second at which the action is done} */
  private static int[] run(ActionMode mode, long durationInMilliseconds) {
    long remained = durationInMilliseconds;
    int performs = 1;      // the first calculation at t = 0 always performs
    int second = 0;

    while (remained > 0) {
      int next = ActionEventTiming.secondsTillNextCalculation(mode, remained);
      boolean finishingOnly = ActionEventTiming.isFinishingOnly(mode, remained);
      second += next;
      remained -= next * 1000L;
      if (!finishingOnly) performs++;
    }
    return new int[] {performs, second};
  }

  @Test
  void drinkingTenSeconds_givesFiveEvents() {
    assertThat(run(ActionMode.drink, 10_000)).containsExactly(5, 10);
  }

  @Test
  void drinkingFiveSeconds_givesEventsAt0_2_4() {
    assertThat(run(ActionMode.drink, 5_000)).containsExactly(3, 5);
  }

  @Test
  void eatingFifteenMinutes_gives90Events() {
    assertThat(run(ActionMode.eat, 15 * 60_000)).containsExactly(90, 900);
  }

  @Test
  void sleepingEightHours_gives96Events() {
    assertThat(run(ActionMode.sleep, 8 * 3_600_000)).containsExactly(96, 8 * 3600);
  }

  @Test
  void walkingThirtySeconds_givesOneEventPerSecond() {
    assertThat(run(ActionMode.walk, 30_000)).containsExactly(30, 30);
  }

  @Test
  void aPunchWithADuration_givesOneEvent_andEndsAfterItsDuration() {
    assertThat(run(ActionMode.punchLeftFistStraight, 3_000)).containsExactly(1, 3);
  }

  @Test
  void anActionWithoutDuration_givesOneEvent() {
    assertThat(run(ActionMode.sayScream, 0)).containsExactly(1, 0);
  }

  @Test
  void aDurationWithMilliseconds_endsAtTheNextFullSecond() {
    assertThat(run(ActionMode.look, 7_300)).containsExactly(2, 8);
  }

  @Test
  void intervals_ofTheActionModes() {
    assertThat(ActionMode.sleep.getEventIntervalSeconds()).isEqualTo(300);
    assertThat(ActionMode.drink.getEventIntervalSeconds()).isEqualTo(2);
    assertThat(ActionMode.eat.getEventIntervalSeconds()).isEqualTo(10);
    assertThat(ActionMode.run.getEventIntervalSeconds()).isEqualTo(1);
    assertThat(ActionMode.taste.getEventIntervalSeconds()).isEqualTo(5);
    assertThat(ActionMode.push.getEventIntervalSeconds()).isEqualTo(2);
    assertThat(ActionMode.jump.getEventIntervalSeconds()).isZero();
    assertThat(ActionMode.weaponClub.getEventIntervalSeconds()).isZero();
    assertThat(ActionMode.askNormal.getEventIntervalSeconds()).isZero();
  }
}
