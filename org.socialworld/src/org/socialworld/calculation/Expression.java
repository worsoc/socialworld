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


import org.socialworld.actions.AbstractAction;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.ISimProperty;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.expressions.Nothing;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.Event;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.State;
import org.socialworld.objects.StateSimulationObject;

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

 * @author Mathias Sikos (tyloesand)
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


	public Expression() {
		calculation = Calculation.getInstance();
		functions = Functions.getInstance();
		operation = Expression_Function.nothing;
		
		setExpression1(Nothing.getInstance());
		setExpression2(Nothing.getInstance());
		setExpression3(Nothing.getInstance());
		
		isValid = false;
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
		
		if (this.isValid()) {
			
			try {
			
			
				switch (this.operation) {
				case nothing:
					//return invalid dummy-Value
					return Calculation.getNothing();
					
				case value:
					return calculation.copy(value);
										
				case attributeValue:
					AttributeArray attributeArray;
					attributeArray = (AttributeArray) getFromValueArrayList( arguments, Type.attributeArray, 1);
					index = (int) value.getValue();
					return calculation.createValue(
						Type.integer,
						attributeArray.get(index ));
						
				case argumentValueByName:
					
					tmp = arguments.getValue( (String) value.getValue());
					name = (String) expression1.evaluate(arguments).getValue();
					if (name.length() > 0) {
							tmp.changeName(name);
					}
 					return tmp;
					
										
				case valueFromValueList:
					// get value list's name
					name =  (String) expression1.evaluate(arguments).getValue();
					// get the value list
					tmp = arguments.getValue(name);
					ValueArrayList subValueList = (ValueArrayList) tmp.getValue();
					// get the value list element's name
					name = (String) expression2.evaluate(arguments).getValue();
					// get the result value from the value list
					return subValueList.getValue(name);
	
				case property:
					
					Value object = arguments.get(0);
					PropertyName simPropName = (PropertyName) value.getValue();
					SimulationCluster cluster = SimulationCluster.getName((int) expression1.evaluate(arguments).getValue());
					String methodName = (String) expression2.evaluate(arguments).getValue();
					name = (String) expression3.evaluate(arguments).getValue();
					
					return getProperty(object, cluster, simPropName, methodName, name);
				
				case get:  // --> returns a ValueProperty!!!
					
					ValueArrayList subArgument = new ValueArrayList();
					boolean checkSuccess = false;
					ValueProperty property;
					
					tmp = expression1.evaluate(arguments);

					int checkUseAsPermission = (int) value.getValue();
					if (checkUseAsPermission > 0) {
						if (tmp instanceof ValueProperty) {
							property = (ValueProperty) tmp;
							if ( property.checkHasUseAsPermission(PropertyUsingAs.getName(checkUseAsPermission)) ) {
								checkSuccess = true;
							}
						}
					}
					else {
						// no check needed --> true
						checkSuccess = true;
					}
					
					if (checkSuccess) {
						subArgument.add(tmp);
						tmp = expression2.evaluate(subArgument);
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
					ValueArrayList calculateArguments = new ValueArrayList();
					calculateArguments.add( expression1.evaluate(arguments) );
					calculateArguments.add( expression2.evaluate(arguments) );
					calculateArguments.add( expression3.evaluate(arguments) );
					return function.calculate(calculateArguments);
					
				case oneExpression:
					tmp = expression1.evaluate(arguments);
					if (this.value != null) {
						if (this.value.getName().length() > 0) {
							tmp.changeName(this.value.getName());
						}
					}
					return tmp;
					
				case sequence:
					expression1.evaluate(arguments);
					expression2.evaluate(arguments);
					tmp = expression3.evaluate(arguments);
					if (this.value != null) {
						if (this.value.getName().length() > 0) {
							tmp.changeName(this.value.getName());
						}
					}
					return tmp;
					
				case replacement:
					
					tmp = expression1.evaluate(arguments);
					name = (String) value.getValue();
					if (name.length() > 0) {
						index = arguments.findValue(name);
						if (index >= 0) {
							if (tmp.getName().isEmpty()) {
								tmp.setName(name);
							}
							arguments.set(index, tmp);
						}
						else {
							tmp.setName(name);
							arguments.add(tmp);
						}
					}
					else {
						arguments.add(tmp);
					}
					return tmp;
				
				case create:
					
					Type type;
					Value createdValue;;
					int size;
					
					type = Type.getName((int)value.getValue());
					
					switch (type) {
					case action:
						createdValue = createValue(type, arguments);
						break;
					case time:
						createdValue = calculation.createValue(type, expression2.evaluate().getValue());
						break;
					case knowledgeElement:
						ValueArrayList knowledgeElementSourceAndAtoms = new ValueArrayList();
						// expression2 is a sequence expression
						expression2.evaluate(arguments);
						size = arguments.size();
						for ( index = 1; index < size; index++) {
							knowledgeElementSourceAndAtoms.add(arguments.get(index));
						}
						createdValue = createValue(type, knowledgeElementSourceAndAtoms);
						break;
					case knowledgeAtom:
						int subType;
						subType = (int) expression1.evaluate().getValue();
						ValueArrayList knowledgeAtom = new ValueArrayList();
						// expression2 is a sequence expression
						expression2.evaluate(arguments);
						size = arguments.size();
						for ( index = 1; index < size; index++) {
							knowledgeAtom.add(arguments.get(index));
						}
						createdValue = createValue(type, subType, knowledgeAtom);
						break;
					default:
						createdValue = Calculation.getNothing();
					}
					
					return createdValue;
					
				default:
					return Calculation.getNothing();
				}
			
			}
			catch (Exception e) {
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
			return value.getValue();
		}
		else {
			return null;
		}
		
	}

	private ValueProperty getProperty(Value valueObject, SimulationCluster cluster, PropertyName simPropName, String methodName, String valueName) {
		
		ValueProperty result;
		result = ValueProperty.getInvalid();
		Object object = valueObject.getValue();
		
		if (simPropName == PropertyName.unknown) {
			
			if (methodName.length() > 0) {
			
				// use reflection for calling the method
				
				if (object instanceof State) {
					State state = (State) object;
					result = state.getProperty(cluster, methodName, valueName);
				}
				else if (object instanceof ISimProperty) {
					ISimProperty simProperty;
					simProperty = (ISimProperty) object;
					result = simProperty.getProperty(cluster, methodName, valueName);
				}
				
			}
		
		}
		else {
			
			// call getProperty()
			
			if (object instanceof SimulationObject) {
				SimulationObject simObj;
				simObj = (SimulationObject) object;
				result = simObj.getProperty(cluster, simPropName, valueName);
			}
			else if (object instanceof StateSimulationObject) {
				StateSimulationObject stateSimObj;
				stateSimObj = (StateSimulationObject) object;
				result = stateSimObj.getProperty(cluster, simPropName, valueName);
			}
			else if (object instanceof State) {
				State stateAddOn;
				stateAddOn = (State) object;
				result = stateAddOn.getProperty(cluster, simPropName, valueName);
			}
			else if (object instanceof ISimProperty) {
				ISimProperty simProperty;
				simProperty = (ISimProperty) object;
				result = simProperty.getProperty(cluster, simPropName, valueName);
			}
			else if (object instanceof Event) {
				
			}
			else if (object instanceof AbstractAction) {
				
			}
			
		}

		return result;
		
	}
	
	// will be overrided in inherited Expressions dedicated to creating values
	protected Value createValue(Type valueType, int subType, ValueArrayList arguments) {
		return new Value();
	}
	
	// will be overrided in inherited Expressions dedicated to creating values
	protected Value createValue(Type valueType, ValueArrayList arguments) {
		return new Value();
	}

	protected void evaluateExpression1(ValueArrayList arguments) {
		expression1.evaluate(arguments);
	}

}
