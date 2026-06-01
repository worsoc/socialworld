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
import org.socialworld.calculation.Expression_ConditionOperator;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.core.IAccessToken;

public class Branching extends Expression {

	private Branching (Expression expIF, Expression expTHEN, Expression expELSE, Expression_Function operation) {
		
		super();
		
		// chechking operation for being a branching operation
		if (operation == Expression_Function.branching || operation == Expression_Function.probability) {
			setExpression1(expIF);
			setExpression2(expTHEN);
			setExpression3(expELSE);
			
			setOperation(operation);
			setValid();
		}
		
	}
	
	protected Branching() {
		super();
	}
	
	public static Expression createBranchingSmart(
	        Expression condition,
	        Expression thenExpr,
	        Expression elseExpr) {

	    if (Comparison.isAttributeComparison(condition)) {
	        return buildProbability(condition, thenExpr, elseExpr);
	    }

	    return new Branching(condition, thenExpr, elseExpr, Expression_Function.branching);
	}

	static Expression parseWenn(IAccessToken token, PropertyUsingAs usablePermission, String line, boolean withWENNDANN ) {
		
		String partWENN;
		
		if (withWENNDANN) {
			int posWenn = line.indexOf("WENN");
			int posDann = line.indexOf("DANN");
			
			partWENN = line.substring(posWenn + 4, posDann);
		}
		else {
			partWENN = line;
		}
		
		String[] listORs = partWENN.split("\\|");
		int countORs = listORs.length;

		if (countORs > 1) {
			
			Expression[] disjunctionParts = new Expression[countORs];
	
			for (int i = 0; i < countORs; i++) {
				
				disjunctionParts[i] = parseConjunction(token, usablePermission, listORs[i]);
				
			}
			
			return new Junction(Expression_ConditionOperator.or, disjunctionParts);
			
		}
		else {
			
			return parseConjunction(token, usablePermission, listORs[0]);
			
		}
		
	}
	
	private static Expression parseConjunction(IAccessToken token, PropertyUsingAs usablePermission, String conjunction) {
		
		String[] listANDs = conjunction.split("&");
		int countANDs = listANDs.length;
		
		if (countANDs > 1) {
			
			Expression[] conjunctionParts = new Expression[countANDs];
			
			for (int i = 0; i < countANDs; i++) {
				
				conjunctionParts[i] = parseCondition(token, usablePermission, listANDs[i].trim());
				
			}
			
			return new Junction(Expression_ConditionOperator.and, conjunctionParts);
		
		}
		else {
			
			return parseCondition(token, usablePermission, listANDs[0]);
			
		}
		
	}
	
	
	
	private static Expression parseCondition(IAccessToken token, PropertyUsingAs usablePermission, String condition) {

		String[] conditionElements = condition.trim().split("\\s+");
		
		// in case of 3 elements --> (attribute name, operator, value)
		//						 --> (float function, operator, value)
		if (conditionElements.length == 3) {
			
			if (conditionElements[0].indexOf("function:") >= 0) {
				// the function is a term construction with triples (function name, operator1, operator2)+-----
				return parseFunctionCondition(token, usablePermission, conditionElements);
			}
			else {
				return parseAttributeCondition(conditionElements);
			}
			
		}
		
		// in case of 4 elements --> (type, get value path, operator, value)
		//						 --> (type, event property name, operator, value)
		if (conditionElements.length == 4) {
			
			if (conditionElements[1].indexOf("(") > 0) {
				// the get value path contains at least one "(" for GETVal(...), GETProp(...) or  GETFctVal(...)
				return parseStateCondition(token, usablePermission, conditionElements);
			}
			else {
				return parseEventPropsCondition(token, usablePermission, conditionElements);
			}
			
		}
		
		return Expression.getDefaultInvalid();

	}
	

	private static  Expression parseAttributeCondition(String[] conditionElements) {
			
		String attributeName = conditionElements[0];
		String operator = conditionElements[1];
		String value = conditionElements[2];
		
		Expression attributeValue = new Expression();
	
	    Attribute attr = Attribute.fromName(attributeName);
	    if (attr != null && attr != Attribute.ignore) {
	        attributeValue = GetAttributeValue.getInstance(attr.getIndex());
	    }
		
		if (!attributeValue.isValid()) return attributeValue;

		Expression constant = new Constant(new Value(value, Type.integer ));
		
		Expression comparison = Expression.getDefaultInvalid();

		Expression_ConditionOperator foundOperator = Expression_ConditionOperator.fromName(operator);
		if (foundOperator != null && foundOperator.getIndex() < Expression_ConditionOperator.NUMBER_OF_COMPARISON_OPERATORS) {
		    comparison = new Comparison(foundOperator, attributeValue, constant);
		} 
		
		return comparison;
			
	}
	
	private static Expression parseFunctionCondition(IAccessToken token, PropertyUsingAs usablePermission, String[] conditionElements) {
		
	
		// the function description
		String functionDescription = conditionElements[0];
		
		// the operator for comparison (here always in data type floating point
		String operator = conditionElements[1];
		
		// the value the function result is compared to (here always type floating point
		String value = conditionElements[2];
		
		Expression calculateFuntionResult = Calculate.getCalculateExpression(functionDescription,  Value.VALUE_NAME_UNUSED_BECAUSE_TEMPORARY); 

		Expression constant = new Constant(new Value(value, Type.floatingpoint ));

		Expression comparison = Expression.getDefaultInvalid();
		
		for (int i = 0; i < Expression_ConditionOperator.NUMBER_OF_COMPARISON_OPERATORS; i++) {
			
			if (Expression_ConditionOperator.getName(i).toString().equals(operator) ) {
				comparison = new Comparison(Expression_ConditionOperator.getName(i), calculateFuntionResult, constant );
				break;
			}
		}
		
		return comparison;
			
	}

	private static Expression parseEventPropsCondition(IAccessToken token, PropertyUsingAs usablePermission, String[] conditionElements) {
		
		// type as Type's index (look at the enum Type)
		String type = conditionElements[0];
		
		// the value list element name (the name of one value in the value list)
		String propertyName = conditionElements[1];
		
		String operator = conditionElements[2];
		
		// the value the event's property is compared to
		String value = conditionElements[3];
		
		Expression propertyValue = new GetValueFromValueList(Value.VALUE_BY_NAME_EVENT_PARAMS, propertyName);
		
		Expression constant = new Constant(new Value(value, Type.getName(Integer.parseInt(type)) ) );
		
		Expression comparison = Expression.getDefaultInvalid();
		
		for (int i = 0; i < Expression_ConditionOperator.NUMBER_OF_COMPARISON_OPERATORS; i++) {
			
			if (Expression_ConditionOperator.getName(i).toString().equals(operator) ) {
				comparison = new Comparison(Expression_ConditionOperator.getName(i), propertyValue, constant );
				break;
			}
		}
		
		return comparison;
			
	}

	private static Expression parseStateCondition(IAccessToken token, PropertyUsingAs usablePermission, String[] conditionElements) {
		
		// type as Type's index (look at the enum Type)
		String type = conditionElements[0];
		
		// the get value path 
		String getValuePath = conditionElements[1];
		
		String operator = conditionElements[2];
		
		// the value the state's property is compared to
		String value = conditionElements[3];
		
		Expression getValue = new GetValue(token, usablePermission, getValuePath, Value.VALUE_NAME_UNUSED_BECAUSE_TEMPORARY);
		
		Expression constant = new Constant(new Value(value, Type.getName(Integer.parseInt(type)) ) );
		
		Expression comparison = Expression.getDefaultInvalid();
		
		for (int i = 0; i < Expression_ConditionOperator.NUMBER_OF_COMPARISON_OPERATORS; i++) {
			
			if (Expression_ConditionOperator.getName(i).toString().equals(operator) ) {
				comparison = new Comparison(Expression_ConditionOperator.getName(i), getValue, constant );
				break;
			}
		}
		
		return comparison;
			
	}

	
	
	
	private static Expression buildProbability(
	        Expression cmp,
	        Expression thenExpr,
	        Expression elseExpr) {


	    // k = fixe Steilheit
	    //Expression kExpr = Constant.FLOATINGPOINT_5_DOT_0;
	    Expression kExpr =new Constant(
		        new Value(Type.floatingpoint, 5.0f)
		    );

	    // SIGMOID
	    Expression sigmoid = new Expression();
	    sigmoid.copyFromExpression(cmp);
	    sigmoid.setOperation(Expression_Function.sigmoid);
	    sigmoid.setExpression3(kExpr);
	    sigmoid.setValid();

	    // Richtung beachten
	    Expression probExpr = sigmoid;

	    if (cmp.checkOperatorIsLessOrLessEqual()) {
            probExpr = invert(sigmoid);
	    }
	    else if (cmp.checkOperatorIsGreaterOrGreaterEqual()) {
	    	// it stays probability mode
	    }
	    else {
	    	// fallback to Branching with comparison
            return new Branching(cmp, thenExpr, elseExpr, Expression_Function.branching);
	    }

	    // PROBABILITY Node
	    Expression probability = new Branching(probExpr, thenExpr, elseExpr, Expression_Function.probability);

	    return probability;
	}

	
	private static Expression invert(Expression sigmoid) {

	 //   Expression one = Constant.FLOATINGPOINT_1_DOT_0;
	    Expression one = new Constant(
		        new Value(Type.floatingpoint, 1.00F)
		    );

	    Expression result = new Expression();
	    result.setOperation(Expression_Function.subtraction);
	    result.setExpression1(one);
	    result.setExpression2(sigmoid);
	    result.setValid();

	    return result;
	}

}
