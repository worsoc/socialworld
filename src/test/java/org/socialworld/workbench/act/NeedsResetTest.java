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
package org.socialworld.workbench.act;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.ActionMode;

/**
 * The divine intervention "Wecken & Sättigen" of the dashboard: which actions it ends and to which values it sets
 * tiredness and hunger. Ending the actions themselves needs a running simulation.
 */
class NeedsResetTest {

  @Test
  void onlySleepingAndEatingAreEnded() {
    assertThat(NeedsReset.isEnded(ActionMode.sleep)).isTrue();
    assertThat(NeedsReset.isEnded(ActionMode.eat)).isTrue();
    assertThat(NeedsReset.isEnded(ActionMode.drink)).isFalse();
    assertThat(NeedsReset.isEnded(ActionMode.walk)).isFalse();
    assertThat(NeedsReset.isEnded(ActionMode.sayScream)).isFalse();
    assertThat(NeedsReset.isEnded(null)).isFalse();
  }

  @Test
  void tirednessAndHungerGoTo2000() {
    assertThat(NeedsReset.TIREDNESS).isEqualTo(2000);
    assertThat(NeedsReset.HUNGER).isEqualTo(2000);
  }

  @Test
  void theResultSaysHowManyWereWokenAndFetched() {
    assertThat(new NeedsReset.Result(12, 5).toString()).isEqualTo("12 geweckt, 5 vom Essen geholt");
  }
}
