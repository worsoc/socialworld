package org.socialworld.core;

import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.Time;
import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.attributes.percipience.PercipienceType;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.objects.Animal;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.properties.IPerceptible;

public class EventToPercipient extends Event implements IPerceptible {

	private Percipience percipience;
	

	
	/**
	 * Constructor
	 */
	public EventToPercipient(int eventType,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType,   causer,  time,  position,  performer);
		this.percipience = new Percipience(PercipienceType.event, 1000);

		
	}
	
	public EventToPercipient(EventType eventType,  SimulationObject causer, int priority, Position position) {

		super(eventType,   causer,    priority,	  position);
		this.percipience = new Percipience(PercipienceType.event, 1000);


	}
	
	public void setPercipience(float maxDistance, float maxSee, float maxHear, float maxSmell, float maxFeel ) {
		this.percipience  = new Percipience( PercipienceType.event, maxDistance,  maxSee,  maxHear,  maxSmell,  maxFeel );
	}

	public boolean checkIsEvent_LetMeBePerceived() {
		return this.getEventType().isLetMeBePerceivedEvent();
	}
	
	
	public final ValueArrayList getProperties() {
		
		ValueArrayList result;
		// TODO check, whether there are elements in param list
		if (hasOptionalParam()) {
			result = getOptionalParam().getParamList();
		}
		else {
			result = new ValueArrayList();
			result.add(new Value(Type.simulationObject, Value.VALUE_BY_NAME_EVENT_CAUSER, this.getCauser()));
			result.add(new Value(Type.simPropName, Value.VALUE_BY_NAME_EVENT_POSITION, this.getPosition()));
		}
		return result;
		
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    implementing IPerceptible     ////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public boolean checkIsPossiblePercipient(Animal possiblePercipient) {
		return this.percipience.checkIsPossibleSeer(possiblePercipient);
	}
	
	
	public boolean checkChanceToBeSeen(Animal possibleSeer) {
		return this.percipience.checkChanceToBeSeen(possibleSeer);
	}
	
	public boolean checkIsPossibleSeer(Animal possibleSeer) {
		return this.percipience.checkIsPossibleSeer(possibleSeer);
	}

}
