package org.socialworld.tools.pct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.socialworld.calculation.Value;
import org.socialworld.core.EventType;

public class PerceptionEditorTest {

  private static final EventType EVENT = EventType.percipientExistsDistance5000;
  private static final EventType OTHER_EVENT = EventType.selfSleep;
  private static final int EVENT_INDEX = EVENT.getIndex();
  private static final String CAUSER = Value.VALUE_BY_NAME_EVENT_CAUSER;
  private static final String TARGET = Value.VALUE_BY_NAME_EVENT_TARGET;

  private static final PerceptionPath COLOUR_CAP = property(0, "cap");
  private static final PerceptionPath COLOUR_SHIRT = property(0, "shirt");
  private static final PerceptionPath MATERIAL_CAP = property(1, "capMaterial");
  private static final PerceptionPath VALUE = new PerceptionPath(PerceptionPath.NO_CRITERION, "v", "SWType_31", steps("v"));

  private InMemoryPerceptionDescriptionStore store;
  private PerceptionEditor editor;

  @BeforeEach
  void setUp() {
    store = new InMemoryPerceptionDescriptionStore();
    editor = new PerceptionEditor(store);
  }

  // ---------------------------------------------------------------------------
  // opening
  // ---------------------------------------------------------------------------

  @Test
  void emptySlot_startsEmpty_withTheGivenSubject() {
    editor.open(EVENT, 40, TARGET);

    assertThat(editor.isEditable()).isTrue();
    assertThat(editor.isChanged()).isFalse();
    assertThat(editor.getPaths()).isEmpty();
    assertThat(editor.getSubject()).isEqualTo(TARGET);
    assertThat(editor.getRelevanceThreshold()).isEqualTo(PerceptionEditor.DEFAULT_RELEVANCE_THRESHOLD);
    assertThat(editor.canSave()).isFalse();
  }

  @Test
  void filledSlot_opensWithItsContent() {
    saveInto(3, TARGET, 80, COLOUR_CAP);

    editor.open(EVENT, 3, CAUSER);

    assertThat(editor.getPaths()).containsExactly(COLOUR_CAP);
    assertThat(editor.getSubject()).isEqualTo(TARGET);
    assertThat(editor.getRelevanceThreshold()).isEqualTo(80);
    assertThat(editor.isChanged()).isFalse();
  }

  @Test
  void exampleRow_opensAndComesOutWordForWord() {
    store = InMemoryPerceptionDescriptionStore.withExampleData();
    editor = new PerceptionEditor(store);

    for (int slot = 0; slot < 40; slot++) {
      editor.open(EVENT, slot, CAUSER);
      assertThat(editor.getJson()).isEqualTo(PerceptionSqlFile.createJson(slot));
    }
  }

  // ---------------------------------------------------------------------------
  // what can only be looked at
  // ---------------------------------------------------------------------------

  @Test
  void brokenRow_canOnlyBeLookedAt_andStaysAsItIs() {
    store.save(EVENT_INDEX, 0, "{ no json");

    editor.open(EVENT, 0, CAUSER);

    assertThat(editor.isEditable()).isFalse();
    assertThat(editor.getReasonNotEditable()).isNotEmpty();
    assertThat(editor.getJson()).isEqualTo("{ no json");
    assertThat(editor.canSave()).isFalse();
    assertThat(editor.isSlotEditable(0)).isFalse();
    assertThatThrownBy(() -> editor.add(Arrays.asList(COLOUR_CAP))).isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> editor.save(Collections.emptyList())).isInstanceOf(IllegalStateException.class);
    assertThat(store.loadAll(EVENT_INDEX).get(0)).isEqualTo("{ no json");
  }

  @Test
  void rowThatBelongsToAnotherSlot_canOnlyBeLookedAt() {
    saveInto(3, CAUSER, 100, COLOUR_CAP);
    store.save(EVENT_INDEX, 7, store.loadAll(EVENT_INDEX).get(3));

    editor.open(EVENT, 7, CAUSER);

    assertThat(editor.isEditable()).isFalse();
    assertThat(editor.getReasonNotEditable()).contains("slot 3").contains("slot 7");
  }

  @Test
  void rowThatBelongsToAnotherEvent_canOnlyBeLookedAt() {
    saveInto(3, CAUSER, 100, COLOUR_CAP);
    store.save(OTHER_EVENT.getIndex(), 3, store.loadAll(EVENT_INDEX).get(3));

    editor.open(OTHER_EVENT, 3, CAUSER);

    assertThat(editor.isEditable()).isFalse();
  }

  // ---------------------------------------------------------------------------
  // changing
  // ---------------------------------------------------------------------------

  @Test
  void add_keepsTheCriteriaTogether_inTheOrderOfTheJson() {
    editor.open(EVENT, 0, CAUSER);

    editor.add(Arrays.asList(COLOUR_CAP, MATERIAL_CAP, VALUE, COLOUR_SHIRT));

    assertThat(editor.getPaths()).containsExactly(COLOUR_CAP, COLOUR_SHIRT, MATERIAL_CAP, VALUE);
    assertThat(editor.isChanged()).isTrue();
  }

  @Test
  void savedOrder_isTheOrderAfterOpeningAgain() {
    editor.open(EVENT, 0, CAUSER);
    editor.add(Arrays.asList(COLOUR_CAP, MATERIAL_CAP, VALUE, COLOUR_SHIRT));
    editor.save(Collections.emptyList());

    editor.open(EVENT, 0, CAUSER);

    assertThat(editor.isEditable()).as(editor.getReasonNotEditable()).isTrue();
    assertThat(editor.getPaths()).containsExactly(COLOUR_CAP, COLOUR_SHIRT, MATERIAL_CAP, VALUE);
  }

  @Test
  void add_leavesOutWhatIsAlreadySelected() {
    editor.open(EVENT, 0, CAUSER);
    editor.add(Arrays.asList(COLOUR_CAP));

    assertThat(editor.add(Arrays.asList(COLOUR_CAP, COLOUR_SHIRT))).isEqualTo(1);
    assertThat(editor.getPaths()).containsExactly(COLOUR_CAP, COLOUR_SHIRT);
  }

  @Test
  void addingNothingNew_isNoChange() {
    saveInto(0, CAUSER, 100, COLOUR_CAP);
    editor.open(EVENT, 0, CAUSER);

    editor.add(Arrays.asList(COLOUR_CAP));
    editor.remove(Arrays.asList(COLOUR_SHIRT));
    editor.setRelevanceThreshold(100);

    assertThat(editor.isChanged()).isFalse();
  }

  @Test
  void remove_isAChange_andNothingIsSavedYet() {
    saveInto(0, CAUSER, 100, COLOUR_CAP, COLOUR_SHIRT);
    String stored = store.loadAll(EVENT_INDEX).get(0);
    editor.open(EVENT, 0, CAUSER);

    editor.remove(Arrays.asList(COLOUR_CAP));

    assertThat(editor.isChanged()).isTrue();
    assertThat(editor.getPaths()).containsExactly(COLOUR_SHIRT);
    assertThat(store.loadAll(EVENT_INDEX).get(0)).isEqualTo(stored);
  }

  @Test
  void threshold_mustBePositive() {
    editor.open(EVENT, 0, CAUSER);

    assertThatThrownBy(() -> editor.setRelevanceThreshold(0)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void subject_canOnlyChangeWhileNoPathIsSelected() {
    editor.open(EVENT, 0, CAUSER);
    assertThat(editor.setSubject(TARGET)).isTrue();
    assertThat(editor.getSubject()).isEqualTo(TARGET);

    editor.add(Arrays.asList(COLOUR_CAP));

    assertThat(editor.setSubject(CAUSER)).isFalse();
    assertThat(editor.getSubject()).isEqualTo(TARGET);
  }

  // ---------------------------------------------------------------------------
  // saving
  // ---------------------------------------------------------------------------

  @Test
  void save_writesTheOpenedSlot() {
    editor.open(EVENT, 5, CAUSER);
    editor.add(Arrays.asList(COLOUR_CAP));

    assertThat(editor.save(Collections.emptyList())).containsExactly(5);

    assertThat(store.loadAll(EVENT_INDEX)).containsOnlyKeys(5);
    assertThat(editor.isChanged()).isFalse();
    assertThat(editor.isFilled(5)).isTrue();
  }

  @Test
  void save_toSeveralSlots_givesEachItsOwnPerceptionType() {
    editor.open(EVENT, 3, CAUSER);
    editor.add(Arrays.asList(COLOUR_CAP));

    assertThat(editor.save(Arrays.asList(8, 13))).containsExactly(3, 8, 13);

    for (int slot : new int[] {3, 8, 13}) {
      PerceptionSelection selection = new PerceptionDescriptionReader().read(store.loadAll(EVENT_INDEX).get(slot));
      assertThat(selection.getPerceptionType()).isEqualTo(slot);
      assertThat(selection.getPaths()).containsExactly(COLOUR_CAP);
    }
  }

  @Test
  void save_usesTheIndexOfTheEvent_notItsPositionInTheEnum() {
    editor.open(EVENT, 0, CAUSER);
    editor.add(Arrays.asList(COLOUR_CAP));

    editor.save(Collections.emptyList());

    assertThat(store.loadAll(508)).hasSize(1);
  }

  @Test
  void emptySelection_cannotBeSaved() {
    saveInto(0, CAUSER, 100, COLOUR_CAP);
    editor.open(EVENT, 0, CAUSER);
    editor.remove(Arrays.asList(COLOUR_CAP));

    assertThat(editor.canSave()).isFalse();
    assertThatThrownBy(() -> editor.save(Collections.emptyList())).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void otherFilledSlots_areFound_withoutTheOpenedOne() {
    saveInto(3, CAUSER, 100, COLOUR_CAP);
    saveInto(8, CAUSER, 100, COLOUR_CAP);
    editor.open(EVENT, 3, CAUSER);

    assertThat(editor.findOtherFilledSlots(Arrays.asList(13, 8, 3))).containsExactly(8);
  }

  // ---------------------------------------------------------------------------
  // test data
  // ---------------------------------------------------------------------------

  private void saveInto(int slot, String subject, int threshold, PerceptionPath... paths) {
    PerceptionEditor other = new PerceptionEditor(store);
    other.open(EVENT, slot, subject);
    other.add(Arrays.asList(paths));
    other.setRelevanceThreshold(threshold);
    other.save(Collections.emptyList());
  }

  private static PerceptionPath property(int criterion, String name) {
    return new PerceptionPath(criterion, name, "", steps(name));
  }

  private static java.util.List<String> steps(String last) {
    return Arrays.asList("GETVal(event_params)", "GETVal(event_causer)#IsElem(50331647)", "GETProp(" + last + ")");
  }
}
