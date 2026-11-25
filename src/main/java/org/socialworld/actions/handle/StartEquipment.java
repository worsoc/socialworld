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

import java.util.List;
import java.util.Map;

import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.concrete.clothes.Glove;
import org.socialworld.objects.concrete.clothes.Shoe;
import org.socialworld.objects.concrete.clothes.Sock;
import org.socialworld.objects.concrete.containers.Purse;
import org.socialworld.objects.concrete.valuables.Valuable;
import org.socialworld.objects.enums.EnumSide;
import org.socialworld.objects.properties.CarrySize;
import org.socialworld.objects.properties.ICarryable;
import org.socialworld.objects.properties.IContainer;
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
	// the back only for what allows it (fitsPlace: the lute, the quiver), 2026-11-25
	private static final InventoryPlace[] MEDIUM = {
		InventoryPlace.rightHand, InventoryPlace.leftHand, InventoryPlace.back
	};
	private static final InventoryPlace[] LARGE = {
		InventoryPlace.rightHand, InventoryPlace.leftHand, InventoryPlace.back
	};

	private StartEquipment() {
	}

	/** Where an item of the start equipment goes: a place, or into a container (then place is noWhere). */
	public static final class Decision {
		public final InventoryPlace place;
		public final SimulationObject container;
		Decision(InventoryPlace place, SimulationObject container) {
			this.place = place;
			this.container = container;
		}
		public boolean isInContainer() {
			return container != null;
		}
		public boolean isNowhere() {
			return container == null && place == InventoryPlace.noWhere;
		}
	}

	/**
	 * The decision for an item (inventory concept V5): the first food into the mouth, then the worn places, then a
	 * liquid into a container for liquids, a valuable into the purse, more food into a basket, bag or sack, and
	 * everything else by its size.
	 *
	 * @param containers the containers placed so far, in the order they were placed
	 * @param contents what is in each of them so far
	 */
	public static Decision decide(Inventory inventory, List<SimulationObject> containers,
			Map<SimulationObject, List<SimulationObject>> contents, SimulationObject item) {
		if (item instanceof IEatable && inventory.canPut(InventoryPlace.mouth, item)) {
			return new Decision(InventoryPlace.mouth, null);
		}
		InventoryPlace worn = chooseWornPlace(inventory, item, EnumSide.none);
		if (worn != InventoryPlace.noWhere) return new Decision(worn, null);

		SimulationObject container = chooseContainer(containers, contents, item);
		if (container != null) return new Decision(InventoryPlace.noWhere, container);

		return new Decision(choosePlace(inventory, item, false), null);
	}

	/**
	 * The container for an item, null if there is none: a liquid into the first container for liquids that holds it,
	 * a valuable into a purse, food into the first basket, bag or sack that has room (not into the purse, not into
	 * a container for liquids).
	 */
	static SimulationObject chooseContainer(List<SimulationObject> containers,
			Map<SimulationObject, List<SimulationObject>> contents, SimulationObject item) {
		boolean liquid = ContainerContent.isLiquid(item);
		boolean valuable = item instanceof Valuable;
		boolean food = item instanceof IEatable;
		if (!liquid && !valuable && !food) return null;
		for (SimulationObject container : containers) {
			if (valuable && !(container instanceof Purse)) continue;
			// food goes into a basket, bag or sack: not into the purse and not into a container for liquids
			if (food && !liquid && (container instanceof Purse || ((IContainer) container).isForLiquids())) continue;
			if (ContainerContent.canHold(container, contents.get(container), item)) return container;
		}
		return null;
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
	 * The side comes as a parameter (protection concept S3): the prototype has no state and so no side; a shoe,
	 * sock or glove with a side goes to its place on that side if it is free.
	 */
	public static InventoryPlace chooseWornPlace(Inventory inventory, SimulationObject item, EnumSide side) {
		if (side != null && side != EnumSide.none) {
			InventoryPlace place = sidedPlace(item, side);
			return (place != InventoryPlace.noWhere && inventory.isFree(place)) ? place : InventoryPlace.noWhere;
		}
		for (InventoryPlace place : WORN) {
			if (inventory.canPut(place, item)) return place;
		}
		return InventoryPlace.noWhere;
	}

	/** The place of a shoe, sock or glove on the side; noWhere for every other piece. */
	static InventoryPlace sidedPlace(SimulationObject item, EnumSide side) {
		boolean left = (side == EnumSide.left);
		if (item instanceof Shoe) return left ? InventoryPlace.leftFootShoe : InventoryPlace.rightFootShoe;
		if (item instanceof Sock) return left ? InventoryPlace.leftFootSock : InventoryPlace.rightFootSock;
		if (item instanceof Glove) return left ? InventoryPlace.leftHandGlove : InventoryPlace.rightHandGlove;
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
