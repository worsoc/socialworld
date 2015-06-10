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
import org.socialworld.attributes.Time;
import org.socialworld.core.Simulation;


import org.socialworld.datasource.tablesSimulation.TableObject;
import org.socialworld.datasource.tablesSimulation.TablePosition;
import org.socialworld.datasource.tablesSimulation.TableReactionByEvent;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class SocialWorld  {
	
	public static final String PLUGIN_ID = "org.socialworld";

	private static final Logger logger = Logger.getLogger(SocialWorld.class);

	private static SocialWorld currentObject;

	private static Simulation simulation;

/*	private SocialWorld() {
		super();
		simulation = Simulation.getInstance();
		
	}
*/	
	
	public static void main(String[] args)
	{
		if (Simulation.WITH_LOGGING == 1 ) logger.info("Start program " );

		currentObject = getCurrent();
		simulation = Simulation.getInstance();
		
		
		///////  Start Test Connection MariaDB   /////
		
		
		TablePosition positions;
		
		positions = new TablePosition();
		
		positions.insert(257, 11, 88, 0, 123456789, "ABCDEFGH");
		positions.update(256,  "KLMNOP");
//		positions.select(positions.SELECT_ALL_COLUMNS);
		
		positions.close();
		
		
		TableObject objects;
		objects = new TableObject();
		
		objects.insert(5, 1, 1967, 1, 1234567, "KLMNOP");
		objects.insert(6, 1, 1968, 2, 123456789, "ABCDEFGH");

		objects.update(4, 3, 1965, 3, 987654321, "XYZABC");

		objects.close();
		
		
		TableReactionByEvent reactByevent;
		reactByevent = new TableReactionByEvent();
		
		int i;
		int reactions1[];
		int reactions2[];
		reactions1 = new int[256];
		reactions2 = new int[256];
		
		for (i = 0; i < 256; i++) {
			reactions1[i] =  256 - i;
			reactions2[i] =  i;
		}
		reactByevent.insert(1, reactions1);
		reactByevent.insert(2, reactions2);
		
		///////  Ende Test Connection MariaDB   /////
		
		
		Time test = new Time();
		if (Simulation.WITH_LOGGING == 1 ) logger.info(test.toString() );
		
		simulation.startSimulation();
		
		simulation.stopSimulation();
		
	}
	
	
/*
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
*/	

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
