package org.socialworld.calculation.application;

import org.socialworld.objects.StateSimulationObject;
import org.socialworld.objects.access.HiddenSimulationObject;

public class CollectionElementActor {
	
	private StateSimulationObject state;
	private HiddenSimulationObject hidden;
	
	CollectionElementActor(StateSimulationObject state, HiddenSimulationObject hidden) {
		this.state = state;
		this.hidden = hidden;
	}
	
	StateSimulationObject getState() {
		return this.state;
	}
	
	HiddenSimulationObject getHidden() {
		return this.hidden;
	}

}
