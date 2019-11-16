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
package org.socialworld.actions.bodilyfunctions;


import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.attributes.ActualTime;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.EventByAction;
import org.socialworld.core.EventType;
import org.socialworld.objects.Animal;
import org.socialworld.objects.SimulationObject;

/**
 * German:
 * Die Klasse ActionBodilyFunction ist von der abstrakten Klasse AbstractAction abgeleitet.
 * Alle Aktionsobjekte, die Schlafen beschreiben, gehören zu dieser Klasse.
 * 
 * Die Ausführung der Aktion wird in der Klasse BodilyFunction geregelt, 
 *   von der ein Objekt als Eigenschaft der Klasse ActionBodilyFunction abgelegt ist.
 * Zur Beschreibung des Schlafens führt die Klasse keine weiteren Eigenschaften.
 * 
 * Die Klasse ActionBodilyFunction dient der Verwaltung der Aktion.
 * Die zugehörige Klasse BodilyFunction dient der Wirksamwerdung der Aktion, 
 *  nämlich als Argument für das zur Aktion gehörende Event.
 *
 *  In der Ausführungsmethode perform() wird das Ausführungsobjekt der Klasse BodilyFunction erzeugt.
 *  Außerdem wird das Ereignis zur Aktion erzeugt, mit dem Ausführungsobjekt als Argument.
 *  Das Ereignis wird in die Ereignisverwaltung (EventMaster) eingetragen.
 *  
 *  Der Name des Ereignis (EventType) 
 *   wird in Abhängigkeit von Aktionsmodus (ActionMode) ermittelt.
 *   
 *  Eine Aktion der Klasse ActionBodilyFunction ist 
 *  a) Schlafen
 *  b) Trinken
 *  c) Essen
 *  4) Urinieren
 *  5) Koten
 *   
 * @author Mathias Sikos
 *
 */
public class ActionBodilyFunction extends AbstractAction {

	BodilyFunction bodilyFunction;
	private SimulationObject item = null;
	
	public ActionBodilyFunction(ValueArrayList actionProperties) {
		super(actionProperties);
	}

	public ActionBodilyFunction(ActionBodilyFunction original) {
		super(original);
	}

	protected void setFurtherProperties(ValueArrayList actionProperties) {
		

	}

	protected void setFurtherProperties(AbstractAction original) {
	}
	
	/* (non-Javadoc)
	 * @see org.socialworld.actions.AbstractAction#perform()
	 */
	@Override
	public void perform() {
		
		EventByAction event;
		EventType eventType;
		
   		switch (mode) {
		case sleep:
			break;
		case drink:
			item = ((Animal) actor).getMouth();
			if (item == null) return;
			break;
		case eat:
			item = ((Animal) actor).getMouth();
			if (item == null) return;
			break;
		case piss:
			break;
		case shit:
			break;

		default:
		}

		
		
		bodilyFunction = new BodilyFunction(this);
		
		eventType = getEventType(mode);
				
		if (eventType == EventType.nothing) return;
				
		event = new EventByAction(eventType,    actor /* as causer*/,  ActualTime.asTime(),
					actor.getPosition(),  bodilyFunction /* as performer */);
		addEvent(event);
	}


	private EventType getEventType( ActionMode mode) {
		switch (mode) {
		case sleep:
			return EventType.sleep;
		case drink:
			return EventType.drink;
		case eat:
			return EventType.eat;
		case piss:
			return EventType.piss;
		case shit:
			return EventType.shit;
		default:
			return EventType.nothing;
		}
	}
	
}
