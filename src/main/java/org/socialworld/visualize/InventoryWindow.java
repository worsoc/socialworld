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
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * The inventory of the selected human in a window of its own (2026-11-24): the stick figure with its slots, a line
 * with the counts and the contents of the containers. There is one window at most; it is not modal and may lose
 * the focus, so that humans can be switched in the dashboard. The dashboard hands in the view at every refresh
 * while the window is open, so the window follows the selection and the changes.
 */
final class InventoryWindow {

	private JFrame frame;
	private final InventoryFigurePanel figure = new InventoryFigurePanel();
	private final JLabel summary = new JLabel(" ");
	private final JTextArea contents = new JTextArea(6, 40);
	private String shownContents = null;

	boolean isOpen() {
		return frame != null && frame.isDisplayable();
	}

	/** Opens the window, or brings it to the front if it is open already. */
	void open() {
		if (isOpen()) {
			frame.toFront();
			return;
		}
		summary.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
		summary.setFont(summary.getFont().deriveFont(Font.BOLD));
		contents.setEditable(false);
		contents.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

		JPanel bottom = new JPanel(new BorderLayout());
		bottom.add(summary, BorderLayout.NORTH);
		bottom.add(new JScrollPane(contents), BorderLayout.CENTER);

		JPanel content = new JPanel(new BorderLayout());
		content.add(figure, BorderLayout.CENTER);
		content.add(bottom, BorderLayout.SOUTH);

		frame = new JFrame("Inventar");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(content);
		frame.pack();
		frame.setLocation(1100, 40);
		frame.setVisible(true);
	}

	void show(InventoryView view) {
		if (!isOpen()) return;
		frame.setTitle(view.title());
		figure.setView(view);
		summary.setText(view.summary());
		String text = String.join("\n", view.contentLines());
		// only on a change, so that the list keeps its scroll position
		if (!text.equals(shownContents)) {
			shownContents = text;
			contents.setText(text.isEmpty() ? "keine Beh\u00E4lter mit Inhalt" : text);
			contents.setCaretPosition(0);
		}
	}
}
