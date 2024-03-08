/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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


import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Calculation;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.application.ActionCreator;
import org.socialworld.calculation.application.KnowledgeCalculator;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.knowledge.KnowledgeFact_Type;

public class CreateValue extends Expression {

	public CreateValue(Type type, Expression exp2) {
		
		super();
		
		setOperation(Expression_Function.create);
		// expression 1 is reserved for creation sub type
		setExpression1(new Constant(new Value(Type.integer, 0)));
		setExpression2(exp2);
		setExpression3(new Constant(new Value(Type.string, Value.META_NAME_4_VALUE_NAME, type.name())));
		setValue(new Value(Type.integer, type.getIndex()));
		
		setValid();
		
	}
	
	public CreateValue(Type type, String name, Expression exp2) {
		
		super();
		
		setOperation(Expression_Function.create);
		// expression 1 is reserved for creation sub type
		setExpression1(new Constant(new Value(Type.integer, 0)));
		setExpression2(exp2);
		setExpression3(new Constant(new Value(Type.string, Value.META_NAME_4_VALUE_NAME, name)));
		setValue(new Value(Type.integer, type.getIndex()));
		
		setValid();
		
	}

	protected CreateValue(Type type) {
		
		super();
		
		setOperation(Expression_Function.create);
		// expression 1 is reserved for creation sub type
		setExpression1(new Constant(new Value(Type.integer, 0)));
		setExpression3(new Constant(new Value(Type.string, Value.META_NAME_4_VALUE_NAME, type.name())));
		setValue(new Value(Type.integer, type.getIndex()));
		
	}
/*	
	protected Value createValue(Type valueType, String name, ValueArrayList arguments) {

		Object createdObject = null;
		Value createdValue;

		int indexOrigArgs;
		
		ValueArrayList localArguments; 

		switch (valueType) {
		case action:
			
			localArguments = new ValueArrayList();
			
			for ( indexOrigArgs = 0; indexOrigArgs < arguments.size(); indexOrigArgs++) {
				localArguments.add( arguments.get(indexOrigArgs) );
			}
			
			localArguments.add( new Value(Type.actionType, Value.VALUE_BY_NAME_ACTION_TYPE, ActionType.ignore) );  
			localArguments.add( new Value(Type.actionMode, Value.VALUE_BY_NAME_ACTION_MODE, ActionMode.ignore) );  
			localArguments.add( new Value(Type.floatingpoint, Value.VALUE_BY_NAME_ACTION_INTENSITY, 0.0F) );  
			localArguments.add( new Value(Type.time, Value.VALUE_BY_NAME_ACTION_MINTIME, new Time(true, 0)) );  
			localArguments.add( new Value(Type.time, Value.VALUE_BY_NAME_ACTION_MAXTIME, new Time(true, 0)) );  
			localArguments.add( new Value(Type.integer, Value.VALUE_BY_NAME_ACTION_PRIORITY, 0) );  
			localArguments.add( new Value(Type.longinteger, Value.VALUE_BY_NAME_ACTION_DURATION, 0L) ); 
			
			evaluateExpression2(localArguments);
					
			createdObject = ActionCreator.createAction(localArguments) ;
			break;
			
		case knowledgeElement:
			
			localArguments =  (ValueArrayList) arguments.getValue(Value.VALUE_NAME_KNOWLEDGE_ELEMENT_PROPS).getValue();
			createdObject  = KnowledgeCalculator.createKnowledgeElement(localArguments);
			break;

			
		default:
			
		}
		
		createdValue = calculation.createValue(valueType, name, createdObject);
		
		return createdValue;
		
	}
*/
	protected Value createValue(Type valueType, int subType, String name, ValueArrayList arguments) {

		Object createdObject = null;
		Value createdValue;
		Value tmp;
		
		ValueArrayList localArguments = new ValueArrayList();
		
		int indexOrigArgs;
		int size;		
		int firstCreateArgument;
		
		switch (valueType) {
		case action:
			
			for ( indexOrigArgs = 0; indexOrigArgs < arguments.size(); indexOrigArgs++) {
				localArguments.add( arguments.get(indexOrigArgs) );
			}
			
			localArguments.add( new Value(Type.actionType, Value.VALUE_BY_NAME_ACTION_TYPE, ActionType.ignore) );  
			localArguments.add( new Value(Type.actionMode, Value.VALUE_BY_NAME_ACTION_MODE, ActionMode.ignore) );  
			localArguments.add( new Value(Type.floatingpoint, Value.VALUE_BY_NAME_ACTION_INTENSITY, 0.0F) );  
			localArguments.add( new Value(Type.time, Value.VALUE_BY_NAME_ACTION_MINTIME, new Time(true, 0)) );  
			localArguments.add( new Value(Type.time, Value.VALUE_BY_NAME_ACTION_MAXTIME, new Time(true, 0)) );  
			localArguments.add( new Value(Type.integer, Value.VALUE_BY_NAME_ACTION_PRIORITY, 0) );  
			localArguments.add( new Value(Type.longinteger, Value.VALUE_BY_NAME_ACTION_DURATION, 0L) ); 
			
			evaluateExpression2(localArguments);
					
			createdObject = ActionCreator.createAction(localArguments) ;
			break;
		case time:
			createdObject = evaluateExpression2(localArguments).getObject(Type.time);
			break;
		case knowledgeElement:
			evaluateExpression2(arguments);
			localArguments = objectRequester.requestValueArrayList(SimulationCluster.total, arguments.getValue(Value.VALUE_NAME_KNOWLEDGE_ELEMENT_PROPS), this); 
			createdObject  = KnowledgeCalculator.createKnowledgeElement(localArguments);
			break;
		case knowledgeSource:
			firstCreateArgument = arguments.size();
			evaluateExpression2(arguments);
			size = arguments.size();
			for ( indexOrigArgs = firstCreateArgument; indexOrigArgs < size; indexOrigArgs++) {
				localArguments.add(arguments.get(indexOrigArgs));
			}
			createdObject  = KnowledgeCalculator.createKnowledgeSource(arguments);
			break;
		case knowledgeAtom:

			KnowledgeFact_Type kft;
			kft = KnowledgeFact_Type.getName(subType);
			firstCreateArgument = arguments.size(); 
			evaluateExpression2(arguments);
			size = arguments.size();
			for ( indexOrigArgs = firstCreateArgument; indexOrigArgs < size; indexOrigArgs++) {
				tmp = arguments.get(indexOrigArgs);
				if (tmp.isValid()) {
					localArguments.add(tmp);
				}
				else {
					return Calculation.getNothing();
				}
			}
			createdObject  = KnowledgeCalculator.createKnowledgeAtom(kft, localArguments);
			break;

		default:
			return Calculation.getNothing();
		}
		
		
		
/*		
		switch (valueType) {

		case knowledgeSource:
			createdObject  = KnowledgeCalculator.createKnowledgeSource(arguments);
			break;
		case knowledgeAtom:
			
			KnowledgeFact_Type kat;
			kat = KnowledgeFact_Type.getName(subType);
			
			createdObject  = KnowledgeCalculator.createKnowledgeAtom(kat, arguments);
			break;
			
		default:
			
		}
*/		
		if (createdObject == null) {
			createdValue = Calculation.getNothing();
		}
		else {
			createdValue = calculation.createValue(valueType, name, createdObject);
		}
		
		return createdValue;
		
	}
	
}
