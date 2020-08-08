package org.socialworld.datasource.pool;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.core.EventType;

public class Strings4EventType {

	private EventType eventType;
	private List<String> lines;
	
	Strings4EventType(EventType eventType) {
		this.eventType = eventType;
		this.lines = new ArrayList<String>();
	}
		
	
	
	EventType getEventType() {
		return this.eventType;
	}
	
	List<String> getLines() {
		return this.lines;
	}
	
	void add(String line) {
		this.lines.add(line);
	}
}
