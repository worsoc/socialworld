package org.socialworld.calculation.expressions;

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.application.ActionCreator;

public class CreateValue extends Expression {

	public CreateValue(Type type, Expression exp1) {
		
		super();
		
		setOperation(Expression_Function.create);
		
		setExpression1(exp1);
		setValue(new Value(Type.integer, type.getIndex()));
		
		setValid();
		
	}
	
	protected Value createValue(Type valueType, Value[] arguments) {

		Object createdObject;
		Value createdValue;
		
		Value localArguments[] = new Value[8];
		
		// copy attribute array to local argument array
		localArguments[0] = calculation.copy(arguments[0]);
		localArguments[1] = new Value(Type.actionType, "actiontype", ActionType.ignore);  
		localArguments[2] = new Value(Type.actionMode, "actionmode", ActionMode.ignore);  
		localArguments[3] = new Value(Type.floatingpoint, "intensity", 0.0F);  
		localArguments[4] = new Value(Type.time, "mintime", new Time(true, 0));  
		localArguments[5] = new Value(Type.time, "maxtime", new Time(true, 0));  
		localArguments[6] = new Value(Type.integer, "priority", 0);  
		localArguments[7] = new Value(Type.longinteger, "duration", 0L); 
		
		evaluateExpression1(localArguments);
		
		ActionType type = (ActionType) localArguments[1].getValueCopy();
		ActionMode mode = (ActionMode) localArguments[2].getValueCopy();
		float intensity = (float) localArguments[3].getValueCopy();
		Time minTime = (Time) localArguments[4].getValueCopy();
		Time maxTime = (Time) localArguments[5].getValueCopy();
		int priority = (int) localArguments[6].getValueCopy();
		long duration = (long) localArguments[7].getValueCopy();
		
		createdObject = ActionCreator.createAction(type, mode, intensity, minTime, maxTime, priority, duration) ;
		createdValue = calculation.createValue(valueType, createdObject);
		
		return createdValue;
		
	}

	
}
