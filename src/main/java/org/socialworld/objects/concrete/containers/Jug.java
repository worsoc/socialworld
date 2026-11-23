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
package org.socialworld.objects.concrete.containers;

import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.properties.IContainer;
import org.socialworld.objects.properties.IThrowable;

/**
 * An earthenware jug.
 */
public class Jug extends Container implements IContainer, IThrowable {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Jug() {
		super();
		belongsTo = EnumHumanCrafted.Jug;
		setLevelObjectSearch(9 /* base 9: 0.2 meters */ , 6 /* base 25: 0.26 meters */);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    abilities (constant values for now)   ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public float getCapacity() {return 1500; }
	public boolean isForLiquids() {return true; }
	public float getGrip() {return 50; }
	public float getMass() {return 1200; }
	public float getVolume() {return 1800; }
	public float getForm() {return 50; }

}
