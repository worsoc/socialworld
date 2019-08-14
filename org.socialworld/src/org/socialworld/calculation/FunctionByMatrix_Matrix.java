/*
* Social World
* Copyright (C) 2014  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.calculation;

public class FunctionByMatrix_Matrix {
	public static final int CALCULATION_MODE_MATRIX_X_VECTOR_SIMPLE = 1; //MATRIX_CALCULATION_SIMPLE = 1;
	public static final int CALCULATION_MODE_MATRIX_X_VECTOR_COMPLEX = 2; //MATRIX_CALCULATION_COMPLEX = 2;
	public static final int CALCULATION_MODE_VECTOR_X_VECTOR = 11;    // Matrix is a vector (a matrix with one row)
	
	private int numberOfColumns;
	private boolean calculateDiv100;
	
	private int functions[];
	private Value shares[];
	private Value offsets[];
	private FunctionByMatrix_InputType inputTypes[];
	

	public FunctionByMatrix_Matrix(int numberOfColumns) {
			this.numberOfColumns = numberOfColumns;
			this.calculateDiv100 = false;
	}

	public FunctionByMatrix_Matrix(String sharesAsLine, String functionsAsLine, String offsetsAsLine, int numberOfColumns){

		this.numberOfColumns = numberOfColumns;

		String[] shares = sharesAsLine.split("\\s+");
		Value[] sharesMatrix = new Value[shares.length];
		
		for(int i = 0; i < shares.length; i++ )
		{
			sharesMatrix[i] =  new Value(shares[i], Type.integer);
		}
		
		setShares(sharesMatrix);

		
		String[] functions = functionsAsLine.split("\\s+");
		int[] functionsMatrix = new int[functions.length];
		
		for(int i = 0; i < functions.length; i++ )
		{
			functionsMatrix[i] =  Integer.parseInt(functions[i]);
		}
		
		setFunctions(functionsMatrix);
		
		if (offsetsAsLine.length() > 0) {
			
			String[] offsets = offsetsAsLine.split("\\s+");
			if (offsets.length > 0) {
				
				Value[] offsetsMatrix = new Value[offsets.length];
				
				for(int i = 0; i < offsets.length; i++ )
				{
					offsetsMatrix[i] =  new Value(offsets[i], Type.integer);
				}
				
				setOffsets(offsetsMatrix);
				
			}

		}	
		
	}
	
	public FunctionByMatrix_Matrix(FunctionByMatrix_Matrix original) {
		
		this.numberOfColumns = original.numberOfColumns;
		this.calculateDiv100 = original.calculateDiv100;
		
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
	
		if (shares.length > 0) {
			this.calculateDiv100 = (shares[0].getType() == Type.integer);
		}
	}
	
	/**
	 * @param share
	 *            the share to set
	 */
	public void setShare(int row, int column, Value share) {
		
		this.calculateDiv100 = (share.getType() == Type.integer);
		
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
	 * This method sets all offsets of the calculation matrix  at one time
	 * We assume that the quantity of array entries is equal to the quantity of matrix elements.
	 * @param offsets
	 *            the offsets to set
	 */
	public void setOffsets( Value offsets[]) {
		
		this.offsets = offsets;
		
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

	public boolean isWithDiv100() {
		
		return this.calculateDiv100;
		
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
