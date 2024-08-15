package org.socialworld.datasource.pool;

import org.socialworld.core.EventType;

public class Jsons4EventType extends Jsons {

	private EventType eventType;

	Jsons4EventType(EventType eventType, int capacity) {
		super(eventType.getIndex(), capacity);
		this.eventType = eventType;
	}

	EventType getEventType() {
		return this.eventType;
	}

}
