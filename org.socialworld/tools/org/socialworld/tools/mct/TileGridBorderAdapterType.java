package org.socialworld.tools.mct;

enum TileGridBorderAdapterType {

	noAdapter, from0To1, from1To0, flat;
	
	private static final int[] tileNumberswithNorth01 = {4, 5, 6, 7, 16, 18};
	private static final int[] tileNumberswithNorth10 = {8, 9, 10, 11, 17, 19};
	private static final int[] tileNumberswithEast01 = {1, 3, 9, 11, 17, 18};
	private static final int[] tileNumberswithEast10 = {4, 6, 12, 14, 16, 19}; 
	private static final int[] tileNumberswithSouth01 = {1, 5, 9, 13, 16, 18};
	private static final int[] tileNumberswithSouth10 = {2, 6, 10, 14, 17, 19};
	private static final int[] tileNumberswithWest01 = {2, 3, 6, 7, 17, 18};
	private static final int[] tileNumberswithWest10 = {8, 9, 12, 13, 16, 19}; 
	
	static TileGridBorderAdapterType getBorderAdapterTypeNorth(int globalTileNumber) {
		int tileNumber = TileType.subGlobalNumberOffset(globalTileNumber);
		for (int index = 0; index < 6; index++) {
			if (tileNumberswithNorth01[index] == tileNumber) return from0To1;
			if (tileNumberswithNorth10[index] == tileNumber) return from1To0;
		}
		return flat;
	}

	static TileGridBorderAdapterType getBorderAdapterTypeEast(int globalTileNumber) {
		int tileNumber = TileType.subGlobalNumberOffset(globalTileNumber);
		for (int index = 0; index < 6; index++) {
			if (tileNumberswithEast01[index] == tileNumber) return from0To1;
			if (tileNumberswithEast10[index] == tileNumber) return from1To0;
		}
		return flat;
	}

	static TileGridBorderAdapterType getBorderAdapterTypeSouth(int globalTileNumber) {
		int tileNumber = TileType.subGlobalNumberOffset(globalTileNumber);
		for (int index = 0; index < 6; index++) {
			if (tileNumberswithSouth01[index] == tileNumber) return from0To1;
			if (tileNumberswithSouth10[index] == tileNumber) return from1To0;
		}
		return flat;
	}

	static TileGridBorderAdapterType getBorderAdapterTypeWest(int globalTileNumber) {
		int tileNumber = TileType.subGlobalNumberOffset(globalTileNumber);
		for (int index = 0; index < 6; index++) {
			if (tileNumberswithWest01[index] == tileNumber) return from0To1;
			if (tileNumberswithWest10[index] == tileNumber) return from1To0;
		}
		return flat;
	}

}
