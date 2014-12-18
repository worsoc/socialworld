package org.socialworld.knowledge;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.objects.Human;

public class Acquaintance {
	private Human human;
	
	AttributeArray attributes;
	
	public Acquaintance(Human human){
		this.human = human;
		
		attributes = new AttributeArray(Acquaintance_Attribute.NUMBER_OF_ATTRIBUTES);
	}
	
	protected boolean isValid() {
		return (human != null);
	}
	
	public boolean isAcquaintanceOfHuman(Human human) {
		return (human == this.human);
	}
	
	public boolean isAttributeValueGreaterThan(Acquaintance_Attribute attribute, int value) {
		if (attributes.get(attribute.getIndex()) > value) return true;
		else return false;
	}

	public boolean isAttributeValueLessThan(Acquaintance_Attribute attribute, int value) {
		if (attributes.get(attribute.getIndex()) < value) return true;
		else return false;
	}

}
