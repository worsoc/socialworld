package org.socialworld.objects.concrete.gods;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.objects.God;
import org.socialworld.objects.State;

public class Weather extends God {

	public Weather(int objectID) {
		super(objectID);
	}
	
	protected List<State> createAddOnStates() {
		
		List<State> result = new ArrayList<State>();
		return result;
		
	}

}
