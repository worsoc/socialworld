/*
* Social World
* Copyright (C) 2022  Mathias Sikos
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
package org.socialworld.tools;

public class StringTupel {

	
	private String[] tupel = new String[0];
	
	public StringTupel(String[] tupel) {
		this.tupel = tupel;
	}
	
	public StringTupel(String left, String right) {
		this.tupel = new String[2];
		this.tupel[0] = left;
		this.tupel[1] = right;
	}
	
	public String get(int index) {
		if (this.tupel.length > index) 
			return this.tupel[index];
		else
			return "";
	}
	
	// deprecated methods
	
	public String getLeft() { 
		if (this.tupel.length > 0) 
			return this.tupel[0];
		else
			return "";
		}
	
	public String getRight() { 
		if (this.tupel.length > 1) 
			return this.tupel[1];
		else
			return "";
	}

}
