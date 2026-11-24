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
package org.socialworld.visualize;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.handle.InventoryPlace;

/**
 * The lie of the slots in the inventory window (2026-11-24): every place has a slot inside the panel, no two slots
 * overlap, every label is different, and the figure looks at the viewer (the right hand is left in the picture).
 */
class InventorySlotLayoutTest {

	@Test
	void everyPlace_hasASlotInsideThePanel_andALabelOfItsOwn() {
		Set<String> labels = new HashSet<>();
		for (InventoryPlace place : InventoryPlace.values()) {
			Rectangle2D.Double slot = InventorySlotLayout.slot(place);
			if (place == InventoryPlace.noWhere) {
				assertThat(slot).isNull();
				continue;
			}
			assertThat(slot).as(place.name()).isNotNull();
			assertThat(slot.getMinX()).as(place.name()).isGreaterThanOrEqualTo(0);
			assertThat(slot.getMinY()).as(place.name()).isGreaterThanOrEqualTo(0);
			assertThat(slot.getMaxX()).as(place.name()).isLessThanOrEqualTo(1);
			assertThat(slot.getMaxY()).as(place.name()).isLessThanOrEqualTo(1);
			assertThat(labels.add(InventorySlotLayout.label(place))).as(place.name()).isTrue();
		}
		assertThat(labels).hasSize(InventoryView.countPlaces());
	}

	@Test
	void noTwoSlots_overlap() {
		InventoryPlace[] places = InventoryPlace.values();
		for (int i = 0; i < places.length; i++) {
			for (int j = i + 1; j < places.length; j++) {
				Rectangle2D.Double a = InventorySlotLayout.slot(places[i]);
				Rectangle2D.Double b = InventorySlotLayout.slot(places[j]);
				if (a == null || b == null) continue;
				assertThat(a.intersects(b)).as(places[i] + " / " + places[j]).isFalse();
			}
		}
	}

	@Test
	void theFigureLooksAtTheViewer_theRightHandIsLeftInThePicture() {
		assertThat(InventorySlotLayout.slot(InventoryPlace.rightHand).x).isLessThan(InventorySlotLayout.slot(InventoryPlace.leftHand).x);
		assertThat(InventorySlotLayout.slot(InventoryPlace.rightFootShoe).x).isLessThan(InventorySlotLayout.slot(InventoryPlace.leftFootShoe).x);
		assertThat(InventorySlotLayout.anchor(InventoryPlace.rightHand).x).isLessThan(InventorySlotLayout.anchor(InventoryPlace.leftHand).x);
	}
}
