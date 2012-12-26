
package org.socialworld.datasource;
import org.socialworld.core.Event;

public class ReactionTypePool {
	private static ReactionTypePool instance;
	
	public static ReactionTypePool getInstance() {
		if (instance == null) {
			instance = new ReactionTypePool();
		}
		return instance;
	}

	public int[] getReactionTypes(long objectID) {
		int types[];
		int type;
		
		types = new int[Event.MAX_EVENT_TYPE];
		
		for (int eventType = 0; eventType < Event.MAX_EVENT_TYPE; eventType++){
			type = mapObjectIDToReactionType(eventType, objectID);
			types[eventType] = type;
		}
		return types;
	}
	
	private int mapObjectIDToReactionType(int eventType, long objectID) {
		// TODO implement mapping
		int type;
		type = (int) objectID;
		return type;
	}
}
