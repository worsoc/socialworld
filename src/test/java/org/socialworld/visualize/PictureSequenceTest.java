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

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.visualize.PictureSequence.Figure;

public class PictureSequenceTest {

	private int countFields(Figure figure) {
		int count = 0;
		for (int field = 0; field < VisualModel.OBJECT_COUNT; field++) {
			if (figure.isSet(field)) count++;
		}
		return count;
	}

	@Test
	void blank_hasNoFields() {
		assertThat(countFields(Figure.BLANK)).isZero();
	}

	@Test
	void everyFigure_hasFields() {
		for (Figure figure : Figure.values()) {
			if (figure != Figure.BLANK) {
				assertThat(countFields(figure)).as(figure.name()).isGreaterThan(0);
			}
		}
	}

	@Test
	void heart_isRedAndLiesWhereItLayInTheOldDisplay() {
		assertThat(Figure.HEART.getColor()).isEqualTo(Color.RED);
		assertThat(countFields(Figure.HEART)).isEqualTo(42);
		assertThat(Figure.HEART.isSet(21)).isTrue();
		assertThat(Figure.HEART.isSet(84)).isTrue();
		assertThat(Figure.HEART.isSet(0)).isFalse();
		assertThat(Figure.HEART.isSet(24)).isFalse();
	}

	@Test
	void letters_areBlack() {
		assertThat(Figure.M.getColor()).isEqualTo(Color.BLACK);
		assertThat(Figure.E.getColor()).isEqualTo(Color.BLACK);
	}

	@Test
	void cycle_lastsSixtyTwoTicks() {
		assertThat(PictureSequence.cycleTicks()).isEqualTo(62);
	}

	@Test
	void figureAt_startsWithAPauseAndThenTheHeart() {
		assertThat(PictureSequence.figureAt(0)).isEqualTo(Figure.BLANK);
		assertThat(PictureSequence.figureAt(4)).isEqualTo(Figure.BLANK);
		assertThat(PictureSequence.figureAt(5)).isEqualTo(Figure.HEART);
		assertThat(PictureSequence.figureAt(6)).isEqualTo(Figure.HEART);
		assertThat(PictureSequence.figureAt(7)).isEqualTo(Figure.BLANK);
	}

	@Test
	void figureAt_repeatsAfterTheCycle() {
		assertThat(PictureSequence.figureAt(PictureSequence.cycleTicks() + 5)).isEqualTo(Figure.HEART);
		assertThat(PictureSequence.figureAt(PictureSequence.cycleTicks())).isEqualTo(Figure.BLANK);
	}

	@Test
	void figures_comeInTheOrderOfTheMessage() {
		List<Figure> shown = new ArrayList<Figure>();
		Figure last = Figure.BLANK;
		for (int tick = 0; tick < PictureSequence.cycleTicks(); tick++) {
			Figure figure = PictureSequence.figureAt(tick);
			if (figure != last && figure != Figure.BLANK) shown.add(figure);
			last = figure;
		}

		assertThat(shown).containsExactly(Figure.HEART, Figure.M, Figure.A, Figure.N, Figure.U,
				Figure.I, Figure.L, Figure.O, Figure.V, Figure.E, Figure.U);
	}

	@Test
	void grid_hasNoToolTipWhileShowingAFigure() {
		ActionGridPanel grid = new ActionGridPanel();
		grid.setSize(1000, 1000);
		grid.setState(new VisualModel().snapshot(), 1);
		grid.setFigure(Figure.HEART);

		assertThat(grid.getToolTipText(new MouseEvent(grid, MouseEvent.MOUSE_MOVED, 0, 0, 50, 50, 0, false))).isNull();
	}
}
