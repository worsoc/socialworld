package org.socialworld.calculation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.descriptions.EventPerceptionDescriptionEntry;
import org.socialworld.calculation.expressions.CreateKnowledgeAtomExpression;
import org.socialworld.calculation.expressions.CreateKnowledgeElementExpression;
import org.socialworld.datasource.parsing.JsonEventPerceptionDescription;

import com.google.gson.Gson;

/**
 * Compares the expression trees built by the two constructors of
 * CreateKnowledgeElementExpression: from a line and from a JSON entry.
 * Only the structure is compared, nothing is evaluated.
 *
 * Temporary: this test goes away together with the String constructor.
 */
public class KnowledgeElementLinesVsJsonTest {

  private static final String SQL_EPD = "src/test/swpool_epd.sql";

  private static final String CAUSER = "GETVal(event_params).GETVal(event_causer)";
  private static final String CAUSER_HUMAN = "GETVal(event_params).GETVal(event_causer)#IsElem(50331647)";
  private static final String HEAD = "KSrcT:1&KSrc:GETVal(myself);KSbj:" + CAUSER + ";";
  private static final String JSON_HEAD =
      "\"sourceCategory\":1,\"sourcePathSteps\":[\"GETVal(myself)\"],"
      + "\"subjectPathSteps\":[\"GETVal(event_params)\",\"GETVal(event_causer)\"],";

  private static final Gson GSON = new Gson();

  // ---------------------------------------------------------------------------
  // property: the existing data
  // ---------------------------------------------------------------------------

  @Test
  void property_templates0To3_buildTheSameKnowledge() throws IOException {
    assertSameKnowledge(line(0), epdEntry(0));
    assertSameKnowledge(line(1), epdEntry(1));
    assertSameKnowledge(line(2), epdEntry(2));
    assertSameKnowledge(line(3), epdEntry(3));
  }

  /**
   * Old and new approach, difference 1: JSON lists the values of one subject per criterion,
   * so the criterion is set once. The line repeats it before each value.
   */
  @Test
  void property_criterion_isRepeatedInLinesButSetOnceInJson() throws IOException {
    Element fromLine = Element.of(new CreateKnowledgeElementExpression(line(0)));
    Element fromJson = Element.of(new CreateKnowledgeElementExpression(epdEntry(0)));

    assertThat(fromLine.atoms.get(0).criteria()).containsExactly("0", "0", "0");
    assertThat(fromJson.atoms.get(0).criteria()).containsExactly("0");
  }

  /**
   * Old and new approach, difference 2: the criterion's name is built from a technical number,
   * the orderNr in JSON and the position of the atom in the line. The number has no meaning.
   */
  @Test
  void property_template2_criterionNameFollowsOrderNr() throws IOException {
    Element fromLine = Element.of(new CreateKnowledgeElementExpression(line(2)));
    Element fromJson = Element.of(new CreateKnowledgeElementExpression(epdEntry(2)));

    assertThat(fromLine.atoms.get(0).criterionNames()).containsOnly("criterion_2");
    assertThat(fromJson.atoms.get(0).criterionNames()).containsOnly("criterion_3");
  }

  /**
   * Old and new approach, difference 3: both values of template 4 have the same criterion,
   * so JSON lists them in one atom. The old line separates them into two atoms.
   */
  @Test
  void property_template4_isOneAtomWithBothValuesInJsonButTwoAtomsInLines() throws IOException {
    Element fromLine = Element.of(new CreateKnowledgeElementExpression(line(4)));
    Element fromJson = Element.of(new CreateKnowledgeElementExpression(epdEntry(4)));

    assertThat(fromLine.atoms).hasSize(2);
    assertThat(fromJson.atoms).hasSize(1);
    assertThat(fromJson.atoms.get(0).values()).hasSize(2);
  }

  /** Template 4 written as a listing: with "&" instead of ";" the line builds the JSON result. */
  @Test
  void property_template4_lineWithListingBuildsTheSameKnowledge() throws IOException {
    String lineWithListing = HEAD + colour("cap", "mainColour") + "&" + colour("cap", "mainColour_2");

    assertSameKnowledge(lineWithListing, epdEntry(4));
  }

  /** Catalog ID 12786: a property without a value name. */
  @Test
  void property_withoutLabel_buildsTheSameKnowledge() {
    String path = CAUSER + "#IsElem(36634623)";

    assertSameKnowledge(
        HEAD + "KProp(3000):" + path,
        entry(step(2, "\"category\":\"property\",\"criterion\":3000", path)));
  }

  // ---------------------------------------------------------------------------
  // value: two thirds of the catalog, no JSON data yet
  // ---------------------------------------------------------------------------

  /** Catalog ID 6 */
  @Test
  void value_withoutLabel_buildsTheSameKnowledge() {
    String path = CAUSER + "#IsElem(1056964607).GETProp(stateAppearance)";

    assertSameKnowledge(
        HEAD + "KVal(StateAppearance):" + path,
        entry(step(2, "\"category\":\"value\"", path)));
  }

  /** Catalog ID 1 */
  @Test
  void value_withLabel_buildsTheSameKnowledge() {
    String path = CAUSER + "#IsElem(1056964607).GETProp(statePerceptible).GETProp(statePerceptible_cuboid)";

    assertSameKnowledge(
        HEAD + "KVal(SWType_31,statePerceptible_cuboid):" + path,
        entry(step(2, "\"category\":\"value\",\"customLabel\":\"statePerceptible_cuboid\"", path)));
  }

  /** The result type lies in the JSON, but the expression does not use it (so far). */
  @Test
  void value_withResultType_isReadAndBuildsTheSameKnowledge() {
    String path = CAUSER + "#IsElem(1056964607).GETProp(statePerceptible).GETProp(statePerceptible_cuboid)";
    EventPerceptionDescriptionEntry entry = entry(step(2,
        "\"category\":\"value\",\"customLabel\":\"statePerceptible_cuboid\",\"resultType\":\"SWType_31\"", path));

    assertThat(entry.extractions.get(0).resultType).isEqualTo("SWType_31");
    assertSameKnowledge(HEAD + "KVal(SWType_31,statePerceptible_cuboid):" + path, entry);
  }

  // ---------------------------------------------------------------------------
  // relations: supported by both constructors, no data yet
  // ---------------------------------------------------------------------------

  @Test
  void relationUnary_buildsTheSameKnowledge() {
    assertSameKnowledge(
        HEAD + relationLine("KRelSub", "KRelVrb", "KRelAdv"),
        entry(relationSteps("KNOWLEDGE_SUBJECT", "KNOWLEDGE_VERB", "KNOWLEDGE_ADVERB")));
  }

  @Test
  void relationBinary_buildsTheSameKnowledge() {
    assertSameKnowledge(
        HEAD + relationLine("KRelSub", "KRelVrb", "KRelAdv", "KRelObj1"),
        entry(relationSteps("KNOWLEDGE_SUBJECT", "KNOWLEDGE_VERB", "KNOWLEDGE_ADVERB", "KNOWLEDGE_OBJECT_1")));
  }

  @Test
  void relationTernary_buildsTheSameKnowledge() {
    assertSameKnowledge(
        HEAD + relationLine("KRelSub", "KRelVrb", "KRelAdv", "KRelObj1", "KRelObj2"),
        entry(relationSteps("KNOWLEDGE_SUBJECT", "KNOWLEDGE_VERB", "KNOWLEDGE_ADVERB", "KNOWLEDGE_OBJECT_1", "KNOWLEDGE_OBJECT_2")));
  }

  // ---------------------------------------------------------------------------
  // the comparison itself must notice differences
  // ---------------------------------------------------------------------------

  @Test
  void comparison_noticesDifferentPath() {
    String path = CAUSER + "#IsElem(1056964607).GETProp(stateAppearance)";
    String otherPath = CAUSER + "#IsElem(1056964607).GETProp(stateComposition)";

    assertThatThrownBy(() -> assertSameKnowledge(
        HEAD + "KVal(StateAppearance):" + path,
        entry(step(2, "\"category\":\"value\"", otherPath))))
        .isInstanceOf(AssertionError.class);
  }

  @Test
  void comparison_noticesDifferentValueName() {
    String path = CAUSER + "#IsElem(1056964607).GETProp(stateAppearance)";

    assertThatThrownBy(() -> assertSameKnowledge(
        HEAD + "KVal(SWType_31,oneName):" + path,
        entry(step(2, "\"category\":\"value\",\"customLabel\":\"otherName\"", path))))
        .isInstanceOf(AssertionError.class);
  }

  @Test
  void comparison_noticesDifferentCriterion() {
    String path = CAUSER + "#IsElem(36634623)";

    assertThatThrownBy(() -> assertSameKnowledge(
        HEAD + "KProp(3000):" + path,
        entry(step(2, "\"category\":\"property\",\"criterion\":2000", path))))
        .isInstanceOf(AssertionError.class);
  }

  @Test
  void comparison_noticesDifferentSubject() {
    String path = CAUSER + "#IsElem(1056964607).GETProp(stateAppearance)";
    String otherHead = "KSrcT:1&KSrc:GETVal(myself);KSbj:GETVal(event_params).GETVal(event_target);";

    assertThatThrownBy(() -> assertSameKnowledge(
        otherHead + "KVal(StateAppearance):" + path,
        entry(step(2, "\"category\":\"value\"", path))))
        .isInstanceOf(AssertionError.class);
  }

  @Test
  void comparison_noticesDifferentRelationSlot() {
    assertThatThrownBy(() -> assertSameKnowledge(
        HEAD + relationLine("KRelSub", "KRelVrb", "KRelAdv"),
        entry(relationSteps("KNOWLEDGE_VERB", "KNOWLEDGE_SUBJECT", "KNOWLEDGE_ADVERB"))))
        .isInstanceOf(AssertionError.class);
  }

  // ---------------------------------------------------------------------------
  // comparison
  // ---------------------------------------------------------------------------

  private static void assertSameKnowledge(String line, EventPerceptionDescriptionEntry entry) {
    CreateKnowledgeElementExpression expressionLine = new CreateKnowledgeElementExpression(line);
    CreateKnowledgeElementExpression expressionJson = new CreateKnowledgeElementExpression(entry);

    assertThat(expressionLine.isValid()).as("line is valid").isTrue();
    assertThat(expressionJson.isValid()).as("JSON is valid").isTrue();

    Element fromLine = Element.of(expressionLine);
    Element fromJson = Element.of(expressionJson);

    assertThat(fromJson.head).as("subject and source").isEqualTo(fromLine.head);
    assertThat(fromJson.atoms).as("number of atoms").hasSameSizeAs(fromLine.atoms);

    for (int index = 0; index < fromLine.atoms.size(); index++) {
      Atom atomLine = fromLine.atoms.get(index);
      Atom atomJson = fromJson.atoms.get(index);
      String label = "atom " + index + " ";

      assertThat(atomJson.factType).as(label + "fact type").isEqualTo(atomLine.factType);
      assertThat(atomJson.distinctCriteria()).as(label + "criterion").isEqualTo(atomLine.distinctCriteria());
      assertThat(atomJson.values()).as(label + "value names and paths").isEqualTo(atomLine.values());
    }
  }

  /** The parts of a knowledge element that matter: subject, source and atoms. */
  private static final class Element {
    final List<String> head = new ArrayList<String>();
    final List<Atom> atoms = new ArrayList<Atom>();

    static Element of(Expression element) {
      Element result = new Element();
      for (Expression replacement : replacements(element.expression2)) {
        Expression part = replacement.expression1;
        if (part instanceof CreateKnowledgeAtomExpression) {
          result.atoms.add(Atom.of(part));
        } else {
          result.head.add(dump(part));
        }
      }
      return result;
    }
  }

  private static final class Atom {
    private static final String CRITERION = Value.VALUE_NAME_KNOWLEDGE_PROPERTY_CRITERION;

    String factType;
    final List<Expression> entries = new ArrayList<Expression>();

    static Atom of(Expression atom) {
      Atom result = new Atom();
      result.factType = String.valueOf(content(atom.expression1.value));
      result.entries.addAll(replacements(atom.expression2));
      return result;
    }

    List<String> criteria() {
      List<String> result = new ArrayList<String>();
      for (Expression entry : entries) {
        if (isCriterion(entry)) {
          result.add(String.valueOf(content(entry.expression1.value)));
        }
      }
      return result;
    }

    List<String> distinctCriteria() {
      List<String> result = new ArrayList<String>();
      for (String criterion : criteria()) {
        if (!result.contains(criterion)) {
          result.add(criterion);
        }
      }
      return result;
    }

    List<String> criterionNames() {
      List<String> result = new ArrayList<String>();
      for (Expression entry : entries) {
        if (isCriterion(entry)) {
          result.add(name(entry));
        }
      }
      return result;
    }

    List<String> values() {
      List<String> result = new ArrayList<String>();
      for (Expression entry : entries) {
        if (!isCriterion(entry)) {
          result.add(name(entry) + " = " + dump(entry.expression1));
        }
      }
      return result;
    }

    private static boolean isCriterion(Expression entry) {
      return name(entry).startsWith(CRITERION);
    }

    private static String name(Expression replacement) {
      return String.valueOf(content(replacement.value));
    }
  }

  /** Collects the replacements of a sequence in their order, without looking inside them. */
  private static List<Expression> replacements(Expression start) {
    List<Expression> result = new ArrayList<Expression>();
    collectReplacements(start, result);
    return result;
  }

  private static void collectReplacements(Expression expression, List<Expression> result) {
    if (expression == null) {
      return;
    }
    if (expression.operation == Expression_Function.replacement) {
      result.add(expression);
      return;
    }
    collectReplacements(expression.expression1, result);
    collectReplacements(expression.expression2, result);
    collectReplacements(expression.expression3, result);
  }

  private static Object content(Value value) {
    return readField(value, Value.class, "value");
  }

  private static Object readField(Object object, Class<?> declaringClass, String fieldName) {
    try {
      Field field = declaringClass.getDeclaredField(fieldName);
      field.setAccessible(true);
      return field.get(object);
    } catch (ReflectiveOperationException e) {
      throw new IllegalStateException(e);
    }
  }

  /** Writes a whole tree as text, with all fields of its expressions and values. */
  private static String dump(Object object) {
    StringBuilder text = new StringBuilder();
    dump(object, text, new IdentityHashMap<Object, Boolean>());
    return text.toString();
  }

  private static void dump(Object object, StringBuilder text, Map<Object, Boolean> onPath) {
    if (object == null) {
      text.append("null");
    } else if (object instanceof Expression || object instanceof Value) {
      if (onPath.containsKey(object)) {
        text.append("<cycle>");
        return;
      }
      onPath.put(object, Boolean.TRUE);
      dumpFields(object, text, onPath);
      onPath.remove(object);
    } else if (object instanceof String || object instanceof Number
        || object instanceof Boolean || object instanceof Enum) {
      text.append(object);
    } else {
      // access tokens and the like: only their class says something
      text.append(object.getClass().getSimpleName());
    }
  }

  private static void dumpFields(Object object, StringBuilder text, Map<Object, Boolean> onPath) {
    text.append(object.getClass().getSimpleName()).append('{');
    for (Class<?> type = object.getClass(); type != Object.class; type = type.getSuperclass()) {
      for (Field field : type.getDeclaredFields()) {
        if (Modifier.isStatic(field.getModifiers())) {
          continue;
        }
        field.setAccessible(true);
        text.append(field.getName()).append('=');
        try {
          dump(field.get(object), text, onPath);
        } catch (IllegalAccessException e) {
          throw new IllegalStateException(e);
        }
        text.append(' ');
      }
    }
    text.append('}');
  }

  // ---------------------------------------------------------------------------
  // test data
  // ---------------------------------------------------------------------------

  /** The lines of EventPerceptionDescriptionPool.initializeWithTestData_Lines */
  private static String line(int template) {
    switch (template) {
      case 0:
        return HEAD + colour("shirt", "mainColour") + "&" + colour("trousers", "mainColour") + "&" + colour("cap", "mainColour")
            + ";" + material("shirt") + "&" + material("cap");
      case 1:
        return HEAD + colour("cap", "mainColour") + ";" + material("cap");
      case 2:
        return HEAD + material("shirt") + "&" + material("cap");
      case 3:
        return HEAD + colour("cap", "mainColour") + "&" + colour("shirt", "mainColour");
      case 4:
        return HEAD + colour("cap", "mainColour") + ";" + colour("cap", "mainColour_2");
      default:
        throw new IllegalArgumentException("template " + template);
    }
  }

  private static String colour(String item, String label) {
    return "KProp(0,inventory_" + item + ".SOGN_184549375." + label + "):" + inventory(item)
        + ".GETProp(stateAppearance).GETFctVal(getMainColour)";
  }

  private static String material(String item) {
    return "KProp(1,inventory_" + item + ".SOGN_184549375.mainMaterial):" + inventory(item)
        + ".GETProp(stateComposition).GETFctVal(getMainMaterial)";
  }

  private static String inventory(String item) {
    return CAUSER_HUMAN + ".GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_" + item
        + ")#IsElem(184549375)";
  }

  private static String relationLine(String... labels) {
    List<String> parts = new ArrayList<String>();
    for (int index = 0; index < labels.length; index++) {
      parts.add(labels[index] + ":" + relationPath(index));
    }
    return String.join("&", parts);
  }

  private static String relationSteps(String... slots) {
    List<String> steps = new ArrayList<String>();
    for (int index = 0; index < slots.length; index++) {
      steps.add(step(2, "\"category\":\"element\",\"slot\":\"" + slots[index] + "\"", relationPath(index)));
    }
    return String.join(",", steps);
  }

  private static String relationPath(int index) {
    return CAUSER + ".GETProp(relationPart_" + index + ")";
  }

  private static String step(int orderNr, String fields, String path) {
    List<String> quoted = new ArrayList<String>();
    for (String pathStep : path.split("\\.")) {
      quoted.add("\"" + pathStep + "\"");
    }
    return "{\"orderNr\":" + orderNr + "," + fields + ",\"pathSteps\":[" + String.join(",", quoted) + "]}";
  }

  private static EventPerceptionDescriptionEntry entry(String steps) {
    return GSON.fromJson("{" + JSON_HEAD + "\"extractions\":[" + steps + "]}", EventPerceptionDescriptionEntry.class);
  }

  /** The entry of one row of swpool_epd.sql, read the same way as the pool does it. */
  private static EventPerceptionDescriptionEntry epdEntry(int perceptionType) throws IOException {
    // The SQL file is not part of the repository: without it the test is skipped.
    assumeTrue(Files.exists(Paths.get(SQL_EPD)), SQL_EPD + " is not available");

    String sql = new String(Files.readAllBytes(Paths.get(SQL_EPD)), StandardCharsets.UTF_8);
    Matcher row = Pattern.compile("\\(508," + perceptionType + ",'(.*?)'\\)\\s*[,;]").matcher(sql);
    assertThat(row.find()).as("row " + perceptionType + " in " + SQL_EPD).isTrue();

    JsonEventPerceptionDescription description = GSON.fromJson(row.group(1), JsonEventPerceptionDescription.class);
    assertThat(description.entrys).hasSize(1);
    return description.entrys.get(0);
  }
}
