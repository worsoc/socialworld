/**
 * 
 */
package org.socialworld.objects;


import org.apache.log4j.Logger;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.Position;
import org.socialworld.core.Action;
import org.socialworld.core.ActionHandler;
import org.socialworld.core.Event;
import org.socialworld.core.Simulation;
import org.socialworld.calculation.Vector;
import org.socialworld.calculation.application.EventCreator;
import org.socialworld.propertyChange.ChangedProperty;
import org.socialworld.propertyChange.ListenedBase;

/**
 * Every simulation object (actor in the simulation) is inherited by the abstract class SimulationObject.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public abstract class SimulationObject extends ListenedBase {
	protected static final Logger logger = Logger.getLogger(SimulationObject.class);

	protected	WriteAccessToSimulationObject guard;
	private		int 			objectID;
	protected   Simulation  	simulation;
	
	private 	Position 		position;


	protected 	ActionHandler 	actionHandler;
	
	protected	int				influenceTypeByEventType[];
	protected	int				reactionTypeByEventType[];
	protected   int				state2ActionType;
	

	/**
	 * The constructor creates a simulation object.
	 * 
	 */
	public SimulationObject() {
		this.guard = null;
		
		this.simulation = Simulation.getInstance();
		
		this.actionHandler = new ActionHandler(this);

		this.position = new Position( new Vector( 0,0,0));
		

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
	void setWriteAccess(WriteAccessToSimulationObject guard) {
		if (this.guard == null)  this.guard = guard;
	}
	
	void setObjectID(int objectID, WriteAccessToSimulationObject guard) {
		if (this.guard == guard ) this.objectID = objectID;	
	}

	void setPosition (Position pos, WriteAccessToSimulationObject guard) {
		if (this.guard == guard) {
			this.position = pos;
			simulation.propertyChanged(this, ChangedProperty.position);
		}
	}

	public void setAction(Action newAction, WriteAccessToSimulationObject guard) {

		if (this.guard == guard) {
			if (Simulation.WITH_LOGGING == 1 ) logger.debug("Mensch " + objectID + " trägt Aktion " + newAction.toString() + " in Liste ein");
			actionHandler.insertAction(newAction);
		}
	}

	void setInfluenceTypes (int types[], WriteAccessToSimulationObject guard) {
		if (this.guard == guard) this.influenceTypeByEventType = types;
	}

	void setReactionTypes (int types[], WriteAccessToSimulationObject guard) {
		if (this.guard == guard) this.reactionTypeByEventType = types;
	}

	void setState2ActionType (int type, WriteAccessToSimulationObject guard) {
		if (this.guard == guard) this.state2ActionType = type;
	}
	
	/**
	 * The method lets a simulation object do an action.
	 * 
	 * @param action
	 */
	public void doAction(Action action) {
		ActionType type;
		Event event;
		boolean actionDone;
		
		type = action.getType();

		doAction(type, action);
		actionDone = action.isDone();
		if (actionDone) {
			event = mapActionToEvent(action);
			simulation.getEventMaster().addEvent(event);
		}
		
	}

	protected abstract void doAction(ActionType type, Action action);
	
	/**
	 * The method creates an event for a simulation object's action.
	 * 
	 * @param action
	 */
	public Event mapActionToEvent(Action action) {
		return EventCreator.createEvent(this, action);
	}
	
	/**
	 * The method determines the influence of an event. It calculates how the
	 * event changes the attributes of the simulation object.
	 * 
	 * @param simualationEvent -
	 *            the event influencing the simulation object
	 */
	public abstract void changeByEvent(final Event simualationEvent);

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
		if (position == null) return null;
		else return new Position(position);
	}

	
	

	/**
	 * The method returns the reference to object's action handler.
	 * 
	 * @return actionHandler
	 */
	public ActionHandler getActionHandler() {
		return null;
	}
	
	/**
	 * The method loads the array of influence types for all event types.
	 */

	/**
	 * The method loads the array of reaction types for all event types.
	 */
	
	public int getReactionType(int eventType) {
	 return reactionTypeByEventType[eventType];
	} 

	public int getInfluenceType(int eventType) {
		 return influenceTypeByEventType[eventType];
		} 

	public int getState2ActionType() {
		 return state2ActionType;
		} 

	public int getObjectID() {
		return objectID;
	}
	
	public String toString() {
		return "" + objectID;
	}
}
