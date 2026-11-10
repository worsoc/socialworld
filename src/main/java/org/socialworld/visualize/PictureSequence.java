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

import java.awt.Color;

/**
 * The picture sequence of the old display for the action grid: a heart, then MANU I LOVE U.
 * Just for fun, started by the main method of SimDashboard without a simulation.
 * Figures and timing are taken over from the former display SimVisual.
 */
final class PictureSequence {

	static final int TICK_MILLIS = 100;

	private static final int COLUMNS = ActionGridPanel.COLUMNS;

	enum Figure {
		BLANK(Color.WHITE),
		HEART(Color.RED,
				"..........",
				"..........",
				".##....##.",
				"####..####",
				"##########",
				".########.",
				"..######..",
				"...####...",
				"....##....",
				".........."),
		M(Color.BLACK,
				"..........",
				"..........",
				".##....##.",
				".#.#..#.#.",
				".#..##..#.",
				".#......#.",
				".#......#.",
				".#......#.",
				".#......#.",
				".........."),
		A(Color.BLACK,
				"..........",
				"....##....",
				"...#..#...",
				"..#....#..",
				"..#....#..",
				"..######..",
				"..#....#..",
				"..#....#..",
				"..#....#..",
				".........."),
		N(Color.BLACK,
				"..........",
				".##....#..",
				".#.#...#..",
				".#..#..#..",
				".#..#..#..",
				".#...#.#..",
				".#...#.#..",
				".#....##..",
				".#.....#..",
				".........."),
		U(Color.BLACK,
				"..........",
				".#.....#..",
				".#.....#..",
				".#.....#..",
				".#.....#..",
				".#....##..",
				".#....##..",
				"..#..#.#..",
				"...##..#..",
				".........."),
		I(Color.BLACK,
				"..........",
				"....##....",
				"....##....",
				"....##....",
				"....##....",
				"....##....",
				"....##....",
				"....##....",
				"....##....",
				".........."),
		L(Color.BLACK,
				"..........",
				"...#......",
				"...#......",
				"...#......",
				"...#......",
				"...#......",
				"...#......",
				"...#......",
				"...#####..",
				".........."),
		O(Color.BLACK,
				"..........",
				"....##....",
				"...#..#...",
				"..#....#..",
				".#......#.",
				".#......#.",
				"..#....#..",
				"...#..#...",
				"....##....",
				".........."),
		V(Color.BLACK,
				"..........",
				".#......#.",
				".#......#.",
				".#......#.",
				".#......#.",
				"..#....#..",
				"..#....#..",
				"...#..#...",
				"....##....",
				".........."),
		E(Color.BLACK,
				"..........",
				"..#####...",
				"..#.......",
				"..#.......",
				"..#####...",
				"..#.......",
				"..#.......",
				"..#.......",
				"..#####...",
				"..........");

		private final Color color;
		private final boolean[] fields = new boolean[VisualModel.OBJECT_COUNT];

		private Figure(Color color, String... rows) {
			this.color = color;
			for (int row = 0; row < rows.length; row++) {
				for (int column = 0; column < COLUMNS; column++) {
					fields[row * COLUMNS + column] = rows[row].charAt(column) == '#';
				}
			}
		}

		Color getColor() {
			return color;
		}

		/**
		 * @param field
		 *            the index of the field, row by row from the top left, starting at 0
		 */
		boolean isSet(int field) {
			return fields[field];
		}
	}

	private static final class Step {
		final Figure figure;
		final int ticks;

		Step(Figure figure, int ticks) {
			this.figure = figure;
			this.ticks = ticks;
		}
	}

	private static final Step[] STEPS = {
		new Step(Figure.BLANK, 5),
		new Step(Figure.HEART, 2),
		new Step(Figure.BLANK, 6),
		new Step(Figure.M, 2),
		new Step(Figure.BLANK, 1),
		new Step(Figure.A, 2),
		new Step(Figure.BLANK, 1),
		new Step(Figure.N, 2),
		new Step(Figure.BLANK, 1),
		new Step(Figure.U, 2),
		new Step(Figure.BLANK, 6),
		new Step(Figure.I, 2),
		new Step(Figure.BLANK, 6),
		new Step(Figure.L, 2),
		new Step(Figure.BLANK, 1),
		new Step(Figure.O, 2),
		new Step(Figure.BLANK, 1),
		new Step(Figure.V, 2),
		new Step(Figure.BLANK, 1),
		new Step(Figure.E, 2),
		new Step(Figure.BLANK, 5),
		new Step(Figure.U, 2),
		new Step(Figure.BLANK, 6)
	};

	private PictureSequence() {
	}

	static int cycleTicks() {
		int ticks = 0;
		for (Step step : STEPS) {
			ticks += step.ticks;
		}
		return ticks;
	}

	/**
	 * @param tick
	 *            counted from the start, the sequence repeats endlessly
	 */
	static Figure figureAt(int tick) {
		int rest = tick % cycleTicks();
		for (Step step : STEPS) {
			if (rest < step.ticks) return step.figure;
			rest -= step.ticks;
		}
		return Figure.BLANK;
	}
}
