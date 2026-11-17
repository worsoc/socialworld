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

import org.socialworld.actions.ActionMode;

/**
 * When a running action is calculated again (concept EID, section 7.1).
 *
 * A running action calls perform(), and so creates its events, at t = 0, a, 2a, ...
 * as long as it is running, a being the event interval of its action mode.
 * An action mode with the interval 0 performs once, at t = 0.
 * At the end of the duration the action is only finished: its remaining time passes
 * without another perform().
 */
public final class ActionEventTiming {

	private ActionEventTiming() {
	}

	private static int intervalOf(ActionMode mode) {
		return mode == null ? 0 : mode.getEventIntervalSeconds();
	}

	private static int secondsUntilTheEnd(long remainedDurationInMilliseconds) {
		if (remainedDurationInMilliseconds <= 0) return 0;
		return (int) ((remainedDurationInMilliseconds + 999) / 1000);
	}

	/**
	 * @param mode the mode of the action
	 * @param remainedDurationInMilliseconds the remaining duration after the calculation just done
	 * @return true if the next calculation only finishes the action, without perform()
	 */
	public static boolean isFinishingOnly(ActionMode mode, long remainedDurationInMilliseconds) {
		int interval = intervalOf(mode);
		return interval == 0 || remainedDurationInMilliseconds <= interval * 1000L;
	}

	/**
	 * @param mode the mode of the action
	 * @param remainedDurationInMilliseconds the remaining duration after the calculation just done
	 * @return the seconds until the next calculation: the interval, or the rest of the duration
	 *         if the next calculation only finishes the action
	 */
	public static int secondsTillNextCalculation(ActionMode mode, long remainedDurationInMilliseconds) {
		if (isFinishingOnly(mode, remainedDurationInMilliseconds)) {
			return secondsUntilTheEnd(remainedDurationInMilliseconds);
		}
		return intervalOf(mode);
	}
}
