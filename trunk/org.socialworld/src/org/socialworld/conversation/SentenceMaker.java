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
import org.socialworld.knowledge.AnswerProperty;
import org.socialworld.knowledge.AnswerRelationBinaer;
import org.socialworld.knowledge.AnswerRelationTrinaer;
import org.socialworld.knowledge.AnswerRelationUnaer;
import org.socialworld.knowledge.KnowledgeFact_Criterion;

public class SentenceMaker {
	private Word lastSubject;
	private int random = 0;

	String getStatementSentenceForRelationUnaer(AnswerRelationUnaer answer) {
		String sentence;

		Word subject;
		Word verb;

		subject = answer.getSubject();
		verb = answer.getVerb();

		if (lastSubject == subject)
			sentence = subject.getPronoun().getWord();
		else {
			sentence = subject.getWord();
			if (subject.getType() == Word_Type.noun) sentence = "The " + sentence;
			lastSubject = subject;
		}
		
		sentence = sentence + " " + verb.getWord() + "." ;
		
		return sentence;
		
	}	
	
	
	String getStatementSentenceForRelationBinaer(AnswerRelationBinaer answer) {
		String sentence;

		Word subject;
		Word verb;
		Word object;

		subject = answer.getSubject();
		verb = answer.getVerb();
		object = answer.getObject();
	
		if (lastSubject == subject)
			sentence = subject.getPronoun().getWord();
		else {
			sentence = subject.getWord();
			if (subject.getType() == Word_Type.noun) sentence = "The " + sentence;
			lastSubject = subject;
		}
		
		sentence = sentence + " " + verb.getWord() ;
		
		if (object != null) {
			if (object.getType() == Word_Type.noun) sentence = sentence + " the";
			sentence = sentence + " " + object.getWord();
		}
		
		sentence = sentence + ".";
		
		return sentence;
		
	}
	
	String getStatementSentenceForRelationTrinaer(AnswerRelationTrinaer answer) {
		String sentence;

		Word subject;
		Word verb;
		Word object1;
		Word object2;

		subject = answer.getSubject();
		verb = answer.getVerb();
		object1 = answer.getObject1();
		object2 = answer.getObject2();
	
		if (lastSubject == subject)
			sentence = subject.getPronoun().getWord();
		else {
			sentence = subject.getWord();
			if (subject.getType() == Word_Type.noun) sentence = "The " + sentence;
			lastSubject = subject;
		}
		
		sentence = sentence + " " + verb.getWord() ;
		
		if (object1 != null) {
			if (object1.getType() == Word_Type.noun) sentence = sentence + " the";
			sentence = sentence + " " + object1.getWord();
		}

		if (object2 != null) {
			if (object2.getType() == Word_Type.noun) sentence = sentence + " the";
			sentence = sentence + " " + object2.getWord();
		}
	
		sentence = sentence + ".";
		
		return sentence;
		
	}
	
	public String getStatementSentenceForFact(AnswerProperty answer) {
		String sentence;
	
		Word subject;
		KnowledgeFact_Criterion criterion;
		Word value;
		
		
	// TODO
		subject = answer.getSubject();
		criterion = answer.getAnswerCriterion();
		value = answer.getAnswerValue();
	
		if (lastSubject == subject)
			sentence = subject.getPronoun().getWord();
		else {
			sentence = subject.getWord();
			if (subject.getType() == Word_Type.noun) sentence = "The " + sentence;
			lastSubject = subject;
		}
		sentence = sentence + getStatementPhraseForFact(criterion, value);
		
		return sentence;
	}
	
	private String getStatementPhraseForFact(KnowledgeFact_Criterion criterion, Word value){
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
	
	public String getQuestionSentence(Word subject, KnowledgeFact_Criterion criterion) {
		String sentence;
		
		sentence = subject.getWord();
		if (subject.getType() == Word_Type.noun) sentence = "the " + sentence;
		
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
	
	private void calculateNewRandom(KnowledgeFact_Criterion criterion, int moduloRange) {
		random = (random + criterion.getIndex()) % moduloRange;
	}
	
}
