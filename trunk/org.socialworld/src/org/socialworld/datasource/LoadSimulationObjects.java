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
package org.socialworld.datasource;

import java.util.Random;

import org.apache.log4j.Logger;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.WriteAccessToSimulationObject;

/**
 * Its the basic class for creating simulation objects.
 * for inherited classes of class SimulationObject
 *  there exists an inherited class of LoadSimulationObjects.
 *  
 * @author Mathias Sikos (tyloesand) 
 */
public abstract class LoadSimulationObjects {
	
	protected static final Logger logger = Logger.getLogger(LoadSimulationObjects.class);

	protected Random random;

	public LoadSimulationObjects() {
		random = new Random();
	}

	public abstract SimulationObject getObject(int objectID) ;
	protected abstract void selectAllForID(int objectID);
	
	protected int mapGaussToIndex(double gaussValue, int arrayCapacity) {
		double factor;
		int result;
		factor = arrayCapacity / 4;
		result = (int) (gaussValue * factor);
		return result;
	}
	

	protected void initObject(WriteAccessToSimulationObject object, int objectID) {
		int indexITP; 
		int indexRTP; 
		int indexS2AP; 
		int indexPosition;
		double gauss_value;
		
		object.setObjectID(objectID, this);
	
		gauss_value = random.nextGaussian();
		indexITP = mapGaussToIndex(gauss_value, InfluenceTypePool.CAPACITY_ITP_ARRAY);
		object.setInfluenceTypes(InfluenceTypePool.getInstance().getInfluenceTypes(indexITP), this);

		gauss_value = random.nextGaussian();
		indexRTP = mapGaussToIndex(gauss_value, ReactionTypePool.CAPACITY_RTP_ARRAY);
		object.setReactionTypes(ReactionTypePool.getInstance().getReactionTypes(indexRTP), this);

		gauss_value = random.nextGaussian();
		indexS2AP = mapGaussToIndex(gauss_value, State2ActionTypePool.CAPACITY_S2AP_ARRAY);
		object.setState2ActionType(State2ActionTypePool.getInstance().getState2ActionType(indexS2AP), this);

		indexPosition = random.nextInt(PositionPool.CAPACITY_PosP_ARRAY);
		if (random.nextBoolean() == false) indexPosition = indexPosition * -1;
		object.setPosition(PositionPool.getInstance().getPosition(indexPosition), this);

	}
}
