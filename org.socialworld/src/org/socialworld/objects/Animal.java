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

/**
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Animal extends SimulationObject {

    protected Move move;

    protected AttributeArray attributes;
    
    public Animal() {
	super();
    }

	public void setAttribute(int attributeIndex, byte attributeValue) {
		attributes.setAttribute(attributeIndex,  attributeValue);
	}

	public void setAttribute(Attribute attributeName, byte attributeValue) {
		attributes.setAttribute(attributeName, attributeValue);
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
     *                the Move to set
     */
    public void setMove(final Move move) {
	this.move = move;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.socialworld.objects.SimulationObject#determineInfluence(org.socialworld.objects.SimulationEvent)
     */
    @Override
    public void determineInfluence(final Event simualationEvent) {

    }

}
