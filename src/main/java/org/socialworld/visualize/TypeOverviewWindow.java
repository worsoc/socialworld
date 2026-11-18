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

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

/**
 * Extra window of the dashboard: which human has which type, or how many humans each type has.
 * A click on a column head sorts by it.
 */
final class TypeOverviewWindow {

	private final TypeOverview overview;
	private final JTable table = new JTable();
	static final String SHOW_COUNTS = "Anzahl pro Typ";
	static final String SHOW_ASSIGNMENTS = "Menschen einzeln";

	private final JToggleButton countsButton = new JToggleButton(SHOW_COUNTS);

	TypeOverviewWindow(TypeOverview overview) {
		this.overview = overview;
	}

	void show() {
		JLabel header = new JLabel(overview.countHumans() + " Menschen, " + overview.countTypes() + " verschiedene Typen");
		header.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
		countsButton.addActionListener(event -> showTable());

		JPanel top = new JPanel(new BorderLayout());
		top.add(header, BorderLayout.CENTER);
		top.add(countsButton, BorderLayout.EAST);

		table.setAutoCreateRowSorter(true);

		JPanel content = new JPanel(new BorderLayout());
		content.add(top, BorderLayout.NORTH);
		content.add(new JScrollPane(table), BorderLayout.CENTER);

		JFrame frame = new JFrame("Typen der Menschen");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(content);
		frame.setBounds(120, 80, 420, 700);
		showTable();
		frame.setVisible(true);
	}

	private void showTable() {
		boolean counts = countsButton.isSelected();
		table.setModel(counts ? countsModel(overview) : assignmentsModel(overview));
		countsButton.setText(buttonText(counts));
	}

	/** The button names the view that a click leads to. */
	static String buttonText(boolean showingCounts) {
		return showingCounts ? SHOW_ASSIGNMENTS : SHOW_COUNTS;
	}

	static DefaultTableModel assignmentsModel(TypeOverview overview) {
		DefaultTableModel model = typedModel(new String[] {"Mensch", "Typ", "Name"});
		for (TypeOverview.Assignment row : overview.getAssignments()) {
			model.addRow(new Object[] {row.objID, row.type, row.name});
		}
		return model;
	}

	static DefaultTableModel countsModel(TypeOverview overview) {
		DefaultTableModel model = typedModel(new String[] {"Typ", "Name", "Anzahl"});
		for (TypeOverview.Count row : overview.getCounts()) {
			model.addRow(new Object[] {row.type, row.name, row.humans});
		}
		return model;
	}

	/** Numbers stay numbers, so that sorting by a column sorts 2 before 10. */
	private static DefaultTableModel typedModel(String[] columns) {
		return new DefaultTableModel(columns, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				return getRowCount() > 0 && getValueAt(0, column) != null ? getValueAt(0, column).getClass() : Object.class;
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}
}
