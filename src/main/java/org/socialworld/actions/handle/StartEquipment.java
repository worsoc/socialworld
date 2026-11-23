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

import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.properties.CarrySize;
import org.socialworld.objects.properties.ICarryable;
import org.socialworld.objects.properties.IEatable;

/**
 * Where a piece of the start equipment goes (inventory concept V3, 2026-11-23): the first free place that fits.
 * Clothes, jewellery and the bag go to their place; the first food goes into the mouth (so that the eating rules
 * find it, as the apple did before); everything else by its size: small to the belt, then the hands, medium to the
 * hands, large to the hands and then the back.
 */
public final class StartEquipment {

	private static final InventoryPlace[] WORN = {
		InventoryPlace.head, InventoryPlace.upperBody, InventoryPlace.legs, InventoryPlace.dress,
		InventoryPlace.overgarmentFront, InventoryPlace.overgarmentArmour, InventoryPlace.overgarmentCloak,
		InventoryPlace.leftFootShoe, InventoryPlace.rightFootShoe, InventoryPlace.leftFootSock, InventoryPlace.rightFootSock,
		InventoryPlace.leftHandGlove, InventoryPlace.rightHandGlove,
		InventoryPlace.neck, InventoryPlace.finger, InventoryPlace.bag
	};
	private static final InventoryPlace[] SMALL = {
		InventoryPlace.belt1, InventoryPlace.belt2, InventoryPlace.belt3, InventoryPlace.rightHand, InventoryPlace.leftHand
	};
	private static final InventoryPlace[] MEDIUM = {
		InventoryPlace.rightHand, InventoryPlace.leftHand
	};
	private static final InventoryPlace[] LARGE = {
		InventoryPlace.rightHand, InventoryPlace.leftHand, InventoryPlace.back
	};

	private StartEquipment() {
	}

	/**
	 * @param inventory the inventory as it is so far
	 * @param item the piece to place (a prototype without state is enough, only its kind counts)
	 * @param foodToTheMouth true: a food goes into the mouth if it is free
	 * @return the place, noWhere if none is free (the piece is then put down at the feet)
	 */
	/**
	 * For the clothes of the kits: only the places where it is worn. A piece whose place is taken (by the more
	 * clothes, e.g. the fur cloak instead of the cloak) gets noWhere and is left out, not carried in the hand.
	 */
	public static InventoryPlace chooseWornPlace(Inventory inventory, SimulationObject item) {
		for (InventoryPlace place : WORN) {
			if (inventory.canPut(place, item)) return place;
		}
		return InventoryPlace.noWhere;
	}

	public static InventoryPlace choosePlace(Inventory inventory, SimulationObject item, boolean foodToTheMouth) {
		if (foodToTheMouth && item instanceof IEatable && inventory.canPut(InventoryPlace.mouth, item)) {
			return InventoryPlace.mouth;
		}
		for (InventoryPlace place : WORN) {
			if (inventory.canPut(place, item)) return place;
		}
		if (item instanceof ICarryable) {
			CarrySize size = ((ICarryable) item).getCarrySize();
			InventoryPlace[] order = (size == CarrySize.small) ? SMALL : (size == CarrySize.large) ? LARGE : MEDIUM;
			for (InventoryPlace place : order) {
				if (inventory.canPut(place, item)) return place;
			}
		}
		return InventoryPlace.noWhere;
	}
}
