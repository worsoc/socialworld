package org.socialworld.tools.mct;

enum TileGridBorderAdapterType {

	noAdapter, from0To1, from1To0, flat;
	
	private static final int[] tileNumbersWithNorth01 = {4, 5, 6, 7, 16, 18};
	private static final int[] tileNumbersWithNorth10 = {8, 9, 10, 11, 17, 19};
	private static final int[] tileNumbersWithEast01 = {1, 3, 9, 11, 17, 18};
	private static final int[] tileNumbersWithEast10 = {4, 6, 12, 14, 16, 19}; 
	private static final int[] tileNumbersWithSouth01 = {1, 5, 9, 13, 16, 18};
	private static final int[] tileNumbersWithSouth10 = {2, 6, 10, 14, 17, 19};
	private static final int[] tileNumbersWithWest01 = {2, 3, 6, 7, 17, 18};
	private static final int[] tileNumbersWithWest10 = {8, 9, 12, 13, 16, 19}; 
	
	static TileGridBorderAdapterType getBorderAdapterTypeNorth(int globalTileNumber) {
		int tileNumber = TileType.subGlobalNumberOffset(globalTileNumber);
		for (int index = 0; index < 6; index++) {
			if (tileNumbersWithNorth01[index] == tileNumber) return from0To1;
			if (tileNumbersWithNorth10[index] == tileNumber) return from1To0;
		}
		return flat;
	}

	static TileGridBorderAdapterType getBorderAdapterTypeEast(int globalTileNumber) {
		int tileNumber = TileType.subGlobalNumberOffset(globalTileNumber);
		for (int index = 0; index < 6; index++) {
			if (tileNumbersWithEast01[index] == tileNumber) return from0To1;
			if (tileNumbersWithEast10[index] == tileNumber) return from1To0;
		}
		return flat;
	}

	static TileGridBorderAdapterType getBorderAdapterTypeSouth(int globalTileNumber) {
		int tileNumber = TileType.subGlobalNumberOffset(globalTileNumber);
		for (int index = 0; index < 6; index++) {
			if (tileNumbersWithSouth01[index] == tileNumber) return from0To1;
			if (tileNumbersWithSouth10[index] == tileNumber) return from1To0;
		}
		return flat;
	}

	static TileGridBorderAdapterType getBorderAdapterTypeWest(int globalTileNumber) {
		int tileNumber = TileType.subGlobalNumberOffset(globalTileNumber);
		for (int index = 0; index < 6; index++) {
			if (tileNumbersWithWest01[index] == tileNumber) return from0To1;
			if (tileNumbersWithWest10[index] == tileNumber) return from1To0;
		}
		return flat;
	}

}
