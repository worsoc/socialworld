package org.socialworld.datasource;

import org.socialworld.objects.God;
import org.socialworld.objects.WriteAccessToSimulationObject;

public class LoadGod extends LoadSimulationObjects {

	private static LoadGod instance;

	/**
	 * The method gets back the only instance of the LoadItem.
	 * 
	 * @return singleton object of LoadHuman
	 */
	public static LoadGod getInstance() {
		if (instance == null) {
			instance = new LoadGod();
			
		}
		return instance;
	}

	@Override
	public God getObject(int objectID) {
		
		God createdGod = new God();
		WriteAccessToSimulationObject god = new WriteAccessToSimulationObject(createdGod);
		

		initObject(god, objectID);	
		
		return createdGod;
	}

}
