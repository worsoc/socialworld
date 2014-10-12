/**
 * 
 */
package org.socialworld.calculation;

import org.socialworld.attributes.AttributeArray;


public class AttributeCalculator {


	/**
	 * the attribute array that is set by the user and is got back to the user
	 * after calculation.
	 */
	private AttributeArray attributes;

	/**
	 * a matrix that holds the information how to calculate on the attribute
	 * array.
	 */
	private AttributeCalculatorMatrix matrix;

	
	/**
	 * a description that holds the information
	 *  how an event effects to the attribute array.
	 */
	private EventInfluenceDescription eventInfluence;
	

	private int numberOfAttributes;

	public AttributeCalculator(int numberOfAttributes) {
		this.numberOfAttributes = numberOfAttributes;
		attributes = new AttributeArray(this.numberOfAttributes);
	}


	/**
	 * The method sets the attribute array of an object 
	 * 
	 * @param attributes
	 */
	public void setAttributes(
			AttributeArray attributes) {
		int index;
		for (index = 0; index < numberOfAttributes; index++) {
				this.attributes.set(index, attributes.get(index));
		}
	}

	/**
	 * By calling that method the caller gets the actual state of the attribute
	 * array.
	 * 
	 * @param attributes
	 * @param user
	 */
	public void fetchAttributes(AttributeArray attributes) {
		int index;
		for (index = 0; index < numberOfAttributes; index++) {
				attributes.set(index, this.attributes.get(index));
		}
	}

	/**
	 * The method sets the event influence description to an instance variable
	 * and starts the calculation.
	 * 
	 * @param eventInfluence
	 *            the informations how an event effects to the attributes.
	 */
	public void changeAttributes(EventInfluenceDescription eventInfluence) {
		this.eventInfluence = eventInfluence;
		modifyAttributes();
	}

	/**
	 * The method sets the attribute calculation matrix to an instance variable
	 * and starts the calculation.
	 *  The attribute calculation may use shares , functions on attributes and offsets.
	 *  The function's input values are 
	 *  - the current attribute values,
	 *  - the last attribute value or 
	 *  - the difference between current and last attribute value.
	 * 
	 * @param matrix
	 *            the informations how the attributes influence each other.
	 */
	public void changeAttributes(AttributeCalculatorMatrix matrix) {
		this.matrix = matrix;
		if (this.matrix.isWithOffset() )
			calculateAttributesByMatrixWithOffset();
		else 
			calculateAttributesByMatrix();
	}

	/**
	 * The method sets a simple attribute calculation matrix (without offsets and input type Attribute)
	 *  to an instance variable and starts a simple calculation.
	 *  The calculation of attributes only depends on 
	 *  - the current attribute values( a function on the current value) and
	 *  - shares
	 * Both is given by the matrix parameter
	 *  There is only one calculation run.
	 * 
	 * @param matrix
	 *            the informations how the attributes influence each other.
	 */
	public void refreshAttributes(AttributeCalculatorMatrix matrix) {
		this.matrix = matrix;
		calculateAttributesSimply();
	}

	/**
	 * The method calculates an event caused change of an attribute array.
	 * The event description was set to an instance variable by public function changeAttributes(...
	 *           
	 */
	private void modifyAttributes() {
		EventInfluenceExpression expression;
		int index;
		
		expression = this.eventInfluence.expression;
		for (index = 0; index < this.numberOfAttributes; index++) {
			modifyAttribute(index, expression);
		}
	}
	
	/**
	 * The method calculates the change of an attribute by an event.
	 * 
	 * @param targetAttribute
	 *            the attribute to modify
	 * @param exression
	 *            the informations how the event changes the attribute
	 *           
	 */
	private void modifyAttribute(int attributeIndex,
			EventInfluenceExpression expression) {
			this.attributes.set(attributeIndex, (int) expression.evaluateExpression(
				this.attributes, this.attributes.get( attributeIndex)));
	}


	/**
	 * The method calculates the attribute changes on a simple way..
	 *  The attribute calculation only uses shares and functions on attributes.
	 *  The function's input values are the current attribute values.
	 * 
	 * @return true if the calculation is not finished and false if the
	 *         calculation stops.
	 */
	private void calculateAttributesSimply() {
		float 	attributesNew[];
		int 	column;
		int		row;

		int 	attributeValue;
		
		int 	functionIndex;
		float 	share;

		AttributeCalculatorFunction function;

		float change;

		attributesNew = new float[this.numberOfAttributes];

		
		for (row = 0; row < this.numberOfAttributes; row++) {

			attributeValue 			= this.attributes.get(row);

			for (column = 0; column < this.numberOfAttributes; column++) {

				functionIndex = this.matrix.getFunction(row, column);
				share = this.matrix.getShare(row, column);
				function = AttributeCalculatorFunctions.get(functionIndex);

				change = share * function.calculate(attributeValue);

				if (change != 0)
					attributesNew[column] += change;

			}
		}

		// rounding the attribute values to integer values
		// writing them from help array to main array
		for (row = 0; row < this.numberOfAttributes; row++) {
			this.attributes.set(row, (int) (attributesNew[row] + 0.5));
		}


	}
	


	/**
	 * The method calculates the attribute changes. For every matrix element the
	 * influence is evaluated and the change is set to the influenced attribute.
	 * The calculation is described by a matrix that holds, shares and functions.
	 * Furthermore an input type defines whether the input value is
	 * - the current attribute value,
	 * - the difference to the last attribute value or
	 * - the last attribute value
	 * 
	 * @return true if the calculation is not finished and false if the
	 *         calculation stops.
	 */
	private boolean calculateAttributesByMatrix() {
		float	attributesNew[];
		int 	column;
		int		row;

		int 	attributeValue;
		int 	attributeChangeValue;
		
		int 	inputValue;
		int 	functionIndex;
		float 	share;

		AttributeCalculatorFunction function;
		CalculationInputType 		inputType;

		float change;

		attributesNew = new float[this.numberOfAttributes];

		for (row = 0; row < this.numberOfAttributes; row++) {

			attributeValue 			= this.attributes.get(row);
			attributeChangeValue 	= this.attributes.getDifference(row);

			for (column = 0; column < this.numberOfAttributes; column++) {

				functionIndex = this.matrix.getFunction(row, column);
				inputType = this.matrix.getInputType(row, column);
				share = this.matrix.getShare(row, column);

				function = AttributeCalculatorFunctions.get(functionIndex);
				inputValue = getInputValue(inputType, attributeValue, attributeChangeValue);

				change = share * function.calculate(inputValue);

				if (change != 0)
					attributesNew[column] += change;

			}
		}

		// rounding the attribute values to integer values
		// writing them from help array to main array
		for (row = 0; row < this.numberOfAttributes; row++) {
			this.attributes.set(row, (int) (attributesNew[row] + 0.5));
		}


		return true;

	}

	/**
	 * The method calculates the attribute changes. For every matrix element the
	 * influence is evaluated and the change is set to the influenced attribute.
	 * The calculation is described by a matrix that holds, shares, functions and offsets.
	 * Furthermore an input type defines whether the input value is
	 * - the current attribute value,
	 * - the difference to the last attribute value or
	 * - the last attribute value
	 * 
	 * @return true if the calculation is not finished and false if the
	 *         calculation stops.
	 */
	private boolean calculateAttributesByMatrixWithOffset() {
		float   attributesNew[];
		int 	column;
		int		row;

		int 	attributeValue;
		int 	attributeChangeValue;
		
		int 	inputValue;
		int 	functionIndex;
		int 	offset;
		float 	share;

		AttributeCalculatorFunction function;
		CalculationInputType 		inputType;

		float change;

		attributesNew = new float[this.numberOfAttributes];

		for (row = 0; row < this.numberOfAttributes; row++) {

			attributeValue 			= this.attributes.get(row);
			attributeChangeValue 	= this.attributes.getDifference(row);

			for (column = 0; column < this.numberOfAttributes; column++) {

				functionIndex = this.matrix.getFunction(row, column);
				inputType = this.matrix.getInputType(row, column);
				share = this.matrix.getShare(row, column);
				offset = this.matrix.getOffset(row, column);

				function = AttributeCalculatorFunctions.get(functionIndex);
				inputValue = getInputValue(inputType, attributeValue, attributeChangeValue);

				change = share * function.calculate(inputValue) + offset;

				if (change != 0)
					attributesNew[column] += change;

			}
		}

		// rounding the attribute values to integer values
		// writing them from help array to main array
		for (row = 0; row < this.numberOfAttributes; row++) {
			this.attributes.set(row, (int) (attributesNew[row] + 0.5));
		}

		return true;

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

	private int getInputValue(CalculationInputType type, int attributeValue,
					int attributeChangeValue) {
		switch (type) {
		case NewAttributeValue:
			return attributeValue;
		case AttributeChange:
			return attributeChangeValue;
		case OldAttributeValue:
			return (attributeValue - attributeChangeValue);
		}
		return 0;
	}
}

