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

import org.socialworld.attributes.properties.BlowKind;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.objects.concrete.clothes.Overgarment;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.properties.IBurnable;
import org.socialworld.objects.properties.IProtective;

/**
 * A cloth apron.
 */
public class Apron extends Overgarment implements IBurnable, IProtective {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Apron() {
		super();
		belongsTo = EnumHumanCrafted.Apron;
		setLevelObjectSearch(8 /* base 9: 0.6 meters */ , 5 /* base 25: 1.3 meters */);
	}

	public InventoryPlace getOvergarmentPlace() {return InventoryPlace.overgarmentFront; }

	// protection from the material (concept value and protection from the material, step 5, 2026-11-26): a working apron, covers the front
	protected float getFormFactor() {return 0.8f; }
	public float getProtectionAgainstStab() {return calculateProtection(BlowKind.stab); }
	public float getProtectionAgainstClub() {return calculateProtection(BlowKind.club); }
	public float getProtectionAgainstStroke() {return calculateProtection(BlowKind.stroke); }
	public float getProtectionAgainstBackhand() {return calculateProtection(BlowKind.backhand); }

}
