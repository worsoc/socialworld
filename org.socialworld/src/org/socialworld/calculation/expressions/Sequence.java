package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;

public class Sequence extends Expression {

	public Sequence(Expression[] expressions) {
		
		Expression[] expressionsA;
		Expression[] expressionsB;
		Expression[] expressionsC;
		
		int index;
		int count = expressions.length;
		
		
		if (count > 6) {
			
			expressionsA = new Expression[3];
			expressionsA[0] = expressions[0];
			expressionsA[1] = expressions[1];
			expressionsA[2] = expressions[2];
			
			expressionsB = new Expression[3];
			expressionsB[0] = expressions[3];
			expressionsB[1] = expressions[4];
			expressionsB[2] = expressions[5];
			
			expressionsC = new Expression[count - 6];
			for (index = 0; index < count - 6; index++ ) {
				expressionsC[index] = expressions[index + 6];
			}
			
			setExpression1(new Sequence(expressionsA));
			setExpression2(new Sequence(expressionsB));
			setExpression3(new Sequence(expressionsC));
			
		}
		else if (count > 3) {
			
			expressionsB = new Expression[3];
			expressionsB[0] = expressions[3];
			expressionsB[1] = expressions[4];
			expressionsB[2] = expressions[5];
			
			expressionsC = new Expression[count - 3];
			for (index = 0; index < count - 3; index++ ) {
				expressionsC[index] = expressions[index + 3];
			}
			
			setExpression1(Nothing.getInstance());
			setExpression2(new Sequence(expressionsB));
			setExpression3(new Sequence(expressionsC));
			
		}
		else {
			if (count == 3) {
				setExpression1(expressions[0]);
				setExpression2(expressions[1]);
				setExpression3(expressions[2]);
			}
			if (count == 2) {
				setExpression1(Nothing.getInstance());
				setExpression2(expressions[0]);
				setExpression3(expressions[1]);
			}
			if (count == 1) {
				setExpression1(Nothing.getInstance());
				setExpression2(Nothing.getInstance());
				setExpression3(expressions[0]);
			}
			if (count == 0) {
				setExpression1(Nothing.getInstance());
				setExpression2(Nothing.getInstance());
				setExpression3(Nothing.getInstance());
			}
		}
		
		
		setOperation(Expression_Function.sequence);
		setValid();
		
	}
	
	
}
