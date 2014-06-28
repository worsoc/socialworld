package org.socialworld.knowledge;


public class KnowledgeFactPool {
	
	private static KnowledgeFactPool instance;
	KnowledgeFactList[] factListsByCriterion;
	
	private KnowledgeFactPool() {
		factListsByCriterion = new KnowledgeFactList[KnowledgeFactCriterion.NUMBER_OF_KNOWLEDGE_FACT_CRITERION];
		
		createPool();
	}

	/**
	 * The method gets back the only instance of the KnowledgeFactPool.
	 * 
	 * @return singleton object of KnowledgeFactPool
	 */
	public static KnowledgeFactPool getInstance() {
		if (instance == null) instance = new KnowledgeFactPool();
		return instance;
	}
	
	public KnowledgeFact find(KnowledgeFactCriterion criterion, int valueWordID) {
		KnowledgeFact fact;
		
		fact = factListsByCriterion[criterion.getIndex()].find(valueWordID);
		
		return fact;
	}
	
	private void createPool() {
	}
	
}
