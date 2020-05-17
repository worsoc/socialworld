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
package org.socialworld.objects;

import java.util.List;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.move.Path;
import org.socialworld.actions.move.PathFinder;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.SimPropertyName;
import org.socialworld.calculation.FunctionByMatrix;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.IEventParam;
import org.socialworld.objects.concrete.animals.ISeer;
import org.socialworld.objects.concrete.animals.StateSeer;


/**
 * An animal is a simulation object with extensions to express the living kind.
 * There are methods for action handling and event effects.
 * @author Mathias Sikos (tyloesand)
 * 
 */
public abstract class Animal extends SimulationObject implements ISeer {

	private StateAnimal state;
	private StateSeer stateSeer;
	
	private PathFinder pathFinder;

	private boolean initialized = false;
	

	
	protected SimulationObject_Type getSimObjectType() {
		return SimulationObject_Type.animal;
	}

	protected void assignState(StateSimulationObject state) {
		if (checkIsMyState(state) ) this.state = (StateAnimal) state;
	}
	
	protected void init() {
		if (initialized == false) {
			pathFinder = new PathFinder(this, this.state.getKnownPathsPool());
			initialized = true;
		}
	}

	
	protected List<State> createAddOnStates() {
		
		List<State> result = super.createAddOnStates();
		
		this.stateSeer = (StateSeer) getInitState(StateSeer.class.getName());
		result.add(this.stateSeer);
		
		return result;
		
	};


	public Value getProperty(SimPropertyName prop, String name) {
		switch (prop) {
		case simobj_attributearray:
		case simobj_inventory:
		case simobj_knowledge:
		case simobj_directionChest:
		case simobj_directionActiveMove:
			return this.state.getProperty(prop, name);
		default:
			return super.getProperty(prop, name);
		}
	}
	


///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////    implementing ISeer     //////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public double getSizeDistanceRelationThreshold() {
		return this.stateSeer.getSizeDistanceRelationThreshold();
	}

	final public Direction getDirectionView() {
		return (Direction) stateSeer.getProperty(SimPropertyName.simobj_directionView).getValue();
	}

	final public float getAngleViewPerceivingObjects() {
		return this.stateSeer.getAngleViewPerceivingObjects();
	}

	final public double getAngleViewPerceivingObjectsInRadians() {
		return this.stateSeer.getAngleViewPerceivingObjectsInRadians();
	}

	final public float getAngleViewPerceivingEvents() {
		return this.stateSeer.getAngleViewPerceivingEvents();
	}

	final public double getAngleViewPerceivingEventsInRadians() {
		return this.stateSeer.getAngleViewPerceivingEventsInRadians();
	}

	final public int getBestPercipiencePerpendicular() {
		return this.stateSeer.getBestPercipiencePerpendicular();
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ATTRIBUTES  //////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	final public FunctionByMatrix getMatrix() {
		return this.state.getMatrix();
	}

	final public int getAttribute(Attribute attributeName) {
		return this.state.getAttribute(attributeName);
	}
	
	final public Value getAttributesAsValue(String valueName) {
		return getProperty(SimPropertyName.simobj_attributearray, valueName);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    KNOWLEDGE  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	final public Path findPath(Position end) {
		return this.pathFinder.findPath(end);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    DIRECTIONS ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @return the directionChest
	 */
	final public Value getDirectionChestAsValue(String valueName) {
		return this.state.getDirectionChestAsValue(valueName);
	}


	final void setDirectionView(Vector directionView, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.stateSeer.setDirectionView(directionView);
		}
	}




///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ACTION     ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	

	/**
	 * The method holds the implementation of bodilyFunctions.
	 * 
	 * @param action
	 */
	protected void bodilyFunction(final AbstractAction action) {
		final ActionMode mode = action.getMode();

		switch (mode) {
		case sleep:
			break;
		case drink:
			break;
		case eat:
			break;
		case piss:
			break;
		case shit:
			break;
		default:
		}
	}

	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    INVENTORY  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	// TODO interface for more complex access to inventory
	
	final public SimulationObject getLeftHandItem() {
	// no copy because it is a simulation object and that isn't allowed to be duplicated
	return this.state.getLeftHandItem();
	}
	
	final public SimulationObject getRightHandItem() {
	// no copy because it is a simulation object and that isn't allowed to be duplicated
	return this.state.getRightHandItem();
	}
	
	final public SimulationObject getMouthItem() {
	// no copy because it is a simulation object and that isn't allowed to be duplicated
	return this.state.getMouthItem();
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IEventParam paramObject) {
	
		super.requestPropertyList(paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		
		propertiesAsValueList.add(getProperty(SimPropertyName.simobj_attributearray));
		propertiesAsValueList.add(getProperty(SimPropertyName.simobj_directionChest));
		propertiesAsValueList.add(getProperty(SimPropertyName.simobj_directionView));
		propertiesAsValueList.add(getProperty(SimPropertyName.simobj_directionActiveMove));
		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}

}
