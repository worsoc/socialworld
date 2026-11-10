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

/**
 * Turns a growing sum into a value per second. The value is renewed about once per second.
 */
class PerSecondRate {

	private static final long INTERVAL_MILLIS = 1000;

	private boolean started;
	private long lastTotal;
	private long lastMillis;
	private long rate;

	long update(long total, long nowMillis) {
		if (!started) {
			started = true;
			lastTotal = total;
			lastMillis = nowMillis;
			return rate;
		}

		long elapsed = nowMillis - lastMillis;
		if (elapsed >= INTERVAL_MILLIS) {
			rate = (total - lastTotal) * 1000 / elapsed;
			lastTotal = total;
			lastMillis = nowMillis;
		}
		return rate;
	}
}
