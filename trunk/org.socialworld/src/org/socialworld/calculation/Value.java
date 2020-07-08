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

import org.socialworld.actions.ActionType;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.Time;

public class Value {


	public static final String PRAEFIX_ACTION = "action_";
	public static final String PRAEFIX_EVENT = "event_";

	public static final String PRAEFIX_WEAPON = "weapon_";

	public static final String VALUE_BY_NAME_ACTION_TYPE = PRAEFIX_ACTION + "type";
	public static final String VALUE_BY_NAME_ACTION_MODE = PRAEFIX_ACTION + "mode";
	public static final String VALUE_BY_NAME_ACTION_INTENSITY = PRAEFIX_ACTION + "intensity";
	public static final String VALUE_BY_NAME_ACTION_PRIORITY = PRAEFIX_ACTION + "priority";
	public static final String VALUE_BY_NAME_ACTION_MINTIME = PRAEFIX_ACTION + "mintime";
	public static final String VALUE_BY_NAME_ACTION_MAXTIME = PRAEFIX_ACTION + "maxtime";
	public static final String VALUE_BY_NAME_ACTION_DURATION = PRAEFIX_ACTION + "duration";
	
	public static final String VALUE_BY_NAME_ACTION_WEAPON = PRAEFIX_ACTION + "weapon";
	public static final String VALUE_BY_NAME_ACTION_ITEM1 = PRAEFIX_ACTION + "item1";
	public static final String VALUE_BY_NAME_ACTION_ITEM2 = PRAEFIX_ACTION + "item2";
	public static final String VALUE_BY_NAME_ACTION_DIRECTION = PRAEFIX_ACTION + "direction";
	public static final String VALUE_BY_NAME_ACTION_TARGET = PRAEFIX_ACTION + "target";
	public static final String VALUE_BY_NAME_ACTION_SENTENCE = PRAEFIX_ACTION + "sentence";
	public static final String VALUE_BY_NAME_ACTION_SENTENCETYPE = PRAEFIX_ACTION + "sentenceType";
	public static final String VALUE_BY_NAME_ACTION_ANSWERS = PRAEFIX_ACTION + "answers";
	
	public static final String VALUE_BY_NAME_ACTION_MOVE_ACCELERATION = ActionType.move.getPraefix() + "acceleration";
	public static final String VALUE_BY_NAME_ACTION_MOVE_VELOCITY = ActionType.move.getPraefix() + "velocity";
	public static final String VALUE_BY_NAME_ACTION_MOVE_ENDPOSITION = ActionType.move.getPraefix() + "endposition";
	public static final String VALUE_BY_NAME_ACTION_SAY_LOUDNESS = ActionType.say.getPraefix() + "loudness";
	public static final String VALUE_BY_NAME_ACTION_EQUIP_ITEM = ActionType.equip.getPraefix() + "item";
	public static final String VALUE_BY_NAME_ACTION_EQUIP_PLACE = ActionType.equip.getPraefix() + "inventoryPlace";
	public static final String VALUE_BY_NAME_ACTION_BF_ITEM =  ActionType.bodilyFunction.getPraefix() + "item";  
	public static final String VALUE_BY_NAME_ACTION_BF_ITEMDRINK = ActionType.bodilyFunction.getPraefix() + "itemDrink";
	public static final String VALUE_BY_NAME_ACTION_BF_ITEMISDRINKABLE = ActionType.bodilyFunction.getPraefix() + "itemIsDrinkable";
	public static final String VALUE_BY_NAME_ACTION_BF_ITEMEAT = ActionType.bodilyFunction.getPraefix() + "itemEat";
	public static final String VALUE_BY_NAME_ACTION_BF_ITEMISEATABLE =  ActionType.bodilyFunction.getPraefix() + "itemIsEatable";

	
	public static final String VALUE_BY_NAME_EVENT = "event";
	public static final String VALUE_BY_NAME_EVENT_PARAMS = PRAEFIX_EVENT + "params";
	public static final String VALUE_BY_NAME_EVENT_CAUSER = PRAEFIX_EVENT + "causer";
	public static final String VALUE_BY_NAME_EVENT_DIRECTION = PRAEFIX_EVENT + "direction";
	public static final String VALUE_BY_NAME_EVENT_INTENSITY = PRAEFIX_EVENT + "intensity";


	public static final String VALUE_BY_NAME_WEAPON_MASS = PRAEFIX_WEAPON + "mass";
	public static final String VALUE_BY_NAME_WEAPON_HARDNESS = PRAEFIX_WEAPON + "hardness";
	public static final String VALUE_BY_NAME_WEAPON_SHARPNESS = PRAEFIX_WEAPON + "sharpness";
	
	public static final String VALUE_BY_NAME_KNOWLEDGE_ELEMENT_PROPS = "knowledgeElementProperties";
	
	
	public static final String VALUE_NAME_UNUSED_BECAUSE_TEMPORARY = "temp";

	private Type type;
	private String name = "";
	private Object value;
	
	boolean valid;
	ValueTransferCode transferCode = ValueTransferCode.noFurtherInformation; 
	
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
	
	public void setTransferCode(ValueTransferCode code) {
		this.transferCode = code;
	}
	
	public ValueTransferCode getTransferCode() {
		return this.transferCode;
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
	
	void changeName(String name) {
		this.name = name;
	}
	
	public boolean hasType(Type type) {
		return this.type == type;
	}
	
	public Type getType() { return type; };

	public String getName() { return name; };

	public Object getValue() { 
		switch (type) {
		case integer:
			return (int) value;
		case longinteger:
			if (value instanceof Integer)	return ((Integer) value).longValue();
			return (long) value;
		case floatingpoint:
			if (value instanceof Double) return (double)value;
			else return (float) value;
		case time:
			if (!(value instanceof Time)) {
				return new Time();
			}
			else
				return (Time) value;
		default:
			return value;
		}
	}		
			

	public boolean equals(Value anotherValue) {
		
		if (this.type.equals(anotherValue.type) ) {
			
			// TODO call concrete equals() implementations
			switch (this.type) {
				case attributeArray:
					return ( ((AttributeArray) this.getValue()).equals((AttributeArray) anotherValue.getValue()) ) ;
				default:
					return (  this.getValue().equals(anotherValue.getValue()) ) ;
			}
		}
		
		return false;
		
	}
	
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
