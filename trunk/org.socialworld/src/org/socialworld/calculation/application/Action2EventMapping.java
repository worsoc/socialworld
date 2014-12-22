package org.socialworld.calculation.application;
import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.Time;
import org.socialworld.core.Action;
import org.socialworld.core.Event;
import org.socialworld.calculation.Vector;
import org.socialworld.objects.SimulationObject;

public class Action2EventMapping {
	
	
	public static Event createEvent(final Action action,	final SimulationObject actor) {
		Event event;
		
		// just for testing:
		if (actor.getObjectID() == 1) {
			event = new Event(1,  10,  actor,  new Time(1500),  new Position(new Vector(100,105,0)),
					 new Vector(0,1,0),  10, 
				 10,  45);
			return event;
		}
		
		// TODO (tyloesand) implement the action event mapping 
		// and the event property calculation (in private methods, see below)
		ActionType actionType;
		ActionMode actionMode;
		
		
		int eventType;
		SimulationObject causer;
		Vector direction;
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
	
	private static int mapActionToEventType(ActionMode actionMode, ActionType actionType ) {
		return 0;
	}
	
	private static int mapActionToIntensity(
			ActionMode actionMode, ActionType actionType, double intensity, SimulationObject actor) {
		return (int) intensity;
	}
	
	private static int mapActionToPriority(ActionMode actionMode, ActionType actionType, int priority ) {
		return priority;
	}

	private static Position calculateEventPosition(final Action action,	final SimulationObject actor ) {
		// getPosition() returns a copy of actor's position!
		Position eventposition = actor.getPosition();
		return eventposition;
	}

	private static Vector calculateEventDirection(final Action action,	final SimulationObject actor ) {
		Vector eventDirection = new Vector(action.getDirection());
		return eventDirection;
	}

	private static float calculateEffectDistance(final Action action,	final SimulationObject actor ) {
		return 123;
	}

	private static float calculateEffectAngle(final Action action,	final SimulationObject actor ) {
		return 12;
	}

}
