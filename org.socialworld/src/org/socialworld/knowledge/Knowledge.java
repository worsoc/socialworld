/*
* Social World
* Copyright (C) 2014  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.knowledge;

import org.socialworld.collections.ReadOnlyIterator;
import org.socialworld.conversation.SpeechRecognition;
import org.socialworld.conversation.SpeechRecognition_Function;
import org.socialworld.conversation.Word;

public class Knowledge {

	final int MAXIMUM_KNOWLEDGE_POOL_CAPACITY = 100;
	final int COMBINE_ARRAY_STEP = 13;
	
	private KnowledgeProperties knowledgeList[];
	private int accessCount[];
	
	private int maxAccessCount;
	private int maxAccessCountIndex;
	
	private int next_combine_index;
	
	private double allowCombinePercent = 0.75;   // between 0 and 1 (0% - 100%)
	

	private SpeechRecognition speechRecognition;
	
	public Knowledge() {
		knowledgeList = new KnowledgeProperties[MAXIMUM_KNOWLEDGE_POOL_CAPACITY];
		accessCount = new int[MAXIMUM_KNOWLEDGE_POOL_CAPACITY];
		
		maxAccessCount = 0;
		maxAccessCountIndex = 0;
		next_combine_index = 0;

		speechRecognition = new SpeechRecognition();
		
	}

	public void addKnowledge(KnowledgeProperties knowledge) {
		
		int index;
		
		index = indexForNewEntry();
		knowledgeList[index] = knowledge;
		accessCount[index] = 0;
		
	}
	
	public void addFactsFromSentence(String sentence, KnowledgeSource source) {
		Word subject;
		Word object1;
		Word object2;
		
		KnowledgeFact fact;
		
		int countFacts = 0;
		int knowledgeIndex = 0;
	
		
		speechRecognition.analyseSentence(sentence);
		
		// add facts for the sentece's subject
		subject = speechRecognition.getSubject();
		do
		{
			fact = speechRecognition.getNextFact(SpeechRecognition_Function.subject);
			if (fact != null) {
				countFacts++;
				if (countFacts == 1)
					knowledgeIndex = addNewKnowledge(subject, fact, source);
				else
					addToKnowledge(knowledgeIndex, fact, source);
				
			}
		}
		while(fact != null);
		
		// add facts for the sentece's first object
		object1 = speechRecognition.getObject1();
		if (object1 != null) {
			speechRecognition.resetCriterions();
			countFacts = 0;
			do
			{
				fact = speechRecognition.getNextFact(SpeechRecognition_Function.object1);
				if (fact != null) {
					countFacts++;
					if (countFacts == 1)
						knowledgeIndex = addNewKnowledge(object1, fact, source);
					else
						addToKnowledge(knowledgeIndex, fact, source);
					
				}
			}
			while(fact != null);
		}
		
		// add facts for the sentece's second object
		object2 = speechRecognition.getObject2();
		if (object2 != null) {
			speechRecognition.resetCriterions();
			countFacts = 0;
			do
			{
				fact = speechRecognition.getNextFact(SpeechRecognition_Function.object2);
				if (fact != null) {
					countFacts++;
					if (countFacts == 1)
						knowledgeIndex = addNewKnowledge(object2, fact, source);
					else
						addToKnowledge(knowledgeIndex, fact, source);
					
				}
			}
			while(fact != null);		
		}
		
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
		KnowledgeProperties knowledge;
		ReadOnlyIterator<KnowledgeFact_Criterion> criterions = null;
		
		Answer answer;
		boolean withAnswer = false;
		
		speechRecognition.analyseSentence(question);

		subject = speechRecognition.getSubject();
		criterions = speechRecognition.getCriterions(SpeechRecognition_Function.subject);
		
		indexesForSubject = findAllKnowledgesForSubject(subject);
		countKnowledges = indexesForSubject.length;
		
		answer = new Answer();
		
		// TODO iterate over criterions
		for (indexKnowledge = 0;indexKnowledge < countKnowledges; indexKnowledge++) {
			
			knowledge = getKnowledge(indexesForSubject[indexKnowledge]);
			indexesForCriterion = knowledge.findFactsForCriterion(criterions.next());
			countFacts = indexesForCriterion.length;
			if (countFacts > 0) { 
				answer.setSubject(subject);
				withAnswer = true;
			}
			
			for (indexFact = 0;indexFact < countFacts; indexFact++) {
				fact = knowledge.getFactAsCopy(indexesForCriterion[indexFact]);
				source = knowledge.getSourceAsCopy(indexesForCriterion[indexFact]);
				
				answer.addItem(fact, source);
			}
		}

		if (withAnswer) return answer;
		else return null;
	}
	
	public void combine() {
		KnowledgeProperties knowledgeA;
		KnowledgeProperties knowledgeB;
		
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
		KnowledgeProperties knowledge;
		int index;
		
		knowledge = new KnowledgeProperties();
		
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
	
	
	
	
	public KnowledgeProperties getKnowledge(int index) {
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
	
	public KnowledgeFact get_Fact (int knowledgeIndex, int factIndex) {
		if ( knowledgeIndex >= 0 & knowledgeIndex < MAXIMUM_KNOWLEDGE_POOL_CAPACITY) {
			accessCount[knowledgeIndex]++;
			if (accessCount[knowledgeIndex] > maxAccessCount) {
				maxAccessCount = accessCount[knowledgeIndex];
				maxAccessCountIndex = knowledgeIndex;
			}
			return knowledgeList[knowledgeIndex].getFactAsCopy(factIndex);
		}
		else
			return null;
	}
	
	
	
	

	
	public void SetAllowCombinePercent(int percent) {
		allowCombinePercent = percent;
	}
	
	private int indexForNewEntry() {
		KnowledgeProperties knowledge;
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
