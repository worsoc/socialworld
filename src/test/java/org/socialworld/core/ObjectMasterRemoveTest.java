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
package org.socialworld.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.SimulationCluster;

/**
 * Taking objects out of the simulation belongs to the ObjectMaster (protection concept S1, 2026-11-24). For now only
 * a valid token of the cluster inventory may do it; the cluster position no longer may.
 */
class ObjectMasterRemoveTest {

	private static IAccessToken token(boolean valid, SimulationCluster cluster) {
		return new IAccessToken() {
			public boolean isValid() { return valid; }
			public SimulationCluster getSimulationCluster() { return cluster; }
		};
	}

	@Test
	void theClusterInventory_mayRemove() {
		assertThat(ObjectMaster.mayRemove(token(true, SimulationCluster.inventory))).isTrue();
	}

	@Test
	void otherClusters_mayNotRemove() {
		assertThat(ObjectMaster.mayRemove(token(true, SimulationCluster.position))).isFalse();
		assertThat(ObjectMaster.mayRemove(token(true, SimulationCluster.attributeArray))).isFalse();
		assertThat(ObjectMaster.mayRemove(token(true, SimulationCluster.core))).isFalse();
		assertThat(ObjectMaster.mayRemove(token(true, SimulationCluster.act))).isFalse();
	}

	@Test
	void anInvalidToken_orNone_mayNotRemove() {
		assertThat(ObjectMaster.mayRemove(token(false, SimulationCluster.inventory))).isFalse();
		assertThat(ObjectMaster.mayRemove(null)).isFalse();
	}
}
