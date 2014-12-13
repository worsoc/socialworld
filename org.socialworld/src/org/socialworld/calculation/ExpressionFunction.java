/**
 * 
 */
package org.socialworld.calculation;

/**
 *  The enumeration holds all possible
 *         expression types. There are mathematical comparison to a constant, an
 *         addition or multiplication by a constnt or the replacement by a
 *         constant.
 * @author Mathias Sikos (tyloesand)
 */
public enum ExpressionFunction {
	condition, addition, multiplication, replacement, comparison, branching, value, attributeValue
}
