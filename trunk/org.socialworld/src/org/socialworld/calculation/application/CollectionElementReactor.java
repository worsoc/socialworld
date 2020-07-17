package org.socialworld.calculation.application;

import org.socialworld.core.Event;
import org.socialworld.objects.StateSimulationObject;
import org.socialworld.objects.access.HiddenSimulationObject;

public class CollectionElementReactor {

	private Event event;
	private StateSimulationObject state;
	private HiddenSimulationObject hidden;
	
	CollectionElementReactor(Event event, StateSimulationObject state, HiddenSimulationObject hidden) {
		this.event = event;
		this.state = state;
		this.hidden = hidden;
	}
	
	Event getEvent() {
		return this.event;
	}
	
	StateSimulationObject getState() {
		return this.state;
	}
	
	HiddenSimulationObject getHidden() {
		return this.hidden;
	}

}
