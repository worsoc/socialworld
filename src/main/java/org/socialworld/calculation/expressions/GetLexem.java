/*
 * Social World
 * Copyright (C) 2020  Mathias Sikos
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

import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.functions.FunctionDynamic;
import org.socialworld.datasource.pool.FunctionPool;

public class GetLexem extends Expression {

	public GetLexem(Expression expressionTranslateToLexemType, List<Expression> expressions ) {
		
		super();
		

		setOperation(Expression_Function.function);
		
		FunctionDynamic function = FunctionDynamic.getInstance() ;
		setFunction(function);
		
		Expression calculateFunctionID = new Expression();
		calculateFunctionID.setOperation(Expression_Function.addition);
		calculateFunctionID.setExpression1(new Constant(new Value(Type.integer, FunctionPool.OFFSET_FUNCTIONID_GET_LEXEM)));
		calculateFunctionID.setExpression2(expressionTranslateToLexemType);
		setExpression1(calculateFunctionID);
		
		Expression toArgumentArray;
		toArgumentArray = new AddOrSetValuesToArguments(expressions);
		setExpression2(toArgumentArray);
		
		setValid();
		
	}

}
