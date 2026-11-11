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

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.ActionMode;
import org.socialworld.core.EventType;

public class EventRangeTest {

	private static final int CAUSER = 1;
	private static final float RANGE = 5000f;

	private static Map<Integer, Point> positionsWithCauserAtOrigin() {
		Map<Integer, Point> positions = new HashMap<Integer, Point>();
		positions.put(CAUSER, new Point(0, 0));
		return positions;
	}

	@Test
	void offersTheSayModesByIncreasingRange() {
		assertThat(EventRange.getSupportedModes())
				.containsExactly(ActionMode.sayWhisper, ActionMode.sayNormal, ActionMode.sayScream);
	}

	@Test
	void knowsTheEventTypeOfAMode() {
		assertThat(EventRange.getEventType(ActionMode.sayWhisper)).isEqualTo(EventType.candidatesSayWhisper);
		assertThat(EventRange.getEventType(ActionMode.sayNormal)).isEqualTo(EventType.candidatesSayNormal);
		assertThat(EventRange.getEventType(ActionMode.sayScream)).isEqualTo(EventType.candidatesSayScream);
	}

	@Test
	void takesTheRangeFromTheEventType() {
		assertThat(EventRange.getRangeInMillimeters(ActionMode.sayWhisper)).isEqualTo(1000f);
		assertThat(EventRange.getRangeInMillimeters(ActionMode.sayNormal)).isEqualTo(5000f);
		assertThat(EventRange.getRangeInMillimeters(ActionMode.sayScream)).isEqualTo(10000f);
	}

	@Test
	void everySupportedModeActsInEveryDirection() {
		for (ActionMode mode : EventRange.getSupportedModes()) {
			assertThat(EventRange.getEventType(mode).getEffectAngle()).isGreaterThan(180f);
		}
	}

	@Test
	void hasNoRangeForAnUnsupportedMode() {
		assertThat(EventRange.isSupported(ActionMode.walk)).isFalse();
		assertThat(EventRange.isSupported(null)).isFalse();
		assertThat(EventRange.getEventType(ActionMode.walk)).isEqualTo(EventType.nothing);
		assertThat(EventRange.getRangeInMillimeters(ActionMode.walk)).isEqualTo(0f);
	}

	@Test
	void countsAnObjectExactlyAtTheRange() {
		Map<Integer, Point> positions = positionsWithCauserAtOrigin();
		positions.put(2, new Point(5000, 0));

		assertThat(EventRange.getObjectsInRange(CAUSER, RANGE, positions)).containsExactly(2);
	}

	@Test
	void leavesOutAnObjectJustBeyondTheRange() {
		Map<Integer, Point> positions = positionsWithCauserAtOrigin();
		positions.put(2, new Point(5001, 0));

		assertThat(EventRange.getObjectsInRange(CAUSER, RANGE, positions)).isEmpty();
	}

	@Test
	void measuresTheDistanceInThePlane() {
		Map<Integer, Point> positions = positionsWithCauserAtOrigin();
		positions.put(2, new Point(3000, 4000));
		positions.put(3, new Point(3001, 4000));

		assertThat(EventRange.getObjectsInRange(CAUSER, RANGE, positions)).containsExactly(2);
	}

	@Test
	void worksAroundACauserAwayFromTheOrigin() {
		Map<Integer, Point> positions = new HashMap<Integer, Point>();
		positions.put(CAUSER, new Point(-20000, 7000));
		positions.put(2, new Point(-24000, 7000));
		positions.put(3, new Point(0, 7000));

		assertThat(EventRange.getObjectsInRange(CAUSER, RANGE, positions)).containsExactly(2);
	}

	@Test
	void neverListsTheCauser() {
		assertThat(EventRange.getObjectsInRange(CAUSER, RANGE, positionsWithCauserAtOrigin())).isEmpty();
	}

	@Test
	void leavesOutAnObjectOnTheSpotOfTheEvent() {
		Map<Integer, Point> positions = positionsWithCauserAtOrigin();
		positions.put(2, new Point(0, 0));

		assertThat(EventRange.getObjectsInRange(CAUSER, RANGE, positions)).isEmpty();
	}

	@Test
	void listsTheObjectsInAscendingOrder() {
		Map<Integer, Point> positions = positionsWithCauserAtOrigin();
		positions.put(30, new Point(100, 0));
		positions.put(4, new Point(200, 0));
		positions.put(17, new Point(300, 0));

		assertThat(EventRange.getObjectsInRange(CAUSER, RANGE, positions)).containsExactly(4, 17, 30);
	}

	@Test
	void findsNothingForAnUnknownCauser() {
		Map<Integer, Point> positions = new HashMap<Integer, Point>();
		positions.put(2, new Point(0, 0));

		assertThat(EventRange.getObjectsInRange(CAUSER, RANGE, positions)).isEmpty();
	}

	@Test
	void doesNotOverflowForFarAwayObjects() {
		Map<Integer, Point> positions = new HashMap<Integer, Point>();
		positions.put(CAUSER, new Point(Integer.MIN_VALUE, 0));
		positions.put(2, new Point(Integer.MAX_VALUE, 0));

		assertThat(EventRange.getObjectsInRange(CAUSER, RANGE, positions)).isEmpty();
	}
}
