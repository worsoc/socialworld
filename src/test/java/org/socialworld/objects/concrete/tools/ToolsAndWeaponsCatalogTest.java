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
package org.socialworld.objects.concrete.tools;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.properties.WeaponProperty;
import org.socialworld.objects.concrete.HumanCrafted;
import org.socialworld.objects.concrete.weapons.*;
import org.socialworld.objects.properties.ICutting;
import org.socialworld.objects.properties.IDigging;
import org.socialworld.objects.properties.ILightSource;
import org.socialworld.objects.properties.IPlayable;
import org.socialworld.objects.properties.IProtective;
import org.socialworld.objects.properties.IThrowable;
import org.socialworld.objects.properties.IWeapon;
import org.socialworld.objects.statics.GetLexemIDLowerPartFromMapping;

/**
 * The 29 tools and 7 weapons of the item hierarchy (2026-11-23): created without the database, lexem values in
 * their purpose level (tools) or weapon level, and the abilities: a tool can be a weapon without being a Weapon.
 */
class ToolsAndWeaponsCatalogTest {

	/** class to the lower lexem value of its level (purpose of a tool, or the weapon itself) */
	private static Map<Class<? extends HumanCrafted>, Integer> catalog() {
		Map<Class<? extends HumanCrafted>, Integer> c = new LinkedHashMap<>();
		List.of(Pitchfork.class, Flail.class, Sickle.class, Hoe.class, Shovel.class, Pickaxe.class).forEach(k -> c.put(k, 528));
		List.of(Axe.class, Hammer.class, Saw.class, Tongs.class, Awl.class, Knife.class).forEach(k -> c.put(k, 544));
		List.of(Needle.class, Spindle.class, Shears.class).forEach(k -> c.put(k, 560));
		List.of(Crook.class, FishingNet.class, Oar.class, Whip.class, Currycomb.class).forEach(k -> c.put(k, 576));
		List.of(Rope.class, Scales.class, Mortar.class, Razor.class, Key.class).forEach(k -> c.put(k, 592));
		List.of(Lantern.class, Candle.class, SignalHorn.class).forEach(k -> c.put(k, 608));
		c.put(Staff.class, 624);
		int level = 1024;
		for (Class<? extends HumanCrafted> k : List.of(Cudgel.class, Dagger.class, ShortSword.class, LongSword.class,
				Spear.class, Bow.class, Shield.class)) {
			level += 16;
			c.put(k, level - 1);	// the weapon itself is the level: value = level, checked as "level - 1 + 1"
		}
		return c;
	}

	private static HumanCrafted create(Class<? extends HumanCrafted> k) throws Exception {
		return k.getDeclaredConstructor().newInstance();
	}

	@Test
	void all36_areCreatedWithoutDatabase() throws Exception {
		assertThat(catalog()).hasSize(36);
		for (Class<? extends HumanCrafted> k : catalog().keySet()) {
			HumanCrafted object = create(k);
			if (k.getPackageName().endsWith(".tools")) assertThat(object).as(k.getSimpleName()).isInstanceOf(Tool.class);
			else assertThat(object).as(k.getSimpleName()).isInstanceOf(Weapon.class);
		}
	}

	@Test
	void lexemLowerValue_isUniqueAndInItsLevel() {
		Set<Integer> seen = new HashSet<>();
		for (Map.Entry<Class<? extends HumanCrafted>, Integer> e : catalog().entrySet()) {
			int value = GetLexemIDLowerPartFromMapping.getForClassName(e.getKey().getName());
			assertThat(value).as(e.getKey().getSimpleName()).isBetween(e.getValue() + 1, e.getValue() + 15);
			assertThat(seen.add(value)).as(e.getKey().getSimpleName() + " unique").isTrue();
		}
	}

	@Test
	void everyWeaponIsAnIWeapon_andToolsCanBeOneToo() {
		for (Class<? extends HumanCrafted> k : catalog().keySet()) {
			if (k.getPackageName().endsWith(".weapons")) {
				assertThat(IWeapon.class.isAssignableFrom(k)).as(k.getSimpleName()).isTrue();
			}
		}
		assertThat(new Knife()).isInstanceOf(IWeapon.class).isNotInstanceOf(Weapon.class);
		assertThat(new Axe()).isInstanceOf(IWeapon.class).isInstanceOf(ICutting.class).isInstanceOf(IThrowable.class);
		assertThat(new Needle()).isNotInstanceOf(IWeapon.class);
	}

	@Test
	void weaponProperties_existForAllFourBlows_andCuttersHaveAnEdge() {
		for (Class<? extends HumanCrafted> k : catalog().keySet()) {
			if (!IWeapon.class.isAssignableFrom(k)) continue;
			IWeapon weapon;
			try {
				weapon = (IWeapon) create(k);
			} catch (Exception e) {
				throw new AssertionError(k.getSimpleName(), e);
			}
			for (WeaponProperty p : List.of(weapon.getStabProperties(), weapon.getClubProperties(),
					weapon.getStrokeProperties(), weapon.getBackhandProperties())) {
				assertThat(p).as(k.getSimpleName()).isNotNull();
				assertThat(p.getMass()).as(k.getSimpleName() + " mass").isPositive();
				assertThat(p.getHardness()).as(k.getSimpleName() + " hardness").isBetween(0f, 100f);
				assertThat(p.getSharpness()).as(k.getSimpleName() + " sharpness").isBetween(0f, 100f);
			}
			if (weapon instanceof ICutting) {
				assertThat(weapon.getStrokeProperties().getSharpness()).as(k.getSimpleName() + " edge").isPositive();
			}
		}
	}

	@Test
	void otherAbilities_asInTheCatalogue() {
		assertThat(new Shovel()).isInstanceOf(IDigging.class);
		assertThat(new Pickaxe()).isInstanceOf(IDigging.class);
		assertThat(new Lantern()).isInstanceOf(ILightSource.class);
		assertThat(new Candle().getLightRange()).isLessThan(new Lantern().getLightRange());
		assertThat(new SignalHorn()).isInstanceOf(IPlayable.class);
		assertThat(new Shield()).isInstanceOf(IProtective.class);
		assertThat(new LongSword().getValue()).isGreaterThan(new ShortSword().getValue());
	}
}
