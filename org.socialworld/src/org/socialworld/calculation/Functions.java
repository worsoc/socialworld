/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
package org.socialworld.calculation;

import java.util.ArrayList;
import java.util.List;

public class Functions {

	private static Functions instance;
	
	private static final int MAX_SIZE = 1000;
	
	private static List<FunctionBase> functions;
	
	private static FunctionNothing nothing;
	
	public static Functions getInstance() {
		if (instance == null) {
			instance = new Functions();
		}
		return instance;
		
	}

	private Functions() {
		nothing = new FunctionNothing();
	}

	public  boolean checkPlaceIsFree(int index) {
		
		return (!(get(index).isValid()));
		
	}
	
	public int findNextFree(int start, int end) {
		
		if (start < 0) start = 0;
		if (end >= MAX_SIZE) end = MAX_SIZE;
		
		for (int i = start; i < end; i++ ) {
			if (checkPlaceIsFree(i)) return i;
		}
		
		return -1;
		
	}
	
	/**
	 * The method returns the attribute calculation function addressed by the
	 * function index. When the method is called the first time the function
	 * list is generated.
	 * 
	 * @param functionIndex
	 * @return attribute calculator function
	 */
	public  FunctionBase get(int functionIndex) {
		if (functions == null) {
			initialize();
		}

		if ( (functionIndex < 0) | (functionIndex >= MAX_SIZE) )
			return nothing;
		else {
			FunctionBase functionAtIndex = functions.get(functionIndex);
			if (functionAtIndex != null)
				return functionAtIndex;
			else
				return nothing;
		}

	}
	
	public   void set(int index, FunctionBase function) {
		
		if ((index >= FunctionByTable_Type.NUMBER_OF_FUNCTION_BY_TABLE_TYPES) &	(index < MAX_SIZE))
			functions.set(index, function);
		
	}

	private void initialize() {
		
		functions = new ArrayList<FunctionBase>();
		
		FunctionBase function;
		
		for (int i = 0; i < MAX_SIZE; i++) functions.add(nothing);

		// !!! the order of creation and adding must be conform to the order of enumeration FunctionByTable_Type
		// because the function array index has to match to the int value that belongs to the FunctionByTable_Type

		// add horizontal_min function 
		function = new FunctionByTable(FunctionByTable_Type.horizontal_min);
		functions.set(FunctionByTable_Type.horizontal_min.getIndex(), function);

		// add identical function 
		function = new FunctionByTable(FunctionByTable_Type.identical);
		functions.set(FunctionByTable_Type.identical.getIndex(),function);

		// add negative_raise function 
		function = new FunctionByTable(FunctionByTable_Type.negative_raise);
		functions.set(FunctionByTable_Type.negative_raise.getIndex(), function);

		// add v function 
		function = new FunctionByTable(FunctionByTable_Type.v);
		functions.set(FunctionByTable_Type.v.getIndex(), function);
		
		// add v_mirrored function 
		function = new FunctionByTable(FunctionByTable_Type.v_mirrored);
		functions.set(FunctionByTable_Type.v_mirrored.getIndex(), function);

		// add u function 
		function = new FunctionByTable(FunctionByTable_Type.u);
		functions.set(FunctionByTable_Type.u.getIndex(), function);
		
		// add u_mirrored function 
		function = new FunctionByTable(FunctionByTable_Type.u_mirrored);
		functions.set(FunctionByTable_Type.u_mirrored.getIndex(), function);

		// add horizontal_max function 
		function = new FunctionByTable(FunctionByTable_Type.horizontal_max);
		functions.set(FunctionByTable_Type.horizontal_max.getIndex(), function);

		// add positive_raise function 
		function = new FunctionByTable(FunctionByTable_Type.positive_raise);
		functions.set(FunctionByTable_Type.positive_raise.getIndex(), function);

	}
}
