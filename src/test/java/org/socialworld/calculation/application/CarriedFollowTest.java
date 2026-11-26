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
package org.socialworld.calculation.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.handle.Inventory;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.core.EventType;

/**
 * Carried objects (connection carried, inventory concept V2, 2026-11-23): they take the position of their
 * carrier plus a fixed offset per place, and they do not move by themselves.
 */
class CarriedFollowTest {

	@Test
	void followPosition_isCarrierPlusOffset_andLeavesBothAlone() {
		Vector carrier = new Vector(1000, 2000, 0);
		Vector offset = new Vector(0, 0, 800);
		Vector position = PositionCalculator.followPosition(carrier, offset);
		assertThat(position.getX()).isEqualTo(1000f);
		assertThat(position.getY()).isEqualTo(2000f);
		assertThat(position.getZ()).isEqualTo(800f);
		assertThat(carrier.getZ()).isZero();
		assertThat(offset.getZ()).isEqualTo(800f);
	}

	@Test
	void followPosition_afterAStep_keepsThePlace_noDrift() {
		Vector offset = Inventory.offsetOf(InventoryPlace.leftFootShoe);
		Vector carrier = new Vector();	// the constructor without arguments gives a mutable vector
		for (int step = 1; step <= 1000; step++) {
			carrier.add(new Vector(1.4f, 0.7f, 0));
			Vector shoe = PositionCalculator.followPosition(carrier, offset);
			// the offset is added to the carrier anew each time, so float rounding does not add up over the steps
			assertThat(shoe.getX() - carrier.getX()).isCloseTo(150f, within(0.01f));
			assertThat(shoe.getZ() - carrier.getZ()).isCloseTo(-800f, within(0.01f));
		}
	}

	@Test
	void aCarriedObject_ignoresEverythingButFollowCarrier() {
		assertThat(PositionCalculator.isIgnoredWhileCarried(EventType.positionStep, true)).isTrue();
		assertThat(PositionCalculator.isIgnoredWhileCarried(EventType.candidatesMoveWalk, true)).isTrue();
		assertThat(PositionCalculator.isIgnoredWhileCarried(EventType.activeMoveStop, true)).isTrue();
		assertThat(PositionCalculator.isIgnoredWhileCarried(EventType.followCarrier, true)).isFalse();
		// a free object goes on as before
		assertThat(PositionCalculator.isIgnoredWhileCarried(EventType.positionStep, false)).isFalse();
		assertThat(PositionCalculator.isIgnoredWhileCarried(EventType.candidatesMoveWalk, false)).isFalse();
	}

	@Test
	void followCarrier_isATechnicalEvent() {
		assertThat(EventType.followCarrier.isTechnical()).isTrue();
		assertThat(EventType.followCarrier.getIndex()).isEqualTo(514);
	}

	@Test
	void offsetsPerPlace_asAtTheStartOfTheSimulation() {
		assertThat(Inventory.offsetOf(InventoryPlace.head).getZ()).isEqualTo(800f);
		assertThat(Inventory.offsetOf(InventoryPlace.upperBody).getZ()).isEqualTo(300f);
		assertThat(Inventory.offsetOf(InventoryPlace.legs).getZ()).isEqualTo(-400f);
		assertThat(Inventory.offsetOf(InventoryPlace.leftFootSock).getX()).isEqualTo(150f);
		assertThat(Inventory.offsetOf(InventoryPlace.rightFootShoe).getX()).isEqualTo(-150f);
		assertThat(Inventory.offsetOf(InventoryPlace.rightHand).getX()).isEqualTo(-300f);
		assertThat(Inventory.offsetOf(InventoryPlace.back).getY()).isEqualTo(-200f);
		assertThat(Inventory.offsetOf(InventoryPlace.mouth).is000()).isTrue();
		assertThat(Inventory.offsetOf(InventoryPlace.belt2).is000()).isTrue();
	}
}
