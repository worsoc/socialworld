package org.socialworld.datasource.pool;


import org.socialworld.calculation.FunctionByMatrix;
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
/*
	private void initializeFromFile() {
		
		try
		{
			String line;
			String values[];
			int matrixIndex;
			int functions[] = new int[64];
			Value shares[] = new Value[64];
			
			int index = 0;
			float deviation = 0;
			int vorzeichen = 1;
			int count = 0;
			
			FunctionByMatrix_Matrix matrix;
	
			InputStream input = new URL("http://sourceforge.net/projects/socialworld/files/hmn_acm.swp").openStream();
			LineNumberReader lnr
			   = new LineNumberReader(new InputStreamReader(input));

			while ((line = lnr.readLine()) != null)
			{
				if (line.startsWith("["))
				{
					
					line = line.substring(1);
					line = line.replace("]", "");
					line = line.trim();
					
					deviation = Float.parseFloat(line);
					
				}
				
				if (line.startsWith("<functions>"))
				{
					
					line = line.substring(11);
					line = line.replace("</functions>", "");
					line = line.trim();
					values = line.split(",");
					for (matrixIndex = 0; matrixIndex < 64; matrixIndex++){
						functions[matrixIndex] = Integer.parseInt(values[matrixIndex]);
					}
				}
				

				if (line.startsWith("<shares>"))
				{
					
					line = line.substring(8);
					line = line.replace("</shares>", "");
					line = line.trim();
					values = line.split(",");
					for (matrixIndex = 0; matrixIndex < 64; matrixIndex++){
						shares[matrixIndex] =
								new Value (values[matrixIndex], Type.floatingpoint);
					}
				}
				
				if (line.startsWith("<matrix>")) {
				}

				if (line.startsWith("</matrix>")) {
					matrix = new FunctionByMatrix_Matrix(numberOfAttributes);
					matrix.setFunctions( functions);
					matrix.setShares( shares );
					
					index = (int) deviation;
					vorzeichen = 1;
					count = 0;
					// find the nearest free index
					// but only CAPACITY_GPACM_ARRAY tries
					if (index >= 0) {
						while (count < CAPACITY_GPACM_ARRAY) 
							if (index < CAPACITY_GPACM_ARRAY && index >= 0 && matrixsForPositiveIndex[index] == null ) {
								matrixsForPositiveIndex[index] = matrix;
								break;
							}
							else {
								count++;
								index = index + vorzeichen * count;
								vorzeichen = vorzeichen * -1;
							}
					}
					else {
						index = index * -1;
						while (count < CAPACITY_GPACM_ARRAY) 
							if (index < CAPACITY_GPACM_ARRAY && index > 0 && matrixsForNegativeIndex[index] == null ) {
								matrixsForNegativeIndex[index] = matrix;
								break;
							}
							else {
								count++;
								index = index + vorzeichen * count;
								vorzeichen = vorzeichen * -1;
							}
					}	
					
				}

			}
			lnr.close();
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Lesen der Datei");
			e.printStackTrace();
		}
	}

*/
