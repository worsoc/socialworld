/**
 * 
 */
package org.socialworld.calculation;

import org.socialworld.attributes.AttributeArray;
import org.socialworld.core.Event;
import org.socialworld.objects.Animal;


public class AttributeCalculator {


	/**
	 * the attribute array that is set by the user and is got back to the user
	 * after calculation.
	 */
	private AttributeArray attributes;
	private int numberOfAttributes;

	public AttributeCalculator(int numberOfAttributes) {
		this.numberOfAttributes = numberOfAttributes;
		attributes = new AttributeArray(this.numberOfAttributes);
	}



	/**
	 * The method sets the event influence description to an instance variable
	 * and starts the calculation.
	 * 
	 * @param eventInfluence
	 *            the informations how an event effects to the attributes.
	 */
	public AttributeArray changeAttributesByEvent(Event event, Animal animal) {
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

		
		attributes = animal.getAttributes();
		
		arguments = new Argument[1];
		arguments[0] = new Argument(ArgumentType.attributeArray, attributes);
	
		return (AttributeArray) f_EventInfluence.calculate(arguments).getValue();
		
		
	}


	public void changeAttributesByMatrix(Animal animal) {
		FunctionByMatrix f_AttributesByMatrix;
		Argument[] arguments;
	
		arguments = new Argument[2];
		arguments[0] = new Argument(ArgumentType.attributeArray, this.attributes);
		arguments[1] = new Argument(ArgumentType.integer, AttributeCalculatorMatrix.MATRIX_CALCULATION_COMPLEX );
		
		// TODO get the function object from animal (not the matrix!)
		//      --> the function object shouldn't be created here
		f_AttributesByMatrix = new FunctionByMatrix(animal.getMatrix());
		this.attributes = (AttributeArray) f_AttributesByMatrix.calculate(arguments).getValue();
	}
	
	public void refreshAttributes(Animal animal) {
		FunctionByMatrix f_AttributesByMatrix;
		Argument[] arguments;
	
		arguments = new Argument[2];
		arguments[0] = new Argument(ArgumentType.attributeArray, this.attributes);
		arguments[1] = new Argument(ArgumentType.integer, AttributeCalculatorMatrix.MATRIX_CALCULATION_SIMPLE);
		
		// TODO get the function object from animal (not the matrix!)
		//      --> the function object shouldn't be created here
		f_AttributesByMatrix = new FunctionByMatrix(animal.getMatrix());
		this.attributes = (AttributeArray) f_AttributesByMatrix.calculate(arguments).getValue();
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

