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
package org.socialworld.datasource.createObjects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.properties.Material;
import org.socialworld.datasource.tablesSimulation.propertySets.TableKindMaterialSet;

/**
 * What the button "Material-Sets aus Katalog" writes (composition concept Z3, 2026-11-24): five sets per class from
 * ID 100 on, without gaps, each adding up to 100, and the coupling class + variant -> set. Only the plan; writing
 * it is the button's.
 */
class CompositionSetsTest {

	private static final CompositionCatalog CATALOG = CompositionCatalog.load();
	private static final CompositionSets PLAN = CompositionSets.plan(CATALOG);

	@Test
	void theSets_startAt100_withoutGaps_andFitIntoTheTable() {
		int classes = CATALOG.classNames().size();
		assertThat(PLAN.kinds).hasSize(classes * CompositionVariants.VARIANTS);
		assertThat(PLAN.kinds.get(0).setID).isEqualTo(CompositionSets.FIRST_SET_ID);
		for (int i = 0; i < PLAN.kinds.size(); i++) {
			assertThat(PLAN.kinds.get(i).setID).isEqualTo(CompositionSets.FIRST_SET_ID + i);
		}
		assertThat(PLAN.lastSetID).isEqualTo(CompositionSets.FIRST_SET_ID + PLAN.kinds.size() - 1);
		assertThat(PLAN.fits()).isTrue();
	}

	@Test
	void everySet_addsUpTo100_withLfdNrFrom1_andKnownMaterials() {
		Map<Integer, Integer> sums = new HashMap<>();
		Map<Integer, Integer> lastLfdNr = new HashMap<>();
		for (CompositionSets.SetRow row : PLAN.sets) {
			assertThat(Material.getName(row.material)).isNotSameAs(Material.nothing);
			assertThat(row.lfdNr).isEqualTo(lastLfdNr.getOrDefault(row.setID, 0) + 1);
			lastLfdNr.put(row.setID, row.lfdNr);
			sums.merge(row.setID, row.share, Integer::sum);
		}
		assertThat(sums).hasSize(PLAN.kinds.size());
		for (int sum : sums.values()) assertThat(sum).isEqualTo(100);
	}

	@Test
	void everyClass_isCoupledToFiveSetsOfItsOwn() {
		Map<String, Set<Integer>> setsByClass = new HashMap<>();
		Set<Integer> allSets = new HashSet<>();
		for (CompositionSets.KindRow row : PLAN.kinds) {
			setsByClass.computeIfAbsent(row.className, name -> new HashSet<>()).add(row.setID);
			assertThat(allSets.add(row.setID)).as("set " + row.setID + " only once").isTrue();
			assertThat(row.variant).isBetween(0, CompositionVariants.VARIANTS - 1);
		}
		assertThat(setsByClass.keySet()).isEqualTo(CATALOG.classNames());
		for (Set<Integer> ids : setsByClass.values()) assertThat(ids).hasSize(CompositionVariants.VARIANTS);
	}

	@Test
	void theFirstClassOfTheFile_isTheBeret_withItsFirstVariantInSet100() {
		assertThat(PLAN.kinds.get(0).className).isEqualTo("Beret");
		assertThat(PLAN.kinds.get(0).variant).isZero();
		assertThat(PLAN.sets.get(0).setID).isEqualTo(100);
		assertThat(PLAN.sets.get(0).material).isEqualTo(Material.wool.getIndex());
	}

	// body build concept B2 (2026-11-25): the humans stand at the end of the file, so the sets of the items keep
	// their IDs (100 .. 634) and the 25 sets of the humans follow
	@Test
	void theHumans_comeAfterAllItems() {
		int firstHuman = -1;
		for (int i = 0; i < PLAN.kinds.size(); i++) {
			boolean human = CompositionCatalog.isHumanKey(PLAN.kinds.get(i).className);
			if (human && firstHuman < 0) firstHuman = i;
			if (firstHuman >= 0) assertThat(human).as(PLAN.kinds.get(i).className + " after the humans").isTrue();
		}
		assertThat(firstHuman).isPositive();
		assertThat(PLAN.kinds.size() - firstHuman).isEqualTo(5 * CompositionVariants.VARIANTS);
		assertThat(PLAN.kinds.get(firstHuman).className).isEqualTo("Human.gaunt");
	}

	// TableKindMaterialSet.insert leaves out a name it does not accept, without a word: until 2026-11-25 the
	// dot of "Human.<build>" was not accepted, and swkind_materialset got no human
	@Test
	void everyNameOfThePlan_isAcceptedBySwkindMaterialset() {
		for (CompositionSets.KindRow row : PLAN.kinds) {
			assertThat(TableKindMaterialSet.isValidClassName(row.className)).as(row.className).isTrue();
		}
		assertThat(TableKindMaterialSet.isValidClassName("Beret'; DROP TABLE x; --")).isFalse();
		assertThat(TableKindMaterialSet.isValidClassName(null)).isFalse();
	}
}
