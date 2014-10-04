package org.socialworld.conversation;

import java.util.ArrayList;
import org.socialworld.knowledge.KnowledgeFactCriterion;
import org.socialworld.knowledge.KnowledgeFact;
import org.socialworld.knowledge.KnowledgeFactPool;

public class SpeechRecognition {
	private ArrayList<String> wordList;
	private Word[] foundWordList; //according to the word list above
	private SpeechRecognitionFunction[] functionList; //according to the word list above
	
	private int indexWordList;
	private int startSearchForCriterion = 0;
	private Word lastSubject;
	
	private PunctuationMark finalPunctuationMark;
	private WordSearchTree allWords;
	
	public SpeechRecognition() {
		allWords = new WordSearchTree();
		lastSubject= null;
		
	}
	
	
	public void analyseSentence(String sentence) {
		int count;
		
		count = divideIntoParts(sentence);
		
		foundWordList = new Word[count];
		functionList = new SpeechRecognitionFunction[count];
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
			if (functionList[index] == SpeechRecognitionFunction.subject) {
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
	
	public KnowledgeFactCriterion getCriterion() {
		KnowledgeFactCriterion criterion = null;
		KnowledgeFactCriterion tmpCriterion = null;
		for (int index = startSearchForCriterion; index < wordList.size(); index++) {
			tmpCriterion = foundWordList[index].getKnowledgeFactCriterion();
			if (tmpCriterion != null) {
				criterion = tmpCriterion;
				indexWordList = index;
				break;
			}
		}
		return criterion;
	}
	
	public KnowledgeFact getNextFact() {
		KnowledgeFact fact = null;
		KnowledgeFactCriterion criterion = null;
		SpeechRecognitionFunction function = null;
		Word word = null;
		
		criterion = getCriterion();
		
		if (criterion != null) {
			function = functionList[indexWordList];
			for (int index = 0; index < wordList.size(); index++) {
				if (functionList[index] == function) {
					word = foundWordList[index];
					break;
				}
			}
			if (word != null)	fact = KnowledgeFactPool.getInstance().find( criterion, word);
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
		
		isCorrect = titleOK(SpeechRecognitionFunction.title);
		
		switch (finalPunctuationMark) {
		case dot:
			isCorrect = subjectOK(SpeechRecognitionFunction.subject) &&
			verbOK(SpeechRecognitionFunction.verb) &&
			objectOK(SpeechRecognitionFunction.object1) && 
			objectOK(SpeechRecognitionFunction.object2); break;
		case question:
			withQuestionWord = questionOK(SpeechRecognitionFunction.questionWord);
			aux = auxiliaryOK();
			
			isCorrect = (aux != null) && 
					subjectOK(SpeechRecognitionFunction.subject) &&
					verbRestOK(SpeechRecognitionFunction.verb, aux) &&
					objectOK(SpeechRecognitionFunction.object1) && 
					objectOK(SpeechRecognitionFunction.object2); 
			
			if (withQuestionWord && !isCorrect  )
				isCorrect = beOK(SpeechRecognitionFunction.verb) && subjectOK(SpeechRecognitionFunction.subject);
			else if	(withQuestionWord && isCorrect  )
				prepositionOK(SpeechRecognitionFunction.object1);
			
			break;
		case exclamation:
			isCorrect = infinitiveOK(SpeechRecognitionFunction.verb) &&
			objectOK(SpeechRecognitionFunction.object1) && 
			objectOK(SpeechRecognitionFunction.object2); break;
			
		}
		
		return isCorrect;
	}
	
	private boolean subjectOK(SpeechRecognitionFunction function) {
		boolean isOK = false;
		isOK = itemOK(SpeechRecognitionFunction.subject) ||
				personOK(SpeechRecognitionFunction.subject) ||
				personalPronounOK(SpeechRecognitionFunction.subject);	
		return isOK;
	}

	private boolean verbOK(SpeechRecognitionFunction function) {
		boolean isOK = false;
		int index_save;
		String word;
		
		index_save = indexWordList;
		
		word = wordList.get(indexWordList);
		if (word == "have" || word == "has" || word == "had") {
			functionList[indexWordList] = SpeechRecognitionFunction.firstAuxVerb;
			foundWordList[indexWordList] = null;
			indexWordList++;
			word = wordList.get(indexWordList); 
			if (word == "been"){
				functionList[indexWordList] = SpeechRecognitionFunction.secondAuxVerb_be;
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
				functionList[indexWordList] = SpeechRecognitionFunction.secondAuxVerb_be;
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
	
	private boolean verbRestOK(SpeechRecognitionFunction function, Auxiliary aux) {
		boolean isOK = false;
		int index_save;
		String word;
		
		index_save = indexWordList;
		
		word = wordList.get(indexWordList);
		
		if ( (aux == Auxiliary.have) || (aux == Auxiliary.has) || (aux == Auxiliary.had) )
		{
			if ( word == "been" ) {
				functionList[indexWordList] = SpeechRecognitionFunction.secondAuxVerb_be;
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
					functionList[indexWordList] = SpeechRecognitionFunction.secondAuxVerb_be;
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
	
	private boolean objectOK(SpeechRecognitionFunction function) {
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

	private boolean wordOK(SpeechRecognitionFunction function, WordType type) {
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
	
	private boolean prepositionOK(SpeechRecognitionFunction function) {
		return wordOK(function, WordType.preposition);
	}

	private boolean nounOK(SpeechRecognitionFunction function) {
		return  wordOK(function, WordType.noun);
	}
	
	private boolean pronounOK(SpeechRecognitionFunction function) {
		boolean isOK;
		isOK = ( wordOK(function, WordType.possessive_pronoun) || wordOK(function, WordType.demonstrative_pronoun) );
		return isOK;
	}
	
	private boolean nameOK(SpeechRecognitionFunction function) {
		return wordOK(function, WordType.name);
	}
	
	private boolean titleOK(SpeechRecognitionFunction function) {
		return wordOK(function, WordType.title);
	}
	
	private boolean personalPronounOK(SpeechRecognitionFunction function) {
		return wordOK(function, WordType.personal_pronoun);
	}

	private boolean infinitiveOK(SpeechRecognitionFunction function) {
		return wordOK(function, WordType.infinitive);
	}

	private boolean beOK(SpeechRecognitionFunction function) {
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

	private boolean pastParticipleOK(SpeechRecognitionFunction function) {
		return wordOK(function, WordType.past_participle);
	}

	private Auxiliary auxiliaryOK() {
		String word;
		
		word = wordList.get(indexWordList);
		
		switch (word) {
		case "will": functionList[indexWordList] = SpeechRecognitionFunction.firstAuxVerb;
				indexWordList++; return Auxiliary.will;
		case "would": functionList[indexWordList] = SpeechRecognitionFunction.firstAuxVerb;
				indexWordList++; return Auxiliary.would;
		case "can": functionList[indexWordList] = SpeechRecognitionFunction.firstAuxVerb;
				indexWordList++; return Auxiliary.can;
		case "could": functionList[indexWordList] = SpeechRecognitionFunction.firstAuxVerb;
				indexWordList++; return Auxiliary.could;
		case "do": functionList[indexWordList] = SpeechRecognitionFunction.firstAuxVerb;
				indexWordList++; return Auxiliary.do_;
		case "does": functionList[indexWordList] = SpeechRecognitionFunction.firstAuxVerb;
				indexWordList++; return Auxiliary.does;
		case "did": functionList[indexWordList] = SpeechRecognitionFunction.firstAuxVerb;
				indexWordList++; return Auxiliary.did;
		case "have": functionList[indexWordList] = SpeechRecognitionFunction.firstAuxVerb;
				indexWordList++; return Auxiliary.have;
		case "has": functionList[indexWordList] = SpeechRecognitionFunction.firstAuxVerb;
				indexWordList++; return Auxiliary.has;
		case "had": functionList[indexWordList] = SpeechRecognitionFunction.firstAuxVerb;
				indexWordList++; return Auxiliary.had;
		case "shall": functionList[indexWordList] = SpeechRecognitionFunction.firstAuxVerb;
				indexWordList++; return Auxiliary.shall;
		case "should": functionList[indexWordList] = SpeechRecognitionFunction.firstAuxVerb;
				indexWordList++; return Auxiliary.should;
		}
		return null;
	}
	
	private boolean goingToFutureOK() {
		boolean isOK = false;
		int index_save;
		String word;
		
		index_save = indexWordList;
		if (beOK(SpeechRecognitionFunction.secondAuxVerb_goingto)) {
			word = wordList.get(indexWordList);
			if (word == "going") {
				functionList[indexWordList] = SpeechRecognitionFunction.secondAuxVerb_goingto;
				foundWordList[indexWordList] = null;
				indexWordList++;
				word = wordList.get(indexWordList);
				if (word == "to") {
					functionList[indexWordList] = SpeechRecognitionFunction.secondAuxVerb_goingto;
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
	
	private boolean finiteFormOK(SpeechRecognitionFunction function) {
		boolean isOK;
		isOK = ( wordOK(function, WordType.infinitive) || wordOK(function, WordType.finitive) );
		return isOK;
	}

	private boolean personOK(SpeechRecognitionFunction function) {
		boolean isOK = false;
		
		isOK = titleOK(function);
		isOK = nameOK(function);
		
		return isOK;
	}

	private boolean itemOK(SpeechRecognitionFunction function) {
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
	
	private boolean questionOK(SpeechRecognitionFunction function) {
		return wordOK(function, WordType.question);
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
