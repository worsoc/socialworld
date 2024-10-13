package org.socialworld.objects.concrete.animals.birds.aequorlithornithes;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.core.IAccessToken;
import org.socialworld.objects.concrete.animals.IFlying;
import org.socialworld.objects.concrete.animals.IRunning;
import org.socialworld.objects.concrete.animals.StateFlying;
import org.socialworld.objects.concrete.animals.StateRunning;
import org.socialworld.objects.concrete.animals.birds.Aequorlithornithes;
import org.socialworld.objects.enums.EnumBird;



public class Stork extends Aequorlithornithes implements IFlying , IRunning{

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public Stork() {
		super();
		belongsTo = EnumBird.Stork;
	}
	
	// add on states
	private StateFlying stateFlying = StateFlying.getObjectNothing();
	private StateRunning stateRunning = StateRunning.getObjectNothing();
	

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IFlying ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public  float getWidthWings() {return 30; };
	public  float getNumberWings() {return 2; };
	public Direction getDirectionFly() {
		return new Direction(PropertyName.stateFlying_directionFly , new Vector(1.2F, 1.3F, 1.4F), 30.5F );
	}

	public StateFlying getSavedStateFlying(IAccessToken token) {
		// make a copy as ValueProperty
		ValueProperty vp = this.stateFlying.getAsValue(token);
		// the copy is permitted for cluster only
		return objectRequester.requestStateFlyng(token, vp, this);
	}
	
	public ValueProperty getStateFlyingAsProperty(IAccessToken token, String name) {
		return this.stateFlying.getAsValue(token, name);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IRunning ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public  float getSpeed() {return 5; };
	public  float getNumberLegs() {return 2; };
	public Direction getDirectionRun() {
		return new Direction(PropertyName.stateRunning_directionRun , new Vector(1.3F, 1.4F, 1.5F),30.6F);
	}
	
	
	public StateRunning getSavedStateRunning(IAccessToken token) {
	// make a copy as ValueProperty
	ValueProperty vp = this.stateRunning.getAsValue(token);
	// the copy is permitted for cluster only
	return objectRequester.requestStateRunning(token, vp, this);
	}
	
	public ValueProperty getStateRunningAsProperty(IAccessToken token, String name) {
	return this.stateRunning.getAsValue(token, name);
	}



}
