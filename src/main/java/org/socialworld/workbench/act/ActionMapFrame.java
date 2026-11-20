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

import java.awt.Point;
import java.util.Collection;

/**
 * The world area of the scrollable map with a scale that stays fixed once it has been fitted,
 * so that a movement is seen as a movement and not as a change of the zoom.
 * The area only grows: when a human leaves it, it is extended, and the caller shifts the
 * view by the returned pixels so that the part already seen stays where it was.
 */
final class ActionMapFrame {

	private final double scale;
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;

	private ActionMapFrame(double scale, double minX, double minY, double maxX, double maxY) {
		this.scale = scale;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	/** Fits the positions into a view of the given size, as the map did before it could scroll. */
	static ActionMapFrame fit(Collection<Point> positions, int viewWidth, int viewHeight) {
		ActionMapScale fitted = new ActionMapScale(positions, viewWidth, viewHeight);
		int width = Math.max(1, viewWidth);
		int height = Math.max(1, viewHeight);

		// the world area of the whole view, margins included
		double minX = fitted.toWorldX(0);
		double maxX = fitted.toWorldX(width);
		double maxY = fitted.toWorldY(0);
		double minY = fitted.toWorldY(height);
		return new ActionMapFrame(fitted.getScale(), minX, minY, maxX, maxY);
	}

	/**
	 * Extends the area so that every position lies at least the margin inside it.
	 * @return the pixels by which the old content moved to the right (x) and down (y)
	 */
	Point include(Collection<Point> positions) {
		double margin = ActionMapScale.MARGIN / scale;
		double oldMinX = minX;
		double oldMaxY = maxY;

		for (Point position : positions) {
			minX = Math.min(minX, position.x - margin);
			maxX = Math.max(maxX, position.x + margin);
			minY = Math.min(minY, position.y - margin);
			maxY = Math.max(maxY, position.y + margin);
		}
		return new Point(toPixels(oldMinX - minX), toPixels(maxY - oldMaxY));
	}

	/**
	 * Extends the area evenly on both sides until it fills a view of the given size.
	 * @return the pixels by which the old content moved to the right (x) and down (y)
	 */
	Point fill(int viewWidth, int viewHeight) {
		int shiftX = 0;
		int shiftY = 0;

		double missingX = viewWidth / scale - (maxX - minX);
		if (missingX > 0) {
			minX -= missingX / 2;
			maxX += missingX / 2;
			shiftX = toPixels(missingX / 2);
		}
		double missingY = viewHeight / scale - (maxY - minY);
		if (missingY > 0) {
			minY -= missingY / 2;
			maxY += missingY / 2;
			shiftY = toPixels(missingY / 2);
		}
		return new Point(shiftX, shiftY);
	}

	int getPixelWidth() {
		return toPixels(maxX - minX);
	}

	int getPixelHeight() {
		return toPixels(maxY - minY);
	}

	ActionMapScale getScale() {
		return ActionMapScale.forArea(scale, minX, minY, maxX, maxY, getPixelWidth(), getPixelHeight());
	}

	private int toPixels(double millimeters) {
		return (int) Math.round(millimeters * scale);
	}
}
