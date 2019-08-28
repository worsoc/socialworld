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
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.datasource.parsing.ParseExpressionStrings;

public class CreateActionExpression extends Expression {
	
	public CreateActionExpression(String[] lines) {
		
		super();
		
		if (lines.length > 0)
		{
			String line;
			
			Expression exp1;  // WENN
			Expression exp2;  // DANN
			Expression exp3;  // SONST
				
			line = lines[0];
			exp1 = parseWenn(line);
			exp2 = parseDann(line);
			
			if (lines.length > 1) {
				exp3 = parseLinesTail(1, lines);
			}
			else {
				exp3 = new CreateValue(Type.action, Nothing.getInstance());

			}
			
			setExpression1(exp1);
			setExpression2(exp2);
			setExpression3(exp3);

			setOperation(Expression_Function.branching);

			setValid();
		}
		
	}

	public CreateActionExpression(String line) {
		
		super();
			
		Expression exp1;  // WENN
		Expression exp2;  // DANN
		Expression exp3;  // SONST (doesn't exist here  --> action Nothing)
			
		exp1 = parseWenn(line);
		exp2 = parseDann(line);
		exp3 = new CreateValue(Type.action, Nothing.getInstance());
		
		setExpression1(exp1);
		setExpression2(exp2);
		setExpression3(exp3);

		setOperation(Expression_Function.branching);

		setValid();
		
	}

	
	private Expression parseLinesTail(int index, String[] lines) {
		
		String line;
		
		Expression wenn;
		Expression dann;
		Expression tail;
		
		line = lines[index];
		wenn = parseWenn(line);
		dann = parseDann(line);
		
		if (index == (lines.length - 1)) 
			 tail = new Expression();
		else
			 tail = parseLinesTail(index + 1, lines);
		
		return new Branching(wenn, dann, tail);
			
		
	}
	
	private Expression parseWenn(String line) {
		
		String partWENN;
		
		int posWenn = line.indexOf("WENN");
		int posDann = line.indexOf("DANN");
		
		partWENN = line.substring(posWenn + 4, posDann);
		
		String[] listORs = partWENN.split("\\|");
		int countORs = listORs.length;

		if (countORs > 1) {
			
			Expression[] disjunctionParts = new Expression[countORs];
	
			for (int i = 0; i < countORs; i++) {
				
				disjunctionParts[i] = parseConjunction(listORs[i]);
				
			}
			
			return new Junction(Expression_ConditionOperator.or, disjunctionParts);
			
		}
		else {
			
			return parseConjunction(listORs[0]);
			
		}
		
	}
	
	private Expression parseDann(String line) {
		
		String partDANN;
		
		Expression actionValue;
		Expression replacementChain;
		Expression[] sequence = new Expression[2];
		Expression createAction;
		
		String[] functionTags = {"Const", "Table", "VSPE", "MX+N","MLogX+N", "MExpX+N"};
		String[] function;
		
		int posDann = line.indexOf("DANN");
		
		partDANN = line.substring(posDann);
		
		
		String tagValueActionType = ParseExpressionStrings.getTagValue(partDANN, "TYPE");
		String tagValueActionMode = ParseExpressionStrings.getTagValue(partDANN, "MODE");
		String tagValueMinTime = ParseExpressionStrings.getTagValue(partDANN, "MIN_TIME");
		String tagValueMaxTime = ParseExpressionStrings.getTagValue(partDANN, "MAX_TIME");
		String tagValuePriority = ParseExpressionStrings.getTagValue(partDANN, "PRIO");
		String tagValueIntensity = ParseExpressionStrings.getTagValue(partDANN, "INTENSITY");
		String tagValueDuration = ParseExpressionStrings.getTagValue(partDANN, "DURATION");
		
		function = ParseExpressionStrings.getTagValue(tagValueActionType, functionTags);
		actionValue = getFunctionExpression("actiontype", function);
		replacementChain = new Replacement("actiontype", actionValue);
		
		function = ParseExpressionStrings.getTagValue(tagValueActionMode, functionTags);
		actionValue = getFunctionExpression("actionmode", function);
		sequence[0] = new Replacement("actionmode", actionValue);
		sequence[1] = replacementChain;
		replacementChain = new Sequence(sequence);
		
		function = ParseExpressionStrings.getTagValue(tagValueMinTime, functionTags);
		actionValue = getFunctionExpression("mintime", function);
		sequence[0] = new Replacement("mintime", actionValue);
		sequence[1] = replacementChain;
		replacementChain = new Sequence(sequence);
		
		function = ParseExpressionStrings.getTagValue(tagValueMaxTime, functionTags);
		actionValue = getFunctionExpression("maxtime", function);
		sequence[0] = new Replacement("maxtime", actionValue);
		sequence[1] = replacementChain;
		replacementChain = new Sequence(sequence);

		function = ParseExpressionStrings.getTagValue(tagValueIntensity, functionTags);
		actionValue = getFunctionExpression("intensity", function);
		sequence[0] = new Replacement("intensity", actionValue);
		sequence[1] = replacementChain;
		replacementChain = new Sequence(sequence);
		
		function = ParseExpressionStrings.getTagValue(tagValuePriority, functionTags);
		actionValue = getFunctionExpression("priority", function);
		sequence[0] = new Replacement("priority", actionValue);
		sequence[1] = replacementChain;
		replacementChain = new Sequence(sequence);
		
		function = ParseExpressionStrings.getTagValue(tagValueDuration, functionTags);
		actionValue = getFunctionExpression("duration", function);
		sequence[0] = new Replacement("duration", actionValue);
		sequence[1] = replacementChain;
		replacementChain = new Sequence(sequence);
		
		createAction = new CreateValue(Type.action, replacementChain);
		return createAction;
	}
	
	private Expression getFunctionExpression(String property, String[] function) {
		
		Expression result = Nothing.getInstance();
		Type type;
		
		switch (function[0]) {
		case "Const":
			switch (property) {
			case  "actiontype": type = Type.actionType; break;
			case  "actionmode": type = Type.actionMode; break;
			case  "mintime": type = Type.time; break;
			case  "maxtime": type = Type.time; break;
			case  "intensity": type = Type.floatingpoint; break;
			case  "priority": type = Type.integer; break;
			case  "duration": type = Type.longinteger; break;
			default: type = Type.nothing;
			}
			result = new Constant(calculation.createValue(type,  Float.parseFloat(function[1] ))); break;
		case "Table":		result = new TableLookup(function[1]); ; break;
		case "VSPE":		result = new VectorScalarProduct(function[1]); ; break;
		case "MX+N":		result = new MXPlusN(function[1]); ; break;
		case "MLogX+N":		result = new MLogXPlusN(function[1]); ; break;
		case "MExpX+N":		result = new MExpXPlusN(function[1]); ; break;
		}
		return result;
		
	}
	
	private Expression parseConjunction(String conjunction) {
		
		String[] listANDs = conjunction.split("&");
		int countANDs = listANDs.length;
		
		if (countANDs > 1) {
			
			Expression[] conjunctionParts = new Expression[countANDs];
			
			for (int i = 0; i < countANDs; i++) {
				
				conjunctionParts[i] = parseCondition(listANDs[i].trim());
				
			}
			
			return new Junction(Expression_ConditionOperator.and, conjunctionParts);
		
		}
		else {
			
			return parseCondition(listANDs[0]);
			
		}
		
	}
	
	private Expression parseCondition(String condition) {
		
		String[] conditionElements = condition.trim().split("\\s+");
		
		// we assume 3 elements --> (attribute name, operator, value)
		if (conditionElements.length == 3) {
			
			String attributeName = conditionElements[0];
			String operator = conditionElements[1];
			String value = conditionElements[2];
			
			Expression attributeValue = new Expression();
			
			for (int i = 0; i < Attribute.NUMBER_OF_ATTRIBUTES; i++) {
				
				if (Attribute.getAttributeName(i).toString().equals(attributeName.toLowerCase()) ) {
					attributeValue = AttributeValue.getInstance(i);
					break;
				}
			}
			
			if (!attributeValue.isValid()) return attributeValue;

			Expression constant = new Constant(new Value(value, Type.integer ));
			
			Expression comparison = new Expression();
			
			for (int i = 0; i < Expression_ConditionOperator.NUMBER_OF_COMPARISON_OPERATORS; i++) {
				
				if (Expression_ConditionOperator.getName(i).toString().equals(operator) ) {
					comparison = new Comparison(Expression_ConditionOperator.getName(i), attributeValue, constant );
					break;
				}
			}
			
			return comparison;
			
		}
		else
			return new Expression();

	}
	
}
