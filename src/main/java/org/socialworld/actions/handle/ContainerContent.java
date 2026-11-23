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

import java.util.List;

import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.properties.CarrySize;
import org.socialworld.objects.properties.ICarryable;
import org.socialworld.objects.properties.IContainer;
import org.socialworld.objects.properties.IDrinkable;

/**
 * What fits into a container (inventory concept V5, 2026-11-23). A liquid fills a container for liquids that is
 * empty (one liquid, nothing else). Other things need to be ICarryable; their space comes from the carry size:
 * small 300 ml, medium 2 l, large 15 l; together they must not exceed the capacity, and they do not go together
 * with a liquid. A container does not go into itself.
 */
public final class ContainerContent {

	static final float SPACE_SMALL = 300f;
	static final float SPACE_MEDIUM = 2000f;
	static final float SPACE_LARGE = 15000f;

	private ContainerContent() {
	}

	/** @return true for a liquid: drinkable and not carryable by itself */
	public static boolean isLiquid(SimulationObject object) {
		return object instanceof IDrinkable && !(object instanceof ICarryable);
	}

	/** @return the space the thing takes in a container, in ml (0 for what is not carryable) */
	public static float spaceOf(SimulationObject object) {
		if (!(object instanceof ICarryable)) return 0f;
		CarrySize size = ((ICarryable) object).getCarrySize();
		if (size == CarrySize.small) return SPACE_SMALL;
		if (size == CarrySize.medium) return SPACE_MEDIUM;
		return SPACE_LARGE;
	}

	/**
	 * @param container the container (it must be IContainer)
	 * @param contents what is in it already
	 * @param item what shall go in
	 */
	public static boolean canHold(SimulationObject container, List<SimulationObject> contents, SimulationObject item) {
		if (!(container instanceof IContainer) || item == null || !item.isSimulationObject() || item == container) return false;
		if (contents.contains(item)) return false;
		IContainer c = (IContainer) container;

		boolean holdsLiquid = false;
		float used = 0f;
		for (SimulationObject content : contents) {
			if (isLiquid(content)) holdsLiquid = true;
			used += spaceOf(content);
		}
		if (isLiquid(item)) return c.isForLiquids() && contents.isEmpty();
		if (!(item instanceof ICarryable) || holdsLiquid) return false;
		return used + spaceOf(item) <= c.getCapacity();
	}
}
