
package org.socialworld.datasource;
import org.socialworld.core.Event;

public class ReactionTypePool {
	public static final int CAPACITY_RTP_ARRAY = 100;

	private static ReactionTypePool instance;

	private static int[] reactionTypesForPositiveIndex;
	private static int[] reactionTypesForNegativeIndex;

	private ReactionTypePool() {
		reactionTypesForPositiveIndex = new int[(CAPACITY_RTP_ARRAY + 1) * Event.MAX_EVENT_TYPE];
		reactionTypesForNegativeIndex = new int[(CAPACITY_RTP_ARRAY + 1) * Event.MAX_EVENT_TYPE];
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
	
		if (indexByGauss >= 0)
			for (int eventType = 0; eventType < Event.MAX_EVENT_TYPE; eventType++){
				index = indexByGauss * Event.MAX_EVENT_TYPE  + eventType;
				types[eventType] = reactionTypesForPositiveIndex[index];
			}
		else {
			indexByGauss = indexByGauss * -1;
			for (int eventType = 0; eventType < Event.MAX_EVENT_TYPE; eventType++){
				index = indexByGauss * Event.MAX_EVENT_TYPE  + eventType;
				types[eventType] = reactionTypesForNegativeIndex[index];
			}
		}	
		
		return types;
	}
	

}
