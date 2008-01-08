/**
 * 
 */
package org.socialworld.objects;

import org.socialworld.core.Action;
import org.socialworld.core.ActionType;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Human extends Mammal {

	protected Inventory inventory;

	public Human() {
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
		case touch:
			touch(action);
			return;
		case sleep:
			sleep(action);
			return;
		case changeMove:
			changeMove(action);
			return;
		case kick:
			kick(action);
			return;
		case controlHandManually:
			controlHandManually(action);
			return;
		case spell:
			spell(action);
			return;
		case useWeaponLeft:
			useWeaponLeft(action);
			return;
		case useWeaponRight:
			useWeaponRight(action);
			return;
		case move:
			move(action);
			return;
		case say:
			say(action);
			return;
		case handleItem:
			handleItem(action);
			return;
		default:
			return;
		}
	}

	protected void handleItem(Action action) {

	}

	protected void say(Action action) {

	}

	protected void useWeaponRight(Action action) {
		SimulationObject rightHand = inventory.getRightHand();
		if (rightHand != null) {
			Weapon weapon = (Weapon) rightHand;
			weapon.handle(action, this);
		}
	}

	protected void useWeaponLeft(Action action) {
		SimulationObject leftHand = inventory.getLeftHand();
		if (leftHand != null) {
			Weapon weapon = (Weapon) leftHand;
			weapon.handle(action, this);
		}
	}

	protected void spell(Action action) {

	}

	protected void controlHandManually(Action action) {

	}

	protected void touch(Action action) {

	}


	/**
	 * @return the inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * @param inventory
	 *            the inventory to set
	 */
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

}
