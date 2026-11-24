/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
 * Written with the assistance of Claude (Anthropic)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.actions.handle;

import java.util.EnumMap;
import java.util.List;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.SimProperty;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.core.IAccessToken;
import org.socialworld.core.ObjectMaster;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.SimulationObject_Type;
import org.socialworld.objects.concrete.clothes.Cap;
import org.socialworld.objects.concrete.clothes.Overgarment;
import org.socialworld.objects.concrete.clothes.Shirt;
import org.socialworld.objects.concrete.clothes.Shoe;
import org.socialworld.objects.concrete.clothes.Sock;
import org.socialworld.objects.concrete.clothes.Trousers;
import org.socialworld.objects.properties.ICarryable;
import org.socialworld.objects.properties.IDrinkable;
import org.socialworld.objects.properties.IEatable;
import org.socialworld.objects.properties.IWeapon;
import org.socialworld.objects.properties.IWearable;
import org.socialworld.tools.StringTupel;

/**
 * The class collects all informations about a
 *         simulation object's inventory. There are set methods for manipulating
 *         the inventory and get methods for access to object's items.

 * @author Mathias Sikos (MatWorsoc)
 */
public class Inventory extends SimProperty {

///////////////////////////////////////////////////////////////////////////////////////////
////////////////// inventory properties   ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	// one object per place (inventory concept V1, 2026-11-23; before, one typed field per place).
	// An empty place is not in the map and is answered with NoSimulationObject, no longer with null (F4).
	private final EnumMap<InventoryPlace, SimulationObject> places = new EnumMap<>(InventoryPlace.class);

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
		this.places.putAll(original.places);
	}
	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValue  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public SimProperty copyForProperty(IAccessToken token) {
		return new Inventory(this, getPropertyProtection(), token);
	}

	public  ValueProperty getProperty(IAccessToken token, PropertyName prop, String valueName) {
		InventoryPlace place = placeOf(prop);
		if (place == InventoryPlace.noWhere) return ValueProperty.getInvalid();
		return new ValueProperty(Type.simulationObject, valueName, get(place));
	}

	/**
	 * @return the place of an inventory property name, noWhere for every other property name
	 */
	static InventoryPlace placeOf(PropertyName prop) {
		switch (prop) {
		case inventory_shirt:		return InventoryPlace.upperBody;
		case inventory_trousers:	return InventoryPlace.legs;
		case inventory_cap:			return InventoryPlace.head;
		case inventory_leftShoe:	return InventoryPlace.leftFootShoe;
		case inventory_rightShoe:	return InventoryPlace.rightFootShoe;
		case inventory_leftSock:	return InventoryPlace.leftFootSock;
		case inventory_rightSock:	return InventoryPlace.rightFootSock;
		case inventory_leftHand:	return InventoryPlace.leftHand;
		case inventory_rightHand:	return InventoryPlace.rightHand;
		case inventory_mouth:		return InventoryPlace.mouth;
		default:					return InventoryPlace.noWhere;
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    places  ///////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @return the object on the place, NoSimulationObject if the place is empty
	 */
	public SimulationObject get(InventoryPlace place) {
		SimulationObject object = this.places.get(place);
		return (object != null) ? object : NoSimulationObject.getObjectNothing();
	}

	public boolean isFree(InventoryPlace place) {
		return !this.places.containsKey(place);
	}

	/**
	 * Whether the item may be put on the place now: it fits there (fits) and the place is free.
	 * A dress needs the upper body and the legs free; while a dress is worn, both are taken.
	 */
	public boolean canPut(InventoryPlace place, SimulationObject item) {
		if (!fits(place, item)) return false;
		if (!isFree(place)) return false;
		switch (place) {
		case dress:
			return isFree(InventoryPlace.upperBody) && isFree(InventoryPlace.legs);
		case upperBody:
		case legs:
			return isFree(InventoryPlace.dress);
		default:
			return true;
		}
	}

	/**
	 * Puts the item on the place if canPut allows it.
	 *
	 * @return true if the item is on the place now
	 */
	public boolean put(InventoryPlace place, SimulationObject item) {
		if (!canPut(place, item)) return false;
		this.places.put(place, item);
		return true;
	}

	/**
	 * Takes the object off the place.
	 *
	 * @return the object that was there, NoSimulationObject if the place was empty
	 */
	public SimulationObject remove(InventoryPlace place) {
		SimulationObject object = this.places.remove(place);
		if (object == null) return NoSimulationObject.getObjectNothing();
		return object;
	}

	/**
	 * @return true if the object lies on one of the places
	 */
	public boolean holds(SimulationObject object) {
		return object != null && this.places.containsValue(object);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    carrying  ////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The offset of an item on the place to its carrier, in mm (inventory concept V2, as the offsets at the start
	 * of the simulation). It is fixed in x, y and z and does not turn with the carrier.
	 */
	public static Vector offsetOf(InventoryPlace place) {
		switch (place) {
		case head:				return new Vector(0, 0, 800);
		case upperBody:
		case dress:
		case overgarmentFront:
		case overgarmentArmour:
		case overgarmentCloak:	return new Vector(0, 0, 300);
		case neck:				return new Vector(0, 0, 500);
		case legs:				return new Vector(0, 0, -400);
		case leftFootShoe:
		case leftFootSock:		return new Vector(150, 0, -800);
		case rightFootShoe:
		case rightFootSock:		return new Vector(-150, 0, -800);
		case leftHand:
		case leftHandGlove:
		case finger:			return new Vector(300, 0, 0);
		case rightHand:
		case rightHandGlove:	return new Vector(-300, 0, 0);
		case back:				return new Vector(0, -200, 0);
		default:				return Vector.get0Vector();	// mouth, belt, bag
		}
	}

	// The holder carries what lies on its places (connection carried). Since 2026-11-24 the state of the holder does
	// that, under the guard of its write access (StateAnimal.putToInventory, protection concept S2); the Inventory
	// only keeps the places and no longer changes any connection.

	/**
	 * Whether the item is of a kind that belongs on the place, regardless of whether the place is free:
	 * clothes and jewellery by IWearable (and the overgarment by its own place), the mouth by IEatable or
	 * IDrinkable, hands, belt and back by ICarryable. A liquid is not ICarryable: it is carried in a container.
	 */
	public static boolean fits(InventoryPlace place, SimulationObject item) {
		if (item == null || !item.isSimulationObject()) return false;

		IWearable wearable = (item instanceof IWearable) ? (IWearable) item : null;
		switch (place) {
		case head:				return wearable != null && wearable.isCap();
		case upperBody:			return wearable != null && wearable.isShirt();
		case legs:				return wearable != null && wearable.isTrousers();
		case dress:				return wearable != null && wearable.isDress();
		case overgarmentFront:
		case overgarmentArmour:
		case overgarmentCloak:	return item instanceof Overgarment && ((Overgarment) item).getOvergarmentPlace() == place;
		case leftFootShoe:		return wearable != null && wearable.isLeftFootShoe();
		case rightFootShoe:		return wearable != null && wearable.isRightFootShoe();
		case leftFootSock:		return wearable != null && wearable.isLeftFootSock();
		case rightFootSock:		return wearable != null && wearable.isRightFootSock();
		case leftHandGlove:		return wearable != null && wearable.isLeftHandGlove();
		case rightHandGlove:	return wearable != null && wearable.isRightHandGlove();
		case neck:				return wearable != null && wearable.isNecklace();
		case finger:			return wearable != null && wearable.isRing();
		case bag:				return wearable != null && wearable.isBag();
		case mouth:				return item instanceof IEatable || item instanceof IDrinkable;
		case leftHand:
		case rightHand:
		case belt1:
		case belt2:
		case belt3:
		case back:				return item instanceof ICarryable && ((ICarryable) item).fitsPlace(place);
		default:				return false;
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

	// The setters below are the old way (start of the simulation, loading, StateInventory): they set the place
	// without the checks of put, as before. An object that is no simulation object (null, nothing) empties the place.

	private void set(InventoryPlace place, SimulationObject object) {
		this.places.remove(place);
		if (object == null || !object.isSimulationObject()) return;
		this.places.put(place, object);
	}

	public SimulationObject getLeftHand() {
		return get(InventoryPlace.leftHand);
	}

	public void setLeftHand(final SimulationObject leftHand) {
		set(InventoryPlace.leftHand, leftHand);
	}

	public SimulationObject getRightHand() {
		return get(InventoryPlace.rightHand);
	}

	public void setRightHand(final SimulationObject rightHand) {
		set(InventoryPlace.rightHand, rightHand);
	}

	public SimulationObject getMouth() {
		return get(InventoryPlace.mouth);
	}

	/**
	 * The method returns the left hand item iff it is an instance of IWeapon
	 * 
	 * @return the leftHand
	 */
	public IWeapon getLeftHandWeapon() {
		SimulationObject leftHand = getLeftHand();
		if (leftHand instanceof IWeapon) {
			return (IWeapon) leftHand;
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
		SimulationObject rightHand = getRightHand();
		if (rightHand instanceof IWeapon) {
			return (IWeapon) rightHand;
		} else {
			return IWeapon.getObjectNothing();
		}
	}

	public void setShirt(final Shirt shirt) {
		set(InventoryPlace.upperBody, shirt);
	}

	public void setTrousers(final Trousers trousers) {
		set(InventoryPlace.legs, trousers);
	}

	public void setShoes(final Shoe leftShoe, final Shoe rightShoe) {
		setLeftShoe(leftShoe);
		setRightShoe(rightShoe);
	}

	public void setLeftShoe(final Shoe shoe) {
		set(InventoryPlace.leftFootShoe, shoe);
	}

	public void setRightShoe(final Shoe shoe) {
		set(InventoryPlace.rightFootShoe, shoe);
	}

	public void setSocks(final Sock leftSock, final Sock rightSock) {
		setLeftSock(leftSock);
		setRightSock(rightSock);
	}

	public void setLeftSock(final Sock sock) {
		set(InventoryPlace.leftFootSock, sock);
	}

	public void setRightSock(final Sock sock) {
		set(InventoryPlace.rightFootSock, sock);
	}

	public void setCap(final Cap cap) {
		set(InventoryPlace.head, cap);
	}

	public void setMouth(final SimulationObject mouth) {
		set(InventoryPlace.mouth, mouth);
	}

	public void setLeftHandID(int  leftHandID) {
		if (!complete) this.leftHandID = leftHandID;
	}

	public void setRightHandID(int  rightHandID) {
		if (!complete) this.rightHandID = rightHandID;
	}

}
