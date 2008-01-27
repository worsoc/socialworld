/**
 * 
 */
package org.socialworld.attributes;

/**
 * @author Mathias Sikos (tyloesand) The class implements an attribute array. It
 *         has an byte array for the value of every attribute and set and get
 *         methods to manipulate and assign the values.
 */
public class AttributeArray {
	private int numberOfAttributes;

	/**
	 * an array for evry attribute value.
	 */
	private byte attributes[];

	public AttributeArray() {
		numberOfAttributes = Attribute.NUMBER_OF_ATTRIBUTES;
		attributes = new byte[numberOfAttributes];
	}

	/**
	 * The method sets an attribute value. The attribute is addressed by the
	 * attribute index.
	 * 
	 * @param attributeIndex
	 * @param attributeValue
	 */
	public void set(int attributeIndex, byte attributeValue) {
		attributes[attributeIndex] = attributeValue;
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
}
