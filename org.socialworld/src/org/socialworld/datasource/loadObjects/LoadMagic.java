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
import org.socialworld.objects.StateSimulationObject;
import org.socialworld.objects.WriteAccessToSimulationObject;

public class LoadMagic extends LoadSimulationObjects {

	private static LoadMagic instance;

	protected LoadMagic(SimulationObjectArray allObjects) {
		
		super(allObjects);
		
	}

	/**
	 * The method creates the only instance of the LoadAnimal.
	 * 
	 */
	public static LoadMagic createInstance(SimulationObjectArray allObjects) {
		if (instance == null) {
			instance = new LoadMagic(allObjects);
			
		}
		return instance;
	}

	@Override
	public void createObject(int objectID) {
		Magic createdMagic = new Magic(objectID);
		allObjects.set(objectID, createdMagic);
	}

	@Override
	public void loadObject(int objectID) {
		Magic createdMagic;
		createdMagic = (Magic) allObjects.get(objectID);

		load(objectID);
	
		// TODO
		StateSimulationObject state = new StateSimulationObject();
		
		WriteAccessToSimulationObject magic = new WriteAccessToSimulationObject(createdMagic, state);
		initState(magic,  objectID);
		
		initObject(magic,  objectID);	
		
	}



}
