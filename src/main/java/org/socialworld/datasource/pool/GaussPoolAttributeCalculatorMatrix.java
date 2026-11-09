/*
 * Social World
 * Copyright (C) 2012  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.datasource.pool;


import org.socialworld.calculation.FunctionBase;
import org.socialworld.calculation.functions.FunctionByMatrix;
import org.socialworld.datasource.tablesPool.TableGaussACM;

public class GaussPoolAttributeCalculatorMatrix {
	public static final int CAPACITY_GPACM_ARRAY = 100;

	private static GaussPoolAttributeCalculatorMatrix instance;
	
	private static FunctionByMatrix matrixsForPositiveIndex[];
	private static FunctionByMatrix matrixsForNegativeIndex[];
	
	
	private GaussPoolAttributeCalculatorMatrix () {
		matrixsForPositiveIndex = new FunctionByMatrix[CAPACITY_GPACM_ARRAY];
		matrixsForNegativeIndex = new FunctionByMatrix[CAPACITY_GPACM_ARRAY];
		
		
		initialize();
	}
	
	public static GaussPoolAttributeCalculatorMatrix getInstance() {
		if (instance == null) {
			instance = new GaussPoolAttributeCalculatorMatrix();
		}
		return instance;
	}
	
	public FunctionByMatrix getMatrix(int index) {
		if (index >= 0)
			if (CAPACITY_GPACM_ARRAY > index ) 
				return matrixsForPositiveIndex[index];
			else return null;
		else {
			index = index * -1;
			if (CAPACITY_GPACM_ARRAY > index ) 
				return matrixsForNegativeIndex[index];
			else return null;
		}	
	}
	
	private void setMatrix(int index, FunctionByMatrix matrix) {
		if (index >= 0)
			if (CAPACITY_GPACM_ARRAY > index ) 
				matrixsForPositiveIndex[index] = matrix;
			else ;
		else {
			index = index * -1;
			if (CAPACITY_GPACM_ARRAY > index ) 
				matrixsForNegativeIndex[index] = matrix;
			else;
		}	
		
	}

	/**
	 * Loads the mapping from the database. Every gauss index that stays empty
	 * (no row, no connection, or a func_id without a matrix) gets a test matrix.
	 */
	private void initialize() {
		
		try {
			loadFromDB();
		}
		catch (RuntimeException e) {
			e.printStackTrace();
		}
		
		fillGapsWithTestData();
	}
	
	private void fillGapsWithTestData() {
		
		FunctionByMatrix matrix;
		int countGaps = 0;
		
		int j=0;
		for (int i = -CAPACITY_GPACM_ARRAY + 1; i < CAPACITY_GPACM_ARRAY; i++) {
			
			if (getMatrix(i) == null) {
				matrix = (FunctionByMatrix) FunctionPool.getInstance().getFunction(100 + j);
				setMatrix(i, matrix);
				countGaps++;
				
				j++;
				if (j == FunctionPool.COUNT_FbM_TEST_ENTRIES) j = 0;
			}
		}
		
		if (countGaps > 0) {
			System.out.println("GaussPoolAttributeCalculatorMatrix: " + countGaps + " gauss indices without a matrix from the database get test data");
		}
	}
	
	private void loadFromDB() {
		TableGaussACM table;
		int rowCount;
		int row;
		
		int gauss_index;
		int func_id;
		FunctionBase function;
		
		table = new TableGaussACM();
		
		table.select(table.SELECT_ALL_COLUMNS, "", "");
		rowCount = table.rowCount();
		
		if (rowCount > 0) {
			
			for (row = 0; row < rowCount; row++) {
				gauss_index = table.getGaussIndex(row);
				func_id = table.getFunctionID(row);
				
				function = FunctionPool.getInstance().getFunction(func_id);
				if (function instanceof FunctionByMatrix) {
					setMatrix(gauss_index, (FunctionByMatrix) function);
				}
			}
		}
	}
	
}
