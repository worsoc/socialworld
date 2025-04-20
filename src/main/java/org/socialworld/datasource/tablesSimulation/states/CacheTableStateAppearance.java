package org.socialworld.datasource.tablesSimulation.states;

import org.socialworld.GlobalSwitches;
import org.socialworld.attributes.ActualTime;

public class CacheTableStateAppearance extends TableStateAppearance {

	private final int STATE_APPEARANCE_ARRAY_MAX_COUNT = 100000;

	private static CacheTableStateAppearance instanceMain;
	private int[] mapId2Index;

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

	private void load() {
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > CacheTableStateAppearance.load() Start " + ActualTime.asTime().toString());
		long lockingID = lockWithWait();
		
		select(SELECT_ALL_COLUMNS, "", " ORDER BY id"); 
		
		if (this.id.length > 0) {
			for (int index = 0; index < this.id.length; index++) {
				mapId2Index[this.id[index]] = index;
			}	
		}
		
		unlock(lockingID);
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > CacheTableStateAppearance.load() Ende " + ActualTime.asTime().toString());
	}

	public int getRowForID(int id) {
		if (mapId2Index.length >  id) {
			return mapId2Index[id];
		}
		else {
			return -1;
		}
	}
	
}
