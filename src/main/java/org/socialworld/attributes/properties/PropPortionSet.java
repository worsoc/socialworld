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

import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.SimProperty;
import org.socialworld.calculation.Type;
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
/////////////////////////////    PropPortionSet  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	protected abstract String getSetsPropertyName();
	
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
		preafixPropertyName = getSetsPropertyName() + "_";
		String propertyName;
		for (PairMemberPortion pair : pairs) {
			propertyName = preafixPropertyName + pair.getProperty().getClass().getSimpleName()+ "_portion";
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
}
