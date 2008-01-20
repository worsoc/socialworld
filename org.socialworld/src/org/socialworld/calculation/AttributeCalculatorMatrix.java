/**
 * 
 */
package org.socialworld.calculation;

import org.socialworld.attributes.Attribute;

/**
 * @author Mathias Sikos (tyloesand)
 *
 */
public class AttributeCalculatorMatrix {

	private int functions[];
	private float shares[];
	private byte offsets[];
	
	public AttributeCalculatorMatrix() {
		
	}

	/**
	 * @return the function
	 */
	public int getFunction(int row, int column) {
		int matrixIndex = row * Attribute.NUMBER_OF_ATTRIBUTES + column;
		return functions[matrixIndex];
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(int row, int column, int function) {
		int matrixIndex = row * Attribute.NUMBER_OF_ATTRIBUTES + column;
		this.functions[matrixIndex] = function;
	}

	/**
	 * @return the share
	 */
	public float getShare(int row, int column) {
		int matrixIndex = row * Attribute.NUMBER_OF_ATTRIBUTES + column;
		return shares[matrixIndex];
	}

	/**
	 * @param share the share to set
	 */
	public void setShare(int row, int column, float share) {
		int matrixIndex = row * Attribute.NUMBER_OF_ATTRIBUTES + column;
		this.shares[matrixIndex] = share;
	}

	/**
	 * @return the offset
	 */
	public byte getOffset(int row, int column) {
		int matrixIndex = row * Attribute.NUMBER_OF_ATTRIBUTES + column;
		return offsets[matrixIndex];
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int row, int column, byte offset) {
		int matrixIndex = row * Attribute.NUMBER_OF_ATTRIBUTES + column;
		this.offsets[matrixIndex] = offset;
	}
	
	
}
