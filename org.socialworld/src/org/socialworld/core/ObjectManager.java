/**
 * 
 */
package org.socialworld.core;

import java.util.List;

import org.apache.log4j.Logger;
import org.socialworld.objects.Human;

/**
 * The object manager collects references to other central objects.
 * There are references to the {@link Simulation} and to the {@link EventMaster}.
 * 
 * @author Andre Schade (circlesmiler)
 */
public class ObjectManager {

	private static final Logger logger = Logger.getLogger(ObjectManager.class
			.getName());

	Simulation simulation;

	public ObjectManager(Simulation simulation) {
		logger.debug("create object manager object");
		this.simulation = simulation;
	}

	public Simulation getSimulation() {
		return this.simulation;
	}

	public EventMaster getEventMaster() {
		return this.simulation.getEventMaster();
	}

	public List<Human> getHumans() {
		return this.simulation.getHumans();
	}
}
