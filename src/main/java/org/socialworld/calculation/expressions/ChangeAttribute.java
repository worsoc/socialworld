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

public class ChangeAttribute extends Branching {

	private static AccessTokenExpressions4Attributes token = AccessTokenExpressions4Attributes.getValid();

    // Privater Konstruktor für die statische Factory-Methode
    private ChangeAttribute() {
        super();
    }

    /**
     * Erzeugt eine Change-Logik für genau EIN Attribut aus einem JSON-Entry.
     */
    public static ChangeAttribute fromJsonEntry(EventInfluencesAttributeEntry entry) {
        ChangeAttribute ca = new ChangeAttribute();
        ca.initFromJson(entry);
        return ca;
    }

    /**
     * Erzeugt eine Change-Logik für genau EIN Attribut aus einer alternativen String-Besschreibung (Liste von string).
     */
	public static ChangeAttribute fromLines(List<String> lines) {
	    ChangeAttribute ca = new ChangeAttribute();
		ca.initFromlines(lines);
	    return ca;
	}

	/////// creation from EventInfluenceAttributeEntry (from JSON)

    private void initFromJson(EventInfluencesAttributeEntry entry) {
        if (entry == null) return;

        // Die Logik für genau dieses eine Attribut bauen
        // convertTermsToExpression erstellt das Branching: WENN (Term0) DANN (Term1) SONST (Term2)
        Expression calculateValue = convertTermsToExpression(entry.term);

        // Der Befehl, dieses spezifische Attribut im Array zu setzen
        SetAttributeValue setAttr = new SetAttributeValue(
            entry.attribute, 
            new GetArgumentByName(org.socialworld.attributes.PropertyName.simobj_attributeArray.toString()), 
            calculateValue
        );

        // Wir kapseln das Ganze als Replacement, damit das Attribut-Array aktualisiert wird
        Expression replacement = new Replacement(
            org.socialworld.attributes.PropertyName.simobj_attributeArray.toString(), 
            setAttr
        );

        // In diesem Modus ist das Replacement unser Haupt-Ausdruck
        setExpression2(replacement); 
        
        // Standard-Rückgabe des Arrays, falls nichts passiert
        setExpression3(new GetArgumentByName(org.socialworld.attributes.PropertyName.simobj_attributeArray.toString()));
        
        setOperation(Expression_Function.branching);
        setValid();
    }

	private Expression convertTermsToExpression(List<Term> terms) {
	    if (terms == null || terms.isEmpty()) {
	        return Nothing.getInstance();
	    }

	    // Fall 1: Nur ein Term vorhanden -> Direkt als Action (DANN) werten
	    if (terms.size() == 1) {
	        return createActionExpression(terms.get(0));
	    }

	    // Fall 2: Mindestens zwei Terms -> Branching (WENN - DANN)
	    // Term 1 (Index 0) ist die Condition
	    Expression condition = createConditionExpression(terms.get(0));
	    
	    // Term 2 (Index 1) ist die Action (hier wird MX+N geladen!)
	    Expression action = createActionExpression(terms.get(1));
	    
	    // Term 3 (Index 2) ist optional der Sonst-Zweig
	    Expression otherwise;
	    if (terms.size() > 2) {
	        otherwise = createActionExpression(terms.get(2));
	    } else {
	        // Default: Aktuellen Attributwert beibehalten, falls Bedingung nicht erfüllt
	        otherwise = new GetArgumentByName(org.socialworld.attributes.PropertyName.simobj_attributeArray.toString());
	    }

	    // Wir geben ein fertiges Branching-Objekt zurück
	    return new Branching(condition, action, otherwise);
	}
	
	
	private Expression createConditionExpression(Term term) {
	    // faNr 1: Attribut (z.B. "mood")
	    String attrName = term.getArgValueAsString(0); 
	    
	    // faNr 2: Operator-String (z.B. ">=")
	    String operatorStr = term.getArgValueAsString(1); 
	    
	    // faNr 3: Vergleichswert (z.B. "50")
	    String valueStr = term.getArgValueAsString(2); 
    
	    // 1. Attribut-Index auflösen
	    Attribute attr = Attribute.fromName(attrName);
	    int attrIndex = (attr != null) ? attr.getIndex() : Attribute.ignore.getIndex();
	    
	    // 2. Operator-Enum auflösen 
	    Expression_ConditionOperator operator = Expression_ConditionOperator.fromName(operatorStr);

	    // 3. Comparison-Instanz erstellen
	    return new Comparison(
	        operator,
	        GetAttributeValue.getInstance(attrIndex),
	        new Constant(new Value(Type.integer, 10 /*Integer.parseInt(valueStr.trim())*/))
	    );

	}
	
	private Expression createActionExpression(Term term) {

	    String actionType = term.getArgValueAsString(0); // z.B. "MX+N"

	    if ("MX+N".equals(actionType)) {
	        String formula = term.getArgValueAsString(1); // z.B. "mood;1.46;8.50"
	        return new MXPlusN(formula, ValueInterpreteAs.attributeValue);
	    }
	    
	    // ... restliche Logik
	    return Nothing.getInstance();
	}

	/////// creation from lines
	
	private void initFromlines(List<String> lines) {
	
		if (lines.size() > 0)
		{
			String line;
			
			Expression exp1;  // WENN
			Expression exp2;  // DANN
			Expression exp3;  // SONST
				
			line = lines.get(0);
			exp1 = parseWenn(token, PropertyUsingAs.todo, line, true /* with WENN/DANN */);
			exp2 = parseDann(line);
			
			if (lines.size() > 1) {
				exp3 = parseLinesTail(1, lines);
			}
			else {
				exp3 = new GetArgumentByName(PropertyName.simobj_attributeArray.toString());
			}
			
			setExpression1(exp1);
			setExpression2(exp2);
			setExpression3(exp3);

			setOperation(Expression_Function.branching);

			setValid();
			
		}
		
	}
	
	private Expression parseLinesTail(int index, List<String> lines) {
		
		String line;
		
		Expression wenn;
		Expression dann;
		Expression tail;
		
		line = lines.get(index);
		wenn = parseWenn(token, PropertyUsingAs.todo, line, true /* with WENN/DANN */);
		dann = parseDann(line);
		
		if (index == (lines.size() - 1)) 
			 tail = new GetArgumentByName(PropertyName.simobj_attributeArray.toString());
		else
			 tail = parseLinesTail(index + 1, lines);
		
		return new Branching(wenn, dann, tail);
			
	}
	
	private Expression parseDann(String line) {
		
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
	
	private Expression getFunctionExpression(String[] function) {
		
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