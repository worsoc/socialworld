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
import java.util.ListIterator;

import org.socialworld.map.IMapProp;
import org.socialworld.map.MapPropTree_Node;
import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos
 *
 */
public class ObjectByPositionSearch_NodeSector extends MapPropTree_Node{

	
	protected ObjectByPositionSearch_NodeSector(ObjectByPositionSearch_TreeSector tree, int level)  {
			
			super(tree, level);
		}
	 

	
	protected ObjectByPositionSearch_NodeSector insertObject(SimulationObject object) {
		String location;

		if ( ((ObjectByPositionSearch_TreeSector) getTree()).getBase() == 9) {
			location = 	Integer.toString(object.getPosition().getLocationByBase9());	
			}
		else if ( ((ObjectByPositionSearch_TreeSector) getTree()).getBase() == 25) {
			location = 	object.getPosition().getLocationByBase25();	
			}
		else
			 return null;
		
		return (ObjectByPositionSearch_NodeSector) setProperty(new MapPropSimulationObject(object), location);

	}
	
	protected ArrayList<SimulationObject> getObjects(String location) {
		ArrayList<SimulationObject> result;
		
		ArrayList<IMapProp> tmpCollection;
		tmpCollection = getCollection(location);

		result = new ArrayList<SimulationObject>(tmpCollection.size());

		ListIterator<IMapProp> iterator = tmpCollection.listIterator();
		while (iterator.hasNext()) {
			result.add( ( (MapPropSimulationObject) iterator.next() ).getObject() );
		}
		
		return result;
		
	}
	
	protected void removeObject() {
		removeProperty();
	}
	

	
	
	

}
