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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.function.IntConsumer;

import javax.swing.JPanel;

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;

/**
 * One row per object, one column per attribute, in two blocks side by side, so that the rows are high
 * enough to read the IDs and to hit them with the mouse. The list of all objects runs down the left block
 * and goes on at the top of the right one.
 * The colour scale has white at the middle value and does not judge: orange is high, blue is low.
 * A dash in a cell marks a recent change, a longer dash a stronger one.
 * A click on a column head sorts by this attribute: high to low, low to high, back to the IDs.
 */
class AttributeMatrixPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	static final int NO_SORTING = -1;
	static final long MARK_MILLIS = 5000;
	static final Color COLOR_LOW = SimColorConstants.COLOR_ROYELBLUE;
	static final Color COLOR_MIDDLE = Color.WHITE;
	static final Color COLOR_HIGH = SimColorConstants.COLOR_DARKORANGE;

	private static final int ROWS = VisualModel.OBJECT_COUNT;
	private static final int BLOCKS = 2;
	private static final int ROWS_PER_BLOCK = ROWS / BLOCKS;
	private static final int BLOCK_GAP = 8;
	private static final int COLUMNS = Attribute.NUMBER_OF_ATTRIBUTES;
	private static final int ROWS_PER_RULE = 10;
	private static final int HEADER_HEIGHT = 24;
	private static final int ID_WIDTH = 28;
	private static final int FULL_MARK_CHANGE = 1000;
	private static final float SMALLEST_MARK = 0.15f;
	// the first two letters of mood and morals are the same
	private static final String[] SHORT_NAMES = {"mo", "co", "mr", "ma", "ti", "cu", "sp", "hu", "po"};
	private static final Font FONT_HEADER = new Font(Font.SANS_SERIF, Font.BOLD, 10);
	private static final Font FONT_ARROW = new Font(Font.SANS_SERIF, Font.PLAIN, 8);
	private static final Font FONT_ID = new Font(Font.SANS_SERIF, Font.PLAIN, 10);

	private VisualModel.Snapshot snapshot;
	private long nowMillis;
	private int[] rowIds;
	private int sortColumn = NO_SORTING;
	private boolean descending;
	private int selectedObject = VisualModel.NO_OBJECT;
	private IntConsumer selectionListener = objID -> { };

	AttributeMatrixPanel() {
		setBackground(Color.WHITE);
		// switches the tool tips on, the text comes from getToolTipText(MouseEvent)
		setToolTipText("");
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (hasRoom() && event.getY() < HEADER_HEIGHT && !isInIdArea(event.getX())) {
					sortBy(columnAt(event.getX()));
				}
			}

			@Override
			public void mousePressed(MouseEvent event) {
				if (rowIds != null && hasRoom() && event.getY() >= HEADER_HEIGHT) {
					selectionListener.accept(rowIds[listIndexAt(event.getX(), event.getY())]);
				}
			}
		});
	}

	void setSelectionListener(IntConsumer selectionListener) {
		this.selectionListener = selectionListener;
	}

	void setSelectedObject(int objID) {
		selectedObject = objID;
		repaint();
	}

	void setState(VisualModel.Snapshot snapshot, long nowMillis) {
		this.snapshot = snapshot;
		this.nowMillis = nowMillis;
		rowIds = sortedIds(snapshot, sortColumn, descending);
		repaint();
	}

	void sortBy(int column) {
		if (column != sortColumn) {
			sortColumn = column;
			descending = true;
		}
		else if (descending) {
			descending = false;
		}
		else {
			sortColumn = NO_SORTING;
		}

		if (snapshot != null) {
			rowIds = sortedIds(snapshot, sortColumn, descending);
			repaint();
		}
	}

	int getSortColumn() {
		return sortColumn;
	}

	boolean isDescending() {
		return descending;
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		if (snapshot == null) return;

		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		for (int block = 0; block < BLOCKS; block++) {
			paintHeader(g, block);
			for (int row = 0; row < ROWS_PER_BLOCK; row++) {
				paintRow(g, block, row);
			}
		}
		paintSelection(g);
	}

	private void paintHeader(Graphics2D g, int block) {
		for (int column = 0; column < COLUMNS; column++) {
			int x = columnX(block, column);
			int width = columnX(block, column + 1) - x;
			boolean sorted = column == sortColumn;

			if (sorted) {
				g.setColor(Color.BLACK);
				g.fillRect(x, 0, width - 1, HEADER_HEIGHT - 1);
				g.setFont(FONT_ARROW);
				g.setColor(Color.WHITE);
				String arrow = descending ? "\u25BC" : "\u25B2";
				g.drawString(arrow, x + (width - g.getFontMetrics().stringWidth(arrow)) / 2, 9);
			}

			g.setFont(FONT_HEADER);
			g.setColor(sorted ? Color.WHITE : Color.BLACK);
			String text = shortAttributeName(column);
			g.drawString(text, x + (width - g.getFontMetrics().stringWidth(text)) / 2, HEADER_HEIGHT - 4);
		}
	}

	private void paintRow(Graphics2D g, int block, int row) {
		int objID = rowIds[block * ROWS_PER_BLOCK + row];
		VisualModel.ObjectState state = snapshot.get(objID);
		int y = rowY(row);
		int height = rowY(row + 1) - y;

		paintId(g, objID, block, y, height, Color.BLACK);

		for (int column = 0; column < COLUMNS; column++) {
			int x = columnX(block, column);
			int width = columnX(block, column + 1) - x - 1;

			Color fill = state.attributesKnown ? cellColor(state.attributes[column]) : ActionGridPanel.COLOR_IDLE;
			g.setColor(fill);
			g.fillRect(x, y, width, height);

			float strength = markStrength(state, column, nowMillis);
			if (strength > 0f) {
				Color mark = ActionGridPanel.readableTextOn(fill);
				g.setColor(new Color(mark.getRed(), mark.getGreen(), mark.getBlue(), Math.round(255 * strength)));
				int markHeight = Math.max(2, height / 3);
				g.fillRect(x, y + (height - markHeight) / 2, Math.round(markLength(state.lastChange[column]) * width), markHeight);
			}
		}

		if (row > 0 && row % ROWS_PER_RULE == 0) {
			g.setColor(Color.WHITE);
			g.drawLine(blockX(block) + ID_WIDTH, y, blockX(block) + blockWidth(), y);
		}
	}

	private void paintId(Graphics2D g, int objID, int block, int y, int height, Color color) {
		g.setFont(FONT_ID);
		g.setColor(color);
		FontMetrics metrics = g.getFontMetrics();
		String text = Integer.toString(objID);
		g.drawString(text, blockX(block) + ID_WIDTH - metrics.stringWidth(text) - 4, y + (height + metrics.getAscent()) / 2 - 1);
	}

	private void paintSelection(Graphics2D g) {
		for (int index = 0; index < ROWS; index++) {
			if (rowIds[index] == selectedObject) {
				int block = index / ROWS_PER_BLOCK;
				int row = index % ROWS_PER_BLOCK;
				int y = rowY(row);
				int height = rowY(row + 1) - y;

				g.setColor(Color.BLACK);
				g.fillRect(blockX(block), y, ID_WIDTH - 1, height);
				g.drawRect(blockX(block) + ID_WIDTH, y - 1, blockWidth() - ID_WIDTH - 1, height + 1);
				paintId(g, selectedObject, block, y, height, Color.WHITE);
			}
		}
	}

	private int blockX(int block) {
		return block * (getWidth() + BLOCK_GAP) / BLOCKS;
	}

	private int blockWidth() {
		return (getWidth() - BLOCK_GAP) / BLOCKS;
	}

	private int columnX(int block, int column) {
		return blockX(block) + ID_WIDTH + column * (blockWidth() - ID_WIDTH) / COLUMNS;
	}

	private int rowY(int row) {
		return HEADER_HEIGHT + row * (getHeight() - HEADER_HEIGHT) / ROWS_PER_BLOCK;
	}

	private boolean hasRoom() {
		return blockWidth() > ID_WIDTH && getHeight() > HEADER_HEIGHT;
	}

	private int blockAt(int x) {
		return Math.max(0, Math.min(BLOCKS - 1, x * BLOCKS / (getWidth() + BLOCK_GAP)));
	}

	private boolean isInIdArea(int x) {
		return x - blockX(blockAt(x)) < ID_WIDTH;
	}

	private int columnAt(int x) {
		int xInBlock = x - blockX(blockAt(x));
		return Math.max(0, Math.min(COLUMNS - 1, (xInBlock - ID_WIDTH) * COLUMNS / (blockWidth() - ID_WIDTH)));
	}

	private int listIndexAt(int x, int y) {
		int row = Math.max(0, Math.min(ROWS_PER_BLOCK - 1, (y - HEADER_HEIGHT) * ROWS_PER_BLOCK / (getHeight() - HEADER_HEIGHT)));
		return blockAt(x) * ROWS_PER_BLOCK + row;
	}

	@Override
	public String getToolTipText(MouseEvent event) {
		if (snapshot == null || !hasRoom() || isInIdArea(event.getX())) return null;

		int column = columnAt(event.getX());
		if (event.getY() < HEADER_HEIGHT) {
			return Attribute.getAttributeName(column) + " (Klick sortiert)";
		}

		int objID = rowIds[listIndexAt(event.getX(), event.getY())];
		return describe(objID, snapshot.get(objID), column, nowMillis);
	}

	static String shortAttributeName(int column) {
		return SHORT_NAMES[column];
	}

	/**
	 * @return the object IDs in the order of the rows. Objects without known attributes come last.
	 */
	static int[] sortedIds(VisualModel.Snapshot snapshot, int sortColumn, boolean descending) {
		Integer[] ids = new Integer[ROWS];
		for (int i = 0; i < ROWS; i++) {
			ids[i] = i + 1;
		}

		if (sortColumn != NO_SORTING) {
			// the sort is stable, equal values stay in the order of their IDs
			Arrays.sort(ids, (a, b) -> {
				VisualModel.ObjectState stateA = snapshot.get(a);
				VisualModel.ObjectState stateB = snapshot.get(b);
				if (stateA.attributesKnown != stateB.attributesKnown) {
					return stateA.attributesKnown ? -1 : 1;
				}
				int compared = Integer.compare(stateA.attributes[sortColumn], stateB.attributes[sortColumn]);
				return descending ? -compared : compared;
			});
		}

		int[] result = new int[ROWS];
		for (int i = 0; i < ROWS; i++) {
			result[i] = ids[i];
		}
		return result;
	}

	static Color cellColor(int value) {
		int middle = AttributeArray.ATTRIBUTE_VALUE_MIDDLE;
		if (value >= middle) {
			float share = (float) (value - middle) / (AttributeArray.ATTRIBUTE_VALUE_MAX - middle);
			return ActionGridPanel.blend(COLOR_MIDDLE, COLOR_HIGH, Math.min(1f, share));
		}
		float share = (float) (middle - value) / middle;
		return ActionGridPanel.blend(COLOR_MIDDLE, COLOR_LOW, Math.min(1f, share));
	}

	/**
	 * @return how visible the mark of the last change is: 1 right after the change, 0 when it is gone
	 */
	static float markStrength(VisualModel.ObjectState state, int column, long nowMillis) {
		if (state.lastChange[column] == 0) return 0f;
		float share = 1f - (float) (nowMillis - state.lastChangeMillis[column]) / MARK_MILLIS;
		return Math.max(0f, Math.min(1f, share));
	}

	/**
	 * The square root keeps small changes visible next to large ones.
	 *
	 * @return the length of the mark as a share of the cell width
	 */
	static float markLength(int change) {
		float length = (float) Math.sqrt((double) Math.abs(change) / FULL_MARK_CHANGE);
		return Math.max(SMALLEST_MARK, Math.min(1f, length));
	}

	static String describe(int objID, VisualModel.ObjectState state, int column, long nowMillis) {
		String start = "Objekt " + objID + ", " + Attribute.getAttributeName(column) + ": ";
		if (!state.attributesKnown) return start + "noch kein Wert";

		int value = state.attributes[column];
		String text = start + AttributeArray.toDisplayValue(value) + " (" + value + ")";
		if (state.lastChange[column] == 0) return text + ", noch keine \u00C4nderung";

		long secondsAgo = Math.max(0, (nowMillis - state.lastChangeMillis[column]) / 1000);
		return text + ", letzte \u00C4nderung " + String.format("%+d", state.lastChange[column]) + " vor " + secondsAgo + " s";
	}
}
