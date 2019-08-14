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


public enum SimulationObject_Type {
	object(0), animal(1),human(2), god(3), item(4), magic(5);

	private int arrayIndex;

	private SimulationObject_Type(int index) {
		this.arrayIndex = index;
	}
	
	/**
	 * The method returns the index of the simulation object type.
	 * 
	 * @return type's index
	 */
	public int getIndex() {
		return arrayIndex;
	}
	
	public int next() {
		switch (arrayIndex) {
		case 2: return 1; 
		case 1: return 3; 
		case 3: return 4; 
		case 4: return 5; 
		case 5: return 2; 
		default:return 0;
		}
	}
	/**
	 * The method returns the simulation object type which belongs to the parameter index.
	 * 
	 * @param arrayIndex
	 *            type index
	 * @return simulation object type
	 */
	public static SimulationObject_Type getSimulationObjectType(int arrayIndex) {
		for (SimulationObject_Type type : SimulationObject_Type.values())
			if (type.arrayIndex == arrayIndex)
				return type;
		return null;
	}
}
