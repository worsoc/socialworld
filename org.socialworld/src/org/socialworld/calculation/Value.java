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

import org.socialworld.attributes.Time;

public class Value {

	public static String ARGUMENT_VALUE_BY_NAME_ATTRIBUTES = "attributes";
	public static String ARGUMENT_VALUE_BY_NAME_EVENT_PARAMS = "eventparams";
	public static String ARGUMENT_VALUE_BY_NAME_EVENT = "event";

	Type type;
	String name = "";
	Object value;
	boolean valid;
	
	//Dummy-Value
	public Value() {
		this.type = Type.nothing;
		valid = false;
	}
	
	public Value(Type type, String name, Object value) {
		this.type = type;
		this.name = name;
		this.value = value;
		valid = true;
	}

	public Value(Type type, Object value) {
		this.type = type;
		this.value = value;
		valid = true;
	}
	
	public Value(String valueAsString, Type castToType) {
		this.type = castToType;

		switch (castToType) {
		case integer:
			this.value = Integer.parseInt(valueAsString);
			valid = true;
			break;
		case longinteger:
			this.value = Integer.parseInt(valueAsString);
			valid = true;
			break;
		case floatingpoint:
			this.value = Float.parseFloat(valueAsString);
			valid = true;
			break;
		default:
			
		}
		
	}
	
	public void setInvalid() {
		valid = false;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public void setName(String name) {
		if (this.name.length() == 0) this.name = name;
	}
	
	public Type getType() { return type; };

	public String getName() { return name; };

	public Object getValue() { return value; };

	public Object getValueCopy() { 
		switch (type) {
		case integer:
			return (int) value;
		case longinteger:
			if (value instanceof Integer)	return ((Integer) value).longValue();
			return (long) value;
		case floatingpoint:
			return (float) value;
		case time:
			if (!(value instanceof Time)) {
				return new Time();
			}
			else
				return (Time) value;
		default:
			return value;
		}
	};
	
	
	public boolean isTrue() {
		switch (type) {
		case bool:
			return (boolean) value;
		case integer:
			return (int) value > 0;
		case longinteger:
			return (long) value > 0;
		case floatingpoint:
			return (float) value > 0;
		default:
			return false;
		}
	}
}
