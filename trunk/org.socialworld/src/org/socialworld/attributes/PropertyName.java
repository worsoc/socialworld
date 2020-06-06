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
package org.socialworld.attributes;


import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.Type;

public enum PropertyName {

	
	unknown(0), 
	
	simobj_position(1001),
	simobj_attributearray(1011),
	simobj_directionMove(1021), simobj_directionChest(1022), simobj_directionView(1023), simobj_directionActiveMove(1024),
	simobj_inventory(1031),
	simobj_knowledge(1041),
	simobj_stateSeer(2001),
	
	event_position(101001),
	event_direction(101020),
	
	action_position(201001),
	
	position_vector(1001001),
	direction_vector(1001023);

	private final static int THREASHOLD_SIMPROPERTY = 1000000;

	public final static String SIMOBJPROP_POSITION = "position";
	public final static String SIMOBJPROP_ATTRIBUTEARRAY = "attributes";
	public final static String SIMOBJPROP_INVENTORY = "inventory";
	public final static String SIMOBJPROP_KNOWLEDGE = "knowledge";
	public final static String SIMOBJPROP_DIRECTION_MOVE = "direction_move";
	public final static String SIMOBJPROP_DIRECTION_CHEST = "direction_chest";
	public final static String SIMOBJPROP_DIRECTION_VIEW = "direction_view";
	public final static String SIMOBJPROP_DIRECTION_ACTIVEMOVE = "direction_activemove";
	public final static String SIMOBJPROP_STATE_SEER = "state_seer";
	
	public final static String EVENT_POSITION = "event_position";
	public final static String EVENT_DIRECTION = "event_direction";
	
	public final static String ACTION_POSITION = "action_position";
	
	
	private int index;

	private PropertyName(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

	public static PropertyName getName(int index) {
		for (PropertyName prop : PropertyName.values())
			if (prop.index == index)
				return prop;
		return unknown;
	}

	
	public Type getType() {
		switch (this) {
		case unknown:
			return Type.nothing; 
		case simobj_attributearray:
			return Type.attributeArray;
		case simobj_position:
		case simobj_inventory:  
		case simobj_knowledge:
		case simobj_directionMove: 
		case simobj_directionChest: 
		case simobj_directionView: 
		case simobj_directionActiveMove: 
		case simobj_stateSeer: 
			return Type.simObjProp; 
		case event_position:
		case event_direction:
			return Type.eventProp; 
		case action_position:
			return Type.actionProp; 
		default:
			return Type.nothing;
		}
	}
	
	public PropertyName toType(Type propertyType) {
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
		case simobj_attributearray: return SIMOBJPROP_ATTRIBUTEARRAY;
		case simobj_inventory: return SIMOBJPROP_INVENTORY; 
		case simobj_knowledge: return SIMOBJPROP_KNOWLEDGE;
		case simobj_directionMove: return SIMOBJPROP_DIRECTION_MOVE;
		case simobj_directionChest: return SIMOBJPROP_DIRECTION_CHEST;
		case simobj_directionView: return SIMOBJPROP_DIRECTION_VIEW;
		case simobj_directionActiveMove: return SIMOBJPROP_DIRECTION_ACTIVEMOVE; 
		case simobj_stateSeer: return SIMOBJPROP_STATE_SEER; 
		
		case event_position: return EVENT_POSITION; 
		case event_direction: return EVENT_DIRECTION; 
		
		case action_position: return ACTION_POSITION; 
		
		default: return "";
		}
		
	}

	public static PropertyName forString(String name) {
		
		switch (name) {
		case "unknown": return unknown; 
		case SIMOBJPROP_POSITION: return simobj_position;
		case SIMOBJPROP_ATTRIBUTEARRAY: return simobj_attributearray;
		case SIMOBJPROP_INVENTORY: return simobj_inventory; 
		case SIMOBJPROP_KNOWLEDGE: return simobj_knowledge;
		case SIMOBJPROP_DIRECTION_MOVE: return simobj_directionMove;
		case SIMOBJPROP_DIRECTION_CHEST: return simobj_directionChest;
		case SIMOBJPROP_DIRECTION_VIEW: return simobj_directionView;
		case SIMOBJPROP_DIRECTION_ACTIVEMOVE: return simobj_directionActiveMove; 
		case SIMOBJPROP_STATE_SEER: return simobj_stateSeer; 
		
		case EVENT_POSITION: return event_position; 
		case EVENT_DIRECTION: return event_direction; 
		
		case ACTION_POSITION: return action_position;
		default: return unknown;
		}
	}
	
	public boolean isSimProperty() {
		
		if (this.index < THREASHOLD_SIMPROPERTY) {
			return true;
		}
		
		return false;
		
	}

	private static PropertyUsingAs emptyPermissions[] = {};
	private static PropertyUsingAs only_pathToKnowledgeValue[] = {PropertyUsingAs.pathToKnowledgeValue };
	private static PropertyUsingAs only_knowledgeValue[] = {PropertyUsingAs.knowledgeValue };
	private static PropertyUsingAs only_knowledgeRelationSubjectOrObject[] =  {PropertyUsingAs.knowledgeRelationSubject, PropertyUsingAs.knowledgeRelationObject };

	
	public  PropertyUsingAs[] getUsingAsPermissions() {
		switch (this) {
		case simobj_position: return only_pathToKnowledgeValue;
		case simobj_inventory: return only_knowledgeRelationSubjectOrObject;
		case simobj_directionMove: return only_pathToKnowledgeValue;
		case simobj_directionChest: return only_pathToKnowledgeValue;
		case simobj_directionView: return only_pathToKnowledgeValue;
		case simobj_directionActiveMove: return only_pathToKnowledgeValue;
		case position_vector: return only_knowledgeValue;
		default:			return emptyPermissions;
		}
	}
}
