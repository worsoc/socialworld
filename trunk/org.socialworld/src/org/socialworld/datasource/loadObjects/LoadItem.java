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
package org.socialworld.datasource.loadObjects;

import org.socialworld.collections.SimulationObjectArray;
import org.socialworld.objects.Item;
import org.socialworld.objects.StateSimulationObject;
import org.socialworld.objects.WriteAccessToSimulationObject;
import org.socialworld.objects.access.HiddenSimulationObject;

public class LoadItem extends LoadSimulationObjects {

	private static LoadItem instance;

	protected LoadItem(SimulationObjectArray allObjects) {
		
		super(allObjects);
		
	}

	/**
	 * The method creates the only instance of the LoadAnimal.
	 * 
	 */
	public static LoadItem createInstance(SimulationObjectArray allObjects) {
		if (instance == null) {
			instance = new LoadItem(allObjects);
			
		}
		return instance;
	}

	@Override
	public void createObject(int objectID) {
		Item createdItem = new Item(objectID);
		allObjects.set(objectID, createdItem);
	}

	@Override
	public void loadObject(int objectID) {
		Item createdItem;
		HiddenSimulationObject hiddenItem = null;
		
		createdItem = (Item) allObjects.get(objectID);

		load(objectID);
	
		// TODO
		StateSimulationObject state = new StateSimulationObject();

		// the constructor "returns" the hidden item object
		new WriteAccessToSimulationObject(createdItem, state, hiddenItem);
		
		initState(hiddenItem,  objectID);
		initObject(hiddenItem,  objectID);	
		
	}


}
