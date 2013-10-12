package org.socialworld.datasource;

import org.socialworld.objects.Magic;
import org.socialworld.objects.WriteAccessToSimulationObject;

public class LoadMagic extends LoadSimulationObjects {

	private static LoadMagic instance;

	/**
	 * The method gets back the only instance of the LoadItem.
	 * 
	 * @return singleton object of LoadHuman
	 */
	public static LoadMagic getInstance() {
		if (instance == null) {
			instance = new LoadMagic();
			
		}
		return instance;
	}

	@Override
	public Magic getObject(int objectID) {
		double gauss_value;
		
		Magic createdMagic = new Magic();
		WriteAccessToSimulationObject magic = new WriteAccessToSimulationObject(createdMagic);
		
		gauss_value = random.nextGaussian();

		initObject(magic, objectID, gauss_value);	
		
		return createdMagic;
	}

}
