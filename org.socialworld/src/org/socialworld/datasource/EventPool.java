package org.socialworld.datasource;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.socialworld.core.Event;
import org.socialworld.attributes.*;

public class EventPool {
	private static final Logger logger = Logger.getLogger(EventPool.class);
	private static EventPool instance;
	
	private static List<Event> events;
	
	private EventPool () {
		logger.debug("create EventPool");
		events = new ArrayList<Event> ();

		initialize();
	}
	
	public static EventPool getInstance() {
		if (instance == null) {
			instance = new EventPool();
		}
		return instance;
	}
	
	public Event getEvent(int index) {
		if (events.size() >= index) 
			return events.get(index);
		else {
			events.add(createEvent());
			return events.get(events.size());
		}
		
	}
	
	private void initialize() {
		events.add(createEvent());
	}

	private Event createEvent() {
		
		return getEvent();
	}

	private Event getEvent() {
		byte priority = 1;
		Event event = new Event(priority);

		int count = events.size();

		switch (count) {
		case 1:
			event.setDirection(new Direction(1,2,3));
			event.setEffectAngle(30);
			event.setEffectDistance(100);
			event.setEventType(1);
			event.setMaxDistance(200);
			event.setMaxSee(200);
			event.setMaxHear(100);
			event.setMaxSmell(0);
			event.setStrength(50);
			return event;
		default:
			event.setDirection(new Direction(3,2,1));
			event.setEffectAngle(33);
			event.setEffectDistance(121);
			event.setEventType(1);
			event.setMaxDistance(180);
			event.setMaxSee(150);
			event.setMaxHear(180);
			event.setMaxSmell(0);
			event.setStrength(66);
			return event;
		}
	}
	
}
