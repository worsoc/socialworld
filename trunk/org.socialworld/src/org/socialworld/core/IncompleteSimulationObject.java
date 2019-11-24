package org.socialworld.core;

import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.access.HiddenSimulationObject;

public class IncompleteSimulationObject {

	private boolean valid;
	private boolean isCompleted = false;
	
	private SimulationObject object;
	private HiddenSimulationObject hiddenObject;
	
	public IncompleteSimulationObject() {
		this.valid = false;
	}
	
	public IncompleteSimulationObject(SimulationObject object, HiddenSimulationObject hiddenObject) {
		this.object = object;
		this.hiddenObject = hiddenObject;
		this.valid = true;
	}
	
	public boolean isValid() {
		return this.valid;
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
	
}
