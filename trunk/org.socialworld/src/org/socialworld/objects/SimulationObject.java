/**
 * 
 */
package org.socialworld.objects;

import org.socialworld.core.ActionHandler;

/**
 * @author Mathias Sikos
 * 
 */
public abstract class SimulationObject {

	private long objectNumber;
	// PSBWS_Object_Manager* mP_objectManager;

	protected Position position;

	protected SimulationEvent releasedEvent;

	// ************* ACTION HANDLING
	// *********************************************/
	protected ActionHandler actionHandler;

	// /************* ATTRIBUTE HANDLING
	// ******************************************/
	// TUChar mu_effectType_event[256];
	// TUChar mu_reactionType_event[256];
	// STR_AttributeCalculatorMatrix* mSTRa_attributeCalculatorMatrix;

	/**
	 * the constructor creates an simulation object with identification number.
	 * 
	 * @param objectNumber
	 *            ... identification number
	 */
	public SimulationObject(long objectNumber) {
		this.objectNumber = objectNumber;
	}

	/**
	 * the method returns the object's identification number
	 * 
	 * @return objectNumber
	 */
	public long getObjectNumber() {
		return objectNumber;
	}

	/**
	 * the method returns the reference to the event released by the object
	 * 
	 * @return releasedEvent
	 */
	public SimulationEvent getEvent() {
		return null;
	}

	/**
	 * the method sets the event released by the object.
	 * 
	 * @param simualationEvent
	 */
	public void setEvent(SimulationEvent simualationEvent) {
	}

	/**
	 * the method deletes the reference of the event released by the object.
	 */
	public void deleteEvent() {
	}

	/**
	 * the method determines the influence of an event. It calculates how the
	 * event changes the attributes of the simulation object.
	 * 
	 * @param simualationEvent
	 *            ... the event influencing the simulation object
	 */
	public abstract void determineInfluence(SimulationEvent simualationEvent);

	/**
	 * the method returns the object's position
	 * 
	 * @return position
	 */
	public Position getPosition() {
		return null;
	}

	/**
	 * the method sets the x value of object's position
	 * 
	 * @param posX
	 */
	public void setPositionX(double posX) {

	}

	/**
	 * the method sets the y value of object's position
	 * 
	 * @param posY
	 */
	public void setPositionY(double posY) {

	}

	/**
	 * the method sets the z value of object's position
	 * 
	 * @param posZ
	 */
	public void setPositionZ(double posZ) {

	}

	/**
	 * the method returns the reference to object's action handler
	 * @return actionHandler
	 */
	public ActionHandler getActionHandler() {
		return null;
	}

}
