package org.socialworld.objects.concrete.eatable;

import org.socialworld.attributes.properties.NutrientProperty;
import org.socialworld.attributes.properties.TasteProperty;
import org.socialworld.objects.properties.IDrinkable;

public class Water extends Liquid implements IDrinkable{

	
	@Override
	public float getTemperature() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getViscosity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public NutrientProperty getNutrientProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TasteProperty getTasteProperties() {
		// TODO Auto-generated method stub
		return null;
	}

}
