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
package org.socialworld.tools.dct;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.calculation.descriptions.EventReactionDescription;
import org.socialworld.calculation.descriptions.EventReactionDescriptionEntry;
import org.socialworld.core.EventType;
import org.socialworld.datasource.parsing.JsonEventReactionDescription;
import org.socialworld.datasource.pool.EventReactionFamily;

import com.google.gson.Gson;

/**
 * The generator of the event reaction rules (erd_regeln_konzept.md, section 4): 7 styles × 16 variants and
 * 4 types with the former behaviour, per event family. Everything is written into a store in memory, no database is used.
 */
public class EventReactionGeneratorTest {

  private static final EventReactionGenerator GENERATOR = new EventReactionGenerator();
  private static final String CAUSER = "<GetEvParm>event_causer</GetEvParm>";

  private static List<EventReactionDescriptionEntry> entries(EventReactionFamily family, int type) {
    JsonEventReactionDescription rule = GENERATOR.buildRule(family, family.getEventTypes().get(0), type);
    return rule == null ? null : new Gson().fromJson(rule.toString(), JsonEventReactionDescription.class).entrys;
  }

  private static List<EventReactionDescriptionEntry> scream(int type) {
    return entries(EventReactionFamily.screamHeard, type);
  }

  @Test
  void generate_writes540Rules_40PairsStayWithoutReaction() {
    InMemoryEventReactionStore store = new InMemoryEventReactionStore();
    store.insert(337, 1, "old");

    EventReactionGenerator.Result result = GENERATOR.generate(store);

    assertThat(EventReactionGenerator.countRules()).isEqualTo(540);
    assertThat(result.getCountRules()).isEqualTo(540);
    assertThat(result.getCountEmpty()).isEqualTo(40);
    // per family: 6 styles × (8 × 1 + 8 × 2) + indifferent 8 × 1 + former 4 × 2 = 160
    assertThat(result.getCountEntries()).isEqualTo(800);
    assertThat(store.size()).isEqualTo(540);
    assertThat(store.get(337, 1)).isNotEqualTo("old").startsWith("{");
  }

  @Test
  void theTypeGivesStyleAndVariant() {
    assertThat(EventReactionGenerator.FIRST_FORMER_TYPE).isEqualTo(112);
    assertThat(EventReactionGenerator.COUNT_REACTION_TYPES).isEqualTo(116);
    assertThat(EventReactionGenerator.styleOf(0)).isEqualTo(EventReactionGenerator.Style.observer);
    assertThat(EventReactionGenerator.styleOf(52)).isEqualTo(EventReactionGenerator.Style.turningAway);
    assertThat(EventReactionGenerator.styleOf(111)).isEqualTo(EventReactionGenerator.Style.brawler);
    // 46: c 2, e 1, k 1
    assertThat(EventReactionGenerator.conditionOf(46)).isEqualTo(2);
    assertThat(EventReactionGenerator.hasFallback(46)).isTrue();
    assertThat(EventReactionGenerator.isLong(46)).isTrue();
  }

  @Test
  void observer0_looksShortlyInAGoodMood_otherwiseNothing() {
    List<EventReactionDescriptionEntry> entries = scream(0);

    assertThat(entries).hasSize(1);
    assertThat(entries.get(0).conditions).isEqualTo("mood >= 5000");
    assertThat(entries.get(0).actionMode).isEqualTo("<Const>21</Const>");
    assertThat(entries.get(0).target).isEqualTo(CAUSER);
    assertThat(entries.get(0).duration).isEqualTo("<Const>5000</Const>");
  }

  @Test
  void observer5_looksInABadMood_touchesInAGoodOne() {
    List<EventReactionDescriptionEntry> entries = scream(5);

    assertThat(entries).hasSize(2);
    assertThat(entries.get(0).conditions).isEqualTo("mood < 5000");
    assertThat(entries.get(0).actionMode).isEqualTo("<Const>21</Const>");
    assertThat(entries.get(1).conditions).isEqualTo("mood >= 5000");
    assertThat(entries.get(1).actionMode).isEqualTo("<Const>31</Const>");
    assertThat(entries.get(1).target).isEqualTo(CAUSER);
  }

  @Test
  void fearful46_sneaksLongWhenBrave_otherwiseWalksAwayLong() {
    List<EventReactionDescriptionEntry> entries = scream(46);

    assertThat(entries.get(0).conditions).isEqualTo("courage >= 5000");
    assertThat(entries.get(0).actionMode).isEqualTo("<Const>13</Const>");
    assertThat(entries.get(0).duration).isEqualTo("<Const>6000</Const>");
    assertThat(entries.get(0).direction).isEqualTo(EventReactionGenerator.awayFromEvent(46));
    assertThat(entries.get(1).conditions).isEqualTo("courage < 5000");
    assertThat(entries.get(1).actionMode).isEqualTo("<Const>11</Const>");
    assertThat(entries.get(1).duration).isEqualTo("<Const>15000</Const>");
    assertThat(entries.get(1).direction).isEqualTo(EventReactionGenerator.awayFromEvent(46));
  }

  @Test
  void turningAway52_walksAway_otherwiseScreamsBack() {
    List<EventReactionDescriptionEntry> entries = scream(52);

    assertThat(entries.get(0).actionMode).isEqualTo("<Const>11</Const>");
    assertThat(entries.get(0).duration).isEqualTo("<Const>5000</Const>");
    assertThat(entries.get(1).actionType).isEqualTo("<Const>10</Const>");
    assertThat(entries.get(1).actionMode).isEqualTo("<Const>102</Const>");
  }

  @Test
  void echo68_screamsBack_otherwisePunches() {
    List<EventReactionDescriptionEntry> entries = scream(68);

    assertThat(entries.get(0).actionMode).isEqualTo("<Const>102</Const>");
    assertThat(entries.get(1).actionType).isEqualTo("<Const>7</Const>");
    assertThat(entries.get(1).actionMode).isEqualTo("<Const>74</Const>");
    assertThat(entries.get(1).target).isEqualTo(CAUSER);
  }

  @Test
  void echo_answersInTheMannerOfTheFamily() {
    assertThat(entries(EventReactionFamily.screamHeard, 64).get(0).actionMode).isEqualTo("<Const>102</Const>");
    assertThat(entries(EventReactionFamily.sayHeard, 64).get(0).actionMode).isEqualTo("<Const>101</Const>");
    assertThat(entries(EventReactionFamily.whisperHeard, 64).get(0).actionMode).isEqualTo("<Const>103</Const>");
    assertThat(entries(EventReactionFamily.lookedAt, 64).get(0).actionMode).isEqualTo("<Const>101</Const>");
    assertThat(entries(EventReactionFamily.touched, 64).get(0).actionMode).isEqualTo("<Const>102</Const>");
  }

  @Test
  void indifferent_withoutFallbackNothing_withFallbackALookUnderTheOppositeCondition() {
    assertThat(scream(80)).isNull();

    List<EventReactionDescriptionEntry> entries = scream(85);
    assertThat(entries).hasSize(1);
    assertThat(entries.get(0).conditions).isEqualTo("mood >= 5000");
    assertThat(entries.get(0).actionMode).isEqualTo("<Const>21</Const>");
  }

  @Test
  void brawler_punchesShort_andTakesTheClubForOneBlowWhenLong() {
    List<EventReactionDescriptionEntry> punch = scream(96);
    assertThat(punch.get(0).actionType).isEqualTo("<Const>7</Const>");
    assertThat(punch.get(0).actionMode).isEqualTo("<Const>74</Const>");
    assertThat(punch.get(0).duration).isEqualTo("<Const>2000</Const>");

    List<EventReactionDescriptionEntry> club = scream(104);
    assertThat(club.get(0).actionType).isEqualTo("<Const>6</Const>");
    assertThat(club.get(0).actionMode).isEqualTo("<Const>67</Const>");
    assertThat(club.get(0).duration).isEqualTo("<Const>1000</Const>");
    assertThat(club.get(0).target).isEqualTo(CAUSER);
  }

  @Test
  void formerTypes_lookElseHand_withTheLoudnessOnlyForTheScream() {
    List<EventReactionDescriptionEntry> scream = scream(112);
    assertThat(scream.get(0).conditions).isEqualTo("3 say_loudness > 50 & mood >= 2000 & mood < 5500");
    assertThat(scream.get(1).conditions).isEqualTo("3 say_loudness > 30 & mood >= 1000 & mood < 6500");
    assertThat(scream.get(1).actionMode).isEqualTo("<Const>31</Const>");
    assertThat(scream.get(1).target).isEqualTo(CAUSER);

    assertThat(entries(EventReactionFamily.lookedAt, 115).get(0).conditions).isEqualTo("mood >= 2000 & mood < 5500");
  }

  @Test
  void awayFromEvent_isTheTagActionDir_withTheTypeDirectionAsFallback() {
    String tag = EventReactionGenerator.awayFromEvent(0);

    assertThat(tag).isEqualTo("<ActionDir>-5;0;(0,0,0);(5.00,0.00,0)</ActionDir>");
    assertThat(EventReactionGenerator.awayFromEvent(87)).isEqualTo("<ActionDir>-5;0;(0,0,0);(0.00,-5.00,0)</ActionDir>");

    String formula = tag.substring("<ActionDir>".length(), tag.length() - "</ActionDir>".length());
    assertThat(new org.socialworld.calculation.expressions.DirectionToReference(formula).isValid()).isTrue();
  }

  @Test
  void onlyMoves_haveADirection_andItIsAlwaysAwayFromTheEvent() {
    for (EventReactionFamily family : EventReactionFamily.values()) {
      for (int t = 0; t < EventReactionGenerator.COUNT_REACTION_TYPES; t++) {
        List<EventReactionDescriptionEntry> rule = entries(family, t);
        if (rule == null) continue; // pair without reaction
        for (EventReactionDescriptionEntry entry : rule) {
          boolean isMove = entry.actionType.equals("<Const>" + ActionType.move.getIndex() + "</Const>");
          if (isMove) {
            assertThat(entry.direction).as("%s type %d", family, t).isEqualTo(EventReactionGenerator.awayFromEvent(t));
          }
          else {
            assertThat(entry.direction).as("%s type %d", family, t).isNull();
          }
        }
      }
    }
  }

  @Test
  void testFly_letsTheFearfulFlyAwayAndUp_everythingElseStays() {
    EventReactionGenerator testGenerator = new EventReactionGenerator(true);
    assertThat(testGenerator.isTestFly()).isTrue();
    assertThat(GENERATOR.isTestFly()).isFalse();

    for (EventReactionFamily family : EventReactionFamily.values()) {
      EventType eventType = family.getEventTypes().get(0);
      for (int t = 0; t < EventReactionGenerator.COUNT_REACTION_TYPES; t++) {
        JsonEventReactionDescription normalRule = GENERATOR.buildRule(family, eventType, t);
        JsonEventReactionDescription testRule = testGenerator.buildRule(family, eventType, t);
        if (normalRule == null) {
          assertThat(testRule).isNull();
          continue;
        }
        assertThat(testRule.entrys).hasSameSizeAs(normalRule.entrys);
        for (int i = 0; i < normalRule.entrys.size(); i++) {
          EventReactionDescriptionEntry normal = normalRule.entrys.get(i);
          EventReactionDescriptionEntry test = testRule.entrys.get(i);
          boolean sneak = normal.actionMode.equals("<Const>" + ActionMode.sneak.getIndex() + "</Const>");
          if (sneak) {
            assertThat(test.actionMode).isEqualTo("<Const>" + ActionMode.fly.getIndex() + "</Const>");
            assertThat(test.direction).isEqualTo(EventReactionGenerator.flyAwayFromEvent(t));
            assertThat(test.conditions).isEqualTo(normal.conditions);
            assertThat(test.duration).isEqualTo(normal.duration);
          }
          else {
            assertThat(test.actionMode).as("%s type %d", family, t).isEqualTo(normal.actionMode);
            assertThat(test.direction).as("%s type %d", family, t).isEqualTo(normal.direction);
          }
        }
      }
    }
  }

  @Test
  void flyAwayFromEvent_isSpatialWithAnUpwardOffset() {
    String tag = EventReactionGenerator.flyAwayFromEvent(0);

    assertThat(tag).isEqualTo("<ActionDir>-5;1;(0,0,3);(5.00,0.00,0)</ActionDir>");
    String formula = tag.substring("<ActionDir>".length(), tag.length() - "</ActionDir>".length());
    assertThat(new org.socialworld.calculation.expressions.DirectionToReference(formula).isValid()).isTrue();
  }

  @Test
  void direction_dependsOnTheType_withoutMinusZero() {
    assertThat(EventReactionGenerator.direction(0)).isEqualTo("(5.00,0.00,0)");
    assertThat(EventReactionGenerator.direction(29)).isEqualTo("(0.00,5.00,0)");
    assertThat(EventReactionGenerator.direction(87)).isEqualTo("(0.00,-5.00,0)");
  }

  @Test
  void everyRule_namesItsPair_andBecomesOneChain() {
    for (EventReactionFamily family : EventReactionFamily.values()) {
      EventType eventType = family.getEventTypes().get(0);
      for (int type = 0; type < EventReactionGenerator.COUNT_REACTION_TYPES; type++) {
        JsonEventReactionDescription rule = GENERATOR.buildRule(family, eventType, type);
        if (rule == null) continue;
        String json = rule.toString();
        assertThat(json).doesNotContain("\\u003c").doesNotContain("null").doesNotContain("'");

        EventReactionDescription description = new EventReactionDescription(new Gson(), json);
        description.setFunctions();

        assertThat(description.getEventType()).isEqualTo(eventType);
        assertThat(description.getReactionType()).isEqualTo(type);
        assertThat(description.countFunctions()).isEqualTo(1);
      }
    }
  }
}
