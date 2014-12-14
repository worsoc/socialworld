/**
 * 
 */
package org.socialworld.attributes;

/**
 * The class implements an attribute array. It
 *         has an byte array for the value of every attribute and set and get
 *         methods to manipulate and assign the values.
 *         There also is an array for the difference of every attribute's new value
 *         to its old value, that means how the attribute has changed by the last calculation
 * @author Mathias Sikos (tyloesand) 
 */
public class AttributeArray {
	/**
	 * The constant holds the informations about the range of values. The range
	 * of values is from 0 to the constant's value.
	 */
	public static final byte ATTRIBUTE_RANGE = 99;
	// !!! if the constant ATTRIBUTE_RANGE is changed the following methods have to be conformed :
	// - AttributeCalculatorFunctionTable.initialize()
	public static final byte VALUE_MIDDLE = 50;
	
	private int numberOfAttributes;

	/**
	 * an array for every attribute value.
	 */
	private int attributes[];

	/**
	 * an array for every attribute's value change.
	 */
	private int differences[];
	                         
	public AttributeArray(int numberOfAttributes) {
		this.numberOfAttributes = numberOfAttributes;
		attributes 	= new int[numberOfAttributes];
		differences = new int[numberOfAttributes];
		for (int i = 0; i < numberOfAttributes; i++ ) {
			attributes[i] = 0;
			differences[i] = 0;
		}
	}

	public AttributeArray(int[] array) {
		numberOfAttributes = array.length;
		attributes 	= new int[numberOfAttributes];
		differences = new int[numberOfAttributes];
		
		for (int i = 0; i < numberOfAttributes; i++ ) {
			attributes[i] = array[i];
			differences[i] = 0;
		}
	}
	
	
	public AttributeArray(AttributeArray original) {
		numberOfAttributes = original.length();
		attributes 	= new int[numberOfAttributes];
		differences = new int[numberOfAttributes];
		
		for (int i = 0; i < numberOfAttributes; i++ ) {
			differences[i] = 0;
			attributes[i] = original.attributes[i];
		}
	}
	/**
	 * The method sets an attribute value. The attribute is addressed by the
	 * attribute index.
	 * 
	 * @param attributeIndex
	 * @param attributeValue
	 */
	public void set(int attributeIndex, int attributeValue) {
		differences[attributeIndex] = (attributeValue - attributes[attributeIndex]);
		attributes[attributeIndex] 	= attributeValue;
	}

	/**
	 * The method sets an attribute value. The attribute is addressed by the
	 * attribute name.
	 * 
	 * @param attributeName
	 * @param attributeValue
	 */
	public void set(Attribute attributeName, int attributeValue) {
		int attributeIndex = attributeName.getIndex();
		differences[attributeIndex] = (attributeValue - attributes[attributeIndex]);
		attributes[attributeIndex] = attributeValue;
	}

	/**
	 * The method sets all attribute values. 
	 * 
	 * @param attributeArray
	 */
	public void set(AttributeArray attributeArray) {
		for (int i = 0; i < numberOfAttributes; i++ ) {
			set(i, attributeArray.get(i));
		}
	}

	/**
	 * The method returns an attribute value. The attribute is addressed by the
	 * attribute's index.
	 * 
	 * @param attibuteIndex
	 *            attribute array index
	 * @return attribute value
	 */
	public int get(int attibuteIndex) {
		return attributes[attibuteIndex];
	}


	/**
	 * The method returns an attribute's value difference. The attribute is addressed by the
	 * attribute's index.
	 * 
	 * @param attibuteIndex
	 *            attribute array index
	 * @return last attribute's value change 
	 */
	public int getDifference(int attibuteIndex) {
	return differences[attibuteIndex];
	}
	
	
	public int length() {
		return numberOfAttributes;
	}
	
	public String toString() {
		String returnValue = "("; 
		
		for (int i = 0; i < numberOfAttributes - 1; i++ ) 
			returnValue = returnValue + attributes[i] + ", ";
		returnValue = returnValue + attributes[numberOfAttributes - 1] + ")";
		return returnValue;
		
	}
}
