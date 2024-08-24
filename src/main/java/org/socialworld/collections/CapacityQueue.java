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

public class CapacityQueue<Type> {

	private String name;
	
	private Type array[];
	int capacity;
	int indexAdd;
	int indexRemove;
	int size;
	
	public CapacityQueue (String name, int capacity) {
		
		this.name = name;
		
		this.array = (Type[]) new Object[capacity];
		this.capacity = capacity;
		this.indexAdd = 0;
		this.indexRemove = 0;
		this.size = 0;
		
	}

	public boolean add(Type element) {
		
		if (this.size == this.capacity) {
			System.out.println("------------------------");
			System.out.println("CapacityQueue " + this.name + " is full!!!");
			System.out.println("------------------------");
			return false;
		}
		else {
			this.array[this.indexAdd] = element;
			this.indexAdd++;
			if (this.indexAdd == this.capacity) {
				this.indexAdd = 0;
				setSize();
				System.out.println("------------------------");
				System.out.println("CapacityQueue " + this.name + "(Add; size = " + this.size + ") starts from index 0 (indexRemove: " + this.indexRemove + ")");
				System.out.println("------------------------");
			}
			else {
				setSize();
			}
			
			if (this.size > this.capacity) {
				System.out.println("------------------------");
				System.out.println("CapacityQueue " + this.name + " is SUPER FULL (size > capacity)");
				System.out.println("------------------------");
			}

			return true;
		}
	}
	
	public Type remove() {
		
		Type remove;
		if (this.size == 0) {
			remove = null;
		}
		else {
			remove = this.array[this.indexRemove];
			this.indexRemove++;
			if (this.indexRemove == this.capacity) {
				this.indexRemove = 0;
				setSize();
				System.out.println("------------------------");
				System.out.println("CapacityQueue " + this.name + "(Remove; size = " + this.size + ") starts from index 0 (indexAdd: " + this.indexAdd + ")");
				System.out.println("------------------------");
			}
			else {
				setSize();
			}
		}
		
		if (this.size < 0) {
			System.out.println("------------------------");
			System.out.println("CapacityQueue " + this.name + " is SUPER EMPTY (size < 0)");
			System.out.println("------------------------");
		}
		
		return remove;
		
	}
	
	private void setSize() {
		if (this.indexAdd >= this.indexRemove) this.size = this.indexAdd - this.indexRemove;
		else  this.size = this.capacity + this.indexAdd - this.indexRemove;
	}
	
	public int size() {
		return this.size;
	}

}
