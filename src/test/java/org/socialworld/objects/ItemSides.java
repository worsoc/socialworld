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

import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenItem;
import org.socialworld.objects.enums.EnumSide;

/**
 * For tests: gives an item a state and sets its side through the protected route (HiddenItem, WriteAccessToItem,
 * grant side), as Simulation.createCarried does it (protection concept S3, 2026-11-24).
 */
public final class ItemSides {

	private ItemSides() {}

	public static <T extends Item> T withSide(T item, EnumSide side) {
		WriteAccessToItem wa = new WriteAccessToItem(item, new StateItem());
		wa.getMeHidden(new GrantedAccessToProperty[] {GrantedAccessToProperty.side}).setSide(side);
		return item;
	}
}
