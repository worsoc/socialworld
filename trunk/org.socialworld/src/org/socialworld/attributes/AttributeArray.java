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
	private int numberOfAttributes;

	/**
	 * an array for every attribute value.
	 */
	private byte attributes[];

	/**
	 * an array for every attribute's value change.
	 */
	private byte differences[];
	                         
	public AttributeArray() {
		numberOfAttributes = Attribute.NUMBER_OF_ATTRIBUTES;
		attributes 	= new byte[numberOfAttributes];
		differences = new byte[numberOfAttributes];
	}

	public AttributeArray(byte[] array) {
		numberOfAttributes = Attribute.NUMBER_OF_ATTRIBUTES;
		attributes 	= new byte[numberOfAttributes];
		differences = new byte[numberOfAttributes];
		if (array.length == numberOfAttributes ) {
			attributes = array;
			for (byte i = 0; i < numberOfAttributes; i++ ) differences[i] = 0;
		}
	}
	/**
	 * The method sets an attribute value. The attribute is addressed by the
	 * attribute index.
	 * 
	 * @param attributeIndex
	 * @param attributeValue
	 */
	public void set(int attributeIndex, byte attributeValue) {
		differences[attributeIndex] = (byte)(attributeValue - attributes[attributeIndex]);
		attributes[attributeIndex] 	= attributeValue;
	}

	/**
	 * The method sets an attribute value. The attribute is addressed by the
	 * attribute name.
	 * 
	 * @param attributeName
	 * @param attributeValue
	 */
	public void set(Attribute attributeName, byte attributeValue) {
		int attributeIndex = attributeName.getIndex();
		differences[attributeIndex] = (byte)(attributeValue - attributes[attributeIndex]);
		attributes[attributeIndex] = attributeValue;
	}

	/**
	 * The method returns an attribute value. The attribute is addressed by the
	 * attribute's index.
	 * 
	 * @param attibuteIndex
	 *            attribute array index
	 * @return attribute value
	 */
	public byte get(int attibuteIndex) {
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
	public byte getDifference(int attibuteIndex) {
	return differences[attibuteIndex];
	}
}
