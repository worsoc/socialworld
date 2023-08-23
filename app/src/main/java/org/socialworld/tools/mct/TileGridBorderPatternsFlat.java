package org.socialworld.tools.mct;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileGridBorderPatternsFlat {

	
	private static final List<int[]> corner_0_Patterns = new ArrayList<int[]>(13000);
	private static List<Integer> corner_0_PatternsIndices = new ArrayList<Integer>();
	
	private RandomRasterIndexOrder random = RandomRasterIndexOrder.getInstance();
	
	private int firstIndexCorner0North_0_0;
	
	private int firstIndexCorner0South_0_0;

	private int firstIndexCorner0West_0_0;
	
	private int firstIndexCorner0East_0_0;
	
	
	static TileGridBorderPatternsFlat instance;
	
	static TileGridBorderPatternsFlat getInstance() {
		if (instance == null) {
			instance = new TileGridBorderPatternsFlat();
		}
		return instance;
	}
	
	private TileGridBorderPatternsFlat() {
		
		corner_0_PatternsIndices = new ArrayList<Integer>();

		for (int index = 0; index < 13000; index++) {
			corner_0_Patterns.add(new int[0]);
		}
		
		/*********** north 0 -> 0 ***********/
		
		firstIndexCorner0North_0_0 = 0;	
		
		corner_0_Patterns.set(0, new int[] {0,0,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(0);
		corner_0_Patterns.set(2, new int[] {0,1,3,2,0,0,0,0,0});corner_0_PatternsIndices.add(2);
		corner_0_Patterns.set(3, new int[] {0,0,1,3,2,0,0,0,0});corner_0_PatternsIndices.add(3);
		corner_0_Patterns.set(4, new int[] {0,0,0,1,3,2,0,0,0});corner_0_PatternsIndices.add(4);
		corner_0_Patterns.set(5, new int[] {0,0,0,0,1,3,2,0,0});corner_0_PatternsIndices.add(5);
		corner_0_Patterns.set(6, new int[] {0,0,0,0,0,1,3,2,0});corner_0_PatternsIndices.add(6);
		corner_0_Patterns.set(12, new int[] {0,1,3,3,3,3,3,2,0});corner_0_PatternsIndices.add(12);
		corner_0_Patterns.set(13, new int[] {0,0,1,3,3,3,2,0,0});corner_0_PatternsIndices.add(13);
		corner_0_Patterns.set(22, new int[] {0,1,3,2,0,1,3,2,0});corner_0_PatternsIndices.add(22);
		corner_0_Patterns.set(23, new int[] {0,0,1,3,2,1,3,2,0});corner_0_PatternsIndices.add(23);
		corner_0_Patterns.set(24, new int[] {0,1,3,2,1,3,2,0,0});corner_0_PatternsIndices.add(24);
		

		/*********** south 0 -> 0 ***********/
		
		firstIndexCorner0South_0_0 = corner_0_PatternsIndices.size();

		corner_0_Patterns.set(3000, new int[] {0,0,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(3000);
		corner_0_Patterns.set(3002, new int[] {0,4,12,8,0,0,0,0,0});corner_0_PatternsIndices.add(3002);
		corner_0_Patterns.set(3003, new int[] {0,0,4,12,8,0,0,0,0});corner_0_PatternsIndices.add(3003);
		corner_0_Patterns.set(3004, new int[] {0,0,0,4,12,8,0,0,0});corner_0_PatternsIndices.add(3004);
		corner_0_Patterns.set(3005, new int[] {0,0,0,0,4,12,8,0,0});corner_0_PatternsIndices.add(3005);
		corner_0_Patterns.set(3006, new int[] {0,0,0,0,0,4,12,8,0});corner_0_PatternsIndices.add(3006);
		corner_0_Patterns.set(3012, new int[] {0,4,12,12,12,12,12,8,0});corner_0_PatternsIndices.add(3012);
		corner_0_Patterns.set(3013, new int[] {0,0,4,12,12,12,8,0,0});corner_0_PatternsIndices.add(3013);
		corner_0_Patterns.set(3022, new int[] {0,4,12,8,0,4,12,8,0});corner_0_PatternsIndices.add(3022);
		corner_0_Patterns.set(3023, new int[] {0,0,4,12,8,4,12,8,0});corner_0_PatternsIndices.add(3023);
		corner_0_Patterns.set(3024, new int[] {0,4,12,8,4,12,8,0,0});corner_0_PatternsIndices.add(3024);


		/*********** west 0 -> 0 ***********/
		
		firstIndexCorner0West_0_0 = corner_0_PatternsIndices.size();
		
		corner_0_Patterns.set(6000, new int[] {0,0,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(6000);
		corner_0_Patterns.set(6002, new int[] {0,1,5,4,0,0,0,0,0});corner_0_PatternsIndices.add(6002);
		corner_0_Patterns.set(6003, new int[] {0,0,1,5,4,0,0,0,0});corner_0_PatternsIndices.add(6003);
		corner_0_Patterns.set(6004, new int[] {0,0,0,1,5,4,0,0,0});corner_0_PatternsIndices.add(6004);
		corner_0_Patterns.set(6005, new int[] {0,0,0,0,1,5,4,0,0});corner_0_PatternsIndices.add(6005);
		corner_0_Patterns.set(6006, new int[] {0,0,0,0,0,1,5,4,0});corner_0_PatternsIndices.add(6006);
		corner_0_Patterns.set(6012, new int[] {0,1,5,5,5,5,5,4,0});corner_0_PatternsIndices.add(6012);
		corner_0_Patterns.set(6013, new int[] {0,0,1,5,5,5,4,0,0});corner_0_PatternsIndices.add(6013);
		corner_0_Patterns.set(6022, new int[] {0,1,5,4,0,1,5,4,0});corner_0_PatternsIndices.add(6022);
		corner_0_Patterns.set(6023, new int[] {0,0,1,5,4,1,5,4,0});corner_0_PatternsIndices.add(6023);
		corner_0_Patterns.set(6024, new int[] {0,1,5,4,1,5,4,0,0});corner_0_PatternsIndices.add(6024);
	
		
		/*********** east 0 -> 0 ***********/
		
		firstIndexCorner0East_0_0 = corner_0_PatternsIndices.size();
		
		corner_0_Patterns.set(9000, new int[] {0,0,0,0,0,0,0,0,0});corner_0_PatternsIndices.add(9000);
		corner_0_Patterns.set(9002, new int[] {0,2,10,8,0,0,0,0,0});corner_0_PatternsIndices.add(9002);
		corner_0_Patterns.set(9003, new int[] {0,0,2,10,8,0,0,0,0});corner_0_PatternsIndices.add(9003);
		corner_0_Patterns.set(9004, new int[] {0,0,0,2,10,8,0,0,0});corner_0_PatternsIndices.add(9004);
		corner_0_Patterns.set(9005, new int[] {0,0,0,0,2,10,8,0,0});corner_0_PatternsIndices.add(9005);
		corner_0_Patterns.set(9006, new int[] {0,0,0,0,0,2,10,8,0});corner_0_PatternsIndices.add(9006);
		corner_0_Patterns.set(9012, new int[] {0,2,10,10,10,10,10,8,0});corner_0_PatternsIndices.add(9012);
		corner_0_Patterns.set(9013, new int[] {0,0,2,10,10,10,8,0,0});corner_0_PatternsIndices.add(9013);
		corner_0_Patterns.set(9022, new int[] {0,2,10,8,0,2,10,8,0});corner_0_PatternsIndices.add(9022);
		corner_0_Patterns.set(9023, new int[] {0,0,2,10,8,2,10,8,0});corner_0_PatternsIndices.add(9023);
		corner_0_Patterns.set(9024, new int[] {0,2,10,8,2,10,8,0,0});corner_0_PatternsIndices.add(9024);
		
	}
	
	int[] getBorderPattern(int north_1__south_2__west_3__east_4) {
		switch (north_1__south_2__west_3__east_4) {
		case 1: 
			return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0North_0_0, firstIndexCorner0South_0_0)));
		case 2: 
			return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0South_0_0, firstIndexCorner0West_0_0)));
		case 3: 
			return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0West_0_0, firstIndexCorner0East_0_0)));
		case 4: 
			return corner_0_Patterns.get(corner_0_PatternsIndices.get(getRandomIntBetween(firstIndexCorner0East_0_0, corner_0_PatternsIndices.size())));
		}
		return corner_0_Patterns.get(0);
	}
	
	private int getRandomIntBetween(int a, int b) {
		return this.random.getRandomIntBetween(a, b);
	}

}
