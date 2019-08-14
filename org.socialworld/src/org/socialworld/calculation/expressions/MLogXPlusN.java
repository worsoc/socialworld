package org.socialworld.calculation.expressions;

import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.FunctionMtimesLogXplusN;
import org.socialworld.calculation.Functions;

public class MLogXPlusN extends Expression {

	public MLogXPlusN(String formula) {
		
		super();
		
		String elements[];
		elements = formula.split(";");

		if ( (elements.length == 6) | (elements.length == 4) ) {
		
			FunctionMtimesLogXplusN function = null;

			int attributeIndex = Integer.parseInt(elements[0].trim());
			
			if ((attributeIndex >= 0) & (attributeIndex < Attribute.NUMBER_OF_ATTRIBUTES)) {
				
				if (elements.length == 6) {
					
					float base = Float.parseFloat(elements[1].trim());
					float m = Float.parseFloat(elements[2].trim());
					float n = Float.parseFloat(elements[3].trim());
					float min = Float.parseFloat(elements[4].trim());
					float max = Float.parseFloat(elements[5].trim());
					
					function = new FunctionMtimesLogXplusN( base, m, n, min,  max, true);
					
				}
				
				if (elements.length == 4) {
					
					float base = Float.parseFloat(elements[1].trim());
					float m = Float.parseFloat(elements[2].trim());
					float n = Float.parseFloat(elements[3].trim());
					
					function = new FunctionMtimesLogXplusN( base, m, n);
					
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
