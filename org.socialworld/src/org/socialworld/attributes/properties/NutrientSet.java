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
package org.socialworld.attributes.properties;

import java.util.List;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.SimProperty;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.tools.StringTupel;

public class NutrientSet extends PropPortionSet {


///////////////////////////////////////////////////////////////////////////////////////////
//////////////////static instance for meta information    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
		new StringTupel(new String[] {"Nutrient", PropertyName.nutrientSet_mainNutrient.name(), PropertyName.nutrientSet_mainNutrient.toString()}),
		new StringTupel(new String[] {Type.valueList.getIndexWithSWTPraefix(), PropertyName.nutrientSet_nutrients.name(), PropertyName.nutrientSet_nutrients.toString()})
	};
	
	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = SimProperty.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////
///////////// object nothing (abstract method from ISimProperty)    ///////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static NutrientSet objectNothing;
	
	public static NutrientSet getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new NutrientSet();
			objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}

	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	private NutrientSet(NutrientSet original, PropertyProtection protectionOriginal, SimulationCluster cluster ) {
		super(original, protectionOriginal, cluster);
	}

	public  NutrientSet() {
		super();
	}
			

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValues  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public SimProperty copyForProperty(SimulationCluster cluster) {
		return new NutrientSet(this, getPropertyProtection(), cluster);
	}

	@Override
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		switch (propName) {
		case nutrientSet_mainNutrient:
			return new ValueProperty(Type.object, valueName, getMain());
		case nutrientSet_nutrients:
			return new ValueProperty(Type.valueList, valueName, getObjectsAsValueArrayList());
/*		case NutrientSet_portions:
			return new ValueProperty(Type.floatingpoint, valueName, this.power);
		case NutrientSet_types:
			return new ValueProperty(Type.floatingpoint, valueName, this.power);
*/			
		default:
			return ValueProperty.getInvalid();
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    NutrientSet  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
}
