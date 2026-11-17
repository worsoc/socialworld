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
 * Every type of simulation object has its own band of 1000 IDs. An ID beyond it would land in the
 * band of the next type, e.g. the 1000th item in the band of magic.
 */
class ObjectMasterIdRangeTest {

	@Test
	void bands_areOneThousandWide_andFollowTheTypeIndex() {
		assertThat(ObjectMaster.rangeStart(SimulationObject_Type.human)).isZero();
		assertThat(ObjectMaster.rangeEnd(SimulationObject_Type.human)).isEqualTo(999);
		assertThat(ObjectMaster.rangeStart(SimulationObject_Type.item)).isEqualTo(3000);
		assertThat(ObjectMaster.rangeEnd(SimulationObject_Type.item)).isEqualTo(3999);
		assertThat(ObjectMaster.rangeStart(SimulationObject_Type.magic)).isEqualTo(4000);
	}

	@Test
	void idsInsideTheBand_areAccepted() {
		assertThatCode(() -> ObjectMaster.checkIdInRange(SimulationObject_Type.human, 1)).doesNotThrowAnyException();
		assertThatCode(() -> ObjectMaster.checkIdInRange(SimulationObject_Type.item, 3001)).doesNotThrowAnyException();
		assertThatCode(() -> ObjectMaster.checkIdInRange(SimulationObject_Type.item, 3999)).doesNotThrowAnyException();
	}

	@Test
	void theThousandthItem_wouldLandInTheBandOfMagic_andStopsTheSimulation() {
		assertThatThrownBy(() -> ObjectMaster.checkIdInRange(SimulationObject_Type.item, 4000))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("4000")
				.hasMessageContaining("item")
				.hasMessageContaining("3001..3999")
				.hasMessageContaining("in the band of magic");
	}

	@Test
	void theStartOfTheBand_isNeverGivenOut() {
		// the first object of a type gets startID + 1; startID itself (e.g. 0 for humans) is outside
		assertThatThrownBy(() -> ObjectMaster.checkIdInRange(SimulationObject_Type.human, 0))
				.isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> ObjectMaster.checkIdInRange(SimulationObject_Type.magic, 5000))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageNotContaining("in the band of");
	}
}
