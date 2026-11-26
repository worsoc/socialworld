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

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.objects.Item;
import org.socialworld.objects.concrete.Food;
import org.socialworld.objects.concrete.eatable.bakery.*;
import org.socialworld.objects.concrete.eatable.dairy.*;
import org.socialworld.objects.concrete.eatable.fish.*;
import org.socialworld.objects.concrete.eatable.fruits.Berries;
import org.socialworld.objects.concrete.eatable.liquids.*;
import org.socialworld.objects.concrete.eatable.meat.*;
import org.socialworld.objects.concrete.eatable.sweets.*;
import org.socialworld.objects.concrete.eatable.veggies.*;
import org.socialworld.objects.properties.ICountable;
import org.socialworld.objects.properties.IDrinkable;
import org.socialworld.objects.properties.IEatable;
import org.socialworld.objects.properties.IHealing;
import org.socialworld.objects.properties.IPerishable;
import org.socialworld.objects.properties.IThrowable;
import org.socialworld.objects.properties.IValuable;
import org.socialworld.objects.statics.GetLexemIDHigherPartFromMapping;
import org.socialworld.objects.statics.GetLexemIDLowerPartFromMapping;

/**
 * The 11 kinds of food and the 2 new drinks of the item hierarchy (2026-11-23).
 */
class FoodAndDrinksCatalogTest {

	/** class to the lower lexem value of its group (food groups; liquid 256) */
	private static Map<Class<? extends Item>, Integer> catalog() {
		Map<Class<? extends Item>, Integer> c = new LinkedHashMap<>();
		c.put(Berries.class, 256);
		List.of(Turnip.class, Onion.class, Root.class).forEach(k -> c.put(k, 512));
		List.of(Sausage.class, Ham.class).forEach(k -> c.put(k, 1024));
		c.put(DriedFish.class, 2048);
		List.of(Bread.class, Cake.class).forEach(k -> c.put(k, 4096));
		c.put(Cheese.class, 8192);
		c.put(Honey.class, 16384);
		List.of(Beer.class, Wine.class).forEach(k -> c.put(k, 256));
		return c;
	}

	private static Item create(Class<? extends Item> k) throws Exception {
		return k.getDeclaredConstructor().newInstance();
	}

	@Test
	void all13_areCreatedWithoutDatabase_foodIsEatable_drinksAreDrinkable() throws Exception {
		assertThat(catalog()).hasSize(13);
		for (Class<? extends Item> k : catalog().keySet()) {
			Item item = create(k);
			if (item instanceof Liquid) {
				assertThat(item).as(k.getSimpleName()).isInstanceOf(IDrinkable.class).isNotInstanceOf(Food.class);
			} else {
				assertThat(item).as(k.getSimpleName()).isInstanceOf(Food.class).isInstanceOf(IEatable.class);
			}
		}
	}

	@Test
	void lexemLowerValue_isUniqueAndInItsGroup() {
		Set<Integer> seen = new HashSet<>();
		for (Map.Entry<Class<? extends Item>, Integer> e : catalog().entrySet()) {
			int value = GetLexemIDLowerPartFromMapping.getForClassName(e.getKey().getName());
			assertThat(value).as(e.getKey().getSimpleName()).isBetween(e.getValue() + 16, e.getValue() + 240);
			assertThat(value % 16).as(e.getKey().getSimpleName() + " on a level of 16").isZero();
			// unique together with the higher part: berries (food 45) and wine (liquid 41) may share 304
			int higher = GetLexemIDHigherPartFromMapping.getForClassName(e.getKey().getName());
			assertThat(seen.add(higher * 100000 + value)).as(e.getKey().getSimpleName() + " unique").isTrue();
		}
	}

	@Test
	void foodValues_areInRange() throws Exception {
		for (Class<? extends Item> k : catalog().keySet()) {
			Item item = create(k);
			if (!(item instanceof IEatable)) continue;
			IEatable food = (IEatable) item;
			assertThat(food.getConsistence()).as(k.getSimpleName()).isBetween(0f, 100f);
			assertThat(food.getFirmness()).as(k.getSimpleName()).isBetween(0f, 100f);
		}
	}

	@Test
	void perishable_allFoodButHoney() throws Exception {
		for (Class<? extends Item> k : catalog().keySet()) {
			Item item = create(k);
			if (!(item instanceof Food)) continue;
			if (item instanceof Honey) {
				assertThat(item).isNotInstanceOf(IPerishable.class);
			} else {
				assertThat(item).as(k.getSimpleName()).isInstanceOf(IPerishable.class);
				assertThat(((IPerishable) item).getShelfLife()).as(k.getSimpleName()).isPositive();
			}
		}
		assertThat(new Bread().getShelfLife()).isLessThan(new DriedFish().getShelfLife());
	}

	@Test
	void otherAbilities_asInTheCatalogue() {
		assertThat(new Berries()).isInstanceOf(ICountable.class);
		assertThat(new Turnip()).isInstanceOf(IThrowable.class);
		assertThat(new Honey()).isInstanceOf(IHealing.class).isInstanceOf(IValuable.class);
		assertThat(new Wine()).isInstanceOf(IValuable.class);
		assertThat(new Beer()).isInstanceOf(IPerishable.class);
	}
}
