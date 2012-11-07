/**
 * 
 */
package org.socialworld.objects;


import org.apache.log4j.Logger;
import org.socialworld.Model;
import org.socialworld.attributes.Position;
import org.socialworld.core.Action;
import org.socialworld.core.EventCreator;
import org.socialworld.core.ActionCreator;
import org.socialworld.core.ActionHandler;
import org.socialworld.core.Event;
import org.socialworld.core.Simulation;
import org.socialworld.calculation.AttributeCalculator;

/**
 * Every simulation object (actor in the simulation) is inherited by the abstract class SimulationObject.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public abstract class SimulationObject extends Model {
	protected static final Logger logger = Logger.getLogger(SimulationObject.class);

	protected	WriteAccessToSimulationObject guard;
	private		long 			objectID;
	protected   Simulation  	simulation;
	
	protected 	Position 		position;


	protected 	ActionHandler 	actionHandler;
	protected 	EventCreator 	eventCreator;
	protected 	ActionCreator 	actionCreator;
	protected	AttributeCalculator attributeCalculator;
	
	protected	int				influenceTypeByEventType[];
	protected	int				reactionTypeByEventType[];
	protected   int				state2ActionType;
	
	public static final int MAX_EVENT_INFLUENCE_TYPE = 256;

	/**
	 * The constructor creates a simulation object.
	 * 
	 */
	public SimulationObject() {
		this.guard = null;
		
		this.simulation = Simulation.getInstance();
		
		this.actionHandler = new ActionHandler(this);
		this.eventCreator = new EventCreator();
		this.actionCreator = new ActionCreator();
		this.attributeCalculator = new AttributeCalculator();

		this.position = new Position( 0,0,0);
		
		loadInfluenceType();
		loadReactionType();

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
	
	void setObjectID(long objectID, WriteAccessToSimulationObject guard) {
		if (this.guard == guard ) this.objectID = objectID;	
	}

	void setPosition (Position pos, WriteAccessToSimulationObject guard) {
		if (this.guard == guard) this.position = pos;
	}

	public void setAction(Action newAction, WriteAccessToSimulationObject guard) {

		if (this.guard == guard) {
			logger.debug("Mensch " + objectID + " trägt Aktion " + newAction.toString() + " in Liste ein");
			actionHandler.insertAction(newAction);
		}
	}

	/**
	 * The method lets a simulation object do an action.
	 * 
	 * @param action
	 */
	public abstract void doAction(Action action);


	/**
	 * The method creates an event for a simulation object's action.
	 * 
	 * @param action
	 */
	public Event mapActionToEvent(Action action) {
		Event event;
		event = eventCreator.createEvent(this, action);
		return event;
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
		return new Position(position);
	}

	
	
	/**
	 * The method sets the x value of object's position.
	 * 
	 * @param posX
	 */
	public void setPositionX(final double posX) {
		//double oldPosX = this.position.getX();
		this.position.setX(posX);
		//firePropertyChange("posX", oldPosX, posX);
	}

	/**
	 * The method sets the y value of object's position.
	 * 
	 * @param posY
	 */
	public void setPositionY(final double posY) {
		this.position.setX(posY);

	}

	/**
	 * The method sets the z value of object's position.
	 * 
	 * @param posZ
	 */
	public void setPositionZ(final double posZ) {
		this.position.setZ(posZ);

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
	private void loadInfluenceType() {
		
	}

	/**
	 * The method loads the array of reaction types for all event types.
	 */
	private void loadReactionType() {
		
	}
	
	public int getReactionType(int eventType) {
	 return reactionTypeByEventType[eventType];
	} 

	public int getState2ActionType() {
		 return state2ActionType;
		} 

	public long getObjectID() {
		return objectID;
	}
	
	public String toString() {
		return "" + objectID;
	}
}
