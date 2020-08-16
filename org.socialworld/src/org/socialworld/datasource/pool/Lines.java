package org.socialworld.datasource.pool;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.core.EventType;

public class Lines {

	private EventType eventType;
	private List<List<List<String>>> lines;
	
	// capacity is the range for the mapped type
	// for example: how many reaction types are possible for event type
	private int capacity;
	
	Lines(EventType eventType, int capacity) {
		this.eventType = eventType;
		this.capacity = capacity;
		this.lines = new ArrayList<List<List<String>>>();
		
		for (int i = 0; i < capacity; i++) {
			this.lines.add(new ArrayList<List<String>>());
		}
	}
		
	
	
	EventType getEventType() {
		return this.eventType;
	}
	
	int getCapacity() {
		return this.capacity;
	}
	
	List<List<String>> getLines(int mainIndex) {
		// main index ... the int value for the mapped type (reaction type, influence type, perception type ...)
		if (mainIndex > 0 & mainIndex < this.capacity)
			return this.lines.get(mainIndex);
		else
			return new ArrayList<List<String>>();
	}
	
	void add(int mainIndex, int nr, String line) {
		// main index ... the int value for the mapped type (reaction type, influence type, perception type ...)
		if (mainIndex >= 0 & mainIndex < this.capacity) {
			
			List<List<String>> forMainIndex = this.lines.get(mainIndex);
			
			if (nr >= 0 & nr >= forMainIndex.size()) {
				for (int i = forMainIndex.size(); i <= nr; i++) {
					forMainIndex.add(new ArrayList<String>());
				}
			}
			
			if (nr >= 0) {
				forMainIndex.get(nr).add(line);
			}
			
		}
	}
}
