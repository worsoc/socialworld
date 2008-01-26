/**
 * 
 */
package org.socialworld.calculation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mathias Sikos (tyloesand) The class holds all attribute calculation
 *         functions.
 */
public class AttributeCalculatorFunctions {

	private static List<AttributeCalculatorFunction> functions;

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
	public static AttributeCalculatorFunction get(int functionIndex) {
		if (functions == null) {
			functions = new ArrayList<AttributeCalculatorFunction>();
		}

		return functions.get(functionIndex);

	}

}
