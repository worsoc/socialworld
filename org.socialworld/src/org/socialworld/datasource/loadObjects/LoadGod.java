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


import org.socialworld.objects.God;
import org.socialworld.objects.StateSimulationObject;
import org.socialworld.objects.WriteAccessToSimulationObject;

public class LoadGod extends LoadSimulationObjects {

	private static LoadGod instance;

	/**
	 * The method gets back the only instance of the LoadGod.
	 * 
	 * @return singleton object of LoadHuman
	 */
	public static LoadGod getInstance() {
		if (instance == null) {
			instance = new LoadGod();
			
		}
		return instance;
	}

	@Override
	public God getObject(int objectID) {
		load(objectID);
	
		// TODO
		StateSimulationObject state = new StateSimulationObject();
		initState(state);
		
		God createdGod = new God(objectID, state);
		WriteAccessToSimulationObject god = new WriteAccessToSimulationObject(createdGod, state);

		initObject(god);	
		
		return createdGod;
	}


}
