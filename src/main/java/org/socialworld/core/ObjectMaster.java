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

	// Jeder Typ erhält ein exklusives ID-Band von 1.000 IDs
	private static final int RANGE_SIZE = 1000;


	private static final int SIMULATION_OBJECTS_START_CAPACITY = 10000;
	
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
		loaders[1] = LoadHuman.getExlusiveInstance(simulationObjects);
		loaders[2] = LoadAnimal.getExlusiveInstance(simulationObjects);
		loaders[3] = LoadGod.getExlusiveInstance(simulationObjects);
		loaders[4] = LoadItem.getExlusiveInstance(simulationObjects);
		loaders[5] = LoadMagic.getExlusiveInstance(simulationObjects);

		creators = new CreateSimulationObjects[8];
		creators[1] = CreateHuman.getExlusiveInstance();
		creators[2] = CreateAnimal.getExlusiveInstance();
		creators[3] = CreateGod.getExlusiveInstance();
		creators[4] = CreateItem.getExlusiveInstance();
		creators[5] = CreateMagic.getExlusiveInstance();
		
		    
	    for (SimulationObject_Type type : SimulationObject_Type.values()) {
	    	
	    	// noObject ignorieren
	    	if (type == SimulationObject_Type.noObject) continue;
	    	
	        int idx = type.getIndex();
	        
	        // Jedes Band startet exklusiv 
	        int startID = (idx - 1) * RANGE_SIZE;
	        
	        this.typeStartIDs[idx] = startID;
	        this.typeEndIDs[idx] = startID + RANGE_SIZE - 1;
	        
	        // Cursor direkt auf den Startwert des jeweiligen Nummernkreises setzen
	        this.typeIdCursors[idx] = startID;

	        // max. IDs direkt auf den Startwert des jeweiligen Nummernkreises setzen
	        this.typeMaxIDs[idx] = startID;
	    }

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
		
		
		// TODO typeMaxIDs  belegen
		
		int allIDs[];
		int length;
		int index;
		
		String fullClassName;
		
		int objectID;
		int type;
		
		TableObject tableObjects;
		tableObjects = new TableObject();
		
		tableObjects.select(tableObjects.SELECT_COLUMNS_ID_TYPE, "", "ORDER BY id");
		
		allIDs = tableObjects.getAllPK1();
		length = allIDs.length;
		
		for (index = 0; index < length; index++) {
			objectID = allIDs[index];
			type = tableObjects.getType(index);

			switch (type) {
				case 1: fullClassName = "org.socialworld.objects.Human"; break;
				case 2: fullClassName = "org.socialworld.objects.concrete.animals.mammals.Dog"; break;
				case 3: fullClassName = "org.socialworld.objects.concrete.gods.Weather"; break;
				case 4: fullClassName = "org.socialworld.objects.concrete.eatable.fruits.Apple"; break;
				case 5: fullClassName = "org.socialworld.objects.concrete.spells.Lightning"; break;
				default: continue;
			}
			this.loaders[type].createObject(objectID, fullClassName);
		}
		
		for (index = 0; index < length; index++) {
			objectID = allIDs[index];
			type = tableObjects.getType(index);

			this.loaders[type].loadObject(objectID);
			addObjectToList(SimulationObject_Type.getSimulationObjectType(type), simulationObjects.get(objectID));
		}
		
		
		this.maxObjectID = getMaxObjectId();
	}
	
	
	int createSimulationObject(
			SimulationObject_Type simulationObjectType, String fullClassName) {
		
		IncompleteSimulationObject incompleteObject;
		SimulationObject object;
		
		int objectID;
		int incompleteObjectIndex;
		

		objectID = typeMaxIDs[simulationObjectType.getIndex()] + 1;
		typeMaxIDs[simulationObjectType.getIndex()] = objectID;
		if (objectID > maxObjectID) maxObjectID = objectID;
		maxObjectID = maxObjectID + 1;

		incompleteObject = creators[simulationObjectType.getIndex()].getObject(objectID, fullClassName);
		
		if (incompleteObject != null) {
			incompleteObjectIndex = incompleteObjects.add(incompleteObject);
			object = incompleteObject.getObject();
			this.simulationObjects.set(objectID, object);
			addObjectToList(simulationObjectType, object);
		}
		else {
			incompleteObjectIndex = -1;
		}
		
		return incompleteObjectIndex;
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
	
	public int refreshNextObjectsState(SimulationObject_Type simObjType) {
		
	    SimulationObject theNextOne = next(simObjType);
	    
	    if (theNextOne != null && theNextOne.isSimulationObject()) {
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
	    int endID   = this.typeEndIDs[typeIndex];
	    
	    // Cursor im eigenen Nummernkreis hochzählen
	    this.typeIdCursors[typeIndex]++;
	    if (this.typeIdCursors[typeIndex] > endID) {
	        log.info("[Nummernkreis-Reset] Typ {} hat das Ende des ID-Bands ({}) erreicht. Springe zurück zu {}", 
	                 simObjType, endID, startID);
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

	
	public void perceiveNextObject() {
		
		SimulationObject theNextOne;
		
		theNextOne = this.simulationObjects.get(this.nextIndexForPerceive);
		
		if (theNextOne.isSimulationObject()) {
			theNextOne.letBePerceived();
		}

		this.nextIndexForPerceive++;
		if (this.nextIndexForPerceive > this.maxObjectID) {
			this.nextIndexForPerceive = 0;
		}
		
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
		}		
	}
	
	private int getMaxObjectId() {
		int maxObjectID = 0;
//		if (maxObjectID < typeMaxIDs[0]) maxObjectID = typeMaxIDs[0];
		if (maxObjectID < typeMaxIDs[1]) maxObjectID = typeMaxIDs[1];
		if (maxObjectID < typeMaxIDs[2]) maxObjectID = typeMaxIDs[2];
		if (maxObjectID < typeMaxIDs[3]) maxObjectID = typeMaxIDs[3];
		if (maxObjectID < typeMaxIDs[4]) maxObjectID = typeMaxIDs[4];
		if (maxObjectID < typeMaxIDs[5]) maxObjectID = typeMaxIDs[4];
		
		return maxObjectID;
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
	
	
	
}
