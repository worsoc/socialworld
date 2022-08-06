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

import java.util.List;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.SimProperty;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.ObjectMaster;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.SimulationObject_Type;
import org.socialworld.objects.properties.IWeapon;
import org.socialworld.tools.StringTupel;

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

	protected SimulationObject shirt;
	protected SimulationObject trousers;
	protected SimulationObject shoes;
	protected SimulationObject cap;

	private int leftHandID;
	private int rightHandID;
	private int mouthID;

	private int shirtID;
	private int trousersID;
	private int shoesID;
	private int capID;
	
	private boolean complete;
	private boolean isHumanInventory;
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////  static instance for meta information    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
			new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_shirt.name(), PropertyName.inventory_shirt.toString()}),
			new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_trousers.name(), PropertyName.inventory_trousers.toString()}),
			new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_shoes.name(), PropertyName.inventory_shoes.toString()}),
			new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_cap.name(), PropertyName.inventory_cap.toString()}),
			new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_leftHand.name(), PropertyName.inventory_leftHand.toString()}),
			new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_rightHand.name(), PropertyName.inventory_rightHand.toString()})
			} ;

	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = SimProperty.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}

	private static KnowledgeFact_Criterion[] resultingKFCs = new KnowledgeFact_Criterion[] {
			KnowledgeFact_Criterion.colour,
			KnowledgeFact_Criterion.material
		};

	public static List<KnowledgeFact_Criterion> getResultingKFCs() {
		List<KnowledgeFact_Criterion> listOfResultingKFCs = SimProperty.getResultingKFCs();
		for (int indexAdd = 0; indexAdd < resultingKFCs.length; indexAdd++) {
		listOfResultingKFCs.add(resultingKFCs[indexAdd]);
		}
		return listOfResultingKFCs;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
////////////////// creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
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


	private Inventory(Inventory original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValues  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public SimProperty copyForProperty(SimulationCluster cluster) {
		return new Inventory(this, getPropertyProtection(), cluster);
	}
	
	public  ValueProperty getProperty(SimulationCluster cluster, PropertyName prop, String valueName) {
		switch (prop) {
  		case inventory_shirt:
			return new ValueProperty(Type.simulationObject, valueName, this.shirt);
		case inventory_trousers:
			return new ValueProperty(Type.simulationObject, valueName, this.trousers);
		case inventory_shoes:
			return new ValueProperty(Type.simulationObject, valueName, this.shoes);
		case inventory_cap:
			return new ValueProperty(Type.simulationObject, valueName, this.cap);
		case inventory_leftHand:
			return new ValueProperty(Type.simulationObject, valueName, this.leftHand);
		case inventory_rightHand:
			return new ValueProperty(Type.simulationObject, valueName, this.rightHand);

		default:
			return ValueProperty.getInvalid();
		}

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

	
	public void setShirt(final SimulationObject shirt) {
		this.shirt = shirt;
	}

	public void setTrousers(final SimulationObject trousers) {
		this.trousers = trousers;
	}

	public void setShoes(final SimulationObject shoes) {
		this.shoes = shoes;
	}

	public void setCap(final SimulationObject cap) {
		this.cap = cap;
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
