package org.socialworld.collections;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.core.IncompleteSimulationObject;

public class IncompleteObjects {

	private final List<IncompleteSimulationObject> incompleteObjects;

	private int freePosition;
	
	public static IncompleteSimulationObject nothing = new IncompleteSimulationObject();
	
	public IncompleteObjects() {
		
		this.incompleteObjects = new ArrayList<IncompleteSimulationObject>();
		freePosition = 0;
			
	}
	
	public int add(IncompleteSimulationObject incompleteObject) {
		
		int position;
		
		if (this.freePosition < this.incompleteObjects.size()) {
			position = this.freePosition;
			this.incompleteObjects.set(position, incompleteObject);
			this.freePosition = getNextFree();
		}
		else {
			this.incompleteObjects.add(incompleteObject);
			this.freePosition = this.incompleteObjects.size();
			position = this.freePosition  - 1;
		}
		
		return position;
		
	}
	
	public IncompleteSimulationObject remove(int position, int objectID) {
		
		IncompleteSimulationObject incompleteObject = this.incompleteObjects.get(position);
		
		if (incompleteObject.isValid()) {
			if (incompleteObject.getObjectID() == objectID) {
				this.incompleteObjects.set(position, nothing);
				if (position < this.freePosition) {
					this.freePosition = position;
				}
				return incompleteObject;
			}
		}
		
		return nothing;
		
	}
	
	 
	public IncompleteSimulationObject get(int position) {
		
		if (position >= 0 && position < this.incompleteObjects.size())
			return this.incompleteObjects.get(position);
		else
			return nothing;
		
	}
	
	private int getNextFree() {
		
		for (int index = 0; index < this.incompleteObjects.size(); index++) {
			if (!this.incompleteObjects.get(index).isValid()) return index;
		}
		
		return this.incompleteObjects.size();
	}
	
}
