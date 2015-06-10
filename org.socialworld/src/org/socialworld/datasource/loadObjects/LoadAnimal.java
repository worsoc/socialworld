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

public class LoadAnimal extends LoadSimulationObjects  {

	private static LoadAnimal instance;
	
	TableState tableAttributes;
	int rowTableAttributes0To7;	
	int rowTableAttributes8To15;	

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
		
		tableAttributes.select(tableAttributes.SELECT_ALL_COLUMNS, " WHERE id = " + objectID , "");
		tableDirections.select(tableDirections.SELECT_ALL_COLUMNS, " WHERE id = " + objectID , "");
		
		rowTableAttributes0To7 = tableAttributes.getIndexFor2PK(objectID, 1);
		rowTableAttributes8To15 = tableAttributes.getIndexFor2PK(objectID, 2);
		rowTableDirections = tableDirections.getIndexFor1PK(objectID);

	}

	public void createObject(int objectID) {
		Animal createdAnimal = new Animal(objectID);
		allObjects.set(objectID, createdAnimal);
	}
	
	@Override
	public  void loadObject(int objectID) {
		Animal createdAnimal;
		createdAnimal = (Animal) allObjects.get(objectID);

		load(objectID);

		StateAnimal state = new StateAnimal();
		initState(state, objectID);
		
		WriteAccessToAnimal animal = new WriteAccessToAnimal(createdAnimal, state);
		initObject(animal,  objectID);	
		
	}

	
	protected void initObject(WriteAccessToAnimal object, int objectID) {
	
		super.initObject(object,  objectID);



	}


	protected void initState(StateAnimal state, int objectID) {

		super.initState(state, objectID);	
		
		int attrib0to7[];
		int attrib8to15[];
		
		int attribs[];
		attribs = new int[9];
		
		
		
		if (rowTableAttributes0To7 >= 0) {
			attrib0to7 = tableAttributes.getAttributes(rowTableAttributes0To7);
			attribs[0] = attrib0to7[0];
			attribs[1] = attrib0to7[1];
			attribs[2] = attrib0to7[2];
			attribs[3] = attrib0to7[3];
			attribs[4] = attrib0to7[4];
			attribs[5] = attrib0to7[5];
			attribs[6] = attrib0to7[6];
			attribs[7] = attrib0to7[7];
		}	

		if (rowTableAttributes8To15 >= 0) {
			attrib8to15 = tableAttributes.getAttributes(rowTableAttributes8To15);
			attribs[8] = attrib8to15[0];
		}
		
			
		state.setAttributes(new AttributeArray(attribs));

		
		if (rowTableDirections >= 0) {
			state.setDirectionChest(new Vector(tableDirections.getChestDirection(rowTableDirections)));
			state.setDirectionView(new Vector(tableDirections.getViewDirection(rowTableDirections)));
			state.setDirectionMove(new Vector(tableDirections.getMoveDirection(rowTableDirections)));
		}
	}


	
	
}
