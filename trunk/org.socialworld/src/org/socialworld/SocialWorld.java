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

import org.socialworld.core.Simulation;
import org.socialworld.data.FillWithTestData_ACM;
import org.socialworld.data.FillWithTestData_Position;



/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class SocialWorld  {
	
	public static final String PLUGIN_ID = "org.socialworld";


	private static SocialWorld currentObject;

	private static Simulation simulation;

/*	private SocialWorld() {
		super();
		simulation = Simulation.getInstance();
		
	}
*/	
	
	public static void main(String[] args)
	{

		currentObject = getCurrent();
		simulation = Simulation.getInstance();
		
		test();
		
		fillTestData();
		
		simulation.startSimulation();
		
		simulation.stopSimulation();
		
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

	private static void test()	{
		
	}
	
	
	
	private static void fillTestData() {
	
		
		
		FillWithTestData_ACM fillACM;
		fillACM = new FillWithTestData_ACM();
		
		fillACM.fill(4);
		
		
		FillWithTestData_Position fillPosition;
		fillPosition = new FillWithTestData_Position();
		fillPosition.fill();
		
	}
}
