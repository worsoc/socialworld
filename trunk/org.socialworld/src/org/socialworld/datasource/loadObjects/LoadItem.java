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
import org.socialworld.objects.StateItem;
import org.socialworld.objects.WriteAccessToItem;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenItem;
import org.socialworld.objects.concrete.eatable.fruits.Apple;

public class LoadItem extends LoadSimulationObjects {

	private static LoadItem instance;
	private static boolean hasBeenCreatedYet = false;

	protected LoadItem(SimulationObjectArray allObjects) {
		
		super(allObjects);
		
	}

	/**
	 * The method returns the only instance of the LoadItem class to first caller.
	 * The method returns null to all further callers. 
	 * 
	 */
	public static LoadItem getExlusiveInstance(SimulationObjectArray allObjects) {
		if (hasBeenCreatedYet == false) {
			instance = new LoadItem(allObjects);
			hasBeenCreatedYet = true;
			return instance;
		}
		else return null;
	}


	@Override
	public void createObject(int objectID) {
		Item createdItem = new Apple(objectID);
		allObjects.set(objectID, createdItem);
	}

	@Override
	public void loadObject(int objectID) {
		Item createdItem;
		WriteAccessToItem wa;
		GrantedAccessToProperty propertiesToInit[];
		HiddenItem hiddenItem = null;
		
		createdItem = (Item) allObjects.get(objectID);

		load(objectID);
	
		StateItem state = new StateItem();

		wa = new WriteAccessToItem(createdItem, state);
		propertiesToInit = new GrantedAccessToProperty[1];
		propertiesToInit[0] = GrantedAccessToProperty.all;
		hiddenItem = wa.getMeHidden(propertiesToInit);
		
		initState(hiddenItem,  objectID);
		initObject(hiddenItem,  objectID);	
		
	}


}
