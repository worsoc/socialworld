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
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.IAccessToken;
import org.socialworld.core.ObjectMaster;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.SimulationObject_Type;
import org.socialworld.objects.concrete.clothes.Cap;
import org.socialworld.objects.concrete.clothes.Shirt;
import org.socialworld.objects.concrete.clothes.Shoe;
import org.socialworld.objects.concrete.clothes.Sock;
import org.socialworld.objects.concrete.clothes.Trousers;
import org.socialworld.objects.concrete.clothes.caps.TestCap;
import org.socialworld.objects.concrete.clothes.shirts.TestShirt;
import org.socialworld.objects.concrete.clothes.shoes.TestShoeLeft;
import org.socialworld.objects.concrete.clothes.shoes.TestShoeRight;
import org.socialworld.objects.concrete.clothes.socks.TestSockLeft;
import org.socialworld.objects.concrete.clothes.socks.TestSockRight;
import org.socialworld.objects.properties.IWeapon;
import org.socialworld.tools.StringTupel;

/**
 * The class collects all informations about a
 *         simulation object's inventory. There are set methods for manipulating
 *         the inventory and get methods for access to object's items.

 * @author Mathias Sikos (MatWorsoc)
 */
public class Inventory extends SimProperty {

///////////////////////////////////////////////////////////////////////////////////////////
////////////////// static method for creating test instances   ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public static Inventory getTestInventory(SimulationObject_Type objectType) {
		Inventory inventory = new Inventory(objectType);
		inventory.cap = new TestCap();
		inventory.shirt = new TestShirt();
		inventory.leftFootShoe = new TestShoeLeft();
		inventory.rightFootShoe = new TestShoeRight();
		inventory.leftFootSock = new TestSockLeft();
		inventory.rightFootSock = new TestSockRight();
		return inventory;
	}

///////////////////////////////////////////////////////////////////////////////////////////
////////////////// inventory properties   ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	protected SimulationObject leftHand;
	protected SimulationObject rightHand;
	protected SimulationObject mouth;

	protected Shirt shirt;
	protected Trousers trousers;
	protected Shoe leftFootShoe;
	protected Shoe rightFootShoe;
	protected Sock leftFootSock;
	protected Sock rightFootSock;
	protected Cap cap;

	private int leftHandID;
	private int rightHandID;
	private int mouthID;

	private int shirtID;
	private int trousersID;
	private int leftShoeID;
	private int rightShoeID;
	private int leftSockID;
	private int rightSockID;
	private int capID;
	
	private boolean complete;
	private boolean isHumanInventory;
	
	private SimulationObject parent = null;
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////  static instance for meta information    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
			new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_shirt.name(), PropertyName.inventory_shirt.toString()}),
			new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_trousers.name(), PropertyName.inventory_trousers.toString()}),
			new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_leftShoe.name(), PropertyName.inventory_leftShoe.toString()}),
			new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_rightShoe.name(), PropertyName.inventory_rightShoe.toString()}),
			new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_leftSock.name(), PropertyName.inventory_leftSock.toString()}),
			new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_rightSock.name(), PropertyName.inventory_rightSock.toString()}),
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
///////////// object nothing (abstract method from ISimProperty)    ///////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static Inventory objectNothing;
	
	public static Inventory getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new Inventory();
			objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}

	private Inventory() {
	
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


	private Inventory(Inventory original, PropertyProtection protectionOriginal, IAccessToken token) {
		super(protectionOriginal, token);
		this.cap = original.cap;
		this.leftFootShoe = original.leftFootShoe;
		this.rightFootShoe = original.rightFootShoe;
		this.leftFootSock = original.leftFootSock;
		this.rightFootSock = original.rightFootSock;
		this.leftHand = original.leftHand;
		this.rightHand = original.rightHand;
		this.mouth = original.mouth;
		this.shirt = original.shirt;
		this.trousers = original.trousers;
	}
	
	public void setParentObject(SimulationObject parent) {
		this.parent = parent;
	}
	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValue  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public SimProperty copyForProperty(IAccessToken token) {
		return new Inventory(this, getPropertyProtection(), token);
	}
	
	public  ValueProperty getProperty(IAccessToken token, PropertyName prop, String valueName) {
		switch (prop) {
  		case inventory_shirt:
			return new ValueProperty(Type.simulationObject, valueName, this.shirt);
		case inventory_trousers:
			return new ValueProperty(Type.simulationObject, valueName, this.trousers);
		case inventory_leftShoe:
			return new ValueProperty(Type.simulationObject, valueName, this.leftFootShoe);
		case inventory_rightShoe:
			return new ValueProperty(Type.simulationObject, valueName, this.rightFootShoe);
		case inventory_leftSock:
			return new ValueProperty(Type.simulationObject, valueName, this.leftFootSock);
		case inventory_rightSock:
			return new ValueProperty(Type.simulationObject, valueName, this.rightFootSock);
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
				setLeftHand(ObjectMaster.getInstance().getSimulationObject(leftHandID));
			}
			if (rightHandID > 0) {
				setRightHand(ObjectMaster.getInstance().getSimulationObject(rightHandID));
			}
			if (mouthID > 0) {
				setMouth(ObjectMaster.getInstance().getSimulationObject(mouthID));
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
			return IWeapon.getObjectNothing();
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
			return IWeapon.getObjectNothing();
		}
	}

	
	public void setShirt(final Shirt shirt) {
		this.shirt = shirt;
	}

	public void setTrousers(final Trousers trousers) {
		this.trousers = trousers;
	}

	public void setShoes(final Shoe leftShoe, final Shoe rightShoe) {
		this.leftFootShoe = leftShoe;
		this.rightFootShoe = rightShoe;
	}

	public void setLeftShoe(final Shoe shoe) {
		this.leftFootShoe = shoe;
	}

	public void setRightShoe(final Shoe shoe) {
		this.rightFootShoe = shoe;
	}

	public void setSocks(final Sock leftSock, final Sock rightSock) {
		this.leftFootSock = leftSock;
		this.rightFootSock = rightSock;
	}

	public void setLeftSock(final Sock sock) {
		this.leftFootSock = sock;
	}

	public void setRightSock(final Sock sock) {
		this.rightFootSock = sock;
	}

	public void setCap(final Cap cap) {
		this.cap = cap;
	}

	/**
	 * @param mouth
	 *            the mouth input (eating, drinking...) to set
	 */
	public void setMouth(final SimulationObject mouth) {
		this.mouth = mouth;
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
