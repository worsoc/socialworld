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
import static org.assertj.core.api.Assertions.within;
import static org.socialworld.objects.ItemMaterialFormulasTest.withMaterials;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.properties.Material;
import org.socialworld.datasource.createObjects.CompositionCatalog;
import org.socialworld.datasource.mariaDB.DatabaseAssumptions;
import org.socialworld.objects.concrete.ConcreteItemClasses;
import org.socialworld.objects.concrete.clothes.caps.Crown;
import org.socialworld.objects.concrete.clothes.caps.Hat;
import org.socialworld.objects.concrete.clothes.overgarments.ChainMail;
import org.socialworld.objects.concrete.eatable.bakery.Bread;
import org.socialworld.objects.concrete.weapons.LongSword;
import org.socialworld.objects.properties.IBurnable;

/**
 * IBurnable is no marker any more (concept value and protection from the material, step 6, 2026-11-26): every item
 * class that can be made of something burnable implements it, Item answers getIgnitionProbability and canBurn from
 * the materials of the instance and their ignition temperature.
 */
class ItemBurningFromMaterialTest {

	@Test
	void everyItemClassWithABurnableMaterialInItsRecipe_isBurnable() throws Exception {
		CompositionCatalog catalog = CompositionCatalog.getInstance();
		List<String> missing = new ArrayList<>();
		for (Class<? extends Item> type : ConcreteItemClasses.find()) {
			if (!catalog.classNames().contains(type.getSimpleName())) continue;	// test classes
			boolean burnableMaterial = catalog.get(type.getSimpleName()).stream()
					.flatMap(recipe -> recipe.parts.stream()).anyMatch(part -> part.material.canBurn());
			if (burnableMaterial && !IBurnable.class.isAssignableFrom(type)) missing.add(type.getSimpleName());
		}
		assertThat(missing).isEmpty();
	}

	@Test
	void withoutAComposition_nothingBurns() {
		IBurnable hat = new Hat();

		assertThat(hat.canBurn()).isFalse();
		assertThat(hat.getIgnitionProbability(1000, 60)).isZero();
	}

	@Test
	void whetherAndHowEasilyItBurns_followsTheMaterialOfTheInstance() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		// a crown of gold is burnable as a cap, but never ignites
		IBurnable crown = (IBurnable) withMaterials(new Crown(), Material.gold, 100);
		assertThat(crown.canBurn()).isFalse();
		assertThat(crown.getIgnitionProbability(1000, 60)).isZero();

		// a straw hat catches fire before a felt hat
		IBurnable strawHat = (IBurnable) withMaterials(new Hat(), Material.straw, 95, Material.linen, 5);
		IBurnable feltHat = (IBurnable) withMaterials(new Hat(), Material.felt, 95, Material.linen, 5);
		assertThat(strawHat.canBurn()).isTrue();
		assertThat(feltHat.canBurn()).isTrue();
		assertThat(strawHat.getIgnitionProbability(300, 1)).isGreaterThan(feltHat.getIgnitionProbability(300, 1));

		// of a sword only the grip burns, of chain mail only the leather: never more than their share
		IBurnable sword = (IBurnable) withMaterials(new LongSword(), Material.steel, 85, Material.leather, 8, Material.wood, 7);
		IBurnable chainMail = (IBurnable) withMaterials(new ChainMail(), Material.iron, 95, Material.leather, 5);
		assertThat(sword.canBurn()).isTrue();
		assertThat(sword.getIgnitionProbability(2000, 3600)).isCloseTo(0.15, within(1e-6));
		assertThat(chainMail.getIgnitionProbability(2000, 3600)).isCloseTo(0.05, within(1e-6));

		// bread burns through its grain; the water and the salt do not
		IBurnable bread = (IBurnable) withMaterials(new Bread(), Material.grain, 66, Material.water, 31, Material.salt, 3);
		assertThat(bread.getIgnitionProbability(2000, 3600)).isCloseTo(0.66, within(1e-6));
	}
}
