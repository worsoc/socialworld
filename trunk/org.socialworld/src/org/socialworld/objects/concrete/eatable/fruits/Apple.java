package org.socialworld.objects.concrete.eatable.fruits;

import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.attributes.percipience.PercipienceType;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StateEatable;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.eatable.Fruit;
import org.socialworld.objects.properties.IThrowable;

public class Apple extends Fruit implements IThrowable {


	protected int getLexemID() {
		// TODO set lexemID
		return 0;
	}

	public  float getTemperature() {return 20; };
	public  float getConsistence() {return 20; };
	public  float getFirmness() {return 20; };
	

	public float getGrip() { return 20; }
	public float getMass()  { return 20; }
	public float getVolume()  { return 20; }
	public float getForm()  { return 20; }

	
	protected State getInitState(String stateClassName) {
		if (stateClassName.equals(StatePerceptible.class.getName())) {
			Percipience percipience = new Percipience(PercipienceType.simobject, 100);
			return new StatePerceptible(percipience);
		}
		else if (stateClassName.equals(StateEatable.class.getName())) {
			return new StateEatable();
		}
		
		return null;
		
	}
}
