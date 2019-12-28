package org.socialworld.objects.concrete.eatable;

import java.util.List;

import org.socialworld.attributes.properties.NutrientProperty;
import org.socialworld.attributes.properties.TasteProperty;
import org.socialworld.objects.Item;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StateEatable;
import org.socialworld.objects.properties.IEatable;

public abstract class Fruit extends Item implements IEatable {

	private StateEatable stateEatable;
	
	
	public  NutrientProperty getNutrientProperties() { return this.stateEatable.getNutrientProperties(); }
	
	public  TasteProperty getTasteProperties() { return this.stateEatable.getTasteProperties(); }

	
	protected List<State> createAddOnStates() {
		
		List<State> result = super.createAddOnStates();
		
		System.out.println("Fruit.createAddOnStates");
		this.stateEatable = (StateEatable) getInitState(StateEatable.class.getName());
		result.add(this.stateEatable);
		
		return result;
		
	}

}
