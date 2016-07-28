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
package org.socialworld.conversation;

import java.util.ArrayList;

import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.collections.ReadOnlyIterator;
import org.socialworld.knowledge.KnowledgeFact;
import org.socialworld.knowledge.KnowledgeFactPool;

public class SpeechRecognition {
	private ArrayList<String> wordList;
	private Word[] foundWordList; //according to the word list above
	private SpeechRecognition_Function[] functionList; //according to the word list above
	
	private int indexWordList;
	private int startSearchForCriterion = 0;
	private Word lastSubject;
	
	private PunctuationMark finalPunctuationMark;
	private WordSearchTree allWords;
	
	
	ReadOnlyIterator<KnowledgeFact_Criterion> iteratorForEmptyCriterionsList;
	
	public SpeechRecognition() {
		allWords = new WordSearchTree();
		lastSubject= null;
	
		iteratorForEmptyCriterionsList = new ReadOnlyIterator<KnowledgeFact_Criterion>(new ArrayList<KnowledgeFact_Criterion>().iterator()) ;
		
	}
	
	
	public void analyseSentence(String sentence) {
		int count;
		
		count = divideIntoParts(sentence);
		
		foundWordList = new Word[count];
		functionList = new SpeechRecognition_Function[count];
		if (checkCorrectness()){
			
		}
	}
	
	public void resetLastSubject() {
		lastSubject = null;
	}

	public void resetIndexSearchCriterion() {
		startSearchForCriterion = 0;
	}

	public Word getSubject() {
		Word word = null;
		
		for (int index = 0; index < wordList.size(); index++) {
			if (functionList[index] == SpeechRecognition_Function.subject) {
				// if the sentence's subject is NOT a knowledge subject (see WordSearchtreeNode) too,
				// return the subject from a previous sentence
				// because the actual sentence belongs to the knowledge subject of a previous sentence
				
				// for example (in both sentences the knowledge subject is "boy"):
				// 1. "The boy has black hair."
				// 2. "He is 15 years old."
				
				if (foundWordList[index].isAllowedAsKnowledgeSubject())	word = foundWordList[index];
				index = wordList.size();
			}
		}
		
		if (word == null) word = lastSubject;
		return word;
	}
	
	public ReadOnlyIterator<KnowledgeFact_Criterion> getCriterions() {
		
		// initialize with iterator for temp empty list
		ReadOnlyIterator<KnowledgeFact_Criterion> criterions = iteratorForEmptyCriterionsList ;
		
		for (int index = startSearchForCriterion; index < wordList.size(); index++) {
			criterions = foundWordList[index].getKnowledgeFact_Criterions();
			if (criterions.hasNext()) {
				indexWordList = index;
				return criterions;
			}
		}
		return criterions;
	}
	
	public KnowledgeFact getNextFact() {
		KnowledgeFact fact = null;
		
		ReadOnlyIterator<KnowledgeFact_Criterion> iteratorCriterions;
		
		SpeechRecognition_Function function = null;
		Word word = null;
		
		iteratorCriterions = getCriterions();
		
		// TODO iterate over criterions
		if (iteratorCriterions.hasNext()) {
			function = functionList[indexWordList];
			for (int index = 0; index < wordList.size(); index++) {
				if (functionList[index] == function) {
					word = foundWordList[index];
					break;
				}
			}
			if (word != null)	fact = KnowledgeFactPool.getInstance().find( iteratorCriterions.next(), word);
		}
		
		return fact;
	}
	
	private int divideIntoParts(String sentence)  {
		String words[];
		
		sentence.replaceAll(",", " , ");
		
		words = sentence.split(" ");
		writeWordsToList(words);
		
		setFinalPunctuationMark(words[words.length - 1]);
		
		return words.length;
	}

	private boolean checkCorrectness() {
		boolean isCorrect;
		boolean withQuestionWord;
		Auxiliary aux;
		
		isCorrect = titleOK(SpeechRecognition_Function.title);
		
		switch (finalPunctuationMark) {
		case dot:
			isCorrect = subjectOK(SpeechRecognition_Function.subject) &&
			verbOK(SpeechRecognition_Function.verb) &&
			objectOK(SpeechRecognition_Function.object1) && 
			objectOK(SpeechRecognition_Function.object2); break;
		case question:
			withQuestionWord = questionOK(SpeechRecognition_Function.questionWord);
			aux = auxiliaryOK();
			
			isCorrect = (aux != null) && 
					subjectOK(SpeechRecognition_Function.subject) &&
					verbRestOK(SpeechRecognition_Function.verb, aux) &&
					objectOK(SpeechRecognition_Function.object1) && 
					objectOK(SpeechRecognition_Function.object2); 
			
			if (withQuestionWord && !isCorrect  )
				isCorrect = beOK(SpeechRecognition_Function.verb) && subjectOK(SpeechRecognition_Function.subject);
			else if	(withQuestionWord && isCorrect  )
				prepositionOK(SpeechRecognition_Function.object1);
			
			break;
		case exclamation:
			isCorrect = infinitiveOK(SpeechRecognition_Function.verb) &&
			objectOK(SpeechRecognition_Function.object1) && 
			objectOK(SpeechRecognition_Function.object2); break;
			
		}
		
		return isCorrect;
	}
	
	private boolean subjectOK(SpeechRecognition_Function function) {
		boolean isOK = false;
		isOK = itemOK(SpeechRecognition_Function.subject) ||
				personOK(SpeechRecognition_Function.subject) ||
				personalPronounOK(SpeechRecognition_Function.subject);	
		return isOK;
	}

	private boolean verbOK(SpeechRecognition_Function function) {
		boolean isOK = false;
		int index_save;
		String word;
		
		index_save = indexWordList;
		
		word = wordList.get(indexWordList);
		if (word == "have" || word == "has" || word == "had") {
			functionList[indexWordList] = SpeechRecognition_Function.firstAuxVerb;
			foundWordList[indexWordList] = null;
			indexWordList++;
			word = wordList.get(indexWordList); 
			if (word == "been"){
				functionList[indexWordList] = SpeechRecognition_Function.secondAuxVerb_be;
				foundWordList[indexWordList] = null;
				indexWordList++;
				isOK = pastParticipleOK(function);
			}
			else isOK = pastParticipleOK(function);
		}
		else if (goingToFutureOK()) isOK = infinitiveOK(function);
		else if (auxiliaryOK() != null)  {
			word = wordList.get(indexWordList);
			if (word == "be") {
				functionList[indexWordList] = SpeechRecognition_Function.secondAuxVerb_be;
				foundWordList[indexWordList] = null;
				indexWordList++;
				isOK = pastParticipleOK(function);
			}
			else isOK = infinitiveOK(function);
		}
		else isOK = finiteFormOK(function);
		
		if (isOK == false) indexWordList = index_save;
		
		return isOK;
	}
	
	private boolean verbRestOK(SpeechRecognition_Function function, Auxiliary aux) {
		boolean isOK = false;
		int index_save;
		String word;
		
		index_save = indexWordList;
		
		word = wordList.get(indexWordList);
		
		if ( (aux == Auxiliary.have) || (aux == Auxiliary.has) || (aux == Auxiliary.had) )
		{
			if ( word == "been" ) {
				functionList[indexWordList] = SpeechRecognition_Function.secondAuxVerb_be;
				foundWordList[indexWordList] = null;
				indexWordList++;
				isOK = pastParticipleOK(function);
			}
			else
				isOK = pastParticipleOK(function);
		}
		else if ( (aux == Auxiliary.will) || (aux == Auxiliary.would) || (aux == Auxiliary.can) ||
				(aux == Auxiliary.could) || (aux == Auxiliary.shall) || (aux == Auxiliary.should)	)
			 {
				 if ( word == "be" )
				 {
					functionList[indexWordList] = SpeechRecognition_Function.secondAuxVerb_be;
					foundWordList[indexWordList] = null;
					indexWordList++;
					isOK = pastParticipleOK(function);
				 }
				 else isOK = infinitiveOK(function);
			 }
			 else isOK = infinitiveOK(function);

		if (isOK == false) indexWordList = index_save;
		
		return isOK;
	}
	
	private boolean objectOK(SpeechRecognition_Function function) {
		boolean isOK = false;
		int index_save;
		String word;
		
		index_save = indexWordList;

		prepositionOK(function);

		word = wordList.get(indexWordList);
		if (word == "the") {
			functionList[indexWordList] = function;
			foundWordList[indexWordList] = null;
			indexWordList++;
			isOK = nounOK(function);
		}
		else if (pronounOK(function)) 			isOK = nounOK(function);
			 else isOK = personOK(function);
		
		if (isOK == false) indexWordList = index_save;
		return isOK;
	}

	private boolean wordOK(SpeechRecognition_Function function, Word_Type type) {
		boolean isOK = false;
		String word;
		Word foundWord;
		
		word = wordList.get(indexWordList);
		foundWord = allWords.findAndGetWord(word);
		isOK = (foundWord.getType() == type);
		if (isOK) {
			functionList[indexWordList] = function;
			
			foundWordList[indexWordList] = foundWord;
			indexWordList++;
		}
		
		return isOK;
	}
	
	private boolean prepositionOK(SpeechRecognition_Function function) {
		return wordOK(function, Word_Type.preposition);
	}

	private boolean nounOK(SpeechRecognition_Function function) {
		return  wordOK(function, Word_Type.noun);
	}
	
	private boolean pronounOK(SpeechRecognition_Function function) {
		boolean isOK;
		isOK = ( wordOK(function, Word_Type.possessive_pronoun) || wordOK(function, Word_Type.demonstrative_pronoun) );
		return isOK;
	}
	
	private boolean nameOK(SpeechRecognition_Function function) {
		return wordOK(function, Word_Type.name);
	}
	
	private boolean titleOK(SpeechRecognition_Function function) {
		return wordOK(function, Word_Type.title);
	}
	
	private boolean personalPronounOK(SpeechRecognition_Function function) {
		return wordOK(function, Word_Type.personal_pronoun);
	}

	private boolean infinitiveOK(SpeechRecognition_Function function) {
		return wordOK(function, Word_Type.infinitive);
	}

	private boolean beOK(SpeechRecognition_Function function) {
		String word;
		boolean isOK;
		
		word = wordList.get(indexWordList);
		
		switch (word) {
		case "am": isOK = true; break;
		case "are": isOK = true; break;
		case "is": isOK = true; break;
		case "was": isOK = true; break;
		case "were": isOK = true; break;
		default: isOK = false;
		}
		if (isOK) {
			functionList[indexWordList] = function;
			foundWordList[indexWordList] = null;
			indexWordList++;
		}
		return isOK;
	}

	private boolean pastParticipleOK(SpeechRecognition_Function function) {
		return wordOK(function, Word_Type.past_participle);
	}

	private Auxiliary auxiliaryOK() {
		String word;
		
		word = wordList.get(indexWordList);
		
		switch (word) {
		case "will": functionList[indexWordList] = SpeechRecognition_Function.firstAuxVerb;
				indexWordList++; return Auxiliary.will;
		case "would": functionList[indexWordList] = SpeechRecognition_Function.firstAuxVerb;
				indexWordList++; return Auxiliary.would;
		case "can": functionList[indexWordList] = SpeechRecognition_Function.firstAuxVerb;
				indexWordList++; return Auxiliary.can;
		case "could": functionList[indexWordList] = SpeechRecognition_Function.firstAuxVerb;
				indexWordList++; return Auxiliary.could;
		case "do": functionList[indexWordList] = SpeechRecognition_Function.firstAuxVerb;
				indexWordList++; return Auxiliary.do_;
		case "does": functionList[indexWordList] = SpeechRecognition_Function.firstAuxVerb;
				indexWordList++; return Auxiliary.does;
		case "did": functionList[indexWordList] = SpeechRecognition_Function.firstAuxVerb;
				indexWordList++; return Auxiliary.did;
		case "have": functionList[indexWordList] = SpeechRecognition_Function.firstAuxVerb;
				indexWordList++; return Auxiliary.have;
		case "has": functionList[indexWordList] = SpeechRecognition_Function.firstAuxVerb;
				indexWordList++; return Auxiliary.has;
		case "had": functionList[indexWordList] = SpeechRecognition_Function.firstAuxVerb;
				indexWordList++; return Auxiliary.had;
		case "shall": functionList[indexWordList] = SpeechRecognition_Function.firstAuxVerb;
				indexWordList++; return Auxiliary.shall;
		case "should": functionList[indexWordList] = SpeechRecognition_Function.firstAuxVerb;
				indexWordList++; return Auxiliary.should;
		}
		return null;
	}
	
	private boolean goingToFutureOK() {
		boolean isOK = false;
		int index_save;
		String word;
		
		index_save = indexWordList;
		if (beOK(SpeechRecognition_Function.secondAuxVerb_goingto)) {
			word = wordList.get(indexWordList);
			if (word == "going") {
				functionList[indexWordList] = SpeechRecognition_Function.secondAuxVerb_goingto;
				foundWordList[indexWordList] = null;
				indexWordList++;
				word = wordList.get(indexWordList);
				if (word == "to") {
					functionList[indexWordList] = SpeechRecognition_Function.secondAuxVerb_goingto;
					foundWordList[indexWordList] = null;
					indexWordList++;
					isOK = true;
				}
			}
		}
		if (isOK == false) {
			indexWordList = index_save;
		}
		return isOK;
	}
	
	private boolean finiteFormOK(SpeechRecognition_Function function) {
		boolean isOK;
		isOK = ( wordOK(function, Word_Type.infinitive) || wordOK(function, Word_Type.finitive) );
		return isOK;
	}

	private boolean personOK(SpeechRecognition_Function function) {
		boolean isOK = false;
		
		isOK = titleOK(function);
		isOK = nameOK(function);
		
		return isOK;
	}

	private boolean itemOK(SpeechRecognition_Function function) {
		boolean isOK = false;
		int index_save;
		String word;
		
		index_save = indexWordList;
		
		word = wordList.get(indexWordList);
		if (word == "the") {
			functionList[indexWordList] = function;
			foundWordList[indexWordList] = null;
			indexWordList++;
			isOK = true;
		}
		else isOK = pronounOK(function);
		if (isOK) isOK = nounOK(function);

		if (isOK == false) {
			indexWordList = index_save;
		}
		return isOK;
	}
	
	private boolean questionOK(SpeechRecognition_Function function) {
		return wordOK(function, Word_Type.question);
	}
	

	private void writeWordsToList(String[] words) {
		int i;
		int count;
		
		count = words.length;
		for(i=0; i < count; i++) {
			wordList.add(i, words[i]);
		}

	}
	
	private void setFinalPunctuationMark(String finalPunctuationMark) {
		switch (finalPunctuationMark)  {
		case ".": this.finalPunctuationMark = PunctuationMark.dot; break;
		case "?": this.finalPunctuationMark = PunctuationMark.question; break;
		case "!": this.finalPunctuationMark = PunctuationMark.exclamation; break;
		default: this.finalPunctuationMark = null;
		}
	}
}
