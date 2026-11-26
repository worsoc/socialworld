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
package org.socialworld.objects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;
import org.socialworld.objects.concrete.containers.Sack;
import org.socialworld.objects.concrete.eatable.bakery.Bread;

/**
 * Only the rail inventory writes the contents of a container (protection concept S2, 2026-11-24): putInside and
 * takeOut need a valid token of the cluster inventory. Any other caller, e.g. the AttributeCalculator, changes
 * nothing. The objects here have no state; the check of the token comes first.
 */
class ContainerAccessTest {

	private static IAccessToken token(boolean valid, SimulationCluster cluster) {
		return new IAccessToken() {
			public boolean isValid() { return valid; }
			public SimulationCluster getSimulationCluster() { return cluster; }
		};
	}

	@Test
	void putInside_withoutATokenOfTheClusterInventory_isRefused() {
		Sack sack = new Sack();
		Bread bread = new Bread();
		assertThat(sack.putInside(null, bread)).isFalse();
		assertThat(sack.putInside(token(true, SimulationCluster.attributeArray), bread)).isFalse();
		assertThat(sack.putInside(token(true, SimulationCluster.position), bread)).isFalse();
		assertThat(sack.putInside(token(true, SimulationCluster.core), bread)).isFalse();
		assertThat(sack.putInside(token(false, SimulationCluster.inventory), bread)).isFalse();
	}

	@Test
	void takeOut_withoutATokenOfTheClusterInventory_isRefused() {
		Sack sack = new Sack();
		Bread bread = new Bread();
		assertThat(sack.takeOut(null, bread)).isFalse();
		assertThat(sack.takeOut(token(true, SimulationCluster.attributeArray), bread)).isFalse();
		assertThat(sack.takeOut(token(false, SimulationCluster.inventory), bread)).isFalse();
	}
}
