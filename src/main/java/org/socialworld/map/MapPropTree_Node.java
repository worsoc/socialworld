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

import org.socialworld.GlobalSwitches;



/**
 * @author Mathias Sikos
 *
 */
public class MapPropTree_Node {
	private MapPropTree_Node sectorNodes[];
	private MapPropTree tree;
	
	private int level;

	private LinkedList<IMapProp> properties;
	
	
	protected MapPropTree_Node(MapPropTree tree, int level)  {
		this.tree = tree;
		this.level = level;
		properties = new LinkedList<IMapProp>();
		createSectorNodesArray(tree.getBase());
	}
	
	protected void createSectorNodesArray(int base) {
		createSectorNodesArray(new MapPropTree_Node[base]);
	}

	protected final void createSectorNodesArray(MapPropTree_Node[] nodes) {
		if (this.sectorNodes == null) this.sectorNodes = nodes;
	}
	
	protected final void setNewNode(int sector) {
		setNode(sector, createNewNode());
	}
	
	protected MapPropTree_Node createNewNode() {
		return new MapPropTree_Node(this.tree, level + 1);
	}

	private void setNode(int sector, MapPropTree_Node node ) {
		int base = tree.getBase();
		
		if (base == 9)
			if (sector > 0 & sector < 10)		sectorNodes[sector - 1] = node;
		if (base == 25)
			if (sector > 0 & sector < 26)		sectorNodes[sector - 1] = node;
		if (base == 27)
			if (sector > 0 & sector < 28)		sectorNodes[sector - 1] = node;
		
	}
	
	protected MapPropTree getTree() { return tree; }
	
	protected int getLevel() { return level; }
	
	protected LinkedList<IMapProp> getProperties(String locationRest) {
		
		int sector;
		LinkedList<IMapProp> list = new LinkedList<IMapProp>();

		
		sector = tree.getSector(locationRest);
		if ( (sector == -1) | (sector == 0) ) {
		//	list.add(tree.getPropertyNothing());
		}
		else {
			
			if (locationRest.length() == 1) {
				// last address sign --> return node's properties
				list.addAll(this.properties);
			}
			else {
				if (sectorNodes[sector - 1] == null) {
					if (GlobalSwitches.OUTPUT_DEBUG_VARIABLE_IS_NULL) {
						System.out.println("MapPropTree_Node.getProperty(): sectorNodes[sector - 1] is null ");
					}
				//	list.add(tree.getPropertyNothing());
				}
				else {
					list = sectorNodes[sector - 1].getProperties(locationRest.substring(1));
				}
			}
		}

		return list;
	}

	protected LinkedList<IMapProp> getCollection(String locationRest, int higherLevelToAdd) {
		
		int sector;
		LinkedList<IMapProp> collection = null;

		sector = tree.getSector(locationRest);

		if (sector >= 0) {
			
			if (sector == 0) {
				collection = getCollection();
			}
			
			if (sector > 0) {
			

				if  (sectorNodes[sector - 1] == null) {
					// return empty collection, 
					// because  there is no child sector
					// in consequence there are no properties in the calculated sector
					collection = new LinkedList<IMapProp>();
				}
				else {
					if (locationRest.length() == 1)
						collection = sectorNodes[sector - 1].getCollection("", higherLevelToAdd);
					else
						collection = sectorNodes[sector - 1].getCollection(locationRest.substring(1), higherLevelToAdd);
				}
				
				if (this.level > higherLevelToAdd && this.properties != null) {
					collection.addAll(this.properties);
				}


			}

		}
		else {
			collection = new LinkedList<IMapProp>();
		}
		
		if (collection.isEmpty()) {
			//collection.add(tree.getPropertyNothing());
		}
		
		return collection;
	}
	
	protected LinkedList<IMapProp> getCollection() {
		int index;
		int base;
		LinkedList<IMapProp> collection = new LinkedList<IMapProp>();
		
		if (this.properties != null) collection.addAll(this.properties);
		
		base = tree.getBase();
		
		for (index = 0; index < base; index++) {
			if (sectorNodes[index] != null) {
				collection.addAll(sectorNodes[index].getCollection());
			}
		}
		
		return collection;
	}

	

	protected MapPropTree_Node addProperty(IMapProp property, String locationRest) {
		int sector;

		sector = tree.getSector(locationRest);
		
		if ( sector == 0 ) {
			return addProperty(property);
		}
		else {
			
			if (sectorNodes[sector - 1] == null) setNewNode(sector);
			
			return sectorNodes[sector - 1].addProperty(property, locationRest.substring(1));

		}
		
	}

	
	private MapPropTree_Node addProperty(IMapProp property) {
		
		this.properties.add(property);

		return this;
	}
	

	protected void removeProperties() {
		this.properties.clear();
	}

	protected void removeProperty(IMapProp propLike) {
		IMapProp propToRemove = null;
		for (IMapProp prop: properties) {
			if (prop.equals(propLike)) {
				propToRemove = prop;
				break;
			}
		}
		if (propToRemove != null) {
			properties.remove(propToRemove);
		}
	}
	

} 
