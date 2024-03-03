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
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.Event;
import org.socialworld.core.EventToCauser;
import org.socialworld.core.EventToTarget;
import org.socialworld.core.EventType;
import org.socialworld.core.IEventParam;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos
 * 
 * German:
 * Die Klasse ActionHandle ist von der abstrakten Klasse AbstractAction abgeleitet.
 * Alle Aktionsobjekte, die Handhabe mit einem Simulationsobjekt beschreiben, geh�ren zu dieser Klasse.
 * Zur Beschreibung einer Handhabe f�hrt die Klasse die zus�tzlichen Eigenschaften
 * f�r die verwendeten Gegenst�nde aus dem Inventar , das Zielobjekt und die Richtung der T�tigkeit.
 * Die Ausf�hrung der Aktion wird in der Klasse Handle geregelt, 
 * von der ein Objekt als Eigenschaft der Klasse ActionHandle abgelegt ist.
 * 
 * Die Klasse ActionHandle dient der Verwaltung der Aktion.
 * Die zugeh�rige Klasse Handle dient der Ausf�hrung der Aktion, 
 *  n�mlich als Argument f�r das zur Aktion geh�rende Event.
 *
 *  In der Ausf�hrungsmethode perform() wird f�r den Fall einer Handhabe mit Gegenst�nden,
 *   der Gegenstand oder die Gegenst�nde der H�nde des Akteurs ermittelt und in den Instanzvariablen item1 und item2 abgelegt. 
 *  Danach wird das Ausf�hrungsobjekt der Klasse Handle erzeugt.
 *  Schlie�lich wird das Ereignis zur Aktion erzeugt, mit dem Ausf�hrungsobjekt als Argument.
 *  Das Ereignis wird in die Ereignisverwaltung (EventMaster) eingetragen.
 *  
 *  Der Name des Ereignis (EventType) 
 *   wird in Abh�ngigkeit des Aktionsmodus (ActionMode) ermittelt.
 *   
 *  Eine Aktion der Klasse ActionHandle ist 
 *  a) eine Handhabe mit dem/den Gegenstand/Gegenst�nden in der linken oder rechten Hand, oder beide
 *  oder
 *  b) das Ziehen oder Schieben eines Gegenstandes/Lebewesen, der/das sich nicht im Besitz des Akteurs befindet
 *  oder
 *  c) das Ber�hren eines Gegenstandes/Lebewesen, der/das sich nicht im Besitz des Akteurs befindet
 *
 */
public class ActionHandle extends AbstractAction {

	private Handle handle;
	
	private Vector direction;
    private SimulationObject target;
    
	private SimulationObject item1;
	private SimulationObject item2;

	public ActionHandle(ValueArrayList actionProperties) {
		super(actionProperties);
	}

	public ActionHandle(ActionHandle original) {
		super(original);
	}
	
	protected void setFurtherProperties(ValueArrayList actionProperties) {

		Value value;
		
		SimulationObject target;
		Vector direction;

		value =  actionProperties.getValue(furtherPropertyNames[0]);
		if (value.isValid()) {
			target =  (SimulationObject) value.getObject() ;
			this.setTarget(target);
		}

		value =  actionProperties.getValue(furtherPropertyNames[1]);
		if (value.isValid()) {
			direction = (Vector) value.getObject();
			this.setDirection(direction);
		}

	}

	protected void setFurtherProperties(AbstractAction original) {
		setTarget(((ActionHandle) original).getTarget());
		setDirection(((ActionHandle) original).getDirection());
	}

	/* (non-Javadoc)
	 * @see org.socialworld.actions.AbstractAction#perform()
	 */
	@Override
	public void perform() {

		// TODO continuation of a handle action that belongs more than one time union
		
		boolean withEventToTarget = true;
		
   		switch (mode) {
		case useItemLeftHand:
			item1 = (SimulationObject) ((Human) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_leftHand, PropertyName.inventory_leftHand.toString()).getValue();
// vormals:			item1 = ((Human) actor).getLeftHandItem();
			if (!this.item1.isSimulationObject()) return;
			break;
		case useItemRightHand:
			item1 = (SimulationObject) ((Human) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_rightHand, PropertyName.inventory_rightHand.toString()).getValue();
//			item1 = ((Human) actor).getRightHandItem();
			if (!this.item1.isSimulationObject()) return;
			break;
		case useTwoItems:
			item1 = (SimulationObject) ((Human) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_rightHand, PropertyName.inventory_rightHand.toString()).getValue();
			item2 = (SimulationObject) ((Human) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_leftHand, PropertyName.inventory_leftHand.toString()).getValue();
//			item1 = ((Human) actor).getRightHandItem();
//			item2 = ((Human) actor).getLeftHandItem();
			if (!this.item1.isSimulationObject() | !this.item2.isSimulationObject()) return;
			break;
		case combineItems_AddLeftToRight:
			item1 = (SimulationObject) ((Human) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_rightHand, PropertyName.inventory_rightHand.toString()).getValue();
			item2 = (SimulationObject) ((Human) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_leftHand, PropertyName.inventory_leftHand.toString()).getValue();
//			item1 = ((Human) actor).getRightHandItem();
//			item2 = ((Human) actor).getLeftHandItem();
			if (!this.item1.isSimulationObject() | !this.item2.isSimulationObject()) return;
			break;
		case combineItems_AddRightToLeft:
			item1 = (SimulationObject) ((Human) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_leftHand, PropertyName.inventory_leftHand.toString()).getValue();
			item2 = (SimulationObject) ((Human) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_rightHand, PropertyName.inventory_rightHand.toString()).getValue();
//			item1 = ((Human) actor).getLeftHandItem();
//			item2 = ((Human) actor).getRightHandItem();
			if (!this.item1.isSimulationObject() | !this.item2.isSimulationObject()) return;
			break;
		case pull:
		case push:
			if (!this.target.isSimulationObject()) return;
			break;
		case hand:
		case foot:
			if (!this.target.isSimulationObject()) {
				withEventToTarget = false;
			}
			break;
		default:
		}

   		this.handle = new Handle(this);
  
   		Event event;
   		EventType eventType;
   		
   		if (withEventToTarget) {
			eventType = getEventToTargetType(type, mode);
			if (eventType != EventType.nothing) {
				event = new EventToTarget( eventType,    actor /* as causer*/,  ActualTime.asTime(),
								actor.getPosition(SimulationCluster.action),  handle /* as performer */);
				addEvent(event);
			}
   		}
   		
		eventType = getEventToCauserType(type, mode);
		if (eventType != EventType.nothing) {
			event = new EventToCauser( eventType,    actor /* as causer*/,  ActualTime.asTime(),
							actor.getPosition(SimulationCluster.action),  handle /* as performer */);
			addEvent(event);
		}
		
		
	}

	private EventType getEventToTargetType(ActionType type, ActionMode mode) {

		switch (type) {
			case handleItem:
				switch (mode) {
					case useTwoItems:
						return EventType.targetHandleItemUse2;
					case useItemLeftHand:
						return EventType.targetHandleItemUseLeft;
					case useItemRightHand:
						return EventType.targetHandleItemUseRight;
					case combineItems_AddRightToLeft:
						return EventType.targetHandleItemAddRtoL;
					case combineItems_AddLeftToRight:
						return EventType.targetHandleItemAddLtoR;
					case pull:
						return EventType.targetHandleItemPull;
					case push:
						return EventType.targetHandleItemPush;
					default:
						return EventType.nothing;
				}
			
			case touch:
				switch (mode) {
					case hand:
						return EventType.targetTouchByHand;
					case foot:
						return EventType.targetTouchByFoot;
					default:
						return EventType.nothing;
				}
			default:
				return EventType.nothing;
				
		}
		
	}

	
	private EventType getEventToCauserType(ActionType type, ActionMode mode) {

		switch (type) {
			case handleItem:
				switch (mode) {
					case useTwoItems:
						return EventType.selfHandleItemUse2;
					case useItemLeftHand:
						return EventType.selfHandleItemUseLeft;
					case useItemRightHand:
						return EventType.selfHandleItemUseRight;
					case combineItems_AddRightToLeft:
						return EventType.selfHandleItemAddRtoL;
					case combineItems_AddLeftToRight:
						return EventType.selfHandleItemAddLtoR;
					case pull:
						return EventType.selfHandleItemPull;
					case push:
						return EventType.selfHandleItemPush;
					default:
						return EventType.nothing;
				}
			
			case touch:
				switch (mode) {
					case hand:
						return EventType.selfTouchByHand;
					case foot:
						return EventType.selfTouchByFoot;
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
	private Vector getDirection() {
		return this.direction;
	}

	/**
	 * @return the direction
	 */
	public Value getDirectionAsValue(String valueName) {
		Vector direction;
		if (!this.target.isSimulationObject()) {
			direction = new Vector(this.direction);
		}
		else {
			direction = actor.getPosition(SimulationCluster.action).getDirectionTo(this.target.getPosition(SimulationCluster.action));
		}
		return new Value(Type.vector, valueName, direction);
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(final Vector direction) {
		this.direction = direction;
	}

	public Value getItem1AsValue(String valueName) {
		return new Value(Type.simulationObject, valueName, this.item1);
	}

	protected SimulationObject getItem1() {
		return this.item1;
	}

	public Value getItem2AsValue(String valueName) {
		return new Value(Type.simulationObject, valueName, this.item2);
	}

	protected SimulationObject getItem2() {
		return this.item2;
	}
	
	public void setTarget(SimulationObject target) {
		this.target = target;
	}
	
	protected SimulationObject getTarget() {
		return this.target;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IEventParam paramObject) {
	
		super.requestPropertyList(paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		
		propertiesAsValueList.add(getItem1AsValue(Value.VALUE_BY_NAME_ACTION_ITEM1));
		propertiesAsValueList.add(getItem2AsValue(Value.VALUE_BY_NAME_ACTION_ITEM2));
		propertiesAsValueList.add(getDirectionAsValue(Value.VALUE_BY_NAME_ACTION_DIRECTION));
		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}

}
