package org.socialworld.calculation.descriptions;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.FunctionByExpression;

public class DescriptionBase {

	private List<FunctionByExpression> functions;
	
	public DescriptionBase() {
		
		functions = new  ArrayList<FunctionByExpression>();
		
	}
	

	public void addFunction(FunctionByExpression function) {

		functions.add(function);

	}
	
	public FunctionByExpression getFunction(int index) {
		
		return functions.get(index);
		
	}
	
	public int countFunctions() {
		
		return functions.size();
		
	}

}
