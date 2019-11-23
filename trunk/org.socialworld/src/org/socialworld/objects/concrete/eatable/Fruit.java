package org.socialworld.objects.concrete.eatable;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.properties.NutrientProperty;
import org.socialworld.attributes.properties.TasteProperty;
import org.socialworld.objects.Item;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StateEatable;
import org.socialworld.objects.properties.IEatable;

public abstract class Fruit extends Item implements IEatable {

	private StateEatable state;
	
	
	public  NutrientProperty getNutrientProperties() { return this.state.getNutrientProperties(); }
	
	public  TasteProperty getTasteProperties() { return this.state.getTasteProperties(); }

	
	protected List<State> createAddOnStates() {
		
		List<State> result = new ArrayList<State>();
		
		State addOnState;
		addOnState = new StateEatable();
		result.add(addOnState);
		
		return result;
		
	}

}
