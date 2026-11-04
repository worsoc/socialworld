package org.socialworld.tools.pct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.EventPerceptionDescription;
import org.socialworld.calculation.expressions.CreateKnowledgeElementExpression;
import org.socialworld.core.EventType;
import org.socialworld.datasource.parsing.JsonEventPerceptionDescription;
import org.socialworld.datasource.parsing.JsonExtractionStep;

import com.google.gson.Gson;

public class PerceptionDescriptionComposerTest {

  private static final String SQL_EPD = "src/test/swpool_epd.sql";
  private static final String CAUSER = Value.VALUE_BY_NAME_EVENT_CAUSER;

  private final PerceptionDescriptionComposer composer = new PerceptionDescriptionComposer();
  private final PerceptionGeneration generation = new PerceptionGeneration();

  // ---------------------------------------------------------------------------
  // the existing data, word for word
  // ---------------------------------------------------------------------------

  @Test
  void template0_isWordForWordTheExistingData() throws IOException {
    assertSameAsExistingData(0, Arrays.asList(
        colour("shirt", "mainColour"), colour("trousers", "mainColour"), colour("cap", "mainColour"),
        material("shirt"), material("cap")));
  }

  /** Only material: the listing keeps its number, nothing moves up. */
  @Test
  void template2_isWordForWordTheExistingData() throws IOException {
    assertSameAsExistingData(2, Arrays.asList(material("shirt"), material("cap")));
  }

  /** Two colours of one criterion are one listing. */
  @Test
  void template4_isWordForWordTheExistingData() throws IOException {
    assertSameAsExistingData(4, Arrays.asList(colour("cap", "mainColour"), colour("cap", "mainColour_2")));
  }

  // ---------------------------------------------------------------------------
  // listing and blocks
  // ---------------------------------------------------------------------------

  @Test
  void pathsOfOneCriterion_becomeOneBlock_evenIfTheyWereSelectedApart() {
    List<JsonExtractionStep> steps = extractions(Arrays.asList(
        colour("shirt", "mainColour"), material("shirt"), colour("cap", "mainColour")));

    assertThat(steps).extracting(step -> step.customLabel).containsExactly(
        "inventory_shirt.SOGN_184549375.mainColour",
        "inventory_cap.SOGN_184549375.mainColour",
        "inventory_shirt.SOGN_184549375.mainMaterial");
    assertThat(steps.get(0).orderNr).isEqualTo(steps.get(1).orderNr);
    assertThat(steps.get(2).orderNr).isNotEqualTo(steps.get(0).orderNr);
  }

  @Test
  void everyValue_getsItsOwnBlock_apartFromTheProperties() {
    List<JsonExtractionStep> steps = extractions(Arrays.asList(
        value("SWType_31", "one"), colour("cap", "mainColour"), value("SWType_3", "two"), material("cap")));

    assertThat(steps).extracting(step -> step.orderNr).doesNotHaveDuplicates();
  }

  // ---------------------------------------------------------------------------
  // the fields in the JSON
  // ---------------------------------------------------------------------------

  @Test
  void value_writesItsResultType_andCategory() {
    String json = composer.toJson(composer.compose(
        EventType.percipientExistsDistance5000, 0, 100, CAUSER, Arrays.asList(value("SWType_31", "one"))));

    assertThat(json).contains("\"category\":\"value\"").contains("\"resultType\":\"SWType_31\"");
  }

  @Test
  void property_writesNoResultType() {
    String json = composer.toJson(composer.compose(
        EventType.percipientExistsDistance5000, 0, 100, CAUSER, Arrays.asList(colour("cap", "mainColour"))));

    assertThat(json).doesNotContain("resultType");
  }

  @Test
  void value_withoutName_writesNoLabel() {
    String json = composer.toJson(composer.compose(
        EventType.percipientExistsDistance5000, 0, 100, CAUSER, Arrays.asList(value("StateAppearance", ""))));

    assertThat(json).doesNotContain("customLabel");
  }

  // ---------------------------------------------------------------------------
  // the simulation can use the result
  // ---------------------------------------------------------------------------

  @Test
  void composedJson_isLoadedLikeThePoolDoesIt() {
    String json = composer.toJson(composer.compose(
        EventType.percipientExistsDistance5000, 7, 100, CAUSER,
        Arrays.asList(colour("cap", "mainColour"), material("cap"), value("SWType_31", "one"))));

    EventPerceptionDescription description = new EventPerceptionDescription(new Gson(), json);
    description.setFunctions();

    assertThat(description.getEventType()).isEqualTo(EventType.percipientExistsDistance5000);
    assertThat(description.getPerceptionType()).isEqualTo(7);
    assertThat(description.countFunctions()).isEqualTo(1);
  }

  @Test
  void everyGeneratedPath_buildsAValidExpression() {
    List<PerceptionPath> paths = new ArrayList<PerceptionPath>();
    paths.addAll(generation.generateAllValuePaths(CAUSER));
    paths.addAll(generation.generateAllPropertyPaths(CAUSER));

    for (PerceptionPath path : paths) {
      JsonEventPerceptionDescription description = composer.compose(
          EventType.percipientExistsDistance5000, 0, 100, CAUSER, Arrays.asList(path));

      assertThat(new CreateKnowledgeElementExpression(description.entrys.get(0)).isValid())
          .as(path.toString()).isTrue();
    }
  }

  // ---------------------------------------------------------------------------
  // test data
  // ---------------------------------------------------------------------------

  private void assertSameAsExistingData(int perceptionType, List<PerceptionPath> paths) throws IOException {
    String json = composer.toJson(composer.compose(
        EventType.percipientExistsDistance5000, perceptionType, 100, CAUSER, paths));

    assertThat(json).isEqualTo(rowOfExistingData(perceptionType));
  }

  private List<JsonExtractionStep> extractions(List<PerceptionPath> paths) {
    return composer.compose(EventType.percipientExistsDistance5000, 0, 100, CAUSER, paths).entrys.get(0).extractions;
  }

  private static PerceptionPath colour(String item, String label) {
    return new PerceptionPath(0, "inventory_" + item + ".SOGN_184549375." + label, "",
        inventorySteps(item, "GETProp(stateAppearance)", "GETFctVal(getMainColour)"));
  }

  private static PerceptionPath material(String item) {
    return new PerceptionPath(1, "inventory_" + item + ".SOGN_184549375.mainMaterial", "",
        inventorySteps(item, "GETProp(stateComposition)", "GETFctVal(getMainMaterial)"));
  }

  private static PerceptionPath value(String resultType, String name) {
    return new PerceptionPath(PerceptionPath.NO_CRITERION, name, resultType, Arrays.asList(
        "GETVal(event_params)", "GETVal(event_causer)#IsElem(1056964607)", "GETProp(stateAppearance)"));
  }

  private static List<String> inventorySteps(String item, String... lastSteps) {
    List<String> steps = new ArrayList<String>(Arrays.asList(
        "GETVal(event_params)",
        "GETVal(event_causer)#IsElem(50331647)",
        "GETProp(stateInventory)",
        "GETProp(stateInventory_inventory)",
        "GETProp(inventory_" + item + ")#IsElem(184549375)"));
    steps.addAll(Arrays.asList(lastSteps));
    return steps;
  }

  private static String rowOfExistingData(int perceptionType) throws IOException {
    // The SQL file is not part of the repository: without it the test is skipped.
    assumeTrue(Files.exists(Paths.get(SQL_EPD)), SQL_EPD + " is not available");

    String sql = new String(Files.readAllBytes(Paths.get(SQL_EPD)), StandardCharsets.UTF_8);
    Matcher row = Pattern.compile("\\(508," + perceptionType + ",'(.*?)'\\)\\s*[,;]").matcher(sql);
    assertThat(row.find()).as("row " + perceptionType + " in " + SQL_EPD).isTrue();
    return row.group(1);
  }
}
