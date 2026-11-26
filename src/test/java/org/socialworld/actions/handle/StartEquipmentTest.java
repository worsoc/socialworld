/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.socialworld.objects.ItemSides;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.SimulationObject_Type;
import org.socialworld.objects.concrete.clothes.caps.Hood;
import org.socialworld.objects.concrete.clothes.caps.Hat;
import org.socialworld.objects.concrete.clothes.overgarments.Cloak;
import org.socialworld.objects.concrete.clothes.Glove;
import org.socialworld.objects.concrete.clothes.Shoe;
import org.socialworld.objects.concrete.clothes.Sock;
import org.socialworld.objects.enums.EnumSide;
import org.socialworld.objects.concrete.containers.Basket;
import org.socialworld.objects.concrete.containers.Jug;
import org.socialworld.objects.concrete.containers.Purse;
import org.socialworld.objects.concrete.eatable.liquids.Beer;
import org.socialworld.objects.concrete.eatable.veggies.Turnip;
import org.socialworld.objects.concrete.eatable.bakery.Cake;
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
		assertThat(placeAndPut(inventory, new Hat(), false)).isEqualTo(InventoryPlace.head);
		Shoe right = ItemSides.withSide(new Shoe(), EnumSide.right);
		assertThat(placeAndPut(inventory, right, false)).isEqualTo(InventoryPlace.rightFootShoe);
		assertThat(placeAndPut(inventory, ItemSides.withSide(new Shoe(), EnumSide.left), false)).isEqualTo(InventoryPlace.leftFootShoe);
	}

	@Test
	void chooseWornPlace_aPrototypeWithoutState_goesByTheGivenSide() {
		Inventory inventory = new Inventory(SimulationObject_Type.human);
		assertThat(StartEquipment.chooseWornPlace(inventory, new Shoe(), EnumSide.right)).isEqualTo(InventoryPlace.rightFootShoe);
		assertThat(StartEquipment.chooseWornPlace(inventory, new Sock(), EnumSide.left)).isEqualTo(InventoryPlace.leftFootSock);
		assertThat(StartEquipment.chooseWornPlace(inventory, new Glove(), EnumSide.left)).isEqualTo(InventoryPlace.leftHandGlove);
		// without a side the prototype fits on neither foot
		assertThat(StartEquipment.chooseWornPlace(inventory, new Shoe(), EnumSide.none)).isEqualTo(InventoryPlace.noWhere);
		assertThat(inventory.put(InventoryPlace.rightFootShoe, ItemSides.withSide(new Shoe(), EnumSide.right))).isTrue();
		assertThat(StartEquipment.chooseWornPlace(inventory, new Shoe(), EnumSide.right)).isEqualTo(InventoryPlace.noWhere);
	}

	@Test
	void moreClothesFirst_thenTheKitPieceForTheSamePlace_findsNoWornPlace() {
		Inventory merchant = new Inventory(SimulationObject_Type.human);
		// the merchant's Cloak[fur] first (since 2026-11-25 a Cloak of fur, no FurCloak class any more)
		assertThat(placeAndPut(merchant, new Cloak(), false)).isEqualTo(InventoryPlace.overgarmentCloak);
		assertThat(StartEquipment.chooseWornPlace(merchant, new Cloak(), EnumSide.none)).isEqualTo(InventoryPlace.noWhere);
		// with choosePlace it would go into a hand
		assertThat(StartEquipment.choosePlace(merchant, new Cloak(), false)).isEqualTo(InventoryPlace.rightHand);
		Inventory beekeeper = new Inventory(SimulationObject_Type.human);
		assertThat(placeAndPut(beekeeper, new Hood(), false)).isEqualTo(InventoryPlace.head);
		assertThat(StartEquipment.chooseWornPlace(beekeeper, new Hat(), EnumSide.none)).isEqualTo(InventoryPlace.noWhere);
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

	@Test
	void decide_liquidIntoItsContainer_coinsIntoThePurse_foodNotIntoThePurse() {
		Inventory inventory = new Inventory(SimulationObject_Type.human);
		List<SimulationObject> containers = new ArrayList<>();
		Map<SimulationObject, List<SimulationObject>> contents = new HashMap<>();
		inventory.put(InventoryPlace.mouth, new Bread());	// the mouth is taken

		Jug jug = new Jug();
		Purse purse = new Purse();
		Basket basket = new Basket();
		for (SimulationObject c : new SimulationObject[] {jug, purse, basket}) {
			containers.add(c);
			contents.put(c, new ArrayList<>());
		}
		assertThat(StartEquipment.decide(inventory, containers, contents, new Beer()).container).isSameAs(jug);
		assertThat(StartEquipment.decide(inventory, containers, contents, new Coins()).container).isSameAs(purse);
		assertThat(StartEquipment.decide(inventory, containers, contents, new Turnip()).container).isSameAs(basket);
	}

	@Test
	void decide_withoutContainers_asBefore() {
		Inventory noble = new Inventory(SimulationObject_Type.human);
		List<SimulationObject> containers = new ArrayList<>();
		Map<SimulationObject, List<SimulationObject>> contents = new HashMap<>();
		StartEquipment.Decision cake = StartEquipment.decide(noble, containers, contents, new Cake());
		assertThat(cake.place).isEqualTo(InventoryPlace.mouth);
		assertThat(cake.isInContainer()).isFalse();
		noble.put(InventoryPlace.mouth, new Cake());
		// only a purse there: the food does not go into it but to the belt
		Purse purse = new Purse();
		containers.add(purse);
		contents.put(purse, new ArrayList<>());
		StartEquipment.Decision second = StartEquipment.decide(noble, containers, contents, new Cake());
		assertThat(second.isInContainer()).isFalse();
		assertThat(second.place).isEqualTo(InventoryPlace.belt1);
		assertThat(StartEquipment.decide(noble, containers, contents, new Beer()).isNowhere()).isTrue();
	}
}
