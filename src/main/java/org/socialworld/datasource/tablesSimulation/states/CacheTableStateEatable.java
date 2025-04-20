package org.socialworld.datasource.tablesSimulation.states;

public class CacheTableStateEatable extends TableStateEatable {

	private final int STATE_EATABLE_ARRAY_MAX_COUNT = 100000;

	private static CacheTableStateEatable instanceMain;

	public static CacheTableStateEatable getInstance() {
		if (instanceMain == null) {
				instanceMain = new CacheTableStateEatable(true);
		}
		return instanceMain;
	}

	private CacheTableStateEatable(boolean isSingleton) {
			mapId2Index = new int[STATE_EATABLE_ARRAY_MAX_COUNT];
			load();
	}

	
}
