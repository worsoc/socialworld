/*
* Social World
* Copyright (C) 2026  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.map.cubes;

import java.util.List;

/**
 * The ground surface of a tile, made of one plane or of two planes that meet in
 * a fold along a diagonal of the tile.
 *
 * A fold is either a ridge (the surface is the lower of the two planes) or a
 * valley (the surface is the higher of the two planes). Which one it is follows
 * from the planes themselves: the corner that only the second plane defines
 * lies below the first plane on a ridge and above it in a valley.
 */
class TileSurface {

	private final Vector3[] firstPlane;
	private final Vector3[] secondPlane;
	private final boolean isRidge;

	TileSurface(List<Vector3[]> planes) {
		if (planes.size() > 2) {
			throw new IllegalArgumentException("A tile surface has at most two planes.");
		}

		this.firstPlane = planes.get(0);

		if (planes.size() == 1) {
			this.secondPlane = null;
			this.isRidge = false;
			return;
		}

		this.secondPlane = planes.get(1);

		Vector3 cornerOfSecondPlaneOnly = null;
		for (Vector3 point : this.secondPlane) {
			if (!isCornerOfFirstPlane(point)) {
				cornerOfSecondPlaneOnly = point;
			}
		}
		this.isRidge = deltaToPlane(this.firstPlane, cornerOfSecondPlaneOnly) < 0;
	}

	private boolean isCornerOfFirstPlane(Vector3 point) {
		for (Vector3 corner : this.firstPlane) {
			if (corner.x == point.x && corner.y == point.y) {
				return true;
			}
		}
		return false;
	}

	private static double deltaToPlane(Vector3[] plane, Vector3 point) {
		return Cube.getDelta(plane[0], plane[1], plane[2], point);
	}

	/**
	 * @return 1 if the point is above the surface, -1 if it is below the surface
	 *         and 0 if it is on the surface
	 */
	int sideOf(double x, double y, double z) {
		Vector3 point = new Vector3(x, y, z);
		int side = (int) Math.signum(deltaToPlane(this.firstPlane, point));

		if (this.secondPlane == null) {
			return side;
		}

		int sideOfSecondPlane = (int) Math.signum(deltaToPlane(this.secondPlane, point));
		return this.isRidge ? Math.max(side, sideOfSecondPlane) : Math.min(side, sideOfSecondPlane);
	}

	/**
	 * Checks whether the surface runs through the axis-parallel cube with the
	 * origin (x, y, z) and the given edge length, which means that the cube has
	 * corners on both sides of it. A cube that only touches the surface stays whole.
	 * 
	 * Comparing the corners is enough although the surface can have a fold: the
	 * fold is a diagonal of the tile, so it only passes through the cubes from
	 * corner to corner.
	 */
	boolean isSplitting(double x, double y, double z, double edge) {
		boolean hasCornerAbove = false;
		boolean hasCornerBelow = false;

		for (double cornerX : new double[] { x, x + edge }) {
			for (double cornerY : new double[] { y, y + edge }) {
				for (double cornerZ : new double[] { z, z + edge }) {
					int side = sideOf(cornerX, cornerY, cornerZ);
					hasCornerAbove = hasCornerAbove || side > 0;
					hasCornerBelow = hasCornerBelow || side < 0;
				}
			}
		}

		return hasCornerAbove && hasCornerBelow;
	}
}
