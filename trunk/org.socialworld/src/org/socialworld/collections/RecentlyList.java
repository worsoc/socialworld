package org.socialworld.collections;

public class RecentlyList<Type> {

	private Type array[];
	int capacity;
	int current;
	
	public RecentlyList (int capacity) {
		this.array = (Type[]) new Object[capacity];
		this.capacity = capacity;
		this.current = -1;
	}

	public void add(Type element) {
		this.current++;
		if (this.current == this.capacity) {
			this.current = 0;
		}
		this.array[this.current] = element;
	}
	
	public Type getRecentlyAdded() {
		
		if (this.current == -1) {
			return null;
		}
		else {
			return this.array[this.current];
		}
		
	}
	
	public Type[] getAll() {
		
		if (this.current == -1) return null;
		
		Type[] result = (Type[]) new Object[this.capacity];
		int indexArray = current;
		for (int i = 0; i < this.capacity; i++) {
			result[i] = this.array[indexArray];
			indexArray++;
			if(indexArray == this.capacity) {
				indexArray = 0;
			}
		}
		
		return result;
		
	}
	
}
