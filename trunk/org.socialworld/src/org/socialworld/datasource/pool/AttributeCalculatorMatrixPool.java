package org.socialworld.datasource.pool;


import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.FunctionByMatrix_Matrix;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.datasource.tablesPool.TablePoolACM;

public class AttributeCalculatorMatrixPool {
	public static final int CAPACITY_ACMP_ARRAY = 100;

	private static AttributeCalculatorMatrixPool instance;
	
	private static FunctionByMatrix_Matrix matrixsForPositiveIndex[];
	private static FunctionByMatrix_Matrix matrixsForNegativeIndex[];
	
	private int numberOfAttributes;
	
	private AttributeCalculatorMatrixPool () {
		matrixsForPositiveIndex = new FunctionByMatrix_Matrix[CAPACITY_ACMP_ARRAY];
		matrixsForNegativeIndex = new FunctionByMatrix_Matrix[CAPACITY_ACMP_ARRAY];
		
		// TODO: must work for different numbers
		numberOfAttributes = Attribute.NUMBER_OF_ATTRIBUTES;
		
		initialize();
	}
	
	public static AttributeCalculatorMatrixPool getInstance() {
		if (instance == null) {
			instance = new AttributeCalculatorMatrixPool();
		}
		return instance;
	}
	
	public FunctionByMatrix_Matrix getMatrix(int index) {
		if (index >= 0)
			if (CAPACITY_ACMP_ARRAY > index ) 
				return matrixsForPositiveIndex[index];
			else return null;
		else {
			index = index * -1;
			if (CAPACITY_ACMP_ARRAY > index ) 
				return matrixsForNegativeIndex[index];
			else return null;
		}	
	}
	
	private void setMatrix(int index, FunctionByMatrix_Matrix matrix) {
		if (index >= 0)
			if (CAPACITY_ACMP_ARRAY > index ) 
				matrixsForPositiveIndex[index] = matrix;
			else ;
		else {
			index = index * -1;
			if (CAPACITY_ACMP_ARRAY > index ) 
				matrixsForNegativeIndex[index] = matrix;
			else;
		}	
		
	}

	private void initialize() {
		
		loadFromDB();
	}
	
	
	
	
	private void loadFromDB() {
		TablePoolACM table;
		int rowCount;
		int row;
		
		int index;
		int matrixIndex;
		
		int lastIndex;
		
		int functions[];
		float share;
		Value shares[];
		
		FunctionByMatrix_Matrix matrix;
		
		table = new TablePoolACM();
		
		table.select(table.SELECT_ALL_COLUMNS, "", "ORDER BY index, row, col ");
		rowCount = table.rowCount();
		
		if (rowCount > 0) {
			lastIndex = table.getIndex(0);
			functions = new int[64];
			shares = new Value[64];
			matrixIndex = 0;
			
			for (row = 0; row < rowCount; row++) {
				index = table.getIndex(row);
				if (index != lastIndex) {
					matrix = new FunctionByMatrix_Matrix(numberOfAttributes);
					matrix.setFunctions( functions);
					matrix.setShares( shares );
					setMatrix(lastIndex, matrix);
					functions = new int[64];
					shares = new Value[64];
					lastIndex = index;
					matrixIndex = 0;
				}
				share = table.getShare(row);
				shares[matrixIndex] = 	new Value (Type.floatingpoint, share);
				functions[matrixIndex] = table.getFunction(row);

				matrixIndex++;
				
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
					// but only CAPACITY_ACMP_ARRAY tries
					if (index >= 0) {
						while (count < CAPACITY_ACMP_ARRAY) 
							if (index < CAPACITY_ACMP_ARRAY && index >= 0 && matrixsForPositiveIndex[index] == null ) {
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
						while (count < CAPACITY_ACMP_ARRAY) 
							if (index < CAPACITY_ACMP_ARRAY && index > 0 && matrixsForNegativeIndex[index] == null ) {
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



		int[] functions1 = {1,1,2,0,2,4,4,2,
							3,1,3,3,2,1,3,4,
							1,0,1,2,0,0,1,2,
							0,0,2,1,0,0,2,1,
							6,2,0,2,1,3,0,2,
							1,1,3,3,2,1,5,1,
							5,0,1,2,0,0,1,1,
							1,0,0,2,0,1,0,1};
		int[] functions2 = {1,1,2,0,2,4,4,2,
							3,1,3,3,2,1,3,4,
							1,0,1,2,0,0,1,2,
							0,0,2,1,0,0,2,1,
							6,2,0,2,1,3,0,2,
							1,1,3,3,2,1,5,1,
							5,0,1,2,0,0,1,1,
							1,0,0,2,0,1,0,1};
		int[] functions3 = {1,1,2,0,2,4,4,2,
							3,1,3,3,2,1,3,4,
							1,0,1,2,0,0,1,2,
							0,0,2,1,0,0,2,1,
							6,2,0,2,1,3,0,2,
							1,1,3,3,2,1,5,1,
							5,0,1,2,0,0,1,1,
							1,0,0,2,0,1,0,1};

		float[] shares1 = 
				  {(float)0.79, (float) 0.03, (float) 0.02, (float) 0.00, (float) 0.05, (float) 0.04, (float) 0.02, (float) 0.05,
				   (float)0.03, (float) 0.79, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03,
				   (float)0.05, (float) 0.00, (float) 0.79, (float) 0.05, (float) 0.00, (float) 0.00, (float) 0.05, (float) 0.06,
				   (float)0.03, (float) 0.00, (float) 0.06, (float) 0.79, (float) 0.00, (float) 0.00, (float) 0.07, (float) 0.05,
				   (float)0.07, (float) 0.02, (float) 0.00, (float) 0.02, (float) 0.79, (float) 0.03, (float) 0.00, (float) 0.07,
				   (float)0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.79, (float) 0.03, (float) 0.03,
				   (float)0.05, (float) 0.00, (float) 0.05, (float) 0.05, (float) 0.00, (float) 0.00, (float) 0.79, (float) 0.06,
				   (float)0.08, (float) 0.00, (float) 0.00, (float) 0.05, (float) 0.00, (float) 0.08, (float) 0.00, (float) 0.79};
	 	// just for test
		float[] shares2 =
				  {(float)0.79, (float) 0.03, (float) 0.03, (float) 0.00, (float) 0.04, (float) 0.03, (float) 0.04, (float) 0.04,
				   (float)0.03, (float) 0.79, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03,
				   (float)0.04, (float) 0.00, (float) 0.79, (float) 0.06, (float) 0.00, (float) 0.00, (float) 0.04, (float) 0.07,
				   (float)0.05, (float) 0.00, (float) 0.06, (float) 0.79, (float) 0.00, (float) 0.00, (float) 0.05, (float) 0.05,
				   (float)0.05, (float) 0.04, (float) 0.00, (float) 0.04, (float) 0.79, (float) 0.04, (float) 0.00, (float) 0.04,
				   (float)0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.79, (float) 0.03, (float) 0.03,
				   (float)0.05, (float) 0.00, (float) 0.05, (float) 0.05, (float) 0.00, (float) 0.00, (float) 0.79, (float) 0.06,
				   (float)0.07, (float) 0.00, (float) 0.00, (float) 0.07, (float) 0.00, (float) 0.07, (float) 0.00, (float) 0.79};
	 	// just for test
		float[] shares3 =
				  {(float)0.79, (float) 0.01, (float) 0.02, (float) 0.00, (float) 0.03, (float) 0.04, (float) 0.06, (float) 0.05,
				   (float)0.00, (float) 0.79, (float) 0.01, (float) 0.02, (float) 0.03, (float) 0.04, (float) 0.05, (float) 0.06,
				   (float)0.01, (float) 0.00, (float) 0.79, (float) 0.03, (float) 0.02, (float) 0.04, (float) 0.05, (float) 0.06,
				   (float)0.03, (float) 0.01, (float) 0.06, (float) 0.79, (float) 0.00, (float) 0.02, (float) 0.04, (float) 0.05,
				   (float)0.05, (float) 0.06, (float) 0.04, (float) 0.02, (float) 0.79, (float) 0.03, (float) 0.00, (float) 0.01,
				   (float)0.00, (float) 0.01, (float) 0.02, (float) 0.03, (float) 0.04, (float) 0.79, (float) 0.05, (float) 0.06,
				   (float)0.01, (float) 0.00, (float) 0.02, (float) 0.05, (float) 0.03, (float) 0.04, (float) 0.79, (float) 0.06,
				   (float)0.06, (float) 0.05, (float) 0.04, (float) 0.03, (float) 0.02, (float) 0.01, (float) 0.00, (float) 0.79};
*/
	

