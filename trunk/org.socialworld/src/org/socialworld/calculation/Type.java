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
package org.socialworld.calculation;

public enum Type {
	nothing(0), integer(1), longinteger(2), floatingpoint(3), string(4), vector(5), bool(6),  actionType(7),
	attributeArray(8), event(9), action(10), simulationObject(11), answer(12);

	private int arrayIndex;

	private Type(int index) {
		this.arrayIndex = index;
	}
	
	/**
	 * The method returns the type name which belongs to the parameter
	 * type index.
	 * 
	 * @param arrayIndex
	 *            type index
	 * @return type name
	 */
	public static Type getName(int arrayIndex) {
		for (Type type : Type.values())
			if (type.arrayIndex == arrayIndex)
				return type;
		return null;
	}	

}
