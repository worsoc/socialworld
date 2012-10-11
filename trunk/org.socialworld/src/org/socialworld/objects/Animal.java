/**
 * 
 */
package org.socialworld.objects;

import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.Move;
import org.socialworld.core.Action;
import org.socialworld.core.ActionCreator;
import org.socialworld.core.Event;
import org.socialworld.core.SemaphoreReturnCode;
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

	protected AttributeCalculatorMatrix attributeCalculatorMatrix;
	
	public Animal() {
		super();
	}

	public void setAttribute(int attributeIndex, byte attributeValue) {
		attributes.set(attributeIndex, attributeValue);
	}

	public void setAttribute(Attribute attributeName, byte attributeValue) {
		attributes.set(attributeName, attributeValue);
	}

	public void setAttributes(AttributeArray array) {
		attributes = array;
	}
	
	
	public AttributeArray getAttributes() {
		return attributes;
	}
	
	public void setMatrix(AttributeCalculatorMatrix matrix) {
		attributeCalculatorMatrix  = matrix;
	}
	
	public AttributeCalculatorMatrix getMatrix() {
		return attributeCalculatorMatrix;
	}
	/**
	 * Depending on the action type the method calls the according procedure
	 * with special implementation of the action.
	 */
	@Override
	public void doAction(final Action action) {

		ActionType type;
		type = action.getType();

		switch (type) {
		case sleep:
			sleep(action);
			return;
		case changeMove:
			changeMove(action);
			return;
		case kick:
			kick(action);
			return;
		case move:
			move(action);
			return;
		case say:
		default:
			return;
		}
	}

	/**
	 * The method changes the move mode of an animal.
	 * 
	 * @param action
	 */
	protected void changeMove(final Action action) {
		this.move.setMode(action.getMode());
	}

	/**
	 * The method holds the implementation of moving an animal.
	 * 
	 * @param action
	 */
	protected void move(final Action action) {
	}

	/**
	 * The method holds the implementation of kicking.
	 * 
	 * @param action
	 */
	protected void kick(final Action action) {
	}

	/**
	 * The method holds the implementation of sleep.
	 * 
	 * @param action
	 */
	protected void sleep(final Action action) {
		final ActionMode mode = action.getMode();

		switch (mode) {
		case sleepIntentioned:
			break;
		case sleepCaused:
			break;
		default:
		}
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
		SemaphoreReturnCode returnCode;
		EventInfluenceDescription eventInfluenceDescription;
		int eventType;
		int eventInfluenceType;
		
		eventType = event.getEventType();
		eventInfluenceType = this.influenceTypeByEventType[eventType];
		
		eventInfluenceDescription = 
			EventInfluenceAssignment.getInstance().getEventInfluenceDescription(
				eventType, eventInfluenceType	);
		
		returnCode = AttributeCalculator.getInstance().lockBy(this);
		if ( returnCode == 	SemaphoreReturnCode.success ||
			 returnCode == 	SemaphoreReturnCode.lockedByUser  ) {
			AttributeCalculator.getInstance().setAttributes(
					this.attributes, this);
			AttributeCalculator.getInstance().changeAttributes(
				eventInfluenceDescription, this);
			AttributeCalculator.getInstance().fetchAttributes(
					this.attributes, this);
			returnCode = AttributeCalculator.getInstance().releaseBy(this);
		}
		else {
		// TODO (tyloesand) Fehlerfall
		}	
	}

	/**
	 * The method lets calculate how an object reacts to an event.
	 * 
	 * @param event
	 *            the event that the object reacts to
	 */
	public void reactToEvent(final Event event) {
		SemaphoreReturnCode returnCode;
		returnCode = ActionCreator.getInstance().lockBy(this);
		if ( returnCode == 	SemaphoreReturnCode.success ||
			 returnCode == 	SemaphoreReturnCode.lockedByUser  ) {
			ActionCreator.getInstance().createAction(
				this, event, this);
			returnCode = ActionCreator.getInstance().releaseBy(this);
		}
		else {
		// TODO (tyloesand) Fehlerfall
		}	
		
	}
	

}
