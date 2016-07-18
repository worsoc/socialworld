package org.socialworld.data;

import org.socialworld.datasource.tablesPool.TablePoolMatrix;
import org.socialworld.datasource.tablesPool.TablePoolMatrixRowCol;

public class FillWithTestData_ACM {

	int[] functions1 = {1,1,2,0,2,4,4,2,0,
						3,1,3,3,2,1,3,4,0,
						1,0,1,2,0,0,1,2,0,
						0,0,2,1,0,0,2,1,0,
						6,2,0,2,1,3,0,2,0,
						1,1,3,3,2,1,5,1,0,
						5,0,1,2,0,0,1,1,0,
						1,0,0,2,0,1,0,1,0,
						0,0,0,0,0,0,0,0,1};
	
	int[] functions2 = {1,1,2,0,2,4,4,2,0,
						3,1,3,3,2,1,3,4,0,
						1,0,1,2,0,0,1,2,0,
						0,0,2,1,0,0,2,1,0,
						6,2,0,2,1,3,0,2,0,
						1,1,3,3,2,1,5,1,0,
						5,0,1,2,0,0,1,1,0,
						1,0,0,2,0,1,0,1,0,
						0,0,0,0,0,0,0,0,1};
	
	int[] functions3 = {1,1,2,0,2,4,4,2,0,
						3,1,3,3,2,1,3,4,0,
						1,0,1,2,0,0,1,2,0,
						0,0,2,1,0,0,2,1,0,
						6,2,0,2,1,3,0,2,0,
						1,1,3,3,2,1,5,1,0,
						5,0,1,2,0,0,1,1,0,
						1,0,0,2,0,1,0,1,0,
						0,0,0,0,0,0,0,0,1};

	float[] shares1 = 
	  {(float)0.79, (float) 0.03, (float) 0.02, (float) 0.00, (float) 0.05, (float) 0.04, (float) 0.02, (float) 0.05, (float) 0.00,
	   (float)0.03, (float) 0.79, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.00,
	   (float)0.05, (float) 0.00, (float) 0.79, (float) 0.05, (float) 0.00, (float) 0.00, (float) 0.05, (float) 0.06, (float) 0.00,
	   (float)0.03, (float) 0.00, (float) 0.06, (float) 0.79, (float) 0.00, (float) 0.00, (float) 0.07, (float) 0.05, (float) 0.00,
	   (float)0.07, (float) 0.02, (float) 0.00, (float) 0.02, (float) 0.79, (float) 0.03, (float) 0.00, (float) 0.07, (float) 0.00,
	   (float)0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.79, (float) 0.03, (float) 0.03, (float) 0.00,
	   (float)0.05, (float) 0.00, (float) 0.05, (float) 0.05, (float) 0.00, (float) 0.00, (float) 0.79, (float) 0.06, (float) 0.00,
	   (float)0.08, (float) 0.00, (float) 0.00, (float) 0.05, (float) 0.00, (float) 0.08, (float) 0.00, (float) 0.79, (float) 0.00,
	   (float)0.00, (float) 0.00, (float) 0.00, (float) 0.00, (float) 0.00, (float) 0.00, (float) 0.00, (float) 0.00, (float) 1.00   };

// just for test
	float[] shares2 =
	  {(float)0.76, (float) 0.03, (float) 0.03, (float) 0.00, (float) 0.04, (float) 0.03, (float) 0.04, (float) 0.04, (float) 0.03,
	   (float)0.03, (float) 0.76, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03,
	   (float)0.04, (float) 0.00, (float) 0.76, (float) 0.06, (float) 0.00, (float) 0.00, (float) 0.04, (float) 0.07, (float) 0.03,
	   (float)0.05, (float) 0.00, (float) 0.06, (float) 0.76, (float) 0.00, (float) 0.00, (float) 0.05, (float) 0.05, (float) 0.03,
	   (float)0.05, (float) 0.04, (float) 0.00, (float) 0.04, (float) 0.76, (float) 0.04, (float) 0.00, (float) 0.04, (float) 0.03,
	   (float)0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.76, (float) 0.03, (float) 0.03, (float) 0.03,
	   (float)0.05, (float) 0.00, (float) 0.05, (float) 0.05, (float) 0.00, (float) 0.00, (float) 0.76, (float) 0.06, (float) 0.03,
	   (float)0.07, (float) 0.00, (float) 0.00, (float) 0.07, (float) 0.00, (float) 0.07, (float) 0.00, (float) 0.76, (float) 0.03,
	   (float)0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.03, (float) 0.76   };

// just for test
	float[] shares3 =
	  {(float)0.76, (float) 0.01, (float) 0.02, (float) 0.03, (float) 0.03, (float) 0.04, (float) 0.06, (float) 0.05, (float) 0.01,
	   (float)0.01, (float) 0.76, (float) 0.01, (float) 0.02, (float) 0.03, (float) 0.04, (float) 0.05, (float) 0.06, (float) 0.02,
	   (float)0.01, (float) 0.00, (float) 0.76, (float) 0.03, (float) 0.02, (float) 0.04, (float) 0.05, (float) 0.06, (float) 0.03,
	   (float)0.03, (float) 0.01, (float) 0.06, (float) 0.76, (float) 0.00, (float) 0.02, (float) 0.03, (float) 0.05, (float) 0.04,
	   (float)0.05, (float) 0.06, (float) 0.04, (float) 0.00, (float) 0.76, (float) 0.03, (float) 0.00, (float) 0.01, (float) 0.05,
	   (float)0.00, (float) 0.01, (float) 0.02, (float) 0.03, (float) 0.04, (float) 0.76, (float) 0.04, (float) 0.06, (float) 0.04,
	   (float)0.01, (float) 0.00, (float) 0.02, (float) 0.05, (float) 0.03, (float) 0.04, (float) 0.76, (float) 0.06, (float) 0.03,
	   (float)0.06, (float) 0.05, (float) 0.04, (float) 0.03, (float) 0.02, (float) 0.01, (float) 0.01, (float) 0.76, (float) 0.02,
	   (float)0.06, (float) 0.05, (float) 0.04, (float) 0.03, (float) 0.02, (float) 0.01, (float) 0.01, (float) 0.02, (float) 0.76   };


	int[] inputTypes =
			   {1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1};
	
	float[] offsets =
		   {0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,};
	
	public void fill(int func_id) {
		
		TablePoolMatrix tableMatrix;
		
		tableMatrix = new TablePoolMatrix();
		
		tableMatrix.insert(func_id, 9, 9, 3, 1);
		
		fillMatrixRowCol(func_id);
		
	}
	
	private  void fillMatrixRowCol(int func_id) {

		TablePoolMatrixRowCol tableMatrixRowCol;
		
		tableMatrixRowCol = new TablePoolMatrixRowCol();
	
		float[] shares;
		int[] functions;
		float[] offsets;
		
		int index;
		
		index = func_id % 3;
		
		
		switch (index) {
		case 0: 
			shares = shares1;
			functions = functions1;
			offsets = this.offsets;
		case 1: 
			shares = shares2;
			functions = functions2;
			offsets = this.offsets;
		case 2: 
			shares = shares3;
			functions = functions3;
			offsets = this.offsets;
		default:
			shares = shares1;
			functions = functions1;
			offsets = this.offsets;
		}
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				
				tableMatrixRowCol.insert(func_id, row, col, shares[row*9+col], functions[row*9+col], offsets[row*9+col], inputTypes[row*9+col]);
			}
				
				
		}
		
		
	}
	
	
	
}
