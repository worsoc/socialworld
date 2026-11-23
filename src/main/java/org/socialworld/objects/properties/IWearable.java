/*
 * Social World
 * Copyright (C) 2019  Mathias Sikos
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

public interface IWearable {

	public static final String NAME = "IWearable";

	
	public abstract boolean isLeftHandGlove();
	public abstract boolean isRightHandGlove();

	public abstract boolean isLeftFootSock();
	public abstract boolean isRightFootSock();

	public abstract boolean isLeftFootShoe();
	public abstract boolean isRightFootShoe();

	public abstract boolean isShirt();
	public abstract boolean isTrousers();
	public abstract boolean isCap();

	// places added with the item hierarchy (2026-11-23); false unless a class says otherwise
	public default boolean isDress() {return false; }
	public default boolean isOvergarment() {return false; }
	public default boolean isJewellery() {return false; }
	public default boolean isBag() {return false; }

}
