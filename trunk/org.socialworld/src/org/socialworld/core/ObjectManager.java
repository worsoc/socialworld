/**
 * 
 */
package org.socialworld.core;

/**
 * @author Andre Schade
 *
 */
public class ObjectManager {
	
	private static ObjectManager manager = null;
	
	private ObjectManager() {
		
	}
	
	/**
	 * Returns the {@link ObjectManager} instance.
	 * @return {@link ObjectManager} instance
	 */
	public static ObjectManager getObjectManager() {
		if (manager == null) {
			manager = new ObjectManager();
		}
		return manager;
	}

}
