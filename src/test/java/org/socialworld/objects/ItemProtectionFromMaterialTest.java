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
import org.socialworld.attributes.properties.BlowKind;
import org.socialworld.attributes.properties.Material;
import org.socialworld.datasource.mariaDB.DatabaseAssumptions;
import org.socialworld.objects.concrete.clothes.caps.Helmet;
import org.socialworld.objects.concrete.clothes.Glove;
import org.socialworld.objects.concrete.clothes.gloves.Gauntlet;
import org.socialworld.objects.concrete.clothes.overgarments.Apron;
import org.socialworld.objects.concrete.clothes.overgarments.ChainMail;
import org.socialworld.objects.concrete.clothes.overgarments.Gambeson;
import org.socialworld.objects.concrete.clothes.shoes.Boot;
import org.socialworld.objects.concrete.weapons.Shield;
import org.socialworld.objects.properties.IProtective;

/**
 * The protective items take their protection from the material (concept value and protection from the material,
 * step 5, 2026-11-26): protection of the material x form factor of the class, per kind of blow, within 0 .. 100.
 * Gambeson and chain mail have a form factor per kind of blow; the apron is protective since then. The form factor
 * is protected in Item, so it is called through the type Item here.
 */
class ItemProtectionFromMaterialTest {

	/** The form factors of the table in the concept: stab, club, stroke, backhand. */
	private static Map<Item, float[]> formFactors() {
		Map<Item, float[]> factors = new LinkedHashMap<>();
		factors.put(new Helmet(), new float[] {0.9f, 0.9f, 0.9f, 0.9f});
		factors.put(new Gauntlet(), new float[] {0.7f, 0.7f, 0.7f, 0.7f});
		factors.put(new Glove(), new float[] {0.6f, 0.6f, 0.6f, 0.6f});
		factors.put(new Boot(), new float[] {0.45f, 0.45f, 0.45f, 0.45f});
		factors.put(new Shield(), new float[] {1.5f, 1.5f, 1.5f, 1.5f});
		factors.put(new Gambeson(), new float[] {2.5f, 3.2f, 2.5f, 2.5f});
		factors.put(new ChainMail(), new float[] {0.74f, 0.57f, 1.03f, 1.03f});
		factors.put(new Apron(), new float[] {0.8f, 0.8f, 0.8f, 0.8f});
		return factors;
	}

	private static float[] protection(IProtective item) {
		return new float[] {item.getProtectionAgainstStab(), item.getProtectionAgainstClub(),
				item.getProtectionAgainstStroke(), item.getProtectionAgainstBackhand()};
	}

	@Test
	void everyProtectiveItem_hasTheFormFactorsOfTheTable_andProtects0WithoutMaterial() {
		for (Map.Entry<Item, float[]> e : formFactors().entrySet()) {
			Item item = e.getKey();
			String name = item.getClass().getSimpleName();
			assertThat(item).as(name).isInstanceOf(IProtective.class);
			BlowKind[] blows = BlowKind.values();
			for (int i = 0; i < blows.length; i++)
				assertThat(item.getFormFactor(blows[i])).as(name + " " + blows[i]).isEqualTo(e.getValue()[i]);
			assertThat(protection((IProtective) item)).as(name + " without composition").containsOnly(0f);
		}
	}

	@Test
	void theProtection_followsTheMaterialAndTheForm() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		// chain mail keeps its profile of today: good against strokes, weak against the club
		float[] chainMail = protection((IProtective) withMaterials(new ChainMail(), Material.iron, 95, Material.leather, 5));
		assertThat(chainMail[0]).isCloseTo(50.1f, within(0.1f));
		assertThat(chainMail[1]).isCloseTo(30.4f, within(0.1f));
		assertThat(chainMail[2]).isCloseTo(79.8f, within(0.1f));
		assertThat(chainMail[3]).isCloseTo(59.7f, within(0.1f));

		// the gambeson takes the club best
		float[] gambeson = protection((IProtective) withMaterials(new Gambeson(), Material.linen, 70, Material.wool, 20, Material.hemp, 10));
		assertThat(gambeson[1]).isGreaterThan(gambeson[0]).isGreaterThan(gambeson[2]).isGreaterThan(gambeson[3]);

		// a woollen glove protects less than a leather one, and the gauntlet more than both
		IProtective leatherGlove = (IProtective) withMaterials(new Glove(), Material.leather, 95, Material.linen, 5);
		IProtective woolGlove = (IProtective) withMaterials(new Glove(), Material.wool, 100);
		IProtective gauntlet = (IProtective) withMaterials(new Gauntlet(), Material.iron, 80, Material.leather, 20);
		assertThat(woolGlove.getProtectionAgainstStroke()).isLessThan(leatherGlove.getProtectionAgainstStroke());
		assertThat(gauntlet.getProtectionAgainstStroke()).isGreaterThan(leatherGlove.getProtectionAgainstStroke());

		// the leather apron has its protection back, the linen apron a little
		IProtective leatherApron = (IProtective) withMaterials(new Apron(), Material.leather, 95, Material.linen, 5);
		IProtective linenApron = (IProtective) withMaterials(new Apron(), Material.linen, 90, Material.hemp, 10);
		assertThat(leatherApron.getProtectionAgainstStroke()).isCloseTo(23.3f, within(0.1f));
		assertThat(linenApron.getProtectionAgainstStroke()).isGreaterThan(0f).isLessThan(leatherApron.getProtectionAgainstStroke());

		// the shield protects more than the helmet against the club, the helmet more than the boot
		IProtective shield = (IProtective) withMaterials(new Shield(), Material.wood, 75, Material.iron, 15, Material.leather, 10);
		IProtective helmet = (IProtective) withMaterials(new Helmet(), Material.iron, 85, Material.leather, 15);
		IProtective boot = (IProtective) withMaterials(new Boot(), Material.leather, 90, Material.wood, 5, Material.iron, 5);
		assertThat(shield.getProtectionAgainstClub()).isGreaterThan(helmet.getProtectionAgainstClub());
		assertThat(helmet.getProtectionAgainstClub()).isGreaterThan(boot.getProtectionAgainstClub());
		for (float p : protection(shield)) assertThat(p).isBetween(0f, 100f);
	}
}
