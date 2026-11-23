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
package org.socialworld.objects.concrete.weapons;

import org.socialworld.attributes.properties.WeaponProperty;
import org.socialworld.calculation.functions.FunctionMXplusN;
import org.socialworld.objects.concrete.weapons.Weapon;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.properties.ICutting;
import org.socialworld.objects.properties.IThrowable;
import org.socialworld.objects.properties.IWeapon;

/**
 * A dagger.
 */
public class Dagger extends Weapon implements IWeapon, ICutting, IThrowable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Dagger() {
		super();
		belongsTo = EnumHumanCrafted.Dagger;
		setLevelObjectSearch(9 /* base 9: 0.2 meters */ , 6 /* base 25: 0.26 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// as a weapon: mass in grams, hardness and sharpness 0 .. 100; stab uses the point, stroke the edge
	private static final FunctionMXplusN OVERCOME_PROTECTION = new FunctionMXplusN(1.0F, 0.0F);
	private static final WeaponProperty STAB = new WeaponProperty(300F, 85F, 85F, OVERCOME_PROTECTION);
	private static final WeaponProperty CLUB = new WeaponProperty(300F, 85F, 0F, OVERCOME_PROTECTION);
	private static final WeaponProperty STROKE = new WeaponProperty(300F, 85F, 70F, OVERCOME_PROTECTION);
	private static final WeaponProperty BACKHAND = new WeaponProperty(300F, 85F, 0F, OVERCOME_PROTECTION);

	public WeaponProperty getStabProperties() {return STAB; }
	public WeaponProperty getClubProperties() {return CLUB; }
	public WeaponProperty getStrokeProperties() {return STROKE; }
	public WeaponProperty getBackhandProperties() {return BACKHAND; }

	public float getSharpness() {return 75; }
	public float getBladeLength() {return 250; }
	public float getGrip() {return 60; }
	public float getMass() {return 300; }
	public float getVolume() {return 80; }
	public float getForm() {return 40; }

}
