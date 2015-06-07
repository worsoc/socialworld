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

import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.socialworld.objects.*;
import org.socialworld.propertyChange.ListenedList;
import org.socialworld.datasource.createObjects.CreateAnimal;
import org.socialworld.datasource.createObjects.CreateGod;
import org.socialworld.datasource.createObjects.CreateHuman;
import org.socialworld.datasource.createObjects.CreateItem;
import org.socialworld.datasource.createObjects.CreateMagic;
import org.socialworld.datasource.loadObjects.LoadAnimal;
import org.socialworld.datasource.loadObjects.LoadGod;
import org.socialworld.datasource.loadObjects.LoadHuman;
import org.socialworld.datasource.loadObjects.LoadItem;
import org.socialworld.datasource.loadObjects.LoadMagic;

import org.socialworld.collections.SimulationObjectArray;

/**
 * @author Mathias Sikos (tyloesand)
 *
 */
public class ObjectMaster {

	private static final Logger logger = Logger.getLogger(ObjectMaster.class);
	private static final int SIMULATION_OBJECTS_START_CAPACITY = 1000;
	
	private static ObjectMaster instance;
	
	private final ListenedList<God> gods;
	private final ListenedList<Human> humans;
	private final ListenedList<Item> items;
	private final ListenedList<Magic> magics;
	private final ListenedList<Animal> animals;
	
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
			SimulationObject_Type simulationObjectType) {
		SimulationObject object;
		int objectID;
		
		maxObjectID = maxObjectID + 1;
		objectID =  maxObjectID;
		
		// TODO (tyloesand) weitere Objekttypen hinzufügen
		switch (simulationObjectType) {
		case animal:
			object = CreateAnimal.getInstance().getObject(objectID);
			this.animals.add((Animal)object);
			animalsIterator = animals.listIterator(animalsIterator.nextIndex());
			break;
		case human:
			object = CreateHuman.getInstance().getObject(objectID);
			this.humans.add((Human)object);
			humansIterator = humans.listIterator(humansIterator.nextIndex());
			break;
		case god:
			object = CreateGod.getInstance().getObject(objectID);
			this.gods.add((God)object);
			godsIterator = gods.listIterator(godsIterator.nextIndex());
			break;
		case item:
			object = CreateItem.getInstance().getObject(objectID);
			this.items.add((Item)object);
			itemsIterator = items.listIterator(itemsIterator.nextIndex());
			break;
		case magic:
			object = CreateMagic.getInstance().getObject(objectID);
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
	
	public boolean hasNext(SimulationObject_Type simulationObjectType) {

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
	
	public SimulationObject next(SimulationObject_Type simulationObjectType) {
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
	public ListenedList<God> getGods() {
		return this.gods;
	}

	/**
	 * The method returns the list of humans
	 * 
	 * @return
	 */
	public ListenedList<Human> getHumans() {
		return this.humans;
	}

	/**
	 * The method returns the list of items
	 * 
	 * @return
	 */
	public ListenedList<Item> getItems() {
		return this.items;
	}


	/**
	 * The method returns a list of magics
	 * 
	 * @return
	 */
	public ListenedList<Magic> getMagics() {
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
