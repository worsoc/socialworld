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

import org.socialworld.attributes.properties.NutrientSet;
import org.socialworld.attributes.properties.TasteSet;
import org.socialworld.objects.enums.EnumPlantPart;
import org.socialworld.objects.properties.IEatable;
import org.socialworld.objects.properties.IHealing;
import org.socialworld.objects.properties.IPerishable;

/**
 * A herb, for the kitchen and for healing.
 */
public class Herb extends PlantPart implements IEatable, IHealing, IPerishable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Herb() {
		super();
		belongsTo = EnumPlantPart.Herb;
		setLevelObjectSearch(9 /* base 9: 0.2 meters */ , 6 /* base 25: 0.26 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// eatable without being food: temperature in degrees Celsius, consistence and firmness 0 .. 100;
	// no eatable state, so no nutrient and taste set (as the drinks)
	public float getTemperature() {return 18; }
	public float getConsistence() {return 20; }
	public float getFirmness() {return 10; }
	public NutrientSet getNutrientSet() {return null; }
	public TasteSet getTasteSet() {return null; }

	public float getHealingPower() {return 50; }
	public float getShelfLife() {return 7; }

}
