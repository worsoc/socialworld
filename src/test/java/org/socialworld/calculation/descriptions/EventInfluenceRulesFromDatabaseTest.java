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
import static org.assertj.core.api.Assumptions.assumeThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.FunctionArgType;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.TickRandomCache;
import org.socialworld.datasource.parsing.Json;
import org.socialworld.datasource.parsing.JsonEventInfluenceDescription;
import org.socialworld.datasource.parsing.JsonEventInfluencesAttributeDescription;
import org.socialworld.datasource.parsing.JsonTerm;
import org.socialworld.datasource.tablesPool.TablePoolAttribute;
import org.socialworld.datasource.tablesPool.TablePoolEID;

/**
 * Evaluates a sample of the real rules of swpool_eid with the real arrays of swpool_attribute.
 * The database is only read. Without a database or without data the test is skipped.
 *
 * The condition of a rule is decided with a probability, so the share of objects that
 * change the target attribute must follow the sigmoid of the condition attribute.
 */
public class EventInfluenceRulesFromDatabaseTest {

  private static final int EVERY_NTH_RULE = 97;
  private static final int OBJECTS_PER_PAIR = 20;
  private static final float K = 500f;
  private static final int BIN_WIDTH = 1000;
  private static final int MIN_SAMPLES_PER_BIN = 2000;
  private static final double TOLERANCE = 0.03;

  private static final class Rule {
    EventInfluenceDescription description;
    int conditionAttribute;
    boolean greater;
    int threshold;
    int targetAttribute;
    int basisAttribute;
    float m;
    float n;
  }

  private static final class Bin {
    double sumExpected;
    long countChanged;
    long samples;
  }

  private static List<int[]> loadArrays() {
    TablePoolAttribute table = new TablePoolAttribute();
    table.select(table.SELECT_ALL_COLUMNS, "", "ORDER BY aa_id ASC, attrib_nr ASC");

    Map<Integer, int[]> arrays = new TreeMap<>();
    for (int row = 0; row < table.rowCount(); row++) {
      int[] values = arrays.computeIfAbsent(table.getAttributeArrayID(row),
          id -> new int[Attribute.NUMBER_OF_ATTRIBUTES]);
      values[table.getAttribNr(row) - 1] = table.getValue(row);
    }
    return new ArrayList<>(arrays.values());
  }

  private static List<Rule> loadSampleOfRules() {
    TablePoolEID table = new TablePoolEID();
    table.select(table.SELECT_ALL_COLUMNS, "", "ORDER BY eventType, influenceType");

    List<Rule> rules = new ArrayList<>();
    for (int row = 0; row < table.rowCount(); row += EVERY_NTH_RULE) {
      Rule rule = parse(table.getJsonEID(row));
      if (rule != null) {
        rules.add(rule);
      }
    }
    return rules;
  }

  /** @return the rule, or null if it is no single condition on an attribute with an MX+N action */
  private static Rule parse(String json) {
    JsonEventInfluenceDescription root = Json.getGsonInstance().fromJson(json, JsonEventInfluenceDescription.class);
    if (root.attributeChanges == null || root.attributeChanges.size() != 1) return null;

    JsonEventInfluencesAttributeDescription change = root.attributeChanges.get(0);
    if (change.term == null || change.term.size() != 2) return null;
    JsonTerm condition = change.term.get(0);
    JsonTerm action = change.term.get(1);

    if (!"condition".equals(condition.function) || condition.functionArgs.size() != 3
        || !FunctionArgType.Attribute.toString().equals(condition.functionArgs.get(0).type)) return null;
    if (!"function".equals(action.function) || action.functionArgs.size() != 2
        || !"MX+N".equals(action.functionArgs.get(0).value.value)) return null;

    String operator = condition.functionArgs.get(1).value.value;
    if (!operator.startsWith(">") && !operator.startsWith("<")) return null;

    String[] formula = action.functionArgs.get(1).value.value.split(";");
    if (formula.length != 3) return null;

    Rule rule = new Rule();
    rule.conditionAttribute = Attribute.fromName(condition.functionArgs.get(0).value.value).getIndex();
    rule.greater = operator.startsWith(">");
    rule.threshold = Integer.parseInt(condition.functionArgs.get(2).value.value.trim());
    rule.targetAttribute = Attribute.fromName(change.attribute).getIndex();
    rule.basisAttribute = Attribute.fromName(formula[0].trim()).getIndex();
    rule.m = Float.parseFloat(formula[1].trim());
    rule.n = Float.parseFloat(formula[2].trim());
    rule.description = new EventInfluenceDescription(json);
    rule.description.setFunctions();
    return rule;
  }

  private static double expectedProbability(Rule rule, int x) {
    double falling = 1.0 / (1.0 + Math.exp((x - rule.threshold) / K));
    return rule.greater ? 1.0 - falling : falling;
  }

  private static int thenValue(Rule rule, int[] values) {
    int value = Math.round(rule.m * values[rule.basisAttribute] + rule.n);
    return Math.max(0, Math.min(AttributeArray.ATTRIBUTE_VALUE_MAX, value));
  }

  private static int[] evaluate(Rule rule, int[] values, int objectID) {
    ValueArrayList arguments = new ValueArrayList();
    arguments.add(new Value(Type.integer, Value.VALUE_BY_NAME_OBJECTID, objectID));
    arguments.add(new Value(Type.attributeArray, PropertyName.simobj_attributeArray.toString(),
        new AttributeArray(values)));
    arguments.add(new Value(Type.valueList, Value.VALUE_BY_NAME_EVENT_PARAMS, new ValueArrayList()));

    Object result = rule.description.getFunction(0).calculate(arguments).getObject(Type.attributeArray);
    assertThat(result).isInstanceOf(AttributeArray.class);

    AttributeArray array = (AttributeArray) result;
    int[] newValues = new int[values.length];
    for (int i = 0; i < values.length; i++) {
      newValues[i] = array.get(i);
    }
    return newValues;
  }

  @Test
  void conditionsOfTheRealRules_followTheSigmoidOfTheirAttribute() {
    List<int[]> arrays;
    List<Rule> rules;
    try {
      arrays = loadArrays();
      rules = loadSampleOfRules();
    } catch (RuntimeException | ExceptionInInitializerError e) {
      assumeThat(false).as("no database available: " + e).isTrue();
      return;
    }
    assumeThat(arrays).as("swpool_attribute").isNotEmpty();
    assumeThat(rules).as("swpool_eid").isNotEmpty();

    Map<Integer, Bin> bins = new TreeMap<>();
    TickRandomCache.getInstance().setTick(5);
    int objectID = 1;

    for (Rule rule : rules) {
      for (int[] values : arrays) {
        int x = values[rule.conditionAttribute];
        int thenValue = thenValue(rule, values);
        // a THEN that writes the value that is there already cannot be told from the ELSE
        if (Math.abs(thenValue - values[rule.targetAttribute]) < 2) continue;

        Bin bin = bins.computeIfAbsent(Math.floorDiv(x - rule.threshold, BIN_WIDTH), key -> new Bin());
        double expected = expectedProbability(rule, x);

        for (int i = 0; i < OBJECTS_PER_PAIR; i++) {
          int[] result = evaluate(rule, values, objectID);
          objectID = objectID % 99990 + 1;

          for (int attribute = 0; attribute < values.length; attribute++) {
            if (attribute != rule.targetAttribute) {
              assertThat(result[attribute]).as("attribute %d is not the target", attribute).isEqualTo(values[attribute]);
            }
          }
          boolean changed = result[rule.targetAttribute] != values[rule.targetAttribute];
          if (changed) {
            assertThat(result[rule.targetAttribute]).as("value written by THEN").isBetween(thenValue - 1, thenValue + 1);
            bin.countChanged++;
          }
          bin.sumExpected += expected;
          bin.samples++;
        }
      }
    }

    int checkedBins = 0;
    for (Map.Entry<Integer, Bin> entry : bins.entrySet()) {
      Bin bin = entry.getValue();
      if (bin.samples < MIN_SAMPLES_PER_BIN) continue;
      double observed = (double) bin.countChanged / bin.samples;
      double expected = bin.sumExpected / bin.samples;
      System.out.printf(java.util.Locale.ROOT, "RULECHECK x-threshold in [%d,%d): samples %d, expected %.3f, observed %.3f%n",
          entry.getKey() * BIN_WIDTH, (entry.getKey() + 1) * BIN_WIDTH, bin.samples, expected, observed);
      assertThat(observed).as("distance bin %d", entry.getKey() * BIN_WIDTH).isBetween(expected - TOLERANCE, expected + TOLERANCE);
      checkedBins++;
    }
    assertThat(checkedBins).as("bins with enough samples").isGreaterThanOrEqualTo(3);
  }
}
