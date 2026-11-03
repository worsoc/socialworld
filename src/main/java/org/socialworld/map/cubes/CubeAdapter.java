/*
* Social World
* Copyright (C) 2021  Jesper Springer, Mathias Sikos
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The adapter tiles. Along nine tiles in a row one edge rises from height 0 to
 * height 1, by 1/9 per tile. The two other corners stay on one height.
 *
 * The number of a tile tells everything about it:
 * the hundreds digit is the height of the two other corners (1 for height 0, 3 for height 1),
 * the tens digit is the family (see FAMILIES),
 * the units digit is the position in the row (1 ... 9).
 */
public class CubeAdapter extends Cube {

	// the corners by bit index, as in the MapCreationTool
	private static final byte SO = 0;
	private static final byte SW = 1;
	private static final byte NO = 2;
	private static final byte NW = 3;

	// each fold is a diagonal, given by its two corners
	private static final byte[] FOLD_SO_NW = { SO, NW };
	private static final byte[] FOLD_SW_NO = { SW, NO };

	private static final class Family {
		final byte risingFrom;
		final byte risingTo;
		final byte[] fold;

		Family(byte risingFrom, byte risingTo, byte[] fold) {
			this.risingFrom = risingFrom;
			this.risingTo = risingTo;
			this.fold = fold;
		}
	}

	// the index is the tens digit of the tile number
	private static final Family[] FAMILIES = {
			new Family(NW, NO, FOLD_SO_NW),   // x01 ... x09
			new Family(NO, SO, FOLD_SW_NO),   // x11 ... x19
			new Family(NW, SW, FOLD_SO_NW),   // x21 ... x29
			new Family(NO, NW, FOLD_SO_NW),   // x31 ... x39
			new Family(SW, NW, FOLD_SO_NW),   // x41 ... x49
			new Family(SO, NO, FOLD_SW_NO),   // x51 ... x59
			new Family(SW, SO, FOLD_SW_NO),   // x61 ... x69
			new Family(SO, SW, FOLD_SW_NO),   // x71 ... x79
	};

	static String describeTile(int tileNumber) {
		String[] corners = { "SO", "SW", "NO", "NW" };
		Family family = FAMILIES[(tileNumber % 100) / 10];
		return corners[family.risingFrom] + " nach " + corners[family.risingTo] + ", Schritt " + (tileNumber % 10)
				+ "/9, übrige Ecken auf Höhe " + (tileNumber / 100 == 1 ? 0 : 1);
	}

	/**
	 * @return the direction {to the east, to the north} in which the tiles of the
	 *         family of this tile follow each other, each with the value -1, 0 or 1
	 */
	static int[] getRowDirection(int tileNumber) {
		Family family = FAMILIES[(tileNumber % 100) / 10];
		int[][] cornerPositions = { { 1, 0 }, { 0, 0 }, { 1, 1 }, { 0, 1 } }; // SO, SW, NO, NW
		int[] from = cornerPositions[family.risingFrom];
		int[] to = cornerPositions[family.risingTo];
		return new int[] { to[0] - from[0], to[1] - from[1] };
	}

	public CubeAdapter(byte size, byte heightOffset) {
		this.size = size;
		this.heightOffset = heightOffset;
	}

	@Override
	protected void initPlanes() {
		// the planes are calculated from the tile number
	}

	@Override
	protected List<Vector3[]> getPlanesForTile(int tileNumber) {
		int hundreds = tileNumber / 100;
		int tens = (tileNumber % 100) / 10;
		int units = tileNumber % 10;

		if ((hundreds != 1 && hundreds != 3) || tens >= FAMILIES.length || units == 0) {
			throw new IllegalArgumentException("There is no adapter tile " + tileNumber + ".");
		}

		Family family = FAMILIES[tens];

		double[] heights = new double[4];
		Arrays.fill(heights, hundreds == 1 ? 0 : 1);
		heights[family.risingFrom] = (double) (units - 1) / (double) 9;
		heights[family.risingTo] = (double) units / (double) 9;

		byte foldStart = family.fold[0];
		byte foldEnd = family.fold[1];

		List<Vector3[]> planes = new ArrayList<Vector3[]>();
		for (byte corner : new byte[] { SO, SW, NO, NW }) {
			if (corner != foldStart && corner != foldEnd) {
				planes.add(new Vector3[] {
						pointAtCorner(foldStart, heights),
						pointAtCorner(foldEnd, heights),
						pointAtCorner(corner, heights) });
			}
		}
		return planes;
	}

	private Vector3 pointAtCorner(byte corner, double[] heights) {
		Vector3 point = new Vector3(coordinatesOfBitsIndex(corner));
		point.z = heights[corner] + this.heightOffset;
		return point;
	}
}
