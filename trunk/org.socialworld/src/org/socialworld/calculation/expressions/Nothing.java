package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;

public class Nothing extends Expression {

	private static Nothing instance;
	
	private Nothing() {
		super();
		setValid();
	}
	
	public static Nothing getInstance() {
		if (instance == null) {
			instance = new Nothing();
		}
		return instance;
		
	}

}
