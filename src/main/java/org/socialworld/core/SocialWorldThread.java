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

import org.socialworld.calculation.IObjectReceiver;
import org.socialworld.calculation.ObjectRequester;

public class SocialWorldThread extends Thread implements IObjectReceiver{

	public static final int SLEEPTIME_ACTION_CREATOR = 2;
	public static final int SLEEPTIME_ATTRIBUTE_CALCULATOR = 5;
	public static final int SLEEPTIME_KNOWLEDGE_CALCULATOR = 5;
	public static final int SLEEPTIME_POSITION_CALCULATOR = 5;
	public static final int SLEEPTIME_TALK_CALCULATOR = 5;
	
	/**
	 * says whether the thread is running or not
	 */
	private boolean isRunning;
	protected int sleepTime;
	
	protected ObjectRequester objectRequester = new ObjectRequester();

	protected boolean isRunning() {return this.isRunning;}
	
	/**
	 * the method stops the event processing
	 */
	public void stopThread() {
		isRunning = false;
	}

	/**
	 * the method starts the event processing
	 */
	public void startThread() {
		isRunning = true;
		this.start();
	}
	
	// TODO what shall happen if the queues in concrete sub class simulations are filled

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////implementing IObjectReceiver ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int receiveObject(int requestID, Object object) {
		objectRequester.receive(requestID, object);
		return 0;
	}

}
