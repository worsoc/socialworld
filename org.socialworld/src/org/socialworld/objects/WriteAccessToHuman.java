package org.socialworld.objects;

import org.socialworld.attributes.Inventory;

public class WriteAccessToHuman extends WriteAccessToAnimal {

	private  Human human;
	
	public WriteAccessToHuman(Human human) {
		super(human);
		this.human = human;
	}
	
	public void setInventory(Inventory inventory, Object caller) {
		if (caller instanceof IHumanWrite) human.setInventory(inventory, this);
	}

}
