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
package org.socialworld.objects.concrete.pastime;

import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.properties.IPlayable;
import org.socialworld.objects.properties.IValuable;

/**
 * A lute.
 */
public class Lute extends Pastime implements IValuable, IPlayable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Lute() {
		super();
		belongsTo = EnumHumanCrafted.Lute;
		setLevelObjectSearch(8 /* base 9: 0.6 meters */ , 5 /* base 25: 1.3 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// value from the material (concept value and protection from the material, step 4, 2026-11-26): the instrument maker's work; the wood is worth almost nothing
	protected float getCraftFactor() {return 50; }
	public float getValue() {return calculateValue(); }
	public float getLoudness() {return 40; }
	public float getPitch() {return 60; }

	/** medium, but carried on the back as well */
	public boolean fitsPlace(InventoryPlace place) {
		if (place == InventoryPlace.back) return true;
		return super.fitsPlace(place);
	}

}
