/**
 * 
 */
package org.socialworld.calculation;

import org.socialworld.attributes.Attribute;

/**
 * The class implements the attribute
 *         calculation function as a table. The attribute value is interpreted
 *         as table's index.
 * @author Mathias Sikos (tyloesand) 
 */
public class AttributeCalculatorFunctionTable extends
		AttributeCalculatorFunction {

	private byte values[];

	private byte range = Attribute.ATTRIBUTE_RANGE;

	public AttributeCalculatorFunctionTable() {
		values = new byte[range + 1];
	}

	/**
	 * The method interprets the function as a table. The input value is
	 * understood as the index of a table and table's value at the index is the
	 * function value.
	 */
	public byte calculate(byte inputValue) {
		int result;

		if (inputValue > range)
			inputValue = range;
		if (inputValue < 0)
			inputValue = 0;

		result = values[inputValue];

		if (result > 100)
			result = 100;
		if (result < 0)
			result = 0;
		return (byte) result;
	}

}