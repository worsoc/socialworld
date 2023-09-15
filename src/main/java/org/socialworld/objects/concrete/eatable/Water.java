package org.socialworld.objects.concrete.eatable;

import org.socialworld.attributes.properties.NutrientSet;
import org.socialworld.attributes.properties.TasteSet;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.enums.EnumLiquid;
import org.socialworld.objects.properties.IDrinkable;

public class Water extends Liquid implements IDrinkable{

///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public static int getLexemIdLowerValue() {return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_WATER; }
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public Water() {
		super();
		belongsTo = EnumLiquid.Water;
	}
	
	
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
	public NutrientSet getNutrientSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TasteSet getTasteSet() {
		// TODO Auto-generated method stub
		return null;
	}

}
