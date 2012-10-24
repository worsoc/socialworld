package org.socialworld.core;

import org.socialworld.objects.SimulationObject;
import org.socialworld.calculation.ActionEventMapping;

public class EventCreator extends Semaphore {

	private static EventCreator creator;
	private ActionEventMapping mapping;
	private Event event;
	
	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private EventCreator() {
		locker = null;
		mapping = ActionEventMapping.getInstance();
	}
	
	/**
	 * The method gets back the only instance of the EventCreator.
	 * 
	 * @return singleton object of EventCreator
	 */
	public static EventCreator getInstance() {
		if (creator == null) {
			creator = new EventCreator();
		}
		return creator;
	}

	/**
	 * If the caller is the owner of the semaphore (that means the owner of the event object),
	 * the method returns the last created event object.
	 * In other cases it returns null.
	 * 
	 * It is assumed, that the caller locks the semaphore 
	 * and that the method createEvent() has been called before the method getEvent() is called().
	 */
	public Event getEvent(final Object user) {
		if (this.locker == user) 
			return this.event;
		else
			return null;
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
		event = mapping.createEvent(action, actor);
	}
	
	
}
