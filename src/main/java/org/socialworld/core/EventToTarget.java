package org.socialworld.core;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.Time;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.objects.SimulationObject;

public class EventToTarget extends Event {

	/**
	 * Constructor
	 */
	public EventToTarget(int eventType,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType,   causer,  time,  position,	  performer);
		eventToTarget = true;
		
	}
	
	/**
	 * Constructor
	 */
	public EventToTarget(EventType eventType,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType.getIndex(),   causer,  time,  position,	  performer);
		eventToTarget = true;

	}

	/**
	 * Constructor
	 */
	public EventToTarget(int eventType, int priority,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType, priority,  causer,  time,  position,	  performer);
		eventToTarget = true;

	}

	public List<SimulationObject> getTargetObjects() {
		List<SimulationObject> targets;
		if  (this.hasOptionalParam()) {
			targets = ((ActionPerformer) getOptionalParam()).getTargets();
		}
		else {
			targets = new ArrayList<SimulationObject>();
		}
		return targets;
	}

	public final ValueArrayList getProperties() {
		
		return getOptionalParam().getParamList();
	}

}
