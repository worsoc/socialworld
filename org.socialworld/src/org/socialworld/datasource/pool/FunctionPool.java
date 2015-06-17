/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.datasource.pool;

import org.socialworld.calculation.FunctionBase;
import org.socialworld.calculation.FunctionByMatrix;
import org.socialworld.calculation.FunctionByMatrix_Matrix;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.datasource.tablesPool.TablePoolMatrix;
import org.socialworld.datasource.tablesPool.TablePoolMatrixRowCol;

/**
 * @author Mathias Sikos
 *
 */
public class FunctionPool {

	public static final int CAPACITY_FP_ARRAY = 1000;

	private static FunctionPool instance;
	
	private static FunctionBase functions[];

	private FunctionPool () {
		functions = new FunctionBase[CAPACITY_FP_ARRAY];
		
		initialize();
	}

	public static FunctionPool getInstance() {
		if (instance == null) {
			instance = new FunctionPool();
		}
		return instance;
	}

	private void setFunction(int index, FunctionBase function) {
		if (index >= 0)
			if (CAPACITY_FP_ARRAY > index ) 	functions[index] = function;
		
	}
	
	public FunctionBase getFunction(int index) {
		if (index >= 0)
			if (CAPACITY_FP_ARRAY > index ) 	return functions[index];
	   return null;
	}

	private void initialize() {
		
		loadMatrixFromDB();
		

	}
	
	private void loadMatrixFromDB() {
		TablePoolMatrixRowCol tableRowCol;
		TablePoolMatrix tableMatrix;

		int rowCount;
		int row;
		
		int func_id;
		int offset;
		int matrixRow;
		int matrixCol;
		int share;
		
		int functions[];
		Value shares[];
		Value offsets[];

		int matrixIndex;
		int rowMulCol;
		int anzRow;
		int anzCol;
		int type;
		
		
		FunctionByMatrix_Matrix matrix;
		
		tableMatrix = new TablePoolMatrix();
		tableRowCol = new TablePoolMatrixRowCol();

		tableMatrix.select(tableMatrix.SELECT_ALL_COLUMNS, "", "ORDER BY func_id");
		rowCount = tableMatrix.rowCount();
		
		if (rowCount > 0) {
				
			for (row = 0; row < rowCount; row++) {
				
				func_id = tableMatrix.getFunctionID(row);
				anzRow = tableMatrix.getAnzRow(row);
				anzCol = tableMatrix.getAnzCol(row);
				type = tableMatrix.getType(row);
				
				rowMulCol = anzRow * anzCol;
				
				tableRowCol.select(tableRowCol.SELECT_ALL_COLUMNS, "WHERE func_Id = " + func_id, "ORDER BY func_id, row, col");
				if 	(tableRowCol.rowCount() == rowMulCol) {
					
					functions = new int[rowMulCol];
					shares = new Value[rowMulCol];
					offsets = new Value[rowMulCol];
					matrixIndex = 0;
					
					for (matrixRow = 0; matrixRow < anzRow; matrixRow++) {
						for (matrixCol = 0; matrixCol < anzCol; matrixCol++) {
							
							share = tableRowCol.getShare(row);
							shares[matrixIndex] = 	new Value (Type.getName(type), share);

							functions[matrixIndex] = tableRowCol.getFunction(row);

							offset = tableRowCol.getOffset(row);
							offsets[matrixIndex] = 	new Value (Type.getName(type), offset);

							matrixIndex++;
						
						}
					}

					
					matrix = new FunctionByMatrix_Matrix(anzCol);
					matrix.setFunctions( functions);
					matrix.setShares( shares );
					matrix.setOffsets( shares );
					
					setFunction(func_id, new FunctionByMatrix(matrix));
				
				}
				
			}
		
		}
	}

}
