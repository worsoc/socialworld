package org.socialworld.conversation;
import org.socialworld.knowledge.KnowledgeFactCriterion;

public class SentenceMaker {
	private Word lastSubject;
	private int random = 0;
	
	public String getStatementSentenceForFact(Word subject, KnowledgeFactCriterion criterion, Word value) {
		String sentence;
		
		if (lastSubject == subject)
			sentence = subject.getPronoun().getWord();
		else {
			sentence = subject.getWord();
			if (subject.getType() == WordType.noun) sentence = "The " + sentence;
			lastSubject = subject;
		}
		sentence = sentence + getStatementPhraseForFact(criterion, value);
		
		return sentence;
	}
	
	private String getStatementPhraseForFact(KnowledgeFactCriterion criterion, Word value){
		String phrase;
		
		switch (criterion) {
			case colour: 
				calculateNewRandom(criterion, 1);
				switch (random) {
					case 0: phrase = " has " + value.getWord() + " colour.";
				}
					
			case material: 
				calculateNewRandom(criterion, 1);
				switch (random) {
					case 0: phrase = " is made of the material " + value.getWord() + ".";
				}
			default: phrase = " is " + value.getWord() + ".";
		}
		return phrase;
	}
	
	public String getQuestionSentence(Word subject, KnowledgeFactCriterion criterion) {
		String sentence;
		
		sentence = subject.getWord();
		if (subject.getType() == WordType.noun) sentence = "the " + sentence;
		
		switch (criterion) {
		case colour: 
			calculateNewRandom(criterion, 1);
			switch (random) {
				case 0: sentence = "What is the colour of " + sentence + "?";
			}
			
		case material: 
			calculateNewRandom(criterion, 1);
			switch (random) {
				case 0: sentence = "What material is " + sentence + " made of?";
			}

		default: sentence = null;
		}
		return sentence;
	}
	
	private void calculateNewRandom(KnowledgeFactCriterion criterion, int moduloRange) {
		random = (random + criterion.getIndex()) % moduloRange;
	}
	
}
