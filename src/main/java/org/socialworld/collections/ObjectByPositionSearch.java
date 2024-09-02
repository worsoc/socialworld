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


import org.socialworld.attributes.Position;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.God;
import org.socialworld.objects.Human;
import org.socialworld.objects.Animal;
import org.socialworld.core.Event;
import org.socialworld.core.EventDistanceCategory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;

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
 *  level		mm		  m
 *   		 4000000	4000
 *   1 		 1333333	1333
 *   2		  444444	 444
 *   3		  148148	 148
 *   4		   49383	  49
 *   5		   16461	  16
 *   6          5487	   5
 *   7			1829	   1,8
 *   8			 610	   0,6
 *   9			 203   	   0,2
 *   			  	
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
  *  level		mm		  m
 *   		 4000000	4000
 *   1 		  800000	 800
 *   2		  160000	 160
 *   3		   32000	  32
 *   4			6400	   6,4
 *   5			1280	   1,3
 *   6           256	   0,26
 *   7			  51,2	   0,05
 *   8			  10,24	   0,01
 *   9			   2,048   0,002

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

	public LinkedList<LinkedList<SimulationObject>> getObjects(Event event) {
		
		Position position = event.getPosition();
		float distance = event.getEventType().getEffectDistance();		
		ArrayList<String> praefixs = getPraefixs(position, distance);
		
		LinkedList<LinkedList<SimulationObject>> result = new LinkedList<LinkedList<SimulationObject>>();

		LinkedList<SimulationObject> gods = new LinkedList<SimulationObject>();
		LinkedList<SimulationObject> humans = new LinkedList<SimulationObject>();
		LinkedList<SimulationObject> animals = new LinkedList<SimulationObject>();
		LinkedList<SimulationObject> others = new LinkedList<SimulationObject>();

		ArrayList<SimulationObject> objects = null;
		String firstLetter;
		for (String praefix : praefixs) {
			firstLetter = praefix.substring(0,1);
			if (firstLetter.equals("1") || firstLetter.equals("2") || firstLetter.equals("3") || firstLetter.equals("4")
					|| firstLetter.equals("5") || firstLetter.equals("6") || firstLetter.equals("7")
					|| firstLetter.equals("8") || firstLetter.equals("9")) {
				// base 9
				objects = getObjectsByBase9(praefix);
			}
			else {
				// base 25
				objects = getObjectsByBase25(praefix);
			}
			
			for (SimulationObject object : objects) {
				if (object instanceof Human) {
					if (humans.contains(object) == false) humans.add(object);
				}
				else if (object instanceof God) {
					if (gods.contains(object) == false) gods.add(object);
				}
				else if (object instanceof Animal) {
					if (animals.contains(object) == false) animals.add(object);
				}
				else {
					if (others.contains(object) == false) others.add(object);
				};
			}
		}
		
		result.add(humans);
		result.add(gods);
		result.add(animals);
		result.add(others);

		return result;
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
	
	private ArrayList<String> getPraefixs(Position position, float distance) {
		
		ArrayList<String> result = new ArrayList<String>();
		
		EventDistanceCategory category = EventDistanceCategory.getCategory(distance);
		
		String locationBase25 = position.getLocationByBase25();
		String locationBase9 = String.valueOf(position.getLocationByBase9());
		
		String offset25;
		String offset9;
		
		String sector25;
		String sector9;
		
		int substringCountLettersBase25 = 0;
		int substringCountLettersBase9 = 0;
		
		List<String> sectorLetters;
		
		switch (category) {
		case less_1_m:
			substringCountLettersBase25 = 4;
			substringCountLettersBase9 = 6;
			break;
		case less_2_m:
			substringCountLettersBase25 = 4;
			substringCountLettersBase9 = 6;
			break;
		case less_5_m:
			substringCountLettersBase25 = 3;
			substringCountLettersBase9 = 5;
			break;
		case less_10_m:
			substringCountLettersBase25 = 2;
			substringCountLettersBase9 = 4;
			break;
		case less_100_m:
			substringCountLettersBase25 = 1;
			substringCountLettersBase9 = 2;
			break;
		default:
			break;
		}
		
		
		switch (category) {
		case less_2_m:
			offset25 = locationBase25.substring(0,substringCountLettersBase25);
			offset9 = locationBase9.substring(0,substringCountLettersBase9);
			result.add(offset25);
			result.add(offset9);
			break;
		case less_1_m:
		case less_5_m:
		case less_10_m:
		case less_100_m:
			offset25 = locationBase25.substring(0,substringCountLettersBase25);
			sector25 = locationBase25.substring(substringCountLettersBase25,1);
			
			switch(sector25) {
			case "A":
				String letters_A[] = {"A", "B", "F", "G"};
				sectorLetters = Arrays.asList(letters_A);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "3":
					String letters_A3[] = {"2", "3", "5", "6"};
					sectorLetters = Arrays.asList(letters_A3);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "6":
					String letters_A6[] = {"8", "9", "5", "6", "2","3"};
					sectorLetters = Arrays.asList(letters_A6);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "7":
					String letters_A7[] = {"7", "8", "4", "5"};
					sectorLetters = Arrays.asList(letters_A7);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "8":
					String letters_A8[] = {"7","8","9","4", "5", "6"};
					sectorLetters = Arrays.asList(letters_A8);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "9":
					String letters_A9[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_A9);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "B":
				String letters_B[] = {"A", "B", "C", "F", "G", "H"};
				sectorLetters = Arrays.asList(letters_B);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "4":
					String letters_B4[] = {"7", "8", "4", "5"};
					sectorLetters = Arrays.asList(letters_B4);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "5":
					String letters_B5[] = {"7", "8", "9", "4", "5", "6"};
					sectorLetters = Arrays.asList(letters_B5);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "6":
					String letters_B6[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_B6);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "1":
					String letters_B1[] = {"4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_B1);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "2":
					String letters_B2[] = {"4", "5", "6", "1", "2", "3"};
					sectorLetters = Arrays.asList(letters_B2);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "3":
					String letters_B3[] = {"5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_B3);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "C":
				String letters_C[] = { "B", "C", "D", "G", "H", "I"};
				sectorLetters = Arrays.asList(letters_C);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "4":
					String letters_C4[] = {"7", "8", "4", "5"};
					sectorLetters = Arrays.asList(letters_C4);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "5":
					String letters_C5[] = {"7", "8", "9", "4", "5", "6"};
					sectorLetters = Arrays.asList(letters_C5);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "6":
					String letters_C6[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_C6);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "1":
					String letters_C1[] = {"4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_C1);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "2":
					String letters_C2[] = {"4", "5", "6", "1", "2", "3"};
					sectorLetters = Arrays.asList(letters_C2);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "3":
					String letters_W3[] = {"5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_W3);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "D":
				String letters_D[] = {"C", "D", "E", "H", "I", "J"};
				sectorLetters = Arrays.asList(letters_D);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "4":
					String letters_D4[] = {"1", "2", "4", "5"};
					sectorLetters = Arrays.asList(letters_D4);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "5":
					String letters_D5[] = {"1", "2", "3", "4", "5", "6"};
					sectorLetters = Arrays.asList(letters_D5);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "6":
					String letters_D6[] = {"2", "3", "5", "6"};
					sectorLetters = Arrays.asList(letters_D6);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "7":
					String letters_D7[] = {"4", "5", "7", "8"};
					sectorLetters = Arrays.asList(letters_D7);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "8":
					String letters_D8[] = {"4", "5", "6", "7", "8", "9"};
					sectorLetters = Arrays.asList(letters_D8);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "9":
					String letters_D9[] = {"5", "6", "8", "9"};
					sectorLetters = Arrays.asList(letters_D9);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "E":
				String letters_E[] = {"D", "E", "I", "J"};
				sectorLetters = Arrays.asList(letters_E);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "7":
					String letters_E7[] = {"7", "8", "4", "5"};
					sectorLetters = Arrays.asList(letters_E7);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "8":
					String letters_E8[] = {"7", "8", "9", "4", "5", "6"};
					sectorLetters = Arrays.asList(letters_E8);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "9":
					String letters_E9[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_E9);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "4":
					String letters_E4[] = {"7", "8", "4", "5","1","2"};
					sectorLetters = Arrays.asList(letters_E4);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "1":
					String letters_E1[] = {"1", "2", "4", "5"};
					sectorLetters = Arrays.asList(letters_E1);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "F":
				String letters_F[] = {"K", "L", "F", "G", "A", "B"};
				sectorLetters = Arrays.asList(letters_F);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "8":
					String letters_F8[] = {"7", "8", "4", "5"};
					sectorLetters = Arrays.asList(letters_F8);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "9":
					String letters_F9[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_F9);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "5":
					String letters_F5[] = {"7", "8", "4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_F5);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "6":
					String letters_F6[] = {"8", "9", "5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_F6);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "2":
					String letters_F2[] = {"4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_F2);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "3":
					String letters_F3[] = {"5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_F3);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "G":
				String letters_G[] = {"K", "L", "M", "F", "G", "H", "A", "B", "C"};
				sectorLetters = Arrays.asList(letters_G);
				result = getStrings(offset25, sectorLetters);
				break;
			case "H":
				String letters_H[] = {"L", "M", "N", "G", "H", "I", "B", "C", "D"};
				sectorLetters = Arrays.asList(letters_H);
				result = getStrings(offset25, sectorLetters);
				break;
			case "I":
				String letters_I[] = {"M", "N", "O", "H", "I", "J", "C", "D", "E"};
				sectorLetters = Arrays.asList(letters_I);
				result = getStrings(offset25, sectorLetters);
				break;
			case "J":
				String letters_J[] = {"N", "O", "I", "J", "B", "C"};
				sectorLetters = Arrays.asList(letters_J);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "7":
					String letters_J7[] = {"7","8","4", "5"};
					sectorLetters = Arrays.asList(letters_J7);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "8":
					String letters_J8[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_J8);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "4":
					String letters_J4[] = {"7", "8", "4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_J4);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "5":
					String letters_J5[] = {"8", "9", "5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_J5);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "1":
					String letters_J1[] = {"4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_J1);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "2":
					String letters_J2[] = {"5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_J2);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "K":
				String letters_K[] = {"P", "Q", "K", "L", "F", "G"};
				sectorLetters = Arrays.asList(letters_K);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "8":
					String letters_K8[] = {"7", "8", "4", "5"};
					sectorLetters = Arrays.asList(letters_K8);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "9":
					String letters_K9[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_K9);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "5":
					String letters_K5[] = {"7", "8", "4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_K5);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "6":
					String letters_K6[] = {"8", "9", "5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_K6);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "2":
					String letters_K2[] = {"4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_K2);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "3":
					String letters_K3[] = {"5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_K3);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "L":
				String letters_L[] = {"P", "Q", "R", "K", "L", "M", "F", "G", "H"};
				sectorLetters = Arrays.asList(letters_L);
				result = getStrings(offset25, sectorLetters);
				break;
			case "M":
				String letters_M[] = {"Q", "R", "S", "L", "M", "N", "G", "H", "I"};
				sectorLetters = Arrays.asList(letters_M);
				result = getStrings(offset25, sectorLetters);
				break;
			case "N":
				String letters_N[] = {"R", "S", "T", "M", "N", "O", "H", "I", "J"};
				sectorLetters = Arrays.asList(letters_N);
				result = getStrings(offset25, sectorLetters);
				break;
			case "O":
				String letters_O[] = {"N", "O", "I", "J", "S", "T"};
				sectorLetters = Arrays.asList(letters_O);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "7":
					String letters_O7[] = {"7","8", "4", "5"};
					sectorLetters = Arrays.asList(letters_O7);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "8":
					String letters_O8[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_O8);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "4":
					String letters_O4[] = {"7", "8", "4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_O4);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "5":
					String letters_O5[] = {"8", "9", "5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_O5);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "1":
					String letters_O1[] = {"4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_O1);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "2":
					String letters_O2[] = {"5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_O2);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "P":
				String letters_P[] = {"U", "V", "P", "Q", "K", "L"};
				sectorLetters = Arrays.asList(letters_P);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "8":
					String letters_P8[] = {"7", "8", "4", "5"};
					sectorLetters = Arrays.asList(letters_P8);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "9":
					String letters_P9[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_P9);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "5":
					String letters_P5[] = {"7", "8", "4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_P5);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "6":
					String letters_P6[] = {"8", "9", "5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_P6);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "2":
					String letters_P2[] = {"4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_P2);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "3":
					String letters_P3[] = {"5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_P3);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "Q":
				String letters_Q[] = {"U", "V", "W", "P", "Q", "R", "K", "L", "M"};
				sectorLetters = Arrays.asList(letters_Q);
				result = getStrings(offset25, sectorLetters);
				break;
			case "R":
				String letters_R[] = {"V", "W", "X", "Q", "R", "S", "L", "M", "N"};
				sectorLetters = Arrays.asList(letters_R);
				result = getStrings(offset25, sectorLetters);
				break;
			case "S":
				String letters_S[] = {"W", "X", "Y", "R", "S", "T", "M", "N", "O"};
				sectorLetters = Arrays.asList(letters_S);
				result = getStrings(offset25, sectorLetters);
				break;
			case "T":
				String letters_T[] = {"X", "Y", "S", "T", "N", "O"};
				sectorLetters = Arrays.asList(letters_T);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "7":
					String letters_T7[] = {"7","8","4", "5"};
					sectorLetters = Arrays.asList(letters_T7);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "8":
					String letters_T8[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_T8);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "4":
					String letters_T4[] = {"7", "8", "4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_T4);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "5":
					String letters_T5[] = {"8", "9", "5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_T5);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "1":
					String letters_T1[] = {"4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_T1);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "2":
					String letters_T2[] = {"5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_T2);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "U":
				String letters_U[] = {"U", "V", "P", "Q"};
				sectorLetters = Arrays.asList(letters_U);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "9":
					String letters_U9[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_U9);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "6":
					String letters_U6[] = {"8", "9", "5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_U6);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "1":
					String letters_U1[] = {"4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_U1);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "2":
					String letters_U2[] = {"4", "5", "6", "1", "2", "3"};
					sectorLetters = Arrays.asList(letters_U2);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "3":
					String letters_U3[] = {"5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_U3);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "V":
				String letters_V[] = {"U", "V", "W", "P", "Q", "R"};
				sectorLetters = Arrays.asList(letters_V);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "4":
					String letters_V4[] = {"7", "8", "4", "5"};
					sectorLetters = Arrays.asList(letters_V4);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "5":
					String letters_V5[] = {"7", "8", "9", "4", "5", "6"};
					sectorLetters = Arrays.asList(letters_V5);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "6":
					String letters_V6[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_V6);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "1":
					String letters_V1[] = {"4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_V1);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "2":
					String letters_V2[] = {"4", "5", "6", "1", "2", "3"};
					sectorLetters = Arrays.asList(letters_V2);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "3":
					String letters_V3[] = {"5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_V3);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "W":
				String letters_W[] = { "V", "W", "X", "Q", "R", "S"};
				sectorLetters = Arrays.asList(letters_W);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "4":
					String letters_W4[] = {"7", "8", "4", "5"};
					sectorLetters = Arrays.asList(letters_W4);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "5":
					String letters_W5[] = {"7", "8", "9", "4", "5", "6"};
					sectorLetters = Arrays.asList(letters_W5);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "6":
					String letters_W6[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_W6);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "1":
					String letters_W1[] = {"4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_W1);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "2":
					String letters_W2[] = {"4", "5", "6", "1", "2", "3"};
					sectorLetters = Arrays.asList(letters_W2);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "3":
					String letters_W3[] = {"5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_W3);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "X":
				String letters_X[] = {"W", "X", "Y", "R", "S", "T"};
				sectorLetters = Arrays.asList(letters_X);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "4":
					String letters_X4[] = {"7", "8", "4", "5"};
					sectorLetters = Arrays.asList(letters_X4);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "5":
					String letters_X5[] = {"7", "8", "9", "4", "5", "6"};
					sectorLetters = Arrays.asList(letters_X5);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "6":
					String letters_X6[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_X6);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "1":
					String letters_X1[] = {"4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_X1);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "2":
					String letters_X2[] = {"4", "5", "6", "1", "2", "3"};
					sectorLetters = Arrays.asList(letters_X2);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "3":
					String letters_X3[] = {"5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_X3);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			case "Y":
				String letters_Y[] = {"X", "Y", "S", "T"};
				sectorLetters = Arrays.asList(letters_Y);
				result = getStrings(offset25, sectorLetters);
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9,1);
				switch(sector9) {
				case "4":
					String letters_Y4[] = {"7", "8", "4", "5","1", "2"};
					sectorLetters = Arrays.asList(letters_Y4);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "7":
					String letters_Y7[] = {"8", "9", "5", "6"};
					sectorLetters = Arrays.asList(letters_Y7);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "1":
					String letters_Y1[] = {"4", "5", "1", "2"};
					sectorLetters = Arrays.asList(letters_Y1);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "2":
					String letters_Y2[] = {"4", "5", "6", "1", "2", "3"};
					sectorLetters = Arrays.asList(letters_Y2);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				case "3":
					String letters_Y3[] = {"5", "6", "2", "3"};
					sectorLetters = Arrays.asList(letters_Y3);
					result.addAll(getStrings(offset9, sectorLetters));
					break;
				}
				break;
			default:
				break;
			}
			break;
		default:
			result.add("1");
			result.add("2");
			result.add("3");
			result.add("4");
			result.add("5");
			result.add("6");
			result.add("7");
			result.add("8");
			result.add("9");
		}
		return result;
	}
	
	private ArrayList<String> getStrings(String offset, List<String> sectorLetters) {
		ArrayList<String> result = new ArrayList<String>();
		
		for (String letter : sectorLetters) {
			result.add(offset + letter);
		}
		return result;
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
