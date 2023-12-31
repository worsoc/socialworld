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
import java.util.List;

import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.collections.ReadOnlyIterator;

import org.socialworld.core.AllWords;

public class SpeechRecognition {
	private ArrayList<String> wordList;
	private Word[] foundWordList; //according to the word list above
	private SpeechRecognition_Function[] functionList; //according to the word list above
	private Word_Type[] wordTypeList; //according to the word list above
	private Tense[] tenseList; //according to the word list above
	private int[] numbers; // indexed equal to the word list above and according to funtionList[sameIndex] AND wordTypeList[sameIndex]
							//(for example: the second element for subject; the third element for object2)
	
	private int indexWordList;
	private int startSearchForCriterion = 0;
	
	private SubjectOrObject subject;
	private SubjectOrObject object1;
	private SubjectOrObject object2;

	private SubjectOrObject lastSubject;

	private PunctuationMark finalPunctuationMark;
	
	private boolean isPassive;
	
	
	ReadOnlyIterator<KnowledgeFact_Criterion> iteratorForEmptyCriterionsList;
	ReadOnlyIterator<KnowledgeFact_Criterion> iteratorCriterions;
	
	public SpeechRecognition() {
		lastSubject= null;
	
		iteratorForEmptyCriterionsList = new ReadOnlyIterator<KnowledgeFact_Criterion>(new ArrayList<KnowledgeFact_Criterion>().iterator()) ;
		iteratorCriterions = iteratorForEmptyCriterionsList;
	}
	
	
	public boolean analyseSentence(String sentence) {
		int count;
		boolean isOK = false;
		
		// new analysis -->
		reset();
		
		count = divideIntoParts(sentence);
		
		foundWordList = new Word[count];
		functionList = new SpeechRecognition_Function[count];
		wordTypeList = new Word_Type[count];
		tenseList = new Tense[count];
		numbers = new int[count];
		
		if (checkCorrectness()){
			isOK = true;
			setSubjectAndObjects();
			lastSubject = getSubject();
			setTense();
		}
		
		return isOK;
	}
	
	private void reset() {
		indexWordList = 0;
		startSearchForCriterion = 0;
		iteratorCriterions = iteratorForEmptyCriterionsList;
		
		isPassive = false;
		
		wordList = new ArrayList<String> (0);
	}
	
	public void resetCriterions() {
		startSearchForCriterion = 0;
		iteratorCriterions = iteratorForEmptyCriterionsList;
	}

	public void resetLastSubject() {
		lastSubject = null;
		subject = null;
		object1 = null;
		object2 = null;
	}
	
	private void setSubjectAndObjects() {
		
		subject = new SubjectOrObject(SpeechRecognition_Function.subject, this);
		object1 = new SubjectOrObject(SpeechRecognition_Function.object1, this);
		object2 = new SubjectOrObject(SpeechRecognition_Function.object2, this);
		
	}
	
	private int countMainElements(SpeechRecognition_Function function) {
		
		int count = -1;
		
		for (int index = 0; index < numbers.length; index++) {
			if ( (functionList[index] == function) && (wordTypeList[index] == Word_Type.noun ) ) {
				if (numbers[index] > count) {
					count = numbers[index];
				};
			}
		}
		
		return count + 1;
	}
	
	private int countAttributeElementsForMain(SpeechRecognition_Function function, int indexMain) {
		
		int count = 0;
		
		for (int index = 0; index < numbers.length; index++) {
			if ( (functionList[index] == function) && (wordTypeList[index] == Word_Type.adjective ) ) {
				if ( (numbers[index] >= indexMain * 100) && (numbers[index] < (indexMain + 1) * 100) ) {
					count++;
				}
			}
		}
		
		return count;
	}

	List<Lexem> getMains(SpeechRecognition_Function function) {
		
		List<Lexem> result = new ArrayList<Lexem>(countMainElements(function));
		int number = 0;
		
		for (int index = 0; index < wordList.size(); index++) {
			if ( (functionList[index] == function) 
					&& (foundWordList[index] != null) 
					&& (wordTypeList[index] == Word_Type.noun ) ) {
				number = numbers[index];
				result.set(number, foundWordList[index].getLexem());
			}
		}
		
		
		return result;
		
	}
	
	List<Lexem> getAttribs(SpeechRecognition_Function function, int indexMain) {
		
		List<Lexem> result = new ArrayList<Lexem>(countAttributeElementsForMain(function, indexMain));
		int number = 0;

		for (int index = 0; index < wordList.size(); index++) {
			if ( (functionList[index] == function) 
					&& (foundWordList[index] != null) 
					&& (wordTypeList[index] == Word_Type.adjective ) ) {
				if ( (numbers[index] >= indexMain * 100) && (numbers[index] < (indexMain + 1) * 100) ) {
					number = numbers[index] - (indexMain * 100);
					result.set(number, foundWordList[index].getLexem());
				}
			}
		}

		return result;
		

	}
	
	// if the sentence's subject is NOT a knowledge subject (see WordSearchtreeNode) too,
	// return the subject from a previous sentence
	// because the actual sentence belongs to the knowledge subject of a previous sentence
	
	// for example (in both sentences the knowledge subject is "boy"):
	// 1. "The boy has black hair."
	// 2. "He is 15 years old."

	public SubjectOrObject getSubject() {
		
		return subject;
		
	}
	
	public SubjectOrObject getObject1() {
		
		return object1;
		
	}	
	
	public SubjectOrObject getObject2() {
		
		return object2;
		
	}	

	public Word getVerb() {
		Word word = null;
		
		for (int index = 0; index < wordList.size(); index++) {
			if ( (functionList[index] == SpeechRecognition_Function.verb) && (foundWordList[index] != null)){
					
				word = foundWordList[index];
				
				// exit the iteration
				index = wordList.size();
				
			}
		}
		
		return word;
	}	

	
	public void setTense() {
		
		for (int index = 0; index < wordList.size(); index++) {
			
			if((functionList[index] == SpeechRecognition_Function.firstAuxVerb_have) &&( wordTypeList[index] == Word_Type.finitive )) {
					if (isPassive)tenseList[index] = Tense.present_perfect_passive;
					else tenseList[index] = Tense.present_perfect_active;
			}
			else if((functionList[index] == SpeechRecognition_Function.firstAuxVerb_have) &&( wordTypeList[index] == Word_Type.simple_past )) {
					if (isPassive)tenseList[index] = Tense.past_perfect_passive;
					else tenseList[index] = Tense.past_perfect_active;
			}
			else if((functionList[index] == SpeechRecognition_Function.firstAuxVerb_will)&&( wordTypeList[index] == Word_Type.finitive )) {
					if (isPassive)tenseList[index] = Tense.will_future_passive;
					else tenseList[index] = Tense.will_future_active;
			}
			else if((functionList[index] == SpeechRecognition_Function.firstAuxVerb_be)&&( wordTypeList[index] == Word_Type.finitive )) {tenseList[index] = Tense.simple_present_passive;}
			else if((functionList[index] == SpeechRecognition_Function.firstAuxVerb_be)&&( wordTypeList[index] == Word_Type.simple_past )) {tenseList[index] = Tense.simple_past_passive;}
			else if((functionList[index] == SpeechRecognition_Function.secondAuxVerb_goingto)&&( wordTypeList[index] == Word_Type.infinitive )) {tenseList[index] = Tense.going_to_future_active;}
			else if((functionList[index] == SpeechRecognition_Function.firstAuxVerb_do) &&( wordTypeList[index] == Word_Type.finitive )) {tenseList[index] = Tense.simple_present_active;}
			else if((functionList[index] == SpeechRecognition_Function.firstAuxVerb_do) &&( wordTypeList[index] == Word_Type.simple_past )) {tenseList[index] = Tense.simple_past_active;}
			else if((functionList[index] == SpeechRecognition_Function.firstAuxVerb_rest) &&( wordTypeList[index] == Word_Type.finitive )) {tenseList[index] = Tense.simple_present_active;}
			else if((functionList[index] == SpeechRecognition_Function.verb) && (wordTypeList[index] == Word_Type.simple_past )){tenseList[index] = Tense.simple_past_active;}
			else if((functionList[index] == SpeechRecognition_Function.verb) && (wordTypeList[index] == Word_Type.finitive )){tenseList[index] = Tense.simple_present_active;}
				
		}
		
	}
	
	public String getTense() {
		String tenseName = "";
		Tense tense = null;
		
		for (int index = 0; index < tenseList.length; index++) {

			tense = tenseList[index];
			
			if (tense != null) {
				
				switch (tense) {
				case simple_present_active: tenseName="simple Present active"; break;
				case simple_present_passive: tenseName="simple Present passive"; break;
				case simple_past_active: tenseName="simple Past active"; break;
				case simple_past_passive: tenseName="simple Past passive"; break;
				case present_perfect_active: tenseName="present Perfect active"; break;
				case present_perfect_passive: tenseName="present Perfect passive"; break;
				case past_perfect_active: tenseName="past Perfect active"; break;
				case past_perfect_passive: tenseName="past Perfect passive"; break;
				case will_future_active: tenseName="will Future active"; break;
				case will_future_passive: tenseName="will Future passive"; break;
				case going_to_future_active: tenseName="going to Future active"; break;
				case going_to_future_passive: tenseName="going to Future passive"; break;

			}
				
			
		}}
		return tenseName;
	}
	
	
	public ReadOnlyIterator<KnowledgeFact_Criterion> getCriterions(SpeechRecognition_Function function) {
		
		// initialize with iterator for temp empty list
		ReadOnlyIterator<KnowledgeFact_Criterion> criterions = iteratorForEmptyCriterionsList ;
		
		for (int index = startSearchForCriterion; index < wordList.size(); index++) {
			if (functionList[index] == function) {
				criterions = foundWordList[index].getKnowledgeFact_Criterions();
				if (criterions.hasNext()) {
					indexWordList = index;
					// next time (next call to method getCriterions()) start with the following word
					startSearchForCriterion = index + 1;
					iteratorCriterions = criterions;
					return criterions;
				}
			}
		}
		return criterions;
	}
	
	
	private int divideIntoParts(String sentence)  {
		String words[];
		
		sentence = sentence.replace(",", " , ");
		sentence = sentence.replace(".", " .");
		sentence = sentence.replace("?", " ?");
		sentence = sentence.replace("!", " !");
		sentence = sentence.replaceAll("\\s+", " ");
		sentence = sentence.toLowerCase();
		sentence = sentence.replace("going to", "going_to");
		
		words = sentence.split(" ");
		writeWordsToList(words);
		
		setFinalPunctuationMark(words[words.length - 1]);
		
		return words.length;
	}

	private boolean checkCorrectness() {
		boolean isCorrect;
		boolean withQuestionWord;
		Auxiliary aux;
		
		isCorrect = titleOK(SpeechRecognition_Function.title, 0);
		
		if (finalPunctuationMark != null)
			switch (finalPunctuationMark) {
			case dot:
				isCorrect = subjectOK(SpeechRecognition_Function.subject) &&
				verbOK(SpeechRecognition_Function.verb) ;
				
				
				if (isCorrect == true) objectOK(SpeechRecognition_Function.object1);
				if (isCorrect == true) objectOK(SpeechRecognition_Function.object2);
				
				
				isCorrect = finalPunctuationMarkOK();
				
				
				break;
				
			case question:
				withQuestionWord = questionOK(SpeechRecognition_Function.questionWord);
				aux = auxiliaryOK();
				
				isCorrect = (aux != null) && 
						subjectOK(SpeechRecognition_Function.subject) &&
						verbRestOK(SpeechRecognition_Function.verb, aux);
						
				if (isCorrect = true) objectOK(SpeechRecognition_Function.object1);
				if (isCorrect = true) objectOK(SpeechRecognition_Function.object2);
				
				
				if (withQuestionWord && !isCorrect  )
					isCorrect = beOK(SpeechRecognition_Function.verb) && subjectOK(SpeechRecognition_Function.subject);
				else if	(withQuestionWord && isCorrect  )
					prepositionOK(SpeechRecognition_Function.object1);
				
				
				isCorrect = finalPunctuationMarkOK();
				
				break;
				
			case exclamation:
				isCorrect = infinitiveOK(SpeechRecognition_Function.verb);
				
				if (isCorrect = true) objectOK(SpeechRecognition_Function.object1);
				if (isCorrect = true) objectOK(SpeechRecognition_Function.object2); 

				isCorrect = finalPunctuationMarkOK();
				
				break;
			}
		
		return isCorrect;
	}
	
	
	private boolean finalPunctuationMarkOK() {
		boolean isOK=false;
		int index_save;
		
		index_save = indexWordList;
		String word;
		
		word = wordList.get(indexWordList);
		isOK = (word.equals(".") || word.equals( "?") || word.equals("!")) ;
		
		if (isOK == false) indexWordList = index_save;
		
		
		return isOK;	
	}
	

	private boolean subjectOK(SpeechRecognition_Function function) {
		
		boolean isOK = false;
		int index_save = indexWordList;

		isOK = itemOK(SpeechRecognition_Function.subject, 0) ||
				personOK(SpeechRecognition_Function.subject, 0) ||
				personalPronounOK(SpeechRecognition_Function.subject, 0);	
		
		int numberFurtherSubjectElements = 0;
		while ( andOK(function) ) {
			
			numberFurtherSubjectElements++;
			isOK = (itemOK(SpeechRecognition_Function.subject, numberFurtherSubjectElements) ||
				personOK(SpeechRecognition_Function.subject, numberFurtherSubjectElements) ||
				personalPronounOK(SpeechRecognition_Function.subject, numberFurtherSubjectElements));
			
			if (isOK == false)		indexWordList = index_save;

		}
		return isOK;
	}

	
	
	private boolean verbOK(SpeechRecognition_Function function) {
		boolean isOK = false;
		int index_save;
		
		Auxiliary aux;
		index_save = indexWordList;
		
		aux = auxiliaryOK();
		
		if (aux != null)   {
			if ((aux == Auxiliary.will) || (aux == Auxiliary.would) || (aux == Auxiliary.can) || (aux == Auxiliary.could) || (aux == Auxiliary.shall) || (aux == Auxiliary.should))      {
				if (beOK(SpeechRecognition_Function.secondAuxVerb_be)) {
					isOK = pastParticipleOK(function);
					isPassive = true;
				}
				if (isOK == false) isOK = infinitiveOK(function);
			} // will and Co
			if ((aux == Auxiliary.have) || (aux == Auxiliary.has) || (aux == Auxiliary.had))   {
				beOK(SpeechRecognition_Function.secondAuxVerb_been);
				isOK = pastParticipleOK(function);
			}				
			if ((aux == Auxiliary.is) ||(aux == Auxiliary.am) || (aux == Auxiliary.are) || (aux == Auxiliary.was) || (aux == Auxiliary.were))    {
				if (goingToFutureOK(SpeechRecognition_Function.secondAuxVerb_goingto))
					isOK = infinitiveOK(function);
				else {
					isOK = pastParticipleOK(function);
					isPassive = true;
				}
			}
		}
		
		if (isOK == false) {
			indexWordList = index_save;
			isPassive = false;
			isOK = finiteFormOK(function);
		}


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
			if ( word.equals( "been") ) {
				beOK(SpeechRecognition_Function.secondAuxVerb_been);
				isOK = pastParticipleOK(function);
			}
			else
				isOK = pastParticipleOK(function);
		}
		else if ( (aux == Auxiliary.would) || (aux == Auxiliary.can) ||
				(aux == Auxiliary.could) || (aux == Auxiliary.shall) || (aux == Auxiliary.should)	)
			 {
				 if ( word.equals("be") )
				 {
					 beOK(SpeechRecognition_Function.secondAuxVerb_be);
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
		
		index_save = indexWordList;

		prepositionOK(function);
		isOK = itemOK(function, 0) || personOK(function, 0);
		
		if (isOK == false)
			indexWordList = index_save;
		else {
			
			int numberFurtherObjectElements = 0;
			while ( andOK(function) ) {
				
				numberFurtherObjectElements++;
				isOK = (itemOK(function, numberFurtherObjectElements) ||
						personOK(function, numberFurtherObjectElements) ) ;
			
			}
			if (isOK == false)		indexWordList = index_save;

		}

		return isOK;
	}

	private boolean attributesOK(SpeechRecognition_Function function, int number) {
		boolean isOK = false;
		int index_save;
		
		index_save = indexWordList;

		isOK = adjectiveOK(function, number * 100);
		
		// there mustn't be an attribute
		if (isOK == false) {
			indexWordList = index_save;
			return true;
		}
		
		int numberFurtherObjectElements = 0;
		while ( andOK(function)) {
			
				numberFurtherObjectElements++;
				isOK = adjectiveOK(function, number * 100 + numberFurtherObjectElements);
			
		}
		
		if (isOK == false)		indexWordList = index_save;


		return isOK;
	}

	private boolean wordOK(SpeechRecognition_Function function, Word_Type type, int number) {
		boolean isOK = false;
		String word;
		Word foundWord;
		
		word = wordList.get(indexWordList);
			
		foundWord = findAndGetWord(word, type);
		if (foundWord != null) {
				
				isOK = true;
				
				functionList[indexWordList] = function;
				wordTypeList[indexWordList] = type;
				numbers[indexWordList] = number;

				foundWordList[indexWordList] = foundWord;
				indexWordList++;
				
		}
		
		
		return isOK;
	}
	
	
	private boolean prepositionOK(SpeechRecognition_Function function) {
		return wordOK(function, Word_Type.preposition, 0);
	}

	private boolean nounOK(SpeechRecognition_Function function, int number) {
		return  wordOK(function, Word_Type.noun, number);
	}
	
	private boolean adjectiveOK(SpeechRecognition_Function function, int number) {
		return  wordOK(function, Word_Type.adjective, number);
	}
	
	private boolean pronounOK(SpeechRecognition_Function function, int number) {
		boolean isOK;
		isOK = ( wordOK(function, Word_Type.possessive_pronoun, number) 
				|| wordOK(function, Word_Type.demonstrative_pronoun, number) );
		return isOK;
	}
	
	private boolean nameOK(SpeechRecognition_Function function, int number) {
		return wordOK(function, Word_Type.name, number);
	}
	
	private boolean titleOK(SpeechRecognition_Function function, int number) {
		return wordOK(function, Word_Type.title, number);
	}
	
	private boolean personalPronounOK(SpeechRecognition_Function function, int number) {
		return wordOK(function, Word_Type.personal_pronoun,  number);
	}

	private boolean infinitiveOK(SpeechRecognition_Function function) {
		return wordOK(function, Word_Type.infinitive, 0);
	}
	
	private boolean firstAuxVerb_haveOK (SpeechRecognition_Function function) {
		String word;
		word = wordList.get(indexWordList);

		if (word.equals("have")) {
			return wordOK(function, Word_Type.finitive, 0);
		}
		else if (word.equals("has")) {
			return wordOK(function, Word_Type.finitive, 0);
		}
		else if (word.equals("had")) {
			return wordOK(function, Word_Type.simple_past, 0);
		}
		else return false;
	}
	
	private boolean firstAuxVerb_willOK (SpeechRecognition_Function function) {
			return wordOK(function, Word_Type.finitive, 0);
	}
	
	private boolean firstAuxVerb_doOK (SpeechRecognition_Function function) {
		String word;
		word = wordList.get(indexWordList);

		if (word.equals("do")) {
			return wordOK(function, Word_Type.finitive, 0);
		}
		else if (word.equals("does")) {
			return wordOK(function, Word_Type.finitive, 0);
		}
		else if (word.equals("did")) {
			return wordOK(function, Word_Type.simple_past, 0);
		}
		else return false;

	}
	
	private boolean firstAuxVerb_beOK (SpeechRecognition_Function function) {
		String word;
		word = wordList.get(indexWordList);

		if (word.equals("am")) {
			return wordOK(function, Word_Type.finitive, 0);
		}
		else if (word.equals("are")) {
			return wordOK(function, Word_Type.finitive, 0);
		}
		else if (word.equals("is")) {
			return wordOK(function, Word_Type.finitive, 0);
		}
		else if (word.equals("was")) {
			return wordOK(function, Word_Type.simple_past, 0);
		}
		else if (word.equals("were")) {
			return wordOK(function, Word_Type.simple_past, 0);
		}
		
		else return false;

	}

	private boolean firstAuxVerb_restOK (SpeechRecognition_Function function) {
		String word;
		word = wordList.get(indexWordList);

		if (word.equals("would")) {
			return wordOK(function, Word_Type.finitive, 0);
		}
		else if (word.equals("can")) {
			return wordOK(function, Word_Type.finitive, 0);
		}
		else if (word.equals("could")) {
			return wordOK(function, Word_Type.finitive, 0);
		}
		else if (word.equals("shall")) {
			return wordOK(function, Word_Type.finitive, 0);
		}
		else if (word.equals("should")) {
			return wordOK(function, Word_Type.finitive, 0);
		}
		else return false;

	}

	private boolean beOK(SpeechRecognition_Function function) {
		String word;
		boolean isOK= false;
		
		word = wordList.get(indexWordList);
		
		if (function == SpeechRecognition_Function.secondAuxVerb_be)  {
			if (word.equals( "be")) 
			{ isOK = true;  isPassive=true; } 
		}
		else if (function == SpeechRecognition_Function.secondAuxVerb_been) {
			if (word.equals( "been")) { 
				isOK = true;  isPassive=true;}
		}
		else {
			if (word.equals ("am")) isOK = true; 
			else if (word.equals ("are")) isOK = true; 
			else if (word.equals( "is")) isOK = true;
			else if (word.equals( "was")) isOK = true;
			else if (word.equals( "were")) isOK = true; 
		}
		
		if (isOK) {
			functionList[indexWordList] = function;
			foundWordList[indexWordList] = null;
			indexWordList++;
		}
		return isOK;
	}

	private boolean pastParticipleOK(SpeechRecognition_Function function) {
		return wordOK(function, Word_Type.past_participle, 0);
	}

	private Auxiliary auxiliaryOK() {
		String word;
		
		word = wordList.get(indexWordList);
		
		
		if (word.equals( "will"))
				if (firstAuxVerb_willOK(SpeechRecognition_Function.firstAuxVerb_will) )	 return Auxiliary.will;
		if (word.equals( "would"))
				if (firstAuxVerb_restOK(SpeechRecognition_Function.firstAuxVerb_rest) )  return Auxiliary.would;
		if (word.equals ("can")) 
				if (firstAuxVerb_restOK(SpeechRecognition_Function.firstAuxVerb_rest) ) return Auxiliary.can;
		if (word.equals ("could")) 
				if (firstAuxVerb_restOK(SpeechRecognition_Function.firstAuxVerb_rest) ) return Auxiliary.could;
		if (word.equals ("do")) 
				if (firstAuxVerb_doOK(SpeechRecognition_Function.firstAuxVerb_do) ) return Auxiliary.do_;
		if (word.equals ("does")) 
				if (firstAuxVerb_doOK(SpeechRecognition_Function.firstAuxVerb_do) )  return Auxiliary.does;
		if (word.equals ("did")) 
				if (firstAuxVerb_doOK(SpeechRecognition_Function.firstAuxVerb_do) )  return Auxiliary.did;
		if (word.equals ("have")) 
				if (firstAuxVerb_haveOK(SpeechRecognition_Function.firstAuxVerb_have) ) return Auxiliary.have;
		if (word.equals ("has")) 
				if (firstAuxVerb_haveOK(SpeechRecognition_Function.firstAuxVerb_have) ) return Auxiliary.has;
		if (word.equals ("had")) 
				if (firstAuxVerb_haveOK(SpeechRecognition_Function.firstAuxVerb_have) ) return Auxiliary.had;
		if (word.equals ("shall")) 
				if (firstAuxVerb_restOK(SpeechRecognition_Function.firstAuxVerb_rest) ) return Auxiliary.shall;
		if (word.equals ("should")) 
				if (firstAuxVerb_restOK(SpeechRecognition_Function.firstAuxVerb_rest) ) return Auxiliary.should;
		if (word.equals ("is")) 
			if (firstAuxVerb_beOK(SpeechRecognition_Function.firstAuxVerb_be) ) return Auxiliary.is;
		if (word.equals ("am")) 
			if (firstAuxVerb_beOK(SpeechRecognition_Function.firstAuxVerb_be) ) return Auxiliary.am;
		if (word.equals ("are")) 
			if (firstAuxVerb_beOK(SpeechRecognition_Function.firstAuxVerb_be) ) return Auxiliary.are;
		if (word.equals ("was")) 
			if (firstAuxVerb_beOK(SpeechRecognition_Function.firstAuxVerb_be) ) return Auxiliary.was;
		if (word.equals ("were")) 
			if (firstAuxVerb_beOK(SpeechRecognition_Function.firstAuxVerb_be) ) return Auxiliary.were;
		return null;
	}
	
	private boolean goingToFutureOK(SpeechRecognition_Function function) {
		boolean isOK = false;
		int index_save;
		String word;
		
		index_save = indexWordList;
		word = wordList.get(indexWordList);
		if (word.equals("going_to")) { 
			isOK = wordOK(SpeechRecognition_Function.secondAuxVerb_goingto,Word_Type.infinitive, 0 );
		}
		
		if (isOK == false) {
			indexWordList = index_save;
		}
		
		return isOK;
	}
	
	private boolean finiteFormOK(SpeechRecognition_Function function) {
		boolean isOK;
		isOK = ( wordOK(function, Word_Type.finitive, 0) || wordOK(function, Word_Type.simple_past, 0)||beOK(function));
		return isOK;
	}

	private boolean personOK(SpeechRecognition_Function function, int number) {
		boolean isOK = false;
		
		isOK = titleOK(function, number);
		isOK = nameOK(function, number);
		
		return isOK;
	}

	private boolean itemOK(SpeechRecognition_Function function, int number) {
		boolean isOK = false;
		int index_save;
		String word;
		
		index_save = indexWordList;
		
		word = wordList.get(indexWordList);
		if (word.equals("the") || word.equals("an") || word.equals("a")) {
			functionList[indexWordList] = function;
			foundWordList[indexWordList] = null;
			indexWordList++;
			isOK = true;
		}
		else isOK = pronounOK(function, number);
		
		
		isOK = attributesOK(function, number);
		isOK = nounOK(function, number);

		if (isOK == false) {
			indexWordList = index_save;
		}
		return isOK;
	}
	
	private boolean andOK(SpeechRecognition_Function function) {
		String word;
		boolean isOK = false;
		
		word = wordList.get(indexWordList);
	
		if (word.equals("and")) {
			functionList[indexWordList] = function;
			foundWordList[indexWordList] = null;
			indexWordList++;
			isOK = true;
		}
		
		return isOK;

	}
	
	private boolean questionOK(SpeechRecognition_Function function) {
		return wordOK(function, Word_Type.question, 0);
	}
	

	private void writeWordsToList(String[] words) {
		int i;
		int count;
		
		count = words.length;
		for(i=0; i < count; i++) {
			wordList.add(i, words[i]);
		}

	}
	
	private  void setFinalPunctuationMark(String finalPunctuationMark) {
		switch (finalPunctuationMark)  {
		case ".": this.finalPunctuationMark = PunctuationMark.dot; break;
		case "?": this.finalPunctuationMark = PunctuationMark.question; break;
		case "!": this.finalPunctuationMark = PunctuationMark.exclamation; break;
		default: this.finalPunctuationMark = null;
		}
	}
	
	public PunctuationMark getFinalPunctuationMark() {
		
		return finalPunctuationMark;
	}
	
	private Word findAndGetWord(String word, Word_Type type) {
		
		Word element;
		
		element = AllWords.findAndGetWord(word, type);
		
		if (element != null) {
			wordTypeList[indexWordList] = type;
		}
				
		return element;
		
	}	
	
}
