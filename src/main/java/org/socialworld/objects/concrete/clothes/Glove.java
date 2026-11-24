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
package org.socialworld.objects.concrete.clothes;

import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.enums.EnumSide;

/**
 * A glove. One class per kind; whether it is worn on the left or on the right hand is a property of the object.
 */
public abstract class Glove extends Clothes {

	public Glove() {
		super();
		belongsTo = EnumHumanCrafted.Glove;
	}

	// the side (left or right) is a property of the object, not a class of its own (2026-11-23); it lies in the
	// state of the item and is set once at the creation (protection concept S3, 2026-11-24); without a side
	// (none, or a prototype without state) the piece fits on neither side
	public boolean isLeftHandGlove() {return getSide() == EnumSide.left; }
	public boolean isRightHandGlove() {return getSide() == EnumSide.right; }

	public boolean isLeftFootSock() {return false; }
	public boolean isRightFootSock() {return false; }

	public boolean isLeftFootShoe() {return false; }
	public boolean isRightFootShoe() {return false; }

	public boolean isShirt() {return false; }
	public boolean isTrousers() {return false; }
	public boolean isCap() {return false; }

}
