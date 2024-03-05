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
package org.socialworld.calculation;


import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.expressions.Nothing;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.objects.SimulationObject;

/**
 * The class is an implementation of an
 *         expression. The expression is part of a term that consists of
 *         mathematical comparisons and (mathematical) operations which are
 *         executed dependent to the evaluation result of the comparison. An
 *         expression is a comparison to a constant OR addition of a constant 
 *          OR a multiplication OR a replacement 
 *         If the expression is a comparison there are given two
 *         further expressions for the boolean evaluation result of the
 *         comparison. So a expression evaluates an expression recursively.

 * @author Mathias Sikos (MatWorsoc)
 */
public class Expression {
	int ID;
	
	Expression_Function operation;
	Expression_ConditionOperator operator;
	
	
	
	// because of circular relations (expressions can be members to functions (see FunctionsByExpression))
	// at first the function id is set to the expressions
	// later the function id will be mapped to a function object that will be set to the expression
	private int func_id;
	FunctionBase function;
	
	Value value;
	
	Expression expression1;
	Expression expression2;
	Expression expression3;
	
		
	private boolean isValid;
	
	protected Calculation calculation;
	protected Functions functions;

	protected Expression(Expression_Function nothing) {
		if (nothing.equals(Expression_Function.nothing)) {
			setNothing();
		}
		else {
			isValid = false;
		}
	}
	
	public Expression() {
		calculation = Calculation.getInstance();
		functions = Functions.getInstance();
		operation = Expression_Function.nothing;
		
		setExpression1(Nothing.getInstance());
		setExpression2(Nothing.getInstance());
		setExpression3(Nothing.getInstance());
		
		isValid = false;
	}
	
	public void setNothing() {
		
		calculation = Calculation.getInstance();
		functions = Functions.getInstance();
		operation = Expression_Function.nothing;
		isValid = true;
		
	}
	public void setValid() {
		isValid = true;
	}

	public boolean isValid() { 
		return isValid;
	}
	
	public void setOperation(Expression_Function operation) {
		if (!isValid) this.operation = operation;
	}

	public Expression_Function getOperation() {
		return this.operation;
	}
	
	public void setOperator(Expression_ConditionOperator operator) {
		if (!isValid) this.operator = operator;
	}

	public void setFuncID(int func_id) {
		if (!isValid) this.func_id = func_id;
	}
	
	public int getFuncID() {
		return this.func_id;
	}
	
	public void setFunction(FunctionBase function) {
		if (!isValid) this.function = function;
	}
	
	public void setExpression1(Expression expression) {
		if (!isValid) this.expression1 = expression;
	}
	
	public void setExpression2(Expression expression) {
		if (!isValid) this.expression2 = expression;
	}

	public void setExpression3(Expression expression) {
		if (!isValid) this.expression3 = expression;
	}

	
	public void setTrueExpression(Expression expressionForTrue) {
		if (!isValid) setExpression2(expressionForTrue);
	}

	public void setFalseExpression(Expression expressionForFalse) {
		if (!isValid) setExpression3(expressionForFalse);
	}

	public void setValue(Value value) {
		if (!isValid) this.value = value;
	}

	protected Value getValue() {
		return value;
	}
	
		
	Value evaluate() {
		ValueArrayList noArguments = null;
		return evaluate(noArguments);
	}

	public Value evaluate(ValueArrayList arguments) {
		
		Value tmp;
		String name;
		int index;
		
		ValueArrayList valueList;
		
		if (this.isValid()) {
			
			try {
			
			
				switch (this.operation) {
				case skip:
					return arguments.get(arguments.size() - 1);
				case nothing:
					//return invalid dummy-Value
					return Calculation.getNothing();
					
				case value:
					return calculation.copy(value);
										
				case attributeValue:
					AttributeArray attributeArray = AttributeArray.getObjectNothing();
					attributeArray = (AttributeArray) getFromValueArrayList( arguments, Type.attributeArray, 1);
					index = (int) value.getObject();
					return calculation.createValue(
						Type.integer,
						attributeArray.get(index ));
						
				case argumentValueByName:
					
					name = (String) value.getObject();
					tmp = arguments.getValue(name );
					if (tmp.isValid()) {
						tmp = calculation.copy(tmp);
					
						name = (String) expression1.evaluate(arguments).getObject();
						if (name != null && name.length() > 0) {
								tmp.changeName(name);
						}
					}
 					return tmp;
					
										
				case valueFromValueList:
					// get value list's name
					name =  (String) expression1.evaluate(arguments).getObject();
					// get the value list
					tmp = arguments.getValue(name);
					if (tmp.isValid()) {

						valueList = (ValueArrayList) tmp.getObject();
						// get the value list element's name
						name = (String) expression2.evaluate(arguments).getObject();
						// get the result value from the value list
						return valueList.getValue(name);
						
					}
					return Calculation.getNothing();
	
				case property:
					
					Value object = arguments.get(0);
					PropertyName simPropName = (PropertyName) value.getObject();
					SimulationCluster cluster = SimulationCluster.getName((int) expression1.evaluate(arguments).getObject());
					String methodName = (String) expression2.evaluate(arguments).getObject();
					name = (String) expression3.evaluate(arguments).getObject();
					
					return getProperty(object, cluster, simPropName, methodName, name);
				
				case get:  

					
					boolean checkSuccess = false;
					ValueProperty property;
					
					tmp = expression1.evaluate(arguments);
					int checkUseAsPermission = 0;
					if (tmp.isValid()) {
						checkUseAsPermission = (int) value.getObject();
						if (checkUseAsPermission > 0) {
							if (tmp instanceof ValueProperty) {
								property = (ValueProperty) tmp;
								if ( property.checkHasUseAsPermission(PropertyUsingAs.getName(checkUseAsPermission)) ) {
									checkSuccess = true;
								}
							}
							else {
								// just instanceof Value
								// TODO checkHasUseAsPermission for different types
								checkSuccess = true;
							}
						}
						else {
							// no check needed --> true
							checkSuccess = true;
						}
					}
					else {
						checkSuccess = false;
					}
					
					if (checkSuccess) {
						if ((tmp.hasType(Type.valueList)) && (tmp.getObject() instanceof ValueArrayList)) {
							valueList = (ValueArrayList) tmp.getObject();
						}
						else {
							valueList	= new ValueArrayList();
							valueList.add(tmp);
						}
						tmp = expression2.evaluate(valueList);
						if (tmp instanceof ValueProperty) {
							property = (ValueProperty) tmp;
							if (property.getUsing() == null) {
								property.setUsing(PropertyUsingAs.getName(checkUseAsPermission));
							}
						}
					}
					else {
						tmp = ValueProperty.getInvalid();
					}
					
					return tmp;
					
				case branching:
					tmp = expression1.evaluate(arguments);
					if (tmp.isTrue()  ) 
						tmp =  expression2.evaluate(arguments);
					else
						tmp = expression3.evaluate(arguments);
					return tmp;
					
				case condition:
					
					switch (operator) {
					case or:
						return calculation.or(
								expression1.evaluate(arguments) ,
								expression2.evaluate(arguments)   );
					case and:
						return calculation.and(
								expression1.evaluate(arguments) ,
								expression2.evaluate(arguments)   );
					default:
						return calculation.createValue(Type.bool,  false);
					}
					
					
				case comparison :
					
					switch (operator) {
					case equal:
						return calculation.compareEqual(
								expression1.evaluate(arguments) ,
								expression2.evaluate(arguments)   );
					case notEqual:
						return calculation.compareNotEqual(
								expression1.evaluate(arguments) ,
								expression2.evaluate(arguments)   );
					case greater:
						return calculation.compareGreater(
								expression1.evaluate(arguments) ,
								expression2.evaluate(arguments)   );
					case greaterEqual:
						return calculation.compareGreaterEqual(
								expression1.evaluate(arguments) ,
								expression2.evaluate(arguments)   );
					case less:
						return calculation.compareLess(
								expression1.evaluate(arguments) ,
								expression2.evaluate(arguments)   );
					case lessEqual:
						return calculation.compareLessEqual(
								expression1.evaluate(arguments) ,
								expression2.evaluate(arguments)   );
					default:
						return calculation.createValue(Type.bool,  false);
					}
					
				case addition:
					return calculation.addition(
							expression1.evaluate(arguments) ,
							expression2.evaluate(arguments)   );
	
				case subtraction:
					return calculation.subtraction(
							expression1.evaluate(arguments) ,
							expression2.evaluate(arguments)   );
					
				case multiplication:
					return calculation.multiplication(
							expression1.evaluate(arguments) ,
							expression2.evaluate(arguments)   );
					
				case division:
					return calculation.division(
							expression1.evaluate(arguments) ,
							expression2.evaluate(arguments)   );
					
				case function:
					valueList = new ValueArrayList();
					valueList.add( expression1.evaluate(arguments) );
					valueList.add( expression2.evaluate(arguments) );
					valueList.add( expression3.evaluate(arguments) );
					return function.calculate(valueList);
					
				case oneExpression:
					tmp = expression1.evaluate(arguments);
					if (tmp.isValid()) {
						tmp = calculation.copy(tmp);
						if (this.value != null) {
							name = (String)this.value.getObject();
							if (name.length() > 0) {
								tmp.changeName(name);
							}
						}
					}
					return tmp;
					
				case sequence:
					expression1.evaluate(arguments);
					tmp = expression2.evaluate(arguments);
					if (expression3.operation != Expression_Function.skip
							&& expression3.operation != Expression_Function.nothing) {
						tmp = expression3.evaluate(arguments);
					}
					
					if (tmp.isValid()) {
						tmp = calculation.copy(tmp);
						if (this.value != null) {
							name = (String)this.value.getObject();
							if (name.length() > 0) {
								tmp.changeName(name);
							}
						}
					}	
					return tmp;
					
				case replacement:
				
					tmp = expression1.evaluate(arguments);
					if (tmp.isValid()) {
						
						tmp = calculation.copy(tmp);
						
						// is there a name for a sub list
						name = (String) expression2.evaluate().getObject();
						if (name.length() > 0) {
							if (arguments.findValue(name) < 0) {
								// if the sub list doesn't exist, then create it and add it to arguments
								valueList = new ValueArrayList();
								arguments.add(new Value(Type.valueList, name, valueList));
							}
							else {
								// get the sub list from arguments
								valueList = (ValueArrayList) arguments.getValue(name).getObject();
							}
						}
						else {
							// use the arguments
							valueList = arguments;
						}
						
						// get the name for the (expression1) evaluated value 
						name = (String) value.getObject();
						
						// just for debugging
						if (name.equals(Value.VALUE_BY_NAME_ACTION_TARGET)) {
							if (tmp.getObject() == null )
								System.out.println("Expression.evaluate: action target ist null "  );
							else 
								if ( tmp.getType() != Type.simulationObject)
									System.out.println("Expression.evaluate: action target ist nicht vom Type.simulationObject "  );
								else
									System.out.println("Expression.evaluate: action target: " + ((SimulationObject)tmp.getObject()).getObjectID()  );
						}

						if (name.length() > 0) {
							tmp.changeName(name);
							index = valueList.findValue(name);
							if (index >= 0) {
								valueList.set(index, tmp);
							}
							else {
								valueList.add(tmp);
							}
						}
						else {
							valueList.add(tmp);
						}
						
					}
					return tmp;
				
				case create:
					
					// TODO name for a created object, given from expression evaluation
					
					Type type;
					Value createdValue;;
					
					int subType;
					type = Type.getName((int) value.getObject());
					
					subType = (int) expression1.evaluate().getObject();
					name = (String) expression3.evaluate().getObject();
					
					createdValue = createValue(type, subType, name, arguments);

					return createdValue;
					
				default:
					return Calculation.getNothing();
				}
			
			}
			catch (Exception e) {
				System.out.println("evaluation expression: " + e.toString());
				return Calculation.getNothing();
			}
		
		}
		else {
			return Calculation.getNothing();
		}
		
	}
	
	

	private Object getFromValueArrayList(ValueArrayList arguments, Type type, int wantedOccurence) {
		
		Value value;
		value = arguments.getValue(type, wantedOccurence);
		
		if (value.isValid()) {
			return value.getObject();
		}
		else {
			return null;
		}
		
	}

	
	// will be overridden in inherited Expressions dedicated to getting ValueProperty
	protected ValueProperty getProperty(Value valueObject, SimulationCluster cluster, PropertyName simPropName, String methodName, String valueName) {
		return new ValueProperty(Type.nothing, "", null );
	}

	// will be overridden in inherited Expressions dedicated to creating values
	protected Value createValue(Type valueType, int subType, String name, ValueArrayList arguments) {
		return Value.getValueNothing();
	}
	
	// will be overridden in inherited Expressions dedicated to creating values
	protected Value createValue(Type valueType, String name, ValueArrayList arguments) {
		return Value.getValueNothing();
	}

	protected final Value evaluateExpression1(ValueArrayList arguments) {
		return expression1.evaluate(arguments);
	}

	protected final Value evaluateExpression2(ValueArrayList arguments) {
		return expression2.evaluate(arguments);
	}
	
	protected final Value evaluateExpression3(ValueArrayList arguments) {
		return expression3.evaluate(arguments);
	}

	public String toString() {
		
		String result;
		
		String val = "";
		String exp1 = "";
		String exp2 = "";
		String exp3 = "";
		
		if (value == null) {
			val = "NULL";
		}
		else {
			val = value.toString();
		}
		
		if (expression1.operation == Expression_Function.skip) {
			exp1 = "Skip";
		}
		else if (expression1.operation == Expression_Function.nothing) {
			exp1 = "Nothing";
		}
		else {
			exp1 = expression1.toString();
		}
		
		if (expression2.operation == Expression_Function.skip) {
			exp2 = "Skip";
		}
		else if (expression2.operation == Expression_Function.nothing) {
			exp2 = "Nothing";
		}
		else {
			exp2 = expression2.toString();
		}
		
		if (expression3.operation == Expression_Function.skip) {
			exp3 = "Skip";
		}
		else if (expression3.operation == Expression_Function.nothing) {
			exp3 = "Nothing";
		}
		else {
			exp3 = expression3.toString();
		}

		if (exp1.equals("Nothing") && exp2.equals("Nothing") && exp3.equals("Nothing")) {
			result = operation.name() + "(" + val + ")";
		}
		else {
			result = operation.name() + "(" + val + ", " + exp1 + ", " + exp2 + ", " + exp3 + ")" ;
		}
		
		return result;
	}
}



/*
case create:
	
	
	Type type;
	Value createdValue;;
	int size;
	
	name = (String) expression3.evaluate().getObject();
	
	type = Type.getName((int)value.getObject());
	
	switch (type) {
	case action:
		createdValue = createValue(type, type.name(), arguments);
		break;
	case time:
		createdValue = calculation.createValue(type, expression2.evaluate().getObject());
		break;
	case knowledgeElement:
		valueList = new ValueArrayList();
		// expression2 is a sequence expression
		expression2.evaluate(arguments);
		createdValue = createValue(type, type.name(), arguments);
		break;
	case knowledgeSource:
	case knowledgeAtom:
		int subType;
		int firstCreateArgument;
		subType = (int) expression1.evaluate().getObject();
		valueList = new ValueArrayList();
		// expression2 is a sequence expression
		firstCreateArgument = arguments.size();
		expression2.evaluate(arguments);
		size = arguments.size();
		for ( index = firstCreateArgument; index < size; index++) {
			valueList.add(arguments.get(index));
		}
		createdValue = createValue(type, subType, type.name(), valueList);
		break;
*/
