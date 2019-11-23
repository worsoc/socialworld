package org.socialworld.objects.concrete.eatable;

import org.socialworld.attributes.properties.NutrientProperty;
import org.socialworld.attributes.properties.TasteProperty;
import org.socialworld.objects.Item;
import org.socialworld.objects.concrete.StateEatable;

public abstract class Fruit extends Item  {

	private StateEatable state;
	
	public Fruit(int objectID) {
		super(objectID);
	}
	
	public  NutrientProperty getNutrientProperties() { return this.state.getNutrientProperties(); }
	
	public  TasteProperty getTasteProperties() { return this.state.getTasteProperties(); }

}
