package org.socialworld.tools.mct;

import java.util.concurrent.ThreadLocalRandom;

class RandomRasterIndexOrder {

	static RandomRasterIndexOrder instance;
	
	static int[] randomRasterIndices;
	
	private RandomRasterIndexOrder() {
		
		
		randomRasterIndices = new int[81];
		
		int[] tmp = new int[81];
		for (int i = 0; i < 81; i++) {
			tmp[i] = i;
			randomRasterIndices[i] = -1;
		}
		
		int randomNum;
		for (int i = 0; i < 81; i++) {
			randomNum = ThreadLocalRandom.current().nextInt(0, 81);
			if (randomNum < 0) randomNum = 0;
			if (randomNum > 80) randomNum = 80;
			
			while (randomRasterIndices[randomNum] >= 0) {
				randomNum++;
				if (randomNum == 81) randomNum = 0;
			}
			randomRasterIndices[randomNum] = tmp[i];
		}

	}
	
	static RandomRasterIndexOrder getInstance() {
		if (instance == null) {
			instance = new RandomRasterIndexOrder();
		}
		return instance;
		
	}
	
	int getIndexFromRandomArray(int indexArray) {
		return randomRasterIndices[indexArray];
	}
	
	int getRandomInt(int min, int max) {
		int randomInt = ThreadLocalRandom.current().nextInt(min, max+1);
		return randomInt;
	}
}
