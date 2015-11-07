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
package org.socialworld.objects;
import org.socialworld.actions.AbstractAction;
import org.socialworld.attributes.Position;
import org.socialworld.calculation.Vector;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenSimulationObject;

public class WriteAccessToSimulationObject {
	
	public static final int WRITE_ACCESS_RETURNS_INVALID_CALLER = -1;
	public static final int WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY = -2;
	public static final int WRITE_ACCESS_RETURNS_SUCCESS = 0;
	
	private SimulationObject object;
	private StateSimulationObject objectsState;
	
	boolean isDummy;
	
	public WriteAccessToSimulationObject(SimulationObject object, StateSimulationObject state, HiddenSimulationObject returnHidden) {
		GrantedAccessToProperty propertiesToInit[];
		
		this.object = object;
		this.objectsState = state;
		
		isDummy = false;

		object.setWriteAccess(this);
		object.setState(state, this);
		state.setWriteAccess(this);
		state.setObject(object);
		
		propertiesToInit = new GrantedAccessToProperty[1];
		propertiesToInit[0] = GrantedAccessToProperty.all;
		
		returnHidden = getMeHidden(propertiesToInit);
	}
	
	// Dummy
	public WriteAccessToSimulationObject() {
		isDummy = true;
	}

	
	public HiddenSimulationObject getMeHidden(GrantedAccessToProperty properties[]) {
		return new HiddenSimulationObject(this, properties);
	}
	
	public SimulationObject getObject() {
		return this.object;
	}
	
	
	protected final boolean checkCaller(HiddenSimulationObject caller){
		
		if (!caller.isValid()) return false;
		
		switch (caller.getClass().getName()) {

		case "org.socialworld.objects.access.HiddenSimulationObject":
			return true;
		case "org.socialworld.objects.access.HiddenAnimal":
			return true;
		case "org.socialworld.objects.access.HiddenHuman":
			return true;
			
		default:
			return false;
		}
		
	}
	
	protected final boolean checkAccessToPropertyGranted(HiddenSimulationObject caller, GrantedAccessToProperty property) {
		return caller.checkAccessToPropertyGranted(property);
	}
	
	public void setObjectID(int objectID, HiddenSimulationObject caller) {
		if (checkCaller(caller) ) object.setObjectID(objectID, this);
	}

	public int setPosition(Position pos, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.position)) {
				objectsState.setPosition(pos, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int setMove(Vector direction, float power, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionMove) & 
					checkAccessToPropertyGranted(caller, GrantedAccessToProperty.powerMove)) {
				objectsState.setMove(direction, power , this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int setAction(AbstractAction action, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.action)) {
				object.setAction(action, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int setInfluenceTypes(int types[], HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.influenceTypes)) {
				objectsState.setInfluenceTypes(types, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int setReactionTypes(int types[], HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.reactionTypes)) {
				objectsState.setReactionTypes(types, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int setState2ActionType(int type, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.state2ActionType)) {
				objectsState.setState2ActionType(type, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}



}
