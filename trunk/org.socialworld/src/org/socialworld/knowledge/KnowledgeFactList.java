package org.socialworld.knowledge;

import java.util.ArrayList;

public class KnowledgeFactList  {
	
	private ArrayList<KnowledgeFact> factSearchList;
	
	public KnowledgeFactList() {
		factSearchList = new ArrayList<KnowledgeFact>();
	}
	
	public KnowledgeFact find(int  valueWordID) {
		KnowledgeFact fact;
		KnowledgeFact temp;
		
		int index = 0;
		int count;
		
		boolean found;
		
		count = factSearchList.size();
		
		// finding the element with the specified value wordID
		do
		{
			fact = factSearchList.get(index);
			index++;
			found =  ( fact.getValue().getWordID() == valueWordID );
		}
		while( found  | (index == count) );
		
		index--;
		
		// exchange the element for the previous one
		if ( found && index > 0)  {
			
			// the previous list element
			temp = factSearchList.get(index - 1);
			
			factSearchList.set(index - 1, fact);
			factSearchList.set(index , temp);
			
		}
		
		if (!found) fact = null;
		
		return fact;
	}
	
	public void add (KnowledgeFact fact) {
		factSearchList.add(fact);
	}
}
