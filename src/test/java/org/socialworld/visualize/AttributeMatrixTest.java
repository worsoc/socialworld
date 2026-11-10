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

import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;

public class AttributeMatrixTest {

	private final VisualModel model = new VisualModel();

	private void report(int objID, int value, long nowMillis) {
		model.setAttributes(objID, new AttributeArray(new int[] {value}), true, nowMillis);
	}

	@Test
	void cellColor_hasThreeAnchors() {
		assertThat(AttributeMatrixPanel.cellColor(0)).isEqualTo(AttributeMatrixPanel.COLOR_LOW);
		assertThat(AttributeMatrixPanel.cellColor(AttributeArray.ATTRIBUTE_VALUE_MIDDLE)).isEqualTo(AttributeMatrixPanel.COLOR_MIDDLE);
		assertThat(AttributeMatrixPanel.cellColor(AttributeArray.ATTRIBUTE_VALUE_MAX)).isEqualTo(AttributeMatrixPanel.COLOR_HIGH);
	}

	@Test
	void cellColor_liesBetweenTheAnchors() {
		assertThat(AttributeMatrixPanel.cellColor(7500))
				.isNotEqualTo(AttributeMatrixPanel.COLOR_MIDDLE)
				.isNotEqualTo(AttributeMatrixPanel.COLOR_HIGH);
		assertThat(AttributeMatrixPanel.cellColor(2500))
				.isNotEqualTo(AttributeMatrixPanel.COLOR_MIDDLE)
				.isNotEqualTo(AttributeMatrixPanel.COLOR_LOW);
	}

	@Test
	void shortAttributeNames_areUnique() {
		Set<String> names = new HashSet<String>();
		for (int column = 0; column < Attribute.NUMBER_OF_ATTRIBUTES; column++) {
			names.add(AttributeMatrixPanel.shortAttributeName(column));
		}
		assertThat(names).hasSize(Attribute.NUMBER_OF_ATTRIBUTES);
	}

	@Test
	void shortAttributeNames_haveTwoLettersFromTheName() {
		for (int column = 0; column < Attribute.NUMBER_OF_ATTRIBUTES; column++) {
			String shortName = AttributeMatrixPanel.shortAttributeName(column);
			assertThat(shortName).hasSize(2);
			assertThat(Attribute.getAttributeName(column).toString()).startsWith(shortName.substring(0, 1));
		}
	}

	@Test
	void shortAttributeName_ofMoralsIsMr() {
		assertThat(Attribute.getAttributeName(2).toString()).isEqualTo("morals");
		assertThat(AttributeMatrixPanel.shortAttributeName(2)).isEqualTo("mr");
	}

	private AttributeMatrixPanel panelWithRoom(AtomicInteger selected) {
		AttributeMatrixPanel panel = new AttributeMatrixPanel();
		// two blocks of 196 pixels with a row height of 20 pixels
		panel.setSize(400, 1024);
		panel.setSelectionListener(selected::set);
		panel.setState(model.snapshot(), 1);
		return panel;
	}

	private void mouse(AttributeMatrixPanel panel, int eventId, int x, int y) {
		panel.dispatchEvent(new MouseEvent(panel, eventId, 0, 0, x, y, 1, false, MouseEvent.BUTTON1));
	}

	@Test
	void clickInTheLeftBlock_selectsFromTheFirstFifty() {
		AtomicInteger selected = new AtomicInteger();

		mouse(panelWithRoom(selected), MouseEvent.MOUSE_PRESSED, 100, 24 + 3 * 20 + 1);

		assertThat(selected.get()).isEqualTo(4);
	}

	@Test
	void clickInTheRightBlock_selectsFromTheSecondFifty() {
		AtomicInteger selected = new AtomicInteger();

		mouse(panelWithRoom(selected), MouseEvent.MOUSE_PRESSED, 300, 24 + 3 * 20 + 1);

		assertThat(selected.get()).isEqualTo(54);
	}

	@Test
	void clickInARow_selectsTheSameObjectInEveryColumn() {
		AtomicInteger selected = new AtomicInteger();
		AttributeMatrixPanel panel = panelWithRoom(selected);

		mouse(panel, MouseEvent.MOUSE_PRESSED, 40, 30);
		assertThat(selected.get()).isEqualTo(1);

		selected.set(0);
		mouse(panel, MouseEvent.MOUSE_PRESSED, 190, 30);
		assertThat(selected.get()).isEqualTo(1);
	}

	@Test
	void sortedList_goesOnAtTheTopOfTheRightBlock() {
		for (int objID = 1; objID <= VisualModel.OBJECT_COUNT; objID++) {
			report(objID, objID, 1);
		}
		AtomicInteger selected = new AtomicInteger();
		AttributeMatrixPanel panel = panelWithRoom(selected);
		panel.sortBy(0);
		panel.setState(model.snapshot(), 1);

		mouse(panel, MouseEvent.MOUSE_PRESSED, 300, 30);

		assertThat(selected.get()).isEqualTo(50);
	}

	@Test
	void clickOnAHeadOfTheRightBlock_sortsThatColumn() {
		AttributeMatrixPanel panel = panelWithRoom(new AtomicInteger());

		// the right block starts at 204, the third column of the attributes at 204 + 28 + 37
		mouse(panel, MouseEvent.MOUSE_CLICKED, 275, 5);

		assertThat(panel.getSortColumn()).isEqualTo(2);
	}

	@Test
	void clickOnTheIdsOfTheHeader_doesNotSort() {
		AttributeMatrixPanel panel = panelWithRoom(new AtomicInteger());

		mouse(panel, MouseEvent.MOUSE_CLICKED, 210, 5);

		assertThat(panel.getSortColumn()).isEqualTo(AttributeMatrixPanel.NO_SORTING);
	}

	@Test
	void sortedIds_withoutSortingAreTheIds() {
		int[] ids = AttributeMatrixPanel.sortedIds(model.snapshot(), AttributeMatrixPanel.NO_SORTING, true);

		assertThat(ids).hasSize(VisualModel.OBJECT_COUNT);
		assertThat(ids[0]).isEqualTo(1);
		assertThat(ids[99]).isEqualTo(100);
	}

	@Test
	void sortedIds_descendingBringsTheHighestFirst() {
		report(10, 3000, 1);
		report(20, 9000, 1);
		report(30, 6000, 1);

		int[] ids = AttributeMatrixPanel.sortedIds(model.snapshot(), 0, true);

		assertThat(ids[0]).isEqualTo(20);
		assertThat(ids[1]).isEqualTo(30);
		assertThat(ids[2]).isEqualTo(10);
	}

	@Test
	void sortedIds_ascendingBringsTheLowestFirst() {
		report(10, 3000, 1);
		report(20, 9000, 1);

		int[] ids = AttributeMatrixPanel.sortedIds(model.snapshot(), 0, false);

		assertThat(ids[0]).isEqualTo(10);
		assertThat(ids[1]).isEqualTo(20);
	}

	@Test
	void sortedIds_unknownAttributesComeLastInBothDirections() {
		report(50, 1, 1);

		assertThat(AttributeMatrixPanel.sortedIds(model.snapshot(), 0, true)[0]).isEqualTo(50);
		assertThat(AttributeMatrixPanel.sortedIds(model.snapshot(), 0, false)[0]).isEqualTo(50);
	}

	@Test
	void sortedIds_equalValuesStayInTheOrderOfTheirIds() {
		report(40, 5000, 1);
		report(15, 5000, 1);

		int[] ids = AttributeMatrixPanel.sortedIds(model.snapshot(), 0, true);

		assertThat(ids[0]).isEqualTo(15);
		assertThat(ids[1]).isEqualTo(40);
	}

	@Test
	void sortBy_cyclesThroughHighLowAndIds() {
		AttributeMatrixPanel panel = new AttributeMatrixPanel();

		panel.sortBy(3);
		assertThat(panel.getSortColumn()).isEqualTo(3);
		assertThat(panel.isDescending()).isTrue();

		panel.sortBy(3);
		assertThat(panel.getSortColumn()).isEqualTo(3);
		assertThat(panel.isDescending()).isFalse();

		panel.sortBy(3);
		assertThat(panel.getSortColumn()).isEqualTo(AttributeMatrixPanel.NO_SORTING);
	}

	@Test
	void sortBy_anotherColumnStartsWithHigh() {
		AttributeMatrixPanel panel = new AttributeMatrixPanel();
		panel.sortBy(3);
		panel.sortBy(3);

		panel.sortBy(5);

		assertThat(panel.getSortColumn()).isEqualTo(5);
		assertThat(panel.isDescending()).isTrue();
	}

	@Test
	void markStrength_withoutChangeIsZero() {
		report(1, 5000, 100);

		assertThat(AttributeMatrixPanel.markStrength(model.snapshot().get(1), 0, 100)).isZero();
	}

	@Test
	void markStrength_fadesAfterTheChange() {
		report(1, 5000, 100);
		report(1, 5300, 1000);
		VisualModel.ObjectState state = model.snapshot().get(1);

		assertThat(AttributeMatrixPanel.markStrength(state, 0, 1000)).isEqualTo(1f);
		assertThat(AttributeMatrixPanel.markStrength(state, 0, 1000 + AttributeMatrixPanel.MARK_MILLIS / 2)).isEqualTo(0.5f);
		assertThat(AttributeMatrixPanel.markStrength(state, 0, 1000 + AttributeMatrixPanel.MARK_MILLIS)).isZero();
	}

	@Test
	void markLength_growsWithTheChangeAndStaysInRange() {
		assertThat(AttributeMatrixPanel.markLength(1)).isGreaterThan(0f);
		assertThat(AttributeMatrixPanel.markLength(400)).isGreaterThan(AttributeMatrixPanel.markLength(100));
		assertThat(AttributeMatrixPanel.markLength(-400)).isEqualTo(AttributeMatrixPanel.markLength(400));
		assertThat(AttributeMatrixPanel.markLength(9999)).isEqualTo(1f);
	}

	@Test
	void describe_tellsValueAndLastChange() {
		report(37, 7000, 100);
		report(37, 7123, 2000);

		assertThat(AttributeMatrixPanel.describe(37, model.snapshot().get(37), 0, 4500))
				.contains("Objekt 37", "mood", "71", "7123", "+123", "vor 2 s");
	}

	@Test
	void describe_ofAnUnknownObject() {
		assertThat(AttributeMatrixPanel.describe(37, model.snapshot().get(37), 0, 100)).contains("noch kein Wert");
	}
}
