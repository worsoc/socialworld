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

import org.socialworld.GlobalSwitches;



/**
 * @author Mathias Sikos
 *
 */
public class MapPropTree_Node {
	private MapPropTree_Node sectorNodes[];
	private MapPropTree tree;
	
	private int level;

	private IMapProp property;
	private boolean isLeaf = false;
	
	
	protected MapPropTree_Node(MapPropTree tree, int level)  {
		this.tree = tree;
		this.level = level;
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

	protected void setNode(int sector, MapPropTree_Node node ) {
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
	
	protected IMapProp getProperty(String locationRest) {
		
		int sector;
		
		if ( this.isLeaf  ) return property;
		
		sector = tree.getSector(locationRest);
		if ( (sector == -1) | (sector == 0) )    		return null;
		else {
			if (sectorNodes[sector - 1] == null) {
				if (GlobalSwitches.OUTPUT_DEBUG_VARIABLE_IS_NULL) {
					System.out.println("MapPropTree_Node.getProperty(): sectorNodes[sector - 1] is null ");
				}
				return null;
			}
			else {
				return sectorNodes[sector - 1].getProperty(locationRest.substring(1));
			}
		}

		
	}

	protected ArrayList<IMapProp> getCollection(String locationRest) {
		
		int sector;
		ArrayList<IMapProp> collection;
		
		if ( (locationRest.length() == 0) | (this.isLeaf) ) {
			collection = new ArrayList<IMapProp>(1);
			collection.add(property);
			return collection;
		}
		
		sector = tree.getSector(locationRest);
		if (sector == -1) 		return null;

		if (sector == 0) return getCollection();
		else return sectorNodes[sector - 1].getCollection(locationRest.substring(1));

	}
	
	protected ArrayList<IMapProp> getCollection() {
		int index;
		int base;
		ArrayList<IMapProp> collection = new ArrayList<IMapProp>();
		
		if (this.property != null) collection.add(this.property);
		
		base = tree.getBase();
		
		for (index = 0; index < base; index++) {
			collection.addAll(sectorNodes[index].getCollection());
		}
		
		return collection;
	}

	

	protected MapPropTree_Node setProperty(IMapProp property, String locationRest) {
		int sector;

		sector = tree.getSector(locationRest);
		
		if ( (sector == 0) | locationRest.length() == 0 ) return setProperty(property);
		
		if (sectorNodes[sector - 1] == null) setNewNode(sector);
		return sectorNodes[sector - 1].setProperty(property, locationRest.substring(1));
		
	}

	
	protected MapPropTree_Node setProperty(IMapProp property) {
		isLeaf = true;
		this.property = property;

		return this;
	}
	

	protected void removeProperty() {
		isLeaf = false;
		this.property = null;
	}

}
