package org.socialworld.datasource;

import org.socialworld.objects.Item;
import org.socialworld.objects.WriteAccessToSimulationObject;

public class LoadItem extends LoadSimulationObjects {

	private static LoadItem instance;

	/**
	 * The method gets back the only instance of the LoadItem.
	 * 
	 * @return singleton object of LoadHuman
	 */
	public static LoadItem getInstance() {
		if (instance == null) {
			instance = new LoadItem();
			
		}
		return instance;
	}

	@Override
	public Item getObject(int objectID) {
		
		Item createdItem = new Item();
		WriteAccessToSimulationObject item = new WriteAccessToSimulationObject(createdItem);
		

		initObject(item, objectID);	
		
		return createdItem;
	}

	protected void selectAllForID(int ObjectID){
		
	}

}
