/**
 * 
 */
package org.socialworld.calculation;

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;

/**
 * @author Mathias Sikos (tyloesand) Because of being a singleton there exists
 *         only one instance of the class. The object access is controlled by a
 *         semaphore. Only the owner of the semaphore can use the object. After
 *         locking the calculation object the user object gives an attribute
 *         array to the calculator. Then there can be a lot of calculations on
 *         that attribute array. If no further calculation is necessary the user
 *         object gets back the new atribute values. Finally the user releases
 *         the calculator. So an other object can take the semaphore and use the
 *         calculation object.
 */
public class AttributeCalculator {

	private static AttributeCalculator calculator;

	/**
	 * the object that holds the semaphore an so can use the calculator
	 */
	private Object locker;

	/**
	 * the attribute array that is set by the user and is got back to the user
	 * after calculation.
	 */
	private AttributeArray attributes;

	/**
	 * a matrix that holds the information how to calculate on the attribute
	 * array.
	 */
	private AttributeCalculatorMatrix matrix;

	/**
	 * a help attribute array that is used for calculation loops
	 */
	private float attributesNew[];

	private int numberOfAttributes;

	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private AttributeCalculator() {
		locker = null;
		this.numberOfAttributes = Attribute.NUMBER_OF_ATTRIBUTES;
		attributes = new AttributeArray();
		attributesNew = new float[numberOfAttributes];
		clearAttributes();
	}

	/**
	 * The method gets back the only instance of the AttributeCalculator.
	 * 
	 * @return singleton object of AttributeCalculator
	 */
	public static AttributeCalculator getInstance() {
		if (calculator == null) {
			calculator = new AttributeCalculator();
		}
		return calculator;
	}

	/**
	 * The method locks the calculator. The calculator can be locked if and only
	 * if there is no other locking object.
	 * 
	 * @param user
	 *            the locking object / the user of the attribute calculator
	 */
	public void lockCalculator(Object user) {
		if (this.locker == null) {
			this.locker = user;
		}
	}

	/**
	 * The method releases the calculator. The calculator can be released by the
	 * locking object only.
	 * 
	 * @param user
	 *            the locking object / the user of the attribute calculator
	 */
	public void releaseCalculator(Object user) {
		if (this.locker == user) {
			this.locker = null;
		}
	}

	/**
	 * The method sets the attribute array of an object (if and only if the
	 * object is owner of the semaphore).
	 * 
	 * @param attributes
	 * @param user
	 */
	public void setAttributes(AttributeArray attributes, Object user) {
		int index;
		if (this.locker == user) {
			for (index = 0; index < numberOfAttributes; index++) {
				this.attributes.set(index, attributes.get(index));
			}
		}
	}

	/**
	 * By calling that method the caller gets the actual state of the attribute
	 * array.
	 * 
	 * @param attributes
	 */
	public void fetchAttributes(AttributeArray attributes) {
		int index;
		for (index = 0; index < numberOfAttributes; index++) {
			attributes.set(index, this.attributes.get(index));
		}
	}

	/**
	 * The method calculates the change of an attribute by an event.
	 * 
	 * @param targetAttribute
	 *            the attribute to modify
	 * @param exression
	 *            the informations how the event changes the attribute
	 */
	public void modifyAttribute(Attribute targetAttribute,
			EventInfluenceExpression exression) {

		this.attributes.set(targetAttribute, exression.evaluateExpression(
				this.attributes, targetAttribute));
	}

	/**
	 * The method sets the attribute calculation matrix to an instance variable
	 * and starts the calculation.
	 * 
	 * @param matrix
	 *            the informations how the attributes influence each other.
	 */
	public void changeAttributes(AttributeCalculatorMatrix matrix) {
		this.matrix = matrix;
		changeAttributes();
	}

	/**
	 * The method calculates the change of all attributes. The change
	 * information of a matrix is evaluated as often as necessary. The
	 * calculation stops when for all attributes the changes are in a range of
	 * +/- 1.
	 * 
	 * @param matrix
	 */
	private void changeAttributes() {
		boolean repeatCalculation;

		do {
			repeatCalculation = calculateAttributes();
		} while (repeatCalculation);

	}

	/**
	 * The method calculates the attribute changes. For every matrix element the
	 * influence is evaluated and the change is set to the influenced attribute.
	 * 
	 * @return true if the calculation is not finished and false if the
	 *         calculation stops.
	 */
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

				change = share * function.calculate(attributeValue, offset);

				if (change != 0)
					attributesNew[column] += change;

			}
		}

		result = checkEpsilon();

		for (row = 0; row < this.numberOfAttributes; row++) {
			this.attributes.set(row, (byte) (attributesNew[row] + 0.5));
		}

		clearAttributes();

		return result;

	}

	/**
	 * The method checks whether the calculation loop continues. The calculation
	 * loop stops if for every attribute the value changes by less than 1.
	 * 
	 * @return true if the calculation loop continues and false if teh
	 *         calculation loop stops
	 */
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

	/**
	 * The method clears the help attribute array.
	 */
	private void clearAttributes() {
		int row;
		for (row = 0; row < this.numberOfAttributes; row++) {
			attributesNew[row] = 0;
		}

	}
}
