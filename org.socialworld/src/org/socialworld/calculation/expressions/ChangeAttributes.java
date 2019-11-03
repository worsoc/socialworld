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

import java.util.List;

import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.ValueInterpreteAs;
import org.socialworld.datasource.parsing.ParseExpressionStrings;

public class ChangeAttributes extends Branching {

	public ChangeAttributes(List<String> lines) {
		
		super();
		
		if (lines.size() > 0)
		{
			String line;
			
			Expression exp1;  // WENN
			Expression exp2;  // DANN
			Expression exp3;  // SONST
				
			line = lines.get(0);
			exp1 = parseWenn(line);
			exp2 = parseDann(line);
			
			if (lines.size() > 1) {
				exp3 = parseLinesTail(1, lines);
			}
			else {
				exp3 = new GetArgumentByName(Value.VALUE_BY_NAME_SIMOBJ_ATTRIBUTES);
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
		wenn = parseWenn(line);
		dann = parseDann(line);
		
		if (index == (lines.size() - 1)) 
			 tail = new GetArgumentByName(Value.VALUE_BY_NAME_SIMOBJ_ATTRIBUTES);
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
		String[] function;
		
		int posDann = line.indexOf("DANN");
		
		partDANN = line.substring(posDann);
				
		String tag;
		String tagValue;
		
		for (Attribute attribute : Attribute.values()) {
			
			tag = attribute.toString().toUpperCase();
			tagValue = ParseExpressionStrings.getTagValue(partDANN, tag);
			
			if (tagValue.length() > 0)
			{
				
				function = ParseExpressionStrings.getTagValue(tagValue, functionTags);
				
				expressionCalculateNewAttributeValue = getFunctionExpression(function);
				
				expressionSetAttributeValue = 
							new SetAttributeValue(attribute, 
									new GetArgumentByName(Value.VALUE_BY_NAME_SIMOBJ_ATTRIBUTES), 
									expressionCalculateNewAttributeValue);
				
				if (isFirstExpression) {
					replacementChain = new Replacement(Value.VALUE_BY_NAME_SIMOBJ_ATTRIBUTES, expressionSetAttributeValue);
					isFirstExpression = false;
				}
				else {
					sequence[0] = replacementChain;
					sequence[1] = new Replacement(Value.VALUE_BY_NAME_SIMOBJ_ATTRIBUTES, expressionSetAttributeValue);
					replacementChain = new Sequence(sequence);

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
