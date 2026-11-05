package org.socialworld.tools.pct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.socialworld.core.EventType;

public class PerceptionCreationToolTest {

  private static final int EVENT_508 = EventType.percipientExistsDistance5000.getIndex();
  private static final PerceptionPathCatalog CATALOG = new PerceptionPathCatalog();

  // ---------------------------------------------------------------------------
  // store
  // ---------------------------------------------------------------------------

  @Test
  void exampleStore_holdsThe40Rows() {
    Map<Integer, String> rows = InMemoryPerceptionDescriptionStore.withExampleData().loadAll(EVENT_508);

    assertThat(rows).hasSize(40);
    assertThat(rows.get(7)).isEqualTo(PerceptionSqlFile.createJson(7));
  }

  @Test
  void store_keepsEventsApart_andSavesOverOldRows() {
    InMemoryPerceptionDescriptionStore store = new InMemoryPerceptionDescriptionStore();

    store.save(1, 0, "first");
    store.save(1, 0, "second");
    store.save(2, 3, "other");

    assertThat(store.loadAll(1)).containsOnlyKeys(0).containsValue("second");
    assertThat(store.loadAll(2)).containsOnlyKeys(3);
    assertThat(store.loadAll(99)).isEmpty();
  }

  @Test
  void store_doesNotHandOutItsInnerData() {
    InMemoryPerceptionDescriptionStore store = new InMemoryPerceptionDescriptionStore();
    store.save(1, 0, "first");

    store.loadAll(1).clear();

    assertThat(store.loadAll(1)).hasSize(1);
  }

  // ---------------------------------------------------------------------------
  // table
  // ---------------------------------------------------------------------------

  @Test
  void table_showsAPropertyPath() {
    PerceptionPathTableModel model = new PerceptionPathTableModel(CATALOG);
    PerceptionPath path = new PerceptionPath(0, "name", "", Arrays.asList("GETVal(a)", "GETVal(event_causer)#IsElem(50331647)"));

    model.setPaths(Arrays.asList(path));

    assertThat(model.getRowCount()).isEqualTo(1);
    assertThat(model.getValueAt(0, 0)).isEqualTo("colour");
    assertThat(model.getValueAt(0, 1)).isEqualTo("ALL_ANIMALS");
    assertThat(model.getValueAt(0, 2)).isEqualTo("name");
    assertThat(model.getValueAt(0, 3)).isEqualTo("");
    assertThat(model.getValueAt(0, 4)).isEqualTo("GETVal(a).GETVal(event_causer)#IsElem(50331647)");
  }

  @Test
  void table_showsAValuePathWithItsType() {
    PerceptionPathTableModel model = new PerceptionPathTableModel(CATALOG);

    model.setPaths(Arrays.asList(new PerceptionPath(PerceptionPath.NO_CRITERION, "name", "SWType_31", Arrays.asList("GETVal(a)"))));

    assertThat(model.getValueAt(0, 0)).isEqualTo("value");
    assertThat(model.getValueAt(0, 3)).isEqualTo("SWType_31");
  }

  // ---------------------------------------------------------------------------
  // texts
  // ---------------------------------------------------------------------------

  @Test
  void pathText_saysKindAndName_andMarksPathsOutsideOfTheMetaInformation() {
    PerceptionPath path = new PerceptionPath(0, "name", "", Arrays.asList("GETVal(a)"));

    assertThat(PerceptionSelectionText.formatPath(path, true)).isEqualTo("colour: name");
    assertThat(PerceptionSelectionText.formatPath(path, false)).isEqualTo("colour: name   [not in the meta information]");
  }

  @Test
  void pathText_ofAValue_saysItsType() {
    PerceptionPath path = new PerceptionPath(PerceptionPath.NO_CRITERION, "", "SWType_31", Arrays.asList("GETVal(a)"));

    assertThat(PerceptionSelectionText.formatPath(path, true)).isEqualTo("value: (no name) : SWType_31");
  }

  // ---------------------------------------------------------------------------
  // dialog, only where a screen exists
  // ---------------------------------------------------------------------------

  @Test
  void dialog_canBeBuilt() {
    assumeFalse(GraphicsEnvironment.isHeadless(), "no screen");

    PerceptionCreationTool tool = new PerceptionCreationTool(InMemoryPerceptionDescriptionStore.withExampleData(), CATALOG);

    assertThat(tool.getTitle()).contains("not the database");
    tool.dispose();
  }
}
