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

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.ISavedValue;
import org.socialworld.attributes.NoSavedValue;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.Time;
import org.socialworld.attributes.properties.IEnumProperty;
import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Relation;
import org.socialworld.core.AllWords;
import org.socialworld.core.IAccessToken;
import org.socialworld.core.Event;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.StateSimulationObject;
import org.socialworld.datasource.parsing.JsonValue;

public class Value {

	public static final String META_NAME_4_VALUE_NAME = "valueName";
	
	public static final String PRAEFIX_ACTION = "action_";
	public static final String PRAEFIX_EVENT = "event_";

	public static final String PRAEFIX_WEAPON = "weapon_";

	public static final String VALUE_BY_NAME_SIMOBJECT = "simObj";

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
	
	public static final String VALUE_BY_NAME_ACTION_MOVE_ACCELERATION = "move_acceleration";
	public static final String VALUE_BY_NAME_ACTION_MOVE_VELOCITY = "move_velocity";
	public static final String VALUE_BY_NAME_ACTION_MOVE_ENDPOSITION = "move_endposition";
	public static final String VALUE_BY_NAME_ACTION_EQUIP_ITEM = "equip_item";
	public static final String VALUE_BY_NAME_ACTION_EQUIP_PLACE = "equip_inventoryPlace";
	public static final String VALUE_BY_NAME_ACTION_BF_ITEM =  "bodilyFunction_item";  
	public static final String VALUE_BY_NAME_ACTION_BF_ITEMDRINK = "bodilyFunction_itemDrink";
	public static final String VALUE_BY_NAME_ACTION_BF_ITEMEAT = "bodilyFunction_itemEat";

	
	public static final String VALUE_BY_NAME_EVENT = "event";
	public static final String VALUE_BY_NAME_EVENT_PARAMS = PRAEFIX_EVENT + "params";
	public static final String VALUE_BY_NAME_EVENT_POSITION = PRAEFIX_EVENT + "position";
	public static final String VALUE_BY_NAME_EVENT_CAUSER = PRAEFIX_EVENT + "causer";
	public static final String VALUE_BY_NAME_EVENT_TARGET = PRAEFIX_EVENT + "target";
	public static final String VALUE_BY_NAME_EVENT_DIRECTION = PRAEFIX_EVENT + "direction";
	public static final String VALUE_BY_NAME_EVENT_INTENSITY = PRAEFIX_EVENT + "intensity";

	public static final String VALUE_BY_NAME_EVENT_WEAPON = PRAEFIX_EVENT + "weapon";
	public static final String VALUE_BY_NAME_EVENT_SENTENCE = PRAEFIX_EVENT + "sentence";
	public static final String VALUE_BY_NAME_EVENT_SENTENCETYPE = PRAEFIX_EVENT + "sentenceType";

	public static final String VALUE_BY_NAME_EVENT_MOVE_ACCELERATION = ActionType.move.getPraefix() + "acceleration";
	public static final String VALUE_BY_NAME_EVENT_MOVE_VELOCITY = ActionType.move.getPraefix() + "velocity";
	public static final String VALUE_BY_NAME_EVENT_EQUIP_ITEM = ActionType.equip.getPraefix() + "item";
	public static final String VALUE_BY_NAME_EVENT_EQUIP_ITEMISDRINKABLE = ActionType.equip.getPraefix() + "itemIsDrinkable";
	public static final String VALUE_BY_NAME_EVENT_EQUIP_ITEMISEATABLE =  ActionType.equip.getPraefix() + "itemIsEatable";
	public static final String VALUE_BY_NAME_EVENT_HANDLE_INTENSITY = ActionType.handleItem.getPraefix() + "intensity";
	public static final String VALUE_BY_NAME_EVENT_HANDLE_ITEM1 = ActionType.handleItem.getPraefix() + "item1";
	public static final String VALUE_BY_NAME_EVENT_HANDLE_ITEM2 = ActionType.handleItem.getPraefix() + "item2";
	public static final String VALUE_BY_NAME_EVENT_USEWEAPON_DIRECTION = ActionType.useWeapon.getPraefix() + "direction";
	public static final String VALUE_BY_NAME_EVENT_USEWEAPON_INTENSITY = ActionType.useWeapon.getPraefix() + "intensity";
	public static final String VALUE_BY_NAME_EVENT_PUNCH_DIRECTION = ActionType.punch.getPraefix() + "direction";
	public static final String VALUE_BY_NAME_EVENT_PUNCH_INTENSITY = ActionType.punch.getPraefix() + "intensity";
	public static final String VALUE_BY_NAME_EVENT_SAY_LOUDNESS = ActionType.say.getPraefix() + "loudness";
	public static final String VALUE_BY_NAME_EVENT_BF_ITEMDRINK = ActionType.bodilyFunction.getPraefix() + "itemDrink";
	public static final String VALUE_BY_NAME_EVENT_BF_ITEMEAT = ActionType.bodilyFunction.getPraefix() + "itemEat";

	public static final String VALUE_BY_NAME_WEAPON_MASS = PRAEFIX_WEAPON + "mass";
	public static final String VALUE_BY_NAME_WEAPON_HARDNESS = PRAEFIX_WEAPON + "hardness";
	public static final String VALUE_BY_NAME_WEAPON_SHARPNESS = PRAEFIX_WEAPON + "sharpness";
	
	public static final String VALUE_NAME_KNOWLEDGE_ELEMENT_PROPS = "knowledgeElementProperties";

	public static final String VALUE_NAME_KNOWLEDGE_SUBJECT = "subject";
	public static final String VALUE_NAME_KNOWLEDGE_SOURCE = "source";
	public static final String VALUE_NAME_KNOWLEDGE_SOURCE_TYPE = "sourceType";
	public static final String VALUE_NAME_KNOWLEDGE_SOURCE_MYSELF = "myself";
	public static final String VALUE_NAME_KNOWLEDGE_RELATION_SUBJECT = "subject";
	public static final String VALUE_NAME_KNOWLEDGE_RELATION_VERB = "verb";
	public static final String VALUE_NAME_KNOWLEDGE_RELATION_ADVERB = "adverb";
	public static final String VALUE_NAME_KNOWLEDGE_RELATION_OBJECT1 = "object1";
	public static final String VALUE_NAME_KNOWLEDGE_RELATION_OBJECT2 = "object2";
	public static final String VALUE_NAME_KNOWLEDGE_PROPERTY_CRITERION = "criterion";
	public static final String VALUE_NAME_KNOWLEDGE_PROPERTY_VALUE = "value_"; // the value's index number is added !
	public static final String VALUE_NAME_KNOWLEDGE_VALUE_VALUE = "value_"; // the value's index number is added !
	
	public static final String VALUE_NAME_UNUSED_BECAUSE_TEMPORARY = "temp";

	public static final String NO_METHOD_NAME = "";
	
	private Type type;
	private String name = "";
	private Object value;
	
	boolean valid;
	ValueTransferCode transferCode = ValueTransferCode.noFurtherInformation; 
	
	private static Value valueNothing;
	
	public static Value getValueNothing() {
		if (valueNothing == null) {
			valueNothing = new Value();
		}
		return valueNothing;
	}
	
	//Dummy-Value
	protected Value() {
		this.type = Type.nothing;
		valid = false;
	}
	
	public Value(JsonValue jsonObject) {
		this.type = Type.fromName(jsonObject.type);
		this.name = jsonObject.name;
		initValueFromString(jsonObject.value, this.type);
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
		initValueFromString(valueAsString, castToType);
	}
	
	public Value (Value original) {
		this.name = original.name;
		this.type = original.type;
		// TODO deep copy original.value
		this.value = original.value;
		this.valid = original.valid;
	}
	
	private void initValueFromString(String valueAsString, Type castToType) {
		
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
		case bool:
			this.value = Boolean.parseBoolean(valueAsString);
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
	
	public boolean checkObjectIsNull() {
		return (value == null);
	}
	
	public void setInvalid() {
		valid = false;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public boolean isInvalidOrNothing() {
		if (!valid) return true;
		
		if (value == null) return true;
		if (value instanceof NoSavedValue) return true;
		
		return false;
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

	
	public Object getObject(Type type) { 
		
		Type typeUsing	= checkType(type);
		if (typeUsing == null) return NoObject.getNoObject(NoObjectReason.typeMismatchForGetObject);
		
		switch (typeUsing) {
		case integer:
			return (int) value;
		case longinteger:
			if (value instanceof Integer)	return ((Integer) value).longValue();
			return (long) value;
		case floatingpoint:
			if (value instanceof Double) return (double)value;
			if (value instanceof Integer)	return (float) ((Integer) value).longValue();
			else return (float) value;
		case time:
			if (!(value instanceof Time)) {
				return new Time();
			}
			else
				return (Time) value;
		case lexem:
			if (value instanceof Lexem)	{
				return (Lexem) value;
			}
			else if (value instanceof Integer) {
				int lexemID = ((Integer) value).intValue();
				return AllWords.getLexem(lexemID);
			}
			else if (Integer.class.isInstance(value)) {
				int lexemID  = (int) value;
				return AllWords.getLexem(lexemID);
			}
			return NoObject.getNoObject(NoObjectReason.instanceOfCheckFailed);
		case relation:
			if (value instanceof Relation)	{
				return (Relation) value;
			}
			else if (value instanceof Integer) {
				int relationID = ((Integer) value).intValue();
				return Relation.getName(relationID);
			}
			else if (Integer.class.isInstance(value)) {
				int relationID  = (int) value;
				return Relation.getName(relationID);
			}
			return NoObject.getNoObject(NoObjectReason.instanceOfCheckFailed);
		default:
			if ((value instanceof IObjectSender) && (this instanceof ValueProperty)) {
				return ((IObjectSender) value).copy();
			}
			else {
				return value;
			}
		}
	}	
	
	private Type checkType(Type type) {
		
		switch (this.type) {
		case integer:
			if ( type == Type.integer) return Type.integer;
			if ( type == Type.longinteger) return Type.longinteger;
			if ( type == Type.floatingpoint) return Type.floatingpoint;
			break;
		case longinteger:
			if ( type == Type.longinteger) return Type.longinteger;
			if ( type == Type.floatingpoint) return Type.floatingpoint;
			break;
		case floatingpoint:
			if ( type == Type.floatingpoint) return Type.floatingpoint;
			break;
		case simPropName:
			if ( type == Type.simPropName) return Type.simPropName;
			break;
		case simObjProp:
			if ( type == Type.simObjProp) return Type.simPropName;
			if ( type == Type.simPropName) return Type.simPropName;
			break;
		case eventProp:
			if ( type == Type.eventProp) return Type.eventProp;
			if ( type == Type.simPropName) return Type.simPropName;
			break;
		case actionProp:
			if ( type == Type.actionProp) return Type.actionProp;
			if ( type == Type.simPropName) return Type.simPropName;
			break;
		default:
			return type;
		}
		
		// no matching types !!!
		return null;
	}
	
	
	protected Object getOriginal() {
		if (this instanceof ValueProperty) {
			return this.value;
		}
		else {
			return NoObject.getNoObject(NoObjectReason.valueIsNoValueProperty);
		}
	}
		
	public int requestObject(IObjectReceiver receiver, int requestID, Type type) {
		
		if (this.type.equals(type)) {
			IObjectSender object;
			object = getObjectSender();
			return object.sendYourselfTo(receiver, requestID);
		}
		else {
			return IObjectSender.OBJECT_NOT_SENDED_BECAUSE_WRONG_TYPE;
		}
	}

	
	public final ValueProperty getProperty(IAccessToken token, PropertyName simPropName, String methodName, String valueName) {
		
		ValueProperty result;
		result = ValueProperty.getInvalid();
		Object object = value;
		
		boolean returnInvalidVP = false;
		
		// check permission for a value property
		if (this instanceof ValueProperty) {
			
			ValueProperty thisAsVP = (ValueProperty)this;
			if (thisAsVP.isProtected()) {
				if (thisAsVP.checkHasPermission(token)) {

				}
				else {
					returnInvalidVP = true;
				}
			}
		}
		
		if (!returnInvalidVP) {
			

			switch (simPropName) {
			case method:
				
				if (methodName.length() > 0) {
					
					// use reflection for calling the method
					
					if (object instanceof ISavedValue) {
						ISavedValue savedValue;
						savedValue = (ISavedValue) object;
						result = savedValue.getPropertyFromMethod(token, methodName, valueName);
					}
					else if (object instanceof IEnumProperty) {
						IEnumProperty enumProperty;
						enumProperty = (IEnumProperty) object;
						if (methodName.equals("getIndex")) {
							result = new ValueProperty(Type.integer,  valueName, enumProperty.getIndex());
						}
					}
					
				}
				break;
				
			case isA:
				
				if (methodName.length() > 0) {
					
					
					if (object instanceof SimulationObject) {
						SimulationObject simObj;
						simObj = (SimulationObject) object;
						result = simObj.isA(methodName, valueName);
					}
					
				}
				break;
	
			case check:
				
				if (methodName.length() > 0) {
					
					
					if (object instanceof SimulationObject) {
						SimulationObject simObj;
						simObj = (SimulationObject) object;
						result = simObj.check(methodName, valueName);
					}
					
				}
				break;
	
			case isElem:
				
				if (methodName.length() > 0) {
					
					
					if (object instanceof SimulationObject) {
						SimulationObject simObj;
						simObj = (SimulationObject) object;
						// the methodName variable holds the setNumber as string
						result = simObj.isElementOf(methodName, valueName);
					}
					
				}
				break;
				
			case unknown:
				
				break;
				
			default:
				
				// call getProperty()
				
				if (object instanceof SimulationObject) {
					SimulationObject simObj;
					simObj = (SimulationObject) object;
					result = simObj.getProperty(token, simPropName, valueName);
				}
				else if (object instanceof StateSimulationObject) {
					StateSimulationObject stateSimObj;
					stateSimObj = (StateSimulationObject) object;
					result = stateSimObj.getProperty(token, simPropName, valueName);
				}
				else if (object instanceof ISavedValue) {
					ISavedValue savedValue;
					savedValue = (ISavedValue) object;
					result = savedValue.getProperty(token, simPropName, valueName);
				}
				else if (object instanceof Event) {
					Event event;
					event = (Event) object;
//					result = event.getProperty(cluster, simPropName, valueName);
				}
				else if (object instanceof AbstractAction) {
					
				}
				break;
				
			}
			
		}
		
		return result;
		
	}

	
	
	
	protected IObjectSender getObjectSender() {
		if (value instanceof IObjectSender) {
			return (IObjectSender) value;
		}
		else {
			return NoObjectSender.getObjectNothing();
		}
	}

	public boolean equals(Value anotherValue) {
		if (anotherValue == null) return false;
		if (this.type.equals(anotherValue.type) ) {

			Object valueThis  = this.getObject(this.type);
			Object valueThat  = anotherValue.getObject(anotherValue.type);
			
			// TODO are two nulls (two invalids, two nothings) equal or not?
			if (valueThis == null || valueThat == null) return false;
			if (this.isInvalidOrNothing()) return false;
			if (anotherValue.isInvalidOrNothing()) return false;
			
			// TODO call concrete equals() implementations
			switch (this.type) {
				case attributeArray:
					if (valueThis instanceof NoSavedValue) return false;
					if (valueThat instanceof NoSavedValue) return false;
					return ( ((AttributeArray) valueThis).equals((AttributeArray) valueThat) ) ;
				default:
					return (  valueThis.equals(valueThat) ) ;
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
	
	public String toString() {
		if (value != null)
			return "'" + value.toString() + "'";
		else
			return "";
	}
}
