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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;
import org.socialworld.datasource.mariaDB.DatabaseAssumptions;
import org.socialworld.objects.concrete.containers.Sack;
import org.socialworld.objects.concrete.eatable.bakery.Bread;

/**
 * What an object carries, holds or is carried by is read only with a token (P5, 2026-11-24): the cluster inventory
 * for all four questions, the cluster act (Action Creation Tool) for isCarried and getContents, the cluster visualize
 * (inventory window, 2026-11-24) for getContents. Any other token,
 * an invalid one or none gets the neutral answer: false, nothing, an empty list.
 */
class ReadConnectionsTest {

	private static IAccessToken token(boolean valid, SimulationCluster cluster) {
		return new IAccessToken() {
			public boolean isValid() { return valid; }
			public SimulationCluster getSimulationCluster() { return cluster; }
		};
	}

	private static final IAccessToken INVENTORY = token(true, SimulationCluster.inventory);
	private static final IAccessToken ACT = token(true, SimulationCluster.act);
	private static final IAccessToken VISUALIZE = token(true, SimulationCluster.visualize);
	private static final IAccessToken POSITION = token(true, SimulationCluster.position);
	private static final IAccessToken INVALID = token(false, SimulationCluster.inventory);

	private final Sack sack = new Sack();
	private final Bread bread = new Bread();

	@BeforeEach
	void putTheBreadIntoTheSack() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		new WriteAccessToItem(sack, new StateItem());
		new WriteAccessToItem(bread, new StateItem());
		sack.putInside(INVENTORY, bread);
	}

	@Test
	void theClusterInventory_getsAllFourAnswers() {
		assertThat(bread.isCarried(INVENTORY)).isTrue();
		assertThat(bread.getCarrier(INVENTORY)).isSameAs(sack);
		assertThat(bread.isInside(INVENTORY)).isTrue();
		assertThat(sack.getContents(INVENTORY)).containsExactly(bread);
	}

	@Test
	void theClusterAct_seesWhetherItIsFree_andWhatIsInside_notMore() {
		assertThat(bread.isCarried(ACT)).isTrue();
		assertThat(sack.getContents(ACT)).containsExactly(bread);
		assertThat(bread.getCarrier(ACT).isSimulationObject()).isFalse();
		assertThat(bread.isInside(ACT)).isFalse();
	}

	@Test
	void theClusterVisualize_seesOnlyWhatIsInside() {
		assertThat(sack.getContents(VISUALIZE)).containsExactly(bread);
		assertThat(bread.isCarried(VISUALIZE)).isFalse();
		assertThat(bread.getCarrier(VISUALIZE).isSimulationObject()).isFalse();
		assertThat(bread.isInside(VISUALIZE)).isFalse();
	}

	@Test
	void anotherCluster_anInvalidToken_orNone_getTheNeutralAnswer() {
		for (IAccessToken other : new IAccessToken[] {POSITION, INVALID, null}) {
			assertThat(bread.isCarried(other)).isFalse();
			assertThat(bread.getCarrier(other).isSimulationObject()).isFalse();
			assertThat(bread.isInside(other)).isFalse();
			assertThat(sack.getContents(other)).isEmpty();
		}
	}

	@Test
	void theListOfContents_isACopy() {
		sack.getContents(INVENTORY).clear();
		assertThat(sack.getContents(INVENTORY)).containsExactly(bread);
	}
}
