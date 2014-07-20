package org.socialworld.objects;

import org.socialworld.core.Action;
import org.socialworld.core.Event;
import org.socialworld.attributes.ActionType;

/**
 * The God is an special simulation object that is responsible for global
 * events. The god is responsible for weather for example.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class God extends SimulationObject {

	public God() {
		super();
	}

	@Override
	public void changeByEvent(final Event event) {
		// TODO Implement method
	}

	public void reactToEvent(final Event event) {
		
	}

	@Override
	protected void doAction(final ActionType type, final Action action) {
	}
}
