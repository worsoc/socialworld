package org.socialworld.calculation;

public class FunctionByMatrix_Matrix {
	public static final int MATRIX_CALCULATION_SIMPLE = 1;
	public static final int MATRIX_CALCULATION_COMPLEX = 2;
	
	private int numberOfColumns;
	
	private int functions[];
	private Value shares[];
	private Value offsets[];
	private FunctionByMatrix_InputType inputTypes[];
	

	public FunctionByMatrix_Matrix(int numberOfColumns) {
			this.numberOfColumns = numberOfColumns;
	}

	public FunctionByMatrix_Matrix(FunctionByMatrix_Matrix original, int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
		int count;
		count = original.length();
		this.functions = new int[count];
		for (int i = 0; i < count; i++) this.functions[i] = original.getFunction(i);
		this.shares = new Value[count];
		for (int i = 0; i < count; i++) this.shares[i] = original.getShare(i);
		if (original.isWithOffset()) {
			this.offsets = new Value[count];
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
		int matrixIndex = row * numberOfColumns + column;
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
		int matrixIndex = row * numberOfColumns + column;
		this.functions[matrixIndex] = function;
	}

	/**
	 * @return the share
	 */
	public Value getShare(int row, int column) {
		int matrixIndex = row * numberOfColumns + column;
		return shares[matrixIndex];
	}

	/**
	 * This method sets all shares of the calculation matrix  at one time
	 * We assume that the quantity of array entries is equal to the quantity of matrix elements.
	 * @param shares
	 *            the shares to set
	 */
	public void setShares( Value shares[]) {
		this.shares = shares;
	}
	
	/**
	 * @param share
	 *            the share to set
	 */
	public void setShare(int row, int column, Value share) {
		int matrixIndex = row * numberOfColumns + column;
		this.shares[matrixIndex] = share;
	}

	/**
	 * @return the offset
	 */
	public Value getOffset(int row, int column) {
		int matrixIndex = row * numberOfColumns + column;
		return offsets[matrixIndex];
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int row, int column, Value offset) {
		int matrixIndex = row * numberOfColumns + column;
		this.offsets[matrixIndex] = offset;
	}

	/**
	 * @return the input type of the calculation function
	 */
	public FunctionByMatrix_InputType getInputType(int row, int column) {
		int matrixIndex = row * numberOfColumns + column;
		return inputTypes[matrixIndex];
	}

	/**
	 * @param input type
	 *            the input type to set
	 */
	public void setInputType(int row, int column, FunctionByMatrix_InputType inputType) {
		int matrixIndex = row * numberOfColumns + column;
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
	public Value getShare(int matrixIndex) {
		return shares[matrixIndex];
	}

	/** 
	 * 
	 * @return the offset
	 */
	public Value getOffset(int matrixIndex) {
		return offsets[matrixIndex];
	}


}
