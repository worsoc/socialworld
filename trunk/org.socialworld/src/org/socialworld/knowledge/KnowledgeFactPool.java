package org.socialworld.knowledge;

public class KnowledgeFactPool {
	
	KnowledgeFactList[] factListsByCriterion;
	
	public KnowledgeFactPool() {
		factListsByCriterion = new KnowledgeFactList[KnowledgeFactCriterion.NUMBER_OF_KNOWLEDGE_FACT_CRITERION];
		
		createPool();
	}
	
	public KnowledgeFact find(KnowledgeFactCriterion criterion, int valueWordID) {
		KnowledgeFact fact;
		
		fact = factListsByCriterion[criterion.getIndex()].find(valueWordID);
		
		return fact;
	}
	
	private void createPool() {
	}
	
}
