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

import java.util.ArrayList;
import java.util.List;

import org.socialworld.collections.ReadOnlyIterator;
import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Numerus;
import org.socialworld.conversation.SpeechRecognition;
import org.socialworld.conversation.SpeechRecognition_Function;
import org.socialworld.conversation.Word;

public class Knowledge {

	final int MAXIMUM_KNOWLEDGE_POOL_CAPACITY = 100;
	final int COMBINE_ARRAY_STEP = 13;
	
	private KnowledgeElement knowledgeElementList[];
	private int accessCount[];
	
	private int maxAccessCount;
	private int maxAccessCountIndex;
	
	private int next_combine_index;
	
	private double allowCombinePercent = 0.75;   // between 0 and 1 (0% - 100%)
	

	private SpeechRecognition speechRecognition;
	
	public Knowledge() {
		knowledgeElementList = new KnowledgeElement[MAXIMUM_KNOWLEDGE_POOL_CAPACITY];
		accessCount = new int[MAXIMUM_KNOWLEDGE_POOL_CAPACITY];
		
		maxAccessCount = 0;
		maxAccessCountIndex = 0;
		next_combine_index = 0;

		speechRecognition = new SpeechRecognition();
		
	}

	public void addKnowledge(KnowledgeElement knowledgeElement) {
		
		int index;
		
		index = indexForNewEntry();
		knowledgeElementList[index] = knowledgeElement;
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
					knowledgeIndex = addNewKnowledgeElement(subject, fact, source);
				else
					addToKnowledgeElement(knowledgeIndex, fact, source);
				
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
						knowledgeIndex = addNewKnowledgeElement(object1, fact, source);
					else
						addToKnowledgeElement(knowledgeIndex, fact, source);
					
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
						knowledgeIndex = addNewKnowledgeElement(object2, fact, source);
					else
						addToKnowledgeElement(knowledgeIndex, fact, source);
					
				}
			}
			while(fact != null);		
		}
		
	}
	
	public List<AnswerProperty> getAnswersForQuestion(String question){
		Word subject;
		Lexem lexemSubject;
		Numerus numerusSubject;
		int indexesForSubject[];
		int indexesForCriterion[];
		int countKnowledges = 0;
		int indexKnowledge;
		int countFacts = 0;
		int indexFact;
		
		KnowledgeFact fact;
		KnowledgeSource source;
		KnowledgeElement knowledgeElement;
		ReadOnlyIterator<KnowledgeFact_Criterion> criterions = null;
		
		AnswerProperty answer;
		List<AnswerProperty> answers;
		boolean withAnswer = false;
		
		speechRecognition.analyseSentence(question);

		subject = speechRecognition.getSubject();
		lexemSubject = subject.getLexem();
		numerusSubject = subject.getNumerus();
		
		criterions = speechRecognition.getCriterions(SpeechRecognition_Function.subject);
		
		indexesForSubject = findAllKnowledgeElementsForSubject(lexemSubject);
		countKnowledges = indexesForSubject.length;
		
		answers = new ArrayList<AnswerProperty>();
		
		// TODO iterate over criterions
		for (indexKnowledge = 0;indexKnowledge < countKnowledges; indexKnowledge++) {
			
			knowledgeElement = getKnowledgeElement(indexesForSubject[indexKnowledge]);
			indexesForCriterion = knowledgeElement.findFactsForCriterion(criterions.next());
			countFacts = indexesForCriterion.length;
			if (countFacts > 0) { 
				withAnswer = true;
			}
			
			for (indexFact = 0;indexFact < countFacts; indexFact++) {
				fact = knowledgeElement.getFactAsCopy(indexesForCriterion[indexFact]);
				source = knowledgeElement.getSourceAsCopy(indexesForCriterion[indexFact]);
				
				answer = new AnswerProperty((KnowledgeProperty)fact);
				answer.setSubject(lexemSubject, numerusSubject);
				answer.setSource(source);
				
				answers.add(answer);
			}
		}

		if (withAnswer) return answers;
		else return null;
	}
	
	public void combine() {
		KnowledgeElement knowledgeElementA;
		KnowledgeElement knowledgeElementB;
		
		int resultCompare;
		int countFactsKnowledgeA;
		int countFactsKnowledgeB;
		
		knowledgeElementA = knowledgeElementList[next_combine_index];
		if ((knowledgeElementA == null) | (!knowledgeElementA.isValid()) ) return;

		countFactsKnowledgeA = knowledgeElementA.countValidFacts();
		
		for (int i = 0; i < MAXIMUM_KNOWLEDGE_POOL_CAPACITY;i++) {
			knowledgeElementB = knowledgeElementList[i];
			if ((knowledgeElementB != null) & knowledgeElementB.isValid() ) {
				resultCompare = knowledgeElementA.compareTo(knowledgeElementB);
				
				//  combine allowed?
				countFactsKnowledgeB = knowledgeElementB.countValidFacts();
				if ( (resultCompare / countFactsKnowledgeA > allowCombinePercent)
						& (resultCompare / countFactsKnowledgeB > allowCombinePercent) ) {
					knowledgeElementA.combineWith(knowledgeElementB);
					knowledgeElementList[i] = null;
				}
			}
		}
		
		next_combine_index = next_combine_index + COMBINE_ARRAY_STEP;
		next_combine_index = next_combine_index % MAXIMUM_KNOWLEDGE_POOL_CAPACITY;
	}
	
	private int addNewKnowledgeElement(Word subject, KnowledgeFact fact, KnowledgeSource source) {
		
		KnowledgeElement knowledgeElement;
		int index;

		Lexem lexemSubject;
		Numerus numerusSubject;

		lexemSubject = subject.getLexem();
		numerusSubject = subject.getNumerus();
		
		knowledgeElement = new KnowledgeElement(lexemSubject);
		
		knowledgeElement.add(fact, source);
		
		index = indexForNewEntry();
		knowledgeElementList[index] = knowledgeElement;
		accessCount[index] = 0;
		
		return index;
	}
	
	private void addToKnowledgeElement(int knowledgeElementIndex, KnowledgeFact fact, KnowledgeSource source) {
		if (knowledgeElementIndex < MAXIMUM_KNOWLEDGE_POOL_CAPACITY) {
			knowledgeElementList[knowledgeElementIndex].add(fact, source);
		}
	}
	
	public int findMostFrequentKnowledgeElementForSubject(Lexem subject) {
		
		int index;
		int  foundIndex = -1;
		int mostFrequent = 0;
		
		for (index=0;index < MAXIMUM_KNOWLEDGE_POOL_CAPACITY; index++) {
			if (knowledgeElementList[index].getLexemSubject() == subject) {
				if (accessCount[index] > mostFrequent) {
					
						foundIndex = index;
						mostFrequent = accessCount[index];

				}
			}
		}
		
		return foundIndex;
	}
	
	public int[] findAllKnowledgeElementsForSubject(Lexem subject) {
		
		int result_tmp[] = new int[MAXIMUM_KNOWLEDGE_POOL_CAPACITY];
		int result[];
		int count = 0;
		int index;
		
		for (index=0;index < MAXIMUM_KNOWLEDGE_POOL_CAPACITY; index++) {
			
			if (knowledgeElementList[index] != null) {
				if (  knowledgeElementList[index].getLexemSubject() == subject) 	{
						
					result_tmp[count] = index;
					count++;
				}
			}
		}

		result = new int[ count];
		for (index = 0; index < count; index++) {
			result[index] = result_tmp[index];
		}
		
		return result;

	}
	
	
	
	
	public KnowledgeElement getKnowledgeElement(int index) {
		if ( index >= 0 & index < MAXIMUM_KNOWLEDGE_POOL_CAPACITY) {
			accessCount[index]++;
			if (accessCount[index] > maxAccessCount) {
				maxAccessCount = accessCount[index];
				maxAccessCountIndex = index;
			}
			return knowledgeElementList[index];
		}
		else
			return null;
	}
	
	public KnowledgeFact getFact (int knowledgeElementIndex, int factIndex) {
		if ( knowledgeElementIndex >= 0 & knowledgeElementIndex < MAXIMUM_KNOWLEDGE_POOL_CAPACITY) {
			accessCount[knowledgeElementIndex]++;
			if (accessCount[knowledgeElementIndex] > maxAccessCount) {
				maxAccessCount = accessCount[knowledgeElementIndex];
				maxAccessCountIndex = knowledgeElementIndex;
			}
			return knowledgeElementList[knowledgeElementIndex].getFactAsCopy(factIndex);
		}
		else
			return null;
	}
	
	
	
	

	
	public void SetAllowCombinePercent(int percent) {
		this.allowCombinePercent = percent;
	}
	
	private int indexForNewEntry() {
		
		KnowledgeElement knowledgeElement;
		int fewestAccess;
		int indexWithFewestAccess;
		
		// find empty or invalid entry
		for (int i = 0; i < MAXIMUM_KNOWLEDGE_POOL_CAPACITY;i++) {
			knowledgeElement = this.knowledgeElementList[i];
			if ((knowledgeElement == null) || (!knowledgeElement.isValid()) ) return i;
		}
		
		
		// find entry with fewest access
		fewestAccess = maxAccessCount + 1;
		indexWithFewestAccess = maxAccessCountIndex;
		for (int i = 0; i < MAXIMUM_KNOWLEDGE_POOL_CAPACITY;i++) {
			if ( this.accessCount[i] < fewestAccess) {
				indexWithFewestAccess = i;
				fewestAccess = this.accessCount[i];
			}
		}
		return indexWithFewestAccess;
		
	}
}
