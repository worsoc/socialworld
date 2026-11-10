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

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;

/**
 * The state of the display, free of Swing. The threads of the simulation write into it,
 * the display reads copies by snapshot(). The values are copied at once,
 * so that the display never reads an object the simulation is changing.
 * Objects with an ID outside 1..OBJECT_COUNT are ignored.
 */
public class VisualModel {

	public static final int OBJECT_COUNT = 100;
	static final int NO_OBJECT = 0;
	static final int RECENT_ACTIONS = 8;

	static final class FinishedAction {
		final ActionType type;
		final ActionMode mode;
		final long endedMillis;
		final boolean endedEarly;

		private FinishedAction(ActionType type, ActionMode mode, long endedMillis, boolean endedEarly) {
			this.type = type;
			this.mode = mode;
			this.endedMillis = endedMillis;
			this.endedEarly = endedEarly;
		}
	}

	static final class ObjectState {
		boolean acting;
		ActionType actionType;
		ActionMode actionMode;
		float intensity;
		long duration;
		long remainedDuration;
		int priority;
		long actionEndedMillis;
		boolean endedEarly;
		int actionCount;
		// the newest first, free places are null
		final FinishedAction[] recentActions = new FinishedAction[RECENT_ACTIONS];

		boolean attributesKnown;
		final int[] attributes = new int[Attribute.NUMBER_OF_ATTRIBUTES];
		final int[] lastChange = new int[Attribute.NUMBER_OF_ATTRIBUTES];
		final long[] lastChangeMillis = new long[Attribute.NUMBER_OF_ATTRIBUTES];
		int changeCount;
		int reportCount;

		private ObjectState copy() {
			ObjectState copy = new ObjectState();
			copy.acting = acting;
			copy.actionType = actionType;
			copy.actionMode = actionMode;
			copy.intensity = intensity;
			copy.duration = duration;
			copy.remainedDuration = remainedDuration;
			copy.priority = priority;
			copy.actionEndedMillis = actionEndedMillis;
			copy.endedEarly = endedEarly;
			copy.actionCount = actionCount;
			System.arraycopy(recentActions, 0, copy.recentActions, 0, recentActions.length);
			copy.attributesKnown = attributesKnown;
			System.arraycopy(attributes, 0, copy.attributes, 0, attributes.length);
			System.arraycopy(lastChange, 0, copy.lastChange, 0, lastChange.length);
			System.arraycopy(lastChangeMillis, 0, copy.lastChangeMillis, 0, lastChangeMillis.length);
			copy.changeCount = changeCount;
			copy.reportCount = reportCount;
			return copy;
		}
	}

	static final class Snapshot {
		private final ObjectState[] objects;
		final long newActions;
		final long attributeReports;

		private Snapshot(ObjectState[] objects, long newActions, long attributeReports) {
			this.objects = objects;
			this.newActions = newActions;
			this.attributeReports = attributeReports;
		}

		ObjectState get(int objID) {
			return objects[objID - 1];
		}

		int countActing() {
			int count = 0;
			for (ObjectState state : objects) {
				if (state.acting) count++;
			}
			return count;
		}

		long sumAttributeChanges() {
			long sum = 0;
			for (ObjectState state : objects) {
				sum += state.changeCount;
			}
			return sum;
		}

		int countAttributesKnown() {
			int count = 0;
			for (ObjectState state : objects) {
				if (state.attributesKnown) count++;
			}
			return count;
		}
	}

	private final ObjectState[] states = new ObjectState[OBJECT_COUNT];
	private final Object[] lastActions = new Object[OBJECT_COUNT];
	private long newActions;
	private long attributeReports;

	public VisualModel() {
		for (int i = 0; i < OBJECT_COUNT; i++) {
			states[i] = new ObjectState();
		}
	}

	/**
	 * The action handler reports a running action again with each of its calculations.
	 * Only a report of another action than the last reported one counts as a new action.
	 *
	 * @param action
	 *            only used to tell whether it is the same action as before
	 */
	public synchronized void startAction(int objID, Object action, ActionType type, ActionMode mode,
			float intensity, long duration, long remainedDuration, int priority) {
		if (!isShown(objID)) return;

		ObjectState state = states[objID - 1];
		if (action != lastActions[objID - 1]) {
			lastActions[objID - 1] = action;
			state.actionCount++;
			newActions++;
		}
		state.acting = true;
		state.actionType = type;
		state.actionMode = mode;
		state.intensity = intensity;
		state.duration = duration;
		state.remainedDuration = remainedDuration;
		state.priority = priority;
		state.endedEarly = false;
	}

	/**
	 * The action stays readable after its end, so that a short action does not get lost.
	 * The action handler reports the end of a finished and of an interrupted action alike,
	 * only an interrupted action is not done at this moment.
	 */
	public synchronized void stopAction(int objID, boolean done, long nowMillis) {
		if (!isShown(objID)) return;

		ObjectState state = states[objID - 1];
		if (!state.acting) return;

		state.acting = false;
		state.actionEndedMillis = nowMillis;
		state.endedEarly = !done;

		if (ActionFamily.of(state.actionType) != null) {
			System.arraycopy(state.recentActions, 0, state.recentActions, 1, RECENT_ACTIONS - 1);
			state.recentActions[0] = new FinishedAction(state.actionType, state.actionMode, nowMillis, !done);
		}
	}

	/**
	 * A last change is kept until the next real change, a report without change leaves it alone.
	 */
	public synchronized void setAttributes(int objID, AttributeArray attributes, boolean withChange, long nowMillis) {
		if (!isShown(objID) || attributes.checkIsObjectNothing()) return;

		ObjectState state = states[objID - 1];
		for (int i = 0; i < attributeCount(attributes); i++) {
			int value = attributes.get(i);
			if (state.attributesKnown && value != state.attributes[i]) {
				state.lastChange[i] = value - state.attributes[i];
				state.lastChangeMillis[i] = nowMillis;
			}
			state.attributes[i] = value;
		}
		state.attributesKnown = true;
		state.reportCount++;
		if (withChange) state.changeCount++;
		attributeReports++;
	}

	/**
	 * The start values. A value already reported by the simulation is newer, so it stays.
	 */
	public synchronized void setInitialAttributes(int objID, AttributeArray attributes) {
		if (!isShown(objID) || attributes.checkIsObjectNothing()) return;

		ObjectState state = states[objID - 1];
		if (state.attributesKnown) return;

		for (int i = 0; i < attributeCount(attributes); i++) {
			state.attributes[i] = attributes.get(i);
		}
		state.attributesKnown = true;
	}

	synchronized Snapshot snapshot() {
		ObjectState[] copies = new ObjectState[OBJECT_COUNT];
		for (int i = 0; i < OBJECT_COUNT; i++) {
			copies[i] = states[i].copy();
		}
		return new Snapshot(copies, newActions, attributeReports);
	}

	static boolean isShown(int objID) {
		return objID >= 1 && objID <= OBJECT_COUNT;
	}

	private int attributeCount(AttributeArray attributes) {
		return Math.min(attributes.length(), Attribute.NUMBER_OF_ATTRIBUTES);
	}
}
