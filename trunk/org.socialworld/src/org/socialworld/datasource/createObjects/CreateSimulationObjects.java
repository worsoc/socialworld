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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.socialworld.datasource.pool.GaussPoolInfluenceType;
import org.socialworld.datasource.pool.GaussPoolPosition;
import org.socialworld.datasource.pool.GaussPoolReactionType;
import org.socialworld.datasource.pool.GaussPoolState2ActionType;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.access.HiddenSimulationObject;

/**
 * Its the basic class for creating simulation objects.
 * for inherited classes of class SimulationObject
 *  there exists an inherited class of CreateSimulationObjects.
 *  
 * @author Mathias Sikos (tyloesand) 
 */
public abstract class CreateSimulationObjects {

	protected Random random;

	private List<Integer> usedPositionIndex;
	
	CreateSimulationObjects() {
		random = new Random();
		usedPositionIndex = new ArrayList<Integer>();
	}

	public abstract SimulationObject getObject(int objectID) ;
	
	protected int mapGaussToIndex(double gaussValue, int arrayCapacity) {
		double factor;
		int result;
		factor = arrayCapacity / 4;
		result = (int) (gaussValue * factor);
		return result;
	}
	

	protected void initObject(HiddenSimulationObject hiddenObject) {
	
	}
	

	protected void initState(HiddenSimulationObject hiddenObject) {
		int indexPosition;

		int indexGPIT; 
		int indexGPRT; 
		int indexGPS2A; 
		
		double gauss_value;
		
	
		gauss_value = random.nextGaussian();
		indexGPIT = mapGaussToIndex(gauss_value, GaussPoolInfluenceType.CAPACITY_GPIT_ARRAY);
		hiddenObject.setInfluenceTypes(GaussPoolInfluenceType.getInstance().getInfluenceTypes(indexGPIT));

		gauss_value = random.nextGaussian();
		indexGPRT = mapGaussToIndex(gauss_value, GaussPoolReactionType.CAPACITY_GPRT_ARRAY);
		hiddenObject.setReactionTypes(GaussPoolReactionType.getInstance().getReactionTypes(indexGPRT));

		gauss_value = random.nextGaussian();
		indexGPS2A = mapGaussToIndex(gauss_value, GaussPoolState2ActionType.CAPACITY_GPS2A_ARRAY);
		hiddenObject.setState2ActionType(GaussPoolState2ActionType.getInstance().getState2ActionType(indexGPS2A));
		
		do {
			indexPosition = random.nextInt(GaussPoolPosition.CAPACITY_GPPos_ARRAY);
			if (random.nextBoolean() == false) indexPosition = indexPosition * -1;
		} while (checkArrayContainsValue(usedPositionIndex, indexPosition));
		usedPositionIndex.add(indexPosition);
		hiddenObject.setPosition(GaussPoolPosition.getInstance().getPosition(indexPosition));

	}
	
	private boolean checkArrayContainsValue(List<Integer> array, int value) {
		
		int size = array.size();
		
		for (int index = 0; index < size; index++) {
			if (array.get(index) == value) return true;
		}
		
		return false;
	}
}
