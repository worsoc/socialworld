/**
 * 
 */
package org.socialworld.datasource;

import org.socialworld.objects.*;
import org.socialworld.datasource.LoadSimulationObjects;
import org.socialworld.datasource.AttributeCalculatorMatrixPool;

/**
 * Because of being a singleton there exists
 *         only one instance of the class. 
 *         The instance is responsible for creating human objects
 *         Therefore the object data is loaded from a data source
 *         and put to a new created instance of class Human
 * @author Mathias Sikos (tyloesand) 
 */
public class LoadHuman extends LoadSimulationObjects {
	
	private static LoadHuman instance;

	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private LoadHuman() {
		logger.debug("create LoadHuman");

	}

	/**
	 * The method gets back the only instance of the LoadHuman.
	 * 
	 * @return singleton object of LoadHuman
	 */
	public static LoadHuman getInstance() {
		if (instance == null) {
			instance = new LoadHuman();
		}
		return instance;
	}
	
	/**
	 * The method creates an instance of class Human.
	 * 
	 * @param objectID
	 * @return  Human
	 */
	public Human getObject(long objectID) {
		
		Human createdHuman = new Human();
		if (objectID == 0) {
			createdHuman.setMatrix(	
					AttributeCalculatorMatrixPool.getInstance().getMatrix(mapObjectIDtoMatrixIndex(objectID)));
			createdHuman.setAttributes(
					AttributeArrayPool.getInstance().getArray(mapObjectIDtoAttributesIndex(objectID)));
		}
		return createdHuman;
	}

	private int mapObjectIDtoMatrixIndex(long objectID) {
		return (int) objectID;
	}

	private int mapObjectIDtoAttributesIndex(long objectID) {
		return (int) objectID;
	}

}
