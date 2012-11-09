/**
 * 
 */
package org.socialworld;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.socialworld.core.Simulation;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class SocialWorld implements BundleActivator {
	
	public static final String PLUGIN_ID = "org.socialworld";

	private static final Logger logger = Logger.getLogger(SocialWorld.class);

	private static SocialWorld currentObject;

	private Simulation simulation;

/*	private SocialWorld() {
		super();
		simulation = Simulation.getInstance();
		
	}
*/	
	public void start(BundleContext context) throws Exception {
		if (Simulation.WITH_LOGGING == 1 ) logger.info("Start bundle " + PLUGIN_ID);

		currentObject = this;
		simulation = Simulation.getInstance();
	}

	public void stop(BundleContext context) throws Exception {
		// TODO Simulation stoppen, Threads beenden, Ressourcen freigeben.
		currentObject = null;
		simulation.stopSimulation();
		simulation = null;
	}

	public static SocialWorld getCurrent() {
		if (currentObject == null) {
			currentObject = new SocialWorld();
		}
		return currentObject;
	}

	/**
	 * @return the simulation
	 */
	public Simulation getSimulation() {
		return simulation;
	}

}
