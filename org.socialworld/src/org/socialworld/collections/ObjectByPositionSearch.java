package org.socialworld.collections;

import org.socialworld.objects.SimulationObject;

/**
 * The class ObjectByPositionSearch holds a search tree for simulation objects.
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
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class ObjectByPositionSearch {
	private ObjectByPositionSearchNode root;
	private ObjectByPositionSearchNode found;
	
	private ObjectByPositionSearchNode allNodesByObjectID[];
	private int size;
	
	public ObjectByPositionSearch (int capacity) {
		root = new ObjectByPositionSearchNode(null, 0, 0, 0, null,null,null,null);;
		this.allNodesByObjectID = new ObjectByPositionSearchNode[capacity];
		size = capacity;
	}
	
	public void removeObject(SimulationObject object) {
		ObjectByPositionSearchNode node;
		ObjectByPositionSearchNode oldParent;
		
		int objectID;
		int oldChildNr;

		objectID = object.getObjectID();
		
		if (objectID < size){
			node = this.allNodesByObjectID[objectID];
			if (node != null) {
				oldParent = node.getParent();
				oldChildNr = node.getChildNr();
				
				oldParent.clearChild(oldChildNr);
				this.allNodesByObjectID[objectID] = null;
			}
		}
	}
	
	public void addObject(SimulationObject object ) {
		ObjectByPositionSearchNode newNode;
		int objectID;
		int x;
		int y;
		
		// if there is no position, no object is added to the position search tree
		if (object.getPosition() == null) return;
		
		objectID = object.getObjectID();
		x = object.getPosition().get_X();
		y = object.getPosition().get_Y();
		
		newNode = addNode(objectID, x, y);
		
		if (size <= objectID) 	ensureCapacity(objectID + 1000);
		this.allNodesByObjectID[objectID] = newNode;
	}
	
	public void changePosition(SimulationObject object) {
		removeObject(object);
		addObject(object);
	}

	public void reset() {
		while (found != root) {
			found.resetGetNode();
			found = found.getParent();
		}
		found.resetGetNode();
	}
	
	public void findNearestObject (double findX, double findY) {
		found = findNearestNoLeaf(findX, findY);
	}
	
	public int getNextObjectID() {
		ObjectByPositionSearchNode tmp;
		int nextID = 0;
		
		while (nextID == 0) {
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
	
	private ObjectByPositionSearchNode addNode(int objectID, int posX, int posY) {
		ObjectByPositionSearchNode created;
		ObjectByPositionSearchNode parent;
		ObjectByPositionSearchNode oldLeaf;
		ObjectByPositionSearchNode newLeaf;

	
		int childNr;

		int x;
		int y;

		parent = findNearestNoLeaf(posX, posY);
		oldLeaf = parent.getNode(posX, posY);
		childNr = parent.getChildNr(posX, posY);

		newLeaf = new ObjectByPositionSearchNode(parent, childNr, objectID);
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
					created = new ObjectByPositionSearchNode(parent, childNr, x, y, newLeaf,null,null,oldLeaf);
				}
				else {
					y = oldLeaf.getY();
					created = new ObjectByPositionSearchNode(parent, childNr, x, y, null,newLeaf,oldLeaf,null);
				}
			}
			else {
				x = oldLeaf.getX();
				if (newLeaf.getY() <= oldLeaf.getY() ) {
					y = newLeaf.getY();
					created = new ObjectByPositionSearchNode(parent, childNr, x, y, null,oldLeaf,newLeaf,null);
				}
				else {
					y = oldLeaf.getY();
					created = new ObjectByPositionSearchNode(parent, childNr, x, y, oldLeaf,null,null,newLeaf);
				}
			}
			parent.setChild(childNr, created);
			newLeaf.setParent(created);
			oldLeaf.setParent(created);
		}
		return newLeaf;
	}
	
	
	private ObjectByPositionSearchNode findNearestNoLeaf(double findX, double findY) {
		ObjectByPositionSearchNode last;
		ObjectByPositionSearchNode next;
		
		
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
		ObjectByPositionSearchNode newAllNodesByObjectID[];
		int index;
		
		if (newCapacity > size) {
			newAllNodesByObjectID = new ObjectByPositionSearchNode[newCapacity];
			for (index = 0; index < size; index++)
				newAllNodesByObjectID[index] = this.allNodesByObjectID[index];
			this.allNodesByObjectID = newAllNodesByObjectID;
			size = newCapacity;
		}
	}
}
