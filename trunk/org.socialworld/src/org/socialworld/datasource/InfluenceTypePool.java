package org.socialworld.datasource;
import org.socialworld.core.Event;

public class InfluenceTypePool {
	private static InfluenceTypePool instance;
	
	public static InfluenceTypePool getInstance() {
		if (instance == null) {
			instance = new InfluenceTypePool();
		}
		return instance;
	}

	public int[] getInfluenceTypes(long objectID) {
		int types[];
		int type;
		
		types = new int[Event.MAX_EVENT_TYPE];
		
		for (int eventType = 0; eventType < Event.MAX_EVENT_TYPE; eventType++){
			type = mapObjectIDToInfluenceType(eventType, objectID);
			types[eventType] = type;
		}
		return types;
	}
	
	private int mapObjectIDToInfluenceType(int eventType, long objectID) {
		// TODO implement mapping
		int type;
		type = (int) objectID;
		return type;
	}
}
