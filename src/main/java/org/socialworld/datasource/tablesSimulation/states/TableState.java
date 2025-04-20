package org.socialworld.datasource.tablesSimulation.states;

import org.socialworld.GlobalSwitches;
import org.socialworld.attributes.ActualTime;
import org.socialworld.datasource.mariaDB.Table;

public  abstract class TableState extends Table {

	protected int[] mapId2Index;
	private boolean isFilledMapId2Index = false;
	
	// in all state tables there is the id column (referencing an simulation object)
	protected int id[];

	protected void load() {
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > " + getTableName() + ".load() Start " + ActualTime.asTime().toString());
		long lockingID = lockWithWait();
		
		select(SELECT_ALL_COLUMNS, "", " ORDER BY id"); 
		
		if (this.id.length > 0) {
			isFilledMapId2Index = true;
			for (int index = 0; index < this.id.length; index++) {
				mapId2Index[this.id[index]] = index;
			}	
		}
		else {
			isFilledMapId2Index = false;
		}
		
		unlock(lockingID);
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > " + getTableName()+ ".load() Ende " + ActualTime.asTime().toString());
	}

	public int getRowForID(int id) {
		if (isFilledMapId2Index && mapId2Index.length >  id) {
			return mapId2Index[id];
		}
		else {
			return -1;
		}
	}

}
