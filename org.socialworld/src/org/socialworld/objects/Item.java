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
	public void use(Action action, SimulationObject user) {
		ActionMode mode = action.getMode();

		switch (mode) {
		case examineItem:
			examineItem(action, user);
			break;
		case takeItem:
			takeItem(action, user);
			break;
		case useExternItem:
			useInternItem(action, user);
			break;
		case useInternItem:
			useInternItem(action, user);
			break;
		case collectItem:
			collectItem(action, user);
			break;
		case switchItemToLeftHand:
			switchItemToLeftHand(action, user);
			break;
		case useTwoItems:
			useTwoItems(action, user);
			break;
		case dropItem:
			dropItem(action, user);
			break;
		default:
		}
	}

	private void examineItem(Action action, SimulationObject user) {

	}

	private void takeItem(Action action, SimulationObject user) {

	}

	private void useInternItem(Action action, SimulationObject user) {

	}

	private void collectItem(Action action, SimulationObject user) {

	}

	private void switchItemToLeftHand(Action action, SimulationObject user) {

	}

	private void useTwoItems(Action action, SimulationObject user) {

	}

	private void dropItem(Action action, SimulationObject user) {

	}

	@Override
	public void doAction(Action action) {

	}
}
