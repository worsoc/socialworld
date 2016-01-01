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
import org.socialworld.objects.God;
import org.socialworld.objects.StateGod;
import org.socialworld.objects.WriteAccessToGod;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenGod;

public class LoadGod extends LoadSimulationObjects {

	private static LoadGod instance;
	private static boolean hasBeenCreatedYet = false;

	protected LoadGod(SimulationObjectArray allObjects) {
		
		super(allObjects);
		
	}

	/**
	 * The method returns the only instance of the LoadGod class to first caller.
	 * The method returns null to all further callers. 
	 * 
	 */
	public static LoadGod getExlusiveInstance(SimulationObjectArray allObjects) {
		if (hasBeenCreatedYet == false) {
			instance = new LoadGod(allObjects);
			hasBeenCreatedYet = true;
			return instance;
		}
		else return null;
	}


	@Override
	public void createObject(int objectID) {
		God createdGod = new God(objectID);
		allObjects.set(objectID, createdGod);
	}

	@Override
	public void loadObject(int objectID) {
		God createdGod;
		WriteAccessToGod wa;
		GrantedAccessToProperty propertiesToInit[];
		HiddenGod hiddenGod = null;
		
		createdGod = (God) allObjects.get(objectID);

		load(objectID);
	
		StateGod state = new StateGod();

		wa = new WriteAccessToGod(createdGod, state);
		propertiesToInit = new GrantedAccessToProperty[1];
		propertiesToInit[0] = GrantedAccessToProperty.all;
		hiddenGod = wa.getMeHidden(propertiesToInit);
				
		initState(hiddenGod,  objectID);
		initObject(hiddenGod,  objectID);	
		
	}


}
