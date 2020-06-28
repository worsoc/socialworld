package org.socialworld.calculation;


class GetSimulationClustersPropUsingAs {

	
	private static PropertyUsingAs[] emptyPossibleUsingAs = {};
	private static PropertyUsingAs[] possibleUsingAs4ClusterKnowledge = 
		{PropertyUsingAs.pathToKnowledgeSubject, PropertyUsingAs.knowledgeSubject,
		PropertyUsingAs.pathToKnowledgeSource, PropertyUsingAs.knowledgeSource,
		PropertyUsingAs.pathToKnowledgeValue, PropertyUsingAs.knowledgeValue,
		PropertyUsingAs.pathToKnowledgeProperty, PropertyUsingAs.knowledgeProperty,
		PropertyUsingAs.pathToKnowledgeRelationSubject, PropertyUsingAs.knowledgeRelationSubject,
		PropertyUsingAs.pathToKnowledgeRelationVerb, PropertyUsingAs.knowledgeRelationVerb,
		PropertyUsingAs.pathToKnowledgeRelationAdverb, PropertyUsingAs.knowledgeRelationAdverb,
		PropertyUsingAs.pathToKnowledgeRelationObject, PropertyUsingAs.knowledgeRelationObject};

	static PropertyUsingAs[] getPossibleUsingAs(SimulationCluster cluster) {
		
		PropertyUsingAs[] result;
		
		switch(cluster) {
		case total:
		case todo:
		case test:
		case toBeSet:
			result = possibleUsingAs4ClusterKnowledge;
			break;
		case knowledge:
			result = possibleUsingAs4ClusterKnowledge;
			break;
		default:
			result = emptyPossibleUsingAs;
			
		}
		
		return result;
	}

}
