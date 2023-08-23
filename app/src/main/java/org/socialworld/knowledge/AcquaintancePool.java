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
package org.socialworld.knowledge;
import org.socialworld.objects.Human;

public class AcquaintancePool {
	final int MAXIMUM_ACQUAINTANCE_POOL_CAPACITY = 100;

	private Acquaintance acquaintanceList[];

	private int accessCount[];
	
	private int maxAccessCount;
	private int maxAccessCountIndex;
	
	public AcquaintancePool() {
		acquaintanceList = new Acquaintance[MAXIMUM_ACQUAINTANCE_POOL_CAPACITY];
		accessCount = new int[MAXIMUM_ACQUAINTANCE_POOL_CAPACITY];
		
		maxAccessCount = 0;
		maxAccessCountIndex = 0;
		
	}
	
	public int addAcquaintance(Acquaintance acquaintance) {
		int index;
		
		index = indexForNewEntry();
		acquaintanceList[index] = acquaintance;
		accessCount[index] = 0;
		
		return index;
		
	}

	public Acquaintance getAcquaintance(Human human) {
		int index;
		
		for (index = 0; index < MAXIMUM_ACQUAINTANCE_POOL_CAPACITY; index++ ) {
			if (acquaintanceList[index].isAcquaintanceOfHuman(human)) {
				accessCount[index]++;
				if (accessCount[index] > maxAccessCount) {
					maxAccessCount = accessCount[index];
					maxAccessCountIndex = index;
				}
				return acquaintanceList[index];
			}
		}
		return null;
	}

	public Acquaintance getAcquaintance(int index) {
		if ( index >= 0 & index < MAXIMUM_ACQUAINTANCE_POOL_CAPACITY) {
			accessCount[index]++;
			if (accessCount[index] > maxAccessCount) {
				maxAccessCount = accessCount[index];
				maxAccessCountIndex = index;
			}
			return acquaintanceList[index];
		}
		else
			return null;
	}

	private int indexForNewEntry() {
		Acquaintance acquaintance;
		int fewestAccess;
		int indexWithFewestAccess;
		
		// find empty or invalid entry
		for (int i = 0; i < MAXIMUM_ACQUAINTANCE_POOL_CAPACITY;i++) {
			acquaintance = acquaintanceList[i];
			if ((acquaintance == null) | (!acquaintance.isValid()) ) return i;
		}
		
		
		// find entry with fewest access
		fewestAccess = maxAccessCount + 1;
		indexWithFewestAccess = maxAccessCountIndex;
		for (int i = 0; i < MAXIMUM_ACQUAINTANCE_POOL_CAPACITY;i++) {
			if ( accessCount[i] < fewestAccess) {
				indexWithFewestAccess = i;
				fewestAccess = accessCount[i];
			}
		}
		return indexWithFewestAccess;
		
	}
	
}
