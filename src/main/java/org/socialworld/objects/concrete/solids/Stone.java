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
package org.socialworld.objects.concrete.solids;

import org.socialworld.attributes.properties.WeaponProperty;
import org.socialworld.calculation.functions.FunctionMXplusN;
import org.socialworld.objects.enums.EnumSolid;
import org.socialworld.objects.properties.IThrowable;
import org.socialworld.objects.properties.IWeapon;

/**
 * A stone.
 */
public class Stone extends Solid implements IWeapon, IThrowable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Stone() {
		super();
		belongsTo = EnumSolid.Stone;
		setLevelObjectSearch(9 /* base 9: 0.2 meters */ , 6 /* base 25: 0.26 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// as a weapon (blunt): mass in grams, hardness 0 .. 100, no point and no edge
	private static final FunctionMXplusN OVERCOME_PROTECTION = new FunctionMXplusN(1.0F, 0.0F);
	private static final WeaponProperty BLUNT = new WeaponProperty(500F, 90F, 0F, OVERCOME_PROTECTION);

	public WeaponProperty getStabProperties() {return BLUNT; }
	public WeaponProperty getClubProperties() {return BLUNT; }
	public WeaponProperty getStrokeProperties() {return BLUNT; }
	public WeaponProperty getBackhandProperties() {return BLUNT; }

	public float getGrip() {return 60; }
	public float getMass() {return 500; }
	public float getVolume() {return 200; }
	public float getForm() {return 80; }

}
