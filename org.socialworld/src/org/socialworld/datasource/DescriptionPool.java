package org.socialworld.datasource;

import java.util.ArrayList;
import java.util.ListIterator;

import org.socialworld.calculation.Expression;

public abstract class DescriptionPool {

	protected int sizeDescriptionsArray;

	private static ArrayList<Expression> expressions;
	private int sizeExpressionsArray;

	protected DescriptionPool() {
		
		expressions = new ArrayList<Expression> ();
		sizeExpressionsArray = 0;

	}
	
	protected abstract void initialize();
	
	protected void addExpression(Expression expression) {
		int ID;
	
		ID = expression.getID();
		if (ID > sizeExpressionsArray) ensureCapacity(ID);
		
		expressions.set(ID - 1, expression);
	}
	
	protected void ensureCapacity(int capacity) {
		if (capacity > sizeExpressionsArray) {
			for (int i = 1; i <= capacity - sizeExpressionsArray; i++)
				expressions.add(null);
			sizeExpressionsArray = capacity;
		}
	}

	protected void setTrueAndFalseExpressions() {
		ListIterator<Expression> iterator;
		Expression expression;

		int IDTrue;
		int IDFalse;
		
		iterator = expressions.listIterator();
		
		while (iterator.hasNext()) {
			expression = iterator.next();
			
			if (expression.getID() > 0 ) {
				IDTrue = expression.getIDTrue();
				IDFalse = expression.getIDFalse();
				
				if (IDTrue > 0) {
					expression.setTrueExpression(expressions.get(IDTrue - 1));
				}
				if (IDFalse > 0) {
					expression.setFalseExpression(expressions.get(IDFalse - 1));
				}
			}
		}
	}
}
