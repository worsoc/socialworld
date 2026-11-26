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
package org.socialworld.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.socialworld.objects.SimulationObject_Type;

/**
 * Every type of simulation object has its own band of 10000 IDs (1000 until 2026-11-23). An ID beyond it would
 * land in the band of the next type, e.g. the 10000th item in the band of magic. The perception jumps from band
 * to band over the unused IDs, and the highest given ID counts only types that have objects.
 */
class ObjectMasterIdRangeTest {

	@Test
	void bands_areTenThousandWide_andFollowTheTypeIndex() {
		assertThat(ObjectMaster.rangeStart(SimulationObject_Type.human)).isZero();
		assertThat(ObjectMaster.rangeEnd(SimulationObject_Type.human)).isEqualTo(9999);
		assertThat(ObjectMaster.rangeStart(SimulationObject_Type.item)).isEqualTo(30000);
		assertThat(ObjectMaster.rangeEnd(SimulationObject_Type.item)).isEqualTo(39999);
		assertThat(ObjectMaster.rangeStart(SimulationObject_Type.magic)).isEqualTo(40000);
		// the highest ID still fits the per-object fields of TickRandomCache and TickObjectCooldown
		assertThat(ObjectMaster.rangeEnd(SimulationObject_Type.magic)).isLessThan(ObjectMaster.LIVING_SIM_OBJECTS_MAX_NUMBER);
	}

	@Test
	void idsInsideTheBand_areAccepted() {
		assertThatCode(() -> ObjectMaster.checkIdInRange(SimulationObject_Type.human, 1)).doesNotThrowAnyException();
		assertThatCode(() -> ObjectMaster.checkIdInRange(SimulationObject_Type.item, 30001)).doesNotThrowAnyException();
		assertThatCode(() -> ObjectMaster.checkIdInRange(SimulationObject_Type.item, 39999)).doesNotThrowAnyException();
	}

	@Test
	void theTenThousandthItem_wouldLandInTheBandOfMagic_andStopsTheSimulation() {
		assertThatThrownBy(() -> ObjectMaster.checkIdInRange(SimulationObject_Type.item, 40000))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("40000")
				.hasMessageContaining("item")
				.hasMessageContaining("30001..39999")
				.hasMessageContaining("in the band of magic");
	}

	@Test
	void theStartOfTheBand_isNeverGivenOut() {
		// the first object of a type gets startID + 1; startID itself (e.g. 0 for humans) is outside
		assertThatThrownBy(() -> ObjectMaster.checkIdInRange(SimulationObject_Type.human, 0))
				.isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> ObjectMaster.checkIdInRange(SimulationObject_Type.magic, 50000))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageNotContaining("in the band of");
	}

	// ---- perception: band to band ----

	/** start and highest given ID per type index 0..5 (noObject, human, animal, god, item, magic) */
	private static int[] starts() {
		return new int[] {0, 0, 10000, 20000, 30000, 40000};
	}

	/** today's run: 100 humans and 776 items, no animals, gods or magic (their highest ID is the band start) */
	private static int[] maxesHumansAndItems() {
		return new int[] {0, 100, 10000, 20000, 30776, 40000};
	}

	@Test
	void perception_goesOnInsideTheBand() {
		assertThat(ObjectMaster.nextIdForPerceive(0, starts(), maxesHumansAndItems())).isEqualTo(1);
		assertThat(ObjectMaster.nextIdForPerceive(57, starts(), maxesHumansAndItems())).isEqualTo(58);
		assertThat(ObjectMaster.nextIdForPerceive(30001, starts(), maxesHumansAndItems())).isEqualTo(30002);
	}

	@Test
	void perception_jumpsOverEmptyBands_toTheNextBandWithObjects() {
		// after the last human the next is the first item: animals and gods have none
		assertThat(ObjectMaster.nextIdForPerceive(100, starts(), maxesHumansAndItems())).isEqualTo(30001);
	}

	@Test
	void perception_afterTheLastBandWithObjects_beginsAgain() {
		assertThat(ObjectMaster.nextIdForPerceive(30776, starts(), maxesHumansAndItems())).isEqualTo(1);
		// also from an ID behind all bands
		assertThat(ObjectMaster.nextIdForPerceive(60000, starts(), maxesHumansAndItems())).isEqualTo(1);
	}

	@Test
	void perception_oneRound_visitsExactlyTheGivenIds() {
		int visits = 0;
		int id = ObjectMaster.nextIdForPerceive(0, starts(), maxesHumansAndItems());
		do {
			visits++;
			id = ObjectMaster.nextIdForPerceive(id, starts(), maxesHumansAndItems());
		} while (id != 1 && visits < 100000);
		assertThat(visits).isEqualTo(100 + 776);
	}

	@Test
	void perception_withoutAnyObject_staysAtZero() {
		int[] noObjects = starts();
		assertThat(ObjectMaster.nextIdForPerceive(0, starts(), noObjects)).isZero();
	}

	@Test
	void perception_onlyMagic_wrapsInsideItsBand() {
		int[] onlyMagic = {0, 0, 10000, 20000, 30000, 40003};
		assertThat(ObjectMaster.nextIdForPerceive(0, starts(), onlyMagic)).isEqualTo(40001);
		assertThat(ObjectMaster.nextIdForPerceive(40003, starts(), onlyMagic)).isEqualTo(40001);
	}

	// ---- highest given ID ----

	@Test
	void maxGivenObjectId_countsOnlyTypesWithObjects() {
		assertThat(ObjectMaster.maxGivenObjectId(starts(), maxesHumansAndItems())).isEqualTo(30776);
		// the old code took the item value when magic was higher, and an empty band counted with its start:
		// without items it gave 3000 instead of 100 (with bands of 1000)
		assertThat(ObjectMaster.maxGivenObjectId(starts(), new int[] {0, 100, 10000, 20000, 30000, 40000})).isEqualTo(100);
		assertThat(ObjectMaster.maxGivenObjectId(starts(), new int[] {0, 100, 10000, 20000, 30776, 40005})).isEqualTo(40005);
		assertThat(ObjectMaster.maxGivenObjectId(starts(), starts())).isZero();
	}
}
