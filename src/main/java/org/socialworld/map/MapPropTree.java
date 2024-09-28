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

import java.util.LinkedList;

/**
 * @author Mathias Sikos
 *
 */
public class MapPropTree {
	private MapPropTree_Node root;
	private int base;
	private int accuracy;
	
	private IMapProp propertyNothing;

	
	
	public MapPropTree(int base, int accuracy, IMapProp propertyNothing) {
		this.base = base;
		this.accuracy = accuracy;
		this.propertyNothing = propertyNothing;
		createRoot();
	}
	
	public MapPropTree(int base, int accuracy) {
		this.base = base;
		this.accuracy = accuracy;
		this.propertyNothing = null;
		createRoot();
	}

	protected void createRoot() {
		setRoot( new MapPropTree_Node(this, 1));
	}
	
	protected final void setRoot(MapPropTree_Node root) {
		if (this.root == null) this.root = root;
	}

	protected MapPropTree_Node getRoot() {return this.root;}
	
	public LinkedList<IMapProp> getProperties(String location) {
		return root.getProperties(location);
	}
	
	public void addProperty (IMapProp property, String location) {
		root.addProperty(property, location);
	}

	public LinkedList<IMapProp> getCollection(String location) {
		return root.getCollection(location, 99);
	}

	public LinkedList<IMapProp> getCollection(String location, int higherLevelToAdd) {
		return root.getCollection(location, higherLevelToAdd);
	}
	
	protected int getBase() {return base;}
	protected int getAccuracy() {return accuracy;}
	IMapProp getPropertyNothing() {return  propertyNothing;}
	
	protected int getSector(String locationRest) {

		char firstSign;
		int firstSign2Int;
		int sector = -1;
		
		if (locationRest.length() == 0) sector = 0;
		
		if (locationRest != null && locationRest.length() > 0) {
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
				else if (base == 27) {
					// 64 ... ASCII-Offset to sector number for upper letters
					// there are only 26 letters, we use sign # for the inner sector (number 14)
					firstSign2Int = (int) firstSign;
					if (firstSign2Int == 35) 	 sector  = 14;
					else if (firstSign2Int < 78) sector = firstSign2Int - 64; 
					else 						 sector = firstSign2Int - 64 + 1;
				}
			}
		}
		
		return sector;

	}
}
