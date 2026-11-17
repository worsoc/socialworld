/*
 * Social World
 * Copyright (C) 2019  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.calculation.expressions;

import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.functions.FunctionByTable_Type;
import org.socialworld.calculation.FunctionBase;
import org.socialworld.datasource.pool.FunctionPool;

public class TableLookup extends Expression {

	public TableLookup(String formula) {
		
		super();
		
		String elements[];
		elements = formula.split(";");

		if ( elements.length == 2)  {
		
			FunctionBase function = null;

			int attributeIndex = Integer.parseInt(elements[0].trim());
			
			if ((attributeIndex >= 0) & (attributeIndex < Attribute.NUMBER_OF_ATTRIBUTES)) {
				
				FunctionByTable_Type type = FunctionByTable_Type.getType(Integer.parseInt(elements[1].trim()));
				if (type == null) return;
				// the table functions are in the pool under the index of their type
				function = FunctionPool.getInstance().getFunction(type.getIndex());
	
				setFuncID(type.getIndex());
				setFunction(function);
				
				setOperation(Expression_Function.function);
				
				setExpression1(GetAttributeValue.getInstance(attributeIndex));
				setExpression2(Nothing.getInstance());
				setExpression3(Nothing.getInstance());
				
				setValid();
	
				
			}
		}	
	}
	
}
