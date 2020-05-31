package org.socialworld.calculation;


public enum PropertyUsingAs {
	
	dontCheck(0),
	pathToKnowledgeSubject(1), knowledgeSubject(2),
	pathToKnowledgeSource(11), knowledgeSource(12),
	pathToKnowledgeValue(21), knowledgeValue(22),
	pathToKnowledgeProperty(31), knowledgeProperty(32),
	pathToKnowledgeRelationSubject(41), knowledgeRelationSubject(42),
	pathToKnowledgeRelationVerb(51), knowledgeRelationVerb(52),
	pathToKnowledgeRelationAdverb(61), knowledgeRelationAdverb(62),
	pathToKnowledgeRelationObject(71), knowledgeRelationObject(72);

	private int index;

	private PropertyUsingAs(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

	public static PropertyUsingAs getName(int index) {
		for (PropertyUsingAs permission : PropertyUsingAs.values())
			if (permission.index == index)
				return permission;
		return dontCheck;
	}
	
	public PropertyUsingAs getPathPermission(PropertyUsingAs finalValuePermission) {
		return getName(finalValuePermission.index - 1);
	}
	
	public SimulationCluster getSimulationCluster() {
		
		if (this.index == 0) return SimulationCluster.unknown;
		else if (this.index < 100) return SimulationCluster.knowledge;
		else return SimulationCluster.unknown;
		
	}

}
