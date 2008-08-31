/**
 * 
 */
package org.socialworld.objects;


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

	protected	long 			objectID;
	
	protected 	Position 		position;

	protected 	Event 			releasedEvent;

	protected 	ActionHandler 	actionHandler;

	protected	int				influenceTypeByEventType[];

	// TUChar mu_reactionType_event[256];
	// STR_AttributeCalculatorMatrix* mSTRa_attributeCalculatorMatrix;

	/**
	 * The constructor creates an simulation object.
	 * 
	 */
	public SimulationObject() {
		loadInfluenceType();
	}

	/**
	 * The method lets an simulation object do an action.
	 * 
	 * @param action
	 */
	public abstract void doAction(Action action);

	/**
	 * The method returns the reference to the event released by the object.
	 * 
	 * @return releasedEvent
	 */
	public Event getEvent() {
		return null;
	}

	/**
	 * The method sets the event released by the object.
	 * 
	 * @param simualationEvent
	 */
	public void setEvent(final Event simualationEvent) {
	}

	/**
	 * The method deletes the reference of the event released by the object.
	 */
	public void deleteEvent() {
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
	 * The method returns the object's position.
	 * 
	 * @return position
	 */
	public Position getPosition() {
		return null;
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

	}

	/**
	 * The method sets the z value of object's position.
	 * 
	 * @param posZ
	 */
	public void setPositionZ(final double posZ) {

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
}
