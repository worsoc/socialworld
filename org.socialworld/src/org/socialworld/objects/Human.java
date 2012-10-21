/**
 * 
 */
package org.socialworld.objects;

import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.Inventory;
import org.socialworld.core.Action;

/**
 * A human is described in most details. It is the most important simulation
 * object. The simulation of a human is the main target of the game. A human has
 * an attribute array that describes his inner state. There are values for
 * courage, spirituality and morals for example. There is a detailed
 * differentiation of action handling an event influence. A human is the only
 * simulation object that has an inventory. So it can carry items and use them.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
 public class Human extends Mammal {

	protected Inventory inventory;

	public Human() {
		super();
	}

	/**
	 * @param inventory
	 *            the inventory to set
	 */
	void setInventory(final Inventory inventory, WriteAccessToHuman guard) {
		if (this.guard == guard ) this.inventory = inventory;
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
		if (rightHand != null ) 
			if (rightHand instanceof Weapon){
				final Weapon weapon = (Weapon) rightHand;
				weapon.handle(action, this);
			}
			else if (rightHand instanceof Item) {
				final Item item = (Item) rightHand;
				item.handle(action, this);
			}
	}

	protected void useWeaponLeft(final Action action) {
		final SimulationObject leftHand = this.inventory.getLeftHand();
		if (leftHand != null ) 
			if (leftHand instanceof Weapon){
				final Weapon weapon = (Weapon) leftHand;
				weapon.handle(action, this);
			}
			else if (leftHand instanceof Item) {
				final Item item = (Item) leftHand;
				item.handle(action, this);
			}
	}

	protected void spell(final Action action) {

	}

	protected void controlHandManually(final Action action) {

	}

	protected void touch(final Action action) {

	}



}
