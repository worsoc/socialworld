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
package org.socialworld.objects.concrete.containers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.objects.concrete.HumanCrafted;
import org.socialworld.objects.concrete.devotional.*;
import org.socialworld.objects.concrete.pastime.*;
import org.socialworld.objects.concrete.valuables.*;
import org.socialworld.objects.concrete.writing.*;
import org.socialworld.objects.properties.IContainer;
import org.socialworld.objects.properties.ICountable;
import org.socialworld.objects.properties.IPlayable;
import org.socialworld.objects.properties.IReadable;
import org.socialworld.objects.properties.ISacred;
import org.socialworld.objects.properties.IThrowable;
import org.socialworld.objects.properties.IValuable;
import org.socialworld.objects.properties.IWearable;
import org.socialworld.objects.properties.IWritingTool;
import org.socialworld.objects.statics.GetLexemIDLowerPartFromMapping;

/**
 * The 10 containers and the 12 other human crafted goods (valuables, devotional, writing, pastime) of the item
 * hierarchy (2026-11-23).
 */
class ContainersAndGoodsCatalogTest {

	/** class to the lower lexem value of its group */
	private static Map<Class<? extends HumanCrafted>, Integer> catalog() {
		Map<Class<? extends HumanCrafted>, Integer> c = new LinkedHashMap<>();
		List.of(Sack.class, Basket.class, Jug.class, Waterskin.class, Bucket.class, Purse.class, Satchel.class,
				Bowl.class, Pot.class, Flask.class).forEach(k -> c.put(k, 2048));
		List.of(Coins.class, Ring.class).forEach(k -> c.put(k, 4096));
		List.of(Rosary.class, Amulet.class, Relic.class).forEach(k -> c.put(k, 8192));
		List.of(Book.class, Scroll.class, Quill.class).forEach(k -> c.put(k, 16384));
		List.of(Lute.class, Flute.class, Dice.class, JugglingBalls.class).forEach(k -> c.put(k, 32768));
		return c;
	}

	private static HumanCrafted create(Class<? extends HumanCrafted> k) throws Exception {
		return k.getDeclaredConstructor().newInstance();
	}

	@Test
	void all22_areCreatedWithoutDatabase() throws Exception {
		assertThat(catalog()).hasSize(22);
		for (Class<? extends HumanCrafted> k : catalog().keySet()) {
			assertThat(create(k)).as(k.getSimpleName()).isNotNull();
		}
	}

	@Test
	void lexemLowerValue_isUniqueAndInItsGroup() {
		Set<Integer> seen = new HashSet<>();
		for (Map.Entry<Class<? extends HumanCrafted>, Integer> e : catalog().entrySet()) {
			int value = GetLexemIDLowerPartFromMapping.getForClassName(e.getKey().getName());
			assertThat(value).as(e.getKey().getSimpleName()).isBetween(e.getValue() + 16, e.getValue() + 240);
			assertThat(value % 16).as(e.getKey().getSimpleName() + " on a level of 16").isZero();
			assertThat(seen.add(value)).as(e.getKey().getSimpleName() + " unique").isTrue();
		}
	}

	@Test
	void everyContainer_holdsSomething() throws Exception {
		for (Class<? extends HumanCrafted> k : catalog().keySet()) {
			if (!Container.class.isAssignableFrom(k)) continue;
			IContainer c = (IContainer) create(k);
			assertThat(c.getCapacity()).as(k.getSimpleName()).isPositive();
		}
		assertThat(new Jug().isForLiquids()).isTrue();
		assertThat(new Waterskin().isForLiquids()).isTrue();
		assertThat(new Sack().isForLiquids()).isFalse();
		assertThat(new Purse().isForLiquids()).isFalse();
	}

	@Test
	void jewelleryAndBag_areWornWithoutAClothesPlace() {
		for (IWearable w : List.<IWearable>of(new Ring(), new Rosary(), new Amulet())) {
			assertThat(w.isJewellery()).as(w.getClass().getSimpleName()).isTrue();
			assertThat(w.isBag() || w.isCap() || w.isShirt() || w.isTrousers() || w.isDress() || w.isOvergarment())
					.as(w.getClass().getSimpleName()).isFalse();
		}
		Satchel satchel = new Satchel();
		assertThat(satchel.isBag()).isTrue();
		assertThat(satchel.isJewellery()).isFalse();
		assertThat(new Relic()).isNotInstanceOf(IWearable.class);
	}

	@Test
	void abilities_asInTheCatalogue() {
		assertThat(new Coins()).isInstanceOf(IValuable.class).isInstanceOf(ICountable.class);
		assertThat(new Coins().getCount()).isPositive();
		assertThat(new Relic()).isInstanceOf(ISacred.class).isInstanceOf(IValuable.class);
		assertThat(new Relic().getHoliness()).isGreaterThan(new Amulet().getHoliness());
		assertThat(new Book()).isInstanceOf(IReadable.class).isInstanceOf(IValuable.class);
		assertThat(new Book().getTextLength()).isGreaterThan(new Scroll().getTextLength());
		assertThat(new Quill()).isInstanceOf(IWritingTool.class);
		assertThat(new Lute()).isInstanceOf(IPlayable.class);
		assertThat(new Flute()).isInstanceOf(IPlayable.class);
		assertThat(new Dice()).isInstanceOf(IThrowable.class);
		assertThat(new Jug()).isInstanceOf(IThrowable.class);
	}
}
