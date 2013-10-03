/**
 * 
 */
package org.socialworld.datasource;

import java.util.Random;

import org.socialworld.objects.*;
import org.socialworld.attributes.Position;
import org.socialworld.datasource.LoadAnimal;
import org.socialworld.datasource.AttributeCalculatorMatrixPool;

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
	private Random random;

	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private LoadHuman() {
		random = new Random();
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
	public Human getObject(long objectID) {
		
		double gauss_value;
		int indexACMP;
		int indexAAP;
		
		gauss_value = random.nextGaussian();
		indexACMP = mapGaussToIndex(gauss_value, AttributeCalculatorMatrixPool.CAPACITY_ACMP_ARRAY);
		indexAAP = mapGaussToIndex(gauss_value, AttributeArrayPool.CAPACITY_AAP_ARRAY);
		
		Human createdHuman = new Human();
		WriteAccessToHuman human = new WriteAccessToHuman(createdHuman);
		
		human.setObjectID(objectID, this);
		human.setMatrix(	
					AttributeCalculatorMatrixPool.getInstance().getMatrix(indexACMP),
					this);
		human.setAttributes(
					AttributeArrayPool.getInstance().getArray(indexAAP),
					this);
		
		// TODO (tyloesand) only for testing, must be deleted
		// find a solution for assign positions
		// (not this following provisional one)
		switch ((int)objectID) {
		case 1: 
			human.setPosition(new Position(100,100,0) , this);
			break;
		case 2: 
			human.setPosition(new Position(100,110,0), this);
			break;
		case 3: 
			human.setPosition(new Position(400,400,0), this);
			break;
		default:
			human.setPosition(new Position(3 * (int)objectID, - 2 * (int) objectID , (int) objectID), this);
			break;
		}
		SimpleClientActionHandler.getInstance().setHumanWrite((int)objectID, human);

		return createdHuman;
	}


}
