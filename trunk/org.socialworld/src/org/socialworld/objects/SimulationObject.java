/**
 * 
 */
package org.socialworld.objects;

import org.socialworld.core.Action;
import org.socialworld.core.ActionHandler;
import org.socialworld.core.ObjectManager;

/**
 * Every simulatable object is inherited by the abstract class SimulationObject.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public abstract class SimulationObject {

	protected ObjectManager objectManager;
	protected Position position;

	protected SimulationEvent releasedEvent;

	protected ActionHandler actionHandler;

	// TUChar mu_effectType_event[256];
	// TUChar mu_reactionType_event[256];
	// STR_AttributeCalculatorMatrix* mSTRa_attributeCalculatorMatrix;

	/**
	 * The constructor creates an simulation object.
	 * 
	 */
	public SimulationObject() {
		objectManager = ObjectManager.getObjectManager();
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
	public SimulationEvent getEvent() {
		return null;
	}

	/**
	 * The method sets the event released by the object.
	 * 
	 * @param simualationEvent
	 */
	public void setEvent(SimulationEvent simualationEvent) {
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
	public abstract void determineInfluence(SimulationEvent simualationEvent);

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
	public void setPositionX(double posX) {

	}

	/**
	 * The method sets the y value of object's position.
	 * 
	 * @param posY
	 */
	public void setPositionY(double posY) {

	}

	/**
	 * The method sets the z value of object's position.
	 * 
	 * @param posZ
	 */
	public void setPositionZ(double posZ) {

	}

	/**
	 * The method returns the reference to object's action handler.
	 * 
	 * @return actionHandler
	 */
	public ActionHandler getActionHandler() {
		return null;
	}
}
