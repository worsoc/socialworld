package org.socialworld.objects.concrete.spells;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.objects.Magic;
import org.socialworld.objects.State;

public class Lightning extends Magic {

	public Lightning(int objectID) {
		super(objectID);
	}
	
	protected List<State> createAddOnStates() {
		
		List<State> result = new ArrayList<State>();
		return result;
		
	}

}
