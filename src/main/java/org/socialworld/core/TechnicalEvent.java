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
package org.socialworld.core;

import org.socialworld.attributes.Position;
import org.socialworld.collections.ValueArrayList;

/**
 * A technical event (movement concept 2026-11-19): it has no causer, no parameters and no meaning
 * in the simulation and never goes through the EventMaster. It only tells the PositionCalculator
 * what to do: the time step of the position or the stop of the active move.
 * The two instances are shared, they hold no state.
 */
public final class TechnicalEvent extends Event {

	public static final TechnicalEvent POSITION_STEP = new TechnicalEvent(EventType.positionStep);
	public static final TechnicalEvent ACTIVE_MOVE_STOP = new TechnicalEvent(EventType.activeMoveStop);
	public static final TechnicalEvent FOLLOW_CARRIER = new TechnicalEvent(EventType.followCarrier);

	private TechnicalEvent(EventType eventType) {
		super(eventType, null /* no causer */, LOWEST_EVENT_PRIORITY, Position.getObjectNothing());
	}

	@Override
	public ValueArrayList getProperties() {
		return ValueArrayList.EMPTY_ARGUMENTS;
	}

	@Override
	public String toString() {
		return getEventType().toString();
	}
}
