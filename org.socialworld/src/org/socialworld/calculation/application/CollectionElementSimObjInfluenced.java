package org.socialworld.calculation.application;

import org.socialworld.core.Event;
import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.access.HiddenAnimal;

public class CollectionElementSimObjInfluenced {

	private Event event;
	private StateAnimal state;
	private HiddenAnimal hidden;
	
	CollectionElementSimObjInfluenced(Event event, StateAnimal state, HiddenAnimal hidden) {
		this.event = event;
		this.state = state;
		this.hidden = hidden;
	}
	
	Event getEvent() {
		return this.event;
	}
	
	StateAnimal getState() {
		return this.state;
	}
	
	HiddenAnimal getHidden() {
		return this.hidden;
	}

}
