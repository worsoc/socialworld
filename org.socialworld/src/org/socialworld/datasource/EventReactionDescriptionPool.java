package org.socialworld.datasource;

import org.socialworld.calculation.descriptions.EventReactionDescription;
import org.socialworld.core.Event;

public class EventReactionDescriptionPool extends DescriptionPool {

	private static EventReactionDescriptionPool instance;
	
	private static EventReactionDescription descriptions[];
	
	private EventReactionDescriptionPool () {
		
		sizeDescriptionsArray = Event.MAX_EVENT_TYPE * ReactionTypePool.CAPACITY_RTP_ARRAY;
		descriptions = new EventReactionDescription[sizeDescriptionsArray];

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

		EventReactionDescription description = null;
		
		index = eventType *  ReactionTypePool.CAPACITY_RTP_ARRAY + reactionType ;
		
		if (sizeDescriptionsArray > index) 			description = descriptions[index];
		return description;
	}

	protected void initialize() {
		initializeFromFile();
	}
	
	private void initializeFromFile() {
		
	}

}
