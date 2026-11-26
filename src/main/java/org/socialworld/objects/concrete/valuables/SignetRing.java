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
package org.socialworld.objects.concrete.valuables;

import org.socialworld.objects.enums.EnumHumanCrafted;

/**
 * A signet ring: a ring that seals letters and contracts (merchant, mayor, steward).
 * New with the item class or composition overview, step 4 (2026-11-25).
 */
public class SignetRing extends Ring {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public SignetRing() {
		super();
		belongsTo = EnumHumanCrafted.SignetRing;
		setLevelObjectSearch(9 /* base 9: 0.2 meters */ , 6 /* base 25: 0.26 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// value from the material (concept value and protection from the material, step 4, 2026-11-26): a ring and its engraving
	protected float getCraftFactor() {return 3; }

}
