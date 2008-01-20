/**
 * 
 */
package org.socialworld.attributes;


/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class AttributeArray {
	private int numberOfAttributes;
	
	private byte attributes[];

	public AttributeArray() {
		numberOfAttributes = Attribute.NUMBER_OF_ATTRIBUTES;
		attributes = new byte[numberOfAttributes];
	}

	public void setAttribute(int attributeIndex, byte attributeValue) {
		attributes[attributeIndex] = attributeValue;
	}

	public void setAttribute(Attribute attributeName, byte attributeValue) {
		int attributeIndex = attributeName.getIndex();
		attributes[attributeIndex] = attributeValue;
	}
	
}
