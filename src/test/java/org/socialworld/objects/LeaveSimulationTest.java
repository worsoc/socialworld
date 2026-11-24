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
package org.socialworld.objects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.handle.Inventory;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.objects.concrete.containers.Sack;
import org.socialworld.objects.concrete.eatable.bakery.Bread;
import org.socialworld.objects.concrete.tools.Knife;

/**
 * An object that leaves the simulation lets go of everything, and everything lets go of it (O1, 2026-11-24):
 * the carrier frees the inventory place, the container forgets the content, what the object carries or holds stays
 * where it is. Only with the right to remove objects (for now a valid token of the cluster inventory).
 */
class LeaveSimulationTest {

	private static final IAccessToken INVENTORY = token(true, SimulationCluster.inventory);

	private static IAccessToken token(boolean valid, SimulationCluster cluster) {
		return new IAccessToken() {
			public boolean isValid() { return valid; }
			public SimulationCluster getSimulationCluster() { return cluster; }
		};
	}

	private static <T extends Item> T withState(T item) {
		new WriteAccessToItem(item, new StateItem());
		return item;
	}

	@Test
	void theContentLeaves_theContainerForgetsIt() {
		Sack sack = withState(new Sack());
		Bread bread = withState(new Bread());
		assertThat(sack.putInside(INVENTORY, bread)).isTrue();
		assertThat(sack.getContents(INVENTORY)).containsExactly(bread);

		assertThat(bread.leaveSimulation(INVENTORY)).isTrue();
		assertThat(sack.getContents(INVENTORY)).isEmpty();
		assertThat(bread.isInside(INVENTORY)).isFalse();
	}

	@Test
	void theContainerLeaves_theContentStaysAndIsFree() {
		Sack sack = withState(new Sack());
		Bread bread = withState(new Bread());
		sack.putInside(INVENTORY, bread);

		assertThat(sack.leaveSimulation(INVENTORY)).isTrue();
		assertThat(bread.isInside(INVENTORY)).isFalse();
		assertThat(bread.getCarrier(INVENTORY).isSimulationObject()).isFalse();
		assertThat(sack.getContents(INVENTORY)).isEmpty();
	}

	@Test
	void aCarriedItemLeaves_thePlaceOfTheCarrierIsFree() {
		Human human = new Human();
		HiddenHuman hidden = new WriteAccessToHuman(human, new StateHuman())
				.getMeHidden(new GrantedAccessToProperty[] {GrantedAccessToProperty.inventory});
		hidden.setInventory(new Inventory(SimulationObject_Type.human));
		Knife knife = withState(new Knife());
		assertThat(hidden.putToInventory(InventoryPlace.rightHand, knife)).isTrue();
		assertThat(knife.getCarrier(INVENTORY)).isSameAs(human);

		assertThat(knife.leaveSimulation(INVENTORY)).isTrue();
		assertThat(knife.isCarried(INVENTORY)).isFalse();
		assertThat(hidden.putToInventory(InventoryPlace.rightHand, withState(new Knife()))).isTrue();
	}

	@Test
	void withoutTheRightToRemove_nothingChanges() {
		Sack sack = withState(new Sack());
		Bread bread = withState(new Bread());
		sack.putInside(INVENTORY, bread);

		assertThat(bread.leaveSimulation(null)).isFalse();
		assertThat(bread.leaveSimulation(token(true, SimulationCluster.position))).isFalse();
		assertThat(bread.leaveSimulation(token(false, SimulationCluster.inventory))).isFalse();
		assertThat(sack.getContents(INVENTORY)).containsExactly(bread);
	}
}
