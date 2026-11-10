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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.IntConsumer;

import javax.swing.JPanel;

/**
 * One field per object, arranged by object ID.
 * Colour: family of the action. Text: ID, intensity and short name of the mode.
 * Bar at the bottom: remaining duration. An ended action fades out, so that short ones can be seen.
 */
class ActionGridPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	static final int COLUMNS = 10;
	static final long FADE_MILLIS = 3000;
	static final Color COLOR_IDLE = SimColorConstants.COLOR_GAINSBORO;

	private static final int GAP = 2;
	private static final int BAR_HEIGHT = 6;
	private static final int INTERRUPTED_BORDER = 3;
	private static final int SELECTION_BORDER = 3;
	private static final Font FONT_SMALL = new Font(Font.SANS_SERIF, Font.PLAIN, 11);
	private static final Font FONT_MODE = new Font(Font.SANS_SERIF, Font.BOLD, 14);

	private VisualModel.Snapshot snapshot;
	private long nowMillis;
	private int selectedObject = VisualModel.NO_OBJECT;
	private IntConsumer selectionListener = objID -> { };
	private PictureSequence.Figure figure;

	ActionGridPanel() {
		setBackground(Color.WHITE);
		// switches the tool tips on, the text comes from getToolTipText(MouseEvent)
		setToolTipText("");
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent event) {
				if (getWidth() > 0 && getHeight() > 0) {
					selectionListener.accept(objIdAt(event.getX(), event.getY(), getWidth(), getHeight()));
				}
			}
		});
	}

	void setSelectionListener(IntConsumer selectionListener) {
		this.selectionListener = selectionListener;
	}

	/**
	 * Replaces the actions by the figure of the picture sequence.
	 */
	void setFigure(PictureSequence.Figure figure) {
		this.figure = figure;
		repaint();
	}

	void setSelectedObject(int objID) {
		selectedObject = objID;
		repaint();
	}

	void setState(VisualModel.Snapshot snapshot, long nowMillis) {
		this.snapshot = snapshot;
		this.nowMillis = nowMillis;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		if (snapshot == null && figure == null) return;

		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (figure != null) {
			// the gaps stay visible, so that the white fields can be told from each other
			g.setColor(COLOR_IDLE);
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		int rows = VisualModel.OBJECT_COUNT / COLUMNS;
		for (int objID = 1; objID <= VisualModel.OBJECT_COUNT; objID++) {
			int column = (objID - 1) % COLUMNS;
			int row = (objID - 1) / COLUMNS;
			int x = column * getWidth() / COLUMNS;
			int y = row * getHeight() / rows;
			int width = (column + 1) * getWidth() / COLUMNS - x - GAP;
			int height = (row + 1) * getHeight() / rows - y - GAP;
			if (figure != null) {
				g.setColor(figure.isSet(objID - 1) ? figure.getColor() : Color.WHITE);
				g.fillRect(x, y, width, height);
			}
			else {
				paintField(g, objID, snapshot.get(objID), x, y, width, height);
			}
		}
	}

	private void paintField(Graphics2D g, int objID, VisualModel.ObjectState state, int x, int y, int width, int height) {
		Color fill = fillColor(state, nowMillis);
		Color text = readableTextOn(fill);

		g.setColor(fill);
		g.fillRect(x, y, width, height);

		g.setColor(text);
		g.setFont(FONT_SMALL);
		FontMetrics small = g.getFontMetrics();
		g.drawString(Integer.toString(objID), x + 4, y + small.getAscent() + 2);

		if (isVisible(state, nowMillis)) {
			String intensity = Integer.toString((int) state.intensity);
			g.drawString(intensity, x + width - small.stringWidth(intensity) - 4, y + small.getAscent() + 2);

			g.setFont(FONT_MODE);
			FontMetrics mode = g.getFontMetrics();
			String name = ActionFamily.shortName(state.actionMode);
			g.drawString(name, x + (width - mode.stringWidth(name)) / 2, y + (height + mode.getAscent()) / 2);
		}

		if (state.acting) {
			int barWidth = (int) (remainingShare(state) * width);
			g.setColor(text);
			g.fillRect(x, y + height - BAR_HEIGHT, barWidth, BAR_HEIGHT);
		}

		if (state.endedEarly && isVisible(state, nowMillis)) {
			int inset = objID == selectedObject ? SELECTION_BORDER : 0;
			g.setColor(SimColorConstants.COLOR_RED);
			g.setStroke(new BasicStroke(INTERRUPTED_BORDER));
			g.drawRect(x + 1 + inset, y + 1 + inset, width - INTERRUPTED_BORDER - 2 * inset, height - INTERRUPTED_BORDER - 2 * inset);
		}

		if (objID == selectedObject) {
			g.setColor(Color.BLACK);
			g.setStroke(new BasicStroke(2));
			g.drawRect(x + 1, y + 1, width - 2, height - 2);
			g.setColor(Color.WHITE);
			g.setStroke(new BasicStroke(1));
			g.drawRect(x + 2, y + 2, width - 5, height - 5);
		}
	}

	@Override
	public String getToolTipText(MouseEvent event) {
		if (figure != null || snapshot == null || getWidth() <= 0 || getHeight() <= 0) return null;

		int objID = objIdAt(event.getX(), event.getY(), getWidth(), getHeight());
		return describe(objID, snapshot.get(objID));
	}

	/**
	 * @return the share of the fading out of an ended action: 0 for a running action, 1 for completely faded
	 */
	static float fadedShare(VisualModel.ObjectState state, long nowMillis) {
		if (state.acting) return 0f;
		float share = (float) (nowMillis - state.actionEndedMillis) / FADE_MILLIS;
		return Math.max(0f, Math.min(1f, share));
	}

	static boolean isVisible(VisualModel.ObjectState state, long nowMillis) {
		return ActionFamily.of(state.actionType) != null && fadedShare(state, nowMillis) < 1f;
	}

	static Color fillColor(VisualModel.ObjectState state, long nowMillis) {
		if (!isVisible(state, nowMillis)) return COLOR_IDLE;
		return blend(ActionFamily.of(state.actionType).getColor(), COLOR_IDLE, fadedShare(state, nowMillis));
	}

	static float remainingShare(VisualModel.ObjectState state) {
		if (state.duration <= 0) return 0f;
		return Math.max(0f, Math.min(1f, (float) state.remainedDuration / state.duration));
	}

	static Color blend(Color from, Color to, float share) {
		return new Color(
				Math.round(from.getRed() + (to.getRed() - from.getRed()) * share),
				Math.round(from.getGreen() + (to.getGreen() - from.getGreen()) * share),
				Math.round(from.getBlue() + (to.getBlue() - from.getBlue()) * share));
	}

	/**
	 * @return black or white, whichever has the higher contrast to the background (WCAG luminance)
	 */
	static Color readableTextOn(Color background) {
		double luminance = 0.2126 * linear(background.getRed())
				+ 0.7152 * linear(background.getGreen())
				+ 0.0722 * linear(background.getBlue());
		double contrastToWhite = 1.05 / (luminance + 0.05);
		double contrastToBlack = (luminance + 0.05) / 0.05;
		return contrastToBlack >= contrastToWhite ? Color.BLACK : Color.WHITE;
	}

	private static double linear(int channel) {
		double value = channel / 255.0;
		return value <= 0.03928 ? value / 12.92 : Math.pow((value + 0.055) / 1.055, 2.4);
	}

	static int objIdAt(int x, int y, int width, int height) {
		int rows = VisualModel.OBJECT_COUNT / COLUMNS;
		int column = Math.max(0, Math.min(COLUMNS - 1, x * COLUMNS / width));
		int row = Math.max(0, Math.min(rows - 1, y * rows / height));
		return row * COLUMNS + column + 1;
	}

	static String describe(int objID, VisualModel.ObjectState state) {
		if (state.actionType == null) return "Objekt " + objID + ": noch keine Aktion";

		StringBuilder text = new StringBuilder("Objekt " + objID + ": ");
		text.append(state.actionMode).append(" (").append(state.actionType).append(")");
		text.append(", Intensit\u00E4t ").append((int) state.intensity);
		if (state.acting) {
			text.append(", Rest ").append(state.remainedDuration / 1000);
			text.append(" s von ").append(state.duration / 1000).append(" s");
		}
		text.append(", Priorit\u00E4t ").append(state.priority);
		if (!state.acting) {
			text.append(state.endedEarly ? ", unterbrochen" : ", beendet");
		}
		return text.toString();
	}
}
