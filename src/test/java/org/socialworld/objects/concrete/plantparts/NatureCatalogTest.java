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
package org.socialworld.objects.concrete.plantparts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.Item;
import org.socialworld.objects.concrete.Food;
import org.socialworld.objects.concrete.animalparts.AnimalPart;
import org.socialworld.objects.concrete.animalparts.Hide;
import org.socialworld.objects.concrete.solids.Charcoal;
import org.socialworld.objects.concrete.solids.Ore;
import org.socialworld.objects.concrete.solids.Solid;
import org.socialworld.objects.concrete.solids.Stone;
import org.socialworld.objects.properties.IBurnable;
import org.socialworld.objects.properties.ICountable;
import org.socialworld.objects.properties.IEatable;
import org.socialworld.objects.properties.IHealing;
import org.socialworld.objects.properties.IPerishable;
import org.socialworld.objects.properties.ISwimable;
import org.socialworld.objects.properties.IThrowable;
import org.socialworld.objects.properties.IValuable;
import org.socialworld.objects.properties.IWeapon;
import org.socialworld.objects.statics.GetLexemIDHigherPartFromMapping;
import org.socialworld.objects.statics.GetLexemIDLowerPartFromMapping;

/**
 * The 7 things of nature of the item hierarchy (2026-11-23): parts of plants and animals and solids, each its
 * own base group with its own higher lexem value.
 */
class NatureCatalogTest {

	/** class to the higher lexem value of its base group */
	private static Map<Class<? extends Item>, Integer> catalog() {
		Map<Class<? extends Item>, Integer> c = new LinkedHashMap<>();
		c.put(Branch.class, (int) GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_PLANTPART);
		c.put(Herb.class, (int) GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_PLANTPART);
		c.put(Flower.class, (int) GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_PLANTPART);
		c.put(Hide.class, (int) GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_ANIMALPART);
		c.put(Stone.class, (int) GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_SOLID);
		c.put(Charcoal.class, (int) GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_SOLID);
		c.put(Ore.class, (int) GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_SOLID);
		return c;
	}

	private static Item create(Class<? extends Item> k) throws Exception {
		return k.getDeclaredConstructor().newInstance();
	}

	@Test
	void all7_areCreatedWithoutDatabase_inTheirGroup() throws Exception {
		assertThat(catalog()).hasSize(7);
		assertThat(create(Branch.class)).isInstanceOf(PlantPart.class);
		assertThat(create(Hide.class)).isInstanceOf(AnimalPart.class);
		assertThat(create(Stone.class)).isInstanceOf(Solid.class);
	}

	@Test
	void lexemId_higherPartIsTheGroup_lowerPartUniqueInIt() {
		Set<Integer> seen = new HashSet<>();
		for (Map.Entry<Class<? extends Item>, Integer> e : catalog().entrySet()) {
			String name = e.getKey().getName();
			int higher = GetLexemIDHigherPartFromMapping.getForClassName(name);
			int lower = GetLexemIDLowerPartFromMapping.getForClassName(name);
			assertThat(higher).as(e.getKey().getSimpleName()).isEqualTo(e.getValue());
			assertThat(lower).as(e.getKey().getSimpleName()).isBetween(272, 496);
			assertThat(lower % 16).as(e.getKey().getSimpleName()).isZero();
			assertThat(seen.add(higher * 100000 + lower)).as(e.getKey().getSimpleName() + " unique").isTrue();
		}
	}

	@Test
	void branchAndStone_canBeWeaponsAndThrown_onlyTheBranchFloats() {
		for (Item item : new Item[] {new Branch(), new Stone()}) {
			assertThat(item).isInstanceOf(IWeapon.class).isInstanceOf(IThrowable.class);
			IWeapon w = (IWeapon) item;
			assertThat(w.getClubProperties()).isNotNull();
			assertThat(w.getClubProperties().getSharpness()).isZero();
		}
		assertThat(new Branch()).isInstanceOf(ISwimable.class).isInstanceOf(IBurnable.class);
		assertThat(new Stone()).isNotInstanceOf(ISwimable.class);
	}

	@Test
	void herb_isEatableWithoutBeingFood() {
		Herb herb = new Herb();
		assertThat(herb).isInstanceOf(IEatable.class).isNotInstanceOf(Food.class);
		assertThat(herb).isInstanceOf(IHealing.class).isInstanceOf(IPerishable.class);
	}

	@Test
	void otherAbilities_asInTheCatalogue() {
		assertThat(new Flower()).isInstanceOf(IPerishable.class);
		assertThat(new Hide()).isInstanceOf(IValuable.class).isInstanceOf(IBurnable.class);
		assertThat(new Charcoal()).isInstanceOf(IBurnable.class).isInstanceOf(ICountable.class);
		assertThat(new Ore()).isInstanceOf(IValuable.class).isInstanceOf(ICountable.class);
	}
}
