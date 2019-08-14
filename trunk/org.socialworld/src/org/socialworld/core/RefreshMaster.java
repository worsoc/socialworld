/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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
package org.socialworld.core;

import org.socialworld.objects.SimulationObject_Type;

/**
 * The refresh master is a thread that calculates the change of state of all simulation objects one time in a given time period.
 * Therefore there is a calculation of a new attribute array (for animals only), depending only on the former attribute array and a calculation matrix.
 * The calculationdoesn't depend on actions or events according to the simulation object. 
 * 
 * @author Mathias Sikos (tyloesand)
 */
public class RefreshMaster extends SocialWorldThread {

	private static RefreshMaster instance;
	
	ObjectMaster myObjectMaster;
	
	private int sleepTime = 1 ; //100;
	
	SimulationObject_Type simObjType;
	
	public static RefreshMaster getInstance(ObjectMaster objectMaster) {
		if (instance == null) {
			instance = new RefreshMaster(objectMaster);
		}
		return instance;
	}
	
	private RefreshMaster(ObjectMaster objectMaster) {
		myObjectMaster = objectMaster;
		simObjType = SimulationObject_Type.human;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		
		while (isRunning()) {
//			System.out.println("RefreshMaster run()");
			
			calculateNextObject();
			
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}

	private final void calculateNextObject() {
		
		int simObjTypeIndex;
		
		simObjTypeIndex = myObjectMaster.refreshNextObjectsState(this.simObjType);
		
		if (simObjTypeIndex != this.simObjType.getIndex())
			this.simObjType = SimulationObject_Type.getSimulationObjectType(simObjTypeIndex);
		
	}
	
}
