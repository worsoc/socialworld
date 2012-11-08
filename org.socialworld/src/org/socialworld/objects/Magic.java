/**
 * 
 */
package org.socialworld.objects;

import org.socialworld.core.Action;
import org.socialworld.core.Event;

/**
 * magic spells are simulation objects that offer some fantasy aspects. A magic
 * spells releases events that change the environment and effect simulation
 * objects.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Magic extends SimulationObject {
// TODO implement Magic class functionality
	public Magic() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.socialworld.objects.SimulationObject#determineInfluence(org.socialworld.objects.SimulationEvent)
	 */
	@Override
	public void changeByEvent(final Event event) {

	}

	public void reactToEvent(final Event event) {
		
	}

	@Override
	public void doAction(final Action action) {

	}

}
