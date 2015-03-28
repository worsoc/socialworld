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

import java.sql.*;


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
		try		{			Class.forName("org.mariadb.jdbc.Driver");		}
		catch ( ClassNotFoundException e )
		{
		  // Blöd: Treiber konnte nicht geladen werden.
		  e.printStackTrace();
		}
		
		Connection  connection;
		
		try {			connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sw1", "sw", "sw");		}
		catch (Exception e) {			  e.printStackTrace();			  return;		}
		
		Statement stmt;
		
		try 		{			stmt = connection.createStatement(); }
		catch (SQLException e) { 
			e.printStackTrace(); return;}

		try 		{		stmt.executeUpdate("INSERT INTO test (id, value) values (1, 'hallo')"); }
		catch (SQLException e) {
			e.printStackTrace(); return;}

		try 		{		stmt.close(); }
		catch (SQLException e) {
			e.printStackTrace(); return;}

		try 		{			connection.close(); }
		catch (SQLException e) { 
			e.printStackTrace(); return;}

		
		
		
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
