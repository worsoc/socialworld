/**
 * 
 */
package org.socialworld.core;

import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.socialworld.objects.*;
import org.socialworld.propertyChange.ListenedList;
import org.socialworld.datasource.*;

import org.socialworld.collections.SimulationObjectArray;

/**
 * @author Mathias Sikos (tyloesand)
 *
 */
public class ObjectMaster {

	private static final Logger logger = Logger.getLogger(ObjectMaster.class);
	private static final int SIMULATION_OBJECTS_START_CAPACITY = 1000;
	
	private static ObjectMaster instance;
	
	private final List<God> gods;
	private final List<Human> humans;
	private final List<Item> items;
	private final List<Magic> magics;
	private final List<Animal> animals;
	
	private final SimulationObjectArray simulationObjects;

	private int maxObjectID = 0;
	
	private  ListIterator<God> godsIterator;
	private  ListIterator<Human> humansIterator;
	private  ListIterator<Item> itemsIterator;
	private  ListIterator<Magic> magicsIterator;
	private  ListIterator<Animal> animalsIterator;
	
	
	private ObjectMaster() {
		if (Simulation.WITH_LOGGING == 1 ) logger.debug("create ObjectMaster");

		this.simulationObjects = new SimulationObjectArray(SIMULATION_OBJECTS_START_CAPACITY);
		
		this.gods = new ListenedList<God>();
		this.humans = new ListenedList<Human>();
		this.animals = new ListenedList<Animal>();
		this.magics = new ListenedList<Magic>();
		this.items = new ListenedList<Item>();
		
		resetIterators();
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
	
	public SimulationObject createSimulationObject(
			SimulationObjectType simulationObjectType) {
		SimulationObject object;
		int objectID;
		
		maxObjectID = maxObjectID + 1;
		objectID =  maxObjectID;
		
		// TODO (tyloesand) weitere Objekttypen hinzufügen
		switch (simulationObjectType) {
		case animal:
			object = LoadAnimal.getInstance().getObject(objectID);
			this.animals.add((Animal)object);
			animalsIterator = animals.listIterator(animalsIterator.nextIndex());
			break;
		case human:
			object = LoadHuman.getInstance().getObject(objectID);
			this.humans.add((Human)object);
			humansIterator = humans.listIterator(humansIterator.nextIndex());
			break;
		case god:
			object = LoadGod.getInstance().getObject(objectID);
			this.gods.add((God)object);
			godsIterator = gods.listIterator(godsIterator.nextIndex());
			break;
		case item:
			object = LoadItem.getInstance().getObject(objectID);
			this.items.add((Item)object);
			itemsIterator = items.listIterator(itemsIterator.nextIndex());
			break;
		case magic:
			object = LoadMagic.getInstance().getObject(objectID);
			this.magics.add((Magic)object);
			magicsIterator = magics.listIterator(magicsIterator.nextIndex());
			break;
		default:
			object = null;
		}
		if (object != null) {
			this.simulationObjects.set(objectID, object);
		}
		return object;
	}
	
	public boolean hasNext(SimulationObjectType simulationObjectType) {

		switch (simulationObjectType) {
		case animal:
			return this.animalsIterator.hasNext();
		case human:
			return this.humansIterator.hasNext();
		case god:
			return this.godsIterator.hasNext();
		case item:
			return this.itemsIterator.hasNext();
		case magic:
			return this.magicsIterator.hasNext();
		default:
			return false;
		}		
		
	}
	
	public SimulationObject next(SimulationObjectType simulationObjectType) {
		switch (simulationObjectType) {
		case animal:
			return this.animalsIterator.next();
		case human:
			return this.humansIterator.next();
		case god:
			return this.godsIterator.next();
		case item:
			return this.itemsIterator.next();
		case magic:
			return this.magicsIterator.next();
		default:
			return null;
		}		
		
	}
	
	/**
	 * The method returns the list of gods
	 * 
	 * @return
	 */
	public List<God> getGods() {
		return this.gods;
	}

	/**
	 * The method returns the list of humans
	 * 
	 * @return
	 */
	public List<Human> getHumans() {
		return this.humans;
	}

	/**
	 * The method returns the list of items
	 * 
	 * @return
	 */
	public List<Item> getItems() {
		return this.items;
	}


	/**
	 * The method returns a list of magics
	 * 
	 * @return
	 */
	public List<Magic> getMagics() {
		return this.magics;
	}	
	
	public void resetIterators() {
		this.godsIterator = this.gods.listIterator();
		this.humansIterator = this.humans.listIterator();
		this.animalsIterator = this.animals.listIterator();
		this.magicsIterator = this.magics.listIterator();
//		this.positionsIterator = this.positions.listIterator();
		this.itemsIterator = this.items.listIterator();
//		this.inventorysIterator = this.inventories.listIterator();

	}
	
}
