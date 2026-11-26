/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.properties.Material;
import org.socialworld.attributes.properties.MaterialSet;
import org.socialworld.datasource.mariaDB.DatabaseAssumptions;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenSimulationObject;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.objects.concrete.valuables.Ring;

/**
 * The protected getters of SimulationObject for the material shares (concept value and protection from the
 * material, step 2, 2026-11-26): the subclasses get the shares of the mass in percent, never the composition
 * state or its material set. The test lies in the package of SimulationObject, so it may call them.
 */
class SimulationObjectMaterialSharesTest {

	@Test
	void withoutAComposition_theSharesAreEmpty_andEveryShareIsZero() {
		Ring ring = new Ring();	// no states yet: the composition is the nothing state

		assertThat(ring.getMaterialShares()).isEmpty();
		assertThat(ring.getMaterialShare(Material.gold)).isZero();
		assertThat(ring.getMaterialShare(Material.nothing)).isZero();
	}

	@Test
	void theSharesOfTheInstance_areGiven_andCannotBeChanged() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		Ring ring = new Ring();
		WriteAccessToItem wa = new WriteAccessToItem(ring, new StateItem());
		MaterialSet set = new MaterialSet();
		set.add(Material.steel, 85);
		set.add(Material.leather, 8);
		set.add(Material.wood, 7);
		// as StartCreation does it for a new instance
		new HiddenSimulationObject(wa, new GrantedAccessToProperty[] {GrantedAccessToProperty.all})
				.setSomething(StateComposition.class.getName(), StateComposition.METHODNAME_SET_MATERIAL_SET, set);

		Map<Material, Integer> shares = ring.getMaterialShares();
		assertThat(shares).containsOnlyKeys(Material.steel, Material.leather, Material.wood);
		assertThat(shares.get(Material.steel)).isEqualTo(85);
		assertThat(ring.getMaterialShare(Material.leather)).isEqualTo(8);
		assertThat(ring.getMaterialShare(Material.wood)).isEqualTo(7);
		assertThat(ring.getMaterialShare(Material.gold)).isZero();
		assertThat(shares.values().stream().mapToInt(Integer::intValue).sum()).isEqualTo(100);

		// the map is a copy that cannot be changed
		assertThatThrownBy(() -> shares.put(Material.gold, 100)).isInstanceOf(UnsupportedOperationException.class);
		assertThat(ring.getMaterialShare(Material.gold)).isZero();
	}
}
