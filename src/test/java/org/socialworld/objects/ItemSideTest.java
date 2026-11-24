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
package org.socialworld.objects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.datasource.mariaDB.DatabaseAssumptions;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenItem;
import org.socialworld.objects.concrete.clothes.gloves.LeatherGlove;
import org.socialworld.objects.concrete.clothes.shoes.LeatherShoe;
import org.socialworld.objects.concrete.clothes.socks.WoolSock;
import org.socialworld.objects.enums.EnumSide;

/**
 * The side of an item lies in its state (protection concept S3, 2026-11-24). It is written only through HiddenItem
 * and WriteAccessToItem with the grant side (or all); without state or side a pair piece fits on neither side.
 */
class ItemSideTest {

	@Test
	void withoutStateOrSide_aPairPieceIsNeitherLeftNorRight() {
		LeatherShoe prototype = new LeatherShoe();
		assertThat(prototype.isLeftFootShoe()).isFalse();
		assertThat(prototype.isRightFootShoe()).isFalse();
		LeatherShoe none = ItemSides.withSide(new LeatherShoe(), EnumSide.none);
		assertThat(none.isLeftFootShoe() || none.isRightFootShoe()).isFalse();
	}

	@Test
	void theSide_fromTheState() {
		LeatherShoe shoe = ItemSides.withSide(new LeatherShoe(), EnumSide.right);
		assertThat(shoe.isRightFootShoe()).isTrue();
		assertThat(shoe.isLeftFootShoe()).isFalse();
		assertThat(ItemSides.withSide(new WoolSock(), EnumSide.left).isLeftFootSock()).isTrue();
		assertThat(ItemSides.withSide(new LeatherGlove(), EnumSide.right).isRightHandGlove()).isTrue();
	}

	@Test
	void theGrantAll_mayWriteTheSide_asTheCreatorGivesIt() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		LeatherShoe shoe = new LeatherShoe();
		HiddenItem hidden = new WriteAccessToItem(shoe, new StateItem())
				.getMeHidden(new GrantedAccessToProperty[] {GrantedAccessToProperty.all});
		assertThat(hidden.setSide(EnumSide.left)).isEqualTo(WriteAccessToSimulationObject.WRITE_ACCESS_RETURNS_SUCCESS);
		assertThat(shoe.isLeftFootShoe()).isTrue();
	}

	@Test
	void withoutTheGrantSide_theSideStays() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		LeatherShoe shoe = new LeatherShoe();
		HiddenItem hidden = new WriteAccessToItem(shoe, new StateItem())
				.getMeHidden(new GrantedAccessToProperty[] {GrantedAccessToProperty.position, GrantedAccessToProperty.inventory});
		assertThat(hidden.setSide(EnumSide.left)).isEqualTo(WriteAccessToSimulationObject.WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY);
		assertThat(shoe.isLeftFootShoe()).isFalse();
	}
}
