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
package org.socialworld.visualize;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JPanel;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.AttributeArray;

public class ObjectDetailTest {

	private final VisualModel model = new VisualModel();

	private void act(int objID, ActionType type, ActionMode mode) {
		model.startAction(objID, new Object(), type, mode, 62f, 10000, 4000, 120);
	}

	private void report(int objID, int value, long nowMillis) {
		model.setAttributes(objID, new AttributeArray(new int[] {value}), true, nowMillis);
	}

	private void press(JPanel panel, int x, int y) {
		panel.dispatchEvent(new MouseEvent(panel, MouseEvent.MOUSE_PRESSED, 0, 0, x, y, 1, false, MouseEvent.BUTTON1));
	}

	@Test
	void recentActions_startEmpty() {
		assertThat(model.snapshot().get(1).recentActions[0]).isNull();
	}

	@Test
	void recentActions_keepTheNewestFirst() {
		act(1, ActionType.move, ActionMode.walk);
		model.stopAction(1, true, 100);
		act(1, ActionType.talk, ActionMode.askScream);
		model.stopAction(1, false, 200);

		VisualModel.FinishedAction[] recent = model.snapshot().get(1).recentActions;

		assertThat(recent[0].mode).isEqualTo(ActionMode.askScream);
		assertThat(recent[0].endedEarly).isTrue();
		assertThat(recent[0].endedMillis).isEqualTo(200);
		assertThat(recent[1].mode).isEqualTo(ActionMode.walk);
		assertThat(recent[1].endedEarly).isFalse();
	}

	@Test
	void recentActions_dropTheOldestWhenFull() {
		act(1, ActionType.move, ActionMode.run);
		model.stopAction(1, true, 1);
		for (int i = 0; i < VisualModel.RECENT_ACTIONS; i++) {
			act(1, ActionType.move, ActionMode.walk);
			model.stopAction(1, true, 2 + i);
		}

		for (VisualModel.FinishedAction action : model.snapshot().get(1).recentActions) {
			assertThat(action.mode).isEqualTo(ActionMode.walk);
		}
	}

	@Test
	void recentActions_leaveOutIgnoredActions() {
		act(1, ActionType.ignore, ActionMode.ignore);
		model.stopAction(1, true, 100);

		assertThat(model.snapshot().get(1).recentActions[0]).isNull();
	}

	@Test
	void recentActions_ofASnapshotDoNotChangeLater() {
		act(1, ActionType.move, ActionMode.walk);
		model.stopAction(1, true, 100);
		VisualModel.Snapshot before = model.snapshot();

		act(1, ActionType.move, ActionMode.run);
		model.stopAction(1, true, 200);

		assertThat(before.get(1).recentActions[0].mode).isEqualTo(ActionMode.walk);
	}

	@Test
	void history_skipsUnknownObjects() {
		AttributeHistory history = new AttributeHistory();

		history.record(model.snapshot(), 0);

		assertThat(history.values(1, 0)).isEmpty();
	}

	@Test
	void history_takesOnlyOneSamplePerInterval() {
		AttributeHistory history = new AttributeHistory();
		report(1, 5000, 1);

		history.record(model.snapshot(), 0);
		history.record(model.snapshot(), AttributeHistory.SAMPLE_MILLIS - 1);
		assertThat(history.values(1, 0)).hasSize(1);

		history.record(model.snapshot(), AttributeHistory.SAMPLE_MILLIS);
		assertThat(history.values(1, 0)).hasSize(2);
	}

	@Test
	void history_isOldestFirstAndForgetsBeyondItsLength() {
		AttributeHistory history = new AttributeHistory();
		int rounds = AttributeHistory.SAMPLES + 5;
		for (int i = 0; i < rounds; i++) {
			report(1, i, 1);
			history.record(model.snapshot(), i * AttributeHistory.SAMPLE_MILLIS);
		}

		int[] values = history.values(1, 0);

		assertThat(values).hasSize(AttributeHistory.SAMPLES);
		assertThat(values[0]).isEqualTo(5);
		assertThat(values[AttributeHistory.SAMPLES - 1]).isEqualTo(rounds - 1);
	}

	@Test
	void actionLines_tellTheRunningAction() {
		act(1, ActionType.move, ActionMode.walk);

		assertThat(String.join("|", ObjectDetailPanel.actionLines(model.snapshot().get(1))))
				.contains("walk (move)", "62", "120", "Rest 4 s von 10 s");
	}

	@Test
	void actionLines_ofAnIdleObject() {
		act(1, ActionType.move, ActionMode.walk);
		model.stopAction(1, true, 100);

		assertThat(ObjectDetailPanel.actionLines(model.snapshot().get(1))[0]).isEqualTo("Unt\u00E4tig");
	}

	@Test
	void changeText_showsDirectionAndDisplayScale() {
		assertThat(ObjectDetailPanel.changeText(123)).isEqualTo("\u25B21.2");
		assertThat(ObjectDetailPanel.changeText(-30)).isEqualTo("\u25BC0.3");
	}

	@Test
	void attributeText_withoutChangeIsOnlyTheValue() {
		assertThat(ObjectDetailPanel.attributeText(7123, 0)).isEqualTo("71");
		assertThat(ObjectDetailPanel.attributeText(7123, 100)).isEqualTo("71  \u25B21.0");
	}

	@Test
	void barShare_runsFromZeroToOne() {
		assertThat(ObjectDetailPanel.barShare(0)).isZero();
		assertThat(ObjectDetailPanel.barShare(AttributeArray.ATTRIBUTE_VALUE_MAX)).isEqualTo(1f);
	}

	@Test
	void historyRange_followsTheValues() {
		assertThat(ObjectDetailPanel.historyRange(new int[] {2000, 8000, 5000})).containsExactly(2000, 8000);
	}

	@Test
	void historyRange_keepsASmallestSpan() {
		int[] range = ObjectDetailPanel.historyRange(new int[] {5000, 5000});

		assertThat(range[1] - range[0]).isEqualTo(500);
		assertThat(range[0]).isLessThan(5000);
		assertThat(range[1]).isGreaterThan(5000);
	}

	@Test
	void positionText_isInMetersWithADecimalPoint() {
		assertThat(ObjectDetailPanel.positionText(12300, -500, 0)).isEqualTo("Position x 12.3 m, y -0.5 m, z 0.0 m");
	}

	@Test
	void describe_tellsAgeAndInterruption() {
		act(1, ActionType.talk, ActionMode.askScream);
		model.stopAction(1, false, 1000);

		assertThat(ObjectDetailPanel.describe(model.snapshot().get(1).recentActions[0], 13500))
				.isEqualTo("ASK!, vor 12 s, unterbrochen");
	}

	@Test
	void gridClick_reportsTheObjectOfTheField() {
		ActionGridPanel grid = new ActionGridPanel();
		grid.setSize(1000, 1000);
		AtomicInteger selected = new AtomicInteger();
		grid.setSelectionListener(selected::set);

		press(grid, 250, 350);

		assertThat(selected.get()).isEqualTo(33);
	}

	@Test
	void matrixClick_reportsTheObjectOfTheSortedRow() {
		AttributeMatrixPanel matrix = new AttributeMatrixPanel();
		matrix.setSize(400, 1024);
		AtomicInteger selected = new AtomicInteger();
		matrix.setSelectionListener(selected::set);
		report(77, 9000, 1);
		matrix.sortBy(0);
		matrix.setState(model.snapshot(), 1);

		press(matrix, 100, 26);

		assertThat(selected.get()).isEqualTo(77);
	}

	@Test
	void matrixClick_onTheHeaderSelectsNothing() {
		AttributeMatrixPanel matrix = new AttributeMatrixPanel();
		matrix.setSize(400, 1024);
		AtomicInteger selected = new AtomicInteger();
		matrix.setSelectionListener(selected::set);
		matrix.setState(model.snapshot(), 1);

		press(matrix, 100, 5);

		assertThat(selected.get()).isZero();
	}
}
