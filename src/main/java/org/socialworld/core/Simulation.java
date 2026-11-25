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

import org.socialworld.objects.properties.IContainer;
import java.util.Map;
import java.util.HashMap;
import org.socialworld.GlobalSwitches;
import org.socialworld.objects.*;
import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.objects.access.HiddenItem;
import org.socialworld.objects.access.HiddenSimulationObject;
import org.socialworld.objects.enums.EnumSide;
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
import org.socialworld.datasource.createObjects.BodyBuildCatalog;
import org.socialworld.datasource.createObjects.CompositionAssignment;
import org.socialworld.datasource.tablesSimulation.propertySets.TableKindMaterialSet;
import org.socialworld.datasource.tablesSimulation.propertySets.TableMaterialSet;
import org.socialworld.datasource.tablesSimulation.states.CacheTableStateComposition;
import org.socialworld.datasource.tablesSimulation.states.TableStateComposition;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.attributes.properties.Material;
import org.socialworld.attributes.properties.MaterialSet;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Calculation;
import org.socialworld.calculation.application.AttributeCalculator;
import org.socialworld.calculation.application.InventoryCalculator;
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

	// body build concept B3 (2026-11-25): swstate_composition was empty at the start, so every new instance gets
	// its set derived from swkind_materialset, and the rows (objectID, setID) are saved after the creation
	private boolean deriveComposition = false;
	private final List<int[]> compositionRows = new ArrayList<int[]>();
	private int compositionsWithoutSet = 0;
	private int compositionsWithoutMaterial = 0;	// a piece asked for a material that no variant has (step 2)

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

		// body build concept 9.1 (2026-11-25): the composition is the first state the simulation saves itself.
		// Filled means loading, which is not promising yet, so for now the start is refused.
		int compositionRows = CacheTableStateComposition.getInstance().rowCount();
		if (compositionRows > 0) {
			System.out.println("Start refused: swstate_composition is not empty (" + compositionRows + " rows). "
					+ "Loading the states is not ready yet. Press \"Zusammensetzung leeren\" in the "
					+ "PropertySetCreationTool and start again.");
			return false;
		}
		this.deriveComposition = true;

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
		List<Integer> characterNumbers = new ArrayList<Integer>();	// from the creation, for the start equipment

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
			characterNumbers.add(incompleteObject.getCharacterNumber());
			
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
			equip(humans.get(i), hiddenHumans.get(i), inventory, catalog.get(characterNumbers.get(i)), counts);
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

		saveCompositions();

		EventInfluenceDescriptionPool.getInstance();
		// load the state-to-action rules (swpool_s2a) here as well, not only when the first human acts
		State2ActionDescriptionPool.getInstance();
		// and the reaction rules (swpool_erd), not only at the first event that asks for a reaction
		EventReactionDescriptionPool.getInstance();
		return true;
	}

	/**
	 * The composition of a new instance (body build concept B3, 2026-11-25): the set of its template in
	 * swkind_materialset, variant objectID % number of variants; a human takes the template of the body build of
	 * its character. The set goes into the state at once, the row is saved after the creation (saveCompositions).
	 * A piece of the start equipment may ask for a material (equipment.csv Apron[leather], item class or composition
	 * step 2): then only the variants with that main material count.
	 */
	private void deriveComposition(IncompleteSimulationObject incompleteObject, SimulationObject_Type type,
			String fullClassName, Material material) {
		int objectID = incompleteObject.getObject().getObjectID();
		boolean isHuman = (type == SimulationObject_Type.human);
		String template = CompositionAssignment.templateName(CompositionAssignment.simpleName(fullClassName), isHuman,
				isHuman ? BodyBuildCatalog.getInstance().get(incompleteObject.getCharacterNumber()) : null);
		int[] variants = TableKindMaterialSet.getInstance().getMaterialSetIDs(template);
		int setID = CompositionAssignment.setID(variants, objectID, material, Simulation::mainMaterialOf);
		if (setID == CompositionAssignment.NO_SET) {
			if (variants.length > 0) compositionsWithoutMaterial++;
			else compositionsWithoutSet++;
			return;
		}
		incompleteObject.getHiddenObject().setSomething(StateComposition.class.getName(),
				StateComposition.METHODNAME_SET_MATERIAL_SET, TableMaterialSet.getInstance().getMaterialSet(setID));
		compositionRows.add(new int[] {objectID, setID});
	}

	/** @return the main material (largest share) of a material set, Material.nothing if it has none */
	private static Material mainMaterialOf(int setID) {
		MaterialSet set = TableMaterialSet.getInstance().getMaterialSet(setID);
		Object main = set.getMain();
		return (main instanceof Material) ? (Material) main : Material.nothing;
	}

	/** Writes the derived compositions into swstate_composition, one row per instance, and says how many arrived. */
	private void saveCompositions() {
		if (!deriveComposition) return;
		long start = System.currentTimeMillis();
		TableStateComposition table = new TableStateComposition();
		for (int[] row : compositionRows) table.insert(row[0], row[1]);
		table.select(1, "", "");
		int saved = table.rowCount();
		System.out.println("Composition: derived from swkind_materialset and saved, " + saved + " of " + compositionRows.size()
				+ " rows in swstate_composition, " + compositionsWithoutSet + " objects without template, "
				+ compositionsWithoutMaterial + " without a variant of the material asked for ("
				+ (System.currentTimeMillis() - start) / 1000 + " s)");
		if (saved < compositionRows.size()) {
			System.out.println("Composition: WARNING, rows are missing (no row in sw_object for the ID? press FillObject)");
		}
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
	 * The pieces are put on their places and into the containers by the rail inventory (InventoryCalculator, with
	 * the token core, protection concept S2, E7); the inventory itself is only read here, for the decisions.
	 */
	private void equip(Human human, HiddenHuman hiddenHuman, Inventory inventory, StartEquipmentCatalog.Entry entry, int[] counts) {
		InventoryCalculator rail = InventoryCalculator.getInstance();
		Position at = human.getPosition(tokenCore);
		if (entry != null) {
			for (StartEquipmentCatalog.Piece piece : entry.clothes) {
				SimulationObject prototype = prototype(piece.className);
				InventoryPlace place = StartEquipment.chooseWornPlace(inventory, prototype, sideOf(piece.side));
				if (place == InventoryPlace.noWhere) continue;	// the place is taken by the more clothes
				Item item = createCarried(piece.className, piece.side, at, piece.material);
				if (item != null && rail.startPutToInventory(tokenCore, hiddenHuman, place, item)) counts[0]++;
			}
			// containers and what they hold so far (inventory concept V5)
			List<SimulationObject> containers = new ArrayList<SimulationObject>();
			Map<SimulationObject, List<SimulationObject>> contents = new HashMap<SimulationObject, List<SimulationObject>>();
			for (String className : entry.items) {
				SimulationObject prototype = prototype(className);
				StartEquipment.Decision decision = StartEquipment.decide(inventory, containers, contents, prototype);
				Item item = createCarried(className, StartEquipmentCatalog.Side.none, at, Material.nothing);
				if (item == null) continue;
				if (decision.isInContainer()) {
					if (rail.startPutIntoContainer(tokenCore, decision.container, item)) {
						contents.get(decision.container).add(item);
						counts[0]++;
					}
					else counts[1]++;
				}
				else if (!decision.isNowhere() && rail.startPutToInventory(tokenCore, hiddenHuman, decision.place, item)) counts[0]++;
				else counts[1]++;	// it stays where it was created: at the feet of the human
				if (item instanceof IContainer) {
					containers.add(item);
					contents.put(item, new ArrayList<SimulationObject>());
				}
			}
			// the liquids into their containers (a jug of beer, a waterskin of water)
			for (String className : entry.contents) {
				SimulationObject prototype = prototype(className);
				StartEquipment.Decision decision = StartEquipment.decide(inventory, containers, contents, prototype);
				if (!decision.isInContainer()) { counts[2]++; continue; }
				Item liquid = createCarried(className, StartEquipmentCatalog.Side.none, at, Material.nothing);
				if (liquid != null && rail.startPutIntoContainer(tokenCore, decision.container, liquid)) {
					contents.get(decision.container).add(liquid);
					counts[0]++;
				}
				else counts[2]++;
			}
		}
		if (inventory.isFree(InventoryPlace.mouth)) {
			Item bread = createCarried("org.socialworld.objects.concrete.eatable.bakery.Bread", StartEquipmentCatalog.Side.none, at,
					Material.nothing);
			if (bread != null && rail.startPutToInventory(tokenCore, hiddenHuman, InventoryPlace.mouth, bread)) counts[3]++;
		}
	}

	/** An object of the class without state (and so without side), only to see where it fits (or nothing). */
	private static SimulationObject prototype(String className) {
		try {
			return (SimulationObject) Class.forName(className).getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException | ClassCastException e) {
			return NoSimulationObject.getObjectNothing();
		}
	}

	private static EnumSide sideOf(StartEquipmentCatalog.Side side) {
		if (side == StartEquipmentCatalog.Side.left) return EnumSide.left;
		if (side == StartEquipmentCatalog.Side.right) return EnumSide.right;
		return EnumSide.none;
	}

	/**
	 * Creates an item at the position of its carrier, without drawing a start position. The side goes into the
	 * item's state through its hidden object, once at the creation (protection concept S3).
	 */
	private Item createCarried(String className, StartEquipmentCatalog.Side side, Position at, Material material) {
		IncompleteSimulationObject incomplete = createSimulationObject(SimulationObject_Type.item, className, at, material);
		SimulationObject object = incomplete.getObject();
		if (!(object instanceof Item)) return null;
		EnumSide itemSide = sideOf(side);
		if (itemSide != EnumSide.none) {
			HiddenSimulationObject hidden = incomplete.getHiddenObject();
			if (hidden instanceof HiddenItem) ((HiddenItem) hidden).setSide(itemSide);
		}
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Equipment " + object.getClass().getSimpleName() + "(" + object.getObjectID() + ")");
		return (Item) object;
	}

	private IncompleteSimulationObject createSimulationObject(
			SimulationObject_Type simulationObjectType,
			String fullClassName) {
		return createSimulationObject(simulationObjectType, fullClassName, null, Material.nothing);
	}

	/**
	 * @param material the main material asked for (start equipment, item class or composition step 2),
	 *                 Material.nothing for none
	 */
	private IncompleteSimulationObject createSimulationObject(
			SimulationObject_Type simulationObjectType,
			String fullClassName, Position startPosition, Material material) {
		
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
				if (deriveComposition && createdObject.isSimulationObject()) {
					deriveComposition(incompleteObject, simulationObjectType, fullClassName, material);
				}
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
