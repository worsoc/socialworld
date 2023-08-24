package org.socialworld.datasource.pool;


import org.socialworld.core.EventType;

public class Lines4EventType extends Lines {

	private EventType eventType;

	Lines4EventType(EventType eventType, int capacity) {
		super(eventType.getIndex(), capacity);
		this.eventType = eventType;
	}

	EventType getEventType() {
		return this.eventType;
	}

}
