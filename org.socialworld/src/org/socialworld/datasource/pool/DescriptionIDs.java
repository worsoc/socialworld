package org.socialworld.datasource.pool;

import java.util.ArrayList;
import java.util.List;

public class DescriptionIDs {
	
	int mainIndex; // the index of the main type (for example the index number of an event type)
	// it is possible that there is only main index 0, and the second index is the only index for difference
		
	private List<List<List<Integer>>> ids;
	
	// capacity is the range for the mapped type
	// for example: how many reaction types are possible for event type
	private int capacity;
	
	DescriptionIDs(int mainIndex, int capacity) {
		this.mainIndex = mainIndex;
		this.capacity = capacity;
		this.ids = new ArrayList<List<List<Integer>>>();
		
		for (int i = 0; i < capacity; i++) {
			this.ids.add(new ArrayList<List<Integer>>());
		}
	}
		
	
	int getMainIndex() {
		return this.mainIndex;
	}
	
	int getCapacity() {
		return this.capacity;
	}
	
	List<List<Integer>> getIDs(int secondIndex) {
		// second index ... the int value for the mapped type (reaction type, influence type, perception type ...)
		// it is possible that there is only the second index 0 (the only difference is in main index)
		if (secondIndex >= 0 & secondIndex < this.capacity)
			return this.ids.get(secondIndex);
		else
			return new ArrayList<List<Integer>>();
	}
	
	void add(int secondIndex, int nr, int id) {
		// second index ... the int value for the mapped type (reaction type, influence type, perception type ...)
		if (secondIndex >= 0 & secondIndex < this.capacity) {
			
			List<List<Integer>> forSecondIndex = this.ids.get(secondIndex);
			
			if (nr >= 0 & nr >= forSecondIndex.size()) {
				for (int i = forSecondIndex.size(); i <= nr; i++) {
					forSecondIndex.add(new ArrayList<Integer>());
				}
			}
			
			if (nr >= 0) {
				forSecondIndex.get(nr).add(id);
			}
			
		}
	}

}
