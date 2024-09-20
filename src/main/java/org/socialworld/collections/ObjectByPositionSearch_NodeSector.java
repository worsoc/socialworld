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


import java.util.LinkedList;
import java.util.ListIterator;

import org.socialworld.calculation.SimulationCluster;
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
	 
	protected void createSectorNodesArray(int base) {
		createSectorNodesArray(new ObjectByPositionSearch_NodeSector[base]);
	}

	protected MapPropTree_Node createNewNode() {
		return new ObjectByPositionSearch_NodeSector((ObjectByPositionSearch_TreeSector) getTree(), getLevel() + 1);
	}

	protected ObjectByPositionSearch_NodeSector insertObject(SimulationObject object) {
		String location;

		if ( ((ObjectByPositionSearch_TreeSector) getTree()).getBase() == 9) {
			location = 	Integer.toString(object.getPosition(SimulationCluster.objectMaster).getLocationByBase9());	
			}
		else if ( ((ObjectByPositionSearch_TreeSector) getTree()).getBase() == 25) {
			location = 	object.getPosition(SimulationCluster.objectMaster).getLocationByBase25();	
			}
		else
			 return null;
		
		return (ObjectByPositionSearch_NodeSector) addProperty(new MapPropSimulationObject(object), location);

	}
	
	protected LinkedList<SimulationObject> getObjects(String location) {
		LinkedList<SimulationObject> result;
		
		IMapProp elem;
		
		LinkedList<IMapProp> tmpCollection;
		tmpCollection = getCollection(location);

		
		result = new LinkedList<SimulationObject>();

		if (tmpCollection.size() > 0) {
			ListIterator<IMapProp> iterator = tmpCollection.listIterator();
			while (iterator.hasNext()) {
				elem = iterator.next();
				if (elem != null) {
					result.add( ( (MapPropSimulationObject)elem ).getObject() );
				}
			}
		}
		
		return result;
		
	}
	
	protected void removeObject(SimulationObject object) {
		removeProperty(new MapPropSimulationObject(object));
	}
	

	
	
	

}
