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

import java.util.List;
import java.util.ArrayList;

import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.Type;
import org.socialworld.conversation.Relation;

public enum PropertyName {

	
	unknown(0), 
	method(1),
	isA(2),
	check(3),
	isElem(4),
	
	simobj_position(1001001),
	simobj_attributeArray(1001011),
	simobj_directionMove(1001021), simobj_directionChest(1001022), simobj_directionActiveMove(1001023),
	simobj_knowledge(1001041),

	stateSeer(1101000),
	stateSeer_directionView(1101001),
	stateSeer_propsSeer(1101002),
	stateSeer_bestPercipiencePerpendicular(1101003),
	propsSeer_sizeDistanceRelationThreshold(1101101),
	propsSeer_angleViewPerceivingEvents(1101102),
	propsSeer_angleViewPerceivingEventsInRadians(1101103),
	propsSeer_angleViewPerceivingObjects(1101104),
	propsSeer_angleViewPerceivingObjectsInRadians(1101105),

	stateInventory(1102000),
	stateInventory_inventory(1102001),
	inventory_shirt(1102031),
	inventory_trousers(1102032),
	inventory_cap(1102034),
	inventory_leftHand(1102011),
	inventory_rightHand(1102012),
	inventory_leftSock(1102013),
	inventory_rightSock(1102014),
	inventory_leftShoe(1102015),
	inventory_rightShoe(1102016),
	inventory_mouth(1102021),

	stateBody(1103000),
	
	stateComposition(1104000),
	stateComposition_materialSet(1104001),
	stateComposition_mainMaterial(1104002),
	materialSet_materials(1104101),
	materialSet_mainMaterial(1104102),
	
	stateAppearance(1105000),
	stateAppearance_mainColour(1105001),
	stateAppearance_colourFrontside(1105011),
	stateAppearance_colourBackside(1105012),
	stateAppearance_colourLeftside(1105013),
	stateAppearance_colourRightside(1105014),
	stateAppearance_colourUpperside(1105015),
	stateAppearance_colourLowerside(1105016),
	stateAppearance_colourInside(1105017),
	stateAppearance_colourSkin(1105021),
	stateAppearance_colourHair(1105022),
	stateAppearance_colourBeard(110523),
	stateAppearance_colourEye(1105024),
	stateAppearance_colourHead(1105031),
	stateAppearance_colourBreast(1105032),
	stateAppearance_colourBack(1105033),
	stateAppearance_colourTail(1105034),
	stateAppearance_colourLegs(1105035),
	stateAppearance_colourArms(1105036),
	stateAppearance_colourLeave(1105041),
	stateAppearance_colourFruit(1105042),
	stateAppearance_colourBlossom(1105043),
	colourSet_colours(1105101),
	//colourSet_portions(1105102),
	//colourSet_types(1105103),
	colourSet_mainColour(1105104),
	stateAppearance_dimension(1105200),
	dimension_height_m(1105201),
	dimension_height_mm(1105202),
	dimension_width_m(1105203),
	dimension_width_mm(1105204),
	dimension_depth_m(1105205),
	dimension_depth_mm(1105206),
	
	statePerceptible(1106000),
	statePerceptible_position(1106001),
	statePerceptible_cuboid(1106002),
	
	stateEatable(1107000),
	stateEatable_nutrientSet(1107001),
	stateEatable_tasteSet(1107002),
	stateEatable_mainNutrient(1107003),
	stateEatable_mainTaste(1107004),
	nutrientSet_nutrients(1107101),
	nutrientSet_mainNutrient(1107102),
	tasteSet_tastes(1107201),
	tasteSet_mainTaste(1107202),
	
	stateRunning(1108000),
	stateRunning_speed(1108001),
	stateRunning_numberLegs(1108002),
	stateRunning_directionRun(1108003),
	
	
	stateFlying(1109000),
	stateFlying_widthWings(1109001),
	stateFlying_numberWings(1109002),
	stateFlying_directionFly(1109003),
	
	
	event_position(2001001),
	event_direction(2001020),
	
	action_position(3001001),
	
	position_vector(4001001),
	
	direction(4002000),
	direction_vector(4002001),
	direction_power(4002002),
	
	vector(5001010),
	vector_x(5001011),
	vector_y(5001012),
	vector_z(5001013),
	vector_length(5001014),

	pathFromPathFinder(6001001),
	pathFromPathFinder_result(6001002);
	
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
//		case simobj_inventory:  
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
		case direction:
			return Type.simPropName;
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
			if (this.name().contains("position"))	return simobj_position;
			else if (this.name().contains("direction"))	return simobj_directionMove;
			else if (this.name().contains("dimension"))	return stateAppearance_dimension;
		case eventProp:
			if (this.name().contains("position"))	return event_position;
			else if (this.name().contains("direction"))	return event_direction;
		case actionProp:
			return action_position;
		default:
			return unknown;
		}
	}
	
	public String toString() {
		return this.name();
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
			return unknown;
		}
	}
	
	public boolean isSimProperty() {
		
		if (this.index < THREASHOLD_SIMPROPERTY) {
			return true;
		}
		
		return false;
		
	}

	public boolean checkIsUsableAsRelationObject(Relation relation) {
		
		switch (relation) {
			case carry: return (this.index >= 1102031 && this.index <= 1102021) ;
			default: return false;
		}
	}
	
	private static List<PropertyUsingAs> emptyPermissions = new ArrayList<PropertyUsingAs>();
//	private static PropertyUsingAs only_pathToKnowledgeValue[] = {PropertyUsingAs.pathToKnowledgeValue };
//	private static PropertyUsingAs only_knowledgeValue[] = {PropertyUsingAs.knowledgeValue };
//	private static PropertyUsingAs only_knowledgeRelationSubjectOrObject[] =  {PropertyUsingAs.knowledgeRelationSubject, PropertyUsingAs.knowledgeRelationObject };

	
	public  List<PropertyUsingAs> getUsingAsPermissions() {
		switch (this) {
		case simobj_position: return emptyPermissions;
//		case simobj_inventory: return only_knowledgeRelationSubjectOrObject;
		case simobj_directionMove: return emptyPermissions;
		case simobj_directionChest: return emptyPermissions;
		case stateSeer_directionView: return emptyPermissions;
		case simobj_directionActiveMove: return emptyPermissions;
		case position_vector: return emptyPermissions;
		default:			return emptyPermissions;
		}
	}
	
	
}
