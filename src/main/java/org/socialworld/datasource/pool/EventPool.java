package org.socialworld.datasource.pool;


import org.socialworld.core.Event;

public class EventPool {
	private static final int EVENT_ARRAY_INCREASE = 100;
	private static EventPool instance;
	
	private static Event events[];
	private int size = 0;
	private int nextFree = 0;
	
	private EventPool () {
		events = new Event[size];

		initialize();
	}
	
	public static EventPool getInstance() {
		if (instance == null) {
			instance = new EventPool();
		}
		return instance;
	}
	
	public Event getEvent(int index) {
		if (size > index) 	return events[index];
		else return null;
		// TODO NULL WEG
	}
	
	private void initialize() {
		
		addEvent(0,createEvent(1));
		addEvent(1, createEvent(2));
		addEvent(2, createEvent(3));
		nextFree = 3;
	}

	private Event createEvent(int number) {
		byte priority = 1;
		int eventType;
		Event event = null; 

		switch (number) {
		case 1:
			eventType = 1;
			break;
		default:
			eventType = 0;
		}
		
		//event = new Event(eventType, priority);
		return event;
	}

	


	private void addEvent(int index, Event event) {
		Event newEvents[];
		int newSize;
		
		if (index >= size) {
			newSize = index + EVENT_ARRAY_INCREASE;
			newEvents = new Event[newSize];
			for (int i = 0; i < size; i++) newEvents[i] = events[i];
			events = newEvents;
			size = newSize;
		}
		events[index] = event;
	}
}
