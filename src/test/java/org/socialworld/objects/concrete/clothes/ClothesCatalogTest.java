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
import org.socialworld.objects.concrete.clothes.socks.*;
import org.socialworld.objects.concrete.clothes.trousers.*;
import org.socialworld.objects.properties.IProtective;
import org.socialworld.objects.properties.ISacred;
import org.socialworld.objects.properties.IValuable;
import org.socialworld.objects.properties.IWearable;
import org.socialworld.objects.statics.GetLexemIDLowerPartFromMapping;

/**
 * The 26 concrete clothes of the item hierarchy (2026-11-23): each one is created without the database,
 * says exactly one place (IWearable), has its own lexem id below its kind, and shoes, socks and gloves
 * have the side as a property.
 */
class ClothesCatalogTest {

	/** class to its kind's lower lexem value */
	private static Map<Class<? extends Clothes>, Integer> catalog() {
		Map<Class<? extends Clothes>, Integer> c = new LinkedHashMap<>();
		for (Class<? extends Clothes> k : java.util.List.of(StrawHat.class, WoolCap.class, Headscarf.class, FeltHat.class,
				Beret.class, Hood.class, Helmet.class)) c.put(k, 272);
		for (Class<? extends Clothes> k : java.util.List.of(LinenShirt.class, WoolTunic.class, SilkShirt.class, Rags.class)) c.put(k, 288);
		for (Class<? extends Clothes> k : java.util.List.of(WorkTrousers.class, Hose.class)) c.put(k, 304);
		for (Class<? extends Clothes> k : java.util.List.of(LeatherShoe.class, Boot.class, Sandal.class)) c.put(k, 320);
		c.put(WoolSock.class, 336);
		c.put(LeatherGlove.class, 352);
		for (Class<? extends Clothes> k : java.util.List.of(LinenDress.class, FineDress.class, Habit.class)) c.put(k, 368);
		for (Class<? extends Clothes> k : java.util.List.of(Apron.class, LeatherApron.class, Cloak.class, FurCloak.class,
				ChainMail.class)) c.put(k, 384);
		return c;
	}

	private static Clothes create(Class<? extends Clothes> k) throws Exception {
		return k.getDeclaredConstructor().newInstance();
	}

	@Test
	void all26_areCreatedWithoutDatabase() throws Exception {
		assertThat(catalog()).hasSize(26);
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
					w.isLeftHandGlove() || w.isRightHandGlove(), w.isJewellery(), w.isBag()};
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
			assertThat(value).as(e.getKey().getSimpleName()).isBetween(e.getValue() + 1, e.getValue() + 15);
			assertThat(seen.add(value)).as(e.getKey().getSimpleName() + " unique").isTrue();
		}
	}

	@Test
	void side_isAPropertyOfTheState_noneUntilSet() {
		LeatherShoe shoe = new LeatherShoe();
		assertThat(shoe.isLeftFootShoe()).isFalse();
		assertThat(shoe.isRightFootShoe()).isFalse();
		shoe = ItemSides.withSide(new LeatherShoe(), EnumSide.right);
		assertThat(shoe.isLeftFootShoe()).isFalse();
		assertThat(shoe.isRightFootShoe()).isTrue();

		WoolSock sock = ItemSides.withSide(new WoolSock(), EnumSide.right);
		assertThat(sock.isRightFootSock()).isTrue();

		LeatherGlove glove = ItemSides.withSide(new LeatherGlove(), EnumSide.left);
		assertThat(glove.isLeftHandGlove()).isTrue();
		assertThat(glove.isRightHandGlove()).isFalse();
	}

	@Test
	void abilities_asInTheCatalogue() {
		assertThat(new Helmet()).isInstanceOf(IProtective.class);
		assertThat(new ChainMail()).isInstanceOf(IProtective.class).isInstanceOf(IValuable.class);
		assertThat(new FurCloak()).isInstanceOf(IValuable.class);
		assertThat(new Habit()).isInstanceOf(ISacred.class);
		assertThat(new LinenShirt()).isNotInstanceOf(IProtective.class).isNotInstanceOf(IValuable.class);
		assertThat(new Helmet().getProtectionAgainstStab()).isBetween(0f, 100f);
	}
}
