/**
 * 
 */
package org.socialworld.objects;

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
