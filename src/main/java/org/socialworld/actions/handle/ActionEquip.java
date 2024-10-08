/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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

import org.socialworld.GlobalSwitches;
import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.NoObject;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.Event;
import org.socialworld.core.EventToCauser;
import org.socialworld.core.EventToTarget;
import org.socialworld.core.EventType;
import org.socialworld.core.IEventParam;
import org.socialworld.objects.SimulationObject;

public class ActionEquip extends AbstractAction {

	private Equip equip;
	
    private SimulationObject item;
    
    private InventoryPlace inventoryPlace;

	private static AccessTokenActionHandle token = AccessTokenActionHandle.getValid();
  
	public ActionEquip(ValueArrayList actionProperties) {
		super(actionProperties);
	}

	@Override
	protected void setFurtherProperties(ValueArrayList actionProperties) {

		Value value;
		
		SimulationObject item;
		InventoryPlace inventoryPlace;

		value =  actionProperties.getValue(furtherPropertyNames[0]);
		if (value.isValid()) {
			item = objectRequester.requestSimulationObject(token, value, this);
			this.setItem(item);
		}

		value =  actionProperties.getValue(furtherPropertyNames[1]);
		if (value.isValid()) {
			Object o = value.getObject(Type.integer);
			if (o instanceof NoObject) {
				if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
					System.out.println("ActionEquip.setFurtherProperties > inventoryPlace: o (getObject(Type.integer)) is NoObject " + ((NoObject)o).getReason().toString() );
				}
				inventoryPlace = InventoryPlace.getName(0);
			}
			else {
				inventoryPlace = InventoryPlace.getName((int)o);
			}

			this.setInventoryPlace(inventoryPlace);
		}

	}

	@Override
	protected void setFurtherProperties(AbstractAction original) {
		this.setItem(((ActionEquip) original).getItem());
		this.setInventoryPlace(((ActionEquip) original).getInventoryPlace());

	}

	@Override
	public void perform() {

      	this.equip = new Equip( this);
      	
		Position position = actor.getPosition(token);
		Time actualTime = ActualTime.asTime();

      	Event event;
      	EventType eventType;
      	
      	// event with influence to equipment objects
		eventType = getEventToTargetType(mode);
		if (eventType != EventType.nothing) {
			event = new EventToTarget( eventType,    actor /* as causer*/, actualTime ,
					position,  equip /* as performer */);
			addEvent(event);
		}
		
      	// event with influence to causer itself 
		eventType = getEventToCauserType(mode);
		if (eventType != EventType.nothing) {
	     	event = new EventToCauser( eventType,    actor /* as causer*/,  actualTime,
	     			position,  equip /* as performer */);
			addEvent(event);
		}
		
	}

	private EventType getEventToCauserType(ActionMode mode) {
		
		EventType eventType;
 			
		switch (mode) {

			case takeItem:
				eventType = EventType.selfInventoryTake;
				break;
			case dropItem:
				eventType = EventType.selfInventoryDrop;
				break;
			case switchItemToOtherHand:
				eventType = EventType.selfInventorySwitch;
				break;
			case setItemToInventory:
				eventType = EventType.selfInventorySet;
				break;
			case getItemFromInventory:
				eventType = EventType.selfInventoryGet;
				break;
			default:
				eventType = EventType.nothing;
				
		}
		
	  	return eventType;
	  	
	}

	private EventType getEventToTargetType(ActionMode mode) {
		
		EventType eventType;
   			
		switch (mode) {
		
			case takeItem:
				eventType = EventType.targetInventoryTake;
				break;
			case dropItem:
				eventType = EventType.targetInventoryDrop;
				break;
			case switchItemToOtherHand:
				eventType = EventType.targetInventorySwitch;
				break;
			case setItemToInventory:
				eventType = EventType.targetInventorySet;
				break;
			case getItemFromInventory:
				eventType = EventType.targetInventoryGet;
				break;
			default:
				eventType = EventType.nothing;
			
		}
		
	  	return eventType;
	  	
	}
	
	public void setInventoryPlace(InventoryPlace inventoryPlace) {
		this.inventoryPlace = inventoryPlace;
	}

	private InventoryPlace getInventoryPlace() {
		return this.inventoryPlace;
	}

	public Value getInventoryPlaceAsValue(String valueName) {
		return new Value(Type.integer, valueName, this.inventoryPlace.getIndex());
	}
	
	public void setItem(SimulationObject item) {
		this.item = item;
	}

	protected SimulationObject getItem() {
		return this.item;
	}

	public Value getItemAsValue(String valueName) {
		return new Value(Type.simulationObject, valueName, this.item);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IEventParam paramObject) {
	
		super.requestPropertyList(paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		
		propertiesAsValueList.add(getItemAsValue(Value.VALUE_BY_NAME_ACTION_EQUIP_ITEM));
		paramObject.answerPropertiesRequest(propertiesAsValueList);
		
	}
	

}
