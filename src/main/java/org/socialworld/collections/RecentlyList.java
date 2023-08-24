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
package org.socialworld.collections;

public class RecentlyList<Type> {

	private Type array[];
	int capacity;
	int current;
	
	public RecentlyList (int capacity) {
		this.array = (Type[]) new Object[capacity];
		this.capacity = capacity;
		this.current = -1;
	}

	public void add(Type element) {
		this.current++;
		if (this.current == this.capacity) {
			this.current = 0;
		}
		this.array[this.current] = element;
	}
	
	public Type getRecentlyAdded() {
		
		if (this.current == -1) {
			return null;
		}
		else {
			return this.array[this.current];
		}
		
	}
	
	public Type[] getAll() {
		
		if (this.current == -1) return null;
		
		Type[] result = (Type[]) new Object[this.capacity];
		int indexArray = current;
		for (int i = 0; i < this.capacity; i++) {
			result[i] = this.array[indexArray];
			indexArray++;
			if(indexArray == this.capacity) {
				indexArray = 0;
			}
		}
		
		return result;
		
	}
	
}
