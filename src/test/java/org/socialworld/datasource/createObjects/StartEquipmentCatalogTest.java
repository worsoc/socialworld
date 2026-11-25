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

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.properties.Material;
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
		// his clogs (more clothes, step 4 of item class or composition) come first and later take the place of the shoes
		assertThat(simple(farmer.clothes)).containsExactly("Clogs(left)", "Clogs(right)", "Shirt[linen]", "WorkTrousers",
				"Shoe(left)", "Shoe(right)", "Sock(left)", "Sock(right)", "Hat[straw]");
		assertThat(farmer.items).containsExactly("org.socialworld.objects.concrete.tools.Pitchfork",
				"org.socialworld.objects.concrete.eatable.bakery.Bread");
	}

	// item class or composition, step 2 (2026-11-25): the material in brackets, checked against composition.csv
	@Test
	void aPiece_namesItsMaterial_inBrackets_andItIsChecked() {
		Entry smith = StartEquipmentCatalog.load().get(31);
		assertThat(smith.clothes.get(0).material).isSameAs(Material.leather);
		assertThat(simple(smith.clothes).subList(0, 2)).containsExactly("Glove[leather](left)", "Glove[leather](right)");
		Entry carpenter = StartEquipmentCatalog.load().get(5);
		assertThat(carpenter.clothes.get(0).material).as("Shirt[linen]").isSameAs(Material.linen);
		assertThat(carpenter.clothes.get(1).material).as("WorkTrousers without brackets").isSameAs(Material.nothing);
		StartEquipmentCatalog parsed = StartEquipmentCatalog.parse(List.of("kit;K1;Hat[gold],Hat[unobtainium],Hat[felt],Cap",
				"1;K1;;;"));
		assertThat(parsed.getProblems()).hasSize(2);
		assertThat(String.join("\n", parsed.getProblems())).contains("no alternative of Hat made of gold", "unknown material 'unobtainium'");
		assertThat(simple(parsed.get(1).clothes)).containsExactly("Hat[felt]", "Cap");
		assertThat(parsed.get(1).clothes.get(1).material).isSameAs(Material.nothing);
	}

	@Test
	void moreClothes_comeFirst() {
		Entry merchant = StartEquipmentCatalog.load().get(48);
		assertThat(simple(merchant.clothes).get(0)).isEqualTo("Cloak[fur]");
		assertThat(simple(merchant.clothes)).contains("Cloak[wool]");	// left out later, when the fur cloak has the place
	}

	@Test
	void barefoot_leavesOutShoesAndSocks() {
		Entry cowherdGirl = StartEquipmentCatalog.load().get(33);
		assertThat(cowherdGirl.barefoot).isTrue();
		assertThat(simple(cowherdGirl.clothes)).noneMatch(p -> p.startsWith("Shoe") || p.startsWith("Sock"));
		assertThat(simple(cowherdGirl.clothes)).contains("Dress[linen]", "Apron[linen]", "Headscarf");
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
		StartEquipmentCatalog parsed = StartEquipmentCatalog.parse(List.of("kit;K1;Shirt", "1;K1;;Unicorn;", "2;K9;;;"));
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
		// 697 clothes objects in the concept, before more clothes replace kit pieces; 231 items with the contents.
		// Since 2026-11-25 (item class or composition, step 4) 34 more pieces of clothing (clogs 10, coif 4, crown 1,
		// wreath 2, doublet 4, gambeson 3, tabard 3, gauntlet 2, neckerchief 5) and 4 more items (2 signet rings and
		// 2 quivers added; the signet rings of 48 and 60 replace their rings)
		assertThat(clothes).isEqualTo(731);
		assertThat(items + contents).isEqualTo(235);
	}
}
