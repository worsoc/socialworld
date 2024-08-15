package org.socialworld.datasource.pool;

import java.util.ArrayList;
import java.util.List;


public class Jsons {

	int mainIndex; // the index of the main type (for example the index number of an event type)
	// it is possible that there is only main index 0, and the second index is the only index for difference
	// the inner list (index nr) is for different json entrys according to the second index (mostly it may be only nr = 0)
	private List<List<String>> jsons;

	// capacity is the range for the mapped type
	// for example: how many reaction types are possible for event type
	private int capacity;

	Jsons(int mainIndex, int capacity) {
		this.mainIndex = mainIndex;
		this.capacity = capacity;
		this.jsons = new ArrayList<List<String>>();
		
		for (int i = 0; i < capacity; i++) {
			this.jsons.add(new ArrayList<String>());
		}
	}

	int getMainIndex() {
		return this.mainIndex;
	}
	
	int getCapacity() {
		return this.capacity;
	}

	List<String> getJsons(int secondIndex) {
		// second index ... the int value for the mapped type (reaction type, influence type, perception type ...)
		// it is possible that there is only the second index 0 (the only difference is in main index)
		if (secondIndex >= 0 & secondIndex < this.capacity)
			return this.jsons.get(secondIndex);
		else
			return new ArrayList<String>();
	}

	void set(int secondIndex, String json) {
		int nr = 0;
		set( secondIndex,  nr,  json);
	}

	void set(int secondIndex, int nr, String json) {
		// second index ... the int value for the mapped type (reaction type, influence type, perception type ...)
		if (secondIndex >= 0 & secondIndex < this.capacity) {
			
			List<String> forSecondIndex = this.jsons.get(secondIndex);
			
			if (nr >= 0 & nr >= forSecondIndex.size()) {
				for (int i = forSecondIndex.size(); i <= nr; i++) {
					forSecondIndex.add("");
				}
			}
			
			if (nr >= 0) {
				forSecondIndex.set(nr, json);
			}
			
		}
	}
	
}
