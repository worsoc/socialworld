package org.socialworld.tools.pct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class PerceptionSlotListTest {

  private static final int SLOTS = 100;

  @Test
  void singleSlots() {
    assertThat(PerceptionSlotList.parse("3, 8,13 21", SLOTS)).containsExactly(3, 8, 13, 21);
  }

  @Test
  void range() {
    assertThat(PerceptionSlotList.parse("5-7", SLOTS)).containsExactly(5, 6, 7);
  }

  @Test
  void rangeWithStep() {
    assertThat(PerceptionSlotList.parse("3-18/5", SLOTS)).containsExactly(3, 8, 13, 18);
  }

  @Test
  void partsTogether_withoutDuplicates() {
    assertThat(PerceptionSlotList.parse("1-3, 2, 90-99/9", SLOTS)).containsExactly(1, 2, 3, 90, 99);
  }

  @Test
  void empty_meansNoSlots() {
    assertThat(PerceptionSlotList.parse("  ", SLOTS)).isEmpty();
  }

  @Test
  void slotOutsideOfTheRange_isRefused() {
    assertThatThrownBy(() -> PerceptionSlotList.parse("100", SLOTS)).hasMessageContaining("0 to 99");
    assertThatThrownBy(() -> PerceptionSlotList.parse("90-100", SLOTS)).hasMessageContaining("0 to 99");
  }

  @Test
  void unreadable_isRefused() {
    for (String text : new String[] {"a", "1-", "-1", "7-5", "1-3/0", "1/2", "1-2-3", "1-5/2/2", "1-3/", "/"}) {
      assertThatThrownBy(() -> PerceptionSlotList.parse(text, SLOTS)).as(text).isInstanceOf(IllegalArgumentException.class);
    }
  }
}
