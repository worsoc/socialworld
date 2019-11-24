package org.socialworld.core;

import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.access.HiddenSimulationObject;

public class IncompleteSimulationObject {

	private boolean valid;
	private boolean isCompleted;
	
	private SimulationObject object;
	private HiddenSimulationObject hiddenObject;
	
	public IncompleteSimulationObject() {
		this.valid = false;
		this.isCompleted = true; 
	}
	
	public IncompleteSimulationObject(SimulationObject object, HiddenSimulationObject hiddenObject) {
		this.object = object;
		this.hiddenObject = hiddenObject;
		this.valid = true;
		this.isCompleted = false;
		
		hiddenObject.setIncompleteSimulationObject(this);
		
	}
	
	public boolean isValid() {
		return this.valid;
	}
	
	public boolean isIncomplete() {
		return this.isCompleted;
	}
	
	void setComplete() {
		this.isCompleted = true;
	}
	
	public int getObjectID() {
		if (!this.isCompleted) {
			return this.object.getObjectID();
		}
		return 0;
	}
	
	SimulationObject getObject() {
		if (!this.isCompleted) {
			return this.object;
		}
		return null;
	}
	
	HiddenSimulationObject getHiddenObject() {
		if (!this.isCompleted) {
			return this.hiddenObject;
		}
		return null;
	}
	
	public boolean checkForHiddenObject(HiddenSimulationObject hiddenObject) {
		
		return this.hiddenObject == hiddenObject;
		
	}
	
}
