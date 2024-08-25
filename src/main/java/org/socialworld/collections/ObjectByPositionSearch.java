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

import org.socialworld.attributes.Position;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.objects.SimulationObject;

/**
 * The class ObjectByPositionSearch holds
 * 
 * 1) a search tree for simulation objects.
 * The tree's basis is 4 for four directions which can be described by position's X and Y value
 * - 1 ... smaller and equal X , smaller and equal Y
 * - 2 ... smaller and equal X , greater Y
 * - 3 ... greater X , smaller and equal Y
 * - 4 ... greater X , greater Y
 * The tree returns the nearest objects for a position. 
 * Therefore it finds the nearest leaf.
 * It first returns the leaf values (objectIDs) of nearest (best) leaf's parent,
 *  then the further leafs of the parent of the parent and so on.
 *  
 *  2) a search tree for simulation objects
 *  The tree's basis is 9 for 9 sectors similar to the number keypad
 *      7    8    9
 *      4    5    6
 *      1    2    3
 *    An object's position is described by a code that holds 9 sector numbers. 
 *    (a number sequence with length 9) 
 *    The sector number 0 means, that there is no more detailed description.
 *    
 *  3) a search tree for simulation objects
 *  The tree's basis is 25 for 25 sectors with an arrangement as shown next
 *     21   22   23   24   25
 *     16   17   18   19   20
 *     11   12   13   14   15
 *      6    7    8    9   10
 *      1    2    3    4    5
 *      
 *    The sector numbers are coded by upper letters
 *      U    V    W    X    Y
 *      P    Q    R    S    T
 *      K    L    M    N    O
 *      F    G    H    I    J
 *      A    B    C    D    E
 *    An object's position is described by a code that holds 9 sector number (letters).
 *    (a letter sequence with length 9) 
 *    The sector number 0 means, that there is no more detailed description.
 *    
 * @author Mathias Sikos (MatWorsoc)
 * 
 */
public class ObjectByPositionSearch {
	private ObjectByPositionSearch_NodeXY root;
	private ObjectByPositionSearch_NodeXY found;

	private ObjectByPositionSearch_TreeSector sectorTreeBase9;
	private ObjectByPositionSearch_TreeSector sectorTreeBase25;
	
	private ArrayList<ObjectByPositionSearch_Nodes> allNodesByObjectID;
	
	private int size;
	
	
	public ObjectByPositionSearch (int capacity) {
		root = new ObjectByPositionSearch_NodeXY(null, 0, 0, 0, null,null,null,null);;
		sectorTreeBase9 = new ObjectByPositionSearch_TreeSector(9, 9);
		sectorTreeBase25 = new ObjectByPositionSearch_TreeSector(25, 9);

		allNodesByObjectID = new ArrayList<ObjectByPositionSearch_Nodes>(capacity);
		size = capacity;
		
	}
	
	public ArrayList<SimulationObject> getObjectsByBase9(String location) {
		return sectorTreeBase9.getObjects(location);
	}

	public ArrayList<SimulationObject> getObjectsByBase25(String location) {
		return sectorTreeBase25.getObjects(location);
	}

	public void removeObject(SimulationObject object) {
		ObjectByPositionSearch_NodeXY node;
		ObjectByPositionSearch_NodeXY oldParent;
		
		ObjectByPositionSearch_Nodes nodes;
		
		int objectID;
		int oldChildNr;

		objectID = object.getObjectID();
		
		if (objectID < size){
			
			nodes = allNodesByObjectID.get(objectID);
			
			node = nodes.getXYNode();
			if (node != null) {
				oldParent = node.getParent();
				oldChildNr = node.getChildNr();
				
				oldParent.clearChild(oldChildNr);
			}
			
			nodes.getBase9Node().removeObject();
			nodes.getBase25Node().removeObject();
			
			nodes.setInvalid();
		}
	}
	
	public void addObject(SimulationObject object ) {
		ObjectByPositionSearch_Nodes nodes;

		int objectID;
		Position position = Position.getObjectNothing();
		int x;
		int y;
		
		
		// if there is no position, no object is added to the position search tree
		if (object.getPosition(SimulationCluster.objectMaster) == Position.getObjectNothing()) return;
				
		objectID = object.getObjectID();
		if (size <= objectID) 	ensureCapacity(objectID + 1000);
		
		position = object.getPosition(SimulationCluster.objectMaster);
		x = position.getX(SimulationCluster.objectMaster);
		y = position.getY(SimulationCluster.objectMaster);
		
		nodes = new ObjectByPositionSearch_Nodes(
					addNode(objectID, x, y),
					sectorTreeBase9.insertObject(object),
					sectorTreeBase25.insertObject(object)
				);
		
		allNodesByObjectID.set(objectID, nodes);
	}
	
	public void changePosition(SimulationObject object) {
		removeObject(object);
		addObject(object);
	}

	/*
	public void reset() {
		while (found != root) {
			found.resetGetNode();
			found = found.getParent();
		}
		found.resetGetNode();
	}
	*/
	
	public void findNearestObject (double findX, double findY) {
		found = findNearestNoLeaf(findX, findY);
	}
	
	public int getNextObjectID() {
		ObjectByPositionSearch_NodeXY tmp;
		int nextID = 0;
		
		if (found == null) found = root;
		
		while (nextID == 0 && found != null) {
			tmp = found.getNode();
			
			while (tmp != null) {
				found = tmp;
				tmp = found.getNode();
			}
			
			if (found.isLeaf()) 				nextID = found.getObjectID();
			found = found.getParent();
		}
		return nextID;
	}
	
	private ObjectByPositionSearch_NodeXY addNode(int objectID, int posX, int posY) {
		ObjectByPositionSearch_NodeXY created;
		ObjectByPositionSearch_NodeXY parent;
		ObjectByPositionSearch_NodeXY oldLeaf;
		ObjectByPositionSearch_NodeXY newLeaf;

	
		int childNr;

		int x;
		int y;

		parent = findNearestNoLeaf(posX, posY);
		oldLeaf = parent.getNode(posX, posY);
		childNr = parent.getChildNr(posX, posY);

		newLeaf = new ObjectByPositionSearch_NodeXY(parent, childNr, objectID);
		newLeaf.setX(posX);
		newLeaf.setY(posY);
		
		if (oldLeaf == null) {
			parent.setChild(childNr, newLeaf);
		}
		else {
			if (newLeaf.getX() <= oldLeaf.getX() ) {
				x = newLeaf.getX();
				if (newLeaf.getY() <= oldLeaf.getY() ) {
					y = newLeaf.getY();
					created = new ObjectByPositionSearch_NodeXY(parent, childNr, x, y, newLeaf,null,null,oldLeaf);
				}
				else {
					y = oldLeaf.getY();
					created = new ObjectByPositionSearch_NodeXY(parent, childNr, x, y, null,newLeaf,oldLeaf,null);
				}
			}
			else {
				x = oldLeaf.getX();
				if (newLeaf.getY() <= oldLeaf.getY() ) {
					y = newLeaf.getY();
					created = new ObjectByPositionSearch_NodeXY(parent, childNr, x, y, null,oldLeaf,newLeaf,null);
				}
				else {
					y = oldLeaf.getY();
					created = new ObjectByPositionSearch_NodeXY(parent, childNr, x, y, oldLeaf,null,null,newLeaf);
				}
			}
			parent.setChild(childNr, created);
			newLeaf.setParent(created);
			oldLeaf.setParent(created);
		}
		return newLeaf;
	}
	
	
	private ObjectByPositionSearch_NodeXY findNearestNoLeaf(double findX, double findY) {
		ObjectByPositionSearch_NodeXY last;
		ObjectByPositionSearch_NodeXY next;
		
		
		last = null;
		next = root.getNode(findX, findY);

		if (next == null) return root;

		while (last != next && next != null) {
			last = next;
			next = next.getNode(findX, findY);
		}
		
		if (last.isLeaf()) return last.getParent();
		else return last;
	}
	
	private void ensureCapacity(int newCapacity) {
		
		ObjectByPositionSearch_Nodes dummy;
		
		int index;
		int listSize;
		
		listSize = allNodesByObjectID.size();
		
		if (newCapacity > listSize) {
			
			dummy = new ObjectByPositionSearch_Nodes();
			
			for (index = 0; index < newCapacity - listSize; index++) {
				allNodesByObjectID.add(dummy);
			}
			
			size = newCapacity;
		}
	}
}
