/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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

package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.FunctionByMatrix;
import org.socialworld.calculation.FunctionByMatrix_Matrix;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.ValueInterpreteAs;

public class VectorScalarProduct extends Expression {

	public VectorScalarProduct(String formula, ValueInterpreteAs interpreteResultAs) {
		
		super();
		init(formula, interpreteResultAs );
	}	
	
	public VectorScalarProduct(String formula) {
		
		super();
		init(formula, ValueInterpreteAs.nothing );
	}	
	
	private void init(String formula, ValueInterpreteAs interpreteResultAs)	{
		String elements[];
		elements = formula.split(";");

		if  (elements.length == 3)  {
		
			String[] shares = elements[0].split(",");
			String[] functions = elements[1].split(",");
			String[] offsets = elements[2].split(",");
			
			Value[] sharesValue = new Value[shares.length];
			for (int i = 0; i < shares.length; i++) sharesValue[i] = new Value(shares[i].trim(), Type.floatingpoint);

			int[] functionIDs = new int[functions.length];
			for (int i = 0; i < functions.length; i++) functionIDs[i] = Integer.parseInt(functions[i].trim());

			Value[] offsetsValue = new Value[offsets.length];
			for (int i = 0; i < offsets.length; i++) offsetsValue[i] = new Value(offsets[i].trim(), Type.floatingpoint);
		
			FunctionByMatrix_Matrix matrix  = new FunctionByMatrix_Matrix(sharesValue, functionIDs, offsetsValue);
			FunctionByMatrix function = new FunctionByMatrix(interpreteResultAs, matrix);
					
	
			int freeFunctionIndex = this.functions.findNextFree(100, 200);
			
			if (freeFunctionIndex > 0) {
			
				this.functions.set(freeFunctionIndex, function);
				
				setFuncID(freeFunctionIndex);
				setFunction(function);
				
				setOperation(Expression_Function.function);
				
				setExpression1(new GetArgumentByName(Value.ARGUMENT_VALUE_BY_NAME_ATTRIBUTES));
				setExpression2(new Constant(new Value(Type.integer, FunctionByMatrix_Matrix.CALCULATION_MODE_VECTOR_X_VECTOR)));
				setExpression3(Nothing.getInstance());
				
				setValid();

			}
				
			
		}	
	}
	
}
