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

import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.Vector;
import org.socialworld.collections.SimulationObjectArray;
import org.socialworld.datasource.tablesSimulation.TableAnimalsDirections;
import org.socialworld.datasource.tablesSimulation.TableState;
import org.socialworld.objects.Animal;
import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.WriteAccessToAnimal;
import org.socialworld.objects.access.HiddenAnimal;

public class LoadAnimal extends LoadSimulationObjects  {

	private static LoadAnimal instance;
	
	TableState tableAttributes;

	TableAnimalsDirections tableDirections;
	int rowTableDirections;
	
	protected LoadAnimal(SimulationObjectArray allObjects) {
		
		super(allObjects);
		
		tableAttributes = new TableState();
		tableDirections = new TableAnimalsDirections();
	}

	/**
	 * The method creates the only instance of the LoadAnimal.
	 * 
	 */
	public static LoadAnimal createInstance(SimulationObjectArray allObjects) {
		if (instance == null) {
			instance = new LoadAnimal(allObjects);
		}
		return instance;
	}
	
	
	protected void load(int objectID) {
		super.load(objectID);
		
		tableAttributes.select(tableAttributes.SELECT_ALL_COLUMNS, " WHERE id = " + objectID + " AND array_nr = 1 " , " ORDER BY attrib_nr");
		tableDirections.select(tableDirections.SELECT_ALL_COLUMNS, " WHERE id = " + objectID , "");
		
		rowTableDirections = tableDirections.getIndexFor1PK(objectID);

	}

	public void createObject(int objectID) {
		Animal createdAnimal = new Animal(objectID);
		allObjects.set(objectID, createdAnimal);
	}
	
	@Override
	public  void loadObject(int objectID) {
		Animal createdAnimal;
		HiddenAnimal hiddenAnimal = null;
		
		createdAnimal = (Animal) allObjects.get(objectID);

		load(objectID);

		StateAnimal state = new StateAnimal();

		// the constructor "returns" the hidden animal object
		new WriteAccessToAnimal(createdAnimal, state, hiddenAnimal);
		
		initState(hiddenAnimal, objectID);
		initObject(hiddenAnimal,  objectID);	
		
	}

	
	protected void initObject(HiddenAnimal hiddenAnimal, int objectID) {
	
		super.initObject(hiddenAnimal,  objectID);



	}


	protected void initState(HiddenAnimal hiddenAnimal, int objectID) {

		super.initState(hiddenAnimal, objectID);	
			
		
		int attribs[];
		
		attribs = tableAttributes.getAttributes();
		hiddenAnimal.setAttributes(new AttributeArray(attribs));

		
		if (rowTableDirections >= 0) {
			hiddenAnimal.setDirectionChest(new Vector(tableDirections.getChestDirection(rowTableDirections)));
			hiddenAnimal.setDirectionView(new Vector(tableDirections.getViewDirection(rowTableDirections)));
			hiddenAnimal.setDirectionActiveMove(new Vector(tableDirections.getMoveDirection(rowTableDirections)));
		}
	}


	
	
}
