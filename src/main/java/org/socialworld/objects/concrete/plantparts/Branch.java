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
package org.socialworld.objects.concrete.plantparts;

import org.socialworld.objects.properties.CarrySize;
import org.socialworld.attributes.properties.WeaponProperty;
import org.socialworld.calculation.functions.FunctionMXplusN;
import org.socialworld.objects.enums.EnumPlantPart;
import org.socialworld.objects.properties.IBurnable;
import org.socialworld.objects.properties.ISwimable;
import org.socialworld.objects.properties.IThrowable;
import org.socialworld.objects.properties.IWeapon;

/**
 * A branch, broken off a tree.
 */
public class Branch extends PlantPart implements IWeapon, IThrowable, IBurnable, ISwimable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Branch() {
		super();
		belongsTo = EnumPlantPart.Branch;
		setLevelObjectSearch(8 /* base 9: 0.6 meters */ , 5 /* base 25: 1.3 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// as a weapon (blunt): mass in grams, hardness 0 .. 100, no point and no edge
	private static final FunctionMXplusN OVERCOME_PROTECTION = new FunctionMXplusN(1.0F, 0.0F);
	private static final WeaponProperty BLUNT = new WeaponProperty(600F, 40F, 0F, OVERCOME_PROTECTION);

	public WeaponProperty getStabProperties() {return BLUNT; }
	public WeaponProperty getClubProperties() {return BLUNT; }
	public WeaponProperty getStrokeProperties() {return BLUNT; }
	public WeaponProperty getBackhandProperties() {return BLUNT; }

	public float getGrip() {return 50; }
	public float getMass() {return 600; }
	public float getVolume() {return 700; }
	public float getForm() {return 20; }

	/** too big for the belt, too small to need the back */
	public CarrySize getCarrySize() {return CarrySize.medium; }

}
