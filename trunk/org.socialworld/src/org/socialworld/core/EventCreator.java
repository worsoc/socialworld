package org.socialworld.core;

import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Time;
//import org.socialworld.calculation.ActionEventMapping;
import org.socialworld.calculation.ActionEventMappingDescription;
import org.socialworld.objects.SimulationObject;

public class EventCreator extends Semaphore {

	private static EventCreator creator;
	
	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private EventCreator() {
		locker = null;
	}
	
	/**
	 * The method gets back the only instance of the ActionCreator.
	 * 
	 * @return singleton object of ActionCreator
	 */
	public static EventCreator getInstance() {
		if (creator == null) {
			creator = new EventCreator();
		}
		return creator;
	}
	
	/**
	 * The method creates a new event for an action.
	 * The creation is saved by semaphore.
	 *  So only the object, that locked the event creator, can create the event object.	  
	 */
	public SemaphoreReturnCode createEvent(
			final SimulationObject actor,
			final Action action,
			final Object user) {
		
		if (this.locker == user) {
			
			createEvent(action, actor);
			
			return SemaphoreReturnCode.success;
		}
		if (this.locker == null)
			return SemaphoreReturnCode.notLockedByAnyone;
		return SemaphoreReturnCode.lockedByAnotherUser;
	}
	
	/**
	 * The method creates a new event.
	 * Therefore an according mapping description is used to set the event properties.
	 * 
	 */
	private void createEvent(final Action action,	final SimulationObject actor) {
		// TODO (tyloesand) implement the action event mapping
		ActionEventMappingDescription mappingDescription;
		ActionType actionType;
		ActionMode actionMode;
	
		
		Event event;
		
		int eventType = 1;
		SimulationObject causer;
		Direction direction;
		int strength;
		Time time;
		int priority;
		Position position;
		
		
		actionType = action.getType();
		actionMode = action.getMode();

		strength = (int) action.getIntensity();
		priority = action.getPriority();
		position = actor.getPosition();
		direction = action.getDirection();
		causer = actor;
		time = new Time();
		event = new Event( eventType,  priority,  causer,  time,  position,
				 direction,  strength, 
				 0,  0,
				 0,  0,  0,  0,  0);
	}
}
