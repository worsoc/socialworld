package org.socialworld.objects.concrete.eatable.fruits;

import org.socialworld.objects.State;
import org.socialworld.objects.concrete.eatable.Fruit;
import org.socialworld.objects.properties.IEatable;
import org.socialworld.objects.properties.IThrowable;

public class Banana extends Fruit implements IEatable, IThrowable{

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IEatable ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public  float getTemperature() {return 22; };
	public  float getConsistence() {return 22; };
	public  float getFirmness() {return 22; };

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IThrowable ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public float getGrip() { return 15; }
	public float getMass()  { return 15; }
	public float getVolume()  { return 15; }
	public float getForm()  { return 15; }



	@Override
	protected int getLexemID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean checkObjectBelongsToGroup(short groupNumberSuffix) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected State getInitState(String stateClassName) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
