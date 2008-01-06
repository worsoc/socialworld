/**
 * 
 */
package org.socialworld.objects;

import org.socialworld.core.Action;
import org.socialworld.core.ActionType;

/**
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Animal extends SimulationObject {

	protected Move move;

	public Animal() {
		super();
	}

	/**
	 * Depending on the action type the method calls the according procedure
	 * with special implementation of the action.
	 */
	public void doAction(Action action) {

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
	protected void changeMove(Action action) {
		move.setMode(action.getMode());
	}

	/**
	 * The method holds the implementation of moving an animal.
	 * 
	 * @param action
	 */
	protected void move(Action action) {
	}

	/**
	 * The method holds the implementation of kicking.
	 * 
	 * @param action
	 */
	protected void kick(Action action) {
	}

	/**
	 * The method holds the implementation of sleep.
	 * 
	 * @param action
	 */
	protected void sleep(Action action) {
		ActionMode mode = action.getMode();

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
		return move;
	}

	/**
	 * @param Move
	 *            the Move to set
	 */
	public void setMove(Move move) {
		this.move = move;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.socialworld.objects.SimulationObject#determineInfluence(org.socialworld.objects.SimulationEvent)
	 */
	@Override
	public void determineInfluence(SimulationEvent simualationEvent) {

	}

}
