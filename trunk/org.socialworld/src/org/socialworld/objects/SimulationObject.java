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


import org.apache.log4j.Logger;
import org.socialworld.actions.AbstractAction;
import org.socialworld.attributes.Position;
import org.socialworld.core.ActionHandler;
import org.socialworld.core.Event;
import org.socialworld.core.Simulation;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.propertyChange.ListenedBase;

/**
 * Every simulation object (actor in the simulation) is inherited by the abstract class SimulationObject.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public abstract class SimulationObject extends ListenedBase {
	protected static final Logger logger = Logger.getLogger(SimulationObject.class);

	private	WriteAccessToSimulationObject guard;
	private		int 			objectID;
	protected   Simulation  	simulation;
	

	protected 	ActionHandler 	actionHandler;
	
	
	private StateSimulationObject state;
	

	private GrantedAccessToProperty grantAccessToAllProperties[];

	/**
	 * The constructor creates an incomplete simulation object. It's an "empty" object. There is only the object ID.
	 * 
	 */
	public SimulationObject(int objectID) {
		this.objectID = objectID;
		this.guard = null;
		this.simulation = Simulation.getInstance();
		this.actionHandler = new ActionHandler(this);
		
		grantAccessToAllProperties = new GrantedAccessToProperty[1];
		grantAccessToAllProperties[0] = GrantedAccessToProperty.all;


	}
	

	final StateSimulationObject getState(WriteAccessToSimulationObject guard) {
		if (checkGuard(guard))
			return this.state;
		else
			return null;
		
	}
	
	final void setState(StateSimulationObject state, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.state = state;
			assignState(state);
		}
	}

	protected abstract void assignState(StateSimulationObject state);

	protected final boolean checkIsMyState(StateSimulationObject state) {
		return (state == this.state);
	}
	
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
	
	
	final boolean checkGuard(WriteAccessToSimulationObject guard) {
		return (this.guard == guard);
	}
	
	void setObjectID(int objectID, WriteAccessToSimulationObject guard) {
		if (this.guard == guard ) this.objectID = objectID;	
	}


	public void setAction(AbstractAction newAction, WriteAccessToSimulationObject guard) {

		if (this.guard == guard) {
			if (Simulation.WITH_LOGGING == 1 ) logger.debug("Mensch " + objectID + " trägt Aktion " + newAction.toString() + " in Liste ein");
			actionHandler.insertAction(newAction);
		}
	}

	
	public void addAction(AbstractAction newAction) {
		actionHandler.insertAction(newAction);
	}
	
	/**
	 * The method lets a simulation object do an action.
	 * 
	 * @param action
	 */
	public void doAction(AbstractAction action) {
		action.setActor(this, guard.getMeHidden(grantAccessToAllProperties));
		action.perform();
		action.removeWriteAccess();
	}

	
	
	/**
	 * The method determines the influence of an event. It calculates how the
	 * event changes the attributes of the simulation object.
	 * 
	 * @param simualationEvent -
	 *            the event influencing the simulation object
	 */
	public void changeByEvent(final Event simualationEvent) {
		this.state.calculateEventInfluence(simualationEvent);
	}

	/**
	 * The method determines the reaction to an event.
	 * It creates an action object and inserts it into the action handler list.
	 * 
	 * @param simualationEvent -
	 *            the event influencing the simulation object
	 */
	public abstract void reactToEvent(final Event simualationEvent);

	/**
	 * The method returns the object's position.
	 * 
	 * @return position
	 */
	public Position getPosition() {
		return this.state.getPosition();
	}

	
	

	/**
	 * The method returns the reference to object's action handler.
	 * 
	 * @return actionHandler
	 */
	public ActionHandler getActionHandler() {
		return null;
	}
	
	
	public int getReactionType(int eventType) {
	 return this.state.getReactionType(eventType);
	} 

	public int getInfluenceType(int eventType) {
		 return this.state.getInfluenceType(eventType);
		} 

	public int getState2ActionType() {
		 return this.state.getState2ActionType();
		} 

	public int getObjectID() {
		return objectID;
	}
	
	public String toString() {
		return "" + objectID;
	}
}
