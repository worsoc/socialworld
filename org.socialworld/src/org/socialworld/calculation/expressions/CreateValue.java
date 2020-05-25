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
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.application.ActionCreator;
import org.socialworld.calculation.application.KnowledgeCalculator;
import org.socialworld.collections.ValueArrayList;

public class CreateValue extends Expression {

	public CreateValue(Type type, Expression exp2) {
		
		super();
		
		setOperation(Expression_Function.create);
		// expression 1 is reserved for creation sub type
		setExpression1(new Constant(new Value(Type.integer, 0)));
		setExpression2(exp2);
		setValue(new Value(Type.integer, type.getIndex()));
		
		setValid();
		
	}
	
	protected CreateValue(Type type) {
		
		super();
		
		setOperation(Expression_Function.create);
		// expression 1 is reserved for creation sub type
		setExpression1(new Constant(new Value(Type.integer, 0)));
		setValue(new Value(Type.integer, type.getIndex()));
		
	}
	
	protected Value createValue(Type valueType, ValueArrayList arguments) {

		Object createdObject = null;
		Value createdValue;

		ValueArrayList localArguments; 

		switch (valueType) {
		case action:
			
			localArguments = new ValueArrayList();
			// copy attribute array to local argument array
			localArguments.add( calculation.copy(arguments.get(0)) );
			localArguments.add( new Value(Type.actionType, "actiontype", ActionType.ignore) );  
			localArguments.add( new Value(Type.actionMode, "actionmode", ActionMode.ignore) );  
			localArguments.add( new Value(Type.floatingpoint, "intensity", 0.0F) );  
			localArguments.add( new Value(Type.time, "mintime", new Time(true, 0)) );  
			localArguments.add( new Value(Type.time, "maxtime", new Time(true, 0)) );  
			localArguments.add( new Value(Type.integer, "priority", 0) );  
			localArguments.add( new Value(Type.longinteger, "duration", 0L) ); 
			
			evaluateExpression1(localArguments);
					
			createdObject = ActionCreator.createAction(localArguments) ;
			break;
			
		case knowledgeElement:
			
			localArguments =  (ValueArrayList) arguments.getValue(Value.VALUE_BY_NAME_KNOWLEDGE_ELEMENT_PROPS).getValue();
			createdObject  = KnowledgeCalculator.createKnowledgeElement(localArguments);
			break;

			
		default:
			
		}
		
		createdValue = calculation.createValue(valueType, createdObject);
		
		return createdValue;
		
	}

	protected Value createValue(Type valueType, int subType, ValueArrayList arguments) {

		Object createdObject = null;
		Value createdValue;

		ValueArrayList localArguments; 

		switch (valueType) {

		case knowledgeAtom:
			// TODO implement KnowledgeAtom
			break;
			
		default:
			
		}
		
		createdValue = calculation.createValue(valueType, createdObject);
		
		return createdValue;
		
	}
	
}
