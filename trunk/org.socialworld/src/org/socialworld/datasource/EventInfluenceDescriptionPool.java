package org.socialworld.datasource;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.socialworld.calculation.EventInfluenceDescription;
import org.socialworld.core.Event;

public class EventInfluenceDescriptionPool {

	private static final Logger logger = Logger.getLogger(EventInfluenceDescriptionPool.class);
	private static EventInfluenceDescriptionPool instance;
	
	private static List<EventInfluenceDescription> descriptions;
	
	private EventInfluenceDescriptionPool () {
		logger.debug("create EventInfluenceDescriptionPool");
		descriptions = new ArrayList<EventInfluenceDescription> ();

		initialize();
	}
	
	public static EventInfluenceDescriptionPool getInstance() {
		if (instance == null) {
			instance = new EventInfluenceDescriptionPool();
		}
		return instance;
	}
	
	public EventInfluenceDescription getDescription(int eventType,	int influenceType) {
		int index;
		EventInfluenceDescription description;
		
		index = eventType * Event.MAX_EVENT_TYPE + influenceType;
		
		if (descriptions.size() >= index) {
			description = descriptions.get(index);
			if (description == null) {
				description = createDescription(eventType, influenceType);
				descriptions.add(index, description);
			}
		}	
		else {
			description = createDescription(eventType, influenceType);
			descriptions.add(index, description);
		}
		return description;
	}

	private void initialize() {
	}
	

	private EventInfluenceDescription createDescription(int eventType,	int influenceType) {
		EventInfluenceDescription description = new	EventInfluenceDescription();
		
		return description;
	}

}
