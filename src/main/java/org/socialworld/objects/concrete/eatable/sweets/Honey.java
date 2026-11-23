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
package org.socialworld.objects.concrete.eatable.sweets;

import org.socialworld.objects.concrete.eatable.Sweets;
import org.socialworld.objects.enums.EnumFood;
import org.socialworld.objects.properties.IHealing;
import org.socialworld.objects.properties.IValuable;

/**
 * A pot of honey.
 */
public class Honey extends Sweets implements IHealing, IValuable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Honey() {
		super();
		belongsTo = EnumFood.Honey;
		setLevelObjectSearch(9 /* base 9: 0.2 meters */ , 6 /* base 25: 0.26 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// as food: temperature in degrees Celsius, consistence and firmness 0 .. 100
	public float getTemperature() {return 18; }
	public float getConsistence() {return 90; }
	public float getFirmness() {return 10; }

	public float getHealingPower() {return 30; }
	public float getValue() {return 30; }

}
