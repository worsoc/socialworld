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
package org.socialworld.objects.concrete.eatable.liquids;

import org.socialworld.attributes.properties.NutrientSet;
import org.socialworld.attributes.properties.TasteSet;
import org.socialworld.objects.concrete.eatable.Liquid;
import org.socialworld.objects.enums.EnumLiquid;
import org.socialworld.objects.properties.IDrinkable;
import org.socialworld.objects.properties.IValuable;

/**
 * Wine. A liquid is carried in a container (a jug, a waterskin).
 */
public class Wine extends Liquid implements IDrinkable, IValuable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Wine() {
		super();
		belongsTo = EnumLiquid.Wine;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// as drink: temperature in degrees Celsius, viscosity 0 .. 100
	public float getTemperature() {return 14; }
	public float getViscosity() {return 12; }

	// as with water: a liquid has no eatable state (yet), so no nutrient and taste set
	public NutrientSet getNutrientSet() {return null; }
	public TasteSet getTasteSet() {return null; }

	// value from the material (concept value and protection from the material, step 4, 2026-11-26): pressing and fermenting make the value, not the stuff
	protected float getCraftFactor() {return 50; }
	public float getValue() {return calculateValue(); }

}
