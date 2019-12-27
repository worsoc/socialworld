package org.socialworld.objects.concrete.eatable.fruits;

import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StateEatable;
import org.socialworld.objects.concrete.eatable.Fruit;
import org.socialworld.objects.properties.IThrowable;

public class Apple extends Fruit implements IThrowable {


	
	public  float getTemperature() {return 20; };
	public  float getConsistence() {return 20; };
	public  float getFirmness() {return 20; };
	

	public float getGrip() { return 20; }
	public float getMass()  { return 20; }
	public float getVolume()  { return 20; }
	public float getForm()  { return 20; }

	
	protected State getInitState(String stateClassName) {
		switch (stateClassName) {
		case "StateEatable": 
			return new StateEatable();
		}
		
		return null;
		
	}
}
