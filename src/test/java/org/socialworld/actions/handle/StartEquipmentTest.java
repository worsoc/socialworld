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
package org.socialworld.actions.handle;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.SimulationObject_Type;
import org.socialworld.objects.concrete.clothes.caps.Hood;
import org.socialworld.objects.concrete.clothes.caps.StrawHat;
import org.socialworld.objects.concrete.clothes.overgarments.Cloak;
import org.socialworld.objects.concrete.clothes.overgarments.FurCloak;
import org.socialworld.objects.concrete.clothes.shoes.LeatherShoe;
import org.socialworld.objects.concrete.containers.Purse;
import org.socialworld.objects.concrete.containers.Satchel;
import org.socialworld.objects.concrete.eatable.bakery.Bread;
import org.socialworld.objects.concrete.eatable.dairy.Cheese;
import org.socialworld.objects.concrete.tools.Hammer;
import org.socialworld.objects.concrete.tools.Knife;
import org.socialworld.objects.concrete.tools.Tongs;
import org.socialworld.objects.concrete.valuables.Coins;
import org.socialworld.objects.concrete.valuables.Ring;
import org.socialworld.objects.concrete.weapons.LongSword;
import org.socialworld.objects.concrete.weapons.Shield;

/**
 * Where a piece of the start equipment goes (inventory concept V3, 2026-11-23).
 */
class StartEquipmentTest {

	private static InventoryPlace placeAndPut(Inventory inventory, SimulationObject item, boolean food) {
		InventoryPlace place = StartEquipment.choosePlace(inventory, item, food);
		if (place != InventoryPlace.noWhere) assertThat(inventory.put(place, item)).isTrue();
		return place;
	}

	@Test
	void clothes_goToTheirPlace_theRightShoeToTheRightFoot() {
		Inventory inventory = new Inventory(SimulationObject_Type.human);
		assertThat(placeAndPut(inventory, new StrawHat(), false)).isEqualTo(InventoryPlace.head);
		LeatherShoe right = new LeatherShoe();
		right.setLeftSide(false);
		assertThat(placeAndPut(inventory, right, false)).isEqualTo(InventoryPlace.rightFootShoe);
		assertThat(placeAndPut(inventory, new LeatherShoe(), false)).isEqualTo(InventoryPlace.leftFootShoe);
	}

	@Test
	void moreClothesFirst_thenTheKitPieceForTheSamePlace_findsNoWornPlace() {
		Inventory merchant = new Inventory(SimulationObject_Type.human);
		assertThat(placeAndPut(merchant, new FurCloak(), false)).isEqualTo(InventoryPlace.overgarmentCloak);
		assertThat(StartEquipment.chooseWornPlace(merchant, new Cloak())).isEqualTo(InventoryPlace.noWhere);
		// with choosePlace it would go into a hand
		assertThat(StartEquipment.choosePlace(merchant, new Cloak(), false)).isEqualTo(InventoryPlace.rightHand);
		Inventory beekeeper = new Inventory(SimulationObject_Type.human);
		assertThat(placeAndPut(beekeeper, new Hood(), false)).isEqualTo(InventoryPlace.head);
		assertThat(StartEquipment.chooseWornPlace(beekeeper, new StrawHat())).isEqualTo(InventoryPlace.noWhere);
	}

	@Test
	void theFirstFood_goesIntoTheMouth_theNextToTheBelt() {
		Inventory inventory = new Inventory(SimulationObject_Type.human);
		assertThat(placeAndPut(inventory, new Bread(), true)).isEqualTo(InventoryPlace.mouth);
		assertThat(placeAndPut(inventory, new Cheese(), true)).isEqualTo(InventoryPlace.belt1);
	}

	@Test
	void smallToTheBelt_mediumToTheHands_largeToHandsAndBack() {
		Inventory smith = new Inventory(SimulationObject_Type.human);
		assertThat(placeAndPut(smith, new Hammer(), true)).isEqualTo(InventoryPlace.rightHand);
		assertThat(placeAndPut(smith, new Tongs(), true)).isEqualTo(InventoryPlace.leftHand);
		assertThat(placeAndPut(smith, new Knife(), true)).isEqualTo(InventoryPlace.belt1);
		assertThat(StartEquipment.choosePlace(smith, new Hammer(), true)).isEqualTo(InventoryPlace.noWhere);

		Inventory knight = new Inventory(SimulationObject_Type.human);
		assertThat(placeAndPut(knight, new LongSword(), true)).isEqualTo(InventoryPlace.rightHand);
		assertThat(placeAndPut(knight, new Shield(), true)).isEqualTo(InventoryPlace.leftHand);
		assertThat(placeAndPut(knight, new LongSword(), true)).isEqualTo(InventoryPlace.back);
	}

	@Test
	void jewelleryAndBag_toTheirPlace_purseAndCoinsToTheBelt() {
		Inventory merchant = new Inventory(SimulationObject_Type.human);
		assertThat(placeAndPut(merchant, new Ring(), true)).isEqualTo(InventoryPlace.finger);
		assertThat(placeAndPut(merchant, new Satchel(), true)).isEqualTo(InventoryPlace.bag);
		assertThat(placeAndPut(merchant, new Purse(), true)).isEqualTo(InventoryPlace.belt1);
		assertThat(placeAndPut(merchant, new Coins(), true)).isEqualTo(InventoryPlace.belt2);
	}
}
