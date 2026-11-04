package org.socialworld.tools.pct;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.Value;

public class PerceptionGenerationTest {

  private static final String COLOUR_OF_CAP = "inventory_cap.SOGN_184549375.mainColour";

  private final PerceptionGeneration generation = new PerceptionGeneration();

  @Test
  void valuePaths_haveNoCriterion() {
    for (PerceptionPath path : valuePaths()) {
      assertThat(path.isProperty()).as(path.toString()).isFalse();
    }
  }

  @Test
  void propertyPaths_haveAPropertyCriterion() {
    for (PerceptionPath path : propertyPaths()) {
      assertThat(path.getCriterion()).as(path.toString()).isBetween(0, 999);
    }
  }

  @Test
  void paths_readSomethingBehindTheGroupingCheck() {
    assertThat(valuePaths()).allMatch(path -> path.getSteps().size() > 2);
    assertThat(propertyPaths()).allMatch(path -> path.getSteps().size() > 2);
  }

  @Test
  void paths_endOnTheEnumNotOnItsIndex() {
    assertThat(propertyPaths()).noneMatch(path -> path.getSteps().contains("GETFctVal(getIndex)"));
    assertThat(valuePaths()).noneMatch(path -> path.getSteps().contains("GETFctVal(getIndex)"));
  }

  @Test
  void paths_areUnique() {
    List<PerceptionPath> values = valuePaths();
    List<PerceptionPath> properties = propertyPaths();

    assertThat(new HashSet<PerceptionPath>(values)).hasSameSizeAs(values);
    assertThat(new HashSet<PerceptionPath>(properties)).hasSameSizeAs(properties);
  }

  @Test
  void propertyPaths_containTheColourOfTheCapFromTheExistingData() {
    PerceptionPath expected = new PerceptionPath(0, COLOUR_OF_CAP, "", Arrays.asList(
        "GETVal(event_params)",
        "GETVal(event_causer)#IsElem(50331647)",
        "GETProp(stateInventory)",
        "GETProp(stateInventory_inventory)",
        "GETProp(inventory_cap)#IsElem(184549375)",
        "GETProp(stateAppearance)",
        "GETFctVal(getMainColour)"));

    assertThat(propertyPaths()).contains(expected);
  }

  @Test
  void valuePaths_knowTheirResultType() {
    assertThat(valuePaths()).allMatch(path -> !path.getResultType().isEmpty());
  }

  @Test
  void resultType_ofAValuePathIsTheClassAtItsEnd() {
    assertThat(valuePaths())
        .filteredOn(path -> path.getSteps().get(path.getSteps().size() - 1).equals("GETProp(statePerceptible_cuboid)"))
        .isNotEmpty()
        .allMatch(path -> path.getResultType().equals("SWType_31"));
  }

  @Test
  void propertyPaths_haveNoResultType_becauseTheirTypeIsImplicit() {
    assertThat(propertyPaths()).allMatch(path -> path.getResultType().isEmpty());
  }

  @Test
  void subject_decidesTheStartOfThePath() {
    assertThat(generation.generateAllPropertyPaths(Value.VALUE_BY_NAME_EVENT_TARGET))
        .allMatch(path -> path.getSteps().get(1).startsWith("GETVal(event_target)#"));
  }

  private List<PerceptionPath> valuePaths() {
    return generation.generateAllValuePaths(Value.VALUE_BY_NAME_EVENT_CAUSER);
  }

  private List<PerceptionPath> propertyPaths() {
    return generation.generateAllPropertyPaths(Value.VALUE_BY_NAME_EVENT_CAUSER);
  }
}
