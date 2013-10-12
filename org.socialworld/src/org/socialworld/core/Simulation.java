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
public class Simulation implements IHumanWrite{
	// TODO (tyloesand) hier ist noch viel zu tun:
	// - ueberdenken, welche Listen
	// - ueberdenken Start EventMaster
	// - EventMaster und ObjectMaster als Singleton
	public static final int WITH_LOGGING = 1;
	public static final int WITH_ERROR_LOGGING = 1;
	private static final Logger logger = Logger.getLogger(Simulation.class);

	private static Simulation instance;
	
	private final EventMaster eventMaster;
	private final ObjectMaster objectMaster;
	private final ActionMaster actionMaster;
	
	private Simulation() {
		
		if (WITH_LOGGING == 1 ) logger.debug("create simulation object");
		
		this.eventMaster = new EventMaster();
		this.objectMaster = new ObjectMaster();
		this.actionMaster = ActionMaster.getInstance();

	}
	
	/**
	 * The method gets back the only instance of the Simulation.
	 * 
	 * @return singleton object of Simulation
	 */
	public static Simulation getInstance() {
		if (instance == null) {
			instance = new Simulation();
		}
		return instance;
		
	}
	
	
	public void startSimulation() {
		this.eventMaster.startEventMaster();
	}
	
	public void stopSimulation() {
		this.eventMaster.stopEventMaster();
	}

	public EventMaster getEventMaster() {
		return this.eventMaster;
	}

	public void nextTimeStep() {
		actionMaster.nextSecond();
	}

	public void nextAction() {
		actionMaster.executeAction();
	}

	// for test visualizes there is a public access to the object master
	public ObjectMaster getObjectMaster() {
		return this.objectMaster;
	}
	
	public SimulationObject createSimulationObject(
			SimulationObjectType simulationObjectType) {
		return this.objectMaster.createSimulationObject(simulationObjectType);
	}

	public SimulationObject getSimulationObject(int objectID) {
		return this.objectMaster.getSimulationObject(objectID);
	}
	
	public boolean hasNext(SimulationObjectType objectType) {
		return this.objectMaster.hasNext(objectType);
	}
	
	public SimulationObject next(SimulationObjectType objectType) {
		return this.objectMaster.next(objectType);
	}
	
}
