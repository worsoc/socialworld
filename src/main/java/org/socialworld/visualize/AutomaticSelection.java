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
package org.socialworld.visualize;

import org.socialworld.attributes.Attribute;

/**
 * Chooses the object for the detail column without a click: after a holding time the object with the
 * largest change of a single attribute since the last switch. The largest single change and not the sum
 * of the changes counts, otherwise the matrix calculation, which moves all attributes at once,
 * would nearly always win against an event with one strong effect.
 * Only used on the event dispatch thread.
 */
class AutomaticSelection {

	static final long HOLD_MILLIS = 5000;

	private boolean active;
	private long lastSwitchMillis;
	private boolean hasReason;
	private int reasonAttribute;
	private int reasonChange;
	private long reasonMillis;

	/**
	 * Changes of the last moments count, so that the column does not wait for the next change.
	 */
	void start(long nowMillis) {
		active = true;
		lastSwitchMillis = nowMillis - HOLD_MILLIS;
		hasReason = false;
	}

	void stop() {
		active = false;
		hasReason = false;
	}

	boolean isActive() {
		return active;
	}

	/**
	 * @return the object to show: a new one, or the current one while holding or without a new change
	 */
	int update(VisualModel.Snapshot snapshot, long nowMillis, int currentObject) {
		if (!active || nowMillis - lastSwitchMillis < HOLD_MILLIS) return currentObject;

		int bestObject = VisualModel.NO_OBJECT;
		int bestAttribute = 0;
		int bestSize = 0;
		for (int objID = 1; objID <= VisualModel.OBJECT_COUNT; objID++) {
			VisualModel.ObjectState state = snapshot.get(objID);
			for (int attribute = 0; attribute < Attribute.NUMBER_OF_ATTRIBUTES; attribute++) {
				int size = Math.abs(state.lastChange[attribute]);
				if (size > bestSize && state.lastChangeMillis[attribute] > lastSwitchMillis) {
					bestObject = objID;
					bestAttribute = attribute;
					bestSize = size;
				}
			}
		}

		if (bestObject == VisualModel.NO_OBJECT) return currentObject;

		VisualModel.ObjectState best = snapshot.get(bestObject);
		lastSwitchMillis = nowMillis;
		hasReason = true;
		reasonAttribute = bestAttribute;
		reasonChange = best.lastChange[bestAttribute];
		reasonMillis = best.lastChangeMillis[bestAttribute];
		return bestObject;
	}

	/**
	 * @return why the shown object was chosen, empty if the automatic is off
	 */
	String describe(long nowMillis) {
		if (!active) return "";
		if (!hasReason) return "Automatik: warte auf eine \u00C4nderung";

		long secondsAgo = Math.max(0, (nowMillis - reasonMillis) / 1000);
		return "Automatik: " + Attribute.getAttributeName(reasonAttribute) + " "
				+ ObjectDetailPanel.changeText(reasonChange) + " vor " + secondsAgo + " s";
	}
}
