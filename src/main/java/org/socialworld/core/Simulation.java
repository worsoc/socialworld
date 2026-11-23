/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
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

import org.socialworld.objects.properties.IContainer;
import java.util.Map;
import java.util.HashMap;
import org.socialworld.GlobalSwitches;
import org.socialworld.objects.*;
import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.objects.access.HiddenItem;
import org.socialworld.objects.concrete.clothes.Glove;
import org.socialworld.objects.concrete.clothes.Shoe;
import org.socialworld.objects.concrete.clothes.Sock;
import org.socialworld.propertyChange.ChangedProperty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.SocialWorld;
import org.socialworld.actions.handle.Inventory;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.actions.handle.StartEquipment;
import org.socialworld.datasource.createObjects.StartEquipmentCatalog;
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
	
	public void startSimulation() {
	
		
		Scheduler myCalcScheduler;
		myCalcScheduler = Scheduler.getInstance();
		myCalcScheduler.startThreads();
		
		// TODO das muss irgendwann wieder rein
		//this.objectMaster.loadSimulationObjects();

	
		this.startThread();
		
		IncompleteSimulationObject incompleteObject;
		
		HiddenHuman myHiddenHuman; 
		Human myHuman;
		
		HiddenItem myHiddenItem;
		Item myItem;

		List<HiddenHuman> hiddenHumans= new ArrayList<HiddenHuman>();
		List<Human> humans= new ArrayList<Human>();

		System.out.println("Start Erstellen Objekte "+ ActualTime.asTime().toString());
		
	//	ValueProperty propInventory[] = new ValueProperty[3];

		for (int i = 0; i < 100; i++ ) {
	//		if (GlobalSwitches.OUTPUT_CREATE_OBJECT_HUMANS) System.out.println("Start createSimulationObject Human " + i + " " +  ActualTime.asTime().toString());
			incompleteObject = createSimulationObject(SimulationObject_Type.human, "org.socialworld.objects.Human");
//			if (GlobalSwitches.OUTPUT_CREATE_OBJECT_HUMANS) System.out.println("Ende createSimulationObject Human " + i + " " +  ActualTime.asTime().toString());
			
			myHiddenHuman = (HiddenHuman) incompleteObject.getHiddenObject();
			myHuman = (Human) incompleteObject.getObject();
			
			
			hiddenHumans.add(myHiddenHuman);
			humans.add(myHuman);
			
			if (GlobalSwitches.OUTPUT_CREATE_OBJECT_HUMANS) System.out.println("Human(" + myHuman.getObjectID() + "): " + myHuman.getPosition(tokenCore).toString() + " " +  ActualTime.asTime().toString());
		}
	
		System.out.println("Start Erstellen Objekte Inventar "+ ActualTime.asTime().toString());

		// The start equipment of the characters (inventory concept V3, 2026-11-23): clothes and items from
		// equipment.csv; they are carried by their human (connection carried) and draw no start position.
		// Replaces the test clothes and the one apple shared by all mouths.
		StartEquipmentCatalog catalog = StartEquipmentCatalog.getInstance();
		for (String problem : catalog.getProblems()) System.err.println("StartEquipmentCatalog: " + problem);
		int[] counts = new int[4];	// placed, put down at the feet, liquids left for V5, breads (D7)
		for (int i = 0; i < humans.size(); i++ ) {
			Inventory inventory = new Inventory(SimulationObject_Type.human);
			hiddenHumans.get(i).setInventory(inventory);
			equip(humans.get(i), inventory, catalog.get(humans.get(i).getCharacterNumber()), counts);
		}
		System.out.println("Start equipment: " + counts[0] + " items placed, " + counts[1] + " put down at the feet, "
				+ counts[2] + " liquids without container, " + counts[3] + " breads for those without food");

		System.out.println("Start Erstellen Aepfel "+ ActualTime.asTime().toString());

		for (int i = 0; i < 75; i++ ) {
			incompleteObject = createSimulationObject(SimulationObject_Type.item, "org.socialworld.objects.concrete.eatable.fruits.Apple");
			myHiddenItem = (HiddenItem) incompleteObject.getHiddenObject();
			myItem = (Item) incompleteObject.getObject();
			if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Apple(" + myItem.getObjectID() + "):" + myItem.getPosition(tokenCore).toString());
		}
		
		System.out.println("Ende Erstellen Objekte "+ ActualTime.asTime().toString());

		EventInfluenceDescriptionPool.getInstance();
		// load the state-to-action rules (swpool_s2a) here as well, not only when the first human acts
		State2ActionDescriptionPool.getInstance();
		// and the reaction rules (swpool_erd), not only at the first event that asks for a reaction
		EventReactionDescriptionPool.getInstance();
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
	
	/**
	 * Equips one human from its entry of the start equipment: first the clothes (more clothes before the kit, so
	 * they take the place), then the items (the first food into the mouth), then a bread into an empty mouth (D7).
	 * A piece is only created when it has a place, except an item without place, which is put down at the feet.
	 */
	private void equip(Human human, Inventory inventory, StartEquipmentCatalog.Entry entry, int[] counts) {
		Position at = human.getPosition(tokenCore);
		if (entry != null) {
			for (StartEquipmentCatalog.Piece piece : entry.clothes) {
				SimulationObject prototype = prototype(piece.className, piece.side);
				InventoryPlace place = StartEquipment.chooseWornPlace(inventory, prototype);
				if (place == InventoryPlace.noWhere) continue;	// the place is taken by the more clothes
				Item item = createCarried(piece.className, piece.side, at);
				if (item != null && inventory.put(place, item)) counts[0]++;
			}
			// containers and what they hold so far (inventory concept V5)
			List<SimulationObject> containers = new ArrayList<SimulationObject>();
			Map<SimulationObject, List<SimulationObject>> contents = new HashMap<SimulationObject, List<SimulationObject>>();
			for (String className : entry.items) {
				SimulationObject prototype = prototype(className, StartEquipmentCatalog.Side.none);
				StartEquipment.Decision decision = StartEquipment.decide(inventory, containers, contents, prototype);
				Item item = createCarried(className, StartEquipmentCatalog.Side.none, at);
				if (item == null) continue;
				if (decision.isInContainer()) {
					if (decision.container.putInside(item)) {
						contents.get(decision.container).add(item);
						counts[0]++;
					}
					else counts[1]++;
				}
				else if (!decision.isNowhere() && inventory.put(decision.place, item)) counts[0]++;
				else counts[1]++;	// it stays where it was created: at the feet of the human
				if (item instanceof IContainer) {
					containers.add(item);
					contents.put(item, new ArrayList<SimulationObject>());
				}
			}
			// the liquids into their containers (a jug of beer, a waterskin of water)
			for (String className : entry.contents) {
				SimulationObject prototype = prototype(className, StartEquipmentCatalog.Side.none);
				StartEquipment.Decision decision = StartEquipment.decide(inventory, containers, contents, prototype);
				if (!decision.isInContainer()) { counts[2]++; continue; }
				Item liquid = createCarried(className, StartEquipmentCatalog.Side.none, at);
				if (liquid != null && decision.container.putInside(liquid)) {
					contents.get(decision.container).add(liquid);
					counts[0]++;
				}
				else counts[2]++;
			}
		}
		if (inventory.isFree(InventoryPlace.mouth)) {
			Item bread = createCarried("org.socialworld.objects.concrete.eatable.bakery.Bread", StartEquipmentCatalog.Side.none, at);
			if (bread != null && inventory.put(InventoryPlace.mouth, bread)) counts[3]++;
		}
	}

	/** An object of the class without state, only to see where it fits (or null). */
	private static SimulationObject prototype(String className, StartEquipmentCatalog.Side side) {
		try {
			SimulationObject object = (SimulationObject) Class.forName(className).getDeclaredConstructor().newInstance();
			setSide(object, side);
			return object;
		} catch (ReflectiveOperationException | ClassCastException e) {
			return NoSimulationObject.getObjectNothing();
		}
	}

	private static void setSide(SimulationObject object, StartEquipmentCatalog.Side side) {
		if (side == StartEquipmentCatalog.Side.none) return;
		boolean left = (side == StartEquipmentCatalog.Side.left);
		if (object instanceof Shoe) ((Shoe) object).setLeftSide(left);
		else if (object instanceof Sock) ((Sock) object).setLeftSide(left);
		else if (object instanceof Glove) ((Glove) object).setLeftSide(left);
	}

	/** Creates an item at the position of its carrier, without drawing a start position. */
	private Item createCarried(String className, StartEquipmentCatalog.Side side, Position at) {
		IncompleteSimulationObject incomplete = createSimulationObject(SimulationObject_Type.item, className, at);
		SimulationObject object = incomplete.getObject();
		if (!(object instanceof Item)) return null;
		setSide(object, side);
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Equipment " + object.getClass().getSimpleName() + "(" + object.getObjectID() + ")");
		return (Item) object;
	}

	private IncompleteSimulationObject createSimulationObject(
			SimulationObject_Type simulationObjectType,
			String fullClassName) {
		return createSimulationObject(simulationObjectType, fullClassName, null);
	}

	private IncompleteSimulationObject createSimulationObject(
			SimulationObject_Type simulationObjectType,
			String fullClassName, Position startPosition) {
		
		int incompleteObjectIndex;
		int objectID;
		
		IncompleteSimulationObject incompleteObject = new IncompleteSimulationObject();
//		HiddenSimulationObject hiddenObject = NoHiddenSimulationObject.getObjectNothing();
		SimulationObject createdObject = NoSimulationObject.getObjectNothing() ;
	
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT_DETAILS) System.out.println("Erstellen SimObj > this.objectMaster.createSimulationObject: " + ActualTime.asTime().toString());
		incompleteObjectIndex = (startPosition == null)
				? this.objectMaster.createSimulationObject(simulationObjectType, fullClassName)
				: this.objectMaster.createSimulationObject(simulationObjectType, fullClassName, startPosition);
		if (incompleteObjectIndex >= 0) {
		//	if (GlobalSwitches.OUTPUT_CREATE_OBJECT_DETAILS) System.out.println("Erstellen SimObj > getObjectIDForIncompleteObjectIndex: " + ActualTime.asTime().toString());
			objectID = this.objectMaster.getObjectIDForIncompleteObjectIndex(incompleteObjectIndex);
			if (objectID > 0) {
				//	if (GlobalSwitches.OUTPUT_CREATE_OBJECT_DETAILS) System.out.println("Erstellen SimObj > this.objectMaster.getIncompleteObject: " + ActualTime.asTime().toString());
				incompleteObject = this.objectMaster.getIncompleteObject(incompleteObjectIndex, objectID);
				createdObject = incompleteObject.getObject();
				if (createdObject.isSimulationObject()) {
					//	if (GlobalSwitches.OUTPUT_CREATE_OBJECT_DETAILS) System.out.println("Erstellen SimObj > this.searchByPosition.addObject: " + ActualTime.asTime().toString());
					this.searchByPosition.addObject(createdObject);
				}
//				hiddenObject = incompleteObject.getHiddenObject();
			}
		}
		
		return incompleteObject; 
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
