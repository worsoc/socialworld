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
package org.socialworld.attributes.properties;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.conversation.Lexem;
import org.socialworld.core.IAccessToken;
import org.socialworld.datasource.mariaDB.DatabaseAssumptions;
import org.socialworld.datasource.tablesSimulation.propertySets.TableMaterialSet;

/**
 * The materials for the composition of the items (composition concept Z1, 2026-11-24): 43 values, nothing 0, then
 * sorted by groups without gaps, every name and index once. And the portion of a member is
 * found again (before, every case of the switch fell through to Taste).
 */
class MaterialTest {

	// a value property gives its value only with a token
	private static final IAccessToken TOKEN = new IAccessToken() {
		public boolean isValid() { return true; }
		public SimulationCluster getSimulationCluster() { return SimulationCluster.attributeArray; }
	};

	@Test
	void nothingIsZero_andTheGroupsFollowEachOther() {
		// sorted by groups since 2026-11-24: wood and metals, textiles, earth and stone, from animals and writing,
		// raw materials, food
		assertThat(Material.nothing.getIndex()).isZero();
		assertThat(Material.wood.getIndex()).isEqualTo(1);
		assertThat(Material.gold.getIndex()).isEqualTo(8);
		assertThat(Material.linen.getIndex()).isEqualTo(9);
		assertThat(Material.wax.getIndex()).isEqualTo(18);
		assertThat(Material.clay.getIndex()).isEqualTo(19);
		assertThat(Material.bone.getIndex()).isEqualTo(22);
		assertThat(Material.coal.getIndex()).isEqualTo(29);
		assertThat(Material.grain.getIndex()).isEqualTo(32);
		assertThat(Material.fat.getIndex()).isEqualTo(42);
		// the order of the enum is the order of the numbers
		for (Material material : Material.values()) assertThat(material.ordinal()).as(material.name()).isEqualTo(material.getIndex());
	}

	@Test
	void indices_runFrom0To42_withoutGaps_andGoBackAndForth() {
		Set<Integer> indices = new HashSet<>();
		for (Material material : Material.values()) {
			assertThat(indices.add(material.getIndex())).as(material.name()).isTrue();
			assertThat(Material.getName(material.getIndex())).isSameAs(material);
			assertThat(Material.fromName(material.name())).isSameAs(material);
		}
		assertThat(Material.values()).hasSize(43);
		assertThat(Material.getMaxIndex()).isEqualTo(42);
		for (int index = 0; index <= 42; index++) assertThat(indices).contains(index);
		assertThat(Material.getName(43)).isSameAs(Material.nothing);
		assertThat(Material.fromName("unobtainium")).isSameAs(Material.nothing);
	}

	@Test
	void theLexemIDs_stayInTheRangeOfTheMaterials() {
		for (Material material : Material.values()) {
			assertThat(material.getLexemID()).as(material.name())
					.isBetween(Lexem.OFFSET_LEXEMID_MATERIAL, Lexem.OFFSET_LEXEMID_MATERIAL + Lexem.LEXEMID_RANGE_PROPERTIES_1 - 1);
		}
	}

	@Test
	void thePortionOfAMaterial_isFound_andTheMainMaterialIsTheLargest() {
		MaterialSet set = new MaterialSet();
		set.add(Material.steel, 85);
		set.add(Material.leather, 8);
		set.add(Material.wood, 7);

		ValueProperty steel = set.getProperty(null, PropertyName.event_property_numeric_value, "portion_Material_steel");
		assertThat(steel.isValid()).isTrue();
		assertThat(steel.getObject(TOKEN, Type.integer)).isEqualTo(85);
		assertThat(set.getProperty(null, PropertyName.event_property_numeric_value, "portion_Material_wood").getObject(TOKEN, Type.integer)).isEqualTo(7);
		assertThat(set.getProperty(null, PropertyName.event_property_numeric_value, "portion_Material_gold").isValid()).isFalse();
		assertThat(set.getMain()).isSameAs(Material.steel);
	}

	@Test
	void theTableOfSets_givesNothingBehindItsLastPlace_insteadOfAnException() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// the table loads swset_material when it is created
		// before 2026-11-24 the check was <= and the ID 1000 ran out of the array
		assertThat(TableMaterialSet.getInstance().getMaterialSet(1000).checkIsObjectNothing()).isTrue();
		assertThat(TableMaterialSet.getInstance().getMaterialSet(-1).checkIsObjectNothing()).isTrue();
	}
}
