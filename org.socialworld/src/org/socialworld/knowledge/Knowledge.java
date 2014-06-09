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
	
	public int getSubject() {
		return subjectWordID;
	}

	public void setSubject(int subjectWordID) {
		if (validItemCount == 0)		this.subjectWordID = subjectWordID;
	}
	
	public KnowledgeFact getFact(int index) {
		if ((index >= 0) & (index < MAXIMUM_KNOWLEDGE_CAPACITY) )
			return facts[index];
		else
			return null;
	}

	public KnowledgeSource getSource(int index) {
		if ((index >= 0) & (index < MAXIMUM_KNOWLEDGE_CAPACITY) )
			return source[index];
		else
			return null;
	}
	
	
	
	public int findFact(boolean trueFindValue_falseFindCriterion, int wordValue) {
		int[] empty = {};
		
		return findFact(trueFindValue_falseFindCriterion, wordValue, empty);
	}
	
	
	public int findFact(boolean trueFindValue_falseFindCriterion, int wordValue, int[] butNotIDs) {
		int id;
		int  foundID = -1;
		int mostFrequent = 0;
		boolean ignoreID = false;
		
		for (id=0;id < MAXIMUM_KNOWLEDGE_CAPACITY; id++) {
			if ( ( trueFindValue_falseFindCriterion && facts[id].getValue().getWordID() == wordValue) ||
				 ( !trueFindValue_falseFindCriterion && facts[id].getCriterion().getWordID() == wordValue) )	{
				if (itemAccessCount[id] > mostFrequent) {
					
					ignoreID = false;
					for(int i=0; i < butNotIDs.length; i++) {
						if (id == butNotIDs[i]) ignoreID = true;
					}
					
					if (ignoreID == false) {
						foundID = id;
						mostFrequent = itemAccessCount[id];

					}
				}
			}
		}

		return foundID;
		
	}
	
	
	public void removeItem(int id) {
		if (this.itemIsValid[id]) {
			this.itemIsValid[id] = false;
			this.itemAccessCount[id] = 0;
			validItemCount--;
		}
	}
	
	public void addItem(KnowledgeFact fact, KnowledgeSource source) {
		int 	replacableID;
		
		replacableID = getID();
		
		this.facts[replacableID] = fact;
		this.source[replacableID] = source;
		
		this.itemAccessCount[replacableID] = 2;
		
		if (this.itemIsValid[replacableID] == false) {
			this.validItemCount++;
			this.itemIsValid[replacableID] = true;
		}
		
		
	}
	
	private int getID() {
		int id;
		int i;
		
		id = 0;
		for (i=0;i < MAXIMUM_KNOWLEDGE_CAPACITY; i++) {
			if (itemAccessCount[i] < itemAccessCount[id]) {
				id = i;
			}
		}
		
		return id ;
	}
}
