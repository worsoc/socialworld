/*
 * Social World
 * Copyright (C) 2022  Mathias Sikos
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
package org.socialworld.objects.concrete.eatable.fruits;

import org.socialworld.objects.concrete.eatable.Fruit;
import org.socialworld.objects.enums.EnumFood;
import org.socialworld.objects.properties.IEatable;
import org.socialworld.objects.properties.IThrowable;

public class Banana extends Fruit implements IEatable, IThrowable{

///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Banana() {
		super();
		belongsTo = EnumFood.Banana;
	}

	
	
	// the initial states (perceptible, eatable, appearance, composition) come from Food (2026-11-24); the stub here
	// gave NoState, so no banana could be created
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IEatable ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public  float getTemperature() {return 22; };
	public  float getConsistence() {return 22; };
	public  float getFirmness() {return 22; };

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IThrowable ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public float getGrip() { return 15; }
	public float getMass()  { return 15; }
	public float getVolume()  { return 15; }
	public float getForm()  { return 15; }




///////////////////////////////////////////////////////////////////////////////////////////
//////////checking whether the class belongs to a sub set of classes //////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
