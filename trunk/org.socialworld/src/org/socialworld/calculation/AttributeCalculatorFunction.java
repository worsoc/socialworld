/**
 * 
 */
package org.socialworld.calculation;

/**
 * @author Mathias Sikos (tyloesand) The abstract class
 *         AttributeCalculatorFunction is the base class for all calculation
 *         functions. Every inherited class must override the method calculate.
 */
public abstract class AttributeCalculatorFunction {
	AttributeCalculatorFunction() {

	}

	/**
	 * The method is implemented in every child and calculates the value of an
	 * attribute.
	 * 
	 * @param attributeValue
	 *            the old attribute value
	 * @param offset
	 *            a value that can be added to the attribute value
	 * @return the calculated new attribute value
	 */
	public abstract byte calculate(byte attributeValue, byte offset);
}
