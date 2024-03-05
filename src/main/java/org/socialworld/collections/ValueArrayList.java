/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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
package org.socialworld.collections;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public class ValueArrayList {

	private List<Value> values;
	private Value nothing;
	
	public ValueArrayList() {
		this.values = new ArrayList<Value>();
		this.nothing = Value.getValueNothing();
	}
	
	public ValueArrayList(List values, Type type) {
		this.values = new ArrayList<Value>();
		for (int i = 0; i < values.size(); i++) {
			add(new Value(type, values.get(i)));
		}
		this.nothing = Value.getValueNothing();
	}
	
	public ValueArrayList(int size) {
		
		this.values = new ArrayList<Value>();
		this.nothing = Value.getValueNothing();
		
		for (int i = 0; i < size; i++) {
			values.add(nothing);
		}
	}
	
	public int size() {
		return values.size();
	}
	
	public void add(Value value) {
		values.add(value);
	}
	
	public void set(int index, Value value) {
		values.set(index, value);
	}
	
	public Value get(int index) {
		return values.get(index);
	}

	public int findValue(String name) {
		
		int argumentsCount;
		int index;
		
		argumentsCount = size();
		
		for (index = 0; index < argumentsCount; index++) {
			if (get(index).getName().equals(name)) return index;
		}
		
		return -1;

	}

	public int findValueNameStartingWith(String nameStartingWith) {
		
		int argumentsCount;
		int index;
		
		String name;
		argumentsCount = size();
		
		for (index = 0; index < argumentsCount; index++) {
			name = get(index).getName();
			if (name.indexOf(nameStartingWith) == 0) return index;
		}
		
		return -1;

	}

	public Value getValue(String name) {
		
		int argumentsCount;
		int index;
		Value value;
		
		argumentsCount = size();
		
		for (index = 0; index < argumentsCount; index++) {
			value = get(index);
			if (value.getName().equals(name)) return value;
		}
		
		return nothing;

	}

	public Value getValue(Type type, int wantedOccurence) {
		
		int argumentsCount;
		int occurence;
		Value value;
		
		argumentsCount = size();
		occurence = 0;
		
		for (int index = 0; index < argumentsCount; index++) {
			value = get(index);
			if (value.getType() == type) {
				occurence++;
				if (occurence == wantedOccurence) {
					return value;
				}
			}
		}
		return nothing;
	}
	
	
}
