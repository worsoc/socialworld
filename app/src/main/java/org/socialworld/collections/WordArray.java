/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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

import org.socialworld.conversation.Word;

/**
 * @author Mathias Sikos
 *
 */
public class WordArray {
	private Word array[];
	int capacity;

	public WordArray(int capacity) {
		array = new Word[capacity];
		this.capacity = capacity;
	}
	
	public void ensureCapacity(int capacity) {
		Word tmpArray[];
		
		if (capacity > this.capacity) {
			tmpArray = new Word[capacity];
			
			for (int index = 0; index < this.capacity; index++) {
				tmpArray[index] = this.array[index];
			}
			this.array = tmpArray;
			this.capacity = capacity;
		}
	}
	
	public void increaseCapacity(int increase)  {
		int newCapacity;
		newCapacity = this.capacity + increase;
		ensureCapacity(newCapacity);
	}

	public void set(int index, Word element) {
		if (index >= this.capacity) ensureCapacity(index + 1);
		array[index] = element;
	}
	
	public Word get(int index){
		if (index < this.capacity) return array[index];
		else return null;
	}
	
	public void delete(int index) {
		if (index < this.capacity) array[index] = null;
	}
}
