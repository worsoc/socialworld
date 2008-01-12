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
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Simulation {

    private final EventMaster eventMaster;
    private final List<God> gods;
    private final List<Human> humans;
    private final List<Item> items;
    private final List<Inventory> inventories;
    private final List<Position> positions;
    private List<Magic> magics;

    public Simulation() {
	this.eventMaster = new EventMaster();

	this.gods = new ArrayList<God>();
	this.humans = new ArrayList<Human>();
	this.positions = new ArrayList<Position>();
	this.items = new ArrayList<Item>();
	this.inventories = new ArrayList<Inventory>();
    }

    public void startSimulation() {
	// TODO Muss noch implementiert werden
    }

    public EventMaster getEventMaster() {
	return this.eventMaster;
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
