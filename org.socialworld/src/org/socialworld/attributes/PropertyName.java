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
	method(1),
	isA(2),
	check(3),
	isElem(4),
	
	simobj_position(1001001),
	simobj_attributeArray(1001011),
	simobj_directionMove(1001021), simobj_directionChest(1001022), simobj_directionActiveMove(1001023),
	simobj_inventory(1001031),  // subs see under stateInventory
	simobj_knowledge(1001041),

	stateSeer(1101000),
	stateSeer_directionView(1101001),
	stateSeer_angleViewPerceivingEvents(11010002),
	stateSeer_angleViewPerceivingEventsInRadians(1101003),
	stateSeer_angleViewPerceivingObjects(1101004),
	stateSeer_angleViewPerceivingObjectsInRadians(1101005),
	stateSeer_bestPercipiencePerpendicular(11010006),
	stateSeer_sizeDistanceRelationThreshold(11010007),

	stateInventory(1102000),
	stateInventory_inventory(1102001),
	inventory_shirt(1102031),
	inventory_trousers(1102032),
	inventory_shoes(1102033),
	inventory_cap(1102034),
	inventory_leftHand(1102011),
	inventory_rightHand(1102012),
	inventory_leftFoot(1102013),
	inventory_rightFoot(1102014),
	inventory_mouth(1102021),

	stateBody(1103000),
	
	stateComposition(1104000),
	
	stateAppearance(1105000),
	stateAppearance_mainColour(1105001),

	statePerceptible(1106000),
	statePerceptible_position(1106001),
	statePerceptible_cuboid(1106002),
	
	stateEatable(1107000),
	
	stateRunnable(1108000),
	stateRunnable_speed(1108001),
	stateRunnable_numberLegs(1108002),
	stateRunnable_directionRun(1108003),
	
	
	stateDispersibility(1109000),
	stateDispersibility_widthWings(1109001),
	stateDispersibility_numberWings(1109002),
	stateDispersibility_directionFly(1109003),
	
	
	event_position(2001001),
	event_direction(2001020),
	
	action_position(3001001),
	
	position_vector(4001001),
	
	direction_vector(4002001),
	direction_power(4002002),
	
	vector(5001010),
	vector_x(5001011),
	vector_y(5001012),
	vector_z(5001013);

	
	private final static int THREASHOLD_SIMPROPERTY = 2000000;

	
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
		case simobj_attributeArray:
			return Type.attributeArray;
		case simobj_position:
		case simobj_inventory:  
		case simobj_knowledge:
		case simobj_directionMove: 
		case simobj_directionChest: 
		case stateSeer_directionView: 
		case simobj_directionActiveMove: 
		case stateSeer: 
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
	

	public static PropertyName forString(String name) {
		
		
		for (PropertyName prop : PropertyName.values())
			if (prop.name().equals( name))
				return prop;
		return unknown;


	}
	
	
	public PropertyName parentState() {
	
		int index_ = name().indexOf("_");
		int indexState = name().indexOf("state");
		String parentStateName;
		
		if (index_ > 0 && indexState == 0) {
			parentStateName = name().substring(0, index_);
			return forString(parentStateName);
		}
		else {
			return null;
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
		case stateSeer_directionView: return only_pathToKnowledgeValue;
		case simobj_directionActiveMove: return only_pathToKnowledgeValue;
		case position_vector: return only_knowledgeValue;
		default:			return emptyPermissions;
		}
	}
}
