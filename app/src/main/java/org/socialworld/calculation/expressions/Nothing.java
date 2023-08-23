package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;

public class Nothing extends Expression {

	private static Nothing instance;
	
	private Nothing() {
		super(Expression_Function.nothing);
	}
	
	public static Nothing getInstance() {
		if (instance == null) {
			instance = new Nothing();
		}
		return instance;
		
	}

}
