package org.socialworld.tools.mct;

public class TileGridEasyPatterns {

	public static final int NORTH_AND_SOUTH = 1;
	public static final int EAST_AND_WEST   = 2;
	public static final int NORTH_AND_EAST  = 3;
	public static final int NORTH_AND_WEST  = 4;
	public static final int SOUTH_AND_EAST  = 5;
	public static final int SOUTH_AND_WEST  = 6;
	
	private int[] totalFreePattern = {
			-9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9
		};

	private int[] easyPatternNorthPlus4SouthPlus4 = {
		-9,  0, 5, 5, 5, 5,  0,  0, -9,
		-9, -9, 5, 5, 5, 5, -9, -9, -9,
		-9, -9, 5, 5, 5, 5, -9, -9, -9,
		-9, -9, 5, 5, 5, 5, -9, -9, -9,
		-9, -9, 5, 5, 5, 5, -9, -9, -9,
		-9, -9, 5, 5, 5, 5, -9, -9, -9,
		-9, -9, 5, 5, 5, 5, -9, -9, -9,
		-9, -9, 5, 5, 5, 5, -9, -9, -9,
		-9,  0, 5, 5, 5, 5,  0,  0, -9
	};
	
	private int[] easyPatternNorthMinus4SouthMinus4 = {
			-9,  0, 10, 10, 10, 10,  0,  0, -9,
			-9, -9, 10, 10, 10, 10, -9, -9, -9,
			-9, -9, 10, 10, 10, 10, -9, -9, -9,
			-9, -9, 10, 10, 10, 10, -9, -9, -9,
			-9, -9, 10, 10, 10, 10, -9, -9, -9,
			-9, -9, 10, 10, 10, 10, -9, -9, -9,
			-9, -9, 10, 10, 10, 10, -9, -9, -9,
			-9, -9, 10, 10, 10, 10, -9, -9, -9,
			-9,  0, 10, 10, 10, 10,  0,  0, -9
		};

	private int[] easyPatternWestPlus4EastPlus4 = {
			-9, -9, -9, -9, -9, -9, -9, -9, -9,
			 0, -9, -9, -9, -9, -9, -9, -9,  0,
			 3,  3,  3,  3,  3,  3,  3,  3,  3,
			 3,  3,  3,  3,  3,  3,  3,  3,  3,
			 3,  3,  3,  3,  3,  3,  3,  3,  3,
			 3,  3,  3,  3,  3,  3,  3,  3,  3,
			 0, -9, -9, -9, -9, -9, -9, -9,  0,
			 0, -9, -9, -9, -9, -9, -9, -9,  0,
			-9, -9, -9, -9, -9, -9, -9, -9, -9
		};

	private int[] easyPatternWestMinus4EastMinus4 = {
			-9, -9, -9, -9, -9, -9, -9, -9, -9,
			 0, -9, -9, -9, -9, -9, -9, -9,  0,
			12, 12, 12, 12, 12, 12, 12, 12, 12,
			12, 12, 12, 12, 12, 12, 12, 12, 12,
			12, 12, 12, 12, 12, 12, 12, 12, 12,
			12, 12, 12, 12, 12, 12, 12, 12, 12,
			 0, -9, -9, -9, -9, -9, -9, -9,  0,
			 0, -9, -9, -9, -9, -9, -9, -9,  0,
			-9, -9, -9, -9, -9, -9, -9, -9, -9
		};
	
	private int[] easyPatternNorthPlus4EastMinus4 = {
			-9,  0,  5,  5,  5,  5,  0,  0, -9,
			-9, -9,  4, 16, 16, 16, 13,  0,  0,
			-9, -9, -9,  4, 16, 16, 16, 12, 12,
			-9, -9, -9, -9,  4, 16, 16, 12, 12,
			-9, -9, -9, -9, -9,  4, 16, 12, 12,
			-9, -9, -9, -9, -9, -9,  4, 12, 12,
			-9, -9, -9, -9, -9, -9, -9, -9,  0,
			-9, -9, -9, -9, -9, -9, -9, -9,  0,
			-9, -9, -9, -9, -9, -9, -9, -9, -9
		};

	private int[] easyPatternSouthPlus4EastPlus4 = {
			-9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9,  0,
			-9, -9, -9, -9, -9, -9, -9,  1,  3,
			-9, -9, -9, -9, -9, -9,  1, 18,  3,
			-9, -9, -9, -9, -9,  1, 18, 18,  3,
			-9, -9, -9, -9,  1, 18, 18, 18,  3,
			-9, -9, -9,  1, 18, 18, 18,  7,  0,
			-9, -9,  1, 18, 18, 18,  7,  0,  0,
			-9,  0,  5,  5,  5,  5,  0,  0, -9,
	};

	private int[] easyPatternSouthMinus4WestPlus4 = {
			-9, -9, -9, -9, -9, -9, -9, -9, -9,
			 0, -9, -9, -9, -9, -9, -9, -9, -9,
			 3,  2, -9, -9, -9, -9, -9, -9, -9,
			 3, 17,  2, -9, -9, -9, -9, -9, -9,
			 3, 17, 17,  2, -9, -9, -9, -9, -9,
			 3, 17, 17, 17,  2, -9, -9, -9, -9,
			 0, 11, 17, 17, 17,  2, -9, -9, -9,
			 0,  0, 10, 10, 10, 10, -9, -9, -9,
			-9,  0, 10, 10, 10, 10,  0,  0, -9
	};

	private int[] easyPatternNorthMinus4WestMinus4 = {
			-9,  0, 10, 10, 10, 10,  0,  0, -9,
			 0, 14, 19, 19, 19,  8, -9, -9, -9,
			12, 19, 19, 19,  8, -9, -9, -9, -9,
			12, 19, 19,  8, -9, -9, -9, -9, -9,
			12, 19,  8, -9, -9, -9, -9, -9, -9,
			12,  8, -9, -9, -9, -9, -9, -9, -9,
			 0, -9, -9, -9, -9, -9, -9, -9, -9,
			 0, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9
	};

	static TileGridEasyPatterns instance;
	
	static TileGridEasyPatterns getInstance() {
		if (instance == null) {
			instance = new TileGridEasyPatterns();
		}
		return instance;
	}

	int[] getEasyPattern(int variante, int deltaLevel) {
		switch (variante) {
		 case NORTH_AND_SOUTH:
			 switch (deltaLevel) {
			  case 4: 
				  return easyPatternNorthPlus4SouthPlus4;
			  case -4: 
				  return easyPatternNorthMinus4SouthMinus4;
			 }
			 break;
		 case EAST_AND_WEST:
			 switch (deltaLevel) {
			  case 4: 
				  return easyPatternWestPlus4EastPlus4;
			  case -4: 
				  return easyPatternWestMinus4EastMinus4;
			 }
			 break;
		 case NORTH_AND_EAST:
			 switch (deltaLevel) {
			  case 4: 
				  return easyPatternNorthPlus4EastMinus4;
			  case -4: 
				  return easyPatternNorthPlus4EastMinus4;
			 }
			 break;
		 case NORTH_AND_WEST:
			 switch (deltaLevel) {
			  case 4: 
				  return easyPatternNorthMinus4WestMinus4;
			  case -4: 
				  return easyPatternNorthMinus4WestMinus4;
			 }
			 break;
		 case SOUTH_AND_EAST:
			 switch (deltaLevel) {
			  case 4: 
				  return easyPatternSouthPlus4EastPlus4;
			  case -4: 
				  return easyPatternSouthPlus4EastPlus4;
			 }
			 break;
		 case SOUTH_AND_WEST:
			 switch (deltaLevel) {
			  case 4: 
				  return easyPatternSouthMinus4WestPlus4;
			  case -4: 
				  return easyPatternSouthMinus4WestPlus4;
			 }
			 break;
		};
		
		return totalFreePattern;
	}

}
