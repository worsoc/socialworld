/**
 * 
 */
package org.socialworld.calculation;

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class AttributeCalculator {

	private static AttributeCalculator calculator;

	private AttributeArray attributes;
	private AttributeCalculatorMatrix matrix;

	private float attributesNew[];

	private int numberOfAttributes;

	private AttributeCalculator() {
		this.numberOfAttributes = Attribute.NUMBER_OF_ATTRIBUTES;
		attributesNew = new float[numberOfAttributes];
		clearAttributes();
	}

	public static AttributeCalculator getInstance() {
		if (calculator == null) {
			calculator = new AttributeCalculator();
		}
		return calculator;
	}


	public void changeAttributes(AttributeArray attributes,
			AttributeCalculatorMatrix matrix) {
		this.attributes = attributes;
		this.matrix = matrix;
		changeAttributes();
	}

	private void changeAttributes() {
		boolean repeatCalculation;

		do {
			repeatCalculation = calculateAttributes();
		} while (repeatCalculation);

	}

	private boolean calculateAttributes() {

		boolean result;
		int column, row;

		byte attributeValue;
		int functionIndex;
		byte offset;
		float share;

		AttributeCalculatorFunction function;

		float change;

		for (row = 0; row < this.numberOfAttributes; row++) {

			attributeValue = this.attributes.get(row);

			for (column = 0; column < this.numberOfAttributes; column++) {

				functionIndex = this.matrix.getFunction(row, column);
				share = this.matrix.getShare(row, column);
				offset = this.matrix.getOffset(row, column);

				function = AttributeCalculatorFunctions.get(functionIndex);

				change = share
						* function.calculate(offset, row, column,
								attributeValue);

				if (change != 0)
					attributesNew[column] += change;

			}
		}

		result = checkEpsilon();

		for (row = 0; row < this.numberOfAttributes; row++) {
			attributes.set(row, (byte) (attributesNew[row] + 0.5));
		}

		clearAttributes();

		return result;

	}

	private boolean checkEpsilon() {
		int row = 0;

		while ((attributesNew[row] + 1) > (float) this.attributes.get(row)
				|| (attributesNew[row] - 1) < (float) this.attributes.get(row)) {
			row++;
			if (row == this.numberOfAttributes)
				return false;
		}
		return true;
	}

	private void clearAttributes() {
		int row;
		for (row = 0; row < this.numberOfAttributes; row++) {
			attributesNew[row] = 0;
		}

	}
}
