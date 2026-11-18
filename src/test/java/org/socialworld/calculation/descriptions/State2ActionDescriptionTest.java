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
package org.socialworld.calculation.descriptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.socialworld.datasource.parsing.JsonEventReactionDescription;

import com.google.gson.Gson;

/**
 * The JSON form of the state-to-action rules and the common entry class of the action descriptions. Without database.
 */
public class State2ActionDescriptionTest {

  private static final Gson GSON = new Gson();

  private static final String SLEEP = "{\"conditions\":\"tiredness >= 7000\",\"actionType\":\"<Const>0</Const>\","
      + "\"actionMode\":\"<Const>1</Const>\",\"minTime\":\"<Now+N>10000</Now+N>\",\"maxTime\":\"<Now+N>100000</Now+N>\","
      + "\"priority\":\"<Const>40</Const>\",\"intensity\":\"<MX+N>4;0.015;0</MX+N>\",\"duration\":\"<Const>28800000</Const>\"}";

  @Test
  void entries_areReadFromJson_andGiveOneFunction() {
    State2ActionDescription description = new State2ActionDescription(GSON,
        "{\"state2ActionType\":7,\"entrys\":[" + SLEEP + "]}");
    description.setFunctions();

    assertThat(description.getState2ActionType()).isEqualTo(7);
    assertThat(description.countEntries()).isEqualTo(1);
    assertThat(description.countFunctions()).isEqualTo(1);
  }

  @Test
  void withoutEntries_thereIsNoFunction() {
    State2ActionDescription description = new State2ActionDescription(GSON, "{\"state2ActionType\":3}");
    description.setFunctions();

    assertThat(description.countEntries()).isZero();
    assertThat(description.countFunctions()).isZero();
  }

  @Test
  void anEventParameter_isRejected() {
    String json = "{\"state2ActionType\":5,\"entrys\":[{\"conditions\":\"hunger >= 6000\","
        + "\"actionType\":\"<Const>4</Const>\",\"item\":\"<GetEvParm>causer</GetEvParm>\"}]}";

    assertThatThrownBy(() -> new State2ActionDescription(GSON, json))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("type 5");
  }

  @Test
  void theReactionEntry_getsTheFieldsOfTheCommonEntryClass() {
    String json = "{\"eventType\":\"candidatesMoveWalk\",\"reactionType\":2,\"entrys\":[{\"conditions\":\"mood < 9000\","
        + "\"actionType\":\"<Const>6</Const>\",\"target\":\"<GetEvParm>causer</GetEvParm>\",\"relevanceThreshold\":\"10\"}]}";

    EventReactionDescriptionEntry entry = GSON.fromJson(json, JsonEventReactionDescription.class).entrys.get(0);

    assertThat(entry).isInstanceOf(ActionDescriptionEntry.class);
    assertThat(entry.conditions).isEqualTo("mood < 9000");
    assertThat(entry.actionType).isEqualTo("<Const>6</Const>");
    assertThat(entry.target).isEqualTo("<GetEvParm>causer</GetEvParm>");
    assertThat(entry.relevanceThreshold).isEqualTo("10");
  }
}
