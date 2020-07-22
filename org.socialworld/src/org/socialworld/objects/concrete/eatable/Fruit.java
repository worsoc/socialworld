package org.socialworld.objects.concrete.eatable;

import java.util.List;

import org.socialworld.attributes.properties.NutrientProperty;
import org.socialworld.attributes.properties.TasteProperty;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.objects.Item;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StateEatable;
import org.socialworld.objects.properties.IEatable;

public abstract class Fruit extends Item implements IEatable {

	private StateEatable stateEatable;
	
	
	protected List<State> createAddOnStates() {
		
		List<State> result = super.createAddOnStates();
		
		this.stateEatable = (StateEatable) getInitState(StateEatable.class.getName());
		result.add(this.stateEatable);
		
		return result;
		
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    implementing IEatable     ////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public  NutrientProperty getNutrientProperties() {
		return (NutrientProperty) 	this.stateEatable.getPropertyFromMethod(SimulationCluster.todo, StateEatable.METHODNAME_GET_NUTRIENT_PROPERTIES, StateEatable.VALUENAME_NUTRIENT_PROPERTIES).getValue();
	}
	
	public  TasteProperty getTasteProperties() {
		return (TasteProperty) 	this.stateEatable.getPropertyFromMethod(SimulationCluster.todo, StateEatable.METHODNAME_GET_TASTE_PROPERTIES, StateEatable.VALUENAME_TASTE_PROPERTIES).getValue();
	}

	

}
