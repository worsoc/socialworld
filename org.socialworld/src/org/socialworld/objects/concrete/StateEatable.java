package org.socialworld.objects.concrete;

import org.socialworld.attributes.properties.NutrientProperty;
import org.socialworld.attributes.properties.TasteProperty;
import org.socialworld.objects.State;

public class StateEatable extends State {

	NutrientProperty nutrientProps;
	TasteProperty tasteProps;

	public NutrientProperty getNutrientProperties() { return new NutrientProperty(nutrientProps); }
//	protected NutrientProperty getOriginalNutrientProperties() { return nutrientProps; }
	protected void setNutrientProperties(NutrientProperty changed) {  }
	
	public TasteProperty getTasteProperties() { return new TasteProperty(tasteProps); }
//	protected TasteProperty getOriginalTasteProperties() { return tasteProps; }
	protected void setTasteProperties(TasteProperty changed) {  }

}
