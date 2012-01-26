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
	 * @param inputValue
	 * 				x
	 *            the inputValue for the calculation
	 *            for example an attribute value or the attribute's change value
	 * @return 
	 * 				f(x)
	 * 				the calculation result
	 */
	public abstract int calculate(int inputValue);
}
