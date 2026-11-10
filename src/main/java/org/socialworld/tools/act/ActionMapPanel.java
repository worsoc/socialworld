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
package org.socialworld.tools.act;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;

import javax.swing.JPanel;

/**
 * Shows the humans seen from above, the causer, the range of the event and who is within it.
 */
final class ActionMapPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int CLICK_TOLERANCE = 10;
	private static final int DRAG_THRESHOLD = 4;
	private static final int DOT = 4;
	private static final int DOT_CAUSER = 7;
	private static final Color COLOR_HUMAN = new Color(150, 150, 150);
	private static final Color COLOR_IN_RANGE = new Color(214, 96, 20);
	private static final Color COLOR_CAUSER = Color.BLACK;
	private static final Color COLOR_RANGE_FILL = new Color(214, 96, 20, 36);
	private static final Color COLOR_RANGE_LINE = new Color(214, 96, 20);
	private static final int SCALE_BAR_MILLIMETERS = 10000;

	private Map<Integer, Point> positions = new HashMap<Integer, Point>();
	private List<Integer> objectsInRange = new ArrayList<Integer>();
	private int causer = ActionMapScale.NO_OBJECT;
	private float rangeInMillimeters;
	private IntConsumer selectionListener = id -> { };
	private BiConsumer<Integer, Point> relocationListener = (id, position) -> { };

	private int pressedObject = ActionMapScale.NO_OBJECT;
	private Point pressedAt = new Point();
	private Point draggedTo = new Point();
	private boolean dragging;

	ActionMapPanel() {
		setBackground(Color.WHITE);

		MouseAdapter mouse = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent event) {
				pressedObject = createScale().findObjectAt(event.getX(), event.getY(), CLICK_TOLERANCE, positions);
				pressedAt = event.getPoint();
				dragging = false;
			}

			@Override
			public void mouseDragged(MouseEvent event) {
				if (pressedObject == ActionMapScale.NO_OBJECT) return;

				if (pressedAt.distance(event.getPoint()) >= DRAG_THRESHOLD) dragging = true;
				draggedTo = event.getPoint();
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				if (pressedObject == ActionMapScale.NO_OBJECT) return;

				if (dragging) {
					ActionMapScale scale = createScale();
					relocationListener.accept(pressedObject, new Point(scale.toWorldX(event.getX()), scale.toWorldY(event.getY())));
				}
				else {
					selectionListener.accept(pressedObject);
				}
				pressedObject = ActionMapScale.NO_OBJECT;
				dragging = false;
				repaint();
			}
		};
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	void setSelectionListener(IntConsumer listener) {
		this.selectionListener = listener;
	}

	/** Gets the object and its new x and y in millimeters when a human has been dragged. */
	void setRelocationListener(BiConsumer<Integer, Point> listener) {
		this.relocationListener = listener;
	}

	void show(Map<Integer, Point> positions, int causer, float rangeInMillimeters, List<Integer> objectsInRange) {
		this.positions = positions;
		this.causer = causer;
		this.rangeInMillimeters = rangeInMillimeters;
		this.objectsInRange = objectsInRange;
		repaint();
	}

	private ActionMapScale createScale() {
		return new ActionMapScale(positions.values(), getWidth(), getHeight());
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		ActionMapScale scale = createScale();

		drawRange(g, scale);
		for (Map.Entry<Integer, Point> entry : positions.entrySet()) {
			drawHuman(g, scale, entry.getKey(), entry.getValue());
		}
		drawDragged(g, scale);
		drawScaleBar(g, scale);
	}

	private void drawDragged(Graphics2D g, ActionMapScale scale) {
		if (!dragging) return;

		g.setColor(COLOR_CAUSER);
		g.drawOval(draggedTo.x - DOT_CAUSER, draggedTo.y - DOT_CAUSER, 2 * DOT_CAUSER, 2 * DOT_CAUSER);

		String text = String.valueOf(pressedObject);
		Point causerPosition = positions.get(causer);
		if (causerPosition != null && pressedObject != causer) {
			Point target = new Point(scale.toWorldX(draggedTo.x), scale.toWorldY(draggedTo.y));
			text += String.format(Locale.ROOT, ": %.1f m", target.distance(causerPosition) / 1000);
		}
		g.drawString(text, draggedTo.x + DOT_CAUSER + 2, draggedTo.y - DOT_CAUSER);
	}

	private void drawRange(Graphics2D g, ActionMapScale scale) {
		Point position = positions.get(causer);
		if (position == null) return;

		int radius = scale.toPixelLength(rangeInMillimeters);
		int x = scale.toPixelX(position.x);
		int y = scale.toPixelY(position.y);

		g.setColor(COLOR_RANGE_FILL);
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
		g.setColor(COLOR_RANGE_LINE);
		g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}

	private void drawHuman(Graphics2D g, ActionMapScale scale, int id, Point position) {
		boolean isCauser = id == causer;
		boolean inRange = objectsInRange.contains(id);
		int size = isCauser ? DOT_CAUSER : DOT;
		int x = scale.toPixelX(position.x);
		int y = scale.toPixelY(position.y);

		g.setColor(isCauser ? COLOR_CAUSER : inRange ? COLOR_IN_RANGE : COLOR_HUMAN);
		g.fillOval(x - size, y - size, 2 * size, 2 * size);

		if (isCauser || inRange) g.drawString(String.valueOf(id), x + size + 2, y - size);
	}

	private void drawScaleBar(Graphics2D g, ActionMapScale scale) {
		int length = scale.toPixelLength(SCALE_BAR_MILLIMETERS);
		int y = getHeight() - 8;

		g.setColor(Color.BLACK);
		g.drawLine(8, y, 8 + length, y);
		g.drawString(SCALE_BAR_MILLIMETERS / 1000 + " m", 8, y - 4);
	}
}
