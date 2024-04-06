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

import org.socialworld.SimpleClientActionHandler;
import org.socialworld.actions.handle.Inventory;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.core.IncompleteSimulationObject;
import org.socialworld.datasource.pool.GaussPoolAttributeArray;
import org.socialworld.datasource.pool.GaussPoolAttributeCalculatorMatrix;
import org.socialworld.datasource.pool.GaussPoolInfluenceType;
import org.socialworld.datasource.pool.GaussPoolPerceptionType;
import org.socialworld.datasource.pool.GaussPoolPosition;
import org.socialworld.datasource.pool.GaussPoolReactionType;
import org.socialworld.datasource.pool.GaussPoolState2ActionType;
import org.socialworld.objects.Human;
import org.socialworld.objects.StateHuman;
import org.socialworld.objects.WriteAccessToHuman;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenHuman;

/**
 * @author Mathias Sikos
 *
 */
public class CreateHuman extends CreateAnimal {

	private static CreateHuman instance;
	private static boolean hasBeenCreatedYet = false;
	
	private static int positionCounter = 0;
	
	/**
	 * The method returns the only instance of the CreateHuman class to first caller.
	 * The method returns null to all further callers. 
	 * 
	 */
	public static CreateHuman getExlusiveInstance() {
		if (hasBeenCreatedYet == false) {
			instance = new CreateHuman();
			hasBeenCreatedYet = true;
			return instance;
		}
		else return null;
	}

	@Override
	public IncompleteSimulationObject getObject(int objectID,  String fullClassName) {
		WriteAccessToHuman wa;
		GrantedAccessToProperty propertiesToInit[];
		HiddenHuman hiddenHuman = null;

		Object createdObject = createObjectForName(fullClassName);
		if (createdObject == null) return new IncompleteSimulationObject();
		
		Human createdHuman = (Human) createdObject;
		createdHuman.setObjectID(objectID);
		StateHuman state = new StateHuman();
		wa = new WriteAccessToHuman(createdHuman, state);
		
		propertiesToInit = new GrantedAccessToProperty[1];
		propertiesToInit[0] = GrantedAccessToProperty.all;
		hiddenHuman = wa.getMeHidden(propertiesToInit);

		initState(hiddenHuman);
		initObject(hiddenHuman);	

		SimpleClientActionHandler.getInstance().setHumanWrite(objectID, hiddenHuman);

		return new IncompleteSimulationObject(createdHuman, hiddenHuman);
	}

	// overrides for testing 3 human
	// TEMP_SOLUTION
	// holds the parent class implementations --> clear the parent implementations
	protected void initState(HiddenHuman hiddenHuman) {

		int indexPosition;

		int indexGPIT; 
		int indexGPRT; 
		int indexGPS2A; 
		int indexGPPT; 
		
		double gauss_value;
		
	
		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE))
			gauss_value = random.nextGaussian();
		indexGPIT = mapGaussToIndex(gauss_value, GaussPoolInfluenceType.CAPACITY_GPIT_ARRAY);
		hiddenHuman.setInfluenceTypes(GaussPoolInfluenceType.getInstance().getInfluenceTypes(indexGPIT));

		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE))
			gauss_value = random.nextGaussian();
		indexGPRT = mapGaussToIndex(gauss_value, GaussPoolReactionType.CAPACITY_GPRT_ARRAY);
		hiddenHuman.setReactionTypes(GaussPoolReactionType.getInstance().getReactionTypes(indexGPRT));

		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE)) 
			gauss_value = random.nextGaussian();
		indexGPS2A = mapGaussToIndex(gauss_value, GaussPoolState2ActionType.CAPACITY_GPS2A_ARRAY);
		hiddenHuman.setState2ActionType(GaussPoolState2ActionType.getInstance().getState2ActionType(indexGPS2A));

		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE)) 
			gauss_value = random.nextGaussian();
		indexGPPT = mapGaussToIndex(gauss_value, GaussPoolPerceptionType.CAPACITY_GPPT_ARRAY);
		hiddenHuman.setPerceptionTypes(GaussPoolPerceptionType.getInstance().getPerceptionTypes(indexGPPT));

/*		
		do {
			indexPosition = random.nextInt(GaussPoolPosition.CAPACITY_GPPos_ARRAY);
			if (random.nextBoolean() == false) indexPosition = indexPosition * -1;
		} while (checkArrayContainsValue(usedPositionIndex, indexPosition));
		usedPositionIndex.add(indexPosition);
		Position position = GaussPoolPosition.getInstance().getPosition(indexPosition);
		position.setPropertyName(PropertyName.simobj_position);
		hiddenHuman.setPosition(position);
*/
		if (positionCounter < 3) {
			Position position = GaussPoolPosition.getInstance().getPosition(positionCounter);
			position.setPropertyName(PropertyName.simobj_position);
			hiddenHuman.setPosition(position);
		}
		positionCounter++;
		
		int indexGPAA;
		int indexGPACM;
	
		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE))
			gauss_value = random.nextGaussian();
		indexGPAA = mapGaussToIndex(gauss_value, GaussPoolAttributeArray.CAPACITY_GPAA_ARRAY);
		hiddenHuman.setAttributes(
				GaussPoolAttributeArray.getInstance().getArrayAsValue(indexGPAA));
		
		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE))
			gauss_value = random.nextGaussian();
		indexGPACM = mapGaussToIndex(gauss_value, GaussPoolAttributeCalculatorMatrix.CAPACITY_GPACM_ARRAY);
		hiddenHuman.setMatrix(	
				GaussPoolAttributeCalculatorMatrix.getInstance().getMatrix(indexGPACM));

		hiddenHuman.setInventory(new Inventory(hiddenHuman.getSimObjectType()));
		
	}

	
}
