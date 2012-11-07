package org.socialworld.datasource;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.socialworld.calculation.State2ActionDescription;

public class State2ActionDescriptionPool {
	
	//public final int MAX_STATE_2_ACTION_TYPE = 16;
	
	private static final Logger logger = Logger.getLogger(State2ActionDescriptionPool.class);
	private static State2ActionDescriptionPool instance;
	
	private static List<State2ActionDescription> descriptions;
	
	private State2ActionDescriptionPool () {
		logger.debug("create State2ActionDescriptionPool");
		descriptions = new ArrayList<State2ActionDescription> ();

		initialize();
	}
	
	public static State2ActionDescriptionPool getInstance() {
		if (instance == null) {
			instance = new State2ActionDescriptionPool();
		}
		return instance;
	}
	
	public State2ActionDescription getDescription(int state2ActionType ) {
		int index;
		State2ActionDescription description;
		
		index = state2ActionType ;
		
		if (descriptions.size() >= index) {
			description = descriptions.get(index);
			if (description == null) {
				description = createDescription(state2ActionType);
				descriptions.add(index, description);
			}
		}	
		else {
			description = createDescription(state2ActionType);
			descriptions.add(index, description);
		}
		return description;
	}

	private void initialize() {
	}
	

	private State2ActionDescription createDescription(int state2ActionType) {
		State2ActionDescription description = new	State2ActionDescription();
		
		return description;
	}


}
