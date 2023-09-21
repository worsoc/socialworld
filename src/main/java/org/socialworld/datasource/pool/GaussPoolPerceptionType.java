package org.socialworld.datasource.pool;

import org.socialworld.core.EventType;

public class GaussPoolPerceptionType {

	public static final int CAPACITY_GPPT_ARRAY = 100;

	private static GaussPoolPerceptionType instance;

	private static int[][] perceptionTypesForPositiveIndex;
	private static int[][] perceptionTypesForNegativeIndex;

	private GaussPoolPerceptionType() {
		perceptionTypesForPositiveIndex = new int[CAPACITY_GPPT_ARRAY][ EventType.MAX_EVENT_TYPE];
		perceptionTypesForNegativeIndex = new int[CAPACITY_GPPT_ARRAY][ EventType.MAX_EVENT_TYPE];
		
		// initial values
		for (int i = 0; i < CAPACITY_GPPT_ARRAY; i++) 
			for (int j = 0; j < EventType.MAX_EVENT_TYPE; j++) {				
				perceptionTypesForPositiveIndex[i][j] = i;
				perceptionTypesForNegativeIndex[i][j] = i;
			}
	}

	public static GaussPoolPerceptionType getInstance() {
		if (instance == null) {
			instance = new GaussPoolPerceptionType();
		}
		return instance;
	}

	public int[] getPerceptionTypes(int indexByGauss) {
		int types[];
	
		if (indexByGauss >= 0)
				types = perceptionTypesForPositiveIndex[indexByGauss];
		else {
			indexByGauss = indexByGauss * -1;
			types = perceptionTypesForNegativeIndex[indexByGauss];
		}	
		
		return types;
	}
	

}
