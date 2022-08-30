package org.socialworld.conversation;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.collections.ReadOnlyIterator;
import org.socialworld.knowledge.KnowledgeFact;
import org.socialworld.knowledge.KnowledgeFactPool;
import org.socialworld.knowledge.KnowledgeFact_Criterion;

public class SubjectOrObject {

	private SpeechRecognition_Function function;
	
	private List<Lexem> mains; 
	private int indexMains = 0;
	private List<List<Lexem>> attribsForMains;
	
	private ReadOnlyIterator<Lexem> iteratorMains;
	private List<Lexem> attribs;
	
	private List<KnowledgeFact_Criterion> handledCriteria;
	
	public SubjectOrObject(SpeechRecognition_Function function, SpeechRecognition sentence) {
		
		this.function = function;
		
		mains = sentence.getMains(function);
		attribsForMains = new ArrayList<List<Lexem>>(mains.size());

		for (int index  = 0; index < mains.size(); index++) {
			attribsForMains.set(index, sentence.getAttribs(function, index));
		}
		
		resetIteratorMains();
	}
	
	public SpeechRecognition_Function getFunction() {
		return this.function;
	}
	
	private void resetIteratorMains() {
		iteratorMains = new ReadOnlyIterator<Lexem>(mains.iterator());
		indexMains = -1;
	}

	private void resetIteratorAttribs() {
		attribs = attribsForMains.get(this.indexMains);
		handledCriteria = new ArrayList<KnowledgeFact_Criterion>();
	}

	public Lexem nextMain() {
		
		Lexem lexem = null;
		if (iteratorMains.hasNext()) {
			lexem = iteratorMains.next();
			indexMains++;
			resetIteratorAttribs();
		}
		return lexem;
				
	}
	
	public List<KnowledgeFact> getNextFacts() {
		
		KnowledgeFact_Criterion criterion = null;
		boolean handleCriterion = false;
		
		for (Lexem lexem : attribs) {
			
			ReadOnlyIterator<KnowledgeFact_Criterion> lexemsCriteria = lexem.getKnowledgeFact_Criterions();
			
			while (lexemsCriteria.hasNext()) {
				criterion = lexemsCriteria.next();
				if (!criterionAlreadyHandled(criterion)) {
					handledCriteria.add(criterion);
					handleCriterion = true;
					break;
				}
			}
			if (handleCriterion) break;
			
		}

		List<KnowledgeFact> facts = new ArrayList<KnowledgeFact>();
		
		if (handleCriterion) {
			
			List<Lexem> lexemsWithCriterion = new ArrayList<Lexem>();

			for (Lexem lexem : attribs) {
				
				if (lexemHasCriterion(lexem, criterion)) {
					lexemsWithCriterion.add(lexem);
				}
			}
			
			facts = KnowledgeFactPool.getInstance().findLexems(criterion , lexemsWithCriterion);
			
		}
				
		return facts;
	
	}

	private boolean criterionAlreadyHandled(KnowledgeFact_Criterion criterion) {
		
		for (KnowledgeFact_Criterion alreadyHandled : handledCriteria) {
			if (criterion.equals(alreadyHandled)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean lexemHasCriterion(Lexem lexem, KnowledgeFact_Criterion criterion) {
		
		ReadOnlyIterator<KnowledgeFact_Criterion> lexemsCriteria = lexem.getKnowledgeFact_Criterions();
		KnowledgeFact_Criterion lexemsCriterion;
		
		while (lexemsCriteria.hasNext()) {
			lexemsCriterion = lexemsCriteria.next();
			if (criterion.equals(lexemsCriterion)) {
				return true;
			}
		}
		return false;
	}

	
	//if (foundWordList[index].isAllowedAsKnowledgeSubject())	word = foundWordList[index];

} 
