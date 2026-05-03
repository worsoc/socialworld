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

import java.util.HashMap;
import java.util.Map;

import org.socialworld.calculation.Value;

/**
 * The enumeration collects all action types.
 * 
 * German:
 * Die Enumeration ActionType enth�lt alle Aktionstypen,
 *   die f�r eine Aktion gesetzt werden k�nnen.
 *   
 * Es sind 11 Aktionstypen umgesetzt:
 *  - K�rperfunktionen (Schlafen,Essen,...)
 *  - Bewegung (Positionswechsel)
 *  - untersuchung von Simulationsobjekten
 *  - Ber�hrung von Simulationsobjekten
 *  - Ausstatten 
 *  - Gegenstandshandhabung
 *  - Waffenbenutzung 
 *  - Schlag 
 *  - H�ren/Zuh�ren
 *  - Gespr�che f�hren
 *  - Sprechen
 *  
 * Zur Ablaufsteuerung wird zus�tzlich der Aktionstyp Ignorieren angeboten. 
 *  Dieser soll Vergleiche auf NULL verhindern.
 *  
 * @author Mathias Sikos (MatWorsoc) 
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

	private static final Map<String, ActionType> NAME_CACHE = new HashMap<>();
	private static final Map<Integer, ActionType> INDEX_CACHE = new HashMap<>();

	static {
		for (ActionType type : values()) {
			NAME_CACHE.put(type.name(), type);
			INDEX_CACHE.put(type.index, type);
		}
	}
	private int index;

	private ActionType(int index) {
		this.index = index;
	}

	public static ActionType getName(int index) {
		ActionType type = INDEX_CACHE.get(index);
		return (type != null) ? type : ignore;
	}

	public static ActionType fromName(String name) {
		ActionType type = NAME_CACHE.get(name);
		return (type != null) ? type : ignore;
	}

	/**
	 * The method returns the index of the ActionType.
	 * 
	 * @return action type's index
	 */
	public int getIndex() {
		return index;
	}
	
	private static String[] STANDARD_PROPERTY_NAMES = {Value.VALUE_BY_NAME_ACTION_TYPE, 
			Value.VALUE_BY_NAME_ACTION_MODE, Value.VALUE_BY_NAME_ACTION_INTENSITY, 
			Value.VALUE_BY_NAME_ACTION_MINTIME, Value.VALUE_BY_NAME_ACTION_MAXTIME,
			Value.VALUE_BY_NAME_ACTION_PRIORITY, Value.VALUE_BY_NAME_ACTION_DURATION};

	private static String[] NO_FURTHER_PROPERTY_NAMES = {};
	private static String[] FURTHER_PROPERTY_NAMES___BODILYFUNCTIONS = {};
	private static String[] FURTHER_PROPERTY_NAMES___MOVE = {Value.VALUE_BY_NAME_ACTION_MOVE_ENDPOSITION,Value.VALUE_BY_NAME_ACTION_DIRECTION};
	private static String[] FURTHER_PROPERTY_NAMES___EXAMINE = {Value.VALUE_BY_NAME_ACTION_TARGET};
	private static String[] FURTHER_PROPERTY_NAMES___TOUCH = {Value.VALUE_BY_NAME_ACTION_TARGET,Value.VALUE_BY_NAME_ACTION_DIRECTION};
	private static String[] FURTHER_PROPERTY_NAMES___EQUIP = {Value.VALUE_BY_NAME_ACTION_EQUIP_ITEM,Value.VALUE_BY_NAME_ACTION_EQUIP_PLACE};
	private static String[] FURTHER_PROPERTY_NAMES___HANDLEITEM = {Value.VALUE_BY_NAME_ACTION_TARGET,Value.VALUE_BY_NAME_ACTION_DIRECTION};
	private static String[] FURTHER_PROPERTY_NAMES___USEWEAPON = {Value.VALUE_BY_NAME_ACTION_TARGET};
	private static String[] FURTHER_PROPERTY_NAMES___PUNCH = {Value.VALUE_BY_NAME_ACTION_TARGET};
	private static String[] FURTHER_PROPERTY_NAMES___HEAR = {Value.VALUE_BY_NAME_ACTION_TARGET};
	private static String[] FURTHER_PROPERTY_NAMES___TALK = {Value.VALUE_BY_NAME_ACTION_TARGET};
	private static String[] FURTHER_PROPERTY_NAMES___SAY = {Value.VALUE_BY_NAME_ACTION_TARGET,Value.VALUE_BY_NAME_ACTION_DIRECTION};
	
	public static String[] getStandardPropertyNames() {
		return STANDARD_PROPERTY_NAMES.clone(); 
	}
	
	public String[] getFurtherPropertyNames() {
		switch (this) { 
			case move: return FURTHER_PROPERTY_NAMES___MOVE.clone();
			case examine: return FURTHER_PROPERTY_NAMES___EXAMINE.clone();
			case touch: return FURTHER_PROPERTY_NAMES___TOUCH.clone();
			case equip: return FURTHER_PROPERTY_NAMES___EQUIP.clone();
			case handleItem: return FURTHER_PROPERTY_NAMES___HANDLEITEM.clone();
			case useWeapon: return FURTHER_PROPERTY_NAMES___USEWEAPON.clone();
			case punch: return FURTHER_PROPERTY_NAMES___PUNCH.clone();
			case hear: return FURTHER_PROPERTY_NAMES___HEAR.clone();
			case talk: return FURTHER_PROPERTY_NAMES___TALK.clone();
			case say: return FURTHER_PROPERTY_NAMES___SAY.clone();
			default: return NO_FURTHER_PROPERTY_NAMES.clone();
		}
	}
	

	public String getPraefix() {
		return name() + "_";
	}

}