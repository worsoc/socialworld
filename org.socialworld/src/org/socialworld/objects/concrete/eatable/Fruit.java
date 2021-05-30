package org.socialworld.objects.concrete.eatable;

import java.util.List;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.properties.NutrientProperty;
import org.socialworld.attributes.properties.TasteProperty;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.objects.Item;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StateEatable;
import org.socialworld.objects.properties.IEatable;
import org.socialworld.tools.Generation;
import org.socialworld.tools.StringPair;

public abstract class Fruit extends Item implements IEatable {

	private StateEatable stateEatable;
	
///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringPair[] propertiesMetaInfos = new StringPair[]{
			new StringPair("stateEatable", PropertyName.stateEatable.name())
			} ;
	
	protected Fruit(Generation calledFromGeneration) {
		super(calledFromGeneration);
		listOfPropertyMetaInfo = super.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
	}
	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public Fruit() {
		super();
	}

	protected List<State> createAddOnStates() {
		
		List<State> result = super.createAddOnStates();
		
		this.stateEatable = (StateEatable) getInitState(StateEatable.class.getName());
		result.add(this.stateEatable);
		
		return result;
		
	}

///////////////////////////////////////////////////////////////////////////////////////////
//////////    checking whether the class belongs to a sub set of classes //////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	protected boolean isInterface(String nameInterface) {
	
		boolean result;
		
		switch (nameInterface) {
			case IEatable.NAME:
				result = (this instanceof IEatable);
				break;
			default:
				result = super.isInterface(nameInterface);

		}
		
		return  result;
	
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
