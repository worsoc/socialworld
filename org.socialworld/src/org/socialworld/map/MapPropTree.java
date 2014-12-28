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
package org.socialworld.map;

import java.util.ArrayList;

/**
 * @author Mathias Sikos
 *
 */
public class MapPropTree {
	private MapPropTree_Node root;
	private int base;
	private int accuracy;
	
	
	public MapPropTree(int base, int accuracy) {
		this.base = base;
		this.accuracy = accuracy;
		createRoot();
	}
	
	private void createRoot() {
		this.root = new MapPropTree_Node(this, 1);
	}
	
	protected MapPropTree_Node getRoot() {return this.root;}
	
	public IMapProp getProperty(String location) {
		return root.getProperty(location);
	}
	
	public ArrayList<IMapProp> getCollection(String location) {
		return root.getCollection(location);
	}
	
	protected int getBase() {return base;}
	protected int getAccuracy() {return accuracy;}
	
	protected int getSector(String locationRest) {

		char firstSign;
		int sector = -1;
		
		if (locationRest.length() > 0) {
			firstSign = locationRest.charAt(0);
			
			if (firstSign == '0') sector = 0;
			else {
				if (base == 9) {
					//  48 ... ASCII-Offset to sector number for digits
					sector = (int) firstSign - 48; 
				}	
				else if (base == 25) {
					// 64 ... ASCII-Offset to sector number for upper letters
					sector = (int) firstSign - 64; 
				}
			}
		}
		
		return sector;

	}
}
