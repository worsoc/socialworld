package org.socialworld.conversation;


public enum SentenceType {

	nothing(0), say(1), question(2), answer(3);
	
	
	private int arrayIndex;

	private SentenceType(int index) {
		this.arrayIndex = index;
	}

	public int getIndex() {
		return this.arrayIndex;
	}
	
	/**
	 * The method returns the SentenceType which belongs to the parameter  index.
	 * 
	 * @param arrayIndex
	 *             index
	 * @return sentence type 
	 */
	public static SentenceType getSentenceType(int arrayIndex) {
		for (SentenceType type : SentenceType.values())
			if (type.arrayIndex == arrayIndex)
				return type;
		return null;
	}

}
