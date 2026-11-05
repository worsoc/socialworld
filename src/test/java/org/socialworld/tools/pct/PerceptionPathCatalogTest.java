package org.socialworld.tools.pct;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.Value;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.tools.pct.PerceptionPathFilter.Kind;

public class PerceptionPathCatalogTest {

  private static final String CAUSER = Value.VALUE_BY_NAME_EVENT_CAUSER;
  private static final String TARGET = Value.VALUE_BY_NAME_EVENT_TARGET;
  private static final int COLOUR = 0;
  private static final int MATERIAL = 1;

  private static final PerceptionPathCatalog CATALOG = new PerceptionPathCatalog();

  private static List<PerceptionPath> find(PerceptionPathFilter filter) {
    return CATALOG.find(CAUSER, filter);
  }

  @Test
  void noFilter_findsAllPathsOfTheGenerator() {
    PerceptionGeneration generation = new PerceptionGeneration();

    assertThat(find(PerceptionPathFilter.NONE)).hasSize(
        generation.generateAllValuePaths(CAUSER).size() + generation.generateAllPropertyPaths(CAUSER).size());
  }

  @Test
  void kind_splitsTheCatalog() {
    List<PerceptionPath> values = find(PerceptionPathFilter.NONE.withKind(Kind.VALUE));
    List<PerceptionPath> properties = find(PerceptionPathFilter.NONE.withKind(Kind.PROPERTY));

    assertThat(values).isNotEmpty().noneMatch(PerceptionPath::isProperty);
    assertThat(properties).isNotEmpty().allMatch(PerceptionPath::isProperty);
    assertThat(values.size() + properties.size()).isEqualTo(find(PerceptionPathFilter.NONE).size());
  }

  @Test
  void criterion_findsOnlyItsPaths() {
    assertThat(find(PerceptionPathFilter.NONE.withCriterion(COLOUR)))
        .isNotEmpty().allMatch(path -> path.getCriterion() == COLOUR);
    assertThat(find(PerceptionPathFilter.NONE.withCriterion(MATERIAL)))
        .isNotEmpty().allMatch(path -> path.getCriterion() == MATERIAL);
  }

  @Test
  void subjectGroup_findsOnlyItsPaths() {
    int animals = GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ANIMALS;

    assertThat(find(PerceptionPathFilter.NONE.withSubjectGroup(animals)))
        .isNotEmpty().allMatch(path -> path.getSteps().get(1).equals("GETVal(event_causer)#IsElem(" + animals + ")"));
  }

  @Test
  void subjectGroup_looksOnlyAtTheSubject_notAtGroupsFurtherOnThePath() {
    int animalsAndItems = GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ANIMALS_AND_ITEMS;

    assertThat(find(PerceptionPathFilter.NONE.withSubjectGroup(animalsAndItems)))
        .allMatch(path -> path.getSubjectGroup() == animalsAndItems);
  }

  @Test
  void text_searchesInNameAndSteps_withoutCase() {
    assertThat(find(PerceptionPathFilter.NONE.withText("INVENTORY_CAP"))).isNotEmpty()
        .allMatch(path -> path.toString().contains("inventory_cap"));
    assertThat(find(PerceptionPathFilter.NONE.withText("  getMainMaterial "))).isNotEmpty()
        .allMatch(path -> path.toString().contains("getMainMaterial"));
    assertThat(find(PerceptionPathFilter.NONE.withText("noSuchThingAnywhere"))).isEmpty();
  }

  @Test
  void filters_workTogether() {
    int animals = GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ANIMALS;

    List<PerceptionPath> found = find(PerceptionPathFilter.NONE
        .withKind(Kind.PROPERTY)
        .withCriterion(COLOUR)
        .withSubjectGroup(animals)
        .withText("inventory_cap"));

    assertThat(found).isNotEmpty().allMatch(path -> path.getCriterion() == COLOUR
        && path.getSubjectGroup() == animals && path.getValueName().contains("inventory_cap"));
    assertThat(found).extracting(PerceptionPath::getValueName).contains("inventory_cap.SOGN_184549375.mainColour");
  }

  @Test
  void filter_isNotChangedByItsWithMethods() {
    PerceptionPathFilter property = PerceptionPathFilter.NONE.withKind(Kind.PROPERTY);

    property.withKind(Kind.VALUE);

    assertThat(find(property)).allMatch(PerceptionPath::isProperty);
    assertThat(find(PerceptionPathFilter.NONE)).anyMatch(path -> !path.isProperty());
  }

  @Test
  void subject_decidesWhichPathsAreFound() {
    assertThat(CATALOG.find(TARGET, PerceptionPathFilter.NONE)).isNotEmpty()
        .allMatch(path -> path.getSteps().get(1).startsWith("GETVal(event_target)#"));
  }

  @Test
  void subjectGroups_areOnlyThoseWithPaths_andHaveNames() {
    List<Integer> groups = CATALOG.getSubjectGroups(CAUSER);

    assertThat(groups).isNotEmpty();
    for (int group : groups) {
      assertThat(find(PerceptionPathFilter.NONE.withSubjectGroup(group))).isNotEmpty();
      assertThat(CATALOG.getGroupName(group)).as("name of " + group).startsWith("ALL_");
    }
  }

  @Test
  void groupName_comesFromTheConstant() {
    assertThat(CATALOG.getGroupName(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ANIMALS)).isEqualTo("ALL_ANIMALS");
    assertThat(CATALOG.getGroupName(12345)).isEqualTo("12345");
  }

  @Test
  void contains_tellsWhetherAPathIsOffered() {
    PerceptionPath colour = find(PerceptionPathFilter.NONE.withCriterion(COLOUR)).get(0);
    PerceptionPath unknown = new PerceptionPath(COLOUR, "unknown", "", Arrays.asList("GETVal(unknown)"));

    assertThat(CATALOG.contains(CAUSER, colour)).isTrue();
    assertThat(CATALOG.contains(CAUSER, unknown)).isFalse();
  }
}
