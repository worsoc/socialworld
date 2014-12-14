package org.socialworld.calculation;

import org.socialworld.attributes.AttributeArray;

public class FunctionByMatrix extends FunctionBase {

	private AttributeCalculatorMatrix matrix;

	public FunctionByMatrix(AttributeCalculatorMatrix matrix) {
		this.matrix = matrix;
	}
	
	public void setMatrix(AttributeCalculatorMatrix matrix) {
		this.matrix = matrix;
	}
	
	@Override
	public Value calculate(Argument[] arguments) {
		
		calculation = Calculation.getInstance();

		switch (arguments[0].getType() ) {
			case attributeArray:
				AttributeArray attributes;
				attributes = (AttributeArray) arguments[0].getArgument();
				if ((int) arguments[1].getArgument() == AttributeCalculatorMatrix.MATRIX_CALCULATION_SIMPLE)
					calculateAttributesSimply(attributes);
				else
					calculateAttributes(attributes, this.matrix.isWithOffset());
				return calculation.createValue(Type.attributeArray, attributes );
			default:
				return null;
		}
		
	}

	
	/**
	 * The method calculates the attribute changes on a simple way..
	 *  The attribute calculation only uses shares and functions on attributes.
	 *  The function's input values are the current attribute values.
	 * 
	 */
	private void calculateAttributesSimply(AttributeArray attributes) {
		float 	attributesNew[];
		int 	column;
		int		row;

		int 	attributeValue;
		
		int 	functionIndex;
		float 	share;

		AttributeCalculatorFunction function;

		float change;

		int     length;

		length = attributes.length();
		attributesNew = new float[length];

		
		for (row = 0; row < length; row++) {

			attributeValue 			= attributes.get(row);

			for (column = 0; column < length; column++) {

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
		for (row = 0; row < length; row++) {
			attributes.set(row, (int) (attributesNew[row] + 0.5));
		}

		return;
	}
	
	
	
	/**
	 * The method calculates the attribute changes. For every matrix element the
	 * influence is evaluated and the change is set to the influenced attribute.
	 * The calculation is described by a matrix that holds shares , functions and offsets.
	 * Furthermore an input type defines whether the input value is
	 * - the current attribute value,
	 * - the difference to the last attribute value or
	 * - the last attribute value
	 * 
	 */
	private void calculateAttributes(AttributeArray attributes, boolean withOffset ) {
		float	attributesNew[];
		int 	column;
		int		row;

		int 	attributeValue;
		int 	attributeChangeValue;
		
		int 	inputValue;
		int 	functionIndex;
		float 	share;
		int 	offset = 0;

		int     length;
		
		AttributeCalculatorFunction function;
		CalculationInputType 		inputType;

		float change;

		length = attributes.length();
		attributesNew = new float[length];

		for (row = 0; row < length; row++) {

			attributeValue 			= attributes.get(row);
			attributeChangeValue 	= attributes.getDifference(row);

			for (column = 0; column < length; column++) {

				functionIndex = this.matrix.getFunction(row, column);
				inputType = this.matrix.getInputType(row, column);
				share = this.matrix.getShare(row, column);
				if (withOffset) offset = this.matrix.getOffset(row, column);

				function = AttributeCalculatorFunctions.get(functionIndex);
				inputValue = getInputValue(inputType, attributeValue, attributeChangeValue);

				change = share * function.calculate(inputValue) + offset;

				if (change != 0)
					attributesNew[column] += change;

			}
		}

		// rounding the attribute values to integer values
		// writing them from help array to main array
		for (row = 0; row < length; row++) {
			attributes.set(row, (int) (attributesNew[row] + 0.5));
		}


		return ;

	}


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
