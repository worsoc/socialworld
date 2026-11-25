/*
 * Social World
 * Copyright (C) 2019  Mathias Sikos
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

import java.util.HashMap;
import java.util.Map;

/**
 * The places of an inventory (2026-11-23: the one list of all places; before, only hands, mouth, gloves, socks
 * and shoes). The indices of the old places stay, because rules and actions may name a place by its index
 * (equip_inventoryPlace). Which item fits on which place is decided by Inventory.fits.
 */
public enum InventoryPlace {

	noWhere(0),

	// carrying
	leftHand(1),
	rightHand(2),
	mouth(3),

	// hands and feet (one class per kind, the side is a property of the object)
	leftHandGlove(11),
	rightHandGlove(12),
	leftFootSock(13),
	rightFootSock(14),
	leftFootShoe(15),
	rightFootShoe(16),

	// body
	head(21),
	upperBody(22),
	legs(23),
	dress(24),				// a dress covers upperBody and legs: it needs both free, and blocks both
	overgarmentFront(25),	// apron (of linen or leather)
	overgarmentArmour(26),	// chain mail
	overgarmentCloak(27),	// cloak (of wool or fur)

	// jewellery
	neck(31),				// amulet, rosary
	finger(32),				// ring

	// carried on the body
	belt1(41),
	belt2(42),
	belt3(43),
	back(44),
	bag(45);				// a satchel worn over the shoulder

	private static final Map<String, InventoryPlace> NAME_CACHE = new HashMap<>();
	private static final Map<Integer, InventoryPlace> INDEX_CACHE = new HashMap<>();

	static {
		for (InventoryPlace place : values()) {
			NAME_CACHE.put(place.name(), place);
			INDEX_CACHE.put(place.index, place);
		}
	}

	private final int index;

	private InventoryPlace(int index) {
		this.index = index;
	}

	/**
	 * @return the highest index of a place (45); the indices have gaps
	 */
	public static int getMaxIndex() {
		int max = 0;
		for (InventoryPlace place : values()) if (place.index > max) max = place.index;
		return max;
	}

	public static InventoryPlace getName(int index) {
		InventoryPlace place = INDEX_CACHE.get(index);
		return (place != null) ? place : noWhere;
	}

	public int getIndex() {
		return index;
	}

	public static InventoryPlace fromName(String name) {
		InventoryPlace place = NAME_CACHE.get(name);
		return (place != null) ? place : noWhere;
	}

	public boolean isBelt() {
		return this == belt1 || this == belt2 || this == belt3;
	}

	public boolean isHand() {
		return this == leftHand || this == rightHand;
	}
}
