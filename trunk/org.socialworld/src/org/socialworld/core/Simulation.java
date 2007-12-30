/**
 * 
 */
package org.socialworld.core;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.objects.God;
import org.socialworld.objects.Human;
import org.socialworld.objects.Inventory;
import org.socialworld.objects.Item;
import org.socialworld.objects.Magic;
import org.socialworld.objects.Position;

/**
 * The class Simulation holds all simulation objects and runs the simulation
 * @author Mathias Sikos (tyloesand)
 *	
 */
public class Simulation {

	private EventMaster eventMaster;
	private List<God> gods;
	private List<Human> humans;
	private List<Item> items;
	private List<Inventory> inventories;
	private List<Position> positions;
	private List<Magic> magics;

	public Simulation() {
		eventMaster = new EventMaster();

		gods = new ArrayList<God>();
		humans = new ArrayList<Human>();
		positions = new ArrayList<Position>();
		items = new ArrayList<Item>();
		inventories = new ArrayList<Inventory>();
	}
	
	public void startSimulation() {

	}


	public EventMaster getEventMaster() {
		return eventMaster;
	}

	/**
	 * The method returns a list of gods
	 * @return
	 */
	public List<God> getGods() {
			return gods;
		}

	/**
	 * The method returns a list of humans
	 * @return
	 */
	public List<Human> getHumans() {
		return humans;
	}


	/**
	 * The method returns a list of items
	 * @return
	 */
	public List<Item> getItems() {
		return items;
	}


	/**
	 * The method returns a list of inventories
	 * @return
	 */
	public List<Inventory> getInventories() {
		return inventories;
	}


	/**
	 * The method returns a list of positions
	 * @return
	 */
	public List<Position> getPositions() {
		return positions;
	}


	/**
	 * The method returns a list of magics
	 * @return
	 */
	public List<Magic> getMagics() {
		return magics;
	}
}
