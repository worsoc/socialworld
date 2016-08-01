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
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.knowledge.AnswerProperties;
import org.socialworld.knowledge.AnswerRelationUnaer;
import org.socialworld.knowledge.AnswerRelationBinaer;
import org.socialworld.knowledge.AnswerRelationTrinaer;
import org.socialworld.knowledge.IAnswer;
import org.socialworld.objects.Human;
import java.util.ArrayList;

public class Talk {
	private Human partner;
	private SentenceMaker sentenceMaker;
	private ArrayList<String> myPlannedSentences;
	private ArrayList<String> myPlannedQuestions;
	private ArrayList<String> partnersSentences;
	private ArrayList<String> partnersQuestions;
	
	public Talk(Human partner) {
		this.partner = partner;
		myPlannedSentences = new ArrayList<String>();
		myPlannedQuestions = new ArrayList<String>();
		partnersSentences = new ArrayList<String>();
		partnersQuestions = new ArrayList<String>();
	}
	
	public void addAnswer(IAnswer answer) {
		switch (answer.getType()) {
		case properties: 
			addSentence(makeAnswerSentence((AnswerProperties)answer), Talk_SentenceType.myPlannedSentence);
		case relationUnaer: 
			addSentence(makeAnswerSentence((AnswerRelationUnaer)answer), Talk_SentenceType.myPlannedSentence);
		case relationBinaer: 
			addSentence(makeAnswerSentence((AnswerRelationBinaer)answer), Talk_SentenceType.myPlannedSentence);
		case relationTrinaer: 
			addSentence(makeAnswerSentence((AnswerRelationTrinaer)answer), Talk_SentenceType.myPlannedSentence);
		}
	}

	private  String makeAnswerSentence(AnswerRelationUnaer answer) {
		String sentence;

		Word subject;
		Word verb;

		subject = answer.getAnswerSubject();
		verb = answer.getAnswerVerb();
		
		sentence = sentenceMaker.getStatementSentenceForRelationUnaer(subject, verb);
		
		return sentence;
	}

	private  String makeAnswerSentence(AnswerRelationBinaer answer) {
		String sentence;

		Word subject;
		Word verb;
		Word object;

		subject = answer.getAnswerSubject();
		verb = answer.getAnswerVerb();
		object = answer.getAnswerObject();
		
		sentence = sentenceMaker.getStatementSentenceForRelationBinaer(subject, verb, object);
		
		return sentence;
	}

	private  String makeAnswerSentence(AnswerRelationTrinaer answer) {
		String sentence;

		Word subject;
		Word verb;
		Word object1;
		Word object2;

		subject = answer.getAnswerSubject();
		verb = answer.getAnswerVerb();
		object1 = answer.getAnswerObject1();
		object2 = answer.getAnswerObject2();
		
		sentence = sentenceMaker.getStatementSentenceForRelationTrinaer(subject, verb, object1, object2);
		
		return sentence;
	}
	
	private String makeAnswerSentence(AnswerProperties answer) {
		String sentence;
		
		Word subject;
		KnowledgeFact_Criterion criterion;
		Word value;
		
		
	// TODO
		answer.resetIndex();
		subject = answer.getAnswerSubject();
		criterion = answer.getAnswerCriterion();
		value = answer.getAnswerValue();
		
		sentence = sentenceMaker.getStatementSentenceForFact(subject, criterion, value);
		
		return sentence;
	}
	
	public void addSentence(String sentence, Talk_SentenceType type) {
		switch (type) {
		case myPlannedSentence:
			myPlannedSentences.add(sentence);
		case myPlannedQuestion:
			myPlannedQuestions.add(sentence);
		case partnersSentence:
			partnersSentences.add(sentence);
		case partnersQuestion:
			partnersQuestions.add(sentence);
		default:
		}
		
	}
	

	public String getSentence(Talk_SentenceType type) {
		switch (type) {
		case myPlannedSentence:
			return getMyPlannedSentence();
		case myPlannedQuestion:
			return getMyPlannedQuestion();
		case partnersSentence:
			return getPartnersSentence();
		case partnersQuestion:
			return getPartnersQuestion();
		default:
			return null;
		}
	}
	
	private String getPartnersSentence() {
		if (partnersSentences.size() > 0)
			return  partnersSentences.remove(0);
		else
			return null;
	}

	private String getPartnersQuestion() {
		if (partnersQuestions.size() > 0)
			return  partnersQuestions.remove(0);
		else
			return null;
	}

	private String getMyPlannedSentence() {
		if (myPlannedSentences.size() > 0)
			return  myPlannedSentences.remove(0);
		else
			return null;
	}

	private String getMyPlannedQuestion() {
		if (myPlannedQuestions.size() > 0)
			return  myPlannedQuestions.remove(0);
		else
			return null;
	}

	public Human getPartner() {
		return partner;
	}
}
