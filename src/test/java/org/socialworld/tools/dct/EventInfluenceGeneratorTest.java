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
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.EventInfluenceDescription;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.EventType;
import org.socialworld.core.TickRandomCache;

/**
 * The generator of the event influence rules: tiers at the band (for hunger and tiredness at the ideal), their order, the factor,
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

  /** @return the value of one attribute after the rule, all other attributes at 5000 */
  private static int after(EventInfluenceDescription rule, Attribute attribute, int value) {
    int[] values = new int[Attribute.NUMBER_OF_ATTRIBUTES];
    for (int i = 0; i < values.length; i++) values[i] = 5000;
    values[attribute.getIndex()] = value;

    ValueArrayList arguments = new ValueArrayList();
    arguments.add(new Value(Type.integer, Value.VALUE_BY_NAME_OBJECTID, 1));
    arguments.add(new Value(Type.attributeArray, PropertyName.simobj_attributeArray.toString(), new AttributeArray(values)));
    arguments.add(new Value(Type.valueList, Value.VALUE_BY_NAME_EVENT_PARAMS, new ValueArrayList()));

    Value result = rule.getFunction(0).calculate(arguments);
    return ((AttributeArray) result.getObject(Type.attributeArray)).get(attribute.getIndex());
  }

  @Test
  void maidSleeps_theTiersOfTirednessSitAtHerIdeal_notAtTheBandOfHerLevel() {
    // maid: tiredness + (band 5800..7500), ideal 30/85 -> tiers at 3000 / 5750 / 8500,
    // sleep steps -7 / -51 / -77 / -94, she needs 8 h (factor 1)
    EventInfluenceDescription rule = description(EventType.selfSleep, 3);

    assertThat(after(rule, Attribute.tiredness, 2500)).as("below the ideal").isEqualTo(2493);
    assertThat(after(rule, Attribute.tiredness, 5000)).as("lower half, below her band").isEqualTo(4949);
    assertThat(after(rule, Attribute.tiredness, 7000)).as("upper half").isEqualTo(6923);
    assertThat(after(rule, Attribute.tiredness, 9000)).as("above the ideal").isEqualTo(8906);
  }

  @Test
  void millerEats_theTiersOfHungerSitAtHisIdeal() {
    // miller: hunger - (band 2500..4200), ideal 25/40 -> tiers at 2500 / 3250 / 4000,
    // eat steps -2 / -16 / -23 / -27 times 3 / 4 meals -> -2 / -12 / -17 / -20
    EventInfluenceDescription rule = description(EventType.selfEat, 9);

    assertThat(after(rule, Attribute.hunger, 2000)).as("below the ideal").isEqualTo(1998);
    assertThat(after(rule, Attribute.hunger, 3000)).as("lower half").isEqualTo(2988);
    assertThat(after(rule, Attribute.hunger, 3500)).as("upper half").isEqualTo(3483);
    assertThat(after(rule, Attribute.hunger, 4100)).as("above the ideal, still inside his band").isEqualTo(4080);
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
  void needOfSleep_scalesTheSleepSteps() {
    // tiredness while sleeping, CSV -9 / -60 / -85 / -102 for farmer and shopkeeper
    String farmer = generator.buildRule(EventType.selfSleep, 0).toString();
    String shopkeeper = generator.buildRule(EventType.selfSleep, 5).toString();

    // farmer needs 8 h: factor 1
    assertThat(farmer).contains("tiredness;1.00;-9.00").contains("tiredness;1.00;-60.00")
        .contains("tiredness;1.00;-85.00").contains("tiredness;1.00;-102.00");
    // shopkeeper needs 7.5 h: factor 8 / 7.5, so he recovers a little faster per event
    assertThat(shopkeeper).contains("tiredness;1.00;-10.00").contains("tiredness;1.00;-64.00")
        .contains("tiredness;1.00;-91.00").contains("tiredness;1.00;-109.00");
  }

  @Test
  void needOfFood_scalesHungerAndTirednessWhileEating() {
    // CSV hunger -2 / -16 / -23 / -27, tiredness +3 / +3 / +2 / +1 for the shepherd
    String shepherd = generator.buildRule(EventType.selfEat, 8).toString();
    // shepherd needs 2 meals: factor 3 / 2, a meal fills for longer
    assertThat(shepherd).contains("hunger;1.00;-3.00").contains("hunger;1.00;-24.00")
        .contains("hunger;1.00;-35.00").contains("hunger;1.00;-41.00");
    assertThat(shepherd).contains("tiredness;1.00;5.00").contains("tiredness;1.00;3.00").contains("tiredness;1.00;2.00");

    // farmhand needs 4 meals (factor 3 / 4) and 8.5 h of sleep (factor 8 / 8.5)
    assertThat(generator.buildRule(EventType.selfEat, 2).toString())
        .contains("hunger;1.00;-12.00").contains("hunger;1.00;-17.00").contains("hunger;1.00;-20.00");
    assertThat(generator.buildRule(EventType.selfSleep, 2).toString())
        .contains("tiredness;1.00;-64.00").contains("tiredness;1.00;-96.00").contains("tiredness;1.00;-112.00");
  }

  @Test
  void needFactor_isOneOutsideSleepingAndEating() {
    AttributeCharacter ascetic = new AttributeCharacter(86, "ascetic", "fasts", levels(), 6f, 1);
    EventInfluenceCatalog catalog = EventInfluenceCatalog.parse(
        "86;1;s;tiredness;-1;-1;-1;-1\n86;3;s;hunger;-1;-1;-1;-1\n86;3;s;mood;1;1;1;1\n86;5;s;tiredness;1;1;1;1\n");
    List<EventInfluenceCatalog.Line> sleep = catalog.getLines(86, 1);
    List<EventInfluenceCatalog.Line> eat = catalog.getLines(86, 3);
    List<EventInfluenceCatalog.Line> walk = catalog.getLines(86, 5);

    assertThat(EventInfluenceRuleBuilder.needFactor(sleep.get(0), ascetic)).isEqualTo(8f / 6f);
    assertThat(EventInfluenceRuleBuilder.needFactor(eat.get(0), ascetic)).as("hunger while eating").isEqualTo(3f);
    assertThat(EventInfluenceRuleBuilder.needFactor(eat.get(1), ascetic)).as("mood while eating").isEqualTo(1f);
    assertThat(EventInfluenceRuleBuilder.needFactor(walk.get(0), ascetic)).as("walking").isEqualTo(1f);
  }

  private static AttributeLevel[] levels() {
    AttributeLevel[] levels = new AttributeLevel[Attribute.NUMBER_OF_ATTRIBUTES];
    java.util.Arrays.fill(levels, AttributeLevel.fromCode("o"));
    return levels;
  }

  @Test
  void generate_writesOneRulePerEventTypeAndInfluenceType_oneCharacterEach() {
    InMemoryEventInfluenceStore store = new InMemoryEventInfluenceStore();
    EventInfluenceGenerator.Result result = generator.generate(store);

    assertThat(store.size()).isEqualTo(EventInfluenceGenerator.countRules());
    assertThat(result.getCountRules()).isEqualTo(store.size());
    assertThat(result.getCountCharacters()).isEqualTo(100);
    assertThat(result.isRepeated()).as("100 characters for 100 influence types").isFalse();

    int punch = EventType.candidatesPunchLeftFistStraight.getIndex();
    assertThat(store.get(punch, 1)).as("influence type 1 is the farmer's wife").isNotEqualTo(store.get(punch, 0));
    assertThat(store.get(punch, 10)).as("influence type 10 is the baker").isNotEqualTo(store.get(punch, 0));
    assertThat(store.get(punch, 21)).as("influence type 21 is the woodcutter").isNotEqualTo(store.get(punch, 0));
    assertThat(store.get(punch, 30)).as("influence type 30 is the village smith").isNotEqualTo(store.get(punch, 0));
    assertThat(store.get(punch, 49)).as("influence type 49 is the mercenary").isNotEqualTo(store.get(punch, 0));
    assertThat(store.get(punch, 50)).as("influence type 50 is the knight").isNotEqualTo(store.get(punch, 0));
    assertThat(store.get(punch, 68)).as("influence type 68 is the wrestler").isNotEqualTo(store.get(punch, 0));
    assertThat(store.get(punch, 74)).as("influence type 74 is the wounded veteran").isNotEqualTo(store.get(punch, 0));
    assertThat(store.get(punch, 87)).as("influence type 87 is the giant").isNotEqualTo(store.get(punch, 0));
    assertThat(store.get(punch, 99).replace("\"influenceType\":99", ""))
        .as("influence type 99 is the child prodigy, not a repeated farmer")
        .isNotEqualTo(store.get(punch, 0).replace("\"influenceType\":0", ""));
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
