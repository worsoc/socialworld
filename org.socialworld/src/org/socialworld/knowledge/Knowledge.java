package org.socialworld.knowledge;

public class Knowledge {
	final int MAXIMUM_KNOWLEDGE_CAPACITY = 100;
	
	private int wordIDs[];
	private int wordAccessCount[];
	
	private KnowledgeColour colour[];
	private KnowledgeLocation location[];
	private KnowledgeLocationByPreposition locationByPreposition[];
	private KnowledgeMaterial material[];


	public Knowledge() {
		wordIDs = new int[MAXIMUM_KNOWLEDGE_CAPACITY];
		
		colour = new KnowledgeColour[MAXIMUM_KNOWLEDGE_CAPACITY];
		location = new KnowledgeLocation[MAXIMUM_KNOWLEDGE_CAPACITY];
		locationByPreposition = new KnowledgeLocationByPreposition[MAXIMUM_KNOWLEDGE_CAPACITY];
		material = new KnowledgeMaterial[MAXIMUM_KNOWLEDGE_CAPACITY];
	}
	
	public KnowledgeColour getKnowledgeColour(int index) {
		if ((index >= 0) & (index < MAXIMUM_KNOWLEDGE_CAPACITY) )
			return colour[index];
		else
			return null;
	}

	public KnowledgeLocation getKnowledgeLocation(int index) {
		if ((index >= 0) & (index < MAXIMUM_KNOWLEDGE_CAPACITY) )
			return location[index];
		else
			return null;
	}
	
	public KnowledgeLocationByPreposition getKnowledgeLocationByPreposition(int index) {
		if ((index >= 0) & (index < MAXIMUM_KNOWLEDGE_CAPACITY) )
			return locationByPreposition[index];
		else
			return null;
	}

	public KnowledgeMaterial getKnowledgeMaterial(int index) {
		if ((index >= 0) & (index < MAXIMUM_KNOWLEDGE_CAPACITY) )
			return material[index];
		else
			return null;
	}


	public int findWord(int word) {
		int[] empty = {};
		
		return findWord(word, empty);
	}
	
	
	public int findWord(int word, int[] butNotIDs) {
		int id;
		int  foundID = -1;
		int mostFrequent = 0;
		boolean ignoreID = false;
		
		for (id=0;id < MAXIMUM_KNOWLEDGE_CAPACITY; id++) {
			if (wordIDs[id] == word) {
				if (wordAccessCount[id] > mostFrequent) {
					
					ignoreID = false;
					for(int i=0; i < butNotIDs.length; i++) {
						if (id == butNotIDs[i]) ignoreID = true;
					}
					
					if (ignoreID == false) {
						foundID = id;
						mostFrequent = wordAccessCount[id];

					}
				}
			}
		}

		return foundID;
		
	}
	
	public void addWord(int word) {
		int 	nextKnowledgeWordID;
		
		nextKnowledgeWordID = getKnowledgeWordID();
		
		wordIDs[nextKnowledgeWordID] = word;
		wordAccessCount[nextKnowledgeWordID] = 1;
		
	}
	
	private int getKnowledgeWordID() {
		int 	nextKnowledgeWordID;
		int i;
		
		nextKnowledgeWordID = 0;
		for (i=0;i < MAXIMUM_KNOWLEDGE_CAPACITY; i++) {
			if (wordAccessCount[i] < wordAccessCount[nextKnowledgeWordID]) {
				nextKnowledgeWordID = i;
			}
		}
		
		return nextKnowledgeWordID ;
	}
}
