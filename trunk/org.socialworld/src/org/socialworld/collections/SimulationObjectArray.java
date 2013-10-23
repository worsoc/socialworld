package org.socialworld.collections;
import org.socialworld.objects.SimulationObject;

public class SimulationObjectArray {
	private SimulationObject array[];
	int capacity;
	
	public SimulationObjectArray(int capacity) {
		array = new SimulationObject[capacity];
		this.capacity = capacity;
	}
	
	public void ensureCapacity(int capacity) {
		SimulationObject tmpArray[];
		
		if (capacity > this.capacity) {
			tmpArray = new SimulationObject[capacity];
			
			for (int index = 0; index < this.capacity; index++) {
				tmpArray[index] = this.array[index];
			}
			this.array = tmpArray;
			this.capacity = capacity;
		}
	}
	
	public void increaseCapacity(int increase)  {
		int newCapacity;
		newCapacity = this.capacity + increase;
		ensureCapacity(newCapacity);
	}

	public void set(int index, SimulationObject object) {
		if (index >= this.capacity) ensureCapacity(index + 1);
		array[index] = object;
	}
	
	public SimulationObject get(int index){
		if (index < this.capacity) return array[index];
		else return null;
	}
	
	public void delete(int index) {
		if (index < this.capacity) array[index] = null;
	}
}
