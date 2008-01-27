/**
 * 
 */
package org.socialworld.calculation;

/**
 * The abstract class AttributeCalculatorFunction is the base class for all
 * calculation functions. Every inherited class must override the method
 * calculate.
 * 
 * @author Mathias Sikos (tyloesand)
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
