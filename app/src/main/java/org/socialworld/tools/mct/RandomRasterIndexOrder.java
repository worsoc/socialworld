package org.socialworld.tools.mct;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class RandomRasterIndexOrder {

	static RandomRasterIndexOrder instance;
	
	static List<Integer> randomIntsFromInputString;
	int randomIntsFromInputStringIndex;
	
	static int[] randomRasterIndices;
	
	private RandomRasterIndexOrder() {


	}
	
	static RandomRasterIndexOrder getInstance() {
		if (instance == null) {
			instance = new RandomRasterIndexOrder();
		}
		return instance;
		
	}
	
	void initRandomRasterIndexOrder() {
		randomRasterIndices = new int[81];
		
		int[] tmp = new int[81];
		for (int i = 0; i < 81; i++) {
			tmp[i] = i;
			randomRasterIndices[i] = -1;
		}
		
		int randomNum;
		for (int i = 0; i < 81; i++) {
			randomNum = getRandomIntBetween(0, 81);
			if (randomNum < 0) randomNum = 0;
			if (randomNum > 80) randomNum = 80;
			
			while (randomRasterIndices[randomNum] >= 0) {
				randomNum++;
				if (randomNum == 81) randomNum = 0;
			}
			randomRasterIndices[randomNum] = tmp[i];
		}

	}
	int getIndexFromRandomArray(int indexArray) {
		return randomRasterIndices[indexArray];
	}
	
	int getRandomIntBetween(int a, int b) {
		int random;
		int range = b - a;
		int value;
		random = this.getNextRandomIntFromInputString();
		value = random % range + a;
		return value;
	}

	void initRandomIntsFromInputString(String input) {
		
			randomIntsFromInputString = new ArrayList<Integer>();

			int length = input.length();
			char[] signs = input.toCharArray();

			int randomValue;
			for (int index = 0; index < length; index++) {
				randomValue = (int) signs[index];
				randomIntsFromInputString.add(randomValue);
			}
			
			randomIntsFromInputStringIndex = 0;
	}
	
	private int getNextRandomIntFromInputString() {
		
		if (randomIntsFromInputString.size() == 0) return 0;
		
		int value;
		if (randomIntsFromInputStringIndex == randomIntsFromInputString.size()) {
			randomIntsFromInputStringIndex = 0;
		}
		
		value = randomIntsFromInputString.get(randomIntsFromInputStringIndex);
		randomIntsFromInputStringIndex++;
		
		return value;
	}
	

}
