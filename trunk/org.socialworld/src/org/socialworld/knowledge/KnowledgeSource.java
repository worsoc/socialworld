package org.socialworld.knowledge;

public class KnowledgeSource {
	KnowledgeSourceType type = null;
	Acquaintance origin = null;
	
	public void setSourceType(KnowledgeSourceType type) {
		this.type = type;
	}
	
	public void setOrigin(Acquaintance origin) {
		this.origin = origin;
	}
}
