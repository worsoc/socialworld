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

import org.socialworld.attributes.ISimProperty;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.SimProperty;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.tools.StringTupel;

public class ColourSet extends PropPortionSet {


///////////////////////////////////////////////////////////////////////////////////////////
//////////////////static instance for meta information    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
		new StringTupel(new String[] {"Colour", PropertyName.colourSet_mainColour.name(), PropertyName.colourSet_mainColour.toString()}),
		new StringTupel(new String[] {Type.valueList.getIndexWithSWTPraefix(), PropertyName.colourSet_colours.name(), PropertyName.colourSet_colours.toString()})
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

	private static ColourSet objectNothing;
	
	public  ISimProperty getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new ColourSet();
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
	
	private ColourSet(ColourSet original, PropertyProtection protectionOriginal, SimulationCluster cluster ) {
		super(original, protectionOriginal, cluster);
	}

	public  ColourSet() {
		super();
	}
			

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValues  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public SimProperty copyForProperty(SimulationCluster cluster) {
		return new ColourSet(this, getPropertyProtection(), cluster);
	}

	@Override
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		switch (propName) {
		case colourSet_mainColour:
			return new ValueProperty(Type.object, valueName, getMain());
		case colourSet_colours:
			return new ValueProperty(Type.valueList, valueName, getObjectsAsValueArrayList());
/*		case colourSet_portions:
			return new ValueProperty(Type.floatingpoint, valueName, this.power);
		case colourSet_types:
			return new ValueProperty(Type.floatingpoint, valueName, this.power);
*/			
		default:
			return ValueProperty.getInvalid();
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ColourSet  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
}
