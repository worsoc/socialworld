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
import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.geometry.Vector;

import org.socialworld.calculation.descriptions.EventReactionDescriptionEntry;

import org.socialworld.datasource.parsing.ParseExpressionStrings;

public class CreateActionExpression extends Branching {
	
	public static final int MODUS_CREATE_STATE2ACTION = 1;
	public static final int MODUS_CREATE_REACTION = 2;
	
	public CreateActionExpression(List<EventReactionDescriptionEntry> descEntrys, boolean dummy) {
		
		super();
		
		if (descEntrys.size() > 0)
		{

			EventReactionDescriptionEntry entry;
			
			Expression exp1;  // WENN
			Expression exp2;  // DANN
			Expression exp3;  // SONST
	 
			entry = descEntrys.get(0);
			exp1 = parseWenn(SimulationCluster.todo, PropertyUsingAs.todo, entry.conditions, false /* without WENN/DANN */);
			exp2 = parseDann(entry);
	
			if (descEntrys.size() > 1) {
				exp3 = parseFurtherEntrys(1, descEntrys);
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
		else {
			System.out.println("CreateActionExpression.constructor(): entrys ist leer!");
		}
	}
	
	public CreateActionExpression(List<String> lines) {
		
		super();
		
		
		if (lines.size() > 0)
		{
			String line;
			
			Expression exp1;  // WENN
			Expression exp2;  // DANN
			Expression exp3;  // SONST
				
			line = lines.get(0);
			exp1 = parseWenn(SimulationCluster.todo, PropertyUsingAs.todo, line, true /* with WENN/DANN */);
			exp2 = parseDann(line);
			
			if (lines.size() > 1) {
				exp3 = parseFurtherLines(1, lines);
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
		else {
			System.out.println("CreateActionExpression.constructor(): lines ist leer!");
		}
		
	}

	
	private Expression parseFurtherEntrys(int index, List<EventReactionDescriptionEntry> descEntrys) {
		
		EventReactionDescriptionEntry entry;
		
		Expression wenn;
		Expression dann;
		Expression tail;
		
		entry = descEntrys.get(index);
		wenn = parseWenn(SimulationCluster.todo, PropertyUsingAs.todo, entry.conditions, false /* without WENN/DANN */);
		dann = parseDann(entry);
		
		if (index == (descEntrys.size() - 1)) 
			 tail = new Expression();
		else
			 tail = parseFurtherEntrys(index + 1, descEntrys);
		
		return new Branching(wenn, dann, tail);
			
	}
	
	
	private Expression parseFurtherLines(int index, List<String> lines) {
		
		String line;
		
		Expression wenn;
		Expression dann;
		Expression tail;
		
		line = lines.get(index);
		wenn = parseWenn(SimulationCluster.todo, PropertyUsingAs.todo, line, true /* with WENN/DANN */);
		dann = parseDann(line);
		
		if (index == (lines.size() - 1)) 
			 tail = new Expression();
		else
			 tail = parseFurtherLines(index + 1, lines);
		
		return new Branching(wenn, dann, tail);
			
	}
	
	
	private Expression parseDann(EventReactionDescriptionEntry descEntry) {
		
		
		Expression actionValue;
		Expression replacementChain = null;
		Expression[] sequence = new Expression[2];
		Expression createAction;
		
		String[] functionTags = {"Const", "Table", "VSPE", "MX+N","MLogX+N", "MExpX+N","Now+N", "GetEvParm"};
		String[] function;
		
		
		
		String[] propertyNames;
		String propertyName;
		String tagValue = "";
		ActionType actionType = ActionType.ignore;
		
		for (int i = 0; i < 2; i++)
		{
			if (i == 0) {
				propertyNames = ActionType.getStandardPropertyNames();
			}
			else {
				propertyNames = actionType.getFurtherPropertyNames();
			}
			
			for (int indexPropertyNames = 0; indexPropertyNames < propertyNames.length; indexPropertyNames++) {
				
				propertyName = propertyNames[indexPropertyNames];
				
				tagValue = getTagValue(descEntry, propertyName);
				if (tagValue.length() == 0) continue;
				
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

	private String getTagValue(EventReactionDescriptionEntry descEntry, String propertyName) {
		
		switch (propertyName) {
			case Value.VALUE_BY_NAME_ACTION_TYPE:
				return descEntry.actionType;
			case Value.VALUE_BY_NAME_ACTION_MODE:
				return descEntry.actionMode;
			case Value.VALUE_BY_NAME_ACTION_INTENSITY:
				return descEntry.intensity;
			case Value.VALUE_BY_NAME_ACTION_MINTIME:
				return descEntry.minTime;
			case Value.VALUE_BY_NAME_ACTION_MAXTIME:
				return descEntry.maxTime;
			case Value.VALUE_BY_NAME_ACTION_PRIORITY:
				return descEntry.priority;
			case Value.VALUE_BY_NAME_ACTION_DURATION:
				return descEntry.duration;
			case Value.VALUE_BY_NAME_ACTION_MOVE_ENDPOSITION:
				return descEntry.endPosition;
			case Value.VALUE_BY_NAME_ACTION_DIRECTION:
				return descEntry.direction;
			case Value.VALUE_BY_NAME_ACTION_TARGET:
				return descEntry.target;
			case Value.VALUE_BY_NAME_ACTION_EQUIP_ITEM:
				return descEntry.item;
			case Value.VALUE_BY_NAME_ACTION_EQUIP_PLACE:
				return descEntry.inventoryPlace;
		}
		return "";
	}
	
	private Expression parseDann(String line) {
		
		String partDANN;
		
		Expression actionValue;
		Expression replacementChain = null;
		Expression[] sequence = new Expression[2];
		Expression createAction;
		
		String[] functionTags = {"Const", "Table", "VSPE", "MX+N","MLogX+N", "MExpX+N","Now+N", "GetEvParm"};
		String[] function;
		
		int posDann = line.indexOf("DANN");
		
		partDANN = line.substring(posDann);
		
		String[] propertyNames;
		String propertyName;
		int positionInString;
		String tag;
		String tagValue = "";
		ActionType actionType = ActionType.ignore;
		
		for (int i = 0; i < 2; i++)
		{
			if (i == 0) {
				propertyNames = ActionType.getStandardPropertyNames();
			}
			else {
				propertyNames = actionType.getFurtherPropertyNames();
			}
			
			for (int indexPropertyNames = 0; indexPropertyNames < propertyNames.length; indexPropertyNames++) {
				
				propertyName = propertyNames[indexPropertyNames];
				
				if (i == 0) {
					// standard propertyNames
					tag = propertyName.substring(Value.PRAEFIX_ACTION.length()).toUpperCase();
				}
				else {
					// further propertyNames
					if (propertyName.indexOf(Value.PRAEFIX_ACTION) == 0) {
						tag = propertyName.substring(Value.PRAEFIX_ACTION.length()).toUpperCase();
					}
					else {
						tag = propertyName.substring(actionType.getPraefix().length()).toUpperCase();
					}
				}
				
				tagValue = ParseExpressionStrings.getTagValue(partDANN, tag);
				if (tagValue.length() == 0) continue;
				
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
		
		
		if (function[0].equals("Const")) {
			
			switch (property) {
			case  Value.VALUE_BY_NAME_ACTION_TYPE: type = Type.actionType; break;
			case  Value.VALUE_BY_NAME_ACTION_MODE: type = Type.actionMode; break;
			case  Value.VALUE_BY_NAME_ACTION_MINTIME: type = Type.time; break;
			case  Value.VALUE_BY_NAME_ACTION_MAXTIME: type = Type.time; break;
			case  Value.VALUE_BY_NAME_ACTION_INTENSITY: type = Type.floatingpoint; break;
			case  Value.VALUE_BY_NAME_ACTION_PRIORITY: type = Type.integer; break;
			case  Value.VALUE_BY_NAME_ACTION_DURATION: type = Type.longinteger; break;
			case  Value.VALUE_BY_NAME_ACTION_DIRECTION: type = Type.vector; break;
			case  Value.VALUE_BY_NAME_ACTION_TARGET:
			case  Value.VALUE_BY_NAME_ACTION_WEAPON:
			case  Value.VALUE_BY_NAME_ACTION_ITEM1:
			case  Value.VALUE_BY_NAME_ACTION_ITEM2:
				type = Type.simulationObject; break;
			default: 
				if (property.equals(Value.VALUE_BY_NAME_ACTION_EQUIP_PLACE)) {
					type = Type.integer;
				}
				else if (property.equals(Value.VALUE_BY_NAME_ACTION_EQUIP_ITEM)) {
					type = Type.simulationObject;
				}
				else {
					type = Type.nothing;
					
				}
				

			}
			
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

		}
		else {
			switch (function[0]) {
			case "Table":		result = new TableLookup(function[1]);  break;
			case "VSPE":		result = new VectorScalarProduct(function[1]);  break;
			case "MX+N":		result = new MXPlusN(function[1]);  break;
			case "MLogX+N":		result = new MLogXPlusN(function[1]);  break;
			case "MExpX+N":		result = new MExpXPlusN(function[1]);  break;
			case "Now+N":		result = new CreateValue(Type.time, property, new Constant(calculation.createValue(Type.longinteger,  Integer.parseInt(function[1] )))); break;
			case "GetEvParm":	result = new GetValueFromValueList(Value.VALUE_BY_NAME_EVENT_PARAMS, function[1]);  break;
			}
		}
		return result;
		
	}
}

