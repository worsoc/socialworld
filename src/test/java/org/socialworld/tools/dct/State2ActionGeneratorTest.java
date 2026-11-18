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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.descriptions.State2ActionDescription;
import org.socialworld.calculation.descriptions.State2ActionDescriptionEntry;
import org.socialworld.datasource.parsing.Json;
import org.socialworld.datasource.parsing.JsonState2ActionDescription;

/**
 * The generator of the state-to-action rules per character (state2action_konzept.md, section 7):
 * sleep and eat at the ideal of the character, sleep for its sleepHours, up to three own rules from its most
 * pronounced levels, the fallback rule. Uses characters.csv as it is in git.
 * Everything is written into a store in memory, no database is used.
 */
public class State2ActionGeneratorTest {

  private static final int FARMER = 0;
  private static final int MAID = 3;
  private static final int ROPEMAKER = 26;
  private static final int ALCHEMIST = 77;
  private static final int PARALYSED_OLD_MAN = 88;
  private static final int MAD_PROPHET = 90;
  private static final int STARVING_REFUGEE = 97;
  private static final int NIGHT_WATCHMAN = 98;

  private static State2ActionGenerator generator;

  @BeforeAll
  static void loadTheCharacters() {
    generator = State2ActionGenerator.fromResources();
  }

  private static List<State2ActionDescriptionEntry> entries(int type) {
    String json = generator.buildRule(type).toString();
    return Json.getGsonInstance().fromJson(json, JsonState2ActionDescription.class).entrys;
  }

  @Test
  void generate_writesOneRulePerType_andClearsTheStoreFirst() {
    InMemoryState2ActionStore store = new InMemoryState2ActionStore();
    store.insert(500, "old");

    State2ActionGenerator.Result result = generator.generate(store);

    assertThat(result.getCountRules()).isEqualTo(100);
    // 2 × 100 for sleep and eat, own rules: 81 × 3 + 12 × 2 + 6 × 1, one fallback
    assertThat(result.getCountEntries()).isEqualTo(474);
    assertThat(result.getCountFallbacks()).isEqualTo(1);
    assertThat(store.size()).isEqualTo(100);
    assertThat(store.get(500)).isNull();
    for (int type = 0; type < 100; type++) {
      assertThat(Json.getGsonInstance().fromJson(store.get(type), JsonState2ActionDescription.class).state2ActionType)
          .isEqualTo(type);
    }
  }

  @Test
  void typeT_getsCharacterTPlusOne() {
    assertThat(generator.characterOf(FARMER).getName()).isEqualTo("farmer");
    assertThat(generator.characterOf(ALCHEMIST).getNumber()).isEqualTo(78);
    assertThat(generator.characterOf(NIGHT_WATCHMAN).getName()).isEqualTo("exhausted night watchman");
  }

  @Test
  void sleepAndEat_comeFirst_atTheIdealOfTheCharacter() {
    // farmer: 70 / 60, as all had before
    assertThat(entries(FARMER).get(0).conditions).isEqualTo("tiredness >= 7000");
    assertThat(entries(FARMER).get(1).conditions).isEqualTo("hunger >= 6000");
    // alchemist, tiredness ++: goes to bed at 90
    assertThat(entries(ALCHEMIST).get(0).conditions).isEqualTo("tiredness >= 9000");
    // starving refugee: goes to eat at 95
    assertThat(entries(STARVING_REFUGEE).get(1).conditions).isEqualTo("hunger >= 9500");

    assertThat(entries(FARMER).get(0).actionMode).isEqualTo("<Const>1</Const>");
    assertThat(entries(FARMER).get(1).actionMode).isEqualTo("<Const>3</Const>");
  }

  @Test
  void sleepLasts_theSleepHoursOfTheCharacter_eatingStays18Minutes() {
    assertThat(entries(FARMER).get(0).duration).isEqualTo("<Const>28800000</Const>");
    assertThat(entries(MAD_PROPHET).get(0).duration).isEqualTo("<Const>21600000</Const>");
    assertThat(entries(PARALYSED_OLD_MAN).get(0).duration).isEqualTo("<Const>36000000</Const>");

    assertThat(entries(MAD_PROPHET).get(1).duration).isEqualTo("<Const>1080000</Const>");
    assertThat(entries(PARALYSED_OLD_MAN).get(1).duration).isEqualTo("<Const>1080000</Const>");
  }

  @Test
  void farmer_powerHigh_runs_inHisDirection() {
    List<State2ActionDescriptionEntry> entries = entries(FARMER);

    assertThat(entries).hasSize(3);
    assertThat(entries.get(2).conditions).isEqualTo("power >= 6650");
    assertThat(entries.get(2).actionType).isEqualTo("<Const>1</Const>");
    assertThat(entries.get(2).actionMode).isEqualTo("<Const>12</Const>");
    assertThat(entries.get(2).duration).isEqualTo("<Const>5000</Const>");
    assertThat(entries.get(2).direction).isEqualTo("<Const>(5.00,0.00,0)</Const>");
  }

  @Test
  void maid_fourPronouncedLevels_theFirstThreeInTheOrderOfTheAttributes() {
    // courage -, morals +, materialism -, spirituality + all have distance 1; spirituality is the fourth
    List<State2ActionDescriptionEntry> entries = entries(MAID);

    assertThat(entries).hasSize(5);
    assertThat(entries.get(2).conditions).isEqualTo("courage < 3350");
    assertThat(entries.get(2).actionMode).isEqualTo("<Const>13</Const>");
    assertThat(entries.get(3).conditions).isEqualTo("morals >= 6650");
    assertThat(entries.get(3).actionMode).isEqualTo("<Const>101</Const>");
    assertThat(entries.get(3).direction).isNull();
    assertThat(entries.get(4).conditions).isEqualTo("materialism < 3350");
    assertThat(entries.get(4).actionMode).isEqualTo("<Const>103</Const>");
  }

  @Test
  void nightWatchman_hungerAndTirednessGiveNoOwnRule() {
    // mood -, curiosity -; tiredness +++ and hunger + are left out
    List<State2ActionDescriptionEntry> entries = entries(NIGHT_WATCHMAN);

    assertThat(entries).hasSize(4);
    assertThat(entries.get(0).conditions).isEqualTo("tiredness >= 9500");
    assertThat(entries.get(2).conditions).isEqualTo("mood < 3350");
    assertThat(entries.get(2).actionMode).isEqualTo("<Const>102</Const>");
    assertThat(entries.get(3).conditions).isEqualTo("curiosity < 3350");
    assertThat(entries.get(3).actionMode).isEqualTo("<Const>13</Const>");
  }

  @Test
  void theMostPronouncedLevel_comesFirst() {
    // alchemist: curiosity ++ before courage + and materialism +
    List<Attribute> attributes = State2ActionGenerator.pronouncedAttributes(generator.characterOf(ALCHEMIST));

    assertThat(attributes).containsExactly(Attribute.curiosity, Attribute.courage, Attribute.materialism);
  }

  @Test
  void ropemaker_withoutAPronouncedLevel_getsTheFormerCuriosityRule() {
    List<State2ActionDescriptionEntry> entries = entries(ROPEMAKER);

    assertThat(entries).hasSize(3);
    assertThat(entries.get(2).conditions).isEqualTo("curiosity >= 5500 & curiosity < 6500 & power >= 3000");
    assertThat(entries.get(2).direction).isEqualTo("<Const>(2,7,0)</Const>");
  }

  @Test
  void middleOfTheBand_roundedUp() {
    assertThat(State2ActionGenerator.middleOfBand(AttributeLevel.high)).isEqualTo(6650);
    assertThat(State2ActionGenerator.middleOfBand(AttributeLevel.veryHigh)).isEqualTo(8200);
    assertThat(State2ActionGenerator.middleOfBand(AttributeLevel.extremelyHigh)).isEqualTo(9500);
    assertThat(State2ActionGenerator.middleOfBand(AttributeLevel.low)).isEqualTo(3350);
    assertThat(State2ActionGenerator.middleOfBand(AttributeLevel.veryLow)).isEqualTo(1800);
    assertThat(State2ActionGenerator.middleOfBand(AttributeLevel.extremelyLow)).isEqualTo(500);
  }

  @Test
  void direction_dependsOnTheNumber_withADecimalPoint_andWithoutMinusZero() {
    assertThat(State2ActionGenerator.direction(4)).isEqualTo("(4.91,0.94,0)");
    assertThat(State2ActionGenerator.direction(78)).isEqualTo("(0.63,-4.96,0)");
    // 90° and 270°: the cosine is a tiny number around 0
    assertThat(State2ActionGenerator.direction(26)).isEqualTo("(0.00,5.00,0)");
    assertThat(State2ActionGenerator.direction(76)).isEqualTo("(0.00,-5.00,0)");
  }

  @Test
  void everyRule_becomesOneChain_withoutEventParameters() {
    for (int type = 0; type < 100; type++) {
      String json = generator.buildRule(type).toString();
      assertThat(json).doesNotContain("GetEvParm").doesNotContain("\\u003c").doesNotContain("null").doesNotContain("'");

      State2ActionDescription description = new State2ActionDescription(Json.getGsonInstance(), json);
      description.setFunctions();

      assertThat(description.getState2ActionType()).isEqualTo(type);
      assertThat(description.countEntries()).isBetween(3, 5);
      assertThat(description.countFunctions()).isEqualTo(1);
    }
  }
}
