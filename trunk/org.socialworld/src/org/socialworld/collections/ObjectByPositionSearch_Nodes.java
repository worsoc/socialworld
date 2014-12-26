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

/**
 * @author Mathias Sikos
 *
 */
public class ObjectByPositionSearch_Nodes {
	ObjectByPositionSearch_NodeXY	xyNode;
	ObjectByPositionSearch_NodeSector base9Node;
	ObjectByPositionSearch_NodeSector base25Node;
	
	boolean valid;
	
	protected ObjectByPositionSearch_Nodes() {
		valid = false;
	}
	
	protected ObjectByPositionSearch_Nodes(
			ObjectByPositionSearch_NodeXY xyNode, 
			ObjectByPositionSearch_NodeSector base9Node, 
			ObjectByPositionSearch_NodeSector base25Node) {
		this.xyNode = xyNode;
		this.base9Node = base9Node;
		this.base25Node = base25Node;
	
		valid = true;
	}
	
	protected void setValid() {
		valid = true;
	}

	protected void setInvalid() {
		valid = false;
	}

	protected void setXYNode(ObjectByPositionSearch_NodeXY xyNode) {
		this.xyNode = xyNode;
	}

	protected void setBase9Node(ObjectByPositionSearch_NodeSector base9Node) {
		this.base9Node = base9Node;
	}

	protected void setBase25Node(ObjectByPositionSearch_NodeSector base25Node) {
		this.base25Node = base25Node;
	}

	protected ObjectByPositionSearch_NodeXY getXYNode() {
		return xyNode;
	}
	
	protected ObjectByPositionSearch_NodeSector getBase9Node() {
		return base9Node;
	}

	protected ObjectByPositionSearch_NodeSector getBase25Node() {
		return base25Node;
	}

}
