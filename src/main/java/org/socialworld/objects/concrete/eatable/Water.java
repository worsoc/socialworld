/*
* Social World
* Copyright (C) 2022  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.objects.concrete.eatable;

import org.socialworld.attributes.properties.NutrientSet;
import org.socialworld.attributes.properties.TasteSet;
import org.socialworld.objects.enums.EnumLiquid;
import org.socialworld.objects.properties.IDrinkable;

public class Water extends Liquid implements IDrinkable{

///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public Water() {
		super();
		belongsTo = EnumLiquid.Water;
	}
	
	
	@Override
	public float getTemperature() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getViscosity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public NutrientSet getNutrientSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TasteSet getTasteSet() {
		// TODO Auto-generated method stub
		return null;
	}

}
