/**
 * 
 */
package org.socialworld.objects;

/**
 * @author Mathias Sikos (tyloesand)
 *
 */
public enum Attribute {
	mood(1),
	courage(2),
	morals(3),
	materialism(4),
	tiredness(5),
	curiosity(6),
	spirituality(7),
	hunger(8);
	
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

