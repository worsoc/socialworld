/*
* Social World
* Copyright (C) 2014  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.attributes;

import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.Weapon;

/**
 * The class collects all informations about a
 *         simulation object's inventory. There are set methods for manipulating
 *         the inventory and get methods for access to object's items.

 * @author Mathias Sikos (tyloesand)
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
