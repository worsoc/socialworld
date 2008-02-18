/**
 * 
 */
package org.socialworld;

import java.util.List;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.socialworld.core.Simulation;
import org.socialworld.objects.Human;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class SocialWorld implements BundleActivator {

    private static final Logger logger = Logger.getLogger(SocialWorld.class);
    
    private static SocialWorld currentObject;
	
    private Simulation simulation;

    public void start(BundleContext context) throws Exception {
	logger.info("Start main method of SocialWorld");
	
	currentObject = this;
	
	simulation = new Simulation();
	simulation.startSimulation();
	
	List<Human> humans = simulation.getHumans();
	humans.add(new Human());
	humans.add(new Human());
	humans.add(new Human());

	System.out.println("Humans: " + humans);

	logger.info("End main method of SocialWorld");
    }

    public void stop(BundleContext context) throws Exception {
	// TODO Simulation stoppen, Threads beenden, Ressourcen freigeben.
    }
    
    public static SocialWorld getCurrent() {
    	return currentObject;
    }
    
    /**
	 * @return the simulation
	 */
	public Simulation getSimulation() {
		return simulation;
	}

}
