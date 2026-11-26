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
import org.socialworld.core.ActionHandler;
import org.socialworld.core.IAccessToken;
import org.socialworld.datasource.mariaDB.DatabaseAssumptions;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.objects.concrete.eatable.bakery.Bread;

/**
 * Only the own action handler of an animal may end its meal (eatUp) or stop its active move (P2, 2026-11-24), as
 * doAction checks it since 2019. Any other caller changes nothing.
 */
class OwnActionHandlerTest {

	private static final IAccessToken ACT = new IAccessToken() {
		public boolean isValid() { return true; }
		public SimulationCluster getSimulationCluster() { return SimulationCluster.act; }
	};

	@Test
	void noHandler_orAForeignOne_isNotTheOwn() {
		Human human = new Human();
		assertThat(((SimulationObject) human).isMyActionHandler(null)).isFalse();
		assertThat(((SimulationObject) human).isMyActionHandler(new ActionHandler(new Human()))).isFalse();
	}

	@Test
	void eatUp_withoutTheOwnHandler_leavesTheBreadInTheMouth() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		Human human = new Human();
		HiddenHuman hidden = new WriteAccessToHuman(human, new StateHuman())
				.getMeHidden(new GrantedAccessToProperty[] {GrantedAccessToProperty.inventory});
		hidden.setInventory(new Inventory(SimulationObject_Type.human));
		Bread bread = new Bread();
		new WriteAccessToItem(bread, new StateItem());
		assertThat(hidden.putToInventory(InventoryPlace.mouth, bread)).isTrue();

		human.eatUp(null);
		human.eatUp(new ActionHandler(new Human()));
		assertThat(human.getInventoryItem(ACT, InventoryPlace.mouth)).isSameAs(bread);
	}
}
