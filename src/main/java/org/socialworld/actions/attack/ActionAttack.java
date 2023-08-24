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
package org.socialworld.actions.attack;


import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.Event;
import org.socialworld.core.EventToCandidates;
import org.socialworld.core.EventToCauser;
import org.socialworld.core.EventToTarget;
import org.socialworld.core.EventType;
import org.socialworld.core.IEventParam;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.properties.IWeapon;
import org.socialworld.objects.Animal;
import org.socialworld.objects.NoSimulationObject;

/**
 * German:
 * Die Klasse ActionAttack ist von der abstrakten Klasse AbstractAction abgeleitet.
 * Alle Aktionsobjekte, die Angriffe beschreiben, geh�ren zu dieser Klasse.
 * Zur Beschreibung eines Angriffs f�hrt die Klasse die zus�tzlichen Eigenschaften
 * f�r die ggf. verwendete Waffe und das Zielobjekt.
 * Die Ausf�hrung der Aktion wird in der Klasse Attack geregelt, 
 * von der ein Objekt als Eigenschaft der Klasse ActionAttack abgelegt ist.
 * 
 * Die Klasse ActionAttack dient der Verwaltung der Aktion.
 * Die zugeh�rige Klasse Attack dient der Ausf�hrung der Aktion, 
 *  n�mlich als Argument f�r das zur Aktion geh�rende Event.
 *
 *  In der Ausf�hrungsmethode perform() wird f�r den Fall eines Waffenangriffs
 *   die Waffe des Akteurs ermittelt und in der Instanzvariablen weapon abgelegt. 
 *  Danach wird das Ausf�hrungsobjekt der Klasse Attack erzeugt.
 *  Schlie�lich wird das Ereignis zur Aktion erzeugt, mit dem Ausf�hrungsobjekt als Argument.
 *  Das Ereignis wird in die Ereignisverwaltung (EventMaster) eingetragen.
 *  
 *  Der Name des Ereignis (EventType) 
 *   wird in Abh�ngigkeit von Aktionstyp (ActionType) und Aktionsmodus (ActionMode) ermittelt.
 *   
 *  Eine Aktion der Klasse ActionAttack ist 
 *  a) ein Angriff mit einer Waffe in der linken oder rechten Hand, oder beide
 *  oder
 *  b) ein Fausschlag (Gerade, Seitw�rtshaken oder  Aufw�rtshaken) mit links oder rechts
 * 
 * @author Mathias Sikos
 * 
 *   
 */
public class ActionAttack extends AbstractAction {
	private Attack attack;
	private IWeapon weapon;
	SimulationObject target;
	
	public ActionAttack(ValueArrayList actionProperties) {
		super(actionProperties);
	}

	
	public ActionAttack(ActionAttack original) {
		super(original);
	}

	protected void setFurtherProperties(ValueArrayList actionProperties) {

		Value value;
		
		SimulationObject target;

		value =  actionProperties.getValue(furtherPropertyNames[0]);
		// TODO there is no target, why?
		if (value.isValid()) {
			target =  (SimulationObject) value.getValue() ;
			this.setTarget(target);
		}

	}

	protected void setFurtherProperties(AbstractAction original) {
		setTarget(((ActionAttack) original).getTarget());
	}
	
	/* (non-Javadoc)
	 * @see org.socialworld.actions.AbstractAction#perform()
	 */
	@Override
	public void perform() {
		
		SimulationObject itemLeft;
		SimulationObject itemRight;
		IWeapon weapon = null;

		switch (type) {
		
		case useWeapon:
	   		switch (mode) {
			case weaponLeftStab:
			case weaponLeftStroke:
			case weaponLeftBackhand:
				itemLeft = (SimulationObject) ((Animal) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_leftHand, PropertyName.inventory_leftHand.toString()).getValue();
				if (itemLeft instanceof IWeapon) {
					weapon = (IWeapon) itemLeft;
				}
				else {
					weapon = IWeapon.getObjectNothing();
				}
				break;
			case weaponRightStab:
			case weaponRightStroke:
			case weaponRightBackhand:
				itemRight = (SimulationObject) ((Animal) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_rightHand, PropertyName.inventory_rightHand.toString()).getValue();
				if (itemRight instanceof IWeapon) {
					weapon = (IWeapon) itemRight;
				}
				else {
					weapon = IWeapon.getObjectNothing();
				}
				break;
			case weaponClub:
				itemLeft = (SimulationObject) ((Animal) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_leftHand, PropertyName.inventory_leftHand.toString()).getValue();
				itemRight = (SimulationObject) ((Animal) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_rightHand, PropertyName.inventory_rightHand.toString()).getValue();
				if (itemLeft != null && itemRight != null && !itemLeft.equals(itemRight)) {
					weapon = IWeapon.getObjectNothing();
				}
				if (itemLeft instanceof IWeapon) {
					weapon = (IWeapon) itemLeft;
				}
				else {
					weapon = IWeapon.getObjectNothing();
				}
		  		break;
			default:
				weapon = IWeapon.getObjectNothing();
			}
	   		break;
		case punch:
			switch (mode) {
			case punchLeftFistStraight:
			case punchLeftFistSideways:
			case punchLeftFistUpward:
				itemLeft = (SimulationObject) ((Animal) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_leftHand, PropertyName.inventory_leftHand.toString()).getValue();
				if (itemLeft instanceof IWeapon) {
					weapon = (IWeapon) itemLeft;
				}
				else {
					weapon = IWeapon.getObjectNothing();
				}
				break;
			case punchRightFistStraight:
			case punchRightFistSideways:
			case punchRightFistUpward:
				// TODO there must not be a weapon in the fist
				itemRight = (SimulationObject) ((Animal) actor).getStateProperty(SimulationCluster.todo, PropertyName.stateInventory, PropertyName.inventory_rightHand, PropertyName.inventory_rightHand.toString()).getValue();
				if (itemRight instanceof IWeapon) {
					weapon = (IWeapon) itemRight;
				}
				else {
					weapon = IWeapon.getObjectNothing();
				}
				break;
			default:
				weapon = IWeapon.getObjectNothing();
			
			}
			break;
	   	default:
	   		weapon = IWeapon.getObjectNothing();
		}
		
		
   		this.weapon = weapon;
   		
   		// if no weapon --> no attack --> break 
   		if (weapon.isObjectNothing()) return;
   		
      	this.attack = new Attack( this);
      				
      	Event event;
		EventType eventType;
     	
		eventType = getEventToCandidatesType(type, mode);
		if (eventType != EventType.nothing) {
			event = new EventToCandidates(eventType ,    actor /* as causer*/,  ActualTime.asTime(),
							actor.getPosition(SimulationCluster.action),  attack /* as performer */);
			addEvent(event);
		}
		
		eventType = getEventToTargetType(type, mode);
		if (eventType != EventType.nothing) {
			event = new EventToTarget(eventType,    actor /* as causer*/,  ActualTime.asTime(),
							actor.getPosition(SimulationCluster.action),  attack /* as performer */);
			addEvent(event);
		}
		
		eventType = getEventToCauserType(type, mode);
		if (eventType != EventType.nothing) {
			event = new EventToCauser(eventType,    actor /* as causer*/,  ActualTime.asTime(),
					actor.getPosition(SimulationCluster.action),  attack /* as performer */);
			addEvent(event);
		}
		
	}

	
	
	private EventType getEventToCandidatesType(ActionType type, ActionMode mode ) {
		EventType eventType;
		
    		switch (type) {
    		case useWeapon:
    			
    			switch (mode) {
      			case weaponClub:
    				eventType = EventType.candidatesWeaponClub;
    				break;
      			case weaponLeftStab:
    				eventType = EventType.candidatesWeaponLeftStab;
    				break;
    			case weaponLeftStroke:
    				eventType = EventType.candidatesWeaponLeftStroke;
    				break;
      			case weaponLeftBackhand:
    				eventType = EventType.candidatesWeaponLeftBackhand;
    				break;
     			case weaponRightStab:
    				eventType = EventType.candidatesWeaponRightStab;
    				break;
    			case weaponRightStroke:
    				eventType = EventType.candidatesWeaponRightStroke;
    				break;
      			case weaponRightBackhand:
    				eventType = EventType.candidatesWeaponRightBackhand;
    				break;
    			default:
    				eventType = EventType.nothing;
    			}
    			
    			break;
    			
    		case punch:
    			switch (mode) {
    			case punchLeftFistStraight:
      				eventType = EventType.candidatesPunchLeftFistStraight;
    				break;
       			case punchLeftFistSideways:
      				eventType = EventType.candidatesPunchLeftFistSideways;
    				break;
       			case punchLeftFistUpward:
      				eventType = EventType.candidatesPunchLeftFistUpward;
    				break;
       			case punchRightFistStraight:
      				eventType = EventType.candidatesPunchRightFistStraight;
    				break;
       			case punchRightFistSideways:
      				eventType = EventType.candidatesPunchRightFistSideways;
    				break;
       			case punchRightFistUpward:
      				eventType = EventType.candidatesPunchRightFistUpward;
    				break;
   				
 	   			default:
					eventType = EventType.nothing;
    			
    			}
    			
    			break;
    			
    		default:
				eventType = EventType.nothing;
   			
    		}

	
	  	return eventType;
	}

	private EventType getEventToTargetType(ActionType type, ActionMode mode ) {
		EventType eventType;
		
    		switch (type) {
    		case useWeapon:
    			
    			switch (mode) {
      			case weaponClub:
    				eventType = EventType.targetWeaponClub;
    				break;
      			case weaponLeftStab:
    				eventType = EventType.targetWeaponLeftStab;
    				break;
    			case weaponLeftStroke:
    				eventType = EventType.targetWeaponLeftStroke;
    				break;
      			case weaponLeftBackhand:
    				eventType = EventType.targetWeaponLeftBackhand;
    				break;
     			case weaponRightStab:
    				eventType = EventType.targetWeaponRightStab;
    				break;
    			case weaponRightStroke:
    				eventType = EventType.targetWeaponRightStroke;
    				break;
      			case weaponRightBackhand:
    				eventType = EventType.targetWeaponRightBackhand;
    				break;
    			default:
    				eventType = EventType.nothing;
    			}
    			
    			break;
    			
    		case punch:
    			switch (mode) {
    			case punchLeftFistStraight:
      				eventType = EventType.targetPunchLeftFistStraight;
    				break;
       			case punchLeftFistSideways:
      				eventType = EventType.targetPunchLeftFistSideways;
    				break;
       			case punchLeftFistUpward:
      				eventType = EventType.targetPunchLeftFistUpward;
    				break;
       			case punchRightFistStraight:
      				eventType = EventType.targetPunchRightFistStraight;
    				break;
       			case punchRightFistSideways:
      				eventType = EventType.targetPunchRightFistSideways;
    				break;
       			case punchRightFistUpward:
      				eventType = EventType.targetPunchRightFistUpward;
    				break;
   				
 	   			default:
					eventType = EventType.nothing;
    			
    			}
    			
    			break;
    			
    		default:
				eventType = EventType.nothing;
   			
    		}

	
	  	return eventType;
	}
	
	private EventType getEventToCauserType(ActionType type, ActionMode mode ) {
		EventType eventType;
		
    		switch (type) {
    		case useWeapon:
    			
    			switch (mode) {
      			case weaponClub:
    				eventType = EventType.selfWeaponClub;
    				break;
      			case weaponLeftStab:
    				eventType = EventType.selfWeaponLeftStab;
    				break;
    			case weaponLeftStroke:
    				eventType = EventType.selfWeaponLeftStroke;
    				break;
      			case weaponLeftBackhand:
    				eventType = EventType.selfWeaponLeftBackhand;
    				break;
     			case weaponRightStab:
    				eventType = EventType.selfWeaponRightStab;
    				break;
    			case weaponRightStroke:
    				eventType = EventType.selfWeaponRightStroke;
    				break;
      			case weaponRightBackhand:
    				eventType = EventType.selfWeaponRightBackhand;
    				break;
    			default:
    				eventType = EventType.nothing;
    			}
    			
    			break;
    			
    		case punch:
    			switch (mode) {
    			case punchLeftFistStraight:
      				eventType = EventType.selfPunchLeftFistStraight;
    				break;
       			case punchLeftFistSideways:
      				eventType = EventType.selfPunchLeftFistSideways;
    				break;
       			case punchLeftFistUpward:
      				eventType = EventType.selfPunchLeftFistUpward;
    				break;
       			case punchRightFistStraight:
      				eventType = EventType.selfPunchRightFistStraight;
    				break;
       			case punchRightFistSideways:
      				eventType = EventType.selfPunchRightFistSideways;
    				break;
       			case punchRightFistUpward:
      				eventType = EventType.selfPunchRightFistUpward;
    				break;
   				
 	   			default:
					eventType = EventType.nothing;
    			
    			}
    			
    			break;
    			
    		default:
				eventType = EventType.nothing;
   			
    		}

	
	  	return eventType;
	}
	
	public Value getWeaponAsValue(String valueName) {
		return new Value(Type.simulationObject, valueName, this.weapon);
	}
	
	public IWeapon getWeapon() {
		return this.weapon;
	}
	
	public void setTarget(SimulationObject target) {
		this.target = target;
	}
	
	public SimulationObject getTarget() {
		return this.target;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IEventParam paramObject) {
	
		super.requestPropertyList(paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		
		propertiesAsValueList.add(getWeaponAsValue(Value.VALUE_BY_NAME_ACTION_WEAPON));
		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}
	
	
}
