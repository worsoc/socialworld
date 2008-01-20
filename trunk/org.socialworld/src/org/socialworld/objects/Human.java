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
	@Override
	public void doAction(final Action action) {

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

	protected void handleItem(final Action action) {

	}

	protected void say(final Action action) {

	}

	protected void useWeaponRight(final Action action) {
		final SimulationObject rightHand = this.inventory.getRightHand();
		if (rightHand != null) {
			final Weapon weapon = (Weapon) rightHand;
			weapon.handle(action, this);
		}
	}

	protected void useWeaponLeft(final Action action) {
		final SimulationObject leftHand = this.inventory.getLeftHand();
		if (leftHand != null) {
			final Weapon weapon = (Weapon) leftHand;
			weapon.handle(action, this);
		}
	}

	protected void spell(final Action action) {

	}

	protected void controlHandManually(final Action action) {

	}

	protected void touch(final Action action) {

	}

	/**
	 * @return the inventory
	 */
	public Inventory getInventory() {
		return this.inventory;
	}

	/**
	 * @param inventory
	 *            the inventory to set
	 */
	public void setInventory(final Inventory inventory) {
		this.inventory = inventory;
	}
}
