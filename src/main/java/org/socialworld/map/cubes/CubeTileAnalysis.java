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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Creates one tile and prepares the result for the CubeTileDialog: statistics
 * and geometry instead of a list of many addresses. Also knows which settings
 * are possible for a tile.
 */
final class CubeTileAnalysis {

	static final int MAX_DEPTH = 6;
	static final int TOP_VIEW_CELLS = 243;

	static final String LETTERS = "ABCDEFGHIJKLM#NOPQRSTUVWXYZ";
	private static final int FIRST_ADAPTER_TILE = 100;

	final int tile;
	final byte size;
	final byte height;
	final byte depth;

	final List<String> addresses;
	final int[] addressesByDepth;

	// position and edge of each cube of the address with the same index
	final double[] cubeX;
	final double[] cubeY;
	final double[] cubeZ;
	final double[] cubeEdge;

	// [column to the east][row to the north], the top of the highest cube
	final double[][] topHeights = new double[TOP_VIEW_CELLS][TOP_VIEW_CELLS];
	final double lowestTop;
	final double highestTop;

	final double meanHeight;
	final double exactMeanHeight;
	final double lowestSurface;
	final double highestSurface;

	final long milliseconds;
	final long memoryBytes;

	private final TileSurface surface;
	private final double groundHeight;

	static List<Integer> getStandardTiles(byte size) {
		List<Integer> tiles = new ArrayList<Integer>();
		int last = size == Cube.CUBE_SIZE_TILE_SMALL ? 15 : 19;
		for (int tile = 0; tile <= last; tile++) {
			tiles.add(tile);
		}
		return tiles;
	}

	static boolean hasAdapterTiles(byte size) {
		return size != Cube.CUBE_SIZE_TILE_LARGE;
	}

	static List<Integer> getAdapterTiles() {
		List<Integer> tiles = new ArrayList<Integer>();
		for (int hundreds : new int[] { 100, 300 }) {
			for (int tens = 0; tens < 80; tens += 10) {
				for (int units = 1; units <= 9; units++) {
					tiles.add(hundreds + tens + units);
				}
			}
		}
		return tiles;
	}

	static boolean isAdapterTile(int tile) {
		return tile >= FIRST_ADAPTER_TILE;
	}

	static String describeTile(int tile) {
		return isAdapterTile(tile) ? CubeAdapter.describeTile(tile) : CubeStandard.describeTile(tile);
	}

	static int getMinHeight(byte size, int tile) {
		return tile >= 16 && tile <= 19 ? 1 : 0;
	}

	/** The height change of 1 has to fit into the cube, on a small tile this leaves only 0. */
	static int getMaxHeight(byte size) {
		return size - 1;
	}

	static int getDefaultDepth(byte size) {
		return size == Cube.CUBE_SIZE_TILE_LARGE ? 6 : 4;
	}

	/** @return the edge of the cubes in a depth, in words, like "1/3 m" */
	static String describeEdge(byte size, int depth) {
		int powerOfThree = (int) Math.pow(3, depth);
		if (powerOfThree <= size) {
			return (size / powerOfThree) + " m";
		}
		return "1/" + (powerOfThree / size) + " m";
	}

	private static long usedMemory() {
		Runtime runtime = Runtime.getRuntime();
		for (int i = 0; i < 3; i++) {
			System.gc();
		}
		return runtime.totalMemory() - runtime.freeMemory();
	}

	CubeTileAnalysis(int tile, byte size, byte height, byte depth) {
		this.tile = tile;
		this.size = size;
		this.height = height;
		this.depth = depth;

		long memoryBefore = usedMemory();
		long start = System.currentTimeMillis();

		Cube cube = isAdapterTile(tile) ? new CubeAdapter(size, height) : new CubeStandard(size, height);
		cube.createTile(tile, depth, false);

		this.milliseconds = System.currentTimeMillis() - start;
		this.memoryBytes = Math.max(0, usedMemory() - memoryBefore);

		this.surface = new TileSurface(cube.getPlanesForTile(tile));
		this.groundHeight = cube.getGroundHeightForTile(tile);

		this.addresses = new ArrayList<String>(cube.getEndAddresses());
		Collections.sort(this.addresses);

		int count = this.addresses.size();
		// the ground is exact to 1 m, so its addresses can be longer than the chosen depth
		int longestAddress = depth;
		for (String address : this.addresses) {
			longestAddress = Math.max(longestAddress, address.length());
		}
		this.addressesByDepth = new int[longestAddress + 1];
		this.cubeX = new double[count];
		this.cubeY = new double[count];
		this.cubeZ = new double[count];
		this.cubeEdge = new double[count];

		double volume = 0;
		for (int i = 0; i < count; i++) {
			String address = this.addresses.get(i);
			this.addressesByDepth[address.length()]++;
			readAddress(address, i);
			volume += Math.pow(this.cubeEdge[i], 3);
			addToTopView(i);
		}
		this.meanHeight = volume / (size * size);

		double lowestFound = Double.POSITIVE_INFINITY;
		double highestFound = Double.NEGATIVE_INFINITY;
		for (double[] column : this.topHeights) {
			for (double top : column) {
				lowestFound = Math.min(lowestFound, top);
				highestFound = Math.max(highestFound, top);
			}
		}
		this.lowestTop = lowestFound;
		this.highestTop = highestFound;

		double sum = 0;
		lowestFound = Double.POSITIVE_INFINITY;
		highestFound = Double.NEGATIVE_INFINITY;
		int samples = 729;
		for (int column = 0; column < samples; column++) {
			for (int row = 0; row < samples; row++) {
				double surfaceHeight = surfaceHeightAt((column + 0.5) / samples * size, (row + 0.5) / samples * size);
				sum += surfaceHeight;
				lowestFound = Math.min(lowestFound, surfaceHeight);
				highestFound = Math.max(highestFound, surfaceHeight);
			}
		}
		this.exactMeanHeight = sum / (samples * samples);
		this.lowestSurface = lowestFound;
		this.highestSurface = highestFound;
	}

	private void readAddress(String address, int index) {
		double x = 0;
		double y = 0;
		double z = 0;
		double edge = this.size;
		for (int position = 0; position < address.length(); position++) {
			edge /= 3;
			int letter = LETTERS.indexOf(address.charAt(position));
			x += (letter % 3) * edge;
			y += ((letter / 3) % 3) * edge;
			z += (letter / 9) * edge;
		}
		this.cubeX[index] = x;
		this.cubeY[index] = y;
		this.cubeZ[index] = z;
		this.cubeEdge[index] = edge;
	}

	private void addToTopView(int index) {
		double cellsPerMeter = (double) TOP_VIEW_CELLS / this.size;
		double tolerance = 1e-6;
		double top = this.cubeZ[index] + this.cubeEdge[index];

		int firstColumn = (int) Math.floor(this.cubeX[index] * cellsPerMeter + tolerance);
		int lastColumn = (int) Math.ceil((this.cubeX[index] + this.cubeEdge[index]) * cellsPerMeter - tolerance);
		int firstRow = (int) Math.floor(this.cubeY[index] * cellsPerMeter + tolerance);
		int lastRow = (int) Math.ceil((this.cubeY[index] + this.cubeEdge[index]) * cellsPerMeter - tolerance);

		for (int column = firstColumn; column < Math.max(lastColumn, firstColumn + 1) && column < TOP_VIEW_CELLS; column++) {
			for (int row = firstRow; row < Math.max(lastRow, firstRow + 1) && row < TOP_VIEW_CELLS; row++) {
				this.topHeights[column][row] = Math.max(this.topHeights[column][row], top);
			}
		}
	}

	/** @return the height the tile should have here; below its lowest corner the ground begins */
	double surfaceHeightAt(double x, double y) {
		return Math.max(this.groundHeight, this.surface.heightAt(x, y));
	}

	/** @return the index of the cube at this position in a vertical cut, or -1 */
	int findCube(double x, double y, double z) {
		for (int i = 0; i < this.cubeX.length; i++) {
			double edge = this.cubeEdge[i];
			if (x >= this.cubeX[i] && x < this.cubeX[i] + edge && y >= this.cubeY[i] && y < this.cubeY[i] + edge
					&& z >= this.cubeZ[i] && z < this.cubeZ[i] + edge) {
				return i;
			}
		}
		return -1;
	}

	/** @return the indices of all cubes above each other at this position, the highest cube first */
	List<Integer> findCubesInColumn(double x, double y) {
		List<Integer> column = new ArrayList<Integer>();
		for (int i = 0; i < this.cubeX.length; i++) {
			double edge = this.cubeEdge[i];
			if (x >= this.cubeX[i] && x < this.cubeX[i] + edge && y >= this.cubeY[i] && y < this.cubeY[i] + edge) {
				column.add(i);
			}
		}
		column.sort((first, second) -> Double.compare(this.cubeZ[second], this.cubeZ[first]));
		return column;
	}

	List<String> getFirstAddresses(int count) {
		return this.addresses.subList(0, Math.min(count, this.addresses.size()));
	}

	@Override
	public String toString() {
		return "tile " + this.tile + ", " + Arrays.toString(this.addressesByDepth);
	}
}
