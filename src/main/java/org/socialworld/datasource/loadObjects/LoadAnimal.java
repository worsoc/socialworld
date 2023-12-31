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
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.collections.SimulationObjectArray;
import org.socialworld.datasource.tablesSimulation.TableAnimalsDirections;
import org.socialworld.datasource.tablesSimulation.TableState;
import org.socialworld.objects.Animal;
import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.WriteAccessToAnimal;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenAnimal;

public class LoadAnimal extends LoadSimulationObjects  {

	private static LoadAnimal instance;
	private static boolean hasBeenCreatedYet = false;
	
	TableState tableAttributes;

	TableAnimalsDirections tableDirections;
	int rowTableDirections;
	
	protected LoadAnimal(SimulationObjectArray allObjects) {
		
		super(allObjects);
		
		tableAttributes = new TableState();
		tableDirections = new TableAnimalsDirections();
	}

	
	/**
	 * The method returns the only instance of the LoadAnimal class to first caller.
	 * The method returns null to all further callers. 
	 * 
	 */
	public static LoadAnimal getExlusiveInstance(SimulationObjectArray allObjects) {
		if (hasBeenCreatedYet == false) {
			instance = new LoadAnimal(allObjects);
			hasBeenCreatedYet = true;
			return instance;
		}
		else return null;
	}
	
	
	protected void load(int objectID) {
		super.load(objectID);
		
		tableAttributes.select(tableAttributes.SELECT_ALL_COLUMNS, " WHERE id = " + objectID + " AND array_nr = 1 " , " ORDER BY attrib_nr");
		tableDirections.select(tableDirections.SELECT_ALL_COLUMNS, " WHERE id = " + objectID , "");
		
		rowTableDirections = tableDirections.getIndexFor1PK(objectID);

	}

	public void createObject(int objectID,  String fullClassName) {
		Object createdObject = createObjectForName(fullClassName);
		if (createdObject == null) return;

		Animal createdAnimal = (Animal) createdObject;
		createdAnimal.setObjectID(objectID);
		allObjects.set(objectID, createdAnimal);
	}
	
	@Override
	public  void loadObject(int objectID) {
		Animal createdAnimal;
		WriteAccessToAnimal wa;
		GrantedAccessToProperty propertiesToInit[];
		HiddenAnimal hiddenAnimal = null;
		
		createdAnimal = (Animal) allObjects.get(objectID);

		load(objectID);

		StateAnimal state = new StateAnimal();

		wa = new WriteAccessToAnimal(createdAnimal, state);
		propertiesToInit = new GrantedAccessToProperty[1];
		propertiesToInit[0] = GrantedAccessToProperty.all;
		hiddenAnimal = wa.getMeHidden(propertiesToInit);
		
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
		hiddenAnimal.setAttributes(new Value(Type.attributeArray, new AttributeArray(attribs)));

		
		if (rowTableDirections >= 0) {
			hiddenAnimal.setDirectionChest(new Vector(tableDirections.getChestDirection(rowTableDirections)));
			hiddenAnimal.setDirectionView(new Vector(tableDirections.getViewDirection(rowTableDirections)));
			hiddenAnimal.setDirectionActiveMove(new Vector(tableDirections.getMoveDirection(rowTableDirections)));
		}
	}


	
	
}
