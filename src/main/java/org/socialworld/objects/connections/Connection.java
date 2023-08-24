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
package org.socialworld.objects.connections;

import org.socialworld.objects.SimulationObject;

public class Connection {

	private boolean object1IsMaster = false;
	
	private SimulationObject object1;
	private SimulationObject object2;
	
	public ConnectionType type;
	
	public Connection(SimulationObject object1, SimulationObject object2, ConnectionType type) {
		this.object1 = object1;
		this.object2 = object2;
		this.type = type;
	}

	public void setAsMasterSlaveConnection() {
			this.object1IsMaster = true;
	}

	
	public boolean isMasterSlaveConnection() {
		return this.object1IsMaster;
	}
	
	public SimulationObject getObject1() {
		return this.object1;
	}

	public SimulationObject getObject2() {
		return this.object2;
	}
	
}
