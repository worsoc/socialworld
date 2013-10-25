package org.socialworld.datasource;

import java.io.*;
import java.net.URL;

import org.socialworld.calculation.AttributeCalculatorMatrix;

public class AttributeCalculatorMatrixPool {
	public static final int CAPACITY_ACMP_ARRAY = 100;

	private static AttributeCalculatorMatrixPool instance;
	
	private static AttributeCalculatorMatrix matrixsForPositiveIndex[];
	private static AttributeCalculatorMatrix matrixsForNegativeIndex[];
	
	private AttributeCalculatorMatrixPool () {
		matrixsForPositiveIndex = new AttributeCalculatorMatrix[CAPACITY_ACMP_ARRAY];
		matrixsForNegativeIndex = new AttributeCalculatorMatrix[CAPACITY_ACMP_ARRAY];
		
		initialize();
	}
	
	public static AttributeCalculatorMatrixPool getInstance() {
		if (instance == null) {
			instance = new AttributeCalculatorMatrixPool();
		}
		return instance;
	}
	
	public AttributeCalculatorMatrix getMatrix(int index) {
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
	
	private void initialize() {
		
		initializeFromFile();
	}
	
	private void initializeFromFile() {
		
		try
		{
			String line;
			String values[];
			int matrixIndex;
			int functions[] = new int[64];
			float shares[] = new float[64];
			
			int index = 0;
			float deviation = 0;
			int vorzeichen = 1;
			int count = 0;
			
			AttributeCalculatorMatrix matrix;
	
			InputStream input = new URL("http://sourceforge.net/projects/socialworld/files/hmn_acm.swp.txt").openStream();
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
						shares[matrixIndex] = Float.parseFloat(values[matrixIndex]);
					}
				}
				
				if (line.startsWith("<matrix>")) {
				}

				if (line.startsWith("</matrix>")) {
					matrix = new AttributeCalculatorMatrix();
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


/*	
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
	
}
