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
package org.socialworld.objects.concrete.clothes.gloves;

import org.socialworld.attributes.properties.BlowKind;
import org.socialworld.objects.concrete.clothes.Glove;
import org.socialworld.objects.enums.EnumHumanCrafted;

/**
 * A gauntlet of iron and leather: the glove of a knight, more protection than the glove.
 * New with the item class or composition overview, step 4 (2026-11-25).
 */
public class Gauntlet extends Glove {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Gauntlet() {
		super();
		belongsTo = EnumHumanCrafted.Gauntlet;
		setLevelObjectSearch(9 /* base 9: 0.2 meters */ , 6 /* base 25: 0.26 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// protection from the material (concept value and protection from the material, step 5, 2026-11-26): plates with joints between them
	protected float getFormFactor() {return 0.7f; }

}
