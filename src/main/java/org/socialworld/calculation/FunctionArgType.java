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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FunctionArgType {
	Const, Attribute, EventPropertyNumericValue, TermNr;
	
	private static final Map<String, FunctionArgType> NAME_CACHE = new HashMap<>();

	static {
		for (FunctionArgType type : values()) {
			NAME_CACHE.put(type.name().toUpperCase(), type);
		}
	}

	public static FunctionArgType fromName(String name) {
		if (name == null) return null;
		return NAME_CACHE.get(name.toUpperCase());
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
