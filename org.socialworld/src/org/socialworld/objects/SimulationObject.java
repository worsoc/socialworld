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


import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionNothing;
import org.socialworld.attributes.Position;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.application.Scheduler;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.ActionHandler;
import org.socialworld.core.Event;
import org.socialworld.core.IEventParam;
import org.socialworld.core.SearchActionDescription;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.connections.Connection;
import org.socialworld.objects.connections.ConnectionType;
import org.socialworld.propertyChange.ListenedBase;

/**
 * Every simulation object (actor in the simulation) is inherited by the abstract class SimulationObject.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public abstract class SimulationObject extends ListenedBase {
	

	private		int 			objectID;
	
	private StateSimulationObject state;

	private 	ActionHandler 	actionHandler;
	

	private	WriteAccessToSimulationObject guard;
	private GrantedAccessToProperty grantAccessToAllProperties[];
	private GrantedAccessToProperty grantAccessToPropertyAction[];
	
	
	/**
	 * The constructor creates an incomplete simulation object. It's an "empty" object. There is only the object ID.
	 * 
	 */
	public SimulationObject(int objectID) {
		this.objectID = objectID;
		this.guard = null;
		this.actionHandler = new ActionHandler(this);
		
		grantAccessToAllProperties = new GrantedAccessToProperty[1];
		grantAccessToAllProperties[0] = GrantedAccessToProperty.all;

		grantAccessToPropertyAction = new GrantedAccessToProperty[1];
		grantAccessToPropertyAction[0] = GrantedAccessToProperty.action;

	}

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    SIMULATION OBJECT TXPE     ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	protected abstract SimulationObject_Type getSimObjectType();
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    WRITE ACCESS     /////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The method sets write access to a guard.
	 * This guard can manipulate the object's state by calling methods.
	 * Only the guard has access to object manipulation methods.
	 * There is only one guard for a simulation object.
	 * So a guard can only be set if no guard has been set yet.
	 * 
	 * @param guard
	 */
	final void setWriteAccess(WriteAccessToSimulationObject guard) {
		if (this.guard == null)  this.guard = guard;
	}
	
	
	protected final boolean checkGuard(WriteAccessToSimulationObject guard) {
		return (this.guard == guard);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    OBJECT_ID      ///////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
		
	public final int getObjectID() {
		return objectID;
	}
		
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    STATE      ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public final void refreshState() {
		this.state.refresh();
	}
	
	protected final void setState(StateSimulationObject state, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.state = state;
			assignState(state);
		}
	}

	protected abstract void assignState(StateSimulationObject state);

	protected final boolean checkIsMyState(StateSimulationObject state) {
		return (state == this.state);
	}
	
	protected final StateSimulationObject getState(WriteAccessToSimulationObject guard) {
		if (checkGuard(guard))
			return this.state;
		else
			return null;
		
	}
	
	/**
	 * The method returns the object's position.
	 * 
	 * @return position
	 */
	public final Position getPosition() {
		return this.state.getPosition();
	}
	
	public final Value getPositionVectorAsValue(String valueName) {
		return this.state.getPositionVectorAsValue(valueName);
	}
	
	public final int getReactionType(int eventType) {
	 return this.state.getReactionType(eventType);
	} 

	public final int getInfluenceType(int eventType) {
		 return this.state.getInfluenceType(eventType);
		} 

	public final int getState2ActionType() {
		 return this.state.getState2ActionType();
		} 
	
		
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ACTION     ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public final void setAction(AbstractAction newAction, WriteAccessToSimulationObject guard) {

		if (this.guard == guard) {
			if (actionHandler.findAction(new SearchActionDescription(newAction)) == ActionNothing.getInstance()) {
				actionHandler.insertAction(newAction);
			}
		}
	}
	
	
	/**
	 * The method lets a simulation object do an action.
	 * Only the object's action handler is allowed to let the object do the action
	 * 
	 * @param action
	 */
	public final void doAction(AbstractAction action, int milliSecondsPast, ActionHandler myActionHandler) {
		if (this.actionHandler == myActionHandler) {
			action.setActor(this, guard.getMeHidden(grantAccessToAllProperties));
			action.perform();
			action.lowerRemainedDuration(milliSecondsPast);
			action.removeWriteAccess();
		}
	}

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    EVENT      ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * The method determines the influence of an event. It calculates how the
	 * event changes the attributes of the simulation object.
	 * 
	 * @param simualationEvent -
	 *            the event influencing the simulation object
	 */
	public final void changeByEvent(final Event simualationEvent) {
		this.state.calculateEventInfluence(simualationEvent);
	}

	/**
	 * The method determines the reaction to an event.
	 * It creates an action object and inserts it into the action handler list.
	 * 
	 * @param simualationEvent -
	 *            the event influencing the simulation object
	 */
	public final void reactToEvent(final Event simualationEvent) {
	
		Scheduler.getInstance().createReaction(simualationEvent, state, guard.getMeHidden(grantAccessToPropertyAction));
		
	}

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    CONNECTED OBJECTS  ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	final public void connectEqualTo(SimulationObject object, ConnectionType type) {
		this.state.connectEqualTo(object, type, this.guard);
	}

	final public void connectAsMasterTo(SimulationObject object, ConnectionType type) {
		this.state.connectAsMasterTo(object, type, this.guard);
	}

	final public void connectAsSlaveTo(SimulationObject object, ConnectionType type) {
		this.state.connectAsSlaveTo(object, type, this.guard);
	}

	final public void addConnection(Connection connection,  SimulationObject connectedObject) {
		this.state.addConnection(connection, connectedObject, this.guard);
	}
	
	final public void releaseConnection(Connection connection,  SimulationObject connectedObject) {
		this.state.releaseConnection(connection, connectedObject, this.guard);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IEventParam paramObject) {
	
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		
		propertiesAsValueList.add(this.state.getPositionVectorAsValue(Value.VALUE_BY_NAME_SIMOBJ_POSITION_VECTOR));
		propertiesAsValueList.add(this.state.getDirectionMoveAsValue(Value.VALUE_BY_NAME_SIMOBJ_MOVE_DIRECTION));
		propertiesAsValueList.add(this.state.getPowerMoveAsValue(Value.VALUE_BY_NAME_SIMOBJ_MOVE_POWER));

		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    toString()  //////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public String toString() {
		return "" + objectID;
	}
}
