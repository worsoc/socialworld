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

import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.AttributeArray;

public class PopulationHistoryTest {

	private final VisualModel model = new VisualModel();
	private final PopulationHistory history = new PopulationHistory();

	private void act(int objID, ActionType type, ActionMode mode) {
		model.startAction(objID, new Object(), type, mode, 62f, 10000, 4000, 120);
	}

	private void report(int objID, int value) {
		model.setAttributes(objID, new AttributeArray(new int[] {value}), true, 1);
	}

	private PopulationHistory.Sample sampleNow() {
		history.record(model.snapshot(), 1000);
		List<PopulationHistory.Sample> samples = history.samples();
		return samples.get(samples.size() - 1);
	}

	@Test
	void record_takesOnlyOneSamplePerInterval() {
		history.record(model.snapshot(), 0);
		history.record(model.snapshot(), PopulationHistory.SAMPLE_MILLIS - 1);
		assertThat(history.samples()).hasSize(1);

		history.record(model.snapshot(), PopulationHistory.SAMPLE_MILLIS);
		assertThat(history.samples()).hasSize(2);
	}

	@Test
	void record_forgetsWhatIsOlderThanFiveMinutes() {
		history.record(model.snapshot(), 0);
		history.record(model.snapshot(), PopulationHistory.LENGTH_MILLIS);
		assertThat(history.samples()).hasSize(2);

		history.record(model.snapshot(), PopulationHistory.LENGTH_MILLIS + PopulationHistory.SAMPLE_MILLIS);

		assertThat(history.samples()).hasSize(2);
		assertThat(history.samples().get(0).millis).isEqualTo(PopulationHistory.LENGTH_MILLIS);
	}

	@Test
	void sample_countsTheRunningActionsPerFamily() {
		act(1, ActionType.move, ActionMode.walk);
		act(2, ActionType.move, ActionMode.run);
		act(3, ActionType.talk, ActionMode.askNormal);

		int[] counts = sampleNow().familyCounts;

		assertThat(counts[ActionFamily.MOVEMENT.ordinal()]).isEqualTo(2);
		assertThat(counts[ActionFamily.SPEECH.ordinal()]).isEqualTo(1);
		assertThat(counts[ActionFamily.FIGHT.ordinal()]).isZero();
	}

	@Test
	void sample_leavesOutEndedAndIgnoredActions() {
		act(1, ActionType.move, ActionMode.walk);
		model.stopAction(1, true, 500);
		act(2, ActionType.ignore, ActionMode.ignore);

		assertThat(PopulationHistoryPanel.stackTops(sampleNow().familyCounts)[ActionFamily.values().length - 1]).isZero();
	}

	@Test
	void sample_withoutKnownAttributesHasNoSpread() {
		assertThat(sampleNow().objectsWithAttributes).isZero();
		assertThat(PopulationHistoryPanel.samplesWithAttributes(history.samples())).isEmpty();
	}

	@Test
	void sample_takesMeanAndPercentilesOnlyFromKnownObjects() {
		for (int objID = 1; objID <= 10; objID++) {
			report(objID, objID * 100);
		}

		PopulationHistory.Sample sample = sampleNow();

		assertThat(sample.objectsWithAttributes).isEqualTo(10);
		assertThat(sample.means[0]).isEqualTo(550);
		assertThat(sample.lows[0]).isEqualTo(100);
		assertThat(sample.highs[0]).isEqualTo(900);
	}

	@Test
	void percentile_ofHundredValues() {
		int[] values = new int[100];
		for (int i = 0; i < 100; i++) {
			values[i] = i + 1;
		}

		assertThat(PopulationHistory.percentile(values, 10)).isEqualTo(10);
		assertThat(PopulationHistory.percentile(values, 90)).isEqualTo(90);
	}

	@Test
	void percentile_ofASingleValue() {
		assertThat(PopulationHistory.percentile(new int[] {42}, 10)).isEqualTo(42);
		assertThat(PopulationHistory.percentile(new int[] {42}, 90)).isEqualTo(42);
	}

	@Test
	void stackTops_addUp() {
		assertThat(PopulationHistoryPanel.stackTops(new int[] {3, 0, 2})).containsExactly(3, 3, 5);
	}

	@Test
	void xAt_placesNowRightAndFiveMinutesAgoLeft() {
		assertThat(PopulationHistoryPanel.xAt(1000000, 1000000, 50, 301)).isEqualTo(350);
		assertThat(PopulationHistoryPanel.xAt(1000000 - PopulationHistory.LENGTH_MILLIS, 1000000, 50, 301)).isEqualTo(50);
		assertThat(PopulationHistoryPanel.xAt(1000000 - PopulationHistory.LENGTH_MILLIS / 2, 1000000, 50, 301)).isEqualTo(200);
	}

	@Test
	void yAt_placesTheMaximumOnTop() {
		assertThat(PopulationHistoryPanel.yAt(100, 100, 20, 101)).isEqualTo(20);
		assertThat(PopulationHistoryPanel.yAt(0, 100, 20, 101)).isEqualTo(120);
		assertThat(PopulationHistoryPanel.yAt(50, 100, 20, 101)).isEqualTo(70);
	}
}
