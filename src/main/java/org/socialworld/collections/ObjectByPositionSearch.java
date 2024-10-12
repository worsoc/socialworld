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
 *   		 6561000	6561
 *   1 		 2187000	2187
 *   2		  729000	 729
 *   3		  243000	 243
 *   4		   81000	  81
 *   5		   27000	  27
 *   6          9000	   9
 *   7			3000	   3
 *   8			1000	   1		!!!
 *   9			 203   	   0,333333333...
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
 *   		 6561000	6561
 *   1 		 1312200	1312
 *   2		  262440	 262
 *   3		   52488	  52,5
 *   4		   10498	  10,5
 *   5			2100	   2,1
 *   6           420	   0,42
 *   7			  84	   0,084
 *   8			  17	   0,017
 *   9			   3   	   0,003

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
	
	private static AccessTokenObjectByPositionSearch token = AccessTokenObjectByPositionSearch.getValid();
	
	public ObjectByPositionSearch (int capacity) {
		root = new ObjectByPositionSearch_NodeXY(null, 0, 0, 0, null,null,null,null);;
		sectorTreeBase9 = new ObjectByPositionSearch_TreeSector(9, 9);
		sectorTreeBase25 = new ObjectByPositionSearch_TreeSector(25, 9);

		allNodesByObjectID = new ArrayList<ObjectByPositionSearch_Nodes>(capacity);
		size = capacity;
		
	}
	
	public LinkedList<SimulationObject> getObjectsByBase9(String location) {
		return sectorTreeBase9.getObjects(location);
	}

	public LinkedList<SimulationObject> getObjectsByBase25(String location) {
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

		LinkedList<SimulationObject> objects = null;
		char firstLetter;
		for (String praefix : praefixs) {
			praefix = praefix + "0";
			firstLetter = praefix.charAt(0);
			if (firstLetter == '1' || firstLetter == '2' || firstLetter == '3' || firstLetter == '4'
					|| firstLetter == '5' || firstLetter == '6' || firstLetter == '7'
					|| firstLetter == '8' || firstLetter == '9') {
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
			
			nodes.getBase9Node().removeObject(object);
			nodes.getBase25Node().removeObject(object);
			
			nodes.setInvalid();
		}
	}
	
	public void addObject(SimulationObject object ) {
		ObjectByPositionSearch_Nodes nodes;

		int objectID;
		Position position = Position.getObjectNothing();
		int x;
		int y;
		
		position = object.getPosition(token);
		
		// if there is no position, no object is added to the position search tree
		if (position == Position.getObjectNothing()) return;
				
		objectID = object.getObjectID();
		if (size <= objectID) 	ensureCapacity(objectID + 1000);
		
		x = position.getX(token);
		y = position.getY(token);
		
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
			substringCountLettersBase9 = 7;
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
			substringCountLettersBase25 = 3;
			substringCountLettersBase9 = 5;
			break;
		case less_100_m:
			substringCountLettersBase25 = 1;
			substringCountLettersBase9 = 3;
			break;
		default:
			break;
		}
		
		
		switch (category) {
		case less_1_m:
		case less_2_m:
		case less_5_m:
		case less_10_m:
		case less_100_m:
			offset25 = locationBase25.substring(0,substringCountLettersBase25);
			sector25 = locationBase25.substring(substringCountLettersBase25,substringCountLettersBase25 + 1);
			
			switch(sector25) {
			case "A":
				String letters_A[] = {"A", "B", "F", "G"};
				sectorLetters = Arrays.asList(letters_A);
				result = getStrings(offset25, sectorLetters);
				break;
			case "B":
				String letters_B[] = {"A", "B", "C", "F", "G", "H"};
				sectorLetters = Arrays.asList(letters_B);
				result = getStrings(offset25, sectorLetters);
				break;
			case "C":
				String letters_C[] = {"B", "C", "D", "G", "H", "I"};
				sectorLetters = Arrays.asList(letters_C);
				result = getStrings(offset25, sectorLetters);
				break;
			case "D":
				String letters_D[] = {"C", "D", "E", "H", "I", "J"};
				sectorLetters = Arrays.asList(letters_D);
				result = getStrings(offset25, sectorLetters);
				break;
			case "E":
				String letters_E[] = {"D", "E", "I", "J"};
				sectorLetters = Arrays.asList(letters_E);
				result = getStrings(offset25, sectorLetters);
				break;
			case "F":
				String letters_F[] = {"K", "L", "F", "G", "A", "B"};
				sectorLetters = Arrays.asList(letters_F);
				result = getStrings(offset25, sectorLetters);
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
				break;
			case "K":
				String letters_K[] = {"P", "Q", "K", "L", "F", "G"};
				sectorLetters = Arrays.asList(letters_K);
				result = getStrings(offset25, sectorLetters);
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
				break;
			case "P":
				String letters_P[] = {"U", "V", "P", "Q", "K", "L"};
				sectorLetters = Arrays.asList(letters_P);
				result = getStrings(offset25, sectorLetters);
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
				break;
			case "U":
				String letters_U[] = {"U", "V", "P", "Q"};
				sectorLetters = Arrays.asList(letters_U);
				result = getStrings(offset25, sectorLetters);
				break;
			case "V":
				String letters_V[] = {"U", "V", "W", "P", "Q", "R"};
				sectorLetters = Arrays.asList(letters_V);
				result = getStrings(offset25, sectorLetters);
				break;
			case "W":
				String letters_W[] = { "V", "W", "X", "Q", "R", "S"};
				sectorLetters = Arrays.asList(letters_W);
				result = getStrings(offset25, sectorLetters);
				break;
			case "X":
				String letters_X[] = {"W", "X", "Y", "R", "S", "T"};
				sectorLetters = Arrays.asList(letters_X);
				result = getStrings(offset25, sectorLetters);
				break;
			case "Y":
				String letters_Y[] = {"X", "Y", "S", "T"};
				sectorLetters = Arrays.asList(letters_Y);
				result = getStrings(offset25, sectorLetters);
			default:
				break;
			}
			
			sectorLetters = new LinkedList<String>();
			offset9 = "";
			
			switch (sector25) {
			case "A":
			case "E":
			case "U":
			case "Y":
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				String letters_AEUY[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
				sectorLetters = Arrays.asList(letters_AEUY);
				break;
			case "B":
			case "C":
			case "D":
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9, substringCountLettersBase9 + 1);
				switch (sector9) {
				case "1":
					String letters_BCD1[] = {"1", "2"};
					sectorLetters = Arrays.asList(letters_BCD1);
					break;
				case "2":
					String letters_BCD2[] = {"1", "2", "3"};
					sectorLetters = Arrays.asList(letters_BCD2);
					break;
				case "3":
					String letters_BCD3[] = {"2", "3"};
					sectorLetters = Arrays.asList(letters_BCD3);
					break;
				case "4":
					String letters_BCD4[] = {"1", "2", "4", "5"};
					sectorLetters = Arrays.asList(letters_BCD4);
					break;
				case "5":
					String letters_BCD5[] = {"1", "2", "3", "4", "5", "6"};
					sectorLetters = Arrays.asList(letters_BCD5);
					break;
				case "6":
					String letters_BCD6[] = {"2", "3", "5", "6"};
					sectorLetters = Arrays.asList(letters_BCD6);
					break;
				case "7":
					String letters_BCD7[] = {"4", "5", "7", "8"};
					sectorLetters = Arrays.asList(letters_BCD7);
					break;
				case "8":
					String letters_BCD8[] = {"4", "5", "6", "7", "8", "9"};
					sectorLetters = Arrays.asList(letters_BCD8);
					break;
				case "9":
					String letters_BCD9[] = {"5", "6", "8", "9"};
					sectorLetters = Arrays.asList(letters_BCD9);
					break;
				}
				break;
			case "J":
			case "O":
			case "T":
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9, substringCountLettersBase9 + 1);
				switch (sector9) {
				case "1":
					String letters_JOT1[] = {"1", "2", "4", "5"};
					sectorLetters = Arrays.asList(letters_JOT1);
					break;
				case "2":
					String letters_JOT2[] = {"2", "3", "5", "6"};
					sectorLetters = Arrays.asList(letters_JOT2);
					break;
				case "3":
					String letters_JOT3[] = {"3", "6"};
					sectorLetters = Arrays.asList(letters_JOT3);
					break;
				case "4":
					String letters_JOT4[] = {"1", "2", "4", "5", "7", "8"};
					sectorLetters = Arrays.asList(letters_JOT4);
					break;
				case "5":
					String letters_JOT5[] = {"2", "3", "5", "6", "8", "9"};
					sectorLetters = Arrays.asList(letters_JOT5);
					break;
				case "6":
					String letters_JOT6[] = {"3", "6", "9"};
					sectorLetters = Arrays.asList(letters_JOT6);
					break;
				case "7":
					String letters_JOT7[] = {"4", "5", "7", "8"};
					sectorLetters = Arrays.asList(letters_JOT7);
					break;
				case "8":
					String letters_JOT8[] = {"5", "6", "8", "9"};
					sectorLetters = Arrays.asList(letters_JOT8);
					break;
				case "9":
					String letters_JOT9[] = {"6", "9"};
					sectorLetters = Arrays.asList(letters_JOT9);
					break;
				}
				break;
			case "F":
			case "K":
			case "P":
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9, substringCountLettersBase9 + 1);
				switch (sector9) {
				case "1":
					String letters_FKP1[] = {"1", "4"};
					sectorLetters = Arrays.asList(letters_FKP1);
					break;
				case "2":
					String letters_FKP2[] = {"1", "2", "4", "5"};
					sectorLetters = Arrays.asList(letters_FKP2);
					break;
				case "3":
					String letters_FKP3[] = {"2", "3", "5", "6"};
					sectorLetters = Arrays.asList(letters_FKP3);
					break;
				case "4":
					String letters_FKP4[] = {"1", "4", "7"};
					sectorLetters = Arrays.asList(letters_FKP4);
					break;
				case "5":
					String letters_FKP5[] = {"1", "2", "4", "5", "7", "8"};
					sectorLetters = Arrays.asList(letters_FKP5);
					break;
				case "6":
					String letters_FKP6[] = {"2", "3", "5", "6", "8", "9"};
					sectorLetters = Arrays.asList(letters_FKP6);
					break;
				case "7":
					String letters_FKP7[] = {"4", "7"};
					sectorLetters = Arrays.asList(letters_FKP7);
					break;
				case "8":
					String letters_FKP9[] = {"4", "5", "7", "8"};
					sectorLetters = Arrays.asList(letters_FKP9);
					break;
				case "9":
					String letters_FKP8[] = {"5", "6", "8", "9"};
					sectorLetters = Arrays.asList(letters_FKP8);
					break;
				}
				break;
			case "V":
			case "W":
			case "X":
				offset9 = locationBase9.substring(0,substringCountLettersBase9);
				sector9 = locationBase9.substring(substringCountLettersBase9, substringCountLettersBase9 + 1);
				switch (sector9) {
				case "1":
					String letters_VWX1[] = {"1", "2", "4", "5"};
					sectorLetters = Arrays.asList(letters_VWX1);
					break;
				case "2":
					String letters_VWX2[] = {"1", "2", "3", "4", "5", "6"};
					sectorLetters = Arrays.asList(letters_VWX2);
					break;
				case "3":
					String letters_VWX3[] = {"2", "3", "5", "6"};
					sectorLetters = Arrays.asList(letters_VWX3);
					break;
				case "4":
					String letters_VWX4[] = {"4", "5", "7", "8"};
					sectorLetters = Arrays.asList(letters_VWX4);
					break;
				case "5":
					String letters_VWX5[] = {"4", "5", "6", "7", "8", "9"};
					sectorLetters = Arrays.asList(letters_VWX5);
					break;
				case "6":
					String letters_VWX8[] = {"5", "6", "8", "9"};
					sectorLetters = Arrays.asList(letters_VWX8);
					break;
				case "7":
					String letters_VWX7[] = {"7", "8"};
					sectorLetters = Arrays.asList(letters_VWX7);
					break;
				case "8":
					String letters_VWX6[] = {"7", "8", "9"};
					sectorLetters = Arrays.asList(letters_VWX6);
					break;
				case "9":
					String letters_VWX9[] = {"8", "9"};
					sectorLetters = Arrays.asList(letters_VWX9);
					break;
				}
			break;
			}
			
			if (sectorLetters.size() > 0) {
				result.addAll(getStrings(offset9, sectorLetters));
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
