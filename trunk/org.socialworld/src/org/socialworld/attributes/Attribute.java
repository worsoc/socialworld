/**
 * 
 */
package org.socialworld.attributes;

/**
 * @author Mathias Sikos (tyloesand)
 *
 */
public enum Attribute {
	mood(0),
	courage(1),
	morals(2),
	materialism(3),
	tiredness(4),
	curiosity(5),
	spirituality(6),
	hunger(7);
	
	public static final int NUMBER_OF_ATTRIBUTES = 8;
	
	private int arrayIndex;

	private Attribute(int index) {
		this.arrayIndex = index;
	}
	
	public int getIndex() {
		return arrayIndex;
	}
	
	public Attribute getAttributeName(int arrayIndex) {
		for (Attribute attribute : Attribute.values()) 
			if (attribute.arrayIndex == arrayIndex)
				return attribute;
		return null;
	}
}

