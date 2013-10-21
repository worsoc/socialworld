package org.socialworld.collections;

/**
 * The class ObjectByPositionSearchNode describes a node of a search tree for simulation objects.
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
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class ObjectByPositionSearchNode {
	protected static final int SMALLEREQUALX_SMALLEREQUALY = 1;
	protected static final int SMALLEREQUALX_GREATERY = 2;
	protected static final int GREATERX_SMALLEREQUALY = 3;
	protected static final int GREATERX_GREATERY = 4;
	
	private ObjectByPositionSearchNode smallerEqualX_smallerEqualY;  // childNr 1
	private ObjectByPositionSearchNode smallerEqualX_greaterY;		 // childNr 2
	private ObjectByPositionSearchNode greaterX_smallerEqualY;		 // childNr 3	
	private ObjectByPositionSearchNode greaterX_greaterY;			 // childNr 4
	
	private ObjectByPositionSearchNode parent;
	private int childNr;

	private int x;
	private int y;
	
	private boolean isLeaf;
	private int done = 0;
	private int objectID;

	public ObjectByPositionSearchNode(ObjectByPositionSearchNode parent, int childNr, int objectID) {
		
		this.parent = parent;
		this.childNr = childNr;

		this.isLeaf = true;
		this.objectID = objectID;
		
		this.smallerEqualX_smallerEqualY = null;
		this.smallerEqualX_greaterY = null;
		this.greaterX_smallerEqualY = null;
		this.greaterX_greaterY = null;
	}

	public ObjectByPositionSearchNode(ObjectByPositionSearchNode parent, int childNr, int x, int y, 
			ObjectByPositionSearchNode smallerEqualX_smallerEqualY,
			ObjectByPositionSearchNode smallerEqualX_greaterY,
			ObjectByPositionSearchNode greaterX_smallerEqualY,
			ObjectByPositionSearchNode greaterX_greaterY) {
		
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


	protected ObjectByPositionSearchNode getNode() {
		if (isLeaf) return null;
		
		done = done + 1;
		switch  (done){
			case SMALLEREQUALX_SMALLEREQUALY:
						if (this.smallerEqualX_smallerEqualY != null)	return this.smallerEqualX_smallerEqualY;
						else done = done + 1;
			case SMALLEREQUALX_GREATERY:	
						if (this.smallerEqualX_greaterY != null)	return this.smallerEqualX_greaterY;
						else done = done + 1;
			case GREATERX_SMALLEREQUALY:
						if (this.greaterX_smallerEqualY != null)	return this.greaterX_smallerEqualY;
						else done = done + 1;
			case GREATERX_GREATERY:	
						if (this.greaterX_greaterY != null)	return this.greaterX_greaterY;
		}
		done = 0;
		return null;
		
	}
	
	protected void resetGetNode() {
		done = 0;
	}
	
	
	protected int getObjectID() {
		return this.objectID;
	}

	protected ObjectByPositionSearchNode getNode(double searchX, double searchY) {
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

	protected void setChild(int childNr, ObjectByPositionSearchNode child){
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

	protected void setParent(ObjectByPositionSearchNode parent) {
		this.parent = parent;
	}
	
	protected ObjectByPositionSearchNode getParent() {
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

