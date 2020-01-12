package org.socialworld.calculation.descriptions;

import org.socialworld.calculation.FunctionByExpression;

public class EventPerceptionDescription extends DescriptionBase {

	public void addFunctionCreatePerception(FunctionByExpression function) {

		addFunction(function);

	}
	
	public FunctionByExpression getFunctionCreatePerception(int index) {
		
		return getFunction(index);
		
	}
	

}
