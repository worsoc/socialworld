package org.socialworld.knowledge;
import org.socialworld.conversation.SpeechRecognition;
import org.socialworld.conversation.Word;

public class KnowledgePool {

	final int MAXIMUM_KNOWLEDGE_POOL_CAPACITY = 100;
	final int COMBINE_ARRAY_STEP = 13;
	
	private Knowledge knowledgeList[];
	private int accessCount[];
	
	private int maxAccessCount;
	private int maxAccessCountIndex;
	
	private int next_combine_index;
	
	private double allowCombinePercent = 0.75;   // between 0 and 1 (0% - 100%)
	

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
		Word subject;
		KnowledgeFact fact;
		
		int countFacts = 0;
		int knowledgeIndex = 0;
	
		
		speechRecognition.analyseSentence(sentence);
		speechRecognition.resetIndexSearchCriterion();
		
		subject = speechRecognition.getSubject();
		
		do
		{
			fact = speechRecognition.getNextFact();
			if (fact != null) {
				countFacts++;
				if (countFacts == 1)
					knowledgeIndex = addNewKnowledge(subject, fact, source);
				else
					addToKnowledge(knowledgeIndex, fact, source);
				
			}
		}
		while(fact != null);
		
	}
	
	public Answer getAnswerForQuestion(String question){
		Word subject;
		int indexesForSubject[];
		int indexesForCriterion[];
		int countKnowledges = 0;
		int indexKnowledge;
		int countFacts = 0;
		int indexFact;
		KnowledgeFact fact;
		KnowledgeSource source;
		Knowledge knowledge;
		KnowledgeFactCriterion criterion = null;
		
		Answer answer;
		boolean withAnswer = false;
		
		speechRecognition.analyseSentence(question);
		speechRecognition.resetIndexSearchCriterion();

		subject = speechRecognition.getSubject();
		criterion = speechRecognition.getCriterion();
		
		indexesForSubject = findAllKnowledgesForSubject(subject);
		countKnowledges = indexesForSubject.length;
		
		answer = new Answer();
		
		for (indexKnowledge = 0;indexKnowledge < countKnowledges; indexKnowledge++) {
			
			knowledge = getKnowledge(indexesForSubject[indexKnowledge]);
			indexesForCriterion = knowledge.findFactsForCriterion(criterion);
			countFacts = indexesForCriterion.length;
			
			if (countFacts > 0) { 
				answer.setSubject(subject);
				withAnswer = true;
			}
			
			for (indexFact = 0;indexFact < countFacts; indexKnowledge++) {
				fact = knowledge.getFact(indexFact);
				source = knowledge.getSource(indexFact);
				
				answer.addItem(fact, source);
			}
		}

		if (withAnswer) return answer;
		else return null;
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
	
	private int addNewKnowledge(Word subject, KnowledgeFact fact, KnowledgeSource source) {
		Knowledge knowledge;
		int index;
		
		knowledge = new Knowledge();
		
		knowledge.setSubject(subject);
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
	
	public int findMostFrequentKnowledgeForSubject(Word word) {
		int index;
		int  foundIndex = -1;
		int mostFrequent = 0;
		
		for (index=0;index < MAXIMUM_KNOWLEDGE_POOL_CAPACITY; index++) {
			if (knowledgeList[index].getSubject() == word) {
				if (accessCount[index] > mostFrequent) {
					
						foundIndex = index;
						mostFrequent = accessCount[index];

				}
			}
		}
		
		return foundIndex;
	}
	
	public int[] findAllKnowledgesForSubject(Word word) {
		int result_tmp[] = new int[MAXIMUM_KNOWLEDGE_POOL_CAPACITY];
		int result[];
		int count = 0;
		int index;
		
		
		for (index=0;index < MAXIMUM_KNOWLEDGE_POOL_CAPACITY; index++) {
			if (  knowledgeList[index].getSubject() == word) 	{
					
				result_tmp[count] = index;
				count++;
			}
		}



		result = new int[ count];
		for (index = 0; index < count; index++) {
			result[index] = result_tmp[index];
		}
		return result;

		
		
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
