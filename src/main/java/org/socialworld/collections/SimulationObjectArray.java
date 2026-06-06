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
package org.socialworld.collections;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.SimulationObject;

public class SimulationObjectArray {
	private SimulationObject array[];
	
	public SimulationObjectArray(int capacity) {
		array = new SimulationObject[capacity];
	}
	
	public void ensureCapacity(int minCapacity) {
		
		if (minCapacity > this.array.length) {
			// Falls das Limit gesprengt wird: Großzügig erweitern (Faktor 1.5 oder 2)
			int newCapacity = Math.max(this.array.length * 2, minCapacity);
			SimulationObject[] tmpArray = new SimulationObject[newCapacity];
			System.arraycopy(this.array, 0, tmpArray, 0, this.array.length);
			this.array = tmpArray;
			
			System.out.println("[WARN] SimulationObjectArray erweitert auf: " + newCapacity);
		}
	}
	
	public void increaseCapacity(int increase)  {
		int newCapacity;
		newCapacity = this.array.length + increase;
		ensureCapacity(newCapacity);
	}

	public void set(int index, SimulationObject object) {
		if (index >= this.array.length) ensureCapacity(index + 1);
		array[index] = object;
	}
	
	public SimulationObject get(int index){
		if (index >= 0 && index < this.array.length && array[index] != null) {
			return array[index];
		}
		return NoSimulationObject.getObjectNothing();
	}
	
	public void delete(int index) {
		if (index >= 0 && index < this.array.length) {
			array[index] = null;
		}
	}
}
