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
 * The attribute values of the last five minutes, sampled from the snapshots of the display.
 * All objects are sampled, so that a newly selected object already has its history.
 * Only used on the event dispatch thread.
 */
class AttributeHistory {

	static final long SAMPLE_MILLIS = 5000;
	static final int SAMPLES = 60;

	private final int[][][] samples = new int[VisualModel.OBJECT_COUNT][Attribute.NUMBER_OF_ATTRIBUTES][SAMPLES];
	private final int[] counts = new int[VisualModel.OBJECT_COUNT];
	private long nextSampleMillis = Long.MIN_VALUE;

	void record(VisualModel.Snapshot snapshot, long nowMillis) {
		if (nowMillis < nextSampleMillis) return;
		nextSampleMillis = nowMillis + SAMPLE_MILLIS;

		for (int objID = 1; objID <= VisualModel.OBJECT_COUNT; objID++) {
			VisualModel.ObjectState state = snapshot.get(objID);
			if (!state.attributesKnown) continue;

			int index = counts[objID - 1] % SAMPLES;
			for (int attribute = 0; attribute < Attribute.NUMBER_OF_ATTRIBUTES; attribute++) {
				samples[objID - 1][attribute][index] = state.attributes[attribute];
			}
			counts[objID - 1]++;
		}
	}

	/**
	 * @return the sampled values of the attribute, the oldest first
	 */
	int[] values(int objID, int attribute) {
		int count = counts[objID - 1];
		int length = Math.min(count, SAMPLES);
		int[] result = new int[length];
		for (int i = 0; i < length; i++) {
			result[i] = samples[objID - 1][attribute][(count - length + i) % SAMPLES];
		}
		return result;
	}
}
