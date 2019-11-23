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
package org.socialworld.objects.access;

import org.socialworld.actions.AbstractAction;
import org.socialworld.attributes.Position;
import org.socialworld.calculation.Vector;
import org.socialworld.objects.SimulationObject_Type;
import org.socialworld.objects.State;
import org.socialworld.objects.WriteAccessToSimulationObject;

/**
 * @author Mathias Sikos
 *
 */
public class HiddenSimulationObject {

	private WriteAccessToSimulationObject wa;
	private boolean isValid;
	
	private GrantedAccessToProperty propertiesWithGrantedAccess[];
	
	public HiddenSimulationObject(WriteAccessToSimulationObject wa, GrantedAccessToProperty properties[]) {
		this.wa = wa;
		this.isValid = true;
		this.propertiesWithGrantedAccess = properties;
	}
	
	// dummy constructor
	public HiddenSimulationObject() {
		this.isValid = false;
	}
	
	public final boolean isValid() {
		return isValid;
	}
	
	public SimulationObject_Type getSimObjectType() {
		return this.wa.getSimObjectType();
	}
	
	public final boolean checkAccessToPropertyGranted(  GrantedAccessToProperty property){
		int size;
		int index;
		
		size = propertiesWithGrantedAccess.length;
		
		for (index = 0; index < size; index++) {
			if ( (propertiesWithGrantedAccess[index] == property ) 
					|| (propertiesWithGrantedAccess[index] == GrantedAccessToProperty.all) ) return true;
		}
		return false;
	}
	
	/*protected WriteAccessToSimulationObject myWriteAccess() {
		return wa;
	}
	*/
	public void closeAccess(WriteAccessToSimulationObject wa) {
		if (wa == this.wa) isValid = false;
	}
	
	
	public int setPosition(Position pos) {
		return wa.setPosition(pos, this);
	}
	
	public int setMove(Vector direction, float  power) {
		return wa.setMove(direction, power, this);
	}

	public int setAction(AbstractAction action) {
		return wa.setAction(action, this);
	}

	public int setInfluenceTypes(int types[]) {
		return wa.setInfluenceTypes(types, this);
	}

	public int setReactionTypes(int types[]) {
		return wa.setReactionTypes(types, this);
	}

	public int setState2ActionType(int type) {
		return wa.setState2ActionType(type, this);
	}
	
	final public int setSomething(String stateClassName, String methodName, Object something) {
		return wa.callMethodSetSomething(stateClassName, methodName, something, this);
	}
	
	final public int addState(State state) {
		return wa.addState(state, this);
	}
}
