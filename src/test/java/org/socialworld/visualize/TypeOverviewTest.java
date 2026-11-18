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

import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import org.junit.jupiter.api.Test;

/**
 * The overview of the types of the humans behind the button "Typen" of the dashboard. Without a window.
 */
public class TypeOverviewTest {

	private static TypeOverview overview() {
		Map<Integer, Integer> influenceTypes = new HashMap<>();
		influenceTypes.put(12, 3);
		influenceTypes.put(2, 0);
		influenceTypes.put(7, 3);
		influenceTypes.put(30, 98);
		Map<Integer, String> names = new HashMap<>();
		names.put(1, "farmer");
		names.put(4, "maid");
		return new TypeOverview(influenceTypes, names);
	}

	@Test
	void assignments_areOrderedByHuman_andTheTypeIsTheInfluenceTypePlusOne() {
		TypeOverview overview = overview();

		assertThat(overview.getAssignments()).extracting(row -> row.objID).containsExactly(2, 7, 12, 30);
		assertThat(overview.getAssignments()).extracting(row -> row.type).containsExactly(1, 4, 4, 99);
		assertThat(overview.getAssignments()).extracting(row -> row.name).containsExactly("farmer", "maid", "maid", "?");
	}

	@Test
	void counts_haveOnlyTheTypesThatOccur_orderedByType() {
		TypeOverview overview = overview();

		assertThat(overview.getCounts()).extracting(row -> row.type).containsExactly(1, 4, 99);
		assertThat(overview.getCounts()).extracting(row -> row.humans).containsExactly(1, 2, 1);
		assertThat(overview.countHumans()).isEqualTo(4);
		assertThat(overview.countTypes()).isEqualTo(3);
	}

	@Test
	void withoutHumans_bothListsAreEmpty() {
		TypeOverview overview = new TypeOverview(new HashMap<>(), new HashMap<>());

		assertThat(overview.getAssignments()).isEmpty();
		assertThat(overview.getCounts()).isEmpty();
		assertThat(TypeOverviewWindow.countsModel(overview).getColumnClass(0)).isEqualTo(Object.class);
	}

	@Test
	void button_namesTheOtherView() {
		assertThat(TypeOverviewWindow.buttonText(false)).isEqualTo("Anzahl pro Typ");
		assertThat(TypeOverviewWindow.buttonText(true)).isEqualTo("Menschen einzeln");
	}

	@Test
	void tables_keepNumbersAsNumbers_soThatSortingIsNumeric() {
		DefaultTableModel assignments = TypeOverviewWindow.assignmentsModel(overview());
		DefaultTableModel counts = TypeOverviewWindow.countsModel(overview());

		assertThat(assignments.getColumnName(0)).isEqualTo("Mensch");
		assertThat(assignments.getRowCount()).isEqualTo(4);
		assertThat(assignments.getColumnClass(0)).isEqualTo(Integer.class);
		assertThat(assignments.getColumnClass(2)).isEqualTo(String.class);
		assertThat(assignments.isCellEditable(0, 0)).isFalse();

		assertThat(counts.getColumnName(2)).isEqualTo("Anzahl");
		assertThat(counts.getValueAt(1, 0)).isEqualTo(4);
		assertThat(counts.getValueAt(1, 1)).isEqualTo("maid");
		assertThat(counts.getValueAt(1, 2)).isEqualTo(2);
	}
}
