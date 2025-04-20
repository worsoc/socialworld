package org.socialworld.datasource.tablesSimulation.states;

public class CacheTableStateFlying extends TableStateFlying {
	private final int STATE_FLYING_ARRAY_MAX_COUNT = 100000;

	private static CacheTableStateFlying instanceMain;

	public static CacheTableStateFlying getInstance() {
		if (instanceMain == null) {
				instanceMain = new CacheTableStateFlying(true);
		}
		return instanceMain;
	}

	private CacheTableStateFlying(boolean isSingleton) {
			mapId2Index = new int[STATE_FLYING_ARRAY_MAX_COUNT];
			load();
	}
}
