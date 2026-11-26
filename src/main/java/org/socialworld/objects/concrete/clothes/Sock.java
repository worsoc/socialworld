/*
 * Social World
 * Copyright (C) 2022  Mathias Sikos
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
package org.socialworld.objects.concrete.clothes;

import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.properties.IBurnable;
import org.socialworld.objects.enums.EnumSide;
import org.socialworld.objects.properties.IWearable;

/**
 * Since 2026-11-25 concrete (item class or composition, step 1): the sock. The material comes from the
 * composition (composition.csv), no longer from the class; formerly WoolSock.
 */
public class Sock extends Clothes implements IWearable, IBurnable {


///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public Sock() {
		super();
		belongsTo = EnumHumanCrafted.Sock;
		setLevelObjectSearch(9 /* base 9: 0.2 meters */ , 6 /* base 25: 0.26 meters */);
	}



	// the side (left or right) is a property of the object, not a class of its own (2026-11-23); it lies in the
	// state of the item and is set once at the creation (protection concept S3, 2026-11-24); without a side
	// (none, or a prototype without state) the piece fits on neither side
	public boolean isLeftFootSock() {return getSide() == EnumSide.left; }
	public boolean isRightFootSock() {return getSide() == EnumSide.right; }

	public boolean isLeftHandGlove() {return false; }
	public boolean isRightHandGlove() {return false; }

	public boolean isLeftFootShoe() {return false; }
	public boolean isRightFootShoe() {return false; }

	public boolean isShirt() {return false; }
	public boolean isTrousers() {return false; }
	public boolean isCap() {return false; }

}
