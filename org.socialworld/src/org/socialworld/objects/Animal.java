/**
 * 
 */
package org.socialworld.objects;

import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.Move;
import org.socialworld.core.Action;
import org.socialworld.core.Event;
import org.socialworld.calculation.AttributeCalculator;
import org.socialworld.calculation.AttributeCalculatorMatrix;
import org.socialworld.calculation.EventInfluenceDescription;
import org.socialworld.calculation.EventInfluenceAssignment;


/**
 * An animal is a simulation object with extensions to express the living kind.
 * There are methods for action handling and event effects.
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Animal extends SimulationObject {

	protected Move move;

	protected AttributeArray attributes;
	protected AttributeCalculator attributeCalculator;
	protected AttributeCalculatorMatrix attributeCalculatorMatrix;
	
	public Animal() {
		super();
		attributes = new AttributeArray();
		this.attributeCalculator = new AttributeCalculator();
	}


	void setAttributes(AttributeArray array, WriteAccessToAnimal guard) {
		if (this.guard == guard ) attributes = array;
	}
	
	
	public AttributeArray getAttributes() {
		return new AttributeArray(attributes);
	}
	
	void setMatrix(AttributeCalculatorMatrix matrix,  WriteAccessToAnimal guard) {
		if (this.guard == guard ) attributeCalculatorMatrix  = matrix;
	}
	
	public AttributeCalculatorMatrix getMatrix() {
		return new AttributeCalculatorMatrix(attributeCalculatorMatrix);
	}
	
	/**
	 * Depending on the action type the method calls the according procedure
	 * with special implementation of the action.
	 */
	@Override
	protected int doAction(final ActionType type, final Action action) {

		int actionDone = 0;
		
		switch (type) {
		case sleep:
			actionDone = sleep(action);
			break;
		case changeMove:
			actionDone = changeMove(action);
			break;
		case kick:
			actionDone = kick(action);
			break;
		case move:
			actionDone = move(action);
			break;
		case say:
			break;
		default:
			break;
		}
		return actionDone;
	}

	/**
	 * The method changes the move mode of an animal.
	 * 
	 * @param action
	 */
	protected int changeMove(final Action action) {
		this.move.setMode(action.getMode());
		return action.isDone();
	}

	/**
	 * The method holds the implementation of moving an animal.
	 * 
	 * @param action
	 */
	protected int move(final Action action) {
		action.lowerRemainedDuration(1000);
		return action.isDone();
	}

	/**
	 * The method holds the implementation of kicking.
	 * 
	 * @param action
	 */
	protected int kick(final Action action) {
		return action.isDone();
	}

	/**
	 * The method holds the implementation of sleep.
	 * 
	 * @param action
	 */
	protected int sleep(final Action action) {
		final ActionMode mode = action.getMode();

		switch (mode) {
		case sleepIntentioned:
			break;
		case sleepCaused:
			break;
		default:
		}
		return action.isDone();
	}

	/**
	 * @return the Move
	 */
	public Move getMove() {
		return this.move;
	}

	/**
	 * @param Move
	 *            the Move to set
	 */
	public void setMove(final Move move) {
		this.move = move;
	}


	/**
	 * The method lets calculate how an event changes an object's state.
	 * 
	 * @param event
	 *            the event that effects to the object
	 */
	public void changeByEvent(final Event event) {
		EventInfluenceDescription eventInfluenceDescription;
		int eventType;
		int eventInfluenceType;
		
		eventType = event.getEventType();
		eventInfluenceType = this.influenceTypeByEventType[eventType];
		
		eventInfluenceDescription = 
			EventInfluenceAssignment.getInstance().getEventInfluenceDescription(
				eventType, eventInfluenceType	);
		
		attributeCalculator.setAttributes(this.attributes);
		attributeCalculator.changeAttributes(eventInfluenceDescription);
		attributeCalculator.fetchAttributes(this.attributes);
		
		
	}

	/**
	 * The method lets calculate how an object reacts to an event.
	 * 
	 * @param event
	 *            the event that the object reacts to
	 */
	public void reactToEvent(final Event event) {
		Action reaction;

		logger.debug("reactToEvent");

		reaction =	actionCreator.createAction(	this, event);
		
		actionHandler.insertAction(reaction);
		
	}
	

}
