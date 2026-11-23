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
package org.socialworld.objects.concrete.devotional;

import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.properties.ISacred;
import org.socialworld.objects.properties.IWearable;

/**
 * An amulet against misfortune.
 */
public class Amulet extends Devotional implements IWearable, ISacred {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Amulet() {
		super();
		belongsTo = EnumHumanCrafted.Amulet;
		setLevelObjectSearch(9 /* base 9: 0.2 meters */ , 6 /* base 25: 0.26 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////


	// worn as jewellery, none of the clothes places
	public boolean isLeftHandGlove() {return false; }
	public boolean isRightHandGlove() {return false; }
	public boolean isLeftFootSock() {return false; }
	public boolean isRightFootSock() {return false; }
	public boolean isLeftFootShoe() {return false; }
	public boolean isRightFootShoe() {return false; }
	public boolean isShirt() {return false; }
	public boolean isTrousers() {return false; }
	public boolean isCap() {return false; }
	public boolean isJewellery() {return true; }

	public float getHoliness() {return 40; }

}
