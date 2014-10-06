package org.socialworld.knowledge;


/**
 * The enumeration holds all acquaintance attribute names and collects an index for every
 * attribute. So an attribute is addressable in arrays.
 * 
 * @author Mathias Sikos (tyloesand)
 */
public enum AcquaintanceAttribute {
	friendship(0), love(1), sympathy(2), responsibility(3), respect(4), trust(5), obedience(6);
	
	/**
	 * The constant holds the informations how many attributes are simulated.
	 * The constant is used for iteration about all attributes and for creation
	 * of attribute arrays.
	 */
	public static final int NUMBER_OF_ATTRIBUTES = 8;

	private int arrayIndex;

	private AcquaintanceAttribute(int index) {
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
	public AcquaintanceAttribute getAttributeName(int arrayIndex) {
		for (AcquaintanceAttribute attribute : AcquaintanceAttribute.values())
			if (attribute.arrayIndex == arrayIndex)
				return attribute;
		return null;
	}

}
