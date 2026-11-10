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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Locale;

import javax.swing.JPanel;

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;

/**
 * Everything the display knows about the selected object:
 * position, current action, the attributes with last change and history, the last actions.
 */
class ObjectDetailPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int MARGIN = 10;
	private static final int LINE_HEIGHT = 16;
	private static final int ATTRIBUTE_ROW_HEIGHT = 34;
	private static final int GRAPH_HEIGHT = 12;
	private static final int CHIP_SIZE = 12;
	private static final int SMALLEST_HISTORY_SPAN = 500;
	private static final Font FONT_TITLE = new Font(Font.SANS_SERIF, Font.BOLD, 16);
	private static final Font FONT_HEADING = new Font(Font.SANS_SERIF, Font.BOLD, 12);
	private static final Font FONT_TEXT = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	private static final Color COLOR_HISTORY = SimColorConstants.COLOR_SLATEGRAY;

	private VisualModel.Snapshot snapshot;
	private AttributeHistory history;
	private long nowMillis;
	private int selectedObject = VisualModel.NO_OBJECT;
	private String position = "";
	private String selectionReason = "";

	ObjectDetailPanel() {
		setBackground(Color.WHITE);
	}

	void setState(VisualModel.Snapshot snapshot, AttributeHistory history, long nowMillis, int selectedObject, String position, String selectionReason) {
		this.snapshot = snapshot;
		this.history = history;
		this.nowMillis = nowMillis;
		this.selectedObject = selectedObject;
		this.position = position;
		this.selectionReason = selectionReason;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		if (snapshot == null) return;

		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);

		if (selectedObject == VisualModel.NO_OBJECT) {
			g.setFont(FONT_TEXT);
			g.drawString(selectionReason.isEmpty() ? "Feld oder Zeile anklicken" : selectionReason, MARGIN, MARGIN + LINE_HEIGHT);
			return;
		}

		VisualModel.ObjectState state = snapshot.get(selectedObject);
		int y = paintTitle(g);
		y = paintAction(g, state, y);
		y = paintAttributes(g, state, y);
		paintRecentActions(g, state, y);
	}

	private int paintTitle(Graphics2D g) {
		int y = MARGIN + LINE_HEIGHT;
		g.setFont(FONT_TITLE);
		g.drawString("Mensch " + selectedObject, MARGIN, y);
		y += LINE_HEIGHT;
		g.setFont(FONT_TEXT);
		g.drawString(position, MARGIN, y);
		y += LINE_HEIGHT;
		// the line stays free without a reason, so that the column does not jump
		g.drawString(selectionReason, MARGIN, y);
		return y + LINE_HEIGHT + 4;
	}

	private int paintAction(Graphics2D g, VisualModel.ObjectState state, int y) {
		int textX = MARGIN;
		if (state.acting && ActionFamily.of(state.actionType) != null) {
			paintChip(g, ActionFamily.of(state.actionType).getColor(), MARGIN, y);
			textX += CHIP_SIZE + 5;
		}

		String[] lines = actionLines(state);
		for (int i = 0; i < lines.length; i++) {
			g.setFont(i == 0 ? FONT_HEADING : FONT_TEXT);
			g.drawString(lines[i], textX, y);
			y += LINE_HEIGHT;
		}
		return y + 6;
	}

	private void paintChip(Graphics2D g, Color color, int x, int textBaseline) {
		g.setColor(color);
		g.fillRect(x, textBaseline - CHIP_SIZE + 1, CHIP_SIZE, CHIP_SIZE);
		g.setColor(Color.BLACK);
	}

	private int paintAttributes(Graphics2D g, VisualModel.ObjectState state, int y) {
		g.setFont(FONT_HEADING);
		g.drawString("Attribute (Wert, letzte \u00C4nderung, 5 min)", MARGIN, y);
		y += LINE_HEIGHT;

		for (int attribute = 0; attribute < Attribute.NUMBER_OF_ATTRIBUTES; attribute++) {
			paintAttributeRow(g, state, attribute, y);
			y += ATTRIBUTE_ROW_HEIGHT;
		}
		return y + 6;
	}

	private void paintAttributeRow(Graphics2D g, VisualModel.ObjectState state, int attribute, int y) {
		int width = getWidth() - 2 * MARGIN;
		int graphWidth = (width - MARGIN) / 2;

		g.setFont(FONT_TEXT);
		FontMetrics metrics = g.getFontMetrics();
		g.drawString(Attribute.getAttributeName(attribute).toString(), MARGIN, y);

		if (!state.attributesKnown) {
			String text = "noch kein Wert";
			g.drawString(text, MARGIN + width - metrics.stringWidth(text), y);
			return;
		}

		String text = attributeText(state.attributes[attribute], state.lastChange[attribute]);
		g.drawString(text, MARGIN + width - metrics.stringWidth(text), y);

		paintBar(g, state.attributes[attribute], MARGIN, y + 4, graphWidth);
		paintHistory(g, history.values(selectedObject, attribute), MARGIN + graphWidth + MARGIN, y + 4, graphWidth);
	}

	private void paintBar(Graphics2D g, int value, int x, int y, int width) {
		g.setColor(ActionGridPanel.COLOR_IDLE);
		g.fillRect(x, y, width, GRAPH_HEIGHT);

		int middle = x + width / 2;
		int end = x + Math.round(barShare(value) * width);
		g.setColor(value >= AttributeArray.ATTRIBUTE_VALUE_MIDDLE ? AttributeMatrixPanel.COLOR_HIGH : AttributeMatrixPanel.COLOR_LOW);
		g.fillRect(Math.min(middle, end), y, Math.abs(end - middle), GRAPH_HEIGHT);

		g.setColor(Color.BLACK);
		g.fillRect(middle, y - 1, 1, GRAPH_HEIGHT + 2);
	}

	private void paintHistory(Graphics2D g, int[] values, int x, int y, int width) {
		g.setColor(SimColorConstants.COLOR_WHITESMOKE);
		g.fillRect(x, y, width, GRAPH_HEIGHT);
		if (values.length >= 2) {
			int[] range = historyRange(values);
			int[] xs = new int[values.length];
			int[] ys = new int[values.length];
			for (int i = 0; i < values.length; i++) {
				// the newest value stands at the right edge, a young history does not reach the left one
				xs[i] = x + width - (values.length - 1 - i) * width / (AttributeHistory.SAMPLES - 1);
				ys[i] = y + GRAPH_HEIGHT - 1 - (values[i] - range[0]) * (GRAPH_HEIGHT - 1) / (range[1] - range[0]);
			}
			g.setColor(COLOR_HISTORY);
			g.setStroke(new BasicStroke(1.5f));
			g.drawPolyline(xs, ys, values.length);
		}
		g.setColor(Color.BLACK);
	}

	private void paintRecentActions(Graphics2D g, VisualModel.ObjectState state, int y) {
		g.setFont(FONT_HEADING);
		g.drawString("Letzte Aktionen", MARGIN, y);
		y += LINE_HEIGHT;

		g.setFont(FONT_TEXT);
		if (state.recentActions[0] == null) {
			g.drawString("noch keine", MARGIN, y);
			return;
		}

		for (VisualModel.FinishedAction action : state.recentActions) {
			if (action == null) return;
			paintChip(g, ActionFamily.of(action.type).getColor(), MARGIN, y);
			g.drawString(describe(action, nowMillis), MARGIN + CHIP_SIZE + 5, y);
			y += LINE_HEIGHT;
		}
	}

	/**
	 * @return the lines about the current action, the first one is the most important
	 */
	static String[] actionLines(VisualModel.ObjectState state) {
		if (!state.acting || ActionFamily.of(state.actionType) == null) {
			return new String[] {"Unt\u00E4tig", " ", " "};
		}
		return new String[] {
				state.actionMode + " (" + state.actionType + ")",
				"Intensit\u00E4t " + (int) state.intensity + ", Priorit\u00E4t " + state.priority,
				"Rest " + state.remainedDuration / 1000 + " s von " + state.duration / 1000 + " s"};
	}

	static String attributeText(int value, int lastChange) {
		String text = Integer.toString(AttributeArray.toDisplayValue(value));
		return lastChange == 0 ? text : text + "  " + changeText(lastChange);
	}

	/**
	 * @return the change on the scale of the displayed values, with a decimal because many changes are smaller than 1
	 */
	static String changeText(int change) {
		String arrow = change > 0 ? "\u25B2" : "\u25BC";
		return arrow + String.format(Locale.ROOT, "%.1f", Math.abs(change) / (double) AttributeArray.ATTRIBUTE_VALUE_SCALE);
	}

	static float barShare(int value) {
		return Math.max(0f, Math.min(1f, (float) value / AttributeArray.ATTRIBUTE_VALUE_MAX));
	}

	/**
	 * The scale follows the values of the attribute, so that small drifts can be seen.
	 * The smallest span keeps tiny wobbling from looking like a large movement.
	 *
	 * @return the lowest and the highest value of the scale
	 */
	static int[] historyRange(int[] values) {
		int low = values[0];
		int high = values[0];
		for (int value : values) {
			low = Math.min(low, value);
			high = Math.max(high, value);
		}
		if (high - low < SMALLEST_HISTORY_SPAN) {
			low = (low + high - SMALLEST_HISTORY_SPAN) / 2;
			high = low + SMALLEST_HISTORY_SPAN;
		}
		return new int[] {low, high};
	}

	/**
	 * @param x,y,z in millimeters
	 */
	static String positionText(int x, int y, int z) {
		return String.format(Locale.ROOT, "Position x %.1f m, y %.1f m, z %.1f m", x / 1000.0, y / 1000.0, z / 1000.0);
	}

	static String describe(VisualModel.FinishedAction action, long nowMillis) {
		long secondsAgo = Math.max(0, (nowMillis - action.endedMillis) / 1000);
		return ActionFamily.shortName(action.mode) + ", vor " + secondsAgo + " s" + (action.endedEarly ? ", unterbrochen" : "");
	}
}
