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

import org.socialworld.GlobalSwitches;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.attributes.Position;
import org.socialworld.calculation.IObjectReceiver;
import org.socialworld.calculation.ObjectRequester;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.core.Event;
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
		// switch, set and get come with V5
		return INVENTORY_CALCULATOR_RETURNS_NO_CHANGE;
	}

	private int take(Event event, StateAnimal state, HiddenAnimal hiddenWriteAccess) {
		SimulationObject holder = state.getObject();
		SimulationObject item = readItem(event);
		if (!item.isSimulationObject()) return noChange("take: no item", holder);
		if (item == holder) return noChange("take: itself", holder);
		if (item.isCarried()) return noChange("take: item " + item.getObjectID() + " is carried by " + item.getCarrier().getObjectID(), holder);

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
