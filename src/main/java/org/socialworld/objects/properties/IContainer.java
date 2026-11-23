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

/**
 * A simulation object that can hold other things or a liquid (a sack, a jug, a purse).
 * What it holds at the moment is not part of this interface yet; it comes with the inventory.
 */
public interface IContainer {

	public static final String NAME = "IContainer";

	/**
	 * @return the capacity in millilitres
	 */
	public abstract float getCapacity();

	/**
	 * @return true if it can hold a liquid without spilling it
	 */
	public abstract boolean isForLiquids();

}
