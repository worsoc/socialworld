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
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.EventToCandidates;
import org.socialworld.core.EventType;
import org.socialworld.core.IEventParam;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.properties.IWeapon;
import org.socialworld.objects.Human;

/**
 * German:
 * Die Klasse ActionAttack ist von der abstrakten Klasse AbstractAction abgeleitet.
 * Alle Aktionsobjekte, die Angriffe beschreiben, gehören zu dieser Klasse.
 * Zur Beschreibung eines Angriffs führt die Klasse die zusätzlichen Eigenschaften
 * für die ggf. verwendete Waffe und das Zielobjekt.
 * Die Ausführung der Aktion wird in der Klasse Attack geregelt, 
 * von der ein Objekt als Eigenschaft der Klasse ActionAttack abgelegt ist.
 * 
 * Die Klasse ActionAttack dient der Verwaltung der Aktion.
 * Die zugehörige Klasse Attack dient der Ausführung der Aktion, 
 *  nämlich als Argument für das zur Aktion gehörende Event.
 *
 *  In der Ausführungsmethode perform() wird für den Fall eines Waffenangriffs
 *   die Waffe des Akteurs ermittelt und in der Instanzvariablen weapon abgelegt. 
 *  Danach wird das Ausführungsobjekt der Klasse Attack erzeugt.
 *  Schließlich wird das Ereignis zur Aktion erzeugt, mit dem Ausführungsobjekt als Argument.
 *  Das Ereignis wird in die Ereignisverwaltung (EventMaster) eingetragen.
 *  
 *  Der Name des Ereignis (EventType) 
 *   wird in Abhängigkeit von Aktionstyp (ActionType) und Aktionsmodus (ActionMode) ermittelt.
 *   
 *  Eine Aktion der Klasse ActionAttack ist 
 *  a) ein Angriff mit einer Waffe in der linken oder rechten Hand, oder beide
 *  oder
 *  b) ein Fausschlag (Gerade, Seitwärtshaken oder  Aufwärtshaken) mit links oder rechts
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
		IWeapon weapon;

		switch (type) {
		
		case useWeapon:
	   		switch (mode) {
			case weaponLeftStab:
			case weaponLeftStroke:
			case weaponLeftBackhand:
				weapon = ((Human) actor).getLeftHandWeapon();
		  		if (weapon == null) return;
				break;
			case weaponRightStab:
			case weaponRightStroke:
			case weaponRightBackhand:
				weapon = ((Human)actor).getRightHandWeapon();
		  		if (weapon == null) return;
				break;
			case weaponClub:
				weapon = ((Human) actor).getLeftHandWeapon();
		  		if (weapon == null) return;
		  		if (weapon != ((Human)actor).getRightHandWeapon()) return;
		  		break;
			default:
				return;
			}
	   		break;
		case punch:
			switch (mode) {
			case punchLeftFistStraight:
			case punchLeftFistSideways:
			case punchLeftFistUpward:
				weapon = ((Human) actor).getLeftHandWeapon();
				break;
			case punchRightFistStraight:
			case punchRightFistSideways:
			case punchRightFistUpward:
				weapon = ((Human) actor).getRightHandWeapon();
				break;
			default:
				return;
			
			}
			break;
	   	default:
	   		return;
		}
		
 
   		this.weapon = weapon;
      	this.attack = new Attack( this);
      				
      	EventToCandidates event;
		event = new EventToCandidates( getEventType(type, mode),    actor /* as causer*/,  ActualTime.asTime(),
						actor.getPosition(),  attack /* as performer */);
		addEvent(event);
		
	}

	public Value getWeaponAsValue(String valueName) {
		return new Value(Type.simulationObject, valueName, this.weapon);
	}
	
	
	private EventType getEventType(ActionType type, ActionMode mode) {
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
