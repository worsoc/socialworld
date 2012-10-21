/**
 * 
 */
package org.socialworld.datasource;

import org.apache.log4j.Logger;
import org.socialworld.objects.ISimulationObjectWrite;
import org.socialworld.objects.SimulationObject;

/**
 * Its the basic class for creating simulation objects.
 * for inherited classes of class SimulationObject
 *  there exists an inherited class of LoadSimulationObjects.
 *  
 * @author Mathias Sikos (tyloesand) 
 */
public abstract class LoadSimulationObjects implements ISimulationObjectWrite {
	
	protected static final Logger logger = Logger.getLogger(LoadSimulationObjects.class);


	public LoadSimulationObjects() {

	}

	public abstract SimulationObject getObject(long objectID) ;
	

}
