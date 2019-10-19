/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.actions.sleep;

import java.util.List;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Value;
import org.socialworld.core.EventByAction;
import org.socialworld.core.EventType;

/**
 * German:
 * Die Klasse ActionSleep ist von der abstrakten Klasse AbstractAction abgeleitet.
 * Alle Aktionsobjekte, die Schlafen beschreiben, gehören zu dieser Klasse.
 * 
 * Die Ausführung der Aktion wird in der Klasse Sleep geregelt, 
 *   von der ein Objekt als Eigenschaft der Klasse ActionSleep abgelegt ist.
 * Zur Beschreibung des Schlafens führt die Klasse keine weiteren Eigenschaften.
 * 
 * Die Klasse ActionSleep dient der Verwaltung der Aktion.
 * Die zugehörige Klasse Sleep dient der Wirksamwerdung der Aktion, 
 *  nämlich als Argument für das zur Aktion gehörende Event.
 *
 *  In der Ausführungsmethode perform() wird das Ausführungsobjekt der Klasse Sleep erzeugt.
 *  Außerdem wird das Ereignis zur Aktion erzeugt, mit dem Ausführungsobjekt als Argument.
 *  Das Ereignis wird in die Ereignisverwaltung (EventMaster) eingetragen.
 *  
 *  Der Name des Ereignis (EventType) 
 *   wird in Abhängigkeit von Aktionsmodus (ActionMode) ermittelt.
 *   
 *  Eine Aktion der Klasse ActionSleep ist 
 *  a) das willkürliche Schlafen, also sich bewusst zum Schlafen hinzulegen
 *  oder
 *  b) das aufgrund von Übermüdung oder auf andere Weise erzwungene Schlafen
 *   
 * @author Mathias Sikos
 *
 */
public class ActionSleep extends AbstractAction {

	Sleep sleep;
	
	public ActionSleep(List<Value> actionProperties) {
		super(actionProperties);
	}

	public ActionSleep(final ActionType type, final ActionMode mode,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		super(type,  mode,
				intensity,  minTime, maxTime,
				 priority,  duration);
			
	}

	/* (non-Javadoc)
	 * @see org.socialworld.actions.AbstractAction#perform()
	 */
	@Override
	public void perform() {
		
		EventByAction event;
		EventType eventType;
		
		sleep = new Sleep(this);
		
		eventType = getEventType(mode);
				
		if (eventType == EventType.nothing) return;
				
		event = new EventByAction(eventType,    actor /* as causer*/,  ActualTime.asTime(),
					actor.getPosition(),  sleep /* as performer */);
		addEvent(event);
	}


	private EventType getEventType( ActionMode mode) {
		switch (mode) {
		case sleepCaused:
			return EventType.sleepCaused;
		case sleepIntentioned:
			return EventType.sleepIntentioned;
		default:
			return EventType.nothing;
		}
	}
	
}
