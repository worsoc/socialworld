package org.socialworld.datasource;
import org.socialworld.core.Event;

public class InfluenceTypePool {
	public static final int CAPACITY_ITP_ARRAY = 100;

	private static InfluenceTypePool instance;
	
	private static int[] influenceTypes;
	
	private InfluenceTypePool() {
		influenceTypes = new int[CAPACITY_ITP_ARRAY * Event.MAX_EVENT_TYPE];
	}
	
	public static InfluenceTypePool getInstance() {
		if (instance == null) {
			instance = new InfluenceTypePool();
		}
		return instance;
	}

	public int[] getInfluenceTypes(int indexByGauss) {
		int types[];
		int index;
		
		types = new int[Event.MAX_EVENT_TYPE];
		
		for (int eventType = 0; eventType < Event.MAX_EVENT_TYPE; eventType++){
			index = indexByGauss * Event.MAX_EVENT_TYPE  + eventType;
			types[eventType] = influenceTypes[index];
		}
		return types;
	}
	
}
