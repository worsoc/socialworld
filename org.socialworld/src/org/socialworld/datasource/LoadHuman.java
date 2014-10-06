/**
 * 
 */
package org.socialworld.datasource;

import org.socialworld.objects.*;
import org.socialworld.datasource.LoadAnimal;
import org.socialworld.SimpleClientActionHandler;

/**
 * Because of being a singleton there exists
 *         only one instance of the class. 
 *         The instance is responsible for creating human objects
 *         Therefore the object data is loaded from a data source
 *         and put to a new created instance of class Human
 * @author Mathias Sikos (tyloesand) 
 */
public class LoadHuman extends LoadAnimal implements IHumanWrite {
	
	private static LoadHuman instance;

	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private LoadHuman() {
	}

	/**
	 * The method gets back the only instance of the LoadHuman.
	 * 
	 * @return singleton object of LoadHuman
	 */
	public static LoadHuman getInstance() {
		if (instance == null) {
			instance = new LoadHuman();
			
		}
		return instance;
	}
	

	
	/**
	 * The method creates an instance of class Human.
	 * 
	 * @param objectID
	 * @return  Human
	 */
	public Human getObject(int objectID) {
	
		Human createdHuman = new Human();
		WriteAccessToHuman human = new WriteAccessToHuman(createdHuman);

	
		initObject(human, objectID);	

		SimpleClientActionHandler.getInstance().setHumanWrite(objectID, human);

		return createdHuman;
	}

	protected void initObject(WriteAccessToHuman object, int objectID) {
		super.initObject(object, objectID);

	}

	public void selectAll() {
		
	}
	
}


/* save
 * 
	public Human getObject(long objectID) {
		
		double gauss_value;
		int indexACMP;
		int indexAAP;
		int indexITP; 
		int indexRTP; 
		int indexS2AP; 
		int indexPosition;
		
		gauss_value = random.nextGaussian();
		indexACMP = mapGaussToIndex(gauss_value, AttributeCalculatorMatrixPool.CAPACITY_ACMP_ARRAY);
		indexAAP = mapGaussToIndex(gauss_value, AttributeArrayPool.CAPACITY_AAP_ARRAY);
		indexITP = mapGaussToIndex(gauss_value, InfluenceTypePool.CAPACITY_ITP_ARRAY);
		indexRTP = mapGaussToIndex(gauss_value, ReactionTypePool.CAPACITY_RTP_ARRAY);
		indexS2AP = mapGaussToIndex(gauss_value, State2ActionTypePool.CAPACITY_S2AP_ARRAY);
		indexPosition = mapGaussToIndex(gauss_value, PositionPool.CAPACITY_PosP_ARRAY);
		
		Human createdHuman = new Human();
		WriteAccessToHuman human = new WriteAccessToHuman(createdHuman);
		
		human.setObjectID(objectID, this);
		human.setMatrix(	
					AttributeCalculatorMatrixPool.getInstance().getMatrix(indexACMP),
					this);
		human.setAttributes(
					AttributeArrayPool.getInstance().getArray(indexAAP),
					this);
		human.setInfluenceTypes(InfluenceTypePool.getInstance().getInfluenceTypes(indexITP), this);
		human.setReactionTypes(ReactionTypePool.getInstance().getReactionTypes(indexRTP), this);
		human.setState2ActionType(State2ActionTypePool.getInstance().getState2ActionType(indexS2AP), this);
		human.setPosition(PositionPool.getInstance().getPosition(indexPosition), this);

		SimpleClientActionHandler.getInstance().setHumanWrite((int)objectID, human);

		return createdHuman;
	}
 * 
 */
