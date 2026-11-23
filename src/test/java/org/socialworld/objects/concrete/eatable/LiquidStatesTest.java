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
package org.socialworld.objects.concrete.eatable;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.objects.concrete.StateAppearance;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.objects.concrete.StatePerceptible;

/**
 * A liquid gets the three states of every item (2026-11-23): before, Liquid.getInitState gave NoState, which
 * SimulationObject.createAddOnStates casts to StatePerceptible, so no water, beer or wine could be created.
 */
class LiquidStatesTest {

	@Test
	void water_givesTheThreeStates() {
		Water water = new Water();
		assertThat(water.getInitState(StatePerceptible.class.getName())).isInstanceOf(StatePerceptible.class);
		assertThat(water.getInitState(StateAppearance.class.getName())).isInstanceOf(StateAppearance.class);
		assertThat(water.getInitState(StateComposition.class.getName())).isInstanceOf(StateComposition.class);
	}
}
