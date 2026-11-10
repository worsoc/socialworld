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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.socialworld.attributes.Attribute;

/**
 * What all objects together did and how their attributes were spread in the last five minutes,
 * sampled from the snapshots of the display. Only used on the event dispatch thread.
 */
class PopulationHistory {

	static final long SAMPLE_MILLIS = 1000;
	static final long LENGTH_MILLIS = 300000;
	static final int LOW_PERCENT = 10;
	static final int HIGH_PERCENT = 90;

	static final class Sample {
		final long millis;
		final int[] familyCounts = new int[ActionFamily.values().length];
		final int objectsWithAttributes;
		final int[] means = new int[Attribute.NUMBER_OF_ATTRIBUTES];
		final int[] lows = new int[Attribute.NUMBER_OF_ATTRIBUTES];
		final int[] highs = new int[Attribute.NUMBER_OF_ATTRIBUTES];

		private Sample(VisualModel.Snapshot snapshot, long millis) {
			this.millis = millis;

			int known = 0;
			for (int objID = 1; objID <= VisualModel.OBJECT_COUNT; objID++) {
				VisualModel.ObjectState state = snapshot.get(objID);
				if (state.acting && ActionFamily.of(state.actionType) != null) {
					familyCounts[ActionFamily.of(state.actionType).ordinal()]++;
				}
				if (state.attributesKnown) known++;
			}
			objectsWithAttributes = known;

			if (known > 0) {
				for (int attribute = 0; attribute < Attribute.NUMBER_OF_ATTRIBUTES; attribute++) {
					fillSpread(snapshot, attribute);
				}
			}
		}

		private void fillSpread(VisualModel.Snapshot snapshot, int attribute) {
			int[] values = new int[objectsWithAttributes];
			int index = 0;
			long sum = 0;
			for (int objID = 1; objID <= VisualModel.OBJECT_COUNT; objID++) {
				VisualModel.ObjectState state = snapshot.get(objID);
				if (state.attributesKnown) {
					values[index++] = state.attributes[attribute];
					sum += state.attributes[attribute];
				}
			}
			Arrays.sort(values);
			means[attribute] = (int) (sum / objectsWithAttributes);
			lows[attribute] = percentile(values, LOW_PERCENT);
			highs[attribute] = percentile(values, HIGH_PERCENT);
		}
	}

	private final ArrayDeque<Sample> samples = new ArrayDeque<Sample>();
	private long nextSampleMillis = Long.MIN_VALUE;

	void record(VisualModel.Snapshot snapshot, long nowMillis) {
		if (nowMillis < nextSampleMillis) return;
		nextSampleMillis = nowMillis + SAMPLE_MILLIS;

		samples.addLast(new Sample(snapshot, nowMillis));
		while (samples.getFirst().millis < nowMillis - LENGTH_MILLIS) {
			samples.removeFirst();
		}
	}

	/**
	 * @return the samples, the oldest first
	 */
	List<Sample> samples() {
		return new ArrayList<Sample>(samples);
	}

	/**
	 * @param sortedValues
	 *            not empty, in ascending order
	 * @return the smallest value that at least the given percentage of the values does not exceed
	 */
	static int percentile(int[] sortedValues, int percent) {
		int rank = (percent * sortedValues.length + 99) / 100;
		return sortedValues[Math.max(0, rank - 1)];
	}
}
