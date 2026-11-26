/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
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
package org.socialworld.calculation.application;

import org.socialworld.objects.properties.IEatable;
import org.socialworld.core.ObjectMaster;
import org.socialworld.GlobalSwitches;
import org.socialworld.actions.handle.ContainerContent;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.attributes.Position;
import org.socialworld.calculation.IObjectReceiver;
import org.socialworld.calculation.ObjectRequester;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.Event;
import org.socialworld.core.IAccessToken;
import org.socialworld.core.EventType;
import org.socialworld.core.IEventParam;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.access.HiddenAnimal;

/**
 * Changes the own inventory by the events of taking and dropping (inventory concept V4, 2026-11-23).
 * The action (ActionEquip) only sends the event; this calculator applies it with the write access of the
 * holder (grant inventory). Putting on a place lets the holder carry the item (connection carried), taking it
 * off releases it. It works at once, in the thread of the event, not in an own thread.
 *
 * takeItem: the item is free (carried by nobody), at most 1 m away from the holder (on the ground, x and y);
 *           it goes into the hand given by the place, else into the right hand if it is free, else into the left.
 * dropItem: the hand given by the place (else the right one if it holds something, else the left) is emptied;
 *           the item stays where it is, at the place of the hand.
 * switch (V5):  the item of a hand goes into the other, free hand.
 * set (V5):     an item of a hand goes to a place: onto it if it fits and is free, else into the container there.
 * get (V5):     an item on a place or in a container of the holder (or the object on a place) goes into a free hand.
 * If a condition is not met, nothing happens (debug line with OUTPUT_DEBUG_INVENTORY).
 */
public final class InventoryCalculator implements IObjectReceiver {

	public static final int INVENTORY_CALCULATOR_RETURNS_CHANGED = 0;
	public static final int INVENTORY_CALCULATOR_RETURNS_NO_CHANGE = 1;

	/** how far a human reaches to take something, in mm (D2) */
	static final float REACH = 1000.0f;

	private static InventoryCalculator instance;
	private static AccessTokenInventoryCalculator token = AccessTokenInventoryCalculator.getValid();

	private InventoryCalculator() {
	}

	public static synchronized InventoryCalculator getInstance() {
		if (instance == null) instance = new InventoryCalculator();
		return instance;
	}

	final synchronized int calculateInventoryChangedByEvent(final Event event, final StateAnimal state, final HiddenAnimal hiddenWriteAccess) {
		if (event == null || state == null || hiddenWriteAccess == null) return INVENTORY_CALCULATOR_RETURNS_NO_CHANGE;

		EventType eventType = event.getEventType();
		if (eventType == EventType.selfInventoryTake) return take(event, state, hiddenWriteAccess);
		if (eventType == EventType.selfInventoryDrop) return drop(event, state, hiddenWriteAccess);
		if (eventType == EventType.selfInventorySwitch) return switchHands(event, state, hiddenWriteAccess);
		if (eventType == EventType.selfInventorySet) return set(event, state, hiddenWriteAccess);
		if (eventType == EventType.selfInventoryGet) return get(event, state, hiddenWriteAccess);
		if (eventType == EventType.eatUp) return eatUp(state, hiddenWriteAccess);
		return INVENTORY_CALCULATOR_RETURNS_NO_CHANGE;
	}

	/**
	 * Start equipment (protection concept S2, E7, 2026-11-24): while the Simulation builds the world, it puts an item
	 * on a place of a human through this rail. Only with a valid token of the cluster core.
	 *
	 * @return true if the item is on the place now (and carried by the holder)
	 */
	public synchronized boolean startPutToInventory(IAccessToken callerToken, HiddenAnimal holder, InventoryPlace place, SimulationObject item) {
		if (!isCoreToken(callerToken) || holder == null) return false;
		return holder.putToInventory(place, item);
	}

	/**
	 * Start equipment (E7): an item into a container, with the token of this calculator. Only with a valid token of
	 * the cluster core. Whether it fits is decided by the caller (StartEquipment).
	 *
	 * @return true if the item lies inside the container now
	 */
	public synchronized boolean startPutIntoContainer(IAccessToken callerToken, SimulationObject container, SimulationObject item) {
		if (!isCoreToken(callerToken) || container == null) return false;
		return container.putInside(token, item);
	}

	static boolean isCoreToken(IAccessToken callerToken) {
		return callerToken != null && callerToken.isValid() && callerToken.getSimulationCluster() == SimulationCluster.core;
	}

	private int take(Event event, StateAnimal state, HiddenAnimal hiddenWriteAccess) {
		SimulationObject holder = state.getObject();
		SimulationObject item = readItem(event);
		if (!item.isSimulationObject()) return noChange("take: no item", holder);
		if (item == holder) return noChange("take: itself", holder);
		if (item.isCarried(token)) return noChange("take: item " + item.getObjectID() + " is carried by " + item.getCarrier(token).getObjectID(), holder);

		Position holderPosition = holder.getPosition(token);
		Position itemPosition = item.getPosition(token);
		if (holderPosition.checkIsObjectNothing() || itemPosition.checkIsObjectNothing()) return noChange("take: no position", holder);
		if (!isInReach(holderPosition.getX(token), holderPosition.getY(token), itemPosition.getX(token), itemPosition.getY(token))) {
			return noChange("take: item " + item.getObjectID() + " out of reach", holder);
		}

		InventoryPlace hand = handForTake(readPlace(event),
				state.canPutToInventory(InventoryPlace.leftHand, item), state.canPutToInventory(InventoryPlace.rightHand, item));
		if (hand == InventoryPlace.noWhere) return noChange("take: no free hand for item " + item.getObjectID(), holder);

		if (!hiddenWriteAccess.putToInventory(hand, item)) return noChange("take: put refused", holder);
		debug("take: " + holder.getObjectID() + " takes " + item.getObjectID() + " into " + hand);
		return INVENTORY_CALCULATOR_RETURNS_CHANGED;
	}

	private int drop(Event event, StateAnimal state, HiddenAnimal hiddenWriteAccess) {
		SimulationObject holder = state.getObject();
		InventoryPlace hand = handForDrop(readPlace(event),
				state.getInventoryItem(InventoryPlace.leftHand).isSimulationObject(),
				state.getInventoryItem(InventoryPlace.rightHand).isSimulationObject());
		if (hand == InventoryPlace.noWhere) return noChange("drop: the hands are empty", holder);

		SimulationObject dropped = hiddenWriteAccess.removeFromInventory(hand);
		if (!dropped.isSimulationObject()) return noChange("drop: nothing in " + hand, holder);
		debug("drop: " + holder.getObjectID() + " drops " + dropped.getObjectID() + " from " + hand);
		return INVENTORY_CALCULATOR_RETURNS_CHANGED;
	}

	/**
	 * eatUp (V6): the meal is over, what is eatable in the mouth is eaten up: it leaves the mouth and the simulation.
	 * Something that is not eatable stays in the mouth.
	 */
	private int eatUp(StateAnimal state, HiddenAnimal hiddenWriteAccess) {
		SimulationObject holder = state.getObject();
		SimulationObject food = state.getInventoryItem(InventoryPlace.mouth);
		if (!isEatenUp(food)) return noChange("eatUp: nothing eatable in the mouth", holder);
		SimulationObject removed = hiddenWriteAccess.removeFromInventory(InventoryPlace.mouth);
		if (removed != food) return noChange("eatUp: remove refused", holder);
		ObjectMaster.getInstance().removeSimulationObject(token, food);
		debug("eatUp: " + holder.getObjectID() + " has eaten " + food.getObjectID() + " " + food.getClass().getSimpleName());
		return INVENTORY_CALCULATOR_RETURNS_CHANGED;
	}

	/** what is eaten up at the end of a meal: an eatable simulation object (whole, D3) */
	static boolean isEatenUp(SimulationObject inTheMouth) {
		return inTheMouth != null && inTheMouth.isSimulationObject() && inTheMouth instanceof IEatable;
	}

	/** switch: the item in the hand (the given one, else right, else left) goes into the other hand, which has to be free. */
	private int switchHands(Event event, StateAnimal state, HiddenAnimal hiddenWriteAccess) {
		SimulationObject holder = state.getObject();
		InventoryPlace from = handForDrop(readPlace(event),
				state.getInventoryItem(InventoryPlace.leftHand).isSimulationObject(),
				state.getInventoryItem(InventoryPlace.rightHand).isSimulationObject());
		if (from == InventoryPlace.noWhere) return noChange("switch: the hands are empty", holder);
		InventoryPlace to = otherHand(from);
		SimulationObject item = state.getInventoryItem(from);
		if (!state.canPutToInventory(to, item)) return noChange("switch: " + to + " is not free", holder);

		hiddenWriteAccess.removeFromInventory(from);
		if (!hiddenWriteAccess.putToInventory(to, item)) {
			hiddenWriteAccess.putToInventory(from, item);
			return noChange("switch: put refused", holder);
		}
		debug("switch: " + holder.getObjectID() + " moves " + item.getObjectID() + " from " + from + " to " + to);
		return INVENTORY_CALCULATOR_RETURNS_CHANGED;
	}

	/**
	 * set: an item in a hand (the given item if it is in a hand, else the right hand, else the left) goes to the
	 * given place: onto it if it fits and is free, else into the container on that place if it has room.
	 */
	private int set(Event event, StateAnimal state, HiddenAnimal hiddenWriteAccess) {
		SimulationObject holder = state.getObject();
		InventoryPlace target = readPlace(event);
		if (target == InventoryPlace.noWhere || target.isHand()) return noChange("set: no place given (or a hand)", holder);

		InventoryPlace from = handOf(state, readItem(event));
		if (from == InventoryPlace.noWhere) return noChange("set: nothing in the hands", holder);
		SimulationObject item = state.getInventoryItem(from);

		if (state.canPutToInventory(target, item)) {
			hiddenWriteAccess.removeFromInventory(from);
			if (hiddenWriteAccess.putToInventory(target, item)) {
				debug("set: " + holder.getObjectID() + " puts " + item.getObjectID() + " from " + from + " to " + target);
				return INVENTORY_CALCULATOR_RETURNS_CHANGED;
			}
			hiddenWriteAccess.putToInventory(from, item);
			return noChange("set: put refused", holder);
		}

		SimulationObject container = state.getInventoryItem(target);
		if (!ContainerContent.canHold(container, container.getContents(token), item)) {
			return noChange("set: " + item.getObjectID() + " fits neither on " + target + " nor into what is there", holder);
		}
		hiddenWriteAccess.removeFromInventory(from);
		if (!container.putInside(token, item)) {
			hiddenWriteAccess.putToInventory(from, item);
			return noChange("set: the container refused", holder);
		}
		debug("set: " + holder.getObjectID() + " puts " + item.getObjectID() + " into " + container.getObjectID() + " on " + target);
		return INVENTORY_CALCULATOR_RETURNS_CHANGED;
	}

	/**
	 * get: into a free hand (the given one, else right, else left) comes the given item, if it is on a place of the
	 * holder or inside a container there; without an item, the object on the given place.
	 */
	private int get(Event event, StateAnimal state, HiddenAnimal hiddenWriteAccess) {
		SimulationObject holder = state.getObject();
		SimulationObject item = readItem(event);
		InventoryPlace placeOfItem = InventoryPlace.noWhere;
		SimulationObject container = NoSimulationObject.getObjectNothing();

		if (item.isSimulationObject()) {
			placeOfItem = placeOf(state, item);
			if (placeOfItem == InventoryPlace.noWhere) {
				container = item.getCarrier(token);
				if (!item.isInside(token) || placeOf(state, container) == InventoryPlace.noWhere) {
					return noChange("get: item " + item.getObjectID() + " is neither on a place nor in a container of the holder", holder);
				}
			}
		} else {
			placeOfItem = readPlace(event);
			if (placeOfItem == InventoryPlace.noWhere || placeOfItem.isHand()) return noChange("get: neither item nor place given", holder);
			item = state.getInventoryItem(placeOfItem);
			if (!item.isSimulationObject()) return noChange("get: " + placeOfItem + " is empty", holder);
		}
		if (placeOfItem.isHand()) return noChange("get: item " + item.getObjectID() + " is in a hand already", holder);

		InventoryPlace hand = handForTake(placeOfItem.isHand() ? InventoryPlace.noWhere : readHand(event),
				state.canPutToInventory(InventoryPlace.leftHand, item), state.canPutToInventory(InventoryPlace.rightHand, item));
		if (hand == InventoryPlace.noWhere) return noChange("get: no free hand for item " + item.getObjectID(), holder);

		if (container.isSimulationObject()) container.takeOut(token, item);
		else hiddenWriteAccess.removeFromInventory(placeOfItem);
		if (!hiddenWriteAccess.putToInventory(hand, item)) return noChange("get: put refused", holder);
		debug("get: " + holder.getObjectID() + " takes " + item.getObjectID() + " into " + hand);
		return INVENTORY_CALCULATOR_RETURNS_CHANGED;
	}

	/** the hand that holds the item, or (no item given) the right hand if it holds something, else the left */
	private static InventoryPlace handOf(StateAnimal state, SimulationObject item) {
		if (item.isSimulationObject()) {
			if (state.getInventoryItem(InventoryPlace.rightHand) == item) return InventoryPlace.rightHand;
			if (state.getInventoryItem(InventoryPlace.leftHand) == item) return InventoryPlace.leftHand;
			return InventoryPlace.noWhere;
		}
		return handForDrop(InventoryPlace.noWhere,
				state.getInventoryItem(InventoryPlace.leftHand).isSimulationObject(),
				state.getInventoryItem(InventoryPlace.rightHand).isSimulationObject());
	}

	/** the place of the holder the item is on, noWhere if it is on none */
	private static InventoryPlace placeOf(StateAnimal state, SimulationObject item) {
		if (!item.isSimulationObject()) return InventoryPlace.noWhere;
		for (InventoryPlace place : InventoryPlace.values()) {
			if (place != InventoryPlace.noWhere && state.getInventoryItem(place) == item) return place;
		}
		return InventoryPlace.noWhere;
	}

	/** for get with an item: the given place counts as the hand to take it into, if it is a hand */
	private InventoryPlace readHand(Event event) {
		InventoryPlace place = readPlace(event);
		return place.isHand() ? place : InventoryPlace.noWhere;
	}

	static InventoryPlace otherHand(InventoryPlace hand) {
		if (hand == InventoryPlace.leftHand) return InventoryPlace.rightHand;
		if (hand == InventoryPlace.rightHand) return InventoryPlace.leftHand;
		return InventoryPlace.noWhere;
	}

	/**
	 * The hand for taking: the requested hand if it is free; without a requested hand (or another place) the right
	 * one if it is free, else the left one; noWhere if no hand is free.
	 */
	static InventoryPlace handForTake(InventoryPlace requested, boolean leftFree, boolean rightFree) {
		if (requested == InventoryPlace.leftHand) return leftFree ? InventoryPlace.leftHand : InventoryPlace.noWhere;
		if (requested == InventoryPlace.rightHand) return rightFree ? InventoryPlace.rightHand : InventoryPlace.noWhere;
		if (rightFree) return InventoryPlace.rightHand;
		if (leftFree) return InventoryPlace.leftHand;
		return InventoryPlace.noWhere;
	}

	/**
	 * The hand for dropping: the requested hand if it holds something; without a requested hand the right one if it
	 * holds something, else the left one; noWhere if the hand (or both) are empty.
	 */
	static InventoryPlace handForDrop(InventoryPlace requested, boolean leftHolds, boolean rightHolds) {
		if (requested == InventoryPlace.leftHand) return leftHolds ? InventoryPlace.leftHand : InventoryPlace.noWhere;
		if (requested == InventoryPlace.rightHand) return rightHolds ? InventoryPlace.rightHand : InventoryPlace.noWhere;
		if (rightHolds) return InventoryPlace.rightHand;
		if (leftHolds) return InventoryPlace.leftHand;
		return InventoryPlace.noWhere;
	}

	/** Whether the item is in reach: at most REACH away on the ground (x and y; the height does not count). */
	static boolean isInReach(float holderX, float holderY, float itemX, float itemY) {
		double dx = itemX - holderX;
		double dy = itemY - holderY;
		return dx * dx + dy * dy <= (double) REACH * REACH;
	}

	private SimulationObject readItem(Event event) {
		Value value = readParam(event, Value.VALUE_BY_NAME_EVENT_EQUIP_ITEM);
		if (value == null || !value.isValid()) return NoSimulationObject.getObjectNothing();
		return getObjectRequester().requestSimulationObject(token, value, this);
	}

	private InventoryPlace readPlace(Event event) {
		Value value = readParam(event, Value.VALUE_BY_NAME_EVENT_EQUIP_PLACE);
		if (value == null || !value.isValid()) return InventoryPlace.noWhere;
		Object o = value.getObject(Type.integer);
		return (o instanceof Number) ? InventoryPlace.getName(((Number) o).intValue()) : InventoryPlace.noWhere;
	}

	private static Value readParam(Event event, String name) {
		if (!event.hasOptionalParam()) return null;
		event.evaluateOptionalParam();
		IEventParam param = event.getOptionalParam();
		return (param != null) ? param.getParam(name) : null;
	}

	private static int noChange(String reason, SimulationObject holder) {
		debug(reason + " (holder " + holder.getObjectID() + ")");
		return INVENTORY_CALCULATOR_RETURNS_NO_CHANGE;
	}

	private static void debug(String line) {
		if (GlobalSwitches.OUTPUT_DEBUG_INVENTORY) System.out.println("INVENTORY " + line);
	}

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////implementing IObjectReceiver ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private ObjectRequester getObjectRequester() {
		return ObjectRequester.getInstance();
	}

	@Override
	public int receiveObject(int requestID, Object object) {
		getObjectRequester().receive(requestID, object);
		return 0;
	}
}
