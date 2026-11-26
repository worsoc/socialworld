/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
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
package org.socialworld.objects.concrete.valuables;

import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.properties.ICountable;
import org.socialworld.objects.properties.IValuable;

/**
 * A handful of coins.
 */
public class Coins extends Valuable implements IValuable, ICountable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Coins() {
		super();
		belongsTo = EnumHumanCrafted.Coins;
		setLevelObjectSearch(9 /* base 9: 0.2 meters */ , 6 /* base 25: 0.26 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// value from the material (concept value and protection from the material, step 4, 2026-11-26): money is the value of its metal
	protected float getCraftFactor() {return 1; }
	public float getValue() {return calculateValue(); }
	public int getCount() {return 12; }

}
