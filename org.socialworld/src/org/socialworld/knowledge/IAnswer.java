package org.socialworld.knowledge;

import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Word;

public interface IAnswer {

	public KnowledgeFact_Type getType();

	public void changeSubject(Lexem subject);
	public void setSpeechRecognitionsSubjectWord(Word subject);

	public void changeSource(KnowledgeSource source);
	
	
	public Word getSubjectAsWord();
	public KnowledgeSource getSource();
}
