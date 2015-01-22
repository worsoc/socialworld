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
package org.socialworld.actions.move;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Vector;
import org.socialworld.core.EventByAction;
import org.socialworld.core.EventType;
import org.socialworld.objects.Animal;
import org.socialworld.objects.WriteAccessToAnimal;

/**
 * @author Mathias Sikos
 * 
 * German:
 * Die Klasse ActionMove ist von der abstrakten Klasse AbstractAction abgeleitet.
 * Alle Aktionsobjekte, die Bewegung (also Positionsänderung) beschreiben, gehören zu dieser Klasse.
 * Zur Beschreibung der Bewegung führt die Klasse die zusätzlichen Eigenschaften
 *   Anzahl der Zeiteinheiten für die Fortsetzung der Bewegung,
 *   die Kennung, ob es sich um den ersten Schritt handelt,
 *   den Pfad, dem entlang die Bewegung vorgenommen wird,
 *   die Position, an dem die Bewegung abgeschlossen ist,
 *   die Gesamtrichtung der Bewegung und
 *   die Richtung eines Teilsbschnittes.
 * Die Ausführung der Aktion wird in der Klasse Move geregelt, 
 * von der ein Objekt als Eigenschaft der Klasse ActionMove abgelegt ist.
 * 
 * Die Klasse ActionMove dient der Verwaltung der Aktion.
 * Die zugehörige Klasse Move dient der Wirksamwerdung der Aktion, 
 *  nämlich als Argument für das zur Aktion gehörende Event.
 *
 *  In der Ausführungsmethode perform() werden Pfad, Richtungen und die weitern Eigenschaften
 *    in den Instanzvariablen abgelegt. 
 *  Falls es sich um den ersten Schritt handelt, wird das Ausführungsobjekt der Klasse Move erzeugt,
 *  andernfalls existiert es bereits.
 *  Schließlich wird das Ereignis zur Aktion erzeugt, mit dem Ausführungsobjekt als Argument.
 *  Das Ereignis wird in die Ereignisverwaltung (EventMaster) eingetragen.
 *  
 *  Ist ein Pfad abgeschlossen, wird das Wissen über ihn beim Akteur abgelegt/angepasst.
 *  
 *  Der Name des Ereignis (EventType) 
 *   wird in Abhängigkeit von Aktionsmodus (ActionMode) ermittelt.
 *   
 *  Eine Aktion der Klasse ActionMove ist 
 *  a) die Bewegung hin zu einer Zielposition (hergeleitet Richtung)
 *  oder
 *  b) die Bewegung entlang einer vorgegebenen Richtung
 * 
 *
 */
public class ActionMove extends AbstractAction {
	
	private Move move;
	private int repeatsMove;
	private boolean firstStep;
	
	private Path path;
	private Position end;
	
	private Vector direction;
	
	private Vector directionForSection;
	
	public ActionMove(final ActionType type, final ActionMode mode,
			final Position end,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		setBaseProperties(type,  mode,
				intensity,  minTime, maxTime,
				 priority,  duration);
			
			this.setEnd(end);
			this.firstStep = true;
	}

	public ActionMove(final ActionType type, final ActionMode mode,
			final Vector direction,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		setBaseProperties(type,  mode,
				intensity,  minTime, maxTime,
				 priority,  duration);
			
			this.setDirection(direction);
			this.firstStep = true;
	}

	public ActionMove(ActionMove original) {
		setBaseProperties(original);
		this.direction = original.direction;
	}


	public Vector getDirectionForSection() {
		return new Vector(this.directionForSection);
	}
	
	public  void perform() {
		if (actor == null) return;

		if (!firstStep) {
			repeatsMove = move.getNumberOfRepeats();
			
			if (repeatsMove == 0) path.completeSection(actor.getPosition());
			
			if (path.isCompleted()) 
				if (path.hasRefToKnownPaths()) 
					path.incrementUsageCounter(1);
				else
				{
					((WriteAccessToAnimal ) getWriteAccess(this)).addPath(path, this);
				}
		}
		
		firstStep = false;
		
		if (repeatsMove == 0) 			createMove();
		
		EventByAction event;
		event = new EventByAction( getEventType(mode),    actor /* as causer*/,  ActualTime.asTime(),
					actor.getPosition(),  move /* as performer */);
		addEvent(event);
		
	}
	
	private void createMove() {
		
		if (path == null & end != null) createPath();
		
		if (path == null) 
			this.directionForSection = new Vector(this.direction);
		else
			this.directionForSection = this.actor.getPosition().getDirectionTo(path.getNextPoint());
			
		
		this.move = new Move( this);
		
	}

	private void createPath() {
		path = ((Animal) actor).findPath(end);
	}
	
	private EventType getEventType(ActionMode mode) {
		switch (mode) {
		case walk:
			return EventType.moveWalk;
		case run:
			return EventType.moveRun;
		case sneak:
			return EventType.moveSneak;
		case jump:
			return EventType.moveJump;
		case swim:
			return EventType.moveSwim;
		case fly: 
			return EventType.moveFly;
		default:
			return EventType.nothing;
		}
	}
	
	/**
	}
	 * @return the direction
	 */
	public Vector getDirection() {
		return this.direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(final Vector direction) {
		this.direction = direction;
	}


	/**
	 * @return the end
	 */
	public Position getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Position end) {
		this.end = end;
	}

}
