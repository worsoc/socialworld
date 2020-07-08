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
package org.socialworld.actions;

import org.socialworld.calculation.Value;

/**
 * The enumeration collects all action types.
 * 
 * German:
 * Die Enumeration ActionType enthält alle Aktionstypen,
 *   die für eine Aktion gesetzt werden können.
 *   
 * Es sind 11 Aktionstypen umgesetzt:
 *  - Körperfunktionen (Schlafen,Essen,...)
 *  - Bewegung (Positionswechsel)
 *  - untersuchung von Simulationsobjekten
 *  - Berührung von Simulationsobjekten
 *  - Ausstatten 
 *  - Gegenstandshandhabung
 *  - Waffenbenutzung 
 *  - Schlag 
 *  - Hören/Zuhören
 *  - Gespräche führen
 *  - Sprechen
 *  
 * Zur Ablaufsteuerung wird zusätzlich der Aktionstyp Ignorieren angeboten. 
 *  Dieser soll Vergleiche auf NULL verhindern.
 *  
 * @author Mathias Sikos (tyloesand) 
 */
public enum ActionType {
	bodilyFunction(0),
	move(1),
	examine(2),
	touch(3), 
	equip(4),
	handleItem(5),
	useWeapon(6),
	punch(7), 
	hear(8),
	talk(9),
	say(10),
	ignore(11);

	private int index;

	private ActionType(int index) {
		this.index = index;
	}

	public static ActionType getName(int index) {
		for (ActionType actiontype : ActionType.values())
			if (actiontype.index == index)
				return actiontype;
		return null;
	}

	/**
	 * The method returns the index of the ActionType.
	 * 
	 * @return action type's index
	 */
	public int getIndex() {
		return index;
	}
	
	private static String[] STANDARD_PROPERTY_NAMES = {"actiontype", "actionmode", "intensity", "mintime", "maxtime", "priority", "duration"};
	private static String[] NO_FURTHER_PROPERTY_NAMES = {};
	private static String[] FURTHER_PROPERTY_NAMES___BODILYFUNCTIONS = {};
	private static String[] FURTHER_PROPERTY_NAMES___MOVE = {"endposition",Value.VALUE_BY_NAME_ACTION_DIRECTION};
	private static String[] FURTHER_PROPERTY_NAMES___EXAMINE = {Value.VALUE_BY_NAME_ACTION_TARGET};
	private static String[] FURTHER_PROPERTY_NAMES___TOUCH = {Value.VALUE_BY_NAME_ACTION_TARGET,Value.VALUE_BY_NAME_ACTION_DIRECTION};
	private static String[] FURTHER_PROPERTY_NAMES___EQUIP = {"item","inventoryPlace"};
	private static String[] FURTHER_PROPERTY_NAMES___HANDLEITEM = {Value.VALUE_BY_NAME_ACTION_TARGET,Value.VALUE_BY_NAME_ACTION_DIRECTION};
	private static String[] FURTHER_PROPERTY_NAMES___USEWEAPON = {Value.VALUE_BY_NAME_ACTION_TARGET};
	private static String[] FURTHER_PROPERTY_NAMES___PUNCH = {Value.VALUE_BY_NAME_ACTION_TARGET};
	private static String[] FURTHER_PROPERTY_NAMES___HEAR = {Value.VALUE_BY_NAME_ACTION_TARGET};
	private static String[] FURTHER_PROPERTY_NAMES___TALK = {Value.VALUE_BY_NAME_ACTION_TARGET};
	private static String[] FURTHER_PROPERTY_NAMES___SAY = {Value.VALUE_BY_NAME_ACTION_TARGET,Value.VALUE_BY_NAME_ACTION_DIRECTION};
	
	public static String[] getStandardPropertyNames() {
		String[] copy;
		
		copy = new String[STANDARD_PROPERTY_NAMES.length];
		for (int i = 0; i < STANDARD_PROPERTY_NAMES.length;i++) {
			copy[i] = new String(STANDARD_PROPERTY_NAMES[i]);
		}
		return copy;
	}
	
	public String[] getFurtherPropertyNames() {
	
		String[] original;
		String[] copy;

		switch (index) {
		case 0: // bodily functions
			original = FURTHER_PROPERTY_NAMES___BODILYFUNCTIONS;
			break;
		case 1: // move
			original = FURTHER_PROPERTY_NAMES___MOVE;
			break;
		case 2: // examine
			original = FURTHER_PROPERTY_NAMES___EXAMINE;
			break;
		case 3: // touch
			original = FURTHER_PROPERTY_NAMES___TOUCH;
			break;
		case 4: // equip
			original = FURTHER_PROPERTY_NAMES___EQUIP;
			break;
		case 5: // handleItem
			original = FURTHER_PROPERTY_NAMES___HANDLEITEM;
			break;
		case 6: // useWeapon
			original = FURTHER_PROPERTY_NAMES___USEWEAPON;
			break;
		case 7: // punch
			original = FURTHER_PROPERTY_NAMES___PUNCH;
			break;
		case 8: // hear
			original = FURTHER_PROPERTY_NAMES___HEAR;
			break;
		case 9: // talk
			original = FURTHER_PROPERTY_NAMES___TALK;
			break;
		case 10: // say
			original = FURTHER_PROPERTY_NAMES___SAY;
			break;
		default:
			original = NO_FURTHER_PROPERTY_NAMES;
			break;
		}
		
		copy = new String[original.length];
		for (int i = 0; i < original.length;i++) {
			copy[i] = new String(original[i]);
		}

		return copy;

	}

}