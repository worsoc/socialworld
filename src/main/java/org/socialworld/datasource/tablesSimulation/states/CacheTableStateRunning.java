package org.socialworld.datasource.tablesSimulation.states;

public class CacheTableStateRunning extends TableStateRunning {
	private final int STATE_RUNNING_ARRAY_MAX_COUNT = 100000;

	private static CacheTableStateRunning instanceMain;

	public static CacheTableStateRunning getInstance() {
		if (instanceMain == null) {
				instanceMain = new CacheTableStateRunning(true);
		}
		return instanceMain;
	}

	private CacheTableStateRunning(boolean isSingleton) {
			mapId2Index = new int[STATE_RUNNING_ARRAY_MAX_COUNT];
			load();
	}

}
