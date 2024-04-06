package org.socialworld.knowledge;

import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Word;

public class NoKnowledgeItem extends KnowledgeItem implements IAnswer{

	private static NoKnowledgeItem itemNothing;
	
	public final static NoKnowledgeItem getObjectNothing() {
		if (itemNothing == null) {
			itemNothing = new NoKnowledgeItem();
		}
		return itemNothing;
	}
	
	@Override
	KnowledgeItem getCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	KnowledgeItemNotes removeNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isEqual(KnowledgeItem item) {
		// TODO Auto-generated method stub
		return false;
	}

///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////// implementing IAnswer ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public KnowledgeFact_Type getType() { return KnowledgeFact_Type.property; }
	
	public void changeSubject(Lexem subject) {	}
	
	public Word getSubjectAsWord() { return Word.getNoWord(); }

	public void setSpeechRecognitionsSubjectWord(Word subject) {}
	
	
}
