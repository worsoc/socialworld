package org.socialworld.objects;

import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;
import org.socialworld.core.Action;
import org.socialworld.core.Event;

/**
 * The class Item describes all non-living simulation objects. It has a method
 * for handling an item. That implements what happens (in simulation sense) if
 * an item is used by a (living) simulation object.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Item extends SimulationObject {

	public Item() {
		super();
	}

	@Override
	public void changeByEvent(final Event event) {

	}
	
	public void reactToEvent(final Event event) {
		
	}


	/**
	 * The method implements the use of an item by an user. The kind of use is
	 * specified by an action mode.
	 * 
	 * @param mode
	 *            the mode of using
	 * @param user
	 *            the user
	 */
	public void handle(final Action action, final SimulationObject user) {
		final ActionMode mode = action.getMode();

		switch (mode) {
		case examineItem:
			examine(action, user);
			break;
		case takeItem:
			take(action, user);
			break;
		case useItem:
			use(action, user);
			break;
		case collectItem:
			collect(action, user);
			break;
		case switchItemToLeftHand:
			switchItemToLeftHand(action, user);
			break;
		case useTwoItems:
			useTwoItems(action, user);
			break;
		case dropItem:
			drop(action, user);
			break;
		default:

		}
	}

	private void use(final Action action, final SimulationObject user) {

	}

	private void examine(final Action action, final SimulationObject user) {

	}

	private void take(final Action action, final SimulationObject user) {

	}

	private void collect(final Action action, final SimulationObject user) {

	}

	private void switchItemToLeftHand(final Action action,
			final SimulationObject user) {

	}

	private void useTwoItems(final Action action, final SimulationObject user) {

	}

	private void drop(final Action action, final SimulationObject user) {

	}

	@Override
	protected int doAction(final ActionType type, final Action action) {
		return action.isDone();
	}
}
