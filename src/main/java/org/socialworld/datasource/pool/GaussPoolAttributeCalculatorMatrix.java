package org.socialworld.datasource.pool;


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

	private void initialize() {
		
		//loadFromDB();
		loadTestData();
	}
	
	
	private void loadTestData() {
		
		FunctionByMatrix matrix;
		
		int j=0;
		for (int i = -CAPACITY_GPACM_ARRAY + 1; i < CAPACITY_GPACM_ARRAY; i++) {
			
			matrix = (FunctionByMatrix) FunctionPool.getInstance().getFunction(100 + j);
			setMatrix(i, matrix);
			
			j++;
			
			if (j == FunctionPool.COUNT_FbM_TEST_ENTRIES) j = 0;

		}
	}
	
	private void loadFromDB() {
		TableGaussACM table;
		int rowCount;
		int row;
		
		int gauss_index;
		int func_id;
		FunctionByMatrix matrix;
		
		table = new TableGaussACM();
		
		table.select(table.SELECT_ALL_COLUMNS, "", "");
		rowCount = table.rowCount();
		
		if (rowCount > 0) {
			
			for (row = 0; row < rowCount; row++) {
				gauss_index = table.getGaussIndex(row);
				func_id = table.getFunctionID(row);
				
				matrix = (FunctionByMatrix) FunctionPool.getInstance().getFunction(func_id);
				setMatrix(gauss_index, matrix);
			}
		}
	}
	
}
