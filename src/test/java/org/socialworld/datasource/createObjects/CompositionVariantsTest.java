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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.properties.Material;
import org.socialworld.datasource.createObjects.CompositionCatalog.Part;
import org.socialworld.datasource.createObjects.CompositionCatalog.Recipe;
import org.socialworld.datasource.createObjects.CompositionVariants.Variant;

/**
 * The five variants of the composition of every class (composition concept Z2, K3, 2026-11-24): each adds up to 100,
 * every share stays in its spread, the alternatives take turns, and the same catalog gives the same sets.
 */
class CompositionVariantsTest {

	private static final CompositionCatalog CATALOG = CompositionCatalog.load();

	@Test
	void everyClass_getsFiveVariants_thatAddUpTo100_withinTheSpread() {
		for (String className : CATALOG.classNames()) {
			List<Recipe> recipes = CATALOG.get(className);
			List<Variant> variants = CompositionVariants.of(className, recipes);
			assertThat(variants).as(className).hasSize(CompositionVariants.VARIANTS);
			for (int number = 0; number < variants.size(); number++) {
				Variant variant = variants.get(number);
				Recipe recipe = recipes.get(number % recipes.size());
				int sum = 0;
				for (int i = 0; i < recipe.parts.size(); i++) {
					Part part = recipe.parts.get(i);
					int share = variant.shares.get(i);
					assertThat(variant.materials.get(i)).isSameAs(part.material);
					assertThat(share).as(className + " " + number + " " + part).isGreaterThanOrEqualTo(1);
					assertThat(Math.abs(share - part.share)).as(className + " " + number + " " + part).isLessThanOrEqualTo(part.spread);
					sum += share;
				}
				assertThat(sum).as(className + " " + number).isEqualTo(100);
			}
		}
	}

	@Test
	void theSameCatalog_givesTheSameSets() {
		for (String className : CATALOG.classNames()) {
			assertThat(CompositionVariants.of(className, CATALOG.get(className)))
					.as(className).isEqualTo(CompositionVariants.of(className, CATALOG.get(className)));
		}
	}

	@Test
	void alternatives_takeTurns() {
		// four alternatives: gold, silver, bronze, copper, then gold again
		List<Variant> rings = CompositionVariants.of("Ring", CATALOG.get("Ring"));
		assertThat(rings.get(0).materials).containsExactly(Material.gold);
		assertThat(rings.get(1).materials).containsExactly(Material.silver);
		assertThat(rings.get(2).materials).containsExactly(Material.bronze);
		assertThat(rings.get(3).materials).containsExactly(Material.copper);
		assertThat(rings.get(4).materials).containsExactly(Material.gold);
	}

	@Test
	void withASpread_theInstancesDiffer_withoutOne_theyDoNot() {
		Set<Variant> rags = new HashSet<>(CompositionVariants.of("Rags", CATALOG.get("Rags")));
		assertThat(rags.size()).isGreaterThan(1);
		Set<Variant> rope = new HashSet<>(CompositionVariants.of("Rope", CATALOG.get("Rope")));
		assertThat(rope).hasSize(1);
		assertThat(rope.iterator().next().share(Material.hemp)).isEqualTo(100);
	}

	@Test
	void aClassWithoutRecipe_hasNoVariants() {
		assertThat(CompositionVariants.of("Unknown", CATALOG.get("Unknown"))).isEmpty();
	}
}
