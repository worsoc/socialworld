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
package org.socialworld.calculation.functions;



import org.socialworld.GlobalSwitches;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.Calculation;
import org.socialworld.calculation.FunctionBase;
import org.socialworld.calculation.Functions;
import org.socialworld.calculation.NoObject;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.ValueInterpreteAs;
import org.socialworld.collections.ValueArrayList;

public class FunctionByMatrix extends FunctionBase{

	ValueInterpreteAs interpreteResultAs;

	private FunctionByMatrix_Matrix matrix;
	private static Value hundred;
	private static Value aHalf;
	
	private static AccessTokenFunction token = AccessTokenFunction.getValid();
	
	public FunctionByMatrix(ValueInterpreteAs interpreteResultAs) {
		
		this.interpreteResultAs = interpreteResultAs;
		returnInvalidNothingvalue = true;
		hundred = new Value(Type.integer, 100);
		aHalf = new Value(Type.floatingpoint, 0.5f);
		
		if (interpreteResultAs == ValueInterpreteAs.attributeValue) {
			setMinMaxCheckFloat(0, AttributeArray.ATTRIBUTE_VALUE_MAX) ;
		}
	}

	public FunctionByMatrix(FunctionByMatrix_Matrix matrix) {
		
		this.interpreteResultAs = ValueInterpreteAs.nothing;
		this.matrix = matrix;
		returnInvalidNothingvalue = false;
		hundred = new Value(Type.floatingpoint, 100.0F);
		aHalf = new Value(Type.floatingpoint, 0.5f);
		
	}

	public FunctionByMatrix(ValueInterpreteAs interpreteResultAs, FunctionByMatrix_Matrix matrix) {
		
		this.interpreteResultAs = interpreteResultAs;
		this.matrix = matrix;
		returnInvalidNothingvalue = false;
		hundred = new Value(Type.floatingpoint, 100.0F);
		aHalf = new Value(Type.floatingpoint, 0.5f);
		
		if (interpreteResultAs == ValueInterpreteAs.attributeValue) {
			setMinMaxCheckFloat(0, AttributeArray.ATTRIBUTE_VALUE_MAX) ;
		}

	}
	
	public void setMatrix(FunctionByMatrix_Matrix matrix) {
		if (returnInvalidNothingvalue == true) {
			this.matrix = matrix;
			returnInvalidNothingvalue = false;
		}
	}
	
	@Override
	public Value calculate(ValueArrayList arguments) {

		Value result;
		
		if (returnInvalidNothingvalue) 
			return Value.getValueNothing();
		
		calculation = Calculation.getInstance();

		switch (arguments.get(0).getType() ) {
		
			case attributeArray:
				AttributeArray attributesOld = AttributeArray.getObjectNothing();
				AttributeArray attributesNew = AttributeArray.getObjectNothing();
				
				attributesOld = objectRequester.requestAttributeArray(token, arguments.get(0), this);
				if (attributesOld == AttributeArray.getObjectNothing()) return Value.getValueNothing();
				
				int calculationMode;
				Value v = arguments.get(1);
				Object o = v.getObject(Type.integer);
				if (o instanceof NoObject) {
					if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
						System.out.println("FunctionByMatrix.calculate > calculationMode: o (getObject(Type.integer)) is NoObject " + ((NoObject)o).getReason().toString() );
					}
					calculationMode = 0;
				}
				else {
					calculationMode = (int) o;
				}
				
				switch (calculationMode)
				{
					case FunctionByMatrix_Matrix.CALCULATION_MODE_MATRIX_X_VECTOR_SIMPLE:
						attributesNew = calculateAttributesSimply(attributesOld);
						result = calculation.createValue(Type.attributeArray, attributesNew );
						break;
					case FunctionByMatrix_Matrix.CALCULATION_MODE_MATRIX_X_VECTOR_COMPLEX:
						attributesNew = calculateAttributes(attributesOld, this.matrix.isWithOffset());
						result = calculation.createValue(Type.attributeArray, attributesNew );
						break;
					case FunctionByMatrix_Matrix.CALCULATION_MODE_VECTOR_X_VECTOR:
						if (interpreteResultAs == ValueInterpreteAs.attributeValue) 
							result = calculation.createValue(Type.integer, calculateScalar(attributesOld));
						else
							result = calculation.createValue(Type.floatingpoint, calculateScalar(attributesOld));
						break;
					default:
						result = Value.getValueNothing();
				}
				
				break;
				
			default:
				result = Value.getValueNothing();
				
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
		ValueArrayList arguments;

		float change;

		length = attributes.length();
		arguments = new ValueArrayList(1);
		
		offset = new Value(Type.integer,  0);
		

		for (column = 0; column < length; column++) {

			functionIndex = this.matrix.getFunction(0, column);
			share = this.matrix.getShare(0, column);
			offset =  this.matrix.getOffset(0, column);

			function = functions.get(functionIndex);
			inputValue = attributes.getAsValue(column);

			arguments.set(0, inputValue);
			
			Value v = calculation.addition(
					calculation.multiplication(share, function.calculate(arguments)),
					offset);
			Object o = v.getObject(Type.floatingpoint);
			if (o instanceof NoObject) {
				if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
					System.out.println("FunctionByMatrix.calculateScalar > change: o (getObject(Type.floatingpoint)) is NoObject " + ((NoObject)o).getReason().toString() );
				}
				change = 0;
			}
			else {
				change = (float) o;
			}

			result += change;

		}
		
		result = getMinMaxedFloat(result);

		return result;
		
	}
	
	/**
	 * The method calculates the attribute changes on a simple way..
	 *  The attribute calculation only uses shares and functions on attributes.
	 *  The function's input values are the current attribute values.
	 * 
	 */
	private AttributeArray calculateAttributesSimply(AttributeArray attributes) {
		
		Value 	attributesNew[];
		
		int 	column;
		int		row;

		Value 	attributeValue;
		Value 	share;
		int 	functionIndex;
		FunctionBase function;
		Functions functions = Functions.getInstance();
		
		Value change;

		ValueArrayList arguments;

		int     length;

		AttributeArray result = new AttributeArray(attributes);
	
		length = attributes.length();
		attributesNew = new Value[length];

		
		for (row = 0; row < length; row++) {
			attributesNew[row] = calculation.getZero(Type.integer);
		}

		arguments = new ValueArrayList(1);
			
		for (row = 0; row < length; row++) {

			attributeValue 			= attributes.getAsValue(row);

			for (column = 0; column < length; column++) {

				functionIndex = this.matrix.getFunction(row, column);
				share =   this.matrix.getShare(row, column);
				function = functions.get(functionIndex);

				arguments.set(0,   attributeValue);

				change = calculation.multiplication(share, function.calculate(arguments));
				
				// DEBUG kann weg, zum Feststellen, ob Fehler
				if (change == null) {
					System.out.println("FunctionByMatrix.calculateAttributesSimply() : change ist Null");
					
				}
				
				attributesNew[column] = calculation.addition(attributesNew[column], change);

			}
		}
		
		

		// rounding the attribute values to integer values
		// writing them from help array to main array
		for (row = 0; row < length; row++) {
			
			if (this.matrix.isWithDiv100() ) attributesNew[row] = calculation.division(attributesNew[row] , hundred);
			attributesNew[row] = calculation.addition(attributesNew[row], aHalf);
			
			Value v = attributesNew[row];
			Object o = v.getObject(Type.floatingpoint);
			if (o instanceof NoObject) {
				if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
					System.out.println("FunctionByMatrix.calculateAttributesSimply > set: o (getObject(Type.floatingpoint)) is NoObject " + ((NoObject)o).getReason().toString() );
				}
				// do nothing
			}
			else {
				result.set(row, ((Float) o).intValue());
			}
			
		}

		return result;
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
	private AttributeArray calculateAttributes(AttributeArray attributes, boolean withOffset ) {
		
		
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
		ValueArrayList arguments;

		float change;

		AttributeArray result = new AttributeArray(attributes);

		length = attributes.length();
		attributesNew = new float[length];
		arguments = new ValueArrayList(1);
		
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

				arguments.set(0,  inputValue);
				
				Value v = calculation.addition(
						calculation.multiplication(share, function.calculate(arguments)),
						offset);
				Object o = v.getObject(Type.floatingpoint);
				if (o instanceof NoObject) {
					if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
						System.out.println("FunctionByMatrix.calculateAttributes > change: o (getObject(Type.floatingpoint)) is NoObject " + ((NoObject)o).getReason().toString() );
					}
					change = 0;
				}
				else {
					change = (float) o;
				}

				if (change != 0)
					attributesNew[column] += change;

			}
		}

		if (this.matrix.isWithDiv100() ) {
			for (row = 0; row < length; row++) {
				 attributesNew[row] = attributesNew[row] / 100;
			}
		}
		
		
		// rounding the attribute values to integer values
		// writing them from help array to main array
		for (row = 0; row < length; row++) {
			
			result.set(row, (int) getMinMaxedFloat(attributesNew[row] + 0.5F));
			
		}


		return result;

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
