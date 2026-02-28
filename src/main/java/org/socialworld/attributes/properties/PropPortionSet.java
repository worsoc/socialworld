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
import java.util.Arrays;
import java.util.List;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.SimProperty;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.IAccessToken;

public abstract class PropPortionSet extends SimProperty {

	private List<PairMemberPortion> pairs;

	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	protected  PropPortionSet(PropPortionSet original, PropertyProtection protectionOriginal, IAccessToken token ) {
		super(protectionOriginal, token);
		this.pairs = original.getPairs();
		setPropertyName(original.getPropertyName());
	}

	public  PropPortionSet() {
		pairs = new ArrayList<PairMemberPortion>();
	}
			
	public void add(		IEnumProperty property, int portion) {
		pairs.add(new PairMemberPortion(property, portion));
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValue  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public ValueProperty getProperty(IAccessToken token, PropertyName propName, String valueName) {
		
		switch (propName) {
		case event_property_numeric_value:
			return getNumericValueProperty(valueName);
		default:
			
			
			return ValueProperty.getInvalid();
		}
	}
	
	private ValueProperty getNumericValueProperty(String valueName) {
		// from general property name event_property_numeric_value 
		// get the concrete numeric value property by taking apart the valueName
		// valueName for portions consists of prefix "portion_" ,  PropPortionSer Property name, the property member name
		//     for example: portion_Colour_silver, portion_Material_leather, portion_Nutrient_protein ...

		// take part the value name
		List<String> valueNameParts = Arrays.asList(valueName.split("_"));
		 
		// there are 3 parts --> may be a PropPoertionSet member portion value 
		if (valueNameParts.size() == 3) {
			// checking whether it is a portion
			if (valueNameParts.get(0).equals("portion")) {
				// --> it is a portion
				String propName = valueNameParts.get(1);
				String member = valueNameParts.get(2);
				IEnumProperty prop = null;
				switch (propName) {
				case "Colour":
					prop = Colour.fromName(member);
				case "Material":
					prop = Material.fromName(member);
				case "Nutrient":
					prop = Nutrient.fromName(member);
				case "Taste":
					prop = Taste.fromName(member);
				}
				if (prop != null) {
					//checking whether it is the addressed member
					for (PairMemberPortion pair : pairs) {
						if (pair.getProperty() == prop) {
							// it is --> return the according portion as integer value property
							return new ValueProperty(Type.integer, valueName, pair.getPortion());
						}
					}
				}
			}
		}
		
		return ValueProperty.getInvalid();
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PropPortionSet  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public static String getSetsPropertyName() {return "";}
	
	
	protected List<PairMemberPortion> getPairs() {
		List<PairMemberPortion> copy = new ArrayList<PairMemberPortion>();
		for (PairMemberPortion pair : this.pairs) {
			copy.add(pair);
		}
		return copy;
	}
	
	public List<String> getPortionValueNames() {
		List<String> names = new ArrayList<String>();
		String preafixPropertyName;
		preafixPropertyName = "portion_" + getSetsPropertyName() + "_";
		String propertyName;
		for (PairMemberPortion pair : pairs) {
			propertyName = preafixPropertyName + pair.getProperty().getClass().getSimpleName() ;
			names.add(propertyName);
		}
		return names;
	}

	public static List<String> getTotalPortionValueNames(String setsPropertyName) {
		List<String> names = new ArrayList<String>();
		String preafixPropertyName;
		preafixPropertyName = "portion_" + setsPropertyName + "_";
		String propertyName;
		List<String> allMembers = getMemberNames(setsPropertyName);
		for (String member : allMembers) {
			propertyName = preafixPropertyName + member ;
			names.add(propertyName);
		}
		return names;
	}

	public Object getMain() {
		int maxPortion = 0;
		IEnumProperty object = null;
		for (PairMemberPortion pair : this.pairs) {
			if (pair.getPortion() > maxPortion) {
				maxPortion = pair.getPortion();
				object = pair.getProperty();
			}
		}
		return object;
	}
	
	protected ValueArrayList getObjectsAsValueArrayList() {
		ValueArrayList list = new ValueArrayList(extractPropertyList(), Type.enumProp);
		return list;
	}
	
	private List<IEnumProperty> extractPropertyList() {
		List<IEnumProperty> properties = new ArrayList<IEnumProperty>();
		for (PairMemberPortion pair : this.pairs) {
			properties.add(pair.getProperty());
		}
		return properties;
	}
	
	private final static List<IEnumProperty> getMembers(String propName) {
		List<IEnumProperty> members = new ArrayList<IEnumProperty>();
		String enumClassName = "org.socialworld.attributes.properties." + propName;
		try {
		    // 1. finding the class by name
		    Class<?> clazz = Class.forName(enumClassName);

		    if (clazz.isEnum()) {
		        // 2. get all enum constants
		        Object[] constants = clazz.getEnumConstants();

		        // 3. transfer to list
		        if (constants != null) {
		            for (Object obj : constants) {
		            	members.add((IEnumProperty) obj);
		            }
		        }
		    }
		} catch (ClassNotFoundException e) {
		    // class not found
		    e.printStackTrace();
		}
		
		return members;
	}

	private final static List<String> getMemberNames(String propName) {
		List<String> members = new ArrayList<String>();
		String enumClassName = "org.socialworld.attributes.properties." + propName;
		String memberName;
		try {
		    // 1. finding the class by name
		    Class<?> clazz = Class.forName(enumClassName);

		    if (clazz.isEnum()) {
		        // 2. get all enum constants
		        Object[] constants = clazz.getEnumConstants();

		        // 3. transfer to list
		        if (constants != null) {
		            for (Object obj : constants) {
		            	memberName = ((Enum<?>) obj).name();
		            	if (!memberName.equals("nothing"))	members.add(memberName);
		            }
		        }
		    }
		} catch (ClassNotFoundException e) {
		    // class not found
		    e.printStackTrace();
		}
		
		return members;
	}
}
