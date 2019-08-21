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
package org.socialworld.calculation;

import org.socialworld.attributes.AttributeArray;

public class FunctionByMatrix extends FunctionBase {

	private FunctionByMatrix_Matrix matrix;
	private static Value hundred;
	private static Value aHalf;
	
	public FunctionByMatrix() {
		returnInvalidNothingvalue = true;
		hundred = new Value(Type.integer, 100);
		aHalf = new Value(Type.floatingpoint, 0.5f);
	}

	public FunctionByMatrix(FunctionByMatrix_Matrix matrix) {
		this.matrix = matrix;
		returnInvalidNothingvalue = false;
		hundred = new Value(Type.floatingpoint, 100.0F);
		aHalf = new Value(Type.floatingpoint, 0.5f);
	}
	
	public void setMatrix(FunctionByMatrix_Matrix matrix) {
		if (returnInvalidNothingvalue == true) {
			this.matrix = matrix;
			returnInvalidNothingvalue = false;
		}
	}
	
	@Override
	public Value calculate(Value[] arguments) {

		Value result;
		
		if (returnInvalidNothingvalue) 
			return new Value();
		
		calculation = Calculation.getInstance();

		switch (arguments[0].getType() ) {
			case attributeArray:
				AttributeArray attributes;
				attributes = (AttributeArray) arguments[0].getValue();
				
				int calculationMode = (int) arguments[1].getValue();
				switch (calculationMode)
				{
				case FunctionByMatrix_Matrix.CALCULATION_MODE_MATRIX_X_VECTOR_SIMPLE:
					calculateAttributesSimply(attributes);
					result = calculation.createValue(Type.attributeArray, attributes );
					break;
				case FunctionByMatrix_Matrix.CALCULATION_MODE_MATRIX_X_VECTOR_COMPLEX:
					calculateAttributes(attributes, this.matrix.isWithOffset());
					result = calculation.createValue(Type.attributeArray, attributes );
					break;
				case FunctionByMatrix_Matrix.CALCULATION_MODE_VECTOR_X_VECTOR:
					result = new Value(Type.floatingpoint, calculateScalar(attributes));
					break;
				default:
					result = new Value();
				}
			default:
				result = new Value();
		}
		
		return result;
		
	}

	
	private float calculateScalar(AttributeArray attributes) {
		
		float result = 0;
		
		int 	column;

		Value 	inputValue;
		int 	functionIndex;
		Value 	share;
		Value 	offset;

		int     length;
		
		Functions functions = Functions.getInstance();
		FunctionBase function;
		Value[] arguments;

		float change;

		length = attributes.length();
		arguments = new Value[1];
		offset = new Value(Type.integer,  0);
		

		for (column = 0; column < length; column++) {

			functionIndex = this.matrix.getFunction(0, column);
			share = this.matrix.getShare(0, column);
			offset =  this.matrix.getOffset(0, column);

			function = functions.get(functionIndex);
			inputValue = attributes.getAsValue(column);

			arguments[0] =  inputValue ;
			change = (float)
					calculation.addition(
							calculation.multiplication(share, function.calculate(arguments)),
							offset)
					.getValueCopy();
			

			result += change;

		}
		
		return result;
		
	}
	
	/**
	 * The method calculates the attribute changes on a simple way..
	 *  The attribute calculation only uses shares and functions on attributes.
	 *  The function's input values are the current attribute values.
	 * 
	 */
	private void calculateAttributesSimply(AttributeArray attributes) {
		
		Value 	attributesNew[];
		
		int 	column;
		int		row;

		Value 	attributeValue;
		Value 	share;
		int 	functionIndex;
		FunctionBase function;
		Functions functions = Functions.getInstance();
		
		Value change;

		Value[] arguments;

		int     length;

		length = attributes.length();
		attributesNew = new Value[length];

		
		for (row = 0; row < length; row++) {
			attributesNew[row] = calculation.getZero(Type.integer);
		}

		arguments = new Value[1];
			
		for (row = 0; row < length; row++) {

			attributeValue 			= attributes.getAsValue(row);

			for (column = 0; column < length; column++) {

				functionIndex = this.matrix.getFunction(row, column);
				share =   this.matrix.getShare(row, column);
				function = functions.get(functionIndex);

				arguments[0] =  attributeValue;

				change = calculation.multiplication(share, function.calculate(arguments));
				attributesNew[column] = calculation.addition(attributesNew[column], change);

			}
		}
		
		

		// rounding the attribute values to integer values
		// writing them from help array to main array
		for (row = 0; row < length; row++) {
			
			if (this.matrix.isWithDiv100() ) attributesNew[row] = calculation.division(attributesNew[row] , hundred);
			attributesNew[row] = calculation.addition(attributesNew[row], aHalf);
			attributes.set(row, ((Float) (attributesNew[row].getValue())).intValue());
			
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

		Functions functions = Functions.getInstance();
		
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
				share = this.matrix.getShare(row, column);
				if (withOffset) offset =  this.matrix.getOffset(row, column);

				function = functions.get(functionIndex);
				inputValue = getInputValue(inputType, attributeValue, attributeChangeValue);

				arguments[0] =  inputValue ;
				change = (float)
						calculation.addition(
								calculation.multiplication(share, function.calculate(arguments)),
								offset)
						.getValueCopy();
				

				if (change != 0)
					attributesNew[column] += change;

			}
		}

		// rounding the attribute values to integer values
		// writing them from help array to main array
		for (row = 0; row < length; row++) {
			
			if (this.matrix.isWithDiv100() ) attributesNew[row] = attributesNew[row] / 100;
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
		return Calculation.getNothing();
	}

}
