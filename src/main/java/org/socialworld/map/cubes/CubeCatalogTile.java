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

import java.util.ArrayList;
import java.util.List;

/**
 * One tile at one height. It shares the tree of its tile type with the same tile
 * at all other heights and only knows how high the ground reaches below the tree.
 */
public final class CubeCatalogTile {

	private final CubeTree shape;
	private final byte size;
	private final int groundCells;

	CubeCatalogTile(CubeTree shape, byte size, int groundHeight) {
		this.shape = shape;
		this.size = size;
		this.groundCells = groundHeight * shape.cellsPerEdge / size;
	}

	/**
	 * @param address the letters of a cube, the empty address is the whole tile
	 * @return what the cube with this address contains
	 */
	public CubeState getState(String address) {
		int edge = this.shape.cellsPerEdge;
		int x = 0;
		int y = 0;
		int z = 0;
		for (int position = 0; position < address.length(); position++) {
			int letter = CubeTileAnalysis.LETTERS.indexOf(address.charAt(position));
			if (letter < 0 || edge == 1) {
				throw new IllegalArgumentException("There is no cube with the address " + address + ".");
			}
			edge /= 3;
			x += (letter % 3) * edge;
			y += ((letter / 3) % 3) * edge;
			z += (letter / 9) * edge;
		}
		return getState(x, y, z, edge);
	}

	private CubeState getState(int x, int y, int z, int edge) {
		int content = 0;
		if (z < this.groundCells) {
			content |= CubeTree.HAS_FULL;
		}
		int minZ = Math.max(z, this.groundCells) - this.groundCells;
		int maxZ = z + edge - this.groundCells;
		if (minZ < maxZ) {
			content |= this.shape.findContent(x, x + edge, y, y + edge, minZ, maxZ);
		}

		if (content == CubeTree.HAS_FULL) {
			return CubeState.FULL;
		}
		return content == CubeTree.HAS_EMPTY ? CubeState.EMPTY : CubeState.PARTIAL;
	}

	/**
	 * @param x the position in meters to the east, from the west edge of the tile
	 * @param y the position in meters to the north, from the south edge of the tile
	 * @param z the height in meters, from the bottom of the cube of the tile
	 * @return whether the point is in a filled cube; outside the tile nothing is filled
	 */
	public boolean isFilled(double x, double y, double z) {
		int cellX = toCell(x);
		int cellY = toCell(y);
		int cellZ = toCell(z);
		int cells = this.shape.cellsPerEdge;
		if (cellX < 0 || cellX >= cells || cellY < 0 || cellY >= cells || cellZ < 0 || cellZ >= cells) {
			return false;
		}
		return getState(cellX, cellY, cellZ, 1) == CubeState.FULL;
	}

	private int toCell(double meters) {
		return (int) Math.floor(meters * this.shape.cellsPerEdge / this.size + Cube.epsilon);
	}

	/** @return the addresses of the biggest cubes that are full and together fill the tile */
	public List<String> getEndAddresses() {
		List<String> addresses = new ArrayList<String>();
		collectEndAddresses("", 0, 0, 0, this.shape.cellsPerEdge, addresses);
		return addresses;
	}

	private void collectEndAddresses(String address, int x, int y, int z, int edge, List<String> addresses) {
		CubeState state = getState(x, y, z, edge);
		if (state == CubeState.FULL) {
			addresses.add(address);
		} else if (state == CubeState.PARTIAL) {
			int childEdge = edge / 3;
			for (int child = 0; child < CubeTileAnalysis.LETTERS.length(); child++) {
				collectEndAddresses(address + CubeTileAnalysis.LETTERS.charAt(child), x + (child % 3) * childEdge,
						y + ((child / 3) % 3) * childEdge, z + (child / 9) * childEdge, childEdge, addresses);
			}
		}
	}
}
