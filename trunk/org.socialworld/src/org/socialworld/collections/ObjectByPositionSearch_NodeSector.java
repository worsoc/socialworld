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

import java.util.ArrayList;

import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos
 *
 */
public class ObjectByPositionSearch_NodeSector {

	private ObjectByPositionSearch_NodeSector sectorNodes[];
	private int base;
	
	private ArrayList<SimulationObject> objects;

	private boolean isLeaf = false;

	protected ObjectByPositionSearch_NodeSector(int base)  {
		
		this.base = base;
		sectorNodes = new ObjectByPositionSearch_NodeSector[base];
		
	}
	
	protected void setNode(int sector, ObjectByPositionSearch_NodeSector node ) {
		
		if (base == 9)
			if (sector > 0 & sector < 26)		sectorNodes[sector - 1] = node;
		if (base == 25)
			if (sector > 0 & sector < 26)		sectorNodes[sector - 1] = node;
		
	}
	 

	protected ObjectByPositionSearch_NodeSector setToLeaf(ArrayList<SimulationObject> objects) {
		isLeaf = true;
		this.objects = objects;
		return this;
	}
	
	protected ObjectByPositionSearch_NodeSector insertObject(SimulationObject object) {
		String location;

		if (base == 9) {
			location = 	Integer.toString(object.getPosition().getLocationByBase9());	
			}
		else if (base == 25) {
			location = 	object.getPosition().getLocationByBase25();	
			}
		else
			 return null;
		
		return addObject(object, location);

	}
	
	private ObjectByPositionSearch_NodeSector addObject(SimulationObject object, String locationRest) {
		int sector;

		sector = getSector(locationRest);
		
		if ( (sector == 0) | locationRest.length() == 0 ) return addObject(object);
		else return sectorNodes[sector - 1].addObject(object, locationRest.substring(1));
		
	}

	private ObjectByPositionSearch_NodeSector addObject(SimulationObject object) {
		objects.add(object);
		return this;
	}
	
	protected void removeObject(SimulationObject object) {
		objects.remove(object);
	}
	
	protected ArrayList<SimulationObject> getObjects(String locationRest) {
		
		int sector;
		
		if ( (locationRest.length() == 0) | (this.isLeaf) ) return objects;
		
		sector = getSector(locationRest);
		if (sector == -1) 		return null;

		if (sector == 0) return getObjects();
		else return sectorNodes[sector - 1].getObjects(locationRest.substring(1));

		
	}

	protected ArrayList<SimulationObject> getObjects() {
		int index;
		ArrayList<SimulationObject> objects = new ArrayList<SimulationObject>();
		
		if (this.objects != null) objects.addAll(this.objects);
		
		for (index = 0; index < base; index++) {
			objects.addAll(sectorNodes[index].getObjects());
		}
		
		return objects;
	}
	
	private int getSector(String locationRest) {

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
