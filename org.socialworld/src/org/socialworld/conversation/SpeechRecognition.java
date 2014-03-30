package org.socialworld.conversation;

import java.util.ArrayList;

public class SpeechRecognition {
	private ArrayList<String> wordList;
	private ArrayList<SpeechRecognitionFunction> functionList; //according to the word list above
	private int[] wordIDList; //according to the word list above
	private int indexWordList;
	
	private PunctuationMark finalPunctuationMark;
	private WordSearchTree allWords;
	
	public SpeechRecognition() {
		allWords = new WordSearchTree();
	}
	
	
	public void analyseSentence(String sentence) {
		divideIntoParts(sentence);
		if (checkCorrectness()){
			
		}
	}
	
	private void divideIntoParts(String sentence)  {
		String words[];
		
		sentence.replaceAll(",", " , ");
		
		words = sentence.split(" ");
		writeWordsToList(words);
		
		setFinalPunctuationMark(words[words.length - 1]);
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
			functionList.set(indexWordList, SpeechRecognitionFunction.firstAuxVerb);
			wordIDList[indexWordList] = 0;
			indexWordList++;
			word = wordList.get(indexWordList); 
			if (word == "been"){
				functionList.set(indexWordList, SpeechRecognitionFunction.secondAuxVerb_be);
				wordIDList[indexWordList] = 0;
				indexWordList++;
				isOK = pastParticipleOK(function);
			}
			else isOK = pastParticipleOK(function);
		}
		else if (goingToFutureOK()) isOK = infinitiveOK(function);
		else if (auxiliaryOK() != null)  {
			word = wordList.get(indexWordList);
			if (word == "be") {
				functionList.set(indexWordList, SpeechRecognitionFunction.secondAuxVerb_be);
				wordIDList[indexWordList] = 0;
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
				functionList.set(indexWordList, SpeechRecognitionFunction.secondAuxVerb_be);
				wordIDList[indexWordList] = 0;
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
					functionList.set(indexWordList, SpeechRecognitionFunction.secondAuxVerb_be);
					wordIDList[indexWordList] = 0;
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
			functionList.set(indexWordList, function);
			wordIDList[indexWordList] = 0;
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
		
		word = wordList.get(indexWordList);
		
		isOK = (allWords.checkExistsWord(word) == type);
		if (isOK) {
			functionList.set(indexWordList, function);
			wordIDList[indexWordList] = allWords.getWordID();
			indexWordList++;
		}
		
		return isOK;
	}
	
	private boolean prepositionOK(SpeechRecognitionFunction function) {
		return wordOK(function, WordType.preposition);
	}

	private boolean nounOK(SpeechRecognitionFunction function) {
		return wordOK(function, WordType.noun);
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
			functionList.set(indexWordList, function);
			wordIDList[indexWordList] = 0;
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
		case "will": functionList.set(indexWordList, SpeechRecognitionFunction.firstAuxVerb);
				indexWordList++; return Auxiliary.will;
		case "would": functionList.set(indexWordList, SpeechRecognitionFunction.firstAuxVerb);
				indexWordList++; return Auxiliary.would;
		case "can": functionList.set(indexWordList, SpeechRecognitionFunction.firstAuxVerb);
				indexWordList++; return Auxiliary.can;
		case "could": functionList.set(indexWordList, SpeechRecognitionFunction.firstAuxVerb);
				indexWordList++; return Auxiliary.could;
		case "do": functionList.set(indexWordList, SpeechRecognitionFunction.firstAuxVerb);
				indexWordList++; return Auxiliary.do_;
		case "does": functionList.set(indexWordList, SpeechRecognitionFunction.firstAuxVerb);
				indexWordList++; return Auxiliary.does;
		case "did": functionList.set(indexWordList, SpeechRecognitionFunction.firstAuxVerb);
				indexWordList++; return Auxiliary.did;
		case "have": functionList.set(indexWordList, SpeechRecognitionFunction.firstAuxVerb);
				indexWordList++; return Auxiliary.have;
		case "has": functionList.set(indexWordList, SpeechRecognitionFunction.firstAuxVerb);
				indexWordList++; return Auxiliary.has;
		case "had": functionList.set(indexWordList, SpeechRecognitionFunction.firstAuxVerb);
				indexWordList++; return Auxiliary.had;
		case "shall": functionList.set(indexWordList, SpeechRecognitionFunction.firstAuxVerb);
				indexWordList++; return Auxiliary.shall;
		case "should": functionList.set(indexWordList, SpeechRecognitionFunction.firstAuxVerb);
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
				functionList.set(indexWordList, SpeechRecognitionFunction.secondAuxVerb_goingto);
				wordIDList[indexWordList] = 0;
				indexWordList++;
				word = wordList.get(indexWordList);
				if (word == "to") {
					functionList.set(indexWordList, SpeechRecognitionFunction.secondAuxVerb_goingto);
					wordIDList[indexWordList] = 0;
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
			functionList.set(indexWordList, function);
			wordIDList[indexWordList] = 0;
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
