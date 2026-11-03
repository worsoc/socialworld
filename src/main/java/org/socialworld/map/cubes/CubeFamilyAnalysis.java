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

/**
 * The nine tiles of an adapter family, ordered by where they lie next to each
 * other: from west to east or from south to north.
 */
final class CubeFamilyAnalysis {

	static final int TILES_IN_FAMILY = 9;

	final CubeTileAnalysis[] tilesByPosition = new CubeTileAnalysis[TILES_IN_FAMILY];
	final boolean isWestToEast;
	final int selectedTile;
	final byte size;
	final byte depth;

	final double lowestTop;
	final double highestTop;
	final double lowestSurface;
	final double highestSurface;

	CubeFamilyAnalysis(int tile, byte size, byte height, byte depth) {
		this.selectedTile = tile;
		this.size = size;
		this.depth = depth;

		int[] direction = CubeAdapter.getRowDirection(tile);
		this.isWestToEast = direction[0] != 0;
		boolean numbersRiseWithPosition = direction[0] + direction[1] > 0;

		int firstTile = tile - tile % 10 + 1;
		double lowTop = Double.POSITIVE_INFINITY;
		double highTop = Double.NEGATIVE_INFINITY;
		double lowSurface = Double.POSITIVE_INFINITY;
		double highSurface = Double.NEGATIVE_INFINITY;

		for (int step = 0; step < TILES_IN_FAMILY; step++) {
			int position = numbersRiseWithPosition ? step : TILES_IN_FAMILY - 1 - step;
			CubeTileAnalysis analysis = new CubeTileAnalysis(firstTile + step, size, height, depth);
			this.tilesByPosition[position] = analysis;
			lowTop = Math.min(lowTop, analysis.lowestTop);
			highTop = Math.max(highTop, analysis.highestTop);
			lowSurface = Math.min(lowSurface, analysis.lowestSurface);
			highSurface = Math.max(highSurface, analysis.highestSurface);
		}

		this.lowestTop = lowTop;
		this.highestTop = highTop;
		this.lowestSurface = lowSurface;
		this.highestSurface = highSurface;
	}

	CubeTileAnalysis getTile(int tile) {
		for (CubeTileAnalysis analysis : this.tilesByPosition) {
			if (analysis.tile == tile) {
				return analysis;
			}
		}
		throw new IllegalArgumentException("Tile " + tile + " is not in this family.");
	}

	int getTotalAddresses() {
		int total = 0;
		for (CubeTileAnalysis analysis : this.tilesByPosition) {
			total += analysis.addresses.size();
		}
		return total;
	}

	long getTotalMemoryBytes() {
		long total = 0;
		for (CubeTileAnalysis analysis : this.tilesByPosition) {
			total += analysis.memoryBytes;
		}
		return total;
	}
}
