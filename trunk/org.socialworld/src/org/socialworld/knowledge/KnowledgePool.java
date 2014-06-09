package org.socialworld.knowledge;

public class KnowledgePool {

	final int MAXIMUM_KNOWLEDGE_POOL_CAPACITY = 100;
	
	private Knowledge knowledgeList[];
	private int knowledgeAccessCount[];
	
	public KnowledgePool() {
		knowledgeList = new Knowledge[MAXIMUM_KNOWLEDGE_POOL_CAPACITY];
		knowledgeAccessCount = new int[MAXIMUM_KNOWLEDGE_POOL_CAPACITY];
	}

	public void addNewKnowledge(int subjectWordID, KnowledgeFact fact, KnowledgeSource source) {
		Knowledge knowledge;
		
		knowledge = new Knowledge();
		
		knowledge.setSubject(subjectWordID);
		knowledge.addItem(fact, source);
	}
	
	public void AddToKnowledge(int knowledgeID, KnowledgeFact fact, KnowledgeSource source) {
		if (knowledgeID < MAXIMUM_KNOWLEDGE_POOL_CAPACITY) {
			knowledgeList[knowledgeID].addItem(fact, source);
		}
	}
	
	public int findSubject(int word) {
		int[] empty = {};
		
		return findSubject(word, empty);
	}
	
	public int findSubject(int word, int[] butNotIDs) {
		int id;
		int  foundID = -1;
		int mostFrequent = 0;
		boolean ignoreID = false;
		
		for (id=0;id < MAXIMUM_KNOWLEDGE_POOL_CAPACITY; id++) {
			if (knowledgeList[id].getSubject() == word) {
				if (knowledgeAccessCount[id] > mostFrequent) {
					
					ignoreID = false;
					for(int i=0; i < butNotIDs.length; i++) {
						if (id == butNotIDs[i]) ignoreID = true;
					}
					
					if (ignoreID == false) {
						foundID = id;
						mostFrequent = knowledgeAccessCount[id];

					}
				}
			}
		}

		return foundID;
		
	}
	
	
}
