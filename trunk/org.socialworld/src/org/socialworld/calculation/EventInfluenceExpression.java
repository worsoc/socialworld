/**
 * 
 */
package org.socialworld.calculation;

import org.socialworld.attributes.AttributeArray;

/**
 * The class is an implementation of an
 *         expression. The expression is part of a term that consists of
 *         mathematical comparisons and mathematical operations which are
 *         executed dependent to the evaluation result of the comparison. An
 *         expression is a comparison to a constant OR addition of a constant to
 *         the attribute value OR the multiplication of a constant with the
 *         attribute value OR the replacement of the attribute value by the
 *         constant. If the expression is a comparison there are given two
 *         further expressions for the boolean evaluation result of the
 *         comparison. So a expression evaluates an expression recursively.

 * @author Mathias Sikos (tyloesand)
 */
public class EventInfluenceExpression extends Expression {

	

	protected  void addition() {
		result = (int) value + (int) constant;
	}

	protected  void multiplication() {
		result = (int) value * (int) constant;
	}

	protected  void replacement( ) {
		result = (int) constant;
	}

	protected  void identity() {
		result = (int) value;
	}
	
	protected  void defaultFunction() {
		result = (int) value;
	}

		
	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() calls the implementation of calculation functions.
	 * Finally the new attribute value is returned.
	 */
	public Object evaluateExpression(AttributeArray attributeArray, Object value) {
		int tmp;
		tmp = (int) super.evaluateExpression(attributeArray, value);
		
		
		tmp += 0.5;

		if (tmp > AttributeArray.ATTRIBUTE_RANGE)
			return AttributeArray.ATTRIBUTE_RANGE;
		if (tmp < 0)
			return 0;
		
		return tmp;
	}
	

}
