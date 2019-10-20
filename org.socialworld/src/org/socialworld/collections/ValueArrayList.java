package org.socialworld.collections;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public class ValueArrayList {

	private List<Value> values;
	private Value nothing;
	
	public ValueArrayList() {
		this.values = new ArrayList<Value>();
		this.nothing = new Value();
	}
	
	public ValueArrayList(int size) {
		
		this.values = new ArrayList<Value>();
		this.nothing = new Value();
		
		for (int i = 0; i < size; i++) {
			values.add(nothing);
		}
	}
	
	public int size() {
		return values.size();
	}
	
	public void add(Value value) {
		values.add(value);
	}
	
	public void set(int index, Value value) {
		values.set(index, value);
	}
	
	public Value get(int index) {
		return values.get(index);
	}

	public int findValue(String name) {
		
		int argumentsCount;
		int index;
		
		argumentsCount = size();
		
		for (index = 0; index < argumentsCount; index++) {
			if (get(index).getName().equals(name)) return index;
		}
		
		return -1;

	}

	public Value getValue(String name) {
		
		int argumentsCount;
		int index;
		Value value;
		
		argumentsCount = size();
		
		for (index = 0; index < argumentsCount; index++) {
			value = get(index);
			if (value.getName().equals(name)) return value;
		}
		
		return nothing;

	}

	public Value getValue(Type type, int wantedOccurence) {
		
		int argumentsCount;
		int occurence;
		Value value;
		
		argumentsCount = size();
		occurence = 0;
		
		for (int index = 0; index < argumentsCount; index++) {
			value = get(index);
			if (value.getType() == type) {
				occurence++;
				if (occurence == wantedOccurence) {
					return value;
				}
			}
		}
		return nothing;
	}
	
	
}
