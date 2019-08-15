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

import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.FunctionMtimesExpXplusN;
import org.socialworld.calculation.Functions;

public class MExpXPlusN extends Expression {

	public MExpXPlusN(String formula) {
		
		super();
		
		String elements[];
		elements = formula.split(";");

		if ( (elements.length == 6) | (elements.length == 4) ) {
		
			FunctionMtimesExpXplusN function = null;

			int attributeIndex = Integer.parseInt(elements[0].trim());
			
			if ((attributeIndex >= 0) & (attributeIndex < Attribute.NUMBER_OF_ATTRIBUTES)) {
				
				if (elements.length == 6) {
					
					float base = Float.parseFloat(elements[1].trim());
					float m = Float.parseFloat(elements[2].trim());
					float n = Float.parseFloat(elements[3].trim());
					float min = Float.parseFloat(elements[4].trim());
					float max = Float.parseFloat(elements[5].trim());
					
					function = new FunctionMtimesExpXplusN( base, m, n, min,  max, true);
					
				}
				
				if (elements.length == 4) {
					
					float base = Float.parseFloat(elements[1].trim());
					float m = Float.parseFloat(elements[2].trim());
					float n = Float.parseFloat(elements[3].trim());
					
					function = new FunctionMtimesExpXplusN( base, m, n);
					
				}
	
				int freeFunctionIndex = Functions.findNextFree(100, 200);
				
				if (freeFunctionIndex > 0) {
				
					Functions.set(freeFunctionIndex, function);
					
					setFuncID(freeFunctionIndex);
					setFunction(function);
					
					setOperation(Expression_Function.function);
					
					setExpression1(AttributeValue.getInstance(attributeIndex));
					setExpression2(Nothing.getInstance());
					setExpression3(Nothing.getInstance());
					
					setValid();
	
				}
				
			}
		}	
	}
	
}
