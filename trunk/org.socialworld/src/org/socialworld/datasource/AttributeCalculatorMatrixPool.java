package org.socialworld.datasource;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.socialworld.calculation.AttributeCalculatorMatrix;

public class AttributeCalculatorMatrixPool {

	private static final Logger logger = Logger.getLogger(AttributeCalculatorMatrixPool.class);
	private static AttributeCalculatorMatrixPool instance;
	
	private static List<AttributeCalculatorMatrix> matrixs;
	
	private AttributeCalculatorMatrixPool () {
		logger.debug("create AttributeCalculatorMatrixPool");
		matrixs = new ArrayList<AttributeCalculatorMatrix> ();

		initialize();
	}
	
	public AttributeCalculatorMatrixPool getInstance() {
		if (instance == null) {
			instance = new AttributeCalculatorMatrixPool();
		}
		return instance;
	}
	
	private void initialize() {
		matrixs.add(createMatrix());
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
		case 1: functions = functions2;
		case 2: functions = functions3;
		default: functions = functions1;
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
		float[] shares2 =
				  {(float)0.79, (float) 0.03, (float) 0.02, (float) 0.00, (float) 0.05, (float) 0.04, (float) 0.02, (float) 0.05,
				   (float)0.03, (float) 0.79, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03,
				   (float)0.05, (float) 0.00, (float) 0.79, (float) 0.05, (float) 0.00, (float) 0.00, (float) 0.05, (float) 0.06,
				   (float)0.03, (float) 0.00, (float) 0.06, (float) 0.79, (float) 0.00, (float) 0.00, (float) 0.07, (float) 0.05,
				   (float)0.07, (float) 0.02, (float) 0.00, (float) 0.02, (float) 0.79, (float) 0.03, (float) 0.00, (float) 0.07,
				   (float)0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.79, (float) 0.03, (float) 0.03,
				   (float)0.05, (float) 0.00, (float) 0.05, (float) 0.05, (float) 0.00, (float) 0.00, (float) 0.79, (float) 0.06,
				   (float)0.08, (float) 0.00, (float) 0.00, (float) 0.05, (float) 0.00, (float) 0.08, (float) 0.00, (float) 0.79};
		float[] shares3 =
				  {(float)0.79, (float) 0.03, (float) 0.02, (float) 0.00, (float) 0.05, (float) 0.04, (float) 0.02, (float) 0.05,
				   (float)0.03, (float) 0.79, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03,
				   (float)0.05, (float) 0.00, (float) 0.79, (float) 0.05, (float) 0.00, (float) 0.00, (float) 0.05, (float) 0.06,
				   (float)0.03, (float) 0.00, (float) 0.06, (float) 0.79, (float) 0.00, (float) 0.00, (float) 0.07, (float) 0.05,
				   (float)0.07, (float) 0.02, (float) 0.00, (float) 0.02, (float) 0.79, (float) 0.03, (float) 0.00, (float) 0.07,
				   (float)0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.79, (float) 0.03, (float) 0.03,
				   (float)0.05, (float) 0.00, (float) 0.05, (float) 0.05, (float) 0.00, (float) 0.00, (float) 0.79, (float) 0.06,
				   (float)0.08, (float) 0.00, (float) 0.00, (float) 0.05, (float) 0.00, (float) 0.08, (float) 0.00, (float) 0.79};
		int count = matrixs.size();
		
		switch (count) {
		case 1: shares = shares2;
		case 2: shares = shares3;
		default: shares = shares1;
		}
		return shares;
	}
}
