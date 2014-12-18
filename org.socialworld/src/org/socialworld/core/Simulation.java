/**
 * 
 */
package org.socialworld.core;

import org.apache.log4j.Logger;
import org.socialworld.objects.*;
import org.socialworld.propertyChange.ChangedProperty;

import org.socialworld.attributes.Position;
import org.socialworld.collections.ObjectByPositionSearch;



/**
 * The class Simulation holds all simulation objects and runs the simulation
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Simulation implements IHumanWrite{
	public static  int WITH_LOGGING = 1;
	public static  int WITH_ERROR_LOGGING = 1;
	private static final Logger logger = Logger.getLogger(Simulation.class);

	private static Simulation instance;
	
	private final EventMaster eventMaster;
	private final ObjectMaster objectMaster;
	private final ActionMaster actionMaster;
	private final ObjectByPositionSearch searchByPosition;
	
	private Simulation() {
		
		if (WITH_LOGGING == 1 ) logger.debug("create simulation object");
		
		this.eventMaster = EventMaster.getInstance();
		this.objectMaster = ObjectMaster.getInstance();
		this.actionMaster = ActionMaster.getInstance();
		this.searchByPosition = new ObjectByPositionSearch(1);
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
			SimulationObject_Type simulationObjectType) {
		return this.objectMaster.createSimulationObject(simulationObjectType);
	}

	public SimulationObject getSimulationObject(int objectID) {
		return this.objectMaster.getSimulationObject(objectID);
	}
	
	public boolean hasNext(SimulationObject_Type objectType) {
		return this.objectMaster.hasNext(objectType);
	}
	
	public SimulationObject next(SimulationObject_Type objectType) {
		return this.objectMaster.next(objectType);
	}
	
	public SimulationObject getFirstByPosition(Position position) {
		this.searchByPosition.findNearestObject(position.getX(), position.getY());
		return getNextByPosition();
	}
	
	public SimulationObject getNextByPosition() {
		int objectID;
		objectID = this.searchByPosition.getNextObjectID();
		return this.objectMaster.getSimulationObject(objectID);
	}
	
	public void propertyChanged(SimulationObject changedObject, ChangedProperty property) {
		switch (property){
			case position: positionChanged(changedObject);
		}
	}
	
	private void positionChanged(SimulationObject changedObject) {
		this.searchByPosition.changePosition(changedObject);
	}
}
