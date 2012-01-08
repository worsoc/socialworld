/**
 * 
 */
package org.socialworld.attributes;

/**
 * The enumeration holds all attribute names and collects an index for every
 * attribute. So an attribute is addressable in arrays.
 * 
 * @author Mathias Sikos (tyloesand)
 */
public enum Attribute {
	mood(0), courage(1), morals(2), materialism(3), tiredness(4),
	curiosity(5), spirituality(6), hunger(7);

	/**
	 * The constant holds the informations how many attributes are simulated.
	 * The constant is used for iteration about all attributes and for creation
	 * of attribute arrays.
	 */
	public static final int NUMBER_OF_ATTRIBUTES = 8;
	/**
	 * The constant holds the informations about the range of values. The range
	 * of values is from 0 to the constant's value.
	 */
	public static final byte ATTRIBUTE_RANGE = 99;
	// !!! if the constant ATTRIBUTE_RANGE is changed the following methods have to be conformed :
	// - AttributeCalculatorFunctionTable.initialize()
	
	private int arrayIndex;

	private Attribute(int index) {
		this.arrayIndex = index;
	}

	/**
	 * The method returns the index of the attribute.
	 * 
	 * @return attribute's index
	 */
	public int getIndex() {
		return arrayIndex;
	}

	/**
	 * The method returns the attribute name which belongs to the parameter
	 * attribute index.
	 * 
	 * @param arrayIndex
	 *            attribute index
	 * @return attribute name
	 */
	public Attribute getAttributeName(int arrayIndex) {
		for (Attribute attribute : Attribute.values())
			if (attribute.arrayIndex == arrayIndex)
				return attribute;
		return null;
	}
}
