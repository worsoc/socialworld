/**
 * 
 */
package org.socialworld.datasource;

import org.apache.log4j.Logger;
import org.socialworld.objects.*;

/**
 * Its the abstract class for creating simulation objects.
 * for inherited classes of class SimulationObject
 *  there exists an inherited class of LoadSimulationObjects.
 *  
 * @author Mathias Sikos (tyloesand) 
 */
public abstract class LoadSimulationObjects {
	
	protected static final Logger logger = Logger.getLogger(LoadSimulationObjects.class);


	public LoadSimulationObjects() {

	}

	public abstract SimulationObject getObject(long objectID);
}
