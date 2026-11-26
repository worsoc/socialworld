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

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.properties.Material;
import org.socialworld.datasource.mariaDB.DatabaseAssumptions;
import org.socialworld.objects.concrete.animalparts.Hide;
import org.socialworld.objects.concrete.clothes.caps.Beret;
import org.socialworld.objects.concrete.clothes.caps.Crown;
import org.socialworld.objects.concrete.clothes.Dress;
import org.socialworld.objects.concrete.clothes.overgarments.ChainMail;
import org.socialworld.objects.concrete.clothes.overgarments.Cloak;
import org.socialworld.objects.concrete.clothes.Shirt;
import org.socialworld.objects.concrete.devotional.Amulet;
import org.socialworld.objects.concrete.devotional.Relic;
import org.socialworld.objects.concrete.eatable.liquids.Wine;
import org.socialworld.objects.concrete.eatable.sweets.Honey;
import org.socialworld.objects.concrete.pastime.Lute;
import org.socialworld.objects.concrete.solids.Ore;
import org.socialworld.objects.concrete.tools.Scales;
import org.socialworld.objects.concrete.valuables.Coins;
import org.socialworld.objects.concrete.valuables.Ring;
import org.socialworld.objects.concrete.valuables.SignetRing;
import org.socialworld.objects.concrete.weapons.LongSword;
import org.socialworld.objects.concrete.weapons.ShortSword;
import org.socialworld.objects.concrete.writing.Book;
import org.socialworld.objects.properties.IValuable;

/**
 * The valuable items take their value from the material (concept value and protection from the material, step 4,
 * 2026-11-26): value per kg of the material x craft factor of the class. Shirt, dress, cloak and amulet are
 * valuable since then. The craft factor is protected in Item, so it is called through the type Item here.
 */
class ItemValueFromMaterialTest {

	/** The craft factors of the table in the concept. */
	private static Map<Item, Float> craftFactors() {
		Map<Item, Float> factors = new LinkedHashMap<>();
		factors.put(new Ore(), 1f);
		factors.put(new Hide(), 1f);
		factors.put(new Honey(), 1f);
		factors.put(new Coins(), 1f);
		factors.put(new Wine(), 50f);
		factors.put(new Beret(), 2f);
		factors.put(new Shirt(), 2f);
		factors.put(new Cloak(), 2f);
		factors.put(new Dress(), 3f);
		factors.put(new Ring(), 2f);
		factors.put(new SignetRing(), 3f);
		factors.put(new Crown(), 4f);
		factors.put(new Amulet(), 3f);
		factors.put(new Scales(), 5f);
		factors.put(new Book(), 10f);
		factors.put(new ShortSword(), 15f);
		factors.put(new LongSword(), 20f);
		factors.put(new Relic(), 20f);
		factors.put(new ChainMail(), 30f);
		factors.put(new Lute(), 50f);
		return factors;
	}

	@Test
	void everyValuableItem_hasTheCraftFactorOfTheTable_andIsWorth0WithoutMaterial() {
		for (Map.Entry<Item, Float> e : craftFactors().entrySet()) {
			Item item = e.getKey();
			String name = item.getClass().getSimpleName();
			assertThat(item).as(name).isInstanceOf(IValuable.class);
			assertThat(item.getCraftFactor()).as(name).isEqualTo(e.getValue());
			assertThat(((IValuable) item).getValue()).as(name + " without composition").isZero();
		}
	}

	@Test
	void theValue_followsTheMaterialOfTheInstance() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		// the lost values come back through the material: silk and fur are worth more than linen and wool
		IValuable linenShirt = (IValuable) withMaterials(new Shirt(), Material.linen, 90, Material.cotton, 10);
		IValuable silkShirt = (IValuable) withMaterials(new Shirt(), Material.silk, 95, Material.linen, 5);
		assertThat(linenShirt.getValue()).isCloseTo(2 * (0.9f * 40 + 0.1f * 30), within(0.01f));
		assertThat(silkShirt.getValue()).isCloseTo(2 * (0.95f * 600 + 0.05f * 40), within(0.01f));
		IValuable woolCloak = (IValuable) withMaterials(new Cloak(), Material.wool, 90, Material.linen, 10);
		IValuable furCloak = (IValuable) withMaterials(new Cloak(), Material.fur, 70, Material.wool, 25, Material.leather, 5);
		assertThat(furCloak.getValue()).isGreaterThan(woolCloak.getValue());
		IValuable silkDress = (IValuable) withMaterials(new Dress(), Material.silk, 60, Material.linen, 40);
		IValuable linenDress = (IValuable) withMaterials(new Dress(), Material.linen, 95, Material.cotton, 5);
		assertThat(silkDress.getValue()).isGreaterThan(linenDress.getValue());

		// a gold ring is worth more than gold ore of the same mass, and a signet ring more than a ring
		IValuable goldRing = (IValuable) withMaterials(new Ring(), Material.gold, 100);
		IValuable goldSignet = (IValuable) withMaterials(new SignetRing(), Material.gold, 100);
		IValuable goldCoins = (IValuable) withMaterials(new Coins(), Material.gold, 100);
		assertThat(goldRing.getValue()).isCloseTo(20000f, within(0.01f));
		assertThat(goldSignet.getValue()).isGreaterThan(goldRing.getValue());
		assertThat(goldRing.getValue()).isGreaterThan(goldCoins.getValue());

		// the long sword is worth more than the short one of the same steel
		IValuable shortSword = (IValuable) withMaterials(new ShortSword(), Material.steel, 85, Material.leather, 8, Material.wood, 7);
		IValuable longSword = (IValuable) withMaterials(new LongSword(), Material.steel, 85, Material.leather, 8, Material.wood, 7);
		assertThat(longSword.getValue()).isGreaterThan(shortSword.getValue());

		// the relic becomes precious through its silver
		IValuable relic = (IValuable) withMaterials(new Relic(), Material.bone, 60, Material.silver, 25, Material.wood, 15);
		assertThat(relic.getValue()).isCloseTo(20 * (0.6f * 2 + 0.25f * 800 + 0.15f * 2), within(0.1f));
	}
}
