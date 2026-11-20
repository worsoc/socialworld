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
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * The scrollable ACT map: the scale is fitted once and stays, the area only grows.
 */
public class ActionMapFrameTest {

	private static final int WIDTH = 648;
	private static final int HEIGHT = 448;

	private static final List<Point> START = Arrays.asList(new Point(0, 0), new Point(40000, 20000));

	@Test
	void fit_drawsLikeTheFittedScale() {
		ActionMapFrame frame = ActionMapFrame.fit(START, WIDTH, HEIGHT);
		ActionMapScale fitted = new ActionMapScale(START, WIDTH, HEIGHT);

		assertThat(frame.getPixelWidth()).isCloseTo(WIDTH, within(1));
		assertThat(frame.getPixelHeight()).isCloseTo(HEIGHT, within(1));
		assertThat(frame.getScale().toPixelX(40000)).isCloseTo(fitted.toPixelX(40000), within(1));
		assertThat(frame.getScale().toPixelY(20000)).isCloseTo(fitted.toPixelY(20000), within(1));
	}

	@Test
	void include_keepsTheScaleWhenAHumanLeaves() {
		ActionMapFrame frame = ActionMapFrame.fit(START, WIDTH, HEIGHT);
		int tenMetersBefore = frame.getScale().toPixelLength(10000);

		frame.include(Arrays.asList(new Point(0, 0), new Point(400000, 20000)));

		assertThat(frame.getScale().toPixelLength(10000)).isEqualTo(tenMetersBefore);
		assertThat(frame.getPixelWidth()).isGreaterThan(WIDTH);
	}

	@Test
	void include_toTheRight_doesNotShiftTheOldContent() {
		ActionMapFrame frame = ActionMapFrame.fit(START, WIDTH, HEIGHT);
		int xBefore = frame.getScale().toPixelX(0);

		Point shift = frame.include(Arrays.asList(new Point(400000, 0)));

		assertThat(shift).isEqualTo(new Point(0, 0));
		assertThat(frame.getScale().toPixelX(0)).isCloseTo(xBefore, within(1));
	}

	@Test
	void include_toTheLeftAndUp_shiftsTheOldContentByTheReturnedPixels() {
		ActionMapFrame frame = ActionMapFrame.fit(START, WIDTH, HEIGHT);
		int xBefore = frame.getScale().toPixelX(0);
		int yBefore = frame.getScale().toPixelY(0);

		Point shift = frame.include(Arrays.asList(new Point(-300000, 200000)));

		assertThat(shift.x).isGreaterThan(0);
		assertThat(shift.y).isGreaterThan(0);
		assertThat(frame.getScale().toPixelX(0)).isCloseTo(xBefore + shift.x, within(1));
		assertThat(frame.getScale().toPixelY(0)).isCloseTo(yBefore + shift.y, within(1));
	}

	@Test
	void include_keepsTheNewPositionInsideTheArea() {
		ActionMapFrame frame = ActionMapFrame.fit(START, WIDTH, HEIGHT);
		Point far = new Point(-300000, -250000);

		frame.include(Arrays.asList(far));
		ActionMapScale scale = frame.getScale();

		assertThat(scale.toPixelX(far.x)).isBetween(0, frame.getPixelWidth());
		assertThat(scale.toPixelY(far.y)).isBetween(0, frame.getPixelHeight());
	}

	@Test
	void include_neverShrinksTheArea() {
		ActionMapFrame frame = ActionMapFrame.fit(START, WIDTH, HEIGHT);
		frame.include(Arrays.asList(new Point(400000, 0)));
		int width = frame.getPixelWidth();

		frame.include(START);

		assertThat(frame.getPixelWidth()).isEqualTo(width);
	}

	@Test
	void fill_growsEvenlyToABiggerView() {
		ActionMapFrame frame = ActionMapFrame.fit(START, WIDTH, HEIGHT);
		int xBefore = frame.getScale().toPixelX(0);

		Point shift = frame.fill(WIDTH + 200, HEIGHT);

		assertThat(frame.getPixelWidth()).isCloseTo(WIDTH + 200, within(1));
		assertThat(shift.x).isCloseTo(100, within(1));
		assertThat(shift.y).isEqualTo(0);
		assertThat(frame.getScale().toPixelX(0)).isCloseTo(xBefore + shift.x, within(1));
	}

	@Test
	void fill_doesNothingForASmallerView() {
		ActionMapFrame frame = ActionMapFrame.fit(START, WIDTH, HEIGHT);

		assertThat(frame.fill(100, 100)).isEqualTo(new Point(0, 0));
		assertThat(frame.getPixelWidth()).isCloseTo(WIDTH, within(1));
	}

	@Test
	void fit_copesWithASinglePositionAndWithNone() {
		ActionMapFrame single = ActionMapFrame.fit(Arrays.asList(new Point(5000, 5000)), WIDTH, HEIGHT);
		ActionMapFrame none = ActionMapFrame.fit(Collections.<Point>emptyList(), WIDTH, HEIGHT);

		assertThat(single.getScale().toPixelX(5000)).isCloseTo(WIDTH / 2, within(1));
		assertThat(none.getScale().toPixelLength(10000)).isGreaterThan(0);
	}
}
