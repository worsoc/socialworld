package org.socialworld.knowledge;

import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Numerus;
import org.socialworld.conversation.Word;

public interface IAnswer {

	public KnowledgeFact_Type getType();

	public void setSubject(Lexem subject, Numerus numerus);
	public void setSource(KnowledgeSource source);
	
	public Word getSubject();
	public KnowledgeSource getSource();
}
