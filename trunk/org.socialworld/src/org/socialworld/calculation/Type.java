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
	nothing(0), integer(1), longinteger(2), floatingpoint(3), string(4), vector(5), bool(6),  actionType(7), actionMode(8), time(9),
	attributeArray(10), event(11), action(12), simulationObject(13), answer(14), valueList(15), knowledgeElement(16);

	private int index;

	private Type(int index) {
		this.index = index;
	}
	
	/**
	 * The method returns the index of the type.
	 * 
	 * @return type's index
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * The method returns the type name which belongs to the parameter
	 * type index.
	 * 
	 * @param index
	 *            type index
	 * @return type name
	 */
	public static Type getName(int index) {
		for (Type type : Type.values())
			if (type.index == index)
				return type;
		return null;
	}	

}
