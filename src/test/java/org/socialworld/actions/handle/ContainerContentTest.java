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
package org.socialworld.actions.handle;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.concrete.containers.Basket;
import org.socialworld.objects.concrete.containers.Jug;
import org.socialworld.objects.concrete.containers.Purse;
import org.socialworld.objects.concrete.containers.Sack;
import org.socialworld.objects.concrete.eatable.Water;
import org.socialworld.objects.concrete.eatable.bakery.Bread;
import org.socialworld.objects.concrete.eatable.liquids.Beer;
import org.socialworld.objects.concrete.eatable.liquids.Wine;
import org.socialworld.objects.concrete.tools.Hammer;
import org.socialworld.objects.concrete.tools.Knife;
import org.socialworld.objects.concrete.tools.Pitchfork;
import org.socialworld.objects.concrete.valuables.Coins;

/**
 * What fits into a container (inventory concept V5, 2026-11-23): liquids, space by carry size, capacity.
 */
class ContainerContentTest {

	private static List<SimulationObject> list(SimulationObject... objects) {
		List<SimulationObject> result = new ArrayList<>();
		for (SimulationObject o : objects) result.add(o);
		return result;
	}

	@Test
	void aLiquid_fillsAnEmptyContainerForLiquids_only() {
		assertThat(ContainerContent.isLiquid(new Beer())).isTrue();
		assertThat(ContainerContent.isLiquid(new Bread())).isFalse();
		assertThat(ContainerContent.canHold(new Jug(), list(), new Beer())).isTrue();
		assertThat(ContainerContent.canHold(new Jug(), list(new Wine()), new Beer())).isFalse();
		assertThat(ContainerContent.canHold(new Sack(), list(), new Water())).isFalse();	// a sack is not for liquids
	}

	@Test
	void noThingsTogetherWithALiquid() {
		assertThat(ContainerContent.canHold(new Jug(), list(new Beer()), new Coins())).isFalse();
	}

	@Test
	void space_bySize_withinTheCapacity() {
		assertThat(ContainerContent.spaceOf(new Knife())).isEqualTo(300f);
		assertThat(ContainerContent.spaceOf(new Hammer())).isEqualTo(2000f);
		assertThat(ContainerContent.spaceOf(new Pitchfork())).isEqualTo(15000f);
		assertThat(ContainerContent.spaceOf(new Beer())).isZero();
		// the purse (300 ml) holds one handful of coins, not two
		assertThat(ContainerContent.canHold(new Purse(), list(), new Coins())).isTrue();
		assertThat(ContainerContent.canHold(new Purse(), list(new Coins()), new Coins())).isFalse();
		// the basket (20 l) holds a hammer and bread, but no pitchfork on top of 3 hammers
		assertThat(ContainerContent.canHold(new Basket(), list(new Hammer()), new Bread())).isTrue();
		assertThat(ContainerContent.canHold(new Basket(), list(new Hammer(), new Hammer(), new Hammer()), new Pitchfork())).isFalse();
	}

	@Test
	void noContainer_notItself_notTwice() {
		Basket basket = new Basket();
		Bread bread = new Bread();
		assertThat(ContainerContent.canHold(new Hammer(), list(), bread)).isFalse();
		assertThat(ContainerContent.canHold(basket, list(), basket)).isFalse();
		assertThat(ContainerContent.canHold(basket, list(bread), bread)).isFalse();
	}
}
