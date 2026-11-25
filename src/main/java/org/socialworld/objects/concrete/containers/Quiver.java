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
package org.socialworld.objects.concrete.containers;

import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.properties.IBurnable;
import org.socialworld.objects.properties.IContainer;

/**
 * A quiver of leather for arrows, carried on the back. It stays empty until there are arrows.
 * New with the item class or composition overview, step 4 (2026-11-25).
 */
public class Quiver extends Container implements IContainer, IBurnable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Quiver() {
		super();
		belongsTo = EnumHumanCrafted.Quiver;
		setLevelObjectSearch(8 /* base 9: 0.6 meters */ , 5 /* base 25: 1.3 meters */);
	}

	public float getCapacity() {return 1500; }
	public boolean isForLiquids() {return false; }

	/** carried on the back as well, like the lute */
	public boolean fitsPlace(InventoryPlace place) {
		if (place == InventoryPlace.back) return true;
		return super.fitsPlace(place);
	}

}
