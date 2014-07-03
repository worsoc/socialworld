package org.socialworld.conversation;
import org.socialworld.knowledge.KnowledgeFactCriterion;

public class SentenceMaker {
	private Word lastSubject;
	
	public String getSentenceForFact(Word subject, KnowledgeFactCriterion criterion, Word value) {
		String sentence;
		
		if (lastSubject == subject)
			sentence = subject.getPronoun().getWord();
		else {
			sentence = subject.getWord();
			if (subject.getType() == WordType.noun) sentence = "The " + sentence;
			lastSubject = subject;
		}
		sentence = sentence + getPhraseForFact(criterion, value);
		
		return sentence;
	}
	
	private String getPhraseForFact(KnowledgeFactCriterion criterion, Word value){
		String phrase;
		
		switch (criterion) {
			case colour: phrase = " has " + value.getWord() + " colour.";
			case material: phrase = " consists of the material " + value.getWord() + ".";
			default: phrase = " is " + value.getWord() + ".";
		}
		return phrase;
	}
}
