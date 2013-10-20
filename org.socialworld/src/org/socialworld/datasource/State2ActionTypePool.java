package org.socialworld.datasource;


public class State2ActionTypePool {

	public static final int CAPACITY_S2AP_ARRAY = 100;

	private static State2ActionTypePool instance;
	
	private static int[] state2ActionTypesForPositiveIndex;
	private static int[] state2ActionTypesForNegativeIndex;
	
	private State2ActionTypePool() {
		state2ActionTypesForPositiveIndex = new int[CAPACITY_S2AP_ARRAY];
		state2ActionTypesForNegativeIndex = new int[CAPACITY_S2AP_ARRAY];
	}
	
	public static State2ActionTypePool getInstance() {
		if (instance == null) {
			instance = new State2ActionTypePool();
		}
		return instance;
	}

	public int getState2ActionType(int index) {
		if (index >= 0)
				return state2ActionTypesForPositiveIndex[index];
		else {
			index = index * -1;
			return state2ActionTypesForNegativeIndex[index];
		}	
		
	}
	
}
