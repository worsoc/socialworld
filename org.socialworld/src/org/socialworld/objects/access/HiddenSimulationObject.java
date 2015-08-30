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
	
	public final boolean checkAccessToPropertyGranted(  GrantedAccessToProperty property){
		int size;
		int index;
		
		size = propertiesWithGrantedAccess.length;
		
		for (index = 0; index < size; index++) {
			if (propertiesWithGrantedAccess[index] == property ) return true;
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
	
	
	public boolean setPosition(Position pos) {
		return wa.setPosition(pos, this);
	}
	
	public boolean setAction(AbstractAction action) {
		return wa.setAction(action, this);
	}

	public boolean setInfluenceTypes(int types[]) {
		return wa.setInfluenceTypes(types, this);
	}

	public boolean setReactionTypes(int types[]) {
		return wa.setReactionTypes(types, this);
	}

	public boolean setState2ActionType(int type) {
		return wa.setState2ActionType(type, this);
	}	
}
