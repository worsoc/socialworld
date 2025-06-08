/*
* Social World
* Copyright (C) 2025  Mathias Sikos
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

import java.util.ArrayList;
import java.util.List;

public enum FunctionArgType {
	Const, Attribute, EventProperty, TermNr;
	
	public static FunctionArgType fromName(String name) {
		for (FunctionArgType type : FunctionArgType.values())
			if (type.toString().toUpperCase().equals(name.toUpperCase()))
				return type;
		return null;
	}
	
	public static List<String> getNameList() {
		List<String> nameList = new ArrayList<String>();
		for (FunctionArgType elem : FunctionArgType.values()) {
			nameList.add(elem.toString());
		}
		return nameList;
	}
	
	public static int count() {
		return FunctionArgType.values().length;
	}


}
