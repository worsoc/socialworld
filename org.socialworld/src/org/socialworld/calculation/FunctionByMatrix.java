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
	public Value calculate(Value[] arguments) {
		
		calculation = Calculation.getInstance();

		switch (arguments[0].getType() ) {
			case attributeArray:
				AttributeArray attributes;
				attributes = (AttributeArray) arguments[0].getValue();
				if ((int) arguments[1].getValue() == AttributeCalculatorMatrix.MATRIX_CALCULATION_SIMPLE)
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

		Value 	attributeValue;
		Value 	share;
		int 	functionIndex;
		FunctionBase function;
		float change;

		Value[] arguments;

		int     length;

		length = attributes.length();
		attributesNew = new float[length];

		arguments = new Value[1];
		
		for (row = 0; row < length; row++) {

			attributeValue 			= attributes.getAsValue(row);

			for (column = 0; column < length; column++) {

				functionIndex = this.matrix.getFunction(row, column);
				share = new Value(Type.floatingpoint,  this.matrix.getShare(row, column));
				function = AttributeCalculatorFunctions.get(functionIndex);

				arguments[0] = new Value(Type.integer, attributeValue );
				change = (float)
						calculation.multiplication(share, function.calculate(arguments))
						.getValue();
						

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

		Value 	attributeValue;
		Value 	attributeChangeValue;
		Value 	inputValue;
		int 	functionIndex;
		Value 	share;
		Value 	offset;
		FunctionByMatrix_InputType 		inputType;

		int     length;
		
		FunctionBase function;
		Value[] arguments;

		float change;

		length = attributes.length();
		attributesNew = new float[length];
		arguments = new Value[1];
		offset = new Value(Type.integer,  0);
		
		for (row = 0; row < length; row++) {

			attributeValue 			= attributes.getAsValue(row);
			attributeChangeValue 	= attributes.getDifferenceAsValue(row);

			for (column = 0; column < length; column++) {

				functionIndex = this.matrix.getFunction(row, column);
				inputType = this.matrix.getInputType(row, column);
				share = new Value(Type.floatingpoint,  this.matrix.getShare(row, column));
				if (withOffset) offset = new Value(Type.integer,  this.matrix.getOffset(row, column));

				function = AttributeCalculatorFunctions.get(functionIndex);
				inputValue = getInputValue(inputType, attributeValue, attributeChangeValue);

				arguments[0] = new Value(Type.integer, inputValue );
				change = (float)
						calculation.addition(
								calculation.multiplication(share, function.calculate(arguments)),
								offset)
						.getValue();
				

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


	private Value getInputValue(FunctionByMatrix_InputType type, Value attributeValue,
			Value attributeChangeValue) {
		switch (type) {
		case NewAttributeValue:
			return attributeValue;
		case AttributeChange:
			return attributeChangeValue;
		case OldAttributeValue:
			return calculation.subtraction(attributeValue , attributeChangeValue);
		}
		return calculation.getNothing();
	}

}
