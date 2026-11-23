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
package org.socialworld.objects.concrete.eatable.meat;

import org.socialworld.objects.concrete.eatable.Meat;
import org.socialworld.objects.enums.EnumFood;
import org.socialworld.objects.properties.IPerishable;

/**
 * A sausage.
 */
public class Sausage extends Meat implements IPerishable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Sausage() {
		super();
		belongsTo = EnumFood.Sausage;
		setLevelObjectSearch(9 /* base 9: 0.2 meters */ , 6 /* base 25: 0.26 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// as food: temperature in degrees Celsius, consistence and firmness 0 .. 100
	public float getTemperature() {return 15; }
	public float getConsistence() {return 50; }
	public float getFirmness() {return 50; }

	public float getShelfLife() {return 14; }

}
