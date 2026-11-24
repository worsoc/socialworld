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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import org.socialworld.actions.handle.InventoryPlace;

/**
 * The stick figure with one slot per place of the inventory (2026-11-24). It paints only what the InventoryView
 * says; the lie of the slots comes from InventorySlotLayout. The tooltip of a slot shows the item and what is
 * inside a container.
 */
final class InventoryFigurePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	static final Color COLOR_FIGURE = new Color(90, 90, 90);
	static final Color COLOR_OCCUPIED = new Color(176, 224, 170);
	static final Color COLOR_EMPTY = new Color(240, 240, 240);
	static final Color COLOR_COVERED = new Color(220, 220, 235);
	static final Color COLOR_BORDER = new Color(120, 120, 120);
	static final Color COLOR_TEXT_EMPTY = new Color(150, 150, 150);
	// the lines from a slot to the body: dashed and blue, so that they differ from the grey strokes of the figure
	static final Color COLOR_ANCHOR_LINE = new Color(70, 110, 200);
	private static final BasicStroke ANCHOR_STROKE = new BasicStroke(1.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
			10f, new float[] {5f, 4f}, 0f);

	private InventoryView view = InventoryView.none();

	InventoryFigurePanel() {
		setPreferredSize(new Dimension(620, 760));
		setBackground(Color.WHITE);
		setToolTipText("");	// switches the tooltips on, the text comes from getToolTipText(MouseEvent)
	}

	void setView(InventoryView view) {
		this.view = view;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics.create();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		paintFigure(g);
		if (view.getMessage() != null) {
			g.setColor(COLOR_TEXT_EMPTY);
			g.setFont(getFont().deriveFont(Font.BOLD, 16f));
			g.drawString(view.getMessage(), 20, 30);
		}
		else {
			paintAnchorLines(g);
			for (InventoryPlace place : InventoryPlace.values()) {
				Rectangle2D.Double slot = InventorySlotLayout.slot(place);
				if (slot != null) paintSlot(g, place, toPixels(slot));
			}
		}
		g.dispose();
	}

	private void paintFigure(Graphics2D g) {
		g.setColor(COLOR_FIGURE);
		g.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		double radius = InventorySlotLayout.HEAD_RADIUS * getHeight();
		Point2D.Double head = toPixels(InventorySlotLayout.HEAD);
		g.draw(new Ellipse2D.Double(head.x - radius, head.y - radius, 2 * radius, 2 * radius));
		line(g, InventorySlotLayout.NECK, InventorySlotLayout.HIP);
		line(g, InventorySlotLayout.SHOULDERS, InventorySlotLayout.RIGHT_HAND);
		line(g, InventorySlotLayout.SHOULDERS, InventorySlotLayout.LEFT_HAND);
		line(g, InventorySlotLayout.HIP, InventorySlotLayout.RIGHT_FOOT);
		line(g, InventorySlotLayout.HIP, InventorySlotLayout.LEFT_FOOT);
	}

	private void paintAnchorLines(Graphics2D g) {
		g.setColor(COLOR_ANCHOR_LINE);
		g.setStroke(ANCHOR_STROKE);
		for (InventoryPlace place : InventoryPlace.values()) {
			Point2D.Double anchor = InventorySlotLayout.anchor(place);
			if (anchor == null || view.slot(place).isEmpty()) continue;
			Rectangle2D.Double slot = toPixels(InventorySlotLayout.slot(place));
			Point2D.Double to = toPixels(anchor);
			double fromX = (slot.getCenterX() < to.x) ? slot.getMaxX() : slot.getMinX();
			g.draw(new Line2D.Double(fromX, slot.getCenterY(), to.x, to.y));
		}
	}

	private void paintSlot(Graphics2D g, InventoryPlace place, Rectangle2D.Double rect) {
		InventoryView.Slot slot = view.slot(place);
		boolean covered = view.isCoveredByDress(place);
		g.setColor(!slot.isEmpty() ? COLOR_OCCUPIED : covered ? COLOR_COVERED : COLOR_EMPTY);
		g.fill(rect);
		g.setColor(COLOR_BORDER);
		g.setStroke(new BasicStroke(1f));
		g.draw(rect);

		Font labelFont = getFont().deriveFont(Font.PLAIN, 10f);
		Font itemFont = getFont().deriveFont(Font.BOLD, 12f);
		int x = (int) rect.x + 4;
		int width = (int) rect.width - 8;

		g.setFont(labelFont);
		g.setColor(COLOR_TEXT_EMPTY);
		FontMetrics labelMetrics = g.getFontMetrics();
		int labelY = (int) rect.y + labelMetrics.getAscent() + 1;
		g.drawString(fit(InventorySlotLayout.label(place), labelMetrics, width), x, labelY);

		String text = slot.isEmpty() ? (covered ? "durch Kleid belegt" : InventoryView.EMPTY) : slot.text();
		g.setFont(itemFont);
		g.setColor(slot.isEmpty() ? COLOR_TEXT_EMPTY : Color.BLACK);
		FontMetrics itemMetrics = g.getFontMetrics();
		g.drawString(fit(text, itemMetrics, width), x, labelY + itemMetrics.getAscent() + 1);
	}

	/** Cuts the text with an ellipsis if it does not fit; the tooltip has the full text. */
	static String fit(String text, FontMetrics metrics, int width) {
		if (metrics.stringWidth(text) <= width) return text;
		String ellipsis = "\u2026";
		for (int end = text.length() - 1; end > 0; end--) {
			String cut = text.substring(0, end) + ellipsis;
			if (metrics.stringWidth(cut) <= width) return cut;
		}
		return ellipsis;
	}

	@Override
	public String getToolTipText(MouseEvent event) {
		if (view.getMessage() != null) return null;
		for (InventoryPlace place : InventoryPlace.values()) {
			Rectangle2D.Double slot = InventorySlotLayout.slot(place);
			if (slot == null || !toPixels(slot).contains(event.getPoint())) continue;
			return tooltipOf(place, view.slot(place));
		}
		return null;
	}

	static String tooltipOf(InventoryPlace place, InventoryView.Slot slot) {
		StringBuilder text = new StringBuilder("<html><b>").append(InventorySlotLayout.label(place)).append("</b><br>");
		text.append(slot.isEmpty() ? "leer" : escape(slot.item));
		for (String content : slot.contents) {
			text.append("<br>&nbsp;&nbsp;").append(escape(content).replace("  ", "&nbsp;&nbsp;"));
		}
		return text.append("</html>").toString();
	}

	private static String escape(String text) {
		return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	private void line(Graphics2D g, Point2D.Double from, Point2D.Double to) {
		Point2D.Double a = toPixels(from);
		Point2D.Double b = toPixels(to);
		g.draw(new Line2D.Double(a, b));
	}

	private Point2D.Double toPixels(Point2D.Double fraction) {
		return new Point2D.Double(fraction.x * getWidth(), fraction.y * getHeight());
	}

	private Rectangle2D.Double toPixels(Rectangle2D.Double fraction) {
		return new Rectangle2D.Double(fraction.x * getWidth(), fraction.y * getHeight(),
				fraction.width * getWidth(), fraction.height * getHeight());
	}
}
