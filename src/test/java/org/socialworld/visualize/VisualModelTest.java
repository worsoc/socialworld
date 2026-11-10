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
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.AttributeArray;

public class VisualModelTest {

	private final VisualModel model = new VisualModel();

	private void startWalking(int objID, Object action) {
		model.startAction(objID, action, ActionType.move, ActionMode.walk, 62f, 10000, 4000, 120);
	}

	private static AttributeArray values(int... values) {
		return new AttributeArray(values);
	}

	@Test
	void startAction_keepsTheValuesOfTheAction() {
		startWalking(37, new Object());

		VisualModel.ObjectState state = model.snapshot().get(37);
		assertThat(state.acting).isTrue();
		assertThat(state.actionType).isEqualTo(ActionType.move);
		assertThat(state.actionMode).isEqualTo(ActionMode.walk);
		assertThat(state.intensity).isEqualTo(62f);
		assertThat(state.duration).isEqualTo(10000);
		assertThat(state.remainedDuration).isEqualTo(4000);
		assertThat(state.priority).isEqualTo(120);
	}

	@Test
	void startAction_repeatedReportOfTheSameActionIsNotNew() {
		Object action = new Object();

		startWalking(1, action);
		startWalking(1, action);

		assertThat(model.snapshot().newActions).isEqualTo(1);
		assertThat(model.snapshot().get(1).actionCount).isEqualTo(1);
	}

	@Test
	void startAction_anotherActionIsNew() {
		startWalking(1, new Object());
		startWalking(1, new Object());

		assertThat(model.snapshot().newActions).isEqualTo(2);
	}

	@Test
	void startAction_sameActionAfterItsStopIsNotNew() {
		Object action = new Object();
		startWalking(1, action);
		model.stopAction(1, false, 100);

		startWalking(1, action);

		assertThat(model.snapshot().newActions).isEqualTo(1);
	}

	@Test
	void idsOutsideTheShownRange_areIgnored() {
		for (int objID : new int[] {0, -1, 101, 1001, 3001}) {
			startWalking(objID, new Object());
			model.stopAction(objID, true, 5);
			model.setAttributes(objID, values(1, 2), true, 5);
			model.setInitialAttributes(objID, values(1, 2));
		}

		VisualModel.Snapshot snapshot = model.snapshot();
		assertThat(snapshot.newActions).isZero();
		assertThat(snapshot.attributeReports).isZero();
		assertThat(snapshot.countAttributesKnown()).isZero();
	}

	@Test
	void firstAndLastId_areShown() {
		startWalking(1, new Object());
		startWalking(VisualModel.OBJECT_COUNT, new Object());

		assertThat(model.snapshot().countActing()).isEqualTo(2);
	}

	@Test
	void stopAction_ofAFinishedActionKeepsItReadable() {
		startWalking(5, new Object());

		model.stopAction(5, true, 777);

		VisualModel.ObjectState state = model.snapshot().get(5);
		assertThat(state.acting).isFalse();
		assertThat(state.actionMode).isEqualTo(ActionMode.walk);
		assertThat(state.actionEndedMillis).isEqualTo(777);
		assertThat(state.endedEarly).isFalse();
	}

	@Test
	void stopAction_ofAnActionNotDoneMeansInterrupted() {
		startWalking(5, new Object());

		model.stopAction(5, false, 777);

		assertThat(model.snapshot().get(5).endedEarly).isTrue();
	}

	@Test
	void stopAction_withoutARunningActionIsIgnored() {
		model.stopAction(5, true, 777);

		assertThat(model.snapshot().get(5).actionEndedMillis).isZero();
	}

	@Test
	void setAttributes_firstReportIsNoChange() {
		model.setAttributes(2, values(5200, 3900), true, 100);

		VisualModel.ObjectState state = model.snapshot().get(2);
		assertThat(state.attributesKnown).isTrue();
		assertThat(state.attributes[0]).isEqualTo(5200);
		assertThat(state.lastChange[0]).isZero();
		assertThat(state.lastChangeMillis[0]).isZero();
	}

	@Test
	void setAttributes_changeIsCalculatedPerAttribute() {
		model.setAttributes(2, values(5200, 3900), true, 100);

		model.setAttributes(2, values(5500, 3900), true, 200);

		VisualModel.ObjectState state = model.snapshot().get(2);
		assertThat(state.lastChange[0]).isEqualTo(300);
		assertThat(state.lastChangeMillis[0]).isEqualTo(200);
		assertThat(state.lastChange[1]).isZero();
		assertThat(state.lastChangeMillis[1]).isZero();
	}

	@Test
	void setAttributes_reportWithoutChangeKeepsTheLastChange() {
		model.setAttributes(2, values(5200), true, 100);
		model.setAttributes(2, values(5000), true, 200);

		model.setAttributes(2, values(5000), false, 300);

		VisualModel.ObjectState state = model.snapshot().get(2);
		assertThat(state.lastChange[0]).isEqualTo(-200);
		assertThat(state.lastChangeMillis[0]).isEqualTo(200);
		assertThat(state.reportCount).isEqualTo(3);
		assertThat(state.changeCount).isEqualTo(2);
	}

	@Test
	void setAttributes_copiesTheValues() {
		AttributeArray array = values(5200);
		model.setAttributes(2, array, true, 100);

		array.set(0, 1);

		assertThat(model.snapshot().get(2).attributes[0]).isEqualTo(5200);
	}

	@Test
	void setAttributes_objectNothingIsIgnored() {
		model.setAttributes(2, AttributeArray.getObjectNothing(), true, 100);

		assertThat(model.snapshot().get(2).attributesKnown).isFalse();
		assertThat(model.snapshot().attributeReports).isZero();
	}

	@Test
	void setInitialAttributes_doesNotCountAsReport() {
		model.setInitialAttributes(3, values(4000));

		VisualModel.Snapshot snapshot = model.snapshot();
		assertThat(snapshot.get(3).attributes[0]).isEqualTo(4000);
		assertThat(snapshot.countAttributesKnown()).isEqualTo(1);
		assertThat(snapshot.attributeReports).isZero();
	}

	@Test
	void setInitialAttributes_doesNotOverwriteAReportedValue() {
		model.setAttributes(3, values(7000), true, 100);

		model.setInitialAttributes(3, values(4000));

		assertThat(model.snapshot().get(3).attributes[0]).isEqualTo(7000);
	}

	@Test
	void setInitialAttributes_objectNothingStaysUnknown() {
		model.setInitialAttributes(3, AttributeArray.getObjectNothing());

		assertThat(model.snapshot().countAttributesKnown()).isZero();
	}

	@Test
	void snapshot_doesNotChangeAfterwards() {
		model.setAttributes(4, values(1000), true, 100);
		VisualModel.Snapshot before = model.snapshot();

		model.setAttributes(4, values(2000), true, 200);

		assertThat(before.get(4).attributes[0]).isEqualTo(1000);
		assertThat(before.attributeReports).isEqualTo(1);
	}

	@Test
	void reportsFromSeveralThreads_areAllCounted() throws InterruptedException {
		final int reportsPerThread = 20000;
		Thread[] writers = new Thread[4];
		for (int i = 0; i < writers.length; i++) {
			final int objID = i + 1;
			writers[i] = new Thread(() -> {
				for (int n = 0; n < reportsPerThread; n++) {
					model.setAttributes(objID, values(n % 9999), true, n);
				}
			});
			writers[i].start();
		}
		for (int i = 0; i < 200; i++) {
			model.snapshot();
		}
		for (Thread writer : writers) {
			writer.join();
		}

		assertThat(model.snapshot().attributeReports).isEqualTo(4L * reportsPerThread);
	}
}
