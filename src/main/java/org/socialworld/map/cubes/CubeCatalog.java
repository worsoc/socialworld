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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The cubes of every tile type. A type is created when it is asked for the first
 * time and is stored without the ground, because the ground follows from the
 * height. So a map only has to know the number and the height of a tile.
 *
 * The cubes of the deepest level have an edge of 1/9 m on small and medium tiles
 * and of 1/3 m on large tiles.
 */
public final class CubeCatalog {

	private static final byte DEPTH_SMALL = 2;
	private static final byte DEPTH_MEDIUM = 4;
	private static final byte DEPTH_LARGE = 5;

	private static final int TILE_NUMBERS = 1000;

	private static final Map<Integer, CubeTree> SHAPES = new ConcurrentHashMap<Integer, CubeTree>();

	private CubeCatalog() {
	}

	/**
	 * A tile inside its cube, so that its cubes can be asked for by address.
	 *
	 * @param size   the edge of the tile in meters: 1, 9 or 81
	 * @param tile   the number of the tile
	 * @param height the height of the tile inside its cube
	 */
	public static CubeCatalogTile getTile(byte size, int tile, int height) {
		CubeTree shape = getShape(size, tile);
		int lowestHeight = CubeTileAnalysis.getMinHeight(size, tile);
		if (height < lowestHeight || height > CubeTileAnalysis.getMaxHeight(size)) {
			throw new IllegalArgumentException("Tile " + tile + " of the size " + size + " cannot have the height " + height + ".");
		}
		return new CubeCatalogTile(shape, size, height - lowestHeight);
	}

	/**
	 * Asks a tile at a height of the whole map, which can be negative or higher than
	 * the cube of the tile. Below the shape of the tile everything is filled.
	 *
	 * @param size   the edge of the tile in meters: 1, 9 or 81
	 * @param tile   the number of the tile
	 * @param height the height of the tile on the map
	 * @param x      the position in meters to the east, from the west edge of the tile
	 * @param y      the position in meters to the north, from the south edge of the tile
	 * @param z      the height of the point on the map
	 * @return whether the point is in a filled cube; outside the edges of the tile nothing is filled
	 */
	public static boolean isFilled(byte size, int tile, int height, double x, double y, double z) {
		CubeTree shape = getShape(size, tile);
		if (x < 0 || x >= size || y < 0 || y >= size) {
			return false;
		}
		int groundHeight = height - CubeTileAnalysis.getMinHeight(size, tile);
		if (z < groundHeight) {
			return true;
		}
		return new CubeCatalogTile(shape, size, 0).isFilled(x, y, z - groundHeight);
	}

	static byte getDepth(byte size) {
		switch (size) {
		case Cube.CUBE_SIZE_TILE_SMALL:
			return DEPTH_SMALL;
		case Cube.CUBE_SIZE_TILE_MEDIUM:
			return DEPTH_MEDIUM;
		case Cube.CUBE_SIZE_TILE_LARGE:
			return DEPTH_LARGE;
		default:
			throw new IllegalArgumentException("There are no tiles of the size " + size + ".");
		}
	}

	static CubeTree getShape(byte size, int tile) {
		if (tile < 0 || tile >= TILE_NUMBERS) {
			throw new IllegalArgumentException("There is no tile " + tile + ".");
		}
		return SHAPES.computeIfAbsent(size * TILE_NUMBERS + tile, key -> createShape(size, tile));
	}

	/* At the lowest possible height of a tile there is no ground, only the shape. */
	private static CubeTree createShape(byte size, int tile) {
		byte depth = getDepth(size);
		checkTile(size, tile);
		byte lowestHeight = (byte) CubeTileAnalysis.getMinHeight(size, tile);
		Cube cube = CubeTileAnalysis.isAdapterTile(tile) ? new CubeAdapter(size, lowestHeight)
				: new CubeStandard(size, lowestHeight);
		cube.createTile(tile, depth, false);
		return new CubeTree(cube.getEndAddresses(), depth);
	}

	private static void checkTile(byte size, int tile) {
		boolean isKnownTile = CubeTileAnalysis.isAdapterTile(tile)
				? CubeTileAnalysis.hasAdapterTiles(size) && CubeTileAnalysis.getAdapterTiles().contains(tile)
				: CubeTileAnalysis.getStandardTiles(size).contains(tile);
		if (!isKnownTile) {
			throw new IllegalArgumentException("There is no tile " + tile + " of the size " + size + ".");
		}
	}
}
