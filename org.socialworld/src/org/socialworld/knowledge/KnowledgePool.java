package org.socialworld.knowledge;
import org.socialworld.conversation.SpeechRecognition;

public class KnowledgePool {

	final int MAXIMUM_KNOWLEDGE_POOL_CAPACITY = 100;
	final int COMBINE_ARRAY_STEP = 13;
	
	private Knowledge knowledgeList[];
	private int accessCount[];
	
	private int maxAccessCount;
	private int maxAccessCountIndex;
	
	private int next_combine_index;
	
	private double allowCombinePercent = 0.75;   // between 0 and 1 (0% - 100%)
	
	private int foundKnowledgeIndex;
	private int foundFactIndex;

	private SpeechRecognition speechRecognition;
	
	public KnowledgePool() {
		knowledgeList = new Knowledge[MAXIMUM_KNOWLEDGE_POOL_CAPACITY];
		accessCount = new int[MAXIMUM_KNOWLEDGE_POOL_CAPACITY];
		
		maxAccessCount = 0;
		maxAccessCountIndex = 0;
		next_combine_index = 0;

		speechRecognition = new SpeechRecognition();
		
	}

	public void addFactsFromSentence(String sentence, KnowledgeSource source) {
		int subjectWordID;
		KnowledgeFact fact;
		
		int countFacts = 0;
		int knowledgeIndex = 0;
	
		
		speechRecognition.analyseSentence(sentence);
		speechRecognition.resetIndexSearchCriterion();
		
		subjectWordID = speechRecognition.getSubject();
		
		do
		{
			fact = speechRecognition.getNextFact();
			if (fact != null) {
				countFacts++;
				if (countFacts == 1)
					knowledgeIndex = addNewKnowledge(subjectWordID, fact, source);
				else
					addToKnowledge(knowledgeIndex, fact, source);
				
			}
		}
		while(fact != null);
		
	}
	
	public void combine() {
		Knowledge knowledgeA;
		Knowledge knowledgeB;
		
		int resultCompare;
		int countFactsKnowledgeA;
		int countFactsKnowledgeB;
		
		knowledgeA = knowledgeList[next_combine_index];
		if ((knowledgeA == null) | (!knowledgeA.isValid()) ) return;

		countFactsKnowledgeA = knowledgeA.countValidItems();
		
		for (int i = 0; i < MAXIMUM_KNOWLEDGE_POOL_CAPACITY;i++) {
			knowledgeB = knowledgeList[i];
			if ((knowledgeB != null) & knowledgeB.isValid() ) {
				resultCompare = knowledgeA.compareTo(knowledgeB);
				
				//  combine allowed?
				countFactsKnowledgeB = knowledgeB.countValidItems();
				if ( (resultCompare / countFactsKnowledgeA > allowCombinePercent)
						& (resultCompare / countFactsKnowledgeB > allowCombinePercent) ) {
					knowledgeA.combineWith(knowledgeB);
					knowledgeList[i] = null;
				}
			}
		}
		
		next_combine_index = next_combine_index + COMBINE_ARRAY_STEP;
		next_combine_index = next_combine_index % MAXIMUM_KNOWLEDGE_POOL_CAPACITY;
	}
	
	private int addNewKnowledge(int subjectWordID, KnowledgeFact fact, KnowledgeSource source) {
		Knowledge knowledge;
		int index;
		
		knowledge = new Knowledge();
		
		knowledge.setSubject(subjectWordID);
		knowledge.addItem(fact, source);
		
		index = indexForNewEntry();
		knowledgeList[index] = knowledge;
		accessCount[index] = 0;
		
		return index;
	}
	
	private void addToKnowledge(int knowledgeIndex, KnowledgeFact fact, KnowledgeSource source) {
		if (knowledgeIndex < MAXIMUM_KNOWLEDGE_POOL_CAPACITY) {
			knowledgeList[knowledgeIndex].addItem(fact, source);
		}
	}
	
	public int findSubject(int word) {
		int[] empty = {};
		
		return findSubject(word, empty);
	}
	
	public int findSubject(int word, int[] butNotIndexs) {
		int index;
		int  foundIndex = -1;
		int mostFrequent = 0;
		boolean ignoreIndex = false;
		
		for (index=0;index < MAXIMUM_KNOWLEDGE_POOL_CAPACITY; index++) {
			if (knowledgeList[index].getSubject() == word) {
				if (accessCount[index] > mostFrequent) {
					
					ignoreIndex = false;
					for(int i=0; i < butNotIndexs.length; i++) {
						if (index == butNotIndexs[i]) ignoreIndex = true;
					}
					
					if (ignoreIndex == false) {
						foundIndex = index;
						mostFrequent = accessCount[index];

					}
				}
			}
		}

		return foundIndex;
		
	}
	
	public Knowledge getKnowledge(int index) {
		if ( index >= 0 & index < MAXIMUM_KNOWLEDGE_POOL_CAPACITY) {
			accessCount[index]++;
			if (accessCount[index] > maxAccessCount) {
				maxAccessCount = accessCount[index];
				maxAccessCountIndex = index;
			}
			return knowledgeList[index];
		}
		else
			return null;
	}
	
	public KnowledgeFact getFact (int knowledgeIndex, int factIndex) {
		if ( knowledgeIndex >= 0 & knowledgeIndex < MAXIMUM_KNOWLEDGE_POOL_CAPACITY) {
			accessCount[knowledgeIndex]++;
			if (accessCount[knowledgeIndex] > maxAccessCount) {
				maxAccessCount = accessCount[knowledgeIndex];
				maxAccessCountIndex = knowledgeIndex;
			}
			return knowledgeList[knowledgeIndex].getFact(factIndex);
		}
		else
			return null;
	}
	
	
	public KnowledgeFact getFact(KnowledgeFactCriterion criterion, int valueWordID, int[] butNotIndexs) {
		
		findFact( criterion,  valueWordID, butNotIndexs);
		if (this.foundKnowledgeIndex >= 0 & this.foundFactIndex >= 0)
			return getFact(this.foundKnowledgeIndex, this.foundFactIndex);
		else
			return null;
	}
	
	private void findFact(KnowledgeFactCriterion criterion, int valueWordID, int[] butNotIndexs) {
		int factIndex = -1;
		int index;
		int  foundIndex = -1;
		boolean ignoreIndex = false;
		
		for (index=0; index < MAXIMUM_KNOWLEDGE_POOL_CAPACITY; index++) {
			
			ignoreIndex = false;
			for (int i=0; i < butNotIndexs.length; i++) {
				if (index == butNotIndexs[i]) ignoreIndex = true;
			}
			
			if (ignoreIndex == false) {
				factIndex = knowledgeList[index].findFact(true, valueWordID);
				if (factIndex >= 0 )  
					if (knowledgeList[index].getFact(factIndex).getCriterion() == criterion)	foundIndex = index;
			}
		}

		foundKnowledgeIndex = foundIndex;
		foundFactIndex = factIndex;
		
	}
	

	
	public void SetAllowCombinePercent(int percent) {
		allowCombinePercent = percent;
	}
	
	private int indexForNewEntry() {
		Knowledge knowledge;
		int fewestAccess;
		int indexWithFewestAccess;
		
		// find empty or invalid entry
		for (int i = 0; i < MAXIMUM_KNOWLEDGE_POOL_CAPACITY;i++) {
			knowledge = knowledgeList[i];
			if ((knowledge == null) | (!knowledge.isValid()) ) return i;
		}
		
		
		// find entry with fewest access
		fewestAccess = maxAccessCount + 1;
		indexWithFewestAccess = maxAccessCountIndex;
		for (int i = 0; i < MAXIMUM_KNOWLEDGE_POOL_CAPACITY;i++) {
			if ( accessCount[i] < fewestAccess) {
				indexWithFewestAccess = i;
				fewestAccess = accessCount[i];
			}
		}
		return indexWithFewestAccess;
		
	}
}
