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
import org.socialworld.calculation.SimulationCluster;
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
	
	protected Expression parseWenn(SimulationCluster cluster, PropertyUsingAs usablePermission, String line, boolean withWENNDANN ) {
		
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
				
				disjunctionParts[i] = parseConjunction(cluster, usablePermission, listORs[i]);
				
			}
			
			return new Junction(Expression_ConditionOperator.or, disjunctionParts);
			
		}
		else {
			
			return parseConjunction(cluster, usablePermission, listORs[0]);
			
		}
		
	}
	
	private Expression parseConjunction(SimulationCluster cluster, PropertyUsingAs usablePermission, String conjunction) {
		
		String[] listANDs = conjunction.split("&");
		int countANDs = listANDs.length;
		
		if (countANDs > 1) {
			
			Expression[] conjunctionParts = new Expression[countANDs];
			
			for (int i = 0; i < countANDs; i++) {
				
				conjunctionParts[i] = parseCondition(cluster, usablePermission, listANDs[i].trim());
				
			}
			
			return new Junction(Expression_ConditionOperator.and, conjunctionParts);
		
		}
		else {
			
			return parseCondition(cluster, usablePermission, listANDs[0]);
			
		}
		
	}
	
	
	
	private Expression parseCondition(SimulationCluster cluster, PropertyUsingAs usablePermission, String condition) {

		String[] conditionElements = condition.trim().split("\\s+");
		
		// in case of 3 elements --> (attribute name, operator, value)
		if (conditionElements.length == 3) {
			
			return parseAttributeCondition(conditionElements);
			
		}
		
		// in case of 4 elements --> (type, get value path, operator, value)
		//						 --> (type, event property name, operator, value)
		if (conditionElements.length == 4) {
			
			if (conditionElements[1].indexOf("(") > 0) {
				// the get value path contains at least one "(" for GETVal(...), GETProp(...) or  GETFctVal(...)
				return parseStateCondition(cluster, usablePermission, conditionElements);
			}
			else {
				return parseEventPropsCondition(cluster, usablePermission, conditionElements);
			}
			
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
				attributeValue = GetAttributeValue.getInstance(i);
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
	
	private Expression parseEventPropsCondition(SimulationCluster cluster, PropertyUsingAs usablePermission, String[] conditionElements) {
		
		// type as Type's index (look at the enum Type)
		String type = conditionElements[0];
		
		// the value list element name (the name of one value in the value list)
		String propertyName = conditionElements[1];
		
		String operator = conditionElements[2];
		
		// the value the event's property is compared to
		String value = conditionElements[3];
		
		Expression propertyValue = new GetValueFromValueList(Value.VALUE_BY_NAME_EVENT_PARAMS, propertyName);
		
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

	private Expression parseStateCondition(SimulationCluster cluster, PropertyUsingAs usablePermission, String[] conditionElements) {
		
		// type as Type's index (look at the enum Type)
		String type = conditionElements[0];
		
		// the get value path 
		String getValuePath = conditionElements[1];
		
		String operator = conditionElements[2];
		
		// the value the event's property is compared to
		String value = conditionElements[3];
		
		Expression getValue = new GetValue(cluster, usablePermission, getValuePath, Value.VALUE_NAME_UNUSED_BECAUSE_TEMPORARY);
		
		Expression constant = new Constant(new Value(value, Type.getName(Integer.parseInt(type)) ) );
		
		Expression comparison = new Expression();
		
		for (int i = 0; i < Expression_ConditionOperator.NUMBER_OF_COMPARISON_OPERATORS; i++) {
			
			if (Expression_ConditionOperator.getName(i).toString().equals(operator) ) {
				comparison = new Comparison(Expression_ConditionOperator.getName(i), getValue, constant );
				break;
			}
		}
		
		return comparison;
			
	}

}
