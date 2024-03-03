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
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.Event;
import org.socialworld.core.EventToCandidates;
import org.socialworld.core.EventToCauser;
import org.socialworld.core.EventType;
import org.socialworld.core.IEventParam;
import org.socialworld.objects.Animal;
import org.socialworld.objects.access.HiddenAnimal;

/**
 * @author Mathias Sikos
 * 
 * German:
 * Die Klasse ActionMove ist von der abstrakten Klasse AbstractAction abgeleitet.
 * Alle Aktionsobjekte, die Bewegung (also Positions�nderung) beschreiben, geh�ren zu dieser Klasse.
 * Zur Beschreibung der Bewegung f�hrt die Klasse die zus�tzlichen Eigenschaften
 *   Anzahl der Zeiteinheiten f�r die Fortsetzung der Bewegung,
 *   die Kennung, ob es sich um den ersten Schritt handelt,
 *   den Pfad, dem entlang die Bewegung vorgenommen wird,
 *   die Position, an dem die Bewegung abgeschlossen ist,
 *   die Gesamtrichtung der Bewegung und
 *   die Richtung eines Teilsbschnittes.
 * Die Ausf�hrung der Aktion wird in der Klasse Move geregelt, 
 * von der ein Objekt als Eigenschaft der Klasse ActionMove abgelegt ist.
 * 
 * Die Klasse ActionMove dient der Verwaltung der Aktion.
 * Die zugeh�rige Klasse Move dient der Wirksamwerdung der Aktion, 
 *  n�mlich als Argument f�r das zur Aktion geh�rende Event.
 *
 *  In der Ausf�hrungsmethode perform() werden Pfad, Richtungen und die weitern Eigenschaften
 *    in den Instanzvariablen abgelegt. 
 *  Falls es sich um den ersten Schritt handelt, wird das Ausf�hrungsobjekt der Klasse Move erzeugt,
 *  andernfalls existiert es bereits.
 *  Schlie�lich wird das Ereignis zur Aktion erzeugt, mit dem Ausf�hrungsobjekt als Argument.
 *  Das Ereignis wird in die Ereignisverwaltung (EventMaster) eingetragen.
 *  
 *  Ist ein Pfad abgeschlossen, wird das Wissen �ber ihn beim Akteur abgelegt/angepasst.
 *  
 *  Der Name des Ereignis (EventType) 
 *   wird in Abh�ngigkeit von Aktionsmodus (ActionMode) ermittelt.
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
	private boolean firstStep = true;
	private boolean firstPerforming = true;
	
	private Path path;
	private Position end = Position.getObjectNothing();
	
	private Vector direction;
	
	private Vector directionForSection;
	
	public ActionMove(ValueArrayList actionProperties) {
		super(actionProperties);
	}


	public ActionMove(ActionMove original) {
		super(original);
	}


	protected void setFurtherProperties(ValueArrayList actionProperties) {

		Value value;
		
		Position endPosition;
		Vector direction;

		value =  actionProperties.getValue(furtherPropertyNames[0]);
		if (value.isValid()) {
			endPosition = new Position(PropertyName.action_position, (Vector) value.getObject() );
			this.setEnd(endPosition);
		}

		value =  actionProperties.getValue(furtherPropertyNames[1]);
		if (value.isValid()) {
			direction = (Vector) value.getObject();
			this.setDirection(direction);
		}
		
	}
	
	protected void setFurtherProperties(AbstractAction original) {
		setEnd(((ActionMove) original).getEnd());
		setDirection(((ActionMove) original).getDirection());
	}
	
	public  void perform() {
		
		boolean moveCompleted = false;
		
		if (!this.actor.isSimulationObject()) return;

		firstStep = firstPerforming;
		
		if ((!firstStep) && (path != null)) {
			moveCompleted = !move.checkContinueMove();
			
			if (moveCompleted) path.completeSection(actor.getPosition(SimulationCluster.action));
			
			if (path.isCompleted()) {
				if (path.hasRefToKnownPaths()) {
					path.incrementUsageCounter(1);
				}
				else {
					((HiddenAnimal) getHiddenWriteAccessToActor(this)).addPath(path);
				}
			}
		}
		
		
		if (moveCompleted || this.firstStep) 			createMove();
		this.firstPerforming = false;
		
		Event event;
		EventType eventType;
		
		eventType = getEventToCauserType(mode);
		if (eventType != EventType.nothing) {
			event = new EventToCauser( eventType,    actor /* as causer*/,  ActualTime.asTime(),
						actor.getPosition(SimulationCluster.action),  move /* as performer */);	
			addEvent(event);
		}
		
		eventType = getEventToCandidatesType(mode);
		if (eventType != EventType.nothing) {
			event = new EventToCandidates( eventType,    actor /* as causer*/,  ActualTime.asTime(),
						actor.getPosition(SimulationCluster.action),  move /* as performer */);	
			addEvent(event);
		}

	}

	private EventType getEventToCauserType(ActionMode mode) {
		switch (mode) {
		case walk:
			return EventType.selfMoveWalk;
		case run:
			return EventType.selfMoveRun;
		case sneak:
			return EventType.selfMoveSneak;
		case jump:
			return EventType.selfMoveJump;
		case swim:
			return EventType.selfMoveSwim;
		case fly: 
			return EventType.selfMoveFly;
		default:
			return EventType.nothing;
		}
	}
	
	private EventType getEventToCandidatesType(ActionMode mode) {
		switch (mode) {
		case walk:
			return EventType.candidatesMoveWalk;
		case run:
			return EventType.candidatesMoveRun;
		case sneak:
			return EventType.candidatesMoveSneak;
		case jump:
			return EventType.candidatesMoveJump;
		case swim:
			return EventType.candidatesMoveSwim;
		case fly: 
			return EventType.candidatesMoveFly;
		default:
			return EventType.nothing;
		}
	}
	
	
	private void createMove() {
		
		if (path == null & end != null) createPath();
		
		if (path == null) { 
			this.directionForSection = new Vector(this.direction);
		}
		else {
			Position nextPoint = path.getNextPoint();
			if (!nextPoint.checkIsObjectNothing()) {
				this.directionForSection = this.actor.getPosition(SimulationCluster.action).getDirectionTo(nextPoint);
			}
			else {
				this.directionForSection = new Vector(this.direction);
			}
		}	
		
		this.move = new Move( this);
		
	}

	private void createPath() {
		path = ((Animal) actor).findPath(end);
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

	public boolean isFirstStep() {
		return this.firstStep;
	}
	
	float getSectionLength() {
		return this.directionForSection.length();
	}

	private Value getSectionDirectionAsValue(String valueName) {
		return new Value(Type.vector, valueName, new Vector(this.directionForSection));
	}
	
	private Value getVelocityAsValue(String valueName) {
		return new Value(Type.floatingpoint, valueName, this.move.getVelocity());
	}

	private Value getAccelerationAsValue(String valueName) {
		return new Value(Type.floatingpoint, valueName, this.move.getAcceleration());
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IEventParam paramObject) {
	
		super.requestPropertyList(paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		
		propertiesAsValueList.add(getSectionDirectionAsValue(Value.VALUE_BY_NAME_ACTION_DIRECTION));
		propertiesAsValueList.add(getVelocityAsValue(Value.VALUE_BY_NAME_ACTION_MOVE_VELOCITY));
		propertiesAsValueList.add(getAccelerationAsValue(Value.VALUE_BY_NAME_ACTION_MOVE_ACCELERATION));
		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}
	
	
}
