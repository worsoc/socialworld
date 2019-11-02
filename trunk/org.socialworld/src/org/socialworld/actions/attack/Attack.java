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


import org.socialworld.actions.ActionPerformer;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;

/**
 * German:
 * Die Klasse Attack ist von der abstrakten Klasse ActionPerformer abgeleitet.
  * 
 * Die Klasse Attack dient der Wirksamwerdung der Aktion,
 *  nämlich als Argument für das zur Aktion gehörende Ereignis.
 *
 *  In der Ausführungsmethode perform() werden für den Fall eines Waffenangriffs/Faustschlags
 *   - die Richtung des Angriffs
 *   - die Intensität des Akteurs
 *   - die Intensität des Akteurs auf globaler Skala
 *   - die Waffe (das als Waffe verwendete Objekt)
 *   - die Richtung (Ausrichtung) der Brust
 *   - die Blickrichtung
 *   für den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
*  In der Ausführungsmethode perform() werden für den Fall eines tierischen Angriffs
 *   - die Richtung des Angriffs
 *   - die Intensität des Akteurs
 *   - die Intensität des Akteurs auf globaler Skala
 *   für den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 * 
 * @author Mathias Sikos
 *
 */
public class Attack extends ActionPerformer {

// TODO implementation methods	performFightAttack() and performAnimalAttack() (until now very very simple)
	
 	public  Attack (ActionAttack action) {
 		super(action);
 	}
 	
    protected final void choosePropertiesFromPropertyList(ValueArrayList properties) {
    	
    	Value property;
    	
    	property = properties.getValue(Value.ARGUMENT_VALUE_BY_NAME_ATTRIBUTES);
    	if (property.isValid()) {
    		addProperty(property);
    	}
 
       	property = properties.getValue(Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_CHEST);
    	if (property.isValid()) {
    		addProperty(property);
    	}

       	property = properties.getValue(Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_VIEW);
    	if (property.isValid()) {
    		addProperty(property);
    	}

       	property = properties.getValue(Value.ARGUMENT_VALUE_BY_NAME_INTENSITY_ACTION);
    	if (property.isValid()) {
    		addProperty(property);
    	}

      	property = properties.getValue(Value.ARGUMENT_VALUE_BY_NAME_WEAPON_ACTION);
    	if (property.isValid()) {
    		addProperty(property);
    	}
  	
    }
 
    
   	public void perform() {
		
		if (!isValid()) {
	
			if (isHumanFightAttack())
				performFightAttack();
			else
				performAnimalAttack();
			
		}
		
	}



   	private void performFightAttack() {
   		
   		/*
		ActionAttack originalAction;
		final Human actor;
		
		IWeapon weapon;
		float actorsIntensity;
		float intensity;
		int actorsPower;
		
		Value directionChest;
		Value directionView;
		Value directionHit;
	
		originalAction  = (ActionAttack) getOriginalActionObject();
		actor = (Human) originalAction.getActor();
		
		
		
		
		actorsPower = actor.getAttribute(Attribute.power);
			
		// to do
	  	directionChest = actor.getDirectionChestAsValue("directionChest");
		directionView = actor.getDirectionViewAsValue("directionView");
		directionHit = actor.getDirectionViewAsValue(Value.ARGUMENT_VALUE_BY_NAME_EVENT_DIRECTION);

		// to do
		actorsIntensity = originalAction.getIntensity();
		intensity = actorsIntensity * actorsPower;
	
		weapon = originalAction.getWeapon();
	
	
	
		addParam( directionHit);
		addParam( new Value(Type.floatingpoint, "actorsIntensity", actorsIntensity));
		addParam( new Value(Type.floatingpoint, "intensity", intensity));
		addParam( new Value(Type.simulationObject, "weapon", weapon));
		addParam( directionChest);
		addParam( directionView);
		*/
   		
		setValid();
  		
   	}
   	
   	private void performAnimalAttack() {
   		/*
		ActionAttack originalAction;
		final Animal actor;
		
		float actorsIntensity;
		float intensity;
		int actorsPower;
		
		Value directionHit;
	
		originalAction  = (ActionAttack) getOriginalActionObject();
		actor = (Animal) originalAction.getActor();
		
		actorsPower = actor.getAttribute(Attribute.power);
			
		// to do
		directionHit = actor.getDirectionViewAsValue(Value.ARGUMENT_VALUE_BY_NAME_EVENT_DIRECTION);

		// to do
		actorsIntensity = originalAction.getIntensity();
		intensity = actorsIntensity * actorsPower;
	
		addParam(directionHit);
		addParam( new Value(Type.floatingpoint, "actorsIntensity", actorsIntensity));
		addParam( new Value(Type.floatingpoint, "intensity", intensity));
		
		*/
   		
		setValid();
  		
   	}
   	
   	private boolean isHumanFightAttack() {
		ActionAttack originalAction;
		originalAction = (ActionAttack) getOriginalActionObject();
		
		switch (originalAction.getType()) {
		case useWeapon:
		case punch:
			return true;
		default:
			return false;
			
		}
   	}
}
