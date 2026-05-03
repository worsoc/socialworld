/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
package org.socialworld.objects;

import java.util.HashMap;
import java.util.Map;

public enum SimulationObject_Type {
	noObject(0), animal(1),human(2), god(3), item(4), magic(5);

	private static final Map<Integer, SimulationObject_Type> INDEX_CACHE = new HashMap<>();
	private static final Map<String, SimulationObject_Type> NAME_CACHE = new HashMap<>();

	static {
		for (SimulationObject_Type type : values()) {
			INDEX_CACHE.put(type.index, type);
			NAME_CACHE.put(type.name(), type);
		}
	}

	private final int index;

	private SimulationObject_Type(int index) {
		this.index = index;
	}
	
	/**
	 * The method returns the index of the simulation object type.
	 * 
	 * @return type's index
	 */
	public int getIndex() {
		return index;
	}
	
	public int next() {
		switch (this) {
			case human:  return 1; // to animal
			case animal: return 3; // to god
			case god:    return 4; // to item
			case item:   return 5; // to magic
			case magic:  return 2; // to human
			default:     return 0;
		}
	}


	/**
	 * The method returns the simulation object type which belongs to the parameter index.
	 * 
	 * @param index
	 *            type index
	 * @return simulation object type
	 */
	public static SimulationObject_Type getSimulationObjectType(int index) {
		SimulationObject_Type type = INDEX_CACHE.get(index);
		return (type != null) ? type : noObject;
	}

	public static SimulationObject_Type fromName(String name) {
		SimulationObject_Type type = NAME_CACHE.get(name);
		return (type != null) ? type : noObject;
	}

	public int getPercipiencePriority() {
		
		switch(this){
		case god: return 40;
		case magic: return 35;
		case human: return 30;
		case animal: return 25;
		case item: return 20;
		default: return 5;
		}
		
	}
	
	
}
