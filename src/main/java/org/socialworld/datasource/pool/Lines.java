package org.socialworld.datasource.pool;

import java.util.ArrayList;
import java.util.List;


public class Lines {

	int mainIndex; // the index of the main type (for example the index number of an event type)
// it is possible that there is only main index 0, and the second index is the only index for difference
	
	private List<List<List<String>>> lines;
	
	// capacity is the range for the mapped type
	// for example: how many reaction types are possible for event type
	private int capacity;
	
	Lines(int mainIndex, int capacity) {
		this.mainIndex = mainIndex;
		this.capacity = capacity;
		this.lines = new ArrayList<List<List<String>>>();
		
		for (int i = 0; i < capacity; i++) {
			this.lines.add(new ArrayList<List<String>>());
		}
	}
		
	
	int getMainIndex() {
		return this.mainIndex;
	}
	
	int getCapacity() {
		return this.capacity;
	}
	
	List<List<String>> getLines(int secondIndex) {
		// second index ... the int value for the mapped type (reaction type, influence type, perception type ...)
		// it is possible that there is only the second index 0 (the only difference is in main index)
		if (secondIndex >= 0 & secondIndex < this.capacity)
			return this.lines.get(secondIndex);
		else
			return new ArrayList<List<String>>();
	}
	
	void add(int secondIndex, int nr, String line) {
		// second index ... the int value for the mapped type (reaction type, influence type, perception type ...)
		if (secondIndex >= 0 & secondIndex < this.capacity) {
			
			List<List<String>> forSecondIndex = this.lines.get(secondIndex);
			
			if (nr >= 0 & nr >= forSecondIndex.size()) {
				for (int i = forSecondIndex.size(); i <= nr; i++) {
					forSecondIndex.add(new ArrayList<String>());
				}
			}
			
			if (nr >= 0) {
				forSecondIndex.get(nr).add(line);
			}
			
		}
	}
	
	void add(int secondIndex, String line) {
		// second index ... the int value for the mapped type (reaction type, influence type, perception type ...)
		// there is not argument int nr in this alternative --> always increment nr (nr := size)
		if (secondIndex >= 0 & secondIndex < this.capacity) {
			
			List<List<String>> forSecondIndex = this.lines.get(secondIndex);
			int nr = forSecondIndex.size();
			
			forSecondIndex.add(new ArrayList<String>());
			forSecondIndex.get(nr).add(line);
			
		}
	}
}
