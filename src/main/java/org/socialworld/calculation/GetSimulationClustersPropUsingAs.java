package org.socialworld.calculation;


class GetSimulationClustersPropUsingAs {

	
	private static PropertyUsingAs[] emptyPossibleUsingAs = {};
	private static PropertyUsingAs[] possibleUsingAs4ClusterAttributeArray = 
		{PropertyUsingAs.pathToAttributeArray, PropertyUsingAs.attributeArray
		};
	private static PropertyUsingAs[] possibleUsingAs4ClusterPosition = 
		{PropertyUsingAs.pathToPosition, PropertyUsingAs.position
		};
	private static PropertyUsingAs[] possibleUsingAs4ClusterAction = 
		{PropertyUsingAs.pathToAction, PropertyUsingAs.action,
		PropertyUsingAs.pathToActionAttack, PropertyUsingAs.actionAttack,
		PropertyUsingAs.pathToActionBodilyFunction, PropertyUsingAs.actionBodilyFunction,
		PropertyUsingAs.pathToActionHandle, PropertyUsingAs.actionHandle,
		PropertyUsingAs.pathToActionHear, PropertyUsingAs.actionHear,
		PropertyUsingAs.pathToActionMove, PropertyUsingAs.actionMove,
		PropertyUsingAs.pathToActionSay, PropertyUsingAs.actionSay
		};
	private static PropertyUsingAs[] possibleUsingAs4ClusterKnowledge = 
		{PropertyUsingAs.pathToKnowledgeSubject, PropertyUsingAs.knowledgeSubject,
		PropertyUsingAs.pathToKnowledgeSource, PropertyUsingAs.knowledgeSource,
		PropertyUsingAs.pathToKnowledgeValue, PropertyUsingAs.knowledgeValue,
		PropertyUsingAs.pathToKnowledgeProperty, PropertyUsingAs.knowledgeProperty,
		PropertyUsingAs.pathToKnowledgeRelationSubject, PropertyUsingAs.knowledgeRelationSubject,
		PropertyUsingAs.pathToKnowledgeRelationVerb, PropertyUsingAs.knowledgeRelationVerb,
		PropertyUsingAs.pathToKnowledgeRelationAdverb, PropertyUsingAs.knowledgeRelationAdverb,
		PropertyUsingAs.pathToKnowledgeRelationObject, PropertyUsingAs.knowledgeRelationObject
		};

	static PropertyUsingAs[] getPossibleUsingAs(SimulationCluster cluster) {
		
		PropertyUsingAs[] result;
		
		switch(cluster) {
		case total:
		case test:
		case toBeSet:
			result = possibleUsingAs4ClusterKnowledge;
			break;
		case attributeArray:
			result = possibleUsingAs4ClusterAttributeArray;
			break;
		case position:
			result = possibleUsingAs4ClusterPosition;
			break;
		case action:
			result = possibleUsingAs4ClusterAction;
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
