package org.socialworld.knowledge;

import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Numerus;

public interface IAnswer {

	public KnowledgeType getType();

	
	public void setSubject(Lexem subject, Numerus numerus);
}
