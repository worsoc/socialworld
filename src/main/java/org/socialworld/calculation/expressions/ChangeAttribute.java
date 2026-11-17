/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.calculation.expressions;

import java.util.List;
import java.util.regex.Matcher;

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Calculation;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_ConditionOperator;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.ValueInterpreteAs;
import org.socialworld.calculation.descriptions.EventInfluencesAttributeEntry;
import org.socialworld.calculation.descriptions.Term;
import org.socialworld.datasource.parsing.ParseExpressionStrings;

public class ChangeAttribute {

	private static Calculation calculation = Calculation.getInstance();

	private static AccessTokenExpressions4Attributes token = AccessTokenExpressions4Attributes.getValid();

    // Privater Konstruktor für die statische Factory-Methode
    private ChangeAttribute() {
        super();
    }

    /**
     * Erzeugt eine Change-Logik für genau EIN Attribut aus einem JSON-Entry.
     */
    public static Expression fromJsonEntry(EventInfluencesAttributeEntry entry) {
         return initFromJson(entry);
    }

    /**
     * Erzeugt eine Change-Logik für genau EIN Attribut aus einer alternativen String-Beschreibung (Liste von string).
     */
	public static Expression fromLines(List<String> lines) {
		return initFromlines(lines);
	}

	/////// creation from EventInfluenceAttributeEntry (from JSON)

	private static Expression initFromJson(EventInfluencesAttributeEntry entry) {
	    if (entry == null) return Nothing.getInstance();;

	    List<Term> terms = entry.term;
	    if (terms == null || terms.isEmpty()) return Nothing.getInstance();;

		Expression exp1;  // WENN
		Expression exp2;  // DANN
		Expression exp3;  // SONST

		// 1. Bedingung (WENN) -> expression1
	    if (terms.size() >= 2) {
	    	// the first term is the condition; for now another function there is only reported
	    	if (terms.get(0).getFunction() != Expression_Function.condition) {
	    		System.out.println("ChangeAttribute.initFromJson(): attribute " + entry.attribute
	    				+ ", the first term is used as condition but its function is " + terms.get(0).getFunction());
	    	}
	    	exp1 = createConditionExpression(terms.get(0));
	    } else {
	        // Falls nur ein Term (keine Bedingung), setzen wir die Bedingung auf TRUE (Constant 1)
	    	exp1 = Constant.INTEGER_1;
	    	//exp1 =  new Constant(     new Value(Type.integer, 1));
	    }

	    // 2. Berechnung & Zuweisung (DANN) -> expression2
	    // Hier nutzen wir nun das SetAttributeValue direkt als Action
	    Expression calculateValue = (terms.size() >= 2) ? createActionExpression(terms.get(1)) : createActionExpression(terms.get(0));
	    
	    SetAttributeValue setAttrAction = new SetAttributeValue(
	        entry.attribute, 
	        new GetArgumentByName(PropertyName.simobj_attributeArray.toString()), 
	        calculateValue
	    );
	    
	    // Das Replacement kapselt das Setzen des Attributs
	    exp2 = new Replacement(
	        PropertyName.simobj_attributeArray.toString(), 
	        setAttrAction
	    );

	    // 3. Fallback (SONST) -> expression3
	    if (terms.size() > 2) {
	        // Falls ein SONST-Term existiert (MX+N etc.)
	        Expression otherwiseValue = createActionExpression(terms.get(2));
	        SetAttributeValue setAttrOtherwise = new SetAttributeValue(
	            entry.attribute, 
	            new GetArgumentByName(PropertyName.simobj_attributeArray.toString()), 
	            otherwiseValue
	        );
	        exp3 = new Replacement(PropertyName.simobj_attributeArray.toString(), setAttrOtherwise);
	    } else {
	        // Default: Einfach das bestehende Array zurückgeben (keine Änderung)
	    	exp3 = new GetArgumentByName(PropertyName.simobj_attributeArray.toString());
	    }

		return Branching.createBranchingSmart(exp1, exp2, exp3);

	}

	
	
	/**
	 * The condition term holds one or more comparisons, three arguments each:
	 * attribute, operator, value (e.g. "mood", ">=", "3800").
	 * One comparison stays a single attribute comparison, which Branching decides softly (sigmoid).
	 * Two or more comparisons are joined by AND and decided strictly.
	 * A number of arguments that is not a multiple of three makes the condition invalid (Nothing).
	 */
	private  static Expression createConditionExpression(Term term) {
		int countArgs = 0;
		String arg = term.getArgValueAsString(countArgs);
		while (arg != null && !arg.isEmpty()) {
			countArgs++;
			arg = term.getArgValueAsString(countArgs);
		}

		if (countArgs == 0 || countArgs % 3 != 0) {
			System.out.println("ChangeAttribute.createConditionExpression(): " + countArgs
					+ " arguments, a condition needs a multiple of three (attribute, operator, value)");
			return Nothing.getInstance();
		}

		int countComparisons = countArgs / 3;
		if (countComparisons == 1) {
			return createComparison(term, 0);
		}

		Expression[] comparisons = new Expression[countComparisons];
		for (int i = 0; i < countComparisons; i++) {
			comparisons[i] = createComparison(term, i * 3);
		}
		return new Junction(Expression_ConditionOperator.and, comparisons);
	}

	/**
	 * One comparison of an attribute with a constant, read from three arguments
	 * starting at firstArg: attribute (e.g. "mood"), operator (e.g. ">="), value (e.g. "50").
	 */
	private static Expression createComparison(Term term, int firstArg) {
	    String attrName = term.getArgValueAsString(firstArg);
	    String operatorStr = term.getArgValueAsString(firstArg + 1);
	    String valueStr = term.getArgValueAsString(firstArg + 2);

	    Attribute attr = Attribute.fromName(attrName);
	    int attrIndex = (attr != null) ? attr.getIndex() : Attribute.ignore.getIndex();

	    Expression_ConditionOperator operator = Expression_ConditionOperator.fromName(operatorStr);

	    return new Comparison(
	        operator,
	        GetAttributeValue.getInstance(attrIndex),
	        new Constant(new Value(Type.integer, Integer.parseInt(valueStr.trim())))
	    );
	}
	
	private static Expression createActionExpression(Term term) {

	    String actionType = term.getArgValueAsString(0); // z.B. "MX+N"

	    if ("MX+N".equals(actionType)) {
	        String formula = term.getArgValueAsString(1); // z.B. "mood;1.46;8.50"
	        return new MXPlusN(formula, ValueInterpreteAs.attributeValue);
	    }
	    
	    // ... restliche Logik
	    return Nothing.getInstance();
	}

	/////// creation from lines
	
	private static Expression initFromlines(List<String> lines) {
	
		if (lines.size() > 0)
		{
			String line;
			
			Expression exp1;  // WENN
			Expression exp2;  // DANN
			Expression exp3;  // SONST
				
			line = lines.get(0);
			exp1 = Branching.parseWenn(token, PropertyUsingAs.todo, line, true /* with WENN/DANN */);
			exp2 = parseDann(line);
			
			if (lines.size() > 1) {
				exp3 = parseLinesTail(1, lines);
			}
			else {
				exp3 = new GetArgumentByName(PropertyName.simobj_attributeArray.toString());
			}
			
			return Branching.createBranchingSmart(exp1, exp2, exp3);

		}
		return Nothing.getInstance();
	}
	
	private static Expression parseLinesTail(int index, List<String> lines) {
		
		String line;
		
		Expression wenn;
		Expression dann;
		Expression tail;
		
		line = lines.get(index);
		wenn = Branching.parseWenn(token, PropertyUsingAs.todo, line, true /* with WENN/DANN */);
		dann = parseDann(line);
		
		if (index == (lines.size() - 1)) 
			 tail = new GetArgumentByName(PropertyName.simobj_attributeArray.toString());
		else
			 tail = parseLinesTail(index + 1, lines);
		
		return Branching.createBranchingSmart(wenn, dann, tail);
			
	}
	
	private static Expression parseDann(String line) {
		
		boolean isFirstExpression = true;
		
		String partDANN;
		
		Expression expressionCalculateNewAttributeValue;
		Expression replacementChain = Nothing.getInstance();
		Expression[] sequence = new Expression[2];
		SetAttributeValue expressionSetAttributeValue;
		
		String[] functionTags = {"Const", "Table", "VSPE", "MX+N","MLogX+N", "MExpX+N"};
		
		int posDann = line.indexOf("DANN");
		
		partDANN = line.substring(posDann);
	
	    Matcher matcher = ParseExpressionStrings.TAG_PATTERN.matcher(partDANN);

		
		
	    while (matcher.find()) {

	        String tagName = matcher.group(1); 
	
	        if (Attribute.isUpperAttributeName(tagName)) {
		
	            Attribute attribute = Attribute.fromName(tagName);
	
	            String tagContent = ParseExpressionStrings.getTagValue(partDANN, tagName);
		
	            if (!tagContent.isEmpty()) {

	                String[] function = ParseExpressionStrings.getTagValue(tagContent, functionTags);

					expressionCalculateNewAttributeValue = getFunctionExpression(function);
	
					expressionSetAttributeValue = 
							new SetAttributeValue(attribute, 
									new GetArgumentByName(PropertyName.simobj_attributeArray.toString()), 
									expressionCalculateNewAttributeValue);
				
					if (isFirstExpression) {
						replacementChain = new Replacement(PropertyName.simobj_attributeArray.toString(), expressionSetAttributeValue);
						isFirstExpression = false;
					}
					else {
						sequence[0] = replacementChain;
						sequence[1] = new Replacement(PropertyName.simobj_attributeArray.toString(), expressionSetAttributeValue);
						replacementChain = new Sequence(sequence);
					}
					
					// Abbruch, weil nur ein Attribut bearbeitet werden soll !!!
					return replacementChain;
					
					
	            }
	        }
			
		}
		
		return replacementChain;

	}
	
	private static  Expression getFunctionExpression(String[] function) {
		
		Expression result = Nothing.getInstance();
		Type type;
		
		switch (function[0]) {
		case "Const":
			type = Type.integer;
			result = new Constant(calculation.createValue(type,  Integer.parseInt(function[1] )));
			break;
		case "Table":		result = new TableLookup(function[1]);  break;
		case "VSPE":		result = new VectorScalarProduct(function[1], ValueInterpreteAs.attributeValue);  break;
		case "MX+N":		result = new MXPlusN(function[1], ValueInterpreteAs.attributeValue);  break;
		case "MLogX+N":		result = new MLogXPlusN(function[1], ValueInterpreteAs.attributeValue);  break;
		case "MExpX+N":		result = new MExpXPlusN(function[1], ValueInterpreteAs.attributeValue);  break;
		}
		return result;
		
	}
	
}