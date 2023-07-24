package org.socialworld.datasource.pool;

import org.socialworld.core.EventType;

public class DescriptionIDs4EventType extends DescriptionIDs {

	private EventType eventType;

	DescriptionIDs4EventType(EventType eventType, int capacity) {
		super(eventType.getIndex(), capacity);
		this.eventType = eventType;
	}

	EventType getEventType() {
		return this.eventType;
	}

}
