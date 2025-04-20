package org.socialworld.datasource.tablesSimulation.states;


public class CacheTableStateAppearance extends TableStateAppearance {

	private final int STATE_APPEARANCE_ARRAY_MAX_COUNT = 100000;

	private static CacheTableStateAppearance instanceMain;

	public static CacheTableStateAppearance getInstance() {
		if (instanceMain == null) {
				instanceMain = new CacheTableStateAppearance(true);
		}
		return instanceMain;
	}

	private CacheTableStateAppearance(boolean isSingleton) {
			mapId2Index = new int[STATE_APPEARANCE_ARRAY_MAX_COUNT];
			load();
	}

	
}
