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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.EventInfluenceDescription;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.EventType;
import org.socialworld.core.TickRandomCache;

/**
 * The generator of the event influence rules: tiers at the band, their order, the factor,
 * the repetition of the characters and the neutral rule. The rules are evaluated for real.
 * Everything is written into a store in memory, no database is used.
 */
public class EventInfluenceGeneratorTest {

  private static final int MOOD = Attribute.mood.getIndex();
  private static final int COURAGE = Attribute.courage.getIndex();

  private static EventInfluenceGenerator generator;

  @BeforeAll
  static void loadTheSources() {
    generator = EventInfluenceGenerator.fromResources();
    TickRandomCache.getInstance().setTick(5);
  }

  private static EventInfluenceDescription description(EventType eventType, int influenceType) {
    EventInfluenceDescription description = new EventInfluenceDescription(generator.buildRule(eventType, influenceType).toString());
    description.setFunctions();
    return description;
  }

  /** @return the values after the rule, or null if the rule gives no attribute array */
  private static int[] after(EventInfluenceDescription rule, int mood, int courage) {
    int[] values = new int[Attribute.NUMBER_OF_ATTRIBUTES];
    for (int i = 0; i < values.length; i++) values[i] = 5000;
    values[MOOD] = mood;
    values[COURAGE] = courage;

    ValueArrayList arguments = new ValueArrayList();
    arguments.add(new Value(Type.integer, Value.VALUE_BY_NAME_OBJECTID, 1));
    arguments.add(new Value(Type.attributeArray, PropertyName.simobj_attributeArray.toString(), new AttributeArray(values)));
    arguments.add(new Value(Type.valueList, Value.VALUE_BY_NAME_EVENT_PARAMS, new ValueArrayList()));

    Value result = rule.getFunction(0).calculate(arguments);
    if (result == null || !result.isValid()) return null;
    Object array = result.getObject(Type.attributeArray);
    if (!(array instanceof AttributeArray)) return null;

    int[] newValues = new int[values.length];
    for (int i = 0; i < values.length; i++) newValues[i] = ((AttributeArray) array).get(i);
    return newValues;
  }

  @Test
  void roundSymmetric_roundsHalfAwayFromZero() {
    assertThat(EventInfluenceRuleBuilder.roundSymmetric(7.5f)).isEqualTo(8);
    assertThat(EventInfluenceRuleBuilder.roundSymmetric(-7.5f)).isEqualTo(-8);
    assertThat(EventInfluenceRuleBuilder.roundSymmetric(0.4f)).isZero();
    assertThat(EventInfluenceRuleBuilder.roundSymmetric(-0.5f)).isEqualTo(-1);
  }

  @Test
  void farmerPunched_eachTierOfTheBandHasItsStep() {
    // farmer: mood ~ (2000..8000, middle 5000), steps -10 / -100 / -200 / -200
    EventInfluenceDescription rule = description(EventType.candidatesPunchLeftFistStraight, 0);

    assertThat(after(rule, 1500, 5000)[MOOD]).as("below the band").isEqualTo(1490);
    assertThat(after(rule, 3000, 5000)[MOOD]).as("lower half").isEqualTo(2900);
    assertThat(after(rule, 6000, 5000)[MOOD]).as("upper half").isEqualTo(5800);
    assertThat(after(rule, 8500, 5000)[MOOD]).as("above the band").isEqualTo(8300);
  }

  @Test
  void farmerPunched_onlyOneTierApplies_inBothDirections() {
    EventInfluenceDescription rule = description(EventType.candidatesPunchLeftFistStraight, 0);

    // mood falls: 2050 is in the lower half (-100) and lands below the band, where -10 must not follow
    assertThat(after(rule, 2050, 5000)[MOOD]).isEqualTo(1950);
    // courage rises (farmer: o, 3800..6200, middle 5000, steps +20 / +20 / +10 / +2):
    // 6195 is in the upper half (+10) and lands above the band, where +2 must not follow
    assertThat(after(rule, 5000, 6195)[COURAGE]).isEqualTo(6205);
  }

  @Test
  void factorOfTheEventType_isAppliedAndRounded() {
    // farmer hit by a weapon: mood -30 / -300 / -500 / -500, the club has the factor 1.5
    String club = generator.buildRule(EventType.candidatesWeaponClub, 0).toString();
    String stab = generator.buildRule(EventType.candidatesWeaponLeftStab, 0).toString();

    assertThat(club).contains("mood;1.00;-45.00").contains("mood;1.00;-450.00").contains("mood;1.00;-750.00");
    assertThat(stab).contains("mood;1.00;-30.00").contains("mood;1.00;-300.00").contains("mood;1.00;-500.00");
  }

  @Test
  void generate_writesOneRulePerEventTypeAndInfluenceType_andRepeatsTheTenCharacters() {
    InMemoryEventInfluenceStore store = new InMemoryEventInfluenceStore();
    EventInfluenceGenerator.Result result = generator.generate(store);

    assertThat(store.size()).isEqualTo(EventInfluenceGenerator.countRules());
    assertThat(result.getCountRules()).isEqualTo(store.size());
    assertThat(result.getCountCharacters()).isEqualTo(10);
    assertThat(result.isRepeated()).isTrue();

    int punch = EventType.candidatesPunchLeftFistStraight.getIndex();
    for (int influenceType = 10; influenceType < 100; influenceType += 10) {
      assertThat(store.get(punch, influenceType).replace("\"influenceType\":" + influenceType, ""))
          .as("influence type %d is the farmer", influenceType)
          .isEqualTo(store.get(punch, 0).replace("\"influenceType\":0", ""));
    }
    assertThat(store.get(punch, 1)).as("influence type 1 is the farmer's wife").isNotEqualTo(store.get(punch, 0));
  }

  @Test
  void eventTypeWithoutSituation_andSituationWithoutEffect_getANeutralRuleThatChangesNothing() {
    for (EventType eventType : new EventType[] {EventType.targetEat, EventType.selfExamineByLook}) {
      assertThat(generator.buildRule(eventType, 0).attributeChanges).as("%s", eventType).isEmpty();

      int[] values = after(description(eventType, 0), 4321, 1234);
      assertThat(values).as("%s gives the attribute array back", eventType).isNotNull();
      assertThat(values[MOOD]).isEqualTo(4321);
      assertThat(values[COURAGE]).isEqualTo(1234);
    }
  }
}
