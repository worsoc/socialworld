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

public enum FunctionByTable_Type {
	horizontal_min(0), identical(1), negative_raise(2), 
	v(3), v_mirrored(4), u(5), u_mirrored(6), horizontal_max(7), positive_raise(8);
	
	/**
	 * The constant holds the informations how many types  are listed in the enum FunctionByTable_Type.
	 */
	public static final int NUMBER_OF_FUNCTION_BY_TABLE_TYPES = 9;

	private int arrayIndex;

	private FunctionByTable_Type(int index) {
		this.arrayIndex = index;
	}

	/**
	 * The method returns the index of the (attribute calculator) function table.
	 * 
	 * @return type's index
	 */
	public int getIndex() {
		return arrayIndex;
	}
	
	/**
	 * The method returns the type which belongs to the parameter index.
	 * 
	 * @param arrayIndex
	 *            
	 * @return type
	 */
	public static FunctionByTable_Type getType(int arrayIndex) {
		for (FunctionByTable_Type type : FunctionByTable_Type.values())
			if (type.arrayIndex == arrayIndex)
				return type;
		return null;
	}

}
