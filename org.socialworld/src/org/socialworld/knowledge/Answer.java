package org.socialworld.knowledge;

import org.socialworld.conversation.Word;

public class Answer extends Knowledge {
	public Word getAnswerSubject() {
		return getSubject();
	}
	
	public KnowledgeFactCriterion getAnswerCriterion(int index) {
		return getFact(index).getCriterion();
	}
	
	public Word getAnswerValue(int index) {
		return getFact(index).getValue().getWord();
	}

	public KnowledgeSource getAnswerSource(int index) {
		return getSource(index);
	}

}
