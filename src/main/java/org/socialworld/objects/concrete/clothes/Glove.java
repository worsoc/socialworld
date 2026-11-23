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

/**
 * A glove. One class per kind; whether it is worn on the left or on the right hand is a property of the object.
 */
public abstract class Glove extends Clothes {

	public Glove() {
		super();
		belongsTo = EnumHumanCrafted.Glove;
	}

	// the side (left or right) is a property of the object, not a class of its own (2026-11-23);
	// it is left until it is set; who sets it comes with the inventory
	private boolean leftSide = true;

	public final void setLeftSide(boolean leftSide) {
		this.leftSide = leftSide;
	}

	public final boolean isLeftSide() {
		return this.leftSide;
	}

	public boolean isLeftHandGlove() {return this.leftSide; }
	public boolean isRightHandGlove() {return !this.leftSide; }

	public boolean isLeftFootSock() {return false; }
	public boolean isRightFootSock() {return false; }

	public boolean isLeftFootShoe() {return false; }
	public boolean isRightFootShoe() {return false; }

	public boolean isShirt() {return false; }
	public boolean isTrousers() {return false; }
	public boolean isCap() {return false; }

}
