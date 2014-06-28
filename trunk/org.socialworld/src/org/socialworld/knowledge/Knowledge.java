package org.socialworld.knowledge;

public class Knowledge {
	final int MAXIMUM_KNOWLEDGE_CAPACITY = 100;
	
	private int subjectWordID;
	
	private KnowledgeFact facts[];
	private KnowledgeSource source[];
	
	private int itemAccessCount[];
	
	private boolean itemIsValid[];
	private int validItemCount;
	
	public Knowledge() {
		facts = new KnowledgeFact[MAXIMUM_KNOWLEDGE_CAPACITY];
		source = new KnowledgeSource[MAXIMUM_KNOWLEDGE_CAPACITY];
		
		itemAccessCount = new int[MAXIMUM_KNOWLEDGE_CAPACITY];
		itemIsValid = new boolean[MAXIMUM_KNOWLEDGE_CAPACITY];
	}
	
	protected int compareTo(Knowledge knowledgeB) {
		int countEqual = 0;
	
		if (subjectWordID == knowledgeB.getSubject()) {
			for (int i = 0; i < MAXIMUM_KNOWLEDGE_CAPACITY; i++) {
				if (itemIsValid[i]) {
					for (int j = 0; j < MAXIMUM_KNOWLEDGE_CAPACITY; j++) {
						if (knowledgeB.isItemValid(j)) {
							if (facts[i] == knowledgeB.getFact(j))  {
								countEqual ++;
								j = MAXIMUM_KNOWLEDGE_CAPACITY;
							}
						}
					}
				}
			}
		}
		return countEqual;
	}
	
	protected void combineWith(Knowledge knowledgeB) {
		
		for (int j = 0; j < MAXIMUM_KNOWLEDGE_CAPACITY; j++) {
			if (knowledgeB.isItemValid(j)) {
				for (int i = 0; i < MAXIMUM_KNOWLEDGE_CAPACITY; i++) {
					if (itemIsValid[i])
						if (facts[i] == knowledgeB.getFact(j)) 
							// break
							i = MAXIMUM_KNOWLEDGE_CAPACITY;
						else 
							// combine fact from knowledge B to Knowledge A
							addItem(knowledgeB.getFact(j), knowledgeB.getSource(j));
				}
			}
		}
	}
	
	protected boolean isValid() {
		return (subjectWordID > 0);
	}
	
	protected int getSubject() {
		return subjectWordID;
	}

	protected int countValidItems() {
		return validItemCount;
	}
	
	protected boolean isItemValid(int index) {
		return itemIsValid[index];
	}
	
	protected void setSubject(int subjectWordID) {
		if (validItemCount == 0)		this.subjectWordID = subjectWordID;
	}
	
	protected KnowledgeFact getFact(int index) {
		if ((index >= 0) & (index < MAXIMUM_KNOWLEDGE_CAPACITY) )
			return facts[index];
		else
			return null;
	}

	protected KnowledgeSource getSource(int index) {
		if ((index >= 0) & (index < MAXIMUM_KNOWLEDGE_CAPACITY) )
			return source[index];
		else
			return null;
	}
	
	
	
	protected int findFact(boolean trueFindValue_falseFindCriterion, int wordValue) {
		int[] empty = {};
		
		return findFact(trueFindValue_falseFindCriterion, wordValue, empty);
	}
	
	
	protected int findFact(boolean trueFindValue_falseFindCriterion, int wordValue, int[] butNotIndexs) {
		int index;
		int  foundIndex = -1;
		int mostFrequent = 0;
		boolean ignoreIndex = false;
		
		for (index=0;index < MAXIMUM_KNOWLEDGE_CAPACITY; index++) {
			if ( ( trueFindValue_falseFindCriterion && facts[index].getValue().getWordID() == wordValue) ||
				 ( !trueFindValue_falseFindCriterion && facts[index].getCriterion().getWordID() == wordValue) )	{
				if (itemAccessCount[index] > mostFrequent) {
					
					ignoreIndex = false;
					for(int i=0; i < butNotIndexs.length; i++) {
						if (index == butNotIndexs[i]) ignoreIndex = true;
					}
					
					if (ignoreIndex == false) {
						foundIndex = index;
						mostFrequent = itemAccessCount[index];

					}
				}
			}
		}

		return foundIndex;
		
	}
	
	
	protected void removeItem(int index) {
		if (this.itemIsValid[index]) {
			this.itemIsValid[index] = false;
			this.itemAccessCount[index] = 0;
			validItemCount--;
		}
	}
	
	protected void addItem(KnowledgeFact fact, KnowledgeSource source) {
		int 	replacableIndex;
		
		replacableIndex = getIndex();
		
		this.facts[replacableIndex] = fact;
		this.source[replacableIndex] = source;
		
		this.itemAccessCount[replacableIndex] = 2;
		
		if (this.itemIsValid[replacableIndex] == false) {
			this.validItemCount++;
			this.itemIsValid[replacableIndex] = true;
		}
		
		
	}
	
	private int getIndex() {
		int index;
		int i;
		
		index = 0;
		for (i=0; i < MAXIMUM_KNOWLEDGE_CAPACITY; i++) {
			if (itemIsValid[i] == false) {
				index = i;
				i = MAXIMUM_KNOWLEDGE_CAPACITY;
			}
			else if (itemAccessCount[i] < itemAccessCount[index]) 	index = i;
			
		}
		
		return index ;
	}
}
