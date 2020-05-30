package org.socialworld.objects.concrete;

import org.socialworld.attributes.SimPropertyName;
import org.socialworld.attributes.properties.NutrientProperty;
import org.socialworld.attributes.properties.TasteProperty;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.State;

public class StateEatable extends State {

	NutrientProperty nutrientProps;
	TasteProperty tasteProps;

	public StateEatable() {
		
	}
	
	private StateEatable(StateEatable original) {
		// TODO implement copy constructor
	}
	
	protected State copyForProperty(Type propertyType) {
		return new StateEatable(this);
	}

	public  ValueProperty getProperty(SimPropertyName prop, String name) {
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
