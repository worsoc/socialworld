/**
 * 
 */
package org.socialworld.core;

import java.util.List;

import org.apache.log4j.Logger;
import org.socialworld.objects.Human;

/**
 * The singleton object manager collects references to other central objects.
 * There are references to the {@link Simulation} and to the {@link EventMaster}.
 * 
 * @author Andre Schade (circlesmiler)
 */
public class ObjectManager {

	private static final Logger logger = Logger.getLogger(ObjectManager.class
			.getName());

	private static ObjectManager manager = null;

	Simulation simulation;

	private ObjectManager() {
		logger.debug("create simulation object");
		this.simulation = new Simulation();
	}

	/**
	 * Returns the {@link ObjectManager} instance.
	 * 
	 * @return {@link ObjectManager} instance
	 */
	public static ObjectManager getCurrent() {
		if (manager == null) {
			manager = new ObjectManager();
		}
		return manager;
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
