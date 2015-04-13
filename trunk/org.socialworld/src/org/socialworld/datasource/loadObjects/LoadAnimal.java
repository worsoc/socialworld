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

import org.socialworld.objects.Animal;
import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.WriteAccessToAnimal;

public class LoadAnimal extends LoadSimulationObjects  {

	private static LoadAnimal instance;
	
	protected LoadAnimal() {
	}

	/**
	 * The method gets back the only instance of the LoadAnimal.
	 * 
	 * @return singleton object of LoadHuman
	 */
	public static LoadAnimal getInstance() {
		if (instance == null) {
			instance = new LoadAnimal();
			
		}
		return instance;
	}
	


	@Override
	public  Animal getObject(int objectID) {
		load(objectID);

		StateAnimal state = new StateAnimal();
		initState(state);
		
		Animal createdAnimal = new Animal(objectID, state);
		WriteAccessToAnimal animal = new WriteAccessToAnimal(createdAnimal, state);
		
		initObject(animal);	
		
		return createdAnimal;
	}

	
	protected void initObject(WriteAccessToAnimal object) {
	
		super.initObject(object);

	}


	protected void initState(StateAnimal state) {

		super.initState(state);		

	}


	
	
}
