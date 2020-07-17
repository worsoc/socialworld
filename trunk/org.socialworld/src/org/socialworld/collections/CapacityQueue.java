package org.socialworld.collections;

public class CapacityQueue<Type> {

	private String name;
	
	private Type array[];
	int capacity;
	int indexAdd;
	int indexRemove;
	int size;
	
	public CapacityQueue (String name, int capacity) {
		
		this.name = name;
		
		this.array = (Type[]) new Object[capacity];
		this.capacity = capacity;
		this.indexAdd = 0;
		this.indexRemove = 0;
		this.size = 0;
		
	}

	public boolean add(Type element) {
		
		if (this.size == this.capacity) {
			System.out.println("------------------------");
			System.out.println("CapacityQueue " + this.name + " is full!!!");
			System.out.println("------------------------");
			return false;
		}
		else {
			this.array[this.indexAdd] = element;
			this.indexAdd++;
			if (this.indexAdd == this.capacity) {
				this.indexAdd = 0;
				System.out.println("------------------------");
				System.out.println("CapacityQueue " + this.name + " starts from index 0 (indexRemove: " + this.indexRemove + ")");
				System.out.println("------------------------");
			}
			this.size++;
			if (this.size > this.capacity) {
				System.out.println("------------------------");
				System.out.println("CapacityQueue " + this.name + " is SUPER FULL (size > capacity)");
				System.out.println("------------------------");
			}

			return true;
		}
	}
	
	public Type remove() {
		
		Type remove;
		if (this.size == 0) {
			remove = null;
		}
		else {
			remove = this.array[this.indexRemove];
			this.indexRemove++;
			if (this.indexRemove == this.capacity) {
				this.indexRemove = 0;
			}
			this.size--;

		}
		if (this.size < 0) {
			System.out.println("------------------------");
			System.out.println("CapacityQueue " + this.name + " is SUPER EMPTY (size < 0)");
			System.out.println("------------------------");
		}
		return remove;
		
	}
	
	public int size() {
		return this.size;
	}

}
