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

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.FunctionBase;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.FunctionByMatrix;
import org.socialworld.calculation.FunctionByMatrix_Matrix;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.ValueInterpreteAs;
import org.socialworld.calculation.expressions.CreateActionExpression;
import org.socialworld.calculation.expressions.Nothing;
import org.socialworld.datasource.tablesPool.TablePoolFunctionExpression;
import org.socialworld.datasource.tablesPool.TablePoolMatrix;
import org.socialworld.datasource.tablesPool.TablePoolMatrixRowCol;

/**
 * @author Mathias Sikos
 *
 */
public class FunctionPool {

	public static final int COUNT_FbM_TEST_ENTRIES = 54;		// Anzahl Testeintraege FunctionByMatrix
	public static final int COUNT_FbE_TEST_ENTRIES = 2;		// Anzahl Testeintraege FunctionByExpression
	
	public static final int CAPACITY_FP_ARRAY = 1000;

	public static final int OFFSET_FUNCTIONID_GET_LEXEM = 900;
	
	private static FunctionPool instance;
	
	private static FunctionBase functions[];

	private FunctionPool () {
		functions = new FunctionBase[CAPACITY_FP_ARRAY];
		
		//initialize();
		initializeWithTestData_FunctionByMatrix();
		
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

	private void initializeWithTestData_FunctionByMatrix() {
		
		int 		shareLinesCount = COUNT_FbM_TEST_ENTRIES;
		String shareLines[] = new String[shareLinesCount];
		
		shareLines[0] = "71 0 2 4 4 8 2 5 4 1 83 2 4 0 6 2 2 0 6 7 67 6 3 2 3 0 6 0 0 0 88 1 0 1 0 10 0 2 4 0 77 0 4 5 8 2 0 4 2 2 80 3 1 6 4 9 3 3 3 10 58 2 8 2 6 1 0 0 0 3 85 3 1 1 2 1 0 2 0 0 93"; 
		shareLines[1] = "78 4 4 3 2 1 2 5 1 0 64 2 2 10 14 4 3 1 5 3 69 13 4 0 0 1 5 0 2 0 91 0 0 2 0 5 0 2 3 0 81 2 3 5 4 3 2 4 10 0 71 2 2 6 3 5 2 0 1 6 70 6 7 7 5 1 0 0 3 9 71 4 2 0 0 0 0 2 1 0 95";  
		shareLines[2] = "84 3 0 2 2 2 3 3 1 0 67 0 2 4 17 6 1 3 4 4 68 14 0 2 4 1 3 1 0 0 83 0 1 3 1 11 0 0 4 0 76 4 5 6 5 1 4 3 4 1 81 1 3 2 4 6 2 2 3 13 57 8 5 3 1 1 0 4 0 6 85 0 4 1 0 0 0 3 1 0 91";  
		shareLines[3] = "83 0 1 5 4 0 4 3 0 1 74 2 2 6 9 2 2 2 3 7 65 10 4 4 0 1 6 0 2 0 81 0 2 5 0 10 0 0 2 0 79 4 0 12 3 3 0 0 0 0 87 1 5 4 1 4 1 0 3 14 52 18 7 6 1 2 0 0 1 5 82 3 2 0 0 0 0 1 0 0 97";  
		shareLines[4] = "84 2 3 1 0 4 3 1 2 0 78 2 2 4 10 3 1 0 1 5 73 12 1 0 2 2 4 0 6 1 75 1 0 3 0 14 1 1 2 0 85 5 0 4 2 1 3 5 5 0 72 4 2 8 3 2 5 0 3 12 55 14 6 6 2 0 0 4 6 2 79 1 4 0 2 0 0 2 0 0 92";  
		shareLines[5] = "84 4 2 2 2 0 2 3 1 0 67 5 2 0 18 5 2 1 3 6 75 7 2 2 2 0 3 1 1 1 80 0 1 3 1 12 1 1 1 0 82 4 0 7 4 0 2 6 4 1 75 2 3 7 0 4 1 0 2 16 63 8 6 2 0 0 0 0 0 4 92 2 2 0 0 0 2 4 1 0 91";  
		shareLines[6] = "87 2 0 4 5 0 2 0 0 0 61 8 3 4 16 7 0 1 0 6 72 12 4 4 0 0 2 0 4 0 84 0 0 2 1 9 0 0 4 0 78 2 4 6 6 0 2 2 8 0 77 1 3 7 0 5 2 3 2 10 62 10 6 2 3 1 1 2 4 6 77 4 2 0 2 0 0 2 1 0 93";  
		shareLines[7] = "82 2 1 2 1 4 1 4 3 0 74 3 6 0 10 4 2 1 5 6 73 6 1 0 2 0 7 0 2 0 81 1 0 3 0 13 0 2 2 0 86 0 0 4 6 4 0 4 2 0 74 2 3 11 2 6 1 0 1 10 64 8 8 3 2 0 0 0 5 6 81 3 4 0 2 0 1 3 0 0 90";  
		shareLines[8] = "76 2 3 3 3 5 2 4 2 1 65 2 6 4 18 0 3 1 3 6 74 10 2 1 0 1 3 0 2 1 83 1 1 1 0 11 0 1 2 0 71 4 2 15 5 0 2 6 0 1 80 1 1 9 2 5 0 3 3 8 64 12 3 6 0 0 0 1 2 3 84 4 0 0 2 0 0 2 0 0 96";  
		shareLines[9] = "84 4 1 3 3 0 2 2 1 0 63 0 2 6 20 6 1 2 2 6 66 19 4 0 0 0 3 1 0 1 88 1 0 1 0 8 0 1 1 0 78 2 6 6 6 2 6 4 4 0 77 3 2 2 1 6 4 1 1 16 59 10 2 4 0 3 0 0 2 5 82 4 4 1 2 0 1 2 0 0 90";  
		shareLines[10] = "85 0 1 3 4 0 4 3 0 0 75 2 2 0 15 0 2 4 3 7 72 8 2 0 3 1 4 1 2 0 81 0 0 1 1 14 3 1 2 0 75 2 4 8 5 7 4 2 4 0 75 1 0 7 1 6 1 0 5 18 59 4 6 2 0 5 1 0 2 2 86 2 0 0 2 0 2 2 0 0 94";  
		shareLines[11] = "96 0 0 1 1 1 1 0 0 0 92 0 6 0 0 2 0 0 0 1 91 3 1 0 2 0 2 0 0 0 95 0 0 1 0 4 0 0 1 0 96 0 2 0 1 2 0 0 2 0 92 1 1 2 2 1 1 0 1 3 89 2 1 6 0 0 0 0 2 1 91 0 2 0 0 0 0 0 0 0 98";  
		shareLines[12] = "73 6 2 4 5 2 2 5 1 1 62 0 9 9 14 2 3 0 5 1 80 9 2 0 0 0 3 0 0 1 86 0 0 3 1 9 0 0 2 1 79 7 2 4 5 2 6 1 4 1 72 0 4 10 1 4 0 1 2 6 72 10 4 10 0 0 0 2 4 8 74 2 2 0 0 0 0 0 0 0 98"; 
		shareLines[13] = "79 3 2 5 5 2 2 1 1 1 64 2 4 4 17 6 0 2 1 4 72 12 3 6 0 0 2 0 0 0 85 0 0 3 1 11 0 0 2 0 79 2 8 6 3 3 4 3 11 1 67 1 2 8 2 3 2 1 2 8 68 13 1 8 0 2 0 0 0 6 81 3 5 0 0 0 0 1 0 0 94"; 
		shareLines[14] = "89 0 2 2 1 2 1 1 2 2 64 1 6 5 14 6 2 0 1 5 70 12 2 4 3 0 3 2 0 4 78 3 4 1 0 8 2 0 2 0 79 1 3 10 3 10 1 0 7 0 70 0 2 10 2 5 0 0 1 2 73 12 5 11 1 2 0 0 0 5 79 2 2 0 0 0 0 0 0 0 98"; 
		shareLines[15] = "81 1 1 6 6 2 1 2 0 0 62 0 5 5 24 4 0 0 2 2 81 8 4 0 2 0 1 0 1 1 83 0 0 4 0 11 0 1 1 0 77 4 7 4 6 3 1 8 8 0 66 3 3 8 1 7 3 1 1 8 62 12 5 6 2 0 1 0 0 2 86 3 2 0 2 0 0 8 0 0 88"; 
		shareLines[16] = "84 0 2 3 2 3 1 4 1 0 58 2 4 8 18 7 2 1 5 6 77 4 5 0 2 0 1 0 2 2 77 0 0 4 4 11 0 0 1 1 84 2 4 6 2 2 5 10 6 0 65 2 2 8 0 2 3 2 1 11 72 6 3 11 0 0 0 0 6 5 72 6 5 0 2 0 0 4 0 0 89"; 
		shareLines[17] = "78 3 5 2 2 1 2 7 0 0 66 2 4 4 12 6 4 2 7 4 76 7 0 0 2 1 3 0 3 2 80 0 0 1 3 11 0 1 3 0 80 2 4 8 2 5 1 4 8 0 71 2 1 8 2 3 2 3 2 8 65 9 6 4 4 1 0 2 4 1 84 0 1 0 0 0 0 0 1 0 98"; 
		shareLines[18] = "78 0 3 4 4 2 1 7 1 0 72 2 7 6 10 0 3 0 7 5 72 8 2 1 3 0 2 0 0 0 80 0 0 4 2 14 1 0 0 1 88 2 0 6 2 3 0 4 2 0 75 5 4 7 0 3 4 5 1 8 60 13 6 4 0 0 0 3 3 3 85 2 3 0 6 3 0 1 1 1 85"; 
		shareLines[19] = "83 2 4 0 0 4 2 3 2 0 66 2 6 0 10 10 5 1 4 10 72 6 5 0 2 0 1 0 5 0 82 0 1 3 1 8 0 1 1 0 84 2 2 6 4 2 4 5 4 0 69 2 3 11 1 4 3 1 2 10 71 6 2 4 2 2 0 0 4 3 80 5 2 0 1 0 0 6 0 0 91"; 
		shareLines[20] = "84 4 2 2 1 2 1 3 1 2 81 0 4 0 6 5 1 1 3 4 85 4 1 0 1 1 1 1 2 3 84 0 0 2 2 6 0 0 1 0 86 4 2 6 1 4 0 5 8 0 76 1 2 4 1 1 1 0 1 2 87 4 3 6 0 1 2 1 2 3 84 1 4 0 0 0 0 2 0 0 94"; 
		shareLines[21] = "85 2 3 0 0 2 2 5 1 0 76 2 5 2 8 3 3 1 5 7 68 6 3 4 2 0 5 0 1 1 81 0 0 4 1 12 1 0 4 0 82 2 0 7 4 2 0 2 2 0 76 2 5 11 5 5 3 0 3 6 60 14 4 8 0 1 0 2 0 5 82 2 2 4 2 0 0 2 0 0 90"; 
		shareLines[22] = "77 4 3 4 2 4 1 3 2 0 54 2 5 7 25 4 2 1 3 1 79 11 2 1 0 0 3 0 1 2 86 0 2 2 0 7 0 1 2 0 86 0 0 6 5 5 3 7 4 2 62 3 3 11 2 7 3 0 1 10 64 12 1 6 0 2 0 0 0 5 86 1 3 0 2 0 0 0 0 0 95"; 
		shareLines[23] = "77 4 1 3 3 7 0 2 3 1 65 2 4 4 14 8 1 1 3 3 73 10 3 4 0 0 4 0 3 0 87 2 0 2 0 6 0 2 3 0 87 0 2 2 4 5 4 7 5 0 69 2 2 6 4 5 3 0 0 6 69 9 4 8 1 2 0 1 1 6 78 3 3 2 2 0 0 2 0 0 91"; 
		shareLines[24] = "74 2 3 3 4 6 0 4 4 0 75 2 4 2 11 2 3 1 5 7 66 13 3 2 2 0 2 0 0 1 80 0 0 6 0 13 0 1 3 1 77 4 6 8 0 2 8 2 4 0 72 3 4 5 4 0 3 0 1 2 79 8 3 6 1 1 0 1 1 5 82 3 2 0 6 1 0 0 0 0 91"; 
		shareLines[25] = "81 3 1 4 4 0 7 0 0 0 72 2 2 9 14 0 1 0 0 4 79 10 4 0 0 2 1 0 0 2 81 3 1 3 1 9 2 0 1 1 77 4 0 6 9 4 3 2 2 1 76 3 3 6 1 8 3 1 7 15 53 8 4 7 4 0 0 2 0 1 82 4 0 1 0 2 0 0 1 1 95"; 
		shareLines[26] = "78 11 1 2 2 2 0 3 1 0 76 4 2 5 8 4 1 0 3 2 69 16 3 1 2 0 4 2 3 1 78 0 0 4 0 12 0 0 2 0 85 0 0 8 5 4 2 4 0 0 78 2 3 7 2 5 2 2 0 9 72 6 2 6 2 0 0 0 2 6 80 4 5 1 5 1 0 0 0 0 88"; 
		shareLines[27] = "87 2 1 3 3 1 2 1 0 1 63 4 0 9 15 8 0 0 0 5 74 13 3 0 1 0 4 0 1 0 80 0 3 5 0 11 0 4 4 0 77 4 2 2 7 2 3 0 2 1 79 5 5 3 5 7 5 0 1 7 56 16 3 4 2 0 0 0 1 5 85 3 2 0 4 1 0 4 1 0 88"; 
		shareLines[28] = "85 2 2 2 3 1 1 3 1 1 69 4 6 0 17 0 2 1 2 2 71 12 4 0 0 1 8 0 2 0 85 0 0 2 0 11 0 0 2 0 72 2 6 12 6 0 0 3 9 0 76 2 2 8 1 7 1 2 1 4 68 13 3 9 4 1 0 0 0 2 79 5 3 1 2 2 0 6 0 0 86"; 
		shareLines[29] = "79 0 1 4 3 4 3 4 2 1 73 2 5 4 12 2 1 0 2 4 76 6 3 0 5 0 4 0 0 0 89 0 1 2 1 7 0 0 2 0 75 2 9 4 8 4 3 2 8 1 65 4 2 11 2 7 4 2 3 7 54 16 5 2 0 0 0 1 2 3 88 4 0 0 11 1 0 2 0 0 86"; 
		shareLines[30] = "79 2 4 3 3 0 3 5 1 1 72 0 2 2 16 1 5 1 5 5 76 6 1 2 2 0 3 2 1 3 78 0 0 3 0 13 0 1 0 0 76 6 2 8 7 5 1 4 6 0 73 3 2 6 0 7 3 0 4 10 61 10 5 0 2 0 0 0 0 4 94 0 3 2 2 0 2 0 0 0 91"; 
		shareLines[31] = "85 2 0 3 3 2 2 2 1 0 69 5 4 0 20 2 0 0 2 4 72 10 1 6 0 1 4 0 1 1 82 1 0 2 0 13 0 0 5 1 72 2 4 8 8 4 0 0 4 0 80 1 4 7 4 9 1 3 2 12 51 14 4 5 2 0 0 0 0 3 89 1 2 0 4 3 1 0 1 1 88"; 
		shareLines[32] = "81 0 2 5 4 1 1 6 0 0 73 0 4 4 8 8 1 2 4 5 67 10 2 3 1 0 8 0 0 0 81 0 2 5 0 12 1 0 2 0 81 0 2 10 4 2 4 0 4 1 78 1 5 5 1 3 1 1 2 12 69 8 3 4 0 2 1 0 0 7 84 2 2 0 2 0 1 2 0 0 93"; 
		shareLines[33] = "80 4 3 2 3 2 1 4 1 0 65 3 6 3 15 5 3 0 4 5 72 6 6 1 0 0 6 2 2 0 87 0 0 0 0 9 0 0 1 0 71 6 4 12 6 0 0 2 6 1 78 4 0 9 1 6 2 0 1 9 69 10 2 6 6 0 0 0 0 3 78 7 2 1 2 1 0 1 0 1 92"; 
		shareLines[34] = "79 4 1 3 3 4 3 1 2 1 72 1 8 6 6 5 1 0 1 5 73 8 2 2 4 1 4 0 5 1 78 1 1 1 1 12 0 0 2 0 85 4 0 4 5 1 2 6 4 6 74 1 0 6 3 5 2 0 3 16 61 8 2 9 2 1 0 4 0 7 76 1 4 0 4 0 0 0 0 0 92"; 
		shareLines[35] = "90 2 1 0 0 1 3 2 1 0 63 5 10 4 13 4 1 0 2 2 80 7 2 0 3 0 4 0 3 3 75 2 0 4 0 13 0 0 5 0 73 6 6 5 5 5 4 2 12 0 63 3 4 7 5 5 3 0 4 6 63 12 2 2 0 1 1 0 0 3 89 4 2 3 0 0 1 3 0 0 91"; 
		shareLines[36] = "96 0 0 0 0 2 1 0 1 0 95 0 2 0 0 2 1 0 0 0 93 4 1 0 0 0 2 0 2 0 87 4 0 4 0 3 0 0 1 1 93 0 0 2 3 0 0 0 0 0 92 2 4 2 1 2 2 0 1 2 92 0 0 0 1 0 0 0 0 1 97 1 2 0 0 0 0 0 0 0 98"; 
		shareLines[36] = "72 1 4 2 2 8 1 5 5 0 67 6 3 0 14 4 5 1 6 6 68 8 3 2 2 0 5 0 2 0 85 1 0 2 0 10 0 0 2 0 73 1 8 10 6 7 1 2 8 1 73 1 2 5 2 5 1 0 2 13 67 8 2 12 0 0 0 0 2 4 80 2 0 0 2 0 0 0 1 0 97"; 
		shareLines[37] = "83 2 1 4 4 0 4 1 1 0 73 0 5 8 8 4 1 1 1 4 75 6 3 0 4 0 7 0 4 0 82 1 1 4 0 8 0 0 5 1 82 0 0 3 9 6 4 2 2 0 74 3 4 5 4 8 3 0 4 8 60 8 5 6 2 0 0 0 1 6 82 3 6 0 6 1 0 0 0 1 86"; 
		shareLines[38] = "79 6 2 2 3 0 3 4 1 1 65 0 6 5 18 0 2 3 4 6 68 8 3 4 1 0 6 0 0 1 87 0 0 3 0 9 0 0 1 0 86 3 2 2 6 2 2 1 6 0 75 3 3 8 1 6 3 1 2 10 64 10 3 4 2 1 0 0 2 3 85 3 2 0 0 0 1 1 0 0 96"; 
		shareLines[39] = "78 2 1 3 3 4 2 4 3 0 60 2 1 2 27 6 1 1 3 4 73 10 4 2 0 0 4 0 0 1 84 0 1 1 1 12 0 0 3 0 79 4 5 2 7 2 2 2 10 0 76 2 1 5 3 6 4 2 3 10 57 10 5 4 0 0 0 0 2 4 87 3 2 0 4 0 1 2 2 1 88"; 
		shareLines[40] = "79 1 2 2 3 3 3 5 2 0 64 4 3 6 19 0 2 2 4 4 78 10 1 0 1 0 2 2 3 0 85 0 0 1 1 8 0 0 2 0 79 3 6 4 6 2 2 0 6 0 84 1 1 4 3 5 1 0 3 20 55 9 4 4 0 1 1 0 2 2 89 1 6 0 1 1 0 3 0 0 89"; 
		shareLines[41] = "73 5 1 6 5 4 1 3 2 0 69 5 2 8 6 5 2 3 4 5 76 4 3 1 2 1 4 0 0 0 80 1 0 6 1 12 0 0 2 0 77 4 8 4 5 4 1 3 10 0 69 2 6 5 2 5 1 0 1 8 74 8 1 0 0 3 0 0 2 3 89 3 2 0 2 0 0 1 1 0 94"; 
		shareLines[42] = "74 2 5 2 3 3 0 9 2 0 70 1 4 4 12 4 5 0 8 6 71 3 3 3 2 1 3 0 2 0 89 0 0 0 1 8 0 0 5 0 76 2 6 2 9 4 0 3 9 0 75 0 1 8 4 9 0 2 0 5 68 8 4 10 1 0 0 0 2 4 80 3 3 1 0 0 1 3 0 0 92"; 
		shareLines[43] = "76 3 2 3 4 4 1 5 2 0 70 0 2 6 14 2 4 2 4 6 72 8 4 2 4 0 0 0 0 1 82 0 0 2 0 15 0 0 5 0 66 8 7 5 9 6 2 6 10 0 69 0 2 5 4 9 0 1 1 7 67 8 3 0 0 3 1 0 0 3 92 1 4 0 0 0 0 0 0 1 95"; 
		shareLines[44] = "86 0 4 0 0 2 2 6 0 2 79 2 2 2 6 2 4 1 6 8 59 14 1 0 4 0 8 1 2 0 83 0 1 2 2 9 0 0 2 0 79 0 6 7 6 4 5 4 8 0 70 2 1 6 2 6 2 2 2 12 53 18 3 2 0 0 0 2 0 2 92 2 4 0 4 0 0 0 1 2 89"; 
		shareLines[45] = "82 2 3 1 2 2 3 5 0 0 71 5 0 4 10 6 3 1 5 5 61 11 5 2 2 0 9 0 2 1 85 0 1 4 0 7 0 0 0 0 84 2 2 6 6 2 2 1 2 2 78 1 4 8 1 6 1 3 2 8 62 9 8 6 0 0 0 0 0 4 86 4 2 2 2 0 0 0 0 0 94"; 
		shareLines[46] = "87 2 0 2 2 2 1 2 2 0 55 2 6 4 19 12 0 2 1 1 74 12 3 5 2 0 2 0 3 0 86 0 0 3 0 8 0 0 0 0 77 7 7 6 3 4 0 0 9 2 69 5 3 8 0 3 4 0 1 6 67 18 1 3 1 1 0 1 2 3 87 2 3 1 1 0 0 2 0 0 93"; 
		shareLines[47] = "76 3 4 4 4 2 2 3 2 1 69 2 2 6 17 0 2 1 2 3 74 7 3 1 4 0 6 0 2 1 83 1 1 4 1 7 0 0 3 1 78 0 2 12 4 9 4 4 2 0 71 2 3 5 3 4 1 0 2 10 72 7 1 8 0 1 0 0 0 4 84 3 4 2 6 0 0 0 0 0 88"; 
		shareLines[48] = "77 2 1 4 4 4 4 2 2 0 71 1 4 6 16 0 2 0 2 4 79 4 6 0 3 0 2 0 4 0 81 0 0 3 0 12 0 2 4 0 76 7 2 5 4 4 6 6 6 0 68 3 2 5 4 4 3 6 4 7 61 10 1 6 0 0 0 2 4 6 76 6 2 0 3 0 0 3 1 1 90"; 
		shareLines[49] = "80 6 1 3 4 0 2 4 0 0 59 5 8 7 12 6 1 2 4 4 76 6 2 2 4 0 2 0 2 2 84 2 0 2 0 8 0 0 1 0 79 4 6 2 8 5 1 2 10 0 70 1 2 9 1 7 1 6 1 9 59 11 5 2 0 1 0 0 2 7 86 2 2 1 1 0 0 0 0 0 96"; 
		shareLines[50] = "81 2 4 1 1 3 3 3 2 0 73 3 2 4 12 0 4 2 2 3 74 10 2 1 5 1 2 0 8 0 79 0 1 2 0 10 1 0 2 1 83 2 5 4 2 4 2 3 6 0 73 5 2 5 2 2 5 0 3 10 68 6 4 3 3 1 1 0 4 6 80 2 3 0 2 0 0 2 1 3 89"; 
		shareLines[51] = "83 2 1 2 2 4 2 2 2 0 76 0 5 7 8 2 2 0 2 8 67 8 1 0 8 2 4 0 2 0 82 0 1 5 0 10 0 1 1 0 75 0 6 8 9 5 2 5 8 1 69 3 4 3 1 9 2 0 3 10 68 4 3 4 0 0 1 0 0 6 88 1 3 0 2 1 0 0 0 0 94"; 
		shareLines[52] = "77 2 4 3 3 4 1 4 2 0 67 0 6 2 14 6 3 2 5 2 69 18 2 0 0 0 4 0 0 0 89 0 0 2 0 9 0 0 2 1 73 2 8 3 11 3 6 0 10 0 71 1 1 8 2 12 0 1 2 8 63 10 2 6 0 2 0 0 4 1 84 3 5 2 2 0 0 0 0 0 91"; 
		shareLines[53] = "84 2 3 1 1 4 1 2 2 0 91 0 2 0 4 0 3 0 2 2 85 7 1 0 0 0 3 0 0 0 95 0 0 0 1 4 1 0 0 0 90 2 0 5 2 4 2 2 2 0 83 1 0 6 0 1 0 0 0 4 86 7 2 6 0 0 0 0 3 2 87 2 2 0 2 0 0 0 0 1 95"; 
		
		String functionLines[] = new String[1];
		functionLines[0] = "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1";
		
		String offssetLine = "";
		
		int numberOfColumns = 9;
		
		for (int i = 0; i <  shareLinesCount; i++) {
			
			setFunction(100 + i, new FunctionByMatrix(ValueInterpreteAs.attributeValue, new FunctionByMatrix_Matrix(shareLines[i], functionLines[0] , offssetLine,  numberOfColumns)));

		}
				
	}

	
	private void initialize() {
		
		loadMatrixFromDB();
		loadExpressionFromDB();
		

	}
	
	private void loadExpressionFromDB() {
		TablePoolFunctionExpression table;

		int rowCount;
		int row;
		
		int func_id;

		int exp_id;
		Expression exp;

		table = new TablePoolFunctionExpression();
		table.select(table.SELECT_ALL_COLUMNS, "", "ORDER BY func_id");
		rowCount = table.rowCount();
		
		if (rowCount > 0) {
			for (row = 0; row < rowCount; row++) {
				
				func_id = table.getFunctionID(row);
				exp_id = table.getExpressionID(row);
	
				exp = ExpressionPool.getInstance().getExpression(exp_id);
				setFunction(func_id, new FunctionByExpression(exp));

			}
		}

	}
	
	private void loadMatrixFromDB() {
		TablePoolMatrixRowCol tableRowCol;
		TablePoolMatrix tableMatrix;

		int rowCount;
		int row;
		
		int matrixRow;
		int matrixCol;
		float share;
		int func_id;
		float offset;
		
		int functions[];
		Value shares[];
		Value offsets[];

		int matrixIndex;
		int rowMulCol;
		int anzRow;
		int anzCol;
		int typeShare;
		int typeOffset;
		
		
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
				typeShare = tableMatrix.getTypeShare(row);
				typeOffset = tableMatrix.getTypeOffset(row);
				
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
							shares[matrixIndex] = 	new Value (Type.getName(typeShare), share);

							functions[matrixIndex] = tableRowCol.getFunction(row);

							offset = tableRowCol.getOffset(row);
							offsets[matrixIndex] = 	new Value (Type.getName(typeOffset), offset);

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
