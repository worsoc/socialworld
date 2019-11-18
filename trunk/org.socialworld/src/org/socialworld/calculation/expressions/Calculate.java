package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.FunctionCheckType;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public class Calculate extends Expression {

	public Calculate(String term, String nameResultValue) {
		
		Expression main = parseTerm(term, this);
		
		setValue(new Value(Type.string,nameResultValue, nameResultValue));
		setOperation(Expression_Function.sequence);
		setExpression1(new Expression());   // ignore
		setExpression2(new Expression());	// ignore
		setExpression3(main);				// the third expression of sequence returns the evaluation value
		
		if (main.isValid()){
			setValid();
		}
		
	}
	
	private Expression parseTerm(String term, Expression parent) {
		
		String functionName = getFunctionName(term);
		Expression result;
		boolean mustHaveSubExpressions = true;
		boolean valid = true;
		
		if (functionName.length() > 0) {
			
			String subString = term.substring(term.indexOf("(") + 1, term.lastIndexOf(")") ).trim();
			
			
			result = new Expression();
			
			switch (functionName.toLowerCase()) {
				case "const":
					result.setOperation(Expression_Function.value);
					mustHaveSubExpressions = false;
					break;
				case "get":
					result.setOperation(Expression_Function.argumentValueByName);
					mustHaveSubExpressions = false;
					break;
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
			result[1] = parseTerm(oneOreTwoTerms.substring(positionComma + 1, oneOreTwoTerms.length() ).trim(), parent);
		}
		else {
			// 1 operand
			result = new Expression[1];
			result[0] = parseTerm(oneOreTwoTerms.substring(0, oneOreTwoTerms.length() ).trim(), parent);
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
	
}
