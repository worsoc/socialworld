
package org.socialworld.datasource;
import org.socialworld.core.Event;

public class ReactionTypePool {
	public static final int CAPACITY_RTP_ARRAY = 100;

	private static ReactionTypePool instance;

	private static int[] reactionTypes;

	private ReactionTypePool() {
		reactionTypes = new int[CAPACITY_RTP_ARRAY * Event.MAX_EVENT_TYPE];
	}

	public static ReactionTypePool getInstance() {
		if (instance == null) {
			instance = new ReactionTypePool();
		}
		return instance;
	}

	public int[] getReactionTypes(int indexByGauss) {
		int types[];
		int index;
		
		types = new int[Event.MAX_EVENT_TYPE];
		
		for (int eventType = 0; eventType < Event.MAX_EVENT_TYPE; eventType++){
			index = indexByGauss * Event.MAX_EVENT_TYPE  + eventType;
			types[eventType] = reactionTypes[index];
		}
		return types;
	}
	

}
