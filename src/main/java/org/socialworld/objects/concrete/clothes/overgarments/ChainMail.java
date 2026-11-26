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
package org.socialworld.objects.concrete.clothes.overgarments;

import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.objects.concrete.clothes.Overgarment;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.properties.IProtective;
import org.socialworld.objects.properties.IValuable;

/**
 * A shirt of chain mail.
 */
public class ChainMail extends Overgarment implements IProtective, IValuable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public ChainMail() {
		super();
		belongsTo = EnumHumanCrafted.ChainMail;
		setLevelObjectSearch(8 /* base 9: 0.6 meters */ , 5 /* base 25: 1.3 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public float getProtectionAgainstStab() {return 50; }
	public float getProtectionAgainstClub() {return 30; }
	public float getProtectionAgainstStroke() {return 80; }
	public float getProtectionAgainstBackhand() {return 60; }
	// value from the material (concept value and protection from the material, step 4, 2026-11-26): thousands of rings, a lot of work
	protected float getCraftFactor() {return 30; }
	public float getValue() {return calculateValue(); }

	public InventoryPlace getOvergarmentPlace() {return InventoryPlace.overgarmentArmour; }

}
