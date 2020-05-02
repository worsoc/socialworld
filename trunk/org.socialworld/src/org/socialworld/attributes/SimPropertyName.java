package org.socialworld.attributes;

import org.socialworld.calculation.Type;

public enum SimPropertyName {

	unknown(0), position(1), inventory(2), knowledge(3),
	directionMove(10), directionChest(11), directionActiveMove(12);
	
	public final static String SIMOBJPROP_POSITION = "position";
	public final static String SIMOBJPROP_INVENTORY = "inventory";
	public final static String SIMOBJPROP_KNOWLEDGE = "knowledge";
	public final static String SIMOBJPROP_DIRECTION_MOVE = "direction_move";
	public final static String SIMOBJPROP_DIRECTION_CHEST = "direction_chest";
	public final static String SIMOBJPROP_DIRECTION_ACTIVEMOVE = "direction_activemove";
	
	private int arrayIndex;

	private SimPropertyName(int index) {
		this.arrayIndex = index;
	}
	
	public Type getType() {
		return Type.simObjProp;
	}
	
	public String toString() {
		
		switch (this) {
		case unknown: return "unknown"; 
		case position: return SIMOBJPROP_POSITION;
		case inventory: return SIMOBJPROP_INVENTORY; 
		case knowledge: return SIMOBJPROP_KNOWLEDGE;
		case directionMove: return SIMOBJPROP_DIRECTION_MOVE;
		case directionChest: return SIMOBJPROP_DIRECTION_CHEST;
		case directionActiveMove: return SIMOBJPROP_DIRECTION_ACTIVEMOVE; 
		default: return "";
		}
		
	}


}
