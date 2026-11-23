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
package org.socialworld.objects.properties;

import org.socialworld.actions.handle.InventoryPlace;

/**
 * A simulation object that can be picked up and carried (inventory concept, D5, 2026-11-23). What is not
 * ICarryable cannot be taken at all, e.g. a liquid without a container.
 * Where clothes and jewellery are worn is said by IWearable, what goes into the mouth by IEatable and IDrinkable;
 * this interface says the places for carrying: hands, belt and back.
 */
public interface ICarryable {

	public static final String NAME = "ICarryable";

	/**
	 * @return small, medium or large
	 */
	public abstract CarrySize getCarrySize();

	/**
	 * Whether it may be carried on the place: in the hands always, at the belt when small, on the back when large.
	 * A class overrides it for its exceptions (a lute on the back, a sword at the belt).
	 */
	public default boolean fitsPlace(InventoryPlace place) {
		if (place.isHand()) return true;
		if (place.isBelt()) return getCarrySize() == CarrySize.small;
		if (place == InventoryPlace.back) return getCarrySize() == CarrySize.large;
		return false;
	}
}
