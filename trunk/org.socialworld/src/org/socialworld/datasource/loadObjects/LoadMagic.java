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
import org.socialworld.objects.Magic;
import org.socialworld.objects.StateMagic;
import org.socialworld.objects.WriteAccessToMagic;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenMagic;
import org.socialworld.objects.concrete.spells.Lightning;

public class LoadMagic extends LoadSimulationObjects {

	private static LoadMagic instance;
	private static boolean hasBeenCreatedYet = false;

	protected LoadMagic(SimulationObjectArray allObjects) {
		
		super(allObjects);
		
	}

	/**
	 * The method returns the only instance of the LoadMagic class to first caller.
	 * The method returns null to all further callers. 
	 * 
	 */
	public static LoadMagic getExlusiveInstance(SimulationObjectArray allObjects) {
		if (hasBeenCreatedYet == false) {
			instance = new LoadMagic(allObjects);
			hasBeenCreatedYet = true;
			return instance;
		}
		else return null;
	}


	@Override
	public void createObject(int objectID) {
		Magic createdMagic = new Lightning(objectID);
		allObjects.set(objectID, createdMagic);
	}

	@Override
	public void loadObject(int objectID) {
		Magic createdMagic;
		WriteAccessToMagic wa;
		GrantedAccessToProperty propertiesToInit[];
		HiddenMagic hiddenMagic = null;
		
		createdMagic = (Magic) allObjects.get(objectID);

		load(objectID);
	
		StateMagic state = new StateMagic();
		
		wa = new WriteAccessToMagic(createdMagic, state);
		propertiesToInit = new GrantedAccessToProperty[1];
		propertiesToInit[0] = GrantedAccessToProperty.all;
		hiddenMagic = wa.getMeHidden(propertiesToInit);

		initState(hiddenMagic,  objectID);
		initObject(hiddenMagic,  objectID);	
		
	}



}
