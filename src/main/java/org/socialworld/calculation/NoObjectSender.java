/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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
package org.socialworld.calculation;


public class NoObjectSender implements IObjectSender {

	private static NoObjectSender objectNothing;
	
	public static NoObjectSender getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new NoObjectSender();
		}
		return objectNothing;
	}
	
	public final int sendYourselfTo(IObjectReceiver receiver, int requestID) {
		return IObjectSender.NO_OBJECT_SENDER;
	}

	public final int sendYourselfTo(SimulationCluster cluster, IObjectReceiver receiver, int requestID) {
		return IObjectSender.NO_OBJECT_SENDER;
	}

	public IObjectSender copy() {
		return objectNothing;
	}

}
