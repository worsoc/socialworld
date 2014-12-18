package org.socialworld.knowledge;
import org.socialworld.conversation.Word;


public class KnowledgeFactPool {
	
	private static KnowledgeFactPool instance;
	KnowledgeFactList[] factListsByCriterion;
	
	private KnowledgeFactPool() {
		factListsByCriterion = new KnowledgeFactList[KnowledgeFact_Criterion.NUMBER_OF_KNOWLEDGE_FACT_CRITERION];
		
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
	
	public KnowledgeFact find(KnowledgeFact_Criterion criterion, Word value) {
		KnowledgeFact fact;
		
		fact = factListsByCriterion[criterion.getIndex()].find(value);
		
		return fact;
	}
	
	private void createPool() {
	}
	
}
