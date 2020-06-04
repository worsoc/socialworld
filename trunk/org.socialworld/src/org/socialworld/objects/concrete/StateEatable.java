package org.socialworld.objects.concrete;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.properties.NutrientProperty;
import org.socialworld.attributes.properties.TasteProperty;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.State;

public class StateEatable extends State {

	NutrientProperty nutrientProps;
	TasteProperty tasteProps;
	
	public StateEatable() {
		super();
	}

	private StateEatable( StateEatable original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
		// TODO implement copy constructor
	}

	public State copyForProperty(SimulationCluster cluster) {
		return new StateEatable(this, getPropertyProtection(), cluster);
	}

	public  ValueProperty getProperty(SimulationCluster cluster, PropertyName prop, String name) {
		// TODO implement getProperty()
		return ValueProperty.getInvalid();
	}

	public NutrientProperty getNutrientProperties() { return new NutrientProperty(nutrientProps); }
//	protected NutrientProperty getOriginalNutrientProperties() { return nutrientProps; }
	protected void setNutrientProperties(NutrientProperty changed) {  }
	
	public TasteProperty getTasteProperties() { return new TasteProperty(tasteProps); }
//	protected TasteProperty getOriginalTasteProperties() { return tasteProps; }
	protected void setTasteProperties(TasteProperty changed) {  }

}
