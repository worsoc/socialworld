/**
 * 
 */
package org.socialworld.calculation;

import java.util.ArrayList;
import java.util.List;

/**
 * The class holds all attribute calculation
 *         functions.
 * @author Mathias Sikos (tyloesand) 
 */
public class AttributeCalculatorFunctions {

	private static List<FunctionBase> functions;
	
	private AttributeCalculatorFunctions() {

	}

	/**
	 * The method returns the attribute calculation function addressed by the
	 * function index. When the method is called the first time the function
	 * list is generated.
	 * 
	 * @param functionIndex
	 * @return attribute calculator function
	 */
	public static FunctionBase get(int functionIndex) {
		if (functions == null) {
			initialize();
		}

		return functions.get(functionIndex);

	}

	private static void initialize() {
		
		functions = new ArrayList<FunctionBase>();
		
		FunctionBase function;

		// !!! the order of creation and adding must be conform to the order of enumeration FunctionByTable_Type
		// because the function array index has to match to the int value that belongs to the FunctionByTable_Type

		// add horizontal_min function 
		function = new FunctionByTable(FunctionByTable_Type.horizontal_min);
		functions.add(function);

		// add identical function 
		function = new FunctionByTable(FunctionByTable_Type.identical);
		functions.add(function);

		// add negative_raise function 
		function = new FunctionByTable(FunctionByTable_Type.negative_raise);
		functions.add(function);

		// add v function 
		function = new FunctionByTable(FunctionByTable_Type.v);
		functions.add(function);
		
		// add v_mirrored function 
		function = new FunctionByTable(FunctionByTable_Type.v_mirrored);
		functions.add(function);

		// add u function 
		function = new FunctionByTable(FunctionByTable_Type.u);
		functions.add(function);
		
		// add u_mirrored function 
		function = new FunctionByTable(FunctionByTable_Type.u_mirrored);
		functions.add(function);

		// add horizontal_max function 
		function = new FunctionByTable(FunctionByTable_Type.horizontal_max);
		functions.add(function);

		// add positive_raise function 
		function = new FunctionByTable(FunctionByTable_Type.positive_raise);
		functions.add(function);

	}
}
