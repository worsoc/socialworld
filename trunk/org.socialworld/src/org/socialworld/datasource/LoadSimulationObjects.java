/**
 * 
 */
package org.socialworld.datasource;

import java.util.Random;

import org.apache.log4j.Logger;
import org.socialworld.objects.ISimulationObjectWrite;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.WriteAccessToSimulationObject;

/**
 * Its the basic class for creating simulation objects.
 * for inherited classes of class SimulationObject
 *  there exists an inherited class of LoadSimulationObjects.
 *  
 * @author Mathias Sikos (tyloesand) 
 */
public abstract class LoadSimulationObjects implements ISimulationObjectWrite {
	
	protected static final Logger logger = Logger.getLogger(LoadSimulationObjects.class);

	protected Random random;

	public LoadSimulationObjects() {
		random = new Random();
	}

	public abstract SimulationObject getObject(int objectID) ;
	
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
