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
import static org.assertj.core.api.Assertions.within;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ActionMapScaleTest {

	private static final int WIDTH = 648;
	private static final int HEIGHT = 448;

	private static ActionMapScale scaleOf(Point... positions) {
		return new ActionMapScale(Arrays.asList(positions), WIDTH, HEIGHT);
	}

	@Test
	void keepsAllPositionsInsideTheMap() {
		Point a = new Point(-25000, -37000);
		Point b = new Point(76000, 75000);
		ActionMapScale scale = scaleOf(a, b);

		assertThat(scale.toPixelX(a.x)).isBetween(0, WIDTH);
		assertThat(scale.toPixelX(b.x)).isBetween(0, WIDTH);
		assertThat(scale.toPixelY(a.y)).isBetween(0, HEIGHT);
		assertThat(scale.toPixelY(b.y)).isBetween(0, HEIGHT);
	}

	@Test
	void usesTheSameScaleForBothAxes() {
		ActionMapScale scale = scaleOf(new Point(0, 0), new Point(100000, 40000));

		assertThat(scale.toPixelX(10000) - scale.toPixelX(0)).isEqualTo(scale.toPixelY(0) - scale.toPixelY(10000));
	}

	@Test
	void letsYGrowUpwards() {
		ActionMapScale scale = scaleOf(new Point(0, 0), new Point(0, 50000));

		assertThat(scale.toPixelY(50000)).isLessThan(scale.toPixelY(0));
	}

	@Test
	void centersThePositions() {
		ActionMapScale scale = scaleOf(new Point(10000, 20000), new Point(50000, 60000));

		assertThat(scale.toPixelX(30000)).isEqualTo(WIDTH / 2);
		assertThat(scale.toPixelY(40000)).isEqualTo(HEIGHT / 2);
	}

	@Test
	void scalesALengthLikeThePositions() {
		ActionMapScale scale = scaleOf(new Point(0, 0), new Point(100000, 0));

		assertThat(scale.toPixelLength(10000)).isEqualTo(scale.toPixelX(10000) - scale.toPixelX(0));
	}

	@Test
	void copesWithASinglePositionAndWithNone() {
		ActionMapScale single = scaleOf(new Point(5000, 5000));
		ActionMapScale none = new ActionMapScale(Collections.<Point>emptyList(), WIDTH, HEIGHT);

		assertThat(single.toPixelX(5000)).isEqualTo(WIDTH / 2);
		assertThat(none.toPixelLength(10000)).isGreaterThan(0);
	}

	@Test
	void copesWithAPanelThatHasNoSizeYet() {
		ActionMapScale scale = new ActionMapScale(Arrays.asList(new Point(0, 0), new Point(100000, 0)), 0, 0);

		assertThat(scale.toPixelLength(100000)).isGreaterThanOrEqualTo(0);
	}

	@Test
	void turnsPixelsBackIntoPositions() {
		ActionMapScale scale = scaleOf(new Point(-25000, -37000), new Point(76000, 75000));
		int millimetersOfOnePixel = scale.toWorldX(1) - scale.toWorldX(0);

		assertThat(scale.toWorldX(scale.toPixelX(12000))).isCloseTo(12000, within(millimetersOfOnePixel));
		assertThat(scale.toWorldY(scale.toPixelY(-8000))).isCloseTo(-8000, within(millimetersOfOnePixel));
	}

	@Test
	void growsTheWorldYWhenThePixelGoesUp() {
		ActionMapScale scale = scaleOf(new Point(0, 0), new Point(0, 50000));

		assertThat(scale.toWorldY(10)).isGreaterThan(scale.toWorldY(100));
	}

	@Test
	void findsTheNearestObjectWithinTheTolerance() {
		Map<Integer, Point> positions = new HashMap<Integer, Point>();
		positions.put(1, new Point(0, 0));
		positions.put(2, new Point(100000, 0));
		ActionMapScale scale = new ActionMapScale(positions.values(), WIDTH, HEIGHT);

		assertThat(scale.findObjectAt(scale.toPixelX(100000) - 3, scale.toPixelY(0), 10, positions)).isEqualTo(2);
	}

	@Test
	void findsNothingBeyondTheTolerance() {
		Map<Integer, Point> positions = new HashMap<Integer, Point>();
		positions.put(1, new Point(0, 0));
		positions.put(2, new Point(100000, 0));
		ActionMapScale scale = new ActionMapScale(positions.values(), WIDTH, HEIGHT);

		assertThat(scale.findObjectAt(WIDTH / 2, HEIGHT / 2, 10, positions)).isEqualTo(ActionMapScale.NO_OBJECT);
	}
}
