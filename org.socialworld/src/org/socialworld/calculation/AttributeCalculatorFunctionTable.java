/**
 * 
 */
package org.socialworld.calculation;

import org.socialworld.attributes.Attribute;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class AttributeCalculatorFunctionTable extends
		AttributeCalculatorFunction {

	private byte attributes[];

	private int numberOfAttributes = Attribute.NUMBER_OF_ATTRIBUTES;

	public AttributeCalculatorFunctionTable() {
		attributes = new byte[numberOfAttributes];
	}

	public byte get(int index) {
		return attributes[index];
	}

	public byte calculate(byte offset, int row, int column, byte attributeValue) {
		int result;
		int matrixIndex;

		matrixIndex = row * this.numberOfAttributes + column;

		result = attributes[matrixIndex] + offset;
		
		if (result > 100) result = 100;
		if (result < 0) result = 0;
		return (byte) result;
	}

}