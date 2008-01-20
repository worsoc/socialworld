/**
 * 
 */
package org.socialworld.calculation;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class AttributeCalculatorFunctions {

	private static List<AttributeCalculatorFunction> functions;

	private AttributeCalculatorFunctions() {

	}

	public static AttributeCalculatorFunction get(int functionIndex) {
		if (functions == null) {
			functions = new ArrayList<AttributeCalculatorFunction>();
		}

		return functions.get(functionIndex);

	}

}
