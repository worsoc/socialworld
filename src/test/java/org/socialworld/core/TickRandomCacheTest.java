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

public class TickRandomCacheTest {

  private static final int OBJECTS = 10;

  @Test
  void getInstance_isAlwaysTheSame() {
    assertThat(TickRandomCache.getInstance()).isSameAs(TickRandomCache.getInstance());
  }

  @Test
  void sameObjectInTheSameTick_getsTheSameValue() {
    TickRandomCache cache = atTick(5);

    assertThat(cache.getRandom(3)).isEqualTo(cache.getRandom(3));
  }

  @Test
  void values_areBetweenZeroAndOne() {
    TickRandomCache cache = new TickRandomCache(OBJECTS);

    for (int tick = 1; tick <= 50; tick++) {
      cache.nextTick();
      for (int objectId = 0; objectId < OBJECTS; objectId++) {
        assertThat(cache.getRandom(objectId)).isGreaterThanOrEqualTo(0.0).isLessThan(1.0);
      }
    }
  }

  @Test
  void nextTick_givesTheObjectANewValue() {
    TickRandomCache cache = atTick(1);
    double first = cache.getRandom(3);

    cache.nextTick();

    assertThat(cache.getRandom(3)).isNotEqualTo(first);
  }

  @Test
  void objectsInTheSameTick_getDifferentValues() {
    TickRandomCache cache = atTick(1);

    assertThat(cache.getRandom(3)).isNotEqualTo(cache.getRandom(4));
  }

  @Test
  void value_dependsOnlyOnObjectAndTick_notOnEarlierCalls() {
    TickRandomCache withHistory = new TickRandomCache(OBJECTS);
    for (int tick = 1; tick <= 7; tick++) {
      withHistory.nextTick();
      withHistory.getRandom(3);
    }

    assertThat(withHistory.getRandom(3)).isEqualTo(atTick(7).getRandom(3));
  }

  @Test
  void setTick_isLikeReachingTheTickStepByStep() {
    TickRandomCache stepByStep = new TickRandomCache(OBJECTS);
    for (int tick = 1; tick <= 4; tick++) {
      stepByStep.nextTick();
    }

    assertThat(atTick(4).getRandom(6)).isEqualTo(stepByStep.getRandom(6));
  }

  @Test
  void beforeTheFirstTick_theValueIsReallyCalculated() {
    TickRandomCache cache = new TickRandomCache(OBJECTS);

    for (int objectId = 1; objectId < OBJECTS; objectId++) {
      assertThat(cache.getRandom(objectId)).as("object %d", objectId).isGreaterThan(0.0).isLessThan(1.0);
    }
    assertThat(cache.getRandom(3)).isNotEqualTo(cache.getRandom(4));
  }

  @Test
  void beforeTheFirstTick_isTheSameAsTickZero() {
    assertThat(new TickRandomCache(OBJECTS).getRandom(3)).isEqualTo(atTick(0).getRandom(3));
  }

  @Test
  void objectOutsideOfTheRange_getsZero() {
    TickRandomCache cache = atTick(1);

    assertThat(cache.getRandom(-1)).isZero();
    assertThat(cache.getRandom(OBJECTS)).isZero();
  }

  private static TickRandomCache atTick(long tick) {
    TickRandomCache cache = new TickRandomCache(OBJECTS);
    cache.setTick(tick);
    return cache;
  }
}
