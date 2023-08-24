/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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

import java.util.List;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.properties.NutrientSet;
import org.socialworld.attributes.properties.TasteSet;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.objects.Item;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StateEatable;
import org.socialworld.objects.properties.IEatable;
import org.socialworld.tools.StringTupel;

public abstract class Fruit extends Item implements IEatable {

	private StateEatable stateEatable;
	
	
///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////    meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
			new StringTupel("stateEatable", PropertyName.stateEatable.name())
			} ;
	
	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = Item.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}
	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public Fruit() {
		super();
	}

	protected List<State> createAddOnStates() {
		
		List<State> result = super.createAddOnStates();
		
		this.stateEatable = (StateEatable) getInitState(StateEatable.class.getName());
		result.add(this.stateEatable);
		
		return result;
		
	}

///////////////////////////////////////////////////////////////////////////////////////////
//////////    checking whether the class belongs to a sub set of classes //////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	protected boolean isInterface(String nameInterface) {
	
		boolean result;
		
		switch (nameInterface) {
			case IEatable.NAME:
				result = (this instanceof IEatable);
				break;
			default:
				result = super.isInterface(nameInterface);

		}
		
		return  result;
	
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////      implementing IEatable     ////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public  NutrientSet getNutrientSet() {
		return (NutrientSet) 	this.stateEatable.getPropertyFromMethod(SimulationCluster.todo, StateEatable.METHODNAME_GET_NUTRIENT_SET, StateEatable.VALUENAME_NUTRIENT_SET).getValue();
	}
	
	public  TasteSet getTasteSet() {
		return (TasteSet) 	this.stateEatable.getPropertyFromMethod(SimulationCluster.todo, StateEatable.METHODNAME_GET_TASTE_SET, StateEatable.VALUENAME_TASTE_SET).getValue();
	}

	

}
