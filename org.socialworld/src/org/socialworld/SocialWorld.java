/**
 * 
 */
package org.socialworld;

import java.util.List;

import org.apache.log4j.Logger;
import org.socialworld.core.ObjectManager;
import org.socialworld.objects.Human;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class SocialWorld implements BundleActivator {

    private static final Logger logger = Logger.getLogger(SocialWorld.class);

    public void start(BundleContext context) throws Exception {
	logger.info("Start main method of SocialWorld");

	ObjectManager objectManager = ObjectManager.getCurrent();
	List<Human> humans = objectManager.getHumans();
	humans.add(new Human());
	humans.add(new Human());
	humans.add(new Human());

	System.out.println("Humans: " + humans);

	logger.info("End main method of SocialWorld");
    }

    public void stop(BundleContext context) throws Exception {
	// TODO Simulation stoppen, Threads beenden, Ressourcen freigeben.
    }

}
