/**
 * 
 */
package org.socialworld;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.log4j.Logger;
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
		
		Human human = new Human();
		human.addPropertyChangeListener("stulle", new PropertyChangeListener() {
		
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
		
			}
		
		} );
	}

}
