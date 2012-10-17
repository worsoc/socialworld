/**
 * 
 */
package org.socialworld.core;

import org.apache.log4j.Logger;
import org.socialworld.objects.*;

/**
 * The class Simulation holds all simulation objects and runs the simulation
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Simulation {
	// TODO (tyloesand) hier ist noch viel zu tun:
	// - ueberdenken, welche Listen
	// - ueberdenken Start EventMaster
	
	private static final Logger logger = Logger.getLogger(Simulation.class);

	private final EventMaster eventMaster;
	private final ObjectMaster objectMaster;

	public Simulation() {
		logger.debug("create simulation object");
		this.eventMaster = new EventMaster();
		this.objectMaster = new ObjectMaster();

		
		// for testing the method is called here
		startSimulation();
		
		// for testing the method is called here
		simulateSocialWorld();
		
		// for testing the method is called here
		stopSimulation();
	}
	
/** This method visualizes the actual state of developing
 * Therefore it is implemented as private procedure 
 * and is called by the class constructor
 */
	private void simulateSocialWorld() {
		// objectID = 0 because there aren't any objects
		// all objects must be created for testing the simulation
		// until now there is no data source for simulation objects
		this.objectMaster.createSimulationObject(SimulationObjectType.human, 0);
		this.objectMaster.createSimulationObject(SimulationObjectType.human, 0);
		this.objectMaster.createSimulationObject(SimulationObjectType.human, 0);
		
	}
	
	public void startSimulation() {
		this.eventMaster.start();
	}
	
	public void stopSimulation() {
		this.eventMaster.stopEventMaster();
	}

	public EventMaster getEventMaster() {
		return this.eventMaster;
	}

	// for test visualizes there is a public access to the object master
	public ObjectMaster getObjectMaster() {
		return this.objectMaster;
	}
	
	public SimulationObject createSimulationObject(
			SimulationObjectType simulationObjectType, long objectID) {
		SimulationObject object;
		object = this.objectMaster.createSimulationObject(simulationObjectType, objectID);
		return object;
	}

	public boolean hasNext(SimulationObjectType objectType) {
		return this.objectMaster.hasNext(objectType);
	}
	
	public SimulationObject next(SimulationObjectType objectType) {
		return this.objectMaster.next(objectType);
	}
	
}
