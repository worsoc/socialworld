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
