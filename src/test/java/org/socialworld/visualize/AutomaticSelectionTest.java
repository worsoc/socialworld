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

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.AttributeArray;

public class AutomaticSelectionTest {

	private static final long START = 100000;
	private static final int NOBODY = VisualModel.NO_OBJECT;

	private final VisualModel model = new VisualModel();
	private final AutomaticSelection automatic = new AutomaticSelection();

	/**
	 * Reports the first value without a change, then the change.
	 */
	private void change(int objID, int[] before, int[] after, long nowMillis) {
		model.setAttributes(objID, new AttributeArray(before), true, 1);
		model.setAttributes(objID, new AttributeArray(after), true, nowMillis);
	}

	private void changeFirst(int objID, int size, long nowMillis) {
		change(objID, new int[] {5000}, new int[] {5000 + size}, nowMillis);
	}

	private int update(long nowMillis) {
		return automatic.update(model.snapshot(), nowMillis, 7);
	}

	@Test
	void off_keepsTheCurrentObject() {
		changeFirst(1, 500, START);

		assertThat(update(START + 1)).isEqualTo(7);
		assertThat(automatic.describe(START + 1)).isEmpty();
	}

	@Test
	void start_takesAChangeOfTheLastMomentsAtOnce() {
		changeFirst(1, 500, START - 1000);
		automatic.start(START);

		assertThat(update(START)).isEqualTo(1);
	}

	@Test
	void start_ignoresOlderChanges() {
		changeFirst(1, 500, START - AutomaticSelection.HOLD_MILLIS - 1);
		automatic.start(START);

		assertThat(update(START)).isEqualTo(7);
	}

	@Test
	void takesTheLargestChange() {
		changeFirst(1, 200, START - 100);
		changeFirst(2, 900, START - 100);
		changeFirst(3, 400, START - 100);
		automatic.start(START);

		assertThat(update(START)).isEqualTo(2);
	}

	@Test
	void aFallCountsLikeARise() {
		changeFirst(1, 200, START - 100);
		changeFirst(2, -900, START - 100);
		automatic.start(START);

		assertThat(update(START)).isEqualTo(2);
	}

	@Test
	void aSingleStrongChangeBeatsManySmallOnes() {
		change(1, new int[] {5000, 5000, 5000}, new int[] {5300, 5300, 5300}, START - 100);
		changeFirst(2, 400, START - 100);
		automatic.start(START);

		assertThat(update(START)).isEqualTo(2);
	}

	@Test
	void holds_theObjectDuringTheHoldingTime() {
		changeFirst(1, 500, START - 100);
		automatic.start(START);
		update(START);

		changeFirst(2, 900, START + 1000);

		assertThat(automatic.update(model.snapshot(), START + AutomaticSelection.HOLD_MILLIS - 1, 1)).isEqualTo(1);
	}

	@Test
	void switches_afterTheHoldingTime() {
		changeFirst(1, 500, START - 100);
		automatic.start(START);
		update(START);

		changeFirst(2, 100, START + 1000);

		assertThat(automatic.update(model.snapshot(), START + AutomaticSelection.HOLD_MILLIS, 1)).isEqualTo(2);
	}

	@Test
	void staysWithoutANewChange() {
		changeFirst(1, 500, START - 100);
		automatic.start(START);
		update(START);

		assertThat(automatic.update(model.snapshot(), START + 60000, 1)).isEqualTo(1);
	}

	@Test
	void aChangeBeforeTheLastSwitchDoesNotCountAgain() {
		changeFirst(1, 900, START - 100);
		changeFirst(2, 100, START - 100);
		automatic.start(START);
		update(START);

		assertThat(automatic.update(model.snapshot(), START + AutomaticSelection.HOLD_MILLIS, 1)).isEqualTo(1);
	}

	@Test
	void stop_keepsTheCurrentObjectAndEndsTheReason() {
		changeFirst(1, 500, START - 100);
		automatic.start(START);
		update(START);

		automatic.stop();

		assertThat(automatic.isActive()).isFalse();
		assertThat(automatic.update(model.snapshot(), START + 60000, 1)).isEqualTo(1);
		assertThat(automatic.describe(START)).isEmpty();
	}

	@Test
	void describe_waitsForTheFirstChange() {
		automatic.start(START);

		assertThat(automatic.update(model.snapshot(), START, NOBODY)).isEqualTo(NOBODY);
		assertThat(automatic.describe(START)).contains("warte");
	}

	@Test
	void describe_tellsAttributeChangeAndAge() {
		changeFirst(1, 320, START - 2000);
		automatic.start(START);
		update(START);

		assertThat(automatic.describe(START))
				.isEqualTo("Automatik: mood " + ObjectDetailPanel.changeText(320) + " vor 2 s");
	}
}
