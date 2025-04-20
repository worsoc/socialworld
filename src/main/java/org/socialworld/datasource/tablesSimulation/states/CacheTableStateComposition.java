package org.socialworld.datasource.tablesSimulation.states;

public class CacheTableStateComposition extends TableStateComposition {
	private final int STATE_COMPOSITION_ARRAY_MAX_COUNT = 100000;

	private static CacheTableStateComposition instanceMain;

	public static CacheTableStateComposition getInstance() {
		if (instanceMain == null) {
				instanceMain = new CacheTableStateComposition(true);
		}
		return instanceMain;
	}

	private CacheTableStateComposition(boolean isSingleton) {
			mapId2Index = new int[STATE_COMPOSITION_ARRAY_MAX_COUNT];
			load();
	}

	
}
