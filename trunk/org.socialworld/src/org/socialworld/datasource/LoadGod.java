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
		double gauss_value;
		
		God createdGod = new God();
		WriteAccessToSimulationObject god = new WriteAccessToSimulationObject(createdGod);
		
		gauss_value = random.nextGaussian();

		initObject(god, objectID, gauss_value);	
		
		return createdGod;
	}

}
