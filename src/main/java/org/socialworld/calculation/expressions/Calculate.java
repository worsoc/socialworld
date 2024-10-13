package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.functions.FunctionCheckType;

public class Calculate extends Expression {

	public static Calculate getCalculateExpression(String functionDescription, String nameResultValue) {
		String term = getTermConstruction(functionDescription);
		return new Calculate(term,  nameResultValue);
	}
	
	private static String getDotOperator(String description) {
		if (description.indexOf(">>>") > 0) {
			return ">>>";
		}
		else if (description.indexOf(">>") > 0) {
			return ">>";
		}
		else if (description.indexOf(">") > 0) {
			return ">";
		}
		else {
			return "\\.";
		}
	}
	


	public Calculate(String term, String nameResultValue) {
		
		Expression main = parseTerm(term, this);
		
		setValue(new Value(Type.string, nameResultValue, nameResultValue));
		setOperation(Expression_Function.oneExpression);
		setExpression1(main);  		
		
		if (main.isValid()){
			setValid();
		}
		
	}
	
	private Expression parseTerm(String term,  Expression parent) {
		
		String functionName = getFunctionName(term).toLowerCase();
		Expression result;
		boolean mustHaveSubExpressions = true;
		boolean valid = true;
		
		if (functionName.length() > 0) {
			
			String subString = term.substring(term.indexOf("(") + 1, term.lastIndexOf(")") ).trim();
			
			if (functionName.equals("get")) {
				String separator = Calculate.getDotOperator(subString);
				result = new GetValue(SimulationCluster.todo, PropertyUsingAs.todo, subString, separator, Value.VALUE_NAME_UNUSED_BECAUSE_TEMPORARY);
				return result;
			}
			else {
				
				result = new Expression();
				
				switch (functionName) {
					case "const":
						result.setOperation(Expression_Function.value);
						mustHaveSubExpressions = false;
						break;
/*					case "get":
						result.setOperation(Expression_Function.argumentValueByName);
						mustHaveSubExpressions = false;
						break;*/
					case "attr":
						result.setOperation(Expression_Function.attributeValue);
						mustHaveSubExpressions = false;
						break;
					case "isnothing":
						result.setOperation(Expression_Function.function);
						result.setFunction(new FunctionCheckType(Type.nothing));
						result.setExpression1(new Expression());
						result.setExpression2(new Expression());
						result.setExpression3(new Expression());
						break;
					case "add":
						result.setOperation(Expression_Function.addition);
						break;
					case "sub":
						result.setOperation(Expression_Function.subtraction);
						break;
					case "mul":
						result.setOperation(Expression_Function.multiplication);
						break;
					case "div":
						result.setOperation(Expression_Function.division);
						break;
					default:
						valid = false;
				}
			}
			
			Expression[] subTermExpressions = parseSubString(subString, result);
			
			if (mustHaveSubExpressions) {
				
				if (subTermExpressions.length == 2) {
					if (subTermExpressions[0].isValid()) {
						result.setExpression1(subTermExpressions[0]);
					}
					else {
						valid = false;
					}
					
					if (subTermExpressions[1].isValid()) {
						result.setExpression2(subTermExpressions[1]);
					}
					else {
						valid = false;
					}
				}
		
				if (subTermExpressions.length == 1) {
					if (subTermExpressions[0].isValid()) {
						result.setExpression1(subTermExpressions[0]);
					}
					else {
						valid = false;
					}
				}
				
				if ((subTermExpressions.length < 1) || (subTermExpressions.length > 2)) {
					valid = false;
				}
				
			}

			if (valid) {
				result.setValid();
			}
			
		}
		else {
			
			switch (parent.getOperation()) {
				case argumentValueByName:
					parent.setValue(new Value(Type.string, term.trim()));
					break;
				case attributeValue:
					parent.setValue(new Value(Type.integer, Integer.parseInt( term.trim())));
					break;
				default:
					String[] typeAndValue = term.trim().split(" ");
					parent.setValue(new Value(typeAndValue[1], Type.getName(Integer.parseInt(typeAndValue[0]))));
					break;
			}

			result = new Expression();
			
		}
		
		return result;
		
	}
	
	private String getFunctionName(String term) {
		
		int positionFirstOpenBracket = term.indexOf("(");
		String result;
		
		if (positionFirstOpenBracket == -1) {
			result = "";
		}
		else {
			result = term.substring(0, positionFirstOpenBracket);
		}
		
		return result.trim();
		
	}
	
	private Expression[] parseSubString(String oneOreTwoTerms, Expression parent) {
		
		Expression[] result;
		
		int positionComma = 0;
		positionComma = oneOreTwoTerms.indexOf(",");
		
		if (positionComma > 0) {
			positionComma = findHighestLevelComma(oneOreTwoTerms);
		}
		
		if (positionComma > 0) {
			// 2 operands
			result = new Expression[2];
			result[0] = parseTerm(oneOreTwoTerms.substring(0, positionComma ).trim(), parent);
			result[1] = parseTerm(oneOreTwoTerms.substring(positionComma + 1, oneOreTwoTerms.length() ).trim(),  parent);
		}
		else {
			// 1 operand
			result = new Expression[1];
			result[0] = parseTerm(oneOreTwoTerms.substring(0, oneOreTwoTerms.length() ).trim(),  parent);
		}
		
		return result;
			
		
	}
	
	private int findHighestLevelComma(String source) {
		
		int countOpenBrackets = 0;
		int positionComma = 0;
		char[] arrayChars;
		
		arrayChars = source.toCharArray();
		
		for (int i = 0; i < arrayChars.length; i++) {
			
			if (arrayChars[i] == '(')  countOpenBrackets++;
			if (arrayChars[i] == ')')  countOpenBrackets--;
			if (arrayChars[i] == ',')  positionComma = i;
			
			if (countOpenBrackets == 0 && positionComma > 0) return positionComma;
		}
		
		return -1;
	}
	
	private static String getTermConstruction(String functionDescription) {
		String term = "";
		
		if (functionDescription.equals("function:distance(event,simObj)")) {

			term = "get(SUB(" + 
					"get(GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ")>" + GetValue.GETVALUE + "(" + Value.VALUE_BY_NAME_EVENT_POSITION + "))," +
					"get(GETVal(" + Value.VALUE_BY_NAME_SIMOBJECT + ")>" + GetValue.GETPROPERTY + "(simobj_position)))>>>" + 
					GetValue.GETPROPERTY + "(direction_vector)>>>" + GetValue.GETPROPERTY + "(vector_length))"
					;
/*			
			term = "SUB(" + 
					"get(GETVal(" + Value.VALUE_BY_NAME_EVENT + ")." + GetValue.GETPROPERTY + "(event_position))," +
					"get(GETVal(" + Value.VALUE_BY_NAME_SIMOBJECT + ")." + GetValue.GETPROPERTY + "(simobj_position)))" //." + 
//					GetValue.GETPROPERTY + "(direction_vector)." + GetValue.GETPROPERTY + "(vector_length)"
					;
*/
		}
		return term;
	}
	
}
