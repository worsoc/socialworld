/**
 * 
 */
package org.socialworld;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.apache.log4j.Logger;
import org.socialworld.core.ObjectManager;
import org.socialworld.objects.Human;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class SocialWorld {

	private static final Logger logger = Logger.getLogger(SocialWorld.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("Start main method of SocialWorld");
		
		ObjectManager objectManager = ObjectManager.getCurrent();
		List<Human> humans = objectManager.getHumans();
		humans.add(new Human());
		humans.add(new Human());
		humans.add(new Human());
		
		System.out.println("Humans: " + humans);
		
		logger.info("End main method of SocialWorld");
	}

}
