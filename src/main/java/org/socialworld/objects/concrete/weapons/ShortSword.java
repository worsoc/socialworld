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

import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.attributes.properties.WeaponProperty;
import org.socialworld.calculation.functions.FunctionMXplusN;
import org.socialworld.objects.concrete.weapons.Weapon;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.properties.ICutting;
import org.socialworld.objects.properties.IValuable;
import org.socialworld.objects.properties.IWeapon;

/**
 * A short sword.
 */
public class ShortSword extends Weapon implements IWeapon, ICutting, IValuable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public ShortSword() {
		super();
		belongsTo = EnumHumanCrafted.ShortSword;
		setLevelObjectSearch(8 /* base 9: 0.6 meters */ , 5 /* base 25: 1.3 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// as a weapon: mass in grams, hardness and sharpness 0 .. 100; stab uses the point, stroke the edge
	private static final FunctionMXplusN OVERCOME_PROTECTION = new FunctionMXplusN(1.0F, 0.0F);
	private static final WeaponProperty STAB = new WeaponProperty(1000F, 85F, 75F, OVERCOME_PROTECTION);
	private static final WeaponProperty CLUB = new WeaponProperty(1000F, 85F, 0F, OVERCOME_PROTECTION);
	private static final WeaponProperty STROKE = new WeaponProperty(1000F, 85F, 80F, OVERCOME_PROTECTION);
	private static final WeaponProperty BACKHAND = new WeaponProperty(1000F, 85F, 0F, OVERCOME_PROTECTION);

	public WeaponProperty getStabProperties() {return STAB; }
	public WeaponProperty getClubProperties() {return CLUB; }
	public WeaponProperty getStrokeProperties() {return STROKE; }
	public WeaponProperty getBackhandProperties() {return BACKHAND; }

	public float getSharpness() {return 75; }
	public float getBladeLength() {return 500; }
	public float getValue() {return 40; }

	/** large, but worn at the belt in its scabbard as well */
	public boolean fitsPlace(InventoryPlace place) {
		if (place.isBelt()) return true;
		return super.fitsPlace(place);
	}

}
