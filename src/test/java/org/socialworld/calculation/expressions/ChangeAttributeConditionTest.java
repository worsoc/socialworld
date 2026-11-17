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
package org.socialworld.calculation.expressions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.FunctionArgType;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.EventInfluenceDescription;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.TickRandomCache;
import org.socialworld.datasource.parsing.JsonEventInfluenceDescription;
import org.socialworld.datasource.parsing.JsonEventInfluencesAttributeDescription;
import org.socialworld.datasource.parsing.JsonFunctionArg;
import org.socialworld.datasource.parsing.JsonTerm;
import org.socialworld.datasource.parsing.JsonValue;

/**
 * The condition term of an event influence rule: one comparison (three arguments) is decided
 * softly by a sigmoid, two or more comparisons (multiples of three) are joined by AND and decided
 * strictly, any other number of arguments makes the rule invalid. No database is used.
 */
public class ChangeAttributeConditionTest {

  private static final int MOOD = Attribute.mood.getIndex();
  private static final int COURAGE = Attribute.courage.getIndex();
  private static final int OBJECTS = 200;

  @BeforeAll
  static void fixTheRandomTick() {
    TickRandomCache.getInstance().setTick(5);
  }

  private static JsonFunctionArg arg(int faNr, FunctionArgType argType, Type valueType, String value) {
    JsonFunctionArg arg = new JsonFunctionArg();
    arg.faNr = faNr;
    arg.type = argType.toString();
    arg.value = new JsonValue();
    arg.value.type = valueType.toString();
    arg.value.name = "";
    arg.value.value = value;
    return arg;
  }

  /** A rule "if (conditions) then mood := mood + 100", the conditions given as attribute, operator, value, ... */
  private static EventInfluenceDescription rule(String... condition) {
    JsonTerm conditionTerm = new JsonTerm();
    conditionTerm.termNr = 1;
    conditionTerm.function = "condition";
    conditionTerm.functionArgs = new ArrayList<>();
    for (int i = 0; i < condition.length; i++) {
      boolean attribute = i % 3 == 0;
      boolean value = i % 3 == 2;
      conditionTerm.functionArgs.add(arg(i + 1,
          attribute ? FunctionArgType.Attribute : FunctionArgType.Const,
          value ? Type.integer : Type.string, condition[i]));
    }

    JsonTerm actionTerm = new JsonTerm();
    actionTerm.termNr = 2;
    actionTerm.function = "function";
    actionTerm.functionArgs = new ArrayList<>();
    actionTerm.functionArgs.add(arg(1, FunctionArgType.Const, Type.string, "MX+N"));
    actionTerm.functionArgs.add(arg(2, FunctionArgType.Const, Type.string, "mood;1.00;100.00"));

    JsonEventInfluencesAttributeDescription change = new JsonEventInfluencesAttributeDescription();
    change.attribute = "mood";
    change.orderNr = 0;
    change.term = new ArrayList<>();
    change.term.add(conditionTerm);
    change.term.add(actionTerm);

    JsonEventInfluenceDescription root = new JsonEventInfluenceDescription();
    root.eventType = "selfSleep";
    root.influenceType = 0;
    root.attributeChanges = new ArrayList<>();
    root.attributeChanges.add(change);

    EventInfluenceDescription description = new EventInfluenceDescription(root.toString());
    description.setFunctions();
    return description;
  }

  private static int[] values(int mood, int courage) {
    int[] values = new int[Attribute.NUMBER_OF_ATTRIBUTES];
    for (int i = 0; i < values.length; i++) values[i] = 5000;
    values[MOOD] = mood;
    values[COURAGE] = courage;
    return values;
  }

  /** @return the new mood, or -1 if the rule gives no attribute array */
  private static int moodAfter(EventInfluenceDescription rule, int[] values, int objectID) {
    ValueArrayList arguments = new ValueArrayList();
    arguments.add(new Value(Type.integer, Value.VALUE_BY_NAME_OBJECTID, objectID));
    arguments.add(new Value(Type.attributeArray, PropertyName.simobj_attributeArray.toString(),
        new AttributeArray(values)));
    arguments.add(new Value(Type.valueList, Value.VALUE_BY_NAME_EVENT_PARAMS, new ValueArrayList()));

    Value result = rule.getFunction(0).calculate(arguments);
    if (result == null || !result.isValid()) return -1;
    Object array = result.getObject(Type.attributeArray);
    if (!(array instanceof AttributeArray)) return -1;
    return ((AttributeArray) array).get(MOOD);
  }

  /** @return how many of the objects got the THEN branch (mood + 100) */
  private static int countChanged(EventInfluenceDescription rule, int mood, int courage) {
    int changed = 0;
    for (int objectID = 1; objectID <= OBJECTS; objectID++) {
      int newMood = moodAfter(rule, values(mood, courage), objectID);
      if (newMood == mood + 100) {
        changed++;
      }
      else {
        assertThat(newMood).as("mood %d, object %d: THEN or unchanged", mood, objectID).isEqualTo(mood);
      }
    }
    return changed;
  }

  @Test
  void twoComparisons_areStrictAtBothBounds() {
    EventInfluenceDescription rule = rule("mood", ">=", "3800", "mood", "<", "5000");

    assertThat(countChanged(rule, 3799, 5000)).as("just below the lower bound").isZero();
    assertThat(countChanged(rule, 3800, 5000)).as("on the lower bound").isEqualTo(OBJECTS);
    assertThat(countChanged(rule, 4999, 5000)).as("just below the upper bound").isEqualTo(OBJECTS);
    assertThat(countChanged(rule, 5000, 5000)).as("on the upper bound").isZero();
  }

  @Test
  void threeComparisons_allMustHold() {
    EventInfluenceDescription rule = rule("mood", ">=", "3800", "mood", "<", "5000", "courage", "<", "6000");

    assertThat(countChanged(rule, 4000, 5999)).as("all three hold").isEqualTo(OBJECTS);
    assertThat(countChanged(rule, 4000, 6000)).as("courage fails").isZero();
    assertThat(countChanged(rule, 3799, 5999)).as("mood fails").isZero();
  }

  @Test
  void oneComparison_staysSoftAtTheThreshold() {
    EventInfluenceDescription rule = rule("mood", ">=", "5000");

    int changed = countChanged(rule, 5000, 5000);
    assertThat(changed).as("about half of the objects at the threshold").isBetween(OBJECTS / 4, OBJECTS * 3 / 4);
  }

  @Test
  void argumentsThatAreNoMultipleOfThree_changeNothing() {
    EventInfluenceDescription rule = rule("mood", ">=", "3800", "mood");

    for (int objectID = 1; objectID <= 20; objectID++) {
      int newMood = moodAfter(rule, values(4000, 5000), objectID);
      assertThat(newMood).as("object %d", objectID).isIn(-1, 4000);
    }
  }
}
