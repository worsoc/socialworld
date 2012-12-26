package org.socialworld.datasource;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.socialworld.calculation.EventInfluenceDescription;
import org.socialworld.core.Event;

public class EventInfluenceDescriptionPool {

	private static final Logger logger = Logger.getLogger(EventInfluenceDescriptionPool.class);
	private static EventInfluenceDescriptionPool instance;
	
	private static ArrayList<EventInfluenceDescription> descriptions;
	
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
		int countFill;
		int maxFill;
		
		EventInfluenceDescription description;
		
		index = eventType * Event.MAX_EVENT_TYPE + influenceType;
		
		if (descriptions.size() > index) {
			description = descriptions.get(index);
			if (description == null) {
				description = createDescription(eventType, influenceType);
				descriptions.set(index, description);
			}
		}	
		else {
			maxFill = index - descriptions.size();
			for ( countFill=1; countFill <= maxFill; countFill++) {
				description = null;
				descriptions.add(description);
			}
			
			description = createDescription(eventType, influenceType);
			descriptions.add(description);
		}
		return description;
	}

	private void initialize() {
	}
	

	
	private EventInfluenceDescription createDescription(int eventType,	int influenceType) {
		EventInfluenceDescription description = new	EventInfluenceDescription();
		
		return description;
	}

/*	private EventInfluenceDescription createDummyDescription(){
		EventInfluenceDescription description = new	EventInfluenceDescription();
		
		return description;
	}
*/
}
