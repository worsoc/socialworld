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

public class Branching extends Expression {

	public Branching (Expression expIF, Expression expTHEN, Expression expELSE) {
		
		super();
		
		
		setExpression1(expIF);
		setExpression2(expTHEN);
		setExpression3(expELSE);
		
		setOperation(Expression_Function.branching);
		setValid();
		
	}
	
	protected Branching() {
		super();
	}
	
	protected Expression parseWenn(String line) {
		
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
		
		// in case of 3 elements --> (attribute name, operator, value)
		if (conditionElements.length == 3) {
			
			return parseAttributeCondition(conditionElements);
			
		}
		
		// in case of 4 elements --> (type, event property name, operator, value)
		if (conditionElements.length == 4) {
			
			return parseEventPropsCondition(conditionElements);
			
		}
		
		return new Expression();

	}
	

	private Expression parseAttributeCondition(String[] conditionElements) {
			
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
	
	private Expression parseEventPropsCondition(String[] conditionElements) {
		
		// type as Type's index (look at the enum Type)
		String type = conditionElements[0];
		
		// the value list element name (the name of one value in the value list)
		String propertyName = conditionElements[1];
		
		String operator = conditionElements[2];
		
		// the value the event's property is compared to
		String value = conditionElements[3];
		
		Expression propertyValue = new ValueFromValueList(Value.ARGUMENT_VALUE_BY_NAME_EVENT_PARAMS, propertyName);
		
		Expression constant = new Constant(new Value(value, Type.getName(Integer.parseInt(type)) ) );
		
		Expression comparison = new Expression();
		
		for (int i = 0; i < Expression_ConditionOperator.NUMBER_OF_COMPARISON_OPERATORS; i++) {
			
			if (Expression_ConditionOperator.getName(i).toString().equals(operator) ) {
				comparison = new Comparison(Expression_ConditionOperator.getName(i), propertyValue, constant );
				break;
			}
		}
		
		return comparison;
			
	}


}
