/**
 * 
 */
package org.socialworld.calculation;

import org.socialworld.attributes.AttributeArray;
import org.socialworld.core.Event;
import org.socialworld.objects.Animal;


public  class AttributeCalculator {



	/**
	 * The method sets the event influence description to an instance variable
	 * and starts the calculation.
	 * 
	 * @param eventInfluence
	 *            the informations how an event effects to the attributes.
	 */
	public static AttributeArray getAttributesChangedByEvent(Event event, Animal animal) {
		EventInfluenceDescription eventInfluenceDescription;
		int eventType;
		int eventInfluenceType;
	
		FunctionByExpression f_EventInfluence;
		Argument[] arguments;
	
		eventType = event.getEventType();
		eventInfluenceType = animal.getInfluenceType(eventType);
		
		eventInfluenceDescription = 
			EventInfluenceAssignment.getInstance().getEventInfluenceDescription(
				eventType, eventInfluenceType	);

		f_EventInfluence = eventInfluenceDescription.getFunctionEventInfluence();

		
		arguments = new Argument[1];
		arguments[0] = new Argument(ArgumentType.attributeArray, animal.getAttributes());
	
		return (AttributeArray) f_EventInfluence.calculate(arguments).getValue();
	}


	public static AttributeArray getAttributesChangedByMatrix(Animal animal) {
		FunctionByMatrix f_AttributesByMatrix;
		Argument[] arguments;
	

		arguments = new Argument[2];
		arguments[0] = new Argument(ArgumentType.attributeArray, animal.getAttributes());
		arguments[1] = new Argument(ArgumentType.integer, AttributeCalculatorMatrix.MATRIX_CALCULATION_COMPLEX );
		
		// TODO get the function object from animal (not the matrix!)
		//      --> the function object shouldn't be created here
		f_AttributesByMatrix = new FunctionByMatrix(animal.getMatrix());
		return (AttributeArray) f_AttributesByMatrix.calculate(arguments).getValue();
	}
	
	public static AttributeArray getAttributesChangedByRefresh(Animal animal) {
		FunctionByMatrix f_AttributesByMatrix;
		Argument[] arguments;
	
		arguments = new Argument[2];
		arguments[0] = new Argument(ArgumentType.attributeArray, animal.getAttributes());
		arguments[1] = new Argument(ArgumentType.integer, AttributeCalculatorMatrix.MATRIX_CALCULATION_SIMPLE);
		
		// TODO get the function object from animal (not the matrix!)
		//      --> the function object shouldn't be created here
		f_AttributesByMatrix = new FunctionByMatrix(animal.getMatrix());
		return (AttributeArray) f_AttributesByMatrix.calculate(arguments).getValue();
	}

	/**
	 * The method checks whether the calculation loop continues. The calculation
	 * loop stops if for every attribute the value changes by less than 1.
	 * 
	 * @return true if the calculation loop continues and false if the
	 *         calculation loop stops
	 */
/*	private boolean checkEpsilon() {
		int row = 0;

		while ((attributesNew[row] + 1) > (float) this.attributes.get(row)
				|| (attributesNew[row] - 1) < (float) this.attributes.get(row)) {
			row++;
			if (row == this.numberOfAttributes)
				return false;
		}
		return true;
	}
*/

}

