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
package org.socialworld.datasource.createObjects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.socialworld.datasource.createObjects.StartEquipmentCatalog.Entry;
import org.socialworld.datasource.createObjects.StartEquipmentCatalog.Piece;
import org.socialworld.datasource.createObjects.StartEquipmentCatalog.Side;

/**
 * The start equipment of the 100 characters read from equipment.csv (inventory concept V3, 2026-11-23).
 */
class StartEquipmentCatalogTest {

	private static List<String> simple(List<Piece> pieces) {
		return pieces.stream().map(Piece::toString).collect(Collectors.toList());
	}

	@Test
	void theFile_hasAll100Characters_andNoProblems() {
		StartEquipmentCatalog catalog = StartEquipmentCatalog.load();
		assertThat(catalog.getProblems()).isEmpty();
		assertThat(catalog.size()).isEqualTo(100);
		for (int no = 1; no <= 100; no++) assertThat(catalog.get(no)).as("character " + no).isNotNull();
	}

	@Test
	void theFarmer_getsKitK1AsPieces_shoesAndSocksAsPairs() {
		Entry farmer = StartEquipmentCatalog.load().get(1);
		assertThat(simple(farmer.clothes)).containsExactly("LinenShirt", "WorkTrousers", "LeatherShoe(left)",
				"LeatherShoe(right)", "WoolSock(left)", "WoolSock(right)", "StrawHat");
		assertThat(farmer.items).containsExactly("org.socialworld.objects.concrete.tools.Pitchfork",
				"org.socialworld.objects.concrete.eatable.bakery.Bread");
	}

	@Test
	void moreClothes_comeFirst() {
		Entry merchant = StartEquipmentCatalog.load().get(48);
		assertThat(simple(merchant.clothes).get(0)).isEqualTo("FurCloak");
		assertThat(simple(merchant.clothes)).contains("Cloak");	// left out later, when the fur cloak has the place
	}

	@Test
	void barefoot_leavesOutShoesAndSocks() {
		Entry cowherdGirl = StartEquipmentCatalog.load().get(33);
		assertThat(cowherdGirl.barefoot).isTrue();
		assertThat(simple(cowherdGirl.clothes)).noneMatch(p -> p.startsWith("LeatherShoe") || p.startsWith("WoolSock"));
		assertThat(simple(cowherdGirl.clothes)).contains("LinenDress", "Apron", "Headscarf");
	}

	@Test
	void aContainerWithContent_isTheContainer_andTheContentApart() {
		Entry barmaid = StartEquipmentCatalog.load().get(16);
		assertThat(barmaid.items).containsExactly("org.socialworld.objects.concrete.containers.Jug");
		assertThat(barmaid.contents).containsExactly("org.socialworld.objects.concrete.eatable.liquids.Beer");
		Entry shepherd = StartEquipmentCatalog.load().get(9);
		assertThat(shepherd.contents).containsExactly("org.socialworld.objects.concrete.eatable.Water");
	}

	@Test
	void classNames_areFoundInTheirPackages_unknownOnesAreProblems() {
		assertThat(StartEquipmentCatalog.fullClassName("Helmet")).isEqualTo("org.socialworld.objects.concrete.clothes.caps.Helmet");
		assertThat(StartEquipmentCatalog.fullClassName("Stone")).isEqualTo("org.socialworld.objects.concrete.solids.Stone");
		assertThat(StartEquipmentCatalog.fullClassName("Unicorn")).isNull();
		StartEquipmentCatalog parsed = StartEquipmentCatalog.parse(List.of("kit;K1;LinenShirt", "1;K1;;Unicorn;", "2;K9;;;"));
		assertThat(parsed.getProblems()).anyMatch(p -> p.contains("Unicorn")).anyMatch(p -> p.contains("K9"));
	}

	@Test
	void totals_asCountedInTheConcept() {
		StartEquipmentCatalog catalog = StartEquipmentCatalog.load();
		int clothes = 0, items = 0, contents = 0;
		for (int no = 1; no <= 100; no++) {
			clothes += catalog.get(no).clothes.size();
			items += catalog.get(no).items.size();
			contents += catalog.get(no).contents.size();
		}
		// 697 clothes objects in the concept, before more clothes replace kit pieces; 231 items with the contents
		assertThat(clothes).isEqualTo(697);
		assertThat(items + contents).isEqualTo(231);
	}
}
