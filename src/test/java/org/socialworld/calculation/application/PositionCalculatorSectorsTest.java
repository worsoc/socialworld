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
package org.socialworld.calculation.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.geometry.Vector;

public class PositionCalculatorSectorsTest {

	// the search levels of a human
	private static final int LEVEL_BASE9 = 8;
	private static final int LEVEL_BASE25 = 5;

	private static Position at(int x, int y) {
		return new Position(PropertyName.simobj_position, new Vector(x, y, 0));
	}

	private static boolean sameSectors(Position a, Position b) {
		return PositionCalculator.isInSameSectors(a, b, LEVEL_BASE9, LEVEL_BASE25);
	}

	@Test
	void thePlaceItselfIsInTheSameSectors() {
		assertThat(sameSectors(at(1200, -3400), at(1200, -3400))).isTrue();
	}

	@Test
	void aSmallStepStaysInTheSameSectors() {
		assertThat(sameSectors(at(0, 0), at(100, 100))).isTrue();
	}

	@Test
	void aChangeOfTheHeightStaysInTheSameSectors() {
		Position high = new Position(PropertyName.simobj_position, new Vector(0, 0, 900));

		assertThat(sameSectors(at(0, 0), high)).isTrue();
	}

	@Test
	void aStepAcrossABorderOfTheBase9TreeChangesTheSectors() {
		// the sectors of base 9 at level 8 are 1 m wide, a border lies at x = 500
		assertThat(sameSectors(at(400, 0), at(600, 0))).isFalse();
	}

	@Test
	void aStepAlongTheYAxisChangesTheSectorsToo() {
		assertThat(sameSectors(at(0, 400), at(0, 600))).isFalse();
	}

	@Test
	void aLongWayChangesTheSectors() {
		assertThat(sameSectors(at(-25000, -37000), at(76000, 75000))).isFalse();
	}

	@Test
	void comparesOnlyDownToTheLevels() {
		Position a = at(400, 0);
		Position b = at(600, 0);

		assertThat(PositionCalculator.isInSameSectors(a, b, 7, 5)).isTrue();
		assertThat(PositionCalculator.isInSameSectors(a, b, 8, 5)).isFalse();
	}

	@Test
	void comparesTheBase25TreeAsWell() {
		// x = 400 and x = 5000 lie in different sectors of base 25 at level 5, but both trees are coarse at level 1
		Position a = at(400, 0);
		Position b = at(5000, 0);

		assertThat(PositionCalculator.isInSameSectors(a, b, 1, 5)).isFalse();
		assertThat(PositionCalculator.isInSameSectors(a, b, 1, 1)).isTrue();
	}

	@Test
	void levelZeroAlwaysMeansTheSameSectors() {
		assertThat(PositionCalculator.isInSameSectors(at(-3000000, 0), at(3000000, 0), 0, 0)).isTrue();
	}
}
