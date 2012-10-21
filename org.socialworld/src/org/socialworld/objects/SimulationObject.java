/**
 * 
 */
package org.socialworld.objects;


import org.apache.log4j.Logger;
import org.socialworld.Model;
import org.socialworld.attributes.Position;
import org.socialworld.core.Action;
import org.socialworld.core.ActionHandler;
import org.socialworld.core.Event;

/**
 * Every simulatable object is inherited by the abstract class SimulationObject.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public abstract class SimulationObject extends Model {
	protected static final Logger logger = Logger.getLogger(SimulationObject.class);

	protected	WriteAccessToSimulationObject guard;
	protected	long 			objectID;
	
	protected 	Position 		position;


	protected 	ActionHandler 	actionHandler;

	protected	int				influenceTypeByEventType[];
	protected	int				reactionTypeByEventType[];

	public static final int MAX_EVENT_INFLUENCE_TYPE = 256;

	/**
	 * The constructor creates an simulation object.
	 * 
	 */
	public SimulationObject() {
		this.guard = null;
		this.actionHandler = new ActionHandler(this);
		this.position = new Position( 0,0,0);
		
		loadInfluenceType();
		loadReactionType();

	}

	void setWriteAccess(WriteAccessToSimulationObject guard) {
		if (this.guard == null)  this.guard = guard;
	}
	
	void setObjectID(long objectID, WriteAccessToSimulationObject guard) {
		if (this.guard == guard ) this.objectID = objectID;	
	}

	void setPosition (Position pos, WriteAccessToSimulationObject guard) {
		if (this.guard == guard) this.position = pos;
	}

	/**
	 * The method lets an simulation object do an action.
	 * 
	 * @param action
	 */
	public abstract void doAction(Action action);


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
		double oldPosX = this.position.getX();
		this.position.setX(posX);
		firePropertyChange("posX", oldPosX, posX);
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
	
	public String toString() {
		return "" + objectID;
	}
}
