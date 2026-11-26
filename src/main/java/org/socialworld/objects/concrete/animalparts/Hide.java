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
package org.socialworld.objects.concrete.animalparts;

import org.socialworld.objects.properties.CarrySize;
import org.socialworld.objects.enums.EnumAnimalPart;
import org.socialworld.objects.properties.IBurnable;
import org.socialworld.objects.properties.IValuable;

/**
 * A hide (fur or leather).
 */
public class Hide extends AnimalPart implements IValuable, IBurnable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Hide() {
		super();
		belongsTo = EnumAnimalPart.Hide;
		setLevelObjectSearch(8 /* base 9: 0.6 meters */ , 5 /* base 25: 1.3 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// value from the material (concept value and protection from the material, step 4, 2026-11-26): a raw material
	protected float getCraftFactor() {return 1; }
	public float getValue() {return calculateValue(); }

	/** too big for the belt, too small to need the back */
	public CarrySize getCarrySize() {return CarrySize.medium; }

}
