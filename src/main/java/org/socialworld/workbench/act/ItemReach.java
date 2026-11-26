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
package org.socialworld.workbench.act;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.handle.InventoryPlace;

/**
 * Taking, dropping, switching, setting and getting in the tool (inventory concept V4 and V5, 2026-11-23): the modes, the reach of a human (as in the
 * InventoryCalculator: 1 m on the ground) and the free items within it.
 */
public final class ItemReach {

	private static final List<ActionMode> ITEM_MODES = Collections.unmodifiableList(
			Arrays.asList(ActionMode.takeItem, ActionMode.dropItem, ActionMode.switchItemToOtherHand,
					ActionMode.setItemToInventory, ActionMode.getItemFromInventory));

	/** how far a human reaches to take something, in mm, as InventoryCalculator.REACH */
	public static final float REACH_IN_MILLIMETERS = 1000f;

	/** the places the tool offers: none given, the hands first, then every other place (for set and get, V5) */
	public static final InventoryPlace[] PLACES = places();

	private static InventoryPlace[] places() {
		List<InventoryPlace> places = new ArrayList<InventoryPlace>();
		places.add(InventoryPlace.noWhere);
		places.add(InventoryPlace.rightHand);
		places.add(InventoryPlace.leftHand);
		for (InventoryPlace place : InventoryPlace.values()) {
			if (!places.contains(place)) places.add(place);
		}
		return places.toArray(new InventoryPlace[0]);
	}

	private ItemReach() {
	}

	public static List<ActionMode> getItemModes() {
		return ITEM_MODES;
	}

	public static boolean isItemMode(ActionMode mode) {
		return ITEM_MODES.contains(mode);
	}

	// the blows with a weapon (inventory concept V7); the item id of the tool names the target (0: none)
	private static final List<ActionMode> WEAPON_MODES = Collections.unmodifiableList(
			Arrays.asList(ActionMode.weaponClub, ActionMode.weaponRightStab, ActionMode.weaponRightStroke,
					ActionMode.weaponRightBackhand, ActionMode.weaponLeftStab, ActionMode.weaponLeftStroke,
					ActionMode.weaponLeftBackhand));

	public static List<ActionMode> getWeaponModes() {
		return WEAPON_MODES;
	}

	public static boolean isWeaponMode(ActionMode mode) {
		return WEAPON_MODES.contains(mode);
	}

	/**
	 * @param causer the x and y of the human in mm
	 * @param freeItems the x and y of the free (not carried) items in mm, by object id
	 * @return the ids of the free items within reach, in ascending order
	 */
	public static List<Integer> getItemsInReach(Point causer, Map<Integer, Point> freeItems) {
		List<Integer> result = new ArrayList<Integer>();
		if (causer == null) return result;
		double squaredReach = (double) REACH_IN_MILLIMETERS * REACH_IN_MILLIMETERS;
		for (Map.Entry<Integer, Point> entry : freeItems.entrySet()) {
			double dx = (double) entry.getValue().x - causer.x;
			double dy = (double) entry.getValue().y - causer.y;
			if (dx * dx + dy * dy <= squaredReach) result.add(entry.getKey());
		}
		Collections.sort(result);
		return result;
	}
}
