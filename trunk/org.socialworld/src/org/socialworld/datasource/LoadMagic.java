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
		
		Magic createdMagic = new Magic();
		WriteAccessToSimulationObject magic = new WriteAccessToSimulationObject(createdMagic);
		

		initObject(magic, objectID);	
		
		return createdMagic;
	}

	protected void selectAllForID(int ObjectID){
		
	}

}
