/**
 * 
 */
package org.socialworld.attributes;

import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.Weapon;

/**
 * @author Mathias Sikos (tyloesand) The class collects all informations about a
 *         simulation object's inventory. There are set methods for manipulating
 *         the inventory and get methods for access to object's items.
 */
public class Inventory {

	protected SimulationObject leftHand;
	protected SimulationObject rightHand;

	public Inventory() {

	}

	/**
	 * @return the leftHand
	 */
	public SimulationObject getLeftHand() {
		return this.leftHand;
	}

	/**
	 * @param leftHand
	 *            the leftHand to set
	 */
	public void setLeftHand(final SimulationObject leftHand) {
		this.leftHand = leftHand;
	}

	/**
	 * @return the rightHand
	 */
	public SimulationObject getRightHand() {
		return this.rightHand;
	}

	/**
	 * @param rightHand
	 *            the rightHand to set
	 */
	public void setRightHand(final SimulationObject rightHand) {
		this.rightHand = rightHand;
	}

	/**
	 * The method returns the left hand item iff it is an instance of Weapon
	 * 
	 * @return the leftHand
	 */
	public SimulationObject getLeftHandWeapon() {
		if (this.leftHand instanceof Weapon) {
			return this.leftHand;
		} else {
			return null;
		}
	}

	/**
	 * The method returns the right hand item if it is an instance of Weapon
	 * 
	 * @return the rightHand
	 */
	public SimulationObject getRightHandWeapon() {
		if (this.rightHand instanceof Weapon) {
			return this.rightHand;
		} else {
			return null;
		}
	}

}
