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
import java.util.Map;

/**
 * Maps positions in millimeters to the pixels of the map: the same scale for x
 * and y, all positions visible and centered, y growing upwards.
 */
final class ActionMapScale {

	static final int NO_OBJECT = 0;

	private static final int MARGIN = 24;
	private static final double MINIMUM_SPAN = 20000;

	private final double scale;
	private final double middleX;
	private final double middleY;
	private final int pixelMiddleX;
	private final int pixelMiddleY;

	ActionMapScale(Collection<Point> positions, int width, int height) {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;

		for (Point position : positions) {
			minX = Math.min(minX, position.x);
			minY = Math.min(minY, position.y);
			maxX = Math.max(maxX, position.x);
			maxY = Math.max(maxY, position.y);
		}

		if (positions.isEmpty()) {
			minX = 0;
			minY = 0;
			maxX = 0;
			maxY = 0;
		}

		double spanX = Math.max(MINIMUM_SPAN, (double) maxX - minX);
		double spanY = Math.max(MINIMUM_SPAN, (double) maxY - minY);
		double usableWidth = Math.max(1, width - 2 * MARGIN);
		double usableHeight = Math.max(1, height - 2 * MARGIN);

		this.scale = Math.min(usableWidth / spanX, usableHeight / spanY);
		this.middleX = ((double) minX + maxX) / 2;
		this.middleY = ((double) minY + maxY) / 2;
		this.pixelMiddleX = width / 2;
		this.pixelMiddleY = height / 2;
	}

	int toPixelX(int x) {
		return (int) Math.round(pixelMiddleX + (x - middleX) * scale);
	}

	int toPixelY(int y) {
		return (int) Math.round(pixelMiddleY - (y - middleY) * scale);
	}

	int toWorldX(int pixelX) {
		return (int) Math.round(middleX + (pixelX - pixelMiddleX) / scale);
	}

	int toWorldY(int pixelY) {
		return (int) Math.round(middleY - (pixelY - pixelMiddleY) / scale);
	}

	int toPixelLength(double millimeters) {
		return (int) Math.round(millimeters * scale);
	}

	/**
	 * @return the object drawn nearest to the pixel, {@link #NO_OBJECT} if none lies within the tolerance
	 */
	int findObjectAt(int pixelX, int pixelY, int tolerance, Map<Integer, Point> positions) {
		int found = NO_OBJECT;
		long bestDistance = (long) tolerance * tolerance;

		for (Map.Entry<Integer, Point> entry : positions.entrySet()) {
			long dx = toPixelX(entry.getValue().x) - pixelX;
			long dy = toPixelY(entry.getValue().y) - pixelY;
			long distance = dx * dx + dy * dy;

			if (distance <= bestDistance) {
				bestDistance = distance;
				found = entry.getKey();
			}
		}
		return found;
	}
}
