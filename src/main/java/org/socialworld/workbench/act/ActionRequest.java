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

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;

/**
 * What the Action Creation Tool wants a human to do. Holds plain values only, so
 * it can be checked without a running simulation.
 */
public final class ActionRequest {

	/** The result of a check. */
	public enum Problem {
		NONE, MODE_NOT_SUPPORTED, INVALID_CAUSER, UNKNOWN_CAUSER, CAUSER_BUSY, ALREADY_PLANNED,
		INVALID_INTENSITY, INVALID_PRIORITY, INVALID_DURATION
	}

	private final ActionMode mode;
	private final int causerObjectId;
	private final float intensity;
	private final int priority;
	private final int durationInMilliseconds;

	/** The longest duration the tool lets an action have: one hour. */
	public static final int MAX_DURATION_IN_MILLISECONDS = 3600000;

	public ActionRequest(ActionMode mode, int causerObjectId, float intensity, int priority, int durationInMilliseconds) {
		this.mode = mode;
		this.causerObjectId = causerObjectId;
		this.intensity = intensity;
		this.priority = priority;
		this.durationInMilliseconds = durationInMilliseconds;
	}

	public ActionMode getMode() {
		return mode;
	}

	public int getCauserObjectId() {
		return causerObjectId;
	}

	public float getIntensity() {
		return intensity;
	}

	public int getPriority() {
		return priority;
	}

	/** A duration of 0 lets the action be performed once and makes it not interruptable. */
	public int getDurationInMilliseconds() {
		return durationInMilliseconds;
	}

	/**
	 * @return the first problem of this request, {@link Problem#NONE} if there is none.
	 *         Whether the causer really exists and is free, and whether the same action is already
	 *         planned, can only be told by a running simulation.
	 */
	public Problem check() {
		if (!EventRange.isSupported(mode)) return Problem.MODE_NOT_SUPPORTED;
		if (causerObjectId <= 0) return Problem.INVALID_CAUSER;
		if (Float.isNaN(intensity) || Float.isInfinite(intensity) || intensity < 0) return Problem.INVALID_INTENSITY;
		if (priority < 0 || priority > AbstractAction.MAX_ACTION_PRIORITY) return Problem.INVALID_PRIORITY;
		if (durationInMilliseconds < 0 || durationInMilliseconds > MAX_DURATION_IN_MILLISECONDS) return Problem.INVALID_DURATION;
		return Problem.NONE;
	}
}
