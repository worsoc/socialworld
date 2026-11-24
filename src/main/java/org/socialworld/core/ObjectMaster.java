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



import org.socialworld.attributes.Position;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.objects.*;
import org.socialworld.propertyChange.ListenedMap;
import org.socialworld.datasource.createObjects.CreateAnimal;
import org.socialworld.datasource.createObjects.CreateGod;
import org.socialworld.datasource.createObjects.CreateHuman;
import org.socialworld.datasource.createObjects.CreateItem;
import org.socialworld.datasource.createObjects.CreateMagic;
import org.socialworld.datasource.createObjects.CreateSimulationObjects;
import org.socialworld.datasource.loadObjects.LoadAnimal;
import org.socialworld.datasource.loadObjects.LoadGod;
import org.socialworld.datasource.loadObjects.LoadHuman;
import org.socialworld.datasource.loadObjects.LoadItem;
import org.socialworld.datasource.loadObjects.LoadMagic;
import org.socialworld.datasource.loadObjects.LoadSimulationObjects;
import org.socialworld.datasource.tablesSimulation.TableObject;
import org.socialworld.collections.IncompleteObjects;
import org.socialworld.collections.SimulationObjectArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mathias Sikos (MatWorsoc)
 *
 */
public class ObjectMaster {

    // Logger-Instanz SLF4J für die Klasse registrieren
	private static final Logger log = LoggerFactory.getLogger(ObjectMaster.class);

	// Jeder Typ erhält ein exklusives ID-Band von 10.000 IDs (bis 2026-11-23: 1.000; zu wenig für die
	// Ausstattung der 100 Charaktere mit Items). Die größte ID (magic, 49.999) bleibt unter
	// LIVING_SIM_OBJECTS_MAX_NUMBER, nach dem TickRandomCache und TickObjectCooldown bemessen sind.
	private static final int RANGE_SIZE = 10000;

	static final int LIVING_SIM_OBJECTS_MAX_NUMBER = 100000;

	// so large that the bands of all five types fit without growing the array (items start at 30.001)
	private static final int SIMULATION_OBJECTS_START_CAPACITY = 50000;
	
	private static ObjectMaster instance;
	
	private LoadSimulationObjects[] loaders;
	private CreateSimulationObjects[] creators;
		
	private IncompleteObjects incompleteObjects;
	
	private final ListenedMap<God> gods;
	private final ListenedMap<Human> humans;
	private final ListenedMap<Item> items;
	private final ListenedMap<Magic> magics;
	private final ListenedMap<Animal> animals;
	
	private final SimulationObjectArray simulationObjects;
	private int maxObjectID = 0;
	private int nextIndexForPerceive = 0;
	
	private final int[] typeMaxIDs = new int[SimulationObject_Type.values().length];
	private final int[] typeIdCursors = new int[SimulationObject_Type.values().length];
	private final int[] typeStartIDs = new int[SimulationObject_Type.values().length];
	private final int[] typeEndIDs = new int[SimulationObject_Type.values().length];

	
	private ObjectMaster() {

		this.simulationObjects = new SimulationObjectArray(SIMULATION_OBJECTS_START_CAPACITY);
		
		this.gods = new ListenedMap<God>();
		this.humans = new ListenedMap<Human>();
		this.animals = new ListenedMap<Animal>();
		this.magics = new ListenedMap<Magic>();
		this.items = new ListenedMap<Item>();
		
		this.incompleteObjects = new IncompleteObjects();
		
		
		loaders = new LoadSimulationObjects[8];
		loaders[SimulationObject_Type.human.getIndex()] = LoadHuman.getExlusiveInstance(simulationObjects);
		loaders[SimulationObject_Type.animal.getIndex()] = LoadAnimal.getExlusiveInstance(simulationObjects);
		loaders[SimulationObject_Type.god.getIndex()] = LoadGod.getExlusiveInstance(simulationObjects);
		loaders[SimulationObject_Type.item.getIndex()] = LoadItem.getExlusiveInstance(simulationObjects);
		loaders[SimulationObject_Type.magic.getIndex()] = LoadMagic.getExlusiveInstance(simulationObjects);

		creators = new CreateSimulationObjects[8];
		creators[SimulationObject_Type.human.getIndex()] = CreateHuman.getExlusiveInstance();
		creators[SimulationObject_Type.animal.getIndex()] = CreateAnimal.getExlusiveInstance();
		creators[SimulationObject_Type.god.getIndex()] = CreateGod.getExlusiveInstance();
		creators[SimulationObject_Type.item.getIndex()] = CreateItem.getExlusiveInstance();
		creators[SimulationObject_Type.magic.getIndex()] = CreateMagic.getExlusiveInstance();
		
		    
	    for (SimulationObject_Type type : SimulationObject_Type.values()) {
	    	
	    	// noObject ignorieren
	    	if (type == SimulationObject_Type.noObject) continue;
	    	
	        int idx = type.getIndex();
	        
	        // Jedes Band startet exklusiv 
	        int startID = rangeStart(type);
	        
	        this.typeStartIDs[idx] = startID;
	        this.typeEndIDs[idx] = rangeEnd(type);
	        
	        // Cursor direkt auf den Startwert des jeweiligen Nummernkreises setzen
	        this.typeIdCursors[idx] = startID;

	        // max. IDs direkt auf den Startwert des jeweiligen Nummernkreises setzen
	        this.typeMaxIDs[idx] = startID;
	    }

	}	
	
	/** The first ID of the band of a type; it is never given out, the first object gets startID + 1. */
	static int rangeStart(SimulationObject_Type type) {
		return (type.getIndex() - 1) * RANGE_SIZE;
	}

	/** The last ID of the band of a type. */
	static int rangeEnd(SimulationObject_Type type) {
		return rangeStart(type) + RANGE_SIZE - 1;
	}

	/**
	 * Every type has its own band of IDs. An ID beyond the band would land in the band of the next
	 * type (e.g. items in the band of magic) and mix up the objects, so the simulation stops here.
	 *
	 * @throws IllegalStateException if the ID lies outside the band of the type
	 */
	static void checkIdInRange(SimulationObject_Type type, int objectID) {
		int start = rangeStart(type);
		int end = rangeEnd(type);
		if (objectID > start && objectID <= end) return;

		SimulationObject_Type intruded = SimulationObject_Type.getSimulationObjectType(objectID / RANGE_SIZE + 1);
		String message = "ObjectMaster: the ID " + objectID + " of a " + type + " lies outside its band "
				+ (start + 1) + ".." + end
				+ (intruded != SimulationObject_Type.noObject && intruded != type ? ", in the band of " + intruded : "")
				+ ". At most " + (RANGE_SIZE - 1) + " objects per type; raise RANGE_SIZE or create fewer objects.";
		log.error(message);
		System.err.println(message);
		throw new IllegalStateException(message);
	}

	public static ObjectMaster getInstance() {
		if (instance == null) {
			instance = new ObjectMaster();
		}
		return instance;
	}
	
	public SimulationObject getSimulationObject(int objectID) {
		return this.simulationObjects.get(objectID);
	}
	
	public void loadSimulationObjects() {
		
		
		int allIDs[];
		int length;
		int index;
		
		String fullClassName;
		
		int objectID;
		int type;
		SimulationObject_Type simObjType;
		
		TableObject tableObjects;
		tableObjects = new TableObject();
		
		tableObjects.select(tableObjects.SELECT_COLUMNS_ID_TYPE, "", "ORDER BY id");
		
		allIDs = tableObjects.getAllPK1();
		length = allIDs.length;
		
		for (index = 0; index < length; index++) {
			objectID = allIDs[index];
			type = tableObjects.getType(index);
			simObjType = SimulationObject_Type.getSimulationObjectType(type);
			
			switch (simObjType) {
				case human: fullClassName = "org.socialworld.objects.Human"; break;
				case animal: fullClassName = "org.socialworld.objects.concrete.animals.mammals.Dog"; break;
				case god: fullClassName = "org.socialworld.objects.concrete.gods.Weather"; break;
				case item: fullClassName = "org.socialworld.objects.concrete.eatable.fruits.Apple"; break;
				case magic: fullClassName = "org.socialworld.objects.concrete.spells.Lightning"; break;
				default: continue;
			}
			checkIdInRange(simObjType, objectID);
			this.loaders[type].createObject(objectID, fullClassName);
		}
		
		for (index = 0; index < length; index++) {
			objectID = allIDs[index];
			type = tableObjects.getType(index);

			this.loaders[type].loadObject(objectID);
			addObjectToList(SimulationObject_Type.getSimulationObjectType(type), simulationObjects.get(objectID));
		}
		
	}
	
	
	/**
	 * As createSimulationObject, but the object starts at the given position instead of one drawn from the
	 * start positions (swgauss_position): for carried items, which take the position of their carrier.
	 */
	int createSimulationObject(SimulationObject_Type simulationObjectType, String fullClassName, Position startPosition) {
		CreateSimulationObjects creator = creators[simulationObjectType.getIndex()];
		creator.setPresetStartPosition(startPosition);
		try {
			return createSimulationObject(simulationObjectType, fullClassName);
		} finally {
			creator.setPresetStartPosition(null);
		}
	}

	int createSimulationObject(
			SimulationObject_Type simulationObjectType, String fullClassName) {
		
		IncompleteSimulationObject incompleteObject;
		SimulationObject object;
		
		int objectID;
		int incompleteObjectIndex;

		objectID = typeMaxIDs[simulationObjectType.getIndex()] + 1;
		checkIdInRange(simulationObjectType, objectID);

		incompleteObject = creators[simulationObjectType.getIndex()].getObject(objectID, fullClassName);
		
		if (incompleteObject != null) {
			incompleteObjectIndex = incompleteObjects.add(incompleteObject);
			object = incompleteObject.getObject();
			this.simulationObjects.set(objectID, object);
			// erst hier werden die max IDs hochgezählt
			addObjectToList(simulationObjectType, object);
		}
		else {
			incompleteObjectIndex = -1;
		}
		
		return incompleteObjectIndex;
	}
	
	public void perceiveNextObject() {
		
		SimulationObject theNextOne;
		
		theNextOne = this.simulationObjects.get(this.nextIndexForPerceive);
		
		if (theNextOne.isSimulationObject()) {
			theNextOne.letBePerceived();
		}

		// jump from band to band over the unused IDs (2026-11-23): every empty ID cost a pass
		// with Thread.yield() in the PercipienceMaster
		this.nextIndexForPerceive = nextIdForPerceive(this.nextIndexForPerceive, this.typeStartIDs, this.typeMaxIDs);

	}

	/**
	 * The ID the perception visits after the given one: the next ID in the same band as long as it is given
	 * out, else the first ID of the next band that has objects, after the last such band the first one again.
	 * A band has objects when its highest given ID lies above its start (the start itself is never given out).
	 *
	 * @param currentID the ID visited last (0 at the beginning)
	 * @param startIDs the start of the band per type index
	 * @param maxIDs the highest given ID per type index (the start of the band while there is none)
	 * @return the next ID to visit, or 0 if there is no object at all
	 */
	static int nextIdForPerceive(int currentID, int[] startIDs, int[] maxIDs) {
		int next = currentID + 1;
		int typeIndex = next / RANGE_SIZE + 1;

		if (typeIndex < maxIDs.length && maxIDs[typeIndex] > startIDs[typeIndex] && next <= maxIDs[typeIndex]) {
			return next;
		}
		for (int i = typeIndex + 1; i < maxIDs.length; i++) {
			if (maxIDs[i] > startIDs[i]) return startIDs[i] + 1;
		}
		for (int i = 1; i <= Math.min(typeIndex, maxIDs.length - 1); i++) {
			if (maxIDs[i] > startIDs[i]) return startIDs[i] + 1;
		}
		return 0;
	}

	/**
	 * The highest given ID over all types, 0 if there is none. Only types with objects count: a type's
	 * highest ID starts at the start of its band (2026-11-23; before, an empty band counted with its start, and the
	 * last line compared magic but took the item value).
	 */
	static int maxGivenObjectId(int[] startIDs, int[] maxIDs) {
		int result = 0;
		for (int i = 1; i < maxIDs.length; i++) {
			if (maxIDs[i] > startIDs[i] && maxIDs[i] > result) result = maxIDs[i];
		}
		return result;
	}
	
	public int refreshNextObjectsState(SimulationObject_Type simObjType) {
		
	    SimulationObject theNextOne = next(simObjType);
	    
	    if (theNextOne != null && theNextOne.isSimulationObject()) {
	        
	        // 1. BLITZSCHNELLE EINLASS-KONTROLLE AN DER WURZEL
	        // Wir holen die ID direkt vom Basis-Objekt, ohne Casts oder Kapselungs-Overhead
	        int objectID = theNextOne.getObjectID(); 
	        
	        // 2. TIMING-SCHLEUSE: Nur 1 Refresh-Schritt pro logischem Tick/Sekunde erlauben
	        if (!Simulation.getInstance().checkAndApplyCooldown(objectID, TickObjectCooldown.TYPE_REFRESH, 1)) {
	            // Das Objekt war in dieser Sekunde schon dran. 
	            // Wir überspringen den kompletten Zustandshochlauf komplett allokations- und rechenfrei!
	            return simObjType.getIndex(); 
	        }
	        
	        // Erst wenn das Cooldown grünes Licht gibt, investieren wir CPU-Zeit in den Zustand:
	        theNextOne.refreshState();
	        return simObjType.getIndex(); // Erfolg: Zustand aktualisiert
	    }
	    
	    // Keine passende ID gefunden oder Lücke -> Weiterschalten zum nächsten Typ/Schritt
	    return simObjType.next();
	}


	private SimulationObject next(SimulationObject_Type simObjType) {
	    if (this.maxObjectID == 0) {
	        return null;
	    }

	    int typeIndex = simObjType.getIndex();
	
	    // Blitzschneller Array-Lookup statt switch-case-Berechnung
	    int startID = this.typeStartIDs[typeIndex];
//	    int endID   = this.typeEndIDs[typeIndex];
	    int maxID	= this.typeMaxIDs[typeIndex];
	    
	    // Cursor im eigenen Nummernkreis hochzählen
	    this.typeIdCursors[typeIndex]++;
	    if (this.typeIdCursors[typeIndex] > maxID) {
	    	if (maxID > startID) {
	//	        log.info("[Nummernkreis-Reset] Typ {} hat das Ende des ID-Bands ({}) erreicht. Springe zurück zu {}", 
	//	                 simObjType, maxID, startID);
	    	}
		    this.typeIdCursors[typeIndex] = startID; // Ringpuffer innerhalb des Nummernkreises
	    }

	    int currentID = this.typeIdCursors[typeIndex];
	    SimulationObject result = null;
	
	                 
        switch (simObjType) {
            case god:   result = this.gods.get(currentID); break;
            case human: result = this.humans.get(currentID); break;
            case animal:result = this.animals.get(currentID); break;
            case magic: result = this.magics.get(currentID); break;
            case item:  result = this.items.get(currentID); break;
            default: result = null;
        }

        // --- DIAGNOSE-LOGS ---
        if (result != null) {
            // Ein Treffer: Zeigt uns, dass wir ein Objekt im richtigen ID-Band gefunden haben
 //           log.info("[Zonierung-HIT] Typ: {} | ID: {} | Band: [{} - {}] -> Objekt '{}' geladen.", 
//                     simObjType, currentID, startID, endID, result.getClass().getSimpleName());
        } else {
            // Optionale Ausgabe für ungenutzte IDs im Kreis (für den Test)
            // Sollte im Dauerlauf deaktiviert werden, um die Konsole nicht zu fluten
            log.debug("[Zonierung-EMPTY-SLOT] Typ: {} | ID: {} -> Unbelegte ID im eigenen Band.", 
                      simObjType, currentID);
        }

        return result;

	}

	/**
	 * Takes an object out of the simulation: out of the search by position, out of the list of its type and out of
	 * the array of all objects. Its ID is not given out again.
	 * Only a valid token of an allowed cluster may do this; for now that is the cluster inventory (eating up,
	 * protection concept S1, 2026-11-24). Carried objects are not refused; the caller releases the connections
	 * before (open point O1: clean up on removal).
	 *
	 * @return true if the object was taken out
	 */
	public boolean removeSimulationObject(IAccessToken token, SimulationObject object) {
		if (!mayRemove(token)) return false;
		if (object == null || !object.isSimulationObject()) return false;
		if (this.simulationObjects.get(object.getObjectID()) != object) return false;
		Simulation.getInstance().removeFromPositionSearch(object);
		removeFromLists(object);
		return true;
	}

	/**
	 * @return true if the token may take objects out of the simulation (for now only the cluster inventory)
	 */
	static boolean mayRemove(IAccessToken token) {
		return token != null && token.isValid() && token.getSimulationCluster() == SimulationCluster.inventory;
	}

	private void removeFromLists(SimulationObject object) {
		int objectID = object.getObjectID();
		if (this.simulationObjects.get(objectID) != object) return;
		switch (SimulationObject_Type.getSimulationObjectType(objectID / RANGE_SIZE + 1)) {
		case animal:	this.animals.remove(objectID); break;
		case human:		this.humans.remove(objectID); break;
		case god:		this.gods.remove(objectID); break;
		case item:		this.items.remove(objectID); break;
		case magic:		this.magics.remove(objectID); break;
		default:		return;
		}
		this.simulationObjects.delete(objectID);
	}

	private void addObjectToList(SimulationObject_Type simulationObjectType, SimulationObject object) {

		// TODO (MatWorsoc) weitere Objekttypen hinzufuegen
		int objectID = object.getObjectID();
		switch (simulationObjectType) {
		case animal:
			this.animals.put(objectID, (Animal)object);
			break;
		case human:
			this.humans.put(objectID, (Human)object);
			break;
		case god:
			this.gods.put(objectID, (God)object);
			break;
		case item:
			this.items.put(objectID, (Item)object);
			break;
		case magic:
			this.magics.put(objectID, (Magic)object);
			break;
		default:
			object = null;
			return;
		}	
		
		typeMaxIDs[simulationObjectType.getIndex()] = objectID;
		if (objectID > maxObjectID) maxObjectID = objectID;

	}
	
	private int getMaxObjectId() {
		return maxGivenObjectId(this.typeStartIDs, this.typeMaxIDs);
	}
	
	
	int getObjectIDForIncompleteObjectIndex(int  incompleteObjectsIndex) {
		IncompleteSimulationObject incompleteObject;
		incompleteObject = this.incompleteObjects.get(incompleteObjectsIndex);
		
		if (incompleteObject.isValid()) {
			return incompleteObject.getObjectID();
		}
		else {
			return 0;
		}
	}
	
/*	
	SimulationObject getObjectForIncompleteObjectIndex(int  incompleteObjectsIndex, int objectID) {
		
		IncompleteSimulationObject incompleteObject;
		incompleteObject = this.incompleteObjects.get(incompleteObjectsIndex);
		
		if (incompleteObject.isValid() && incompleteObject.getObjectID() == objectID) {
			return incompleteObject.getObject();
		}
		else {
			return NoSimulationObject.getObjectNothing();
		}
		
	}
*/
/*
	HiddenSimulationObject getHiddenObjectForIncompleteObjectIndex(int  incompleteObjectsIndex, int objectID) {
		
		IncompleteSimulationObject incompleteObject;
		incompleteObject = this.incompleteObjects.get(incompleteObjectsIndex);
		
		if (incompleteObject.isValid() && incompleteObject.getObjectID() == objectID) {
			return incompleteObject.getHiddenObject();
		}
		else {
			return new HiddenSimulationObject(); // instead of null
		}
	}
*/
	IncompleteSimulationObject getIncompleteObject(int  incompleteObjectsIndex, int objectID) {
		
		IncompleteSimulationObject incompleteObject;
		incompleteObject = this.incompleteObjects.get(incompleteObjectsIndex);
		
		if (incompleteObject.isValid() && incompleteObject.getObjectID() == objectID) {
			return incompleteObject;
		}
		else {
			return new IncompleteSimulationObject(); // instead of null
		}
	}

	void setSimulationObjectComplete(int  incompleteObjectsIndex, int objectID) {
		
		IncompleteSimulationObject removedObject = this.incompleteObjects.remove(incompleteObjectsIndex, objectID);
		
		if (removedObject.isValid()) {
			removedObject.setComplete();
		}
	}
	
	/**
	 * The method returns the list of gods
	 * 
	 * @return
	 */
	public ListenedMap<God> getGods() {
		return this.gods;
	}

	/**
	 * The method returns the list of humans
	 * 
	 * @return
	 */
	public ListenedMap<Human> getHumans() {
		return this.humans;
	}

	/**
	 * The method returns the list of items
	 * 
	 * @return
	 */
	public ListenedMap<Item> getItems() {
		return this.items;
	}


	/**
	 * The method returns a list of magics
	 * 
	 * @return
	 */
	public ListenedMap<Magic> getMagics() {
		return this.magics;
	}	
	
    public static int mapObjectIdTo_1_999(int objectID) {
        // 1. Bit-Mischung (Scrambling), um aufeinanderfolgende Zahlen zu streuen
        long hash = (long) objectID * 0x45d9f3b;
        hash = ((hash >>> 16) ^ hash) * 0x45d9f3b;
        hash = (hash >>> 16) ^ hash;

        // 2. Mathematisch korrekter Modulo für den Bereich 1 bis 999
        int ergebnis = (int) (Math.abs(hash) % 999) + 1;
        
        return ergebnis;
    }

	
}
