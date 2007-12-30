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
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Simulation {

	private God god;
	private EventMaster eventMaster;
	private List<Human> humans;
	private List<Item> items;
	private List<Inventory> inventories;
	private List<Position> positions;
	private List<Magic> magics;

	public Simulation() {
		god = new God();
		
		humans = new ArrayList<Human>();
		positions = new ArrayList<Position>();
		items = new ArrayList<Item>();
		inventories = new ArrayList<Inventory>();

		eventMaster = new EventMaster();
	}

	
	public void startSimulation() {

	}


	public God getGod() {
		return god;
	}


	public EventMaster getEventMaster() {
		return eventMaster;
	}


	public List<Human> getHumans() {
		return humans;
	}


	public List<Item> getItems() {
		return items;
	}


	public List<Inventory> getInventories() {
		return inventories;
	}


	public List<Position> getPositions() {
		return positions;
	}


	public List<Magic> getMagics() {
		return magics;
	}
}
