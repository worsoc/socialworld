package org.socialworld.attributes;

import org.socialworld.calculation.Type;

public enum SimPropertyName {

	unknown, 
	
	simobj_position,
	simobj_directionMove, simobj_directionChest, simobj_directionActiveMove,
	simobj_inventory,
	simobj_knowledge,
	
	event_position,
	
	action_position;
	
	public final static String SIMOBJPROP_POSITION = "position";
	public final static String SIMOBJPROP_INVENTORY = "inventory";
	public final static String SIMOBJPROP_KNOWLEDGE = "knowledge";
	public final static String SIMOBJPROP_DIRECTION_MOVE = "direction_move";
	public final static String SIMOBJPROP_DIRECTION_CHEST = "direction_chest";
	public final static String SIMOBJPROP_DIRECTION_ACTIVEMOVE = "direction_activemove";
	
	
	public Type getType() {
		switch (this) {
		case unknown:
			return Type.nothing; 
		case simobj_position:
		case simobj_inventory:  
		case simobj_knowledge:
		case simobj_directionMove: 
		case simobj_directionChest: 
		case simobj_directionActiveMove: 
			return Type.simObjProp; 
		case event_position:
			return Type.eventProp; 
		case action_position:
			return Type.actionProp; 
		default:
			return Type.nothing;
		}
	}
	
	public SimPropertyName toType(Type propertyType) {
		if (getType() == propertyType ){
			return this;
		}
		switch (propertyType) {
		case simObjProp:
			return simobj_position;
		case eventProp:
			return event_position;
		case actionProp:
			return action_position;
		default:
			return unknown;
		}
	}
	
	public String toString() {
		
		switch (this) {
		case unknown: return "unknown"; 
		case simobj_position: return SIMOBJPROP_POSITION;
		case simobj_inventory: return SIMOBJPROP_INVENTORY; 
		case simobj_knowledge: return SIMOBJPROP_KNOWLEDGE;
		case simobj_directionMove: return SIMOBJPROP_DIRECTION_MOVE;
		case simobj_directionChest: return SIMOBJPROP_DIRECTION_CHEST;
		case simobj_directionActiveMove: return SIMOBJPROP_DIRECTION_ACTIVEMOVE; 
		default: return "";
		}
		
	}


}
