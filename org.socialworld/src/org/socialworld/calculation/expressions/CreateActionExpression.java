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

import org.socialworld.actions.ActionType;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.datasource.parsing.ParseExpressionStrings;

public class CreateActionExpression extends Branching {
	
	public static final int MODUS_CREATE_STATE2ACTION = 1;
	public static final int MODUS_CREATE_REACTION = 2;
	
	private int modus;
	
	public CreateActionExpression(List<String> lines, int modus) {
		
		super();
		
		this.modus = modus;
		
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
				exp3 = new CreateValue(Type.action, Nothing.getInstance());
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
			 tail = new Expression();
		else
			 tail = parseLinesTail(index + 1, lines);
		
		return new Branching(wenn, dann, tail);
			
	}
	private Expression parseDann(String line) {
		
		String partDANN;
		
		Expression actionValue;
		Expression replacementChain = null;
		Expression[] sequence = new Expression[2];
		Expression createAction;
		
		String[] functionTags = {"Const", "Table", "VSPE", "MX+N","MLogX+N", "MExpX+N","Now+N"};
		String[] function;
		
		int posDann = line.indexOf("DANN");
		
		partDANN = line.substring(posDann);
		
		String tagValue = "";
		String[] propertyNames;
		String propertyName;
		ActionType actionType = ActionType.ignore;
		
		for (int i = 0; i < 2; i++)
		{
			if (i==0) 
				propertyNames = ActionType.getStandardPropertyNames();
			else
				propertyNames = actionType.getFurtherPropertyNames();
			
			for (int indexPropertyNames = 0; indexPropertyNames < propertyNames.length; indexPropertyNames++) {
				
				propertyName = propertyNames[indexPropertyNames];
				tagValue = ParseExpressionStrings.getTagValue(partDANN, propertyName.toUpperCase());
				function = ParseExpressionStrings.getTagValue(tagValue, functionTags);
				actionValue = getFunctionExpression(propertyName, function);
				
				if (( i == 0) && ( indexPropertyNames == 0 )) 
				{
					// first iteration: the first property has to be the action type as constant expression
					replacementChain = new Replacement(propertyName, actionValue);
					actionType = ActionType.getName(Integer.parseInt(function[1]) );
				}
				else
				{
					sequence[0] = new Replacement(propertyName, actionValue);
					sequence[1] = replacementChain;
					replacementChain = new Sequence(sequence);

				}
			}
		}
	
		createAction = new CreateValue(Type.action, replacementChain);
		return createAction;
	}
	
	private Expression getFunctionExpression(String property, String[] function) {
		
		Expression result = Nothing.getInstance();
		Type type;
		
		switch (property) {
		case  "actiontype": type = Type.actionType; break;
		case  "actionmode": type = Type.actionMode; break;
		case  "mintime": type = Type.time; break;
		case  "maxtime": type = Type.time; break;
		case  "intensity": type = Type.floatingpoint; break;
		case  "priority": type = Type.integer; break;
		case  "duration": type = Type.longinteger; break;
		case  "direction": type = Type.vector; break;
		default: type = Type.nothing;
		}
		
		switch (function[0]) {
		case "Const":
			switch (type) {
			case actionType:
			case actionMode:
			case integer:
			case floatingpoint:
			case longinteger:
				result = new Constant(calculation.createValue(type,  Float.parseFloat(function[1] )));
				break;
			case vector:
				result = new Constant(calculation.createValue(type,  new Vector(function[1]) ));
				break;
			default:
				break;
			}
			break;
		case "Table":		result = new TableLookup(function[1]);  break;
		case "VSPE":		result = new VectorScalarProduct(function[1]);  break;
		case "MX+N":		result = new MXPlusN(function[1]);  break;
		case "MLogX+N":		result = new MLogXPlusN(function[1]);  break;
		case "MExpX+N":		result = new MExpXPlusN(function[1]);  break;
		case "Now+N":		result = new CreateValue(Type.time, new Constant(calculation.createValue(Type.longinteger,  Integer.parseInt(function[1] )))); break;
		}
		return result;
		
	}
}


/*
String tagValueActionType = ParseExpressionStrings.getTagValue(partDANN, "TYPE");
String tagValueActionMode = ParseExpressionStrings.getTagValue(partDANN, "MODE");
String tagValueMinTime = ParseExpressionStrings.getTagValue(partDANN, "MIN_TIME");
String tagValueMaxTime = ParseExpressionStrings.getTagValue(partDANN, "MAX_TIME");
String tagValuePriority = ParseExpressionStrings.getTagValue(partDANN, "PRIO");
String tagValueIntensity = ParseExpressionStrings.getTagValue(partDANN, "INTENSITY");
String tagValueDuration = ParseExpressionStrings.getTagValue(partDANN, "DURATION");

function = ParseExpressionStrings.getTagValue(tagValueActionType, functionTags);
actionValue = getFunctionExpression("actiontype", function);
replacementChain = new Replacement("actiontype", actionValue);

function = ParseExpressionStrings.getTagValue(tagValueActionMode, functionTags);
actionValue = getFunctionExpression("actionmode", function);
sequence[0] = new Replacement("actionmode", actionValue);
sequence[1] = replacementChain;
replacementChain = new Sequence(sequence);

function = ParseExpressionStrings.getTagValue(tagValueMinTime, functionTags);
actionValue = getFunctionExpression("mintime", function);
sequence[0] = new Replacement("mintime", actionValue);
sequence[1] = replacementChain;
replacementChain = new Sequence(sequence);

function = ParseExpressionStrings.getTagValue(tagValueMaxTime, functionTags);
actionValue = getFunctionExpression("maxtime", function);
sequence[0] = new Replacement("maxtime", actionValue);
sequence[1] = replacementChain;
replacementChain = new Sequence(sequence);

function = ParseExpressionStrings.getTagValue(tagValueIntensity, functionTags);
actionValue = getFunctionExpression("intensity", function);
sequence[0] = new Replacement("intensity", actionValue);
sequence[1] = replacementChain;
replacementChain = new Sequence(sequence);

function = ParseExpressionStrings.getTagValue(tagValuePriority, functionTags);
actionValue = getFunctionExpression("priority", function);
sequence[0] = new Replacement("priority", actionValue);
sequence[1] = replacementChain;
replacementChain = new Sequence(sequence);

function = ParseExpressionStrings.getTagValue(tagValueDuration, functionTags);
actionValue = getFunctionExpression("duration", function);
sequence[0] = new Replacement("duration", actionValue);
sequence[1] = replacementChain;
replacementChain = new Sequence(sequence);
*/
