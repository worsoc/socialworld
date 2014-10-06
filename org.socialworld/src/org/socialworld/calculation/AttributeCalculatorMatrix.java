/**
 * 
 */
package org.socialworld.calculation;


/**
 *  An attribute calculation matrix holds the
 *         information how to compute attribute values. It holds calculation
 *         functions, shares and offsets for every attribute dependent to all
 *         attributes. Access to functions, shares and offset is given by set
 *         and get methods which have the matrix row and matrix column as
 *         parameters.
 * @author Mathias Sikos (tyloesand)
 */
public class AttributeCalculatorMatrix {
	private int numberOfAttributes;
	
	private int functions[];
	private float shares[];
	private int offsets[];
	private CalculationInputType inputTypes[];
	

	public AttributeCalculatorMatrix(int numberOfAttributes) {
			this.numberOfAttributes = numberOfAttributes;
	}

	public AttributeCalculatorMatrix(AttributeCalculatorMatrix original, int numberOfAttributes) {
		this.numberOfAttributes = numberOfAttributes;
		int count;
		count = original.length();
		this.functions = new int[count];
		for (int i = 0; i < count; i++) this.functions[i] = original.getFunction(i);
		this.shares = new float[count];
		for (int i = 0; i < count; i++) this.shares[i] = original.getShare(i);
		if (original.isWithOffset()) {
			this.offsets = new int[count];
			for (int i = 0; i < count; i++) this.offsets[i] = original.getOffset(i);
		}	
	}
	
	public int length() {
		return functions.length;
	}

	/** 
	 * 
	 * @return the function
	 */
	public int getFunction(int row, int column) {
		int matrixIndex = row * numberOfAttributes + column;
		return functions[matrixIndex];
	}

	/**
	 * This method sets all attribute calculation functions (its numbers) at one time
	 * We assume that the quantity of array entries is equal to the quantity of matrix elements.
	 * @param functions
	 *            the function to set
	 */
	public void setFunctions( int functions[]) {
		this.functions = functions;
	}
	
	/**
	 * This method sets an attribute calculation function (its number)
	 *  for one row and one column of the matrix
	 * @param row
	 * 			the row of the attribute calculation matrix
	 * @param column
	 * 			the column of the attribute calculation matrix
	 * @param function
	 *            the function to set
	 */
	public void setFunction(int row, int column, int function) {
		int matrixIndex = row * numberOfAttributes + column;
		this.functions[matrixIndex] = function;
	}

	/**
	 * @return the share
	 */
	public float getShare(int row, int column) {
		int matrixIndex = row * numberOfAttributes + column;
		return shares[matrixIndex];
	}

	/**
	 * This method sets all shares of the calculation matrix  at one time
	 * We assume that the quantity of array entries is equal to the quantity of matrix elements.
	 * @param shares
	 *            the shares to set
	 */
	public void setShares( float shares[]) {
		this.shares = shares;
	}
	
	/**
	 * @param share
	 *            the share to set
	 */
	public void setShare(int row, int column, float share) {
		int matrixIndex = row * numberOfAttributes + column;
		this.shares[matrixIndex] = share;
	}

	/**
	 * @return the offset
	 */
	public int getOffset(int row, int column) {
		int matrixIndex = row * numberOfAttributes + column;
		return offsets[matrixIndex];
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int row, int column, int offset) {
		int matrixIndex = row * numberOfAttributes + column;
		this.offsets[matrixIndex] = offset;
	}

	/**
	 * @return the input type of the calculation function
	 */
	public CalculationInputType getInputType(int row, int column) {
		int matrixIndex = row * numberOfAttributes + column;
		return inputTypes[matrixIndex];
	}

	/**
	 * @param input type
	 *            the input type to set
	 */
	public void setInputType(int row, int column, CalculationInputType inputType) {
		int matrixIndex = row * numberOfAttributes + column;
		this.inputTypes[matrixIndex] = inputType;
	}
	
	public boolean isWithOffset() {
		return this.offsets.length > 0;
	}
	
	/** 
	 * 
	 * @return the function
	 */
	public int getFunction(int matrixIndex) {
		return functions[matrixIndex];
	}

	/** 
	 * 
	 * @return the share
	 */
	public float getShare(int matrixIndex) {
		return shares[matrixIndex];
	}

	/** 
	 * 
	 * @return the offset
	 */
	public int getOffset(int matrixIndex) {
		return offsets[matrixIndex];
	}

}
