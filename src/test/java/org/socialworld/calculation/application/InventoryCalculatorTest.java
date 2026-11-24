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

import org.junit.jupiter.api.Test;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;
import org.socialworld.objects.concrete.containers.Sack;
import org.socialworld.objects.concrete.tools.Knife;
import org.socialworld.core.EventType;

/**
 * The rules of taking and dropping in the InventoryCalculator (inventory concept V4, 2026-11-23), without the
 * running simulation: which hand, and what is in reach.
 */
class InventoryCalculatorTest {

	@Test
	void take_theRequestedHand_ifItIsFree() {
		assertThat(InventoryCalculator.handForTake(InventoryPlace.leftHand, true, true)).isEqualTo(InventoryPlace.leftHand);
		assertThat(InventoryCalculator.handForTake(InventoryPlace.leftHand, false, true)).isEqualTo(InventoryPlace.noWhere);
		assertThat(InventoryCalculator.handForTake(InventoryPlace.rightHand, true, false)).isEqualTo(InventoryPlace.noWhere);
	}

	@Test
	void take_withoutAHand_theRightOne_elseTheLeftOne() {
		assertThat(InventoryCalculator.handForTake(InventoryPlace.noWhere, true, true)).isEqualTo(InventoryPlace.rightHand);
		assertThat(InventoryCalculator.handForTake(InventoryPlace.noWhere, true, false)).isEqualTo(InventoryPlace.leftHand);
		assertThat(InventoryCalculator.handForTake(InventoryPlace.noWhere, false, false)).isEqualTo(InventoryPlace.noWhere);
		// a place that is no hand counts as no hand given
		assertThat(InventoryCalculator.handForTake(InventoryPlace.belt1, true, true)).isEqualTo(InventoryPlace.rightHand);
	}

	@Test
	void drop_theRequestedHand_ifItHoldsSomething_elseRightThenLeft() {
		assertThat(InventoryCalculator.handForDrop(InventoryPlace.leftHand, true, true)).isEqualTo(InventoryPlace.leftHand);
		assertThat(InventoryCalculator.handForDrop(InventoryPlace.leftHand, false, true)).isEqualTo(InventoryPlace.noWhere);
		assertThat(InventoryCalculator.handForDrop(InventoryPlace.noWhere, true, true)).isEqualTo(InventoryPlace.rightHand);
		assertThat(InventoryCalculator.handForDrop(InventoryPlace.noWhere, true, false)).isEqualTo(InventoryPlace.leftHand);
		assertThat(InventoryCalculator.handForDrop(InventoryPlace.noWhere, false, false)).isEqualTo(InventoryPlace.noWhere);
	}

	@Test
	void reach_isOneMetreOnTheGround() {
		assertThat(InventoryCalculator.REACH).isEqualTo(1000f);
		assertThat(InventoryCalculator.isInReach(0, 0, 600, 800)).isTrue();		// exactly 1 m
		assertThat(InventoryCalculator.isInReach(0, 0, 600, 801)).isFalse();
		assertThat(InventoryCalculator.isInReach(5000, 5000, 5000, 5000)).isTrue();
	}

	@Test
	void theInventoryEvents_areTheSelfInventoryEvents() {
		assertThat(EventType.selfInventoryTake.isSelfInventory()).isTrue();
		assertThat(EventType.selfInventoryDrop.isSelfInventory()).isTrue();
		assertThat(EventType.selfInventoryGet.isSelfInventory()).isTrue();
		assertThat(EventType.targetInventoryTake.isSelfInventory()).isFalse();
		assertThat(EventType.selfMoveWalk.isSelfInventory()).isFalse();
	}

	@Test
	void switch_goesToTheOtherHand() {
		assertThat(InventoryCalculator.otherHand(InventoryPlace.leftHand)).isEqualTo(InventoryPlace.rightHand);
		assertThat(InventoryCalculator.otherHand(InventoryPlace.rightHand)).isEqualTo(InventoryPlace.leftHand);
		assertThat(InventoryCalculator.otherHand(InventoryPlace.belt1)).isEqualTo(InventoryPlace.noWhere);
	}

	// ---- own rail (protection concept S1, 2026-11-24) ----

	@Test
	void theToken_belongsToTheClusterInventory_notToPosition() {
		AccessTokenInventoryCalculator token = AccessTokenInventoryCalculator.getValid();
		assertThat(token.isValid()).isTrue();
		assertThat(token.getSimulationCluster()).isEqualTo(SimulationCluster.inventory);
		assertThat(SimulationCluster.inventory.getIndex()).isEqualTo(81);
	}

	@Test
	void theClusterInventory_readsObjectAndPosition() {
		assertThat(SimulationCluster.inventory.getPossibleUsingAs())
				.containsExactlyInAnyOrder(PropertyUsingAs.object, PropertyUsingAs.pathToPosition, PropertyUsingAs.position);
	}

	// ---- start equipment through the rail (protection concept S2, E7, 2026-11-24) ----

	private static IAccessToken token(boolean valid, SimulationCluster cluster) {
		return new IAccessToken() {
			public boolean isValid() { return valid; }
			public SimulationCluster getSimulationCluster() { return cluster; }
		};
	}

	@Test
	void theStartEquipment_onlyWithAValidTokenOfTheClusterCore() {
		assertThat(InventoryCalculator.isCoreToken(token(true, SimulationCluster.core))).isTrue();
		assertThat(InventoryCalculator.isCoreToken(token(false, SimulationCluster.core))).isFalse();
		assertThat(InventoryCalculator.isCoreToken(token(true, SimulationCluster.inventory))).isFalse();
		assertThat(InventoryCalculator.isCoreToken(token(true, SimulationCluster.attributeArray))).isFalse();
		assertThat(InventoryCalculator.isCoreToken(null)).isFalse();
	}

	@Test
	void theStartEquipment_withAnotherToken_changesNothing() {
		InventoryCalculator rail = InventoryCalculator.getInstance();
		IAccessToken attributes = token(true, SimulationCluster.attributeArray);
		assertThat(rail.startPutToInventory(attributes, null, InventoryPlace.rightHand, new Knife())).isFalse();
		assertThat(rail.startPutIntoContainer(attributes, new Sack(), new Knife())).isFalse();
	}
}
