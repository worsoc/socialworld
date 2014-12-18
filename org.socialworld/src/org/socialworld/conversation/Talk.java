package org.socialworld.conversation;
import org.socialworld.knowledge.KnowledgeFactCriterion;
import org.socialworld.knowledge.Answer;
import org.socialworld.objects.Human;
import java.util.ArrayList;

public class Talk {
	private Human partner;
	private SentenceMaker sentenceMaker;
	private ArrayList<String> partnersSentences;
	private ArrayList<String> partnersQuestions;
	private ArrayList<String> myPlannedSentences;
	private ArrayList<String> myPlannedQuestions;
	
	public Talk() {
		partnersSentences = new ArrayList<String>();
		partnersQuestions = new ArrayList<String>();
		myPlannedSentences = new ArrayList<String>();
		myPlannedQuestions = new ArrayList<String>();
	}
	
	public String makeAnswerSentence(Answer answer) {
		String sentence;
		
		Word subject;
		KnowledgeFactCriterion criterion;
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
	
	public PunctuationMark getPunctuationMark(String sentence) {
		PunctuationMark returnValue = null;
		if (sentence.contains("?")) {
			returnValue = PunctuationMark.question;
		}
		else {
			returnValue = PunctuationMark.dot;
		}
		return returnValue;
		
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
