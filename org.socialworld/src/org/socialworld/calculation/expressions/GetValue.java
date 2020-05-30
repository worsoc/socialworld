/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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

import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public class GetValue extends Expression {

	public static String GETVALUE = "GETVal";
	public static String GETPROPERTY = "GETProp";
	public static String GETFUNCTIONVALUE = "GETFctVal";
	
	public GetValue(PropertyUsingAs usablePermission, String getValuePath, String valueAliasName) {
		
		super();
		
		String steps[];
		steps = getValuePath.split(".");
		
		if (steps.length > 0) {
			setOperation(Expression_Function.oneExpression);
			
			Expression exp1 = new GetValue(usablePermission, steps, 0, valueAliasName);
			
			setExpression1(exp1);
			setValid();
			
		}
		else {
			setOperation(Expression_Function.nothing);
		}
			
	}
	
	private GetValue(PropertyUsingAs usablePermission, String[] steps, int indexContinue, String valueAliasName) {
		
		Expression exp1 = Nothing.getInstance();
		Expression exp2 = Nothing.getInstance();
		String step;
		
		Value checkPermission;
		
		step = steps[indexContinue];
		exp1 = getStepExpression(step, valueAliasName);

		if (steps.length == indexContinue + 1) {
			// that is the value from expression 1,
			// because the expression 1 evaluation result
			// is forwarded (as argument value list array with that one element)  to expression 2 evaluation
			exp2 = new GetArgumentByName(valueAliasName, valueAliasName);
			checkPermission = new Value(Type.integer, usablePermission.getIndex());
		}
		
		if (steps.length == indexContinue + 2) {
			step = steps[indexContinue + 1];
			exp2 = getStepExpression(step, valueAliasName);
			checkPermission = new Value(Type.integer, usablePermission.getIndex() - 1);
		}
		else {
				
			exp2 = new GetValue(usablePermission, steps, indexContinue + 1, valueAliasName);
			checkPermission = new Value(Type.integer, usablePermission.getIndex() - 1);

		}

		setOperation(Expression_Function.get);
		setValue(checkPermission);
		setExpression1(exp1);
		setExpression2(exp2);
		setExpression3(Nothing.getInstance());
		
		setValid();

	}
	
	public static String getValue(PropertyName prop) {
		return GETVALUE + "(" + prop.toString() + ")"; 
	}
	
	public static String getValue(String name) {
		return GETVALUE + "(" + name + ")"; 
	}

	public static String getProperty(PropertyName prop) {
		return GETPROPERTY + "(" + prop.toString() + ")"; 
	}
	
	public static String getProperty(String name) {
		return GETPROPERTY + "(" + name + ")"; 
	}

	public static String getFctValue(String name) {
		return GETFUNCTIONVALUE + "(" + name + ")"; 
	}
	
	private Expression getStepExpression(String step, String valueAliasName) {
		Expression result = Nothing.getInstance();
		String name;
		
		name = step.substring(step.indexOf("(") + 1, step.indexOf(")"));
		if (step.indexOf(GETVALUE + "(") > 0 ) {
			result = new GetArgumentByName(name, valueAliasName);
		}
		if (step.indexOf(GETPROPERTY + "(") > 0 ) {
			result = new GetProperty(PropertyName.forString(name), valueAliasName);
		}
		if (step.indexOf(GETFUNCTIONVALUE + "(") > 0 ) {
			result = new GetProperty(name, valueAliasName);
		}

		return result;
	}

}
