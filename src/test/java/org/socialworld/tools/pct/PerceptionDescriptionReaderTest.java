package org.socialworld.tools.pct;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.Value;
import org.socialworld.core.EventType;

public class PerceptionDescriptionReaderTest {

  private static final EventType EVENT = EventType.percipientExistsDistance5000;
  private static final String CAUSER = Value.VALUE_BY_NAME_EVENT_CAUSER;
  private static final String TARGET = Value.VALUE_BY_NAME_EVENT_TARGET;

  private final PerceptionDescriptionReader reader = new PerceptionDescriptionReader();
  private final PerceptionDescriptionComposer composer = new PerceptionDescriptionComposer();

  // ---------------------------------------------------------------------------
  // round trip: load, compose again, nothing has changed
  // ---------------------------------------------------------------------------

  @Test
  void all40Rows_comeBackWordForWord() {
    List<String> rows = rowsOfTheTool();
    assertThat(rows).hasSize(40);

    for (String json : rows) {
      PerceptionSelection selection = reader.read(json);

      assertThat(selection.isEditable()).as(selection.getReasonNotEditable()).isTrue();
      assertThat(composeAgain(selection)).isEqualTo(json);
    }
  }

  @Test
  void valuesWithTypeAndWithoutName_comeBackWordForWord() {
    String json = composer.toJson(composer.compose(EVENT, 3, 80, CAUSER, Arrays.asList(
        value("SWType_31", "one"), colour("cap"), value("StateAppearance", ""), material("cap"))));

    PerceptionSelection selection = reader.read(json);

    assertThat(selection.isEditable()).as(selection.getReasonNotEditable()).isTrue();
    assertThat(composeAgain(selection)).isEqualTo(json);
  }

  // ---------------------------------------------------------------------------
  // what is read
  // ---------------------------------------------------------------------------

  @Test
  void settings_areRead() {
    PerceptionSelection selection = reader.read(
        composer.toJson(composer.compose(EVENT, 7, 80, TARGET, Arrays.asList(colour("cap")))));

    assertThat(selection.getEventType()).isEqualTo(EVENT);
    assertThat(selection.getPerceptionType()).isEqualTo(7);
    assertThat(selection.getRelevanceThreshold()).isEqualTo(80);
    assertThat(selection.getSubjectValueName()).isEqualTo(TARGET);
  }

  @Test
  void paths_areRead_withTheirKindAndType() {
    PerceptionPath colour = colour("cap");
    PerceptionPath value = value("SWType_31", "one");

    PerceptionSelection selection = reader.read(
        composer.toJson(composer.compose(EVENT, 0, 100, CAUSER, Arrays.asList(colour, value))));

    assertThat(selection.getPaths()).containsExactly(colour, value);
  }

  @Test
  void valueOfAnOldRowWithoutType_isReadAndStaysWithoutType() {
    String json = "{\"eventType\":\"percipientExistsDistance5000\",\"perceptionType\":0,\"relevanceThreshold\":100,"
        + "\"entrys\":[{\"relevanceThreshold\":\"100\",\"sourceCategory\":1,\"sourcePathSteps\":[\"GETVal(myself)\"],"
        + "\"subjectPathSteps\":[\"GETVal(event_params)\",\"GETVal(event_causer)\"],"
        + "\"extractions\":[{\"orderNr\":2,\"category\":\"value\",\"criterion\":0,\"pathSteps\":[\"GETVal(a)\"]}]}]}";

    PerceptionSelection selection = reader.read(json);

    assertThat(selection.isEditable()).as(selection.getReasonNotEditable()).isTrue();
    assertThat(selection.getPaths().get(0).getResultType()).isEmpty();
    assertThat(composeAgain(selection)).isEqualTo(json);
  }

  /** The material over ALL_ANIMALS_AND_ITEMS must survive loading and saving. */
  @Test
  void pathsOutsideOfTheMetaInformation_areKept() {
    List<PerceptionPath> outside = PerceptionTemplates.findPathsOutsideOfMetaInformation();
    assertThat(outside).isNotEmpty();

    PerceptionSelection selection = reader.read(composer.toJson(composer.compose(EVENT, 0, 100, CAUSER, outside)));

    assertThat(selection.isEditable()).as(selection.getReasonNotEditable()).isTrue();
    assertThat(selection.getPaths()).containsExactlyInAnyOrderElementsOf(outside);
  }

  // ---------------------------------------------------------------------------
  // what can only be looked at
  // ---------------------------------------------------------------------------

  @Test
  void brokenJson_isNotEditable() {
    assertNotEditable("{ this is no json");
    assertNotEditable("");
  }

  @Test
  void unknownEvent_isNotEditable() {
    assertNotEditable(row(0).replace("percipientExistsDistance5000", "noSuchEvent"));
  }

  @Test
  void twoEntries_areNotEditable() {
    String json = row(0);
    int start = json.indexOf("\"entrys\":[") + "\"entrys\":[".length();
    String entry = json.substring(start, json.lastIndexOf("]}"));

    assertNotEditable(json.substring(0, start) + entry + "," + entry + "]}");
  }

  @Test
  void otherSource_isNotEditable() {
    assertNotEditable(row(0).replace("\"sourceCategory\":1", "\"sourceCategory\":2"));
    assertNotEditable(row(0).replace("GETVal(myself)", "GETVal(somebody)"));
  }

  @Test
  void otherFormOfTheSubject_isNotEditable() {
    assertNotEditable(row(0).replace(
        "\"subjectPathSteps\":[\"GETVal(event_params)\",\"GETVal(event_causer)\"]",
        "\"subjectPathSteps\":[\"GETVal(event_params)\",\"GETProp(a)\"]"));
  }

  @Test
  void differentThresholds_areNotEditable() {
    assertNotEditable(row(0).replace("\"relevanceThreshold\":\"100\"", "\"relevanceThreshold\":\"50\""));
  }

  @Test
  void relation_isNotEditable() {
    assertNotEditable(row(1).replace("\"category\":\"property\"", "\"category\":\"element\""));
  }

  /** A second block for the same criterion would be merged by composing, so it must not be saved. */
  @Test
  void criterionSplitIntoTwoBlocks_isNotEditable() {
    String firstColourOfTwo = "\"orderNr\":2,\"category\":\"property\",\"criterion\":0,\"customLabel\":\"inventory_cap.SOGN_184549375.mainColour\"";
    assertThat(row(3)).contains(firstColourOfTwo);

    assertNotEditable(row(3).replace(firstColourOfTwo, firstColourOfTwo.replace("\"orderNr\":2", "\"orderNr\":9")));
  }

  // ---------------------------------------------------------------------------
  // test data
  // ---------------------------------------------------------------------------

  private void assertNotEditable(String json) {
    PerceptionSelection selection = reader.read(json);

    assertThat(selection.isEditable()).as(json).isFalse();
    assertThat(selection.getReasonNotEditable()).isNotEmpty();
    assertThat(selection.getPaths()).isEmpty();
  }

  private String composeAgain(PerceptionSelection selection) {
    return composer.toJson(composer.compose(
        selection.getEventType(), selection.getPerceptionType(), selection.getRelevanceThreshold(),
        selection.getSubjectValueName(), selection.getPaths()));
  }

  private static String row(int perceptionType) {
    return rowsOfTheTool().get(perceptionType);
  }

  private static List<String> rowsOfTheTool() {
    List<String> rows = new ArrayList<String>();
    Matcher row = Pattern.compile("\\(508,\\d+,'(.*?)'\\)\\s*[,;]").matcher(PerceptionSqlFile.createContent());
    while (row.find()) {
      rows.add(row.group(1));
    }
    return rows;
  }

  private static PerceptionPath colour(String item) {
    return new PerceptionPath(0, "inventory_" + item + ".mainColour", "", Arrays.asList("GETVal(event_params)", "GETVal(event_causer)#IsElem(50331647)", "GETProp(inventory_" + item + ")", "GETFctVal(getMainColour)"));
  }

  private static PerceptionPath material(String item) {
    return new PerceptionPath(1, "inventory_" + item + ".mainMaterial", "", Arrays.asList("GETVal(event_params)", "GETVal(event_causer)#IsElem(50331647)", "GETProp(inventory_" + item + ")", "GETFctVal(getMainMaterial)"));
  }

  private static PerceptionPath value(String resultType, String name) {
    return new PerceptionPath(PerceptionPath.NO_CRITERION, name, resultType, Arrays.asList("GETVal(event_params)", "GETVal(event_causer)#IsElem(50331647)", "GETProp(stateAppearance)"));
  }
}
