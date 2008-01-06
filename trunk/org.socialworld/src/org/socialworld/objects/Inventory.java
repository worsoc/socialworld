/**
 * 
 */
package org.socialworld.objects;

/**
 * @author Mathias Sikos (tyloesand)
 * 
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
		return leftHand;
	}

	/**
	 * @param leftHand
	 *            the leftHand to set
	 */
	public void setLeftHand(SimulationObject leftHand) {
		this.leftHand = leftHand;
	}

	/**
	 * @return the rightHand
	 */
	public SimulationObject getRightHand() {
		return rightHand;
	}

	/**
	 * @param rightHand
	 *            the rightHand to set
	 */
	public void setRightHand(SimulationObject rightHand) {
		this.rightHand = rightHand;
	}

	/**
	 * The method returns the left hand item iff it is an instance of Weapon
	 * 
	 * @return the leftHand
	 */
	public SimulationObject getLeftHandWeapon() {
		if (leftHand instanceof Weapon)
			return leftHand;
		else
			return null;
	}

	/**
	 * The method returns the right hand item iff it is an instance of Weapon
	 * 
	 * @return the rightHand
	 */
	public SimulationObject getRightHandWeapon() {
		if (rightHand instanceof Weapon)
			return rightHand;
		else
			return null;
	}

}
