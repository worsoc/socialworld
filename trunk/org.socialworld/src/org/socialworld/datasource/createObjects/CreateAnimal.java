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

import org.socialworld.actions.handle.Inventory;
import org.socialworld.core.IncompleteSimulationObject;
import org.socialworld.datasource.pool.GaussPoolAttributeArray;
import org.socialworld.datasource.pool.GaussPoolAttributeCalculatorMatrix;
import org.socialworld.objects.Animal;
import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.WriteAccessToAnimal;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenAnimal;

/**
 * @author Mathias Sikos
 *
 */
public class CreateAnimal extends CreateSimulationObjects {

	private static CreateAnimal instance;
	private static boolean hasBeenCreatedYet = false;
	
	/**
	 * The method returns the only instance of the CreateAnimal class to first caller.
	 * The method returns null to all further callers. 
	 * 
	 */
	public static CreateAnimal getExlusiveInstance() {
		if (hasBeenCreatedYet == false) {
			instance = new CreateAnimal();
			hasBeenCreatedYet = true;
			return instance;
		}
		else return null;
	}
	

		
	@Override
	public IncompleteSimulationObject getObject(int objectID,  String fullClassName) {
		
		WriteAccessToAnimal wa;
		GrantedAccessToProperty propertiesToInit[];
		HiddenAnimal hiddenAnimal = null;
		
		Object createdObject = createObjectForName(fullClassName);
		if (createdObject == null) return null;

		Animal createdAnimal = (Animal) createdObject;
		createdAnimal.setObjectID(objectID);
		StateAnimal state = new StateAnimal();
		wa = new WriteAccessToAnimal(createdAnimal, state);

		propertiesToInit = new GrantedAccessToProperty[1];
		propertiesToInit[0] = GrantedAccessToProperty.all;
		hiddenAnimal = wa.getMeHidden(propertiesToInit);

		initState(hiddenAnimal);
		initObject(hiddenAnimal);	
		
		return new IncompleteSimulationObject(createdAnimal, hiddenAnimal);
	}

	protected void initObject(HiddenAnimal hiddenAnimal) {
		
		super.initObject(hiddenAnimal);
		

	}

	
	protected void initState(HiddenAnimal hiddenAnimal) {

		super.initState(hiddenAnimal);		

		int indexGPAA;
		int indexGPACM;
		double gauss_value;

		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE))
			gauss_value = random.nextGaussian();
		indexGPAA = mapGaussToIndex(gauss_value, GaussPoolAttributeArray.CAPACITY_GPAA_ARRAY);
		hiddenAnimal.setAttributes(
				GaussPoolAttributeArray.getInstance().getArrayAsValue(indexGPAA));
		
		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE))
			gauss_value = random.nextGaussian();
		indexGPACM = mapGaussToIndex(gauss_value, GaussPoolAttributeCalculatorMatrix.CAPACITY_GPACM_ARRAY);
		hiddenAnimal.setMatrix(	
				GaussPoolAttributeCalculatorMatrix.getInstance().getMatrix(indexGPACM));

		hiddenAnimal.setInventory(new Inventory(hiddenAnimal.getSimObjectType()));
		
	}

}
