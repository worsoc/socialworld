/**
 * 
 */
package org.socialworld.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.socialworld.ListModel;
import org.socialworld.attributes.Inventory;
import org.socialworld.attributes.Position;
import org.socialworld.objects.*;

/**
 * The class Simulation holds all simulation objects and runs the simulation
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Simulation {
	// TODO (tyloesand) hier ist noch viel zu tun:
	// - ueberdenken, welche Listen
	// - ueberdenken Start EventMaster
	
	private static final Logger logger = Logger.getLogger(Simulation.class);

	private final EventMaster eventMaster;
	private final List<God> gods;
	private final List<Human> humans;
	private final List<Item> items;
	private final List<Inventory> inventories;
	private final List<Position> positions;
	private final List<Magic> magics;
	private final List<Animal> animals;
	
	private final List<SimulationObject> simulationObjects;

	public Simulation() {
		logger.debug("create simulation object");
		this.eventMaster = new EventMaster();

		this.simulationObjects = new ListModel<SimulationObject>();
		
		this.gods = new ArrayList<God>();
		this.humans = new ListModel<Human>();
		this.animals = new ListModel<Animal>();

		this.magics = new ArrayList<Magic>();
		
		this.positions = new ArrayList<Position>();
		this.items = new ArrayList<Item>();
		this.inventories = new ArrayList<Inventory>();
	}

	public void startSimulation() {
		this.eventMaster.start();
	}
	
	public void stopSimulation() {
		this.eventMaster.stopEventMaster();
	}

	public EventMaster getEventMaster() {
		return this.eventMaster;
	}

	
	public SimulationObject createSimulationObject(
			SimulationObjectType simulationObjectType) {
		SimulationObject object;
		// TODO (tyloesand) weitere Objekttypen hinzufügen
		switch (simulationObjectType) {
		case animal:
			object = new Animal();
			this.animals.add((Animal)object);
			break;
		case human:
			object = new Human();
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
	/**
	 * The method returns a list of gods
	 * 
	 * @return
	 */
	public List<God> getGods() {
		return this.gods;
	}

	/**
	 * The method returns a list of humans
	 * 
	 * @return
	 */
	public List<Human> getHumans() {
		return this.humans;
	}

	/**
	 * The method returns a list of items
	 * 
	 * @return
	 */
	public List<Item> getItems() {
		return this.items;
	}

	/**
	 * The method returns a list of inventories
	 * 
	 * @return
	 */
	public List<Inventory> getInventories() {
		return this.inventories;
	}

	/**
	 * The method returns a list of positions
	 * 
	 * @return
	 */
	public List<Position> getPositions() {
		return this.positions;
	}

	/**
	 * The method returns a list of magics
	 * 
	 * @return
	 */
	public List<Magic> getMagics() {
		return this.magics;
	}
}
