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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.BodyBuild;
import org.socialworld.attributes.properties.Material;
import org.socialworld.datasource.createObjects.CompositionCatalog.Part;
import org.socialworld.datasource.createObjects.CompositionCatalog.Recipe;
import org.socialworld.objects.Item;
import org.socialworld.objects.concrete.ConcreteItemClasses;

/**
 * The catalog of the typical composition (composition concept Z2, 2026-11-24): composition.csv has a line for every
 * concrete item class, every recipe adds up to 100 with known materials, and lines with a mistake are named.
 */
class CompositionCatalogTest {

	@Test
	void theCatalog_isReadWithoutProblems() {
		assertThat(CompositionCatalog.load().getProblems()).isEmpty();
	}

	@Test
	void everyConcreteItemClass_hasItsComposition_andTheCatalogNoOther() throws Exception {
		CompositionCatalog catalog = CompositionCatalog.load();
		List<String> missing = new ArrayList<>();
		List<String> classes = new ArrayList<>();
		for (Class<? extends Item> type : ConcreteItemClasses.find()) {
			classes.add(type.getSimpleName());
			if (catalog.get(type.getSimpleName()).isEmpty()) missing.add(type.getSimpleName());
		}
		assertThat(missing).isEmpty();
		List<String> itemNames = new ArrayList<>();
		for (String name : catalog.classNames()) {
			if (!CompositionCatalog.isHumanKey(name)) itemNames.add(name);
		}
		assertThat(classes).containsAll(itemNames);
		// exactly the concrete item classes (until 2026-11-25 a fixed "at least 107", which the merges of the clothes change)
		assertThat(itemNames).hasSameSizeAs(classes);
	}

	// body build concept B2 (2026-11-25): the humans are in the catalog as Human.<build>
	@Test
	void everyBodyBuild_hasItsRecipe_andNoOtherHumanIsThere() {
		CompositionCatalog catalog = CompositionCatalog.load();
		List<String> humanKeys = new ArrayList<>();
		for (String name : catalog.classNames()) {
			if (CompositionCatalog.isHumanKey(name)) humanKeys.add(name);
		}
		List<String> expected = new ArrayList<>();
		for (BodyBuild build : BodyBuild.values()) expected.add(CompositionCatalog.humanKey(build));
		assertThat(humanKeys).containsExactlyInAnyOrderElementsOf(expected);
		assertThat(CompositionCatalog.humanKey(BodyBuild.stout)).isEqualTo("Human.stout");
		Recipe stout = catalog.get("Human.stout").get(0);
		assertThat(stout.parts.get(stout.indexOfMain()).material).as("the main of a human").isSameAs(Material.meat);
	}

	@Test
	void everyRecipe_addsUpTo100_withKnownMaterials() {
		CompositionCatalog catalog = CompositionCatalog.load();
		for (String className : catalog.classNames()) {
			for (Recipe recipe : catalog.get(className)) {
				int sum = 0;
				for (Part part : recipe.parts) {
					assertThat(part.material).as(className).isNotSameAs(Material.nothing);
					assertThat(part.spread).as(className + " " + part).isLessThanOrEqualTo(part.share);
					sum += part.share;
				}
				assertThat(sum).as(className + " " + recipe).isEqualTo(100);
			}
		}
	}

	@Test
	void alternatives_andSpreads_areRead() {
		CompositionCatalog catalog = CompositionCatalog.parse(Arrays.asList(
				"# a comment", "",
				"Bowl;wood:100|clay:100",
				"Shoe;leather:93+-3,linen:7±3"));
		assertThat(catalog.getProblems()).isEmpty();
		assertThat(catalog.get("Bowl")).hasSize(2);
		assertThat(catalog.get("Bowl").get(1).parts.get(0).material).isSameAs(Material.clay);
		Part linen = catalog.get("Shoe").get(0).parts.get(1);
		assertThat(linen.material).isSameAs(Material.linen);
		assertThat(linen.share).isEqualTo(7);
		assertThat(linen.spread).isEqualTo(3);
		assertThat(catalog.get("Shoe").get(0).indexOfMain()).isZero();
		assertThat(catalog.get("Unknown")).isEmpty();
	}

	@Test
	void linesWithAMistake_areLeftOut_andNamed() {
		CompositionCatalog catalog = CompositionCatalog.parse(Arrays.asList(
				"Dragon;wood:100",
				"Bowl;unobtainium:100",
				"Jug;clay:90",
				"Rope;hemp:x",
				"Staff",
				"Oar;wood:100",
				"Oar;wood:100",
				"Crook;wood:50,wood:50",
				"Human.giant;meat:100",
				"Human.wiry;meat:100"));
		assertThat(catalog.getProblems()).hasSize(8);
		assertThat(catalog.classNames()).containsExactly("Oar", "Human.wiry");
		assertThat(String.join("\n", catalog.getProblems()))
				.contains("unknown item class Dragon", "unknown material unobtainium", "add up to 90", "no number",
						"expected <class>;<recipes>", "Oar twice", "wood twice",
						"unknown body build giant");
	}
}
