/*
* Social World
* Copyright (C) 2014  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.core;

import org.socialworld.objects.*;
import org.socialworld.propertyChange.ChangedProperty;

import org.socialworld.attributes.Position;
import org.socialworld.calculation.application.Scheduler;
import org.socialworld.collections.ObjectByPositionSearch;



/**
 * The class Simulation holds all simulation objects and runs the simulation
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Simulation {
	
	
	public static  int WITH_LOGGING = 1;
	public static  int WITH_ERROR_LOGGING = 1;

	private static Simulation instance;
	
	private final ObjectMaster objectMaster;

	// threads
	private final EventMaster eventMaster;
	private final ActionMaster actionMaster;
	private final RefreshMaster refreshMaster;

	private final ObjectByPositionSearch searchByPosition;
	
	private Simulation() {
		
		
		this.objectMaster = ObjectMaster.getInstance();

		this.eventMaster = EventMaster.getInstance();
		this.actionMaster = ActionMaster.getInstance();
		this.refreshMaster = RefreshMaster.getInstance(this.objectMaster);
		
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
	
		
		Scheduler myCalcScheduler;
		myCalcScheduler = Scheduler.getInstance();
		myCalcScheduler.startThreads();
		
		this.objectMaster.loadSimulationObjects();

		this.eventMaster.startThread();
		this.actionMaster.startThread();
		this.refreshMaster.startThread();
		
		
		Human myHuman;
		for (int i = 0;i < 20; i++ ) {
			myHuman = (Human) this.objectMaster.createSimulationObject(SimulationObject_Type.human);
		}
		
	}
	
	public void stopSimulation() {
		this.eventMaster.stopThread();
		this.actionMaster.stopThread();
		this.refreshMaster.stopThread();
	}

	public EventMaster getEventMaster() {
		return this.eventMaster;
	}

	public void nextTimeStep() {
		actionMaster.nextSecond();
	}


	// for test visualizes there is a public access to the object master
	public ObjectMaster getObjectMaster() {
		return this.objectMaster;
	}
	
	public SimulationObject createSimulationObject(
			SimulationObject_Type simulationObjectType) {
		return this.objectMaster.createSimulationObject(simulationObjectType);
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
