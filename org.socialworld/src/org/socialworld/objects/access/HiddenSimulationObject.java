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
	
	public HiddenSimulationObject(WriteAccessToSimulationObject wa, GrantedAccessToProperty properties[]) {
		this.wa = wa;
		this.isValid = true;
	}
	
	// dummy constructor
	public HiddenSimulationObject() {
		this.isValid = false;
	}
	
	public final boolean isValid() {
		return isValid;
	}
	
	/*protected WriteAccessToSimulationObject myWriteAccess() {
		return wa;
	}
	*/
	public void closeAccess(WriteAccessToSimulationObject wa) {
		if (wa == this.wa) isValid = false;
	}
	
	
	public void setPosition(Position pos) {
		wa.setPosition(pos, this);
	}
	
	public void setAction(AbstractAction action) {
		wa.setAction(action, this);
	}

	public void setInfluenceTypes(int types[]) {
		wa.setInfluenceTypes(types, this);
	}

	public void setReactionTypes(int types[]) {
		wa.setReactionTypes(types, this);
	}

	public void setState2ActionType(int type) {
		wa.setState2ActionType(type, this);
	}	
}
