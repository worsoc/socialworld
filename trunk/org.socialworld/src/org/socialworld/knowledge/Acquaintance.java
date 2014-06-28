package org.socialworld.knowledge;
import org.socialworld.objects.Human;

public class Acquaintance {
	private Human human;
	
	protected boolean isValid() {
		return (human != null);
	}
	
	public boolean isAcquaintanceOfHuman(Human human) {
		return (human == this.human);
	}
}
