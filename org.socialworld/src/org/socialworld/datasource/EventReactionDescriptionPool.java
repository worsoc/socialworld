package org.socialworld.datasource;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.socialworld.calculation.EventReactionDescription;
import org.socialworld.core.Event;

public class EventReactionDescriptionPool {

	private static final Logger logger = Logger.getLogger(EventReactionDescriptionPool.class);
	private static EventReactionDescriptionPool instance;
	
	private static ArrayList<EventReactionDescription> descriptions;
	
	private EventReactionDescriptionPool () {
		logger.debug("create EventReactionDescriptionPool");
		descriptions = new ArrayList<EventReactionDescription> ();

		initialize();
	}
	
	public static EventReactionDescriptionPool getInstance() {
		if (instance == null) {
			instance = new EventReactionDescriptionPool();
		}
		return instance;
	}
	
	public EventReactionDescription getDescription(int eventType,	int reactionType) {
		int index;
		int countFill;
		int maxFill;

		EventReactionDescription description;
		
		index = eventType * Event.MAX_EVENT_TYPE + reactionType;
		
		if (descriptions.size() >= index) {
			description = descriptions.get(index);
			if (description == null) {
				description = createDescription(eventType, reactionType);
				descriptions.set(index, description);
			}
		}	
		else {
			maxFill = index - descriptions.size();
			for ( countFill=1; countFill <= maxFill; countFill++) {
				description = null;
				descriptions.add(description);
			}
			description = createDescription(eventType, reactionType);
			descriptions.add(description);
		}
		return description;
	}

	private void initialize() {
	}
	

	private EventReactionDescription createDescription(int eventType,	int reactionType) {
		EventReactionDescription description = new	EventReactionDescription();
		
		return description;
	}

}
