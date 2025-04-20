package org.socialworld.datasource.tablesSimulation.states;

public class CacheTableStateSeer extends TableStateSeer {
	private final int STATE_SEER_ARRAY_MAX_COUNT = 100000;

	private static CacheTableStateSeer instanceMain;

	public static CacheTableStateSeer getInstance() {
		if (instanceMain == null) {
				instanceMain = new CacheTableStateSeer(true);
		}
		return instanceMain;
	}

	private CacheTableStateSeer(boolean isSingleton) {
			mapId2Index = new int[STATE_SEER_ARRAY_MAX_COUNT];
			load();
	}

}
