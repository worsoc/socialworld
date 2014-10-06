package org.socialworld.knowledge;

import org.socialworld.conversation.Word;

public class Answer extends Knowledge {
	
	private int index;
	private int count;
	
	public boolean  resetIndex() {
		index = getIndexForFirstValid();
		if (index == -1) return false;
		
		count = count();
		return true;
	}
	
	public boolean next() {
		do	{
			index = index + 1;
			if (index == count) return false;
		}
		while (!isItemValid(index));
		return true;
	}
	
	public Word getAnswerSubject() {
		return getSubject();
	}
	
	public KnowledgeFactCriterion getAnswerCriterion() {
		if (index < count)		return getFact(index).getCriterion();
		else return null;
	}
	
	public Word getAnswerValue() {
		if (index < count)	return getFact(index).getValue().getWord();
		else return null;
	}

	public KnowledgeSource getAnswerSource() {
		if (index < count)	return getSource(index);
		else return null;
	}

	public void sortBySource() {
		
	}
	
	public void reduceToFactWithMaxAccessCount() {
		int count;
		int index;
		int indexWithMaxAccessCount;
		
		count = count();
		indexWithMaxAccessCount = getIndexForValidWithMaxAccessCount();
		for (index = count - 1; index >= 0; index--) {
			if (index != indexWithMaxAccessCount) removeItem(index);
		}
		
	}
	
	public void reduceToFactWithMinAccessCount() {
		int count;
		int index;
		int indexWithMinAccessCount;
		
		count = count();
		indexWithMinAccessCount = getIndexForValidWithMinAccessCount();
		for (index = count - 1; index >= 0; index--) {
			if (index != indexWithMinAccessCount) removeItem(index);
		}
		
	}

}
