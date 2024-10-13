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
		// QUESTION ? use offset constant from Functions instead of FunctionsPool ? is class Functions obsolete ?
		calculateFunctionID.setExpression1(new Constant(new Value(Type.integer, FunctionPool.OFFSET_FUNCTIONID_GET_LEXEM)));
		calculateFunctionID.setExpression2(expressionTranslateToLexemType);
		setExpression1(calculateFunctionID);
		
		Expression toArgumentArray;
		toArgumentArray = new AddOrSetValuesToArguments(expressions);
		setExpression2(toArgumentArray);
		
		setValid();
		
	}

}
