package org.socialworld.objects;

import org.socialworld.core.Action;

public class Item extends SimulationObject {

	public Item() {
		super();
	}

	@Override
	public void determineInfluence(SimulationEvent simualationEvent) {

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
	public void handle(Action action, SimulationObject user) {
		ActionMode mode = action.getMode();

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

	private void use(Action action, SimulationObject user) {

	}

	private void examine(Action action, SimulationObject user) {

	}

	private void take(Action action, SimulationObject user) {

	}

	private void collect(Action action, SimulationObject user) {

	}

	private void switchItemToLeftHand(Action action, SimulationObject user) {

	}

	private void useTwoItems(Action action, SimulationObject user) {

	}

	private void drop(Action action, SimulationObject user) {

	}

	@Override
	public void doAction(Action action) {

	}
}
