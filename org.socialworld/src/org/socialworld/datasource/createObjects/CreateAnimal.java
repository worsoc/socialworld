/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.datasource.createObjects;

import org.socialworld.datasource.pool.GaussPoolAttributeArray;
import org.socialworld.datasource.pool.GaussPoolAttributeCalculatorMatrix;
import org.socialworld.objects.Animal;
import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.WriteAccessToAnimal;

/**
 * @author Mathias Sikos
 *
 */
public class CreateAnimal extends CreateSimulationObjects {

	private static CreateAnimal instance;
	
	public static CreateAnimal getInstance() {
		if (instance == null)			instance = new CreateAnimal();
		return instance;
	}
		
	@Override
	public Animal getObject(int objectID) {
		StateAnimal state = new StateAnimal();
		
		Animal createdAnimal = new Animal(objectID);

		WriteAccessToAnimal animal = new WriteAccessToAnimal(createdAnimal, state);

		initState(animal);
		initObject(animal, objectID);	
		
		return createdAnimal;
	}

	protected void initObject(WriteAccessToAnimal waAnimal, int objectID) {
		
		super.initObject(waAnimal, objectID);

	}

	protected void initState(WriteAccessToAnimal waAnimal) {

		super.initState(waAnimal);		

		int indexGPAA;
		int indexGPACM;
		double gauss_value;

		gauss_value = random.nextGaussian();
		indexGPAA = mapGaussToIndex(gauss_value, GaussPoolAttributeArray.CAPACITY_GPAA_ARRAY);
		waAnimal.setAttributes(
				GaussPoolAttributeArray.getInstance().getArray(indexGPAA), this);
		
		gauss_value = random.nextGaussian();
		indexGPACM = mapGaussToIndex(gauss_value, GaussPoolAttributeCalculatorMatrix.CAPACITY_GPACM_ARRAY);

		waAnimal.setMatrix(	
				GaussPoolAttributeCalculatorMatrix.getInstance().getMatrix(indexGPACM), this);

		
	}

}