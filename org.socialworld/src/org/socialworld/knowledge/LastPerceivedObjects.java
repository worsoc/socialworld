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
package org.socialworld.knowledge;

import org.socialworld.collections.RecentlyList;
import org.socialworld.objects.SimulationObject;

public class LastPerceivedObjects {

	private RecentlyList<SimulationObject> lastSeenObjects;
	private RecentlyList<SimulationObject> lastHeardObjects;
	private RecentlyList<SimulationObject> lastSmelledObjects;
	
	public LastPerceivedObjects(int capacity) {
		
		this.lastSeenObjects = new RecentlyList<SimulationObject>(capacity);
		this.lastHeardObjects = new RecentlyList<SimulationObject>(capacity);
		this.lastSmelledObjects = new RecentlyList<SimulationObject>(capacity);
		
	}
	
	public void addLastSeen(SimulationObject element) {
		this.lastSeenObjects.add(element);
	}
	
	public void addLastHeard(SimulationObject element) {
		this.lastHeardObjects.add(element);
	}

	public void addLastSmelled(SimulationObject element) {
		this.lastSmelledObjects.add(element);
	}

	public SimulationObject getLastSeen() {
		return this.lastSeenObjects.getRecentlyAdded();
	}

	public SimulationObject getLastHeard() {
		return this.lastHeardObjects.getRecentlyAdded();
	}

	public SimulationObject getLastSmelled() {
		return this.lastSmelledObjects.getRecentlyAdded();
	}

}
