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

import org.socialworld.core.ObjectMaster;
/**
 * The class ObjectByPositionSearch_NodeXY describes a node of a search tree for simulation objects.
 * The tree's basis is 4 for four directions which can be described by position's X and Y value
 * - 1 ... smaller and equal X , smaller and equal Y
 * - 2 ... smaller and equal X , greater Y
 * - 3 ... greater X , smaller and equal Y
 * - 4 ... greater X , greater Y
 * So every no leaf node has 4 child nodes
 *  and a x and y value for deciding in which child tree the nearest object will be found.
 *  
 *  Every leaf node has a value for the objectID. 
 *  Here the x and y values are the position values of the object according to objectID.
 * 
 * @author Mathias Sikos (MatWorsoc)
 * 
 */
public class ObjectByPositionSearch_NodeXY {
	protected static final int SMALLEREQUALX_SMALLEREQUALY = 1;
	protected static final int SMALLEREQUALX_GREATERY = 2;
	protected static final int GREATERX_SMALLEREQUALY = 3;
	protected static final int GREATERX_GREATERY = 4;
	
	private ObjectByPositionSearch_NodeXY smallerEqualX_smallerEqualY;  // childNr 1
	private ObjectByPositionSearch_NodeXY smallerEqualX_greaterY;		 // childNr 2
	private ObjectByPositionSearch_NodeXY greaterX_smallerEqualY;		 // childNr 3	
	private ObjectByPositionSearch_NodeXY greaterX_greaterY;			 // childNr 4
	
	private ObjectByPositionSearch_NodeXY parent;
	private int childNr;

	private int x;
	private int y;
	
	private boolean isLeaf;
	private int[] done = {0,0,0,0};
	private int objectID;

	// just for debugging
	private  ObjectMaster objectMaster = ObjectMaster.getInstance();
	
	public ObjectByPositionSearch_NodeXY(ObjectByPositionSearch_NodeXY parent, int childNr, int objectID) {
		
		this.parent = parent;
		this.childNr = childNr;

		this.isLeaf = true;
		this.objectID = objectID;
		
		this.smallerEqualX_smallerEqualY = null;
		this.smallerEqualX_greaterY = null;
		this.greaterX_smallerEqualY = null;
		this.greaterX_greaterY = null;
	}

	public ObjectByPositionSearch_NodeXY(ObjectByPositionSearch_NodeXY parent, int childNr, int x, int y, 
			ObjectByPositionSearch_NodeXY smallerEqualX_smallerEqualY,
			ObjectByPositionSearch_NodeXY smallerEqualX_greaterY,
			ObjectByPositionSearch_NodeXY greaterX_smallerEqualY,
			ObjectByPositionSearch_NodeXY greaterX_greaterY) {
		
		this.parent = parent;
		this.childNr = childNr;
		
		this.x = x;
		this.y = y;
		this.isLeaf = false;
		
		this.smallerEqualX_smallerEqualY = smallerEqualX_smallerEqualY;
		this.smallerEqualX_greaterY = smallerEqualX_greaterY;
		this.greaterX_smallerEqualY = greaterX_smallerEqualY;
		this.greaterX_greaterY = greaterX_greaterY;
		
		if (smallerEqualX_smallerEqualY != null) smallerEqualX_smallerEqualY.setChildNr(SMALLEREQUALX_SMALLEREQUALY);
		if (smallerEqualX_greaterY != null) smallerEqualX_greaterY.setChildNr(SMALLEREQUALX_GREATERY);
		if (greaterX_smallerEqualY != null) greaterX_smallerEqualY.setChildNr(GREATERX_SMALLEREQUALY);
		if (greaterX_greaterY != null) greaterX_greaterY.setChildNr(GREATERX_GREATERY);
		
	}


	protected ObjectByPositionSearch_NodeXY getNode() {
		if (isLeaf) return null;
		
		if (done[SMALLEREQUALX_SMALLEREQUALY - 1] == 0) {
			done[SMALLEREQUALX_SMALLEREQUALY - 1] = 1;
			if (this.smallerEqualX_smallerEqualY != null) 		return this.smallerEqualX_smallerEqualY;
		}
		if (done[SMALLEREQUALX_GREATERY - 1] == 0) {
			done[SMALLEREQUALX_GREATERY - 1] = 1;
			if (this.smallerEqualX_greaterY != null) 		return this.smallerEqualX_greaterY;
		}
		if (done[GREATERX_SMALLEREQUALY - 1] == 0) {
			done[GREATERX_SMALLEREQUALY - 1] = 1;
			if (this.greaterX_smallerEqualY != null) 		return this.greaterX_smallerEqualY;
		}
		if (done[GREATERX_GREATERY - 1] == 0) {
			done[GREATERX_GREATERY - 1] = 1;
			if (this.greaterX_greaterY != null) 		return this.greaterX_greaterY;
		}
		
		if ( (done[SMALLEREQUALX_SMALLEREQUALY - 1] + done[SMALLEREQUALX_GREATERY - 1] + done[GREATERX_SMALLEREQUALY - 1] + done[GREATERX_GREATERY - 1]) == 4) {
			resetGetNode();
			if (parent != null) {
				parent.setDone(this.childNr);
			}
		}
		
		return null;
		
	}
	
	protected void resetGetNode() {
		done[0] = 0;
		done[1] = 0;
		done[2] = 0;
		done[3] = 0;
	}
	
	private void setDone(int childNr) {
		done[childNr - 1] = 1;
	}
	
	protected int getObjectID() {
		return this.objectID;
	}

	protected ObjectByPositionSearch_NodeXY getNode(double searchX, double searchY) {
		if (this.isLeaf == true) return this;
		if (searchX > this.x) 
			if (searchY > this.y)	return this.greaterX_greaterY;
			else	return this.greaterX_smallerEqualY;
		else if (searchY > this.y)	return this.smallerEqualX_greaterY;
			 else	return this.smallerEqualX_smallerEqualY;
	}

	protected int getChildNr(double searchX, double searchY) {
		if (this.isLeaf == true) return 0;
		if (searchX > this.x) 
			if (searchY > this.y)	return GREATERX_GREATERY;
			else	return GREATERX_SMALLEREQUALY;
		else if (searchY > this.y)	return SMALLEREQUALX_GREATERY;
			 else	return SMALLEREQUALX_SMALLEREQUALY;
	}

	protected void setChild(int childNr, ObjectByPositionSearch_NodeXY child){
		switch (childNr) {
		case SMALLEREQUALX_SMALLEREQUALY: this.smallerEqualX_smallerEqualY = child; break;
		case SMALLEREQUALX_GREATERY: this.smallerEqualX_greaterY = child; break;
		case GREATERX_SMALLEREQUALY: this.greaterX_smallerEqualY = child; break;
		case GREATERX_GREATERY: this.greaterX_greaterY = child; break;
		}
	}
	
	protected void clearChild(int childNr) {
		setChild(childNr, null);
	}

	protected void setParent(ObjectByPositionSearch_NodeXY parent) {
		this.parent = parent;
	}
	
	protected ObjectByPositionSearch_NodeXY getParent() {
		return this.parent;
	}

	protected int getChildNr() { return childNr; }

	protected void setChildNr(int childNr) { this.childNr = childNr; }

	protected boolean isLeaf() {return isLeaf; }
	
	protected int getX() {return x; }

	protected int getY() {return y; }
	
	protected void setX(int x) {this.x = x; }
	
	protected void setY(int y) {this.y = y; }


}

