package org.socialworld.datasource;


public class State2ActionTypePool {

	public static final int CAPACITY_S2AP_ARRAY = 100;

	private static State2ActionTypePool instance;
	
	private static int[] state2ActionTypes;
	
	private State2ActionTypePool() {
		state2ActionTypes = new int[CAPACITY_S2AP_ARRAY];
	}
	
	public static State2ActionTypePool getInstance() {
		if (instance == null) {
			instance = new State2ActionTypePool();
		}
		return instance;
	}

	public int getState2ActionType(int index) {
		return state2ActionTypes[index];
		
	}
	
}
