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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.socialworld.core.IncompleteSimulationObject;
import org.socialworld.datasource.pool.GaussPoolInfluenceType;
import org.socialworld.datasource.pool.GaussPoolPerceptionType;
import org.socialworld.datasource.pool.GaussPoolPosition;
import org.socialworld.datasource.pool.GaussPoolReactionType;
import org.socialworld.datasource.pool.GaussPoolState2ActionType;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.access.HiddenSimulationObject;

/**
 * Its the basic class for creating simulation objects.
 * for inherited classes of class SimulationObject
 *  there exists an inherited class of CreateSimulationObjects.
 *  
 * @author Mathias Sikos (MatWorsoc) 
 */
public abstract class CreateSimulationObjects {

	protected static final int THRESHOLD_RANDOM_GAUSSIAN_VALUE = 4;
	protected Random random;

	private List<Integer> usedPositionIndex;
	
	CreateSimulationObjects() {
		random = new Random();
		usedPositionIndex = new ArrayList<Integer>();
	}

	public abstract IncompleteSimulationObject getObject(int objectID, String fullClassName) ;
	
	protected final Object createObjectForName(String fullClassName) {
		
		
		Object createdObject = NoSimulationObject.getObjectNothing();
		Object noObject = NoSimulationObject.getObjectNothing();
		try {
			createdObject = Class.forName(fullClassName).getDeclaredConstructor().newInstance();
		}
		catch (ClassNotFoundException cnfe ) {
			System.out.println(cnfe.getMessage());
			return noObject;
		}
		catch (InvocationTargetException ite ) {
			System.out.println(ite.getMessage());
			return noObject;
		}
		catch (NoSuchMethodException nsme ) {
			System.out.println(nsme.getMessage());
			return noObject;
		}
		catch (IllegalAccessException iae ) {
			System.out.println(iae.getMessage());
			return noObject;
		}
		catch (InstantiationException ie) {
			ie.printStackTrace();
			return noObject;
		}
		
		return createdObject;
	}
	
	protected int mapGaussToIndex(double gaussValue, int arrayCapacity) {
		double factor;
		int result;
		factor = arrayCapacity / THRESHOLD_RANDOM_GAUSSIAN_VALUE;
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
		int indexGPPT; 
		
		double gauss_value;
		
	
		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE))
			gauss_value = random.nextGaussian();
		indexGPIT = mapGaussToIndex(gauss_value, GaussPoolInfluenceType.CAPACITY_GPIT_ARRAY);
		hiddenObject.setInfluenceTypes(GaussPoolInfluenceType.getInstance().getInfluenceTypes(indexGPIT));

		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE))
			gauss_value = random.nextGaussian();
		indexGPRT = mapGaussToIndex(gauss_value, GaussPoolReactionType.CAPACITY_GPRT_ARRAY);
		hiddenObject.setReactionTypes(GaussPoolReactionType.getInstance().getReactionTypes(indexGPRT));

		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE)) 
			gauss_value = random.nextGaussian();
		indexGPS2A = mapGaussToIndex(gauss_value, GaussPoolState2ActionType.CAPACITY_GPS2A_ARRAY);
		hiddenObject.setState2ActionType(GaussPoolState2ActionType.getInstance().getState2ActionType(indexGPS2A));

		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE)) 
			gauss_value = random.nextGaussian();
		indexGPPT = mapGaussToIndex(gauss_value, GaussPoolPerceptionType.CAPACITY_GPPT_ARRAY);
		hiddenObject.setPerceptionTypes(GaussPoolPerceptionType.getInstance().getPerceptionTypes(indexGPPT));
		
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
