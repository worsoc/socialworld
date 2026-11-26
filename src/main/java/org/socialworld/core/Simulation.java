/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.core;

import org.socialworld.GlobalSwitches;
import org.socialworld.objects.*;
import org.socialworld.propertyChange.ChangedProperty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.socialworld.SocialWorld;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Calculation;
import org.socialworld.calculation.application.AttributeCalculator;
import org.socialworld.calculation.application.KnowledgeCalculator;
import org.socialworld.calculation.application.Scheduler;
import org.socialworld.collections.ObjectByPositionSearch;

import org.socialworld.datasource.pool.EventInfluenceDescriptionPool;
import org.socialworld.datasource.pool.EventReactionDescriptionPool;
import org.socialworld.datasource.pool.State2ActionDescriptionPool;

/**
 * The class Simulation holds all simulation objects and runs the simulation
 * 
 * @author Mathias Sikos (MatWorsoc)
 * 
 */
public class Simulation extends SocialWorldThread {
	
	
	public static  int WITH_LOGGING = 1;
	public static  int WITH_ERROR_LOGGING = 1;

	private static Simulation instance;
	
	private final ObjectMaster objectMaster;

	// threads
	private final EventMaster eventMaster;
	private final ActionMaster actionMaster;
	private final RefreshMaster refreshMaster;
	private final PercipienceMaster percipienceMaster;

	private final ObjectByPositionSearch searchByPosition;
	
	private boolean run = false;
	private int sleepTime = 500;
	
    private TickObjectCooldown objectCooldown = new TickObjectCooldown(ObjectMaster.LIVING_SIM_OBJECTS_MAX_NUMBER);


	private static AccessTokenCore tokenCore = AccessTokenCore.getValid();

	// the creation of the objects at the start (2026-11-25): only until it is over, then null
	private StartCreation startCreation;

	private Simulation() {
		
		
		this.objectMaster = ObjectMaster.getInstance();

		this.eventMaster = EventMaster.getInstance();
		this.actionMaster = ActionMaster.getInstance();
		this.refreshMaster = RefreshMaster.getInstance(this.objectMaster);
		this.percipienceMaster = PercipienceMaster.getInstance(this.objectMaster);
		
		this.searchByPosition = new ObjectByPositionSearch(1);

		AllWords.init();
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
	
	
	public void run() {
		while (isRunning()) {
			
			
			if (run) 
				nextTimeStep();
			else
				waitForStartTime();
			
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}	
	
	/**
	 * @return false if the simulation did not start: swstate_composition is filled, and loading is not ready yet
	 */
	public boolean startSimulation() {

		this.startCreation = new StartCreation(this.objectMaster, this.searchByPosition);
		// body build concept 9.1 (2026-11-25): the composition is the first state the simulation saves itself.
		// Filled means loading, which is not promising yet. Since 2026-11-26 the start empties the table itself
		// instead of refusing, so every run derives the composition anew.
		int clearedRows = this.startCreation.clearCompositionTable();
		if (clearedRows > 0) System.out.println("swstate_composition emptied at the start (" + clearedRows
				+ " rows of the last run); the composition is derived anew.");
		// still refused if the cache of the states had been read before the table was emptied
		int compositionRows = this.startCreation.rowsInCompositionTable();
		if (compositionRows > 0) {
			System.out.println("Start refused: the cache of swstate_composition still holds " + compositionRows
					+ " rows (read before the table was emptied). Start again.");
			this.startCreation = null;
			return false;
		}

		Scheduler myCalcScheduler;
		myCalcScheduler = Scheduler.getInstance();
		myCalcScheduler.startThreads();
		
		// TODO das muss irgendwann wieder rein
		//this.objectMaster.loadSimulationObjects();

	
		this.startThread();

		// the humans, their start equipment, the apples and their composition (StartCreation, 2026-11-25)
		this.startCreation.createObjects();
		this.startCreation = null;

		EventInfluenceDescriptionPool.getInstance();
		// load the state-to-action rules (swpool_s2a) here as well, not only when the first human acts
		State2ActionDescriptionPool.getInstance();
		// and the reaction rules (swpool_erd), not only at the first event that asks for a reaction
		EventReactionDescriptionPool.getInstance();
		return true;
	}
	
	public void stopSimulation() {
		this.eventMaster.stopThread();
		this.actionMaster.stopThread();
		this.refreshMaster.stopThread();
		this.percipienceMaster.stopThread();
	}

	public EventMaster getEventMaster() {
		return this.eventMaster;
	}

	private void nextTimeStep() {
		Time actualTime = ActualTime.asTime();
		System.out.println("Zeit: " + actualTime.toString());
		TickRandomCache.getInstance().nextTick();
		objectCooldown.nextTick();
		AttributeCalculator.getInstance().printInfluencedQueueCounts();
		//KnowledgeCalculator.getInstance().printKnowledgeQueueCounts();
		//EventMaster.getInstance().printAndResetTickStatistics();
		if (GlobalSwitches.OUTPUT_MEMORY_TELEMETRY)		printMemoryTelemetry();
		//if (GlobalSwitches.OUTPUT_MEMORY_TELEMETRY)  Calculation.getInstance().printStatistics();
		actionMaster.nextSecond(actualTime);
		// at the end, so that the processes changing velocities have run (movement concept G5)
		Scheduler.getInstance().sendPositionSteps();
	}

	private static void printMemoryTelemetry() {
	    Runtime runtime = Runtime.getRuntime();
	    // Berechnet den aktuell belegten Speicher
	    long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
	    
	    System.out.println("--- Telemetrie ---");
	    System.out.println("Genutzter Heap-Speicher: " + (memoryUsed / 1024 / 1024) + " MB");
	}

	private void waitForStartTime() {
		Time time = ActualTime.asTime();
		byte secondOfTheActualMinute = time.getSecond();
		
		if (secondOfTheActualMinute == 0) {
			sleepTime = 1000;
			run = true;
			this.eventMaster.startThread();
			this.actionMaster.startThread();
			this.refreshMaster.startThread();
			this.percipienceMaster.startThread();
		}
		else if (secondOfTheActualMinute < 50)
			sleepTime = 500;
		else if (secondOfTheActualMinute < 55)
			sleepTime = 100;
		else if (secondOfTheActualMinute < 59)
			sleepTime = 1;
		
		//System.out.println("Sekunde: " + secondOfTheActualMinute);
	}
	
	// for test visualizes there is a public access to the object master
	public ObjectMaster getObjectMaster() {
		return this.objectMaster;
	}
	
	public ObjectByPositionSearch getObjectSearch() {
		return this.searchByPosition;
	}

	public SimulationObject getFirstByPosition(Position position) {
		this.searchByPosition.findNearestObject(position.getX(tokenCore), position.getY(tokenCore));
		return getNextByPosition();
	}
	
	public SimulationObject getNextByPosition() {
		int objectID;
		objectID = this.searchByPosition.getNextObjectID();
		return this.objectMaster.getSimulationObject(objectID);
	}
	
	public boolean checkAndApplyCooldown(int objectId, int cooldownType, int cooldownInSeconds) {
        if (this.objectCooldown != null) {
            return this.objectCooldown.checkAndApplyCooldown(objectId, cooldownType, cooldownInSeconds);
        }
        return true; // Fail-safe: Im Zweifel durchlassen
    }

    public boolean checkAndApplyPeriod(int objectId, int cooldownType, int periodInSeconds) {
        if (this.objectCooldown != null) {
            return this.objectCooldown.checkAndApplyPeriod(objectId, cooldownType, periodInSeconds);
        }
        return true; // Fail-safe: Im Zweifel durchlassen
    }

    public boolean checkAndApplyCooldown(int objectId, int cooldownType, int cooldownInSeconds, int currentThreshold) {
        if (this.objectCooldown != null) {
            return this.objectCooldown.checkAndApplyCooldown(objectId, cooldownType, cooldownInSeconds, currentThreshold);
        }
        return true; // Fail-safe: Im Zweifel durchlassen
    }
    
 
	/**
	 * Takes an object out of the search by position. Only the ObjectMaster calls this, when it takes the object out of
	 * the simulation (protection concept S1, 2026-11-24: removing belongs to the ObjectMaster).
	 */
	void removeFromPositionSearch(SimulationObject object) {
		this.searchByPosition.remove(object);
	}

	public void changePosition(SimulationObject objectWithNewPosition) {
		this.searchByPosition.changePosition(objectWithNewPosition);
	}
	
	public void propertyChanged(SimulationObject changedObject, ChangedProperty property) {
		switch (property){
			case position: positionChanged(changedObject);
		}
	}
	
	private void positionChanged(SimulationObject changedObject) {
		this.searchByPosition.changePosition(changedObject);
	}
	
	public static void showMessage(String methodName, Object something) {
		Method[] methods = SocialWorld.getSimVisual().getClass().getDeclaredMethods();
		
		for (Method m : methods) {
			String mname = m.getName();
			if (mname.equals(methodName)) {

					try {
						m.setAccessible(true);
					    m.invoke(Simulation.getInstance(), something);
		
					// Handle any exceptions thrown by method to be invoked.
					}
					catch (InvocationTargetException ite) {
					    Throwable cause = ite.getCause();
					    System.out.println( cause.getMessage());
					}
					catch (IllegalAccessException iae) {
						iae.printStackTrace();
					} 
					
					break;
			}
		}

	}
	
}
