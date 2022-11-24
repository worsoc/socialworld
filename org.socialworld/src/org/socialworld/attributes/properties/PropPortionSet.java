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
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.collections.ValueArrayList;

public abstract class PropPortionSet extends SimProperty {

	private List<Object> objects;
	private List<Integer> portions;
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	protected  PropPortionSet(PropPortionSet original, PropertyProtection protectionOriginal, SimulationCluster cluster ) {
		super(protectionOriginal, cluster);
		this.objects = original.getObjects();
		this.portions = original.getPortions();
		setPropertyName(original.getPropertyName());
	}

	public  PropPortionSet() {
		objects = new ArrayList<Object>();
		portions = new ArrayList<Integer>();
	}
			
	public void add(		Object object, int portion) {
		objects.add(object);
		portions.add(portion);
	}


///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PropPortionSet  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	protected final List<Object> getObjects() {
		List<Object> copy = new ArrayList<Object>();
		for (Object object : this.objects) {
			copy.add(object);
		}
		return copy;
	}
	
	protected List<Integer> getPortions( ) {
		List<Integer> copy = new ArrayList<Integer>();
		for (Integer portion : this.portions) {
			copy.add(portion);
		}
		return copy;
	}
	

	public Object getMain() {
		int maxPortion = 0;
		Object object = null;
		for (int index = 0; index < portions.size() ; index++) {
			if (portions.get(index) > maxPortion) {
				maxPortion = portions.get(index);
				object = objects.get(index);
			}
		}
		return object;
	}
	
	protected ValueArrayList getObjectsAsValueArrayList() {
		ValueArrayList list = new ValueArrayList(objects, Type.object);
		return list;
	}
}
