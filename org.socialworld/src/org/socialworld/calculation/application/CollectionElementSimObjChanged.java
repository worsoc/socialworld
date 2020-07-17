package org.socialworld.calculation.application;

import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.access.HiddenAnimal;

public class CollectionElementSimObjChanged {
	
	private StateAnimal state;
	private HiddenAnimal hidden;
	
	CollectionElementSimObjChanged(StateAnimal state, HiddenAnimal hidden) {
		this.state = state;
		this.hidden = hidden;
	}
	
	StateAnimal getState() {
		return this.state;
	}
	
	HiddenAnimal getHidden() {
		return this.hidden;
	}

}
