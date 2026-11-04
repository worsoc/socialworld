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
package org.socialworld.tools.mct;

import org.socialworld.map.cubes.Cube;
import org.socialworld.map.cubes.CubeCatalog;

/**
 * Connects a tile grid with the cube catalog: for a position on the map it finds
 * the tile and asks the catalog.
 *
 * The rows of a tile grid grow to the south, the y axis inside a tile grows to the
 * north. This class is the only place that has to know about that.
 */
public final class TileGridCubes {

	private static final int TILES_PER_EDGE = 9;
	private static final int INVALID_HEIGHT = -999;

	// keeps a point on the north edge of a tile inside the highest row of its cubes
	private static final double NORTH_EDGE_DISTANCE = 0.00001;

	private final TileGrid root;

	public TileGridCubes(TileGrid root) {
		this.root = root;
	}

	/** @return the edge of the whole grid in meters */
	public int getEdge() {
		return TILES_PER_EDGE * getTileEdge(this.root);
	}

	/**
	 * @param east   the position in meters to the east, from the north west corner of the grid
	 * @param south  the position in meters to the south, from the north west corner of the grid
	 * @param height the height of the point, on the same scale as the heights of the tiles
	 * @return whether the point is in a filled cube; beside the grid nothing is filled
	 * @throws IllegalStateException if the catalog does not know the tile at this position
	 */
	public boolean isFilled(double east, double south, double height) {
		if (east < 0 || east >= getEdge() || south < 0 || south >= getEdge()) {
			return false;
		}

		TileGrid grid = this.root;
		double eastInTile = east;
		double southInTile = south;

		while (true) {
			int tileEdge = getTileEdge(grid);
			int column = (int) (eastInTile / tileEdge);
			int row = (int) (southInTile / tileEdge);
			eastInTile -= column * tileEdge;
			southInTile -= row * tileEdge;

			Tile tile = grid.tiles == null ? null : grid.tiles[row * TILES_PER_EDGE + column];
			if (tile instanceof TileGrid) {
				grid = (TileGrid) tile;
			} else {
				return isFilled(tile, tileEdge, eastInTile, southInTile, height, east, south);
			}
		}
	}

	private static boolean isFilled(Tile tile, int tileEdge, double eastInTile, double southInTile, double height,
			double east, double south) {

		String position = " at " + east + " m east, " + south + " m south.";
		if (tile == null) {
			throw new IllegalStateException("There is no tile" + position);
		}
		if (tile.getHeightLevel() == INVALID_HEIGHT) {
			throw new IllegalStateException("The tile " + tile + " has no valid height" + position);
		}

		double north = Math.min(tileEdge - southInTile, tileEdge - NORTH_EDGE_DISTANCE);
		return CubeCatalog.isFilled(getCatalogSize(tile, position), tile.getNumber(), tile.getHeightLevel(), eastInTile,
				north, height);
	}

	private static byte getCatalogSize(Tile tile, String position) {
		switch (tile.getType()) {
		case largeStandard:
			return Cube.CUBE_SIZE_TILE_LARGE;
		case mediumStandard:
		case mediumAdapter:
			return Cube.CUBE_SIZE_TILE_MEDIUM;
		case smallStandard:
		case smallAdapter:
			return Cube.CUBE_SIZE_TILE_SMALL;
		default:
			throw new IllegalStateException("The cube catalog does not know the tile " + tile + position);
		}
	}

	private static int getTileEdge(TileGrid grid) {
		switch (grid.getTileTypeLevel()) {
		case TileType.TILE_LEVEL_LARGE:
			return Cube.CUBE_SIZE_TILE_LARGE;
		case TileType.TILE_LEVEL_MEDIUM:
			return Cube.CUBE_SIZE_TILE_MEDIUM;
		default:
			return Cube.CUBE_SIZE_TILE_SMALL;
		}
	}
}
