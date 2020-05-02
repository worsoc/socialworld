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
package org.socialworld.actions.handle;

import org.socialworld.attributes.SimProperty;
import org.socialworld.calculation.Type;
import org.socialworld.core.ObjectMaster;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.SimulationObject_Type;
import org.socialworld.objects.properties.IWeapon;

/**
 * The class collects all informations about a
 *         simulation object's inventory. There are set methods for manipulating
 *         the inventory and get methods for access to object's items.

 * @author Mathias Sikos (tyloesand)
 */
public class Inventory extends SimProperty {

	
	protected SimulationObject leftHand;
	protected SimulationObject rightHand;
	protected SimulationObject mouth;

	private int leftHandID;
	private int rightHandID;
	private int mouthID;
	
	private boolean complete;
	private boolean isHumanInventory;
	
	public Inventory(SimulationObject_Type objectType) {
		this.complete = true;
		this.isHumanInventory = false;
		
		if (objectType  == SimulationObject_Type.human ) {
			this.isHumanInventory = true;
			this.complete = false;
		}
		
		if (objectType  == SimulationObject_Type.animal ) {
			this.complete = false;
		}

	}

	public Inventory(Inventory inventory) {
		//TODO implementcopy constructor
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISimProperty  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	protected SimProperty copyForProperty(Type propertyType) {
		return new Inventory(this);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    Inventory  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	
	public void complete() {
		if (!complete) {
			
// TODO getting the objects	according to the ids	
			
			if (leftHandID > 0) {
				leftHand = ObjectMaster.getInstance().getSimulationObject(leftHandID);
			}
			if (rightHandID > 0) {
				rightHand = ObjectMaster.getInstance().getSimulationObject(rightHandID);
			}
			if (mouthID > 0) {
				mouth = ObjectMaster.getInstance().getSimulationObject(mouthID);
			}
			
			complete = true;
		}
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
	 * @return the mouth item
	 */
	public SimulationObject getMouth() {
		return this.mouth;
	}
	
	
	/**
	 * The method returns the left hand item iff it is an instance of IWeapon
	 * 
	 * @return the leftHand
	 */
	public IWeapon getLeftHandWeapon() {
		if (this.leftHand instanceof IWeapon) {
			return (IWeapon) this.leftHand;
		} else {
			return null;
		}
	}

	/**
	 * The method returns the right hand item if it is an instance of IWeapon
	 * 
	 * @return the rightHand
	 */
	public IWeapon getRightHandWeapon() {
		if (this.rightHand instanceof IWeapon) {
			return (IWeapon) this.rightHand;
		} else {
			return null;
		}
	}

	/**
	 * @param leftHandID
	 *            the leftHandID to set
	 */
	public void setLeftHandID(int  leftHandID) {
		if (!complete) this.leftHandID = leftHandID;
	}
	
	/**
	 * @param rightHandID
	 *            the rightHandID to set
	 */
	public void setRightHandID(int  rightHandID) {
		if (!complete) this.rightHandID = rightHandID;
	}
		
}
