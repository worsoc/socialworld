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
package org.socialworld.objects.concrete.clothes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.objects.ItemSides;
import org.socialworld.objects.enums.EnumSide;
import org.socialworld.objects.concrete.clothes.caps.*;
import org.socialworld.objects.concrete.clothes.dresses.*;
import org.socialworld.objects.concrete.clothes.gloves.*;
import org.socialworld.objects.concrete.clothes.overgarments.*;
import org.socialworld.objects.concrete.clothes.shirts.*;
import org.socialworld.objects.concrete.clothes.shoes.*;
import org.socialworld.objects.concrete.clothes.trousers.*;
import org.socialworld.objects.properties.IProtective;
import org.socialworld.objects.properties.ISacred;
import org.socialworld.objects.properties.IValuable;
import org.socialworld.objects.properties.IWearable;
import org.socialworld.objects.statics.GetLexemIDLowerPartFromMapping;

/**
 * The 30 concrete clothes of the item hierarchy (2026-11-23; 26 until the merges of 2026-11-25: Hat, and SilkShirt,
 * FineDress, LeatherApron and FurCloak became Shirt, Dress, Apron and Cloak of that material; then 9 new clothes of
 * step 4: coif, crown, wreath, doublet, gambeson, tabard, gauntlet, clogs, neckerchief): each one is created without the database,
 * says exactly one place (IWearable), has its own lexem id below its kind, and shoes, socks and gloves
 * have the side as a property. Since 2026-11-25 Cap, Shirt, Shoe, Sock, Glove and Dress are concrete themselves
 * (item class or composition, step 1) and have the lexem id of their kind.
 */
class ClothesCatalogTest {

	/** class to its kind's lower lexem value */
	private static Map<Class<? extends Clothes>, Integer> catalog() {
		Map<Class<? extends Clothes>, Integer> c = new LinkedHashMap<>();
		for (Class<? extends Clothes> k : java.util.List.of(Hat.class, Cap.class, Headscarf.class,
				Beret.class, Hood.class, Helmet.class, Coif.class, Crown.class, Wreath.class)) c.put(k, 272);
		for (Class<? extends Clothes> k : java.util.List.of(Shirt.class, Tunic.class, Rags.class, Doublet.class)) c.put(k, 288);
		for (Class<? extends Clothes> k : java.util.List.of(WorkTrousers.class, Hose.class)) c.put(k, 304);
		for (Class<? extends Clothes> k : java.util.List.of(Shoe.class, Boot.class, Sandal.class, Clogs.class)) c.put(k, 320);
		c.put(Sock.class, 336);
		for (Class<? extends Clothes> k : java.util.List.of(Glove.class, Gauntlet.class)) c.put(k, 352);
		for (Class<? extends Clothes> k : java.util.List.of(Dress.class, Habit.class)) c.put(k, 368);
		for (Class<? extends Clothes> k : java.util.List.of(Apron.class, Cloak.class,
				ChainMail.class, Gambeson.class, Tabard.class)) c.put(k, 384);
		c.put(Neckerchief.class, 400);	// a kind of its own (step 4)
		return c;
	}

	/** The kinds that are concrete themselves (since 2026-11-25): their lexem id is that of the kind. */
	private static final Set<Class<? extends Clothes>> CONCRETE_KINDS = Set.of(Cap.class, Shirt.class, Shoe.class,
			Sock.class, Glove.class, Dress.class, Neckerchief.class);

	private static Clothes create(Class<? extends Clothes> k) throws Exception {
		return k.getDeclaredConstructor().newInstance();
	}

	@Test
	void all30_areCreatedWithoutDatabase() throws Exception {
		assertThat(catalog()).hasSize(30);
		for (Class<? extends Clothes> k : catalog().keySet()) {
			assertThat(create(k)).as(k.getSimpleName()).isInstanceOf(IWearable.class);
		}
	}

	@Test
	void eachOne_saysExactlyOnePlace() throws Exception {
		for (Class<? extends Clothes> k : catalog().keySet()) {
			// with a state and a side: a pair piece without side fits on neither side (protection concept S3)
			IWearable w = (IWearable) ItemSides.withSide(create(k), EnumSide.left);
			boolean[] places = {w.isCap(), w.isShirt(), w.isTrousers(), w.isDress(), w.isOvergarment(),
					w.isLeftFootShoe() || w.isRightFootShoe(), w.isLeftFootSock() || w.isRightFootSock(),
					w.isLeftHandGlove() || w.isRightHandGlove(), w.isNecklace(), w.isRing(), w.isBag()};
			int count = 0;
			for (boolean p : places) if (p) count++;
			assertThat(count).as(k.getSimpleName()).isEqualTo(1);
		}
	}

	@Test
	void lexemLowerValue_isUniqueAndJustAboveItsKind() {
		Set<Integer> seen = new HashSet<>();
		for (Map.Entry<Class<? extends Clothes>, Integer> e : catalog().entrySet()) {
			int value = GetLexemIDLowerPartFromMapping.getForClassName(e.getKey().getName());
			if (CONCRETE_KINDS.contains(e.getKey())) {
				assertThat(value).as(e.getKey().getSimpleName() + " is its kind").isEqualTo(e.getValue());
			} else {
				assertThat(value).as(e.getKey().getSimpleName()).isBetween(e.getValue() + 1, e.getValue() + 15);
			}
			assertThat(seen.add(value)).as(e.getKey().getSimpleName() + " unique").isTrue();
		}
	}

	@Test
	void side_isAPropertyOfTheState_noneUntilSet() {
		Shoe shoe = new Shoe();
		assertThat(shoe.isLeftFootShoe()).isFalse();
		assertThat(shoe.isRightFootShoe()).isFalse();
		shoe = ItemSides.withSide(new Shoe(), EnumSide.right);
		assertThat(shoe.isLeftFootShoe()).isFalse();
		assertThat(shoe.isRightFootShoe()).isTrue();

		Sock sock = ItemSides.withSide(new Sock(), EnumSide.right);
		assertThat(sock.isRightFootSock()).isTrue();

		Glove glove = ItemSides.withSide(new Glove(), EnumSide.left);
		assertThat(glove.isLeftHandGlove()).isTrue();
		assertThat(glove.isRightHandGlove()).isFalse();
	}

	@Test
	void abilities_asInTheCatalogue() {
		assertThat(new Helmet()).isInstanceOf(IProtective.class);
		assertThat(new ChainMail()).isInstanceOf(IProtective.class).isInstanceOf(IValuable.class);
		assertThat(new Beret()).isInstanceOf(IValuable.class);
		// the value of SilkShirt, FineDress and FurCloak comes back from the material since 2026-11-26 (V2 step 4):
		// shirt, dress and cloak are valuable, the silk and fur variants more than the others
		assertThat(new Cloak()).isInstanceOf(IValuable.class);
		assertThat(new Dress()).isInstanceOf(IValuable.class);
		// the new clothes of step 4
		assertThat(new Crown()).isInstanceOf(IValuable.class).isNotInstanceOf(org.socialworld.objects.properties.IBurnable.class);
		assertThat(new Gauntlet()).isInstanceOf(IProtective.class).isNotInstanceOf(org.socialworld.objects.properties.IBurnable.class);
		assertThat(new Gauntlet().getProtectionAgainstStroke()).isGreaterThan(new Glove().getProtectionAgainstStroke());
		assertThat(new Gambeson()).isInstanceOf(IProtective.class).isInstanceOf(org.socialworld.objects.properties.IBurnable.class);
		assertThat(new Neckerchief().isJewellery()).as("at the neck, but no jewellery").isFalse();
		assertThat(new Glove()).as("a gauntlet is a glove too").isNotInstanceOf(org.socialworld.objects.properties.IBurnable.class);
		assertThat(new Habit()).isInstanceOf(ISacred.class);
		assertThat(new Glove()).as("the protection of the former LeatherGlove").isInstanceOf(IProtective.class);
		assertThat(new Cap()).as("a Helmet is a Cap too").isNotInstanceOf(org.socialworld.objects.properties.IBurnable.class);
		assertThat(new Shirt()).isNotInstanceOf(IProtective.class).isInstanceOf(IValuable.class);
		assertThat(new Helmet().getProtectionAgainstStab()).isBetween(0f, 100f);
	}
}
