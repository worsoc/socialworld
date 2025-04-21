package org.socialworld.datasource.tablesSimulation;

import org.socialworld.GlobalSwitches;
import org.socialworld.attributes.ActualTime;

public class CacheTablePosition extends TablePosition {

	private final int POSITION_ARRAY_MAX_COUNT = 100000;

	protected int[] mapPosId2Index;
	private boolean isFilledMapPosId2Index = false;
	
	private static CacheTablePosition instanceMain;

	public static CacheTablePosition getInstance() {
		if (instanceMain == null) {
				instanceMain = new CacheTablePosition(true);
		}
		return instanceMain;
	}

	private CacheTablePosition(boolean isSingleton) {
		mapPosId2Index = new int[POSITION_ARRAY_MAX_COUNT];
		load();
	}

	protected void load() {
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > " + getTableName() + ".load() Start " + ActualTime.asTime().toString());
		long lockingID = lockWithWait();
		
		select(SELECT_ALL_COLUMNS, "", " ORDER BY pos_id"); 
		
		if (this.pos_id.length > 0) {
			isFilledMapPosId2Index = true;
			for (int index = 0; index < this.pos_id.length; index++) {
				mapPosId2Index[this.pos_id[index]] = index;
			}	
		}
		else {
			isFilledMapPosId2Index = false;
		}
		
		unlock(lockingID);
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > " + getTableName()+ ".load() Ende " + ActualTime.asTime().toString());
	}

	public int getRowForPosID(int pos_id) {
		if (isFilledMapPosId2Index && mapPosId2Index.length >  pos_id) {
			return mapPosId2Index[pos_id];
		}
		else {
			return -1;
		}
	}


}
