package org.socialworld.calculation;

import org.socialworld.attributes.AttributeArray;

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
	
	Expression_Function function;
	Expression_ConditionOperator operator;

	
	Value value;
	
	Expression expression1;
	Expression expression2;
	Expression expression3;
	
	int ID_Exp1;
	int ID_Exp2;
	int ID_Exp3;
	
	private Calculation calculation;
	

	public Expression() {
		calculation = Calculation.getInstance();
	}
	
	
	public void setID(int ID) {		this.ID = ID;	}

	public void setID(String ID) {		this.ID = (int) Float.parseFloat(ID);	}

	public int getID() {return ID; };

	public void set_ID_Exp2(int ID) {		this.ID_Exp2 = ID;	}

	public void set_ID_Exp2(String ID) {		this.ID_Exp2 = (int) Float.parseFloat(ID);	}
	
	public int get_ID_Exp2() {return ID_Exp2; };
	
	public void set_ID_Exp3(int ID) {		this.ID_Exp3 = ID;	}

	public void set_ID_Exp3(String ID) {		this.ID_Exp3 = (int) Float.parseFloat(ID);	}

	public int get_ID_Exp3() {return ID_Exp3; };

	public void setFunction(Expression_Function function) {
		this.function = function;
	}

	public void setOperator(Expression_ConditionOperator operator) {
		this.operator = operator;
	}




	
	public void setExpression1(Expression expression) {
		this.expression1 = expression;
	}
	
	public void setExpression2(Expression expression) {
		this.expression2 = expression;
	}

	public void setExpression3(Expression expression) {
		this.expression3 = expression;
	}

	
	public void setTrueExpression(Expression expressionForTrue) {
		setExpression2(expressionForTrue);
	}

	public void setFalseExpression(Expression expressionForFalse) {
		setExpression3(expressionForFalse);
	}

	public void setValue(Value value) {
		this.value = value;
	}

	protected Value getValue() {
		return value;
	}
	
		


	/**
	 */
	Value evaluateExpression(Value[] arguments) {
		Value tmp;
		
		switch (this.function) {
		case value:
			return calculation.copy(value);
			
		case attributeValue:
			AttributeArray attributeArray;
			attributeArray = (AttributeArray) get( arguments, Type.attributeArray, 1);
			return calculation.createValue(
				Type.integer,
				attributeArray.get( (int) value.getValueCopy()));
				
		case branching:
			tmp = expression1.evaluateExpression(arguments);
			if (tmp.isTrue()  ) 
				tmp =  expression2.evaluateExpression(arguments);
			else
				tmp = expression3.evaluateExpression(arguments);
			return tmp;
			
		case condition:
			
			switch (operator) {
			case or:
				return calculation.or(
						expression1.evaluateExpression(arguments) ,
						expression2.evaluateExpression(arguments)   );
			case and:
				return calculation.and(
						expression1.evaluateExpression(arguments) ,
						expression2.evaluateExpression(arguments)   );
			default:
				return calculation.createValue(Type.bool,  false);
			}
			
			
		case comparison :
			return calculation.compare(
						expression1.evaluateExpression(arguments) ,
						expression2.evaluateExpression(arguments)   );
			
			
			
		case addition:
			return calculation.addition(
					expression1.evaluateExpression(arguments) ,
					expression2.evaluateExpression(arguments)   );
			
		case multiplication:
			return calculation.multiplication(
					expression1.evaluateExpression(arguments) ,
					expression2.evaluateExpression(arguments)   );
			
		case replacement:
			//TODO
			
			return calculation.getNothing();
			
			
		default:
			return calculation.getNothing();
		}
		


	}
	

	private Object get(Value[] arguments, Type type, int wantedOccurence) {
		int argumentsCount;
		int occurence;
		
		argumentsCount = arguments.length;
		occurence = 0;
		
		for (int index = 0; index < argumentsCount; index++) {
			if (arguments[index].getType() == type) {
				occurence++;
				if (occurence == wantedOccurence) {
					return arguments[index].getValue();
				}
			}
		}
		return null;
	}
}
