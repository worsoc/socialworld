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
import org.socialworld.core.Event;
import org.socialworld.calculation.FunctionByMatrix_Matrix;
import org.socialworld.calculation.application.ActionCreator;
import org.socialworld.calculation.application.AttributeCalculator;


/**
 * An animal is a simulation object with extensions to express the living kind.
 * There are methods for action handling and event effects.
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Animal extends SimulationObject {

	protected Move move;

	protected AttributeArray attributes;
	protected FunctionByMatrix_Matrix attributeCalculatorMatrix;
	
	public Animal() {
		super();
		attributes = new AttributeArray(Attribute.NUMBER_OF_ATTRIBUTES);
	}


	void setAttributes(AttributeArray array, WriteAccessToAnimal guard) {
		if (this.guard == guard ) attributes = array;
	}
	
	
	public AttributeArray getAttributes() {
		if (attributes == null) return null;
		return new AttributeArray(attributes);
	}
	
	void setMatrix(FunctionByMatrix_Matrix matrix,  WriteAccessToAnimal guard) {
		if (this.guard == guard ) attributeCalculatorMatrix  = matrix;
	}
	
	public FunctionByMatrix_Matrix getMatrix() {
		return new FunctionByMatrix_Matrix(attributeCalculatorMatrix, Attribute.NUMBER_OF_ATTRIBUTES);
	}
	
	/**
	 * Depending on the action type the method calls the according procedure
	 * with special implementation of the action.
	 */
	@Override
	protected void doAction(final ActionType type, final Action action) {

		
		switch (type) {
		case sleep:
			 sleep(action);
			break;
		case changeMove:
			 changeMove(action);
			break;
		case kick:
			 kick(action);
			break;
		case move:
			 move(action);
			break;
		case say:
			break;
		default:
			break;
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
		action.lowerRemainedDuration(1000);
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
		AttributeArray tmp;
		
		tmp = AttributeCalculator.getAttributesChangedByEvent(event, this);
		this.attributes.set(tmp);
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

		reaction =	ActionCreator.createAction(	this, event);
		
		actionHandler.insertAction(reaction);
		
	}
	

}
