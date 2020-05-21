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

	public CreateValue(Type type, Expression exp1) {
		
		super();
		
		setOperation(Expression_Function.create);
		
		setExpression1(exp1);
		setValue(new Value(Type.integer, type.getIndex()));
		
		setValid();
		
	}
	
	protected CreateValue(Type type) {
		
		super();
		
		setOperation(Expression_Function.create);
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

	
}
