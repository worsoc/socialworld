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
		// TODO Auto-generated method stub

	}

	@Override
	public void doAction(final Action action) {
		// TODO Auto-generated method stub

	}

}
