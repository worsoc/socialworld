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
package org.socialworld.actions.handle;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Vector;
import org.socialworld.core.EventByAction;
import org.socialworld.core.EventType;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos
 * 
 * German:
 * Die Klasse ActionHandle ist von der abstrakten Klasse AbstractAction abgeleitet.
 * Alle Aktionsobjekte, die Handhabe mit einem Simulationsobjekt beschreiben, gehören zu dieser Klasse.
 * Zur Beschreibung einer Handhabe führt die Klasse die zusätzlichen Eigenschaften
 * für die verwendeten Gegenstände aus dem Inventar , das Zielobjekt und die Richtung der Tätigkeit.
 * Die Ausführung der Aktion wird in der Klasse Handle geregelt, 
 * von der ein Objekt als Eigenschaft der Klasse ActionHandle abgelegt ist.
 * 
 * Die Klasse ActionHandle dient der Verwaltung der Aktion.
 * Die zugehörige Klasse Handle dient der Ausführung der Aktion, 
 *  nämlich als Argument für das zur Aktion gehörende Event.
 *
 *  In der Ausführungsmethode perform() wird für den Fall einer Handhabe mit Gegenständen,
 *   der Gegenstand oder die Gegenstände der Hände des Akteurs ermittelt und in den Instanzvariablen item1 und item2 abgelegt. 
 *  Danach wird das Ausführungsobjekt der Klasse Handle erzeugt.
 *  Schließlich wird das Ereignis zur Aktion erzeugt, mit dem Ausführungsobjekt als Argument.
 *  Das Ereignis wird in die Ereignisverwaltung (EventMaster) eingetragen.
 *  
 *  Der Name des Ereignis (EventType) 
 *   wird in Abhängigkeit des Aktionsmodus (ActionMode) ermittelt.
 *   
 *  Eine Aktion der Klasse ActionHandle ist 
 *  a) eine Handhabe mit dem/den Gegenstand/Gegenständen in der linken oder rechten Hand, oder beide
 *  oder
 *  b) das Ziehen oder Schieben eines Gegenstandes/Lebewesen, der/das sich nicht im Besitz des Akteurs befindet
 *  oder
 *  c) das Berühren eines Gegenstandes/Lebewesen, der/das sich nicht im Besitz des Akteurs befindet
 *
 */
public class ActionHandle extends AbstractAction {

	private Handle handle;
	
	private Vector direction;
    private SimulationObject target;
    
	private SimulationObject item1;
	private SimulationObject item2;

	public ActionHandle(final ActionType type, final ActionMode mode,
			final SimulationObject target, final Vector direction,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		setBaseProperties(type,  mode,
				intensity,  minTime, maxTime,
				 priority,  duration);
			
			this.setDirection(direction);
			this.target = target;
	}


	/* (non-Javadoc)
	 * @see org.socialworld.actions.AbstractAction#perform()
	 */
	@Override
	public void perform() {

		// TODO continuation of a handle action that belongs more than one time union
		
   		switch (mode) {
		case useItemLeftHand:
			item1 = ((Human) actor).getLeftHand();
			if (item1 == null) return;
			break;
		case useItemRightHand:
			item1 = ((Human) actor).getRightHand();
			if (item1 == null) return;
			break;
		case useTwoItems:
			item1 = ((Human) actor).getRightHand();
			item2 = ((Human) actor).getLeftHand();
			if (item1 == null | item2 == null) return;
			break;
		case combineItems_AddLeftToRight:
			item1 = ((Human) actor).getRightHand();
			item2 = ((Human) actor).getLeftHand();
			if (item1 == null | item2 == null) return;
			break;
		case combineItems_AddRightToLeft:
			item1 = ((Human) actor).getLeftHand();
			item2 = ((Human) actor).getRightHand();
			if (item1 == null | item2 == null) return;
			break;

		default:
		}

   		this.handle = new Handle(this);
  
   		EventByAction event;
		event = new EventByAction( getEventType(type, mode),    actor /* as causer*/,  ActualTime.asTime(),
						actor.getPosition(),  handle /* as performer */);
		addEvent(event);

	}


	
	private EventType getEventType(ActionType type, ActionMode mode) {

		switch (type) {
			case handleItem:
				switch (mode) {
					case useTwoItems:
						return EventType.handleItemUse2;
					case useItemLeftHand:
						return EventType.handleItemUseLeft;
					case useItemRightHand:
						return EventType.handleItemUseRight;
					case combineItems_AddRightToLeft:
						return EventType.handleItemAddRtoL;
					case combineItems_AddLeftToRight:
						return EventType.handleItemAddLtoR;
					case pull:
						return EventType.handleItemPull;
					case push:
						return EventType.handleItemPush;
					default:
						return EventType.nothing;
				}
			
			case touch:
				switch (mode) {
				// TODO
					case hand:
						return EventType.touchByHand;
					case foot:
						return EventType.touchByFoot;
					default:
						return EventType.nothing;
				}
			default:
				return EventType.nothing;
				
		}
		
	}

	/**
	 * @return the direction
	 */
	public Vector getDirection() {
		return this.direction;
	}

	/**
	 * @return the direction
	 */
	public Vector getDirectionCopy() {
		return new Vector(this.direction);
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(final Vector direction) {
		this.direction = direction;
	}

	public SimulationObject getItem1() {
		return this.item1;
	}
	
	public SimulationObject getItem2() {
		return this.item2;
	}

	public SimulationObject getTarget() {
		return this.target;
	}
}
