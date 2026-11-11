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
package org.socialworld.workbench.act;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.socialworld.actions.ActionMode;
import org.socialworld.core.EventType;

/**
 * Tells how far the event of an action reaches and who is within that range.
 * Works on x and y only: the land is taken as flat. The event master decides
 * who really becomes a candidate.
 *
 * Only modes whose event type has its own range and acts in every direction
 * are supported, so a distance is all that has to be compared.
 */
public final class EventRange {

	private static final List<ActionMode> SUPPORTED_MODES = Collections.unmodifiableList(
			Arrays.asList(ActionMode.sayWhisper, ActionMode.sayNormal, ActionMode.sayScream));

	private EventRange() {
	}

	/** @return the modes the tool offers, ordered by increasing range */
	public static List<ActionMode> getSupportedModes() {
		return SUPPORTED_MODES;
	}

	public static boolean isSupported(ActionMode mode) {
		return SUPPORTED_MODES.contains(mode);
	}

	/** @return the event type that reaches the candidates, {@code EventType.nothing} for an unsupported mode */
	public static EventType getEventType(ActionMode mode) {
		if (mode == ActionMode.sayWhisper) return EventType.candidatesSayWhisper;
		if (mode == ActionMode.sayNormal) return EventType.candidatesSayNormal;
		if (mode == ActionMode.sayScream) return EventType.candidatesSayScream;
		return EventType.nothing;
	}

	/** @return the range in millimeters, 0 for an unsupported mode */
	public static float getRangeInMillimeters(ActionMode mode) {
		if (!isSupported(mode)) return 0;
		return getEventType(mode).getEffectDistance();
	}

	/**
	 * Lists the objects within range of the causer, the same way the event master
	 * sorts them out: at most the range away, and not on the very spot of the event.
	 *
	 * @param causerObjectId the object at whose position the event happens
	 * @param rangeInMillimeters the range of the event
	 * @param positions the x and y of the objects in millimeters, by object id
	 * @return the object ids in ascending order, without the causer
	 */
	public static List<Integer> getObjectsInRange(int causerObjectId, float rangeInMillimeters, Map<Integer, Point> positions) {
		List<Integer> result = new ArrayList<Integer>();

		Point causer = positions.get(causerObjectId);
		if (causer == null) return result;

		double squaredRange = (double) rangeInMillimeters * rangeInMillimeters;

		for (Map.Entry<Integer, Point> entry : positions.entrySet()) {
			if (entry.getKey() == causerObjectId) continue;

			long dx = (long) entry.getValue().x - causer.x;
			long dy = (long) entry.getValue().y - causer.y;
			if (dx == 0 && dy == 0) continue;

			if ((double) dx * dx + (double) dy * dy <= squaredRange) result.add(entry.getKey());
		}

		Collections.sort(result);
		return result;
	}
}
