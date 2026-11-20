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
package org.socialworld.workbench.act;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;

import javax.swing.JPanel;
import javax.swing.JViewport;

import org.socialworld.actions.ActionMode;
import org.socialworld.visualize.SimColorConstants;

/**
 * Shows the humans seen from above, the causer, the range of the event and who is within it.
 * The scale is fitted once and then stays fixed; the map lies in a scroll pane and grows when
 * a human leaves it (see ActionMapFrame). Each human is coloured by its running action
 * (see ActionColors), a legend lists the actions running now.
 */
final class ActionMapPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int CLICK_TOLERANCE = 10;
	private static final int DRAG_THRESHOLD = 4;
	private static final int DOT = 4;
	private static final int DOT_CAUSER = 7;
	// idle as in the dashboard grid (ActionGridPanel.COLOR_IDLE), apart from the slate gray of perception
	private static final Color COLOR_HUMAN = SimColorConstants.COLOR_GAINSBORO;
	private static final Color COLOR_IN_RANGE = new Color(214, 96, 20);
	private static final Color COLOR_CAUSER = Color.BLACK;
	private static final Color COLOR_RANGE_FILL = new Color(214, 96, 20, 36);
	private static final Color COLOR_RANGE_LINE = new Color(214, 96, 20);
	private static final int SCALE_BAR_MILLIMETERS = 10000;

	private Map<Integer, Point> positions = new HashMap<Integer, Point>();
	private List<Integer> objectsInRange = new ArrayList<Integer>();
	private Map<Integer, ActionMode> running = new HashMap<Integer, ActionMode>();
	private ActionMapFrame frame;
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

	void show(Map<Integer, Point> positions, Map<Integer, ActionMode> running, int causer, float rangeInMillimeters,
			List<Integer> objectsInRange) {
		this.positions = positions;
		this.running = running;
		this.causer = causer;
		this.rangeInMillimeters = rangeInMillimeters;
		this.objectsInRange = objectsInRange;
		updateFrame();
		repaint();
	}

	/** Fits the scale anew to the humans as they are now (button "Karte einpassen"). */
	void fitToHumans() {
		frame = null;
		updateFrame();
		repaint();
	}

	private void updateFrame() {
		if (!(getParent() instanceof JViewport)) return;
		JViewport viewport = (JViewport) getParent();
		Dimension view = viewport.getExtentSize();
		if (view.width <= 0 || view.height <= 0) return;

		Point viewPosition = viewport.getViewPosition();
		if (frame == null) {
			if (positions.isEmpty()) return;
			frame = ActionMapFrame.fit(positions.values(), view.width, view.height);
			viewPosition = new Point();
		}

		Point shiftInclude = frame.include(positions.values());
		Point shiftFill = frame.fill(view.width, view.height);

		Dimension size = new Dimension(frame.getPixelWidth(), frame.getPixelHeight());
		if (!size.equals(getPreferredSize())) {
			setPreferredSize(size);
			setSize(size);
			revalidate();
		}
		// the part already seen keeps its place on the screen
		viewport.setViewPosition(new Point(viewPosition.x + shiftInclude.x + shiftFill.x,
				viewPosition.y + shiftInclude.y + shiftFill.y));
	}

	private ActionMapScale createScale() {
		if (frame != null) return frame.getScale();
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
		drawLegend(g);
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
		Color action = ActionColors.of(running.get(id));
		int size = isCauser ? DOT_CAUSER : DOT;
		int x = scale.toPixelX(position.x);
		int y = scale.toPixelY(position.y);

		Color fill = action != null ? action : isCauser ? COLOR_CAUSER : inRange ? COLOR_IN_RANGE : COLOR_HUMAN;
		g.setColor(fill);
		g.fillOval(x - size, y - size, 2 * size, 2 * size);

		// with an action the fill shows the action, a ring keeps causer (black) and range (orange) visible
		Color ring = action == null ? null : isCauser ? COLOR_CAUSER : inRange ? COLOR_IN_RANGE : null;
		if (ring != null) {
			g.setColor(ring);
			g.drawOval(x - size - 2, y - size - 2, 2 * size + 4, 2 * size + 4);
		}

		if (isCauser || inRange || action != null) {
			g.setColor(isCauser ? COLOR_CAUSER : inRange ? COLOR_IN_RANGE : action);
			g.drawString(String.valueOf(id), x + size + 2, y - size);
		}
	}

	/** the actions running now with their colour and how many humans do them, at the upper left of the part seen */
	private void drawLegend(Graphics2D g) {
		Map<ActionMode, Integer> counts = new TreeMap<ActionMode, Integer>();
		for (ActionMode mode : running.values()) {
			if (ActionColors.of(mode) != null) counts.merge(mode, 1, Integer::sum);
		}
		if (counts.isEmpty()) return;

		Rectangle visible = getVisibleRect();
		int lineHeight = g.getFontMetrics().getHeight();
		int x = visible.x + 8;
		int y = visible.y + 8;

		g.setColor(new Color(255, 255, 255, 210));
		g.fillRect(x - 4, y - 4, 150, counts.size() * lineHeight + 8);

		for (Map.Entry<ActionMode, Integer> entry : counts.entrySet()) {
			g.setColor(ActionColors.of(entry.getKey()));
			g.fillOval(x, y + lineHeight / 2 - DOT, 2 * DOT, 2 * DOT);
			g.setColor(Color.BLACK);
			g.drawString(entry.getKey() + " " + entry.getValue(), x + 2 * DOT + 6, y + lineHeight - 4);
			y += lineHeight;
		}
	}

	private void drawScaleBar(Graphics2D g, ActionMapScale scale) {
		// at the lower left of the part that is seen, also when the map is scrolled
		Rectangle visible = getVisibleRect();
		int length = scale.toPixelLength(SCALE_BAR_MILLIMETERS);
		int x = visible.x + 8;
		int y = visible.y + visible.height - 8;

		g.setColor(Color.BLACK);
		g.drawLine(x, y, x + length, y);
		g.drawString(SCALE_BAR_MILLIMETERS / 1000 + " m", x, y - 4);
	}
}
