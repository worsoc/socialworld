/**
 * 
 */
package org.socialworld.core;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.socialworld.ListModel;
import org.socialworld.objects.*;
import org.socialworld.datasource.*;

/**
 * @author Mathias Sikos (tyloesand)
 *
 */
public class ObjectMaster {

	private static final Logger logger = Logger.getLogger(ObjectMaster.class);

	private final List<God> gods;
	private final List<Human> humans;
	private final List<Item> items;
	private final List<Magic> magics;
	private final List<Animal> animals;
	
	private final List<SimulationObject> simulationObjects;

	private final LoadHuman humanCreator;
	
	private long maxObjectID = 0;
	
	private  ListIterator<God> godsIterator;
	private  ListIterator<Human> humansIterator;
	private  ListIterator<Item> itemsIterator;
//	private  ListIterator<Magic> magicsIterator;
	private  ListIterator<Animal> animalsIterator;
	
	private  ListIterator<SimulationObject> objectsIterator;
	
	public ObjectMaster() {
		logger.debug("create ObjectMaster");

		this.humanCreator = LoadHuman.getInstance();
		
		this.simulationObjects = new ListModel<SimulationObject>();
		
		this.gods = new ArrayList<God>();
		this.humans = new ListModel<Human>();
		this.animals = new ListModel<Animal>();
		this.magics = new ArrayList<Magic>();
		this.items = new ArrayList<Item>();
		
		resetIterators();
	}	
	
	public SimulationObject createSimulationObject(
			SimulationObjectType simulationObjectType, long objectID) {
		SimulationObject object;
		
		if (objectID == 0) {
			maxObjectID = maxObjectID + 1;
			objectID = maxObjectID;
		}
		// TODO (tyloesand) weitere Objekttypen hinzufügen
		switch (simulationObjectType) {
		case animal:
			object = new Animal();
			this.animals.add((Animal)object);
			break;
		case human:
			object = this.humanCreator.getObject(objectID);
			this.humans.add((Human)object);
			break;
		case god:
			object = new God();
			this.gods.add((God)object);
			break;
		case item:
			object = new Item();
			this.items.add((Item)object);
			break;
		default:
			object = null;
		}
		if (object != null) this.simulationObjects.add(object);
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
		default:
			return this.objectsIterator.hasNext();
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
		default:
			return this.objectsIterator.next();
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
//		this.magicsIterator = this.magics.listIterator();
//		this.positionsIterator = this.positions.listIterator();
		this.itemsIterator = this.items.listIterator();
//		this.inventorysIterator = this.inventories.listIterator();

	}
	
}
