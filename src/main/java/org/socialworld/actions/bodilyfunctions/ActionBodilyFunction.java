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
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.Event;
import org.socialworld.core.EventToCauser;
import org.socialworld.core.EventToTarget;
import org.socialworld.core.EventType;
import org.socialworld.core.IEventParam;
import org.socialworld.objects.Animal;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.properties.IDrinkable;
import org.socialworld.objects.properties.IEatable;

/**
 * German:
 * Die Klasse ActionBodilyFunction ist von der abstrakten Klasse AbstractAction abgeleitet.
 * Alle Aktionsobjekte, die Schlafen beschreiben, geh�ren zu dieser Klasse.
 * 
 * Die Ausf�hrung der Aktion wird in der Klasse BodilyFunction geregelt, 
 *   von der ein Objekt als Eigenschaft der Klasse ActionBodilyFunction abgelegt ist.
 * Zur Beschreibung des Schlafens f�hrt die Klasse keine weiteren Eigenschaften.
 * 
 * Die Klasse ActionBodilyFunction dient der Verwaltung der Aktion.
 * Die zugeh�rige Klasse BodilyFunction dient der Wirksamwerdung der Aktion, 
 *  n�mlich als Argument f�r das zur Aktion geh�rende Event.
 *
 *  In der Ausf�hrungsmethode perform() wird das Ausf�hrungsobjekt der Klasse BodilyFunction erzeugt.
 *  Au�erdem wird das Ereignis zur Aktion erzeugt, mit dem Ausf�hrungsobjekt als Argument.
 *  Das Ereignis wird in die Ereignisverwaltung (EventMaster) eingetragen.
 *  
 *  Der Name des Ereignis (EventType) 
 *   wird in Abh�ngigkeit von Aktionsmodus (ActionMode) ermittelt.
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
	private SimulationObject item = NoSimulationObject.getObjectNothing();
	
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
		
		boolean withEventTotarget = false;
		
   		switch (mode) {
		case sleep:
			break;
		case drink:
			this.item = (SimulationObject) ((Animal) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_mouth, PropertyName.inventory_mouth.toString()).getObject();
//			this.item = ((Animal) actor).getMouthItem();
			if 	(!(this.item instanceof IDrinkable)) {
				return;
			}
			withEventTotarget = true;
			break;
		case eat:
			this.item = (SimulationObject) ((Animal) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_mouth, PropertyName.inventory_mouth.toString()).getObject();
//			this.item = ((Animal) actor).getMouthItem();
			if 	(!(this.item instanceof IEatable)) {
				return;
			}
			withEventTotarget = true;
			break;
		case piss:
			break;
		case shit:
			break;

		default:
		}
		
		bodilyFunction = new BodilyFunction(this);
		
		Event event;
		EventType eventType;
		
		eventType = getEventToCauserType(mode);
		if (eventType != EventType.nothing) {
			event = new EventToCauser(eventType,    actor /* as causer*/,  ActualTime.asTime(),
						actor.getPosition(SimulationCluster.action),  bodilyFunction /* as performer */);
			addEvent(event);
		}
		
		if (withEventTotarget) {
			eventType = getEventToTargetType(mode);
			if (eventType != EventType.nothing) {
				event = new EventToTarget(eventType,    actor /* as causer*/,  ActualTime.asTime(),
							actor.getPosition(SimulationCluster.action),  bodilyFunction /* as performer */);
				addEvent(event);
			}
		}
	}


	private EventType getEventToCauserType( ActionMode mode) {
		switch (mode) {
		case sleep:
			return EventType.selfSleep;
		case drink:
			return EventType.selfDrink;
		case eat:
			return EventType.selfEat;
		case piss:
			return EventType.selfPiss;
		case shit:
			return EventType.selfShit;
		default:
			return EventType.nothing;
		}
	}
	
	private EventType getEventToTargetType( ActionMode mode) {
		switch (mode) {
		case drink:
			return EventType.targetDrink;
		case eat:
			return EventType.targetEat;
		default:
			return EventType.nothing;
		}
	}
	
	public Value getItemAsValue(String valueName) {
		if (!this.item.isSimulationObject()) {
			return Value.getValueNothing();
		}
		else {
			return new Value(Type.simulationObject, valueName, this.item);
		}
	}

	public Value getItemDrinkAsValue(String valueName) { 
		if (!this.item.isSimulationObject()) {
			return Value.getValueNothing();
		}
		else {
			if 	 (this.item instanceof IDrinkable) {
				return new Value(Type.simulationObject, valueName, this.item);
			}
			else {
				return Value.getValueNothing();
			}
		}
	}

	public Value getItemEatAsValue(String valueName) { 
		if (!this.item.isSimulationObject()) {
			return Value.getValueNothing();
		}
		else {
			if 	 (this.item instanceof IEatable) {
				return new Value(Type.simulationObject, valueName, this.item);
			}
			else {
				return Value.getValueNothing();
			}
		}
	}

	public SimulationObject getItem() {
		return this.item;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IEventParam paramObject) {
	
		super.requestPropertyList(paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		
		propertiesAsValueList.add(getItemAsValue(Value.VALUE_BY_NAME_ACTION_BF_ITEM));
		propertiesAsValueList.add(getItemDrinkAsValue(Value.VALUE_BY_NAME_ACTION_BF_ITEMDRINK));
		propertiesAsValueList.add(getItemEatAsValue(Value.VALUE_BY_NAME_ACTION_BF_ITEMEAT));
		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}
	
}
