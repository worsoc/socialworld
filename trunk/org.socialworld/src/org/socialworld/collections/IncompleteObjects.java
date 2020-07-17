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
package org.socialworld.collections;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.core.IncompleteSimulationObject;

public class IncompleteObjects {

	private final List<IncompleteSimulationObject> incompleteObjects;

	private int freePosition;
	
	public static IncompleteSimulationObject nothing = new IncompleteSimulationObject();
	
	public IncompleteObjects() {
		
		this.incompleteObjects = new ArrayList<IncompleteSimulationObject>();
		freePosition = 0;
			
	}
	
	public int add(IncompleteSimulationObject incompleteObject) {
		
		int position;
		
		if (this.freePosition < this.incompleteObjects.size()) {
			position = this.freePosition;
			this.incompleteObjects.set(position, incompleteObject);
			this.freePosition = getNextFree();
		}
		else {
			this.incompleteObjects.add(incompleteObject);
			this.freePosition = this.incompleteObjects.size();
			position = this.freePosition  - 1;
		}
		
		return position;
		
	}
	
	public IncompleteSimulationObject remove(int position, int objectID) {
		
		IncompleteSimulationObject incompleteObject = this.incompleteObjects.get(position);
		
		if (incompleteObject.isValid()) {
			if (incompleteObject.getObjectID() == objectID) {
				this.incompleteObjects.set(position, nothing);
				if (position < this.freePosition) {
					this.freePosition = position;
				}
				return incompleteObject;
			}
		}
		
		return nothing;
		
	}
	
	 
	public IncompleteSimulationObject get(int position) {
		
		if (position >= 0 && position < this.incompleteObjects.size())
			return this.incompleteObjects.get(position);
		else
			return nothing;
		
	}
	
	private int getNextFree() {
		
		for (int index = 0; index < this.incompleteObjects.size(); index++) {
			if (!this.incompleteObjects.get(index).isValid()) return index;
		}
		
		return this.incompleteObjects.size();
	}
	
}
