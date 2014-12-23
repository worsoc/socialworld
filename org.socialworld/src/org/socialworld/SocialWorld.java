/*
* Social World
* Copyright (C) 2014  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
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
