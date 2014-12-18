package org.socialworld.knowledge;

public class KnowledgeSource {
	KnowledgeSource_Type type = null;
	Acquaintance origin = null;
	
	public void setSourceType(KnowledgeSource_Type type) {
		this.type = type;
	}
	
	public void setOrigin(Acquaintance origin) {
		this.origin = origin;
	}
}
