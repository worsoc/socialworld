package org.socialworld.objects;

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
	public void use(ActionMode mode, SimulationObject user) {

		switch (mode) {
		case examineItem:
			examineItem(user);
			break;
		case takeItem:
			takeItem(user);
			break;
		case useExternItem:
			useInternItem(user);
			break;
		case useInternItem:
			useInternItem(user);
			break;
		case collectItem:
			collectItem(user);
			break;
		case switchItemToLeftHand:
			switchItemToLeftHand(user);
			break;
		case useTwoItems:
			useTwoItems(user);
			break;
		case dropItem:
			dropItem(user);
			break;
		default:
		}
	}

	private void examineItem(SimulationObject user) {

	}

	private void takeItem(SimulationObject user) {

	}

	private void useInternItem(SimulationObject user) {

	}

	private void collectItem(SimulationObject user) {

	}

	private void switchItemToLeftHand(SimulationObject user) {

	}

	private void useTwoItems(SimulationObject user) {

	}

	private void dropItem(SimulationObject user) {

	}
}
