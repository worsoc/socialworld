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


/**
 * The percipience master is a thread that lets all simulation objects trigger an event
 *  that will be handled from other objects as percipient event.
 *  The result is the possibility / chance to be percieved / noticed 
 * 
 * @author Mathias Sikos (MatWorsoc)
 */
public class PercipienceMaster extends SocialWorldThread {

	private static PercipienceMaster instance;
	
	private ObjectMaster myObjectMaster;

	int sleepTime = 100;

	public static PercipienceMaster getInstance(ObjectMaster objectMaster) {
		if (instance == null) {
			instance = new PercipienceMaster(objectMaster);
		}
		return instance;
	}

	private PercipienceMaster(ObjectMaster objectMaster) {
		myObjectMaster = objectMaster;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		
		while (isRunning()) {
			
			calculateNextObject();
			
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}

	private final void calculateNextObject() {
		
		myObjectMaster.perceiveNextObject();
		
	}

}
