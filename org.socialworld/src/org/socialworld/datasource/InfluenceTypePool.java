package org.socialworld.datasource;
import org.socialworld.core.Event;

public class InfluenceTypePool {
	public static final int CAPACITY_ITP_ARRAY = 100;

	private static InfluenceTypePool instance;
	
	private static int[] influenceTypesForPositiveIndex;
	private static int[] influenceTypesForNegativeIndex;
	
	private InfluenceTypePool() {
		influenceTypesForPositiveIndex = new int[(CAPACITY_ITP_ARRAY + 1) * Event.MAX_EVENT_TYPE];
		influenceTypesForNegativeIndex = new int[(CAPACITY_ITP_ARRAY + 1) * Event.MAX_EVENT_TYPE];
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
		
		
		if (indexByGauss >= 0)
			for (int eventType = 0; eventType < Event.MAX_EVENT_TYPE; eventType++){
				index = indexByGauss * Event.MAX_EVENT_TYPE  + eventType;
				types[eventType] = influenceTypesForPositiveIndex[index];
			}
		else {
			indexByGauss = indexByGauss * -1;
			for (int eventType = 0; eventType < Event.MAX_EVENT_TYPE; eventType++){
				index = indexByGauss * Event.MAX_EVENT_TYPE  + eventType;
				types[eventType] = influenceTypesForNegativeIndex[index];
			}
		}	

		return types;
	}
	
}
