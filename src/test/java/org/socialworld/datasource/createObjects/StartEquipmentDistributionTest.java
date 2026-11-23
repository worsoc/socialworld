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
package org.socialworld.datasource.createObjects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.handle.Inventory;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.actions.handle.StartEquipment;
import org.socialworld.datasource.createObjects.StartEquipmentCatalog.Entry;
import org.socialworld.datasource.createObjects.StartEquipmentCatalog.Piece;
import org.socialworld.datasource.createObjects.StartEquipmentCatalog.Side;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.SimulationObject_Type;
import org.socialworld.objects.concrete.clothes.Glove;
import org.socialworld.objects.concrete.clothes.Shoe;
import org.socialworld.objects.concrete.clothes.Sock;
import org.socialworld.objects.concrete.eatable.bakery.Bread;

/**
 * The whole start equipment of the 100 characters, distributed as Simulation.equip does it, but on objects without
 * state and without the database (inventory concept V3, 2026-11-23).
 */
class StartEquipmentDistributionTest {

	private static SimulationObject create(String className, Side side) throws Exception {
		SimulationObject object = (SimulationObject) Class.forName(className).getDeclaredConstructor().newInstance();
		boolean left = side != Side.right;
		if (object instanceof Shoe) ((Shoe) object).setLeftSide(left);
		if (object instanceof Sock) ((Sock) object).setLeftSide(left);
		if (object instanceof Glove) ((Glove) object).setLeftSide(left);
		return object;
	}

	@Test
	void all100_areEquipped_nothingFallsToTheFeet() throws Exception {
		StartEquipmentCatalog catalog = StartEquipmentCatalog.load();
		int placed = 0, leftOut = 0, dropped = 0, breads = 0;
		List<String> droppedNames = new ArrayList<>();
		for (int no = 1; no <= 100; no++) {
			Entry entry = catalog.get(no);
			Inventory inventory = new Inventory(SimulationObject_Type.human);
			for (Piece piece : entry.clothes) {
				SimulationObject item = create(piece.className, piece.side);
				InventoryPlace place = StartEquipment.chooseWornPlace(inventory, item);
				if (place == InventoryPlace.noWhere) { leftOut++; continue; }
				assertThat(inventory.put(place, item)).isTrue();
				placed++;
			}
			for (String className : entry.items) {
				SimulationObject item = create(className, Side.none);
				InventoryPlace place = StartEquipment.choosePlace(inventory, item, true);
				if (place == InventoryPlace.noWhere) { dropped++; droppedNames.add(no + ":" + item.getClass().getSimpleName()); continue; }
				assertThat(inventory.put(place, item)).isTrue();
				placed++;
			}
			if (inventory.isFree(InventoryPlace.mouth)) {
				assertThat(inventory.put(InventoryPlace.mouth, new Bread())).isTrue();
				breads++;
			}
			// every human has something to wear on the upper body (shirt, dress or rags)
			assertThat(inventory.isFree(InventoryPlace.upperBody) && inventory.isFree(InventoryPlace.dress)).as("character " + no).isFalse();
		}
		System.out.println("StartEquipmentDistributionTest: placed " + placed + ", left out " + leftOut + ", dropped " + dropped
				+ " " + droppedNames + ", breads " + breads);
		assertThat(droppedNames).isEmpty();
		// the more clothes that replace a kit piece: fur cloak (48), hood (26) and helmet (66)
		assertThat(leftOut).isEqualTo(3);
	}
}
