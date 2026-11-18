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

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.Value;
import org.socialworld.core.EventType;
import org.socialworld.datasource.parsing.Json;
import org.socialworld.datasource.parsing.JsonEventReactionDescription;

import com.google.gson.Gson;

/**
 * The JSON form of the event reaction rules as they are stored in swpool_erd (erd_regeln_konzept.md, E1 to E3).
 * Without database.
 */
public class EventReactionDescriptionTest {

  private static String screamRule(String eventType, int reactionType) {
    EventReactionDescriptionEntry look = new EventReactionDescriptionEntry();
    look.conditions = "3 " + Value.VALUE_BY_NAME_EVENT_SAY_LOUDNESS + " > 50 & mood >= 2000 & mood < 5500";
    look.actionType = "<Const>2</Const>";
    look.actionMode = "<Const>21</Const>";
    look.target = "<GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm>";

    JsonEventReactionDescription rule = new JsonEventReactionDescription();
    rule.eventType = eventType;
    rule.reactionType = reactionType;
    rule.entrys = new ArrayList<EventReactionDescriptionEntry>();
    rule.entrys.add(look);
    return rule.toString();
  }

  @Test
  void theJson_keepsTheTagsReadable() {
    String json = screamRule("candidatesSayScream", 1);

    assertThat(json).contains("\"target\":\"<GetEvParm>event_causer</GetEvParm>\"");
    assertThat(json).contains("say_loudness > 50");
    assertThat(json).doesNotContain("\\u003c").doesNotContain("null");
  }

  @Test
  void aRuleFromTheDatabase_namesItsPair_andGivesOneFunction() {
    EventReactionDescription description = new EventReactionDescription(new Gson(), screamRule("candidatesSayScream", 1));
    description.setFunctions();

    assertThat(description.getEventType()).isEqualTo(EventType.candidatesSayScream);
    assertThat(description.getEventType().getIndex()).isEqualTo(337);
    assertThat(description.getReactionType()).isEqualTo(1);
    assertThat(description.countFunctions()).isEqualTo(1);
    // no relevance threshold given: 100 as before
    assertThat(description.getRelevanceThreshold()).isEqualTo(100);
  }

  @Test
  void anUnknownEventType_becomesNothing_soThePoolIgnoresTheRow() {
    EventReactionDescription description = new EventReactionDescription(new Gson(), screamRule("candidatesSayShout", 1));

    assertThat(description.getEventType()).isEqualTo(EventType.nothing);
  }

  @Test
  void aNewJsonHolder_isAJson() {
    assertThat(new JsonEventReactionDescription()).isInstanceOf(Json.class);
  }
}
