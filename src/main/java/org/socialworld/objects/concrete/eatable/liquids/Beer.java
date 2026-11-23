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
package org.socialworld.objects.concrete.eatable.liquids;

import org.socialworld.attributes.properties.NutrientSet;
import org.socialworld.attributes.properties.TasteSet;
import org.socialworld.objects.concrete.eatable.Liquid;
import org.socialworld.objects.enums.EnumLiquid;
import org.socialworld.objects.properties.IDrinkable;
import org.socialworld.objects.properties.IPerishable;

/**
 * Beer. A liquid is carried in a container (a jug, a waterskin).
 */
public class Beer extends Liquid implements IDrinkable, IPerishable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Beer() {
		super();
		belongsTo = EnumLiquid.Beer;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// as drink: temperature in degrees Celsius, viscosity 0 .. 100
	public float getTemperature() {return 12; }
	public float getViscosity() {return 10; }

	// as with water: a liquid has no eatable state (yet), so no nutrient and taste set
	public NutrientSet getNutrientSet() {return null; }
	public TasteSet getTasteSet() {return null; }

	public float getShelfLife() {return 30; }

}
