package org.socialworld.datasource;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.AttributeCalculatorMatrix;

public class AttributeCalculatorMatrixPool {

	private static AttributeCalculatorMatrixPool instance;
	
	private static List<AttributeCalculatorMatrix> matrixs;
	
	private AttributeCalculatorMatrixPool () {
		matrixs = new ArrayList<AttributeCalculatorMatrix> ();

		initialize();
	}
	
	public static AttributeCalculatorMatrixPool getInstance() {
		if (instance == null) {
			instance = new AttributeCalculatorMatrixPool();
		}
		return instance;
	}
	
	public AttributeCalculatorMatrix getMatrix(int index) {
		if (matrixs.size() > index ) 
			return matrixs.get(index);
		else {
			matrixs.add(createMatrix());
			return matrixs.get(matrixs.size()-1);
		}
	}
	
	private void initialize() {
		
		initializeFromFile();
		/*
		matrixs.add(createMatrix());
		matrixs.add(createMatrix());
		matrixs.add(createMatrix());
		matrixs.add(createMatrix());
		matrixs.add(createMatrix());
		matrixs.add(createMatrix());*/
	}
	
	private void initializeFromFile() {
		
		try
		{
			String line;
			String values[];
			int matrixIndex;
			int functions[] = new int[64];
			float shares[] = new float[64];
			
			AttributeCalculatorMatrix matrix;
	
			InputStream input = new URL("http://sourceforge.net/projects/socialworld/files/hmn_swacm.txt").openStream();
			LineNumberReader lnr
			   = new LineNumberReader(new InputStreamReader(input));

			//File input = new File("C:/Users/Mathias/workspace/socialworld/data/hmn_swacm.txt");
			//FileReader in = new FileReader(input);
			//LineNumberReader lnr = new LineNumberReader(in);
	
			while ((line = lnr.readLine()) != null)
			{
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
					matrixs.add(matrix);
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

	private AttributeCalculatorMatrix createMatrix () {
		AttributeCalculatorMatrix matrix = new AttributeCalculatorMatrix();
		matrix.setFunctions( getFunctionArray() );
		matrix.setShares( getShareArray() );
		return matrix;
	}
	
	private int[] getFunctionArray() {
		int[] functions;
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
		int count = matrixs.size();
		
		switch (count) {
		case 1:
			functions = functions2;
			break;
		case 2:
			functions = functions3;
			break;
		default: 
			functions = functions1;
			break;
		}
		return functions;
	}
	
	private float[] getShareArray() {
		float[] shares;
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
		int count = matrixs.size();
		
		switch (count) {
		case 1: 
			shares = shares2;
			break;
		case 2: 
			shares = shares3;
			break;
		default: 
			shares = shares1;
			break;
		}
		return shares;
	}
	
	
}
