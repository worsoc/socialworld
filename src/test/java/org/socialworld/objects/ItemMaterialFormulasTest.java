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

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.properties.BlowKind;
import org.socialworld.attributes.properties.Material;
import org.socialworld.attributes.properties.MaterialSet;
import org.socialworld.datasource.mariaDB.DatabaseAssumptions;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenSimulationObject;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.objects.concrete.valuables.Ring;

/**
 * The formulas of Item for value, protection and burning (concept value and protection from the material,
 * step 3, 2026-11-26): the material gives the base values, the class its factors, and a class may override the
 * formulas. The test lies in the package of Item, so it may call the protected methods.
 */
class ItemMaterialFormulasTest {

	/** A ring with the craft factor 3 instead of its own 2. */
	static class CraftedRing extends Ring {
		@Override
		protected float getCraftFactor() { return 3f; }
	}

	/** Made like chain mail: a form factor per kind of blow. */
	static class RingsOfIron extends Ring {
		@Override
		protected float getFormFactor(BlowKind blow) {
			switch (blow) {
			case stab: return 0.74f;
			case club: return 0.57f;
			default: return 1.03f;
			}
		}
	}

	/** One form factor for every blow, so high that the protection is cut at 100. */
	static class ThickRing extends Ring {
		@Override
		protected float getFormFactor() { return 2f; }
	}

	/** A wider S curve. */
	static class SlowBurningRing extends Ring {
		@Override
		protected float getIgnitionCurveWidth() { return 20f; }
	}

	/** Gives the item a state and the material set, as StartCreation does it for a new instance. */
	static <T extends Item> T withMaterials(T item, Object... materialsAndShares) {
		WriteAccessToItem wa = new WriteAccessToItem(item, new StateItem());
		MaterialSet set = new MaterialSet();
		for (int i = 0; i < materialsAndShares.length; i += 2)
			set.add((Material) materialsAndShares[i], (Integer) materialsAndShares[i + 1]);
		new HiddenSimulationObject(wa, new GrantedAccessToProperty[] {GrantedAccessToProperty.all})
				.setSomething(StateComposition.class.getName(), StateComposition.METHODNAME_SET_MATERIAL_SET, set);
		return item;
	}

	@Test
	void theSCurve_givesTheValuesOfTheConcept_andNothingBelowTheCutoff() {
		Ring ring = new Ring();	// the curve per material needs no composition
		float tz = Material.wood.getIgnitionTemperature();	// 300

		assertThat(ring.getIgnitionProbabilityPerSecond(Material.wood, tz)).isCloseTo(0.5, within(1e-9));
		assertThat(ring.getIgnitionProbabilityPerSecond(Material.wood, tz - 10)).isCloseTo(0.269, within(0.001));
		assertThat(ring.getIgnitionProbabilityPerSecond(Material.wood, tz - 30)).isCloseTo(0.047, within(0.001));
		assertThat(ring.getIgnitionProbabilityPerSecond(Material.wood, tz - 50)).isCloseTo(0.0067, within(0.0001));
		assertThat(ring.getIgnitionProbabilityPerSecond(Material.wood, tz - 50.5f)).isZero();
		assertThat(ring.getIgnitionProbabilityPerSecond(Material.wood, tz + 20)).isCloseTo(0.881, within(0.001));
		// what does not burn, never, however hot
		assertThat(ring.getIgnitionProbabilityPerSecond(Material.iron, 5000)).isZero();
		// a class may make the curve wider
		assertThat(new SlowBurningRing().getIgnitionProbabilityPerSecond(Material.wood, tz - 10)).isCloseTo(0.378, within(0.001));
	}

	@Test
	void withoutAComposition_valueProtectionAndBurningAreZero() {
		Ring ring = new Ring();

		assertThat(ring.calculateValue()).isZero();
		for (BlowKind blow : BlowKind.values()) assertThat(ring.calculateProtection(blow)).as(blow.name()).isZero();
		assertThat(ring.calculateIgnitionProbability(1000, 60)).isZero();
	}

	@Test
	void theValue_isMaterialTimesCraft_andOpenToTheTop() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		Ring gold = withMaterials(new Ring(), Material.gold, 100);
		Ring mixed = withMaterials(new Ring(), Material.silver, 90, Material.copper, 10);

		// the ring has its own craft factor 2 since step 4
		assertThat(gold.getMaterialValuePerKg()).isCloseTo(10000f, within(0.01f));
		assertThat(gold.calculateValue()).isCloseTo(20000f, within(0.01f));
		assertThat(mixed.getMaterialValuePerKg()).isCloseTo(0.9f * 800 + 0.1f * 20, within(0.01f));
		assertThat(withMaterials(new CraftedRing(), Material.gold, 100).calculateValue()).isCloseTo(30000f, within(0.01f));
	}

	@Test
	void theProtection_isMaterialTimesForm_perKindOfBlow_andCutAt100() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();
		// as chain mail: iron 95, leather 5 -> stab 67.75, club 53.25, stroke 77.5, backhand 58
		Ring plain = withMaterials(new Ring(), Material.iron, 95, Material.leather, 5);
		assertThat(plain.calculateProtection(BlowKind.stab)).isCloseTo(67.75f, within(0.01f));
		assertThat(plain.calculateProtection(BlowKind.club)).isCloseTo(53.25f, within(0.01f));
		assertThat(plain.calculateProtection(BlowKind.stroke)).isCloseTo(77.5f, within(0.01f));
		assertThat(plain.calculateProtection(BlowKind.backhand)).isCloseTo(58f, within(0.01f));

		// a form factor per kind of blow gives the profile of chain mail (today 50/30/80/60)
		Ring rings = withMaterials(new RingsOfIron(), Material.iron, 95, Material.leather, 5);
		assertThat(rings.calculateProtection(BlowKind.stab)).isCloseTo(50.1f, within(0.1f));
		assertThat(rings.calculateProtection(BlowKind.club)).isCloseTo(30.4f, within(0.1f));
		assertThat(rings.calculateProtection(BlowKind.stroke)).isCloseTo(79.8f, within(0.1f));
		assertThat(rings.calculateProtection(BlowKind.backhand)).isCloseTo(59.7f, within(0.1f));

		// one factor for every blow; never above 100
		Ring thick = withMaterials(new ThickRing(), Material.iron, 95, Material.leather, 5);
		assertThat(thick.calculateProtection(BlowKind.club)).isEqualTo(100f);
		assertThat(thick.calculateProtection(BlowKind.stroke)).isEqualTo(100f);

		// wool protects less than leather
		Ring wool = withMaterials(new Ring(), Material.wool, 100);
		Ring leather = withMaterials(new Ring(), Material.leather, 100);
		assertThat(wool.calculateProtection(BlowKind.stroke)).isLessThan(leather.calculateProtection(BlowKind.stroke));
	}

	@Test
	void burning_isWeightedByTheShares_andGrowsWithTheTime() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();
		// straw 50, iron 50 at the ignition temperature of straw for one second: 0.5 x 0.5
		Ring half = withMaterials(new Ring(), Material.straw, 50, Material.iron, 50);
		assertThat(half.calculateIgnitionProbability(230, 1)).isCloseTo(0.25, within(1e-6));
		// no time, no fire
		assertThat(half.calculateIgnitionProbability(230, 0)).isZero();

		// wood 30 degrees below its ignition temperature: rare in one second, nearly sure after long heat
		Ring wood = withMaterials(new Ring(), Material.wood, 100);
		double oneSecond = wood.calculateIgnitionProbability(270, 1);
		double oneMinute = wood.calculateIgnitionProbability(270, 60);
		assertThat(oneSecond).isCloseTo(0.047, within(0.001));
		assertThat(oneMinute).isGreaterThan(0.9).isLessThanOrEqualTo(1.0);

		// a straw hat catches fire before a felt hat
		Ring straw = withMaterials(new Ring(), Material.straw, 95, Material.linen, 5);
		Ring felt = withMaterials(new Ring(), Material.felt, 95, Material.linen, 5);
		assertThat(straw.calculateIgnitionProbability(300, 1)).isGreaterThan(felt.calculateIgnitionProbability(300, 1));
	}
}
