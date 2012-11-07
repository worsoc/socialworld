package org.socialworld.calculation;
import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.Time;
import org.socialworld.core.Action;
import org.socialworld.core.Event;
import org.socialworld.objects.SimulationObject;

public class Action2EventMapping {
	private static Action2EventMapping mapping;
	
	private Action2EventMapping() {
		
	}
	
	/**
	 * The method gets back the only instance of the Action2EventMapping.
	 * 
	 * @return singleton object of Action2EventMapping
	 */
	public static Action2EventMapping getInstance() {
		if (mapping == null) mapping = new Action2EventMapping();
		return mapping;
	}
	
	public Event createEvent(final Action action,	final SimulationObject actor) {
		Event event;
		
		// just for testing:
		if (actor.getObjectID() == 1) {
			event = new Event(1,  10,  actor,  new Time(1500),  new Position(100,105,0),
				 new Direction(0,1,0),  10, 
				 10,  45);
			return event;
		}
		
		// TODO (tyloesand) implement the action event mapping 
		// and the event property calculation (in private methods, see below)
		ActionType actionType;
		ActionMode actionMode;
		
		
		int eventType;
		SimulationObject causer;
		Direction direction;
		int strength;
		Time time;
		int priority;
		Position position;
		float effectDistance;
		float effectAngle;		
		
		actionType = action.getType();
		actionMode = action.getMode();
				
		eventType = mapActionToEventType(actionMode, actionType );
		strength = mapActionToIntensity(actionMode, actionType, action.getIntensity(), actor);
		priority = mapActionToPriority(actionMode, actionType, action.getPriority());
		position = calculateEventPosition(action, actor);
		direction = calculateEventDirection(action, actor);
		causer = actor;
		time = new Time();
		effectDistance = calculateEffectDistance(action, actor);
		effectAngle = calculateEffectAngle(action, actor);
		
		event = new Event( eventType,  priority,  causer,  time,  position,
				 direction,  strength, 
				 effectDistance,  effectAngle);

		return event;
	}
	
	private int mapActionToEventType(ActionMode actionMode, ActionType actionType ) {
		return 0;
	}
	
	private int mapActionToIntensity(
			ActionMode actionMode, ActionType actionType, double intensity, SimulationObject actor) {
		return (int) intensity;
	}
	
	private int mapActionToPriority(ActionMode actionMode, ActionType actionType, int priority ) {
		return priority;
	}

	private Position calculateEventPosition(final Action action,	final SimulationObject actor ) {
		// getPosition() returns a copy of actor's position!
		Position eventposition = actor.getPosition();
		return eventposition;
	}

	private Direction calculateEventDirection(final Action action,	final SimulationObject actor ) {
		Direction eventDirection = new Direction(action.getDirection());
		return eventDirection;
	}

	private float calculateEffectDistance(final Action action,	final SimulationObject actor ) {
		return 123;
	}

	private float calculateEffectAngle(final Action action,	final SimulationObject actor ) {
		return 12;
	}

}
