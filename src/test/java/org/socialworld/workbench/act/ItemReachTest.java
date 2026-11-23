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

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.workbench.act.ActionRequest.Problem;

/**
 * Taking and dropping in the Action Creation Tool (inventory concept V4, 2026-11-23).
 */
class ItemReachTest {

	@Test
	void offersTheFiveItemModes_andAllPlacesHandsFirst() {
		assertThat(ItemReach.getItemModes()).containsExactly(ActionMode.takeItem, ActionMode.dropItem,
				ActionMode.switchItemToOtherHand, ActionMode.setItemToInventory, ActionMode.getItemFromInventory);
		assertThat(ItemReach.isItemMode(ActionMode.sayScream)).isFalse();
		assertThat(ItemReach.PLACES).startsWith(InventoryPlace.noWhere, InventoryPlace.rightHand, InventoryPlace.leftHand);
		assertThat(ItemReach.PLACES).hasSize(InventoryPlace.values().length).doesNotHaveDuplicates();
	}

	@Test
	void itemsInReach_withinOneMetre_sorted() {
		Map<Integer, Point> items = new HashMap<Integer, Point>();
		items.put(30005, new Point(1600, 2800));	// exactly 1 m from (1000, 2000)
		items.put(30002, new Point(1000, 2000));	// on the spot
		items.put(30009, new Point(1000, 3001));	// just too far
		assertThat(ItemReach.getItemsInReach(new Point(1000, 2000), items)).containsExactly(30002, 30005);
		assertThat(ItemReach.getItemsInReach(null, items)).isEmpty();
	}

	@Test
	void takingNeedsAnItem_droppingDoesNot() {
		assertThat(new ActionRequest(ActionMode.takeItem, 1, 60f, 100, 0, 0, InventoryPlace.rightHand).check())
				.isEqualTo(Problem.INVALID_ITEM);
		assertThat(new ActionRequest(ActionMode.takeItem, 1, 60f, 100, 0, 30001, InventoryPlace.rightHand).check())
				.isEqualTo(Problem.NONE);
		assertThat(new ActionRequest(ActionMode.dropItem, 1, 60f, 100, 0, 0, InventoryPlace.noWhere).check())
				.isEqualTo(Problem.NONE);
		assertThat(new ActionRequest(ActionMode.walk, 1, 60f, 100, 0, 30001, InventoryPlace.rightHand).check())
				.isEqualTo(Problem.MODE_NOT_SUPPORTED);
	}

	@Test
	void theItemActionProperties_nameTypeEquip_theItemAndTheHand() {
		ActionRequest drop = new ActionRequest(ActionMode.dropItem, 7, 60f, 120, 0, 0, InventoryPlace.leftHand);
		org.socialworld.collections.ValueArrayList properties = ActionSender.createItemActionProperties(drop, 1000L);
		assertThat(properties.getValue(org.socialworld.calculation.Value.VALUE_BY_NAME_ACTION_TYPE).getObject(org.socialworld.calculation.Type.actionType))
				.isEqualTo(org.socialworld.actions.ActionType.equip);
		assertThat(properties.getValue(org.socialworld.calculation.Value.VALUE_BY_NAME_ACTION_EQUIP_PLACE).getObject(org.socialworld.calculation.Type.integer))
				.isEqualTo(InventoryPlace.leftHand.getIndex());
		assertThat(properties.getValue(org.socialworld.calculation.Value.VALUE_BY_NAME_ACTION_EQUIP_ITEM).isValid()).isTrue();
	}

	@Test
	void offersTheWeaponBlows_withAnOptionalTarget() {
		assertThat(ItemReach.getWeaponModes()).hasSize(7).contains(ActionMode.weaponClub, ActionMode.weaponRightStab);
		assertThat(ItemReach.isWeaponMode(ActionMode.takeItem)).isFalse();
		assertThat(new ActionRequest(ActionMode.weaponClub, 1, 60f, 100, 0, 0, InventoryPlace.noWhere).check()).isEqualTo(Problem.NONE);
		org.socialworld.collections.ValueArrayList properties = ActionSender.createWeaponActionProperties(
				new ActionRequest(ActionMode.weaponRightStab, 7, 60f, 120, 0, 0, InventoryPlace.noWhere), 1000L);
		assertThat(properties.getValue(org.socialworld.calculation.Value.VALUE_BY_NAME_ACTION_TYPE).getObject(org.socialworld.calculation.Type.actionType))
				.isEqualTo(org.socialworld.actions.ActionType.useWeapon);
		// without target id no target value: the blow reaches the candidates in front
		assertThat(properties.getValue(org.socialworld.calculation.Value.VALUE_BY_NAME_ACTION_TARGET).isValid()).isFalse();
	}
}
