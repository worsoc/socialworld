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

import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.SimProperty;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.tools.StringTupel;

public class ColourSet extends SimProperty {

	private List<Colour> colours;
	private List<Integer> portions;
	private List<Integer> types;

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
//////////////////creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	private ColourSet(ColourSet original, PropertyProtection protectionOriginal, SimulationCluster cluster ) {
		super(protectionOriginal, cluster);
		this.colours = original.getColours();
		this.portions = original.getPortions();
		this.types = original.getTypes();
		setPropertyName(original.getPropertyName());
	}

	public  ColourSet() {
		colours = new ArrayList<Colour>();
		portions = new ArrayList<Integer>();
		types = new ArrayList<Integer>();
	}
			
	public void add(		Colour colour, int type, int portion) {
		colours.add(colour);
		portions.add(portion);
		types.add(type);
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
			return new ValueProperty(Type.object, valueName, getMainColour());
		case colourSet_colours:
			return new ValueProperty(Type.valueList, valueName, getColoursAsValueArrayList());
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
	
	private List<Colour> getColours( ) {
		List<Colour> copy = new ArrayList<Colour>();
		for (Colour colour : this.colours) {
			copy.add(colour);
		}
		return copy;
	}
	
	private List<Integer> getPortions( ) {
		List<Integer> copy = new ArrayList<Integer>();
		for (Integer portion : this.portions) {
			copy.add(portion);
		}
		return copy;
	}
	
	private List<Integer> getTypes( ) {
		List<Integer> copy = new ArrayList<Integer>();
		for (Integer type : this.types) {
			copy.add(type);
		}
		return copy;
	}

	private Colour getMainColour() {
		int maxPortion = 0;
		Colour colour = null;
		for (int index = 0; index < types.size() ; index++) {
			if (types.get(index) == 1) {
				if (portions.get(index) > maxPortion) {
					maxPortion = portions.get(index);
					colour = colours.get(index);
				}
			}
		}
		return colour;
	}
	
	private ValueArrayList getColoursAsValueArrayList() {
		ValueArrayList list = new ValueArrayList(colours, Type.object);
		return list;
	}
}
