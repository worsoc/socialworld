package org.socialworld.knowledge;

public enum KnowledgeFactCriterion {
	colour(0), material(1);

	/**
	 * The constant holds the informations how many knowledge fact criterions exist.
	 * The constant is used for iteration about all criterions 
	 */
	public static final int NUMBER_OF_KNOWLEDGE_FACT_CRITERION = 8;

	/**
	 * The constant holds the offset that must be added to criterion's index for getting the word ID (word search tree ID)
	 */
	private final int CRITERION_WORD_ID_OFFSET = 0;

	private int arrayIndex;

	private KnowledgeFactCriterion(int index) {
		this.arrayIndex = index;
	}
	
	/**
	 * The method returns the index of the knowledge fact criterion.
	 * 
	 * @return criterion's index
	 */
	public int getIndex() {
		return arrayIndex;
	}

	/**
	 * The method returns the word ID of the knowledge fact criterion.
	 * 
	 * @return criterion's word ID
	 */
	public int getWordID() {
		return arrayIndex + CRITERION_WORD_ID_OFFSET;
	}
	
	/**
	 * The method returns the knowledge fact criterion name which belongs to the parameter
	 * criterion index.
	 * 
	 * @param arrayIndex
	 *            criterion index
	 * @return criterion name
	 */
	public KnowledgeFactCriterion getName(int arrayIndex) {
		for (KnowledgeFactCriterion criterion : KnowledgeFactCriterion.values())
			if (criterion.arrayIndex == arrayIndex)
				return criterion;
		return null;
	}	
}
