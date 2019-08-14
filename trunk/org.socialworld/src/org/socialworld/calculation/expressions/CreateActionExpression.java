package org.socialworld.calculation.expressions;

import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_ConditionOperator;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

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
				exp3 = new Expression();
			}
			
			setExpression1(exp1);
			setExpression2(exp2);
			setExpression3(exp3);
	
			setValid();
		}
		
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
				
				disjunctionParts[0] = parseConjunction(listORs[i]);
				
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
		
		int posDann = line.indexOf("DANN");
		
		partDANN = line.substring(posDann);
		
		
		actionValue = new  Expression();
		
		
			
		return actionValue;
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
		
		String[] conditionElements = condition.split("\\s+");
		
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
